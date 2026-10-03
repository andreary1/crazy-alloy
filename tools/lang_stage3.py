# -*- coding: utf-8 -*-
"""Stage 3 translations (remodelled creatures, Gingerbread King). Merged over lang_data.py and lang_stage2.py."""
from lang_data import _common, N, I

EN = _common(
    {I("gingerbread_king_spawn_egg"): "Gingerbread King Spawn Egg"},
    {
        I("gingerbread_king_spawn_egg") + ".desc": "Boss. Slams the ground and calls his soldiers.",
        I("gingerbread_warrior_spawn_egg") + ".desc": "Hostile. Fights with its fists. Guards Gingerbread Towers.",
        I("gingerbread_soldier_spawn_egg") + ".desc": "Hostile. Shoots gumdrops from its rifle.",
        I("jelly_shark_spawn_egg") + ".desc": "Hostile. Hunts swimmers and leaps at players on the shore.",
    },
    {"gingerbread_king": "Gingerbread King"},
    {
        "gingerbread_king.ambient": "Gingerbread King grumbles", "gingerbread_king.hurt": "Gingerbread King hurts",
        "gingerbread_king.death": "Gingerbread King crumbles", "gingerbread_king.roar": "Gingerbread King roars",
        "gingerbread_king.slam": "Gingerbread King slams the ground", "gingerbread_king.summon": "Gingerbread King calls his guard",
    },
    {"defeat_gingerbread_king": ("Off With the Crown!", "Defeat the Gingerbread King")},
    {},
    {
        "gingerbreadKingInTowers": "Gingerbread King in Towers", "gingerbreadKingHealth": "Gingerbread King Health",
        "gingerbreadKingMaxGuards": "Gingerbread King Max Guards",
    },
    {},
)

PT = _common(
    {I("gingerbread_king_spawn_egg"): "Ovo Gerador de Rei de Pão de Mel"},
    {
        I("gingerbread_king_spawn_egg") + ".desc": "Chefe. Golpeia o chão e convoca seus soldados.",
        I("gingerbread_warrior_spawn_egg") + ".desc": "Hostil. Luta com os punhos. Guarda as Torres de Pão de Mel.",
        I("gingerbread_soldier_spawn_egg") + ".desc": "Hostil. Atira balas de goma com seu rifle.",
        I("jelly_shark_spawn_egg") + ".desc": "Hostil. Caça quem nada e salta em jogadores na margem.",
    },
    {"gingerbread_king": "Rei de Pão de Mel"},
    {
        "gingerbread_king.ambient": "Rei de Pão de Mel resmunga", "gingerbread_king.hurt": "Rei de Pão de Mel se machuca",
        "gingerbread_king.death": "Rei de Pão de Mel esfarela", "gingerbread_king.roar": "Rei de Pão de Mel ruge",
        "gingerbread_king.slam": "Rei de Pão de Mel golpeia o chão", "gingerbread_king.summon": "Rei de Pão de Mel convoca a guarda",
    },
    {"defeat_gingerbread_king": ("Abaixo a Coroa!", "Derrote o Rei de Pão de Mel")},
    {},
    {
        "gingerbreadKingInTowers": "Rei de Pão de Mel nas Torres", "gingerbreadKingHealth": "Vida do Rei de Pão de Mel",
        "gingerbreadKingMaxGuards": "Máximo de Guardas do Rei",
    },
    {},
)
