package net.acoyt.recomposed.mixin.enchants.disable;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(ItemEnchantmentsComponent.Builder.class)
public abstract class ItemEnchantmentsComponentBuilderMixin {
    @WrapMethod(method = "set")
    private void recomposed$removedIfDisabled(RegistryEntry<Enchantment> enchantment, int level, Operation<Void> original) {
        if (CRUtil.isDisabled(enchantment)) return;
        original.call(enchantment, level);
    }

    @WrapMethod(method = "add")
    private void recomposed$dontAddIfDisabled(RegistryEntry<Enchantment> enchantment, int level, Operation<Void> original) {
        if (CRUtil.isDisabled(enchantment)) return;
        original.call(enchantment, level);
    }
}
