package net.acoyt.recomposed.impl.networking.c2s;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.cca.entity.AscendWaterComponent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * @author AcoYT
 */
public record AscendWaterPayload(boolean shouldAscend) implements CustomPacketPayload {
    public static final Type<AscendWaterPayload> ID = new Type<>(Recomposed.id("ascend_water"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AscendWaterPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, AscendWaterPayload::shouldAscend,
            AscendWaterPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
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