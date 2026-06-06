package net.acoyt.recomposed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

/**
 * @author AcoYT
 */
@Mixin(VaultBlockEntity.Server.class)
public abstract class VaultBlockEntityServerMixin {
    @WrapMethod(method = "generateLoot")
    private static List<ItemStack> recomposed$rarerMace(ServerWorld world, VaultConfig config, BlockPos pos, PlayerEntity player, Operation<List<ItemStack>> original) {
        List<ItemStack> stacks = original.call(world, config, pos, player);
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.removeIf(stack -> stack.isOf(Items.HEAVY_CORE) && world.random.nextFloat() != 0.2F)) break;
        }

        return stacks;
    }
}
