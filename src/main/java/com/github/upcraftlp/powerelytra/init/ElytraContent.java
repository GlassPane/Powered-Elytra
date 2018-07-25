package com.github.upcraftlp.powerelytra.init;

import com.github.upcraftlp.glasspane.api.registry.AutoRegistry;
import com.github.upcraftlp.powerelytra.PoweredElytra;
import com.github.upcraftlp.powerelytra.item.ItemCreativeElytra;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
@AutoRegistry(PoweredElytra.MODID)
public class ElytraContent {

    public static final Item CREATIVE_ELYTRA = new ItemCreativeElytra();
}
