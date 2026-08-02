package net.acoyt.recomposed.impl;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.acoyt.recomposed.impl.event.client.ChimeStepEvent;
import net.acoyt.recomposed.impl.event.client.CoyoteBiteEvent;
import net.acoyt.recomposed.impl.event.client.ItemTooltipsEvent;
import net.acoyt.recomposed.impl.networking.CRNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

/**
 * @author AcoYT
 */
@Environment(EnvType.CLIENT)
public class RecomposedClient implements ClientModInitializer {
    public void onInitializeClient() {
        CRNetworking.registerS2CPackets();

        ClientTickEvents.END_CLIENT_TICK.register(new ChimeStepEvent());
        ClientTickEvents.END_WORLD_TICK.register(new CoyoteBiteEvent());
        BetterItemTooltipEvent.EVENT.register(new ItemTooltipsEvent());
    }
}
