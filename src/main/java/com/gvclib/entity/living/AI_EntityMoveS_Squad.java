package gvclib.entity.living;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class AI_EntityMoveS_Squad
{
	public static void newmove(EntityGVCLivingBase entity, int id, float sp, float turnspeed, double max, double range1, double range2, double followrange) {
		entity.sneak = false;
		boolean ta = false;
		double range = range1;
		if(entity.getMobMode() == 1){
			range = range2;
		}
		
		boolean medic_flag = false;
		if(id == 3 && entity.getMoveT() != 3 && entity.getHealth() > 0.0F){
			{
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				double han = range;
				AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double) (x - han), (double) (y - han),
						(double) (z - han), (double) (x + han), (double) (y + han), (double) (z + han)))
								.grow(han);
				List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb2);
				if (llist != null) {
					//player
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							if (entity1 != null && entity1 instanceof EntityLivingBase)//follow
							{
								EntityLivingBase living = (EntityLivingBase) entity1;
								double d5 = entity1.posX - entity.posX;
								double d7 = entity1.posZ - entity.posZ;
								double d6 = entity1.posY - entity.posY;
								double d1 = entity.posY - (entity1.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								if (!entity.getattacktask()) {
									entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote = -((float) Math
											.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									entity.rotationp = entity.rotationPitch = -f11 + 0;
								}
								double ddx = Math.abs(d5);
								double ddz = Math.abs(d7);
								
								boolean ffflag = false;
								
								if (living instanceof ISoldier ) {
									if(living.getHealth() < living.getMaxHealth() / 2) {
										ffflag = true;
									}
								}
								if (living instanceof EntityPlayerMP) {
									if(living.getHealth() < living.getMaxHealth() / 2) {
										ffflag = true;
									}
								}
								
								if(ffflag) {
									if(living.getHealth() <= 0.0F && living instanceof ISoldier) {
										if ((ddx < 1 && ddz < 1) && entity.cooltime > 40) {
											living.deathTime = 0;
											living.setHealth(4);
											living.playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 1.0F);
											entity.cooltime = 0;
											entity.swingArm(EnumHand.MAIN_HAND);
										}
									}
									if ((ddx < 1 && ddz < 1) && entity.cooltime > 40) {
										living.setHealth(living.getHealth() + 4);
										living.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
										entity.cooltime = 0;
										entity.swingArm(EnumHand.MAIN_HAND);
									}
									if ((ddx < followrange && ddz < followrange)  && entity.cooltime > 40) {
										MoveS(entity, sp, 1, living.posX, living.posY, living.posZ, 0);
									}
									medic_flag = true;
								}
							}
						}
					}
					//player
				}
			}
		}
		if(medic_flag)return;
		
		{
			double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;
			double han = range;
			AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double) (x - han), (double) (y - han),
					(double) (z - han), (double) (x + han), (double) (y + han), (double) (z + han)))
							.grow(han);
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb2);
			if (llist != null) {
				//player
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if (entity.getMoveT() == 0)//follow
						{
							if (entity1 != null && entity1 instanceof EntityPlayerMP) {
								EntityPlayer player = (EntityPlayer) entity1;
								//System.out.println(String.format("m"));
								if (entity.isOwner(player) && !entity.isRiding()) {
									double d5 = entity1.posX - entity.posX;
									double d7 = entity1.posZ - entity.posZ;
									double d6 = entity1.posY - entity.posY;
									double d1 = entity.posY - (entity1.posY);
									double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
									float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
									if (!entity.getattacktask()) {
										entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote = -((float) Math
												.atan2(d5, d7)) * 180.0F / (float) Math.PI;
										entity.rotationp = entity.rotationPitch = -f11 + 0;
									}
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									if ((ddx > followrange || ddz > followrange)) {
										entity.setPositionAndUpdate(player.posX, player.posY, player.posZ);
									} else {
										if ((ddx > entity.squad_followrange || ddz > entity.squad_followrange)) {
											if (ta) {
												MoveS(entity, sp, 1, player.posX, player.posY, player.posZ, 1);
											} else {
												MoveS(entity, sp, 1, player.posX, player.posY, player.posZ, 0);
											}
											
										}
									}
									//System.out.println(String.format("move"));
								}
							}
						}
						if (entity1 != null && entity1 instanceof ISoldier && entity.getMoveT() != 3 && entity1 instanceof EntityGVCLivingBase) {
							//System.out.println(String.format("en"));
							EntityGVCLivingBase soldier = (EntityGVCLivingBase) entity1;
							double d5 = entity1.posX - entity.posX;
							double d7 = entity1.posZ - entity.posZ;
							double d6 = entity1.posY - entity.posY;
							double d1 = entity.posY - (entity1.posY);
							double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
							if ((ddx < 2 && ddz < 2) && ((EntityLivingBase) entity1).getHealth() > 0.0F && entity.getHealth() > 0.0F) {
								if(!entity.medic && !soldier.medic)MoveS(entity, -sp, 1, entity1.posX, entity1.posY, entity1.posZ, 0);
							}
							//break;
						}
					}
				}
				//player
			}
		}
		{
			double x = entity.posX;
			double y = entity.posY;
			double z = entity.posZ;
			double han = range;
			AxisAlignedBB axisalignedbb2 = 
					(new AxisAlignedBB((double)(x-han), (double)(y-han), (double)(z-han), (double)(x + han), (double)(y + han), (double)(z+ han)))
            		.grow(han);
			//List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
			//		.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb2);
			if (llist != null) {
				//mob
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if(entity.targetentity == entity1 && entity1 != null && entity1 instanceof EntityLivingBase) {
							EntityLivingBase en = (EntityLivingBase) entity1;
							if(en.deathTime > 0) {
								entity.targetentity = null;
							}
						}
						if(entity.getMoveT() == 3) {//wait
							if(entity.targetentity == entity1 && entity1 != null) {
								double d5 = entity.targetentity.posX - entity.posX;
								double d7 = entity.targetentity.posZ - entity.posZ;
								double d6 = entity.targetentity.posY - entity.posY;
								double d1 = entity.posY - (entity.targetentity.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								entity.rotationYaw = entity.renderYawOffset = entity.rotationYawHead = entity.rotation = entity.rote
										= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								entity.rotationp = entity.rotationPitch = -f11 + 0;
								ta = true;
								break;
							}
						}
						else if(entity.getMoveT() == 5) {//pointfire
							{
								double d5 = entity.getMoveX() - entity.posX;
								double d7 = entity.getMoveZ()- entity.posZ;
								double d6 = entity.getMoveY() - entity.posY;
								double d1 = entity.posY - (entity.getMoveY());
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								 
								{
									entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote
											= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									entity.rotationp = entity.rotationPitch = -f11 + 0;
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									ta = true;
								}
								ta = true;
								break;
							} 
						}
						else if(entity.getMoveT() == 0) {//follow
							if(entity.targetentity == entity1 && entity1 != null) {
								double d5 = entity.targetentity.posX - entity.posX;
								double d7 = entity.targetentity.posZ - entity.posZ;
								double d6 = entity.targetentity.posY - entity.posY;
								double d1 = entity.posY - (entity.targetentity.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								entity.rotationYaw = entity.renderYawOffset = entity.rotationYawHead = entity.rotation = entity.rote
										= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								entity.rotationp = entity.rotationPitch = -f11 + 0;
								if (flag) {
									entity.targetentity = (EntityLivingBase) entity1;
								}
								ta = true;
							}
							
						}
						else if(entity.getMoveT() == 1 && !entity.getattacktask()) {//free
							if (entity1 != null && entity1 instanceof EntityVehicleBase && flag && entity.biped && entity.targetentity == null) {
								EntityVehicleBase ve = (EntityVehicleBase) entity1;
								double d5 = entity1.posX - entity.posX;
								double d7 = entity1.posZ - entity.posZ;
								double ddx = Math.abs(d5);
								double ddz = Math.abs(d7);
								if(ve.getPassengers().size() < ve.riddng_maximum  && !ve.getVehicleLock() && ve.getHealth() > 0.0F)
								{
									if(entity.wrench && ve.getHealth() < ve.getMaxHealth()){
										if ((ddx > 3 || ddz > 3)) {
											MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, 0);
										}
									}else{
										MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, 0);
									}
									entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
								}
							}else
							if (entity.CanAttack(entity1) && entity1 != null) {//target
								if (entity.targetentity == entity1
										&& ((EntityLivingBase) entity1).getHealth() > 0.0F) {
									double d5 = entity1.posX - entity.posX;
									double d7 = entity1.posZ - entity.posZ;
									double d6 = entity1.posY - entity.posY;
									double d1 = entity.posY - (entity1.posY);
									double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
									float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
									
									if (flag) {
										entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote
												= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
										entity.rotationp = entity.rotationPitch = -f11 + 0;
										double ddx = Math.abs(d5);
										double ddz = Math.abs(d7);
										if ((ddx > max || ddz > max)) {
											MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, 0);
										}
										entity.noflag = 0;
										ta = true;
									} else {
										++entity.noflag;
										if(entity.noflag > 100) {
											{
												MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, 0);
											}
										}else {
											ta = true;
										}
									}
									if (flag) {
										entity.targetentity = (EntityLivingBase) entity1;
									}
									ta = true;
									break;
								} 
							}//target
						}
						else if(entity.getMoveT() == 2) {//pointattack
							{//target
								{
									double d5 = entity.getMoveX() - entity.posX;
									double d7 = entity.getMoveZ()- entity.posZ;
									double d6 = entity.getMoveY() - entity.posY;
									double d1 = entity.posY - (entity.getMoveY());
									double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
									float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
									{
										if(!entity.getattacktask())entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
										if(!entity.getattacktask())entity.rotationp = entity.rotationPitch = -f11 + 0;
										double ddx = Math.abs(d5);
										double ddz = Math.abs(d7);
										if ((ddx > 1 || ddz > 1)){
											MoveS(entity, sp, 1, entity.getMoveX(), entity.getMoveY(), entity.getMoveZ(), 0);
										}else {
											entity.setMoveT(3);
										}
										ta = true;
									}
									ta = true;
									break;
								} 
							}//target
						}
					}
				}
				//mob
			}
		}
		if(!entity.getattacktask()){
			boolean item = false;
			{
				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				double han = range;
				AxisAlignedBB axisalignedbb2 = (new AxisAlignedBB((double) (x - han), (double) (y - han),
						(double) (z - han), (double) (x + han), (double) (y + han), (double) (z + han)))
								.grow(han);
				List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb2);
				if (llist != null) {
					//player
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							if (entity.getMoveT() == 3)//follow
							{
								if (entity1 != null && entity1 instanceof EntityPlayer) {
									EntityPlayer player = (EntityPlayer) entity1;
									if (entity.isOwner(player) && !entity.isRiding()) {
										double d5 = entity1.posX - entity.posX;
										double d7 = entity1.posZ - entity.posZ;
										double d6 = entity1.posY - entity.posY;
										double d1 = entity.posY - (entity1.posY);
										double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
										float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
										ItemStack itemstack = player.getHeldItemMainhand();
										//if (!itemstack.isEmpty() && itemstack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER))
										if (!itemstack.isEmpty() && itemstack.getItem() == Items.DIAMOND)
										{
											entity.rotationYawHead = entity.rotationYaw = entity.renderYawOffset = entity.rotation = entity.rote = -((float) Math
													.atan2(d5, d7)) * 180.0F / (float) Math.PI;
											entity.rotationp = entity.rotationPitch = -f11 + 0;
											item = true;
										}
									}
								}
							}
						}
					}
					//player
				}
			}
			
			if(!item)stay(entity, id);
			//entity.setattacktask(false);
		}else{
			//entity.sneak = true;
			//entity.setattacktask(true);
		}
		if(entity.targetentity != null) {
			//entity.setattacktask(true);
		}else {
			//entity.setattacktask(false);
		}
	}
	
	public static void MoveS(EntityGVCLivingBase entity, double speed, double han, double ex, double ey, double ez, int id){
		{// 1
			//if(!entity.world.isRemote)
			{
				double d5 = ex - entity.posX;
				double d7 = ez - entity.posZ;
				double root = Math.sqrt(d5 * d5 + d7 * d7);
				//entity.rotationYaw = entity.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				float yawset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				float yaw = yawset * (2 * (float) Math.PI / 360);
				double mox = 0;
				double moy = entity.motionY;
				double moz = 0;
				mox -= MathHelper.sin(yaw) * speed * 1.5;
				moz += MathHelper.cos(yaw) * speed * 1.5;
				if (entity.onGround) {
					
				}
				entity.stepHeight = entity.height * 0.8F;
				//if(entity.world.rand.nextInt(4) == 0) 
				{
					//entity.getNavigator().tryMoveToXYZ(ex, ey, ez, speed * 10);
					if(id == 0 || !(entity.getHealth() > 0)){
						entity.motionX = mox;
						entity.motionZ = moz;
						//entity.motionY = y;
						entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
					}else 
					{
						if(entity.cooltime3 > 20) {
							entity.getNavigator().tryMoveToXYZ(ex, ey, ez, speed * 15);
							entity.cooltime3 = 0;
						}
						
					}
				}
			}
		} // 1
	}
	
	public static void stay(EntityGVCLivingBase entity, int id) {
		entity.setMobMode(0);
		double xPosition = 0;
	    double yPosition = 0;
	    double zPosition = 0;
		if (entity.getIdleTime() >= 100)
        {
        }
        else if (entity.getRNG().nextInt(120) != 0)
        {
        }
        else
        {
            Vec3d vec3 = RandomPositionGenerator.findRandomTarget(entity, 10, 7);

            if (vec3 == null)
            {
            }
            else
            {
                xPosition = vec3.x;
                yPosition = vec3.y;
                zPosition = vec3.z;
            }
            entity.rotation = entity.rotationYawHead = entity.getRNG().nextInt(120) - 60;
        }
		{
			if(entity.rote > 180F){
				entity.rote = -179F;
			}
			if(entity.rote < -180F){
				entity.rote = 179F;
			}
			float f3 = (float) (entity.rotationYawHead - entity.rote);
			 if(entity.rotationYawHead != entity.rote){
         		if(f3 > 1){
 					if(f3 > 180F){
 						entity.rotationYaw = entity.rotationYaw + 1;
 					}else{
 						entity.rotationYaw = entity.rotationYaw - 1;
 					}
 				}
 				else if(f3 < -1){
 					if(f3 < -180F){
 						entity.rotationYaw = entity.rotationYaw - 1;
 					}else{
 						entity.rotationYaw = entity.rotationYaw + 1;
 					}
 				}
	            }
		}
		if(id != 0 && entity.getMoveT() != 3) {
			entity.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, 1D);
		}
	}
}

