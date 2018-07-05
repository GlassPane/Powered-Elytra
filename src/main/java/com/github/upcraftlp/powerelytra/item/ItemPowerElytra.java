package com.github.upcraftlp.powerelytra.item;

import com.github.upcraftlp.glasspane.api.capability.CapabilityProviderSerializable;
import com.github.upcraftlp.glasspane.item.ItemBase;
import com.github.upcraftlp.powerelytra.PoweredElytra;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public class ItemPowerElytra extends ItemBase {

    private final int capacity;
    private final int consumptionPerTick;
    private final int consumptionPerRocket;

    public ItemPowerElytra(String name, int batteryCapacity, int consumptionPerTick, int consumptionPerRocket) {
        super(name);
        this.capacity = batteryCapacity;
        this.consumptionPerTick = consumptionPerTick;
        this.consumptionPerRocket = consumptionPerRocket;
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.addPropertyOverride(new ResourceLocation(PoweredElytra.MODID, "wing_type"), (stack, world, entity) -> 1); //FIXME overrides?!
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list){
        if(this.isInCreativeTab(tabs)){
            ItemStack stackFull = new ItemStack(this);
            if(stackFull.hasCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING)){
                IEnergyStorage storage = stackFull.getCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING);
                if(storage != null){
                    storage.receiveEnergy(storage.getMaxEnergyStored(), false);
                    list.add(stackFull);
                }
            }
        }
        super.getSubItems(tabs, list);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return armorType == EntityEquipmentSlot.CHEST;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityProviderSerializable<>(CapabilityEnergy.ENERGY, DEFAULT_FACING, new EnergyStorage(this.getCapacity()));
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getMaxEnergyStored(ItemStack stack) {
        if(stack.hasCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING);
            return storage.getMaxEnergyStored();
        }
        return this.getCapacity();
    }

    public int getCurrentEnergyStored(ItemStack stack) {
        if(stack.hasCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING);
            return storage.getEnergyStored();
        }
        return 0;
    }

    @Override
    public boolean getShareTag() { //needed to make sure the capabilities stay in sync with the client
        return true;
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        stack.setTagInfo("sync_elytra_energy", new NBTTagInt(getCurrentEnergyStored(stack))); //trick MC into sending the stack capabilities to the client!
        return super.getNBTShareTag(stack);
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.CHEST;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
        //TODO add custom elytra model
    }



    /**
     * @return how much energy to consume per single booster rocket; only one booster rocket can be spawned per second!
     */
    public int getConsumptionPerRocket(ItemStack stack) {
        //TODO account for unbreaking enchantment!
        return this.consumptionPerRocket;
    }

    public int getTickConsumption(ItemStack stack) {
        return consumptionPerTick;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if(stack.hasCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING)) {
            IEnergyStorage battery = stack.getCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING);
            double max = battery.getMaxEnergyStored();
            double difference = max - battery.getEnergyStored();
            return difference / max;
        }
        return super.getDurabilityForDisplay(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NumberFormat numberFormatter = NumberFormat.getInstance();
        tooltip.add(TextFormatting.GRAY.toString() + String.format("%s/%s FE", numberFormatter.format(getCurrentEnergyStored(stack)), numberFormatter.format(getMaxEnergyStored(stack))));
        if(flagIn.isAdvanced() || GuiScreen.isAltKeyDown()) {
            tooltip.add(TextFormatting.GRAY.toString() + String.format("Usage per tick: %s", numberFormatter.format(getTickConsumption(stack))));
            tooltip.add(TextFormatting.GRAY.toString() + String.format("Usage per rocket: %s", numberFormatter.format(getConsumptionPerRocket(stack))));
        }
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        //TODO filter enchantments!
        return super.isBookEnchantable(stack, book);
    }

    public boolean isUsableElytra(EntityPlayer player, ItemStack stack) {
        if(player.isCreative()) return true;
        else if(stack.hasCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING)) {
            IEnergyStorage battery = stack.getCapability(CapabilityEnergy.ENERGY, DEFAULT_FACING);
            int toConsume = this.getTickConsumption(stack);
            return battery.canExtract() && battery.extractEnergy(toConsume, true) >= toConsume;
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (playerIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty()) { //player is not wearing a chestpiece
            playerIn.setItemStackToSlot(EntityEquipmentSlot.CHEST, itemstack.copy());
            itemstack.setCount(0);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }
}
