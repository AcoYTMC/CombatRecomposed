package net.acoyt.recomposed.impl.event.client;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.acoyt.recomposed.impl.index.CRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * @author AcoYT
 */
public class ItemTooltipsEvent implements BetterItemTooltipEvent {
    public void getTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag tooltipFlag, List<Component> lines) {
        if (stack.is(CRItems.SAPPHIRE_CRYSTAL)) {
            if (Screen.hasShiftDown()) {
                lines.add(Component.translatable("item.recomposed.sapphire_crystal.tooltip").withStyle(ChatFormatting.GRAY));
            } else {
                lines.add(Component.translatable("tooltip.recomposed.hold_shift", Component.literal("[Shift]").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}
