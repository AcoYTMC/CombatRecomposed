package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.compat.CRConfig;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.minecraft.core.Holder;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @WrapOperation(
            method = "calculateFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getAttributeValue(Lnet/minecraft/core/Holder;)D",
                    ordinal = 0
            )
    )
    private double recomposed$reduceFallDamage(LivingEntity instance, Holder<Attribute> attribute, Operation<Double> original) {
        double value = original.call(instance, attribute);
        WindChimeComponent component = WindChimeComponent.KEY.getNullable(this);
        if (component != null && component.getJumpsLeft() > 0 && instance instanceof Player player && WindChimeUsableEvent.EVENT.invoker().canUse(player, player.level())) {
            return value + component.getJumpsLeft();
        }

        return value;
    }

    @WrapOperation(
            method = "calculateFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;ceil(D)I"
            )
    )
    private int recomposed$dontPlayFallSound(double value, Operation<Integer> original, float fallDistance) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (!(living instanceof Player player)) return original.call(value);
        return fallDistance > 1.0F
                && !WindChimeItem.getWorn(living).isEmpty()
                && WindChimeUsableEvent.EVENT.invoker().canUse(player, player.level())
                    ? 0 : original.call(value);
    }

    @WrapOperation(
            method = "hurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"
            )
    )
    private void recomposed$setCombatTimer(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (living instanceof Player player && source.getEntity() instanceof Player attacker && !player.level().isClientSide && CRConfig.combatTimer > 0) {
            CombatTimerComponent.KEY.get(player).setRemaining(CRConfig.combatTimer * 20); // 20s
            CombatTimerComponent.KEY.get(attacker).setRemaining(CRConfig.combatTimer * 20); // 20s
        }

        if (source.getDirectEntity() instanceof MinecartTNT) {
            amount = Mth.clamp(amount, 0.0F, CRConfig.minecartDamageCap);
        }

        original.call(instance, source, amount);
    }

    @WrapOperation(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;canStandOnFluid(Lnet/minecraft/world/level/material/FluidState;)Z"
            )
    )
    private boolean recomposed$walkOnWaterHehe(LivingEntity instance, FluidState state, Operation<Boolean> original) {
        if (!LifeVestItem.getWorn(instance).isEmpty() && state.is(FluidTags.WATER)) {
            return true;
        }

        return original.call(instance, state);
    }

    @WrapMethod(method = "getFluidFallingAdjustedMovement")
    private Vec3 recomposed$noWaterSlowdown(double gravity, boolean falling, Vec3 motion, Operation<Vec3> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (!LifeVestItem.getWorn(living).isEmpty()) {
            return motion;
        }

        return original.call(gravity, falling, motion);
    }
}
