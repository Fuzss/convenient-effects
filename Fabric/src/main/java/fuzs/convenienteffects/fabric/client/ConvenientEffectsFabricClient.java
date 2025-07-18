package fuzs.convenienteffects.fabric.client;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.client.ConvenientEffectsClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ConvenientEffectsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ConvenientEffects.MOD_ID, ConvenientEffectsClient::new);
    }
}
