package fuzs.convenienteffects.config;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;
import net.minecraft.client.Minecraft;

public class ClientConfig implements ConfigCore {
    static final String DESCRIPTION_EFFECT_PARTICLES = "Control rendering of mob effect particles on the player.";
    static final String DESCRIPTION_DISCREET_EFFECT_PARTICLES = "The discreet setting will greatly reduce the amount of particles and makes them transparent, just like vanilla particles from beacon effects.";

    @Config(description = "Time in seconds before an effect is about to end when any visuals begin to fade back to normal.")
    @Config.IntRange(min = 1, max = 20)
    public int effectFadeTime = 3;
    @Config(description = "Fire resistance provides better vision in lava, no longer shows the flame overlay.")
    public boolean betterFireResistanceVision = true;
    @Config(description = "Night vision fades away when ending instead of flashing.")
    public boolean noNightVisionFlashing = true;
    @Config(description = {DESCRIPTION_EFFECT_PARTICLES, DESCRIPTION_DISCREET_EFFECT_PARTICLES})
    public EffectParticleStatus firstPersonEffectParticles = EffectParticleStatus.DISCREET;
    @Config(description = {DESCRIPTION_EFFECT_PARTICLES, DESCRIPTION_DISCREET_EFFECT_PARTICLES})
    public EffectParticleStatus thirdPersonEffectParticles = EffectParticleStatus.DISCREET;
    @Config(description = "Should the effect particles setting affect all entities in the level, not just your player.")
    public boolean reduceEffectParticlesForAllEntities = false;

    public enum EffectParticleStatus {
        NONE,
        DISCREET,
        ALL
    }

    public static EffectParticleStatus getParticleStatus(boolean isCameraEntity) {
        Minecraft minecraft = Minecraft.getInstance();
        if (isCameraEntity && minecraft.options.getCameraType().isFirstPerson()) {
            return ConvenientEffects.CONFIG.get(ClientConfig.class).firstPersonEffectParticles;
        } else {
            return ConvenientEffects.CONFIG.get(ClientConfig.class).thirdPersonEffectParticles;
        }
    }
}
