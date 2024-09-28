package fuzs.convenienteffects.client.util;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ClientConfig;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class EffectParticleRenderHelper {

    public static boolean addEffectParticles(Entity entity, List<ParticleOptions> effectParticles) {
        boolean isCameraEntity = entity == Minecraft.getInstance().getCameraEntity();
        if (isCameraEntity || ConvenientEffects.CONFIG.get(ClientConfig.class).reduceEffectParticlesForAllEntities) {
            ClientConfig.EffectParticleStatus particleStatus = ClientConfig.getParticleStatus(isCameraEntity);
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
                Particle particle = addParticle(particleOptions, entity.getRandomX(0.5), entity.getRandomY(),
                        entity.getRandomZ(0.5), 1.0, 1.0, 1.0
                );
                // set particle alpha to 15% as vanilla does for ambient effects
                if (particle != null) particle.setAlpha(0.15F);
            }
        }
    }

    /**
     * Copied from
     * {@link net.minecraft.client.renderer.LevelRenderer#addParticle(ParticleOptions, double, double, double, double,
     * double, double)}.
     * <p>
     * We must return the created particle.
     */
    public static Particle addParticle(ParticleOptions options, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return addParticle(options, options.getType().getOverrideLimiter(), false, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    /**
     * Copied from
     * {@link net.minecraft.client.renderer.LevelRenderer#addParticle(ParticleOptions, boolean, boolean, double, double,
     * double, double, double, double)}.
     * <p>
     * We must return the created particle.
     */
    public static Particle addParticle(ParticleOptions options, boolean force, boolean decreased, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        Minecraft minecraft = Minecraft.getInstance();
        try {
            return minecraft.levelRenderer.addParticleInternal(options, force, decreased, x, y, z, xSpeed, ySpeed,
                    zSpeed
            );
        } catch (Throwable var19) {
            CrashReport crashReport = CrashReport.forThrowable(var19, "Exception while adding particle");
            CrashReportCategory crashReportCategory = crashReport.addCategory("Particle being added");
            crashReportCategory.setDetail("ID", BuiltInRegistries.PARTICLE_TYPE.getKey(options.getType()));
            crashReportCategory.setDetail("Parameters", () -> ParticleTypes.CODEC.encodeStart(
                    minecraft.level.registryAccess().createSerializationContext(NbtOps.INSTANCE), options).toString());
            crashReportCategory.setDetail("Position",
                    () -> CrashReportCategory.formatLocation(minecraft.level, x, y, z)
            );
            throw new ReportedException(crashReport);
        }
    }
}
