package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.enchantment.effect.entity.IgniteEnchantmentEffect;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(IgniteEnchantmentEffect.class)
public abstract class IgniteEnchantmentEffectMixin {
    @WrapOperation(
            method = "apply",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setOnFireFor(F)V"
            )
    )
    private void recomposed$burnForLessTime(Entity instance, float seconds, Operation<Void> original) {
        original.call(instance, seconds / 2);
    }
}
