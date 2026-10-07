#!/usr/bin/env python3
"""Builds the stage 6 structure template: the Ice Cream Nest (data/crazyalloy_revival/structure/ice_cream_nest.nbt).

A round obsidian platform with a low rim, raised on a rough mound of mixed ice cream blocks (vanilla, chocolate,
strawberry and mint in uneven bands and patches), with the Ice Cream Dragon Egg in the middle. A chocolate brick
stair climbs the west side to a gap in the rim. Deterministic: the same seed gives the same nest every run.
Run: python3 tools/gen_stage6_structures.py   (requires nbtlib)
"""
import math, random
from gen_stage4_structures import Template, STAIR

SIZE, HEIGHT = 37, 14
C = 18             # centre (x and z)
TOP = 10           # platform layer
PLATFORM_R = 7.5
BASE_R = 10.5
FLAVOURS = ["vanilla_ice_cream_block", "chocolate_ice_cream_block", "strawberry_ice_cream_block", "mint_ice_cream_block"]


def nest():
    rnd = random.Random(61006)
    t = Template(SIZE, HEIGHT, SIZE)
    # Bands of flavour by height, broken up by patches so the mound reads as "mixed scoops", not stripes.
    band_offset = {(x, z): rnd.randint(-1, 1) for x in range(SIZE) for z in range(SIZE)}
    patches = [(rnd.uniform(0, SIZE), rnd.uniform(0, TOP), rnd.uniform(0, SIZE), rnd.uniform(1.8, 3.2), rnd.choice(FLAVOURS)) for _ in range(26)]
    wobble = [rnd.uniform(-0.8, 0.8) for _ in range(16)]

    def radius(y, ang):
        # Narrows from the base towards the top, with a lumpy outline (16 lobes around the mound).
        lobe = wobble[int((ang + math.pi) / (2 * math.pi) * 16) % 16]
        return BASE_R - (BASE_R - PLATFORM_R + 0.6) * (y / TOP) + lobe

    def flavour(x, y, z):
        for px, py, pz, pr, f in patches:
            if (x - px) ** 2 + ((y - py) * 1.6) ** 2 + (z - pz) ** 2 < pr * pr:
                return f
        return FLAVOURS[((y + band_offset[(x, z)]) // 3) % 4]

    for y in range(TOP):
        for x in range(SIZE):
            for z in range(SIZE):
                dx, dz = x - C, z - C
                if math.hypot(dx, dz) <= radius(y, math.atan2(dz, dx)):
                    t.put(x, y, z, flavour(x, y, z))
    # The platform and its rim (one block high, at the edge), with an entrance gap on the west side.
    for x in range(SIZE):
        for z in range(SIZE):
            d = math.hypot(x - C, z - C)
            if d <= PLATFORM_R:
                t.put(x, TOP, z, "minecraft:obsidian")
                gap = x < C and abs(z - C) <= 1
                if d > PLATFORM_R - 1.0 and not gap:
                    t.put(x, TOP + 1, z, "minecraft:obsidian")
                else:
                    for y in range(TOP + 1, HEIGHT):
                        t.put(x, y, z, "minecraft:air")  # keeps terrain from poking into the arena
    # West stair: three wide, one step up per block from the ground to the platform edge.
    first = C - int(PLATFORM_R) - 1 - TOP
    for k in range(TOP + 1):
        x = first + k
        for z in (C - 1, C, C + 1):
            t.put(x, k, z, "crazyalloy_revival:chocolate_brick_stairs", facing="east", **STAIR)
            for y in range(k):
                if t.get(x, y, z) is None:
                    t.put(x, y, z, "chocolate_ice_cream_block")
            for y in range(k + 1, min(k + 4, HEIGHT)):
                t.put(x, y, z, "minecraft:air")
    t.put(C, TOP + 1, C, "ice_cream_dragon_egg", summoning="false")
    t.save("ice_cream_nest")


if __name__ == "__main__":
    nest()
