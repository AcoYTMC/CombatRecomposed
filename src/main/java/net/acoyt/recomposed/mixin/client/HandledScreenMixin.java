package net.acoyt.recomposed.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.recomposed.impl.event.client.ChimeSwingEvent;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen {
    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @WrapOperation(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawItem(Lnet/minecraft/client/gui/DrawContext;" +
                            "Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
                    ordinal = 0
            )
    )
    private void recomposed$drawCursorStack(HandledScreen<T> instance, DrawContext context, ItemStack stack, int x, int y, String amountText, Operation<Void> original) {
        if (stack.isOf(CRItems.WIND_CHIME)) {
            ChimeSwingEvent.render(instance, context, stack, x, y, amountText);
        } else {
            original.call(instance, context, stack, x, y, amountText);
        }
    }
}
