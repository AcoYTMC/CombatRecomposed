package net.acoyt.recomposed.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;

/**
 * @author AcoYT
 */
public interface CRDataComponents {
    ComponentTypeRegistrant COMPONENTS = new ComponentTypeRegistrant(Recomposed.MOD_ID);

    ComponentType<Boolean> IMMORTAL = COMPONENTS.register("immortal", Codec.BOOL, PacketCodecs.BOOL);

    static void init() {}
}
