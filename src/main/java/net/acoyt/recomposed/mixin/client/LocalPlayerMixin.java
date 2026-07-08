package net.acoyt.recomposed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }

    @WrapOperation(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z",
                    ordinal = 0
            )
    )
    private boolean recomposed$canSprint(LocalPlayer instance, Operation<Boolean> original) {
        return original.call(instance) && !LifeVestItem.getWorn(instance).isEmpty();
    }

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z",
                    ordinal = 1
            )
    )
    private boolean recomposed$dontStopSprinting(boolean original) {
        return original && !LifeVestItem.getWorn(this).isEmpty();
    }
}
