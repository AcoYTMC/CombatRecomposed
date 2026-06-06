package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @WrapMethod(method = "isAcceptableItem")
    private boolean recomposed$unenchantableMace(ItemStack stack, Operation<Boolean> original) {
        return original.call(stack) && !stack.isOf(Items.MACE);
    }
}
