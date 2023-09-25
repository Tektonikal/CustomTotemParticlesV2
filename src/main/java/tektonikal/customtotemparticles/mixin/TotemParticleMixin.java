package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.config.YACLConfig;

import java.awt.*;
import java.util.Random;

import static tektonikal.customtotemparticles.MathHelper.*;


@Mixin(TotemParticle.class)
public class TotemParticleMixin extends AnimatedParticle {
    private static final Random rand = new Random();


    public TotemParticleMixin(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 1.25f);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider, CallbackInfo info) {
        if (YACLConfig.INSTANCE.getConfig().modEnabled) {
            if (YACLConfig.INSTANCE.getConfig().useMovement) {
                this.velocityMultiplier = SafeRandom(YACLConfig.INSTANCE.getConfig().MinVelocityMultiplier, YACLConfig.INSTANCE.getConfig().MaxVelocityMultiplier);
                this.collidesWithWorld = YACLConfig.INSTANCE.getConfig().useCollisions;
            }
            if (YACLConfig.INSTANCE.getConfig().useGravity) {
                this.gravityStrength = SafeRandom(YACLConfig.INSTANCE.getConfig().MinUpwardsAccel, YACLConfig.INSTANCE.getConfig().MaxUpwardsAccel);
            }
            if (YACLConfig.INSTANCE.getConfig().customVelocity) {
                this.velocityZ = SafeRandom(YACLConfig.INSTANCE.getConfig().minZVelocity, YACLConfig.INSTANCE.getConfig().maxZVelocity);
                this.velocityY = SafeRandom(YACLConfig.INSTANCE.getConfig().minYVelocity, YACLConfig.INSTANCE.getConfig().maxYVelocity);
                this.velocityX = SafeRandom(YACLConfig.INSTANCE.getConfig().minXVelocity, YACLConfig.INSTANCE.getConfig().maxXVelocity);
            }
            if (YACLConfig.INSTANCE.getConfig().UseAngle) {
                this.angle = SafeRandom(YACLConfig.INSTANCE.getConfig().MinStartRotation, YACLConfig.INSTANCE.getConfig().MaxStartRotation);
            }
            if (YACLConfig.INSTANCE.getConfig().useScale) {
                this.scale *= SafeRandom(YACLConfig.INSTANCE.getConfig().minScale, YACLConfig.INSTANCE.getConfig().maxScale);
            }
            if (YACLConfig.INSTANCE.getConfig().useAge) {
                this.maxAge = SafeRandom(YACLConfig.INSTANCE.getConfig().minAge, YACLConfig.INSTANCE.getConfig().maxAge);
            }
            if (YACLConfig.INSTANCE.getConfig().useColor) {
                if (!YACLConfig.INSTANCE.getConfig().StartColorList.isEmpty()) {
                    if (YACLConfig.INSTANCE.getConfig().BlendColors) {
                        if (YACLConfig.INSTANCE.getConfig().StartColorList.size() >= 2) {
                            Color color1 = YACLConfig.INSTANCE.getConfig().StartColorList.get(0);
                            Color color2 = YACLConfig.INSTANCE.getConfig().StartColorList.get(1);
                            int bufferR;
                            int bufferG;
                            int bufferB;
                            //I could blend the colors in so many ways better but I DONT CARE
                            if (color1.getRed() == color2.getRed()) {
                                bufferR = color1.getRed();
                            } else {
                                if (color1.getRed() < color2.getRed()) { //color 1 is min
                                    bufferR = rand.nextInt(color2.getRed() - color1.getRed()) + color1.getRed();
                                } else {
                                    bufferR = rand.nextInt(color1.getRed() - color2.getRed()) + color2.getRed();
                                }
                            }
                            if (color1.getGreen() == color2.getGreen()) {
                                bufferG = color1.getGreen();
                            } else {
                                if (color1.getGreen() < color2.getGreen()) {
                                    bufferG = rand.nextInt(color2.getGreen() - color1.getGreen()) + color1.getGreen();

                                } else {
                                    bufferG = rand.nextInt(color1.getGreen() - color2.getGreen()) + color2.getGreen();
                                }
                            }
                            if (color1.getBlue() == color2.getBlue()) {
                                bufferB = color1.getBlue();
                            } else {
                                if (color1.getBlue() < color2.getBlue()) {//color1 is min
                                    bufferB = rand.nextInt(color2.getBlue() - color1.getBlue()) + color1.getBlue();
                                } else {
                                    bufferB = rand.nextInt(color1.getBlue() - color2.getBlue()) + color2.getBlue();
                                }
                            }
                            this.setColor((float) bufferR / 255.0f, (float) bufferG / 255.0f, (float) bufferB / 255.0f);
                        }
                    } else {
                        this.setColor(YACLConfig.INSTANCE.getConfig().StartColorList.get(rand.nextInt(YACLConfig.INSTANCE.getConfig().StartColorList.size())).hashCode());
                    }
                }
            }
            if (YACLConfig.INSTANCE.getConfig().useAlpha) {
                this.alpha = SafeRandom(YACLConfig.INSTANCE.getConfig().minAlpha, YACLConfig.INSTANCE.getConfig().maxAlpha);
            }
        }
    }
}