package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
    private float recomposed$windChimeBunnyHopping(float original) {
        if (WindChimeComponent.KEY.get(this).getPossibleJumps() > WindChimeComponent.KEY.get(this).getRemainingJumps()) {
            return original * 1.65F;
        }

        return original;
    }

    @WrapOperation(
            method = "updateSwimming",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;updateSwimming()V"
            )
    )
    private void recomposed$noSwimming(PlayerEntity instance, Operation<Void> original) {
        if (!LifeVestItem.getWorn(instance).isEmpty()) {
            instance.setSwimming(false);
            return;
        }

        original.call(instance);
    }
}
