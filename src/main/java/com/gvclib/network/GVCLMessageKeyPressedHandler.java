package gvclib.network;
import java.util.List;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityB_Grapple;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import gvclib.entity.trader.EntityNPCBase;
import gvclib.item.ItemArmor_Grapple;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_AR;
import gvclib.item.gunbase.IGun_Shield;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
 
import net.minecraft.util.text.TextComponentTranslation;
 
public class GVCLMessageKeyPressedHandler implements IMessageHandler<GVCLMessageKeyPressed, IMessage> {
 
    @Override
    public IMessage onMessage(GVCLMessageKeyPressed message, MessageContext ctx) {
        EntityPlayer entityPlayer = ctx.getServerHandler().player;
//        EntityPlayer player = mod_GVCLib.proxy.getEntityPlayerInstance();
        if(message.key == 10){
    		//Entity en = (Entity) player.world.getEntityByID(message.key2);
    		//if(player == en)
    		if(entityPlayer.getEntityId() == message.fre)
    		{
    			NBTTagCompound nbt = entityPlayer.getEntityData();
    			nbt.setInteger("hitentity", 20);
    			{
    		//		entityPlayer.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F);
    			}
    			//player.world.playSoundAtEntity(player, "gvcmob:gvcmob.heli", 4.0F, 1.0F);
    			//player.addChatComponentMessage(new ChatComponentText(String.format("Received byte %d", message.key2)));
    		}
    	}
        if(message.key == 9){
    		//Entity en = (Entity) player.world.getEntityByID(message.key2);
    		//if(player == en)
    		if(entityPlayer.getEntityId() == message.fre)
    		{
    			NBTTagCompound nbt = entityPlayer.getEntityData();
    			nbt.setInteger("hitentitydead", 20);
    		}
    	}
        //受け取ったMessageクラスのkey変数の数字をチャットに出力
        if(message.key == 1){//开火减少耐久
        	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
            int li = itemstack.getMaxDamage() - itemstack.getItemDamage();
            if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
            	ItemGunBase gun = (ItemGunBase) itemstack.getItem();
            	gun.zandan = gun.getDamage(itemstack);
    			}
    	    itemstack.damageItem(li, entityPlayer);
        }/**/
        
        if (message.key == 5) {
			ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
			//if(itemstack != null && itemstack.getItem() instanceof GVCItemmagazine){
			/*if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
				if (entityPlayer.openContainer != entityPlayer.inventoryContainer)
		        {
					entityPlayer.closeScreen();
		        }
			//	System.out.println(String.format("1"));
			entityPlayer.openGui(mod_GVCLib.INSTANCE, 0, 
					entityPlayer.world, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
		//	entityPlayer.addChatComponentMessage(new ChatComponentText("aaa"));
			}*/
			if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
				if (entityPlayer.openContainer == entityPlayer.inventoryContainer)
		        {
					entityPlayer.openGui(mod_GVCLib.INSTANCE, 0, 
							entityPlayer.world, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
		        }
			}
		}
        if (message.key == 6) {
			ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
			//if(itemstack != null && itemstack.getItem() instanceof GVCItemmagazine){
			if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase){
			//	System.out.println(String.format("1"));
			entityPlayer.openGui(mod_GVCLib.INSTANCE, 3, 
					entityPlayer.world, (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
		//	entityPlayer.addChatComponentMessage(new ChatComponentText("aaa"));
			}
		}

        if(message.key == 11){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.server1 = true;
        	}
        }
        if(message.key == 12){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.server2 = true;
        	}
        }
        if(message.key == 13){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverspace = true;
        	}
        }
        if(message.key == 14){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverx = true;
        	}
        }
        if(message.key == 15){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverg = true;
        	}
        }
        if(message.key == 16){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverc = true;
        	}
        }
        if(message.key == 17){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverz = true;
        	}
        }
        if(message.key == 18){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverh = true;
        	}
        }
        if(message.key == 19){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverf = true;
        	}
        }
        if(message.key == 20){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverv = true;
        	}
        }
        if(message.key == 21){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.serverb = true;
        	}
        }
        if(message.key == 22){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityGVCLivingBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.servertab = true;
        	}
        }
        
        if(message.key == 31){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityVehicleBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityVehicleBase base = (EntityVehicleBase) en;
        		base.spg_yaw = message.sp;
        	}
        }
        if(message.key == 32){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (entityPlayer.getRidingEntity() instanceof EntityVehicleBase && entityPlayer.getRidingEntity() != null && entityPlayer.getRidingEntity() == en) {// 1
        		EntityVehicleBase base = (EntityVehicleBase) en;
        		base.spg_picth = message.sp;
        	}
        }
        
        {
        	 if(message.key == 1310){
              	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
              	if(!itemstack.isEmpty()){
      				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
      				NBTTagCompound nbtgun = itemstack.getTagCompound();
      				nbtgun.setInteger("RecoiledTime", 0);
      				nbtgun.setBoolean("Recoiled", true);
      			}
              }
			  
        	 if(message.key == 1311){
               	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
               	if(!itemstack.isEmpty()){
       				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
       				NBTTagCompound nbtgun = itemstack.getTagCompound();
       				nbtgun.setInteger("RecoiledTime", message.da);
       				nbtgun.setBoolean("Recoiled", false);
       			}
               }
			   
        	 if(message.key == 1312){
              	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
              	if(!itemstack.isEmpty()){
      				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
					int li = gun.getMaxDamage() - itemstack.getItemDamage();
					if(gun instanceof ItemGun_AR){
						//mod_GVCLib.proxy.onShootAnimation(ply, stack);
						gun.Fire_AR_Left(itemstack, entityPlayer.world, entityPlayer, li, true, true);
						//gun.Fire_AR(stack, ply.world, ply, li, true);
						//gun.FireBullet(stack, ply.world, ply);
					}else{
						//mod_GVCLib.proxy.onShootAnimation(ply, stack);
						gun.Fire_SR_Left(itemstack, entityPlayer.world, entityPlayer, li, true, true);
						//gun.Fire_SR(stack, ply.world, ply, li, true);
						//gun.FireBullet(stack, ply.world, ply);
					}
      			}
              }
        }
        
        
        if(message.key == 75){
         	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
         	if(!itemstack.isEmpty() && itemstack.getItem() instanceof IGun_Shield){
 				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
 				NBTTagCompound nbtgun = itemstack.getTagCompound();
 				nbtgun.setBoolean("CAN_SD", message.torf);
 			}
         }
        if(message.key == 76){
         	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemOffhand();
         	if(!itemstack.isEmpty() && itemstack.getItem() instanceof IGun_Shield){
 				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
 				NBTTagCompound nbtgun = itemstack.getTagCompound();
 				nbtgun.setBoolean("CAN_SD", message.torf);
 			}
         }
        
        
        {
        	 if(message.key == 101){
             	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemOffhand();
             	if(!itemstack.isEmpty()  && itemstack.getItem() instanceof ItemGunBase){
     				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
     				NBTTagCompound nbtgun = itemstack.getTagCompound();
     				nbtgun.setBoolean("LeftClick", true);
     				nbtgun.setInteger("LeftTime", 0);
     			}
             }
             if(message.key == 1011){
             	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemOffhand();
             	if(!itemstack.isEmpty()  && itemstack.getItem() instanceof ItemGunBase){
     				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
     				NBTTagCompound nbtgun = itemstack.getTagCompound();
     				nbtgun.setBoolean("LeftClick", false);
     				nbtgun.setInteger("LeftTime", 0);
     		//		nbtgun.setBoolean("Cocking", false);
			//		nbtgun.setBoolean("Recoiled", false);
     				//System.out.println(String.format("offhand"));
     			}
             }
        }
        {
        	if(message.key == 111){
            	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemOffhand();
            	if(!itemstack.isEmpty()){
    				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
    				NBTTagCompound nbtgun = itemstack.getTagCompound();
    				nbtgun.setBoolean("RightClick", true);
    				nbtgun.setInteger("RightTime", 0);
    			}
            }
            if(message.key == 1111){
            	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemOffhand();
            	if(!itemstack.isEmpty()){
    				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
    				NBTTagCompound nbtgun = itemstack.getTagCompound();
    				nbtgun.setBoolean("RightClick", false);
    				nbtgun.setInteger("RightTime", 0);
    				nbtgun.setBoolean("Cocking", false);
					nbtgun.setBoolean("Recoiled", false);
    			}
            }
        }
        {
        	 if(message.key == 102){
             	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
     			if(itemstack != null){
     				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
     				NBTTagCompound nbtgun = itemstack.getTagCompound();
     				nbtgun.setBoolean("LeftClick", true);
     				nbtgun.setInteger("LeftTime", 0);
     			}
             }
        	 if(message.key == 1021){
              	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
      			if(itemstack != null){
      				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
      				NBTTagCompound nbtgun = itemstack.getTagCompound();
      				nbtgun.setBoolean("LeftClick", false);
      				nbtgun.setInteger("LeftTime", 0);
      			}
              }
        }
        {
        	if(message.key == 112){
            	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
    			if(itemstack != null){
    				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
    				NBTTagCompound nbtgun = itemstack.getTagCompound();
    				nbtgun.setBoolean("RightClick", true);
    				nbtgun.setInteger("RightTime", 0);
    			}
            }
        	if(message.key == 1121){
            	ItemStack itemstack = ((EntityPlayer)(entityPlayer)).getHeldItemMainhand();
    			if(itemstack != null){
    				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
    				NBTTagCompound nbtgun = itemstack.getTagCompound();
    				nbtgun.setBoolean("RightClick", false);
    				nbtgun.setInteger("RightTime", 0);
    			}
            }
        }
        
        if(message.key == 201 && entityPlayer != null && entityPlayer.getEntityId() == message.fre){
        	World worldIn = entityPlayer.world;
        	ItemStack itemstack = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        	if(!itemstack.isEmpty() && itemstack.getItem() instanceof ItemArmor_Grapple) {
        		{//list
					List<EntityB_Grapple> llist =entityPlayer.world.getEntitiesWithinAABB(EntityB_Grapple.class,
							entityPlayer.getEntityBoundingBox().grow(120.0D));
					if (llist != null) {
						for (int lj = 0; lj < llist.size(); lj++) {
							EntityB_Grapple entity1 = (EntityB_Grapple) llist.get(lj);
							if (entity1 != null){
									if (!entity1.world.isRemote)
									{
										entity1.setDead();
									}
							}
						}
					}
				}//list
        		ItemArmor_Grapple grapplei = (ItemArmor_Grapple) itemstack.getItem();
        		worldIn.playSound((EntityPlayer)null, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, 
            			SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));
            	//Vec3d lock = entityPlayer.getForward();
        		Vec3d lock = Vec3d.fromPitchYaw(entityPlayer.rotationPitch, entityPlayer.rotationYaw);
        		{
        			grapplei.point = true;
        			entityPlayer.getCooldownTracker().setCooldown(itemstack.getItem(), 40);

        			EntityB_Grapple grapple = new EntityB_Grapple(worldIn, entityPlayer);
        			//grapple.gra = 0.0299F;
        			grapple.playerid = entityPlayer.getEntityId();
        			grapple.setTamedBy(entityPlayer);
        			//grapple.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw,player.rotationPitch);
        			double xx11 = 0;
        			double zz11 = 0;
        			double yy11 = 0;
        			float xz = 1.57F;
        			
        			double yy = 1.5D;
        			double zzz = 1.0 * Math.cos(Math.toRadians(-entityPlayer.rotationPitch));
        			xx11 -= MathHelper.sin(entityPlayer.rotationYawHead * 0.01745329252F) * zzz;
        			zz11 += MathHelper.cos(entityPlayer.rotationYawHead * 0.01745329252F) * zzz;
        			xx11 -= MathHelper.sin(entityPlayer.rotationYawHead * 0.01745329252F + xz) * 0.3;
        			zz11 += MathHelper.cos(entityPlayer.rotationYawHead * 0.01745329252F + xz) * 0.3;
        			yy11 = MathHelper.sqrt(zzz* zzz) * Math.tan(Math.toRadians(-entityPlayer.rotationPitch)) * 1D;
        			grapple.setLocationAndAngles(entityPlayer.posX + xx11, entityPlayer.posY + yy + yy11, entityPlayer.posZ + zz11, 
        					entityPlayer.rotationYaw,entityPlayer.rotationPitch);
        			grapple.setHeadingFromThrower(entityPlayer, entityPlayer.rotationPitch, entityPlayer.rotationYaw, 0.0F, 4.0F, 0.5F);
        			if (!worldIn.isRemote) {
        				worldIn.spawnEntity(grapple);
        			}
        		}
        		/*for (int x = 0; x < 120; ++x) {
        			if (worldIn.getBlockState(new BlockPos(entityPlayer.posX + lock.x * x, entityPlayer.posY + 1.5 + lock.y * x,
        					entityPlayer.posZ + lock.z * x)).getBlock() != Blocks.AIR) {
        				grapplei.pointX = (int) (entityPlayer.posX + lock.x * x);
        				grapplei.pointY = (int) (entityPlayer.posY + 1.5 + lock.y * x);
        				grapplei.pointZ = (int) (entityPlayer.posZ + lock.z * x);
        				grapplei.point = true;
        				entityPlayer.getCooldownTracker().setCooldown(itemstack.getItem(), 40);
        				if (!worldIn.isRemote)
        		        {
        		            EntityB_Grapple grapple = new EntityB_Grapple(worldIn, entityPlayer);
        		            grapple.gra = 0.03F;
        		            grapple.playerid = entityPlayer.getEntityId();
        		            grapple.setLocationAndAngles(grapplei.pointX, grapplei.pointY, grapplei.pointZ, entityPlayer.rotationYaw,entityPlayer.rotationPitch);
        		            grapple.setHeadingFromThrower(entityPlayer, entityPlayer.rotationPitch, entityPlayer.rotationYaw, 0.0F, 0.4F, 0F);
        		            worldIn.spawnEntity(grapple);
        		        }
        				break;
        			}
        		}*/
        	}
        }
        if(message.key == 202 && entityPlayer != null){
        	World worldIn = entityPlayer.world;
        	{
        		{//list
        			double x = entityPlayer.posX;
        			double y = entityPlayer.posY;
        			double z = entityPlayer.posZ;
        			int han = 120;
        			AxisAlignedBB aabb = (new AxisAlignedBB((double) (x - han), (double) (y - han),
        					(double) (z - han),
        					(double) (x + han), (double) (y + han), (double) (z + han)))
        							.grow(1);
        			List<EntityB_Grapple> llist = entityPlayer.world.getEntitiesWithinAABB(EntityB_Grapple.class,
        					aabb);
					if (llist != null) {
						for (int lj = 0; lj < llist.size(); lj++) {
							EntityB_Grapple entity1 = (EntityB_Grapple) llist.get(lj);
							if (entity1 != null){
									if (!entity1.world.isRemote)
									{
										entity1.setDead();
									}
							}
						}
					}
				}//list
        	}
        }
       
        
        if(message.key == 1110){
        	if(entityPlayer != null && !entityPlayer.world.isRemote) {
        		entityPlayer.entityDropItem(new ItemStack(Items.APPLE), 1);
        	}
        }
        
        if(message.key == 2000){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.startuptime = message.da;
        	}
        }
        if(message.key == 2001){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime = message.da;
        	}
        }
        if(message.key == 2002){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime2 = message.da;
        	}
        }
        if(message.key == 2003){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime3 = message.da;
        	}
        }
        if(message.key == 2004){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime4 = message.da;
        	}
        }
        if(message.key == 2005){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime5 = message.da;
        	}
        }
        if(message.key == 2006){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.cooltime6 = message.da;
        	}
        }
        if(message.key == 2007){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count1 = message.da;
        	}
        }
        if(message.key == 2008){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count2 = message.da;
        	}
        }
        if(message.key == 2009){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count3 = message.da;
        	}
        }
        if(message.key == 2010){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.gun_count4 = message.da;
        	}
        }
        
        if(message.key == 1500){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.sell_now_id < base.sell_page - 1) {
        			++base.sell_now_id;
        		}
        		int page = 10 * base.sell_now_id;
        		for(int x = 0; x < 9; ++x) {
        			base.hand[0 + x + page] = false;
	        	}
        	}
        }
        if(message.key == 1501){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.sell_now_id > 0) {
        			--base.sell_now_id;
        		}
        		int page = 10 * base.sell_now_id;
        		for(int x = 0; x < 9; ++x) {
        			base.hand[0 + x + page] = false;
	        	}
        	}
        }
        
        if(message.key == 1502){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.buy_now_id < base.buy_page - 1) {
        			++base.buy_now_id;
        		}
        	}
        }
        if(message.key == 1503){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityNPCBase) {// 1
        		EntityNPCBase base = (EntityNPCBase) en;
        		if(base.buy_now_id > 0) {
        			--base.buy_now_id;
        		}
        	}
        }
        
        if(message.key == 421){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase base = (EntityGVCLivingBase) en;
        		base.rotationYaw = (float)message.posx;
        		base.rotationPitch = (float)message.posy;
       // 		System.out.println(String.format("%1$.2f", message.posx));
       // 		System.out.println(String.format("%1$.2f", message.posy));
        	}
        }
        
        if(message.key == 422){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityLivingBase) {// 1
        		EntityLivingBase base = (EntityLivingBase) en;
        		base.onGround = message.torf;
        	}
        }
        
        
        
        if(message.key == 444){
        	EntityLivingBase en = (EntityLivingBase) entityPlayer.world.getEntityByID(message.fre);
        	if (en != null && en instanceof EntityGVCLivingBase) {// 1
        		EntityGVCLivingBase gvc_en = (EntityGVCLivingBase) en;
        		if(en.getControllingPassenger() != null) {
        			en.getControllingPassenger().setPositionAndUpdate(gvc_en.getMoveX(), gvc_en.getMoveY(), gvc_en.getMoveZ());
        			en.getControllingPassenger().dismountRidingEntity();
        			
        		}
        	}
        }
        
        return null;
    }
}