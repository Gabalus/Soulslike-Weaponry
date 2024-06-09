package net.soulsweaponry.events;

import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.text.LiteralText;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathResourcePack;
import net.soulsweaponry.SoulsWeaponry;
import net.soulsweaponry.entity.mobs.*;
import net.soulsweaponry.entity.projectile.NightsEdge;
import net.soulsweaponry.registry.EntityRegistry;

import java.io.IOException;
import java.nio.file.Path;

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
    public static void addBuiltinPack(AddPackFindersEvent event) {
        // NOTE: Maybe done differently in other versions, if so see https://github.com/MinecraftForge/MinecraftForge/blob/1.18.x/src/test/java/net/minecraftforge/debug/AddPackFinderEventTest.java
        try {
            if (event.getPackType() == ResourceType.CLIENT_RESOURCES) {
                IModFileInfo info = ModList.get().getModFileById(SoulsWeaponry.ModId);
                if (info == null) {
                    return;
                }
                IModFile file = info.getFile();
                Path resourcePath = file.findResource("resourcepacks/2d_weapons");
                PathResourcePack pack = new PathResourcePack(file.getFileName() + ":" + resourcePath, resourcePath);
                PackResourceMetadata metadata = pack.parseMetadata(PackResourceMetadata.READER);
                event.addRepositorySource((consumer, constructor) -> {
                    consumer.accept(constructor.create("builtin/2d_weapons", new LiteralText("2D Weapon Models"), false,
                            () -> pack, metadata, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.PACK_SOURCE_BUILTIN, false));
                });
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
