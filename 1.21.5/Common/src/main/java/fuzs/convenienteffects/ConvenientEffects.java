package fuzs.convenienteffects;

import fuzs.convenienteffects.config.ClientConfig;
import fuzs.convenienteffects.config.CommonConfig;
import fuzs.convenienteffects.config.ServerConfig;
import fuzs.convenienteffects.handler.VanillaEffectsHandler;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.entity.EntityTickEvents;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingVisibilityCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvenientEffects implements ModConstructor {
    public static final String MOD_ID = "convenienteffects";
    public static final String MOD_NAME = "Convenient Effects";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).client(ClientConfig.class).common(
            CommonConfig.class).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        EntityTickEvents.END.register(VanillaEffectsHandler::onEndEntityTick);
        LivingVisibilityCallback.EVENT.register(VanillaEffectsHandler::onLivingVisibility);
    }

    @Override
    public void onCommonSetup() {
        if (CONFIG.get(CommonConfig.class).jumpBoostIncreasesStepHeight) {
            // this will enable stepping up a single block at an amplifier of at least II (the default player step height is 0.6)
            MobEffects.JUMP.value().addAttributeModifier(Attributes.STEP_HEIGHT,
                    ResourceLocation.withDefaultNamespace("effect.jump_boost"), 0.5,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            );
        }
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
