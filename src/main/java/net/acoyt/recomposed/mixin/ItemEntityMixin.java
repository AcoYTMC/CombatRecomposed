package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.api.ItemMaxCountEvent;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

/**
 * @author AcoYT
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Unique private boolean logged = false;

    @Shadow public abstract ItemStack getStack();

    @WrapOperation(
            method = "onPlayerCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"
            )
    )
    private boolean recomposed$overrideCount(PlayerInventory instance, ItemStack stack, Operation<Boolean> original, PlayerEntity player) {
        int inv = CRUtil.getCountOnPlayer(player, stack.getItem());
        int entity = this.getStack().getCount();
        Optional<Integer> maxCount = ItemMaxCountEvent.EVENT.invoker().getMaxCount(player, stack);

        if (maxCount.isPresent()) {
            int max = maxCount.get();
            int sum = inv + entity;
            if (player.isCreative()) return original.call(instance, stack);
            if (max == 0) return false;
            if ((inv == 0 && entity <= max) || sum <= max) return original.call(instance, stack);

            if (entity > max) {
                if (!logged) Recomposed.LOGGER.info("1 [Inv: {}, Entity: {}, Max: {}]", inv, entity, max);
                while (entity > max) entity -= max;

                if (!logged) Recomposed.LOGGER.info("2 [Inv: {}, Entity: {}, Max: {}]", inv, entity, max);
                int dif = sum - entity;

                if (instance.insertStack(this.getStack().copyWithCount(dif))) {
                    this.getStack().decrement(dif);
                }

                if (!logged) {
                    Recomposed.LOGGER.info("3 [Inv: {}, Entity: {}, Max: {}, Dif: {}]", inv, entity, max, dif);
                    logged = true;
                }
            } else {
                while (sum > max) sum -= max;

                int dif = max - sum;

                this.getStack().decrement(dif);
                stack.increment(dif);
            }

            return false;
        }

        return original.call(instance, stack);
    }
}
