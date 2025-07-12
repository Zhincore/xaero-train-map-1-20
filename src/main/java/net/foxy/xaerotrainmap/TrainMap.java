package net.foxy.xaerotrainmap;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.simibubi.create.CreateClient;
import com.simibubi.create.compat.trainmap.TrainMapManager;
import com.simibubi.create.compat.trainmap.TrainMapSyncClient;
import com.simibubi.create.foundation.gui.RemovedGuiUtils;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.Mth;
import net.minecraftforge.client.event.InputEvent.MouseButton.Pre;
import xaero.map.gui.GuiMap;

import java.util.List;

public class TrainMap {

	private static boolean requesting;

	public TrainMap() {
	}

	public static void tick() {
		if (!AllConfigs.client().showTrainMapOverlay.get() || !(Minecraft.getInstance().screen instanceof GuiMap)) {
			if (requesting)
				TrainMapSyncClient.stopRequesting();
			requesting = false;
			return;
		}
		TrainMapManager.tick();
		requesting = true;
		TrainMapSyncClient.requestData();
	}

	public static void mouseClick(Pre event) {
		Minecraft mc = Minecraft.getInstance();
		if (!(mc.screen instanceof GuiMap screen))
			return;

		Window window = mc.getWindow();
		double mX = mc.mouseHandler.xpos() * window.getGuiScaledWidth() / window.getScreenWidth();
		double mY = mc.mouseHandler.ypos() * window.getGuiScaledHeight() / window.getScreenHeight();

		if (TrainMapManager.handleToggleWidgetClick(Mth.floor(mX), Mth.floor(mY), 3, 30))
			event.setCanceled(true);
	}

	// GuiGraphics graphics, Fullscreen screen, double x, double z, int mX, int mY, float pt
	public static void onRender(Screen screen, GuiGraphics graphics, int mX,
								int mY, float pt, double mapScale, double x, double z, int mPosX, int mPosZ) {

		if (!AllConfigs.client().showTrainMapOverlay.get()) {
			renderToggleWidgetAndTooltip(graphics, screen, mX, mY);
			return;
		}

		Minecraft mc = Minecraft.getInstance();
		Window window = mc.getWindow();

		double guiScale = (double) window.getScreenWidth() / window.getGuiScaledWidth();
		double scale = mapScale / guiScale;

		PoseStack pose = graphics.pose();
		pose.pushPose();

		pose.translate(screen.width / 2.0f, screen.height / 2.0f, 0);
		pose.scale((float) scale, (float) scale, 1);
		pose.translate(-x, -z, 0);

		Rect2i bounds =
			new Rect2i(Mth.floor(-screen.width / 2.0f / scale + x), Mth.floor(-screen.height / 2.0f / scale + z),
				Mth.floor(screen.width / scale), Mth.floor(screen.height / scale));
		//LogUtils.getLogger().warn(bounds.getX() + " " + bounds.getY() + " " + bounds.getWidth() + " " + bounds.getHeight());
		//LogUtils.getLogger().warn(String.valueOf(CreateClient.RAILWAYS.trains.values()));
		List<FormattedText> tooltip =
			TrainMapManager.renderAndPick(graphics, Mth.floor(mPosX), Mth.floor(mPosZ), pt, false, bounds);

		pose.popPose();

		if (!renderToggleWidgetAndTooltip(graphics, screen, mX, mY) && tooltip != null)
			RemovedGuiUtils.drawHoveringText(graphics, tooltip, mX, mY, screen.width, screen.height, 256, mc.font);
	}

	private static boolean renderToggleWidgetAndTooltip(GuiGraphics graphics, Screen screen, int mouseX, int mouseY) {
		TrainMapManager.renderToggleWidget(graphics, 3, 30);
		if (!TrainMapManager.isToggleWidgetHovered(mouseX, mouseY, 3, 30))
			return false;

		RemovedGuiUtils.drawHoveringText(graphics, List.of(Component.translatable("create.train_map.toggle")
		), mouseX, mouseY + 20, screen.width, screen.height, 256, Minecraft.getInstance().font);
		return true;
	}

}
