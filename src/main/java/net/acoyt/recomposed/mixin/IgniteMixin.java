package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.effects.Ignite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(Ignite.class)
public abstract class IgniteMixin {
    @WrapOperation(
            method = "apply",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;igniteForSeconds(F)V"
            )
    )
    private void recomposed$burnForLessTime(Entity instance, float seconds, Operation<Void> original) {
        original.call(instance, seconds / 2);
    }
}
