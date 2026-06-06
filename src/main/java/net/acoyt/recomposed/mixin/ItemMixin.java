package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(Item.class)
public abstract class ItemMixin {
    @WrapOperation(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/component/type/FoodComponent;canAlwaysEat()Z"
            )
    )
    private boolean recomposed$noChugging(FoodComponent instance, Operation<Boolean> original,
                                          @Local(argsOnly = true) PlayerEntity user, @Local(argsOnly = true) Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        return original.call(instance) && !stack.isOf(Items.ENCHANTED_GOLDEN_APPLE);
    }
}
