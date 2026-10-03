#!/usr/bin/env python3
"""Builds the Cookie Hut structure template (data/crazyalloy_revival/structure/cookie_hut.nbt).

The hut is described here as layers so it can be reviewed and edited without a game client.
DataVersion 3953 (1.21) is used on purpose: the game upgrades older templates on load, and every
vanilla block used here exists unchanged in 26.1.2.
Run: python3 tools/gen_structure.py   (requires nbtlib)
"""
import os
from nbtlib import File, Compound, List, Int, String
NS = "crazyalloy_revival"
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "data", NS, "structure", "cookie_hut.nbt")

W = D = 7
H = 9
blocks = {}  # (x, y, z) -> (name, props, nbt)

def put(x, y, z, name, nbt=None, **props):
    blocks[(x, y, z)] = (name if ":" in name else f"{NS}:{name}", props, nbt)

for x in range(W):
    for z in range(D):
        put(x, 0, z, "chocolate_bricks")  # floor
        for y in range(1, H):
            put(x, y, z, "minecraft:air")  # clears terrain inside the footprint

for y in range(1, 5):
    for x in range(W):
        for z in range(D):
            edge_x = x in (0, W - 1)
            edge_z = z in (0, D - 1)
            if edge_x and edge_z:
                put(x, y, z, "sweetwood_log", axis="y")
            elif edge_x or edge_z:
                put(x, y, z, "sweetwood_planks")

# door (front = z 0), windows
put(3, 1, 0, "minecraft:air"); put(3, 2, 0, "minecraft:air")
for (x, z) in [(0, 3), (6, 3), (3, 6)]:
    put(x, 2, z, "minecraft:pink_stained_glass")

# roof: frosting, then stepped chocolate
for x in range(W):
    for z in range(D):
        put(x, 5, z, "minecraft:white_wool")
for x in range(1, 6):
    for z in range(1, 6):
        put(x, 6, z, "chocolate_block")
for x in range(2, 5):
    for z in range(2, 5):
        put(x, 7, z, "chocolate_bricks")
put(3, 8, 3, "chiseled_chocolate_bricks")

# interior
put(1, 1, 5, "minecraft:chest", nbt=Compound({"id": String("minecraft:chest"), "LootTable": String(f"{NS}:chests/cookie_hut")}),
    facing="south", type="single", waterlogged="false")
put(5, 1, 5, "minecraft:crafting_table")
put(5, 1, 1, "minecraft:lantern", hanging="false", waterlogged="false")
put(1, 1, 1, "lollipop_flower")
put(1, 0, 1, "chocolate_grass_block")
# path in front of the door
put(3, 0, 0, "chocolate_grass_block")

palette, index = [], {}
block_list = []
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

root = Compound({
    "DataVersion": Int(3953),
    "size": List[Int]([Int(W), Int(H), Int(D)]),
    "palette": List[Compound](palette),
    "blocks": List[Compound](block_list),
    "entities": List[Compound]([]),
})
os.makedirs(os.path.dirname(OUT), exist_ok=True)
File(root).save(OUT, gzipped=True)
print(f"cookie hut: {len(block_list)} blocks, {len(palette)} palette entries")
