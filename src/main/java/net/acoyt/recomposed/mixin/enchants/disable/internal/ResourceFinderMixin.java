package net.acoyt.recomposed.mixin.enchants.disable.internal;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.recomposed.impl.util.CRUtil;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AcoYT
 */
@Mixin(ResourceFinder.class)
public abstract class ResourceFinderMixin {
    @Shadow public abstract Identifier toResourceId(Identifier path);

    @ModifyReturnValue(method = "findResources", at = @At("RETURN"))
    private Map<Identifier, Resource> recomposed$removeDisabled(Map<Identifier, Resource> original) {
        Map<Identifier, Resource> filtered = new HashMap<>();
        original.forEach((id, resource) -> {
            if (!id.getPath().startsWith("enchantment/") || !CRUtil.isDisabled(toResourceId(id))) {
                filtered.put(id, resource);
            }
        });

        return filtered;
    }
}
