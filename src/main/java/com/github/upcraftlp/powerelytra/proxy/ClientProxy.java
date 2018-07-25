package com.github.upcraftlp.powerelytra.proxy;

import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.powerelytra.client.render.layer.LayerCapeCustom;
import com.github.upcraftlp.powerelytra.client.render.layer.LayerElytraCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

    @SuppressWarnings("unchecked")
    @Override
    public void postInit(FMLPostInitializationEvent event) {
        //FIXME register elytra layer for non-player entities as well!
        //FIXME remove default elytra and cape layers!
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.entrySet().stream().filter(entry -> entry.getValue() instanceof RenderLivingBase).forEach(entry -> {
            RenderLivingBase render = (RenderLivingBase) entry.getValue();
            ((List<LayerRenderer>) ReflectionHelper.getPrivateValue(RenderLivingBase.class, render, "layerRenderers", "field_177097_h")).removeIf(layer -> layer instanceof LayerElytra || layer instanceof LayerCape);
            render.addLayer(new LayerElytraCustom(render));
            if(render instanceof RenderPlayer) render.addLayer(new LayerCapeCustom((RenderPlayer) render));
        });
    }
}
