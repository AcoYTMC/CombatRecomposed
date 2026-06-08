package net.acoyt.recomposed.impl.cca.entity;

import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.index.CRDataComponents;
import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.acoyt.recomposed.impl.networking.c2s.AirJumpPayload;
import net.acoyt.recomposed.mixin.access.LivingEntityAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
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
        KEY.sync(this.player);
    }

    public void performDoubleJump() {
        if (this.living.recomposed$getJumpingCooldown() <= 0 && this.getRemainingJumps() > 0) {
            this.jumpsUsed++;
            this.player.fallDistance = 0.0F;
            this.living.recomposed$setJumpingCooldown(10);
            this.sync();

            ItemStack stack = WindChimeItem.getWorn(this.player);
            if (stack.contains(CRDataComponents.JUMPS) && stack.getItem() instanceof WindChimeItem windChime) {
                windChime.tryDecrement(stack, this.player);
            }
        }
    }

    public void tick() {
        if (this.player.isOnGround() && this.jumpsUsed > 0) {
            this.jumpsUsed = 0;
            this.sync();
        }
    }

    public void clientTick() {
        if (this.living.recomposed$isJumping() && this.jumpCooldown <= 0 && this.getRemainingJumps() > 0 && !this.player.getAbilities().flying) {
            this.jumpCooldown = 12;
            Vec3d vec3d = this.player.getVelocity();
            if (this.player.isSprinting()) {
                vec3d = new Vec3d(vec3d.x * 1.25, 0.65 * (1 + this.player.getJumpBoostVelocityModifier()), vec3d.z * 1.25);
                float f = this.player.getYaw() * 0.017453292F;
                this.player.setVelocity(vec3d.add(-MathHelper.sin(f) * 0.2F, 0.0D, MathHelper.cos(f) * 0.2F));
            } else {
                this.player.setVelocity(vec3d.x * 1.1, 0.55 * (1 + this.player.getJumpBoostVelocityModifier()), vec3d.z * 1.1);
            }

            this.player.velocityModified = true;
            ClientPlayNetworking.send(new AirJumpPayload());
        }

        if (this.player.isOnGround()) {
            this.jumpCooldown = 8;
        } else {
            if (this.jumpCooldown > 0) {
                this.jumpCooldown--;
            }
        }

        this.tick();
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        int jumpsUsed = nbt.getInt("JumpsUsed");
        if (this.player.getWorld().isClient && jumpsUsed > this.jumpsUsed) {
            for (int i = 0; i < 12; i++) {
                Vec3d pos = new Vec3d(
                        this.player.getX() + this.player.getRandom().nextGaussian() * 0.2F,
                        this.player.getBoundingBox().minY + 0.5F + this.player.getRandom().nextGaussian() * 0.2F,
                        this.player.getZ() + this.player.getRandom().nextGaussian() * 0.2F
                );

                Vec3d velocity = new Vec3d(
                        this.player.getRandom().nextGaussian() * 0.15F,
                        this.player.getRandom().nextFloat() * 0.15F,
                        this.player.getRandom().nextGaussian() * 0.15F
                );

                this.player.getWorld().addParticle(
                        ParticleTypes.CLOUD,
                        pos.x, pos.y, pos.z,
                        velocity.x, velocity.y, velocity.z
                );
            }
        }

        this.jumpsUsed = jumpsUsed;
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("JumpsUsed", this.jumpsUsed);
    }

    public int getPossibleJumps() {
        return !WindChimeItem.getWorn(this.player).isEmpty() ? 2 : 0;
    }

    public int getRemainingJumps() {
        return this.getPossibleJumps() - this.jumpsUsed;
    }
}
