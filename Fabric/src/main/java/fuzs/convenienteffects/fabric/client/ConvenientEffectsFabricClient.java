package fuzs.convenienteffects.fabric.client;

import fuzs.convenienteffects.common.ConvenientEffects;
import fuzs.convenienteffects.common.client.ConvenientEffectsClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ConvenientEffectsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ConvenientEffects.MOD_ID, ConvenientEffectsClient::new);
    }
}
