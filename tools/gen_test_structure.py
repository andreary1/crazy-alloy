#!/usr/bin/env python3
"""Builds the empty platform used by the game tests (src/gametest/.../structure/gametest/platform.nbt).

A 9x5x9 box: a stone floor at y=0 and air above it. Run: python3 tools/gen_test_structure.py (requires nbtlib)
"""
import os
from nbtlib import File, Compound, List, Int, String
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "gametest", "resources",
                   "data", "crazyalloy_revival", "structure", "gametest", "platform.nbt")
W, H, D = 9, 5, 9
palette = [Compound({"Name": String("minecraft:stone")}), Compound({"Name": String("minecraft:air")})]
blocks = []
for y in range(H):
    for z in range(D):
        for x in range(W):
            blocks.append(Compound({"pos": List[Int]([Int(x), Int(y), Int(z)]), "state": Int(0 if y == 0 else 1)}))
root = Compound({
    "DataVersion": Int(3953),
    "size": List[Int]([Int(W), Int(H), Int(D)]),
    "palette": List[Compound](palette),
    "blocks": List[Compound](blocks),
    "entities": List[Compound]([]),
})
os.makedirs(os.path.dirname(OUT), exist_ok=True)
File(root).save(OUT, gzipped=True)
print(f"test platform: {len(blocks)} blocks")
