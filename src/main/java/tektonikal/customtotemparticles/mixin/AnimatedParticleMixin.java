package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.config.YACLConfig;

import java.awt.*;

@Mixin(AnimatedParticle.class)
public abstract class AnimatedParticleMixin extends SpriteBillboardParticle {
    @Final
    @Shadow
    protected SpriteProvider spriteProvider;

    @Shadow
    public abstract void setTargetColor(int rgbHex);

    protected AnimatedParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    private void injected(CallbackInfo ci) {
        if (YACLConfig.INSTANCE.getConfig().modEnabled) {
            super.tick();
            this.setSpriteForAge(this.spriteProvider);
            if (this.age > (float) this.maxAge * YACLConfig.INSTANCE.getConfig().FadeToTime && YACLConfig.INSTANCE.getConfig().DoInColor) {
                Color col = YACLConfig.INSTANCE.getConfig().InTargetColor;
                this.red += ((col.getRed() / 255.0f) - this.red) * YACLConfig.INSTANCE.getConfig().FadeToSpeed;
                this.green += ((col.getGreen() / 255.0f) - this.green) * YACLConfig.INSTANCE.getConfig().FadeToSpeed;
                this.blue += ((col.getBlue() / 255.0f) - this.blue) * YACLConfig.INSTANCE.getConfig().FadeToSpeed;
            }
            if (this.age > this.maxAge * YACLConfig.INSTANCE.getConfig().FadeOutTime && YACLConfig.INSTANCE.getConfig().DoOutColor) {
                Color col = YACLConfig.INSTANCE.getConfig().OutTargetColor;
                this.red += ((col.getRed() / 255.0f) - this.red) * YACLConfig.INSTANCE.getConfig().FadeOutSpeed;
                this.green += ((col.getGreen() / 255.0f) - this.green) * YACLConfig.INSTANCE.getConfig().FadeOutSpeed;
                this.blue += ((col.getBlue() / 255.0f) - this.blue) * YACLConfig.INSTANCE.getConfig().FadeOutSpeed;
            }
            if (this.age > this.maxAge * YACLConfig.INSTANCE.getConfig().changeGravityAtPercent && YACLConfig.INSTANCE.getConfig().gravityOverTime) {
                this.gravityStrength += YACLConfig.INSTANCE.getConfig().gravityOverTimeAmount;
            }
            if (YACLConfig.INSTANCE.getConfig().HideOnGround && this.onGround) {
                this.markDead();
            }
            if (YACLConfig.INSTANCE.getConfig().scaleOnGround && this.onGround) {
                if (this.scale + YACLConfig.INSTANCE.getConfig().onGroundScale <= 0) {
                    this.markDead();
                } else {
                    this.scale += YACLConfig.INSTANCE.getConfig().onGroundScale;
                }
            }
            if (YACLConfig.INSTANCE.getConfig().fadeOnGround && this.onGround) {
                if (this.alpha + YACLConfig.INSTANCE.getConfig().AlphaOutSpeed >= 1) {
                    this.alpha = 1;
                }
                if (this.alpha + YACLConfig.INSTANCE.getConfig().onGroundFade <= 0) {
                    this.markDead();
                } else {
                    this.alpha += YACLConfig.INSTANCE.getConfig().onGroundFade;
                }
            }
            if (YACLConfig.INSTANCE.getConfig().scaleOverTime) {
                if (this.age > this.maxAge * YACLConfig.INSTANCE.getConfig().scaleAtPercent) {
                    if (this.scale + YACLConfig.INSTANCE.getConfig().ScaleAmount <= 0) {
                        this.markDead();
                    } else {
                        this.scale += YACLConfig.INSTANCE.getConfig().ScaleAmount;
                    }
                }
            }
            if (YACLConfig.INSTANCE.getConfig().LoseAlpha) {
                if (this.age > this.maxAge * YACLConfig.INSTANCE.getConfig().AlphaOutTime) {
                    if (this.alpha + YACLConfig.INSTANCE.getConfig().AlphaOutSpeed >= 1) {
                        this.alpha = 1;
                    }
                    if (this.alpha + YACLConfig.INSTANCE.getConfig().AlphaOutSpeed <= 0) {
                        this.markDead();
                    } else {
                        this.alpha += YACLConfig.INSTANCE.getConfig().AlphaOutSpeed;
                    }
                }
            }
            if (YACLConfig.INSTANCE.getConfig().gravityOverTime) {
                if (this.age > this.maxAge * YACLConfig.INSTANCE.getConfig().changeGravityAtPercent) {
                    this.gravityStrength += YACLConfig.INSTANCE.getConfig().gravityOverTimeAmount;
                }
            }
            ci.cancel();
        }
    }
}

