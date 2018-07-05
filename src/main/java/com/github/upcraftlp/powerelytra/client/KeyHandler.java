package com.github.upcraftlp.powerelytra.client;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.powerelytra.PoweredElytra;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import com.github.upcraftlp.powerelytra.net.PacketElytraBoostRocket;
import com.github.upcraftlp.powerelytra.net.PacketElytraStartFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = PoweredElytra.MODID, value = Side.CLIENT)
public class KeyHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static long lastPacket = 0L;
    private static boolean lastTickJump = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(mc.player != null && mc.player.isElytraFlying() && mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemPowerElytra) {
                if(mc.gameSettings.keyBindJump.isKeyDown()) {
                    long currentTime = System.nanoTime() / 1000000L;
                    if(currentTime - PacketElytraBoostRocket.NETWORK_TIMEOUT >= lastPacket) {
                        NetworkHandler.INSTANCE.sendToServer(new PacketElytraBoostRocket());
                        lastPacket = currentTime;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPressKey(InputUpdateEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        if(!lastTickJump && event.getMovementInput().jump && !player.isElytraFlying() && !player.onGround && !player.isRiding() && !player.capabilities.isFlying && !player.capabilities.allowFlying) {
            ItemStack stack = mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if(stack.getItem() instanceof ItemPowerElytra && ((ItemPowerElytra) stack.getItem()).isUsableElytra(mc.player, stack)) NetworkHandler.INSTANCE.sendToServer(new PacketElytraStartFlying());
        }
        lastTickJump = event.getMovementInput().jump;
    }
}
