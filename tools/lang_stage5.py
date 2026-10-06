# -*- coding: utf-8 -*-
"""Stage 5 translations (Candy Cave, Impostor Cake, Ice Cream Machine). Merged over the earlier lang files."""
from lang_data import _common, N, I, B

EN = _common(
    {
        B("pink_candy_rock"): "Pink Candy Rock", B("purple_candy_rock"): "Purple Candy Rock", B("sugar_crystal"): "Sugar Crystal",
        B("ice_cream_machine"): "Ice Cream Machine", I("impostor_cake_spawn_egg"): "Impostor Cake Spawn Egg",
    },
    {
        B("pink_candy_rock") + ".desc": "Sweet rock from the Candy Caves.",
        B("purple_candy_rock") + ".desc": "Sweet rock from the Candy Caves.",
        B("sugar_crystal") + ".desc": "Glows faintly. Breaks into sugar.",
        B("ice_cream_machine") + ".desc": "Wafer Cone + flavour + milk = ice cream.",
        B("ice_cream_machine") + ".desc.2": "One Milk Bucket fills several servings.",
        B("ice_cream_machine") + ".desc.3": "Hover the ? in its window for help.",
        I("impostor_cake_spawn_egg") + ".desc": "Hostile. Looks like a cake until you get too close.",
    },
    {"impostor_cake": "Impostor Cake"},
    {
        "impostor_cake.ambient": "Impostor Cake squelches", "impostor_cake.hurt": "Impostor Cake hurts",
        "impostor_cake.death": "Impostor Cake collapses", "impostor_cake.reveal": "Impostor Cake opens wide",
        "impostor_cake.chomp": "Impostor Cake chomps", "ice_cream_machine.serve": "Ice Cream Machine serves",
    },
    {
        "enter_candy_cave": ("Rock Candy", "Find a Candy Cave below a Sweet Forest or Jelly Bean Fields"),
        "sugar_rocks": ("Sugar Rush Hour", "Mine a Sugar Crystal with Silk Touch"),
        "the_cake_is_a_lie": ("The Cake Is a Lie", "Defeat an Impostor Cake"),
        "soft_serve": ("Soft Serve", "Get an Ice Cream Machine"),
    },
    {
        f"biome.{N}.candy_cave": "Candy Cave",
        f"container.{N}.ice_cream_machine": "Ice Cream Machine",
        f"gui.{N}.ice_cream_machine.progress": "Serving: %s%%",
        f"gui.{N}.ice_cream_machine.milk": "Milk: %s of %s servings",
        f"gui.{N}.ice_cream_machine.flavor": "Flavour: %s",
        f"gui.{N}.ice_cream_machine.no_flavor": "Add a flavour",
        f"gui.{N}.ice_cream_machine.help.title": "How to use",
        f"gui.{N}.ice_cream_machine.help.1": "Top slot: Wafer Cones. Lower slot: the flavour.",
        f"gui.{N}.ice_cream_machine.help.2": "Milk Bucket in the small slot fills the tank.",
        f"gui.{N}.ice_cream_machine.help.3": "Flavours: Sugar, Sweet Berries, Cocoa Powder.",
    },
    {
        "candyCaveEnabled": "Candy Caves", "impostorCakeSpawnChance": "Impostor Cake Spawn Chance",
        "impostorCakeDisguise": "Impostor Cake Disguise", "impostorCakeRevealDistance": "Impostor Cake Reveal Distance",
        "iceCreamMachineSpeed": "Ice Cream Machine Speed", "iceCreamMachineServingsPerBucket": "Ice Cream Machine Servings per Bucket",
    },
    {},
)

PT = _common(
    {
        B("pink_candy_rock"): "Rocha Doce Rosa", B("purple_candy_rock"): "Rocha Doce Roxa", B("sugar_crystal"): "Cristal de Açúcar",
        B("ice_cream_machine"): "Máquina de Sorvete", I("impostor_cake_spawn_egg"): "Ovo Gerador de Bolo Impostor",
    },
    {
        B("pink_candy_rock") + ".desc": "Rocha doce das Cavernas de Doces.",
        B("purple_candy_rock") + ".desc": "Rocha doce das Cavernas de Doces.",
        B("sugar_crystal") + ".desc": "Brilha de leve. Quebra em açúcar.",
        B("ice_cream_machine") + ".desc": "Casquinha + sabor + leite = sorvete.",
        B("ice_cream_machine") + ".desc.2": "Um Balde de Leite rende várias porções.",
        B("ice_cream_machine") + ".desc.3": "Passe o mouse no ? da janela para ver a ajuda.",
        I("impostor_cake_spawn_egg") + ".desc": "Hostil. Parece um bolo até você chegar perto demais.",
    },
    {"impostor_cake": "Bolo Impostor"},
    {
        "impostor_cake.ambient": "Bolo Impostor chapinha", "impostor_cake.hurt": "Bolo Impostor se machuca",
        "impostor_cake.death": "Bolo Impostor desmorona", "impostor_cake.reveal": "Bolo Impostor escancara a boca",
        "impostor_cake.chomp": "Bolo Impostor morde", "ice_cream_machine.serve": "Máquina de Sorvete serve",
    },
    {
        "enter_candy_cave": ("Pedra de Açúcar", "Encontre uma Caverna de Doces abaixo de uma Floresta Doce ou dos Campos de Jujuba"),
        "sugar_rocks": ("Hora do Açúcar", "Minere um Cristal de Açúcar com Toque Suave"),
        "the_cake_is_a_lie": ("O Bolo É uma Mentira", "Derrote um Bolo Impostor"),
        "soft_serve": ("Sorvete na Máquina", "Consiga uma Máquina de Sorvete"),
    },
    {
        f"biome.{N}.candy_cave": "Caverna de Doces",
        f"container.{N}.ice_cream_machine": "Máquina de Sorvete",
        f"gui.{N}.ice_cream_machine.progress": "Servindo: %s%%",
        f"gui.{N}.ice_cream_machine.milk": "Leite: %s de %s porções",
        f"gui.{N}.ice_cream_machine.flavor": "Sabor: %s",
        f"gui.{N}.ice_cream_machine.no_flavor": "Coloque um sabor",
        f"gui.{N}.ice_cream_machine.help.title": "Como usar",
        f"gui.{N}.ice_cream_machine.help.1": "Espaço de cima: casquinhas. De baixo: o sabor.",
        f"gui.{N}.ice_cream_machine.help.2": "Balde de Leite no espaço pequeno enche o tanque.",
        f"gui.{N}.ice_cream_machine.help.3": "Sabores: Açúcar, Frutas Doces, Cacau em Pó.",
    },
    {
        "candyCaveEnabled": "Cavernas de Doces", "impostorCakeSpawnChance": "Chance de Surgir do Bolo Impostor",
        "impostorCakeDisguise": "Disfarce do Bolo Impostor", "impostorCakeRevealDistance": "Distância de Revelação do Bolo Impostor",
        "iceCreamMachineSpeed": "Velocidade da Máquina de Sorvete", "iceCreamMachineServingsPerBucket": "Porções por Balde da Máquina de Sorvete",
    },
    {},
)
