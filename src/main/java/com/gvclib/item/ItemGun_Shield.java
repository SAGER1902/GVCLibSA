package gvclib.item;

import gvclib.mod_GVCLib;
import gvclib.item.gunbase.IGun_Shield;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


	public class ItemGun_Shield extends ItemGunBase implements IGun_Shield{
		public static String ads;
		
		public ItemGun_Shield() {
			super();
			
			
			this.maxStackSize = 1;
	        
	        this.addPropertyOverride(new ResourceLocation("hold"), new IItemPropertyGetter()
	        {
	            @SideOnly(Side.CLIENT)
	            public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
	            {
	            	if (entityIn instanceof EntityPlayer) {
						if (entityIn.isSneaking() && entityIn.getHeldItemMainhand() == stack) {
							if (!(getMaxDamage() - stack.getItemDamage() > 0)) {
								return 1F;
								/*
								 * }else if(stack == entityIn.getActiveItemStack()){
								 * return 3F;
								 */
							} else {
								return 2F;
							}
						} else if ((getMaxDamage() - stack.getItemDamage() > 0)) {
							return 0F;
						} else {
							return 1F;
						}
					} else {
						if ((getMaxDamage() - stack.getItemDamage() > 0)) {
							return 0F;
						} else {
							return 1F;
						}
					}
				}
	        });
		}
		
		public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
	    {
			super.onUpdate(itemstack, world, entity, i, flag);
			checkTags(itemstack);
			EntityPlayer entityplayer = (EntityPlayer)entity;

			if(!itemstack.hasTagCompound())return;
			NBTTagCompound nbt = itemstack.getTagCompound();
			

			boolean gethand = false;
			if(itemstack == entityplayer.getHeldItemOffhand()) {
				gethand = true;
			}else if(flag){
				gethand = true;
			}
			if (gethand) {
				entityplayer.motionX = entityplayer.motionX * this.motion;
				//entityplayer.motionY = entityplayer.motionY * 0.1;
				entityplayer.motionZ = entityplayer.motionZ * this.motion;
			}
			
			if (entity != null && !world.isRemote  && entityplayer != null && !itemstack.isEmpty()) 
			{
				if(itemstack == entityplayer.getHeldItemOffhand()) {
	    			/*boolean left = nbt.getBoolean("LeftClick");
	    			if (mod_GVCLib.proxy.leftclick()) 
					{
	    				nbt.setBoolean("LeftClick", true);
	    				nbt.setInteger("LeftTime", 0);
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(101));
					}*/
	    			if (entityplayer.isSneaking())
					{
	    				nbt.setBoolean("CAN_SD", true);
	    				/*if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
	    					for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
		    	            {
		    					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(76, true));
		    	            }
	    				}*/
	    				
	    				//nbt.setBoolean("LeftClick", false);
						//GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1011));
					}
	    			else {
						//nbt.setBoolean("LeftClick", false);
						//GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1011));
						nbt.setBoolean("CAN_SD", false);
						/*if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
							for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
		    	            {
								GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(76, false));
		    	            }
						}*/
						
					}
	    		}else if(flag){
	    			if (entityplayer.getActiveItemStack() == itemstack || entityplayer.isSneaking()) {
	    				nbt.setBoolean("CAN_SD", true);
	    				/*if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
	    					for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
		    	            {
		    					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(75, true));
		    	            }
	    				}*/
	    				
	    			}else {
	    				nbt.setBoolean("CAN_SD", false);
	    				/*if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
	    					for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
		    	            {
		    					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(75, false));
		    	            }
	    				}*/
	    				
	    			}
	    		}
			}
			
		//	this.DO_SD(itemstack, world, entityplayer, i, flag);
			/*{
				int left_time = nbt.getInteger("LeftTime");
				boolean left = nbt.getBoolean("LeftClick");
				if(left) {
					if(left_time < 5) {
						nbt.setInteger("LeftTime", ++left_time);
					}else {
						nbt.setInteger("LeftTime", 0);
						nbt.setBoolean("LeftClick", false);
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1011));
					}
					entityplayer.motionX = entityplayer.motionX * 0.5F;
					entityplayer.motionZ = entityplayer.motionZ * 0.5F;
				}
			}*/
			
			
	    }
		
		
		public void DO_SD(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			boolean can = nbt.getBoolean("CAN_SD");
			
		}
		
		
		
		
		public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase par3EntityPlayer, int par4){
        }
		
		public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	    {
			boolean flag = this.func_185060_a(playerIn) != null;

	        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
	        if (ret != null) return ret;

	        if (!playerIn.capabilities.isCreativeMode && !flag)
	        {
	            return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn);
	        }
	        else
	        {
	            playerIn.setActiveHand(hand);
	            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	        }
	    }

}
