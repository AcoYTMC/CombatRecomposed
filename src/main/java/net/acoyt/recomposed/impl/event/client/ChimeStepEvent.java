package net.acoyt.recomposed.impl.event.client;

import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.acoyt.recomposed.mixin.access.LivingEntityAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

/**
 * @author AcoYT
 */
public class ChimeStepEvent implements ClientTickEvents.EndTick {
    public int fallingTicks = 0;
    private final double[] yValues = {0, 0};

    public void onEndTick(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        int ticks = 10;

        if (player.isOnGround()) {
            yValues[0] = player.getY();
            yValues[1] = player.getY();

            fallingTicks = 0;
        } else {
            yValues[1] = player.getY();
            fallingTicks++;

            if (fallingTicks < ticks && ((LivingEntityAccessor)player).recomposed$isJumping() && yValues[1] < yValues[0] && !WindChimeItem.getWorn(player).isEmpty()) {
                player.jump();
                fallingTicks = ticks;
            }
        }
    }
}
