# Villagers can extends there village by building new structures (that cost them resources).
Create a structure in a single chunck for each builing:
 <!-- - Townhall -->
 <!-- - House -->
 - Orchad (oak, spruce, etc)
 - Field (wheat, potatoes, carrot, etc)
 - Enclosed plot (cow, sheep, pig, etc)
 - Mine
 - Quary
 - Armory
 - Stable
 - Library
 - Fishing docs
 - Storage
<!-- Villages contains building linked to structures -->
Tool function to list all villages resources **RV**
Villagers can pick 1 item from a chest **RV LootChest**
Villagers can pick a list of items from any village chests **RV LootChest**
Villagers can build building **DoT**
Villagers can build building while removing material from there inventory
Villagers can craft any missing resources from village resources
Village have start resources and some villagers to be able not to starve and build fiew start building
Village have a long list of building to build or a function to know witch building should be next

# Villagers can defend themselves (as soldiers villagers that protect the town).
Villagers can wear armor **RV**
Villagers skin display weared armor **RV**
Villagers can pick up weapon and armor from chest **RV**
Fight Behavior: 
 - MeleeAttack **RV**
 - RangeWeaponAttack while staying close enoth but not to close from target **RV**
 - Random shield defend when opponent is close enoth to hit **RV**
 - horse fight

# Villagers produce ressources (wood, stone, ores, wool, leather, foods).
Villagers pick up usefull item **RV**
Villagers know where they sleep
Villagers know where they work
Villagers work at day (or at night for some warrior) and sleep at night
Villagers know how to work:
 - Farmer **W**
 - Animal farmer (Normal or Shepherd) **W**
 - Lumberjack **W**
 - Miner (create minning tunels outside of his job chunk) **W MinerAI**
 - Fisherman **W**
 - Beekeeper **W**
Villagers know how to deposit item **W DepositItemsInChestGoal**


# Villagers sell what they really produce and buy what they really need (emerald or economic plugin).
Merchant know how to sell content of his inventory as emeralds traid **W MerchantEntity**
Merchant can switch from emeralds to economic money
Village know how much it need to store from builing blocks, craft resources, tools, weapons and armors
Merchant know how to pick up stuff to sell from village chests without removing more than minimal stock of village resources **W**
Merchant change it's price depending of a default value & quantity that his village have to sell
Merchant travel to random place to find player.
Merchant travel to other villages
Merchant can trade with other merchant
Merchant try to reach player
Soldiers can protect with merchant (and counter attack if someting hit a villager (soldier or merchant))

# Villagers claim there village (with the Towny plugin)
Village can create an empty town with the same name than village name
Village know how to extend claims
Village extends claims before it build someting
Village can sell extra resources for economic money

# Other
Villages forceload claimed chuncks **W AbstractChunkLoaderEntity**
Villagers forceload there current chunck **W AbstractChunkLoaderEntity**
Villagers eat food from there inventory. **W** **RV**
Villagers get more food when they don't have any. **W** **RV**