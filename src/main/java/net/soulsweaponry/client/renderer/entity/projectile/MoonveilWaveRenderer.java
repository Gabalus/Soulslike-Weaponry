package net.soulsweaponry.client.renderer.entity.projectile;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.soulsweaponry.client.model.entity.projectile.MoonveilWaveModel;
import net.soulsweaponry.entity.projectile.noclip.MoonveilWave;

public class MoonveilWaveRenderer extends GeoProjectileRenderer<MoonveilWave> {

    public MoonveilWaveRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MoonveilWaveModel());
    }

    @Override
    public RenderLayer getRenderType(MoonveilWave animatable, Identifier texture, VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(this.getTexture(animatable));
    }

    @Override
    public void render(MoonveilWave entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        poseStack.scale(3f, 1.5f, 3f);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
