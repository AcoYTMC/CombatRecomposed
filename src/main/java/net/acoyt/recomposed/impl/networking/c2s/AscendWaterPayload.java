package net.acoyt.recomposed.impl.networking.c2s;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.cca.entity.AscendWaterComponent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

/**
 * @author AcoYT
 */
public record AscendWaterPayload(boolean shouldAscend) implements CustomPayload {
    public static final Id<AscendWaterPayload> ID = new Id<>(Recomposed.id("ascend_water"));

    public static final PacketCodec<RegistryByteBuf, AscendWaterPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, AscendWaterPayload::shouldAscend,
            AscendWaterPayload::new
    );

    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<AscendWaterPayload> {
        public void receive(AscendWaterPayload payload, ServerPlayNetworking.Context context) {
            AscendWaterComponent component = AscendWaterComponent.KEY.get(context.player());
            if (component.hasAscend() && component.canUse(true)) {
                component.setShouldAscend(payload.shouldAscend());
            }
        }
    }
}