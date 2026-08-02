package net.acoyt.recomposed.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.component.ChargesComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

/**
 * @author AcoYT
 */
public interface CRDataComponents {
    DataComponentTypeRegistrant COMPONENTS = new DataComponentTypeRegistrant(Recomposed.MOD_ID);

    DataComponentType<Boolean> IMMORTAL = COMPONENTS.register("immortal", Codec.BOOL, ByteBufCodecs.BOOL);
    DataComponentType<ChargesComponent> CHARGES = COMPONENTS.register("charges", ChargesComponent.CODEC, ChargesComponent.STREAM_CODEC);

    static void init() {}
}
