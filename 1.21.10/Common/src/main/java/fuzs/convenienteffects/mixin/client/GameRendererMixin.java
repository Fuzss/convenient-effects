package fuzs.convenienteffects.mixin.client;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ClientConfig;
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

    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void getNightVisionScale(LivingEntity livingEntity, float partialTick, CallbackInfoReturnable<Float> callback) {
        if (!ConvenientEffects.CONFIG.get(ClientConfig.class).noNightVisionFlashing) {
            return;
        }

        MobEffectInstance mobEffect = livingEntity.getEffect(MobEffects.NIGHT_VISION);
        if (mobEffect != null && !mobEffect.isInfiniteDuration()) {
            float fadeTime = ConvenientEffects.CONFIG.get(ClientConfig.class).effectFadeTime * 20.0F;
            float nightVisionScale = Mth.clamp((mobEffect.getDuration() - partialTick) / fadeTime, 0.0F, 1.0F);
            callback.setReturnValue(nightVisionScale);
        }
    }
}
