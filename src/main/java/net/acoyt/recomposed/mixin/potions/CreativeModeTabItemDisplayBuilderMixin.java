package net.acoyt.recomposed.mixin.potions;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(CreativeModeTab.ItemDisplayBuilder.class)
public abstract class CreativeModeTabItemDisplayBuilderMixin {
    @WrapMethod(method = "accept")
    private void recomposed$dontAddIfEmpty(ItemStack stack, CreativeModeTab.TabVisibility visibility, Operation<Void> original) {
        if (stack.isEmpty()) return;
        original.call(stack, visibility);
    }
}
