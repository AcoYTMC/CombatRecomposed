package net.acoyt.recomposed.impl.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.acoyt.recomposed.impl.Recomposed;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * @author AcoYT
 */
public interface CRItemTags {
    TagBuilder<Item> ITEMS = new TagBuilder<>(Recomposed.MOD_ID, Registries.ITEM);

    TagKey<Item> HAS_DURABILITY = ITEMS.register("has_durability");
    TagKey<Item> STRONG = ITEMS.register("strong");
}
