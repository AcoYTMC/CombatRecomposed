package net.acoyt.recomposed.impl.event.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;

/**
 * @author AcoYT
 */
public class CoyoteBiteEvent implements ClientTickEvents.EndWorldTick {
    private static final Minecraft client = Minecraft.getInstance();

    public static Entity target = null;
    public static int ticks = 0;

    public void onEndTick(ClientLevel world) {
        if (client.crosshairPickEntity != null) {
            target = client.crosshairPickEntity;
            ticks = 2;
        }

        if (ticks > 0) ticks--;

        if (ticks == 0 || target == null || target.isRemoved() || !target.isAlive()) {
            target = null;
            ticks = 0;
        }
    }
}
