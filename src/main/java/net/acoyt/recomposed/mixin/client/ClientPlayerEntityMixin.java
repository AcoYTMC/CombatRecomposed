package net.acoyt.recomposed.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @WrapOperation(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isTouchingWater()Z",
                    ordinal = 0
            )
    )
    private boolean recomposed$canSprint(ClientPlayerEntity instance, Operation<Boolean> original) {
        return original.call(instance) && !LifeVestItem.getWorn(instance).isEmpty();
    }

    @ModifyExpressionValue(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isTouchingWater()Z",
                    ordinal = 1
            )
    )
    private boolean recomposed$dontStopSprinting(boolean original) {
        return original && !LifeVestItem.getWorn(this).isEmpty();
    }
}
