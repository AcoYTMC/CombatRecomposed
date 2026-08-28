package net.acoyt.recomposed.mixin.enchants.disable;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(ItemEnchantments.Mutable.class)
public abstract class ItemEnchantmentsMutableMixin {
    @WrapMethod(method = "set")
    private void recomposed$removedIfDisabled(Holder<Enchantment> holder, int i, Operation<Void> original) {
        if (CRUtil.isDisabled(holder)) return;
        original.call(holder, i);
    }

    @WrapMethod(method = "upgrade")
    private void recomposed$dontAddIfDisabled(Holder<Enchantment> holder, int i, Operation<Void> original) {
        if (CRUtil.isDisabled(holder)) return;
        original.call(holder, i);
    }
}
