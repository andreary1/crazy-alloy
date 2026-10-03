# -*- coding: utf-8 -*-
"""Translations. Every key in EN must exist in PT (the generator checks this)."""
N = "crazyalloy_revival"

def _common(names, descs, ents, subs, advs, gui, config, misc):
    d = {}
    for k, v in names.items():
        d[k] = v
    for k, v in descs.items():
        d[k] = v
    for k, v in ents.items():
        d[f"entity.{N}.{k}"] = v
    for k, v in subs.items():
        d[f"subtitles.{N}.{k}"] = v
    for k, (t, desc) in advs.items():
        d[f"advancements.{N}.{k}.title"] = t
        d[f"advancements.{N}.{k}.description"] = desc
    for k, v in gui.items():
        d[k] = v
    for k, v in config.items():
        d[f"{N}.configuration.{k}"] = v
    d.update(misc)
    return d

B = lambda x: f"block.{N}.{x}"
I = lambda x: f"item.{N}.{x}"

EN = _common(
    {
        B("chocolate_soil"): "Chocolate Soil", B("chocolate_grass_block"): "Frosted Chocolate Grass",
        B("chocolate_block"): "Block of Chocolate", B("chocolate_bricks"): "Chocolate Bricks",
        B("chocolate_brick_stairs"): "Chocolate Brick Stairs", B("chocolate_brick_slab"): "Chocolate Brick Slab",
        B("chocolate_brick_wall"): "Chocolate Brick Wall", B("chiseled_chocolate_bricks"): "Chiseled Chocolate Bricks",
        B("sweetwood_log"): "Sweetwood Log", B("sweetwood_planks"): "Sweetwood Planks",
        B("cotton_candy_leaves"): "Cotton Candy Leaves", B("sweetwood_sapling"): "Sweetwood Sapling",
        B("lollipop_flower"): "Lollipop Flower", B("tourmaline_ore"): "Tourmaline Ore",
        B("deepslate_tourmaline_ore"): "Deepslate Tourmaline Ore", B("tourmaline_block"): "Block of Tourmaline",
        B("chocolate_factory"): "Chocolate Factory",
        I("tourmaline"): "Tourmaline", I("cocoa_powder"): "Cocoa Powder", I("candy_tube"): "Candy Tube",
        I("chocolate_bar"): "Chocolate Bar", I("milk_chocolate"): "Milk Chocolate", I("lollipop"): "Lollipop",
        I("cotton_candy"): "Cotton Candy", I("grape"): "Grape",
        I("tourmaline_sword"): "Tourmaline Sword", I("tourmaline_pickaxe"): "Tourmaline Pickaxe", I("tourmaline_axe"): "Tourmaline Axe",
        I("tourmaline_shovel"): "Tourmaline Shovel", I("tourmaline_hoe"): "Tourmaline Hoe",
        I("tourmaline_helmet"): "Tourmaline Helmet", I("tourmaline_chestplate"): "Tourmaline Chestplate",
        I("tourmaline_leggings"): "Tourmaline Leggings", I("tourmaline_boots"): "Tourmaline Boots",
        I("candy_tube_dog_spawn_egg"): "Candy Tube Dog Spawn Egg", I("lollipop_guy_spawn_egg"): "Lollipop Guy Spawn Egg",
        I("grape_spider_spawn_egg"): "Grape Spider Spawn Egg",
    },
    {
        B("chocolate_soil") + ".desc": "Can be ground into Cocoa Powder in a Chocolate Factory.",
        B("chocolate_grass_block") + ".desc": "The frosted ground of the Sweet Forest.",
        B("sweetwood_sapling") + ".desc": "Grows a Sweetwood tree with Cotton Candy Leaves.",
        B("lollipop_flower") + ".desc": "Craft with a Stick to get a Lollipop.",
        B("tourmaline_ore") + ".desc": "Found underground between Y=-16 and Y=48. Needs an iron pickaxe.",
        B("deepslate_tourmaline_ore") + ".desc": "Larger veins appear below Y=0. Needs an iron pickaxe.",
        B("chocolate_factory") + ".desc": "Turns cocoa and sweets into chocolate treats.",
        B("chocolate_factory") + ".desc.2": "Ingredients on top, any furnace fuel below.",
        B("chocolate_factory") + ".desc.3": "Hover the ? in its window for help.",
        I("tourmaline") + ".desc": "A pink gem. Tools made from it are tougher than iron and take enchantments well.",
        I("cocoa_powder") + ".desc": "Made in the Chocolate Factory from Cocoa Beans or Chocolate Soil.",
        I("candy_tube") + ".desc": "Shed now and then by tamed Candy Tube Dogs. Crafts into Sugar.",
        I("chocolate_bar") + ".desc": "A quick snack.",
        I("milk_chocolate") + ".desc": "Filling, with a moment of Regeneration.",
        I("lollipop") + ".desc": "Sugar rush: a short burst of Speed. Candy Tube Dogs love it.",
        I("cotton_candy") + ".desc": "Light as air: a short Jump Boost.",
        I("grape") + ".desc": "Dropped by Grape Spiders.",
        I("candy_tube_dog_spawn_egg") + ".desc": "Tame with Lollipops; heal and breed with sweets.",
        I("lollipop_guy_spawn_egg") + ".desc": "Neutral. Give it Sugar and it returns a Lollipop.",
        I("grape_spider_spawn_egg") + ".desc": "Hostile. Climbs walls; its bite poisons.",
    },
    {"candy_tube_dog": "Candy Tube Dog", "lollipop_guy": "Lollipop Guy", "grape_spider": "Grape Spider"},
    {
        "candy_tube_dog.ambient": "Candy Tube Dog yips", "candy_tube_dog.hurt": "Candy Tube Dog hurts",
        "candy_tube_dog.death": "Candy Tube Dog dies", "candy_tube_dog.shed": "Candy Tube Dog sheds a candy tube",
        "lollipop_guy.ambient": "Lollipop Guy chimes", "lollipop_guy.hurt": "Lollipop Guy cracks",
        "lollipop_guy.death": "Lollipop Guy shatters", "lollipop_guy.gift": "Lollipop Guy tosses a gift",
        "grape_spider.ambient": "Grape Spider hisses", "grape_spider.hurt": "Grape Spider squishes",
        "grape_spider.death": "Grape Spider bursts", "chocolate_factory.working": "Chocolate Factory bubbles",
    },
    {
        "root": ("Crazy Alloy: Revival", "Sweet worlds, strange creatures. Start by finding a Sweet Forest in a forest-like region."),
        "enter_sweet_forest": ("A Sweet Discovery", "Enter a Sweet Forest"),
        "sugar_rush": ("Sugar Rush", "Eat a Lollipop. Lollipop Flowers and Lollipop Guys are good sources"),
        "tame_candy_tube_dog": ("Best Friend Forever", "Tame a Candy Tube Dog with Lollipops"),
        "grape_crusher": ("Grape Crusher", "Defeat a Grape Spider"),
        "visit_cookie_hut": ("Knock Knock", "Find a Cookie Hut in a Sweet Forest"),
        "pink_gem": ("Pink Gem", "Mine Tourmaline underground"),
        "tourmaline_tools": ("Polished Edge", "Craft a Tourmaline tool or sword"),
        "tourmaline_armor": ("Pretty in Pink", "Wear a full set of Tourmaline armor"),
        "factory_floor": ("Factory Floor", "Build a Chocolate Factory"),
        "milk_chocolate": ("Smooth Operator", "Make Milk Chocolate with Cocoa Powder and a Milk Bucket"),
        "chocolate_architect": ("Chocolate Architect", "Collect every Chocolate Brick building block"),
    },
    {
        "itemGroup." + N: "Crazy Alloy: Revival",
        f"biome.{N}.sweet_forest": "Sweet Forest",
        f"container.{N}.chocolate_factory": "Chocolate Factory",
        f"gui.{N}.chocolate_factory.progress": "Progress: %s%%",
        f"gui.{N}.chocolate_factory.burning": "Heating",
        f"gui.{N}.chocolate_factory.no_fuel": "Needs fuel (coal, wood, ...)",
        f"gui.{N}.chocolate_factory.help.title": "How to use",
        f"gui.{N}.chocolate_factory.help.1": "Top slots: one or two ingredients, in any order.",
        f"gui.{N}.chocolate_factory.help.2": "Bottom slot: any furnace fuel.",
        f"gui.{N}.chocolate_factory.help.3": "Try Cocoa Beans, then Cocoa Powder + Sugar or + Milk Bucket.",
    },
    {
        "title": "Crazy Alloy: Revival Settings",
        "section.crazyalloy_revival.common.toml": "Crazy Alloy: Revival Settings",
        "section.crazyalloy_revival.common.toml.title": "Crazy Alloy: Revival Settings",
        "worldgen": "World Generation", "spawns": "Creature Spawning", "difficulty": "Difficulty", "machines": "Machines",
        "sweetForestRegionWeight": "Sweet Forest Frequency", "tourmalineVeinsPerChunk": "Tourmaline Veins per Chunk",
        "deepTourmalineVeinsPerChunk": "Deep Tourmaline Veins per Chunk", "cookieHutFrequency": "Cookie Hut Frequency",
        "candyTubeDogSpawnChance": "Candy Tube Dog Spawn Chance", "lollipopGuySpawnChance": "Lollipop Guy Spawn Chance",
        "grapeSpiderSpawnChance": "Grape Spider Spawn Chance", "mobHealthMultiplier": "Creature Health Multiplier",
        "mobDamageMultiplier": "Creature Damage Multiplier", "grapeSpiderPoisonSeconds": "Grape Spider Poison (seconds)",
        "chocolateFactorySpeed": "Chocolate Factory Speed",
    },
    {},
)

PT = _common(
    {
        B("chocolate_soil"): "Solo de Chocolate", B("chocolate_grass_block"): "Grama de Chocolate Confeitada",
        B("chocolate_block"): "Bloco de Chocolate", B("chocolate_bricks"): "Tijolos de Chocolate",
        B("chocolate_brick_stairs"): "Escada de Tijolos de Chocolate", B("chocolate_brick_slab"): "Laje de Tijolos de Chocolate",
        B("chocolate_brick_wall"): "Muro de Tijolos de Chocolate", B("chiseled_chocolate_bricks"): "Tijolos de Chocolate Talhados",
        B("sweetwood_log"): "Tronco de Madeira-Doce", B("sweetwood_planks"): "Tábuas de Madeira-Doce",
        B("cotton_candy_leaves"): "Folhas de Algodão-Doce", B("sweetwood_sapling"): "Muda de Madeira-Doce",
        B("lollipop_flower"): "Flor-Pirulito", B("tourmaline_ore"): "Minério de Turmalina",
        B("deepslate_tourmaline_ore"): "Minério de Turmalina de Ardósia", B("tourmaline_block"): "Bloco de Turmalina",
        B("chocolate_factory"): "Fábrica de Chocolate",
        I("tourmaline"): "Turmalina", I("cocoa_powder"): "Cacau em Pó", I("candy_tube"): "Tubo de Doce",
        I("chocolate_bar"): "Barra de Chocolate", I("milk_chocolate"): "Chocolate ao Leite", I("lollipop"): "Pirulito",
        I("cotton_candy"): "Algodão-Doce", I("grape"): "Uva",
        I("tourmaline_sword"): "Espada de Turmalina", I("tourmaline_pickaxe"): "Picareta de Turmalina", I("tourmaline_axe"): "Machado de Turmalina",
        I("tourmaline_shovel"): "Pá de Turmalina", I("tourmaline_hoe"): "Enxada de Turmalina",
        I("tourmaline_helmet"): "Capacete de Turmalina", I("tourmaline_chestplate"): "Peitoral de Turmalina",
        I("tourmaline_leggings"): "Calças de Turmalina", I("tourmaline_boots"): "Botas de Turmalina",
        I("candy_tube_dog_spawn_egg"): "Ovo Gerador de Cão Tubo-de-Doce", I("lollipop_guy_spawn_egg"): "Ovo Gerador de Cara-Pirulito",
        I("grape_spider_spawn_egg"): "Ovo Gerador de Aranha-Uva",
    },
    {
        B("chocolate_soil") + ".desc": "Pode virar Cacau em Pó na Fábrica de Chocolate.",
        B("chocolate_grass_block") + ".desc": "O chão confeitado da Floresta Doce.",
        B("sweetwood_sapling") + ".desc": "Cresce uma árvore de Madeira-Doce com Folhas de Algodão-Doce.",
        B("lollipop_flower") + ".desc": "Combine com um Graveto para obter um Pirulito.",
        B("tourmaline_ore") + ".desc": "Encontrado no subsolo entre Y=-16 e Y=48. Exige picareta de ferro.",
        B("deepslate_tourmaline_ore") + ".desc": "Veios maiores aparecem abaixo de Y=0. Exige picareta de ferro.",
        B("chocolate_factory") + ".desc": "Transforma cacau e doces em guloseimas de chocolate.",
        B("chocolate_factory") + ".desc.2": "Ingredientes em cima, qualquer combustível de fornalha embaixo.",
        B("chocolate_factory") + ".desc.3": "Passe o mouse no ? da janela para ver a ajuda.",
        I("tourmaline") + ".desc": "Uma gema rosa. Ferramentas feitas dela duram mais que as de ferro e aceitam bem encantamentos.",
        I("cocoa_powder") + ".desc": "Feito na Fábrica de Chocolate com Sementes de Cacau ou Solo de Chocolate.",
        I("candy_tube") + ".desc": "Solto de vez em quando por Cães Tubo-de-Doce domesticados. Vira Açúcar.",
        I("chocolate_bar") + ".desc": "Um lanche rápido.",
        I("milk_chocolate") + ".desc": "Sustenta bem e dá um instante de Regeneração.",
        I("lollipop") + ".desc": "Pico de açúcar: um breve impulso de Velocidade. Cães Tubo-de-Doce adoram.",
        I("cotton_candy") + ".desc": "Leve como o ar: um breve Super Pulo.",
        I("grape") + ".desc": "Solta por Aranhas-Uva.",
        I("candy_tube_dog_spawn_egg") + ".desc": "Domestique com Pirulitos; cure e reproduza com doces.",
        I("lollipop_guy_spawn_egg") + ".desc": "Neutro. Dê Açúcar e ele devolve um Pirulito.",
        I("grape_spider_spawn_egg") + ".desc": "Hostil. Escala paredes; sua mordida envenena.",
    },
    {"candy_tube_dog": "Cão Tubo-de-Doce", "lollipop_guy": "Cara-Pirulito", "grape_spider": "Aranha-Uva"},
    {
        "candy_tube_dog.ambient": "Cão Tubo-de-Doce late", "candy_tube_dog.hurt": "Cão Tubo-de-Doce se machuca",
        "candy_tube_dog.death": "Cão Tubo-de-Doce morre", "candy_tube_dog.shed": "Cão Tubo-de-Doce solta um tubo de doce",
        "lollipop_guy.ambient": "Cara-Pirulito tilinta", "lollipop_guy.hurt": "Cara-Pirulito racha",
        "lollipop_guy.death": "Cara-Pirulito se estilhaça", "lollipop_guy.gift": "Cara-Pirulito joga um presente",
        "grape_spider.ambient": "Aranha-Uva sibila", "grape_spider.hurt": "Aranha-Uva se espreme",
        "grape_spider.death": "Aranha-Uva estoura", "chocolate_factory.working": "Fábrica de Chocolate borbulha",
    },
    {
        "root": ("Crazy Alloy: Revival", "Mundos doces, criaturas estranhas. Comece procurando uma Floresta Doce em regiões de floresta."),
        "enter_sweet_forest": ("Uma Descoberta Doce", "Entre em uma Floresta Doce"),
        "sugar_rush": ("Pico de Açúcar", "Coma um Pirulito. Flores-Pirulito e Caras-Pirulito são boas fontes"),
        "tame_candy_tube_dog": ("Amigo para Sempre", "Domestique um Cão Tubo-de-Doce com Pirulitos"),
        "grape_crusher": ("Esmaga-Uvas", "Derrote uma Aranha-Uva"),
        "visit_cookie_hut": ("Toc Toc", "Encontre uma Cabana de Biscoito em uma Floresta Doce"),
        "pink_gem": ("Gema Rosa", "Minere Turmalina no subsolo"),
        "tourmaline_tools": ("Fio Polido", "Fabrique uma ferramenta ou espada de Turmalina"),
        "tourmaline_armor": ("Tudo Cor-de-Rosa", "Vista uma armadura completa de Turmalina"),
        "factory_floor": ("Chão de Fábrica", "Construa uma Fábrica de Chocolate"),
        "milk_chocolate": ("Cremoso", "Faça Chocolate ao Leite com Cacau em Pó e um Balde de Leite"),
        "chocolate_architect": ("Arquiteto do Chocolate", "Reúna todos os blocos de construção de Tijolos de Chocolate"),
    },
    {
        "itemGroup." + N: "Crazy Alloy: Revival",
        f"biome.{N}.sweet_forest": "Floresta Doce",
        f"container.{N}.chocolate_factory": "Fábrica de Chocolate",
        f"gui.{N}.chocolate_factory.progress": "Progresso: %s%%",
        f"gui.{N}.chocolate_factory.burning": "Aquecendo",
        f"gui.{N}.chocolate_factory.no_fuel": "Precisa de combustível (carvão, madeira...)",
        f"gui.{N}.chocolate_factory.help.title": "Como usar",
        f"gui.{N}.chocolate_factory.help.1": "Espaços de cima: um ou dois ingredientes, em qualquer ordem.",
        f"gui.{N}.chocolate_factory.help.2": "Espaço de baixo: qualquer combustível de fornalha.",
        f"gui.{N}.chocolate_factory.help.3": "Experimente Sementes de Cacau, depois Cacau em Pó + Açúcar ou + Balde de Leite.",
    },
    {
        "title": "Configurações do Crazy Alloy: Revival",
        "section.crazyalloy_revival.common.toml": "Configurações do Crazy Alloy: Revival",
        "section.crazyalloy_revival.common.toml.title": "Configurações do Crazy Alloy: Revival",
        "worldgen": "Geração de Mundo", "spawns": "Surgimento de Criaturas", "difficulty": "Dificuldade", "machines": "Máquinas",
        "sweetForestRegionWeight": "Frequência da Floresta Doce", "tourmalineVeinsPerChunk": "Veios de Turmalina por Chunk",
        "deepTourmalineVeinsPerChunk": "Veios Profundos de Turmalina por Chunk", "cookieHutFrequency": "Frequência das Cabanas de Biscoito",
        "candyTubeDogSpawnChance": "Chance de Surgir Cão Tubo-de-Doce", "lollipopGuySpawnChance": "Chance de Surgir Cara-Pirulito",
        "grapeSpiderSpawnChance": "Chance de Surgir Aranha-Uva", "mobHealthMultiplier": "Multiplicador de Vida das Criaturas",
        "mobDamageMultiplier": "Multiplicador de Dano das Criaturas", "grapeSpiderPoisonSeconds": "Veneno da Aranha-Uva (segundos)",
        "chocolateFactorySpeed": "Velocidade da Fábrica de Chocolate",
    },
    {},
)
