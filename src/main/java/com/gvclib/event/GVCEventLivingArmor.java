package gvclib.event;

import java.util.List;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityBBase;
import gvclib.entity.EntityB_Grapple;
import gvclib.item.ItemArmor_Grapple;
import gvclib.item.ItemArmor_NewObj;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVCEventLivingArmor {
	
	@SubscribeEvent
	public void onUpdateEvent_Debag(LivingUpdateEvent event) {
		World worldIn = event.getEntityLiving().world;
		EntityLivingBase target = event.getEntityLiving();
		
		//grapple
		if (target != null && target instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) target;
			if(player != null && mod_GVCLib.cfg_debag) {
				ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				if ((!head.isEmpty()) && (head.getItem() == Items.GOLDEN_HELMET)) {//head
					if(worldIn.getWorldTime() > 10000) {
						worldIn.setWorldTime(1000);
					}
					if(worldIn.getWorldInfo().isRaining()) {
						//worldIn.getWorldInfo().setRainTime(0);
						//worldIn.getWorldInfo().setCleanWeatherTime(2000);
						worldIn.getWorldInfo().setCleanWeatherTime(0);
						worldIn.getWorldInfo().setRainTime(0);
						worldIn.getWorldInfo().setThunderTime(0);
						worldIn.getWorldInfo().setRaining(false);
						worldIn.getWorldInfo().setThundering(false);
					}
				}
				ItemStack body = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				if ((!body.isEmpty()) && (body.getItem() == Items.GOLDEN_CHESTPLATE)) {//head
					player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2000, 2000, false, false));
				}
				player.world.getGameRules().setOrCreateGameRule("keepInventory", "true");
			}
			if(player != null && mod_GVCLib.cfg_debag_weather) {
				if(worldIn.getWorldTime() > 10000) {
					worldIn.setWorldTime(1000);
				}
				if(worldIn.getWorldInfo().isRaining()) {
					//worldIn.getWorldInfo().setRainTime(0);
					//worldIn.getWorldInfo().setCleanWeatherTime(2000);
					worldIn.getWorldInfo().setCleanWeatherTime(0);
					worldIn.getWorldInfo().setRainTime(0);
					worldIn.getWorldInfo().setThunderTime(0);
					worldIn.getWorldInfo().setRaining(false);
					worldIn.getWorldInfo().setThundering(false);
				}
				player.world.getGameRules().setOrCreateGameRule("keepInventory", "true");
			}
		}
	}
	
	@SubscribeEvent
	public void onUpdateEvent(LivingUpdateEvent event) {
		World worldIn = event.getEntityLiving().world;
		EntityLivingBase target = event.getEntityLiving();
		
		//grapple
		if (target != null && target instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) target;
			if(player != null) {
				if ((!player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty())
						&& (player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_Grapple)
						) {//head
					ItemArmor_Grapple grapplei = (ItemArmor_Grapple) player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
					if (mod_GVCLib.proxy.keyc() 
							&& player.getCooldownTracker().getCooldown(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem(), 0) == 0
							&& !player.isRiding()) {
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(201, player.getEntityId()));
					}
					if(grapplei.point && !player.isRiding()) {
						EntityB_Grapple gra = null;
						{
							double x = target.posX;
							double y = target.posY;
							double z = target.posZ;
							int han = 120;
							AxisAlignedBB aabb = (new AxisAlignedBB((double) (x - han), (double) (y - han),
									(double) (z - han),
									(double) (x + han), (double) (y + han), (double) (z + han)))
											.grow(1);
					        List llist2 = target.world.getEntitiesWithinAABBExcludingEntity(target, aabb);
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
						
						target.motionX = mox;
						target.motionY = moy;
						target.motionZ = moz;
						target.move(MoverType.PLAYER, target.motionX, target.motionY, target.motionZ);
						//if(!player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem()
						//.onEntitySwing(player, player.getItemStackFromSlot(EntityEquipmentSlot.LEGS)))
						//if(player.isSwingInProgress) 
						}
						if(player.isSneaking()) 
						{
							GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(202));
							grapplei.point = false;
							{//list
								double x = player.posX;
								double y = player.posY;
								double z = player.posZ;
								int han = 120;
								AxisAlignedBB aabb = (new AxisAlignedBB((double) (x - han), (double) (y - han),
										(double) (z - han),
										(double) (x + han), (double) (y + han), (double) (z + han)))
												.grow(1);
								List<EntityB_Grapple> llist = player.world.getEntitiesWithinAABB(EntityB_Grapple.class,
										aabb);
								if (llist != null) {
									for (int lj = 0; lj < llist.size(); lj++) {
										EntityB_Grapple entity1 = (EntityB_Grapple) llist.get(lj);
										//if (entity1 != null && entity1.getThrower() != null && entity1.getThrower() == player) 
										if (entity1 != null) {
											entity1.setDead();
										}
									}
								}
								
							}//list
						}
					}
				}
			}
		}//grapple
		
	}
	
	
	@SubscribeEvent
	public void ArmorLiving(LivingFallEvent event) {
		// if(eevent.getSide().isClient())
		{
			EntityLivingBase entityLiving = event.getEntityLiving();
			NBTTagCompound nbt = entityLiving.getEntityData();
			boolean para = nbt.getBoolean("Para");
			if ((entityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
					&& (entityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
				if(armor.fall || para){
					event.setDistance(0.0F);
				}
			}
			if ((entityLiving.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
					&& (entityLiving.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entityLiving.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
				if(armor.fall || para){
					event.setDistance(0.0F);
				}
			}
			if ((entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
					&& (entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entityLiving.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
				if(armor.fall || para){
					event.setDistance(0.0F);
				}
			}
			if ((entityLiving.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
					&& (entityLiving.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entityLiving.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
				if(armor.fall || para){
					event.setDistance(0.0F);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingMob(LivingUpdateEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		if (target != null) {
			if ((target.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
					&& (target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
				
			}
			else if ((target.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
					&& (target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
				
			}
			else if ((target.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
					&& (target.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
				
			}
			else if ((target.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
					&& (target.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
				
			}else{
				NBTTagCompound nbt = target.getEntityData();
				nbt.setBoolean("Para", false);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingPara(LivingUpdateEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		if (target != null) {
			NBTTagCompound nbt = target.getEntityData();
			boolean para = nbt.getBoolean("Para");
			boolean b = false;
			double m = 1;
			double paraspeed = 0.75;
			//if(!(target instanceof EntityPlayer))
			{
				if ((target.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
						&& (target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
					if(armor.booster){
						b = true;
					//	this.ArmorMove(target, armor, 4);
					}
					m = armor.motion;
					if(target.isSneaking() && armor.para){
						para = true;
						paraspeed = armor.paraspeed;
					}
				}
				if ((target.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
						&& (target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
					if(armor.booster){
						b = true;
					//	this.ArmorMove(target, armor, 3);
					}
					if(m < armor.motion){
						m = armor.motion;
					}
					if(target.isSneaking() && armor.para){
						para = true;
						paraspeed = armor.paraspeed;
					}
				}
				if ((target.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
						&& (target.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
					if(armor.booster){
						b = true;
					//	this.ArmorMove(target, armor, 2);
					}
					if(m < armor.motion){
						m = armor.motion;
					}
					if(target.isSneaking() && armor.para){
						para = true;
						paraspeed = armor.paraspeed;
					}
				}
				if ((target.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
						&& (target.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
					ItemArmor_NewObj armor = (ItemArmor_NewObj) target.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
					if(armor.booster){
						b = true;
					//	this.ArmorMove(target, armor, 1);
					}
					if(m < armor.motion){
						m = armor.motion;
					}
					if(target.isSneaking() && armor.para){
						para = true;
						paraspeed = armor.paraspeed;
					}
				}
				
				{
					target.motionX *= m;
					target.motionZ *= m;
				}
				
				if(para){
					target.motionY *= paraspeed;
					if(target.isSwingInProgress){
						para = false;
					}
				}
				
				
				
				BlockPos bp = target.world.getHeight(new BlockPos(target.posX, target.posY, target.posZ));
				int genY = bp.getY() + 3;
				if(target != null && !(target instanceof EntityPlayer)){
					if(target.posY > genY){
						para = true;
					}
				}
				if(target.onGround){
					para = false;
				}
				if(target.isRiding()) {
					para = false;
				}
				nbt.setBoolean("Para", para);
			}
			
		}
	}
	
	
	
	@SubscribeEvent
	public void onHurtEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		if (target != null) 
		{
			if(source.getImmediateSource() instanceof EntityBBase)
	        {
				if (target instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) target;
					damage = damage / 4.0F;

			        if (damage < 1.0F)
			        {
			            damage = 1.0F;
			        }
					ItemStack head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
					if(!head.isEmpty() && head.getItem() instanceof ItemArmor) {
						head.damageItem(-(int)damage, player);
						//System.out.println(String.format("0"));
					}
					ItemStack chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
					if(!chest.isEmpty() && chest.getItem() instanceof ItemArmor) {
						chest.damageItem(-(int)damage, player);
					}
					ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
					if(!legs.isEmpty() && legs.getItem() instanceof ItemArmor) {
						legs.damageItem(-(int)damage, player);
					}
					ItemStack feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
					if(!feet.isEmpty() && feet.getItem() instanceof ItemArmor) {
						feet.damageItem(-(int)damage, player);
					}
					/*for (int i = 0; i < 36; ++i)
			        {
			            ItemStack itemstack = player.inventory.getStackInSlot(i);

			            if (itemstack.getItem() instanceof ItemArmor)
			            {
			                itemstack.damageItem(-(int)damage, player);
			            }
			        }*/
				}
	        }
		}
	}
	
	
	
}
