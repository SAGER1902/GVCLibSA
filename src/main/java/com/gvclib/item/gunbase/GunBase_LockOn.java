package gvclib.item.gunbase;

import java.util.List;

import gvclib.event.GVCSoundEvent;
import gvclib.item.ItemGunBase;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GunBase_LockOn {

	public static void load(ItemGunBase gun1, ItemStack itemstack, World world, Entity entity, int i, boolean flag) {

		
		if(!(entity instanceof EntityPlayer)) return;
		EntityPlayer entityplayer = (EntityPlayer)entity;
		ItemGunBase gun = null;
		if(!entityplayer.getHeldItemMainhand().isEmpty() && entityplayer.getHeldItemMainhand().getItem() instanceof ItemGunBase) {
			gun = (ItemGunBase) entityplayer.getHeldItemMainhand().getItem();
		}
		
  //  	if(world.isRemote)return;
    	
    	if (gun != null && gun.gun_type == 4) {
    		
    		NBTTagCompound nbt = itemstack.getTagCompound();
    		int d = 120;
    		
    		int target_id = nbt.getInteger("missile_target");
    		
    		/*if(nbt.getInteger("missile_target") != 0) {
    			if(flag)System.out.println(String.format("mitarget"));
    		}else {
    			if(flag)System.out.println(String.format("mitarget_null"));
    		}*/
    		
    		
    		Entity getTarget = null;
    		
    		if(entity.isSneaking())
    		{
    			
    			if(getTarget ==null)
    			{
    				int ix = 0;
					int iy = 0;
					int iz = 0;
					Vec3d lock = Vec3d.fromPitchYaw(entityplayer.rotationPitch, entityplayer.rotationYaw);
    				for(int xxx = 0; xxx < d; ++xxx) {
    					ix = (int) (entityplayer.posX + lock.x * xxx);
						iy = (int) (entityplayer.posY + 1.5 + lock.y * xxx);
						iz = (int) (entityplayer.posZ + lock.z * xxx);
						int han = 1;
						AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(ix-han), (double)(iy-han), (double)(iz-han), 
		            			(double)(ix + han), (double)(iy + han), (double)(iz+ han)))
		                		.grow(1);
		                List llist = entityplayer.world.getEntitiesWithinAABBExcludingEntity(entityplayer, axisalignedbb2);
        				if (llist != null) {
        					for (int lj = 0; lj < llist.size(); lj++) {
        						Entity entity1 = (Entity) llist.get(lj);
        						if (entity1.canBeCollidedWith()) {
        							//boolean flag = entity.getEntitySenses().canSee(entity1);
        							if (entity1 instanceof IMob && entity1 != null && entity1 != entityplayer && entity1 != entity && flag) {
        								getTarget = entity1;
        								break;
        							}
        						}
        					}
        				}
    				}
    				if(getTarget != null) {
						if(getTarget.isRiding() && getTarget.getRidingEntity() != null && getTarget.getRidingEntity() instanceof EntityLivingBase) {
							EntityLivingBase en = (EntityLivingBase) getTarget.getRidingEntity();
							BlockPos bp = en.world.getHeight(new BlockPos(en.posX, en.posY, en.posZ));
							if(gun.aam && en.posY > bp.getY() + 5 && !en.onGround)
							//if(gun.aam)
							{
								getTarget = (EntityLivingBase) en;
								gun.lockt = 0;
							}else if(en.posY < bp.getY() + 5){
								getTarget = (EntityLivingBase) en;
								gun.lockt = 0;
							}
						}else {
							BlockPos bp = getTarget.world.getHeight(new BlockPos(getTarget.posX, getTarget.posY, getTarget.posZ));
							if(gun.aam && getTarget.posY > bp.getY() + 10 && !getTarget.onGround)
							//if(gun.aam)
							{
								getTarget = (EntityLivingBase) getTarget;
								gun.lockt = 0;
							}else if(getTarget.posY < bp.getY() + 5){
								getTarget = (EntityLivingBase) getTarget;
								gun.lockt = 0;
							}
						}
    				}
    				
    			}
    			
    		}
    		/*else {
    			
    		}*/
    		
    		if(getTarget != null){
    			if(getTarget instanceof EntityLivingBase) {
    				EntityLivingBase getTarget_living = (EntityLivingBase) getTarget;
    				//getTarget_living.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 2, 1));
    				//gun.mitarget = getTarget_living;
        			if(getTarget_living.getEntityData() != null) {
        				NBTTagCompound target_nbt = getTarget_living.getEntityData();
        				target_nbt.setInteger("lockon", 100);
        				nbt.setInteger("missile_target", getTarget.getEntityId());
        				 if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
     						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
     							 if(playermp != null)GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(61, getTarget_living.getEntityId()), playermp);
        				 }
        			}else {
        			}
    			}
    			if (entityplayer.world.getWorldTime() % 2 == 0) {
    				entityplayer.playSound(GVCSoundEvent.sound_lock, 0.4F, 1.0F);
				}
			}
    		
    		++gun.lockt;
    		if(gun.lockt > 80) {
				boolean lock = false;
				int ix = 0;
				int iy = 0;
				int iz = 0;
				Vec3d locked = Vec3d.fromPitchYaw(entityplayer.rotationPitch, entityplayer.rotationYaw);
				for(int xxx = 0; xxx < d; ++xxx) {
					ix = (int) (entityplayer.posX + locked.x * xxx);
					iy = (int) (entityplayer.posY + 1.5 + locked.y * xxx);
					iz = (int) (entityplayer.posZ + locked.z * xxx);
					int han = 1;
					AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(ix-han), (double)(iy-han), (double)(iz-han), 
	            			(double)(ix + han), (double)(iy + han), (double)(iz+ han)))
	                		.grow(1);
	                List llist = entityplayer.world.getEntitiesWithinAABBExcludingEntity(entityplayer, axisalignedbb2);
    				if (llist != null) {
    					for (int lj = 0; lj < llist.size(); lj++) {
    						Entity entity1 = (Entity) llist.get(lj);
    						if (entity1.canBeCollidedWith()) {
    							//boolean flag = entity.getEntitySenses().canSee(entity1);
    							if (entity1 instanceof IMob && entity1 != null && entity1 != entityplayer
    									&& entity1 != entity && flag && entity1 == world.getEntityByID(target_id)) {
    								lock = true;
    								break;
    							}
    						}
    					}
    				}
				}
				if(!lock) {
					nbt.setInteger("missile_target", 0);
					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
 						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
 							GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(62), playermp);
					}
					
				}
			}
			
		}
	}
	
	
	public static EntityLivingBase getMissileTarget(ItemGunBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer) {
		EntityLivingBase target = null;
		/*if(gun.mitarget != null) {
			target = gun.mitarget;
			System.out.println(String.format("mitarget"));
		}*/
		
		if(itemstack.hasTagCompound()) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if(nbt.getInteger("missile_target") != 0) {
				Entity tgtEntity = world.getEntityByID(nbt.getInteger("missile_target"));
	            if(tgtEntity != null && tgtEntity instanceof EntityLivingBase) {
	            	target = (EntityLivingBase) tgtEntity;
	            	System.out.println(String.format("missile_target"));
	            }
			}
		}
		
		return target;
	}
	public static int getMissileTarget_ID(ItemGunBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer) {
		int targetid = 0;
		/*if(gun.mitarget != null && world != null) {
			targetid = gun.mitarget.getEntityId();
		}*/
		if(itemstack.hasTagCompound()) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			if(nbt.getInteger("missile_target") != 0) {
				targetid = nbt.getInteger("missile_target");
			}
		}
		return targetid;
	}
	
	
	
	
public static void load_old(ItemGunBase gun1, ItemStack itemstack, World world, Entity entity, int i, boolean flag) {

		
		if(!(entity instanceof EntityPlayer)) return;
		EntityPlayer entityplayer = (EntityPlayer)entity;
		ItemGunBase gun = null;
		if(!entityplayer.getHeldItemMainhand().isEmpty() && entityplayer.getHeldItemMainhand().getItem() instanceof ItemGunBase) {
			gun = (ItemGunBase) entityplayer.getHeldItemMainhand().getItem();
		}
		
  //  	if(world.isRemote)return;
    	
    	if (gun != null && gun.gun_type == 4) {
    		
    		if(gun.mitarget != null){
    			if(flag)System.out.println(String.format("mitarget"));
    		}else {
    			if(flag)System.out.println(String.format("mitarget_null"));
    		}
    		
    		NBTTagCompound nbt = entityplayer.getEntityData();
    		++gun.lockt;
    		Vec3d locken = entityplayer.getLookVec();
    		float d = 120;
    		if(gun.mitarget != null){
    			gun.mitarget.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 2, 1));
    			NBTTagCompound target_nbt = gun.mitarget.getEntityData();
    			if(target_nbt != null) {
    				target_nbt.setInteger("lockon", 100);
    				 System.out.println("1");
    				 if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
 						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
 							 if(playermp != null)GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(61, gun.mitarget.getEntityId()), playermp);
    				 }
    			}else {
    				//gun.mitarget.customEntityData = new NBTTagCompound();
    				//gun.mitarget.writeToNBT(new NBTTagCompound());
    				//gun.mitarget.set
    				//NBTTagCompound target_nbt2 = gun.mitarget.getEntityData();
    			}
			}
    		/*if(gun.lockt > 80) {
				boolean lock = false;
				int ix = 0;
				int iy = 0;
				int iz = 0;
				Vec3d locked = Vec3d.fromPitchYaw(entityplayer.rotationPitch, entityplayer.rotationYaw);
				for(int xxx = 0; xxx < d; ++xxx) {
					ix = (int) (entityplayer.posX + locked.x * xxx);
					iy = (int) (entityplayer.posY + 1.5 + locked.y * xxx);
					iz = (int) (entityplayer.posZ + locked.z * xxx);
					int han = 1;
					AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(ix-han), (double)(iy-han), (double)(iz-han), 
	            			(double)(ix + han), (double)(iy + han), (double)(iz+ han)))
	                		.grow(1);
	                List llist = entityplayer.world.getEntitiesWithinAABBExcludingEntity(entityplayer, axisalignedbb2);
    				if (llist != null) {
    					for (int lj = 0; lj < llist.size(); lj++) {
    						Entity entity1 = (Entity) llist.get(lj);
    						if (entity1.canBeCollidedWith()) {
    							//boolean flag = entity.getEntitySenses().canSee(entity1);
    							if (entity1 instanceof IMob && entity1 != null && entity1 != entityplayer
    									&& entity1 != entity && flag && entity1 == gun.mitarget) {
    								lock = true;
    								break;
    							}
    						}
    					}
    				}
				}
				if(!lock) {
					gun.mitarget =null;
					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
 						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
 							GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(62), playermp);
					}
					
				}
			}*/
    		if(entity.isSneaking()){
    			
    			if(gun.mitarget ==null)
    			{
    				//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(62));
    				Entity getTarget = null;
    				int ix = 0;
					int iy = 0;
					int iz = 0;
					Vec3d lock = Vec3d.fromPitchYaw(entityplayer.rotationPitch, entityplayer.rotationYaw);
    				for(int xxx = 0; xxx < d; ++xxx) {
    					ix = (int) (entityplayer.posX + lock.x * xxx);
						iy = (int) (entityplayer.posY + 1.5 + lock.y * xxx);
						iz = (int) (entityplayer.posZ + lock.z * xxx);
    					//entityplayer.world.spawnParticle(EnumParticleTypes.CLOUD, entity.posX + locken.x * xxx-1, entity.posY + locken.y * xxx-1, entity.posZ + locken.z * xxx-1, 0.0D, 0.0D, 0.0D, new int[0]);
    					/*AxisAlignedBB axisalignedbb = (new AxisAlignedBB(entity.posX + locken.x * xxx-1, entity.posY + locken.y * xxx-1, entity.posZ + locken.z * xxx-1, 
    							entity.posX + locken.x * xxx+1, entity.posY + locken.y * xxx+1, entity.posZ + locken.z * xxx+1))
                        		.grow(1);
    					List<Entity> llist = entityplayer.world.getEntitiesWithinAABBExcludingEntity(entityplayer,axisalignedbb);*/
						int han = 1;
						AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double)(ix-han), (double)(iy-han), (double)(iz-han), 
		            			(double)(ix + han), (double)(iy + han), (double)(iz+ han)))
		                		.grow(1);
		                List llist = entityplayer.world.getEntitiesWithinAABBExcludingEntity(entityplayer, axisalignedbb2);
        				if (llist != null) {
        					for (int lj = 0; lj < llist.size(); lj++) {
        						Entity entity1 = (Entity) llist.get(lj);
        						if (entity1.canBeCollidedWith()) {
        							//boolean flag = entity.getEntitySenses().canSee(entity1);
        							if (entity1 instanceof IMob && entity1 != null && entity1 != entityplayer && entity1 != entity && flag) {
        								getTarget = entity1;
        								break;
        							}
        						}
        					}
        				}
    				}
    				if(getTarget != null) {
						if(getTarget.isRiding() && getTarget.getRidingEntity() != null && getTarget.getRidingEntity() instanceof EntityLivingBase) {
							EntityLivingBase en = (EntityLivingBase) getTarget.getRidingEntity();
							BlockPos bp = en.world.getHeight(new BlockPos(en.posX, en.posY, en.posZ));
							//if(gun.aam && en.posY > bp.getY() + 5 && !en.onGround)
							if(gun.aam){
								gun.mitarget = (EntityLivingBase) en;
								gun.lockt = 0;
							}else if(en.posY < bp.getY() + 5){
								gun.mitarget = (EntityLivingBase) en;
								gun.lockt = 0;
							}
						}else {
							BlockPos bp = getTarget.world.getHeight(new BlockPos(getTarget.posX, getTarget.posY, getTarget.posZ));
							//if(gun.aam && getTarget.posY > bp.getY() + 10 && !getTarget.onGround)
							if(gun.aam)
							{
								gun.mitarget = (EntityLivingBase) getTarget;
								gun.lockt = 0;
							}else if(getTarget.posY < bp.getY() + 5){
								gun.mitarget = (EntityLivingBase) getTarget;
								gun.lockt = 0;
							}
						}
    				}
    				
    			}
    			
    		}else {
    			
    		}
    		if (entity instanceof EntityPlayerMP) {
				EntityPlayerMP lep = (EntityPlayerMP) entity;
				Container lctr = lep.openContainer;
				for (int li2 = 0; li2 < lctr.inventorySlots.size(); li2++) {
					ItemStack li2s = ((Slot) lctr.getSlot(li2)).getStack();
					if (li2s == itemstack) {
						lctr.inventoryItemStacks.set(li2, itemstack.copy());
						break;
					}
				}
			}
			
		}
	}
}
