package com.github.upcraftlp.powerelytra.init;

import com.github.upcraftlp.glasspane.api.registry.AutoRegistry;
import com.github.upcraftlp.powerelytra.PoweredElytra;
import com.github.upcraftlp.powerelytra.item.ItemCreativeElytra;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
@AutoRegistry(PoweredElytra.MODID)
public class ElytraContent {

    public static final Item BASIC_ELYTRA = new ItemPowerElytra("basic_elytra", 288000, 80, 1000); //flight time: 3 minutes = 180 seconds = 3600 ticks
    public static final Item CREATIVE_ELYTRA = new ItemCreativeElytra();
}
