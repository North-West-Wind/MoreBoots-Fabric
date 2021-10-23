package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.init.item.TooltipItem;
import ml.northwestwind.moreboots.init.item.boots.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ItemInit {
    public static final Item SPIDER_BOOTS = new SpiderBootsItem();
    public static final Item QUARTZ_BOOTS = new QuartzBootsItem();
    public static final Item SOCKS_BOOTS = new SocksBootsItem();
    public static final Item RAINBOW_SOCKS_BOOTS = new RainbowSocksBootItem();
    public static final Item MINER_BOOTS = new MinerBootsItem();
    public static final Item LAPIS_BOOTS = new LapisBootsItem();
    public static final Item GLOWSTONE_BOOTS = new GlowstoneBootsItem();
    public static final Item WATER_BOOTS = new WaterBootsItem();
    public static final Item LAVA_BOOTS = new LavaBootsItem();
    public static final Item OBSIDIAN_BOOTS = new ObsidianBootsItem();
    public static final Item ICE_BOOTS = new IceBootsItem();
    public static final Item VANISHING_BOOTS = new VanishingBootsItem();
    public static final Item MILK_BOOTS = new MilkBootsItem();
    public static final Item REDSTONE_BOOTS = new RedstoneBootsItem();
    public static final Item PLUMBER_BOOTS = new PlumberBootsItem();
    public static final Item METAL_BOOTS = new MetalBootsItem();
    public static final Item UPWARP_BOOTS = new UpwarpBootsItem();
    public static final Item DOWNWARP_BOOTS = new DownwarpBootsItem();
    public static final Item ENDER_BOOTS = new EnderBootsItem();
    public static final Item BONE_BOOTS = new BoneBootsItem();
    public static final Item MUSHROOM_BOOTS = new MushroomBootsItem();
    public static final Item SLIME_BOOTS = new SlimeBootsItem();
    public static final Item BLAZE_BOOTS = new BlazeBootsItem();
    public static final Item CACTUS_BOOTS = new CactusBootsItem();
    public static final Item EXPLOSIVE_BOOTS = new ExplosiveBootsItem();
    public static final Item WINDY_BOOTS = new WindyBootsItem();
    public static final Item SKATER = new SkatingBootsItem();
    public static final Item PRISMARINE_BOOTS = new PrismarineBootsItem();
    public static final Item BAT_BOOTS = new BatBootsItem();
    public static final Item KA_BOOTS = new KABootsItem();
    public static final Item GLASS_BOOTS = new GlassBootsItem();
    public static final Item GLASS_BOOTS_EMPTY = new EmptyGlassBootsItem();
    public static final Item FLOATIE_BOOTS = new FloatieBootsItem();
    public static final Item STRIDER_BOOTS = new StriderBootsItem();
    public static final Item SANDALS = new SandBootsItem();
    public static final Item MUSIC_BOOTS = new MusicBootsItem();
    public static final Item HOPPER_BOOTS = new HopperBootsItem();
    public static final Item LOKI_BOOTS = new LokiBootsItem();
    public static final Item STORAGE_BOOTS = new StorageBootsItem();
    public static final Item GLIDER = new GlidingBootsItem();
    public static final Item SPONGE_BOOTS = new SpongeBootsItem(ModArmorMaterial.SPONGE, "sponge_boots", FluidTags.WATER, false);
    public static final Item LAVA_SPONGE_BOOTS = new SpongeBootsItem(ModArmorMaterial.LAVA_SPONGE, "lava_sponge_boots", FluidTags.LAVA, true);
    public static final Item LIGHTNING_BOOTS = new LightningBootsItem();
    public static final Item MAGMA_BOOTS = new MagmaBootsItem();
    public static final Item ENDER_DRAGON_BOOTS = new EnderDragonBootsItem();
    public static final Item WITHER_BOOTS = new WitherBootsItem();
    public static final Item MACHINE_BOW_BOOTS = new MachineBowBoots();
    public static final Item SLIPPERY_BOOTS = new SlipperyBoots();
    public static final Item FLYING_BOOTS = new FlyingBootsItem();

    public static final Item QUARTZ_INGOT = new TooltipItem("quartz_ingot");
    public static final Item METAL_MIX = new TooltipItem("metal_mix");
    public static final Item BAT_HIDE = new TooltipItem("bat_hide");
    public static final Item STRIDER_FOOT = new TooltipItem("strider_foot");
    public static final Item FLOATING_CORE = new TooltipItem("floating_core");

    public static void registerItems() {
        registerAll(
                SPIDER_BOOTS,
                QUARTZ_BOOTS,
                SOCKS_BOOTS,
                RAINBOW_SOCKS_BOOTS,
                MINER_BOOTS,
                LAPIS_BOOTS,
                GLOWSTONE_BOOTS,
                WATER_BOOTS,
                LAVA_BOOTS,
                OBSIDIAN_BOOTS,
                ICE_BOOTS,
                VANISHING_BOOTS,
                MILK_BOOTS,
                REDSTONE_BOOTS,
                PLUMBER_BOOTS,
                METAL_BOOTS,
                UPWARP_BOOTS,
                DOWNWARP_BOOTS,
                ENDER_BOOTS,
                BONE_BOOTS,
                MUSHROOM_BOOTS,
                SLIME_BOOTS,
                BLAZE_BOOTS,
                CACTUS_BOOTS,
                EXPLOSIVE_BOOTS,
                WINDY_BOOTS,
                SKATER,
                PRISMARINE_BOOTS,
                BAT_BOOTS,
                KA_BOOTS,
                GLASS_BOOTS,
                GLASS_BOOTS_EMPTY,
                FLOATIE_BOOTS,
                STRIDER_BOOTS,
                SANDALS,
                MUSIC_BOOTS,
                HOPPER_BOOTS,
                LOKI_BOOTS,
                STORAGE_BOOTS,
                GLIDER,
                SPONGE_BOOTS,
                LAVA_SPONGE_BOOTS,
                LIGHTNING_BOOTS,
                MAGMA_BOOTS,
                ENDER_DRAGON_BOOTS,
                WITHER_BOOTS,
                MACHINE_BOW_BOOTS,
                SLIPPERY_BOOTS,
                FLYING_BOOTS
        );

        registerAll(
                QUARTZ_INGOT,
                METAL_MIX,
                BAT_HIDE,
                STRIDER_FOOT,
                FLOATING_CORE
        );
    }

    public static void registerAll(Item... items) {
        for (Item item : items) {
            if (item instanceof BootsItem) Registry.register(Registry.ITEM, ((BootsItem) item).getRegistryName(), item);
            else if (item instanceof TooltipItem) Registry.register(Registry.ITEM, ((TooltipItem) item).getRegistryName(), item);
        }
    }

    public enum ModArmorMaterial implements ArmorMaterial {
        SPIDER("spider", 12, 1, 10, SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 0.0F, 10000, () -> {
            return Ingredient.ofItems(Items.COBWEB);
        }),
        QUARTZ("quartz", 150, 6, 120, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 6.0f, 0.0F, 50000, () -> {
            return Ingredient.ofItems(Items.QUARTZ_BLOCK);
        }),
        SOCKS("socks", 20, 1, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5f, 0.0F, 20000, () -> {
            return Ingredient.ofItems(Items.WHITE_WOOL);
        }),
        RAINBOW_SOCKS("rainbow_socks", 200, 10, 42, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 10.0f, 0.0F, 150000, () -> {
            return Ingredient.ofItems(BlockInit.RAINBOW_WOOL);
        }),
        MINER("miner", 16, 2, 15, SoundEvents.BLOCK_BEACON_ACTIVATE, 1.5F, 0.0F, 100000, () -> {
            return Ingredient.ofItems(Blocks.IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE);
        }),
        LAPIS("lapis", 64, 2, 690, SoundEvents.ENTITY_PLAYER_LEVELUP, 2.0F, 0.0F, 60000, () -> {
            return Ingredient.ofItems(Items.LAPIS_LAZULI);
        }),
        GLOWSTONE("glowstone", 24, 2, 10, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0F, 0.0F, 30000, () -> {
            return Ingredient.ofItems(Blocks.GLOWSTONE);
        }),
        WATER("water", 24, 1, 12, SoundEvents.AMBIENT_UNDERWATER_ENTER, 1.0f, 0.0F, 20000, () -> Ingredient.EMPTY),
        LAVA("lava", 24, 1, 12, SoundEvents.BLOCK_LAVA_POP, 1.0f, 0.0F, 40000, () -> Ingredient.EMPTY),
        OBSIDIAN("obsidian", 200, 4, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, 90000, () -> {
            return Ingredient.ofItems(Blocks.OBSIDIAN);
        }),
        ICE("ice", 5, 1, 5, SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 0.0f, 15000, () -> {
            return Ingredient.ofItems(Blocks.PACKED_ICE);
        }),
        VANISHING("vanishing", 32, 2, 8, SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR, 0.0f, 0.0f, 100000, () -> Ingredient.EMPTY),
        MILK("milk", 24, 1, 10, SoundEvents.ENTITY_COW_MILK, 0.0f, 0.0f, 20000, () -> Ingredient.EMPTY),
        REDSTONE("redstone", 10, 2, 12, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 0.0f, 80000, () -> {
            return Ingredient.ofItems(Items.REDSTONE_BLOCK);
        }),
        PLUMBER("plumber", 12, 1, 4, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> {
            return Ingredient.ofItems(Items.LEATHER);
        }),
        METAL("metal", 6, 5, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0f, 1.0f, 120000, () -> {
            return Ingredient.ofItems(ItemInit.METAL_MIX);
        }),
        UPWARP("upwarp", 10, 2, 8, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, 75000, () -> {
            return Ingredient.ofItems(Items.ENDER_PEARL);
        }),
        DOWNWARP("downwarp", 10, 2, 8, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, 75000, () -> {
            return Ingredient.ofItems(Items.ENDER_PEARL);
        }),
        ENDER("ender", 20, 1, 4, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, 70000, () -> {
            return Ingredient.ofItems(Items.ENDER_PEARL);
        }),
        BONE("bone", 12, 1, 8, SoundEvents.ITEM_CROP_PLANT, 0.0f, 0.0f, 25000, () -> Ingredient.ofItems(Items.BONE)),
        MUSHROOM("mushroom", 12, 1, 8, SoundEvents.ITEM_CROP_PLANT, 0.0f, 0.0f, 25000, () -> Ingredient.ofItems(Items.BROWN_MUSHROOM_BLOCK, Items.RED_MUSHROOM_BLOCK)),
        SLIME("slime", 20, 2, 16, SoundEvents.BLOCK_SLIME_BLOCK_HIT, 0.0f, 0.0f, 50000, () -> Ingredient.ofItems(Items.SLIME_BALL)),
        BLAZE("blaze", 20, 5, 20, SoundEvents.ENTITY_BLAZE_AMBIENT, 1.0f, 0.0f, 100000, () -> Ingredient.ofItems(Items.BLAZE_ROD)),
        CACTUS("cactus", 20, 1, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 30000, () -> Ingredient.ofItems(Items.CACTUS)),
        EXPLOSIVE("explosive", 0.1875f, 1, 16, SoundEvents.ENTITY_TNT_PRIMED, 0.0f, 0.0f, 80000, () -> Ingredient.EMPTY),
        WINDY("windy", 20, 1, 12, SoundEvents.BLOCK_GLASS_BREAK, 0.0f, 0.0f, 125000, () -> Ingredient.ofItems(Blocks.BLUE_ICE)),
        SKATER("skating", 32, 1, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 60000, () -> Ingredient.ofItems(Items.SHEARS)),
        PRISMARINE("prismarine", 28, 2, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 75000, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD)),
        BAT("bat", 24, 1, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> Ingredient.ofItems(ItemInit.BAT_HIDE)),
        KA("ka", 16, 2, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 120000, () -> Ingredient.EMPTY),
        GLASS_EMPTY("glass", 10, 1, 20, SoundEvents.ITEM_BOTTLE_FILL, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.GLASS)),
        GLASS("glass", 10, 1, 20, SoundEvents.BLOCK_GLASS_BREAK, 0.0f, 0.0f, () -> Ingredient.ofItems(Items.GLASS)),
        FLOATIE("floatie", 40, 4, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.ofItems(Items.OAK_PLANKS)),
        STRIDER("strider", 40, 4, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.ofItems(ItemInit.STRIDER_FOOT)),
        SAND("sand", 18, 1, 8, SoundEvents.BLOCK_SAND_PLACE, 0.0f, 0.0f, 40000, () -> Ingredient.ofItems(Items.SAND)),
        MUSIC("music", 20, 1, 10, SoundEvents.BLOCK_NOTE_BLOCK_BANJO, 0.0f, 0.0f, 60000, () -> Ingredient.ofItems(Items.NOTE_BLOCK)),
        ENERGY("energy", 1, 4, 4, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f, 0.0f, 75000, () -> Ingredient.EMPTY),
        HOPPER("hopper", 8, 2, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, 90000, () -> Ingredient.ofItems(Items.HOPPER)),
        LOKI("loki", 20, 1, 12, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f, 0.0f, 150000, () -> Ingredient.EMPTY),
        STORAGE("storage", 10, 1, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 45000, () -> Ingredient.ofItems(Items.CHEST)),
        GLIDER("gliding", 20, 1, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 80000, () -> Ingredient.ofItems(Items.PHANTOM_MEMBRANE)),
        SPONGE("sponge", 10, 1, 6, SoundEvents.BLOCK_GRASS_BREAK, 0.0f, 0.0f, 60000, () -> Ingredient.ofItems(Items.SPONGE, Items.WET_SPONGE)),
        LAVA_SPONGE("lava_sponge", 10, 1, 6, SoundEvents.BLOCK_GRASS_BREAK, 0.0f, 0.0f, 65000, () -> Ingredient.ofItems(Items.SPONGE)),
        LIGHTNING("lightning", 12, 4, 8, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 0f, 0f, 120000, () -> Ingredient.EMPTY),
        MAGMA("magma", 16, 2, 8, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 0f, 0f, 100000, () -> Ingredient.ofItems(Items.MAGMA_CREAM)),
        DRAGON("ender_dragon", 40, 4, 20, SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT, 1.0f, 1.0f, 240000, () -> Ingredient.EMPTY),
        WITHER("wither", 40, 5, 24, SoundEvents.ENTITY_WITHER_AMBIENT, 1.0f, 1.0f, 240000, () -> Ingredient.EMPTY),
        MACHINE_BOW("machine_bow", 20, 1, 10, SoundEvents.ENTITY_ARROW_SHOOT, 0f, 0f, 90000, () -> Ingredient.EMPTY),
        SLIPPERY("slippery", 20, 1, 8, SoundEvents.BLOCK_GLASS_BREAK, 0f, 0f, 20000, () -> Ingredient.ofItems(Items.BLUE_ICE)),
        FLYING("flying", 5, 2, 20, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, 0f, 0f, 180000, () -> Ingredient.EMPTY);
        private static final int MAX_DMG = 16;
        private final String name;
        private final float maxDmgFac;
        private final int dmgRedAmt;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final float knockbackResistance;
        private final Lazy<Ingredient> repairMaterial;
        private final int energy;
        ModArmorMaterial(String name, float maxDmg, int dmgRed,
                         int enchant, SoundEvent sound, float tough, float kbRes, int energy,
                         Supplier<Ingredient> repairMat) {
            this.name = name;
            this.maxDmgFac = maxDmg;
            this.dmgRedAmt = dmgRed;
            this.enchantability = enchant;
            this.soundEvent = sound;
            this.toughness = tough;
            this.repairMaterial = new Lazy<>(repairMat);
            this.knockbackResistance = kbRes;
            this.energy = energy;
        }
        ModArmorMaterial(String name, float maxDmg, int dmgRed,
                         int enchant, SoundEvent sound, float tough, float kbRes,
                         Supplier<Ingredient> repairMat) {
            this(name, maxDmg, dmgRed, enchant, sound, tough, kbRes, -1, repairMat);
        }
        @Override
        public int getDurability(EquipmentSlot slotIn) {
            return (int) (MAX_DMG * this.maxDmgFac);
        }
        @Override
        public int getProtectionAmount(EquipmentSlot slotIn) {
            return this.dmgRedAmt;
        }
        @Override
        public int getEnchantability() {
            return this.enchantability;
        }
        @Override
        public SoundEvent getEquipSound() {
            return this.soundEvent;
        }
        @Override
        public Ingredient getRepairIngredient() {
            return this.repairMaterial.get();
        }

        @Override
        public String getName() {
            return this.name;
        }
        @Override
        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
        public int getEnergy() {
            return energy;
        }
    }

}
