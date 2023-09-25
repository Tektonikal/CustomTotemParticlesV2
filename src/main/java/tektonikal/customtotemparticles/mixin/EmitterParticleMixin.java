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
import org.spongepowered.asm.mixin.Shadow;
import tektonikal.customtotemparticles.config.YACLConfig;


@Mixin(EmitterParticle.class)
@Environment(EnvType.CLIENT)
public abstract class EmitterParticleMixin extends NoRenderParticle {
    @Shadow
    private Entity entity;
    @Shadow
    private ParticleEffect parameters;
    @Shadow
    private int emitterAge;
    @Shadow
    @Final
    private int maxEmitterAge;
    private int MaxEmitterAge;

    protected EmitterParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Override
    public void tick() {
        if (YACLConfig.INSTANCE.getConfig().modEnabled) {
            if (parameters == ParticleTypes.TOTEM_OF_UNDYING) {
                this.MaxEmitterAge = YACLConfig.INSTANCE.getConfig().EmitterAge;
                for (int i = 0; i < 16.0f * YACLConfig.INSTANCE.getConfig().Multiplier; ++i) {
                    double d = this.random.nextFloat() * 2.0F - 1.0F;
                    double e = this.random.nextFloat() * 2.0F - 1.0F;
                    double f = this.random.nextFloat() * 2.0F - 1.0F;
                    if (d * d + e * e + f * f > 1)
                        continue;
                    double g = this.entity.offsetX(d / 4.0);
                    double h = this.entity.getBodyY((0.5 + e / 4.0));
                    double j = this.entity.offsetZ(f / 4.0);
                    if (!YACLConfig.INSTANCE.getConfig().EmitterMovesWithPlayer && YACLConfig.INSTANCE.getConfig().useEmitter) {
                        g = this.x;
                        h = this.y;
                        j = this.z;
                    }
                    if (YACLConfig.INSTANCE.getConfig().useEmitter) {
                        h += YACLConfig.INSTANCE.getConfig().EmitterYOffset;
                    }
                    switch (YACLConfig.INSTANCE.getConfig().INSTANCE.getConfig().Particles) {
                        case CRIT -> this.world.addParticle(ParticleTypes.CRIT, true, g, h, j, d, e + 0.2, f);
                        case ENCHANTED_HIT ->
                                this.world.addParticle(ParticleTypes.ENCHANTED_HIT, false, g, h, j, d, e + 0.2, f);
                        case EFFECT -> this.world.addParticle(ParticleTypes.EFFECT, false, g, h, j, d, e + 0.2, f);
                        default ->
                                this.world.addParticle(ParticleTypes.TOTEM_OF_UNDYING, false, g, h, j, d, e + 0.2, f);
                    }
                }
                ++this.emitterAge;
                if (YACLConfig.INSTANCE.getConfig().useEmitter) {
                    if (this.emitterAge >= this.MaxEmitterAge) {
                        this.markDead();
                    }
                } else {
                    if (this.emitterAge >= this.maxEmitterAge) {
                        this.markDead();
                    }
                }
            } else {
                for (int i = 0; i < 16 * YACLConfig.INSTANCE.getConfig().GlobalMultiplier; ++i) {
                    double f;
                    double e;
                    double d = this.random.nextFloat() * 2.0f - 1.0f;
                    if (d * d + (e = this.random.nextFloat() * 2.0f - 1.0f) * e + (f = this.random.nextFloat() * 2.0f - 1.0f) * f > 1.0)
                        continue;
                    double g = this.entity.offsetX(d / 4.0);
                    double h = this.entity.getBodyY(0.5 + e / 4.0);
                    double j = this.entity.offsetZ(f / 4.0);
                    this.world.addParticle(this.parameters, false, g, h, j, d, e + 0.2, f);
                }
                ++this.emitterAge;
                if (this.emitterAge >= this.maxEmitterAge) {
                    this.markDead();
                }
            }
        } else {
            for (int i = 0; i < 16; ++i) {
                double d = this.random.nextFloat() * 2.0F - 1.0F;
                double e = this.random.nextFloat() * 2.0F - 1.0F;
                double f = this.random.nextFloat() * 2.0F - 1.0F;
                if (!(d * d + e * e + f * f > 1.0)) {
                    double g = this.entity.offsetX(d / 4.0);
                    double h = this.entity.getBodyY(0.5 + e / 4.0);
                    double j = this.entity.offsetZ(f / 4.0);
                    this.world.addParticle(this.parameters, false, g, h, j, d, e + 0.2, f);
                }
            }

            ++this.emitterAge;
            if (this.emitterAge >= this.maxEmitterAge) {
                this.markDead();
            }
        }
    }

}