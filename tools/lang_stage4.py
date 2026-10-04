# -*- coding: utf-8 -*-
"""Stage 4 translations (Gingerbread Fortress, Ice Cream Truck, Ice Cream Vendor, ice creams, remodelled creatures).
Merged over the earlier lang files."""
from lang_data import _common, N, I

COLOURS_EN = {"white": "White", "orange": "Orange", "magenta": "Magenta", "light_blue": "Light Blue", "yellow": "Yellow",
              "lime": "Lime", "pink": "Pink", "gray": "Gray", "light_gray": "Light Gray", "cyan": "Cyan", "purple": "Purple",
              "blue": "Blue", "brown": "Brown", "green": "Green", "red": "Red", "black": "Black"}
COLOURS_PT = {"white": "Branca", "orange": "Laranja", "magenta": "Magenta", "light_blue": "Azul-Clara", "yellow": "Amarela",
              "lime": "Verde-Limão", "pink": "Rosa", "gray": "Cinza", "light_gray": "Cinza-Clara", "cyan": "Ciano", "purple": "Roxa",
              "blue": "Azul", "brown": "Marrom", "green": "Verde", "red": "Vermelha", "black": "Preta"}

def banners(names, colours, fmt):
    return {f"block.{N}.banner.{p}.{c}": fmt(cn, pn) for p, pn in names.items() for c, cn in colours.items()}

EN = _common(
    {
        I("wafer_cone"): "Wafer Cone", I("vanilla_ice_cream"): "Vanilla Ice Cream", I("strawberry_ice_cream"): "Strawberry Ice Cream",
        I("chocolate_ice_cream"): "Chocolate Ice Cream", I("ice_cream_vendor_spawn_egg"): "Ice Cream Vendor Spawn Egg",
    },
    {
        I("wafer_cone") + ".desc": "Crunchy on its own; fill it with ice cream.",
        I("vanilla_ice_cream") + ".desc": "Cold! A few seconds of Fire Resistance.",
        I("strawberry_ice_cream") + ".desc": "Brief Regeneration.",
        I("chocolate_ice_cream") + ".desc": "Haste for a while.",
        I("ice_cream_vendor_spawn_egg") + ".desc": "Trader. Sells ice cream, buys sugar, berries and milk.",
        I("grape_spider_spawn_egg") + ".desc": "Hostile. Crouches, then pounces; its bite poisons.",
        I("candy_tube_dog_spawn_egg") + ".desc": "Tame with Lollipops; heal and breed with sweets.",
        I("brown_sugar_rhino_spawn_egg") + ".desc": "Neutral. Scrapes the ground, then charges.",
        I("gingerbread_king_spawn_egg") + ".desc": "Boss. Waits on his throne in the Gingerbread Fortress.",
    },
    {"ice_cream_vendor": "Ice Cream Vendor"},
    {},
    {
        "visit_gingerbread_fortress": ("The Sweetest Siege", "Find a Gingerbread Fortress in a Sweet Forest"),
        "visit_ice_cream_truck": ("Ice Cream Time!", "Find an Ice Cream Truck"),
        "brain_freeze": ("Brain Freeze", "Eat vanilla, strawberry and chocolate ice cream"),
    },
    {},
    {
        "gingerbreadFortressFrequency": "Gingerbread Fortress Frequency", "gingerbreadKingInFortresses": "Gingerbread King in Fortresses",
        "iceCreamTruckFrequency": "Ice Cream Truck Frequency", "grapeSpiderPounce": "Grape Spider Pounce",
        "brownSugarRhinoCharge": "Brown Sugar Rhino Charge", "brownSugarRhinoChargeDamage": "Brown Sugar Rhino Charge Damage",
    },
    banners({"ice_cream_cone": "Cone", "ice_cream_scoop": "Scoop"}, COLOURS_EN, lambda c, p: f"{c} Ice Cream {p}"),
)

PT = _common(
    {
        I("wafer_cone"): "Casquinha", I("vanilla_ice_cream"): "Sorvete de Baunilha", I("strawberry_ice_cream"): "Sorvete de Morango",
        I("chocolate_ice_cream"): "Sorvete de Chocolate", I("ice_cream_vendor_spawn_egg"): "Ovo Gerador de Sorveteiro",
    },
    {
        I("wafer_cone") + ".desc": "Crocante sozinha; recheie com sorvete.",
        I("vanilla_ice_cream") + ".desc": "Gelado! Alguns segundos de Resistência ao Fogo.",
        I("strawberry_ice_cream") + ".desc": "Regeneração breve.",
        I("chocolate_ice_cream") + ".desc": "Pressa por um tempo.",
        I("ice_cream_vendor_spawn_egg") + ".desc": "Comerciante. Vende sorvete, compra açúcar, frutas e leite.",
        I("grape_spider_spawn_egg") + ".desc": "Hostil. Se encolhe e dá o bote; sua mordida envenena.",
        I("candy_tube_dog_spawn_egg") + ".desc": "Domestique com Pirulitos; cure e reproduza com doces.",
        I("brown_sugar_rhino_spawn_egg") + ".desc": "Neutro. Raspa o chão e depois investe.",
        I("gingerbread_king_spawn_egg") + ".desc": "Chefe. Espera no trono da Fortaleza de Pão de Mel.",
    },
    {"ice_cream_vendor": "Sorveteiro"},
    {},
    {
        "visit_gingerbread_fortress": ("O Cerco Mais Doce", "Encontre uma Fortaleza de Pão de Mel em uma Floresta Doce"),
        "visit_ice_cream_truck": ("Hora do Sorvete!", "Encontre um Caminhão de Sorvete"),
        "brain_freeze": ("Cérebro Congelado", "Coma sorvete de baunilha, de morango e de chocolate"),
    },
    {},
    {
        "gingerbreadFortressFrequency": "Frequência da Fortaleza de Pão de Mel", "gingerbreadKingInFortresses": "Rei de Pão de Mel nas Fortalezas",
        "iceCreamTruckFrequency": "Frequência do Caminhão de Sorvete", "grapeSpiderPounce": "Bote da Aranha-Uva",
        "brownSugarRhinoCharge": "Investida do Rinoceronte", "brownSugarRhinoChargeDamage": "Dano da Investida do Rinoceronte",
    },
    banners({"ice_cream_cone": "Casquinha", "ice_cream_scoop": "Bola de Sorvete"}, COLOURS_PT, lambda c, p: f"{p} {c}"),
)
