package gvclib.event;

import java.util.List;

import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_AR;
import gvclib.item.ItemGun_SR;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

import net.minecraft.nbt.NBTTagCompound;
import gvclib.entity.living.EntityVehicleBase;
import ra2sa.entity.NewSoldierBase;

import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Loader;

import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import gvclib.util.ForgeEvent;
import net.minecraftforge.fml.common.Mod;
import gvclib.ClientProxyGVClib;
import net.minecraft.client.Minecraft;
public class GVCEventLiving {
	
	/*//@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void localClientPlayerTick(PlayerTickEvent event) {
		if (event.phase == Phase.START) {
			ClientProxyGVClib cp = ClientProxyGVClib.get();
			if (event.player == cp.getPlayerClient()) {
				if (Minecraft.getMinecraft().inGameHasFocus && !event.player.isSpectator()) {
					ItemStack stack = event.player.getHeldItemMainhand();
					ItemStack stackOff = event.player.getHeldItemOffhand();
					if (!stack.isEmpty() && stack.getItem() instanceof ItemGunBase) {
						if (cp.leftclick()) {
							ItemGunBase gun = (ItemGunBase) stack.getItem();
							int li = gun.getMaxDamage() - stack.getItemDamage();
							NBTTagCompound nbt = stack.getTagCompound();
							GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(107));
							gun.Fire_Base(stack, event.player.world, event.player, li, true, nbt);
						}
					}
				}
			}
		}
	}*/
	
	@SubscribeEvent
	public void onHurtEvent_SA(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		if (target instanceof EntityPlayer && target != null) //玩家受伤
		{
			Entity entity = source.getTrueSource();
	    	if(entity != null && entity.getTeam() == target.getTeam() && target.getTeam() !=null){
	    		event.setAmount(0);
	            event.setCanceled(true);
	    	}else if(entity != null && entity instanceof EntityGVCLivingBase){
				EntityGVCLivingBase entity1 = (EntityGVCLivingBase) entity;
				if(entity1.getOwner() == target){
	    		//event.setAmount(0);
	            event.setCanceled(true);
				}
			}
			
			{
				List<Entity> llist = target.world.getEntitiesWithinAABBExcludingEntity(target, target
						.getEntityBoundingBox().expand(target.motionX, target.motionY, target.motionZ).grow(40));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							if (entity1 != null && (entity1 instanceof EntityGVCLivingBase)) {//target
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase)entity1;
								if (target.getHealth() > 0.0F && target == gvcentity.getOwner()||gvcentity.getTeam() == target.getTeam() && target.getTeam() !=null) {//保护主人玩家/同队伍玩家
									if(entity != null && entity.getTeam() != target.getTeam() && target.getTeam() !=null && entity instanceof EntityLivingBase){
										gvcentity.targetentity = (EntityLivingBase)entity;
									}else if(entity != null && entity.getTeam() !=null && entity instanceof EntityLivingBase){
										gvcentity.targetentity = (EntityLivingBase)entity;
									}
								} 
							}//target
						}
					}
				}
			}
			
		}
	}
	@Optional.Method(modid = "ra2sa")
	@SubscribeEvent
	public void onUpdateEvent(LivingUpdateEvent event) {//载具阻止燃烧
		World worldIn = event.getEntityLiving().world;
		EntityLivingBase target = event.getEntityLiving();
		boolean left_key = mod_GVCLib.proxy.leftclick();
		if(target != null) {
			if(target instanceof EntityVehicleBase){
				EntityVehicleBase vehicle = (EntityVehicleBase)target;
				if(vehicle.getHealth()>vehicle.getMaxHealth()/3 || target.isBurning()){
					if (!worldIn.isRemote)
					{
						vehicle.setFire(0);
					}
				}
			}
			if(target instanceof NewSoldierBase){
				NewSoldierBase vehicle = (NewSoldierBase)target;
				if(vehicle.getHealth()>vehicle.getMaxHealth()/3 || target.isBurning()){
					if (!worldIn.isRemote)
					{
						vehicle.setFire(0);
					}
				}
			}
			/*if(target instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)target;
				ItemStack itemstack = player.getHeldItemMainhand();
				if(!itemstack.isEmpty()  && itemstack.getItem() instanceof ItemGunBase){
					ItemGunBase gun = (ItemGunBase) itemstack.getItem();
					//if(!gun.is_fire)gun.is_fire = true;
					
					if(player != null && !itemstack.isEmpty()){
						if(left_key){
							//if(!gun.is_fire)gun.is_fire = true;
							GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(103));
							//GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(104));
						}
					}
					
					if(gun.getShootAnimation()||gun.has_fire)mod_GVCLib.proxy.onShootAnimation(player, itemstack);
				}
			}*/
		}
	}
	
	@SubscribeEvent
	  public void onLivingFromTeamEvent_SA(LivingAttackEvent event){
		EntityLivingBase target = event.getEntityLiving();
		DamageSource source = event.getSource();
		float damage = event.getAmount();
		
		if (target instanceof EntityLivingBase && target != null) //AI受伤
		{
			Entity entity = source.getTrueSource();
	    	if(entity != null && entity instanceof EntityLivingBase && entity.getTeam() == target.getTeam() && entity.getTeam() != null){//免疫同队伍伤害
	            event.setCanceled(true);
	    	}
			
			{
				List<Entity> llist = target.world.getEntitiesWithinAABBExcludingEntity(target, target
						.getEntityBoundingBox().expand(target.motionX, target.motionY, target.motionZ).grow(40));
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith()) {
							if (entity1 != null && (entity1 instanceof EntityGVCLivingBase)) {//target
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase)entity1;
								if (target.getHealth() > 0.0F && target == gvcentity.getOwner()||gvcentity.getTeam() == target.getTeam() && target.getTeam() !=null) {//保护主人/同队伍生物
									if(entity != null && entity.getTeam() != target.getTeam() && target.getTeam() !=null && entity instanceof EntityLivingBase){
										gvcentity.targetentity = (EntityLivingBase)entity;
									}else if(entity != null && entity.getTeam() !=null && entity instanceof EntityLivingBase){
										gvcentity.targetentity = (EntityLivingBase)entity;
									}
								}
							}//target
						}
					}
				}
			}
		}
	}
	
	/*@SubscribeEvent
	public void onUpdateEvent_Debag(WorldEvent.Unload event) {
		if(mod_GVCLib.cfg_mobdismount_insave) {
			World world = event.getWorld();
			List<Entity> entitylist = world.loadedEntityList;
			if (entitylist != null) {
				for (int lj = 0; lj < entitylist.size(); lj++) {
					Entity entity1 = (Entity) entitylist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if(entity1 != null && entity1 instanceof EntityGVCLivingBase) {
							if (entity1.isRiding()) {
								entity1.dismountRidingEntity();
							}
						}
					}
				}
			}
		}
	}
	
	public void onUpdateEvent_Debag2(WorldEvent.Unload event) {
		{
			World world = event.getWorld();
			List<Entity> entitylist = world.loadedEntityList;
			if (entitylist != null) {
				for (int lj = 0; lj < entitylist.size(); lj++) {
					Entity entity1 = (Entity) entitylist.get(lj);
					if (entity1.canBeCollidedWith()) {
						if(entity1 != null && entity1 instanceof EntityGVCLivingBase) {
							Chunk chunk = world.getChunkFromBlockCoords(entity1.getPosition());
							entity1.posX = chunk.x*16;
							entity1.posZ = chunk.z*16;
						}
					}
				}
			}
		}
	}*/
}
