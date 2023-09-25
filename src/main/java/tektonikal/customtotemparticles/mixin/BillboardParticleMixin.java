package tektonikal.customtotemparticles.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tektonikal.customtotemparticles.config.YACLConfig;

import static tektonikal.customtotemparticles.MathHelper.SafeRandom;

@Mixin(BillboardParticle.class)
public abstract class BillboardParticleMixin extends Particle {
    @Shadow
    protected abstract float getMinU();

    @Shadow
    public abstract float getSize(float tickDelta);

    @Shadow
    protected abstract float getMaxV();

    @Shadow
    protected abstract float getMaxU();

    @Shadow
    protected abstract float getMinV();

    private final float rot = SafeRandom(YACLConfig.INSTANCE.getConfig().minRotationSpeed, YACLConfig.INSTANCE.getConfig().maxRotationSpeed);

    public BillboardParticleMixin(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
    }

    /**
     * @author Tektonikal
     * @reason BECAUSE I SAID SO!!!
     */
    @Overwrite
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Quaternionf quaternionf = new Quaternionf(camera.getRotation());
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        if (this.getClass().equals(TotemParticle.class) && YACLConfig.INSTANCE.getConfig().modEnabled) {
            if (YACLConfig.INSTANCE.getConfig().useRotation) {
                quaternionf.rotateZ(Math.abs(this.angle));
                if (!MinecraftClient.getInstance().isPaused()) {
                    if (this.onGround && !YACLConfig.INSTANCE.getConfig().rotateOnGround) {
                        this.angle -= rot / MinecraftClient.getInstance().getCurrentFps();
                    }
                    this.angle += rot / MinecraftClient.getInstance().getCurrentFps();
                }
            }
        } else {
            if (this.angle == 0.0f) {
                quaternionf = camera.getRotation();
            } else {
                quaternionf = new Quaternionf(camera.getRotation());
                quaternionf.rotateZ(MathHelper.lerp(tickDelta, this.prevAngle, this.angle));
            }
        }
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float i = this.getSize(tickDelta);
        for (int j = 0; j < 4; ++j) {
            Vector3f vector3f = vector3fs[j];
            vector3f.rotate(quaternionf);
            vector3f.mul(i);
            vector3f.add(f, g, h);
        }
        float k = this.getMinU();
        float l = this.getMaxU();
        float m = this.getMinV();
        float n = this.getMaxV();
        int o = this.getBrightness(tickDelta);
        vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(l, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(l, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(k, m).color(this.red, this.green, this.blue, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(k, n).color(this.red, this.green, this.blue, this.alpha).light(o).next();
    }
}
