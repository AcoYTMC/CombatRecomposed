package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(PotionContents.class)
public abstract class PotionContentsMixin {
    @WrapMethod(method = "createItemStack")
    private static ItemStack recomposed$returnEmptyIfDisabled(Item item, Holder<Potion> holder, Operation<ItemStack> original) {
        if (CRUtil.isPotionDisabled(holder)) return ItemStack.EMPTY;
        return original.call(item, holder);
    }
}
