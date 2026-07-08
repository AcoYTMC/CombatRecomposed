package net.acoyt.recomposed.mixin.client;

import net.acoyt.recomposed.impl.event.client.CoyoteBiteEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author AcoYT
 */
@Mixin(value = Minecraft.class, priority = 1001)
public abstract class MinecraftMixin {
    @Shadow @Nullable public MultiPlayerGameMode gameMode;
    @Shadow @Nullable public LocalPlayer player;

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void recomposed$coyoteBite(CallbackInfoReturnable<Boolean> cir) {
        if (CoyoteBiteEvent.target != null) {
            gameMode.attack(player, CoyoteBiteEvent.target);
            player.swing(InteractionHand.MAIN_HAND);
            cir.setReturnValue(true);
        }
    }
}
