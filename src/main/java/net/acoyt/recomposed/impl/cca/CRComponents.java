package net.acoyt.recomposed.impl.cca;

import net.acoyt.recomposed.impl.cca.entity.WindChimeComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

/**
 * @author AcoYT
 */
public class CRComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(WindChimeComponent.KEY, WindChimeComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
