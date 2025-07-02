package fuzs.convenienteffects.client;

import fuzs.convenienteffects.client.handler.VanillaEffectsClientHandler;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.event.v1.renderer.FogEvents;
import fuzs.puzzleslib.api.client.event.v1.renderer.RenderBlockOverlayCallback;

public class ConvenientEffectsClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        FogEvents.SETUP.register(VanillaEffectsClientHandler::onSetupBlindnessFog);
        FogEvents.SETUP.register(VanillaEffectsClientHandler::onSetupFireResistanceFog);
        RenderBlockOverlayCallback.EVENT.register(VanillaEffectsClientHandler::onRenderBlockOverlay);
    }
}
