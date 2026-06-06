package net.acoyt.recomposed.impl.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

/**
 * @author AcoYT
 */
public interface CRItemTags {
    TagBuilder<Item> ITEMS = new TagBuilder<>(Recomposed.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> HAS_DURABILITY = ITEMS.register("has_durability");
}
