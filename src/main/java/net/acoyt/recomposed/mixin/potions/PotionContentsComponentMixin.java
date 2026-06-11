package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(PotionContentsComponent.class)
public abstract class PotionContentsComponentMixin {
    @WrapMethod(method = "createStack")
    private static ItemStack recomposed$returnEmptyIfDisabled(Item item, RegistryEntry<Potion> potion, Operation<ItemStack> original) {
        if (CRUtil.isPotionDisabled(potion)) return ItemStack.EMPTY;
        return original.call(item, potion);
    }
}
