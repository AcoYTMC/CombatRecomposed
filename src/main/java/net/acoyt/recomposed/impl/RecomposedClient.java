package net.acoyt.recomposed.impl;

import net.acoyt.recomposed.impl.event.client.ChimeStepEvent;
import net.acoyt.recomposed.impl.index.CRNetworking;
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
    }
}
