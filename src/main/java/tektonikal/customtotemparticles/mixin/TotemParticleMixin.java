package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.customtotemparticles.Utils;
import tektonikal.customtotemparticles.config.YACLConfig;

import java.awt.*;

import static tektonikal.customtotemparticles.Utils.SafeRandom;
import static tektonikal.customtotemparticles.Utils.rand;


@Mixin(TotemParticle.class)
public abstract class TotemParticleMixin extends AnimatedParticle {
	@Unique
	private final Quaternionf rotation = new Quaternionf();
	@Unique
	public float prevScale = scale;
	@Unique
	public float prevRed, prevGreen, prevBlue, prevAlpha;
	@Unique
	public float prevRed2, prevGreen2, prevBlue2;
	@Unique
	public float red2, green2, blue2;
	@Unique
	public float varRed, varGreen, varBlue;
	@Unique
	public float[] vals = new float[3];
	@Unique
	private float rotationSpeed = SafeRandom(YACLConfig.CONFIG.instance().minRotationSpeed, YACLConfig.CONFIG.instance().maxRotationSpeed);
	//Maybe optimize this later?
	@Unique
	private Color mainCol = Color.RED;
	@Unique
	Color col2 = Color.RED;
//    @Unique
//    private Quaternionf rot = new Quaternionf();


	protected TotemParticleMixin(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
		super(world, x, y, z, spriteProvider, upwardsAcceleration);
	}

	//TODO: redirect constructor here
	@Inject(at = @At("TAIL"), method = "<init>")
	private void CustomTotemParticles$initParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider, CallbackInfo info) {
		if (YACLConfig.CONFIG.instance().modEnabled) {
			collidesWithWorld = YACLConfig.CONFIG.instance().useCollisions;
			if (YACLConfig.CONFIG.instance().useMovement) {
				velocityMultiplier = SafeRandom(YACLConfig.CONFIG.instance().minVelocityMultiplier, YACLConfig.CONFIG.instance().maxVelocityMultiplier);
				if (YACLConfig.CONFIG.instance().customVelocity) {
					this.velocityZ = SafeRandom(YACLConfig.CONFIG.instance().minZVelocity, YACLConfig.CONFIG.instance().maxZVelocity);
					this.velocityY = SafeRandom(YACLConfig.CONFIG.instance().minYVelocity, YACLConfig.CONFIG.instance().maxYVelocity);
					this.velocityX = SafeRandom(YACLConfig.CONFIG.instance().minXVelocity, YACLConfig.CONFIG.instance().maxXVelocity);
				}
				if (YACLConfig.CONFIG.instance().useGravity) {
					gravityStrength = SafeRandom(YACLConfig.CONFIG.instance().minUpwardsAccel, YACLConfig.CONFIG.instance().maxUpwardsAccel);
				}
				if (YACLConfig.CONFIG.instance().useRotation) {
					zRotation = SafeRandom(YACLConfig.CONFIG.instance().minStartRotation, YACLConfig.CONFIG.instance().maxStartRotation);
					lastZRotation = SafeRandom(YACLConfig.CONFIG.instance().minStartRotation, YACLConfig.CONFIG.instance().maxStartRotation);
				}
			}
			if (YACLConfig.CONFIG.instance().useScale) {
				scale *= SafeRandom(YACLConfig.CONFIG.instance().minScale, YACLConfig.CONFIG.instance().maxScale);
			}
			if (YACLConfig.CONFIG.instance().useAge) {
				maxAge = SafeRandom(YACLConfig.CONFIG.instance().minAge, YACLConfig.CONFIG.instance().maxAge);
			}
			if (YACLConfig.CONFIG.instance().useColor) {
				if (YACLConfig.CONFIG.instance().doRainbow) {
					if (YACLConfig.CONFIG.instance().startColorRainbow) {
						setColor(MathHelper.hsvToRgb((float) Math.random(), 1, 1));
					} else if (YACLConfig.CONFIG.instance().syncRainbow && YACLConfig.CONFIG.instance().rainbowOverTime) {
						setColor(getRainbowCol(0));
					}
				}
				if (!YACLConfig.CONFIG.instance().mainColorList.isEmpty()) {
					if (YACLConfig.CONFIG.instance().blendColors) {
						if (YACLConfig.CONFIG.instance().mainColorList.size() >= 2) {
							mainCol = new Color((float) SafeRandom(YACLConfig.CONFIG.instance().mainColorList.get(0).getRed(), YACLConfig.CONFIG.instance().mainColorList.get(1).getRed()) / 255.0F, (float) SafeRandom(YACLConfig.CONFIG.instance().mainColorList.get(0).getGreen(), YACLConfig.CONFIG.instance().mainColorList.get(1).getGreen()) / 255.0F, (float) SafeRandom(YACLConfig.CONFIG.instance().mainColorList.get(0).getBlue(), YACLConfig.CONFIG.instance().mainColorList.get(1).getBlue()) / 255.0f);
						} else {
							mainCol = (YACLConfig.CONFIG.instance().mainColorList.get(0));
						}
					} else {
						mainCol = (YACLConfig.CONFIG.instance().mainColorList.get(rand.nextInt(YACLConfig.CONFIG.instance().mainColorList.size())));
					}
					if (YACLConfig.CONFIG.instance().useGradients) {
						//divide twice to limit variation amount
						varRed = Utils.SafeRandom(-YACLConfig.CONFIG.instance().variationAmount.getRed(), YACLConfig.CONFIG.instance().variationAmount.getRed()) / 510F;
						varGreen = Utils.SafeRandom(-YACLConfig.CONFIG.instance().variationAmount.getGreen(), YACLConfig.CONFIG.instance().variationAmount.getGreen()) / 510F;
						varBlue = Utils.SafeRandom(-YACLConfig.CONFIG.instance().variationAmount.getBlue(), YACLConfig.CONFIG.instance().variationAmount.getBlue()) / 510F;
						col2 = new Color(Utils.clampToColor(mainCol.getRed() / 255F + varRed), Utils.clampToColor(mainCol.getGreen() / 255F + varGreen), Utils.clampToColor(mainCol.getBlue() / 255F + varBlue));
						red2 = red;
						green2 = green;
						blue2 = blue;
						prevRed2 = prevRed;
						prevGreen2 = prevGreen;
						prevBlue2 = prevBlue;
					} else {
						col2 = mainCol;
					}
				}
				if (YACLConfig.CONFIG.instance().doStartColor) {
					setColor(YACLConfig.CONFIG.instance().startColor.getRGB());
				} else {
					setColor(mainCol.getRGB());
				}
				if (YACLConfig.CONFIG.instance().useAlpha) {
					alpha = SafeRandom(YACLConfig.CONFIG.instance().minAlpha, YACLConfig.CONFIG.instance().maxAlpha);
				}
			}
//            rot.set(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());
			red2 = red;
			green2 = green;
			blue2 = blue;
			prevRed2 = prevRed;
			prevGreen2 = prevGreen;
			prevBlue2 = prevBlue;
			this.tick();
		}
	}

	//https://github.com/Splzh/ClearHitboxes/blob/main/src/main/java/splash/utils/ColorUtils.java !!
	@Unique
	private static int getRainbowCol(int delay) {
		return getRainbow(-((System.currentTimeMillis() + delay) % 10000L / 10000.0f) * YACLConfig.CONFIG.instance().rainbowSpeed);
	}

	@Unique
	private static int getRainbow(double percent) {
		double offset = Math.PI * 2 / 3;
		double pos = percent * (Math.PI * 2);
		float red = (float) ((Math.sin(pos) * 127) + 128);
		float green = (float) ((Math.sin(pos + offset) * 127) + 128);
		float blue = (float) ((Math.sin(pos + offset * 2) * 127) + 128);
		return new Color((int) (red), (int) (green), (int) (blue), 255).getRGB();
	}

	@Unique
	private void yeah(boolean a) {
		//yeah! 👍
		if (a) {
			setRainbowColor();
		} else {
			updateColor();
		}
	}

	@Override
	public void tick() {
		if (age++ >= maxAge || alpha <= 0 || scale <= 0 || (YACLConfig.CONFIG.instance().hideOnGround && onGround)) {
			markDead();
			return;
		}
		prevRed = red;
		prevGreen = green;
		prevBlue = blue;
		prevAlpha = alpha;
		if (YACLConfig.CONFIG.instance().useGradients) {
			prevRed2 = red2;
			prevGreen2 = green2;
			prevBlue2 = blue2;
		} else {
			red2 = red;
			green2 = green;
			blue2 = blue;
			prevRed2 = prevRed;
			prevGreen2 = prevGreen;
			prevBlue2 = prevBlue;
		}
		lastX = x;
		lastY = y;
		lastZ = z;
		lastZRotation = zRotation;
		prevScale = scale;
		velocityY -= 0.04 * (double) gravityStrength;
		move(velocityX, velocityY, velocityZ);
		if (ascending && y == lastY) {
			velocityX *= 1.1;
			velocityZ *= 1.1;
		}
		velocityX *= velocityMultiplier;
		velocityY *= velocityMultiplier;
		velocityZ *= velocityMultiplier;
		if (onGround) {
			velocityX *= 0.7f;
			velocityZ *= 0.7f;
		}
		setSprite(spriteProvider.getSprite(this.age, this.maxAge));
		if (YACLConfig.CONFIG.instance().modEnabled) {
			if (YACLConfig.CONFIG.instance().useColor) {
				if (YACLConfig.CONFIG.instance().doRainbow && YACLConfig.CONFIG.instance().rainbowOverTime) {
					//this probably isn't the best way to go about this, but it gets the job done
					switch (YACLConfig.CONFIG.instance().rainbowMode) {
						case END:
							yeah(age > (float) maxAge * YACLConfig.CONFIG.instance().fadeOutTime && YACLConfig.CONFIG.instance().doOutColor);
							break;
						case START:
							yeah(age < (float) maxAge * YACLConfig.CONFIG.instance().fadeToTime && YACLConfig.CONFIG.instance().doStartColor);
							break;
						case MAIN:
							yeah((!YACLConfig.CONFIG.instance().doStartColor || age > (float) maxAge * YACLConfig.CONFIG.instance().fadeToTime) && age < (float) maxAge * YACLConfig.CONFIG.instance().fadeOutTime);
							break;
						case UNTIL_END:
							yeah(age < (float) maxAge * YACLConfig.CONFIG.instance().fadeOutTime);
							break;
						case AFTER_START:
							yeah(!YACLConfig.CONFIG.instance().doStartColor || age > (float) maxAge * YACLConfig.CONFIG.instance().fadeToTime);
							break;
						//honestly, if you're using this mode and turn off start/end, you're the moron. Any weird behaviour here isn't my problem.
						case EXCLUDING_MAIN:
							yeah((age > (float) maxAge * YACLConfig.CONFIG.instance().fadeOutTime || age < (float) maxAge * YACLConfig.CONFIG.instance().fadeToTime));
							break;
						default:
							setRainbowColor();
							break;
					}
				} else {
					updateColor();
				}
				if (YACLConfig.CONFIG.instance().useAlpha) {
					if (YACLConfig.CONFIG.instance().fadeOnGround && onGround) {
						alpha = MathHelper.clamp(alpha + YACLConfig.CONFIG.instance().onGroundFade, 0, 1);
					}
					if (age > maxAge * YACLConfig.CONFIG.instance().alphaOutTime && YACLConfig.CONFIG.instance().loseAlpha) {
						alpha = MathHelper.clamp(alpha + YACLConfig.CONFIG.instance().alphaOutSpeed, 0, 1);
					}
				}
			}
			if (YACLConfig.CONFIG.instance().useMovement) {
				if (YACLConfig.CONFIG.instance().useGravity) {
					if (age > maxAge * YACLConfig.CONFIG.instance().changeGravityAtPercent && YACLConfig.CONFIG.instance().gravityOverTime) {
						gravityStrength += YACLConfig.CONFIG.instance().gravityOverTimeAmount;
					}
				}
				if (YACLConfig.CONFIG.instance().useRotation) {
					if (YACLConfig.CONFIG.instance().rotateOverTime) {
						if (!onGround || YACLConfig.CONFIG.instance().rotateOnGround) {
							zRotation += rotationSpeed;
						}
						if (age > maxAge * YACLConfig.CONFIG.instance().rotateAtPercent) {
							if (YACLConfig.CONFIG.instance().smartROT) {
								if (rotationSpeed != 0) {
									if (YACLConfig.CONFIG.instance().rotateOverTimeAmount > 0) {
										if (rotationSpeed > 0) {
											rotationSpeed += YACLConfig.CONFIG.instance().rotateOverTimeAmount;
										} else if (rotationSpeed < 0) {
											rotationSpeed -= YACLConfig.CONFIG.instance().rotateOverTimeAmount;
										}
									} else if (YACLConfig.CONFIG.instance().rotateOverTimeAmount < 0) {
										if (rotationSpeed > 0) {
											rotationSpeed = MathHelper.clamp(rotationSpeed + YACLConfig.CONFIG.instance().rotateOverTimeAmount, 0, 360);
										} else if (rotationSpeed < 0) {
											rotationSpeed = MathHelper.clamp(rotationSpeed - YACLConfig.CONFIG.instance().rotateOverTimeAmount, -360, 0);
										}
									}
								}
							} else {
								rotationSpeed += YACLConfig.CONFIG.instance().rotateOverTimeAmount;
							}
						}
					}
				}
			}
			if (YACLConfig.CONFIG.instance().useScale) {
				if (YACLConfig.CONFIG.instance().scaleOnGround && onGround) {
					scale = MathHelper.clamp(scale + YACLConfig.CONFIG.instance().onGroundScale, 0, 5);
				}
				if (YACLConfig.CONFIG.instance().scaleOverTime) {
					if (age > maxAge * YACLConfig.CONFIG.instance().scaleAtPercent) {
						scale = MathHelper.clamp(scale + YACLConfig.CONFIG.instance().scaleAmount, 0, 5);
					}
				}
			}
		}
	}

	@Unique
	private void setRainbowColor() {
		if (YACLConfig.CONFIG.instance().syncRainbow) {
			setColor(getRainbowCol(0));
			setSecondaryColor(getRainbowCol(YACLConfig.CONFIG.instance().rainbowGradientDelay));
		} else {
			Color.RGBtoHSB((int) (red * 255), (int) (green * 255), (int) (blue * 255), vals);
			vals[0] += ((YACLConfig.CONFIG.instance().rainbowSpeed) / 100F);
			setColor(Color.getHSBColor(vals[0], vals[1], vals[2]).getRGB());
			setSecondaryColor(Color.getHSBColor(vals[0] + (YACLConfig.CONFIG.instance().rainbowGradientDelay / 10F), vals[1], vals[2]).getRGB());
		}
	}

	@Unique
	private void setSecondaryColor(int rgbHex) {
		red2 = (float) ((rgbHex & 0xFF0000) >> 16) / 255.0f;
		green2 = (float) ((rgbHex & 0xFF00) >> 8) / 255.0f;
		blue2 = (float) ((rgbHex & 0xFF)) / 255.0f;
	}

	@Unique
	private void updateColor() {
		//this is so bad. idc
		if (age > (float) maxAge * YACLConfig.CONFIG.instance().fadeToTime && age < maxAge * 0.5F && YACLConfig.CONFIG.instance().doStartColor) {
			red = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, red, mainCol.getRed() / 255.0F);
			green = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, green, mainCol.getGreen() / 255.0F);
			blue = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, blue, mainCol.getBlue() / 255.0F);
			red2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, red2, col2.getRed() / 255.0F);
			green2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, green2, col2.getGreen() / 255.0F);
			blue2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, blue2, col2.getBlue() / 255.0F);

		} else if (age > (float) maxAge * YACLConfig.CONFIG.instance().fadeOutTime && YACLConfig.CONFIG.instance().doOutColor && age > maxAge * 0.5F) {
			red = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, red, YACLConfig.CONFIG.instance().outTargetColor.getRed() / 255.0f);
			green = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, green, YACLConfig.CONFIG.instance().outTargetColor.getGreen() / 255.0f);
			blue = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, blue, YACLConfig.CONFIG.instance().outTargetColor.getBlue() / 255.0f);
			red2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, red2, YACLConfig.CONFIG.instance().outTargetColor.getRed() / 255.0f);
			green2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, green2, YACLConfig.CONFIG.instance().outTargetColor.getGreen() / 255.0f);
			blue2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, blue2, YACLConfig.CONFIG.instance().outTargetColor.getBlue() / 255.0f);
		}
		else{
			red2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, red2, col2.getRed() / 255.0F);
			green2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, green2, col2.getGreen() / 255.0F);
			blue2 = MathHelper.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, blue2, col2.getBlue() / 255.0F);
		}
	}


	@Override
	public int getBrightness(float tint) {
		if (YACLConfig.CONFIG.instance().modEnabled && YACLConfig.CONFIG.instance().lightLevel != -1) {
			return YACLConfig.CONFIG.instance().lightLevel;
		} else {
			BlockPos blockPos = BlockPos.ofFloored(x, y, z);
			return world.isChunkLoaded(blockPos) ? WorldRenderer.getLightmapCoordinates(world, blockPos) : 0;
		}
	}

	@Override
	public float getSize(float tickDelta) {
		return net.minecraft.util.math.MathHelper.lerp(tickDelta, prevScale, scale);
	}

//	@Override
//	protected void method_60374(VertexConsumer vertexConsumer, Quaternionf quaternionf, float f, float g, float h, float tickDelta) {
//		float j = this.getSize(tickDelta);
//		float k = this.getMinU();
//		float l = this.getMaxU();
//		float m = this.getMinV();
//		float n = this.getMaxV();
//		int o = this.getBrightness(tickDelta);
//		this.yeah(vertexConsumer, quaternionf, f, g, h, 1.0F, -1.0F, j, l, n, o, MathHelper.lerp(tickDelta, prevRed, red), MathHelper.lerp(tickDelta, prevGreen, green), MathHelper.lerp(tickDelta, prevBlue, blue), MathHelper.lerp(tickDelta, prevAlpha, alpha));
//		this.yeah(vertexConsumer, quaternionf, f, g, h, 1.0F, 1.0F, j, l, m, o, MathHelper.lerp(0.5F, MathHelper.lerp(tickDelta, prevRed, red), MathHelper.lerp(tickDelta, prevRed2, red2)), MathHelper.lerp(0.5F, MathHelper.lerp(tickDelta, prevGreen, green), MathHelper.lerp(tickDelta, prevGreen2, green2)), MathHelper.lerp(0.5F, MathHelper.lerp(tickDelta, prevBlue, blue), MathHelper.lerp(tickDelta, prevBlue2, blue2)), MathHelper.lerp(tickDelta, prevAlpha, alpha));
//		this.yeah(vertexConsumer, quaternionf, f, g, h, -1.0F, 1.0F, j, k, m, o, MathHelper.lerp(tickDelta, prevRed2, red2), MathHelper.lerp(tickDelta, prevGreen2, green2), MathHelper.lerp(tickDelta, prevBlue2, blue2), MathHelper.lerp(tickDelta, prevAlpha, alpha));
//		this.yeah(vertexConsumer, quaternionf, f, g, h, -1.0F, -1.0F, j, k, n, o, MathHelper.lerp(0.5F, MathHelper.lerp(tickDelta, prevRed, red), MathHelper.lerp(tickDelta, prevRed2, red2)), MathHelper.lerp(0.5F, MathHelper.lerp(tickDelta, prevGreen, green), MathHelper.lerp(tickDelta, prevGreen2, green2)), MathHelper.lerp(0.5F, MathHelper.lerp(tickDelta, prevBlue, blue), MathHelper.lerp(tickDelta, prevBlue2, blue2)), MathHelper.lerp(tickDelta, prevAlpha, alpha));
//	}

	@Unique
	private void yeah(VertexConsumer vertexConsumer, Quaternionf quaternionf, float f, float uhh, float h, float i, float j, float k, float l, float m, int n, float r, float g, float b, float a) {
		Vector3f vector3f = (new Vector3f(i, j, 0.0F)).rotate(quaternionf).mul(k).add(f, uhh, h);
		vertexConsumer.vertex(vector3f.x(), vector3f.y(), vector3f.z()).texture(l, m).color(r, g, b, a).light(n);
	}
}