package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class CombatTimerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<CombatTimerComponent> KEY = ComponentRegistry.getOrCreate(Recomposed.id("combat_timer"), CombatTimerComponent.class);
    private final Player player;

    private int remaining;

    public CombatTimerComponent(Player player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (remaining > 0) {
            remaining--;
            if (remaining == 0) {
                sync();
            }
        }
    }

    public void readFromNbt(CompoundTag nbt, HolderLookup.Provider registries) {
        remaining = nbt.getInt("Remaining");
    }

    public void writeToNbt(CompoundTag nbt, HolderLookup.Provider registries) {
        nbt.putInt("Remaining", remaining);
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
        this.sync();
    }
}
