package fuzs.convenienteffects.handler;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ServerConfig;
import fuzs.puzzleslib.api.event.v1.data.MutableDouble;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;

public class VanillaEffectsHandler {

    public static void onEndEntityTick(Entity entity) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).noFireResistanceBurnTime) return;
        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            entity.setRemainingFireTicks(Math.min(1, entity.getRemainingFireTicks()));
        }
    }

    public static void onLivingVisibility(LivingEntity entity, @Nullable Entity lookingEntity, MutableDouble visibilityPercentage) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).strongerBlindness) return;
        if (lookingEntity instanceof Mob mob && mob.hasEffect(MobEffects.BLINDNESS)) {
            visibilityPercentage.accept(getVisibilityMultiplier(mob.getEffect(MobEffects.BLINDNESS).getAmplifier()) * 0.5F);
        }
    }

    public static float getVisibilityMultiplier(int amplifier) {
        return Mth.clamp(1.0F / (float) Math.sqrt(Math.max(1.0F, amplifier + 1.0F)), 0.0F, 1.0F);
    }
}
