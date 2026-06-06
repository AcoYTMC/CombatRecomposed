package net.acoyt.recomposed.mixin.enchants;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 */
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Shadow @Final private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @ModifyExpressionValue(
            method = "canTakeOutput",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/Property;get()I"
            )
    )
    private int recomposed$freeEnchanting(int original) {
        return 0;
    }

    @Inject(method = "updateResult", at = @At("TAIL"))
    private void recomposed$freeEnchanting(CallbackInfo ci) {
        levelCost.set(0);
    }
}
