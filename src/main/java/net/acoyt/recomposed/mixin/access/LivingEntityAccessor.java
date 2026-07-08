package net.acoyt.recomposed.mixin.access;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author AcoYT
 */
@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("jumping")
    boolean recomposed$isJumping();

    @Accessor("noJumpDelay")
    int recomposed$getJumpingCooldown();

    @Accessor("noJumpDelay")
    void recomposed$setJumpingCooldown(int jumpingCooldown);
}
