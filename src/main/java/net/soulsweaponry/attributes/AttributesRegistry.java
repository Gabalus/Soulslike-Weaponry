package net.soulsweaponry.attributes;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.soulsweaponry.SoulsWeaponry;
import org.spongepowered.asm.lib.Attribute;

public class AttributesRegistry {
    public static final DeferredRegister<EntityAttribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SoulsWeaponry.ModId);
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_DETONATE = ATTRIBUTES.register("asd",()-> new ClampedEntityAttribute("asd",0,0,1));
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

}