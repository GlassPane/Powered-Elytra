package com.github.upcraftlp.powerelytra.client;

import com.github.upcraftlp.glasspane.api.event.RegisterRenderLayerEvent;
import com.github.upcraftlp.powerelytra.PoweredElytra;
import com.github.upcraftlp.powerelytra.client.render.layer.LayerCapeElytraCustom;
import com.github.upcraftlp.powerelytra.client.render.layer.LayerElytraCustom;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = PoweredElytra.MODID, value = Side.CLIENT)
public class LayerHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRegisterLayers(RegisterRenderLayerEvent event) {
        event.getLayers().removeIf(layer -> layer instanceof LayerCape || layer instanceof LayerElytra);
        RenderLivingBase render = event.getRender();
        event.addRenderLayer(new LayerElytraCustom(render));
        if(render instanceof RenderPlayer) event.addRenderLayer(new LayerCapeElytraCustom((RenderPlayer) render));
        MinecraftForge.EVENT_BUS.unregister(LayerHandler.class);
    }

}
