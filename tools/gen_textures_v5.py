#!/usr/bin/env python3
"""Stage 5 textures for Crazy Alloy: Revival: Candy Cave blocks (pink and purple candy rock, sugar crystals), the Ice
Cream Machine (block faces, lever, screen) and the Impostor Cake (creature and spawn egg).

The Impostor Cake uses the same method as gen_mobs_v3.py: ImpostorCakeModel.java carries a "// tex:<name>" tag on
every cube line and this script paints each tagged region. Original art drawn in code from the project owner's
written descriptions; no pixels copied from anywhere.
Run by tools/gen_textures.py after the stage 4 textures.
"""
import math, random
from PIL import Image, ImageDraw
from gen_textures_v2 import hexc, shade, mix, ramp, art, save, blank, smooth_noise, outline
from gen_mobs_v3 import paint, fill, WHITE, CLEAR

rng = random.Random(50)

# ================================================================== Candy Cave blocks (16x16)
def candy_rock(name, base, accent, seed):
    """Compact sweet rock: close shades of one colour in slightly wavy horizontal strata, a few darker and lighter
    grains, and the odd fleck of the other candy colour."""
    pal = ramp(base, 6, 0.74, 1.18)
    n = smooth_noise(16, seed, 4.0)
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            stripe = 0.5 + 0.5 * math.sin((y + 2.2 * n[y][x]) * math.tau / 5.0)
            v = 0.55 * stripe + 0.45 * n[(y * 3) % 16][x]
            c = pal[min(5, int(v * 5.99))]
            r = rng.random()
            if r < 0.05:
                c = shade(c, 0.86)
            elif r < 0.09:
                c = shade(c, 1.1)
            elif r < 0.105:
                c = accent
            px[x, y] = c
    save(im, f"block/{name}.png")

def sugar_crystal():
    """A cluster of small, glassy sugar crystals growing from the bottom edge (cross model, cutout)."""
    im = blank(); px = im.load()
    light, mid, dark, edge = hexc("fff4fa"), hexc("ffc6e2"), hexc("f08ac0"), hexc("c45a96")
    # (base x, half width, height, lean)
    for (bx, hw, h, lean) in [(7.5, 1.6, 13, 0.0), (4.0, 1.2, 9, -0.25), (11.0, 1.3, 10, 0.22), (2.0, 0.9, 5, -0.3), (13.5, 0.9, 6, 0.3)]:
        for i in range(h):
            y = 15 - i
            cx = bx + lean * i
            w = hw * (1.0 - (i / h) ** 2.2) + 0.3
            for x in range(16):
                d = x + 0.5 - cx
                if abs(d) <= w:
                    if abs(d) > w - 0.8:
                        c = edge if d > 0 else mid
                    elif d < 0:
                        c = light
                    else:
                        c = mid if i < h - 2 else light
                    if i == h - 1:
                        c = light
                    px[x, y] = c
                    if i < 2 and px[x, y][3]:
                        px[x, y] = dark
    save(im, "block/sugar_crystal.png")
    # A brighter, smaller version for the item icon is the same picture.

# ================================================================== Ice Cream Machine (16x16 faces)
CASING = [hexc("dedbd6"), hexc("e9e6e1"), hexc("f2f0ec"), hexc("faf9f6")]
PINK = hexc("f06a9a")
DARK = [hexc("141416"), hexc("1d1d21"), hexc("27272c")]

def casing_noise(x, y):
    return CASING[1 + (rng.random() < 0.5)] if rng.random() < 0.8 else CASING[rng.choice((0, 3))]

def machine():
    # Casing: off-white enamel with a faint panel seam.
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            px[x, y] = casing_noise(x, y)
            if x in (0, 15) or y in (0, 15):
                px[x, y] = CASING[0]
    save(im, "block/ice_cream_machine_casing.png")

    # Top: the wide lid, a pink trim stripe around it and a little hatch.
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            px[x, y] = casing_noise(x, y)
            if x in (1, 14) or y in (1, 14):
                px[x, y] = PINK
            if 5 <= x <= 10 and 5 <= y <= 10:
                px[x, y] = CASING[0] if x in (5, 10) or y in (5, 10) else CASING[3]
    save(im, "block/ice_cream_machine_top.png")

    # Front: rows 0-4 face the head (pink stripe, "lamp" window), rows 5-15 the recessed body behind the tray.
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            px[x, y] = casing_noise(x, y)
        if y == 4:
            for x in range(16):
                px[x, y] = PINK
    for x in range(5, 11):
        for y in (1, 2):
            px[x, y] = hexc("ffd6e8") if y == 1 else hexc("f7a6c8")  # backlit flavour sign
    for y in range(6, 16):
        for x in range(16):
            px[x, y] = shade(casing_noise(x, y), 0.88)  # the recess sits in shadow
    for x in range(3, 13):
        px[x, 15] = CASING[0]
    save(im, "block/ice_cream_machine_front.png")

    # Side: rows 0-4 the head band with its pink stripe, rows 5-15 the body with the ice cream icon in u 3..13.
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            px[x, y] = casing_noise(x, y)
    for x in range(16):
        px[x, 4] = PINK
    icon = [
        "..mmm..",
        ".mMMMm.",
        "mMHMMMm",
        "mMMMMDm",
        ".mDmDm.",
        ".yYyYy.",
        "..yYy..",
        "..yYy..",
        "...y...",
    ]
    pal = {"m": hexc("b0287a"), "M": hexc("e0449b"), "H": hexc("ff9ccd"), "D": hexc("c4307f"), "y": hexc("c88a1c"), "Y": hexc("f2c14a")}
    for j, row in enumerate(icon):
        for i, ch in enumerate(row):
            if ch != ".":
                px[5 + i, 6 + j] = pal[ch]
    save(im, "block/ice_cream_machine_side.png")

    # Dark mechanism: near-black with a faint highlight line.
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            px[x, y] = DARK[rng.randrange(3)]
            if y == 0:
                px[x, y] = hexc("3a3a42")
    save(im, "block/ice_cream_machine_dark.png")

    # Tray: light grey drip grid.
    im = blank(); px = im.load()
    for y in range(16):
        for x in range(16):
            px[x, y] = hexc("cfd2d6") if (x % 3 and y % 3) else hexc("a9adb3")
    save(im, "block/ice_cream_machine_tray.png")

    # Lever (entity texture, 16x16): the 1x1x4 handle net at (0,0), the 2x2x2 knob net at (0,6).
    im = blank(); px = im.load()
    for y in range(0, 5):
        for x in range(0, 10):
            px[x, y] = DARK[(x + y) % 3]
    for y in range(6, 10):
        for x in range(0, 8):
            px[x, y] = PINK if y > 6 else hexc("ff9ccd")
    save(im, "entity/ice_cream_machine_lever.png")

def gui():
    im = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    d = ImageDraw.Draw(im)
    W, H = 176, 166
    bg, light, darkc, black = hexc("c6c6c6"), hexc("ffffff"), hexc("555555"), hexc("000000")
    d.rectangle([0, 0, W - 1, H - 1], fill=bg)
    d.line([(0, 0), (W - 2, 0)], fill=black); d.line([(0, 0), (0, H - 2)], fill=black)
    d.line([(W - 1, 1), (W - 1, H - 1)], fill=black); d.line([(1, H - 1), (W - 1, H - 1)], fill=black)
    d.line([(1, 1), (W - 3, 1)], fill=light); d.line([(1, 1), (1, H - 3)], fill=light)
    d.line([(W - 2, 2), (W - 2, H - 2)], fill=darkc); d.line([(2, H - 2), (W - 2, H - 2)], fill=darkc)

    def slot(x, y, size=18):
        d.rectangle([x, y, x + size - 1, y + size - 1], fill=hexc("8b8b8b"))
        d.line([(x, y), (x + size - 2, y)], fill=hexc("373737")); d.line([(x, y), (x, y + size - 2)], fill=hexc("373737"))
        d.line([(x + 1, y + size - 1), (x + size - 1, y + size - 1)], fill=light); d.line([(x + size - 1, y + 1), (x + size - 1, y + size - 1)], fill=light)
    slot(25, 19); slot(25, 45); slot(60, 53)
    slot(119, 28, 26)
    for r in range(3):
        for c in range(9):
            slot(7 + c * 18, 83 + r * 18)
    for c in range(9):
        slot(7 + c * 18, 141)
    # milk tank (14x31 inside at 62,18) with a sunken frame
    d.rectangle([61, 17, 76, 49], fill=hexc("6f6f6f"))
    d.line([(61, 17), (76, 17)], fill=hexc("373737")); d.line([(61, 17), (61, 49)], fill=hexc("373737"))
    d.line([(62, 49), (76, 49)], fill=light); d.line([(76, 18), (76, 49)], fill=light)
    # little arrows from the cone and flavour slots into the machine
    for yy in (27, 53):
        d.line([(45, yy), (55, yy)], fill=hexc("8b8b8b"))
        d.polygon([(55, yy - 2), (58, yy), (55, yy + 2)], fill=hexc("8b8b8b"))
    # serving arrow (24x16 at 88,37)
    d.polygon([(88, 41), (104, 41), (104, 37), (111, 45), (104, 52), (104, 48), (88, 48)], fill=hexc("8b8b8b"))
    # pink icing drips along the top edge
    for x in range(3, W - 3):
        h = 2 + (1 if (x * 7) % 11 < 3 else 0) + (1 if (x * 5) % 17 == 0 else 0)
        for y in range(2, 2 + h):
            im.putpixel((x, y), hexc("f7a6c8") if y < 2 + h - 1 else hexc("e0699b"))
    # help "?" box at 158,5
    d.rectangle([158, 5, 169, 16], fill=hexc("8b8b8b"), outline=hexc("373737"))
    for (x, y) in [(162, 7), (163, 7), (164, 7), (165, 8), (165, 9), (164, 10), (163, 11), (163, 12), (163, 14)]:
        im.putpixel((x, y), light)
    save(im, "gui/container/ice_cream_machine.png")

# ================================================================== Impostor Cake (64x64)
CAKE = hexc("8b4a32")
ICING = [hexc("e9e2d8"), hexc("f6f1ea"), hexc("fffdf9")]
MOUTH = hexc("5c1224")

def crumb(x, y, w, h):
    """Baked sponge: reddish brown with darker pores and lighter crumbs."""
    r = rng.random()
    if r < 0.12:
        return shade(CAKE, 0.78)
    if r < 0.2:
        return shade(CAKE, 1.15)
    return shade(CAKE, 0.95 + 0.1 * rng.random())

def icing_px():
    return ICING[1 + (rng.random() < 0.35)] if rng.random() < 0.9 else ICING[0]

def frosted(rows):
    """Side face: `rows` of thick icing on top with irregular drips running down, sponge below."""
    drips = {}
    def f(x, y, w, h):
        if x not in drips:
            # Pixelated drips: most columns stop at the icing line, some run one to three pixels further.
            k = (x * 7 + w * 3 + h) % 9
            drips[x] = rows + (2 if k == 0 else 1 if k in (3, 5) else 3 if k == 7 and h > rows + 3 else 0)
        if y < drips[x]:
            return icing_px() if y < drips[x] - 1 or y < rows else ICING[0]
        return crumb(x, y, w, h)
    return f

def icing_top(x, y, w, h):
    # Thick white top with a few pink sprinkles.
    if rng.random() < 0.035:
        return hexc("f48bbd")
    return icing_px()

def mouth(x, y, w, h):
    # Wet dark red inside of the mouth, darker towards the back.
    c = shade(MOUTH, 1.15 - 0.4 * y / max(1, h - 1))
    return shade(c, 0.9) if rng.random() < 0.2 else c

def tongue(x, y, w, h):
    base = hexc("f07aa6")
    if w == 4 and x in (1, 2):
        return shade(base, 0.88)  # the groove down the middle
    return shade(base, 1.0 + 0.06 * (rng.random() - 0.5))

def impostor_cake():
    painters = {
        "lower_jaw": {"side": crumb, "top": mouth, "bottom": fill(shade(CAKE, 0.7))},
        "first_tier": {"side": frosted(2), "top": icing_top, "bottom": mouth},
        "middle_tier": {"side": frosted(2), "top": icing_top, "bottom": fill(shade(CAKE, 0.7))},
        "top_tier": {"side": frosted(2), "top": icing_top, "bottom": fill(shade(CAKE, 0.7))},
        "candle": {"side": lambda x, y, w, h: hexc("fdfbf6") if y % 2 == 0 else hexc("ecebe6"), "top": fill(hexc("3a2a20"))},
        "flame": {"side": lambda x, y, w, h: hexc("ffd23f"), "top": fill(hexc("fff2a8")), "bottom": fill(hexc("ff9a2e"))},
        "tooth": fill(hexc("f4ecdc")),
        "tongue": {"side": tongue, "top": tongue, "bottom": fill(shade(hexc("f07aa6"), 0.8))},
        "tongue_tip": {"side": tongue, "top": tongue, "bottom": fill(shade(hexc("f07aa6"), 0.8)), "front": fill(hexc("ff9ccd"))},
    }
    paint("impostor_cake", "ImpostorCakeModel.java", 64, 64, painters)

def items():
    egg = [
        "................",
        ".......y........",
        ".......w........",
        "......kwk.......",
        ".....kWWWk......",
        "....kWWWWWk.....",
        "....kCWCWCk.....",
        "...kCCCCCCCk....",
        "...kWWdWWWWk....",
        "..kWdWWWWdWWk...",
        "..kCCCCCCCCCk...",
        "..kCMMMMMMMCk...",
        "..kCMtTTtMMCk...",
        "...kCCtTCCCk....",
        "....kkktkkk.....",
        "................",
    ]
    save(art(egg, {"k": hexc("4a2418"), "W": hexc("fbf6ee"), "d": hexc("e9e2d8"), "C": hexc("8b4a32"), "M": hexc("5c1224"),
                   "t": hexc("f07aa6"), "T": hexc("ff9ccd"), "w": hexc("fdfbf6"), "y": hexc("ffd23f")}), "item/impostor_cake_spawn_egg.png")

def main():
    candy_rock("pink_candy_rock", hexc("e77fb5"), hexc("b45fd0"), 501)
    candy_rock("purple_candy_rock", hexc("a35bc4"), hexc("f08ac0"), 502)
    sugar_crystal()
    machine(); gui()
    impostor_cake(); items()
    print("stage 5 textures generated")

if __name__ == "__main__":
    main()
