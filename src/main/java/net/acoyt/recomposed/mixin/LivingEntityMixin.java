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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapOperation(
            method = "computeFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;getAttributeValue(Lnet/minecraft/registry/entry/RegistryEntry;)D",
                    ordinal = 0
            )
    )
    private double recomposed$reduceFallDamage(LivingEntity instance, RegistryEntry<EntityAttribute> attribute, Operation<Double> original) {
        double value = original.call(instance, attribute);
        WindChimeComponent component = WindChimeComponent.KEY.getNullable(this);
        if (component != null && component.getRemainingJumps() > 0 && instance instanceof PlayerEntity player && WindChimeUsableEvent.EVENT.invoker().canUse(player, player.getWorld())) {
            return value + component.getRemainingJumps();
        }

        return value;
    }

    @WrapOperation(
            method = "computeFallDamage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/MathHelper;ceil(D)I"
            )
    )
    private int recomposed$dontPlayFallSound(double value, Operation<Integer> original, float fallDistance) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (!(living instanceof PlayerEntity player)) return original.call(value);
        return fallDistance > 1.0F
                && !WindChimeItem.getWorn(living).isEmpty()
                && WindChimeUsableEvent.EVENT.invoker().canUse(player, player.getWorld())
                    ? 0 : original.call(value);
    }

    @WrapOperation(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
            )
    )
    private void recomposed$setCombatTimer(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (living instanceof PlayerEntity player && source.getAttacker() instanceof PlayerEntity attacker && !player.getWorld().isClient && CRConfig.combatTimer > 0) {
            CombatTimerComponent.KEY.get(player).setRemaining(CRConfig.combatTimer * 20); // 20s
            CombatTimerComponent.KEY.get(attacker).setRemaining(CRConfig.combatTimer * 20); // 20s
        }

        if (source.getSource() instanceof TntMinecartEntity) {
            amount = MathHelper.clamp(amount, 0.0F, CRConfig.minecartDamageCap);
        }

        original.call(instance, source, amount);
    }

    @WrapOperation(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;canWalkOnFluid(Lnet/minecraft/fluid/FluidState;)Z"
            )
    )
    private boolean recomposed$walkOnWaterHehe(LivingEntity instance, FluidState state, Operation<Boolean> original) {
        if (!LifeVestItem.getWorn(instance).isEmpty() && state.isIn(FluidTags.WATER)) {
            return true;
        }

        return original.call(instance, state);
    }

    @WrapMethod(method = "applyFluidMovingSpeed")
    private Vec3d recomposed$noWaterSlowdown(double gravity, boolean falling, Vec3d motion, Operation<Vec3d> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (!LifeVestItem.getWorn(living).isEmpty()) {
            return motion;
        }

        return original.call(gravity, falling, motion);
    }
}
