package net.foxy.xaerotrainmap;

import net.neoforged.api.distmarker.Dist;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = XaeroTrainMap.MODID, dist = Dist.CLIENT)
public class XaeroTrainMap {
    public static final String MODID = "xaerotrainmap";
    private static final Logger LOGGER = LogUtils.getLogger();

    public XaeroTrainMap(IEventBus modEventBus, ModContainer modContainer) {}
}
