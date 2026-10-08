#!/usr/bin/env python3
"""Stage 6 data: the Ice Cream Dimension (dimension type, terrain, Ice Cream Plains biome, pinnacles, chocolate lakes),
its portal, the Ice Cream Nest, the new creatures, the Ultimate Ice Cream and the Ice Cream Amulet.

Called by tools/gen_resources.py after gen_stage5 (files written here replace earlier files of the same name, and
tags are merged). The terrain shape reuses the vanilla Overworld noise router (tools/data/overworld_noise_router.json,
copied from the game's own overworld noise settings) with ice cream blocks instead of stone and melted chocolate
instead of water.
"""
import json, os
import gen_resources as G
from gen_resources import NS, A, D, write, ns, flat_item, item_def, simple_state, state, tag, shaped, adv, has, drop_self

W = f"{D}/worldgen"
HERE = os.path.dirname(os.path.abspath(__file__))
FLAVOURS = ("chocolate", "vanilla", "strawberry", "mint")
# The zombie and the living ice cream are one entity type each; the flavour is a variant (IceCreamFlavor).
EGGS = ["ice_cream_zombie", "ice_cream_beast", "ice_cream_gargoyle", "living_ice_cream", "angry_ice_cream_cone", "ice_cream_dragon"]
BLOCKS = [f"{f}_ice_cream_block" for f in ("vanilla", "chocolate", "strawberry", "mint")]

# =================================================================== assets
def gen_assets():
    for b in BLOCKS:
        G.block_cube(b)
    # Portal: a thin translucent pane like the Nether portal (vanilla's nether_portal_ew/ns models, our texture).
    for axis, parent in (("ew", "nether_portal_ew"), ("ns", "nether_portal_ns")):
        write(f"{A}/models/block/ice_cream_portal_{axis}.json", {"parent": f"minecraft:block/{parent}", "render_type": "minecraft:translucent",
              "textures": {"particle": f"{NS}:block/ice_cream_portal", "portal": f"{NS}:block/ice_cream_portal"}})
    write(f"{A}/blockstates/ice_cream_portal.json", {"variants": {
        "axis=x": {"model": f"{NS}:block/ice_cream_portal_ns"}, "axis=z": {"model": f"{NS}:block/ice_cream_portal_ew"}}})
    # Egg: the vanilla dragon egg shape with an ice cream shell.
    write(f"{A}/models/block/ice_cream_dragon_egg.json", {"parent": "minecraft:block/dragon_egg", "textures": {
        "particle": f"{NS}:block/ice_cream_dragon_egg", "all": f"{NS}:block/ice_cream_dragon_egg"}})
    write(f"{A}/blockstates/ice_cream_dragon_egg.json", {"variants": {
        "summoning=false": {"model": f"{NS}:block/ice_cream_dragon_egg"}, "summoning=true": {"model": f"{NS}:block/ice_cream_dragon_egg"}}})
    item_def("ice_cream_dragon_egg", f"{NS}:block/ice_cream_dragon_egg")
    for i in ("mint_ice_cream", "ultimate_ice_cream", "ice_cream_amulet"):
        flat_item(i)
    for e in EGGS:
        flat_item(f"{e}_spawn_egg")

def gen_sounds():
    path = f"{A}/sounds.json"
    sounds = json.load(open(path, encoding="utf-8"))
    def ev(names, subtitle, pitch=1.0, volume=1.0):
        return {"subtitle": f"subtitles.{NS}.{subtitle}", "sounds": [{"name": s, "pitch": pitch, "volume": volume} for s in names]}
    def mob(name, ambient, hurt, death, pitch):
        sounds[f"entity.{name}.ambient"] = ev(ambient, f"{name}.ambient", pitch)
        sounds[f"entity.{name}.hurt"] = ev(hurt, f"{name}.hurt", pitch)
        sounds[f"entity.{name}.death"] = ev(death, f"{name}.death", pitch)
    # Provisional: re-pitched vanilla sounds until original recordings exist.
    mob("ice_cream_zombie", ["minecraft:mob/zombie/say1", "minecraft:mob/zombie/say2", "minecraft:mob/zombie/say3"],
        ["minecraft:mob/zombie/hurt1", "minecraft:mob/zombie/hurt2"], ["minecraft:mob/zombie/death"], 1.25)
    mob("ice_cream_beast", ["minecraft:mob/ravager/idle1", "minecraft:mob/ravager/idle2"], ["minecraft:mob/ravager/hurt1", "minecraft:mob/ravager/hurt2"],
        ["minecraft:mob/ravager/death1"], 1.2)
    mob("ice_cream_gargoyle", ["minecraft:mob/phantom/idle1", "minecraft:mob/phantom/idle2"], ["minecraft:mob/phantom/hurt1", "minecraft:mob/phantom/hurt2"],
        ["minecraft:mob/phantom/death1"], 1.4)
    mob("living_ice_cream", ["minecraft:mob/slime/small1", "minecraft:mob/slime/small2"], ["minecraft:mob/slime/small3", "minecraft:mob/slime/small4"],
        ["minecraft:mob/slime/small5"], 1.6)
    mob("angry_ice_cream_cone", ["minecraft:mob/vex/idle1", "minecraft:mob/vex/idle2"], ["minecraft:mob/vex/hurt1", "minecraft:mob/vex/hurt2"],
        ["minecraft:mob/vex/death1"], 0.8)
    mob("ice_cream_dragon", ["minecraft:mob/ravager/idle1", "minecraft:mob/ravager/idle3"], ["minecraft:mob/ravager/hurt1", "minecraft:mob/ravager/hurt3"],
        ["minecraft:mob/ravager/death1", "minecraft:mob/ravager/death2"], 0.7)
    sounds["entity.ice_cream_dragon.roar"] = ev(["minecraft:mob/ravager/roar1", "minecraft:mob/ravager/roar2"], "ice_cream_dragon.roar", 0.6, 1.5)
    write(path, sounds)

# =================================================================== loot
def gen_loot():
    for b in BLOCKS + ["ice_cream_dragon_egg"]:
        write(f"{D}/loot_table/blocks/{b}.json", drop_self(b))
    def pool(item, lo, hi, looting=True, conditions=None):
        fns = [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": lo, "max": hi}, "add": False}]
        if looting:
            fns.append({"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting", "count": {"type": "minecraft:uniform", "min": 0.0, "max": 1.0}})
        p = {"rolls": 1.0, "bonus_rolls": 0.0, "entries": [{"type": "minecraft:item", "name": ns(item), "functions": fns}]}
        if conditions:
            p["conditions"] = conditions
        return p
    def entity(name, pools):
        write(f"{D}/loot_table/entities/{name}.json", {"type": "minecraft:entity", "random_sequence": f"{NS}:entities/{name}", "pools": pools})
    # The flavour's own ice cream is dropped in code (dropCustomDeathLoot); the tables hold what all flavours share.
    entity("ice_cream_zombie", [pool("minecraft:sugar", 0.0, 2.0)])
    entity("living_ice_cream", [pool("wafer_cone", 0.0, 1.0)])
    entity("ice_cream_beast", [pool("vanilla_ice_cream", 0.0, 2.0), pool("chocolate_ice_cream", 0.0, 2.0), pool("strawberry_ice_cream", 0.0, 2.0),
                               pool("mint_ice_cream", 0.0, 2.0), pool("wafer_cone", 1.0, 2.0)])
    entity("ice_cream_gargoyle", [pool("wafer_cone", 0.0, 2.0), pool("minecraft:sugar", 1.0, 3.0)])
    entity("angry_ice_cream_cone", [pool("wafer_cone", 0.0, 1.0)])
    # The dragon: 10 to 15 ice creams of each flavour and 5 to 10 Ultimate Ice Creams (no looting bonus: it is a boss).
    entity("ice_cream_dragon", [pool(f"{f}_ice_cream", 10.0, 15.0, looting=False) for f in FLAVOURS]
           + [pool("ultimate_ice_cream", 5.0, 10.0, looting=False)])

# =================================================================== recipes
def gen_recipes():
    # Ultimate Ice Cream: mint and chocolate on top, vanilla and strawberry below.
    shaped("ultimate_ice_cream", ["MC", "VS"], {"M": "mint_ice_cream", "C": "chocolate_ice_cream", "V": "vanilla_ice_cream", "S": "strawberry_ice_cream"},
           "ultimate_ice_cream", 1, "misc")
    for f in FLAVOURS:
        shaped(f"{f}_ice_cream_block", ["##", "##"], {"#": f"{f}_ice_cream"}, f"{f}_ice_cream_block", 1, "building")
    # Mint for the Ice Cream Machine: a fern is the closest thing to a mint leaf.
    write(f"{D}/recipe/ice_cream_machine/mint_ice_cream.json", {"type": f"{NS}:ice_cream_machine", "flavor": "minecraft:fern",
        "result": {"id": ns("mint_ice_cream")}, "processing_time": 80, "experience": 0.2})

# =================================================================== tags
def gen_tags():
    n = lambda *xs: [ns(x) for x in xs]
    tag("block", "minecraft", "mineable/shovel", n(*BLOCKS))
    tag("block", "minecraft", "mineable/pickaxe", n("ice_cream_dragon_egg"))
    tag("block", "minecraft", "overworld_carver_replaceables", n(*BLOCKS))
    tag("block", "minecraft", "dragon_immune", n("ice_cream_portal", "ice_cream_dragon_egg"))
    tag("block", "minecraft", "wither_immune", n("ice_cream_portal"))
    tag("block", "minecraft", "portals", n("ice_cream_portal"))
    tag("item", NS, "ice_creams", n("mint_ice_cream"))
    tag("item", NS, "sweets", n("mint_ice_cream", "ultimate_ice_cream"))
    tag("worldgen/biome", NS, "has_structure/ice_cream_nest", n("ice_cream_plains"))
    tag("worldgen/biome", NS, "is_ice_cream", n("ice_cream_plains"))
    tag("entity_type", NS, "ice_cream_creatures", n("ice_cream_zombie", "living_ice_cream", "ice_cream_beast", "ice_cream_gargoyle", "angry_ice_cream_cone", "ice_cream_dragon"))

# =================================================================== worldgen
def spawn(t, w, lo, hi):
    return {"type": ns(t), "weight": w, "minCount": lo, "maxCount": hi}

def flavour_rule(top):
    """Strawberry and mint patches cutting through the vanilla cover; `top` False gives the layers under the surface."""
    s = lambda b: {"type": "minecraft:block", "result_state": state(b)}
    patch = lambda noise, lo: {"type": "minecraft:noise_threshold", "noise": f"{NS}:{noise}", "min_threshold": lo, "max_threshold": 1e9}
    return {"type": "minecraft:sequence", "sequence": [
        {"type": "minecraft:condition", "if_true": patch("strawberry_patch", 0.32), "then_run": s("strawberry_ice_cream_block")},
        {"type": "minecraft:condition", "if_true": patch("mint_patch", 0.32), "then_run": s("mint_ice_cream_block")},
        s("vanilla_ice_cream_block")]}

def surface_rule():
    s = lambda b: {"type": "minecraft:block", "result_state": state(b)}
    on_floor = {"type": "minecraft:stone_depth", "offset": 0, "add_surface_depth": False, "secondary_depth_range": 0, "surface_type": "floor"}
    under_floor = {"type": "minecraft:stone_depth", "offset": 0, "add_surface_depth": True, "secondary_depth_range": 0, "surface_type": "floor"}
    dry = {"type": "minecraft:water", "offset": -1, "surface_depth_multiplier": 0, "add_stone_depth": False}
    return {"type": "minecraft:sequence", "sequence": [
        {"type": "minecraft:condition", "if_true": {"type": "minecraft:vertical_gradient", "random_name": "minecraft:bedrock_floor",
            "true_at_and_below": {"above_bottom": 0}, "false_at_and_above": {"above_bottom": 5}}, "then_run": s("minecraft:bedrock")},
        {"type": "minecraft:condition", "if_true": {"type": "minecraft:above_preliminary_surface"}, "then_run": {"type": "minecraft:sequence", "sequence": [
            # Lake and sea beds under melted chocolate stay chocolate.
            {"type": "minecraft:condition", "if_true": on_floor, "then_run": {"type": "minecraft:sequence", "sequence": [
                {"type": "minecraft:condition", "if_true": dry, "then_run": flavour_rule(True)}, s("chocolate_ice_cream_block")]}},
            {"type": "minecraft:condition", "if_true": under_floor, "then_run": {"type": "minecraft:sequence", "sequence": [
                {"type": "minecraft:condition", "if_true": dry, "then_run": flavour_rule(False)}, s("chocolate_ice_cream_block")]}}]}}]}

def gen_worldgen():
    router = json.load(open(os.path.join(HERE, "data", "overworld_noise_router.json"), encoding="utf-8"))
    write(f"{W}/noise_settings/ice_cream.json", {
        "aquifers_enabled": True,
        "default_block": state("chocolate_ice_cream_block"),
        "default_fluid": state("melted_chocolate", level="0"),
        "disable_mob_generation": False,
        "legacy_random_source": False,
        "noise": {"height": 384, "min_y": -64, "size_horizontal": 1, "size_vertical": 2},
        "noise_router": router["noise_router"],
        "ore_veins_enabled": False,
        # Sea level 40: only the deepest basins fill with melted chocolate; the rest of the low ground stays dry.
        "sea_level": 40,
        "spawn_target": router["spawn_target"],
        "surface_rule": surface_rule()})
    for noise, first in (("strawberry_patch", -5), ("mint_patch", -5)):
        write(f"{W}/noise/{noise}.json", {"firstOctave": first, "amplitudes": [1.0, 0.6, 0.3]})

    attrs = {
        "minecraft:visual/sky_color": "#f8e6c8", "minecraft:visual/fog_color": "#fdf1df", "minecraft:visual/water_fog_color": "#5a321e",
        "minecraft:visual/cloud_color": "#ccfff4fa",
        "minecraft:audio/background_music": {"default": {"max_delay": 24000, "min_delay": 12000, "sound": "minecraft:music.overworld.cherry_grove"}},
        # A faint lilac sparkle in the air (the bluish-lilac light of some reference images).
        "minecraft:visual/ambient_particles": [{"particle": {"type": "minecraft:dust", "color": 0xC9B6F2, "scale": 0.7}, "probability": 0.0015}],
    }
    vanilla_type = {
        "ambient_light": 0.0, "coordinate_scale": 1.0, "default_clock": "minecraft:overworld", "has_ceiling": False,
        "has_ender_dragon_fight": False, "has_skylight": True, "height": 384, "infiniburn": "#minecraft:infiniburn_overworld",
        "logical_height": 384, "min_y": -64, "monster_spawn_block_light_limit": 0,
        "monster_spawn_light_level": {"type": "minecraft:uniform", "max_inclusive": 7, "min_inclusive": 0},
        "timelines": "#minecraft:in_overworld",
        "attributes": dict(attrs, **{
            "minecraft:audio/ambient_sounds": {"mood": {"block_search_extent": 8, "offset": 2.0, "sound": "minecraft:ambient.cave", "tick_delay": 6000}},
            "minecraft:gameplay/bed_rule": {"can_set_spawn": "always", "can_sleep": "when_dark", "error_message": {"translate": "block.minecraft.bed.no_sleep"}},
            "minecraft:gameplay/respawn_anchor_works": False,
            "minecraft:visual/ambient_light_color": "#0d0b12",
            "minecraft:visual/cloud_height": 192.33})}
    write(f"{D}/dimension_type/ice_cream.json", vanilla_type)
    write(f"{D}/dimension/ice_cream.json", {"type": f"{NS}:ice_cream", "generator": {"type": "minecraft:noise", "settings": f"{NS}:ice_cream",
        "biome_source": {"type": "minecraft:fixed", "biome": f"{NS}:ice_cream_plains"}}})

    # Features: three-flavour pinnacles and melted chocolate lakes.
    write(f"{W}/configured_feature/ice_cream_pinnacle.json", {"type": f"{NS}:ice_cream_pinnacle", "config": {}})
    write(f"{W}/placed_feature/ice_cream_pinnacles.json", {"feature": f"{NS}:ice_cream_pinnacle", "placement": [
        {"type": f"{NS}:config_count", "setting": "ice_cream_pinnacles"}, {"type": "minecraft:rarity_filter", "chance": 3},
        {"type": "minecraft:in_square"}, {"type": "minecraft:heightmap", "heightmap": "WORLD_SURFACE_WG"}, {"type": "minecraft:biome"}]})
    write(f"{W}/configured_feature/lake_melted_chocolate_ice_cream.json", {"type": "minecraft:lake", "config": {
        "fluid": {"type": "minecraft:simple_state_provider", "state": state("melted_chocolate", level="0")},
        "barrier": {"type": "minecraft:simple_state_provider", "state": state("chocolate_ice_cream_block")}}})
    write(f"{W}/placed_feature/lake_melted_chocolate_ice_cream.json", {"feature": f"{NS}:lake_melted_chocolate_ice_cream", "placement": [
        {"type": "minecraft:rarity_filter", "chance": 6}, {"type": "minecraft:in_square"},
        {"type": "minecraft:heightmap", "heightmap": "WORLD_SURFACE_WG"}, {"type": "minecraft:biome"}]})
    write(f"{W}/placed_feature/lake_melted_chocolate_underground.json", {"feature": f"{NS}:lake_melted_chocolate_ice_cream", "placement": [
        {"type": "minecraft:rarity_filter", "chance": 4}, {"type": "minecraft:in_square"},
        {"type": "minecraft:height_range", "height": {"type": "minecraft:uniform", "min_inclusive": {"above_bottom": 8}, "max_inclusive": {"absolute": 40}}},
        {"type": "minecraft:environment_scan", "direction_of_search": "down", "max_steps": 32,
         "target_condition": {"type": "minecraft:all_of", "predicates": [{"type": "minecraft:not", "predicate": {"type": "minecraft:matching_blocks", "blocks": "minecraft:air"}},
                                                                         {"type": "minecraft:inside_world_bounds", "offset": [0, -5, 0]}]},
         "allowed_search_condition": {"type": "minecraft:matching_blocks", "blocks": "minecraft:air"}},
        {"type": "minecraft:surface_relative_threshold_filter", "heightmap": "OCEAN_FLOOR_WG", "max_inclusive": -5}, {"type": "minecraft:biome"}]})

    write(f"{W}/biome/ice_cream_plains.json", {
        "attributes": attrs,
        "carvers": ["minecraft:cave", "minecraft:cave_extra_underground", "minecraft:canyon"],
        "downfall": 0.4,
        "effects": {"foliage_color": "#9fe7c8", "grass_color": "#9fe7c8", "water_color": "#7b4a2e"},
        "features": [[], [f"{NS}:lake_melted_chocolate_underground", f"{NS}:lake_melted_chocolate_ice_cream"], [], [], [], [], [], [], [],
                     [f"{NS}:ice_cream_pinnacles"], []],
        "has_precipitation": False,
        "spawn_costs": {},
        "spawners": {
            "ambient": [], "axolotls": [], "misc": [], "underground_water_creature": [], "water_ambient": [], "water_creature": [],
            "creature": [spawn("living_ice_cream", 40, 1, 3)],
            "monster": [spawn("ice_cream_zombie", 160, 2, 4)] + [spawn("ice_cream_gargoyle", 25, 1, 2), spawn("ice_cream_beast", 8, 1, 1),
                                                               spawn("angry_ice_cream_cone", 12, 1, 3)]},
        "temperature": 0.3})

    # Ice Cream Nest: the dragon's arena, a rare landmark of the plains.
    name = "ice_cream_nest"
    # Its own structure type (IceCreamNestStructure): centred on the chunk at the lowest point of a fairly flat
    # footprint, instead of a jigsaw that samples one corner and can leave the nest floating on a hillside.
    write(f"{W}/structure/{name}.json", {"type": f"{NS}:ice_cream_nest", "biomes": f"#{NS}:has_structure/{name}",
        "step": "surface_structures", "spawn_overrides": {}, "terrain_adaptation": "beard_box"})
    for freq, (spacing, separation) in {"rare": (36, 14), "normal": (24, 10), "common": (16, 6)}.items():
        write(f"{W}/structure_set/ice_cream_nests_{freq}.json", {
            "neoforge:conditions": [{"type": f"{NS}:structure_frequency", "frequency": freq, "structure": name}],
            "structures": [{"structure": f"{NS}:{name}", "weight": 1}],
            "placement": {"type": "minecraft:random_spread", "spacing": spacing, "separation": separation, "salt": 1650724817}})

# =================================================================== advancements
def gen_advancements():
    kill = lambda t: {"trigger": "minecraft:player_killed_entity", "conditions": {"entity": [
        {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"type": ns(t)}}]}}
    adv("ultimate_ice_cream", "visit_ice_cream_truck", "ultimate_ice_cream", "task", {"ultimate": has("ultimate_ice_cream")})
    adv("ice_cream_amulet", "ultimate_ice_cream", "ice_cream_amulet", "goal", {"amulet": has("ice_cream_amulet")})
    adv("enter_ice_cream_dimension", "ice_cream_amulet", "vanilla_ice_cream_block", "goal", {"enter": {
        "trigger": "minecraft:changed_dimension", "conditions": {"to": f"{NS}:ice_cream"}}})
    adv("scoop_thief", "enter_ice_cream_dimension", "wafer_cone", "task", {"cone": kill("angry_ice_cream_cone")})
    adv("visit_ice_cream_nest", "enter_ice_cream_dimension", "ice_cream_dragon_egg", "task", {"visit": {"trigger": "minecraft:location", "conditions": {"player": [
        {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"location": {"structures": f"{NS}:ice_cream_nest"}}}]}}})
    adv("dragon_killer", "visit_ice_cream_nest", "ice_cream_dragon_spawn_egg", "challenge", {"kill": kill("ice_cream_dragon")},
        rewards={"experience": 200})

def generate():
    gen_assets(); gen_sounds(); gen_loot(); gen_recipes(); gen_tags(); gen_worldgen(); gen_advancements()
