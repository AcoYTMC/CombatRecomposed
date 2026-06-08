package net.acoyt.recomposed.mixin.access;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author AcoYT
 */
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("jumping")
    boolean recomposed$isJumping();

    @Accessor("jumpingCooldown")
    int recomposed$getJumpingCooldown();

    @Accessor("jumpingCooldown")
    void recomposed$setJumpingCooldown(int jumpingCooldown);
}
