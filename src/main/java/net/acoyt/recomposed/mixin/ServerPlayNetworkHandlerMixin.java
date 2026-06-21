package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@ModifyExpressionValue(
            method = "onPlayerMove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isInTeleportationState()Z"
            )
    )
	public boolean recomposed$noVelocityChecks(boolean value) {
		return true;
	}
}
