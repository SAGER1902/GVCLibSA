package gvclib.entity.living;

import java.util.List;

import gvclib.mod_GVCLib;
import gvclib.world.GVCExplosionBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;

public abstract class AI_EntityMoveS
{
	public static void newmove(EntityGVCLivingBase entity, int id, float sp, float turnspeed, double max, double range1, double range2) {
		int fps = mod_GVCLib.proxy.getFPS();
		/*if(fps < 15)return;
		Chunk chunk = entity.world.getChunkFromBlockCoords(new BlockPos(entity.posX, entity.posY, entity.posZ));
		if (chunk.isEmpty())return;*/
		entity.sneak = false;
		boolean ta = false;
		double range = range1;
		if(entity.getMobMode() == 1){
			range = range2;
		}
		//entity.motionY *= 0.98;
		if (entity.isPotionActive(MobEffects.SLOWNESS))
        {
			sp = sp * 0.5F;
        }
		if (entity.isPotionActive(MobEffects.BLINDNESS))
        {
			sp = sp * 0.1F;
        }
		
		if(entity.getMoveT() == 1){
			/*if(entity.getAIType3() == 2) {
				if(entity.targetentity != null) {
					List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
							.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
					if (llist != null) {
						for (int lj = 0; lj < llist.size(); lj++) {
							Entity entity1 = (Entity) llist.get(lj);
							if (entity1.canBeCollidedWith()) {
								boolean flag = entity.getEntitySenses().canSee(entity1);
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
											entity.rotationYawHead = entity.rotation = entity.rote = entity.renderYawOffset = -((float) Math
													.atan2(d5, d7)) * 180.0F / (float) Math.PI;
											entity.rotationp = entity.rotationPitch = -f11 + 0;
											double ddx = Math.abs(d5);
											double ddz = Math.abs(d7);
											if ((ddx > max || ddz > max)) {
												MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
											}
											ta = true;
											entity.targetentity = (EntityLivingBase) entity1;
										}else {
											MoveT(entity, sp, 1, entity.getMoveX(), entity.getMoveY(), entity.getMoveZ(), id);
										}
										break;
									}
								} //target
							}
						}
					}
				}else {
					MoveT(entity, sp, 1, entity.getMoveX(), entity.getMoveY(), entity.getMoveZ(), id);
				}
			}else */
			{
				MoveT(entity, sp * 0.8, 1, entity.getMoveX(), entity.getMoveY(), entity.getMoveZ(), id);
			}
			if(entity.posX == entity.getMoveX() && entity.posY == entity.getMoveY() && entity.posZ == entity.getMoveZ()) {
				entity.setMoveT(0);
			}
			{
				List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
						entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ)
								.grow(range));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith() && entity1 != null && entity.getHealth() > 0.0F) {
							boolean flag = entity.getEntitySenses().canSee(entity1);
							if (entity.CanAttack(entity1) && entity1 != null) {
								if (entity.targetentity == entity1) {
									{
										double d51 = entity1.posX - entity.posX;
										double d71 = entity1.posZ - entity.posZ;
										double d61 = entity1.posY - entity.posY;
										double d11 = entity.posY - (entity1.posY);
										double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
										float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
										entity.rotation = entity.rotationYaw = entity.rotationYawHead = entity.renderYawOffset 
												= -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
										entity.rotationp = entity.prevRotationPitch = entity.rotationp = entity.rotationPitch = -f111
												+ 10;
										if (flag) {
											entity.targetentity = (EntityLivingBase) entity1;
										}
										ta = true;
									}
									break;
								} else if (entity.targetentity == null) {
									if (entity.CanAttack(entity1) && flag) {
										entity.targetentity = (EntityLivingBase) entity1;
										ta = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			if(entity.getAIType() == 1 && id == 0) {
				ta = true;
			}
		}
		else
		{
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
					.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (entity1 != null && entity1 instanceof EntityVehicleBase && flag && !entity.getattacktask()) {
							EntityVehicleBase ve = (EntityVehicleBase) entity1;
							//if (ve.getControllingPassenger() == null)
							if(ve.getPassengers().size() < ve.riddng_maximum && ve.getHealth() > 0.0F && !ve.getVehicleLock())
							{
								MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
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
									entity.rotationYawHead = entity.rotation = entity.rote = entity.renderYawOffset 
											= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									//entity.rotationYaw = entity.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									entity.rotationp = entity.rotationPitch = -f11 + 0;
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									if ((ddx > max || ddz > max)) {
										MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
									}
									entity.noflag = 0;
									ta = true;
								} else {
									++entity.noflag;
									if(entity.noflag < 100) {
										//if ((ddx > max / 2 || ddz > max / 2)) 
										{
											MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
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
							/*else 
								if (entity.targetentity == null) {
								if (entity.CanAttack(entity1) && flag && entity.world.rand.nextInt(4) == 0) {
									entity.targetentity = (EntityLivingBase) entity1;
									ta = true;
									entity.setMobMode(1);
									break;
								}
							}*/
						}//target
						
					}
				}
			}
		}
		if(!entity.getattacktask()){
			stay(entity, id);
			//entity.setattacktask(false);
			//entity.setSneak(false);
		}else{
			//entity.setSneak(true);
			//entity.sneak = true;
			//entity.setattacktask(true);
			//entity.setFlag(1, true);
			//entity.getDataManager().set(entity.FLAGS, Byte.valueOf((byte)(b0 | 1 << flag)));
		}
		{
			if(entity.rotationYaw > 180F){
				entity.rotationYaw = -179F;
			}
			if(entity.rotationYaw < -180F){
				entity.rotationYaw = 179F;
			}
			if(entity.rotationYawHead > 360F || entity.rotationYawHead < -360F){
				entity.rotationYawHead = 0;
			}
			if(entity.rotationYawHead > 180F){
				entity.rotationYawHead = -179F;
			}
			if(entity.rotationYawHead < -180F){
				entity.rotationYawHead = 179F;
			}
			float f3 = (float) (entity.rotationYawHead - entity.rotationYaw);
			 if(entity.rotationYawHead != entity.rotationYaw){
				 if(f3 <= turnspeed && f3 >= -turnspeed){
					 
				 }
				 else if(f3 > 1){
 					if(f3 > 180F){
 						entity.rotationYaw = entity.rotationYaw - turnspeed;
 					}else{
 						entity.rotationYaw = entity.rotationYaw + turnspeed;
 					}
 				}
 				else if(f3 < -1){
 					if(f3 < -180F){
 						entity.rotationYaw = entity.rotationYaw + turnspeed;
 					}else{
 						entity.rotationYaw = entity.rotationYaw - turnspeed;
 					}
 				}
	            }
		}
		/*if(!ta){
			stay(entity, id);
		}else{
			entity.sneak = true;
			//entity.setFlag(1, true);
			//entity.getDataManager().set(entity.FLAGS, Byte.valueOf((byte)(b0 | 1 << flag)));
		}*/
	}
	
	
	
	public static void MoveS(EntityLiving entity, double speed, double han, double ex, double ey, double ez, int id){
		{// 1
			if(!entity.world.isRemote)
			{
				//this.motionY = 0.2D;
				double ix = 0;
				double iz = 0;
				double iy = entity.motionY;
				float f12 = entity.rotationYaw * (2 * (float) Math.PI / 360);
				float f22 = entity.rotationPitch * (2 * (float) Math.PI / 360);

				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				
				double d5 = ex - entity.posX;
				double d7 = ez - entity.posZ;
				
				double root = Math.sqrt(d5 * d5 + d7 * d7);
				
				float off = 0;
				entity.rotationYaw = entity.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				float yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
				double mox = 0;
				double moy = entity.motionY;
				double moz = 0;
				mox -= MathHelper.sin(yaw) * speed * 1;
				moz += MathHelper.cos(yaw) * speed * 1;
				if (entity.onGround) {
					
				}
				entity.stepHeight = entity.height * 0.8F;
				//if(entity.world.rand.nextInt(4) == 0) 
				{
					//entity.getNavigator().tryMoveToXYZ(ex, ey, ez, speed * 10);
					{
						entity.motionX = mox;
						entity.motionZ = moz;
						//entity.motionY = y;
						entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
					}
				}
				
				
				
				/*boolean shougai = false;
				for(int i = 0; i < 50; ++i){
					ix -= MathHelper.sin(f12) * i * 0.5;
					iz += MathHelper.cos(f12) * i * 0.5;
					if(entity.world.getBlockState(new BlockPos(x + ix, y -1, z + iz)).getBlock() == Blocks.AIR
							&& entity.world.getBlockState(new BlockPos(x + ix, y - 2, z + iz)).getBlock() == Blocks.AIR) {
						shougai = true;
						//entity.world.createExplosion(entity, x, y, z, 0, false);
						break;
					}
					
				}
				double px = d5;
				double pz = d7;
				double root2 = Math.sqrt(px * px + pz * pz);
				
				
				//if(shougai) 
				{
					for(int i2 = 0; i2 < 360; i2 = i2 + 60){
						float ken = (entity.rotationYaw + i2) * (2 * (float) Math.PI / 360);
						boolean ro = false;
					     for(int i = 0; i < 20; ++i){
							ix -= MathHelper.sin(ken) * i * 0.5;
							iz += MathHelper.cos(ken) * i * 0.5;
							if(entity.world.getBlockState(new BlockPos(x + ix, y -1, z + iz)).getBlock() == Blocks.AIR
									&& entity.world.getBlockState(new BlockPos(x + ix, y - 2, z + iz)).getBlock() == Blocks.AIR) {
								ro = true;
								break;
							}
							
						}
					     if(!ro) {
					    	 //yaw = ken;
					    	 //entity.world.createExplosion(entity, x, y, z, 0, false);
					    	 px   -= MathHelper.sin(ken) *20 * 0.5;
					    	 pz += MathHelper.cos(ken) * 20 * 0.5;
					    	 break;
					     }
					}
				}
				*/
				//entity.setVelocity(mox, moy, moz);
				
				/*if (entity.isCollidedHorizontally) {
					speed = speed * - 1.1;
				//	entity.motionY = 1.2;
					off = 1.34F;
				}
				for(int i = 0; i < 5; ++i){
					ix -= MathHelper.sin(f12) * i * 0.5;
					iz += MathHelper.cos(f12) * i * 0.5;
					if(entity.world.getBlockState(new BlockPos(x + ix, y -1, z + iz)).getBlock() == Blocks.AIR
							&&entity.world.getBlockState(new BlockPos(x + ix, y - 2, z + iz)).getBlock() == Blocks.AIR) {
						speed = speed * - 0.6;
						ix = iz = 0;
						boolean left = false;
						for(int i2 = 0; i2 < 5; ++i2){
							ix -= MathHelper.sin(f12 + 1.57F) * i2 * 0.5;
							iz += MathHelper.cos(f12 + 1.57F) * i2 * 0.5;
							if(entity.world.getBlockState(new BlockPos(x + ix, y -1, z + iz)).getBlock() == Blocks.AIR
									&& entity.world.getBlockState(new BlockPos(x + ix, y - 2, z + iz)).getBlock() == Blocks.AIR) {
								left = true;
								break;
							}
						}
						if(!left) {
							off = -1.57F;
						}else {
							off =  1.57F;
						}
						
					}
					if(entity.world.getBlockState(new BlockPos(x + ix, y + 2, z + iz)).getBlock() != Blocks.AIR) {
						off = 1.34F;
					}
				}*/
				/*double mox = 0;
				double moz = 0;
				
				entity.stepHeight = entity.height * 0.75F;
				BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
				int genY = bp.getY() + 1;
				if(entity.isCollidedVertically){
					entity.motionX -= MathHelper.sin(f12 + off) * speed * 0.01;
					entity.motionZ += MathHelper.cos(f12 + off) * speed * 0.01;
				}else{
					entity.motionX -= MathHelper.sin(f12 + off) * speed;
					entity.motionZ += MathHelper.cos(f12 + off) * speed;
				//	Vec3d vec3d =entity.getLookVec();
				//	entity.getNavigator().tryMoveToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, speed);
				}
				if(id != 0) {
					//entity.move(MoverType.PLAYER, entity.motionX, entity.motionY,  entity.motionZ);
				}*/
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
		if(entity.isRiding()) {
			/*if(entity.getAIType() == 3) 
			{
				if(entity.ontick <= 5) {
					entity.rotationPitch = entity.rotationPitch - 4;
				}else if(entity.ontick >= 5 && entity.ontick <= 15) {
					entity.rotationPitch = entity.rotationPitch + 4;
				}else {
					entity.rotationPitch = entity.rotationPitch - 4;
				}
			}*/
		}
	}
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static void MoveT(EntityGVCLivingBase entity, double speed, double han, double ex, double ey, double ez, int id){
		{// 1
			/*if(entity.rotationYawHead > 360F || entity.rotationYawHead < -360F){
				entity.rotationYawHead = 0;
				entity.rotationYaw = 0;
				entity.prevRotationYaw = 0;
				entity.prevRotationYawHead = 0;
				entity.renderYawOffset = 0;
			}
			if(entity.rotationYawHead > 180F){
				entity.rotationYawHead = -179F;
				entity.rotationYaw = -179F;
				entity.prevRotationYaw = -179F;
				entity.prevRotationYawHead = -179F;
				entity.renderYawOffset = -179F;
			}
			if(entity.rotationYawHead < -180F){
				entity.rotationYawHead = 179F;
				entity.rotationYaw = 179F;
				entity.prevRotationYaw = 179F;
				entity.prevRotationYawHead = 179F;
				entity.renderYawOffset = 179F;
			}*/
			if(!entity.world.isRemote)
			{
				double ix = 0;
				double iz = 0;
				double iy = entity.motionY;
				float f12 = entity.rotationYaw * (2 * (float) Math.PI / 360);
				float f22 = entity.rotationPitch * (2 * (float) Math.PI / 360);

				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				
				double d5 = ex - entity.posX;
				double d7 = ez - entity.posZ;
				
				double root = Math.sqrt(d5 * d5 + d7 * d7);
				
				float off = 0;
				entity.rotationYaw = entity.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				float yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
				entity.stepHeight = entity.height * 0.8F;
				Vec3d lock = Vec3d.fromPitchYaw(entity.rotationPitch, entity.rotationYaw);//entity.getForward();
				if(id == 0) {
					double mox = 0;
					double moy = entity.motionY;
					double moz = 0;
					float yawoffset = 0F;
					for(int xx = 0; xx < 2; ++xx){
						for(int zz = 0; zz < 2; ++zz){
							//for(int yy = 0; yy < 3; ++yy)
							{
								BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY - 1,entity.posZ + lock.z * zz);
								BlockPos pos2 = new BlockPos(entity.posX + lock.x * xx, entity.posY - 2,entity.posZ + lock.z * zz);
								if (entity.world.getBlockState(pos1).getBlock() == Blocks.AIR
										&&entity.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
									double xyaw = 0;
									double zyaw = 0;
									xyaw -= MathHelper.sin(yaw + 1.57F) * xx;
									zyaw += MathHelper.cos(yaw + 1.57F) * zz;
									BlockPos pos3 = new BlockPos(entity.posX + xyaw, entity.posY - 1,entity.posZ + zyaw);
									BlockPos pos4 = new BlockPos(entity.posX + xyaw, entity.posY - 2,entity.posZ + zyaw);
									if (entity.world.getBlockState(pos3).getBlock() == Blocks.AIR
											&&entity.world.getBlockState(pos4).getBlock() == Blocks.AIR) {
										yawoffset = -1.57F;
									}else {
										yawoffset = 1.57F;
									}
								}
							}
						}
					}
					mox -= MathHelper.sin(yaw) * speed * 1;
					moz += MathHelper.cos(yaw) * speed * 1;
					if(entity.getAIType() != 1) 
					{
						/*if(entity.cooltime4 > 20){
							entity.getNavigator().tryMoveToXYZ(ex, ey, ez, speed * 10);
							entity.cooltime4 = 0;
						}*/
						{
							BlockPos pos0 = new BlockPos(entity.posX, entity.posY + 0,entity.posZ);
							BlockPos pos1 = new BlockPos(entity.posX, entity.posY + 1,entity.posZ);
							if (entity.world.getBlockState(pos0).getBlock() instanceof BlockLadder 
									|| entity.world.getBlockState(pos1).getBlock() instanceof BlockLadder) {
								entity.motionY = 0.2;
							}
							entity.motionX = mox;
							entity.motionZ = moz;
							//entity.motionY = y;
							entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
						}
						{
							boolean extime = false;
							for(int xx = 0; xx < 2; ++xx){
								for(int zz = 0; zz < 2; ++zz){
									{
										//BlockPos pos0 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 0,entity.posZ + lock.z * zz);
										BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 1,entity.posZ + lock.z * zz);
										if ( getBlockfalse(entity.world.getBlockState(pos1).getBlock())) {
											extime = true;
										}
									}
								}
							}
							if(extime){
								++entity.w5barst;
								entity.swingArm(EnumHand.MAIN_HAND);
								{
									if(entity.getAIType3() == 1) {
										if (!entity.world.isRemote) {
											for(int xx = 0; xx < 2; ++xx){
												for(int zz = 0; zz < 2; ++zz){
													BlockPos pos0 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 0,entity.posZ + lock.z * zz);
													BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 1,entity.posZ + lock.z * zz);
														{
							        						IBlockState iblockstate = entity.world.getBlockState(pos0);
							        		                Block block = iblockstate.getBlock();
							        		                if (iblockstate.getMaterial() != Material.AIR 
							        		                		&& (entity.w5barst >= block.getBlockHardness(iblockstate, entity.world, pos0) * 100))
							        		                {
							        		                    block.dropBlockAsItemWithChance(entity.world, pos0, entity.world.getBlockState(pos0), 1.0F, 0);
							        		                    entity.world.setBlockToAir(pos0);
							        		                    entity.w5barst = 0;
							        		                }
														}
														{
							        						IBlockState iblockstate = entity.world.getBlockState(pos1);
							        		                Block block = iblockstate.getBlock();
							        		                if (iblockstate.getMaterial() != Material.AIR
							        		                		&& (entity.w5barst >= block.getBlockHardness(iblockstate, entity.world, pos1) * 100))
							        		                {
							        		                    block.dropBlockAsItemWithChance(entity.world, pos1, entity.world.getBlockState(pos1), 1.0F, 0);
							        		                    entity.world.setBlockToAir(pos1);
							        		                    entity.w5barst = 0;
							        		                }
														}
												}
											}
						    			}
									}else if(entity.w5barst >= 200){
										if (!entity.world.isRemote) {
											BlockPos pos1 = new BlockPos(entity.posX, entity.posY - 0,entity.posZ);
											entity.world.setBlockState(pos1, Blocks.PLANKS.getDefaultState(), 2);
											entity.setPositionAndUpdate(entity.posX, entity.posY + 1.5D,entity.posZ);
						    			}
										entity.w5barst = 0;
									}
									
								}
							}else{
								entity.w5barst = 0;
							}
						}
					}
				}else if(id == 1 && !entity.isRiding()) {
					double mox = 0;
					double moy = entity.motionY;
					double moz = 0;
					mox -= MathHelper.sin(yaw) * speed * 0.75;
					moz += MathHelper.cos(yaw) * speed * 0.75;
					if(entity.cooltime4 > 40){
						
						for(int xx = 0; xx < 2; ++xx){
							for(int zz = 0; zz < 2; ++zz){
								//for(int yy = 0; yy < 3; ++yy)
								{
									BlockPos pos0 = new BlockPos(entity.posX + lock.x * xx, entity.posY - 0,entity.posZ + lock.z * zz);
									BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY - 1,entity.posZ + lock.z * zz);
									BlockPos pos2 = new BlockPos(entity.posX + lock.x * xx, entity.posY - 2,entity.posZ + lock.z * zz);
									if (getBlock(entity.world.getBlockState(pos0).getBlock())
											&& getBlock(entity.world.getBlockState(pos1).getBlock())
											&& getBlock(entity.world.getBlockState(pos2).getBlock())) {
										entity.swingArm(EnumHand.MAIN_HAND);
										entity.cooltime4 = 0;
										entity.world.setBlockState(pos1, Blocks.PLANKS.getDefaultState(), 2);
									}
								}
								{
									BlockPos pos0 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 0,entity.posZ + lock.z * zz);
									BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 1,entity.posZ + lock.z * zz);
									BlockPos pos2 = new BlockPos(entity.posX + lock.x * (xx - 1), entity.posY + 0,entity.posZ + lock.z * (zz - 1));
									BlockPos pos3 = new BlockPos(entity.posX + lock.x * (xx - 1), entity.posY + 1,entity.posZ + lock.z * (zz - 1));
									if ((entity.world.getBlockState(pos0).getBlock() != Blocks.AIR 
											&& entity.world.getBlockState(pos0).getBlock() != Blocks.LADDER 
											&& entity.world.getBlockState(pos2).getBlock() != Blocks.LADDER)
											&&(entity.world.getBlockState(pos1).getBlock() != Blocks.AIR 
											&& entity.world.getBlockState(pos1).getBlock() != Blocks.LADDER
											&& entity.world.getBlockState(pos3).getBlock() != Blocks.LADDER)) {
										entity.swingArm(EnumHand.MAIN_HAND);
										entity.cooltime4 = 0;
										int meta =2;
										EnumFacing enumfacing1 = entity.getAdjustedHorizontalFacing();
										if(enumfacing1 == EnumFacing.NORTH)meta = 3;//2
										if(enumfacing1 == EnumFacing.SOUTH)meta = 2;//3
										if(enumfacing1 == EnumFacing.WEST)meta = 5;//4
										if(enumfacing1 == EnumFacing.EAST)meta = 4;//5
										//System.out.println(String.format("%1$3d", meta));
										EnumFacing enumfacing = EnumFacing.getFront(meta);
										if(enumfacing1 != null && enumfacing != null) {
										entity.world.setBlockState(pos2, Blocks.LADDER.getDefaultState().withProperty(FACING, enumfacing), 2);
										entity.world.setBlockState(pos3,Blocks.LADDER.getDefaultState().withProperty(FACING, enumfacing), 2);
										}
									}
								}
								{
									BlockPos pos2 = new BlockPos(entity.posX, entity.posY + entity.height,entity.posZ);
									BlockPos pos11 = new BlockPos(entity.posX + lock.x * xx, entity.posY +0,entity.posZ + lock.z * zz);
									BlockPos pos12 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 1,entity.posZ + lock.z * zz);
									if (entity.world.getBlockState(pos2).getBlock() != Blocks.AIR
											&& (entity.world.getBlockState(pos11).getBlock() != Blocks.LADDER
													&& entity.world.getBlockState(pos12).getBlock() != Blocks.LADDER)){
										{
											++entity.w5barst;
											entity.swingArm(EnumHand.MAIN_HAND);
											BlockPos pos0 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 0,entity.posZ + lock.z * zz);
											BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 1,entity.posZ + lock.z * zz);
											{
				        						IBlockState iblockstate = entity.world.getBlockState(pos1);
				        		                Block block = iblockstate.getBlock();
				        		                if (iblockstate.getMaterial() != Material.AIR
				        		                		&& (entity.w5barst >= block.getBlockHardness(iblockstate, entity.world, pos1) * 100))
				        		                {
				        		                    block.dropBlockAsItemWithChance(entity.world, pos1, entity.world.getBlockState(pos1), 1.0F, 0);
				        		                    entity.world.setBlockToAir(pos1);
				        		                    entity.w5barst = 0;
				        		                }
											}
											{
				        						IBlockState iblockstate = entity.world.getBlockState(pos0);
				        		                Block block = iblockstate.getBlock();
				        		                if (iblockstate.getMaterial() != Material.AIR
				        		                		&& (entity.w5barst >= block.getBlockHardness(iblockstate, entity.world, pos1) * 100))
				        		                {
				        		                    block.dropBlockAsItemWithChance(entity.world, pos0, entity.world.getBlockState(pos0), 1.0F, 0);
				        		                    entity.world.setBlockToAir(pos0);
				        		                    entity.w5barst = 0;
				        		                }
											}
											
										}
									}else {
										entity.w5barst = 0;
									}
								}
								
							}
						}
						
						{
							BlockPos pos0 = new BlockPos(entity.posX, entity.posY + 0,entity.posZ);
							BlockPos pos1 = new BlockPos(entity.posX, entity.posY + 1,entity.posZ);
							if (entity.world.getBlockState(pos0).getBlock() instanceof BlockLadder 
									|| entity.world.getBlockState(pos1).getBlock() instanceof BlockLadder) {
								entity.motionY = 0.2;
							}
							entity.motionX = mox;
							entity.motionZ = moz;
							//entity.motionY = y;
							entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
						}
					}
				}else if(id == 2) {
					double mox = 0;
					double moy = entity.motionY;
					double moz = 0;
					mox -= MathHelper.sin(yaw) * speed * 0.5;
					moz += MathHelper.cos(yaw) * speed * 0.5;
					{
						boolean extime = false;
						for(int xx = 0; xx < 2; ++xx){
							for(int zz = 0; zz < 2; ++zz){
								{
									BlockPos pos0 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 0,entity.posZ + lock.z * zz);
									BlockPos pos1 = new BlockPos(entity.posX + lock.x * xx, entity.posY + 1,entity.posZ + lock.z * zz);
									if ((entity.world.getBlockState(pos0).getBlock() != Blocks.AIR )
											&&(entity.world.getBlockState(pos1).getBlock() != Blocks.AIR)) {
										extime = true;
									}
								}
							}
						}
						if(extime){
							++entity.w5barst;
							entity.swingArm(EnumHand.MAIN_HAND);
							if(entity.w5barst >= 100) {
								if (!entity.world.isRemote) {
				    				GVCExplosionBase.ExplosionKai(entity, entity, entity.posX, entity.posY, entity.posZ, 3.5F, false,  true);
				    				if (!entity.world.isRemote) {
				    					entity.setDead();
				    				}
				    			}
								entity.w5barst = 0;
							}
						}else{
							entity.w5barst = 0;
						}
						{
							BlockPos pos0 = new BlockPos(entity.posX, entity.posY + 0,entity.posZ);
							if (entity.world.getBlockState(pos0).getBlock() instanceof BlockLadder) {
								entity.motionY = 0.2;
							}
							entity.motionX = mox;
							entity.motionZ = moz;
							//entity.motionY = y;
							entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
						}
					}
				}
				
			}
			/*{
				//this.motionY = 0.2D;
				double ix = 0;
				double iz = 0;
				double iy = entityx.motionY;
				float f12 = entityx.rotationYaw * (2 * (float) Math.PI / 360);
				float f22 = entityx.rotationPitch * (2 * (float) Math.PI / 360);

				double d5 = x - entityx.posX;
				double d7 = z - entityx.posZ;
				entityx.rotationYaw = entityx.renderYawOffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				
				float off = 0;
				
				double mox = 0;
				double moz = 0;
				
				ix -= MathHelper.sin(f12) * 1;
				iz += MathHelper.cos(f12) * 1;
				if (entityx.collidedHorizontally) {
					off = 1.34F;
					if(time > 100){
						if(entityx.world.getBlockState(new BlockPos(entityx.posX, entityx.posY + entityx.height + 1, entityx.posZ)).getBlock() == Blocks.AIR){
							entityx.world.setBlockState(new BlockPos(entityx.posX, entityx.posY + 0, entityx.posZ), Blocks.PLANKS.getDefaultState(),2);
							entityx.motionY = 0.2;
						}else if((entityx.world.getBlockState(new BlockPos(entityx.posX + ix, entityx.posY + 1, entityx.posZ + iz)).getBlock() != Blocks.BEDROCK
								&& entityx.world.getBlockState(new BlockPos(entityx.posX + ix, entityx.posY + 2, entityx.posZ + iz)).getBlock() != Blocks.BEDROCK
								)
								&&(entityx.world.getBlockState(new BlockPos(entityx.posX + ix, entityx.posY + 1, entityx.posZ + iz)).getBlock() != Blocks.OBSIDIAN
								&& entityx.world.getBlockState(new BlockPos(entityx.posX + ix, entityx.posY + 2, entityx.posZ + iz)).getBlock() != Blocks.OBSIDIAN
								)){
							if(!entityx.world.isRemote){
								entityx.world.setBlockToAir(new BlockPos(entityx.posX + ix, entityx.posY + 1, entityx.posZ + iz));
								entityx.world.setBlockToAir(new BlockPos(entityx.posX + ix, entityx.posY + 2, entityx.posZ + iz));
								entityx.world.setBlockToAir(new BlockPos(entityx.posX, entityx.posY + 2, entityx.posZ));
							}
						}
						entityx.motionY = 0.5;
					}
				}
				if(entityx.world.getBlockState(new BlockPos(entityx.posX, entityx.posY - 1, entityx.posZ)).getBlock() == Blocks.AIR
						&& entityx.world.getBlockState(new BlockPos(entityx.posX, entityx.posY - 2, entityx.posZ)).getBlock() == Blocks.AIR
						&& entityx.posY <= y){
					speed = speed * - 1.2;
				//	entityx.motionY = 1.2;
					off = 1.34F;
				}
				
				entityx.stepHeight = entityx.height / 1.5F;
				if(entityx.onGround){
					entityx.motionX -= MathHelper.sin(f12 + off) * speed;
					entityx.motionZ += MathHelper.cos(f12 + off) * speed;
				}else{
					entityx.motionX-= MathHelper.sin(f12 + off) * speed * 0.5;
					entityx.motionZ += MathHelper.cos(f12 + off) * speed * 0.5;
				}
				entityx.move(MoverType.SELF,entityx.motionX, entityx.motionY, entityx.motionZ);
			}*/
			
		} // 1
	}
	
	public static boolean getBlock(Block block) {
		boolean flag = false;
		if(block == Blocks.WATER
				|| block == Blocks.FLOWING_WATER
				|| block == Blocks.LAVA
				|| block == Blocks.FLOWING_LAVA
				|| block == Blocks.AIR) {
			flag = true;
		}
		return flag;
		
	}
	public static boolean getBlockfalse(Block block) {
		boolean flag = false;
		if(block != Blocks.WATER
				&& block != Blocks.FLOWING_WATER
				&& block != Blocks.LAVA
				&& block != Blocks.FLOWING_LAVA
				&& block != Blocks.AIR) {
			flag = true;
		}
		return flag;
		
	}
	
	public static void newmove_turret(EntityGVCLivingBase entity, int id, float sp, float turnspeed, double max, double range1, double range2) {
		int fps = mod_GVCLib.proxy.getFPS();
		if(fps < 15)return;
		Chunk chunk = entity.world.getChunkFromBlockCoords(new BlockPos(entity.posX, entity.posY, entity.posZ));
		if (chunk.isEmpty())return;
		entity.sneak = false;
		boolean ta = false;
		double range = range1;
		if(entity.getMobMode() == 1){
			range = range2;
		}
		//entity.motionY *= 0.98;
		if (entity.isPotionActive(MobEffects.SLOWNESS))
        {
			sp = sp * 0.5F;
        }
		if (entity.isPotionActive(MobEffects.BLINDNESS))
        {
			sp = sp * 0.1F;
        }
		
		if(entity.getMoveT() == 1){
			{
				MoveT(entity, sp * 0.8, 1, entity.getMoveX(), entity.getMoveY(), entity.getMoveZ(), id);
			}
			if(entity.posX == entity.getMoveX() && entity.posY == entity.getMoveY() && entity.posZ == entity.getMoveZ()) {
				entity.setMoveT(0);
			}
			{
				List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
						entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ)
								.grow(range));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith() && entity1 != null && entity.getHealth() > 0.0F) {
							boolean flag = entity.getEntitySenses().canSee(entity1);
							if (entity.CanAttack(entity1) && entity1 != null) {
								if (entity.targetentity == entity1) {
									{
										double d51 = entity1.posX - entity.posX;
										double d71 = entity1.posZ - entity.posZ;
										double d61 = entity1.posY - entity.posY;
										double d11 = entity.posY - (entity1.posY);
										double d31 = (double) MathHelper.sqrt(d51 * d51 + d71 * d71);
										float f111 = (float) (-(Math.atan2(d11, d31) * 180.0D / Math.PI));
										entity.rotationYawHead
												= -((float) Math.atan2(d51, d71)) * 180.0F / (float) Math.PI;
										entity.rotationp = entity.prevRotationPitch = entity.rotationp = entity.rotationPitch = -f111
												+ 10;
										if (flag) {
											entity.targetentity = (EntityLivingBase) entity1;
										}
										ta = true;
									}
									break;
								} else if (entity.targetentity == null) {
									if (entity.CanAttack(entity1) && flag) {
										entity.targetentity = (EntityLivingBase) entity1;
										ta = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			if(entity.getAIType() == 1 && id == 0) {
				ta = true;
			}
		}
		else
		{
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
					.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (entity1 != null && entity1 instanceof EntityVehicleBase && flag) {
							EntityVehicleBase ve = (EntityVehicleBase) entity1;
							//if (ve.getControllingPassenger() == null)
							if(ve.getPassengers().size() < ve.riddng_maximum && ve.getHealth() > 0.0F && !ve.getVehicleLock())
							{
								MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
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
									entity.rotationYawHead
											= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									entity.rotationp = entity.rotationPitch = -f11 + 0;
									double ddx = Math.abs(d5);
									double ddz = Math.abs(d7);
									if ((ddx > max || ddz > max)) {
										MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
									}
									entity.noflag = 0;
									ta = true;
								} else {
									++entity.noflag;
									if(entity.noflag > 100) {
										{
											MoveS(entity, sp, 1, entity1.posX, entity1.posY, entity1.posZ, id);
										}
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
				}
			}
		}
		if(!entity.getattacktask()){
			stay(entity, id);
		}
		
		{
			if(entity.rotationYaw > 180F){
				entity.rotationYaw = -179F;
			}
			if(entity.rotationYaw < -180F){
				entity.rotationYaw = 179F;
			}
			if(entity.rotationYawHead > 360F || entity.rotationYawHead < -360F){
				entity.rotationYawHead = 0;
			}
			if(entity.rotationYawHead > 180F){
				entity.rotationYawHead = -179F;
			}
			if(entity.rotationYawHead < -180F){
				entity.rotationYawHead = 179F;
			}
			float f3 = (float) (entity.rotationYawHead - entity.rotationYaw);
			if (entity.rotationYawHead != entity.rotationYaw) {
				float yaw = entity.rotationYaw * (2 * (float) Math.PI / 360);
				double mox = 0;
				double moy = entity.motionY;
				double moz = 0;
				mox -= MathHelper.sin(yaw) * sp * 0.1;
				moz += MathHelper.cos(yaw) * sp * 0.1;
				if (f3 <= turnspeed && f3 >= -turnspeed) {

				} else if (f3 > 1) {
					if (f3 > 180F) {
						entity.rotationYaw = entity.rotationYaw - turnspeed;
					} else {
						entity.rotationYaw = entity.rotationYaw + turnspeed;
					}
					entity.motionX = mox;
					entity.motionZ = moz;
					entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
				} else if (f3 < -1) {
					if (f3 < -180F) {
						entity.rotationYaw = entity.rotationYaw + turnspeed;
					} else {
						entity.rotationYaw = entity.rotationYaw - turnspeed;
					}
					entity.motionX = mox;
					entity.motionZ = moz;
					entity.move(MoverType.PLAYER, entity.motionX, entity.motionY, entity.motionZ);
				}
			}
		}
	}
	
}

