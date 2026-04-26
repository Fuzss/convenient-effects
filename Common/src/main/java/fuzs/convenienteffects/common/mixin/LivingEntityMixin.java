package fuzs.convenienteffects.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import fuzs.convenienteffects.common.ConvenientEffects;
import fuzs.convenienteffects.common.config.ServerConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
}
