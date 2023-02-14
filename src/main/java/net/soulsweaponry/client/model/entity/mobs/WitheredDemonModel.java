package net.soulsweaponry.client.model.entity.mobs;

import net.minecraft.util.Identifier;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.entity.mobs.WitheredDemon;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class WitheredDemonModel extends AnimatedGeoModel<WitheredDemon>{

    @Override
    public Identifier getModelLocation(WitheredDemon object)
    {
        return new Identifier(SoulsWeaponry.ModId, "geo/withered_demon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WitheredDemon object)
    {
        return new Identifier(SoulsWeaponry.ModId, "textures/entity/withered_demon.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WitheredDemon object)
    {
        return new Identifier(SoulsWeaponry.ModId, "animations/withered_demon.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setCustomAnimations(WitheredDemon animatable, int instanceId, AnimationEvent customPredicate) {
        super.setCustomAnimations(animatable, instanceId, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("h_head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
    }
}
