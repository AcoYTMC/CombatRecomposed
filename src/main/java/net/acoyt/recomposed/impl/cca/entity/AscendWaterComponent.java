package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.acoyt.recomposed.impl.networking.c2s.AscendWaterPayload;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class AscendWaterComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<AscendWaterComponent> KEY = ComponentRegistry.getOrCreate(Recomposed.id("ascend_water"), AscendWaterComponent.class);
    private final PlayerEntity player;
    private boolean shouldAscend = false;
    private float ascend = 0;

    private boolean hasAscend = false;

    public AscendWaterComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        float boostStrength = !LifeVestItem.getWorn(player).isEmpty() ? 1.0F : 0.0F;
        hasAscend = boostStrength > 0;
        if (hasAscend) {
            if (shouldAscend) {
                if (player.isSubmergedInWater() && CRUtil.isGroundedOrAirborne(player, true)) {
                    ascend = (float) MathHelper.clamp(ascend + 0.0025, boostStrength * 0.075, boostStrength);
                    player.addVelocity(0, ascend, 0);
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
                ParticleEffect bubbleColumn = ParticleTypes.BUBBLE_COLUMN_UP, splash = ParticleTypes.SPLASH, bubble = ParticleTypes.BUBBLE;
                player.getWorld().addParticle(bubbleColumn, x, y, z, 0, 0.04, 0);
                player.getWorld().addParticle(bubbleColumn, player.getParticleX(0.5), y + player.getHeight() / 8, player.getParticleZ(0.5), 0, 0.04, 0);
                if (player.getWorld().getBlockState(player.getBlockPos().up()).isAir()) {
                    for (int i = 0; i < 2; i++) {
                        player.getWorld().addParticle(splash, player.getParticleX(0.5), player.getBlockY() + 1, player.getParticleZ(0.5), 0, 1, 0);
                        player.getWorld().addParticle(bubble, player.getParticleX(0.5), player.getBlockY() + 1, player.getParticleZ(0.5), 0, 0.2, 0);
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

    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        shouldAscend = tag.getBoolean("ShouldAscend");
        ascend = tag.getFloat("Ascend");
    }

    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
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
        return (ignoreAscend || !shouldAscend) && player.isSubmergedInWater();
    }
}
