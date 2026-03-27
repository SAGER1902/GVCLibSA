package gvclib.item;

import gvclib.item.gunbase.IGun_Grenade;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


	public class ItemGun_Grenade extends ItemGunBase  implements IGun_Grenade{
		public static String ads;
		
		public ItemGun_Grenade() {
			super();
			//this.maxStackSize = 1;
			this.shrinkitem = true;
		}
		
		public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	    {
			super.onUpdate(itemstack, world, entity, i, flag);
			EntityPlayer entityplayer = (EntityPlayer)entity;
			boolean lflag = cycleBolt(itemstack);
			if (flag) {
				entityplayer.motionX = entityplayer.motionX * this.motion;
				entityplayer.motionZ = entityplayer.motionZ * this.motion;
			}
			this.gunbase_reload(itemstack, world, entity, i, flag);
			
	    }
		
		public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase par3EntityPlayer, int par4){
			if (par3EntityPlayer instanceof EntityPlayer)
	        {
				EntityPlayer entityplayer = (EntityPlayer)par3EntityPlayer;
				int s;
				int li = getMaxDamage() - par1ItemStack.getItemDamage();
				boolean lflag = cycleBolt(par1ItemStack);
				boolean var5 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
				Item item = par1ItemStack.getItem();
				
				if (par1ItemStack == entityplayer.getHeldItemMainhand())
		        {
					
		         if(par1ItemStack.getItemDamage() == this.getMaxDamage())
				 {
				 }
		        	else
				 {
						FireBullet(par1ItemStack,par2World,(EntityPlayer) par3EntityPlayer);
						par3EntityPlayer.resetActiveHand();
						entityplayer.addStat(StatList.getObjectUseStats(this));
				  }
				}
	        }
        	
			
        }
		
		public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	    {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
	        {
	            playerIn.setActiveHand(handIn);
	            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	        }
	    }

}
