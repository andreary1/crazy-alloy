#!/usr/bin/env python3
"""Stage 4 textures for Crazy Alloy: Revival: creatures (remodelled Grape Spider, Candy Tube Dog and Brown Sugar Rhino,
and the new Ice Cream Vendor), the ice cream items and the ice cream banner patterns.

Same method as gen_mobs_v3.py: the Java model classes carry a "// tex:<name>" tag on every cube line, and this script
paints each tagged region (and fails if two regions overlap or leave the texture). Original art drawn in code from the
project owner's written descriptions; no pixels copied from anywhere.
Run by tools/gen_textures.py after the stage 3 textures (it replaces the stage 2 textures of these three creatures).
"""
import math, random
from PIL import Image
from gen_textures_v2 import hexc, shade, mix, ramp, art, save, blank
from gen_mobs_v3 import paint, flat, fill, WHITE, CLEAR

rng = random.Random(40)
BLACK = hexc("0d0a0e")

def speckle(base, spread=0.08, spec=None, spec_chance=0.0, light_top=True):
    """Grainy surface: a few close shades of one colour, optional light grains, lighter top row, darker bottom row."""
    p = ramp(base, 5, 1.0 - spread * 2, 1.0 + spread * 2)
    def f(x, y, w, h):
        c = p[1 + rng.randrange(3)]
        if light_top and y == 0 and h > 2:
            c = p[4]
        elif y == h - 1 and h > 2:
            c = p[0]
        if spec is not None and rng.random() < spec_chance:
            c = spec
        return c
    return f

# ================================================================== Grape Spider (64x32)
def grape_spider():
    BODY = hexc("35143f")
    grapes = ramp(BODY, 5, 0.8, 1.35)
    def grape_skin(x, y, w, h):
        # Very dark purple with faint round grape highlights, so the body still reads as a bunch of grapes up close.
        c = grapes[1 + rng.randrange(2)]
        if (x + 2 * (y // 3)) % 4 == 1 and y % 3 == 0:
            c = grapes[4]
        elif (x + 2 * (y // 3)) % 4 == 2 and y % 3 == 0:
            c = grapes[3]
        return c
    RIM = hexc("e6d7ee")
    def face_front(x, y, w, h):
        if x in (0, w - 1) or y in (0, h - 1):
            return RIM if (x, y) not in ((0, 0), (w - 1, 0), (0, h - 1), (w - 1, h - 1)) else shade(RIM, 0.8)
        if (x, y) == (1, 1):
            return hexc("3a2a44")  # a dull glint in the big eye
        return BLACK
    LEG = hexc("2c1036")
    def leg(x, y, w, h):
        return shade(LEG, 1.25) if y == 0 else flat(LEG, spread=0.08)(x, y, w, h)
    def shin(x, y, w, h):
        return shade(LEG, 0.7) if y == h - 1 else flat(LEG, spread=0.08)(x, y, w, h)
    painters = {
        "body": {"side": grape_skin, "front": grape_skin},
        "face": {"side": fill(RIM), "front": face_front, "back": fill(BLACK)},
        "fang": {"side": fill(hexc("d8c8e0")), "bottom": fill(hexc("f4ecf7"))},
        "bump": grape_skin,
        "thigh": leg, "shin": shin,
    }
    paint("grape_spider", "GrapeSpiderModel.java", 64, 32, painters)

# ================================================================== Candy Tube Dog (64x32)
def candy_tube_dog():
    WINE = hexc("7c1631")
    tube = speckle(WINE, 0.06, spec=hexc("b9475f"), spec_chance=0.05)
    CREAM = hexc("f6dfe2")
    def eyes(x, y, w, h):
        # 7x6 face: two light eyes with dark pupils looking forward, a little above the muzzle.
        if y in (1, 2) and x in (1, 2, 4, 5):
            if y == 2 and x in (2, 4):
                return hexc("1a0b10")
            return hexc("fbf3f0")
        if y == 0 and x in (1, 2, 4, 5):
            return shade(WINE, 0.7)  # brows
        return tube(x, y, w, h)
    def muzzle_front(x, y, w, h):
        if y == 0 and x in (1, 2):
            return hexc("140a0c")  # nose
        if (y == 1 and x in (0, w - 1)) or (y == 2 and 0 < x < w - 1):
            return hexc("140a0c")  # smile
        return tube(x, y, w, h)
    def muzzle_side(x, y, w, h):
        # Mouth line along the bottom, curling up at the back corner into a smile.
        if y == h - 1 or (y == h - 2 and x == w - 1):
            return hexc("140a0c")
        return tube(x, y, w, h)
    def muzzle_side_l(x, y, w, h):
        if y == h - 1 or (y == h - 2 and x == 0):
            return hexc("140a0c")
        return tube(x, y, w, h)
    def chest(x, y, w, h):
        return CREAM if rng.random() > 0.15 else shade(CREAM, 0.94)
    def leg(x, y, w, h):
        return shade(WINE, 0.6) if y == h - 1 else tube(x, y, w, h)
    def tail(x, y, w, h):
        return CREAM if x >= w - 1 else tube(x, y, w, h)
    painters = {
        "tube": {"side": tube, "bottom": fill(shade(WINE, 0.85))},
        "chest": {"side": chest, "front": chest},
        "tail": {"side": tail, "back": fill(CREAM)},
        "head": {"side": tube, "front": eyes},
        "muzzle": {"side": tube, "front": muzzle_front, "right": muzzle_side, "left": muzzle_side_l, "bottom": fill(shade(WINE, 0.8))},
        "ear": speckle(shade(WINE, 0.75), 0.05),
        "leg": leg,
    }
    paint("candy_tube_dog", "CandyTubeDogModel.java", 64, 32, painters)

# ================================================================== Brown Sugar Rhino (128x64)
def brown_sugar_rhino():
    HIDE = hexc("4a2b16")
    hide = speckle(HIDE, 0.07, spec=hexc("6e4424"), spec_chance=0.06)
    top_hide = speckle(shade(HIDE, 1.12), 0.07, spec=hexc("7a4d2a"), spec_chance=0.08, light_top=False)
    belly = speckle(shade(HIDE, 0.85), 0.05, light_top=False)
    def eye_at(ex):
        def f(x, y, w, h):
            if x == ex and y == 3:
                return hexc("120904")
            if x == ex and y == 2:
                return shade(HIDE, 0.7)
            return hide(x, y, w, h)
        return f
    MOUTH = hexc("1a0d06")
    def muzzle_side(x, y, w, h):
        return MOUTH if y == h - 2 else hide(x, y, w, h)
    def muzzle_front(x, y, w, h):
        if y == 2 and x in (1, w - 2):
            return MOUTH  # nostrils
        if y == h - 2:
            return MOUTH
        return hide(x, y, w, h)
    SUGAR = hexc("8b5a2b")
    sugar = ramp(SUGAR, 5, 0.8, 1.25)
    def horn(x, y, w, h):
        # Packed brown sugar: mid brown with lighter crumbs and a few dark pits.
        r = rng.random()
        if r < 0.22:
            return hexc("d3a56a")
        if r < 0.3:
            return sugar[0]
        return sugar[1 + rng.randrange(3)]
    def horn_tip(x, y, w, h):
        return hexc("dcb07a") if rng.random() < 0.35 else sugar[3]
    def leg(x, y, w, h):
        if y == h - 1:
            return hexc("c9a27a") if x % 2 == 1 else shade(HIDE, 0.6)  # toenails
        return hide(x, y, w, h)
    def tail(x, y, w, h):
        return shade(HIDE, 0.55) if y >= h - 1 else hide(x, y, w, h)
    painters = {
        "torso": {"side": hide, "top": top_hide, "bottom": belly},
        "back": {"side": top_hide, "top": top_hide},
        "hump": {"side": top_hide, "top": speckle(shade(HIDE, 1.2), 0.06, light_top=False)},
        "shoulder": {"side": hide, "bottom": belly},
        "head": {"side": hide, "right": eye_at(6), "left": eye_at(2), "top": top_hide, "bottom": belly},
        "muzzle": {"side": muzzle_side, "front": muzzle_front, "top": top_hide, "bottom": belly},
        "horn_big": horn, "horn_tip": {"side": horn_tip, "top": fill(hexc("e2bb86"))}, "horn_small": horn,
        "ear": {"side": hide, "front": fill(hexc("7a4a3a"))},
        "leg": leg, "tail": tail,
    }
    paint("brown_sugar_rhino", "BrownSugarRhinoModel.java", 128, 64, painters)

# ================================================================== Ice Cream Vendor (64x64)
def ice_cream_vendor():
    SKIN = hexc("f0c49a")
    UNIFORM = hexc("f7f5f2")
    PINK = hexc("f59bc0")
    skin = flat(SKIN, spread=0.03)
    cloth = flat(UNIFORM, spread=0.02)
    def face(x, y, w, h):
        if y == 3 and x in (2, 5):
            return hexc("3a2416")  # eyes
        if y == 2 and x in (1, 2, 5, 6):
            return hexc("6b4423")  # brows
        if y == 6 and 2 <= x <= 5:
            return hexc("b55a5a") if x in (2, 5) and False else (hexc("c0616a") if 3 <= x <= 4 else SKIN)  # smile
        if y == 5 and x in (2, 5):
            return hexc("c0616a")
        if y in (0, 1):
            return hexc("6b4423")  # hair line under the cap
        return skin(x, y, w, h)
    def hair(x, y, w, h):
        return hexc("6b4423") if y < 3 else skin(x, y, w, h)
    def body_front(x, y, w, h):
        if y == 0 and x in (3, 4):
            return hexc("e0457f")  # bow tie knot
        if y in (0, 1) and x in (2, 5):
            return PINK
        if x in (1, 6) and y >= 2:
            return PINK  # apron straps
        if y >= 5 and 1 <= x <= 6:
            return hexc("fde3ee") if (x + y) % 5 else PINK  # apron with a few pink dots
        return cloth(x, y, w, h)
    def body_side(x, y, w, h):
        return PINK if y == h - 1 else cloth(x, y, w, h)
    def arm(x, y, w, h):
        if y >= h - 3:
            return skin(x, y, w, h)  # hands
        if y == h - 4:
            return PINK  # cuff
        return cloth(x, y, w, h)
    def leg(x, y, w, h):
        if y >= h - 2:
            return hexc("3b2a2a")  # shoes
        return flat(hexc("e9b6cb"), spread=0.03)(x, y, w, h)  # light pink trousers
    def hat(x, y, w, h):
        return PINK if y == h - 1 else hexc("ffffff")
    painters = {
        "head": {"side": hair, "front": face, "top": fill(hexc("6b4423"))},
        "nose": skin,
        "hat": {"side": hat, "top": fill(hexc("ffffff"))}, "hat_ridge": fill(hexc("ffffff")),
        "body": {"side": body_side, "front": body_front},
        "arm": arm, "leg": leg,
        "cone": {"side": lambda x, y, w, h: hexc("c98b45") if (x + y) % 2 else hexc("a96d32")},
        "scoop": {"side": fill(hexc("f7a6c8")), "top": fill(hexc("ffc2db"))},
    }
    paint("ice_cream_vendor", "IceCreamVendorModel.java", 64, 64, painters)

# ================================================================== items (16x16)
CONE_ROWS = [
    "...kkCcCCcCkk...",
    "....kCCcCCck....",
    "....kcCCcCCk....",
    ".....kCcCCk.....",
    ".....kCCcCk.....",
    "......kcCk......",
    "......kCCk......",
    ".......kk.......",
]
SCOOP_ROWS = [
    "................",
    "......oooo......",
    "....ooSHSSoo....",
    "...oSHHSSSSSo...",
    "...oSHSSSSSSo...",
    "..oSSSSSSSSSDo..",
    "..oSSSSSSSSDDo..",
    "..oDSDDSSDDDDo..",
]
CONE_PAL = {"k": hexc("6b3d14"), "C": hexc("d9a05b"), "c": hexc("a8692c")}

def items():
    for flavour, base in [("vanilla", hexc("fbf3dc")), ("strawberry", hexc("f68fb6")), ("chocolate", hexc("7a4422"))]:
        pal = dict(CONE_PAL, o=shade(base, 0.55), S=base, H=shade(base, 1.12) if flavour != "vanilla" else hexc("ffffff"), D=shade(base, 0.82))
        save(art(SCOOP_ROWS + CONE_ROWS, pal), f"item/{flavour}_ice_cream.png")
    cone = [
        "................", "................", "................",
        "...kkkkkkkkkk...",
        "...kCCcCCcCCk...",
        "...kcCCcCCcCk...",
        "....kCcCCcCk....",
        "....kCCcCCck....",
        ".....kcCCck.....",
        ".....kCcCCk.....",
        "......kCck......",
        "......kcCk......",
        ".......kk.......",
        "................", "................", "................",
    ]
    save(art(cone, dict(CONE_PAL, k=hexc("6b3d14"))), "item/wafer_cone.png")
    egg = [
        "................",
        "......pPPp......",
        ".....pPHPPp.....",
        "....kpPPPPpk....",
        "....kWpWWpWk....",
        "...kWWWpWWWWk...",
        "...kWWWWWWsWk...",
        "..kWWsWWWWWWDk..",
        "..kWWWWWWtWWDk..",
        "..kWtWWWWWWDDk..",
        "..kWWWWsWWWDDk..",
        "...kWWWWWWDDk...",
        "...kDWWWWDDDk...",
        "....kDDDDDDk....",
        ".....kkkkkk.....",
        "................",
    ]
    save(art(egg, {"k": hexc("8a6a5a"), "W": hexc("fff6ea"), "D": hexc("e8d6c2"), "p": hexc("e0699b"), "P": hexc("f7a6c8"),
                   "H": hexc("ffd6e8"), "s": hexc("7a4422"), "t": hexc("5fbf4f")}), "item/ice_cream_vendor_spawn_egg.png")

# ================================================================== banner patterns (64x64, tinted by the game)
def banner_patterns():
    def draw(w, h, cone_top, cone_tip, cone_half, scoop_c, scoop_r):
        """A cone (cross-hatched through the alpha) and a round scoop with drips, in face coordinates."""
        cone, scoop = {}, {}
        for y in range(h):
            for x in range(w):
                cx = (w - 1) / 2
                if cone_top <= y <= cone_tip:
                    half = cone_half * (cone_tip - y) / (cone_tip - cone_top) + 0.5
                    if abs(x - cx) <= half:
                        grid = (x + y) % 3 == 0 or (x - y) % 3 == 0
                        cone[(x, y)] = 255 if grid else 190
                d = math.hypot(x - cx, (y - scoop_c) * 1.1)
                if d <= scoop_r or (y > scoop_c and y <= scoop_c + scoop_r + 1.5 and abs(x - cx) <= scoop_r and (x * 7) % 5 < 2 and y < cone_top + 2):
                    scoop[(x, y)] = 255
        return cone, scoop
    for kind, (ox, oy, w, h, bx), params in [("banner", (1, 1, 20, 40, 22), (19, 34, 5.5, 13, 6.5)),
                                              ("shield", (1, 1, 12, 22, None), (12, 20, 3.5, 8, 4.0))]:
        cone, scoop = draw(w, h, *params)
        for name, pix in [("ice_cream_cone", cone), ("ice_cream_scoop", scoop)]:
            im = blank(64, 64)
            for (x, y), a in pix.items():
                im.putpixel((ox + x, oy + y), (255, 255, 255, a))
                if bx is not None:
                    im.putpixel((bx + (w - 1 - x), oy + y), (255, 255, 255, a))  # back of the flag, mirrored
            save(im, f"entity/{kind}/{name}.png")

def main():
    grape_spider(); candy_tube_dog(); brown_sugar_rhino(); ice_cream_vendor()
    items(); banner_patterns()
    print("stage 4 creature textures generated")

if __name__ == "__main__":
    main()
