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
        FogEvents.RENDER.register(VanillaEffectsClientHandler::onRenderFog$1);
        FogEvents.RENDER.register(VanillaEffectsClientHandler::onRenderFog$2);
        RenderBlockOverlayCallback.EVENT.register(VanillaEffectsClientHandler::onRenderBlockOverlay);
    }
}
