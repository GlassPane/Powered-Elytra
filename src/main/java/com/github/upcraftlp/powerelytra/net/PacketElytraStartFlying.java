package com.github.upcraftlp.powerelytra.net;

import com.github.upcraftlp.glasspane.api.net.MessageBase;
import com.github.upcraftlp.powerelytra.event.ElytraHandler;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketElytraStartFlying extends MessageBase implements IMessageHandler<PacketElytraStartFlying, IMessage> {

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(PacketElytraStartFlying message, MessageContext ctx) {
        boolean flying = false;
        EntityPlayerMP player = ctx.getServerHandler().player;
        if(!player.onGround && !player.isRiding() && !player.capabilities.isFlying && !player.capabilities.allowFlying) {
            ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if(stack.getItem() instanceof ItemPowerElytra && ((ItemPowerElytra) stack.getItem()).isUsableElytra(player, stack)) {
                flying = true;
            }
        }
        ElytraHandler.setFlying(player, flying);
        return null;
    }
}
