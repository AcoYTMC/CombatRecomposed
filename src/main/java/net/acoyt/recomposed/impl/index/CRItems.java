package net.acoyt.recomposed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.acoyt.recomposed.impl.Recomposed;

/**
 * @author AcoYT
 */
public interface CRItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Recomposed.MOD_ID);

    static void init() {}
}
