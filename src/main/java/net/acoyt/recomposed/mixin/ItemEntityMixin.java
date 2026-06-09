package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.api.ItemMaxCountEvent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow public abstract void setStack(ItemStack stack);

    @WrapOperation(
            method = "onPlayerCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"
            )
    )
    private boolean recomposed$overrideCount(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, PlayerEntity player) {
        int storedCount = 0;
        for (int i = 0; i < instance.size(); i++) {
            ItemStack itemStack = instance.getStack(i);
            if (ItemStack.areItemsEqual(itemStack, stack)) {
                storedCount += itemStack.getCount();
            }
        }

        Optional<Integer> maxCount = ItemMaxCountEvent.EVENT.invoker().getMaxCount(player, stack);
        if (maxCount.isPresent() && storedCount >= maxCount.get()) {
            if (storedCount == maxCount.get()) {
                return true;
            } else {
                ItemStack remainder = stack.split(storedCount - maxCount.get());
                stack.setCount(remainder.getCount());
                return false;
            }
        }

        return original.call(instance, stack);
    }
}
