package net.acoyt.recomposed.impl.networking.c2s;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import net.acoyt.recomposed.impl.index.CRSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundCategory;

/**
 * @author AcoYT
 */
public record AirJumpPayload() implements CustomPayload {
    public static final Id<AirJumpPayload> ID = new Id<>(Recomposed.id("air_jump"));

    public static final PacketCodec<RegistryByteBuf, AirJumpPayload> CODEC = PacketCodec.unit(new AirJumpPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<AirJumpPayload> {
        public void receive(AirJumpPayload payload, ServerPlayNetworking.Context context) {
            WindChimeComponent.KEY.get(context.player()).performDoubleJump();
            context.player().getWorld().playSound(null, context.player().getBlockPos(), CRSounds.JUMP, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }
}
