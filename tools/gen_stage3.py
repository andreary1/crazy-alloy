#!/usr/bin/env python3
"""Stage 3 data: remodelled creatures and the Gingerbread King (sounds, loot, spawn egg, advancement).

Called by tools/gen_resources.py after gen_stage2 (files written here replace stage 2 files of the same name).
"""
import json
from gen_resources import NS, A, D, write, ns, flat_item, adv

def gen_assets():
    flat_item("gingerbread_king_spawn_egg")

def gen_sounds():
    path = f"{A}/sounds.json"
    sounds = json.load(open(path, encoding="utf-8"))
    def ev(names, subtitle, pitch=1.0, volume=1.0):
        return {"subtitle": f"subtitles.{NS}.{subtitle}", "sounds": [{"name": s, "pitch": pitch, "volume": volume} for s in names]}
    # Provisional: re-pitched vanilla sounds until original recordings exist. A deep, grumpy villager voice for the King.
    sounds["entity.gingerbread_king.ambient"] = ev(["minecraft:mob/villager/idle1", "minecraft:mob/villager/idle2", "minecraft:mob/villager/idle3"],
                                                   "gingerbread_king.ambient", 0.55)
    sounds["entity.gingerbread_king.hurt"] = ev(["minecraft:mob/villager/hit1", "minecraft:mob/villager/hit2"], "gingerbread_king.hurt", 0.55)
    sounds["entity.gingerbread_king.death"] = ev(["minecraft:mob/villager/death"], "gingerbread_king.death", 0.45)
    sounds["entity.gingerbread_king.roar"] = ev(["minecraft:mob/ravager/roar1", "minecraft:mob/ravager/roar2"], "gingerbread_king.roar", 1.3)
    sounds["entity.gingerbread_king.slam"] = ev(["minecraft:random/explode1", "minecraft:random/explode2"], "gingerbread_king.slam", 0.8)
    sounds["entity.gingerbread_king.summon"] = ev(["minecraft:mob/evocation_illager/prepare_summon"], "gingerbread_king.summon", 1.2)
    write(path, sounds)

def gen_loot():
    def entity(name, pools):
        write(f"{D}/loot_table/entities/{name}.json", {"type": "minecraft:entity", "random_sequence": f"{NS}:entities/{name}", "pools": pools})
    def pool(item, lo, hi, looting=True, conditions=None):
        fns = [{"function": "minecraft:set_count", "count": {"type": "minecraft:uniform", "min": lo, "max": hi}, "add": False}]
        if looting:
            fns.append({"function": "minecraft:enchanted_count_increase", "enchantment": "minecraft:looting", "count": {"type": "minecraft:uniform", "min": 0.0, "max": 1.0}})
        p = {"rolls": 1.0, "bonus_rolls": 0.0, "entries": [{"type": "minecraft:item", "name": ns(item), "functions": fns}]}
        if conditions:
            p["conditions"] = conditions
        return p
    # The Warrior no longer carries the Brown Sugar Sword; it drops one now and then instead (5 %, +1 % per Looting level).
    sword_chance = [{"condition": "minecraft:killed_by_player"},
                    {"condition": "minecraft:random_chance_with_enchanted_bonus", "enchantment": "minecraft:looting", "unenchanted_chance": 0.05,
                     "enchanted_chance": {"type": "minecraft:linear", "base": 0.06, "per_level_above_first": 0.01}}]
    entity("gingerbread_warrior", [pool("gingerbread", 1.0, 2.0), pool("brown_sugar_sword", 1.0, 1.0, looting=False, conditions=sword_chance)])
    entity("gingerbread_king", [pool("gingerbread", 8.0, 14.0), pool("gumdrop", 4.0, 8.0), pool("frosted_gingerbread_block", 4.0, 8.0),
                                pool("minecraft:gold_ingot", 3.0, 6.0), pool("brown_sugar_sword", 1.0, 1.0, looting=False)])

def gen_advancements():
    adv("defeat_gingerbread_king", "visit_gingerbread_tower", "gingerbread_king_spawn_egg", "challenge", {"kill": {
        "trigger": "minecraft:player_killed_entity", "conditions": {"entity": [
            {"condition": "minecraft:entity_properties", "entity": "this", "predicate": {"type": f"{NS}:gingerbread_king"}}]}}},
        rewards={"experience": 100})

def generate():
    gen_assets(); gen_sounds(); gen_loot(); gen_advancements()
