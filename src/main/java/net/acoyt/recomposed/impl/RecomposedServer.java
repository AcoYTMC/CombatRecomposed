package net.acoyt.recomposed.impl;

import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.recomposed.compat.CRConfig;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author AcoYT
 */
@Environment(EnvType.SERVER)
public class RecomposedServer implements DedicatedServerModInitializer {
    public void onInitializeServer() {
        MidnightConfig.init(Recomposed.MOD_ID, CRConfig.class);
    }
}
