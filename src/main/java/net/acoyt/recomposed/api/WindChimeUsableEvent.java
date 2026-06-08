package net.acoyt.recomposed.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author AcoYT
 */
public interface WindChimeUsableEvent {
    Event<WindChimeUsableEvent> EVENT = EventFactory.createArrayBacked(WindChimeUsableEvent.class, events -> (player, world) -> {
        List<WindChimeUsableEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(WindChimeUsableEvent::getPriority));
        for (WindChimeUsableEvent event : sortedEvents) {
            if (!event.canUse(player, world)) {
                return false;
            }
        }

        return true;
    });

    default int getPriority() {
        return 1000;
    }

    boolean canUse(PlayerEntity player, World world);
}
