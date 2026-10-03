#!/usr/bin/env python3
"""Builds the Gingerbread Tower structure template (data/crazyalloy_revival/structure/gingerbread_tower.nbt).

Revival take on the original tower: three floors of gingerbread with frosting bands, a ladder, two chests,
four spawners (Gingerbread Warriors below, Soldiers above) and, since stage 3, the Gingerbread King on the roof. Spawners use custom spawn rules so they work in any light.
Run: python3 tools/gen_tower.py   (requires nbtlib)
"""
import os
from nbtlib import File, Compound, List, Int, Short, String, Double, Byte
NS = "crazyalloy_revival"
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "data", NS, "structure", "gingerbread_tower.nbt")

W = D = 7
H = 14
blocks = {}

def put(x, y, z, name, nbt=None, **props):
    blocks[(x, y, z)] = (name if ":" in name else f"{NS}:{name}", props, nbt)

def spawner(mob):
    return Compound({"id": String("minecraft:mob_spawner"), "Delay": Short(20), "MinSpawnDelay": Short(200), "MaxSpawnDelay": Short(600),
        "SpawnCount": Short(2), "MaxNearbyEntities": Short(4), "RequiredPlayerRange": Short(16), "SpawnRange": Short(3),
        "SpawnData": Compound({"entity": Compound({"id": String(f"{NS}:{mob}")}), "custom_spawn_rules": Compound({
            "block_light_limit": List[Int]([Int(0), Int(15)]), "sky_light_limit": List[Int]([Int(0), Int(15)])})})})

def chest(facing):
    return dict(nbt=Compound({"id": String("minecraft:chest"), "LootTable": String(f"{NS}:chests/gingerbread_tower")}),
                facing=facing, type="single", waterlogged="false")

FLOORS = [0, 5, 9]
for x in range(W):
    for z in range(D):
        for y in range(H):
            put(x, y, z, "minecraft:air")
        edge = x in (0, W - 1) or z in (0, D - 1)
        corner = x in (0, W - 1) and z in (0, D - 1)
        for y in range(0, 12):
            if y in FLOORS:
                put(x, y, z, "gingerbread_block")
            elif edge:
                put(x, y, z, "frosted_gingerbread_block" if y in (4, 8) or corner else "gingerbread_block")
        # battlements
        if edge:
            put(x, 12, z, "frosted_gingerbread_block")
            if (x + z) % 2 == 0:
                put(x, 13, z, "gingerbread_slab", type="bottom", waterlogged="false")

# roof floor at y 12 inside
for x in range(1, W - 1):
    for z in range(1, D - 1):
        put(x, 12, z, "gingerbread_block")

# entrance (front = z 0) with stairs as an arch
put(3, 1, 0, "minecraft:air"); put(3, 2, 0, "minecraft:air")
put(2, 3, 0, "gingerbread_stairs", facing="east", half="top", shape="straight", waterlogged="false")
put(4, 3, 0, "gingerbread_stairs", facing="west", half="top", shape="straight", waterlogged="false")
put(3, 3, 0, "frosted_gingerbread_block")
put(3, 4, 0, "minecraft:red_wall_banner", facing="north")

# windows: candy fence panes on the upper floors
for y in (6, 7, 10):
    for (x, z) in [(3, 0), (0, 3), (6, 3), (3, 6)]:
        if (x, z) == (3, 0) and y == 6:
            continue
        put(x, y, z, "sweetwood_fence", north="false", east="false", south="false", west="false", waterlogged="false")

# ladder along the back wall (inside, z 5) through holes in both floors
for y in range(1, 12):
    put(3, y, 5, "minecraft:ladder", facing="north", waterlogged="false")
put(3, 12, 5, "minecraft:air")
put(3, 12, 4, "gingerbread_slab", type="bottom", waterlogged="false")

# ground floor: warriors
put(1, 1, 1, "minecraft:spawner", nbt=spawner("gingerbread_warrior"))
put(5, 1, 3, "minecraft:spawner", nbt=spawner("gingerbread_warrior"))
put(1, 1, 5, "minecraft:lantern", hanging="false", waterlogged="false")
# middle floor: soldiers and the first chest
put(1, 6, 1, "minecraft:spawner", nbt=spawner("gingerbread_soldier"))
put(5, 6, 1, "minecraft:spawner", nbt=spawner("gingerbread_soldier"))
put(5, 6, 5, "minecraft:chest", **chest("west"))
put(1, 6, 4, "minecraft:lantern", hanging="false", waterlogged="false")
# roof: the Gingerbread King (stage 3). He is finalized as a STRUCTURE spawn, which makes him persistent and
# lets the gingerbreadKingInTowers config remove him on his first tick.
KING = (3.5, 13.0, 2.5)
entities = [Compound({"pos": List[Double]([Double(c) for c in KING]), "blockPos": List[Int]([Int(int(c)) for c in KING]),
                      "nbt": Compound({"id": String(f"{NS}:gingerbread_king"), "PersistenceRequired": Byte(1)})})]
# top floor: second chest
put(1, 10, 5, "minecraft:chest", **chest("east"))
put(5, 10, 3, "frosted_gingerbread_block")
put(5, 11, 3, "minecraft:lantern", hanging="false", waterlogged="false")

palette, index, block_list = [], {}, []
for (pos, (name, props, nbt)) in sorted(blocks.items(), key=lambda kv: (kv[0][1], kv[0][2], kv[0][0])):
    key = (name, tuple(sorted(props.items())))
    if key not in index:
        entry = Compound({"Name": String(name)})
        if props:
            entry["Properties"] = Compound({k: String(v) for k, v in props.items()})
        index[key] = len(palette)
        palette.append(entry)
    b = Compound({"pos": List[Int]([Int(pos[0]), Int(pos[1]), Int(pos[2])]), "state": Int(index[key])})
    if nbt is not None:
        b["nbt"] = nbt
    block_list.append(b)

root = Compound({"DataVersion": Int(3953), "size": List[Int]([Int(W), Int(H), Int(D)]), "palette": List[Compound](palette),
                 "blocks": List[Compound](block_list), "entities": List[Compound](entities)})
os.makedirs(os.path.dirname(OUT), exist_ok=True)
File(root).save(OUT, gzipped=True)
print(f"gingerbread tower: {len(block_list)} blocks, {len(palette)} palette entries")
