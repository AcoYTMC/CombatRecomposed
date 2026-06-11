package net.acoyt.recomposed.impl.event;

import net.acoyt.recomposed.api.ItemMaxCountEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class CRItemMaxCountEvent implements ItemMaxCountEvent {
    public Optional<Integer> getMaxCount(PlayerEntity player, ItemStack stack) {
        return Optional.ofNullable(get(stack));
    }

    @Nullable
    public Integer get(ItemStack stack) {
        if (stack.isOf(Items.GOLDEN_APPLE)) return 64;
        if (stack.isOf(Items.COBWEB)) return 32;
        if (stack.isOf(Items.TOTEM_OF_UNDYING)) return 2;
        if (stack.isOf(Items.WIND_CHARGE)) return 128;

        return null;
    }
}
