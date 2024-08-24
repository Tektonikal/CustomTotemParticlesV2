package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.config.YACLConfig;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(at = @At("HEAD"), method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V", cancellable = true)
    private void CustomTotemParticles$addEmitter(Entity entity, ParticleEffect parameters, int maxAge, CallbackInfo ci) {
        if (YACLConfig.CONFIG.instance().modEnabled) {
            if (!YACLConfig.CONFIG.instance().showOwnParticles)
                if (entity == MinecraftClient.getInstance().player && parameters == ParticleTypes.TOTEM_OF_UNDYING) {
                    ci.cancel();
                }
        }
    }
}
