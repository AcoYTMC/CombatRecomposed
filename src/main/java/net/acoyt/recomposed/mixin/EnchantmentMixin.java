package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @WrapMethod(method = "canEnchant")
    private boolean recomposed$unenchantableMace(ItemStack stack, Operation<Boolean> original) {
        return original.call(stack) && !stack.is(Items.MACE);
    }
}
