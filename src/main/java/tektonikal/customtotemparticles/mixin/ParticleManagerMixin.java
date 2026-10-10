package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.config.YACLConfig;

@Mixin(ParticleEngine.class)
public class ParticleManagerMixin {

    @Inject(at = @At("HEAD"), method = "createTrackingEmitter(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;)V", cancellable = true)
    private void CustomTotemParticles$addEmitter(Entity entity, ParticleOptions parameters, CallbackInfo ci) {
        if (CustomTotemParticles$shouldHideOwnTotemParticles(entity, parameters)) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "createTrackingEmitter(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;I)V", cancellable = true)
    private void CustomTotemParticles$addEmitterWithAge(Entity entity, ParticleOptions parameters, int maxAge, CallbackInfo ci) {
        if (CustomTotemParticles$shouldHideOwnTotemParticles(entity, parameters)) {
            ci.cancel();
        }
    }

    @Unique
    private static boolean CustomTotemParticles$shouldHideOwnTotemParticles(Entity entity, ParticleOptions parameters) {
        return YACLConfig.CONFIG.instance().modEnabled
                && !YACLConfig.CONFIG.instance().showOwnParticles
                && entity == Minecraft.getInstance().player
                && parameters == ParticleTypes.TOTEM_OF_UNDYING;
    }
}
