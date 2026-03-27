package gvclib.entity.living.cnt.ai;

import java.util.List;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.PL_RoteModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AI_EntityMove_AirCraft {
	public static void movefighter(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed,
			double max, double range, int minheight) {
		Vec3d look = entity.getLookVec();
		boolean can_flight = false;
		float rop = 0;
		VehicleAI_RotationYawOffset.offset(entity, ridding);
		
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY() + minheight;
		if (entity.posY > genY - 5) {
			can_flight = true;
		}
		if(!can_flight && entity.thpower > 10) {
			if (entity.rotationPitch_gvc > -30) {
				entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*2;
			}
			entity.setAIType(3);
			entity.aitypetime = 0;
		}
		{
			if (entity.throttle < entity.thmax) {
				++entity.throttle;
			}
			if (entity.thpower < entity.thmax) {
				entity.thpower = entity.thpower + entity.thmaxa;
			}
		}
		if (ridding.getMoveT() == 1) {//自由行动
			double d5 = ridding.getMoveX() - entity.posX;
			double d7 = ridding.getMoveZ() - entity.posZ;
			double d6 = ridding.getMoveY() - entity.posY;
			double d1 = entity.posY - (ridding.getMoveY());
			double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
			float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

			double ddx = Math.abs(d5);
			double ddz = Math.abs(d7);
			entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
			rop = -f11;
			if (entity.rote > 180F) {
				entity.rote = -179F;
			}
			if (entity.rote < -180F) {
				entity.rote = 179F;
			}
			float f3 = (float) (entity.rotationYawHead - entity.rote);
			if (entity.getAIType() != 3) {
				if (entity.rotationYawHead != entity.rote) {
					if (f3 > turnspeed) {
						if (f3 > 180F) {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						}
					} else if (f3 < -turnspeed) {
						if (f3 < -180F) {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						}
					}
				}
			}
			ridding.target = true;
		} else {
//////////////////////////////////////////////////////////////////////////
			Entity tagetentity = null;
			Entity[] targetlist = new Entity[1024];
			int targetplus = 0;
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
					.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (ridding.CanAttack(entity1) && entity1 != null) {
							if(ridding.targetentity == entity1 && ((EntityLivingBase) entity1).getHealth() > 0.0F)
							{
								tagetentity = entity1;
								if (flag) {
									ridding.targetentity = (EntityLivingBase) entity1;
								}
								ridding.target = true;
								break;
							}else {
								if(ridding.targetentity == null)
								{
									if (ridding.CanAttack(entity1) && flag) 
									{
										targetlist[targetplus] = entity1;
										++targetplus;
										ridding.targetentity = (EntityLivingBase) entity1;
										ridding.target = true;
										//break;
									}
									ridding.target = false;
								}
								//break;
							}
						}
					}
				}
			}
			if(tagetentity == null) {
				for(int xs = 0; xs < targetlist.length; ++xs) {
					if(targetlist[xs] != null) {
							if(ridding.targetentity != null) {
								if(!targetlist[xs].onGround) {
									ridding.targetentity = (EntityLivingBase)  targetlist[xs];
									ridding.target = true;
								}
							}else {
								ridding.targetentity = (EntityLivingBase)  targetlist[xs];
								ridding.target = true;
							}
					}
				}
			}
			
//////////////////////////////////////////////////////////////////////////
			float f11 = 0;
			if(tagetentity != null) {
				double d5 = tagetentity.posX - entity.posX;
				double d7 = tagetentity.posZ - entity.posZ;
				double d6 = tagetentity.posY - entity.posY;
				double d1 = entity.posY - (tagetentity.posY);
				double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
				f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
						/ (float) Math.PI;

				double ddx = Math.abs(d5);
				double ddz = Math.abs(d7);
				entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				rop = -f11;
				if(entity.rote > 360 || entity.rote < -360) {
					entity.rote = entity.rote %360;
				}
				if (entity.rote > 180F) {
					entity.rote = -179F;
				}
				if (entity.rote < -180F) {
					entity.rote = 179F;
				}
				float f3 = (float) (entity.rotationYawHead - entity.rote);
				//if(!entity.world.isRemote)
				if (entity.getAIType() != 3 && can_flight) {
					if (entity.rotationYawHead != entity.rote) {
						if (f3 > turnspeed) {
							if (f3 > 180F) {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							}
						} else if (f3 < -turnspeed) {
							if (f3 < -180F) {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							}
						}
					}
				}
				
				
			}
			float rotep_offset = -f11 + 10;
			entity.rotep = rotep_offset;
			if(can_flight) {
				if(entity.posY < bp.getY() + minheight + 10) {
					if (entity.getAIType() == 3) {
						if (entity.rotationPitch_gvc > -30) {
							entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*2;
						}
					}else {
						if (entity.rotationPitch != entity.rotep) {
							float f3 = (float) (entity.rotationPitch - entity.rotep);
							if (entity.rotationPitch_gvc < entity.rotep - turnspeed*3) {
								entity.rotationPitch_gvc = entity.rotationPitch_gvc + turnspeed*1;
							} else if (entity.rotationPitch_gvc >= entity.rotep + turnspeed*3){
								entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*1;
							}
						}
					}
				}
				else 
				{
					if (entity.rotationPitch != entity.rotep) {
						float f3 = (float) (entity.rotationPitch - entity.rotep);
						if (entity.rotationPitch_gvc < entity.rotep - turnspeed*3) {
							entity.rotationPitch_gvc = entity.rotationPitch_gvc + turnspeed*1;
						} else if (entity.rotationPitch_gvc >= entity.rotep + turnspeed*3){
							entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*1;
						}
					}
				}
			}

			if (!ridding.target && entity.getHealth() > 0.0F) {
				movestay(entity, ridding, turnspeed, turnspeed, turnspeed, max, range);
			}
			
		}
		
		
		entity.rotationp = entity.rotationPitch_gvc;
		entity.rotationPitch = entity.rotationPitch_gvc;
		entity.prevRotationPitch = entity.rotationPitch_gvc;
		
		if (entity != null) {
			
		}

		//if( entity.throttle >= 1)
		{
			if (entity.thpera < 360) {
				entity.thpera = entity.thpera + (entity.throttle * 2);
			} else {
				entity.thpera = 0;
			}
		}
	}
	
	public static void movebomber(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed,
			double max, double range, int minheight) {
		Vec3d look = entity.getLookVec();
		boolean can_flight = false;
		float rop = 0;
		VehicleAI_RotationYawOffset.offset(entity, ridding);
		
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY() + minheight;
		if (entity.posY > genY - 5) {
			can_flight = true;
		}
		if(!can_flight && entity.thpower > 15) {
			if (entity.rotationPitch_gvc > -30) {
				entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*2;
			}
			entity.setAIType(3);
			entity.aitypetime = 0;
		}
		{
			if (entity.throttle < entity.thmax) {
				++entity.throttle;
			}
			if (entity.thpower < entity.thmax) {
				entity.thpower = entity.thpower + entity.thmaxa;
			}
		}
		if (ridding.getMoveT() == 1) {//自由行动
			double d5 = ridding.getMoveX() - entity.posX;
			double d7 = ridding.getMoveZ() - entity.posZ;
			double d6 = ridding.getMoveY() - entity.posY;
			double d1 = entity.posY - (ridding.getMoveY());
			double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
			float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

			double ddx = Math.abs(d5);
			double ddz = Math.abs(d7);
			entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
			rop = -f11;
			if (entity.rote > 180F) {
				entity.rote = -179F;
			}
			if (entity.rote < -180F) {
				entity.rote = 179F;
			}
			float f3 = (float) (entity.rotationYawHead - entity.rote);
			//if(!entity.world.isRemote)
			if (entity.getAIType() != 3) {
				if (entity.rotationYawHead != entity.rote) {
					if (f3 > turnspeed) {
						if (f3 > 180F) {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						}
					} else if (f3 < -turnspeed) {
						if (f3 < -180F) {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						}
					}
				}
			}
			ridding.target = true;
		} 
		else {
//////////////////////////////////////////////////////////////////////////
			
			
			
			Entity tagetentity = null;
			Entity[] targetlist = new Entity[1024];
			int targetplus = 0;
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity
					.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (ridding.CanAttack(entity1) && entity1 != null) {
							if(ridding.targetentity == entity1 && ((EntityLivingBase) entity1).getHealth() > 0.0F)
							{
								tagetentity = entity1;
								if (flag) {
									ridding.targetentity = (EntityLivingBase) entity1;
								}
								ridding.target = true;
								break;
							}else {
								if(ridding.targetentity == null)
								{
									if (ridding.CanAttack(entity1) && flag) 
									{
										targetlist[targetplus] = entity1;
										++targetplus;
										ridding.targetentity = (EntityLivingBase) entity1;
										ridding.target = true;
										//break;
									}
									ridding.target = false;
								}
								//break;
							}
						}
					}
				}
			}
			if(tagetentity == null) {
				for(int xs = 0; xs < targetlist.length; ++xs) {
					if(targetlist[xs] != null) {
							if(ridding.targetentity != null) {
								if(!targetlist[xs].onGround) {
									ridding.targetentity = (EntityLivingBase)  targetlist[xs];
									ridding.target = true;
								}
							}else {
								ridding.targetentity = (EntityLivingBase)  targetlist[xs];
								ridding.target = true;
							}
					}
				}
			}
			
//////////////////////////////////////////////////////////////////////////
			float f11 = 0;
			if(tagetentity != null) {
				double d5 = tagetentity.posX - entity.posX;
				double d7 = tagetentity.posZ - entity.posZ;
				double d6 = tagetentity.posY - entity.posY;
				double d1 = entity.posY - (tagetentity.posY);
				double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
				f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
				entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
						/ (float) Math.PI;

				double ddx = Math.abs(d5);
				double ddz = Math.abs(d7);
				entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
				rop = -f11;
				if(entity.rote > 360 || entity.rote < -360) {
					entity.rote = entity.rote %360;
				}
				if (entity.rote > 180F) {
					entity.rote = -179F;
				}
				if (entity.rote < -180F) {
					entity.rote = 179F;
				}
				float f3 = (float) (entity.rotationYawHead - entity.rote);
				//if(!entity.world.isRemote)
				if (entity.getAIType() != 3 && can_flight) {
					if (entity.rotationYawHead != entity.rote) {
						if (f3 > turnspeed) {
							if (f3 > 180F) {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							}
						} else if (f3 < -turnspeed) {
							if (f3 < -180F) {
								PL_RoteModel.rotemodel(entity, -turnspeed);
								if (entity.throte > -50) {
									entity.throte = entity.throte - 2;
								}
							} else {
								PL_RoteModel.rotemodel(entity, +turnspeed);
								if (entity.throte < 50) {
									entity.throte = entity.throte + 2;
								}
							}
						}
					}
				}
				
				
			}
			float rotep_offset = -f11 + 10;
			entity.rotep = rotep_offset;
			if(can_flight) {
				if(entity.posY < bp.getY() + minheight) {
					if (entity.getAIType() == 3) {
						if (entity.rotationPitch_gvc > -30) {
							entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*2;
						}
					}else {
						if (entity.rotationPitch != 0) {
							if (entity.rotationPitch_gvc < 0 - turnspeed*3) {
								entity.rotationPitch_gvc = entity.rotationPitch_gvc + turnspeed*1;
							} else if (entity.rotationPitch_gvc >= 0 + turnspeed*3){
								entity.rotationPitch_gvc = entity.rotationPitch_gvc - turnspeed*1;
							}
						}
					}
				}
				else 
				{
					entity.rotationPitch_gvc = 0;
					
				}
				
			}
			
			
			
			if (!ridding.target && entity.getHealth() > 0.0F) {
				movestay(entity, ridding, turnspeed, turnspeed, turnspeed, max, range);
			}
			
		}
		
		
		entity.rotationp = entity.rotationPitch_gvc;
		entity.rotationPitch = entity.rotationPitch_gvc;
		entity.prevRotationPitch = entity.rotationPitch_gvc;
		
		if (entity != null) {
			
		}

		//if( entity.throttle >= 1)
		{
			if (entity.thpera < 360) {
				entity.thpera = entity.thpera + (entity.throttle * 2);
			} else {
				entity.thpera = 0;
			}
		}
	}
	
	
	
	
	
	public static void movestay(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed, double max, double range) {
		Vec3d look = entity.getLookVec();
		{
			BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
			int genY = bp.getY();

			if (entity.posY < genY + 15 + (entity.getAIType2() * 4)) {
				entity.rotationp = entity.rotationPitch = entity.prevRotationPitch = -4;
				ridding.rotationp = ridding.rotationPitch = ridding.prevRotationPitch = -4;
			} else if (entity.posY > genY + 15 + (entity.getAIType2() * 4)) {
				entity.rotationp = entity.rotationPitch = entity.prevRotationPitch = 4;
				ridding.rotationp = ridding.rotationPitch = ridding.prevRotationPitch = 4;
			} else {
				entity.rotationp = entity.rotationPitch = entity.prevRotationPitch = -0;
				ridding.rotationp = ridding.rotationPitch = ridding.prevRotationPitch = -0;
			}
			//entity.setVelocity(look.x * sp, MathHelper.sin(-entity.rotationp * 0.017453292F), look.z * sp);
		}

		if (entity.getMoveX() != 0) {
			double d5 = entity.getMoveX() - entity.posX;
			double d7 = entity.getMoveZ() - entity.posZ;

			entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

			double ddx = Math.abs(d5);
			double ddz = Math.abs(d7);
			entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
			if (entity.rote > 180F) {
				entity.rote = -179F;
			}
			if (entity.rote < -180F) {
				entity.rote = 179F;
			}
			float f3 = (float) (entity.rotationYawHead - entity.rote);

			{
				if (entity.rotationYawHead != entity.rote) {
					if (f3 > turnspeed) {
						if (f3 > 180F) {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						}
					} else if (f3 < -turnspeed) {
						if (f3 < -180F) {
							PL_RoteModel.rotemodel(entity, -turnspeed);
							if (entity.throte > -50) {
								entity.throte = entity.throte - 2;
							}
						} else {
							PL_RoteModel.rotemodel(entity, +turnspeed);
							if (entity.throte < 50) {
								entity.throte = entity.throte + 2;
							}
						}
					}
				}
			}
		} else {
			if (!entity.world.isRemote) {
				entity.rotationYawHead = entity.rotationYawHead + 1;
				entity.rotationYaw = entity.rotationYaw + 1;
				entity.prevRotationYaw = entity.prevRotationYaw + 1;
				entity.prevRotationYawHead = entity.prevRotationYawHead + 1;
			}
		}

		if (entity.throttle < entity.thmax * 0.25) {
			++entity.throttle;
			//entity.thpower = entity.thpower + 0.6D;
			entity.thpower = 1;
		}

	}
}
