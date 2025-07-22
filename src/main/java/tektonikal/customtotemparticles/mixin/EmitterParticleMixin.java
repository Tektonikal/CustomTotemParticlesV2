package tektonikal.customtotemparticles.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.config.YACLConfig;


@Mixin(EmitterParticle.class)
@Environment(EnvType.CLIENT)
public abstract class EmitterParticleMixin extends NoRenderParticle {
    @Final
    @Shadow
    private Entity entity;
    @Final
    @Shadow
    private ParticleEffect parameters;
    @Shadow
    private int emitterAge;
    @Shadow
    @Final
    @Mutable
    private int maxEmitterAge;

    protected EmitterParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Redirect(method = "<init>(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;ILnet/minecraft/util/math/Vec3d;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EmitterParticle;tick()V"))
    public void CustomTotemParticles$emitterInit(EmitterParticle instance) {
        //silly easter egg, but also useful for debugging
        if (YACLConfig.CONFIG.instance().multiplier == 0) {
            world.addParticleClient(YACLConfig.CONFIG.instance().particleType.getParticleTypes(), entity.getBodyX(random.nextFloat() * 2.0F - 1.0F / 4.0), entity.getBodyY((0.5 + random.nextFloat() * 2.0F - 1.0F / 4.0)), entity.getBodyZ(random.nextFloat() * 2.0F - 1.0F / 4.0), random.nextFloat() * 2.0F - 1.0F, random.nextFloat() * 2.0F - 1.0F + 0.2F, random.nextFloat() * 2.0F - 1.0F);
            markDead();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void CustomTotemParticles$emitterTick(CallbackInfo ci) {
        if (YACLConfig.CONFIG.instance().modEnabled) {
            if (parameters == ParticleTypes.TOTEM_OF_UNDYING) {
                if (YACLConfig.CONFIG.instance().useEmitter) {
                    maxEmitterAge = YACLConfig.CONFIG.instance().emitterLifetime;
                }
                for (int i = 0; i < 16 * YACLConfig.CONFIG.instance().multiplier; ++i) {
                    double d = random.nextFloat() * 2.0F - 1.0F;
                    double e = random.nextFloat() * 2.0F - 1.0F;
                    double f = random.nextFloat() * 2.0F - 1.0F;
                    if (d * d + e * e + f * f > 1)
                        continue;
                    double g = entity.getBodyX(d / 4.0);
                    double h = entity.getBodyY((0.5 + e / 4.0));
                    double j = entity.getBodyZ(f / 4.0);
                    if (YACLConfig.CONFIG.instance().useEmitter) {
                        if (!YACLConfig.CONFIG.instance().emitterMovesWithPlayer) {
                            g = x;
                            h = y;
                            j = z;
                        }
                        h += YACLConfig.CONFIG.instance().emitterYOffset;
                    }
                    world.addParticleClient(YACLConfig.CONFIG.instance().particleType.getParticleTypes(), g, h, j, d, e + 0.2F, f);
                }
                ++emitterAge;
                if (emitterAge >= maxEmitterAge) {
                    markDead();
                }
                ci.cancel();
            }
        }
    }
}