package net.acoyt.recomposed.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author AcoYT
 */
public interface ItemMaxCountEvent {
    Event<ItemMaxCountEvent> EVENT = EventFactory.createArrayBacked(ItemMaxCountEvent.class, events -> (player, stack) -> {
        List<ItemMaxCountEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(ItemMaxCountEvent::getPriority));
        for (ItemMaxCountEvent event : sortedEvents) {
            Optional<Integer> level = event.getMaxCount(player, stack);
            if (level.isPresent()) {
                return level;
            }
        }

        return Optional.empty();
    });

    default int getPriority() {
        return 1000;
    }

    Optional<Integer> getMaxCount(@Nullable Player player, ItemStack stack);
}
