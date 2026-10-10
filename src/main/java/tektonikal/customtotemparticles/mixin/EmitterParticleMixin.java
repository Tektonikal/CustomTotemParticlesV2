package tektonikal.customtotemparticles.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.TrackingEmitter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.config.YACLConfig;


@Mixin(TrackingEmitter.class)
@Environment(EnvType.CLIENT)
public abstract class EmitterParticleMixin extends NoRenderParticle {
    @Final
    @Shadow
    private Entity entity;
    @Final
    @Shadow
    private ParticleOptions particleType;
    @Shadow
    private int life;
    @Shadow
    @Final
    @Mutable
    private int lifeTime;

    protected EmitterParticleMixin(ClientLevel clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Redirect(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/particles/ParticleOptions;ILnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/TrackingEmitter;tick()V"))
    public void CustomTotemParticles$emitterInit(TrackingEmitter instance) {
        //silly easter egg, but also useful for debugging
        if (YACLConfig.CONFIG.instance().multiplier == 0) {
            level.addParticle(YACLConfig.CONFIG.instance().particleType.getParticleTypes(), entity.getRandomX((random.nextFloat() * 2.0F - 1.0F) / 4.0), entity.getY(0.5 + (random.nextFloat() * 2.0F - 1.0F) / 4.0), entity.getRandomZ((random.nextFloat() * 2.0F - 1.0F) / 4.0), random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 2.0F - 1.0F + 0.2F, random.nextFloat() * 2.0F - 1.0F);
            remove();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void CustomTotemParticles$emitterTick(CallbackInfo ci) {
        if (YACLConfig.CONFIG.instance().modEnabled) {
            if (particleType == ParticleTypes.TOTEM_OF_UNDYING) {
                if (YACLConfig.CONFIG.instance().useEmitter) {
                    lifeTime = YACLConfig.CONFIG.instance().emitterLifetime;
                }
                for (int i = 0; i < 16 * YACLConfig.CONFIG.instance().multiplier; ++i) {
                    double d = random.nextFloat() * 2.0F - 1.0F;
                    double e = random.nextFloat() * 2.0F - 1.0F;
                    double f = random.nextFloat() * 2.0F - 1.0F;
                    if (d * d + e * e + f * f > 1)
                        continue;
                    double g = entity.getRandomX(d / 4.0);
                    double h = entity.getY((0.5 + e / 4.0));
                    double j = entity.getRandomZ(f / 4.0);
                    if (YACLConfig.CONFIG.instance().useEmitter) {
                        if (!YACLConfig.CONFIG.instance().emitterMovesWithPlayer) {
                            g = x;
                            h = y;
                            j = z;
                        }
                        h += YACLConfig.CONFIG.instance().emitterYOffset;
                    }
                    level.addParticle(YACLConfig.CONFIG.instance().particleType.getParticleTypes(), g, h, j, d, e + 0.2F, f);
                }
                ++life;
                if (life >= lifeTime) {
                    remove();
                }
                ci.cancel();
            }
        }
    }
}