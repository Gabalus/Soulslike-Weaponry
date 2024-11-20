package net.soulsweaponry.client.model.entity.projectile;

import net.minecraft.util.Identifier;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.entity.projectile.noclip.MoonveilWave;
import software.bernie.geckolib.model.GeoModel;

public class MoonveilWaveModel extends GeoModel<MoonveilWave> {

    @Override
    public Identifier getAnimationResource(MoonveilWave animatable) {
        return null;
    }

    @Override
    public Identifier getModelResource(MoonveilWave object) {
        return new Identifier(SoulsWeaponry.ModId, "geo/moonlight_projectile_big.geo.json");
    }

    @Override
    public Identifier getTextureResource(MoonveilWave object) {
        return new Identifier(SoulsWeaponry.ModId, "textures/entity/moonveil_wave.png");
    }
}
