package fuzs.convenienteffects.client.handler;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ClientConfig;
import fuzs.convenienteffects.config.ServerConfig;
import fuzs.convenienteffects.handler.VanillaEffectsHandler;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.data.MutableFloat;
import fuzs.puzzleslib.api.event.v1.data.MutableValue;
import net.minecraft.client.Camera;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.Nullable;

public class VanillaEffectsClientHandler {

    public static void onRenderFog$1(GameRenderer gameRenderer, Camera camera, float partialTicks, FogRenderer.FogMode fogMode, FogType fogType, MutableFloat fogStart, MutableFloat fogEnd, MutableValue<FogShape> fogShape) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).strongerBlindness) return;
        if (fogType != FogType.LAVA && fogType != FogType.POWDER_SNOW &&
                camera.getEntity() instanceof LocalPlayer player && player.hasEffect(MobEffects.BLINDNESS)) {
            MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.BLINDNESS);
            float multiplier = VanillaEffectsHandler.getVisibilityMultiplier(mobEffectInstance.getAmplifier());
            fogStart.mapFloat(value -> value * multiplier);
            fogEnd.mapFloat(value -> value * multiplier);
        }
    }

    public static void onRenderFog$2(GameRenderer gameRenderer, Camera camera, float partialTicks, FogRenderer.FogMode fogMode, FogType fogType, MutableFloat fogStart, MutableFloat fogEnd, MutableValue<FogShape> fogShape) {
        if (!ConvenientEffects.CONFIG.get(ClientConfig.class).betterFireResistanceVision) return;
        if (fogType == FogType.LAVA && camera.getEntity() instanceof LocalPlayer player && applyFireResistanceEffects(
                player)) {
            MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.FIRE_RESISTANCE);
            float fogDistance;
            // infinite duration returns -1, so we cannot handle that below
            if (player.isCreative() || mobEffectInstance.isInfiniteDuration()) {
                fogDistance = 1.0F;
            } else {
                float effectFadeTime = ConvenientEffects.CONFIG.get(ClientConfig.class).effectFadeTime * 20.0F;
                fogDistance = Mth.clamp((mobEffectInstance.getDuration() - partialTicks) / effectFadeTime, 0.0F, 1.0F);
            }
            fogStart.accept(Mth.lerp(fogDistance, 0.25F, -4.0F));
            fogEnd.accept(Mth.lerp(fogDistance, 1.0F, gameRenderer.getRenderDistance() * 0.25F));
        }
    }

    public static EventResult onRenderBlockOverlay(LocalPlayer player, PoseStack poseStack, @Nullable BlockState blockState) {
        if (!ConvenientEffects.CONFIG.get(ClientConfig.class).betterFireResistanceVision) return EventResult.PASS;
        if (blockState == Blocks.FIRE.defaultBlockState() && applyFireResistanceEffects(player)) {
            return EventResult.INTERRUPT;
        } else {
            return EventResult.PASS;
        }
    }

    private static boolean applyFireResistanceEffects(Player player) {
        // this is already handled in vanilla for spectators
        return player.isCreative() || !player.isSpectator() && player.hasEffect(MobEffects.FIRE_RESISTANCE);
    }
}
