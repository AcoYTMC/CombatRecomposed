package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
                    target = "Lnet/minecraft/world/food/FoodProperties;canAlwaysEat()Z"
            )
    )
    private boolean recomposed$noChugging(FoodProperties instance, Operation<Boolean> original,
                                          @Local(argsOnly = true) Player user, @Local(argsOnly = true) InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        return original.call(instance) && !stack.is(Items.ENCHANTED_GOLDEN_APPLE);
    }
}
