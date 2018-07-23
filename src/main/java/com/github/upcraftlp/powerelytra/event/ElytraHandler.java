package com.github.upcraftlp.powerelytra.event;

import com.github.upcraftlp.powerelytra.PoweredElytra;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = PoweredElytra.MODID)
public class ElytraHandler {

    public static synchronized void setFlying(EntityPlayerMP playerMP, boolean flying) {
        LAST_TICK_FLIGHT.put(playerMP, flying);
        if(flying) {
            if(!playerMP.isElytraFlying()) playerMP.setElytraFlying();
        }
        else if(playerMP.isElytraFlying()) playerMP.clearElytraFlying();
    }

    public static boolean isFlying(EntityPlayerMP playerMP) {
        return LAST_TICK_FLIGHT.getOrDefault(playerMP, false);
    }

    private static final Map<EntityPlayerMP, Boolean> LAST_TICK_FLIGHT = new WeakHashMap<>(); //use a weak hash map so we can use EntityPlayerMP as keys!

    @SubscribeEvent
    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.END) { //END phase is important, otherwise this won't work because vanilla keeps resetting the elytra state!
            if(event.player instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) event.player;
                ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
                boolean flag = false;
                if(stack.getItem() instanceof ItemPowerElytra) {
                    ItemPowerElytra elytra = (ItemPowerElytra) stack.getItem();
                    flag = elytra.isUsableElytra(player, stack) && !player.onGround && !player.isRiding() && !player.capabilities.isFlying;
                    if(flag) {
                        if(!player.isCreative() && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                            IEnergyStorage battery = stack.getCapability(CapabilityEnergy.ENERGY, null);
                            if(battery != null) battery.extractEnergy(elytra.getTickConsumption(stack), false);
                        }
                        player.fallDistance = 0.0F;
                    }
                }
                if(isFlying(player)) setFlying(player, flag);
            }
        }
    }
}
