package net.acoyt.recomposed.mixin.client;

import net.acoyt.acornlib.api.util.ItemUtils;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 */
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart head;

    @Inject(method = "positionRightArm", at = @At("TAIL"))
    private void recomposed$rightArmWindChime(T entity, CallbackInfo ci) {
        if (ItemUtils.getHeldStacks(entity).stream().anyMatch(stack -> stack.isOf(CRItems.WIND_CHIME)) && entity.getMainArm() == Arm.RIGHT) {
            positionArmForWindChime(this.rightArm, this.leftArm, this.head, entity.getMainHandStack().isOf(CRItems.WIND_CHIME));
        }
    }

    @Inject(method = "positionLeftArm", at = @At("TAIL"))
    private void recomposed$leftArmWindChime(T entity, CallbackInfo ci) {
        if (ItemUtils.getHeldStacks(entity).stream().anyMatch(stack -> stack.isOf(CRItems.WIND_CHIME)) && entity.getMainArm() != Arm.RIGHT) {
            positionArmForWindChime(this.rightArm, this.leftArm, this.head, entity.getOffHandStack().isOf(CRItems.WIND_CHIME));
        }
    }

    @Unique
    private static void positionArmForWindChime(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        modelPart.pitch = Math.clamp(head.pitch + 80.0F, 79.5F, 80.75F);
    }
}
