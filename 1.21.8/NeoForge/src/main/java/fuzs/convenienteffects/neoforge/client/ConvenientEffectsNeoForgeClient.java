package fuzs.convenienteffects.neoforge.client;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.client.ConvenientEffectsClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ConvenientEffects.MOD_ID, dist = Dist.CLIENT)
public class ConvenientEffectsNeoForgeClient {

    public ConvenientEffectsNeoForgeClient() {
        ClientModConstructor.construct(ConvenientEffects.MOD_ID, ConvenientEffectsClient::new);
    }
}
