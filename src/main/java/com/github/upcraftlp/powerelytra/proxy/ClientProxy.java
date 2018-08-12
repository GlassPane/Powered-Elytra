package com.github.upcraftlp.powerelytra.proxy;

import com.github.upcraftlp.glasspane.api.client.SkinnableMapping;
import com.github.upcraftlp.glasspane.api.proxy.IProxy;
import com.github.upcraftlp.powerelytra.item.ItemPowerElytra;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        SkinnableMapping.addMapping(ItemPowerElytra.SKIN_MANFORGED, 1);
        SkinnableMapping.addMapping(ItemPowerElytra.SKIN_TEAM_RAPTURE, 2);
        SkinnableMapping.addMapping(ItemPowerElytra.SKIN_LADYSNAKE, 3);
    }
}
