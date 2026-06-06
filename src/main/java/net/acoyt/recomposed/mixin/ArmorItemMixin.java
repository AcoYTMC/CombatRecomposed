package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin {
    @WrapOperation(
            method = "method_56689",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ArmorMaterial;knockbackResistance()F"
            )
    )
    private static float recomposed$noKnockbackResistance(ArmorMaterial instance, Operation<Float> original) {
        return instance.repairIngredient().get().test(Items.NETHERITE_INGOT.getDefaultStack()) ? 0.0F : original.call(instance);
    }
}
