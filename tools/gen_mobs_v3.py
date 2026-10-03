#!/usr/bin/env python3
"""Stage 3 creature textures for Crazy Alloy: Revival (remodelled Jelly Shark, Roll Cake Monster, Cotton Candy
Tornado, Gingerbread Warrior and Soldier, and the new Gingerbread King).

The Java model classes are the single source of truth for the UV layout: every cube line carries a
"// tex:<name>" tag after its texOffs(u, v).addBox(x, y, z, w, h, d) call. This script reads those tags, checks that
no two regions overlap or leave the texture, and paints each region with the painter registered for its name.
Original art drawn in code from the project owner's written descriptions; no pixels copied from anywhere.
Run by tools/gen_textures.py after the stage 2 textures (it replaces the five stage 2 creature textures).
"""
import math, os, random, re
from gen_textures_v2 import hexc, shade, mix, save, blank, ramp, box

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..")
MODELS = os.path.join(ROOT, "src", "main", "java", "com", "crazyalloy", "revival", "client", "model")
CUBE = re.compile(r"texOffs\((\d+),\s*(\d+)\)(?:\.mirror\(\))?\.addBox\(([^)]*)\).*//\s*tex:(\w+)")

def regions(java_file):
    """{name: (u, v, w, h, d)} from the tagged cubes of a model class."""
    out = {}
    for line in open(os.path.join(MODELS, java_file), encoding="utf-8"):
        m = CUBE.search(line)
        if not m:
            continue
        args = [a.strip().rstrip("F") for a in m.group(3).split(",")]
        u, v = int(m.group(1)), int(m.group(2))
        w, h, d = (int(float(a)) for a in args[3:6])
        key = (u, v, w, h, d)
        name = m.group(4)
        if name in out and out[name] != key:
            raise SystemExit(f"{java_file}: tex:{name} used with two different boxes {out[name]} and {key}")
        out[name] = key
    return out

def check_layout(label, regs, tw, th):
    """Fails if a cube's UV net leaves the texture or overlaps another cube's net."""
    owner = {}
    for name, (u, v, w, h, d) in regs.items():
        rects = [(u + d, v, w, d), (u + d + w, v, w, d), (u, v + d, d, h), (u + d, v + d, w, h), (u + d + w, v + d, d, h), (u + 2 * d + w, v + d, w, h)]
        for (x0, y0, rw, rh) in rects:
            for y in range(y0, y0 + rh):
                for x in range(x0, x0 + rw):
                    if not (0 <= x < tw and 0 <= y < th):
                        raise SystemExit(f"{label}: tex:{name} leaves the {tw}x{th} texture at {x},{y}")
                    if owner.setdefault((x, y), name) != name:
                        raise SystemExit(f"{label}: tex:{name} overlaps tex:{owner[(x, y)]} at {x},{y}")

def paint(label, java_file, tw, th, painters, only=None):
    regs = regions(java_file)
    if only is not None:
        regs = {k: v for k, v in regs.items() if only(k)}
    check_layout(label, regs, tw, th)
    missing = set(regs) - set(painters)
    if missing:
        raise SystemExit(f"{label}: no painter for {sorted(missing)}")
    im = blank(tw, th)
    for name, (u, v, w, h, d) in regs.items():
        faces = painters[name]
        if not isinstance(faces, dict):
            faces = {"side": faces}
        box(im, u, v, w, h, d, faces.get("side"), top=faces.get("top"), front=faces.get("front"), bottom=faces.get("bottom"),
            back=faces.get("back"), right=faces.get("right"), left=faces.get("left"))
    save(im, f"entity/{label}.png")
    return regs

# ------------------------------------------------------------------ painting helpers
rng = random.Random(30)
WHITE = hexc("ffffff")
CLEAR = (0, 0, 0, 0)

def jelly(base, spec=0.05):
    """Glossy gummy surface: noisy body colour, a light top row, a darker bottom row and sugar specks."""
    p = ramp(base, 5, 0.82, 1.12)
    def f(x, y, w, h):
        c = p[1 + rng.randrange(3)]
        if y == 0 and h > 1:
            c = p[4]
        elif y == h - 1 and h > 2:
            c = p[0]
        if rng.random() < spec:
            c = mix(c, WHITE, 0.6)
        return c
    return f

def flat(base, var=3, spread=0.06):
    p = [shade(base, 1.0 + spread * (i - var // 2)) for i in range(var)]
    return lambda x, y, w, h: p[rng.randrange(var)]

def fill(c):
    return lambda x, y, w, h: c

def teeth_ring(x, y, w, h):
    """A ring of 1-pixel square teeth along the edge of a face; the middle stays clear."""
    edge = x in (0, w - 1) or y in (0, h - 1)
    return WHITE if edge and (x + y) % 2 == 0 else CLEAR

def teeth_row(x, y, w, h):
    return WHITE if x % 2 == 0 else CLEAR

# ================================================================== Jelly Shark (128x64)
def jelly_shark():
    GREEN, MAG, ORANGE, PURPLE = hexc("4cc23e"), hexc("d4359f"), hexc("f39226"), hexc("8a45cf")
    MOUTH, TONGUE = hexc("5a0f2a"), hexc("c8486e")
    dark = hexc("101010")
    g, m, o, pu = jelly(GREEN), jelly(MAG), jelly(ORANGE), jelly(PURPLE)
    def eye_at(ex):
        def f(x, y, w, h):
            if x == ex and y == 1:
                return dark
            if x == ex and y == 2:
                return hexc("e8ffe0")
            return g(x, y, w, h)
        return f
    def belly(base):
        p = ramp(base, 4, 1.05, 1.25)
        return lambda x, y, w, h: p[rng.randrange(4)]
    def fin(base):
        p = ramp(base, 4, 0.72, 1.0)
        return lambda x, y, w, h: p[3] if y == 0 else p[rng.randrange(3)]
    def snout_front(x, y, w, h):
        if y == 1 and x in (1, w - 2):
            return shade(GREEN, 0.5)  # nostrils
        return g(x, y, w, h)
    painters = {
        "trunk": {"side": m, "bottom": belly(MAG)},
        "skull": {"side": g, "right": eye_at(7), "left": eye_at(2), "bottom": fill(MOUTH)},
        "snout": {"side": g, "front": snout_front, "bottom": fill(MOUTH)},
        "jaw": {"side": g, "top": lambda x, y, w, h: TONGUE if 2 <= x < w - 2 and y < h - 2 else MOUTH, "bottom": belly(GREEN)},
        "teeth_up": {"side": teeth_row, "top": CLEAR, "bottom": teeth_ring, "back": CLEAR},
        "teeth_low": {"side": teeth_row, "top": teeth_ring, "bottom": CLEAR, "back": CLEAR},
        "dorsal": fin(MAG), "dorsal_tip": fin(MAG), "pec_fin": fin(shade(MAG, 0.9)),
        "rear": {"side": o, "bottom": belly(ORANGE)},
        "rear_dorsal": fin(ORANGE), "pelvic_fin": fin(ORANGE), "anal_fin": fin(ORANGE),
        "tail": pu, "fin_upper": fin(PURPLE), "fin_lower": fin(PURPLE),
    }
    paint("jelly_shark", "JellySharkModel.java", 128, 64, painters)

# ================================================================== Roll Cake Monster (64x64)
def roll_cake_monster():
    BODY = [hexc("3b2314"), hexc("442817"), hexc("33200f"), hexc("4a2c1a")]
    body = lambda x, y, w, h: BODY[rng.randrange(4)]
    CRUST = ramp(hexc("a8662a"), 4, 0.85, 1.12)
    def crust(x, y, w, h):
        c = CRUST[rng.randrange(4)]
        if rng.random() < 0.05:
            c = hexc("f3dcb0")  # powdered sugar
        return c
    SPONGE = [hexc("fbe7c0"), hexc("f6dcae")]
    FILL = [hexc("f08fb3"), hexc("e8789f")]
    def spiral(gx, gy):
        cx = cy = 4.5
        dx, dy = gx - cx, gy - cy
        r = math.hypot(dx, dy)
        if r > 4.9:
            return CRUST[1]  # the outer skin of the roll
        a = math.atan2(dy, dx)
        s = r - (a / math.tau) * 2.0
        band = int(math.floor(s / 1.0)) % 2
        return (FILL if band else SPONGE)[rng.randrange(2)]
    def spiral_face(ox, oy):
        return lambda x, y, w, h: spiral(x + ox, y + oy)
    def joint_bottom(x, y, w, h):
        return hexc("6a4128") if y == h - 1 else body(x, y, w, h)
    def joint_top(x, y, w, h):
        return hexc("6a4128") if y == 0 else body(x, y, w, h)
    CLAW = hexc("f2e6cf")
    painters = {
        "torso": body, "neck": body,
        "head_main": {"side": crust, "front": spiral_face(0, 1), "back": spiral_face(0, 1)},
        "head_cap_top": {"side": crust, "front": spiral_face(1, 0), "back": spiral_face(1, 0)},
        "head_cap_bottom": {"side": crust, "front": spiral_face(1, 9), "back": spiral_face(1, 9)},
        "upper_arm": joint_bottom, "forearm": joint_top,
        "claw": lambda x, y, w, h: shade(CLAW, 0.85) if y == h - 1 else CLAW,
        "thigh": joint_bottom, "shin": joint_top, "foot": fill(hexc("2a170c")),
    }
    paint("roll_cake_monster", "RollCakeMonsterModel.java", 64, 64, painters)

# ================================================================== Cotton Candy Tornado (64x64)
def cotton_candy_tornado():
    def fluff(base):
        p = ramp(base, 5, 0.86, 1.08)
        def f(x, y, w, h):
            c = p[1 + rng.randrange(3)]
            if (x + 2 * y) % 5 == 0:
                c = p[0]  # swirl streaks
            if y == 0:
                c = p[4]
            return c
        return f
    PINK, CREAM, BLUE, RED = hexc("ffb0dc"), hexc("fff0cf"), hexc("b4e2ff"), hexc("e8657c")
    def mouth(x, y, w, h):
        # Top of the face layer, seen through the rim: a dark whirl.
        cx, cy = (w - 1) / 2, (h - 1) / 2
        dx, dy = x - cx, y - cy
        r = math.hypot(dx, dy)
        a = math.atan2(dy, dx)
        band = int(r - a / math.tau * 2.5) % 2
        c = hexc("b0478a") if band else hexc("7c2563")
        return mix(c, hexc("ffb0dc"), max(0.0, (r - 3.5) / 3.0)) if r > 3.5 else c
    def eye_front(x, y, w, h):
        if (x, y) == (1, 1):
            return hexc("1a0505")
        if (x, y) == (0, 0):
            return hexc("ffd0d0")
        return hexc("e0202a")
    METAL = ramp(hexc("8a8f96"), 4, 0.8, 1.1)
    def metal(x, y, w, h):
        c = METAL[1 + rng.randrange(2)]
        if y == h - 1:
            c = METAL[0]
        if y == 0:
            c = METAL[3]
        if y == 1 and x % 4 == 1:
            c = hexc("d6d9de")  # rivets
        return c
    painters = {
        "l4": {"side": fluff(PINK), "top": mouth},
        "rim_fb": fluff(shade(PINK, 1.05)), "rim_side": fluff(shade(PINK, 1.05)),
        "l3": fluff(CREAM), "l2": fluff(BLUE), "tip": fluff(RED),
        "eye": {"side": fill(hexc("b5141c")), "front": eye_front},
        "base": metal, "stick": flat(hexc("a0a4aa")), "white_piece": flat(hexc("f4f4f2"), spread=0.03),
        "foot": fill(hexc("1e1e1e")),
    }
    paint("cotton_candy_tornado", "CottonCandyTornadoModel.java", 64, 64, painters)

# ================================================================== Gingerbread men (64x64, shared layout)
COOKIE = [hexc("c07a32"), hexc("b86f2a"), hexc("c98439"), hexc("b0682a")]
ICING = hexc("fbfbf4")

def cookie(x, y, w, h):
    c = COOKIE[rng.randrange(4)]
    if rng.random() < 0.04:
        c = hexc("8a4d18")  # baked spots
    return c

def zigzag(row):
    """A wavy line of icing across a face at the given row."""
    def f(x, y, w, h):
        if y == row + (x % 2):
            return ICING
        return cookie(x, y, w, h)
    return f

def gingerbread(name, face_marks, extras, prefix):
    def face(x, y, w, h):
        return ICING if (x, y) in face_marks else cookie(x, y, w, h)
    def torso_front(x, y, w, h):
        if y == (x % 2):  # icing trim at the neck
            return ICING
        if x == w // 2 and y in (2, 4, 6):  # buttons
            return ICING
        return cookie(x, y, w, h)
    painters = {
        "head": {"side": cookie, "front": face},
        "torso": {"side": cookie, "front": torso_front},
        "arm": zigzag(5), "leg": zigzag(3),
    }
    painters.update(extras)
    paint(name, "GingerbreadModel.java", 64, 64, painters, only=lambda k: not k[:2] in ("w_", "s_") or k.startswith(prefix))

def gingerbread_men():
    RED = hexc("d8202e")
    red = jelly(RED, spec=0.0)
    warrior_face = {(1, 3), (2, 3), (2, 4), (5, 3), (6, 3), (5, 4), (2, 5), (3, 5), (4, 5), (5, 5)}
    gingerbread("gingerbread_warrior", warrior_face, {"w_band": red, "w_knot": red, "w_tail": red}, "w_")

    GREEN = hexc("1f4f2a")
    def crown(x, y, w, h):
        if y == h - 1:
            return shade(GREEN, 0.7)  # band at the base of the crown
        if y == 0:
            return shade(GREEN, 1.15)
        return flat(GREEN, spread=0.05)(x, y, w, h)
    WOOD = hexc("7a4a22")
    def barrel_side(x, y, w, h):
        return hexc("2c2c2e") if x in (0, w - 1) else hexc("454548")
    soldier_face = {(1, 2), (2, 2), (2, 3), (5, 2), (6, 2), (5, 3), (2, 5), (3, 5), (4, 5), (5, 5)}
    gingerbread("gingerbread_soldier", soldier_face, {
        "s_brim": {"side": fill(shade(GREEN, 0.8)), "top": flat(GREEN, spread=0.05), "bottom": fill(shade(GREEN, 0.6))},
        "s_crown": {"side": crown, "top": flat(shade(GREEN, 1.1), spread=0.05)},
        "s_badge": {"side": fill(ICING), "front": lambda x, y, w, h: hexc("e8c23a") if (x + y) % 2 else ICING},
        "s_stock": flat(shade(WOOD, 0.9)), "s_body": flat(WOOD),
        "s_barrel": {"side": barrel_side, "front": fill(hexc("0c0c0c")), "back": fill(hexc("2c2c2e"))},
    }, "s_")

# ================================================================== Gingerbread King (128x128)
def gingerbread_king():
    GOLD = ramp(hexc("f2c230"), 4, 0.8, 1.15)
    def gold(x, y, w, h):
        c = GOLD[1 + rng.randrange(2)]
        if y == 0:
            c = GOLD[3]
        elif y == h - 1:
            c = GOLD[0]
        return c
    def band_front(x, y, w, h):
        if x == w // 2:
            return hexc("d2283c")  # ruby
        if x in (2, w - 3):
            return hexc("2f6fe0")  # sapphires
        return gold(x, y, w, h)
    PURPLE = hexc("7b3fb0")
    def ruff(base):
        p = ramp(base, 4, 0.8, 1.1)
        def f(x, y, w, h):
            c = p[1 + rng.randrange(2)]
            if x % 3 == 2:
                c = p[0]  # pleats
            if y == 0:
                c = p[3]
            return c
        return f
    def ruff_top(base):
        p = ramp(base, 4, 0.9, 1.15)
        return lambda x, y, w, h: p[rng.randrange(4)]
    def torso_side(x, y, w, h):
        # Wavy icing along the bottom edge of the body, all the way around.
        wave = h - 3 + (0 if x % 4 in (0, 3) else 1)
        if y == wave:
            return ICING
        return cookie(x, y, w, h)
    def arm(x, y, w, h):
        if y in (3 + (x % 2), 9 + (x % 2)):
            return ICING
        return cookie(x, y, w, h)
    def hand(x, y, w, h):
        return ICING if y == 0 and x % 2 == 0 else cookie(x, y, w, h)
    def foot(x, y, w, h):
        return ICING if y == 0 and x % 2 == 1 else shade(cookie(x, y, w, h), 0.9)
    def head_front(x, y, w, h):
        # Small dark eyes under the brows; the moustache and brows are separate cubes.
        if y == 6 and x in (3, 4, 7, 8):
            return hexc("2b1406")
        if y == 5 and x in (3, 8):
            return hexc("4a2810")
        return cookie(x, y, w, h)
    ICE = [ICING, hexc("f1f1ea"), hexc("e4e4dc")]
    def icing(x, y, w, h):
        return ICE[2] if y == h - 1 and h > 1 else ICE[rng.randrange(2)]
    painters = {
        "torso": {"side": torso_side, "top": cookie, "bottom": cookie},
        "button": icing,
        "collar1": {"side": ruff(PURPLE), "top": ruff_top(PURPLE), "bottom": fill(shade(PURPLE, 0.6))},
        "collar2": {"side": ruff(shade(PURPLE, 1.15)), "top": ruff_top(shade(PURPLE, 1.15))},
        "arm": arm, "hand": hand,
        "leg": zigzag(5), "foot": foot,
        "head": {"side": cookie, "front": head_front},
        "crown_band": {"side": gold, "front": band_front, "top": fill(shade(hexc("f2c230"), 0.7))},
        "crown_pt_fb": gold, "crown_pt_side": gold,
        "must_center": icing, "must_tip": icing, "must_hook": icing, "brow": icing,
    }
    paint("gingerbread_king", "GingerbreadKingModel.java", 128, 128, painters)

def main():
    jelly_shark(); roll_cake_monster(); cotton_candy_tornado(); gingerbread_men(); gingerbread_king()
    print("stage 3 creature textures generated")

if __name__ == "__main__":
    main()
