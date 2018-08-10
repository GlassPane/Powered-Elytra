package com.github.upcraftlp.powerelytra.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.github.upcraftlp.powerelytra.PoweredElytra.*;

/**
 * @author UpcraftLP
 */
@Config(modid = MODID, name = "glasspanemods/PoweredElytra") //--> /config/glasspanemods/PoweredElytra.cfg
public class PowerElytraConfig {

    @SuppressWarnings("unused") //parsed via recipe conditions
    @Config.Name("Basic Elytra Recipe")
    @Config.Comment({"register a recipe for the default elytra", "can be used to disable the crafting recipe in favor of adding custom recipes via crafttweaker"})
    public static boolean enableBasicElytraRecipe = true;

    @Mod.EventBusSubscriber(modid = MODID)
    public static class ConfigHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(MODID)) {
                ConfigManager.load(MODID, Config.Type.INSTANCE);
            }
        }
    }
}
