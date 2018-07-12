package com.github.upcraftlp.powerelytra.net;

import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketElytraBoostRocket implements IMessageHandler<PacketElytraBoostRocket, IMessage>, IMessage {

    private static final Map<UUID, Long> LAST_PRESSES = new HashMap<>();
    public static final Long NETWORK_TIMEOUT = 1000L;

    public PacketElytraBoostRocket() {
        //NO-OP
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public synchronized IMessage onMessage(PacketElytraBoostRocket message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        if(player.isElytraFlying()) {
            long currentTime = System.nanoTime() / 1000000L; //now we have milliseconds
            if(LAST_PRESSES.getOrDefault(player.getUniqueID(), 0L) <= currentTime - NETWORK_TIMEOUT) { //delay: 1 sec = 1000 millis
                ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                if(stack.getItem() instanceof ItemPowerElytra) {
                    EntityFireworkRocket rocket = new EntityFireworkRocket(player.world, stack, player);
                    player.world.spawnEntity(rocket);
                    if(!player.isCreative() && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        IEnergyStorage battery = stack.getCapability(CapabilityEnergy.ENERGY, null);
                        int toConsume = ((ItemPowerElytra) stack.getItem()).getConsumptionPerRocket(stack);
                        if(battery.canExtract() && battery.extractEnergy(toConsume, true) >= toConsume) battery.extractEnergy(toConsume, false);
                    }
                }
                LAST_PRESSES.put(player.getUniqueID(), currentTime);
            }
        }
        return null;
    }
}
