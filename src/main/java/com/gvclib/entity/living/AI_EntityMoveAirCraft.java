package gvclib.entity.living;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class AI_EntityMoveAirCraft {
	public static void moveheli(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed, double max, double range, int gy) {
		entity.target = false;
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY() + gy;
		if (entity.posY <= genY) {
			if (entity.throttle < entity.thmax) {
				++entity.throttle;
			}
			entity.thpower = entity.thpower + entity.thmaxa;
		}
		if (entity.posY > genY) {
			if (entity.thpower > entity.thmax / 2) {
				entity.thpower = entity.thpower + entity.thmina;
			}
			if (entity.throttle > entity.thmax / 2) {
				--entity.throttle;
			}
		}
		if (ridding.rotationYawHead > 360F || ridding.rotationYawHead < -360F) {
			ridding.rotationYawHead = 0;
			ridding.rotationYaw = 0;
			ridding.prevRotationYaw = 0;
			ridding.prevRotationYawHead = 0;
			ridding.renderYawOffset = 0;
		}
		if (ridding.rotationYawHead > 180F) {
			ridding.rotationYawHead = -179F;
			ridding.rotationYaw = -179F;
			ridding.prevRotationYaw = -179F;
			ridding.prevRotationYawHead = -179F;
			ridding.renderYawOffset = -179F;
		}
		if (ridding.rotationYawHead < -180F) {
			ridding.rotationYawHead = 179F;
			ridding.rotationYaw = 179F;
			ridding.prevRotationYaw = 179F;
			ridding.prevRotationYawHead = 179F;
			ridding.renderYawOffset = 179F;
		}
		if (entity.rotationYawHead > 360F || entity.rotationYawHead < -360F) {
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			entity.prevRotationYaw = 0;
			entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if (entity.rotationYawHead > 180F) {
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			entity.prevRotationYaw = -179F;
			entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if (entity.rotationYawHead < -180F) {
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			entity.prevRotationYaw = 179F;
			entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
		/*if( entity.throttle >= 0){
			if(entity.thpower < entity.throttle){
				if( entity.throttle > entity.thmax-5){
					entity.thpower = entity.thpower + entity.thmaxa;
				}else{
					entity.thpower = entity.thpower + entity.thmaxa*0.5;
				}
			}else{
				entity.thpower = entity.thpower + entity.thmina;
			}
			if(entity.throttle <= 0 && entity.throttle > 0){
				entity.thpower = entity.thpower + entity.thmina * 2;
			}
		}*/
		if (ridding.getMoveT() == 1) {
			double d5 = ridding.getMoveX() - entity.posX;
			double d7 = ridding.getMoveZ() - entity.posZ;
			double d6 = ridding.getMoveY() - entity.posY;
			double d1 = entity.posY - (ridding.getMoveY());
			double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
			float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

			if (entity.rote > 180F) {
				entity.rote = -179F;
			}
			if (entity.rote < -180F) {
				entity.rote = 179F;
			}
			if (entity.getAIType3() != 1) {
				if (entity.rotationYawHead != entity.rote) {
					if (entity.rotationYawHead < entity.rote) {
						entity.rotationYawHead = entity.rotationYawHead + turnspeed;
						entity.rotationYaw = entity.rotationYaw + turnspeed;
						entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
						entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
					} else if (entity.rotationYawHead > entity.rote) {
						entity.rotationYawHead = entity.rotationYawHead - turnspeed;
						entity.rotationYaw = entity.rotationYaw - turnspeed;
						entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
						entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
					}
				}
			}

			double ddx = Math.abs(d5);
			double ddz = Math.abs(d7);
			if ((ddx > max || ddz > max)) {
				if (entity.getAIType3() != 1) {
					ridding.rotationPitch = entity.rotationPitch = entity.rotationp = -f11 + 10;
					entity.setAIType3(0);
				}
			} else {
				if (entity.getAIType3() == 0) {
					entity.setAIType3(1);
					entity.aitypetime3 = 0;
				}
			}
		} else {
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (ridding.targetentity == entity1 && ridding.CanAttack(entity1)) {
							double d5 = entity1.posX - entity.posX;
							double d7 = entity1.posZ - entity.posZ;
							double d6 = entity1.posY - entity.posY;
							double d1 = entity.posY - (entity1.posY);
							double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
							float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
							entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

							if (entity.rote > 180F) {
								entity.rote = -179F;
							}
							if (entity.rote < -180F) {
								entity.rote = 179F;
							}
							if (entity.getAIType3() != 1) {
								if (entity.rotationYawHead != entity.rote) {
									if (entity.rotationYawHead < entity.rote) {
										entity.rotationYawHead = entity.rotationYawHead + turnspeed;
										entity.rotationYaw = entity.rotationYaw + turnspeed;
										entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
										entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
									} else if (entity.rotationYawHead > entity.rote) {
										entity.rotationYawHead = entity.rotationYawHead - turnspeed;
										entity.rotationYaw = entity.rotationYaw - turnspeed;
										entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
										entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
									}
								}
							}

							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
							if ((ddx > max || ddz > max)) {
								if (entity.getAIType3() != 1) {
									ridding.rotationPitch = entity.rotationPitch = entity.rotationp = -f11 + 10;
									entity.setAIType3(0);
								}
							} else {
								if (entity.getAIType3() == 0) {
									entity.setAIType3(1);
									entity.aitypetime3 = 0;
								}
							}

							if (flag) {
								ridding.targetentity = (EntityLivingBase) entity1;
							}
							ridding.target = true;
							break;
						} else
						//if(ridding.targetentity == null)
						if (ridding.CanAttack(entity1) && flag) {
							//if (ridding.CanAttack(entity1) && flag) 
							{
								ridding.targetentity = (EntityLivingBase) entity1;
								ridding.target = true;
								break;
							}
						}
					}
				}
			}
			if (!ridding.target && entity.getHealth() > 0.0F) {
				if (entity.getMoveT() == 2) {
					ridding.rotationPitch = entity.rotationPitch = entity.rotationp = 10;
					double d5 = entity.getMoveX() - entity.posX;
					double d7 = entity.getMoveZ() - entity.posZ;

					entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					if (entity.rote > 180F) {
						entity.rote = -179F;
					}
					if (entity.rote < -180F) {
						entity.rote = 179F;
					}
					if (entity.getAIType3() != 1) {
						if (entity.rotationYawHead != entity.rote) {
							if (entity.rotationYawHead < entity.rote) {
								entity.rotationYawHead = entity.rotationYawHead + turnspeed;
								entity.rotationYaw = entity.rotationYaw + turnspeed;
								entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
								entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
							} else if (entity.rotationYawHead > entity.rote) {
								entity.rotationYawHead = entity.rotationYawHead - turnspeed;
								entity.rotationYaw = entity.rotationYaw - turnspeed;
								entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
								entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
							}
						}
					}

					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if ((ddx > max || ddz > max)) {
						if (entity.getAIType3() != 1) {
							entity.setAIType3(0);
						}
					} else {
						if (entity.getAIType3() == 0) {
							entity.setAIType3(1);
							entity.aitypetime3 = 0;
						}
					}
				} else {
					if (!entity.world.isRemote) {
						entity.rotationYawHead = entity.rotationYawHead + 1;
						entity.rotationYaw = entity.rotationYaw + 1;
						entity.prevRotationYaw = entity.prevRotationYaw + 1;
						entity.prevRotationYawHead = entity.prevRotationYawHead + 1;
					}
					ridding.rotationPitch = entity.rotationPitch = entity.rotationp = 10;
				}
			}
		}
		if (entity.getAIType3() == 1) {
			ridding.rotationPitch = entity.rotationPitch = entity.rotationp = 40;
			if (entity.aitypetime3 > 80) {
				entity.setAIType3(0);
				entity.aitypetime3 = 0;
			}
		}
	}

	public static void movefighter(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed,
			double max, double range, int minheight) {
		Vec3d look = entity.getLookVec();
		//entity.rotationp = entity.rotationPitch= entity.prevRotationPitch = -30;
		//ridding.rotationp = ridding.rotationPitch= ridding.prevRotationPitch = -30;

		float rop = 0;
		if (ridding.rotationYawHead > 360F || ridding.rotationYawHead < -360F) {
			ridding.rotationYawHead = 0;
			ridding.rotationYaw = 0;
			ridding.prevRotationYaw = 0;
			ridding.prevRotationYawHead = 0;
			ridding.renderYawOffset = 0;
		}
		if (ridding.rotationYawHead > 180F) {
			ridding.rotationYawHead = -179F;
			ridding.rotationYaw = -179F;
			ridding.prevRotationYaw = -179F;
			ridding.prevRotationYawHead = -179F;
			ridding.renderYawOffset = -179F;
		}
		if (ridding.rotationYawHead < -180F) {
			ridding.rotationYawHead = 179F;
			ridding.rotationYaw = 179F;
			ridding.prevRotationYaw = 179F;
			ridding.prevRotationYawHead = 179F;
			ridding.renderYawOffset = 179F;
		}
		if (entity.rotationYawHead > 360F || entity.rotationYawHead < -360F) {
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			entity.prevRotationYaw = 0;
			entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if (entity.rotationYawHead > 180F) {
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			entity.prevRotationYaw = -179F;
			entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if (entity.rotationYawHead < -180F) {
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			entity.prevRotationYaw = 179F;
			entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
		if (ridding.getMoveT() == 1) {
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
		} else {
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
								double d5 = entity1.posX - entity.posX;
								double d7 = entity1.posZ - entity.posZ;
								double d6 = entity1.posY - entity.posY;
								double d1 = entity.posY - (entity1.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F
										/ (float) Math.PI;

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
								if (flag) {
									ridding.targetentity = (EntityLivingBase) entity1;
								}
								ridding.target = true;
								//System.out.println(String.format("0"));
								break;
							}else {
								if(ridding.targetentity == null)
								{
									if (ridding.CanAttack(entity1) && flag) 
									{
										ridding.targetentity = (EntityLivingBase) entity1;
										//System.out.println(String.format("1"));
										break;
									}
									ridding.target = false;
								}
								break;
							}
							
						} else {
							ridding.target = false;
						}
					}
				}
			}
			if (!ridding.target && entity.getHealth() > 0.0F) {
				movestay(entity, ridding, turnspeed, turnspeed, turnspeed, max, range);
			}
		}
		if (entity != null) {
			BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
			int genY = bp.getY() + minheight;
			if (entity.posY < genY) {
				entity.rotationp = entity.rotationPitch = entity.prevRotationPitch = entity.rotationPitch - 1;
				ridding.rotationp = ridding.rotationPitch = ridding.prevRotationPitch = entity.rotationPitch - 1;
			} else {

				entity.rotationp = entity.rotationPitch = entity.prevRotationPitch = rop;
				ridding.rotationp = ridding.rotationPitch = ridding.prevRotationPitch = rop;
			}
			//entity.setVelocity(look.x * sp*20, MathHelper.sin(-entity.rotationp * 0.017453292F), look.z * sp*20);
			{
				entity.motionX = look.x * sp * 20;
				entity.motionY = MathHelper.sin(-entity.rotationp * 0.017453292F);
				entity.motionZ = look.z * sp * 20;
			}
			if (entity.throttle < entity.thmax * 0.5) {
				++entity.throttle;
				//entity.thpower = entity.thpower + 0.6D;
				entity.thpower = entity.thmax * 0.5;
			}
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
		/*if(!entity.world.isRemote){
			entity.rotationYawHead = entity.rotationYawHead + 1;
		entity.rotationYaw = entity.rotationYaw + 1;
		entity.prevRotationYaw = entity.prevRotationYaw + 1;
		entity.prevRotationYawHead = entity.prevRotationYawHead + 1;
		}*/
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

	
	
	
	public static void moveheli_new(EntityGVCLivingBase entity, EntityGVCLivingBase ridding, float f1, float sp,
			float turnspeed, double max, double range, int gy) {
		entity.target = false;
		BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
		int genY = bp.getY() + gy;
		if (entity.posY <= genY) {
			if (entity.throttle < entity.thmax) {
				++entity.throttle;
			}
			entity.thpower = entity.thpower + entity.thmaxa;
			entity.throttle_up = 1;
		}
		if (entity.posY > genY) {
			if (entity.thpower > entity.thmax / 2) {
				entity.thpower = entity.thpower + entity.thmina;
			}
			if (entity.throttle > entity.thmax / 2) {
				--entity.throttle;
			}
			entity.throttle_up = -1;
		}
		if (ridding.rotationYawHead > 360F || ridding.rotationYawHead < -360F) {
			ridding.rotationYawHead = 0;
			ridding.rotationYaw = 0;
			ridding.prevRotationYaw = 0;
			ridding.prevRotationYawHead = 0;
			ridding.renderYawOffset = 0;
		}
		if (ridding.rotationYawHead > 180F) {
			ridding.rotationYawHead = -179F;
			ridding.rotationYaw = -179F;
			ridding.prevRotationYaw = -179F;
			ridding.prevRotationYawHead = -179F;
			ridding.renderYawOffset = -179F;
		}
		if (ridding.rotationYawHead < -180F) {
			ridding.rotationYawHead = 179F;
			ridding.rotationYaw = 179F;
			ridding.prevRotationYaw = 179F;
			ridding.prevRotationYawHead = 179F;
			ridding.renderYawOffset = 179F;
		}
		if (entity.rotationYawHead > 360F || entity.rotationYawHead < -360F) {
			entity.rotationYawHead = 0;
			entity.rotationYaw = 0;
			entity.prevRotationYaw = 0;
			entity.prevRotationYawHead = 0;
			entity.renderYawOffset = 0;
		}
		if (entity.rotationYawHead > 180F) {
			entity.rotationYawHead = -179F;
			entity.rotationYaw = -179F;
			entity.prevRotationYaw = -179F;
			entity.prevRotationYawHead = -179F;
			entity.renderYawOffset = -179F;
		}
		if (entity.rotationYawHead < -180F) {
			entity.rotationYawHead = 179F;
			entity.rotationYaw = 179F;
			entity.prevRotationYaw = 179F;
			entity.prevRotationYawHead = 179F;
			entity.renderYawOffset = 179F;
		}
		if (ridding.getMoveT() == 1) {
			double d5 = ridding.getMoveX() - entity.posX;
			double d7 = ridding.getMoveZ() - entity.posZ;
			double d6 = ridding.getMoveY() - entity.posY;
			double d1 = entity.posY - (ridding.getMoveY());
			double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
			float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
			entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

			if (entity.rote > 180F) {
				entity.rote = -179F;
			}
			if (entity.rote < -180F) {
				entity.rote = 179F;
			}
			if (entity.getAIType3() != 1) {
				if (entity.rotationYawHead != entity.rote) {
					if (entity.rotationYawHead < entity.rote) {
						entity.rotationYawHead = entity.rotationYawHead + turnspeed;
						entity.rotationYaw = entity.rotationYaw + turnspeed;
						entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
						entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
					} else if (entity.rotationYawHead > entity.rote) {
						entity.rotationYawHead = entity.rotationYawHead - turnspeed;
						entity.rotationYaw = entity.rotationYaw - turnspeed;
						entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
						entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
					}
				}
			}

			double ddx = Math.abs(d5);
			double ddz = Math.abs(d7);
			if ((ddx > max || ddz > max)) {
				if (entity.getAIType3() != 1) {
					ridding.rotationPitch = entity.rotationPitch = entity.rotationp = -f11 + 10;
					entity.setAIType3(0);
				}
			} else {
				if (entity.getAIType3() == 0) {
					entity.setAIType3(1);
					entity.aitypetime3 = 0;
				}
			}
		} else {
			List<Entity> llist = entity.world.getEntitiesWithinAABBExcludingEntity(entity,
					entity.getEntityBoundingBox().expand(entity.motionX, entity.motionY, entity.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entity.getEntitySenses().canSee(entity1);
						if (ridding.targetentity == entity1 && ridding.CanAttack(entity1)) {
							double d5 = entity1.posX - entity.posX;
							double d7 = entity1.posZ - entity.posZ;
							double d6 = entity1.posY - entity.posY;
							double d1 = entity.posY - (entity1.posY);
							double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
							float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
							entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;

							if (entity.rote > 180F) {
								entity.rote = -179F;
							}
							if (entity.rote < -180F) {
								entity.rote = 179F;
							}
							if (entity.getAIType3() != 1) {
								if (entity.rotationYawHead != entity.rote) {
									if (entity.rotationYawHead < entity.rote) {
										entity.rotationYawHead = entity.rotationYawHead + turnspeed;
										entity.rotationYaw = entity.rotationYaw + turnspeed;
										entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
										entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
									} else if (entity.rotationYawHead > entity.rote) {
										entity.rotationYawHead = entity.rotationYawHead - turnspeed;
										entity.rotationYaw = entity.rotationYaw - turnspeed;
										entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
										entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
									}
								}
							}

							double ddx = Math.abs(d5);
							double ddz = Math.abs(d7);
							if ((ddx > max || ddz > max)) {
								if (entity.getAIType3() != 1) {
									ridding.rotationPitch = entity.rotationPitch = entity.rotationp = -f11 + 10;
									entity.setAIType3(0);
								}
							} else {
								if (entity.getAIType3() == 0) {
									entity.setAIType3(1);
									entity.aitypetime3 = 0;
								}
							}

							if (flag) {
								ridding.targetentity = (EntityLivingBase) entity1;
							}
							ridding.target = true;
							break;
						} else
						//if(ridding.targetentity == null)
						if (ridding.CanAttack(entity1) && flag) {
							//if (ridding.CanAttack(entity1) && flag) 
							{
								ridding.targetentity = (EntityLivingBase) entity1;
								ridding.target = true;
								break;
							}
						}
					}
				}
			}
			if (!ridding.target && entity.getHealth() > 0.0F) {
				if (entity.getMoveT() == 2) {
					ridding.rotationPitch = entity.rotationPitch = entity.rotationp = 10;
					double d5 = entity.getMoveX() - entity.posX;
					double d7 = entity.getMoveZ() - entity.posZ;

					entity.rotation = entity.rote = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
					if (entity.rote > 180F) {
						entity.rote = -179F;
					}
					if (entity.rote < -180F) {
						entity.rote = 179F;
					}
					if (entity.getAIType3() != 1) {
						if (entity.rotationYawHead != entity.rote) {
							if (entity.rotationYawHead < entity.rote) {
								entity.rotationYawHead = entity.rotationYawHead + turnspeed;
								entity.rotationYaw = entity.rotationYaw + turnspeed;
								entity.prevRotationYaw = entity.prevRotationYaw + turnspeed;
								entity.prevRotationYawHead = entity.prevRotationYawHead + turnspeed;
							} else if (entity.rotationYawHead > entity.rote) {
								entity.rotationYawHead = entity.rotationYawHead - turnspeed;
								entity.rotationYaw = entity.rotationYaw - turnspeed;
								entity.prevRotationYaw = entity.prevRotationYaw - turnspeed;
								entity.prevRotationYawHead = entity.prevRotationYawHead - turnspeed;
							}
						}
					}

					double ddx = Math.abs(d5);
					double ddz = Math.abs(d7);
					if ((ddx > max || ddz > max)) {
						if (entity.getAIType3() != 1) {
							entity.setAIType3(0);
						}
					} else {
						if (entity.getAIType3() == 0) {
							entity.setAIType3(1);
							entity.aitypetime3 = 0;
						}
					}
				} else {
					if (!entity.world.isRemote) {
						entity.rotationYawHead = entity.rotationYawHead + 1;
						entity.rotationYaw = entity.rotationYaw + 1;
						entity.prevRotationYaw = entity.prevRotationYaw + 1;
						entity.prevRotationYawHead = entity.prevRotationYawHead + 1;
					}
					ridding.rotationPitch = entity.rotationPitch = entity.rotationp = 10;
				}
			}
		}
		if (entity.getAIType3() == 1) {
			ridding.rotationPitch = entity.rotationPitch = entity.rotationp = 40;
			if (entity.aitypetime3 > 80) {
				entity.setAIType3(0);
				entity.aitypetime3 = 0;
			}
		}
	}
	
	
}