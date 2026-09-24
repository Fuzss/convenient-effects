package fuzs.convenienteffects.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import fuzs.convenienteffects.common.ConvenientEffects;
import fuzs.convenienteffects.common.config.ServerConfig;
import fuzs.convenienteffects.common.handler.VanillaEffectsHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "getEffectiveGravity", at = @At("RETURN"))
    protected double getEffectiveGravity(double effectiveGravity) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).slowFallingQuickDescent) {
            return effectiveGravity;
        }

        return effectiveGravity != this.getGravity() && this.isDescending() ? Math.max(this.getGravity(), 0.01) :
                effectiveGravity;
    }

    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    protected double getVisibilityPercent(double visibilityPercent, @Local(argsOnly = true) @Nullable Entity lookingEntity) {
        return VanillaEffectsHandler.getVisibilityPercent(lookingEntity, visibilityPercent);
    }
}
