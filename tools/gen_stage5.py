#!/usr/bin/env python3
"""Stage 5 data: Candy Cave (biome, candy rock, sugar crystals, crevice carver), Impostor Cake and Ice Cream Machine.

Called by tools/gen_resources.py after gen_stage4 (files written here replace earlier files of the same name, and
tags are merged).
"""
import json
import gen_resources as G
from gen_resources import NS, A, D, write, ns, flat_item, item_def, simple_state, state, tag, shaped, adv, has, drop_self

W = f"{D}/worldgen"

# =================================================================== assets
def gen_assets():
    for rock in ("pink_candy_rock", "purple_candy_rock"):
        G.block_cube(rock)
    # Sugar crystal: a cross model turned to the face it grows from, like vanilla amethyst clusters.
    write(f"{A}/models/block/sugar_crystal.json", {"parent": "minecraft:block/cross", "render_type": "minecraft:cutout",
        "textures": {"cross": f"{NS}:block/sugar_crystal"}})
    rot = {"up": {}, "down": {"x": 180}, "north": {"x": 90}, "east": {"x": 90, "y": 90}, "south": {"x": 90, "y": 180}, "west": {"x": 90, "y": 270}}
    write(f"{A}/blockstates/sugar_crystal.json", {"variants": {
        f"facing={f}": dict({"model": f"{NS}:block/sugar_crystal"}, **r) for f, r in rot.items()}})
    flat_item("sugar_crystal", "block/sugar_crystal")
    machine_model()
    flat_item("impostor_cake_spawn_egg")

def machine_model():
    """Ice Cream Machine facing north: body, a wide head overhanging the front, the black dispensing bar and nozzle
    under it, and the tray sticking out at the bottom. The lever is drawn by the block entity renderer."""
    t = lambda n: f"#{n}"
    def face(tex, uv, cull=None):
        f = {"texture": t(tex), "uv": uv}
        if cull:
            f["cullface"] = cull
        return f
    def box(frm, to, faces):
        return {"from": frm, "to": to, "faces": faces}
    elements = [
        # body
        box([1, 0, 5], [15, 11, 15], {
            "north": face("front", [1, 5, 15, 16]), "south": face("casing", [1, 5, 15, 16]),
            "east": face("side", [3, 5, 13, 16]), "west": face("side", [3, 5, 13, 16]),
            "down": face("casing", [1, 1, 15, 11], "down")}),
        # head
        box([1, 11, 1], [15, 16, 15], {
            "north": face("front", [1, 0, 15, 5]), "south": face("casing", [1, 0, 15, 5]),
            "east": face("side", [1, 0, 15, 5]), "west": face("side", [1, 0, 15, 5]),
            "up": face("top", [1, 1, 15, 15], "up"), "down": face("casing", [1, 1, 15, 15])}),
        # dispensing bar
        box([2, 9, 2], [14, 11, 5], {d: face("dark", [2, 0, 14, 3] if d in ("up", "down") else [2, 5, 14, 7] if d in ("north", "south") else [2, 5, 5, 7])
                                     for d in ("north", "south", "east", "west", "up", "down")}),
        # nozzle
        box([7, 7, 3], [9, 9, 5], {d: face("dark", [7, 7, 9, 9]) for d in ("north", "south", "east", "west", "down")}),
        # tray
        box([2, 0, 0], [14, 2, 5], {
            "up": face("tray", [2, 0, 14, 5]), "north": face("casing", [2, 14, 14, 16]),
            "east": face("casing", [0, 14, 5, 16]), "west": face("casing", [0, 14, 5, 16]), "down": face("casing", [2, 0, 14, 5], "down")}),
    ]
    write(f"{A}/models/block/ice_cream_machine.json", {"parent": "minecraft:block/block", "textures": {
        "particle": f"{NS}:block/ice_cream_machine_casing", "casing": f"{NS}:block/ice_cream_machine_casing",
        "front": f"{NS}:block/ice_cream_machine_front", "side": f"{NS}:block/ice_cream_machine_side",
        "top": f"{NS}:block/ice_cream_machine_top", "dark": f"{NS}:block/ice_cream_machine_dark",
        "tray": f"{NS}:block/ice_cream_machine_tray"}, "elements": elements})
    write(f"{A}/blockstates/ice_cream_machine.json", {"variants": {
        f"facing={f}": dict({"model": f"{NS}:block/ice_cream_machine"}, **({"y": y} if y else {}))
        for f, y in (("north", 0), ("east", 90), ("south", 180), ("west", 270))}})
    item_def("ice_cream_machine", f"{NS}:block/ice_cream_machine")

def gen_sounds():
    path = f"{A}/sounds.json"
    sounds = json.load(open(path, encoding="utf-8"))
    def ev(names, subtitle, pitch=1.0, volume=1.0):
        return {"subtitle": f"subtitles.{NS}.{subtitle}", "sounds": [{"name": s, "pitch": pitch, "volume": volume} for s in names]}
    # Provisional: re-pitched vanilla sounds until original recordings exist. A squelchy, spongy cake.
    sounds["entity.impostor_cake.ambient"] = ev(["minecraft:mob/slime/small1", "minecraft:mob/slime/small2"], "impostor_cake.ambient", 0.7)
    sounds["entity.impostor_cake.hurt"] = ev(["minecraft:mob/slime/small3", "minecraft:mob/slime/small4"], "impostor_cake.hurt", 0.8)
    sounds["entity.impostor_cake.death"] = ev(["minecraft:mob/slime/big1"], "impostor_cake.death", 0.9)
    sounds["entity.impostor_cake.reveal"] = ev(["minecraft:mob/ravager/stun1", "minecraft:mob/ravager/stun2"], "impostor_cake.reveal", 1.4)
    sounds["entity.impostor_cake.chomp"] = ev(["minecraft:random/eat1", "minecraft:random/eat2", "minecraft:random/eat3"], "impostor_cake.chomp", 0.6)
    sounds["block.ice_cream_machine.serve"] = ev(["minecraft:block/honeyblock/slide1", "minecraft:block/honeyblock/slide2", "minecraft:block/honeyblock/slide3"], "ice_cream_machine.serve", 1.2, 0.8)
    write(path, sounds)

# =================================================================== loot
def gen_loot():
    for b in ("pink_candy_rock", "purple_candy_rock", "ice_cream_machine"):
        write(f"{D}/loot_table/blocks/{b}.json", drop_self(b))
    write(f"{D}/loot_table/blocks/sugar_crystal.json", {"type": "minecraft:block", "random_sequence": f"{NS}:blocks/sugar_crystal",
        "pools": [{"rolls": 1.0, "bonus_rolls": 0.0, "entries": [{"type": "minecraft:alternatives", "children": [
            {"type": "minecraft:item", "name": f"{NS}:sugar_crystal", "conditions": [G.SILK]},
            {"type": "minecraft:item", "name": "minecraft:sugar", "functions": [
                {"function": "minecraft:set_count", "add": False, "count": {"type": "minecraft:uniform", "min": 2.0, "max": 4.0}},
                {"function": "minecraft:apply_bonus", "enchantment": "minecraft:fortune", "formula": "minecraft:ore_drops"},
                {"function": "minecraft:explosion_decay"}]}]}]}]})
    def pool(item, lo, hi, looting=True, conditions=None):
        fns = [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": lo, "max": hi}, "add": False}]
        if looting:
            fns.append({"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting", "count": {"type": "minecraft:uniform", "min": 0.0, "max": 1.0}})
        p = {"rolls": 1.0, "bonus_rolls": 0.0, "entries": [{"type": "minecraft:item", "name": ns(item), "functions": fns}]}
        if conditions:
            p["conditions"] = conditions
        return p
    # Impostor Cake: sugar and egg from the sponge, and now and then a whole (honest) cake.
    cake_chance = [{"condition": "minecraft:killed_by_player"},
                   {"condition": "minecraft:random_chance_with_enchanted_bonus", "enchantment": "minecraft:looting", "unenchanted_chance": 0.08,
                    "enchanted_chance": {"type": "minecraft:linear", "base": 0.1, "per_level_above_first": 0.02}}]
    write(f"{D}/loot_table/entities/impostor_cake.json", {"type": "minecraft:entity", "random_sequence": f"{NS}:entities/impostor_cake", "pools": [
        pool("minecraft:sugar", 1.0, 3.0), pool("minecraft:egg", 0.0, 1.0), pool("minecraft:cake", 1.0, 1.0, looting=False, conditions=cake_chance)]})

# =================================================================== recipes
def machine_recipe(name, flavor, result, time=80, xp=0.2):
    write(f"{D}/recipe/ice_cream_machine/{name}.json", {"type": f"{NS}:ice_cream_machine", "flavor": ns(flavor),
        "result": {"id": ns(result)}, "processing_time": time, "experience": xp})

def gen_recipes():
    shaped("ice_cream_machine", ["QQQ", "IPI", "ISI"], {"Q": "minecraft:quartz", "I": "minecraft:iron_ingot", "P": "minecraft:packed_ice",
           "S": "minecraft:smooth_quartz_slab"}, "ice_cream_machine", 1, "misc")
    machine_recipe("vanilla_ice_cream", "minecraft:sugar", "vanilla_ice_cream")
    machine_recipe("strawberry_ice_cream", "minecraft:sweet_berries", "strawberry_ice_cream")
    machine_recipe("chocolate_ice_cream", "cocoa_powder", "chocolate_ice_cream")

# =================================================================== tags
def gen_tags():
    n = lambda *xs: [ns(x) for x in xs]
    tag("block", "minecraft", "mineable/pickaxe", n("pink_candy_rock", "purple_candy_rock", "sugar_crystal", "ice_cream_machine"))
    tag("block", "minecraft", "overworld_carver_replaceables", n("pink_candy_rock", "purple_candy_rock"))
    tag("block", "minecraft", "crystal_sound_blocks", n("sugar_crystal"))
    tag("item", NS, "ice_creams", n("vanilla_ice_cream", "strawberry_ice_cream", "chocolate_ice_cream"))
    for t in [("minecraft", "is_overworld"), ("c", "is_overworld"), ("c", "is_cave"), ("c", "is_underground"), (NS, "is_candy"),
              ("minecraft", "has_structure/mineshaft"), ("minecraft", "has_structure/stronghold"), ("minecraft", "has_structure/trial_chambers")]:
        tag("worldgen/biome", t[0], t[1], n("candy_cave"))

# =================================================================== worldgen
def gen_worldgen():
    # Candy rock blobs: big, frequent patches that coat the cave walls (surface rules only reach floors and ceilings).
    for rock, size, count in (("pink_candy_rock", 56, 26), ("purple_candy_rock", 40, 14)):
        write(f"{W}/configured_feature/ore_{rock}.json", {"type": "minecraft:ore", "config": {
            "discard_chance_on_air_exposure": 0.0, "size": size, "targets": [
                {"state": state(rock), "target": {"predicate_type": "minecraft:tag_match", "tag": "minecraft:stone_ore_replaceables"}},
                {"state": state(rock), "target": {"predicate_type": "minecraft:tag_match", "tag": "minecraft:deepslate_ore_replaceables"}}]}})
        write(f"{W}/placed_feature/ore_{rock}.json", {"feature": f"{NS}:ore_{rock}", "placement": [
            {"type": "minecraft:count", "count": count}, {"type": "minecraft:in_square"},
            {"type": "minecraft:height_range", "height": {"type": "minecraft:uniform", "min_inclusive": {"absolute": -48}, "max_inclusive": {"absolute": 72}}},
            {"type": "minecraft:biome"}]})
    # Sugar crystals: small glowing clusters on floors and ceilings (revival proposal: points of interest in the dark).
    for where, facing, direction in (("floor", "up", "down"), ("ceiling", "down", "up")):
        write(f"{W}/configured_feature/sugar_crystal_{where}.json", {"type": "minecraft:simple_block", "config": {
            "to_place": {"type": "minecraft:simple_state_provider", "state": state("sugar_crystal", facing=facing, waterlogged=False)}}})
        write(f"{W}/placed_feature/sugar_crystals_{where}.json", {"feature": f"{NS}:sugar_crystal_{where}", "placement": [
            {"type": "minecraft:count", "count": 28}, {"type": "minecraft:in_square"},
            {"type": "minecraft:height_range", "height": {"type": "minecraft:uniform", "min_inclusive": {"absolute": -50}, "max_inclusive": {"absolute": 64}}},
            {"type": "minecraft:environment_scan", "direction_of_search": direction, "max_steps": 12,
             "target_condition": {"type": "minecraft:solid"}, "allowed_search_condition": {"type": "minecraft:matching_block_tag", "tag": "minecraft:air"}},
            {"type": "minecraft:random_offset", "xz_spread": 0, "y_spread": 1 if facing == "up" else -1},
            {"type": "minecraft:block_predicate_filter", "predicate": {"type": "minecraft:all_of", "predicates": [
                {"type": "minecraft:matching_block_tag", "tag": "minecraft:air"},
                {"type": "minecraft:would_survive", "state": state("sugar_crystal", facing=facing, waterlogged=False)}]}},
            {"type": "minecraft:biome"}]})
    # Crevices: a canyon carver that is more frequent and reaches lower than vanilla's, for tall, narrow rifts.
    write(f"{W}/configured_carver/candy_crevice.json", {"type": "minecraft:canyon", "config": {
        "lava_level": {"above_bottom": 8}, "probability": 0.06, "replaceable": "#minecraft:overworld_carver_replaceables",
        "shape": {"distance_factor": {"type": "minecraft:uniform", "min_inclusive": 0.75, "max_exclusive": 1.0},
                  "horizontal_radius_factor": {"type": "minecraft:uniform", "min_inclusive": 0.5, "max_exclusive": 0.8},
                  "thickness": {"type": "minecraft:trapezoid", "min": 0.0, "max": 6.0, "plateau": 2.0},
                  "vertical_radius_center_factor": 0.0, "vertical_radius_default_factor": 1.0, "width_smoothness": 3},
        "vertical_rotation": {"type": "minecraft:uniform", "min_inclusive": -0.125, "max_exclusive": 0.125},
        "y": {"type": "minecraft:uniform", "min_inclusive": {"absolute": -40}, "max_inclusive": {"absolute": 40}}, "yScale": 4.0}})

    def spawn(t, w, lo, hi):
        return {"type": ns(t), "weight": w, "minCount": lo, "maxCount": hi}
    write(f"{W}/biome/candy_cave.json", {
        "attributes": {
            "minecraft:audio/background_music": {"default": {"max_delay": 24000, "min_delay": 12000, "sound": "minecraft:music.overworld.lush_caves"}},
            # Rare, faint pink sparks in the air (revival proposal); the cave itself stays dark.
            "minecraft:visual/ambient_particles": [{"particle": {"type": "minecraft:dust", "color": 0xF58CC4, "scale": 0.6}, "probability": 0.0025}],
            "minecraft:visual/fog_color": "#c9a0c0", "minecraft:visual/sky_color": "#ff99ff", "minecraft:visual/water_fog_color": "#a3207f"},
        "carvers": ["minecraft:cave", "minecraft:cave_extra_underground", "minecraft:canyon", f"{NS}:candy_crevice"],
        "downfall": 0.6,
        "effects": {"foliage_color": "#c35bb8", "grass_color": "#ff6dff", "water_color": "#ff33cc"},
        "features": [[], ["minecraft:lake_lava_underground"], ["minecraft:amethyst_geode"],
                     ["minecraft:monster_room", "minecraft:monster_room_deep"], [], [],
                     [f"{NS}:ore_pink_candy_rock", f"{NS}:ore_purple_candy_rock"] + G.VANILLA_UNDERGROUND, [],
                     ["minecraft:spring_water", "minecraft:spring_lava"],
                     ["minecraft:glow_lichen", f"{NS}:sugar_crystals_floor", f"{NS}:sugar_crystals_ceiling"],
                     []],
        "has_precipitation": True,
        "spawn_costs": {},
        "spawners": {
            "ambient": [spawn("minecraft:bat", 10, 8, 8)], "axolotls": [], "creature": [], "misc": [],
            "monster": [spawn("bubbaloo_creeper", 40, 1, 2), spawn("impostor_cake", 30, 1, 1),
                        spawn("minecraft:zombie", 95, 4, 4), spawn("minecraft:zombie_villager", 5, 1, 1), spawn("minecraft:skeleton", 100, 4, 4),
                        spawn("minecraft:creeper", 60, 4, 4), spawn("minecraft:spider", 100, 4, 4), spawn("minecraft:enderman", 10, 1, 4),
                        spawn("minecraft:witch", 5, 1, 1)],
            "underground_water_creature": [spawn("minecraft:glow_squid", 10, 4, 6)], "water_ambient": [], "water_creature": []},
        "temperature": 0.7})

    # The Impostor Cake also hides in Sweet Forests at night, among the real sweets (revival proposal).
    path = f"{W}/biome/sweet_forest.json"
    biome = json.load(open(path, encoding="utf-8"))
    biome["spawners"]["monster"].insert(2, spawn("impostor_cake", 10, 1, 1))
    write(path, biome)

# =================================================================== advancements
def gen_advancements():
    adv("enter_candy_cave", "enter_sweet_forest", "pink_candy_rock", "task", {"cave": {"trigger": "minecraft:location", "conditions": {"player": [
        {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"location": {"biomes": f"{NS}:candy_cave"}}}]}}})
    adv("sugar_rocks", "enter_candy_cave", "sugar_crystal", "task", {"crystal": has("sugar_crystal")})
    adv("the_cake_is_a_lie", "enter_candy_cave", "impostor_cake_spawn_egg", "goal", {"kill": {
        "trigger": "minecraft:player_killed_entity", "conditions": {"entity": [
            {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"type": f"{NS}:impostor_cake"}}]}}})
    adv("soft_serve", "visit_ice_cream_truck", "ice_cream_machine", "task", {"machine": has("ice_cream_machine")})

def generate():
    gen_assets(); gen_sounds(); gen_loot(); gen_recipes(); gen_tags(); gen_worldgen(); gen_advancements()
