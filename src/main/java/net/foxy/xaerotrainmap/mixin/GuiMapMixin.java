package net.foxy.xaerotrainmap.mixin;

import net.foxy.xaerotrainmap.TrainMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.gui.GuiMap;

@Mixin(GuiMap.class)
public class GuiMapMixin extends Screen {

  @Shadow(remap = false)
  private double scale;

  @Shadow(remap = false)
  private double cameraZ;

  @Shadow(remap = false)
  private double cameraX;

  @Shadow(remap = false)
  private int mouseBlockPosX;

  @Shadow(remap = false)
  private int mouseBlockPosZ;

  protected GuiMapMixin(Component title) {
    super(title);
  }

  @Inject(method = "m_88315_", at = @At("TAIL"), remap = false)
  public void renderTrain(
    GuiGraphics guiGraphics,
    int scaledMouseX,
    int scaledMouseY,
    float partialTicks,
    CallbackInfo ci
  ) {
    TrainMap.onRender(
      this,
      guiGraphics,
      scaledMouseX,
      scaledMouseY,
      partialTicks,
      this.scale,
      this.cameraX,
      this.cameraZ,
      mouseBlockPosX,
      mouseBlockPosZ
    );
  }

  @Override
  public boolean isPauseScreen() {
    return false;
  }
}
