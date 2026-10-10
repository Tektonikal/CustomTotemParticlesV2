package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
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

import com.mojang.blaze3d.vertex.VertexConsumer;


@Mixin(TotemParticle.class)
public abstract class TotemParticleMixin extends SimpleAnimatedParticle {
	@Unique
	private final Quaternionf rotation = new Quaternionf();
	@Unique
	public float prevScale = quadSize;
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


	protected TotemParticleMixin(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, float upwardsAcceleration) {
		super(world, x, y, z, spriteProvider, upwardsAcceleration);
	}

	//TODO: redirect constructor here
	@Inject(at = @At("TAIL"), method = "<init>")
	private void CustomTotemParticles$initParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider, CallbackInfo info) {
		if (YACLConfig.CONFIG.instance().modEnabled) {
			hasPhysics = YACLConfig.CONFIG.instance().useCollisions;
			if (YACLConfig.CONFIG.instance().useMovement) {
				friction = SafeRandom(YACLConfig.CONFIG.instance().minVelocityMultiplier, YACLConfig.CONFIG.instance().maxVelocityMultiplier);
				if (YACLConfig.CONFIG.instance().customVelocity) {
					this.zd = SafeRandom(YACLConfig.CONFIG.instance().minZVelocity, YACLConfig.CONFIG.instance().maxZVelocity);
					this.yd = SafeRandom(YACLConfig.CONFIG.instance().minYVelocity, YACLConfig.CONFIG.instance().maxYVelocity);
					this.xd = SafeRandom(YACLConfig.CONFIG.instance().minXVelocity, YACLConfig.CONFIG.instance().maxXVelocity);
				}
				if (YACLConfig.CONFIG.instance().useGravity) {
					gravity = SafeRandom(YACLConfig.CONFIG.instance().minUpwardsAccel, YACLConfig.CONFIG.instance().maxUpwardsAccel);
				}
				if (YACLConfig.CONFIG.instance().useRotation) {
					roll = SafeRandom(YACLConfig.CONFIG.instance().minStartRotation, YACLConfig.CONFIG.instance().maxStartRotation);
					oRoll = SafeRandom(YACLConfig.CONFIG.instance().minStartRotation, YACLConfig.CONFIG.instance().maxStartRotation);
				}
			}
			if (YACLConfig.CONFIG.instance().useScale) {
				quadSize *= SafeRandom(YACLConfig.CONFIG.instance().minScale, YACLConfig.CONFIG.instance().maxScale);
			}
			if (YACLConfig.CONFIG.instance().useAge) {
				lifetime = SafeRandom(YACLConfig.CONFIG.instance().minAge, YACLConfig.CONFIG.instance().maxAge);
			}
			if (YACLConfig.CONFIG.instance().useColor) {
				if (YACLConfig.CONFIG.instance().doRainbow) {
					if (YACLConfig.CONFIG.instance().startColorRainbow) {
						setColor(Mth.hsvToRgb((float) Math.random(), 1, 1));
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
						red2 = rCol;
						green2 = gCol;
						blue2 = bCol;
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
			red2 = rCol;
			green2 = gCol;
			blue2 = bCol;
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
		if (!YACLConfig.CONFIG.instance().modEnabled) {
			super.tick();
			return;
		}
		if (age++ >= lifetime || alpha <= 0 || quadSize <= 0 || (YACLConfig.CONFIG.instance().hideOnGround && onGround)) {
			remove();
			return;
		}
		prevRed = rCol;
		prevGreen = gCol;
		prevBlue = bCol;
		prevAlpha = alpha;
		if (YACLConfig.CONFIG.instance().useGradients) {
			prevRed2 = red2;
			prevGreen2 = green2;
			prevBlue2 = blue2;
		} else {
			red2 = rCol;
			green2 = gCol;
			blue2 = bCol;
			prevRed2 = prevRed;
			prevGreen2 = prevGreen;
			prevBlue2 = prevBlue;
		}
		xo = x;
		yo = y;
		zo = z;
		oRoll = roll;
		prevScale = quadSize;
		yd -= 0.04 * (double) gravity;
		move(xd, yd, zd);
		if (speedUpWhenYMotionIsBlocked && y == yo) {
			xd *= 1.1;
			zd *= 1.1;
		}
		xd *= friction;
		yd *= friction;
		zd *= friction;
		if (onGround) {
			xd *= 0.7f;
			zd *= 0.7f;
		}
		setSprite(sprites.get(this.age, this.lifetime));
		if (YACLConfig.CONFIG.instance().modEnabled) {
			if (YACLConfig.CONFIG.instance().useColor) {
				if (YACLConfig.CONFIG.instance().doRainbow && YACLConfig.CONFIG.instance().rainbowOverTime) {
					//this probably isn't the best way to go about this, but it gets the job done
					switch (YACLConfig.CONFIG.instance().rainbowMode) {
						case END:
							yeah(age > (float) lifetime * YACLConfig.CONFIG.instance().fadeOutTime && YACLConfig.CONFIG.instance().doOutColor);
							break;
						case START:
							yeah(age < (float) lifetime * YACLConfig.CONFIG.instance().fadeToTime && YACLConfig.CONFIG.instance().doStartColor);
							break;
						case MAIN:
							yeah((!YACLConfig.CONFIG.instance().doStartColor || age > (float) lifetime * YACLConfig.CONFIG.instance().fadeToTime) && age < (float) lifetime * YACLConfig.CONFIG.instance().fadeOutTime);
							break;
						case UNTIL_END:
							yeah(age < (float) lifetime * YACLConfig.CONFIG.instance().fadeOutTime);
							break;
						case AFTER_START:
							yeah(!YACLConfig.CONFIG.instance().doStartColor || age > (float) lifetime * YACLConfig.CONFIG.instance().fadeToTime);
							break;
						//honestly, if you're using this mode and turn off start/end, you're the moron. Any weird behaviour here isn't my problem.
						case EXCLUDING_MAIN:
							yeah((age > (float) lifetime * YACLConfig.CONFIG.instance().fadeOutTime || age < (float) lifetime * YACLConfig.CONFIG.instance().fadeToTime));
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
						alpha = Mth.clamp(alpha + YACLConfig.CONFIG.instance().onGroundFade, 0, 1);
					}
					if (age > lifetime * YACLConfig.CONFIG.instance().alphaOutTime && YACLConfig.CONFIG.instance().loseAlpha) {
						alpha = Mth.clamp(alpha + YACLConfig.CONFIG.instance().alphaOutSpeed, 0, 1);
					}
				}
			}
			if (YACLConfig.CONFIG.instance().useMovement) {
				if (YACLConfig.CONFIG.instance().useGravity) {
					if (age > lifetime * YACLConfig.CONFIG.instance().changeGravityAtPercent && YACLConfig.CONFIG.instance().gravityOverTime) {
						gravity += YACLConfig.CONFIG.instance().gravityOverTimeAmount;
					}
				}
				if (YACLConfig.CONFIG.instance().useRotation) {
					if (YACLConfig.CONFIG.instance().rotateOverTime) {
						if (!onGround || YACLConfig.CONFIG.instance().rotateOnGround) {
							roll += rotationSpeed;
						}
						if (age > lifetime * YACLConfig.CONFIG.instance().rotateAtPercent) {
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
											rotationSpeed = Mth.clamp(rotationSpeed + YACLConfig.CONFIG.instance().rotateOverTimeAmount, 0, 360);
										} else if (rotationSpeed < 0) {
											rotationSpeed = Mth.clamp(rotationSpeed - YACLConfig.CONFIG.instance().rotateOverTimeAmount, -360, 0);
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
					quadSize = Mth.clamp(quadSize + YACLConfig.CONFIG.instance().onGroundScale, 0, 5);
				}
				if (YACLConfig.CONFIG.instance().scaleOverTime) {
					if (age > lifetime * YACLConfig.CONFIG.instance().scaleAtPercent) {
						quadSize = Mth.clamp(quadSize + YACLConfig.CONFIG.instance().scaleAmount, 0, 5);
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
			Color.RGBtoHSB((int) (rCol * 255), (int) (gCol * 255), (int) (bCol * 255), vals);
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
		if (age > (float) lifetime * YACLConfig.CONFIG.instance().fadeToTime && age < lifetime * 0.5F && YACLConfig.CONFIG.instance().doStartColor) {
			rCol = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, rCol, mainCol.getRed() / 255.0F);
			gCol = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, gCol, mainCol.getGreen() / 255.0F);
			bCol = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, bCol, mainCol.getBlue() / 255.0F);
			red2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, red2, col2.getRed() / 255.0F);
			green2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, green2, col2.getGreen() / 255.0F);
			blue2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, blue2, col2.getBlue() / 255.0F);

		} else if (age > (float) lifetime * YACLConfig.CONFIG.instance().fadeOutTime && YACLConfig.CONFIG.instance().doOutColor && age > lifetime * 0.5F) {
			rCol = Mth.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, rCol, YACLConfig.CONFIG.instance().outTargetColor.getRed() / 255.0f);
			gCol = Mth.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, gCol, YACLConfig.CONFIG.instance().outTargetColor.getGreen() / 255.0f);
			bCol = Mth.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, bCol, YACLConfig.CONFIG.instance().outTargetColor.getBlue() / 255.0f);
			red2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, red2, YACLConfig.CONFIG.instance().outTargetColor.getRed() / 255.0f);
			green2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, green2, YACLConfig.CONFIG.instance().outTargetColor.getGreen() / 255.0f);
			blue2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeOutSpeed, blue2, YACLConfig.CONFIG.instance().outTargetColor.getBlue() / 255.0f);
		}
		else{
			red2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, red2, col2.getRed() / 255.0F);
			green2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, green2, col2.getGreen() / 255.0F);
			blue2 = Mth.lerp(YACLConfig.CONFIG.instance().fadeToSpeed, blue2, col2.getBlue() / 255.0F);
		}
	}


	@Override
	public int getLightCoords(float tint) {
		if (YACLConfig.CONFIG.instance().modEnabled && YACLConfig.CONFIG.instance().lightLevel != -1) {
			return YACLConfig.CONFIG.instance().lightLevel;
		} else {
			BlockPos blockPos = BlockPos.containing(x, y, z);
			return level.hasChunkAt(blockPos) ? LevelRenderer.getLightCoords(level, blockPos) : 0;
		}
	}

	@Override
	public float getQuadSize(float tickDelta) {
		if (!YACLConfig.CONFIG.instance().modEnabled) {
			return super.getQuadSize(tickDelta);
		}
		return net.minecraft.util.Mth.lerp(tickDelta, prevScale, quadSize);
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
		vertexConsumer.addVertex(vector3f.x(), vector3f.y(), vector3f.z()).setUv(l, m).setColor(r, g, b, a).setLight(n);
	}
}