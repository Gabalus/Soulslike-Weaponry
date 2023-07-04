package net.soulsweaponry.client.model.entity.projectile;

import net.minecraft.util.Identifier;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.entity.projectile.MoonlightProjectile;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunlightProjectileSmallModel extends AnimatedGeoModel<MoonlightProjectile> {

    @Override
    public Identifier getAnimationFileLocation(MoonlightProjectile animatable) {
        return null;
    }

    @Override
    public Identifier getModelLocation(MoonlightProjectile object) {
        return new Identifier(SoulsWeaponry.ModId, "geo/moonlight_projectile.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MoonlightProjectile object) {
        return new Identifier(SoulsWeaponry.ModId, "textures/entity/sunlight_projectile_small.png");
    }
}