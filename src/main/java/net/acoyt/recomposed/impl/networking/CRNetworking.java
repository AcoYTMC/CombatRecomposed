package net.acoyt.recomposed.impl.networking;

import net.acoyt.recomposed.impl.networking.c2s.AirJumpPayload;
import net.acoyt.recomposed.impl.networking.c2s.AscendWaterPayload;
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
        PayloadTypeRegistry.playC2S().register(AscendWaterPayload.ID, AscendWaterPayload.CODEC);
    }

    static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(AirJumpPayload.ID, new AirJumpPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(AscendWaterPayload.ID, new AscendWaterPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void registerS2CPackets() {
        //
    }
}
