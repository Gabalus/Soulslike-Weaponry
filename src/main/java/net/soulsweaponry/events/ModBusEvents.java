package net.soulsweaponry.events;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.resource.PathPackResources;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.attributes.AttributesRegistry;
import net.soulsweaponry.entity.mobs.*;
import net.soulsweaponry.entity.projectile.NightsEdge;
import net.soulsweaponry.networking.ModMessages;
import net.soulsweaponry.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = SoulsWeaponry.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.WITHERED_DEMON.get(), WitheredDemon.createDemonAttributes().build());
        event.put(EntityRegistry.ACCURSED_LORD_BOSS.get(), AccursedLordBoss.createDemonAttributes().build());
        event.put(EntityRegistry.DRAUGR_BOSS.get(), DraugrBoss.createBossAttributes().build());
        event.put(EntityRegistry.NIGHT_SHADE.get(), NightShade.createBossAttributes().build());
        event.put(EntityRegistry.RETURNING_KNIGHT.get(), ReturningKnight.createBossAttributes().build());
        event.put(EntityRegistry.BIG_CHUNGUS.get(), BigChungus.createChungusAttributes().build());
        event.put(EntityRegistry.REMNANT.get(), Remnant.createRemnantAttributes().build());
        event.put(EntityRegistry.DARK_SORCERER.get(), DarkSorcerer.createSorcererAttributes().build());
        event.put(EntityRegistry.SOUL_REAPER_GHOST.get(), SoulReaperGhost.createRemnantAttributes().build());
        event.put(EntityRegistry.FORLORN.get(), Forlorn.createForlornAttributes().build());
        event.put(EntityRegistry.EVIL_FORLORN.get(), EvilForlorn.createForlornAttributes().build());
        event.put(EntityRegistry.SOULMASS.get(), Soulmass.createSoulmassAttributes().build());
        event.put(EntityRegistry.CHAOS_MONARCH.get(), ChaosMonarch.createBossAttributes().build());
        event.put(EntityRegistry.FREYR_SWORD_ENTITY_TYPE.get(), FreyrSwordEntity.createEntityAttributes().build());
        event.put(EntityRegistry.MOONKNIGHT.get(), Moonknight.createBossAttributes().build());
        event.put(EntityRegistry.FROST_GIANT.get(), FrostGiant.createGiantAttributes().build());
        event.put(EntityRegistry.RIME_SPECTRE.get(), RimeSpectre.createSpectreAttributes().build());
        event.put(EntityRegistry.DAY_STALKER.get(), DayStalker.createBossAttributes().build());
        event.put(EntityRegistry.NIGHT_PROWLER.get(), NightProwler.createBossAttributes().build());
        event.put(EntityRegistry.WARMTH_ENTITY.get(), WarmthEntity.createEntityAttributes().build());
        event.put(EntityRegistry.NIGHTS_EDGE.get(), NightsEdge.createAttributes().build());
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event){
        event.add(EntityType.PLAYER, AttributesRegistry.DRAUPNIR_DETONATE.get());
    }

    @SubscribeEvent
    public static void addBuiltinPack(AddPackFindersEvent event) {
        // NOTE: Maybe done differently in other versions, if so see https://github.com/MinecraftForge/MinecraftForge/blob/1.18.x/src/test/java/net/minecraftforge/debug/AddPackFinderEventTest.java
        if (event.getPackType() == ResourceType.CLIENT_RESOURCES) {
            var resourcePath = ModList.get().getModFileById(SoulsWeaponry.ModId).getFile().findResource("resourcepacks/2d_weapons");
            var pack = ResourcePackProfile.create("builtin/2d_weapons", Text.literal("2D Weapon Models"), false,
                    (path) -> new PathPackResources(path, true, resourcePath), ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.BUILTIN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));

            var pathLegacy2D = ModList.get().getModFileById(SoulsWeaponry.ModId).getFile().findResource("resourcepacks/legacy_2d");
            var packLegacy2D = ResourcePackProfile.create("builtin/legacy_2d", Text.literal("Legacy 2D Models"), false,
                    (path) -> new PathPackResources(path, true, pathLegacy2D), ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.BUILTIN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(packLegacy2D));

            var pathLegacy3D = ModList.get().getModFileById(SoulsWeaponry.ModId).getFile().findResource("resourcepacks/legacy_3d");
            var packLegacy3D = ResourcePackProfile.create("builtin/legacy_3d", Text.literal("Legacy 3D Models"), false,
                    (path) -> new PathPackResources(path, true, pathLegacy3D), ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.BUILTIN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(packLegacy3D));

            var pathFACompat = ModList.get().getModFileById(SoulsWeaponry.ModId).getFile().findResource("resourcepacks/fresh_animations_compat");
            var packFACompat = ResourcePackProfile.create("builtin/fresh_animations_compat", Text.literal("Fresh Animations Compat."), false,
                    (path) -> new PathPackResources(path, true, pathFACompat), ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.BUILTIN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(packFACompat));
        }
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModMessages.register();
        });
    }
}
