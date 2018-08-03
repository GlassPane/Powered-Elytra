package com.github.upcraftlp.powerelytra.client.render.layer;

import com.github.upcraftlp.glasspane.client.render.layer.LayerCapeCustom;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class LayerCapeElytraCustom extends LayerCapeCustom {

    public LayerCapeElytraCustom(RenderPlayer playerRendererIn) {
        super(playerRendererIn);
    }

    @Override
    protected boolean shouldRender(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        return !(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemPowerElytra) && super.shouldRender(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
