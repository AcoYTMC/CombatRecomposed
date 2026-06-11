package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.api.WindChimeUsableEvent;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.acoyt.recomposed.impl.networking.c2s.AirJumpPayload;
import net.acoyt.recomposed.mixin.access.LivingEntityAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class WindChimeComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<WindChimeComponent> KEY = ComponentRegistry.getOrCreate(Recomposed.id("wind_chime"), WindChimeComponent.class);
    private final PlayerEntity player;
    private final LivingEntityAccessor living;
    private int jumpsUsed = 0;
    private int jumpCooldown = 0;

    public WindChimeComponent(PlayerEntity player) {
        this.player = player;
        this.living = (LivingEntityAccessor) player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void performDoubleJump() {
        if (living.recomposed$getJumpingCooldown() <= 0 && getRemainingJumps() > 0) {
            jumpsUsed++;
            player.onLanding();
            living.recomposed$setJumpingCooldown(10);
            sync();

            player.getWorld().playSound(
                    null,
                    player.getBlockPos(),
                    SoundEvents.BLOCK_SAND_BREAK, SoundCategory.PLAYERS,
                    1.0F, 1.0F
            );
        }
    }

    public void tick() {
        if (player.isOnGround() && jumpsUsed > 0) {
            jumpsUsed = 0;
            sync();
        }
    }

    public void clientTick() {
        if (living.recomposed$isJumping() && jumpCooldown <= 0 && getRemainingJumps() > 0 && player.fallDistance > 0.3F && !player.getAbilities().flying && WindChimeUsableEvent.EVENT.invoker().canUse(player, player.getWorld())) {
            jumpCooldown = 12;
            Vec3d vec3d = player.getVelocity();
            if (player.isSprinting()) {
                vec3d = new Vec3d(vec3d.x * 1.25, 0.65 * (1 + player.getJumpBoostVelocityModifier()), vec3d.z * 1.25);
                float f = player.getYaw() * 0.017453292F;
                player.setVelocity(vec3d.add(-MathHelper.sin(f) * 0.2F, 0.0D, MathHelper.cos(f) * 0.2F));
            } else {
                player.setVelocity(vec3d.x * 1.1, 0.55 * (1 + player.getJumpBoostVelocityModifier()), vec3d.z * 1.1);
            }

            player.velocityModified = true;
            ClientPlayNetworking.send(new AirJumpPayload());
        }

        if (player.isOnGround()) {
            jumpCooldown = 8;
        } else {
            if (jumpCooldown > 0) {
                jumpCooldown--;
            }
        }

        tick();
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        int jumpsUsed = nbt.getInt("JumpsUsed");
        if (player.getWorld().isClient && jumpsUsed > this.jumpsUsed) {
            for (int i = 0; i < 12; i++) {
                Vec3d pos = new Vec3d(
                        player.getX() + player.getRandom().nextGaussian() * 0.2F,
                        player.getBoundingBox().minY + 0.5F + player.getRandom().nextGaussian() * 0.2F,
                        player.getZ() + player.getRandom().nextGaussian() * 0.2F
                );

                Vec3d velocity = new Vec3d(
                        player.getRandom().nextGaussian() * 0.15F,
                        player.getRandom().nextFloat() * 0.15F,
                        player.getRandom().nextGaussian() * 0.15F
                );

                player.getWorld().addParticle(
                        ParticleTypes.CLOUD,
                        pos.x, pos.y, pos.z,
                        velocity.x, velocity.y, velocity.z
                );
            }
        }

        this.jumpsUsed = jumpsUsed;
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("JumpsUsed", jumpsUsed);
    }

    public int getPossibleJumps() {
        return !WindChimeItem.getWorn(player).isEmpty() ? 2 : 0;
    }

    public int getRemainingJumps() {
        return getPossibleJumps() - jumpsUsed;
    }
}
