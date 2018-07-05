package com.github.upcraftlp.powerelytra;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.powerelytra.net.PacketElytraBoostRocket;
import com.github.upcraftlp.powerelytra.net.PacketElytraStartFlying;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.upcraftlp.powerelytra.PoweredElytra.*;

@SuppressWarnings("WeakerAccess")
@Mod(
        certificateFingerprint = FINGERPRINT_KEY,
        name = MODNAME,
        version = VERSION,
        acceptedMinecraftVersions = MCVERSIONS,
        modid = MODID,
        dependencies = DEPENDENCIES,
        updateJSON = UPDATE_JSON
)
public class PoweredElytra {

    //Version
    public static final String MCVERSIONS = "[1.12.2, 1.13)";
    public static final String VERSION = "@VERSION@";

    //Meta Information
    public static final String MODNAME = "Powered Elytra";
    public static final String MODID = "power_elytra";
    public static final String DEPENDENCIES = "required-after:glasspane";
    public static final String UPDATE_JSON = "@UPDATE_JSON@";

    public static final String FINGERPRINT_KEY = "@FINGERPRINTKEY@";

    private static final Logger log = LogManager.getLogger(MODID);

    private static final boolean debugMode = false; //global debug flag

    public static Logger getLogger() {
        return log;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkHandler.INSTANCE.registerMessage(PacketElytraBoostRocket.class, PacketElytraBoostRocket.class, 0, Side.SERVER);
        NetworkHandler.INSTANCE.registerMessage(PacketElytraStartFlying.class, PacketElytraStartFlying.class, 1, Side.SERVER);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {

    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {

    }
}
