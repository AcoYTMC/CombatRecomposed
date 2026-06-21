package net.acoyt.recomposed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

/**
 * @author AcoYT
 */
public interface CRItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Recomposed.MOD_ID);

    Item WIND_CHIME = ITEMS.register("wind_chime", WindChimeItem::new, new Item.Settings()
            .maxCount(1)
            .fireproof());

    Item LIFE_VEST = ITEMS.register("life_vest", LifeVestItem::new, new Item.Settings()
            .maxCount(1)
            .fireproof());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(CRItems::addToolEntries);
    }

    private static void addToolEntries(FabricItemGroupEntries entries) {
        entries.add(WIND_CHIME);
        entries.add(LIFE_VEST);
    }
}
