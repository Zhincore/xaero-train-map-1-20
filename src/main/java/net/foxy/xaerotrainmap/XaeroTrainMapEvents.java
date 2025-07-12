package net.foxy.xaerotrainmap;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.client.event.InputEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = XaeroTrainMap.MODID)
public class XaeroTrainMapEvents {

	@SubscribeEvent
	public static void tick(ClientTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null)
			return;

		TrainMap.tick();
	}

	@SubscribeEvent
	public static void mouseClick(InputEvent.MouseButton.Pre event) {
		if (event.getAction() != InputConstants.PRESS)
			return;

		TrainMap.mouseClick(event);
	}

}
