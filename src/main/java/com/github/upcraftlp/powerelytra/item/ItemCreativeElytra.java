package com.github.upcraftlp.powerelytra.item;

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
        super("creative_elytra", 0, 0, 0);
        this.setHasSubtypes(false);
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.DARK_PURPLE.toString() + "infinite flight, infinite boost!");
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

}
