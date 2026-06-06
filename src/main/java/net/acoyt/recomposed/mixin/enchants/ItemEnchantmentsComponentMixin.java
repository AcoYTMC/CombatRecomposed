package net.acoyt.recomposed.mixin.enchants;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(ItemEnchantmentsComponent.class)
public abstract class ItemEnchantmentsComponentMixin {
    @WrapOperation(
            method = "appendTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/Enchantment;getName(Lnet/minecraft/registry/entry/RegistryEntry;I)Lnet/minecraft/text/Text;"
            )
    )
    private Text recomposed$setTemporaryValues(RegistryEntry<Enchantment> enchantment, int level, Operation<Text> original, @Local(argsOnly = true) TooltipType type) {
        if (type.isAdvanced()) level = CRUtil.getFunctionalLevel(enchantment);
        return original.call(enchantment, level);
    }
}
