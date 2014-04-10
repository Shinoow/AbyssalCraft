package com.shinoow.abyssalcraft.common.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.WorldEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.DLTSapling;
import com.shinoow.abyssalcraft.common.blocks.Dreadsapling;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsghoul;
import com.shinoow.abyssalcraft.common.entity.EntityDragonBoss;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;
import com.shinoow.abyssalcraft.common.entity.Entitydreadguard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class AbyssalCraftEventHooks {

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		if (event.entityLiving.isPotionActive(AbyssalCraft.Cplague)) {
			if (event.entityLiving.worldObj.rand.nextInt(20) == 0) {
				event.entityLiving.attackEntityFrom(DamageSource.magic, 2);
				if (event.entityLiving instanceof EntityDepthsghoul || event.entityLiving instanceof EntityDepthsZombie || event.entityLiving instanceof EntityDragonBoss || event.entityLiving instanceof EntityDragonMinion)
				{
					event.entityLiving.removePotionEffect(AbyssalCraft.Cplague.id);
				}
				if( event.entityLiving.isDead == true )
				{
					EntityDepthsZombie entityzombie = new EntityDepthsZombie(event.entityLiving.worldObj);
					entityzombie.copyLocationAndAnglesFrom(event.entityLiving);
					entityzombie.onSpawnWithEgg((IEntityLivingData)null);
					entityzombie.setIsPlayer(true);

					event.entityLiving.worldObj.removeEntity(event.entityLiving);
					event.entityLiving.worldObj.spawnEntityInWorld(entityzombie);
				}

			}
		}
		if (event.entityLiving.isPotionActive(AbyssalCraft.Dplague)){
			if (event.entityLiving.worldObj.rand.nextInt(20) == 0) {
				event.entityLiving.attackEntityFrom(DamageSource.magic, 1);
				if (event.entityLiving instanceof Entitydreadguard)
				{
					event.entityLiving.removePotionEffect(AbyssalCraft.Dplague.id);
				}
			}
		}
	}
	@SubscribeEvent
	public void craftingEvent(PlayerEvent.ItemCraftedEvent event)
	{
		for(int i=0; i < event.craftMatrix.getSizeInventory(); i++)
		{               
			if(event.craftMatrix.getStackInSlot(i) != null)
			{
				ItemStack j = event.craftMatrix.getStackInSlot(i);
				if(j.getItem() != null && j.getItem() == AbyssalCraft.cloth)
				{
					ItemStack k = new ItemStack(AbyssalCraft.cloth, 2, (j.getItemDamage() + 1));
					if(k.getItemDamage() >= k.getMaxDamage()){
						k.stackSize--;
					}
					event.craftMatrix.setInventorySlotContents(i, k);
				}
			}
		}
	}

	//Bonemeal events
	@SubscribeEvent
	public void bonemealUsed(BonemealEvent event)
	{
		if (event.block == AbyssalCraft.DLTSapling)
		{
			if (!event.world.isRemote)
			{
				((DLTSapling)AbyssalCraft.DLTSapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
			}
			event.setResult(Result.ALLOW);
		}

		if (event.block == AbyssalCraft.dreadsapling)
		{
			if (!event.world.isRemote)
			{
				((Dreadsapling)AbyssalCraft.dreadsapling).growTree(event.world, event.x, event.y, event.z, event.world.rand);
			}
			event.setResult(Result.ALLOW);
		}

	}
	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event)
	{
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Darkstone_cobble))
		{
			event.entityPlayer.addStat(AbyssalCraft.mineDS, 1);
		}

		if(event.item.getEntityItem().getItem() == AbyssalCraft.abychunk)
		{
			event.entityPlayer.addStat(AbyssalCraft.mineAby, 1);
		}

		if(event.item.getEntityItem().getItem() == AbyssalCraft.Coralium)
		{
			event.entityPlayer.addStat(AbyssalCraft.mineCorgem, 1);
		}

		if(event.item.getEntityItem().getItem() == AbyssalCraft.Cchunk)
		{
			event.entityPlayer.addStat(AbyssalCraft.mineCor, 1);
		}

		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.DGhead))
		{
			event.entityPlayer.addStat(AbyssalCraft.ghoulhead, 1);
		}

		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Phead))
		{
			event.entityPlayer.addStat(AbyssalCraft.petehead, 1);
		}

		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Whead))
		{
			event.entityPlayer.addStat(AbyssalCraft.wilsonhead, 1);
		}

		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.Ohead))
		{
			event.entityPlayer.addStat(AbyssalCraft.orangehead, 1);
		}

		if(event.item.getEntityItem().getItem() == AbyssalCraft.devsword)
		{
			event.entityPlayer.addStat(AbyssalCraft.secret1, 1);
		}
		if(event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacer)
		{
			event.entityPlayer.addStat(AbyssalCraft.GK1, 1);
		}
		if(event.item.getEntityItem().getItem() == AbyssalCraft.portalPlacerDL)
		{
			event.entityPlayer.addStat(AbyssalCraft.GK2, 1);
		}
		if(event.item.getEntityItem().getItem() == Item.getItemFromBlock(AbyssalCraft.PSDL))
		{
			event.entityPlayer.addStat(AbyssalCraft.findPSDL, 1);
		}
	}

	/**@SubscribeEvent
	public void renderDepthsHelmetOverlay(RenderGameOverlayEvent event)
	{
		final ResourceLocation coraliumBlur = new ResourceLocation("abyssalcraft:textures/misc/coraliumblur.png");

		ItemStack helmet = Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3);
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && helmet != null && helmet.getItem() == AbyssalCraft.Depthshelmet)
		{
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

			Tessellator t = Tessellator.instance;

			ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			int width = scale.getScaledWidth();
			int height = scale.getScaledHeight();

			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			Minecraft.getMinecraft().renderEngine.bindTexture(coraliumBlur);

			t.startDrawingQuads();
			t.addVertexWithUV(0.0D, (double)height, 90.0D, 0.0D, 1.0D);
			t.addVertexWithUV((double)width, (double)height, 90.0D, 1.0D, 1.0D);
			t.addVertexWithUV((double)width, 0.0D, 90.0D, 1.0D, 0.0D);
			t.addVertexWithUV(0.0D, 0.0D, 90.0D, 0.0D, 0.0D);
			t.draw();

			GL11.glPopAttrib();
		}
	}*/

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		new UpdateCheck().start();
	}

	private class UpdateCheck extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				Thread.sleep(10000L);

				if(isUpdateAvailable())
				{
					FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("["+EnumChatFormatting.BLUE
							+"AbyssalCraft"+EnumChatFormatting.RESET+"] An Update is available for this mod. Check http://adf.ly/FQarm for more info. (Your Version: "
							+EnumChatFormatting.AQUA+AbyssalCraft.version+EnumChatFormatting.RESET+")"));

				}
			}
			catch(Exception e)
			{
				System.err.println("UpdateChecker encountered an Exception, see following stacktrace:");
				e.printStackTrace();
			}
		}
		public boolean isUpdateAvailable() throws IOException, MalformedURLException {
			BufferedReader versionFile = new BufferedReader(new InputStreamReader(new URL("https://dl.dropboxusercontent.com/s/ff14wwf1hqav59z/version.txt?token_hash=AAHGqet5RWJdHIPJVNrE4omCxAtx_PJbN4R_1YYSFAs-Og&dl=1").openStream()));
			String curVersion = versionFile.readLine();

			versionFile.close();

			if (!curVersion.equals(AbyssalCraft.version)) {
				return true;
			}

			return false;
		}
	}
}

