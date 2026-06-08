package net.acoyt.recomposed.impl.index;

import net.acoyt.recomposed.impl.networking.c2s.AirJumpPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * @author AcoYT
 */
public interface CRNetworking {
    static void registerTypes() {
        PayloadTypeRegistry.playC2S().register(AirJumpPayload.ID, AirJumpPayload.CODEC);
    }

    static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(AirJumpPayload.ID, new AirJumpPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void registerS2CPackets() {
        //
    }
}
