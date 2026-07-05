package net.acoyt.recomposed.impl.event;

import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.cca.entity.CombatTimerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

/**
 * @author AcoYT
 */
public class CRCombatTimerEvent implements WindChimeUsableEvent {
    public boolean canUse(PlayerEntity player, World world) {
        //player.sendMessage(Text.translatable("tooltip.recomposed.in_combat"), true);
        return CombatTimerComponent.KEY.get(player).getRemaining() <= 0;
    }
}
