package net.acoyt.recomposed.impl.networking.c2s;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import net.acoyt.recomposed.impl.index.CRSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;

/**
 * @author AcoYT
 */
public record AirJumpPayload() implements CustomPacketPayload {
    public static final Type<AirJumpPayload> ID = new Type<>(Recomposed.id("air_jump"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AirJumpPayload> CODEC = StreamCodec.unit(new AirJumpPayload());

    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<AirJumpPayload> {
        public void receive(AirJumpPayload payload, ServerPlayNetworking.Context context) {
            WindChimeComponent.KEY.get(context.player()).performDoubleJump();
            context.player().level().playSound(null, context.player().blockPosition(), CRSounds.JUMP, SoundSource.PLAYERS, 1.0F, (float) (1.0F + context.player().getRandom().nextGaussian() / 10.0F));
        }
    }
}
