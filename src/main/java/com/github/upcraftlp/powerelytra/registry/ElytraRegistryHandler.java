package com.github.upcraftlp.powerelytra.registry;

import com.github.upcraftlp.glasspane.client.ClientUtil;
import com.github.upcraftlp.glasspane.config.Lens;
import com.github.upcraftlp.glasspane.registry.GlassPaneAutomatedRegistry;
import com.github.upcraftlp.glasspane.util.JsonUtil;
import com.github.upcraftlp.powerelytra.PoweredElytra;
import com.github.upcraftlp.powerelytra.config.PowerElytraConfig;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = PoweredElytra.MODID)
public class ElytraRegistryHandler {

    public static List<ElytraConfig> CUSTOM_CONFIGS = new LinkedList<>();

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {

        //TODO check other config values!
        CUSTOM_CONFIGS.forEach(config -> GlassPaneAutomatedRegistry.register(new ItemPowerElytra(config.name, config.textureName, config.capacity, config.costPerFlightTick, config.costPerBoosterRocket, config.canUseRockets, config.canUseRFBoost), event.getRegistry()));

        //cleanup
        CUSTOM_CONFIGS = null;
        MinecraftForge.EVENT_BUS.unregister(ElytraRegistryHandler.class);
    }

    public static void init(FMLPreInitializationEvent event) {
        File configDir = new File(event.getModConfigurationDirectory(), "glasspanemods/custom_elytra");
        if(!configDir.exists()) {
            PoweredElytra.getLogger().info("no default elytra config found, creating one!");
            File config = new File(configDir, "basic_elytra.json");
            try(InputStream inputStream = PowerElytraConfig.ConfigHandler.class.getResourceAsStream("/assets/" + PoweredElytra.MODID + "/config/basic_elytra.json")) {
                FileUtils.copyInputStreamToFile(inputStream, config);
            } catch(IOException e) {
                PoweredElytra.getLogger().error("unable to write default config file!", e);
            }

            if(event.getSide().isClient()) {
                if(Lens.debugMode) PoweredElytra.getLogger().info("copying default resources...");
                try(InputStream inputStream0 = PowerElytraConfig.class.getResourceAsStream("/assets/" + PoweredElytra.MODID + "/config/resources/basic_elytra_model.json"); InputStream inputStream1 = PowerElytraConfig.class.getResourceAsStream("/assets/" + PoweredElytra.MODID + "/config/resources/en_us.lang")) {
                    FileUtils.copyInputStreamToFile(inputStream0, new File(ClientUtil.RESOURCES_DIR, "assets/" + PoweredElytra.MODID + "/models/item/basic_elytra.json"));
                    FileUtils.copyInputStreamToFile(inputStream1, new File(ClientUtil.RESOURCES_DIR, "assets/" + PoweredElytra.MODID + "/lang/en_us.lang"));
                } catch(IOException e) {
                    PoweredElytra.getLogger().error("unable to write default resources!", e);
                }
            }
        }

        File[] files = configDir.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".json"));
        if(files != null) {
            Arrays.stream(files).forEach(configFile -> {
                try {
                    CUSTOM_CONFIGS.add(JsonUtil.GSON.fromJson(new FileReader(configFile), ElytraConfig.class));
                } catch(FileNotFoundException e) {
                    //should never happen!
                    e.printStackTrace();
                }
            });
        }
        else PoweredElytra.getLogger().error("{} does not denote a directory!", configDir.getAbsolutePath());
    }
}
