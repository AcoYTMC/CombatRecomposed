package net.acoyt.recomposed.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.*;

/**
 * @author AcoYT
 */
public interface FunctionalLevelEvent {
    Event<FunctionalLevelEvent> EVENT = EventFactory.createArrayBacked(FunctionalLevelEvent.class, events -> enchantment -> {
        List<FunctionalLevelEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(FunctionalLevelEvent::getPriority));
        for (FunctionalLevelEvent event : sortedEvents) {
            Optional<Integer> level = event.getFunctionalLevel(enchantment);
            if (level.isPresent()) {
                return level;
            }
        }

        return Optional.empty();
    });

    default int getPriority() {
        return 1000;
    }

    Optional<Integer> getFunctionalLevel(RegistryEntry<Enchantment> enchantment);
}
