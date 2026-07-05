package net.acoyt.recomposed.impl.event.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

/**
 * @author AcoYT
 */
public class CoyoteBiteEvent implements ClientTickEvents.EndWorldTick {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static Entity target = null;
    public static int ticks = 0;

    public void onEndTick(ClientWorld world) {
        if (client.targetedEntity != null) {
            target = client.targetedEntity;
            ticks = 2;
        }

        if (ticks > 0) ticks--;

        if (ticks == 0 || target == null || target.isRemoved() || !target.isAlive()) {
            target = null;
            ticks = 0;
        }
    }
}
