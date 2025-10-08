package fuzs.convenienteffects.neoforge;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.neoforged.fml.common.Mod;

@Mod(ConvenientEffects.MOD_ID)
public class ConvenientEffectsNeoForge {

    public ConvenientEffectsNeoForge() {
        ModConstructor.construct(ConvenientEffects.MOD_ID, ConvenientEffects::new);
    }
}
