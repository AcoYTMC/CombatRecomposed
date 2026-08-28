package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AcoYT
 */
@Mixin(FileToIdConverter.class)
public abstract class FileToIdConverterMixin {
    @Shadow public abstract ResourceLocation fileToId(ResourceLocation resourceLocation);

    @ModifyReturnValue(method = "listMatchingResources", at = @At("RETURN"))
    private Map<ResourceLocation, Resource> recomposed$removeDisabled(Map<ResourceLocation, Resource> original) {
        Map<ResourceLocation, Resource> filtered = new HashMap<>();
        original.forEach((id, resource) -> {
            if (!id.getPath().startsWith("enchantment/") || !CRUtil.isDisabled(fileToId(id))) {
                filtered.put(id, resource);
            }
        });

        return filtered;
    }
}
