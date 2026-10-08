# Crazy Alloy Revival Mod

![Crazy Alloy Title](https://cdn.modrinth.com/data/cached_images/2e42343b4b482a0ea86c9bf13aff7d562ea49014.png)

Reconstrução e expansão do [Crazy Alloy](https://modrinth.com/mod/crazy-alloy) para **Minecraft Java 26.1.2** (NeoForge).
Namespace: `crazyalloy_revival`. Versão atual: **0.6.0-alpha (Dimensão do Sorvete e Dragão de Sorvete)**.
As seções abaixo descrevem cada etapa na ordem em que entrou; as notas completas de cada versão ficam junto dos JARs.

> Esta é uma versão alfa. Só o conteúdo listado em "Conteúdo implementado" existe; o restante do roteiro
> (outros biomas, chefes, dimensão de sorvete, magia, cogumelos, economia) ainda **não** foi feito.
> Texturas e sons são provisórios (veja "Recursos provisórios").

## Instalação

| Requisito | Versão |
|---|---|
| Minecraft Java | 26.1.2 |
| NeoForge | 26.1.2.112 ou mais novo |
| TerraBlender (NeoForge) | 26.1.2.0.3 ou mais novo |
| Java | 25 (o launcher oficial já inclui) |

1. Instale o NeoForge 26.1.2 no launcher.
2. Coloque `crazyalloy_revival-26.1.2-0.6.0-alpha.jar` e o JAR do TerraBlender na pasta `mods`.
3. Em servidores dedicados, instale os mesmos dois JARs no servidor e em todos os clientes.

O bioma só aparece em chunks gerados depois da instalação; terreno já explorado não é alterado.

## Compilar

```sh
./gradlew build          # JAR em build/libs/
./gradlew runClient      # cliente de desenvolvimento
./gradlew runServer      # servidor dedicado de desenvolvimento
./gradlew runGameTestServer          # roda os gametests sem interface e sai (código 0 = todos passaram)
./gradlew runClient -PquickPlay=<mundo>          # abre direto um mundo de run/saves
./gradlew runClient -PquickPlayServer=localhost  # entra direto no servidor do runServer
sh tools/generate_all.sh                 # regenera JSON, texturas e a estrutura (Python 3 + Pillow + nbtlib)
python3 tools/validate_resources.py  # verificação estática dos recursos
```

Os arquivos JSON, as texturas e a estrutura em `src/main/resources` são gerados pelos scripts em `tools/`;
edite os scripts, não os arquivos gerados.

## Conteúdo implementado (etapa 1)

**Bioma: Sweet Forest (Floresta Doce)**
- Substitui parte das florestas comuns e florestas de flores em regiões próprias do TerraBlender (peso configurável, padrão 3; regiões vanilla usam 10).
- Solo de chocolate com grama confeitada, árvores de Madeira-Doce com Folhas de Algodão-Doce (duas alturas), Flores-Pirulito.
- Céu, névoa e água rosados; música do cherry grove.
- Criaturas: Cão Tubo-de-Doce e Cara-Pirulito (dia), Aranha-Uva (escuro), além de mobs vanilla.

**Blocos**
- Família de construção de chocolate: Bloco de Chocolate, Tijolos, Escada, Laje, Muro, Tijolos Talhados (bancada e cortador de pedras).
- Madeira-Doce: tronco, tábuas (contam como tábuas vanilla), folhas, muda.
- Solo de Chocolate, Grama de Chocolate Confeitada, Flor-Pirulito.
- Minério de Turmalina (pedra e ardósia), Bloco de Turmalina.
- Fábrica de Chocolate (máquina).

**Turmalina e equipamentos** (entre ferro e diamante; minerar exige picareta de ferro)

| Material | Durabilidade | Velocidade | Bônus de dano | Encantabilidade |
|---|---|---|---|---|
| Ferro | 250 | 6,0 | 2,0 | 14 |
| **Turmalina** | **600** | **7,0** | **2,5** | **18** |
| Diamante | 1561 | 8,0 | 3,0 | 10 |

- Espada, picareta, machado, pá e enxada de turmalina.
- Armadura de turmalina: 2/5/7/2 pontos (ferro 2/5/6/2, diamante 3/6/8/3), resistência 1, fator de durabilidade 22.
- Receitas no formato vanilla; reparo com Turmalina.
- Minério: 4 veios/chunk entre Y=-16 e 48 e 2 veios maiores abaixo de Y=0 (configurável).

**Ingredientes e comidas**

| Item | Fome | Efeito | Origem |
|---|---|---|---|
| Barra de Chocolate | 4 | — | Fábrica: Cacau em Pó + Açúcar |
| Chocolate ao Leite | 6 | Regeneração 3 s | Fábrica: Cacau em Pó + Balde de Leite (rende 3, devolve o balde) |
| Pirulito | 2 | Velocidade 8 s | Flor-Pirulito + Graveto; Cara-Pirulito; Fábrica |
| Algodão-Doce | 1 | Super Pulo 10 s | Folhas de Algodão-Doce; Fábrica: Açúcar + Corante Rosa |
| Uva | 2 | — | Aranha-Uva |
| Cacau em Pó | — | ingrediente | Fábrica: Sementes de Cacau (×2) ou Solo de Chocolate |
| Tubo de Doce | — | vira 3 Açúcar | Solto por Cães Tubo-de-Doce domesticados |

**Criaturas**

| Criatura | Comportamento | Vida / dano | Interação | Drops |
|---|---|---|---|---|
| Cão Tubo-de-Doce | Passivo, domesticável | 12 (30 domesticado) / 3 | Domestica com Pirulito (1 em 3); senta/levanta com a mão; cura e reproduz com doces; vai até doces jogados no chão; defende o dono | Açúcar |
| Cara-Pirulito | Neutro; se atacado, ele e os vizinhos revidam | 16 / 3 | Troca: dê Açúcar e ele joga um Pirulito (espera ~1 min entre trocas) | Pirulito, Açúcar |
| Aranha-Uva | Hostil no escuro | 14 / 3 | Escala paredes, salta; mordida dá Veneno (4 s no Normal, 8 s no Difícil, configurável) | Uvas, Linha, Olho de Aranha raro |

Cada criatura tem modelo, animações de andar, sons (provisórios) e legendas próprios. O Cão balança o rabo mais rápido quando domesticado e tem pose sentada; o Cara-Pirulito balança a cabeça e bate com os dois braços.

**Estrutura: Cabana de Biscoito** — aparece só na Floresta Doce; baú com doces, cacau, mudas, turmalina e, raramente, Chocolate ao Leite ou uma picareta de turmalina gasta. Frequência configurável (desligada, rara, normal, comum).

**Máquina: Fábrica de Chocolate** — 2 espaços de ingrediente (qualquer ordem), 1 de combustível (qualquer combustível de fornalha), 1 de saída. A janela mostra chama, seta de progresso, dicas ao passar o mouse e um botão "?" com instruções. Funciona com funis (cima: ingredientes; lados: combustível; baixo: saída), guarda o progresso ao salvar o mundo e dá experiência ao retirar o resultado. As receitas são JSON (`data/crazyalloy_revival/recipe/chocolate_factory/`), então podem ser estendidas por datapacks.

**Avanços**: 12 avanços introdutórios em uma aba própria, guiando bioma, turmalina, fábrica, criaturas e cabana.

**Traduções**: português do Brasil e inglês, incluindo descrições de itens e telas de configuração.

## Guia de progressão (etapa 1)

1. Explore florestas até achar uma **Floresta Doce** (rosa, com árvores listradas). Colete Madeira-Doce, Flores-Pirulito e Algodão-Doce.
2. Faça Pirulitos e domestique um **Cão Tubo-de-Doce**. Dê Açúcar a um **Cara-Pirulito** para ganhar mais Pirulitos.
3. Procure uma **Cabana de Biscoito** para o primeiro baú de recompensas.
4. Desça entre Y=48 e Y=-16 (ou abaixo de 0 para veios maiores) com picareta de ferro e minere **Turmalina**.
5. Construa a **Fábrica de Chocolate** (turmalina, ferro, fornalha, tijolos) e transforme Sementes de Cacau em Cacau em Pó, depois em Barras e Chocolate ao Leite.
6. Use os chocolates para construir com a família de **Tijolos de Chocolate** e faça o conjunto de **equipamentos de turmalina**.

Não há dependências circulares: a Fábrica não usa chocolate, e cacau vem de selvas vanilla ou do próprio Solo de Chocolate.

## Conteúdo da etapa 2 (0.2.0-alpha)

Baseado na análise detalhada do mod original. Os IDs da etapa 1 foram mantidos (mundos antigos continuam válidos);
só os nomes mudaram para os do original: Grama de Goma, Terra de Chocolate, Tronco/Tábuas/Folhas/Muda de Doce.

**Floresta Doce refeita**
- Cores do original: céu e névoa #FF99FF, água #FF33CC. Grama rosa de goma sobre terra de chocolate.
- Árvores de bengala (tronco listrado vermelho e branco, copa redonda magenta), mais altas como no original.
- Plantas de Alcaçuz Vermelho, Flores-Pirulito e lagos de **Chocolate Derretido** (fluido próprio, com balde).
- Conjunto completo de madeira de doce: escada, laje, cerca, portão, porta, botão, placa de pressão.
- Pão de Mel: item, bloco, bloco glaceado, escada e laje.
- Criaturas: Cão Tubo-de-Doce, Cara-Pirulito (agora de cartola), **Rinoceronte de Açúcar Mascavo** (neutro, solta tijolos),
  **Tornado de Algodão-Doce** (hostil; com 10 de vida ou menos, clique com um graveto para capturá-lo e ganhar 3 algodões-doces),
  **Chiclete** (voador, estoura ao morrer). A Aranha-Uva não surge mais sozinha aqui (veja Campos de Jujuba).
- **Torre de Pão de Mel**: três andares, 2 baús e 4 geradores (Guerreiros embaixo, Soldados em cima).
  **Guerreiro de Pão de Mel** usa a Espada de Açúcar Mascavo; **Soldado de Pão de Mel** atira balas de goma.

**Campos de Jujuba (novo bioma)**
- Substitui parte das planícies nas regiões doces. Céu #99FF99, névoa #66FF33, água vermelha.
- Chão de jujuba verde com subsolo amarelo/vermelho, manchas de jujuba laranja e montes de jujuba roxa.
- Blocos de jujuba (todos elásticos): verde (pulo alto), amarela (Fadiga de Mineração ao andar), vermelha (fome ao socar),
  laranja (explode após 1 s ao ser pisada; agachado é seguro), roxa e roxa infestada (solta Aranhas-Uva).
- Criaturas: **Coelho de Gelatina** (passivo, reproduz com jujubas), **Cobra de Gelatina** (veneno), **Tubarão de Gelatina**
  (caça quem nada), **Monstro Rocambole** (neutro), **Creeper de Bubbaloo** (deixa uma poça de Bubbaloo, fluido que machuca).

**Itens e equipamentos**: Alcaçuz (cru e assado), Pão de Mel, Bala de Goma, Rocambole, Jujubas, Tijolo de Açúcar Mascavo
(arremessável), Espada de Açúcar Mascavo (empurra longe), **Bazuca de Gelatina** (atira Cobras de Gelatina Mortas),
baldes de Chocolate Derretido e Bubbaloo, 10 ovos geradores.
A Fábrica de Chocolate transforma um balde de Chocolate Derretido em 10 barras e faz balas de goma.

**Escolhas do revival** (onde o original era estranho ou desequilibrado; o espírito foi mantido):
- Pulo da jujuba 1,8x (original 3x) e queda na jujuba com metade do dano.
- Jujuba laranja com pavio de 1 s e força 2 (original: explosão instantânea força 4); configurável.
- Estouro do Chiclete força 2, configurável, respeita `mobGriefing`.
- Mordida da Cobra de Gelatina: Veneno II por 5 s (configurável). Tijolo arremessado causa 2 de dano.
- Bazuca com recarga de 1 s e 250 usos. Aranhas-Uva só saem de jujuba infestada.
- Algodão-Doce continua sendo comida (no original era um item de teletransporte).
- Receitas novas para Pão de Mel (trigo + trigo + açúcar) porque no original ele só vinha de criaturas.
- Fortaleza de Pão de Mel e o Gingerbread King ficam para a etapa de chefes.

As Botas Pesadas da etapa 2 foram removidas na 0.5.0-alpha.

## Conteúdo da etapa 5 (0.5.0-alpha)

**Bioma subterrâneo: Candy Cave (Caverna de Doces)**, embaixo das Florestas Doces e dos Campos de Jujuba.
- Rocha Doce Rosa e Rocha Doce Roxa misturadas com pedra comum, Cristais de Açúcar no chão e no teto (brilho fraco;
  com Toque Suave cai o cristal, sem ele 2 a 4 de açúcar), fendas altas e estreitas e partículas rosadas fracas
  (as três últimas são propostas do revival).
- Criaturas: Creeper de Bubbaloo e Bolo Impostor, além de zumbis e outros monstros vanilla de caverna.

**Bolo Impostor** (hostil): bolo de três andares com cobertura escorrendo e vela. Proposta do revival: fica parado,
de boca fechada, alinhado ao mundo como um bloco; quando um jogador chega perto (4 blocos, configurável) ou o ataca,
abre a boca, a língua desenrola e os andares balançam por 1 s, e só então ataca. Volta a se disfarçar depois de 10 s
sem alvo. Também aparece raramente na Floresta Doce.

**Máquina de Sorvete**: Casquinha + sabor + leite = sorvete (Açúcar = baunilha, Frutas Doces = morango,
Cacau em Pó = chocolate). Um Balde de Leite enche 4 porções no tanque (configurável) e o balde vazio fica no espaço do leite.
A janela mostra os ingredientes, o tanque de leite, o progresso e o sabor escolhido; o `?` explica o uso.
A alavanca desce, o sorvete cresce na bandeja e a alavanca volta ao fim de cada porção. Funciona com funis
(casquinha e sabor por cima, leite pelos lados, sorvete por baixo). Está dentro dos Caminhões de Sorvete, e o
sorveteiro aciona a animação da máquina ao vender um sorvete (proposta do revival). Receita: quartzo, ferro,
gelo compactado e laje de quartzo liso.

**Outras mudanças**: Tubarão de Gelatina e Rinoceronte de Açúcar Mascavo 25% maiores; Caminhões de Sorvete só na
Floresta Doce; Botas Pesadas removidas.

## Conteúdo da etapa 6 (0.6.0-alpha)

**Acesso**: o Sorveteiro agora sobe de nível como um aldeão (1 a 5, com trocas novas a cada nível) e no nível 5 vende o
**Amuleto de Sorvete** por 5 **Sorvetes Supremos**. Sorvete Supremo: grade 2x2 com Menta e Chocolate em cima, Baunilha e
Morango embaixo. Bloco de Sorvete (4 sabores): 4 sorvetes iguais em 2x2. Sorvete de Menta: Máquina de Sorvete com Samambaia
(proposta do revival) ou no Sorveteiro.

**Portal**: moldura de Blocos de Sorvete de Chocolate como a do Nether (abertura mínima 2x3, moldura 4x5, cantos opcionais).
Use o Amuleto na parte de dentro para acender. O portal leva à Dimensão do Sorvete e volta para o Mundo Normal (escala 1:1);
se não houver portal do outro lado, uma moldura com plataforma é criada.

**Dimensão do Sorvete / Planícies de Sorvete**: relevo no formato do Mundo Normal feito de sorvete: cobertura branca de
baunilha, camadas de chocolate aparecendo nos cortes, manchas de morango e menta, lagos e mares de chocolate derretido
(nível do mar 40), pináculos de três sabores (base branca, meio rosa, topo marrom, afinando em degraus), céu e névoa creme
com um brilho lilás no ar.

**Criaturas**: Zumbi de Sorvete (20 de vida), Fera de Sorvete (100 de vida, grande; renova Velocidade, Força,
Resistência e Regeneração em combate), Gárgula de Sorvete (voa, 30 de vida, dano por contato), Sorvete Vivo (uma Casquinha
nele dá o sorvete do sabor, gasta a casquinha e deixa uma Casquinha Raivosa no lugar). O Zumbi e o Sorvete Vivo têm um
ovo gerador cada; o sabor (chocolate, baunilha, morango ou menta) é uma variante sorteada ao nascer, com textura, nome e
sorvete próprios.

**Ninho de Sorvete e Dragão de Sorvete**: plataforma de obsidiana com borda baixa sobre um monte de blocos de sorvete
misturados, com escada e o Ovo do Dragão no centro, nas Planícies de Sorvete. Clique com o botão direito no ovo: 3 s depois
(partículas e som) o ovo some e nasce o Dragão (300 de vida): mordida, rajadas de 3 bolas de fogo grandes, arrancada com
Velocidade, Regeneração ao cair abaixo de 2/3 e 1/3 da vida e Casquinhas Raivosas chamadas com um rugido. Solta 10 a 15
sorvetes de cada sabor e 5 a 10 Sorvetes Supremos; quem mata recebe a conquista Matador de Dragão.

## Configuração (`config/crazyalloy_revival-common.toml`, também editável em Mods > Config)

| Opção | Padrão | Efeito |
|---|---|---|
| `worldgen.candyRegionWeight` | 3 | Frequência das regiões doces (0 desliga; exige reiniciar) |
| `worldgen.sweetForestEnabled` / `jellyBeanFieldsEnabled` | true | Liga ou desliga cada bioma |
| `worldgen.tourmalineVeinsPerChunk` | 4 | Veios comuns de turmalina |
| `worldgen.deepTourmalineVeinsPerChunk` | 2 | Veios grandes abaixo de Y=0 |
| `worldgen.cookieHutFrequency` / `gingerbreadTowerFrequency` | NORMAL | DISABLED, RARE, NORMAL, COMMON (exige reiniciar) |
| `spawns.*SpawnChance` | 1.0 | Chance de cada tentativa natural de surgimento |
| `difficulty.mobHealthMultiplier` / `mobDamageMultiplier` | 1.0 | Vida e dano das criaturas do mod |
| `difficulty.grapeSpiderPoisonSeconds` | 4 | Veneno da Aranha-Uva no Normal (dobro no Difícil) |
| `difficulty.jellySnakePoisonSeconds` | 5 | Veneno da Cobra de Gelatina |
| `difficulty.bubblegumExplosionPower` / `orangeJellyExplosionPower` | 2.0 | Força das explosões (0 desliga) |
| `machines.chocolateFactorySpeed` | 1.0 | Velocidade da Fábrica |
| `worldgen.candyCaveEnabled` | true | Caverna de Doces embaixo dos biomas doces (exige reiniciar) |
| `difficulty.impostorCakeDisguise` | true | O Bolo Impostor nasce disfarçado e volta ao disfarce |
| `difficulty.impostorCakeRevealDistance` | 4.0 | Distância em que o bolo se revela |
| `machines.iceCreamMachineSpeed` | 1.0 | Velocidade da Máquina de Sorvete |
| `machines.iceCreamMachineServingsPerBucket` | 4 | Porções por Balde de Leite |
| `worldgen.iceCreamPortalEnabled` | true | O Amuleto acende portais e os portais funcionam |
| `worldgen.iceCreamNestFrequency` | NORMAL | Frequência dos Ninhos de Sorvete (DISABLED, RARE, NORMAL, COMMON) |
| `worldgen.iceCreamPinnacles` | 1 | Tentativas de pináculo por chunk (0 desliga) |
| `spawns.iceCream*SpawnChance` / `livingIceCreamSpawnChance` / `angryIceCreamConeSpawnChance` | 1.0 | Surgimento das criaturas da dimensão |
| `difficulty.iceCreamBeastBuffs` | true | A Fera renova os próprios efeitos |
| `difficulty.iceCreamDragonHealth` | 300 | Vida base do Dragão |
| `difficulty.iceCreamDragonFireballPower` | 1 | Força da explosão das bolas de fogo (0 = sem explosão) |
| `difficulty.iceCreamDragonMaxCones` | 4 | Máximo de Casquinhas Raivosas chamadas perto do Dragão |

## Testes

Os gametests ficam em `src/gametest` (fora do JAR final) e cobrem: as três criaturas rodando IA sem travar,
domar o Cão Tubo-de-Doce com pirulitos, a troca de açúcar por pirulito do Cara-Pirulito, o veneno da Aranha-Uva,
a Fábrica de Chocolate produzindo uma barra e a muda de Madeira-Doce crescendo em solo de chocolate.
Etapa 2 (11 testes novos, 17 no total): as 10 criaturas novas rodando IA, jujuba amarela dando Fadiga de Mineração,
jujuba laranja (agachado não ativa; pisando explode), jujuba infestada soltando Aranhas-Uva, captura do Tornado com graveto,
estouro do Chiclete, poça do Creeper de Bubbaloo, Fábrica fazendo 10 barras com balde de chocolate derretido (e devolvendo o balde),
Alcaçuz só em chão doce, Bazuca gastando munição e durabilidade, Guerreiro nascendo com espada.

Testado manualmente na versão 0.1.0-alpha (NeoForge 26.1.2.112, Java 25): servidor dedicado com mundo novo,
cliente em mundo local e cliente conectado ao servidor dedicado (Fábrica, domar, troca e veneno pela rede).
Ainda não testado: surgimento natural das criaturas ao longo de uma partida, sons (o ambiente de teste não tem áudio),
equilíbrio de combate e geração do bioma em vários seeds.

Testado manualmente na versão 0.2.0-alpha: `/locate` dos dois biomas e da Torre num servidor de desenvolvimento e num
servidor NeoForge 26.1.2.112 limpo só com o JAR final e o TerraBlender; cliente conectado ao servidor mostrando a Floresta
Doce, os Campos de Jujuba, a Torre gerada naturalmente, todas as criaturas, blocos novos e os dois fluidos.
Ainda não testado: surgimento natural ao longo de uma partida, sons, equilíbrio de combate.

Etapa 5 (6 testes novos, 34 no total): Bolo Impostor ignora jogador a 6 blocos, se revela a 2 blocos e morde;
se revela ao apanhar; Máquina de Sorvete faz 2 sorvetes de morango com um balde (sobra leite e o balde vazio);
o Caminhão tem a máquina; Botas Pesadas fora do registro, Caminhão só na Floresta Doce, Creeper de Bubbaloo na
caverna, tubarão e rinoceronte maiores. As notas da 0.5.0-alpha listam o que foi testado à mão.

Etapa 6 (16 testes novos, 50 no total; inclui sabores como variantes e o tamanho da Fera e do Dragão): as 11 criaturas novas rodando IA (vida de zumbi, Fera e Gárgula); receitas do
Sorvete Supremo e do bloco; Amuleto acende a moldura (e o portal some ao quebrar a moldura), não acende moldura incompleta
nem de obsidiana; moldura de saída construída e acesa; Sorveteiro do nível 1 ao 5 vendendo o Amuleto; Casquinha no Sorvete
Vivo; buffs da Fera; Gárgula acertando o alvo; ovo chocando o Dragão em 60 ticks; rajada de bolas de fogo; Regeneração e
Casquinhas do Dragão; loot do Dragão; Ninho com ovo sobre obsidiana; registros, bioma e tags. O servidor de gametest não
carrega dimensões de datapack, então a viagem pelo portal foi testada num servidor de desenvolvimento (ver as notas da 0.6.0-alpha).

## Recursos provisórios

- **Texturas**: todas geradas por `tools/gen_textures.py`, originais (nenhum pixel do Minecraft ou do Crazy Alloy original foi copiado) e marcadas como provisórias.
- **Sons**: eventos próprios apontando para sons vanilla com tom alterado (`sounds.json`), até haver gravações próprias.
- **Modelos de criaturas**: modelos vanilla em código (sem GeckoLib nesta etapa).

## Arquitetura

```
com.crazyalloy.revival
├── CrazyAlloyRevival / CrazyAlloyRevivalClient   pontos de entrada comum e cliente
├── registry/   blocos, itens, entidades, block entities, menus, receitas, sons, abas, tags, condições
├── block/      blocos com comportamento (Fábrica) e block/entity/
├── item/       materiais (ferramenta/armadura) e comidas
├── entity/     criaturas; entity/ai/ objetivos de IA próprios
├── worldgen/   região TerraBlender, regras de superfície, árvores, placement e condição configuráveis
├── recipe/     tipo de receita da Fábrica
├── menu/       menus (lado servidor)
├── client/     modelos, renderizadores e telas (somente cliente)
├── config/     configuração comum
└── event/      atributos, surgimento, capacidades, multiplicadores de dificuldade
```

A pasta `network/` ainda não existe porque a etapa 1 não precisa de pacotes próprios (a Fábrica sincroniza pelo menu vanilla). Ela entra com o sistema de magia.

## Próximas etapas

1. Demais biomas de doce.
2. Conteúdo místico e magia.
3. Conteúdo de cogumelos.
4. Economia, recursos opcionais (incluindo AK47 configurável) e polimento.

## Créditos e licença

- Mod original: **Crazy Alloy**, por andreary1, licença MIT (<https://modrinth.com/mod/crazy-alloy>). O código-fonte original não é público; este projeto é uma reconstrução escrita do zero e não reutiliza código nem arquivos do original.
- Ideias marcadas como "proposta do revival" no código (por exemplo, o Cão soltar Tubos de Doce e a troca de Açúcar por Pirulito) são novas e não pertencem ao mod original.
- Este projeto: licença MIT (veja `LICENSE`).
