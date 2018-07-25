package com.github.upcraftlp.powerelytra.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCreativeElytra extends ItemPowerElytra {

    public ItemCreativeElytra() {
        super("creative_elytra", null,0, 0, 0);
        this.setHasSubtypes(false);
        this.setHasAdvancedTooltip(false);
    }

    @Override
    public boolean isUsableElytra(EntityPlayer player, ItemStack stack) {
        return true;
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tabs))
        {
            list.add(new ItemStack(this));
        }
    }

    @Override
    public void showTooltip(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flag) {
        //super.showTooltip(stack, worldIn, tooltip, flagIn); //not needed, as we don't want to display FE usage of zero!
        tooltip.add(TextFormatting.DARK_PURPLE.toString() + TextFormatting.ITALIC.toString() + I18n.format("tooltip.power_elytra.elytra_creative"));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

}
