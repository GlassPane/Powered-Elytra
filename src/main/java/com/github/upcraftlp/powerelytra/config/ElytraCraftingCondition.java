package com.github.upcraftlp.powerelytra.config;

import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.function.BooleanSupplier;

@SuppressWarnings("unused") //will be registered via json recipe
public class ElytraCraftingCondition implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        return () -> ReflectionHelper.getPrivateValue(PowerElytraConfig.class, null, json.getAsJsonPrimitive("property").getAsString());
    }
}
