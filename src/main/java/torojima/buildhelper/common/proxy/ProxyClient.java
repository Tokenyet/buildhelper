// <copyright file="ProxyClient.java">
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
// <summary>class for client specific methods</summary>

package torojima.buildhelper.common.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import torojima.buildhelper.BuildHelperMod;
import torojima.buildhelper.common.itemMeshDefinitions.ExchangeWandMeshDefinition;

public class ProxyClient extends ProxyServer
{
	@Override
	public void registerModels()
	{
		BuildHelperMod.logger.info("registering models");
		this.registerModel(BuildHelperMod.sandWaterWand);
		this.registerModel(BuildHelperMod.fillWandDirt);
		this.registerModel(BuildHelperMod.fillWandCobble);
		this.registerModel(BuildHelperMod.fillWandStone);
		this.registerModel(BuildHelperMod.fillWandAir);
		this.registerModel(BuildHelperMod.gapFillWand);
		this.registerModel(BuildHelperMod.gapFillWaterWand);
		this.registerModel(BuildHelperMod.cubeDiggerWand);
		this.registerModel(BuildHelperMod.removeWaterWand);
		this.registerModel(BuildHelperMod.exchangeWand);
	}
	
	@Override
	public void registerModelVariants()
	{
		BuildHelperMod.logger.info("registering model variants");
		ModelBakery.registerItemVariants(BuildHelperMod.exchangeWand, 
				new ModelResourceLocation(BuildHelperMod.exchangeWand.getRegistryName(), "inventory"),
				new ModelResourceLocation(BuildHelperMod.exchangeWand.getRegistryName() + "_c1", "inventory"),
				new ModelResourceLocation(BuildHelperMod.exchangeWand.getRegistryName() + "_c2", "inventory"),
				new ModelResourceLocation(BuildHelperMod.exchangeWand.getRegistryName() + "_c3", "inventory")
				);
		ModelLoader.setCustomMeshDefinition(BuildHelperMod.exchangeWand, new ExchangeWandMeshDefinition());
	}
	
	private void registerModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(
				item, 
				0, 
	    		new ModelResourceLocation(item.getRegistryName(), "inventory"));		
	}
}
