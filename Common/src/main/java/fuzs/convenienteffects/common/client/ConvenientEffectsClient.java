package fuzs.convenienteffects.common.client;

import fuzs.convenienteffects.common.client.handler.VanillaEffectsClientHandler;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.FogEvents;
import fuzs.puzzleslib.common.api.client.event.v1.renderer.RenderBlockOverlayCallback;

public class ConvenientEffectsClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        FogEvents.SETUP.register(VanillaEffectsClientHandler::onSetupFog);
        RenderBlockOverlayCallback.EVENT.register(VanillaEffectsClientHandler::onRenderBlockOverlay);
    }
}
