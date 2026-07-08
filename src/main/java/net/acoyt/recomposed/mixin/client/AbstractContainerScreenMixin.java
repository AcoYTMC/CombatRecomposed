package net.acoyt.recomposed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.event.client.ChimeSwingEvent;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen {
    protected AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderFloatingItem(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
                    ordinal = 0
            )
    )
    private void recomposed$drawCursorStack(AbstractContainerScreen<T> instance, GuiGraphics context, ItemStack stack, int x, int y, String amountText, Operation<Void> original) {
        if (stack.is(CRItems.WIND_CHIME)) {
            ChimeSwingEvent.render(instance, context, stack, x, y, amountText);
        } else {
            original.call(instance, context, stack, x, y, amountText);
        }
    }
}
