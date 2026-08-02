package net.acoyt.recomposed.impl.event;

import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * @author AcoYT
 */
public class CombatTimerEvent implements WindChimeUsableEvent {
    public boolean canUse(Player player, Level world) {
        //player.sendMessage(Text.translatable("tooltip.recomposed.in_combat"), true);
        return CombatTimerComponent.KEY.get(player).getRemaining() <= 0;
    }
}
