package net.soulsweaponry.config;

public class ConfigConstructor extends MidnightConfig {

    @Entry public static boolean disable_all_legendary_recipes = false;
    @Entry public static boolean disable_gun_recipes = false;
    @Entry public static boolean disable_all_enchantments = false;

    @Entry public static boolean disable_enchantment_fast_hands = false;
    @Entry public static boolean disable_enchantment_posture_breaker = false;
    @Entry public static boolean disable_enchantment_stagger = false;
    
    @Entry public static boolean disable_recipe_bloodthirster = false;
    @Entry public static boolean disable_recipe_comet_spear = false;
    @Entry public static boolean disable_recipe_darkin_blade = false;
    @Entry public static boolean disable_recipe_dawnbreaker = false;
    @Entry public static boolean disable_recipe_heap_of_raw_iron = false;
    @Entry public static boolean disable_recipe_dragonslayer_swordspear = false;
    @Entry public static boolean disable_recipe_dragon_staff = false;
    @Entry public static boolean disable_recipe_draugr = false;
    @Entry public static boolean disable_recipe_galeforce = false;
    @Entry public static boolean disable_recipe_rageblade = false;
    @Entry public static boolean disable_recipe_lich_bane = false;
    @Entry public static boolean disable_recipe_moonlight_greatsword = false;
    @Entry public static boolean disable_recipe_moonlight_shortsword = false;
    @Entry public static boolean disable_recipe_nightfall = false;
    @Entry public static boolean disable_recipe_soul_reaper = false;
    @Entry public static boolean disable_recipe_whirligig_sawblade = false;
    @Entry public static boolean disable_recipe_withered_wabbajack = false;
    @Entry public static boolean disable_recipe_leviathan_axe = false;
    @Entry public static boolean disable_recipe_skofnung = false;
    @Entry public static boolean disable_recipe_pure_moonlight_greatsword = false;
    //@Entry public static boolean disable_recipe_pure_moonlight_shortsword = false;
    @Entry public static boolean disable_recipe_mjolnir = false;
    @Entry public static boolean disable_recipe_sword_of_freyr = false;
    @Entry public static boolean disable_recipe_sting = false;
    @Entry public static boolean disable_recipe_featherlight = false;
    @Entry public static boolean disable_recipe_crucible_sword = false;
    @Entry public static boolean disable_recipe_darkin_scythe = false;
    @Entry public static boolean disable_recipe_kirkhammer = false;
    @Entry public static boolean disable_recipe_ludwigs_holy_blade = false;

    @Entry(min=0,max=100) public static int withered_demon_spawnrate = 20;
    @Entry(min=0,max=100) public static int moderatly_sized_chungus_spawnrate = 20;
    @Entry(min=0,max=100) public static int evil_forlorn_spawnrate = 10;

    @Entry public static boolean can_withered_demon_spawn = true;
    @Entry public static boolean can_moderatly_sized_chungus_spawn = true;
    @Entry public static boolean can_evil_forlorn_spawn = true;
    
    @Entry public static int bloodthirster_damage = 8;
    @Entry public static boolean bloodthirster_overshields = true;
    @Entry public static int lifesteal_item_base_healing = 2;
    @Entry public static boolean lifesteal_item_heal_scales = true;
    @Entry public static int lifesteal_item_cooldown = 60;
    @Entry public static int bluemoon_greatsword_damage = 8;
    @Entry public static int bluemoon_shortsword_damage = 7;
    @Entry public static int comet_spear_damage = 7;
    @Entry public static float comet_spear_projectile_damage = 8.0f;
    @Entry public static float comet_spear_ability_damage = 10f;
    @Entry public static int comet_spear_skyfall_ability_cooldown = 400;
    @Entry public static int comet_spear_throw_ability_cooldown = 25;
    @Entry public static int crucible_sword_normal_damage = 9;
    @Entry public static int crucible_sword_empowered_damage = 30;
    @Entry public static int crucible_sword_empowered_cooldown = 300;
    @Entry public static int darkin_blade_damage = 11;
    @Entry public static float darkin_blade_ability_damage = 12f;
    @Entry public static int darkin_blade_ability_cooldown = 150;
    @Entry public static int darkin_scythe_damage = 9;
    @Entry public static int darkin_scythe_bonus_damage = 3;
    @Entry public static int darkin_scythe_max_souls = 100;
    @Entry public static float darkin_scythe_prime_ability_damage = 20f;
    @Entry(min=0,max=100) public static float darkin_scythe_prime_ability_percent_health_damage = 10f;
    @Entry public static int darkin_scythe_prime_ability_cooldown = 400;
    @Entry public static float darkin_scythe_prime_heal_modifier = 0.25f;
    @Entry public static int darkin_scythe_prime_ticks_before_dismount = 80;
    @Entry public static int dawnbreaker_damage = 8;
    @Entry public static float dawnbreaker_ability_damage = 10.0f;
    @Entry(min=0,max=1) public static double dawnbreaker_ability_chance_modifier = 0.0D;
    @Entry public static boolean dawnbreaker_affect_all_entities = false;
    @Entry public static int heap_of_raw_iron_damage = 10;
    @Entry public static int heap_of_raw_iron_cooldown = 200;
    @Entry public static int dragonslayer_swordspear_damage = 7;
    @Entry public static float dragonslayer_swordspear_projectile_damage = 7.0f;
    @Entry public static float dragonslayer_swordspear_ability_damage = 6.0f;
    @Entry public static int dragonslayer_swordspear_lightning_amount = 1;
    @Entry public static int dragonslayer_swordspear_throw_cooldown = 100;
    @Entry public static int dragonslayer_swordspear_ability_cooldown = 300;
    @Entry public static int dragon_staff_damage = 8;
    @Entry public static int dragon_staff_aura_strength = 1;
    @Entry public static int dragon_staff_cooldown = 100;
    @Entry public static int dragon_staff_use_time = 100;
    @Entry public static int draugr_damage_at_night = 10;
    @Entry public static int forlorn_scythe_damage = 11;
    @Entry public static int featherlight_damage = 8;
    @Entry public static float featherlight_attack_speed = 1.6f;
    @Entry public static float galeforce_bonus_damage = 1.0f;
    @Entry public static int galeforce_dash_cooldown = 50;
    @Entry public static int rageblade_damage = 7;
    @Entry public static boolean rageblade_haste_cap = true;
    @Entry public static int kirkhammer_damage = 9;
    @Entry public static int kirkhammer_silver_sword_damage = 6;
    @Entry public static int leviathan_axe_damage = 10;
    @Entry public static float leviathan_axe_projectile_damage = 7f;
    @Entry public static int leviathan_axe_return_speed = 5;
    @Entry public static int lich_bane_damage = 7;
    @Entry public static float lich_bane_bonus_magic_damage = 2f;
    @Entry public static int ludwigs_holy_greatsword = 8;
    @Entry public static float righteous_undead_bonus_damage = 2f;
    @Entry public static int mjolnir_damage = 9;
    @Entry public static int mjolnir_rain_bonus_damage = 2;
    @Entry public static float mjolnir_smash_damage = 8f;
    @Entry public static float mjolnir_projectile_damage = 7f;
    @Entry public static int mjolnir_lightning_smash_cooldown = 200;
    @Entry public static int mjolnir_riptide_cooldown = 300;
    @Entry public static int mjolnir_return_speed = 5;
    @Entry public static int moonlight_greatsword_damage = 9;
    @Entry public static float moonlight_greatsword_projectile_damage = 8.0f;
    @Entry public static int pure_moonlight_greatsword_damage = 12;
    //@Entry public static int pure_moonlight_shortsword_damage = 8;
    @Entry public static int moonlight_shortsword_damage = 8;
    @Entry public static float moonlight_shortsword_projectile_damage = 3.0f;
    @Entry public static int moonlight_shortsword_projectile_cooldown = 13;
    @Entry public static boolean moonlight_shortsword_enable_right_click = false;
    @Entry public static boolean moonlight_shortsword_enable_left_click = true;
    @Entry public static int nightfall_damage = 11;
    @Entry public static float nightfall_ability_damage = 15.0f;
    @Entry public static int nightfall_ability_shield_power = 2;
    @Entry public static int nightfall_shield_cooldown = 500;
    @Entry public static int nightfall_smash_cooldown = 300;
    @Entry(min=0, max=1) public static double nightfall_summon_chance = 0.3D;
    @Entry public static int shadow_assassin_scythe_shadow_step_bonus_damage = 2;
    @Entry public static int shadow_assassin_scythe_shadow_step_ticks = 60;
    @Entry public static int shadow_assassin_scythe_shadow_step_cooldown = 100;
    @Entry public static float shadow_assassin_scythe_ability_damage = 40f;
    @Entry public static int shadow_assassin_scythe_ability_cooldown = 300;
    @Entry public static int shadow_assassin_scythe_ticks_before_dismount = 120;
    @Entry public static int skofnung_damage = 8;
    @Entry public static int skofnung_bonus_damage = 2;
    @Entry public static int skofnung_disable_heal_duration = 100;
    @Entry public static int skofnung_stone_additional_empowered_strikes = 8;
    @Entry public static int soulreaper_damage = 11;
    @Entry public static int sting_damage = 6;
    @Entry public static float sting_bonus_arthropod_damage = 4f;
    @Entry public static int sword_of_freyr_damage = 7;
    //@Entry public static float sword_of_freyr_animation_speed = 1.0f;
    @Entry public static int whirligig_sawblade_damage = 8;
    @Entry public static float whirligig_sawblade_ability_damage = 5.0f;
    @Entry public static int whirligig_sawblade_cooldown = 100;
    @Entry public static int whirligig_sawblade_use_time = 100;
    @Entry public static int withered_wabbajack_damage = 8;

    @Entry public static boolean can_projectiles_apply_posture_break = true;
    @Entry public static int blunderbuss_damage = 4;
    @Entry public static int blunderbuss_cooldown = 80;
    @Entry public static int gatling_gun_damage = 1;
    @Entry public static int gatling_gun_startup_time = 60;
    @Entry public static int hunter_cannon_damage = 10;
    @Entry public static int hunter_cannon_cooldown = 300;
    @Entry public static int hunter_pistol_damage = 2;
    @Entry public static int hunter_pistol_cooldown = 50;
    
    @Entry public static double decaying_king_health = 500D;
    @Entry(min=0) public static int decaying_king_attack_cooldown_ticks = 20;
    @Entry(min=0) public static int decaying_king_special_cooldown_ticks = 60;
    @Entry(min=0) public static float decaying_king_damage_modifier = 1f;

    @Entry public static double returning_knight_health = 400D;
    @Entry(min=0) public static int returning_knight_attack_cooldown_ticks = 40;
    @Entry(min=0) public static int returning_knight_special_cooldown_ticks = 80;
    @Entry(min=0) public static float returning_knight_damage_modifier = 1f;

    @Entry public static double old_champions_remains_health = 200D;
    @Entry(min=0) public static int old_champions_remains_attack_cooldown_ticks = 10;
    @Entry(min=0) public static float old_champions_remains_damage_modifier = 1f;
    @Entry(min=1) public static double old_champions_remains_generic_damage = 10D;
    @Entry public static double frenzied_shade_health = 100D;
    @Entry(min=1) public static double frenzied_shade_generic_damage = 6D;

    @Entry public static double chaos_monarch_health = 400D;
    @Entry(min=0) public static int chaos_monarch_attack_cooldown_ticks = 20;
    @Entry(min=0) public static float chaos_monarch_damage_modifier = 1f;

    @Entry public static double fallen_icon_health = 500D;
    @Entry(min=0) public static int fallen_icon_attack_cooldown_ticks_phase_1 = 30;
    @Entry(min=0) public static int fallen_icon_attack_cooldown_ticks_phase_2 = 0;
    @Entry(min=0) public static int fallen_icon_special_cooldown_ticks = 50;
    @Entry(min=0) public static float fallen_icon_damage_modifier = 1f;
}
