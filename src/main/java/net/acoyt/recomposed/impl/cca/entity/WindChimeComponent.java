package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.acoyt.recomposed.impl.networking.c2s.AirJumpPayload;
import net.acoyt.recomposed.mixin.access.LivingEntityAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class WindChimeComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<WindChimeComponent> KEY = ComponentRegistry.getOrCreate(Recomposed.id("wind_chime"), WindChimeComponent.class);
    private final Player player;
    private final LivingEntityAccessor living;
    private int jumpsUsed = 0;
    private int jumpCooldown = 0;

    public WindChimeComponent(Player player) {
        this.player = player;
        this.living = (LivingEntityAccessor) player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void performDoubleJump() {
        if (living.recomposed$getJumpingCooldown() <= 0 && getRemainingJumps() > 0) {
            jumpsUsed++;
            player.resetFallDistance();
            living.recomposed$setJumpingCooldown(10);
            sync();

            player.level().playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.SAND_BREAK, SoundSource.PLAYERS,
                    1.0F, 1.0F
            );
        }
    }

    public void tick() {
        if (player.onGround() && jumpsUsed > 0) {
            jumpsUsed = 0;
            sync();
        }
    }

    public void clientTick() {
        if (living.recomposed$isJumping() && canJump() && player.fallDistance > 0.2F && !player.getAbilities().flying && WindChimeUsableEvent.EVENT.invoker().canUse(player, player.level())) {
            jumpCooldown = 12;
            Vec3 vec3d = player.getDeltaMovement();
            if (player.isSprinting()) {
                vec3d = new Vec3(vec3d.x * 1.25, 0.65 * (1 + player.getJumpBoostPower()), vec3d.z * 1.25);
                float f = player.getYRot() * 0.017453292F;
                player.setDeltaMovement(vec3d.add(-Mth.sin(f) * 0.2F, 0.0D, Mth.cos(f) * 0.2F));
            } else {
                player.setDeltaMovement(vec3d.x * 1.1, 0.55 * (1 + player.getJumpBoostPower()), vec3d.z * 1.1);
            }

            player.hurtMarked = true;
            ClientPlayNetworking.send(new AirJumpPayload());
        }

        if (player.onGround()) {
            jumpCooldown = 8;
        } else {
            if (jumpCooldown > 0) {
                jumpCooldown--;
            }
        }

        tick();
    }

    public void readFromNbt(CompoundTag tag, HolderLookup.Provider provider) {
        int jumpsUsed = tag.getInt("JumpsUsed");
        if (player.level().isClientSide && jumpsUsed > this.jumpsUsed) {
            for (int i = 0; i < 12; i++) {
                Vec3 pos = new Vec3(
                        player.getX() + player.getRandom().nextGaussian() * 0.2F,
                        player.getBoundingBox().minY + 0.5F + player.getRandom().nextGaussian() * 0.2F,
                        player.getZ() + player.getRandom().nextGaussian() * 0.2F
                );

                Vec3 velocity = new Vec3(
                        player.getRandom().nextGaussian() * 0.15F,
                        player.getRandom().nextFloat() * 0.15F,
                        player.getRandom().nextGaussian() * 0.15F
                ).scale(0.6);

                player.level().addParticle(
                        ParticleTypes.CLOUD,
                        pos.x, pos.y, pos.z,
                        velocity.x, velocity.y, velocity.z
                );
            }
        }

        this.jumpsUsed = jumpsUsed;
    }

    public void writeToNbt(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("JumpsUsed", jumpsUsed);
    }

    public int getPossibleJumps() {
        return !WindChimeItem.getWorn(player).isEmpty() ? 2 : 0;
    }

    public int getRemainingJumps() {
        return getPossibleJumps() - jumpsUsed;
    }

    public boolean canJump() {
        return jumpsUsed > 0 && jumpCooldown <= 0;
    }
}
