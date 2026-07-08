package net.acoyt.recomposed.impl.event;

import net.acoyt.recomposed.api.ItemMaxCountEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class CRItemMaxCountEvent implements ItemMaxCountEvent {
    public Optional<Integer> getMaxCount(Player player, ItemStack stack) {
        return Optional.ofNullable(get(stack));
    }

    @Nullable
    public Integer get(ItemStack stack) {
        if (stack.is(Items.GOLDEN_APPLE)) return 64;
        if (stack.is(Items.COBWEB)) return 32;
        if (stack.is(Items.TOTEM_OF_UNDYING)) return 2;
        if (stack.is(Items.WIND_CHARGE)) return 128;

        return null;
    }
}
