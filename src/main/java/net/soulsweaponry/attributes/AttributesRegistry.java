package net.soulsweaponry.attributes;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.config.ConfigConstructor;
import org.spongepowered.asm.lib.Attribute;

public class AttributesRegistry {
    public static final DeferredRegister<EntityAttribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, SoulsWeaponry.ModId);
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_DETONATE = ATTRIBUTES.register("draupnir/detonate",()-> new ClampedEntityAttribute("draupnir_detonate",0,0,1));
    public static final RegistryObject<ClampedEntityAttribute> MAX_DRAUPNIR_SPEARS = ATTRIBUTES.register("draupnir/max_spears",()-> new ClampedEntityAttribute("max_draupnir_spears",2,1,99));
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_STOMP = ATTRIBUTES.register("draupnir/stomp",()-> new ClampedEntityAttribute("draupnir_stomp",0,0,1));
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_SUMMON = ATTRIBUTES.register("draupnir/summon",()-> new ClampedEntityAttribute("draupnir_summon",0,0,1));
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_SUMMON_COOLDOWN = ATTRIBUTES.register("draupnir/summon_cooldown",()-> new ClampedEntityAttribute("draupnir_summon_cooldown", ConfigConstructor.draupnir_spear_summon_spears_cooldown,0,999));
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_DETONATE_COOLDOWN = ATTRIBUTES.register("draupnir/detonate_cooldown",()-> new ClampedEntityAttribute("draupnir_detonate_cooldown", ConfigConstructor.draupnir_spear_detonate_cooldown,0,999));
    public static final RegistryObject<ClampedEntityAttribute> DRAUPNIR_THROW_COOLDOWN = ATTRIBUTES.register("draupnir/throw_cooldown",()-> new ClampedEntityAttribute("draupnir_throw_cooldown", ConfigConstructor.draupnir_spear_throw_cooldown,0,999));
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

}