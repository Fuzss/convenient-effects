package fuzs.convenienteffects;

import fuzs.convenienteffects.config.ClientConfig;
import fuzs.convenienteffects.config.ServerConfig;
import fuzs.convenienteffects.handler.VanillaEffectsHandler;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingEvents;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvenientEffects implements ModConstructor {
    public static final String MOD_ID = "convenienteffects";
    public static final String MOD_NAME = "Convenient Effects";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID)
            .client(ClientConfig.class)
            .server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        LivingEvents.TICK.register(VanillaEffectsHandler::onLivingTick);
        LivingEvents.VISIBILITY.register(VanillaEffectsHandler::onLivingVisibility);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
