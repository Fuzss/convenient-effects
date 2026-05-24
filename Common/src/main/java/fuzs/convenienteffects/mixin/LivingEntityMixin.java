package fuzs.convenienteffects.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ServerConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyExpressionValue(method = "travel",
                           at = @At(value = "INVOKE",
                                    target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z",
                                    ordinal = 0))
    public boolean travel(boolean hasEffect) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).slowFallingQuickDescent) return hasEffect;
        return hasEffect && !this.isDescending();
    }

    @ModifyExpressionValue(method = "maxUpStep",
                           at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;maxUpStep()F"))
    public float maxUpStep(float maxUpStep) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).jumpBoostIncreasesStepHeight) {
            return maxUpStep;
        }

        if (this.hasEffect(MobEffects.JUMP)) {
            // This will enable stepping up a single block at an amplifier of at least II (the default player step height is 0.6).
            return maxUpStep + maxUpStep * (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.5F;
        } else {
            return maxUpStep;
        }
    }

    @Shadow
    public abstract boolean hasEffect(MobEffect effect);

    @Shadow
    public abstract @Nullable MobEffectInstance getEffect(MobEffect effect);
}
