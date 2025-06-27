package net.foxy.xaerotrainmap.mixin;

import com.simibubi.create.content.trains.station.StationScreen;
import net.foxy.xaerotrainmap.TrainMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StationScreen.class)
public class StationScreenMixin {

    @Inject(
            method = "mapModsPresent",
            at = @At("HEAD"),
            cancellable = true
    )
    public void renderTrain(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
