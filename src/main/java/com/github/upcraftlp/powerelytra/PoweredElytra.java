package com.github.upcraftlp.powerelytra;

import com.github.upcraftlp.glasspane.api.net.NetworkHandler;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.glasspane.util.ModUpdateHandler;
import com.github.upcraftlp.powerelytra.init.ElytraContent;
import com.github.upcraftlp.powerelytra.net.PacketElytraBoostRocket;
import com.github.upcraftlp.powerelytra.net.PacketElytraStartFlying;
import com.github.upcraftlp.powerelytra.registry.ElytraRegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
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
    public static final String DEPENDENCIES = "required-after:glasspane;required-after:forge";
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

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID + ".name") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ElytraContent.CREATIVE_ELYTRA);
        }
    };

    @SidedProxy(clientSide = "com.github.upcraftlp.powerelytra.proxy.ClientProxy", serverSide = "com.github.upcraftlp.powerelytra.proxy.ServerProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModUpdateHandler.registerMod(MODID);
        NetworkHandler.INSTANCE.registerMessage(PacketElytraBoostRocket.class, PacketElytraBoostRocket.class, NetworkHandler.getNextPacketID(), Side.SERVER);
        NetworkHandler.INSTANCE.registerMessage(PacketElytraStartFlying.class, PacketElytraStartFlying.class, NetworkHandler.getNextPacketID(), Side.SERVER);
        ElytraRegistryHandler.init(event);
        proxy.preInit(event);
    }
}
