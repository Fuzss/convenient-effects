package fuzs.convenienteffects.mixin;

import fuzs.convenienteffects.ConvenientEffects;
import fuzs.convenienteffects.config.ServerConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(
            method = "travel",
            at = @At(value = "STORE", ordinal = 0),
            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;min(DD)D", ordinal = 0))
    )
    public double travel(double gravity) {
        if (!ConvenientEffects.CONFIG.get(ServerConfig.class).slowFallingQuickDescent) return gravity;
        return this.isDescending() ? Math.max(this.getGravity(), 0.01) : gravity;
    }
}
