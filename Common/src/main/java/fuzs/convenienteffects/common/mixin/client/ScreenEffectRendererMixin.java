package fuzs.convenienteffects.common.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.convenienteffects.common.ConvenientEffects;
import fuzs.convenienteffects.common.config.ClientConfig;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenEffectRenderer.class)
abstract class ScreenEffectRendererMixin {

    @WrapOperation(method = "submit",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/ScreenEffectRenderer;submitFire(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"))
    private void submitFire(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, TextureAtlasSprite textureAtlasSprite, Operation<Void> operation) {
        double flameOverlayHeight = ConvenientEffects.CONFIG.get(ClientConfig.class).flameOverlayHeight;
        if (flameOverlayHeight >= 1.0) {
            operation.call(poseStack, submitNodeCollector, textureAtlasSprite);
        } else if (flameOverlayHeight > 0.0) {
            poseStack.pushPose();
            poseStack.translate(0.0, -0.5 + flameOverlayHeight / 2.0, 0.0);
            operation.call(poseStack, submitNodeCollector, textureAtlasSprite);
            poseStack.popPose();
        }
    }
}
