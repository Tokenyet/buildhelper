// <copyright file="ItemExchangeWand.java">
// Copyright (c) 2016 All Right Reserved, http://buildhelper.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2016-07-21</date>
// <summary>Item class for the exchange wand item</summary>

package torojima.buildhelper.common.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;

public class ItemExchangeWand extends ItemFillWand
{
	public static final int FILL = 3;
	
	public static final String NAME = "exchangewand";

	protected Map<String, IBlockState> fillBlock;

	public ItemExchangeWand()
	{
		super(false);
		this.setHasSubtypes(true);
		this.register();
		this.fillBlock = new HashMap<String, IBlockState>();
	}
	
	public ItemExchangeWand(boolean register)
	{
		super(register);
		if(register)
		{
			this.register();
		}
		this.fillBlock = new HashMap<String, IBlockState>();			
	}
	
	private void register()
	{
		this.setRegistryName(ItemExchangeWand.NAME);
		this.setUnlocalizedName(ItemExchangeWand.NAME);
		GameRegistry.register(this);
	}

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		String username = playerIn.getName();
		
		stack.setItemDamage(this.status);
		EnumActionResult returnValue = EnumActionResult.FAIL;
		
		if (!worldIn.isRemote)
		{
			if(this.usedBlock.containsKey(username))
			{
				if(this.fillBlock.containsKey(username))
				{
					if(this.isStartPointPresent(username))
					{
						BlockPos startPos = this.popStartPos(username);
						BlockPos endPos = pos;
						if(this.pointsInDistanceLimit(startPos, endPos))
						{
							BlockPos posA = this.getPosAllBig(startPos, endPos);
							BlockPos posB = this.getPosAllSmall(startPos, endPos);

							IBlockState usedBlock = this.usedBlock.get(username);
							this.usedBlock.remove(username);
							
							IBlockState fillBlock = this.fillBlock.get(username);
							this.fillBlock.remove(username);

							for(int x = posA.getX(); x <= posB.getX(); x++)
							{
								for(int y = posA.getY(); y <= posB.getY(); y++)
								{
									for(int z = posA.getZ(); z <= posB.getZ(); z++)
									{
										BlockPos changePos = new BlockPos(x,y,z);
										
										if(worldIn.getBlockState(changePos).getBlock() == fillBlock.getBlock()
												&& !this.isBedRock(worldIn, changePos))
										{
											worldIn.setBlockState(changePos, usedBlock, 3);
										}
									}
								}
							}
							this.status = NONE;
							stack.setItemDamage(this.status);
							returnValue = EnumActionResult.SUCCESS;
						}
						else
						{
							this.resetWand(username);
						}
					}
					else
					{
						this.putStartPos(pos, username);
						this.status = CHARGED;
						stack.setItemDamage(this.status);
						returnValue = EnumActionResult.SUCCESS;
					}
				}
				else
				{
					this.fillBlock.put(username, worldIn.getBlockState(pos));
					this.status = FILL;
					stack.setItemDamage(this.status);
					returnValue = EnumActionResult.SUCCESS;
				}
			}
			else
			{
				this.usedBlock.put(username, worldIn.getBlockState(pos));
				this.status = NAMED;
				stack.setItemDamage(this.status);
				returnValue = EnumActionResult.SUCCESS;
			}
		}
		stack.setItemDamage(this.status);
		return returnValue;
	}
}
