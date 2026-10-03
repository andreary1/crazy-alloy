#!/usr/bin/env python3
"""Stage 2 textures for Crazy Alloy: Revival (Sweet Forest rework + Jelly Bean Fields).

Original art, drawn in code at 16x16 with vanilla-style shading (top-left light, bottom-right shadow,
darker outlines on items). Shapes and palettes follow the original Crazy Alloy texture sheets that the
project owner supplied as reference; no pixels are copied from the original mod or from vanilla.
Run by tools/gen_textures.py after the stage 1 textures, so files written here replace stage 1 ones
of the same name (grass, soil, candy tree, lollipop, chocolate bar, cotton candy, Candy Tube Dog,
Lollipop Guy).
"""
import math, os, random
from PIL import Image, ImageDraw

NS = "crazyalloy_revival"
T = os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "src", "main", "resources", "assets", NS, "textures")
R = random.Random(20261002)

def hexc(h, a=255):
    h = h.lstrip("#")
    return (int(h[0:2], 16), int(h[2:4], 16), int(h[4:6], 16), a)

def shade(c, f):
    return tuple(max(0, min(255, int(round(v * f)))) for v in c[:3]) + (c[3],)

def mix(a, b, t):
    return tuple(int(round(a[i] * (1 - t) + b[i] * t)) for i in range(3)) + (255,)

def save(img, rel):
    p = os.path.join(T, rel)
    os.makedirs(os.path.dirname(p), exist_ok=True)
    img.save(p)

def blank(w=16, h=16):
    return Image.new("RGBA", (w, h), (0, 0, 0, 0))

def art(rows, pal):
    """Pixel art from a list of strings. '.' or ' ' is transparent; other chars index pal."""
    h = len(rows); w = max(len(r) for r in rows)
    im = blank(w, h); px = im.load()
    for y, row in enumerate(rows):
        for x, ch in enumerate(row):
            if ch not in ". ":
                px[x, y] = pal[ch]
    return im

def ramp(base, n=5, lo=0.62, hi=1.22):
    """n shades of one colour from dark to light."""
    return [shade(base, lo + (hi - lo) * i / (n - 1)) for i in range(n)]

def noise_tile(pal, weights=None, size=16, rng=R):
    im = blank(size, size); px = im.load()
    for y in range(size):
        for x in range(size):
            px[x, y] = rng.choices(pal, weights=weights)[0]
    return im

def smooth_noise(size, seed, scale=4.0):
    """Tileable value noise in [0,1] (sum of a few sine waves on a torus)."""
    rng = random.Random(seed)
    waves = [(rng.randint(1, 3), rng.randint(-3, 3), rng.random() * math.tau, rng.uniform(0.5, 1.0)) for _ in range(5)]
    out = [[0.0] * size for _ in range(size)]
    for y in range(size):
        for x in range(size):
            v = 0.0
            for (a, b, ph, amp) in waves:
                v += amp * math.sin(math.tau * (a * x + b * y) / size + ph)
            out[y][x] = v
    flat = [v for r in out for v in r]
    lo, hi = min(flat), max(flat)
    return [[(v - lo) / (hi - lo) for v in r] for r in out]

def outline(im, col, diagonal=False):
    """Adds an outline of `col` around opaque pixels (items)."""
    src = im.copy(); s = src.load(); px = im.load()
    w, h = im.size
    nb = [(1, 0), (-1, 0), (0, 1), (0, -1)] + ([(1, 1), (-1, -1), (1, -1), (-1, 1)] if diagonal else [])
    for y in range(h):
        for x in range(w):
            if s[x, y][3] == 0 and any(0 <= x + dx < w and 0 <= y + dy < h and s[x + dx, y + dy][3] > 0 for dx, dy in nb):
                px[x, y] = col
    return im

def emboss_edges(im, light, dark):
    """Lightens top/left edges and darkens bottom/right edges of opaque regions (items)."""
    src = im.copy(); s = src.load(); px = im.load()
    w, h = im.size
    for y in range(h):
        for x in range(w):
            if s[x, y][3] == 0:
                continue
            up = y == 0 or s[x, y - 1][3] == 0
            left = x == 0 or s[x - 1, y][3] == 0
            down = y == h - 1 or s[x, y + 1][3] == 0
            right = x == w - 1 or s[x + 1, y][3] == 0
            if up or left:
                px[x, y] = shade(s[x, y], light)
            elif down or right:
                px[x, y] = shade(s[x, y], dark)
    return im

# =================================================================== palettes
GUMMY = [hexc("c93fc9"), hexc("dd4fdd"), hexc("ee5cee"), hexc("ff6dff"), hexc("ff8cff"), hexc("ffb3ff")]
CHOC_DIRT = [hexc("2e1a0e"), hexc("3a2213"), hexc("462a18"), hexc("54341f"), hexc("634026"), hexc("74502f")]
CANE_WHITE = [hexc("c9c4c8"), hexc("dcd7da"), hexc("e9e5e7"), hexc("f5f2f3"), hexc("ffffff")]
CANE_RED = [hexc("7d0a1c"), hexc("a3102a"), hexc("c8173a"), hexc("e0324f")]
CANE_MAG = [hexc("7a0d4a"), hexc("a01562"), hexc("c41f78"), hexc("de3c93")]
LEAF = [hexc("5e1d55"), hexc("7a2870"), hexc("93338a"), hexc("ab42a1"), hexc("c35bb8"), hexc("d97ccf")]
GINGER = [hexc("6e3d0b"), hexc("8a5212"), hexc("a1651a"), hexc("b37522"), hexc("c4862e"), hexc("d69a3e")]
ICING = [hexc("b9b2ad"), hexc("d9d4d0"), hexc("efebe8"), hexc("ffffff")]
LICORICE_RED = [hexc("5c0710"), hexc("8c0f1c"), hexc("c01b2a"), hexc("e33a44"), hexc("ff7a7a")]
LICORICE_COOKED = [hexc("2e0a0c"), hexc("4a1216"), hexc("672024"), hexc("843134"), hexc("a24d4c")]
JELLY = {
    "green": hexc("4bbf3f"), "yellow": hexc("f2e85a"), "red": hexc("d9605f"),
    "orange": hexc("f0aa5a"), "purple": hexc("b25ec2"),
}

# =================================================================== blocks
def sweet_blocks():
    # --- Gummy Grass top: glossy pink gum, small highlights and darker dents.
    n = smooth_noise(16, 11)
    top = blank(); t = top.load()
    for y in range(16):
        for x in range(16):
            v = n[y][x] * 0.6 + R.random() * 0.4
            t[x, y] = GUMMY[min(4, int(v * 5))]
    for _ in range(7):  # glossy highlights: 2px streaks
        x, y = R.randrange(16), R.randrange(16)
        t[x, y] = GUMMY[5]; t[(x + 1) % 16, y] = GUMMY[4]
    for _ in range(6):  # dents
        x, y = R.randrange(16), R.randrange(16)
        t[x, y] = GUMMY[0]; t[x, (y + 1) % 16] = GUMMY[1]
    save(top, "block/chocolate_grass_block_top.png")

    # --- Chocolate Dirt: vanilla-like dirt noise with crumbs and pits.
    def dirt(seed):
        rng = random.Random(seed)
        im = noise_tile(CHOC_DIRT[1:5], [3, 4, 3, 2], rng=rng); p = im.load()
        for _ in range(9):
            x, y = rng.randrange(16), rng.randrange(16)
            p[x, y] = CHOC_DIRT[5]
            if rng.random() < 0.5:
                p[(x + 1) % 16, y] = CHOC_DIRT[4]
        for _ in range(9):
            x, y = rng.randrange(16), rng.randrange(16)
            p[x, y] = CHOC_DIRT[0]
        return im
    soil = dirt(5)
    save(soil, "block/chocolate_soil.png")

    # --- Gummy Grass side: gum top drips over chocolate dirt, with a shadow under the drip.
    side = dirt(5); s = side.load(); tp = top.load()
    profile = [3, 3, 4, 5, 4, 3, 3, 2, 3, 4, 6, 5, 4, 3, 2, 3]
    for x in range(16):
        d = profile[x]
        for y in range(d):
            s[x, y] = tp[x, y]
        s[x, d - 1] = GUMMY[1] if d > 2 else s[x, d - 1]
        s[x, d] = GUMMY[0]
        s[x, d + 1] = shade(s[x, d + 1], 0.75)
    for x in range(16):  # top row highlight
        s[x, 0] = GUMMY[4] if x % 5 else GUMMY[5]
    save(side, "block/chocolate_grass_block_side.png")

    # --- Candy Log side: white bark with diagonal red and magenta stripes that wrap around.
    log = blank(); l = log.load()
    for y in range(16):
        for x in range(16):
            k = (x + y) % 16
            col_shade = 3 if x in (5, 6, 11) else 2
            if x in (0, 15):
                col_shade = 1
            if k < 4:
                stripe = CANE_RED
                idx = 2 if k in (1, 2) else (3 if k == 0 else 1)
                if x in (0, 15): idx = max(0, idx - 1)
                l[x, y] = stripe[idx]
            elif 8 <= k < 11:
                stripe = CANE_MAG
                idx = 2 if k == 9 else (3 if k == 8 else 1)
                if x in (0, 15): idx = max(0, idx - 1)
                l[x, y] = stripe[idx]
            else:
                l[x, y] = CANE_WHITE[col_shade + (1 if R.random() < 0.15 else 0) - (1 if R.random() < 0.12 else 0)]
    for y in range(16):  # sugar-crack grain
        if R.random() < 0.35:
            x = R.randrange(1, 15)
            if l[x, y] in CANE_WHITE:
                l[x, y] = CANE_WHITE[0]
    save(log, "block/sweetwood_log.png")

    # --- Candy Log top: striped bark ring, cream core with rings and a pink heart.
    lt = blank(); q = lt.load()
    for y in range(16):
        for x in range(16):
            dx, dy = x - 7.5, y - 7.5
            r = max(abs(dx), abs(dy))
            ang = (math.atan2(dy, dx) / math.tau) % 1.0
            if r > 6.5:
                seg = int(ang * 8) % 4
                q[x, y] = [CANE_RED[2], CANE_WHITE[3], CANE_MAG[2], CANE_WHITE[3]][seg]
            elif r > 5.5:
                q[x, y] = CANE_WHITE[1]
            else:
                rr = math.hypot(dx, dy)
                q[x, y] = CANE_WHITE[2] if int(rr) % 2 == 0 else CANE_WHITE[3]
                if rr < 1.6:
                    q[x, y] = hexc("f28cb8")
                elif rr < 2.4:
                    q[x, y] = hexc("f7b8d3")
    save(lt, "block/sweetwood_log_top.png")

    # --- Candy Planks: light grey-white planks with seams and a faint pink sugar glaze.
    pl = blank(); p = pl.load()
    seam = [3, 11, 6, 14]
    for y in range(16):
        row = y // 4
        for x in range(16):
            if y % 4 == 3:
                p[x, y] = hexc("aaa4a8")
            elif x == seam[row]:
                p[x, y] = hexc("b8b2b6")
            elif y % 4 == 0:
                p[x, y] = CANE_WHITE[4] if R.random() < 0.7 else CANE_WHITE[3]
            else:
                p[x, y] = R.choice([CANE_WHITE[2], CANE_WHITE[2], CANE_WHITE[3], CANE_WHITE[1]])
        if y % 4 in (1, 2):
            for x in range(16):
                if R.random() < 0.08:
                    p[x, y] = hexc("f3d7e4")
    for row in range(4):  # nail dots
        x = (seam[row] + 2) % 16
        p[x, row * 4 + 1] = hexc("9a9498")
    save(pl, "block/sweetwood_planks.png")

    # --- Candy Leaves: clustered magenta leaves with depth shading (transparent gaps).
    lv = blank(); v = lv.load()
    n2 = smooth_noise(16, 23)
    for y in range(16):
        for x in range(16):
            dens = 0.55 + 0.35 * n2[y][x]
            if R.random() < dens:
                v[x, y] = LEAF[1 + min(4, int(n2[y][x] * 3 + R.random() * 2.2))]
    src = lv.copy(); sp = src.load()
    for y in range(16):
        for x in range(16):
            if sp[x, y][3] and sp[x, (y + 1) % 16][3] == 0:
                v[x, y] = shade(sp[x, y], 0.78)
            if sp[x, y][3] and sp[x, (y - 1) % 16][3] == 0 and R.random() < 0.6:
                v[x, y] = LEAF[5]
    save(lv, "block/cotton_candy_leaves.png")

    # --- Candy Sapling: tiny candy cane with a magenta tuft.
    sap = art([
        "................",
        "................",
        ".....aab........",
        "....abccbb......",
        "...abcddcbb.....",
        "...bcddddcb.....",
        "....bcddcb......",
        ".....bWRb.......",
        "......WR........",
        "......RW........",
        "......WR........",
        "......RW........",
        "......WR........",
        "......RW........",
        "......WR........",
        "................",
    ], {"a": LEAF[5], "b": LEAF[2], "c": LEAF[3], "d": LEAF[4], "W": CANE_WHITE[3], "R": CANE_RED[2]})
    save(sap, "block/sweetwood_sapling.png")

    # --- Red Licorice Plant: branching magenta stalk with curled red licorice tips.
    lic = art([
        "................",
        "...rr......rr...",
        "..rR..ppp...Rr..",
        "..R..pP.Pp...R..",
        "..R.pP...Pp..R..",
        "..rpP.....Ppr...",
        "...P...M...P....",
        ".......M........",
        ".rr...MMm...rr..",
        "rR..ppMm.pp..Rr.",
        "R..pP.Mm..Pp..R.",
        "r.pP..Mm...Pp.r.",
        ".pP...Mm....Pp..",
        "......Mm........",
        ".....mMMm.......",
        "....mmmmmm......",
    ], {"r": LICORICE_RED[3], "R": LICORICE_RED[2], "p": hexc("e05ad6"), "P": hexc("b8329f"),
        "M": hexc("c83cb5"), "m": hexc("8e2380")})
    save(lic, "block/red_licorice_plant.png")

    # --- Gingerbread: baked crust border, spice specks, slightly puffy centre.
    def ginger_base(seed):
        rng = random.Random(seed)
        im = blank(); g = im.load()
        for y in range(16):
            for x in range(16):
                edge = min(x, y, 15 - x, 15 - y)
                base = 3 if edge > 1 else (2 if edge == 1 else 1)
                if edge > 3 and rng.random() < 0.35:
                    base = 4
                if rng.random() < 0.18:
                    base = max(0, base - 1)
                g[x, y] = GINGER[base]
        for _ in range(8):
            x, y = rng.randrange(2, 14), rng.randrange(2, 14)
            g[x, y] = GINGER[0]
        for x in range(16):  # top/left crust highlight, bottom/right shadow
            g[x, 0] = GINGER[2]; g[0, x] = GINGER[2]
            g[x, 15] = GINGER[0]; g[15, x] = GINGER[0]
        return im
    gb = ginger_base(31)
    save(gb, "block/gingerbread_block.png")

    fr = ginger_base(31); f = fr.load()
    wave = [6, 7, 8, 8, 7, 6, 6, 7, 8, 8, 7, 6, 6, 7, 8, 8]
    for x in range(16):
        top_y = wave[x]
        for y in range(top_y - 1, top_y + 2):
            f[x, y] = ICING[3] if y == top_y - 1 else ICING[2]
        f[x, top_y + 2] = ICING[0]  # shadow under the icing
        f[x, top_y + 3] = shade(f[x, top_y + 3], 0.8)
        if x % 4 == 1:  # icing drip
            f[x, top_y + 2] = ICING[2]; f[x, top_y + 3] = ICING[1]
    save(fr, "block/frosted_gingerbread_block.png")

    # --- Candy door (bottom/top) and item.
    frame = CANE_RED[1]
    def door_half(top):
        im = blank(); d = im.load()
        for y in range(16):
            for x in range(16):
                if x in (0, 15) or (top and y == 0) or (not top and y == 15):
                    k = (x + y) % 6
                    d[x, y] = CANE_RED[2] if k < 3 else CANE_WHITE[3]
                else:
                    d[x, y] = CANE_WHITE[2] if (y % 5) else CANE_WHITE[1]
        if top:
            for y in range(3, 11):  # window with pink glass and candy mullion
                for x in range(3, 13):
                    if x in (3, 12) or y in (3, 10):
                        d[x, y] = frame
                    elif x in (7, 8):
                        d[x, y] = CANE_WHITE[4] if y % 2 else CANE_RED[2]
                    else:
                        d[x, y] = hexc("f6a6cf", 255) if (x + y) % 5 else hexc("ffd6ea")
        else:
            for (y0, y1) in [(2, 7), (9, 13)]:  # two raised panels
                for y in range(y0, y1 + 1):
                    for x in range(3, 13):
                        if y == y0 or x == 3:
                            d[x, y] = CANE_WHITE[4]
                        elif y == y1 or x == 12:
                            d[x, y] = CANE_WHITE[0]
                        else:
                            d[x, y] = CANE_WHITE[2] if (x + y) % 7 else CANE_WHITE[3]
                d[7, (y0 + y1) // 2] = CANE_RED[2]; d[8, (y0 + y1) // 2] = CANE_RED[2]  # candy stud
            d[11, 7] = hexc("f2c14e"); d[11, 8] = hexc("b8862b")  # handle
        return im
    save(door_half(True), "block/sweetwood_door_top.png")
    save(door_half(False), "block/sweetwood_door_bottom.png")
    di = art([
        "....rWrWrWrW....",
        "....WppppppW....",
        "....rpPWWPpr....",
        "....WpPrrPpW....",
        "....rpPWWPpr....",
        "....WppppppW....",
        "....rWWWWWWr....",
        "....WwwwwwwW....",
        "....rwWWWWwr....",
        "....WwWssWwW....",
        "....rwWWWWgr....",
        "....WwwwwwwW....",
        "....rwWWWWwr....",
        "....WwWssWwW....",
        "....rwwwwwwr....",
        "....WrWrWrWr....",
    ], {"r": CANE_RED[2], "W": CANE_WHITE[4], "w": CANE_WHITE[2], "s": CANE_RED[1], "p": hexc("f6a6cf"),
        "P": hexc("ffd6ea"), "g": hexc("f2c14e")})
    save(di, "item/sweetwood_door.png")

    # --- Fluids (animated): melted chocolate and bubbaloo.
    def fluid(name, pal, frames, seed, bubbles=None, flow=False):
        sheet = blank(16, 16 * frames)
        sp = sheet.load()
        rng = random.Random(seed)
        waves = [(rng.randint(1, 2), rng.randint(-2, 2) or 1, rng.random() * math.tau, rng.uniform(0.6, 1.0)) for _ in range(4)]
        for f in range(frames):
            ph = f / frames * math.tau
            for y in range(16):
                for x in range(16):
                    v = 0.0
                    for (a, b, p0, amp) in waves:
                        yy = (y + f * 16 / frames) if flow else y
                        v += amp * math.sin(math.tau * (a * x + b * yy) / 16 + p0 + (0 if flow else ph))
                    v = (v / 3.4 + 1) / 2
                    i = max(0, min(len(pal) - 1, int(v * len(pal))))
                    sp[x, f * 16 + y] = pal[i]
            if bubbles:
                for k in range(3):
                    bx = (k * 5 + 3) % 16
                    by = int((k * 7 + f * 1.3) % 16) if not flow else (k * 6 + f) % 16
                    life = (f + k * 5) % frames
                    if life < frames // 2:
                        sp[bx, f * 16 + by] = bubbles[0]
                        if life > 2:
                            sp[(bx + 1) % 16, f * 16 + by] = bubbles[1]
        save(sheet, f"block/{name}.png")
        with open(os.path.join(T, f"block/{name}.png.mcmeta"), "w") as fh:
            fh.write('{\n  "animation": {\n    "frametime": %d,\n    "interpolate": true\n  }\n}\n' % (3 if not flow else 2))
    CHOC_LIQ = [hexc("3d200f"), hexc("4a2814"), hexc("57301a"), hexc("643920"), hexc("744428"), hexc("8a5634")]
    fluid("melted_chocolate_still", CHOC_LIQ, 16, 41, bubbles=(hexc("a06a42"), hexc("8a5634")))
    fluid("melted_chocolate_flow", CHOC_LIQ, 16, 42, flow=True)
    GUM_LIQ = [hexc("c2387f"), hexc("d4458f"), hexc("e3569f"), hexc("ef6bb0"), hexc("f888c2"), hexc("ffb0d8")]
    fluid("bubbaloo_still", GUM_LIQ, 16, 43, bubbles=(hexc("ffe0f0"), hexc("ffc4e2")))
    fluid("bubbaloo_flow", GUM_LIQ, 16, 44, flow=True)

def jelly_blocks():
    """A block packed with glossy jelly beans of one colour (beans wrap around the edges so it tiles)."""
    # (centre x, centre y, radius x, radius y): a staggered layout that covers most of the face.
    beans = [(2.5, 1.5, 2.6, 1.7), (8.5, 2.0, 2.7, 1.7), (14.0, 4.0, 1.7, 2.6), (4.0, 5.5, 1.7, 2.6),
             (9.5, 6.5, 2.7, 1.7), (1.0, 9.5, 1.7, 2.6), (6.5, 10.0, 2.7, 1.7), (13.0, 10.0, 2.7, 1.7),
             (3.5, 14.0, 2.7, 1.7), (10.0, 13.5, 1.7, 2.6), (15.0, 15.0, 2.0, 1.5)]
    for name, base in JELLY.items():
        rng = random.Random(sum(map(ord, name)))
        pal = ramp(base, 6, 0.55, 1.28)
        im = blank(); px = im.load()
        for y in range(16):
            for x in range(16):
                px[x, y] = pal[1] if rng.random() < 0.8 else pal[0]  # sugar-dusted gaps
        for (cx, cy, rx, ry) in beans:
            tint = rng.uniform(0.94, 1.06)
            for y in range(-4, 5):
                for x in range(-4, 5):
                    dx, dy = x + 0.5 - (cx - int(cx)), y + 0.5 - (cy - int(cy))
                    e = (dx / rx) ** 2 + (dy / ry) ** 2
                    if e > 1.0:
                        continue
                    light = -(dx / rx + dy / ry)  # light from the top-left
                    k = 3 + (1 if light > 0.55 else 0) - (1 if light < -0.5 else 0) - (1 if e > 0.8 and light < 0 else 0)
                    px[(int(cx) + x) % 16, (int(cy) + y) % 16] = shade(pal[max(1, min(4, k))], tint)
            gx, gy = int(cx - rx * 0.45), int(cy - ry * 0.45)
            px[gx % 16, gy % 16] = pal[5]  # gloss
        save(im, f"block/{name}_jelly_bean_block.png")

# =================================================================== items
def items():
    # Lollipop: square spiral (red/white) like the original, with depth shading, on a grey stick.
    lp = art([
        "................",
        "...kkkkkkkkkk...",
        "..kRRRRRRRRRRk..",
        "..kRWWWWWWWWRk..",
        "..kRWRRRRRRWRk..",
        "..kRWRWWWWRWRk..",
        "..kRWRWRRWRWRk..",
        "..kRWRWWRWRWRk..",
        "..kRWRRRRWRWRk..",
        "..kRWWWWWWRWRk..",
        "..kRRRRRRRRWRk..",
        "...kkkkkSkkkk...",
        "........Ss......",
        "........Ss......",
        "........Ss......",
        "........ss......",
    ], {"k": hexc("8a0f22"), "R": hexc("e4123a"), "W": hexc("ffffff"), "S": hexc("d8d8d8"), "s": hexc("a8a8a8")})
    p = lp.load()
    for y in range(2, 11):  # light from top-left
        for x in range(3, 13):
            c = p[x, y]
            if c[3] and c != hexc("8a0f22"):
                f = 1.0 + 0.08 * ((6 - x) + (6 - y)) / 6
                p[x, y] = shade(c, min(1.08, max(0.8, f)))
    save(lp, "item/lollipop.png")

    # Chocolate bar: six dark segments over a red wrapper with a foil edge.
    cb = art([
        "................",
        "....kkkkkkkk....",
        "....kHhkHhhk....",
        "....khcdkhcd....",
        "....kccdkccd....",
        "....kddkkddk....",
        "....kHhkHhhk....",
        "....khcdkhcd....",
        "....kccdkccd....",
        "....kddkkddk....",
        "....FFFFFFFF....",
        "....rRRRRRRr....",
        "....pPPPPPPp....",
        "....rRRRRRRr....",
        "....xrrrrrrx....",
        "................",
    ], {"k": hexc("2a140a"), "H": hexc("7a4a2c"), "h": hexc("5e3520"), "c": hexc("4a2816"), "d": hexc("361c0e"),
        "F": hexc("d9d9e0"), "r": hexc("8a0f16"), "R": hexc("b81d24"), "p": hexc("d84a52"), "P": hexc("f07a80"),
        "x": hexc("5c0a0f")})
    save(cb, "item/chocolate_bar.png")

    # Cotton candy: fluffy pink cloud with lighter puffs on a paper stick.
    cc = art([
        "................",
        ".....lLLl.......",
        "....lLWWLll.....",
        "...lLWWLLLLl....",
        "..lLLLLLppLLl...",
        "..LLWLLppppLl...",
        ".lLWWLLpPPpLLl..",
        ".lLLLLpPPPppLl..",
        "..lLLppPPpLLl...",
        "...lppPPPpll....",
        "....lpPPpl......",
        "......SP........",
        "......Ss........",
        "......Ss........",
        "......Ss........",
        "......ss........",
    ], {"l": hexc("f59ad7"), "L": hexc("ffbde9"), "W": hexc("fff0fa"), "p": hexc("e984c8"), "P": hexc("d36cb3"),
        "S": hexc("f2efe9"), "s": hexc("c9c3ba")})
    save(cc, "item/cotton_candy.png")

    # Red licorice: twisted rope, diagonal.
    def licorice(pal):
        im = blank(); d = im.load()
        for i in range(12):
            x, y = 2 + i, 13 - i
            for (dx, dy, k) in [(0, 0, 2), (1, 0, 3), (0, 1, 1), (1, 1, 2)]:
                xx, yy = x + dx, y + dy
                if 0 <= xx < 16 and 0 <= yy < 16:
                    kk = k + (1 if i % 3 == 0 and (dx, dy) == (1, 0) else 0) - (1 if i % 3 == 2 and (dx, dy) == (0, 1) else 0)
                    d[xx, yy] = pal[max(0, min(4, kk))]
        d[13, 2] = pal[4]; d[2, 14] = pal[0]
        return outline(im, pal[0])
    save(licorice(LICORICE_RED), "item/red_licorice.png")
    save(licorice(LICORICE_COOKED), "item/cooked_licorice.png")

    # Brown sugar brick: small isometric brick with sugar sparkles.
    bs = art([
        "................",
        "................",
        "................",
        "......kkkkkkk...",
        "....kkTTTTtTTk..",
        "..kkTTtTTTTTSk..",
        ".kTTTTTTTtTSSk..",
        ".kFFFFFFFFSSSk..",
        ".kFfFFFFfFSSSk..",
        ".kFFFFfFFFSSk...",
        ".kFFFFFFFFSk....",
        ".kfFFFFfFFk.....",
        "..kkkkkkkk......",
        "................",
        "................",
        "................",
    ], {"k": hexc("3b1f07"), "T": hexc("c98a3a"), "t": hexc("f0d6a8"), "F": hexc("a0621f"), "f": hexc("e2bb7f"),
        "S": hexc("6e3f12")})
    save(bs, "item/brown_sugar_brick.png")

    # Brown sugar sword.
    sw = art([
        "................",
        "............kkk.",
        "...........kTTk.",
        "..........kTtSk.",
        ".........kTtSk..",
        "........kTtSk...",
        ".......kTtSk....",
        "..kk..kTtSk.....",
        "..kGk kTSk......",
        "...kGkSSk.......",
        "....kGGk........",
        "...kHkGGk.......",
        "..kHhk.kGk......",
        ".kHhk...kk......",
        ".kHk............",
        "..k.............",
    ], {"k": hexc("2e1604"), "T": hexc("c98a3a"), "t": hexc("f3dcae"), "S": hexc("8a5318"),
        "G": hexc("6e3f12"), "H": hexc("8a5318"), "h": hexc("5a320c")})
    save(sw, "item/brown_sugar_sword.png")

    # Gingerbread man with icing face, buttons and cuffs.
    gm = art([
        "......kkkk......",
        ".....kGGGGk.....",
        "....kGWGGWGk....",
        "....kGGGGGGk....",
        "....kGWWWWGk....",
        ".....kGGGGk.....",
        "..kkkGGRGGkkk...",
        ".kGGWGGGGGWGGk..",
        ".kGgkGGNGGkgGk..",
        "..kk.kGGGk.kk...",
        ".....kGRGGk.....",
        "....kGGkGGGk....",
        "...kGGk.kGGk....",
        "...kWWk.kWWk....",
        "...kgk...kgk....",
        "....k.....k.....",
    ], {"k": hexc("4a2608"), "G": hexc("b8772a"), "g": hexc("8a5318"), "W": hexc("ffffff"),
        "R": hexc("e3263a"), "N": hexc("3fbf4b")})
    emboss_edges(gm, 1.0, 1.0)
    save(gm, "item/gingerbread.png")

    # Gumdrop (soldier ammunition, also a small food).
    gd = art([
        "................",
        "................",
        "................",
        "................",
        "......kkkk......",
        ".....kWLLGk.....",
        "....kLWLLGGk....",
        "....kLLLGGGk....",
        "...kLLLGGGGDk...",
        "...kLLGGGGDDk...",
        "...kGGGGGDDDk...",
        "....kkkkkkkk....",
        "................",
        "................",
        "................",
        "................",
    ], {"k": hexc("7a0f22"), "W": hexc("ffe0e6"), "L": hexc("ff6f86"), "G": hexc("e3263a"), "D": hexc("a8132a")})
    for (x, y) in [(6, 7), (9, 9), (7, 10)]:
        gd.putpixel((x, y), hexc("ffffff"))  # sugar crystals
    save(gd, "item/gumdrop.png")

    # Roll cake: log of sponge with a cream/jam spiral on its end.
    rc = art([
        "................",
        "................",
        "........kkkkkk..",
        "......kkBBBBBBk.",
        "....kkBBbBBBBBk.",
        "..kkBBBBBBBbBDk.",
        ".kccccBBBBBBDDk.",
        "kcCCCCcBBbBDDk..",
        "kCJJJCCcBBDDk...",
        "kCJCCJCcBDDk....",
        "kCJCJJCcDDk.....",
        "kCCJJCCcDk......",
        "kcCCCCcckk......",
        ".kcccckk........",
        "..kkkk..........",
        "................",
    ], {"k": hexc("3b1d0b"), "B": hexc("8a4a1c"), "b": hexc("a8622c"), "D": hexc("5e2f10"),
        "c": hexc("c9a46a"), "C": hexc("fff1c9"), "J": hexc("c0142e")})
    save(rc, "item/roll_cake.png")

    # Dead jelly snake: curled tri-colour snake, X eye.
    ds = art([
        "................",
        "......rrrrRR....",
        "....GGrRRRRRRk..",
        "...GgGkk...kRRk.",
        "..GxGk......kRk.",
        "..GGk.......kRk.",
        "...k........kRk.",
        "............kRk.",
        "....oooooooRRRk.",
        "..ooOOOOOOoRRk..",
        ".oOOkkkkkkkkk...",
        ".oOk............",
        ".oOk............",
        ".oOk............",
        "..ok............",
        "................",
    ], {"G": hexc("3ec93a"), "g": hexc("7ef06e"), "x": hexc("101010"), "k": hexc("1f1f1f"),
        "r": hexc("ff5a5a"), "R": hexc("e01616"), "o": hexc("f2b04a"), "O": hexc("d8861e")})
    save(ds, "item/dead_jelly_snake.png")

    # Jelly bazooka: segmented candy tube (orange breech, red barrel, green muzzle), diagonal.
    jb = blank(); q = jb.load()
    segs = [(0, 4, JELLY["orange"]), (4, 9, hexc("d42020")), (9, 14, JELLY["green"])]
    for t in range(14):
        base = next(c for (a0, a1, c) in segs if a0 <= t < a1)
        pal = ramp(base, 4, 0.7, 1.3)
        cx, cy = 1 + t, 1 + t
        for (dx, dy, k) in [(1, -1, 3), (0, 0, 2), (1, 0, 2), (0, 1, 1), (-1, 1, 1)]:
            x, y = cx + dx, cy + dy
            if 0 <= x < 16 and 0 <= y < 16:
                ring = t in (3, 4, 8, 9)
                q[x, y] = shade(pal[k], 0.75) if ring else pal[k]
    for (x, y, c) in [(6, 9, "P"), (6, 10, "P"), (5, 10, "P"), (5, 11, "P"), (9, 4, "Y"), (10, 3, "Y")]:
        q[x, y] = hexc("e03fae") if c == "P" else hexc("f2e85a")
    q[14, 14] = hexc("1a6a1a"); q[13, 14] = hexc("1a6a1a")  # muzzle opening
    outline(jb, hexc("1f1f1f"))
    save(jb, "item/jelly_bazooka.png")

    # Heavy boots.
    hb = art([
        "................",
        "................",
        "..kkkk...kkkk...",
        "..kWMk...kWMk...",
        "..kWMk...kWMk...",
        "..kWMk...kWMk...",
        "..kWMk...kWMk...",
        "..kLMk...kLMk...",
        "..kLMk...kLMk...",
        "..kLMMkk.kLMMkk.",
        "..kLMMMDkkLMMMDk",
        "..kDDDDDkkDDDDDk",
        "..kkkkkkk.kkkkkk",
        "................",
        "................",
        "................",
    ], {"k": hexc("1b1b1e"), "W": hexc("d2d2d6"), "L": hexc("a9a9ae"), "M": hexc("6e6e74"), "D": hexc("45454b")})
    save(hb, "item/heavy_boots.png")

    # Jelly beans (small food).
    jbn = blank()
    for (x, y, col) in [(3, 6, "red"), (7, 4, "green"), (10, 7, "yellow"), (5, 10, "purple"), (9, 11, "orange"), (12, 4, "red")]:
        base = JELLY[col]
        pal = ramp(base, 5, 0.55, 1.3)
        for (dx, dy, k) in [(0, 0, 3), (1, 0, 3), (2, 0, 2), (0, 1, 2), (1, 1, 2), (2, 1, 1)]:
            jbn.putpixel((x + dx, y + dy), pal[k])
        jbn.putpixel((x, y), pal[4])
    outline(jbn, hexc("2a1a2a"))
    save(jbn, "item/jelly_beans.png")

    # Buckets: our own metal bucket shape with a liquid top.
    def bucket(name, liquid, hi):
        b = art([
            "................",
            "................",
            "................",
            "...kkkkkkkkkk...",
            "..kMLLLLLLLLMk..",
            "..kMlllllhlMMk..",
            "..kkMlhlllMMkk..",
            "..kSkkkkkkkkSk..",
            "...kSWWSSSDDk...",
            "...kSWSSSSSDk...",
            "...kSWSSSSDDk...",
            "....kSSSSSDk....",
            "....kSSSSDDk....",
            ".....kkkkkk.....",
            "................",
            "................",
        ], {"k": hexc("2b2b30"), "M": hexc("8c8c94"), "S": hexc("b4b4bc"), "W": hexc("e4e4ea"), "D": hexc("7a7a82"),
            "L": liquid, "l": shade(liquid, 0.8), "h": hi})
        save(b, f"item/{name}.png")
    bucket("melted_chocolate_bucket", hexc("6b3d22"), hexc("a06a42"))
    bucket("bubbaloo_bucket", hexc("ef6bb0"), hexc("ffd0ea"))

    def egg(name, base, spot, spot2=None):
        im = art([
            "................",
            "......kkkk......",
            ".....kBBBBk.....",
            "....kBHBBBBk....",
            "....kHBBBBBk....",
            "...kBBBBBBBBk...",
            "...kBBBBBBBBk...",
            "..kBBBBBBBBBDk..",
            "..kBBBBBBBBBDk..",
            "..kBBBBBBBBDDk..",
            "..kBBBBBBBBDDk..",
            "...kBBBBBBDDk...",
            "...kDBBBBDDDk...",
            "....kDDDDDDk....",
            ".....kkkkkk.....",
            "................",
        ], {"k": shade(base, 0.45), "B": base, "H": shade(base, 1.3), "D": shade(base, 0.78)})
        pts = [(6, 4), (9, 6), (5, 8), (10, 10), (7, 11), (8, 8)]
        for i, (x, y) in enumerate(pts):
            c = spot if (spot2 is None or i % 2 == 0) else spot2
            im.putpixel((x, y), c); im.putpixel((x + 1, y), shade(c, 0.85))
        save(im, f"item/{name}_spawn_egg.png")
    egg("candy_tube_dog", hexc("b3165e"), hexc("ffffff"))
    egg("lollipop_guy", hexc("ffffff"), hexc("e4123a"), hexc("1f1f1f"))
    egg("grape_spider", hexc("7b3fa0"), hexc("5fbf4f"))
    egg("brown_sugar_rhino", hexc("8a5318"), hexc("e2bb7f"))
    egg("cotton_candy_tornado", hexc("ffbde9"), hexc("d36cb3"))
    egg("bubblegum", hexc("ff7fc2"), hexc("1f1f1f"))
    egg("gingerbread_warrior", hexc("b8772a"), hexc("ffffff"), hexc("e3263a"))
    egg("gingerbread_soldier", hexc("b8772a"), hexc("ffffff"), hexc("1f4f2a"))
    egg("gingerbread_king", hexc("b8772a"), hexc("f2c230"), hexc("7b3fb0"))
    egg("jelly_bunny", hexc("f2e85a"), hexc("e03fae"), hexc("3ec93a"))
    egg("jelly_snake", hexc("3ec93a"), hexc("e01616"), hexc("f2b04a"))
    egg("jelly_shark", hexc("d13a9e"), hexc("4bbf3f"), hexc("f0aa5a"))
    egg("roll_cake_monster", hexc("8a4a1c"), hexc("fff1c9"), hexc("c0142e"))
    egg("bubbaloo_creeper", hexc("f06aa8"), hexc("7a1450"))

# =================================================================== entities
def box(img, u, v, w, h, d, side, top=None, front=None, bottom=None, back=None, right=None, left=None):
    """Paints a model cube's UV net. Each face is a colour or a callable (x, y, fw, fh) -> colour."""
    px = img.load()
    def fill(x0, y0, fw, fh, col):
        for y in range(fh):
            for x in range(fw):
                c = col(x, y, fw, fh) if callable(col) else col
                if c is not None and 0 <= x0 + x < img.width and 0 <= y0 + y < img.height:
                    px[x0 + x, y0 + y] = c
    top = top or side; front = front or side; bottom = bottom or side; back = back or side
    right = right or side; left = left or side
    fill(u + d, v, w, d, top)
    fill(u + d + w, v, w, d, bottom)
    fill(u, v + d, d, h, right)
    fill(u + d, v + d, w, h, front)
    fill(u + d + w, v + d, d, h, left)
    fill(u + 2 * d + w, v + d, w, h, back)

def tex(pal, rng, weights=None):
    """A face filler: shaded noise from a palette, darker toward the bottom of the face."""
    def f(x, y, w, h):
        c = rng.choices(pal, weights=weights)[0]
        if h > 2 and y == h - 1:
            c = shade(c, 0.82)
        if h > 2 and y == 0:
            c = shade(c, 1.08)
        return c
    return f

def entities():
    rng = random.Random(7)
    dark = hexc("1a0f14"); white = hexc("ffffff")

    # ---------------- Candy Tube Dog: magenta tubes with dark red rings and white details.
    MAG = [hexc("b3165e"), hexc("c41f6a"), hexc("a8125a")]
    RING = hexc("6e0a24")
    def tube(x, y, w, h):
        if y % 4 == 3:
            return RING
        if y % 4 == 0:
            return hexc("d94a8a")
        return rng.choice(MAG)
    def tube_long(x, y, w, h):  # body: rings across its length (the z axis is the face's x on sides, y on top)
        return tube(y, x, w, h) if w > h else tube(x, y, w, h)
    dog = blank(64, 32)
    def body_side(x, y, w, h):
        if x % 4 == 3:
            return RING
        if y == 0:
            return hexc("d94a8a")
        return rng.choice(MAG)
    def body_top(x, y, w, h):
        if y % 4 == 3:
            return RING
        if x in (2, 3):
            return hexc("f2f2f2")  # white stripe along the back
        return rng.choice(MAG)
    box(dog, 0, 0, 6, 6, 12, body_side, top=body_top, bottom=hexc("8e0e4a"),
        front=lambda x, y, w, h: rng.choice(MAG), back=lambda x, y, w, h: rng.choice(MAG))
    def dog_face(x, y, w, h):
        if y == 2 and x in (1, 4):
            return dark
        if y == 1 and x in (1, 4):
            return white
        return rng.choice(MAG)
    box(dog, 0, 18, 6, 6, 4, lambda x, y, w, h: rng.choice(MAG), front=dog_face, top=hexc("c41f6a"))
    def snout(x, y, w, h):
        return dark if (y == 0 and x == 1) else (hexc("e6e6e6") if y == 2 else white)
    box(dog, 20, 18, 3, 3, 3, white, front=snout)
    box(dog, 36, 0, 2, 2, 1, RING)
    def leg(x, y, w, h):
        if y >= 4:
            return white if y == 4 else hexc("e6e6e6")
        return tube(x, y, w, h)
    box(dog, 36, 18, 2, 6, 2, leg, bottom=hexc("d0d0d0"))
    def tail(x, y, w, h):
        return white if (max(x, y) % 3 == 0) else hexc("c41f6a")
    box(dog, 44, 18, 2, 2, 6, tail)
    save(dog, "entity/candy_tube_dog.png")

    # ---------------- Lollipop Guy: red/white spiral head, thin limbs, black top hat.
    lg = blank(64, 32)
    red = hexc("e4123a")
    def spiral(x, y, w, h):
        cx, cy = (w - 1) / 2, (h - 1) / 2
        dx, dy = x - cx, y - cy
        r = max(abs(dx), abs(dy))
        a = math.atan2(dy, dx)
        band = int(r + (a / math.tau) * 2) % 2
        c = white if band == 0 else red
        if r > 4.2:
            c = hexc("a00c28")
        return shade(c, 1.05 - 0.04 * (dx + dy) / 4)
    def lg_face(x, y, w, h):
        if y == 4 and x in (3, 6):
            return dark
        if y == 6 and 3 <= x <= 6:
            return dark if x in (3, 6) else hexc("5a0614")
        return spiral(x, y, w, h)
    box(lg, 0, 0, 10, 10, 2, hexc("c00f30"), front=lg_face, back=spiral, top=hexc("c00f30"))
    box(lg, 0, 12, 4, 10, 3, lambda x, y, w, h: hexc("f0f0f0") if x % 3 else hexc("d8d8d8"))
    box(lg, 16, 12, 2, 8, 2, hexc("e8e8e8"), bottom=hexc("ffffff"))
    box(lg, 24, 12, 2, 6, 2, hexc("e8e8e8"), bottom=hexc("2a2a2a"))
    box(lg, 32, 0, 8, 1, 4, hexc("1c1c1c"), top=hexc("2a2a2a"))
    def hat(x, y, w, h):
        if y == h - 1:
            return hexc("b81d24")  # red hat band
        return hexc("232323") if x % 4 else hexc("2e2e2e")
    box(lg, 32, 5, 6, 5, 3, hat, top=hexc("303030"))
    save(lg, "entity/lollipop_guy.png")

    # ---------------- Brown Sugar Rhino (128x64).
    rh = blank(128, 64)
    SUG = [hexc("8a5318"), hexc("94591b"), hexc("7d4a14"), hexc("9e6322")]
    def sugar(x, y, w, h):
        c = rng.choice(SUG)
        if rng.random() < 0.06:
            c = hexc("e2bb7f")  # sugar crystals
        if y == h - 1:
            c = shade(c, 0.8)
        return c
    def plates(x, y, w, h):  # armour-like plates on the sides
        c = sugar(x, y, w, h)
        if x % 8 == 0 or y == 3:
            c = hexc("5e340c")
        return c
    box(rh, 0, 0, 14, 12, 24, plates, top=sugar, bottom=hexc("6e3f12"))
    def rh_face(x, y, w, h):
        if y == 3 and x in (1, 8):
            return dark
        if y == 3 and x in (2, 7):
            return hexc("f5e6c8")
        if y >= 7 and x in (3, 6):
            return hexc("4a2608")  # nostrils
        return sugar(x, y, w, h)
    box(rh, 0, 36, 10, 9, 10, sugar, front=rh_face)
    def leg_r(x, y, w, h):
        return hexc("4a2608") if y >= h - 2 else sugar(x, y, w, h)
    box(rh, 76, 0, 5, 8, 5, leg_r, bottom=hexc("3a1d06"))
    HORN = [hexc("f5ecd8"), hexc("e6d8b8"), hexc("cdbb92")]
    box(rh, 76, 14, 3, 4, 3, lambda x, y, w, h: HORN[min(2, y // 2)])
    box(rh, 76, 22, 2, 3, 2, lambda x, y, w, h: HORN[min(2, y)])
    box(rh, 90, 14, 2, 6, 1, lambda x, y, w, h: hexc("4a2608") if y > 3 else sugar(x, y, w, h))
    box(rh, 100, 0, 2, 3, 1, hexc("7d4a14"), front=hexc("c98a3a"))
    save(rh, "entity/brown_sugar_rhino.png")

    # ---------------- Cotton Candy Tornado (64x64).
    ct = blank(64, 64)
    FLUFF = [hexc("ffbde9"), hexc("ffd2f0"), hexc("f59ad7"), hexc("ffe6f6"), hexc("ea8acb")]
    def fluff(x, y, w, h):
        c = FLUFF[(x * 7 + y * 3 + rng.randrange(3)) % 5]
        if (x + 2 * y) % 6 == 0:
            c = shade(c, 0.88)  # swirl lines
        return c
    def tornado_face(x, y, w, h):
        if y in (2, 3) and x in (3, 4, 7, 8):
            return dark if y == 3 else white
        if y == 5 and 4 <= x <= 7:
            return hexc("7a1450")
        return fluff(x, y, w, h)
    box(ct, 0, 0, 12, 7, 12, fluff, front=tornado_face)
    box(ct, 0, 19, 8, 6, 8, fluff)
    box(ct, 32, 19, 4, 6, 4, fluff)
    box(ct, 0, 33, 8, 4, 8, fluff)
    save(ct, "entity/cotton_candy_tornado.png")

    # ---------------- Bubblegum (64x32).
    bg = blank(64, 32)
    GUM = [hexc("ff7fc2"), hexc("ff8fca"), hexc("f56db6"), hexc("ffa3d4")]
    def gum(x, y, w, h):
        c = rng.choice(GUM)
        if x == 0 or y == 0:
            c = shade(c, 1.1)
        if x == w - 1 or y == h - 1:
            c = shade(c, 0.85)
        if (x, y) in ((2, 2), (3, 2), (2, 3)):
            c = hexc("ffd6ec")  # glossy highlight
        return c
    def gum_face(x, y, w, h):
        if y == 7 and 3 <= x <= 6:
            return hexc("a01a64")
        return gum(x, y, w, h)
    box(bg, 0, 0, 10, 10, 10, gum, front=gum_face)
    box(bg, 40, 0, 2, 2, 1, dark, front=lambda x, y, w, h: white if (x, y) == (0, 0) else dark)
    box(bg, 40, 4, 4, 4, 3, lambda x, y, w, h: hexc("ffb8de") if (x + y) % 3 else hexc("ffd6ec"))
    save(bg, "entity/bubblegum.png")

    # ---------------- Gingerbread Warrior / Soldier (humanoid 64x64 layout).
    def gingerbread_humanoid(name, accent, hat_col=None):
        im = blank(64, 64)
        G = [hexc("b8772a"), hexc("c4842f"), hexc("a96a22"), hexc("b37522")]
        def cookie(x, y, w, h):
            c = rng.choice(G)
            if rng.random() < 0.05:
                c = hexc("7a4510")
            return c
        def zig(y0):
            def f(x, y, w, h):
                if y in (y0, y0 + 1) and (x + y) % 2 == 0:
                    return ICING[3]
                if y == y0 + 1:
                    return ICING[2]
                return cookie(x, y, w, h)
            return f
        def face(x, y, w, h):
            if y == 3 and x in (2, 5):
                return white
            if y == 5 and 2 <= x <= 5:
                return white if x in (2, 5) or y == 6 else hexc("f0f0f0")
            if y == 6 and 3 <= x <= 4:
                return white
            return cookie(x, y, w, h)
        box(im, 0, 0, 8, 8, 8, cookie, front=face)
        def torso(x, y, w, h):
            if x in (3, 4) and y in (2, 5, 8):
                return accent if y != 5 else hexc("3fbf4b")
            if y == 10 and (x % 2 == 0):
                return ICING[3]
            return cookie(x, y, w, h)
        box(im, 16, 16, 8, 12, 4, cookie, front=torso)
        box(im, 40, 16, 4, 12, 4, zig(8))
        box(im, 32, 48, 4, 12, 4, zig(8))
        box(im, 0, 16, 4, 12, 4, zig(9))
        box(im, 16, 48, 4, 12, 4, zig(9))
        if hat_col:  # hat layer: a shako-style cap
            def cap(x, y, w, h):
                if y >= h - 2:
                    return hat_col if y == h - 2 else shade(hat_col, 0.7)
                return None
            box(im, 32, 0, 8, 8, 8, cap, top=hat_col, bottom=None)
        save(im, f"entity/{name}.png")
    gingerbread_humanoid("gingerbread_warrior", hexc("e3263a"), hexc("c01b2a"))
    gingerbread_humanoid("gingerbread_soldier", hexc("2f5fd8"), hexc("1f3f9e"))

    # ---------------- Jelly Bunny (64x32).
    jbun = blank(64, 32)
    Y = ramp(JELLY["yellow"], 4, 0.8, 1.1); O = ramp(JELLY["orange"], 4, 0.8, 1.1)
    def bunny_body(x, y, w, h):
        return (O if (x + y) % 5 < 2 else Y)[rng.randrange(4)]
    def bunny_face(x, y, w, h):
        if y == 1 and x in (0, 4):
            return dark
        if y == 3 and x == 2:
            return hexc("e03fae")
        return Y[rng.randrange(4)]
    box(jbun, 0, 0, 6, 5, 8, bunny_body)
    box(jbun, 0, 13, 5, 5, 5, lambda x, y, w, h: Y[rng.randrange(4)], front=bunny_face)
    box(jbun, 20, 13, 2, 6, 1, lambda x, y, w, h: hexc("ff8a8a") if x == 0 and 1 <= y <= 4 else ramp(JELLY["red"], 3)[rng.randrange(3)])
    box(jbun, 28, 0, 2, 3, 2, lambda x, y, w, h: ramp(JELLY["green"], 3)[rng.randrange(3)])
    box(jbun, 28, 5, 3, 3, 2, lambda x, y, w, h: hexc("ff9fd6") if (x + y) % 2 else hexc("ffc2e6"))
    save(jbun, "entity/jelly_bunny.png")

    # ---------------- Jelly Snake (64x32).
    js = blank(64, 32)
    def seg(base):
        p = ramp(base, 4, 0.75, 1.15)
        return lambda x, y, w, h: p[3] if y == 0 else p[rng.randrange(3)]
    def snake_face(x, y, w, h):
        if y == 0 and x in (0, 3):
            return hexc("ffe14d")
        if y == 0 and x in (1, 2):
            return ramp(JELLY["green"], 3)[2]
        if y == 2 and x in (1, 2):
            return hexc("a01020")
        return ramp(JELLY["green"], 3)[1]
    box(js, 0, 0, 4, 3, 5, seg(hexc("3ec93a")), front=snake_face)
    box(js, 0, 8, 3, 3, 4, seg(hexc("e01616")))
    box(js, 14, 8, 3, 3, 4, seg(hexc("f2d04a")))
    box(js, 28, 8, 3, 3, 4, seg(hexc("f0962a")))
    box(js, 0, 15, 2, 2, 4, seg(hexc("e01616")))
    save(js, "entity/jelly_snake.png")

    # ---------------- Jelly Shark (128x64).
    sh = blank(128, 64)
    MG = ramp(hexc("d13a9e"), 4, 0.8, 1.1)
    def shark_side(x, y, w, h):
        if y >= h - 3:
            return ramp(JELLY["green"], 3)[rng.randrange(3)]  # green belly band
        if y == h - 4:
            return hexc("f2f2f2")
        return MG[rng.randrange(4)]
    box(sh, 0, 0, 8, 8, 18, shark_side, top=lambda x, y, w, h: MG[rng.randrange(4)],
        bottom=lambda x, y, w, h: ramp(JELLY["green"], 3)[rng.randrange(3)])
    def shark_face(x, y, w, h):
        if y == 1 and x in (0, 6):
            return dark
        if y == 4 and x % 2 == 0:
            return white  # teeth
        if y >= 4:
            return hexc("7a0f3f")
        return MG[rng.randrange(4)]
    box(sh, 0, 26, 7, 6, 7, lambda x, y, w, h: MG[rng.randrange(4)] if y < 4 else white if (x % 2 == 0 and y == 4) else MG[0],
        front=shark_face)
    OR = ramp(JELLY["orange"], 3, 0.8, 1.1)
    box(sh, 52, 0, 2, 6, 8, lambda x, y, w, h: MG[rng.randrange(4)])
    box(sh, 52, 14, 1, 8, 4, lambda x, y, w, h: OR[rng.randrange(3)])
    box(sh, 72, 0, 1, 5, 5, lambda x, y, w, h: OR[rng.randrange(3)])
    box(sh, 72, 10, 6, 1, 4, lambda x, y, w, h: OR[rng.randrange(3)])
    save(sh, "entity/jelly_shark.png")

    # ---------------- Roll Cake Monster (64x32).
    rcm = blank(64, 32)
    SP = [hexc("8a4a1c"), hexc("94521f"), hexc("7d4318")]
    def sponge(x, y, w, h):
        return rng.choice(SP)
    def spiral_end(x, y, w, h):
        cx, cy = (w - 1) / 2, (h - 1) / 2
        dx, dy = x - cx, y - cy
        r = math.hypot(dx, dy)
        a = math.atan2(dy, dx)
        band = int(r * 1.1 + a / math.tau * 2.2) % 3
        if r > 5.6:
            return SP[0]
        return [hexc("fff1c9"), hexc("c9945a"), hexc("c0142e")][band]
    def rc_face(x, y, w, h):
        if y == 4 and x in (3, 4, 7, 8):
            return dark if x in (4, 7) else white
        if y == 8 and 3 <= x <= 8:
            return hexc("3a1405")
        if y == 9 and x in (4, 7):
            return white  # fangs
        return sponge(x, y, w, h)
    box(rcm, 0, 0, 12, 12, 10, sponge, front=rc_face, right=spiral_end, left=spiral_end,
        top=lambda x, y, w, h: hexc("fff1c9") if y % 4 == 0 else sponge(x, y, w, h))
    box(rcm, 44, 0, 3, 8, 3, lambda x, y, w, h: hexc("fff1c9") if y >= 6 else sponge(x, y, w, h))
    box(rcm, 0, 22, 4, 6, 4, lambda x, y, w, h: hexc("5e2f10") if y >= 4 else sponge(x, y, w, h))
    save(rcm, "entity/roll_cake_monster.png")

    # ---------------- Bubbaloo Creeper (creeper layout 64x32, original design).
    bc = blank(64, 32)
    PG = [hexc("f06aa8"), hexc("e85a9c"), hexc("f57db5"), hexc("dc4e90"), hexc("f890c2")]
    def pg(x, y, w, h):
        c = rng.choice(PG)
        if rng.random() < 0.08:
            c = hexc("ffd0e6")  # gum bubbles
        return c
    def bc_face(x, y, w, h):
        # Original face: round eyes with a highlight and a bubble-shaped mouth.
        if (x, y) in ((1, 2), (2, 2), (5, 2), (6, 2), (1, 3), (2, 3), (5, 3), (6, 3)):
            return white if (x, y) in ((1, 2), (5, 2)) else hexc("4a0f2e")
        if 3 <= x <= 4 and 5 <= y <= 7:
            return hexc("7a1450") if y != 5 else hexc("4a0f2e")
        if y == 6 and x in (2, 5):
            return hexc("7a1450")
        return pg(x, y, w, h)
    box(bc, 0, 0, 8, 8, 8, pg, front=bc_face)
    box(bc, 16, 16, 8, 12, 4, pg)
    box(bc, 0, 16, 4, 6, 4, lambda x, y, w, h: shade(pg(x, y, w, h), 0.85))
    save(bc, "entity/bubbaloo_creeper.png")

    # ---------------- Heavy Boots armour layer (humanoid, boots only on the lower leg).
    hbl = blank(64, 32)
    def boot(x, y, w, h):
        if y < 6:
            return None
        if y == 6:
            return hexc("d2d2d6")
        return [hexc("8c8c94"), hexc("7a7a82"), hexc("6e6e74")][(x + y) % 3] if y < h - 1 else hexc("45454b")
    box(hbl, 0, 16, 4, 12, 4, boot, top=None, bottom=hexc("45454b"))
    save(hbl, "entity/equipment/humanoid/heavy.png")

def main():
    sweet_blocks(); jelly_blocks(); items(); entities()
    print("stage 2 textures generated")

if __name__ == "__main__":
    main()
