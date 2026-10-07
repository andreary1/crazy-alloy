#!/usr/bin/env python3
"""Stage 6 textures for Crazy Alloy: Revival: the Ice Cream Dimension.

Blocks: vanilla, chocolate, strawberry and mint ice cream blocks, the animated portal surface and the Ice Cream
Dragon egg. Items: mint ice cream, Ultimate Ice Cream, Ice Cream Amulet and twelve spawn eggs. Creatures: the four
Ice Cream Zombies, the Ice Cream Beast, the Ice Cream Gargoyle, the four Living Ice Creams, the Angry Ice Cream Cone
and the Ice Cream Dragon.

Creature textures use the same method as gen_mobs_v3.py: every cube line in the Java model carries a "// tex:<name>"
tag and this script paints each tagged region. Original art drawn in code from the project owner's written
descriptions of his reference images; no pixels copied from anywhere.
Run by tools/gen_textures.py after the stage 5 textures.
"""
import math, random
from PIL import Image
from gen_textures_v2 import hexc, shade, mix, ramp, art, save, blank, smooth_noise, outline
from gen_mobs_v3 import paint, fill, WHITE, CLEAR
from gen_mobs_v4 import SCOOP_ROWS, CONE_ROWS, CONE_PAL

rng = random.Random(60)

# Flavour palettes: base, light, shadow, and an accent used for chips, seeds and specks.
FLAVOURS = {
    "vanilla": {"base": hexc("f6ecd2"), "light": hexc("fffaf0"), "dark": hexc("e1d0ad"), "accent": hexc("3a2a1c")},
    "chocolate": {"base": hexc("7b4a2e"), "light": hexc("93603c"), "dark": hexc("5c3420"), "accent": hexc("4a2716")},
    "strawberry": {"base": hexc("f4a6c4"), "light": hexc("fcc6da"), "dark": hexc("de7fa6"), "accent": hexc("c23b62")},
    "mint": {"base": hexc("9fe7c8"), "light": hexc("c4f5df"), "dark": hexc("78cfa9"), "accent": hexc("4a2716")},
}
WAFER = hexc("e2b066")
WAFER_DARK = hexc("b07a35")
EYE = hexc("1a1210")

# ================================================================== blocks (16x16)
def ice_cream_block(name, f, seed):
    """Soft scooped ice cream: round lumps (light tops, shaded undersides) with flavour specks."""
    n = smooth_noise(16, seed, 3.0)
    im = blank(); px = im.load()
    lumps = [(rng.uniform(0, 16), rng.uniform(0, 16), rng.uniform(3.0, 5.0)) for _ in range(7)]
    for y in range(16):
        for x in range(16):
            v = n[y][x]
            # Shade by the nearest lump: light at its top-left, dark at its bottom-right edge.
            best = None
            for (cx, cy, r) in lumps:
                for ox in (-16, 0, 16):
                    for oy in (-16, 0, 16):
                        dx, dy = x + 0.5 - (cx + ox), y + 0.5 - (cy + oy)
                        d = math.hypot(dx, dy) / r
                        if best is None or d < best[0]:
                            best = (d, dx, dy)
            d, dx, dy = best
            light = -(dx + dy) / 6.0
            c = f["base"]
            if d > 0.92:
                c = f["dark"]
            elif light > 0.35 and d < 0.7:
                c = f["light"]
            elif v > 0.7:
                c = mix(f["base"], f["light"], 0.5)
            elif v < 0.25:
                c = mix(f["base"], f["dark"], 0.5)
            px[x, y] = c
    # Specks: vanilla bean dots, chocolate chunks, strawberry pieces, mint chocolate chips.
    count = {"vanilla": 7, "chocolate": 5, "strawberry": 6, "mint": 7}[name]
    for _ in range(count):
        x, y = rng.randrange(16), rng.randrange(16)
        px[x, y] = f["accent"]
        if name in ("mint", "strawberry", "chocolate") and rng.random() < 0.6:
            px[(x + 1) % 16, y] = shade(f["accent"], 1.15)
    save(im, f"block/{name}_ice_cream_block.png")

def portal():
    """Animated swirl of melted chocolate with strawberry, vanilla and mint ribbons (16 frames, translucent)."""
    frames = 16
    im = Image.new("RGBA", (16, 16 * frames), (0, 0, 0, 0)); px = im.load()
    cols = [hexc("5a321e", 200), hexc("7b4a2e", 190), hexc("a4673f", 180), hexc("f4a6c4", 185), hexc("fff4de", 185), hexc("9fe7c8", 185)]
    for fr in range(frames):
        t = fr / frames * math.tau
        for y in range(16):
            for x in range(16):
                dx, dy = x - 7.5, y - 7.5
                r = math.hypot(dx, dy)
                a = math.atan2(dy, dx)
                v = math.sin(a * 2 + r * 0.7 - t) + 0.5 * math.sin(r * 1.3 + t * 2)
                k = int((v + 1.5) / 3.0 * len(cols))
                px[x, 16 * fr + y] = cols[max(0, min(len(cols) - 1, k))]
    save(im, "block/ice_cream_portal.png")
    import os, json
    from gen_textures_v2 import T
    with open(os.path.join(T, "block", "ice_cream_portal.png.mcmeta"), "w", encoding="utf-8") as fh:
        json.dump({"animation": {"frametime": 2, "interpolate": True}}, fh, indent=2)
        fh.write("\n")

def dragon_egg():
    """Cream shell with pink, mint and chocolate spots (used on the vanilla dragon egg model shape)."""
    im = blank(); px = im.load()
    base = hexc("fff1dc")
    for y in range(16):
        for x in range(16):
            px[x, y] = shade(base, 0.94 + 0.08 * rng.random())
    spots = [hexc("f4a6c4"), hexc("9fe7c8"), hexc("7b4a2e"), hexc("f4a6c4"), hexc("9fe7c8")]
    for i in range(14):
        cx, cy = rng.randrange(16), rng.randrange(16)
        c = spots[i % len(spots)]
        for (ox, oy) in ((0, 0), (1, 0), (0, 1), (1, 1)) if i % 3 else ((0, 0), (1, 0)):
            px[(cx + ox) % 16, (cy + oy) % 16] = c
    save(im, "block/ice_cream_dragon_egg.png")

# ================================================================== items (16x16)
def items():
    f = FLAVOURS["mint"]
    pal = dict(CONE_PAL, o=shade(f["base"], 0.6), S=f["base"], H=f["light"], D=f["dark"])
    mint = art(SCOOP_ROWS + CONE_ROWS, pal)
    mint.putpixel((6, 4), f["accent"]); mint.putpixel((9, 5), f["accent"]); mint.putpixel((5, 6), f["accent"])
    save(mint, "item/mint_ice_cream.png")
    # Ultimate: four flavours in quarters (mint and chocolate on top, vanilla and strawberry below, like the recipe).
    ultimate = [
        "......oooo......",
        "....oMMHCCoo....",
        "...oMMMMCCCCo...",
        "...oMMMMCCCCo...",
        "..oVVVVVSSSSSo..",
        "..oVHVVVSHSSSo..",
        "..oVVVVVSSSSDo..",
        "..oVDVVDSSDDDo..",
    ] + CONE_ROWS
    up = dict(CONE_PAL, o=hexc("6b3d14"), M=FLAVOURS["mint"]["base"], C=FLAVOURS["chocolate"]["light"], H=hexc("ffffff"),
              V=FLAVOURS["vanilla"]["base"], S=FLAVOURS["strawberry"]["base"], D=FLAVOURS["strawberry"]["dark"])
    save(art(ultimate, up), "item/ultimate_ice_cream.png")
    amulet = [
        "....gg....gg....",
        "...g..g..g..g...",
        "..g....gg....g..",
        "..g..........g..",
        "...g........g...",
        "....g......g....",
        ".....gg..gg.....",
        ".......GG.......",
        "......kSSk......",
        ".....kMHSCk.....",
        ".....kMMCCk.....",
        ".....kVVPPk.....",
        "......kCck......",
        "......kcCk......",
        ".......kk.......",
        "................",
    ]
    save(art(amulet, {"g": hexc("e8c25a"), "G": hexc("b8902a"), "k": hexc("6b3d14"), "S": hexc("fff4de"), "H": hexc("ffffff"),
                      "M": FLAVOURS["mint"]["base"], "C": FLAVOURS["chocolate"]["light"], "V": FLAVOURS["vanilla"]["base"],
                      "P": FLAVOURS["strawberry"]["base"], "c": hexc("a8692c")}), "item/ice_cream_amulet.png")

EGG = [
    "................",
    "......kkkk......",
    ".....kBBBBk.....",
    "....kBBsBBBk....",
    "....kBBBBBBk....",
    "...kBsBBBBsBk...",
    "...kBBBBBBBBk...",
    "..kBBBBsBBBBBk..",
    "..kBBBBBBBBsBk..",
    "..kBsBBBBBBBDk..",
    "..kBBBBBsBBBDk..",
    "...kBBBBBBBDk...",
    "...kDBBsBBDDk...",
    "....kDDDDDDk....",
    ".....kkkkkk.....",
    "................",
]

def spawn_egg(name, base, spots):
    pal = {"k": shade(base, 0.45), "B": base, "D": shade(base, 0.85), "s": spots}
    save(art(EGG, pal), f"item/{name}_spawn_egg.png")

def spawn_eggs():
    for fl in ("chocolate", "vanilla", "strawberry", "mint"):
        f = FLAVOURS[fl]
        spawn_egg(f"{fl}_ice_cream_zombie", f["base"], EYE)
        spawn_egg(f"living_{fl}_ice_cream", WAFER, f["base"])
    spawn_egg("ice_cream_beast", FLAVOURS["vanilla"]["base"], FLAVOURS["strawberry"]["dark"])
    spawn_egg("ice_cream_gargoyle", FLAVOURS["chocolate"]["base"], hexc("d4358f"))
    spawn_egg("angry_ice_cream_cone", WAFER, hexc("b3261e"))
    spawn_egg("ice_cream_dragon", FLAVOURS["vanilla"]["base"], FLAVOURS["mint"]["dark"])

# ================================================================== creature painters
def cream(f, spec=0.04, chips=0.0):
    """Ice cream surface: close shades of the flavour, light top row, darker bottom row, sparse specks."""
    p = [f["dark"], mix(f["base"], f["dark"], 0.4), f["base"], f["base"], mix(f["base"], f["light"], 0.5), f["light"]]
    def g(x, y, w, h):
        c = p[1 + rng.randrange(4)]
        if y == 0 and h > 2:
            c = p[5]
        elif y == h - 1 and h > 2:
            c = p[0]
        r = rng.random()
        if r < chips:
            c = f["accent"]
        elif r < chips + spec:
            c = f["light"]
        return c
    return g

def wafer(x, y, w, h):
    """Yellow wafer with the checked grid of diagonal ridges."""
    if (x + y) % 4 == 0 or (x - y) % 4 == 0:
        return WAFER_DARK
    return shade(WAFER, 0.97 + 0.06 * rng.random())

def face(f, eyes, mouth=None, brow=None, width_eye=1):
    """A face painter: small dark eyes at the given (x, y) positions on top of a flavour surface."""
    base = cream(f, spec=0.02)
    def g(x, y, w, h):
        for (ex, ey) in eyes:
            if ey == y and ex <= x < ex + width_eye:
                return EYE
        if mouth and y == mouth[1] and mouth[0] <= x < mouth[0] + mouth[2]:
            return shade(f["dark"], 0.7)
        if brow and (x, y) in brow:
            return EYE
        return base(x, y, w, h)
    return g

# ================================================================== Ice Cream Zombies (64x32)
def zombies():
    for fl, f in FLAVOURS.items():
        chips = 0.05 if fl in ("mint", "chocolate") else 0.03 if fl == "strawberry" else 0.02
        body = cream(f, chips=chips)
        painters = {
            "head": {"side": cream(f, chips=chips), "front": face(f, [(1, 3), (5, 3)], mouth=(2, 5, 3))},
            "body": body, "arm": cream(f, chips=chips), "leg": cream(f, chips=chips),
        }
        paint(f"{fl}_ice_cream_zombie", "IceCreamZombieModel.java", 64, 32, painters)

# ================================================================== Ice Cream Beast (128x64)
def beast():
    van, choc, straw, mint = FLAVOURS["vanilla"], FLAVOURS["chocolate"], FLAVOURS["strawberry"], FLAVOURS["mint"]
    def drip_torso(x, y, w, h):
        # Vanilla torso with strawberry drips running down from the neck.
        if y < 2 + ((x * 5) % 3) and h > 6:
            return straw["base"] if rng.random() < 0.85 else straw["light"]
        return cream(van)(x, y, w, h)
    painters = {
        "leg": cream(mint, chips=0.04),
        "torso": {"side": drip_torso, "top": cream(straw)},
        "head": {"side": cream(straw), "front": face(straw, [(2, 4), (5, 4)], mouth=(3, 6, 2))},
        "arm": cream(choc, chips=0.03),
        "hat_rim": {"side": wafer, "top": wafer, "bottom": fill(WAFER_DARK)},
        "hat_mid": wafer, "hat_top": wafer, "hat_tip": wafer,
    }
    paint("ice_cream_beast", "IceCreamBeastModel.java", 128, 64, painters)

# ================================================================== Ice Cream Gargoyle (64x32)
def gargoyle():
    van, choc = FLAVOURS["vanilla"], FLAVOURS["chocolate"]
    pink = {"base": hexc("d4358f"), "light": hexc("e866aa"), "dark": hexc("a8256f"), "accent": hexc("8a1d5a")}
    def head_side(x, y, w, h):
        return cream(van, spec=0.02)(x, y, w, h)
    def head_front(x, y, w, h):
        # Dark marks around the eyes, small pale pupils.
        if y in (1, 2) and x in (0, 1, 4, 5):
            return hexc("3a2a2a") if not (y == 2 and x in (1, 4)) else hexc("fff4de")
        return cream(van, spec=0.02)(x, y, w, h)
    def snout_front(x, y, w, h):
        if y == 1 and x in (0, 3):
            return shade(van["dark"], 0.6)  # nostrils
        return cream(van)(x, y, w, h)
    def wing(x, y, w, h):
        # Caramel wafer membrane: grid ridges plus darker spars.
        if x % 3 == 0:
            return hexc("8c5a22")
        return wafer(x, y, w, h)
    painters = {
        "body": cream(choc, chips=0.03),
        "head": {"side": head_side, "front": head_front},
        "snout": {"side": cream(van), "front": snout_front},
        "horn": {"side": lambda x, y, w, h: van["light"] if y < h - 1 else van["dark"], "top": fill(van["light"])},
        "limb": cream(pink),
        "wing": wing,
    }
    paint("ice_cream_gargoyle", "IceCreamGargoyleModel.java", 64, 32, painters)

# ================================================================== Living Ice Creams and Angry Cone (64x32)
def living():
    leg = lambda x, y, w, h: hexc("fff4de") if y < h - 1 else hexc("e6d6bc")
    for fl, f in FLAVOURS.items():
        chips = 0.06 if fl in ("mint", "chocolate") else 0.04 if fl == "strawberry" else 0.02
        painters = {
            "scoop": {"side": cream(f, chips=chips), "top": cream(f, chips=chips)},
            "drip": {"side": lambda x, y, w, h, f=f: f["base"] if (y == 0 or (x * 7) % 5 == 0) else CLEAR,
                     "top": cream(f), "bottom": fill(f["dark"])},
            "cone_top": {"side": wafer, "front": lambda x, y, w, h: EYE if y == 1 and x in (1, 4) else wafer(x, y, w, h),
                         "top": fill(shade(f["dark"], 0.9))},
            "cone_mid": wafer, "cone_tip": wafer, "leg": leg, "foot": fill(hexc("e6d6bc")),
        }
        paint(f"living_{fl}_ice_cream", "LivingIceCreamModel.java", 64, 32, painters)

def angry_cone():
    leg = lambda x, y, w, h: hexc("fff4de") if y < h - 1 else hexc("e6d6bc")
    def angry_face(x, y, w, h):
        # Slanted brows over small eyes and a downturned mouth.
        if (x, y) in {(0, 0), (1, 1), (5, 0), (4, 1)}:
            return EYE
        if y == 2 and x in (1, 4):
            return hexc("b3261e")
        if y == 3 and 2 <= x <= 3:
            return shade(WAFER_DARK, 0.6)
        return wafer(x, y, w, h)
    painters = {
        "cone_top": {"side": wafer, "front": angry_face, "top": fill(hexc("6b3d14")), "bottom": fill(WAFER_DARK)},
        "cone_mid": wafer, "cone_tip": wafer, "leg": leg, "foot": fill(hexc("e6d6bc")), "arm": fill(WAFER),
    }
    paint("angry_ice_cream_cone", "AngryIceCreamConeModel.java", 64, 32, painters)

# ================================================================== Ice Cream Dragon (128x128)
def dragon():
    van, choc, straw, mint = FLAVOURS["vanilla"], FLAVOURS["chocolate"], FLAVOURS["strawberry"], FLAVOURS["mint"]
    def body_side(x, y, w, h):
        # Chocolate torso with a paler vanilla belly band along the bottom.
        if y >= h - 3:
            return cream(van)(x, y, w, h)
        return cream(choc, chips=0.02)(x, y, w, h)
    def head_side(x, y, w, h):
        # Yellow eye with a dark slit near the front of each side.
        if y in (2, 3) and x in (w - 4, w - 3) or y in (2, 3) and x in (2, 3):
            return hexc("f5c518") if not (x in (w - 3, 3) and y == 3) else EYE
        return cream(van, spec=0.02)(x, y, w, h)
    def mouth_inside(x, y, w, h):
        return hexc("e8b84a") if rng.random() < 0.7 else hexc("d99a3a")
    painters = {
        "body": {"side": body_side, "top": cream(choc, chips=0.02), "bottom": cream(van)},
        "leg": cream(straw), "foot": {"side": cream(straw), "top": cream(straw), "bottom": fill(straw["dark"])},
        "neck": {"side": cream(van), "bottom": cream(straw)},
        "head": {"side": head_side, "front": cream(van), "bottom": fill(hexc("d99a3a"))},
        "snout": {"side": cream(van), "front": lambda x, y, w, h: shade(van["dark"], 0.6) if y == 1 and x in (1, w - 2) else cream(van)(x, y, w, h),
                  "bottom": mouth_inside},
        "jaw": {"side": cream(van), "top": mouth_inside, "bottom": cream(straw)},
        "head_crest": cream(mint), "crest": cream(mint),
        "tooth": fill(hexc("fffaf0")),
        "tail1": {"side": cream(choc, chips=0.02), "bottom": cream(van)},
        "tail2": {"side": cream(choc, chips=0.02), "bottom": cream(van)},
        "tail3": {"side": cream(choc, chips=0.02), "bottom": cream(van)},
    }
    paint("ice_cream_dragon", "IceCreamDragonModel.java", 128, 128, painters)

def main():
    for i, (name, f) in enumerate(FLAVOURS.items()):
        ice_cream_block(name, f, 600 + i)
    portal(); dragon_egg()
    items(); spawn_eggs()
    zombies(); beast(); gargoyle(); living(); angry_cone(); dragon()
    print("stage 6 textures generated")

if __name__ == "__main__":
    main()
