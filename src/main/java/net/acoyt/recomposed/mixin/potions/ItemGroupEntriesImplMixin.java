package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(ItemGroup.EntriesImpl.class)
public abstract class ItemGroupEntriesImplMixin {
    @WrapMethod(method = "add")
    private void recomposed$dontAddIfEmpty(ItemStack stack, ItemGroup.StackVisibility visibility, Operation<Void> original) {
        if (stack.isEmpty()) return;
        original.call(stack, visibility);
    }
}
