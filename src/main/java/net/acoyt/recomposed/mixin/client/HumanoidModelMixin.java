package net.acoyt.recomposed.mixin.client;

import net.acoyt.acornlib.api.util.ItemUtils;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
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
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart head;

    @Inject(method = "poseRightArm", at = @At("TAIL"))
    private void recomposed$rightArmWindChime(T entity, CallbackInfo ci) {
        if (ItemUtils.getHeldStacks(entity).stream().anyMatch(stack -> stack.is(CRItems.WIND_CHIME)) && entity.getMainArm() == HumanoidArm.RIGHT) {
            positionArmForWindChime(this.rightArm, this.leftArm, this.head, entity.getMainHandItem().is(CRItems.WIND_CHIME));
        }
    }

    @Inject(method = "poseLeftArm", at = @At("TAIL"))
    private void recomposed$leftArmWindChime(T entity, CallbackInfo ci) {
        if (ItemUtils.getHeldStacks(entity).stream().anyMatch(stack -> stack.is(CRItems.WIND_CHIME)) && entity.getMainArm() != HumanoidArm.RIGHT) {
            positionArmForWindChime(this.rightArm, this.leftArm, this.head, entity.getOffhandItem().is(CRItems.WIND_CHIME));
        }
    }

    @Unique
    private static void positionArmForWindChime(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        modelPart.xRot = Math.clamp(head.xRot + 80.0F, 79.5F, 80.75F);
    }
}
