package net.acoyt.recomposed.compat;

import eu.midnightdust.lib.config.MidnightConfig;

/**
 * @author AcoYT
 */
public class CRConfig extends MidnightConfig {
    @Server @Entry(min = 0, max = 60) public static int combatTimer = 30;
    @Server @Entry public static boolean invisKillsNoDeathMsg = false;
}
