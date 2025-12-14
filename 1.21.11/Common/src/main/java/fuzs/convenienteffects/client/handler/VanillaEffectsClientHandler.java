package fuzs.convenienteffects.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ClientConfig;
import fuzs.convenienteffects.config.ServerConfig;
import fuzs.convenienteffects.handler.VanillaEffectsHandler;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import org.jspecify.annotations.Nullable;

public class VanillaEffectsClientHandler {

    public static void onSetupBlindnessFog(Camera camera, float partialTick, @Nullable FogEnvironment fogEnvironment, FogType fogType, FogData fogData) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).strongerBlindness) {
            return;
        }

        if (fogType != FogType.LAVA && fogType != FogType.POWDER_SNOW && camera.entity() instanceof LocalPlayer player
                && player.hasEffect(MobEffects.BLINDNESS)) {
            MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.BLINDNESS);
            float multiplier = VanillaEffectsHandler.getVisibilityMultiplier(mobEffectInstance.getAmplifier());
            fogData.environmentalStart *= multiplier;
            fogData.environmentalEnd *= multiplier;
        }
    }

    public static void onSetupFireResistanceFog(Camera camera, float partialTick, @Nullable FogEnvironment fogEnvironment, FogType fogType, FogData fogData) {
        if (!ConvenientEffects.CONFIG.get(ClientConfig.class).betterFireResistanceVision) {
            return;
        }

        if (fogType == FogType.LAVA && camera.entity() instanceof LocalPlayer player && applyFireResistanceEffects(
                player)) {
            MobEffectInstance mobEffectInstance = player.getEffect(MobEffects.FIRE_RESISTANCE);
            float fogDistance;
            // infinite duration returns -1, so we cannot handle that below
            if (player.isCreative() || mobEffectInstance.isInfiniteDuration()) {
                fogDistance = 1.0F;
            } else {
                float effectFadeTime = ConvenientEffects.CONFIG.get(ClientConfig.class).effectFadeTime * 20.0F;
                fogDistance = Mth.clamp((mobEffectInstance.getDuration() - partialTick) / effectFadeTime, 0.0F, 1.0F);
            }
            GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
            fogData.environmentalStart = Mth.lerp(fogDistance, 0.25F, -4.0F);
            fogData.environmentalEnd = Mth.lerp(fogDistance, 1.0F, gameRenderer.getRenderDistance() * 0.25F);
        }
    }

    public static EventResult onRenderBlockOverlay(LocalPlayer player, PoseStack poseStack, MultiBufferSource bufferSource, BlockState blockState, MaterialSet materialSet) {
        if (!ConvenientEffects.CONFIG.get(ClientConfig.class).betterFireResistanceVision) {
            return EventResult.PASS;
        }

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
