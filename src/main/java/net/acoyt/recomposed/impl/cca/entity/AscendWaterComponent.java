package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.acoyt.recomposed.impl.networking.c2s.AscendWaterPayload;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class AscendWaterComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<AscendWaterComponent> KEY = ComponentRegistry.getOrCreate(Recomposed.id("ascend_water"), AscendWaterComponent.class);
    private final Player player;
    private boolean shouldAscend = false;
    private float ascend = 0;

    private boolean hasAscend = false;

    public AscendWaterComponent(Player player) {
        this.player = player;
    }

    public void tick() {
        float boostStrength = !LifeVestItem.getWorn(player).isEmpty() ? 1.0F : 0.0F;
        hasAscend = boostStrength > 0;
        if (hasAscend) {
            if (shouldAscend) {
                if (player.isUnderWater() && CRUtil.isGroundedOrAirborne(player, true)) {
                    ascend = (float) Mth.clamp(ascend + 0.0025, boostStrength * 0.075, boostStrength);
                    player.push(0, ascend, 0);
                } else {
                    shouldAscend = false;
                    ascend = 0;
                }
            } else {
                ascend = 0;
            }
        } else {
            shouldAscend = false;
            ascend = 0;
        }
    }

    public void clientTick() {
        tick();
        if (hasAscend) {
            if (shouldAscend) {
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                ParticleOptions bubbleColumn = ParticleTypes.BUBBLE_COLUMN_UP, splash = ParticleTypes.SPLASH, bubble = ParticleTypes.BUBBLE;
                player.level().addParticle(bubbleColumn, x, y, z, 0, 0.04, 0);
                player.level().addParticle(bubbleColumn, player.getRandomX(0.5), y + player.getBbHeight() / 8, player.getRandomZ(0.5), 0, 0.04, 0);
                if (player.level().getBlockState(player.blockPosition().above()).isAir()) {
                    for (int i = 0; i < 2; i++) {
                        player.level().addParticle(splash, player.getRandomX(0.5), player.getBlockY() + 1, player.getRandomZ(0.5), 0, 1, 0);
                        player.level().addParticle(bubble, player.getRandomX(0.5), player.getBlockY() + 1, player.getRandomZ(0.5), 0, 0.2, 0);
                    }
                }
            }

            if (!LifeVestItem.getWorn(player).isEmpty()) {
                if (canUse(false)) {
                    shouldAscend = true;
                    ClientPlayNetworking.send(new AscendWaterPayload(true));
                }
            } else if (shouldAscend) {
                shouldAscend = false;
                ClientPlayNetworking.send(new AscendWaterPayload(false));
            }
        }
    }

    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        shouldAscend = tag.getBoolean("ShouldAscend");
        ascend = tag.getFloat("Ascend");
    }

    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putBoolean("ShouldAscend", shouldAscend);
        tag.putFloat("Ascend", ascend);
    }

    public void setShouldAscend(boolean shouldAscend) {
        this.shouldAscend = shouldAscend;
    }

    public boolean hasAscend() {
        return hasAscend;
    }

    public boolean canUse(boolean ignoreAscend) {
        return (ignoreAscend || !shouldAscend) && player.isUnderWater();
    }
}
