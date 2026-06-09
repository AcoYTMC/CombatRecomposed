package net.acoyt.recomposed.impl.event;

import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * @author AcoYT
 */
public class CombatTimerUsableEvent implements WindChimeUsableEvent {
    public boolean canUse(PlayerEntity player, World world) {
        return CombatTimerComponent.KEY.get(player).getRemaining() <= 0;
    }
}
