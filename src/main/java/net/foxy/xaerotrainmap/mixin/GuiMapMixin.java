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
    @Shadow private double scale;

    @Shadow private double cameraZ;

    @Shadow private double cameraX;

    @Shadow private int mouseBlockPosX;

    @Shadow private int mouseBlockPosZ;

    protected GuiMapMixin(Component title) {
        super(title);
    }

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    public void renderTrain(GuiGraphics guiGraphics, int scaledMouseX,
                            int scaledMouseY, float partialTicks, CallbackInfo ci) {
        TrainMap.onRender(this, guiGraphics, scaledMouseX, scaledMouseY,
                partialTicks, this.scale, this.cameraX, this.cameraZ, mouseBlockPosX, mouseBlockPosZ);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
