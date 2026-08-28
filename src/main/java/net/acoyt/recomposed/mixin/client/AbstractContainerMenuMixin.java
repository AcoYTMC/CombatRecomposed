package net.acoyt.recomposed.mixin.client;

import net.acoyt.recomposed.impl.event.client.ChimeSwingEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 */
@Environment(EnvType.CLIENT)
@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {
    @Shadow @Final public NonNullList<Slot> slots;

    @Inject(method = "clicked", at = @At("TAIL"))
    private void recomposed$resetSwing(int i, int j, ClickType clickType, Player player, CallbackInfo ci) {
        if (i >= 0) {
            Slot slot = this.slots.get(i);
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) {
                ChimeSwingEvent.swing = 0.0F;
            }
        }
    }
}
