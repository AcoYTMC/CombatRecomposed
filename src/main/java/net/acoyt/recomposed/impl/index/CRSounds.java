package net.acoyt.recomposed.impl.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.sounds.SoundEvent;

/**
 * @author AcoYT
 */
public interface CRSounds {
    SoundEventRegistrant SOUNDS = new SoundEventRegistrant(Recomposed.MOD_ID);

    SoundEvent JUMP = SOUNDS.register("entity.jump");

    static void init() {}
}
