package fuzs.convenienteffects.common.mixin.client;

import fuzs.convenienteffects.common.ConvenientEffects;
import fuzs.convenienteffects.common.config.ClientConfig;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
abstract class GameRendererMixin {

    @Inject(method = "nightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void nightVisionScale(LivingEntity camera, float a, CallbackInfoReturnable<Float> callback) {
        if (!ConvenientEffects.CONFIG.get(ClientConfig.class).noNightVisionFlashing) {
            return;
        }

        MobEffectInstance mobEffect = camera.getEffect(MobEffects.NIGHT_VISION);
        if (mobEffect != null && !mobEffect.isInfiniteDuration()) {
            float fadeTime = ConvenientEffects.CONFIG.get(ClientConfig.class).effectFadeTime * 20.0F;
            float nightVisionScale = Mth.clamp((mobEffect.getDuration() - a) / fadeTime, 0.0F, 1.0F);
            callback.setReturnValue(nightVisionScale);
        }
    }
}
