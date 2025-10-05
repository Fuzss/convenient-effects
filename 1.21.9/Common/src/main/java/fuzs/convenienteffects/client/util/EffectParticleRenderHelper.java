package fuzs.convenienteffects.client.util;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ClientConfig;
import fuzs.puzzleslib.api.client.util.v1.ClientParticleHelper;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class EffectParticleRenderHelper {

    public static boolean tickEffectParticles(Entity entity, List<ParticleOptions> effectParticles) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean isCameraEntity = entity == minecraft.getCameraEntity();
        if (isCameraEntity || ConvenientEffects.CONFIG.get(ClientConfig.class).reduceEffectParticlesForAllEntities) {
            ClientConfig.EffectParticleStatus particleStatus = ClientConfig.getParticleStatus(minecraft,
                    isCameraEntity);
            if (particleStatus == ClientConfig.EffectParticleStatus.DISCREET) {
                addDiscreetEffectParticles(entity, effectParticles);
            }

            return particleStatus != ClientConfig.EffectParticleStatus.ALL;
        }

        return false;
    }

    public static void addDiscreetEffectParticles(Entity entity, List<ParticleOptions> effectParticles) {
        if (!effectParticles.isEmpty()) {
            int invisibleMultiplier = entity.isInvisible() ? 15 : 4;
            // reduce particle count to 20% as vanilla does for ambient effects
            int ambientMultiplier = 5;
            if (entity.getRandom().nextInt(invisibleMultiplier * ambientMultiplier) == 0) {
                ParticleOptions particleOptions = Util.getRandom(effectParticles, entity.getRandom());
                Particle particle = ClientParticleHelper.addParticle((ClientLevel) entity.level(),
                        particleOptions,
                        entity.getRandomX(0.5),
                        entity.getRandomY(),
                        entity.getRandomZ(0.5),
                        1.0,
                        1.0,
                        1.0);
                // set particle alpha to 15% as vanilla does for ambient effects
                if (particle instanceof SingleQuadParticle singleQuadParticle) {
                    singleQuadParticle.alpha = 0.15F;
                }
            }
        }
    }
}
