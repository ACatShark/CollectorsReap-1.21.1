# Collector's Reap 1.21.1 — 数据包修复改动日志

版本：`1.0.0`　目标：Minecraft 1.21.1 / NeoForge 21.1.248
构建产物：`build/libs/collectorsreap-1.21.1-1.0.0.jar`

## 问题背景
本模组由 1.20.1 移植到 1.21.1 时，残留了大量 **1.20.1 旧版数据包格式**。
这些 JSON 在 1.20.5+ 的数据包加载器中无法正确反序列化，导致相关配方被整体跳过（静默失效），
典型表现为「海潮蛤砧板配方失效」。本次使用校验工具系统性比对了模组 229 个数据包与依赖模组
官方数据文件，定位并修复了全部同类问题。

## 一、Farmer's Delight 砧板概率产物（ChanceResult）修复
FD 砧板配方的概率产物字段需为 `{"item": {"id": ...}, "chance": x}` 结构，
旧格式使用了扁平的 `id` 字段，导致整条配方反序列化失败。

- `data/collectorsreap/recipe/cutting/clam.json`
  - 海潮蛤肉：`{"chance": 0.5, "item": {"id": "collectorsreap:clam_meat"}}`
  - 月华珠：`{"chance": 0.1, "item": {"id": "collectorsreap:lunar_pearl"}}`
- `data/collectorsreap/recipe/cutting/dragon_bush.json`
  - 粉色染料：`{"chance": 0.3, "item": {"id": "minecraft:pink_dye"}}`

## 二、成品 / 容器字段 `item` → `id`（1.20.5+ 格式）
自 1.20.5 起，物品成品需写作 `{"id": "xxx", "count": n}`，旧 `{"item": "xxx"}` 失效。

- `data/collectorsreap/recipe/atmospheric/golden_dragon_fruit.json`（result）
- `data/collectorsreap/recipe/brewinandchewin/cooking/cheesy_pasta.json`（result + container）
- `data/collectorsreap/recipe/brewinandchewin/cooking/creamy_onion_soup.json`（result + container）
- `data/collectorsreap/recipe/brewinandchewin/ham_and_cheese_sandwich.json`（result，`count: 2`）
- `data/collectorsreap/recipe/brewinandchewin/pizza.json`（result）
- `data/collectorsreap/recipe/brewinandchewin/quiche_from_bacon.json`（result）
- `data/collectorsreap/recipe/brewinandchewin/quiche_from_mushroom.json`（result）
- `data/collectorsreap/recipe/neapolitan/strawberry_banana_smoothie.json`（result）
- `data/collectorsreap/recipe/neapolitan/mixed/strawberry_banana_smoothie.json`（result）
- `data/collectorsreap/recipe/neapolitan/neapolitan_ice_cream_from_vanilla.json`（result）

## 三、Create 6 / NeoForge 1.21 兼容配方升级
Create 6 改用了新的字段命名与 NeoForge 流体成分（FluidIngredient）格式。

### 3.1 搅拌配方 `mixing/gummy/*.json`（28 个）
- `heatRequirement` → `heat_requirement`
- `results[].item` → `results[].id`

### 3.2 灌装配方 `filling/*.json`（3 个）
- 流体配料补充判别符 `"type": "neoforge:single"`，移除旧 `nbt` 字段
- `results[].item` → `results[].id`

### 3.3 倾倒配方 `emptying/*.json`（2 个）
- 物品结果 `item` → `id`
- 流体结果 `{"amount", "fluid"}` → `{"amount", "id"}`

涉及目录：`data/collectorsreap/recipe/integration/create/{mixing,filling,emptying}/`

## 五、Tooltip buff 重复显示修复
软糖（`GummyItem`）的 tooltip 上食物效果显示了两遍：
- `GummyItem.appendHoverText` 先调用父类 `CompatConsumable` → FD 的 `ConsumableItem`，
  在 `hasFoodEffectTooltip=true` 时已自动渲染 `FoodProperties` 中的效果；
- 随后对属于 `MOB_FEEDABLE_GUMMIES` 标签的软糖，又手动把同一批效果（`getEffects`）再列出一次，
  造成「buff 显示双重」。

修复：删除 `GummyItem` 中冗余的手动效果渲染块（`appendHoverText` 仅保留 `super` 调用）。
效果仍由 Farmer's Delight 统一渲染一次；喂动物时施加效果的逻辑（`getEffects` / `addEffects`，
供 `ForgeEvents` 调用）保持不变，功能不受影响。同时清理了因此变为未使用的导入。

## 四、构建脚本调整
- `build.gradle`：在 `repositories` 块顶部加入 `mavenLocal()`，以便在离线/镜像缺失环境下
  从本地 Maven 仓库（`~/.m2`）解析 `net.neoforged:minecraft-dependencies:1.21.1`。
- 构建需使用 **JDK 21**（Gradle 8.9 不兼容 JDK 25）。

## 复验结论
经校验工具复验，剩余差异仅为键名/格式噪音，已全部无 JSON 语法错误；
`pouring` 容器的 `container.item`、`fermenting` 的 `#c:milk`、`sniffer_digging` 的
`collectorsreap:enabled`、`botanypots` 掉落、`woodworks:sawmill` 字符串 id 等均为合法写法，无需改动。
