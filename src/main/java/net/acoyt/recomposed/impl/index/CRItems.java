package net.acoyt.recomposed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.acoyt.recomposed.impl.Recomposed;
import net.acoyt.recomposed.impl.item.LifeVestItem;
import net.acoyt.recomposed.impl.item.SapphireCrystalItem;
import net.acoyt.recomposed.impl.item.WindChimeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

/**
 * @author AcoYT
 */
public interface CRItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Recomposed.MOD_ID);

    Item WIND_CHIME = ITEMS.register("wind_chime", WindChimeItem::new, buildProperties());
    Item LIFE_VEST = ITEMS.register("life_vest", LifeVestItem::new, buildProperties());
    Item SAPPHIRE_CRYSTAL = ITEMS.register("sapphire_crystal", SapphireCrystalItem::new, buildProperties());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(CRItems::addToolEntries);
    }

    private static void addToolEntries(FabricItemGroupEntries entries) {
        entries.accept(WIND_CHIME);
        entries.accept(LIFE_VEST);
        entries.accept(SAPPHIRE_CRYSTAL);
    }

    private static Item.Properties buildProperties() {
        return new Item.Properties()
                .component(CRDataComponents.IMMORTAL, true)
                .stacksTo(1)
                .fireResistant();
    }
}
