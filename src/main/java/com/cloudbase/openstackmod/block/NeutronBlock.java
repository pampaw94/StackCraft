package com.cloudbase.openstackmod.block;

import java.util.Random;

import com.cloudbase.openstackmod.OpenStackMod;
import com.cloudbase.openstackmod.gui.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NeutronBlock extends BlockContainer{
	public static final String NAME = "NeutronBlock";
	public static NeutronBlock instance = null;
	
	public static void init(){
		GameRegistry.registerBlock(instance, instance.NAME);
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(instance), 0, new ModelResourceLocation(OpenStackMod.MODID+":"+instance.NAME,"inventory"));
		
		GameRegistry.addRecipe(new ItemStack(instance), new Object[]{
		    	"EEE",
		    	"EOE",
		    	"EEE",
		    	'O', OpenStackness.instance,
		    	'E', Items.ender_pearl
		    	});
		
		instance.setUnlocalizedName(OpenStackMod.MODID + "_" + instance.NAME);
		instance.setCreativeTab(CreativeTabs.tabBlock);
		instance.setLightLevel(8);
		instance.setHardness(3);
	}

	public NeutronBlock() {
		super(Material.ground);
	}
	
	public Item getItemDropped(int metadata, Random random, int fortune){
		return Item.getItemFromBlock(NeutronBlock.instance);
	}
	
	@Override
	public boolean isFullCube(){
		return true;
	}
	
	@Override
	public boolean isFullBlock(){
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(){
		return true;
	}
	
	@Override
	public int getRenderType(){
        return 3;
    }

	public boolean renderAsNormalBlock(){
        return true;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new NeutronBlockEntity();
	}
	
	public void breakBlock(World world, BlockPos pos, IBlockState blockstate) {
	    NeutronBlockEntity te = (NeutronBlockEntity) world.getTileEntity(pos);
	    InventoryHelper.dropInventoryItems(world, pos, te);
	    super.breakBlock(world, pos, blockstate);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote){
			player.openGui(OpenStackMod.instance, GuiHandler.MOD_NEUTRON_GUI, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
