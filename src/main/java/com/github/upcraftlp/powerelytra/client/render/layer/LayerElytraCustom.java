package com.github.upcraftlp.powerelytra.client.render.layer;

import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LayerElytraCustom extends LayerElytra {

    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    private final ModelElytra modelElytra = new ModelElytra();

    public LayerElytraCustom(RenderLivingBase render) {
        super(render);
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

        if(itemstack.getItem() == Items.ELYTRA || itemstack.getItem() instanceof ItemPowerElytra) {
            ResourceLocation texture = TEXTURE_ELYTRA;
            String textureName = itemstack.getItem().getArmorTexture(itemstack, entitylivingbaseIn, EntityEquipmentSlot.CHEST, "overlay");
            if(textureName != null) texture = new ResourceLocation(textureName);
            else {
                if(entitylivingbaseIn instanceof AbstractClientPlayer && itemstack.getItem() == Items.ELYTRA) {
                    AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer) entitylivingbaseIn;
                    if(abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null)
                        texture = abstractclientplayer.getLocationElytra();
                    else if(abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE))
                        texture = abstractclientplayer.getLocationCape();
                }
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.translate(0.0F, 0.0F, 0.125F);
                this.renderPlayer.bindTexture(texture);
                ModelBase model = null;
                if(itemstack.getItem() instanceof ItemPowerElytra)
                    model = ((ItemPowerElytra) itemstack.getItem()).getElytraModel(entitylivingbaseIn, itemstack);
                if(model == null) model = this.modelElytra;

                model.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
                model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                if(itemstack.isItemEnchanted())
                    LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, model, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }
}
