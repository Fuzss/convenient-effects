package fuzs.convenienteffects.mixin.client;

import fuzs.convenienteffects.client.util.EffectParticleRenderHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity {
    @Shadow
    @Final
    private static EntityDataAccessor<List<ParticleOptions>> DATA_EFFECT_PARTICLES;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tickEffects", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/LivingEntity;DATA_EFFECT_PARTICLES:Lnet/minecraft/network/syncher/EntityDataAccessor;"
    ), cancellable = true
    )
    protected void tickEffects(CallbackInfo callback) {
        // this mixin is only added on the client,
        // but still add a check to be sure since we access client-only classes directly in the subsequent methods
        if (this.level().isClientSide && EffectParticleRenderHelper.addEffectParticles(this,
                this.entityData.get(DATA_EFFECT_PARTICLES)
        )) {
            callback.cancel();
        }
    }
}
