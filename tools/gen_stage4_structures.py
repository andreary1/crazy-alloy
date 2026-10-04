#!/usr/bin/env python3
"""Builds the stage 4 structure templates: the Gingerbread Fortress and the Ice Cream Truck
(data/crazyalloy_revival/structure/{gingerbread_fortress,ice_cream_truck}.nbt).

Both are described in code, block by block, so they can be reviewed and edited without a game client.
Front = north (z 0) in template space; the game rotates the whole template when it places it.
DataVersion 3953 (1.21) like the other templates; the game upgrades it on load.
Run: python3 tools/gen_stage4_structures.py   (requires nbtlib)
"""
import os
from nbtlib import File, Compound, List, Int, Short, String, Double, Byte, Float
NS = "crazyalloy_revival"
OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "data", NS, "structure")


class Template:
    def __init__(self, w, h, d):
        self.w, self.h, self.d = w, h, d
        self.blocks = {}
        self.entities = []

    def put(self, x, y, z, name, nbt=None, **props):
        if not (0 <= x < self.w and 0 <= y < self.h and 0 <= z < self.d):
            raise SystemExit(f"block {name} at {x},{y},{z} is outside the {self.w}x{self.h}x{self.d} template")
        self.blocks[(x, y, z)] = (name if ":" in name else f"{NS}:{name}", props, nbt)

    def fill(self, x0, x1, y0, y1, z0, z1, name, **props):
        for x in range(min(x0, x1), max(x0, x1) + 1):
            for y in range(min(y0, y1), max(y0, y1) + 1):
                for z in range(min(z0, z1), max(z0, z1) + 1):
                    self.put(x, y, z, name, **props)

    def get(self, x, y, z):
        b = self.blocks.get((x, y, z))
        return b[0] if b else None

    def entity(self, mob, x, y, z, yaw=0.0, **extra):
        nbt = Compound({"id": String(f"{NS}:{mob}"), "PersistenceRequired": Byte(1),
                        "Rotation": List[Float]([Float(yaw), Float(0.0)])})
        nbt.update(extra)
        self.entities.append(Compound({"pos": List[Double]([Double(x), Double(y), Double(z)]),
                                       "blockPos": List[Int]([Int(int(x)), Int(int(y)), Int(int(z))]), "nbt": nbt}))

    def save(self, name):
        palette, index, block_list = [], {}, []
        for (pos, (bname, props, nbt)) in sorted(self.blocks.items(), key=lambda kv: (kv[0][1], kv[0][2], kv[0][0])):
            key = (bname, tuple(sorted(props.items())))
            if key not in index:
                entry = Compound({"Name": String(bname)})
                if props:
                    entry["Properties"] = Compound({k: String(v) for k, v in props.items()})
                index[key] = len(palette)
                palette.append(entry)
            b = Compound({"pos": List[Int]([Int(pos[0]), Int(pos[1]), Int(pos[2])]), "state": Int(index[key])})
            if nbt is not None:
                b["nbt"] = nbt
            block_list.append(b)
        root = Compound({"DataVersion": Int(3953), "size": List[Int]([Int(self.w), Int(self.h), Int(self.d)]),
                         "palette": List[Compound](palette), "blocks": List[Compound](block_list),
                         "entities": List[Compound](self.entities)})
        os.makedirs(OUT, exist_ok=True)
        File(root).save(os.path.join(OUT, f"{name}.nbt"), gzipped=True)
        print(f"{name}: {len(block_list)} blocks, {len(palette)} palette entries, {len(self.entities)} entities")


def spawner(mob, count=2, nearby=4):
    """A spawner that works in any light (custom spawn rules), like the Gingerbread Tower ones."""
    return Compound({"id": String("minecraft:mob_spawner"), "Delay": Short(20), "MinSpawnDelay": Short(240), "MaxSpawnDelay": Short(640),
        "SpawnCount": Short(count), "MaxNearbyEntities": Short(nearby), "RequiredPlayerRange": Short(16), "SpawnRange": Short(3),
        "SpawnData": Compound({"entity": Compound({"id": String(f"{NS}:{mob}")}), "custom_spawn_rules": Compound({
            "block_light_limit": List[Int]([Int(0), Int(15)]), "sky_light_limit": List[Int]([Int(0), Int(15)])})})})

def container(kind, loot):
    return Compound({"id": String(f"minecraft:{kind}"), "LootTable": String(f"{NS}:chests/{loot}")})

STAIR = dict(half="bottom", shape="straight", waterlogged="false")
NOT_WET = dict(waterlogged="false")
LEAVES = dict(distance="1", persistent="true", waterlogged="false")

# =============================================================================================== Gingerbread Fortress
def fortress():
    """A square fortress, 33 x 33: four corner towers that stand out of the wall line, high walls with wavy icing
    bands and battlements, a stepped main gate, a dark chocolate path through a courtyard with candy trees and two
    raised soldier platforms, and the King's throne on a stepped dais at the back. Functional parts (revival
    proposal): stairs up to the wall walk, guard rooms in the front towers, a storeroom and living quarters in the
    back towers, spawners for Warriors and Soldiers, and placed guards that defend the way to the King."""
    S, H = 33, 22
    t = Template(S, H, S)
    G, F = "gingerbread_block", "frosted_gingerbread_block"
    t.fill(0, S - 1, 0, H - 1, 0, S - 1, "minecraft:air")

    # ground: gingerbread under the walls, frosted grass in the courtyard, a dark chocolate path to the throne
    t.fill(2, 30, 0, 0, 2, 30, G)
    t.fill(4, 28, 0, 0, 4, 28, "chocolate_grass_block")
    t.fill(15, 17, 0, 0, 0, 21, "chocolate_bricks")

    def icing(along, y, base_y):
        # Wavy band of icing: two blocks up, two blocks down along the wall.
        return y == base_y + (1 if along % 4 >= 2 else 0)

    def wall_block(along, y):
        return F if icing(along, y, 3) or icing(along, y, 7) else G

    # curtain walls (2 thick, 9 high) between the towers, set 2 blocks in from the towers' outer faces
    for a in range(7, 26):
        for y in range(1, 10):
            for (x, z) in [(a, 2), (a, 3), (a, 29), (a, 30), (2, a), (3, a), (29, a), (30, a)]:
                t.put(x, y, z, wall_block(a, y))
        # battlements on the outer edge: alternate merlons
        if a % 2 == 1:
            for (x, z) in [(a, 2), (a, 30), (2, a), (30, a)]:
                t.put(x, 10, z, F)

    # corner towers: 7 x 7, three levels (ground room, wall-walk level, open lookout) and a low stepped roof
    def tower(mx, mz):
        X = (lambda x: x) if not mx else (lambda x: S - 1 - x)
        Z = (lambda z: z) if not mz else (lambda z: S - 1 - z)
        def p(x, y, z, name, **props):
            t.put(X(x), y, Z(z), name, **props)
        def face(dx, dz):
            # Stair facings as seen from the template's front-left tower, mirrored with the tower.
            if dx and mx: dx = -dx
            if dz and mz: dz = -dz
            return {(1, 0): "east", (-1, 0): "west", (0, 1): "south", (0, -1): "north"}[(dx, dz)]
        for x in range(7):
            for z in range(7):
                edge = x in (0, 6) or z in (0, 6)
                corner = x in (0, 6) and z in (0, 6)
                p(x, 0, z, G)
                for y in range(1, 15):
                    if edge:
                        along = x if z in (0, 6) else z
                        p(x, y, z, F if corner and y in (4, 9, 14) or icing(along, y, 3) or icing(along, y, 7) or icing(along, y, 11) else G)
                for y in (9, 14):
                    p(x, y, z, G if not edge else F)
                if corner:
                    for y in range(15, 18):
                        p(x, y, z, F)  # lookout pillars
                elif edge:
                    p(x, 15, z, "gingerbread_slab", type="bottom", **NOT_WET)
        # stepped roof over the lookout: three rings of stairs climbing to a point
        for ring, y in [(0, 18), (1, 19), (2, 20)]:
            lo, hi = ring, 6 - ring
            for x in range(lo, hi + 1):
                for z in range(lo, hi + 1):
                    if x in (lo, hi) or z in (lo, hi):
                        if x in (lo, hi) and z in (lo, hi):
                            p(x, y, z, F)
                        else:
                            fx = 1 if x == lo else -1 if x == hi else 0
                            fz = 1 if z == lo else -1 if z == hi else 0
                            p(x, y, z, "gingerbread_stairs", facing=face(fx, fz), **STAIR)
                    else:
                        p(x, y, z, G)
        p(3, 21, 3, "gingerbread_slab", type="bottom", **NOT_WET)
        # ladder in the corner of the room, through both floors
        for y in range(1, 15):
            p(1, y, 3, "minecraft:ladder", facing=face(1, 0), **NOT_WET)
        # door to the courtyard and openings onto both wall walks
        for y in (1, 2):
            p(6, y, 5, "minecraft:air")
        for y in (10, 11):
            for k in (2, 3):
                p(6, y, k, "minecraft:air")
                p(k, y, 6, "minecraft:air")
        # arrow slits on the two outer faces
        for y in (11, 12):
            p(3, y, 0, "minecraft:air")
            p(0, y, 4, "minecraft:air")  # not z 3: the ladder hangs on that wall
        p(4, 10, 4, "minecraft:lantern", hanging="false", **NOT_WET)
        p(4, 1, 1, "minecraft:lantern", hanging="false", **NOT_WET)
        return p, face

    fl = tower(False, False)   # front left: guard room
    fr = tower(True, False)    # front right: guard room
    bl = tower(False, True)    # back left: storeroom
    br = tower(True, True)     # back right: living quarters
    for p, _ in (fl, fr):
        p(3, 1, 3, "minecraft:spawner", nbt=spawner("gingerbread_warrior"))
    p, face = bl
    p(1, 1, 1, "minecraft:chest", nbt=container("chest", "gingerbread_fortress"), facing=face(1, 0), type="single", **NOT_WET)
    p(5, 1, 1, "minecraft:chest", nbt=container("chest", "gingerbread_fortress"), facing=face(-1, 0), type="single", **NOT_WET)
    for x in (2, 3, 4):
        p(x, 1, 1, "minecraft:barrel", nbt=container("barrel", "gingerbread_fortress"), facing="up", open="false")
    p(3, 2, 1, "minecraft:barrel", nbt=container("barrel", "gingerbread_fortress"), facing="up", open="false")
    p, face = br
    for x in (3, 5):
        p(x, 1, 2, "minecraft:red_bed", facing=face(0, -1), part="head", occupied="false")
        p(x, 1, 3, "minecraft:red_bed", facing=face(0, -1), part="foot", occupied="false")
    p(4, 1, 1, "minecraft:chest", nbt=container("chest", "gingerbread_fortress_quarters"), facing=face(0, 1), type="single", **NOT_WET)
    for x in range(2, 6):
        p(x, 1, 5, "minecraft:red_carpet")

    # main gate: a wide opening with a stepped outline, framed by icing, between two stepped pillars
    for x in range(14, 19):
        for y in range(1, 6):
            for z in (2, 3):
                t.put(x, y, z, "minecraft:air")
    for x in (15, 16, 17):
        for z in (2, 3):
            t.put(x, 6, z, "minecraft:air")
    for z in (2, 3):
        t.put(16, 7, z, "minecraft:air")
        for (x, y) in [(13, 1), (13, 2), (13, 3), (13, 4), (13, 5), (19, 1), (19, 2), (19, 3), (19, 4), (19, 5),
                       (14, 6), (18, 6), (15, 7), (17, 7), (16, 8)]:
            t.put(x, y, z, F)
    for x, top in [(12, 11), (20, 11), (13, 9), (19, 9)]:
        for y in range(0, top + 1):
            for z in (0, 1):
                t.put(x, y, z, F if y in (4, 8, top) else G)

    # stairs from the courtyard up to the side wall walks
    for i in range(9):
        y, z = 1 + i, 8 + i
        for x in (4, 28):
            t.put(x, y, z, "gingerbread_stairs", facing="south", **STAIR)
            for yy in range(1, y):
                t.put(x, yy, z, G)

    # two raised platforms for the soldiers on either side of the path, each with stairs from the path
    for x0, x1, sx, sface in [(6, 10, (13, 12, 11), "west"), (22, 26, (19, 20, 21), "east")]:
        t.fill(x0, x1, 1, 3, 10, 15, G)
        for x in range(x0, x1 + 1):
            for z in range(10, 16):
                if x in (x0, x1) or z in (10, 15):
                    t.put(x, 3, z, F)
        for i, x in enumerate(sx):
            for z in (12, 13):
                t.put(x, i + 1, z, "gingerbread_stairs", facing=sface, **STAIR)
                for yy in range(1, i + 1):
                    t.put(x, yy, z, G)
        cx = (x0 + x1) // 2
        t.put(cx, 4, 11, "minecraft:spawner", nbt=spawner("gingerbread_soldier", count=1, nearby=3))
        t.entity("gingerbread_soldier", cx + 0.5, 4.0, 14.5, yaw=180.0)

    # candy trees in the courtyard (sweetwood with cotton candy leaves that never decay)
    def tree(x, z):
        for y in range(1, 6):
            t.put(x, y, z, "sweetwood_log", axis="y")
        for y, r in [(4, 2), (5, 2), (6, 1), (7, 0)]:
            for dx in range(-r, r + 1):
                for dz in range(-r, r + 1):
                    if abs(dx) == r and abs(dz) == r and r > 1:
                        continue
                    if t.get(x + dx, y, z + dz) == "minecraft:air":
                        t.put(x + dx, y, z + dz, "cotton_candy_leaves", **LEAVES)
    for (x, z) in [(9, 7), (23, 7), (7, 21), (25, 21)]:
        tree(x, z)

    # lanterns along the path
    for (x, z) in [(14, 6), (18, 6), (14, 18), (18, 18)]:
        t.put(x, 1, z, "minecraft:lantern", hanging="false", **NOT_WET)

    # the throne: a stepped dais with icing on every step, a seat with armrests and a tall back with a crown
    for i, (x0, x1, z0) in enumerate([(11, 21, 21), (12, 20, 22), (13, 19, 23)]):
        y = i + 1
        for x in range(x0, x1 + 1):
            for z in range(z0, 29):
                t.put(x, y, z, F if z == z0 or x in (x0, x1) else G)
    for x in (15, 16, 17):
        t.put(x, 4, 26, "gingerbread_stairs", facing="south", **STAIR)
    for x in (14, 18):
        for z in (25, 26):
            t.put(x, 4, z, "gingerbread_slab", type="bottom", **NOT_WET)
    for x in range(14, 19):
        for y in range(4, 11):
            for z in (27, 28):
                t.put(x, y, z, F if x in (14, 18) or y == 10 else G)
    for x in (14, 16, 18):
        t.put(x, 11, 27, F)
    t.put(16, 12, 27, "minecraft:gold_block")
    t.put(16, 8, 26, "minecraft:red_wall_banner", facing="north")

    # the King waits on the top step in front of his throne, two Warriors at the foot of the dais
    t.entity("gingerbread_king", 16.5, 4.0, 24.5, yaw=180.0, FromFortress=Byte(1))
    t.entity("gingerbread_warrior", 12.5, 3.0, 22.5, yaw=180.0)
    t.entity("gingerbread_warrior", 20.5, 3.0, 22.5, yaw=180.0)
    t.save("gingerbread_fortress")

# =============================================================================================== Ice Cream Truck
def truck():
    """A small ice cream truck, 11 long: white body with light pink trim on four black square wheels, the cab at the
    east end. The north side opens into a big serving window with a white counter that sticks out and pink glass at
    its edges, between two pink banners with ice cream cones (both long sides have the banners). Stepped pink and
    white roof with a giant leaning ice cream on top. Inside (revival proposal): the Ice Cream Vendor, an ice cream
    machine and ingredient storage."""
    W, H, D = 11, 14, 7
    t = Template(W, H, D)
    WH, PK = "minecraft:white_concrete", "minecraft:pink_concrete"
    t.fill(0, W - 1, 0, H - 1, 0, D - 1, "minecraft:air")
    for x in (1, 2, 8, 9):
        for z in (1, 5):
            t.put(x, 0, z, "minecraft:black_concrete")
    t.fill(0, 10, 1, 1, 1, 5, "minecraft:smooth_quartz")

    # service box (x 0..7) and cab (x 8..10)
    for x in range(0, 11):
        for z in range(1, 6):
            top = 5 if x <= 7 else 4
            for y in range(2, top + 1):
                if x <= 7 and (x in (0, 7) or z in (1, 5)):
                    t.put(x, y, z, PK if y == 2 else WH)
                elif x >= 8 and (x == 10 or z in (1, 5)):
                    t.put(x, y, z, PK if y == 2 else WH)
    # roofs: the service box is higher, in two pink-trimmed steps
    for x in range(0, 8):
        for z in range(1, 6):
            t.put(x, 6, z, PK if x in (0, 7) or z in (1, 5) else WH)
    for x in range(1, 7):
        for z in range(2, 5):
            t.put(x, 7, z, PK if x in (1, 6) or z in (2, 4) else WH)
    for x in range(8, 11):
        for z in range(1, 6):
            t.put(x, 5, z, WH if 1 < z < 5 else PK)
    # cab: windscreen, side windows, headlights, driver's seat; a doorway from the service box
    for z in (2, 3, 4):
        for y in (3, 4):
            t.put(10, y, z, "minecraft:light_gray_stained_glass")
    for z in (1, 5):
        for y in (3, 4):
            t.put(9, y, z, "minecraft:light_gray_stained_glass")
        t.put(10, 2, z, "minecraft:sea_lantern")
    t.put(9, 2, 3, "minecraft:quartz_stairs", facing="west", **STAIR)
    t.put(7, 2, 3, "minecraft:air")
    t.put(7, 3, 3, "minecraft:air")

    # serving window on the north side, framed by pink glass, with the counter sticking out
    for x in range(2, 6):
        for y in (3, 4):
            t.put(x, y, 1, "minecraft:pink_stained_glass" if x in (2, 5) else "minecraft:air")
        t.put(x, 2, 0, "minecraft:smooth_quartz_slab", type="top", **NOT_WET)
        t.put(x, 2, 2, "minecraft:smooth_quartz_slab", type="top", **NOT_WET)
    # ice cream banners on both long sides
    cone = List[Compound]([Compound({"pattern": String(f"{NS}:ice_cream_cone"), "color": String("brown")}),
                           Compound({"pattern": String(f"{NS}:ice_cream_scoop"), "color": String("white")})])
    for x in (1, 6):
        t.put(x, 4, 0, "minecraft:pink_wall_banner", nbt=Compound({"id": String("minecraft:banner"), "patterns": cone}), facing="north")
        t.put(x, 4, 6, "minecraft:pink_wall_banner", nbt=Compound({"id": String("minecraft:banner"), "patterns": cone}), facing="south")

    # the giant ice cream on the roof: a stepped wafer cone leaning east, then the white scoop
    t.put(3, 8, 3, "gingerbread_block")
    for (x, z) in [(4, 3), (3, 3), (5, 3), (4, 2), (4, 4)]:
        t.put(x, 9, z, "gingerbread_block")
    for x in range(4, 7):
        for z in range(2, 5):
            t.put(x, 10, z, "frosted_gingerbread_block" if (x + z) % 2 else "gingerbread_block")
            t.put(x, 11, z, "minecraft:white_wool")
            if not (x in (4, 6) and z in (2, 4)):
                t.put(x, 12, z, "minecraft:white_wool")
    t.put(5, 13, 3, "minecraft:white_wool")

    # inside: ice cream machine, storage, a lamp and the door at the back
    t.put(1, 2, 4, "minecraft:iron_block")
    t.put(1, 3, 4, "minecraft:powder_snow_cauldron", level="3")
    t.put(2, 2, 4, "minecraft:quartz_block")
    t.put(2, 3, 4, "minecraft:lever", face="floor", facing="south", powered="false")
    t.put(5, 2, 4, "minecraft:chest", nbt=container("chest", "ice_cream_truck"), facing="north", type="single", **NOT_WET)
    t.put(6, 2, 4, "minecraft:barrel", nbt=container("barrel", "ice_cream_truck"), facing="north", open="false")
    t.put(4, 5, 3, "minecraft:lantern", hanging="true", **NOT_WET)
    t.put(0, 2, 3, "sweetwood_door", facing="west", half="lower", hinge="left", open="false", powered="false")
    t.put(0, 3, 3, "sweetwood_door", facing="west", half="upper", hinge="left", open="false", powered="false")

    t.entity("ice_cream_vendor", 3.5, 2.0, 3.5, yaw=180.0)
    t.save("ice_cream_truck")

if __name__ == "__main__":
    fortress()
    truck()
