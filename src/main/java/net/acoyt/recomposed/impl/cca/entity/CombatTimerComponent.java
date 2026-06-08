package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class CombatTimerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<CombatTimerComponent> KEY = ComponentRegistry.getOrCreate(Recomposed.id("combat_timer"), CombatTimerComponent.class);
    private final PlayerEntity player;

    private int remaining;

    public CombatTimerComponent(PlayerEntity player) {
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

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        remaining = nbt.getInt("Remaining");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
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
