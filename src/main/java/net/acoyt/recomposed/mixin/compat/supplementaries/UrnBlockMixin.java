package net.acoyt.recomposed.mixin.compat.supplementaries;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.mehvahdjukaar.supplementaries.common.block.blocks.UrnBlock;
import net.minecraft.block.FallingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(UrnBlock.class)
public abstract class UrnBlockMixin extends FallingBlock {
    public UrnBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyExpressionValue(
            method = "onBreak",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/World;isClient:Z"
            )
    )
    private boolean recomposed$crashFix(boolean original) {
        return false;
    }
}
