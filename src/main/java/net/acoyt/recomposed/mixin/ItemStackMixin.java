package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.index.tag.CRItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean isOf(Item item);

    @ModifyReturnValue(method = "getDamage", at = @At("RETURN"))
    private int recomposed$noDurability(int original) {
        ItemStack stack = (ItemStack)(Object)this;
        return !stack.isEmpty() && !stack.isIn(CRItemTags.HAS_DURABILITY) ? 0 : original;
    }

    @WrapMethod(method = "isEnchantable")
    private boolean recomposed$unenchantableMace(Operation<Boolean> original) {
        return original.call() && !this.isOf(Items.MACE);
    }
}
