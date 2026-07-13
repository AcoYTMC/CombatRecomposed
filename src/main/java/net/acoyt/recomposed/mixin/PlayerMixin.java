package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getFlyingSpeed", at = @At("RETURN"))
    private float recomposed$windChimeBunnyHopping(float original) {
        if (WindChimeComponent.KEY.get(this).getPossibleJumps() > WindChimeComponent.KEY.get(this).getJumpsUsed()) {
            return original * 1.65F;
        }

        return original;
    }

//    @WrapOperation(
//            method = "updateSwimming",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/world/entity/LivingEntity;updateSwimming()V"
//            )
//    )
//    private void recomposed$noSwimming(Player instance, Operation<Void> original) {
//        if (!LifeVestItem.getWorn(instance).isEmpty()) {
//            instance.setSwimming(false);
//            return;
//        }
//
//        original.call(instance);
//    }
}
