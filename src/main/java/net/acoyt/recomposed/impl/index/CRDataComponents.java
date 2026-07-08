package net.acoyt.recomposed.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

/**
 * @author AcoYT
 */
public interface CRDataComponents {
    ComponentTypeRegistrant COMPONENTS = new ComponentTypeRegistrant(Recomposed.MOD_ID);

    DataComponentType<Boolean> IMMORTAL = COMPONENTS.register("immortal", Codec.BOOL, ByteBufCodecs.BOOL);

    static void init() {}
}
