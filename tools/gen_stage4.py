#!/usr/bin/env python3
"""Stage 4 data: Gingerbread Fortress, Ice Cream Truck (with the Ice Cream Vendor and ice creams), and the
remodelled Grape Spider, Candy Tube Dog and Brown Sugar Rhino (no new data needed for those three).

Called by tools/gen_resources.py after gen_stage3 (files written here replace earlier files of the same name).
"""
from gen_resources import NS, A, D, write, ns, flat_item, tag, shapeless, adv, has

W = f"{D}/worldgen"
COLOURS = ["white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple",
           "blue", "brown", "green", "red", "black"]
FOODS = ["wafer_cone", "vanilla_ice_cream", "strawberry_ice_cream", "chocolate_ice_cream"]

def gen_assets():
    for item in FOODS + ["ice_cream_vendor_spawn_egg"]:
        flat_item(item)

def gen_banner_patterns():
    # Data-driven banner patterns for the truck's banners; their textures live in textures/entity/{banner,shield}.
    for name in ("ice_cream_cone", "ice_cream_scoop"):
        write(f"{D}/banner_pattern/{name}.json", {"asset_id": f"{NS}:{name}", "translation_key": f"block.{NS}.banner.{name}"})

def jigsaw(name, adaptation, spacing_table, salt):
    write(f"{W}/structure/{name}.json", {"type": "minecraft:jigsaw", "biomes": f"#{NS}:has_structure/{name}",
        "step": "surface_structures", "spawn_overrides": {}, "terrain_adaptation": adaptation,
        "start_pool": f"{NS}:{name}/start", "size": 1, "start_height": {"absolute": 0},
        "project_start_to_heightmap": "WORLD_SURFACE_WG", "max_distance_from_center": 80, "use_expansion_hack": False})
    write(f"{W}/template_pool/{name}/start.json", {"fallback": "minecraft:empty", "elements": [{"weight": 1, "element": {
        "element_type": "minecraft:single_pool_element", "location": f"{NS}:{name}", "projection": "rigid", "processors": "minecraft:empty"}}]})
    plural = {"gingerbread_fortress": "gingerbread_fortresses", "ice_cream_truck": "ice_cream_trucks"}[name]
    for freq, (spacing, separation) in spacing_table.items():
        write(f"{W}/structure_set/{plural}_{freq}.json", {
            "neoforge:conditions": [{"type": f"{NS}:structure_frequency", "frequency": freq, "structure": name}],
            "structures": [{"structure": f"{NS}:{name}", "weight": 1}],
            "placement": {"type": "minecraft:random_spread", "spacing": spacing, "separation": separation, "salt": salt}})

def gen_worldgen():
    n = lambda *xs: [ns(x) for x in xs]
    tag("worldgen/biome", NS, "has_structure/gingerbread_fortress", n("sweet_forest"))
    tag("worldgen/biome", NS, "has_structure/ice_cream_truck", n("sweet_forest", "jelly_bean_fields"))
    # The fortress is big and rare; beard_box gives it a solid foundation on uneven ground.
    jigsaw("gingerbread_fortress", "beard_box", {"rare": (64, 28), "normal": (44, 20), "common": (30, 12)}, 774231509)
    jigsaw("ice_cream_truck", "beard_thin", {"rare": (40, 16), "normal": (26, 10), "common": (16, 6)}, 1926114083)

def gen_loot():
    def e(item, weight, lo, hi):
        return {"type": "minecraft:item", "name": ns(item), "weight": weight, "functions": [
            {"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": lo, "max": hi}, "add": False}]}
    def table(name, pools):
        write(f"{D}/loot_table/chests/{name}.json", {"type": "minecraft:chest", "random_sequence": f"{NS}:chests/{name}", "pools": pools})
    # Fortress storeroom: provisions and the treasury.
    table("gingerbread_fortress", [
        {"rolls": {"type": "minecraft:uniform", "min": 4.0, "max": 7.0}, "bonus_rolls": 0.0, "entries": [
            e("gingerbread", 15, 2, 6), e("gumdrop", 12, 2, 6), e("frosted_gingerbread_block", 10, 2, 6), e("chocolate_bar", 10, 1, 4),
            e("brown_sugar_brick", 8, 1, 4), e("minecraft:iron_ingot", 8, 2, 5), e("minecraft:gold_ingot", 6, 1, 4),
            e("tourmaline", 4, 1, 3), e("minecraft:emerald", 4, 1, 3), e("minecraft:bread", 8, 2, 4)]},
        {"rolls": 1.0, "bonus_rolls": 0.0, "entries": [
            {"type": "minecraft:empty", "weight": 6},
            {"type": "minecraft:item", "name": f"{NS}:jelly_bazooka", "weight": 1, "functions": [
                {"function": "minecraft:set_damage", "damage": {"type": "minecraft:uniform", "min": 0.4, "max": 0.9}}]},
            {"type": "minecraft:item", "name": f"{NS}:heavy_boots", "weight": 1},
            {"type": "minecraft:item", "name": "minecraft:golden_apple", "weight": 2}]}])
    # Quarters: what a gingerbread man keeps by his bed.
    table("gingerbread_fortress_quarters", [
        {"rolls": {"type": "minecraft:uniform", "min": 2.0, "max": 4.0}, "bonus_rolls": 0.0, "entries": [
            e("gingerbread", 12, 1, 4), e("lollipop", 10, 1, 3), e("cotton_candy", 8, 1, 3), e("gumdrop", 10, 1, 4),
            e("minecraft:white_wool", 5, 1, 2), e("minecraft:gold_nugget", 6, 2, 8)]},
        {"rolls": 1.0, "bonus_rolls": 0.0, "entries": [
            {"type": "minecraft:empty", "weight": 4},
            {"type": "minecraft:item", "name": f"{NS}:brown_sugar_sword", "weight": 1, "functions": [
                {"function": "minecraft:set_damage", "damage": {"type": "minecraft:uniform", "min": 0.3, "max": 0.9}}]}]}])
    # Ice Cream Truck: ingredients, so players can work out the recipes, plus a few finished cones.
    table("ice_cream_truck", [
        {"rolls": {"type": "minecraft:uniform", "min": 3.0, "max": 6.0}, "bonus_rolls": 0.0, "entries": [
            e("minecraft:sugar", 15, 2, 8), e("minecraft:snowball", 12, 2, 8), e("minecraft:sweet_berries", 10, 2, 6),
            e("cocoa_powder", 8, 1, 4), e("wafer_cone", 12, 1, 4), e("minecraft:milk_bucket", 4, 1, 1),
            e("vanilla_ice_cream", 5, 1, 2), e("strawberry_ice_cream", 4, 1, 2), e("chocolate_ice_cream", 4, 1, 2),
            e("minecraft:emerald", 3, 1, 2)]}])
    write(f"{D}/loot_table/entities/ice_cream_vendor.json", {"type": "minecraft:entity", "random_sequence": f"{NS}:entities/ice_cream_vendor",
        "pools": [{"rolls": 1.0, "bonus_rolls": 0.0, "entries": [e("wafer_cone", 1, 0, 2)]}]})

def gen_recipes():
    shapeless("wafer_cone", ["minecraft:wheat", "minecraft:wheat", "minecraft:sugar"], "wafer_cone", 3, "misc")
    base = ["wafer_cone", "minecraft:milk_bucket", "minecraft:snowball"]
    shapeless("vanilla_ice_cream", base + ["minecraft:sugar"], "vanilla_ice_cream", 2, "misc")
    shapeless("strawberry_ice_cream", base + ["minecraft:sweet_berries"], "strawberry_ice_cream", 2, "misc")
    shapeless("chocolate_ice_cream", base + ["cocoa_powder"], "chocolate_ice_cream", 2, "misc")

def gen_tags():
    n = lambda *xs: [ns(x) for x in xs]
    tag("item", NS, "sweets", n("wafer_cone", "vanilla_ice_cream", "strawberry_ice_cream", "chocolate_ice_cream"))
    tag("item", NS, "candy_tube_dog_food", n("vanilla_ice_cream", "strawberry_ice_cream", "chocolate_ice_cream"))

def gen_advancements():
    def visit(structure):
        return {"visit": {"trigger": "minecraft:location", "conditions": {"player": [
            {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"location": {"structures": f"{NS}:{structure}"}}}]}}}
    adv("visit_gingerbread_fortress", "visit_gingerbread_tower", "frosted_gingerbread_block", "goal", visit("gingerbread_fortress"))
    # The King now lives in the fortress, so his advancement follows it (stage 3 hung it under the tower).
    adv("defeat_gingerbread_king", "visit_gingerbread_fortress", "gingerbread_king_spawn_egg", "challenge", {"kill": {
        "trigger": "minecraft:player_killed_entity", "conditions": {"entity": [
            {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"type": f"{NS}:gingerbread_king"}}]}}},
        rewards={"experience": 100})
    adv("visit_ice_cream_truck", "enter_sweet_forest", "vanilla_ice_cream", "task", visit("ice_cream_truck"))
    eat = lambda item: {"trigger": "minecraft:consume_item", "conditions": {"item": {"items": ns(item)}}}
    adv("brain_freeze", "visit_ice_cream_truck", "strawberry_ice_cream", "goal",
        {"vanilla": eat("vanilla_ice_cream"), "strawberry": eat("strawberry_ice_cream"), "chocolate": eat("chocolate_ice_cream")})

def generate():
    gen_assets(); gen_banner_patterns(); gen_worldgen(); gen_loot(); gen_recipes(); gen_tags(); gen_advancements()
