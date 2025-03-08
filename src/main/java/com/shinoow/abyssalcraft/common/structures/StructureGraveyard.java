package com.shinoow.abyssalcraft.common.structures;

import java.util.*;
import java.util.Map.Entry;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockTombstone;
import com.shinoow.abyssalcraft.common.world.gen.*;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.*;
import net.minecraft.world.gen.structure.template.Template.BlockInfo;

public class StructureGraveyard extends WorldGenerator {

	private Map<Integer, Set<BlockPos>> positions = new HashMap<>();

	private boolean tooClose(int dim, BlockPos pos) {
		positions.putIfAbsent(dim, new HashSet<BlockPos>());
		return positions.get(dim).stream().anyMatch(b -> b.getDistance(pos.getX(), b.getY(), pos.getZ()) <= 150);
	} //TODO config option

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {

		if(tooClose(worldIn.provider.getDimension(), position))
			return false;

		PlacementSettings placeSettings = new PlacementSettings().setReplacedBlock(Blocks.STRUCTURE_VOID);

		IBlockState topBlock = getTopBlock(worldIn.provider.getDimension());
		IBlockState fillerBlock = getFillerBlock(worldIn.provider.getDimension());
		IBlockState slabBlock = getSlabBlock(worldIn.provider.getDimension());

		ITemplateProcessor processor = (world, pos1, blockInfoIn) -> {

			if(blockInfoIn.blockState == Blocks.GRASS.getDefaultState())
				return new BlockInfo(pos1, topBlock, null);
			if(blockInfoIn.blockState == Blocks.DIRT.getDefaultState())
				return new BlockInfo(pos1, fillerBlock, null);
			if(blockInfoIn.blockState == Blocks.SAND.getDefaultState())
				return new BlockInfo(pos1, slabBlock, null);
			return blockInfoIn;
		};

		position = position.down(1);

		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager templateManager = worldIn.getSaveHandler().getStructureTemplateManager();

		String size = "small";

		switch(rand.nextInt(3)) {
		case 0:
			size = "small";
			break;
		case 1:
			size = "medium";
			break;
		case 2:
			size = "large";
			break;
		}

		Template template = templateManager.getTemplate(server, new ResourceLocation("abyssalcraft", "graveyard/graveyard_"+size));

		template.addBlocksToWorld(worldIn, position, processor, placeSettings, 2);

		positions.get(worldIn.provider.getDimension()).add(position);

		Map<BlockPos, String> map = template.getDataBlocks(position, placeSettings);

		for(Entry<BlockPos, String> entry : map.entrySet())
			if("tombstone".equals(entry.getValue())) {
				worldIn.setBlockToAir(entry.getKey());
				worldIn.setBlockState(entry.getKey().down(), getTombstone(worldIn, rand, entry.getKey()));
			} else if("tree".equals(entry.getValue())) {
				worldIn.setBlockToAir(entry.getKey());
				if(rand.nextInt(3) == 0) {
					WorldGenTreeAC tree = new WorldGenDLT(false);
					if(worldIn.provider.getDimension() == ACLib.dreadlands_id && worldIn.getBiome(entry.getKey()) instanceof IDreadlandsBiome) {
						tree = new WorldGenDrT(false);
					}
					if(worldIn.provider.getDimension() == ACLib.abyssal_wasteland_id) {
						tree = new WorldGenDeadTree(false);
					}
					tree.setFixed();
					tree.generate(worldIn, rand, entry.getKey());
				} else worldIn.setBlockState(entry.getKey().down(), topBlock);
			} else if("treasure".equals(entry.getValue())) {
				if(rand.nextBoolean()) {
					worldIn.setBlockState(entry.getKey(), Blocks.CHEST.getDefaultState());

					TileEntity tile = worldIn.getTileEntity(entry.getKey());

					if(tile instanceof TileEntityChest) {
						if(rand.nextInt(10) == 0 && worldIn.provider.getDimension() == ACLib.abyssal_wasteland_id) {

							int num = rand.nextInt(((TileEntityChest) tile).getSizeInventory());

							((TileEntityChest) tile).setInventorySlotContents(num, getRandomShard(rand));

						} else {
							boolean anyLoot = false;
							// dumb random-loot logic
							int num = rand.nextInt(10);
							List<Tuple<Integer, Integer>> randList = new ArrayList<>();
							for(int i = 0; i < num; i++) {
								randList.add(new Tuple<>(rand.nextInt(((TileEntityChest) tile).getSizeInventory()), rand.nextInt(16)+1));
							}
							Collections.shuffle(randList, rand); // fairly pointless shuffle

							List<ItemStack> loot = getLoot(worldIn.provider.getDimension());

							if(rand.nextBoolean() && worldIn.provider.getDimension() == ACLib.abyssal_wasteland_id)
								loot.add(getRandomShard(rand));

							for(Tuple<Integer, Integer> t : randList) { // inventory slot, quantity
								ItemStack stack = loot.get(rand.nextInt(loot.size()));
								if(stack.isEmpty()) continue; // oh no!
								anyLoot = true;
								// fixed lower quantity for dimensional skins
								if(!isConfiguratorShard(stack))
									stack.setCount(!isSkin(stack) ? t.getSecond() : rand.nextInt(2) + 1);
								((TileEntityChest) tile).setInventorySlotContents(t.getFirst(), stack);
							}
							if(!anyLoot)
								worldIn.setBlockState(entry.getKey(), fillerBlock);
						}
					}

				} else worldIn.setBlockState(entry.getKey(), fillerBlock);
			}

		return true;
	}

	private ItemStack getRandomShard(Random rand) {

		switch(rand.nextInt(3)) {
		case 0:
			return new ItemStack(ACItems.configurator_shard_0);
		case 1:
			return new ItemStack(ACItems.configurator_shard_1);
		case 2:
			return new ItemStack(ACItems.configurator_shard_2);
		case 3:
			return new ItemStack(ACItems.configurator_shard_3);
		default:
			return new ItemStack(ACItems.configurator_shard_0);
		}
	}

	private boolean isSkin(ItemStack stack) {
		return stack.getItem() == ACItems.skin_of_the_abyssal_wasteland
				|| stack.getItem() == ACItems.skin_of_the_dreadlands
				|| stack.getItem() == ACItems.skin_of_omothol;
	}

	private static boolean isConfiguratorShard(ItemStack stack) {
		return stack.getItem() == ACItems.configurator_shard_0 || stack.getItem() == ACItems.configurator_shard_1
				|| stack.getItem() == ACItems.configurator_shard_2 || stack.getItem() == ACItems.configurator_shard_3;
	}

	private List<ItemStack> getLoot(int dim) {

		List<ItemStack> res = new ArrayList<>();
		// universal free stuff
		res.add(new ItemStack(Items.BONE));
		res.add(new ItemStack(Items.LEATHER));
		res.add(ItemStack.EMPTY); // also free nothing! :)

		if(dim == ACLib.abyssal_wasteland_id) {
			res.add(new ItemStack(ACItems.coralium_plagued_flesh));
			res.add(new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone));
			res.add(new ItemStack(ACItems.abyssal_shoggoth_flesh));
			res.add(new ItemStack(ACItems.skin_of_the_abyssal_wasteland));
		}
		else if(dim == ACLib.dreadlands_id) {
			res.add(new ItemStack(ACItems.dread_fragment));
			res.add(new ItemStack(ACItems.dreaded_shoggoth_flesh));
			res.add(new ItemStack(ACItems.skin_of_the_dreadlands));
		}
		else if(dim == ACLib.omothol_id) {
			res.add(new ItemStack(ACItems.omothol_flesh));
			res.add(new ItemStack(ACItems.omothol_shoggoth_flesh));
			res.add(new ItemStack(ACItems.skin_of_omothol));
		}
		else if(dim == ACLib.dark_realm_id) {
			res.add(new ItemStack(ACItems.shadow_shard));
			res.add(new ItemStack(ACItems.shadow_shoggoth_flesh));
			res.add(new ItemStack(ACItems.shadow_fragment));
		}
		else {
			res.add(new ItemStack(Items.ROTTEN_FLESH));
			res.add(new ItemStack(ACItems.overworld_shoggoth_flesh));
		}

		return res;
	}

	private IBlockState getTopBlock(int id) {
		if(id == ACLib.abyssal_wasteland_id)
			return ACBlocks.fused_abyssal_sand.getDefaultState();
		if(id == ACLib.dreadlands_id)
			return ACBlocks.dreadlands_grass.getDefaultState();
		if(id == ACLib.omothol_id)
			return ACBlocks.omothol_stone.getDefaultState();
		if(id == ACLib.dark_realm_id)
			return ACBlocks.darkstone.getDefaultState();
		return Blocks.GRASS.getDefaultState();
	}

	private IBlockState getFillerBlock(int id) {
		if(id == ACLib.abyssal_wasteland_id)
			return ACBlocks.abyssal_sand.getDefaultState();
		if(id == ACLib.dreadlands_id)
			return ACBlocks.dreadlands_dirt.getDefaultState();
		if(id == ACLib.omothol_id)
			return ACBlocks.omothol_stone.getDefaultState();
		if(id == ACLib.dark_realm_id)
			return ACBlocks.darkstone.getDefaultState();
		return Blocks.DIRT.getDefaultState();
	}

	@SuppressWarnings("deprecation")
	private IBlockState getSlabBlock(int id) {
		if(id == ACLib.abyssal_wasteland_id)
			return ACBlocks.abyssal_cobblestone_slab.getDefaultState();
		if(id == ACLib.dreadlands_id)
			return ACBlocks.dreadstone_cobblestone_slab.getDefaultState();
		if(id == ACLib.omothol_id)
			return ACBlocks.darkstone_cobblestone_slab.getDefaultState();
		if(id == ACLib.dark_realm_id)
			return ACBlocks.darkstone_cobblestone_slab.getDefaultState();
		return Blocks.STONE_SLAB.getStateFromMeta(3);
	}

	private IBlockState getTombstone(World world, Random rand, BlockPos pos) {

		IBlockState state = ACBlocks.tombstone_stone.getDefaultState();

		int id = world.provider.getDimension();

		if(id == 0) {
			if(rand.nextInt(10) == 0)
				state = ACBlocks.tombstone_darkstone.getDefaultState();
		}
		if(id == ACLib.abyssal_wasteland_id) {
			state = ACBlocks.tombstone_abyssal_stone.getDefaultState();
			if(rand.nextBoolean())
				state = ACBlocks.tombstone_coralium_stone.getDefaultState();
		}
		if(id == ACLib.dreadlands_id) {
			state = ACBlocks.tombstone_dreadstone.getDefaultState();
			if(rand.nextBoolean())
				state = ACBlocks.tombstone_elysian_stone.getDefaultState();
		}
		if(id == ACLib.omothol_id) {
			state = ACBlocks.tombstone_omothol_stone.getDefaultState();
			if(rand.nextBoolean())
				state = ACBlocks.tombstone_ethaxium.getDefaultState();
		}

		if(id == ACLib.dark_realm_id || world.getBiome(pos) instanceof IDarklandsBiome ||
				rand.nextInt(10) == 0) {
			state = ACBlocks.tombstone_darkstone.getDefaultState();
		}

		return state.withProperty(BlockTombstone.FACING, EnumFacing.NORTH);
	}
}
