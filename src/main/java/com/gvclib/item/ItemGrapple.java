package gvclib.item;

import java.util.List;

import javax.annotation.Nullable;

import gvclib.entity.EntityB_Grapple;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemGrapple extends Item
{
	public int ii;
	
    public ItemGrapple()
    {
        this.maxStackSize = 1;
    }

    
    public double pointX;
    public double pointY;
    public double pointZ;
    public boolean point = false;
    
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	  {
  	tooltip.add(TextFormatting.WHITE + "R-click" + " : " + "Wire injection");
  	tooltip.add(TextFormatting.WHITE + "Sneak" + " : " + "Wire cut");
	  }
    
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    	EntityLivingBase target = (EntityLivingBase) entity;
		if (target != null && target instanceof EntityPlayer) {
			//target.motionX *= 0.1F;
			//target.motionZ *= 0.1F;
			if(point && flag && !target.isRiding()) {
				EntityB_Grapple gra = null;
				{
					double x = entity.posX;
					double y = entity.posY;
					double z = entity.posZ;
					int han = 120;
					AxisAlignedBB aabb = (new AxisAlignedBB((double) (x - han), (double) (y - han),
							(double) (z - han),
							(double) (x + han), (double) (y + han), (double) (z + han)))
									.grow(1);
			        List llist2 = entity.world.getEntitiesWithinAABBExcludingEntity(entity, aabb);
			        if(llist2!=null){
			            for (int lj = 0; lj < llist2.size(); lj++) {
			            	Entity entity1 = (Entity)llist2.get(lj);
			                {
			            		if (entity1 instanceof EntityB_Grapple && entity1 != null)
			                    {
			            			EntityB_Grapple g = (EntityB_Grapple) entity1;
			            			if(g.isOwner(target)) {
			            				gra = g;
			            				break;
			            			}
			                    }
			                }
			            }
			        }
				}
				//if(gra != null && (gra.onGround || gra.hit)) 
				if(gra != null && gra.canmove) 
				{
					double d5 = (gra.posX) - target.posX;
					double d7 = (gra.posZ) - target.posZ;
					float yawset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					float yaw = yawset * (2 * (float) Math.PI / 360);
					double mox = 0;
					double moy = 0;
					double moz = 0;
					mox -= MathHelper.sin(yaw) * 0.3;
					moz += MathHelper.cos(yaw) * 0.3;
					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					double d6 = (gra.posY) - target.posY;
					if(d6 > 0) {
						/*if (!(ddx > 3 || ddz > 3))
						{
							moy = 0.1;
						}else {
							moy = 0.1;
						}*/
						moy = 0.1;
					}else {
						//moy = -0.1;
					}
					/*double d1 = pointY - target.posY;
					double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
					float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
					float pich = f11 * (2 * (float) Math.PI / 360);
					moy += MathHelper.cos(f11) * 0.3;*/
					
					target.motionX = mox;
					target.motionY = moy;
					target.motionZ = moz;
					
					//entity.motionY = y;
					target.move(MoverType.PLAYER, target.motionX, target.motionY, target.motionZ);
					
					
					/*if (!(ddx > 1 || ddz > 1))
					{
						point = false;
					}*/
				}
				
			}
			if(target.isSneaking() && flag) {
				point = false;
				this.clearhouk(target);
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(202));
			}
			//this.clearhouk(target);
		}
    }
    
    /*public boolean onEntitySwing(EntityLivingBase entity, ItemStack par1ItemStack) {
		World worldIn = entity.world;
		if (entity != null && entity instanceof EntityPlayer) {
			EntityPlayer playerIn = (EntityPlayer) entity;
			ItemStack itemstack = playerIn.getHeldItemMainhand();
			if (!itemstack.isEmpty()) {
				point = false;
			}
			this.clearhouk(playerIn);
			if (mod_GVCLib.proxy.keyc() 
					&& !entity.isRiding()) {
				GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(202));
			}
		}
		return false;
    }*/
    
    public void clearhouk(EntityLivingBase entity) {
    	entity.world.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, 
    			SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1.0F);
    	GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(202));
    	{
    		double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;
			int han = 120;
			AxisAlignedBB aabb = (new AxisAlignedBB((double) (x - han), (double) (y - han),
					(double) (z - han),
					(double) (x + han), (double) (y + han), (double) (z + han)))
							.grow(1);
	        List llist2 = entity.world.getEntitiesWithinAABBExcludingEntity(entity, aabb);
	        if(llist2!=null){
	            for (int lj = 0; lj < llist2.size(); lj++) {
	            	
	            	Entity entity1 = (Entity)llist2.get(lj);
	            	//if (entity1.canBeCollidedWith())
	                {
	            		if (entity1 instanceof EntityB_Grapple && entity1 != null)
	                    {
	            			//if (!entity1.world.isRemote) 
							{
								entity1.setDead();
								//System.out.println(String.format("derete"));
							}
	                    }
	                }
	            }
	        }
    	}
    }
    
    public void fire_grapple(ItemStack itemstack, World worldIn, EntityPlayer player) {
    	//this.clearhouk(player);
    	
    	worldIn.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, 
    			SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    	//Vec3d lock = player.getForward();
    	Vec3d lock = Vec3d.fromPitchYaw(player.rotationPitch, player.rotationYaw);
		/*for (int x = 0; x < 120; ++x) {
			if (worldIn.getBlockState(new BlockPos(player.posX + lock.x * x, player.posY + 1.5 + lock.y * x,
					player.posZ + lock.z * x)).getBlock() != Blocks.AIR) {
				pointX = (int) (player.posX + lock.x * x);
				pointY = (int) (player.posY + 1.5 + lock.y * x);
				pointZ = (int) (player.posZ + lock.z * x);
				point = true;
				player.getCooldownTracker().setCooldown(itemstack.getItem(), 40);
				
		            EntityB_Grapple grapple = new EntityB_Grapple(worldIn, player);
		            grapple.gra = 0.03F;
		            grapple.playerid = player.getEntityId();
		            grapple.setTamedBy(player);
		            grapple.setLocationAndAngles(pointX, pointY, pointZ, player.rotationYaw,player.rotationPitch);
		            grapple.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.4F, 0F);
		            if (!worldIn.isRemote)
			        {
		            worldIn.spawnEntity(grapple);
		        }
				break;
			}
		}*/
		{
			point = true;
			player.getCooldownTracker().setCooldown(itemstack.getItem(), 40);

			EntityB_Grapple grapple = new EntityB_Grapple(worldIn, player);
			//grapple.gra = 0.0299F;
			grapple.playerid = player.getEntityId();
			grapple.setTamedBy(player);
			//grapple.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw,player.rotationPitch);
			double xx11 = 0;
			double zz11 = 0;
			double yy11 = 0;
			float xz = 1.57F;
			
			double yy = 1.5D;
			double zzz = 1.0 * Math.cos(Math.toRadians(-player.rotationPitch));
			xx11 -= MathHelper.sin(player.rotationYawHead * 0.01745329252F) * zzz;
			zz11 += MathHelper.cos(player.rotationYawHead * 0.01745329252F) * zzz;
			xx11 -= MathHelper.sin(player.rotationYawHead * 0.01745329252F + xz) * 0.3;
			zz11 += MathHelper.cos(player.rotationYawHead * 0.01745329252F + xz) * 0.3;
			yy11 = MathHelper.sqrt(zzz* zzz) * Math.tan(Math.toRadians(-player.rotationPitch)) * 1D;
			grapple.setLocationAndAngles(player.posX + xx11, player.posY + yy + yy11, player.posZ + zz11, 
					player.rotationYaw,player.rotationPitch);
			grapple.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 4.0F, 0.5F);
			if (!worldIn.isRemote) {
				worldIn.spawnEntity(grapple);
			}
		}
    }
    
    public void onPlayerStoppedUsing(ItemStack itemstack, World worldIn, EntityLivingBase playerIn,
			int par4) {
    	
		
    	if (playerIn != null && playerIn instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) playerIn;
    		if(player.getCooldownTracker().getCooldown(itemstack.getItem(), 0) == 0 && !player.isRiding()) {
    			this.fire_grapple(itemstack, worldIn, player);
    		}
		}
	}
    
    /*public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!itemstack.isEmpty() && playerIn.getHeldItemMainhand() == itemstack) {
        	 ItemStack itemstack2 = playerIn.getHeldItemOffhand();
        	 if(!itemstack2.isEmpty() && itemstack2.getItem() instanceof ItemBlock) {
        		 ItemBlock ib = (ItemBlock) itemstack2.getItem();
        		 if (!playerIn.capabilities.isCreativeMode)
        	        {
        	            //itemstack.shrink(1);
        	        }

        	        if (!worldIn.isRemote)
        	        {
        	        	this.fire_grapple(itemstack, worldIn, playerIn);
        	        }
        	 }
        }
        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }*/
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	{
    	return EnumAction.BOW;
    	}
    }
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
    	return 7200;
    }
}