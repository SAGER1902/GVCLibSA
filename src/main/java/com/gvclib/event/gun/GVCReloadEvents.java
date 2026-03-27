package gvclib.event.gun;

import gvclib.mod_GVCLib;
import gvclib.entity.EntityBBase;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_Shield;
import gvclib.item.gunbase.IGun_Shield;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GVCReloadEvents {

	
	
	
	@SubscribeEvent
	public void onUPEvent(LivingUpdateEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		ItemStack itemstack = target.getHeldItemMainhand();
		if(target instanceof EntityPlayer){
			if(target != null){
				EntityPlayer player =(EntityPlayer) target;
				if(mod_GVCLib.proxy.keyq() && !player.capabilities.isCreativeMode){
					player.closeScreen();//关闭物品GUI
				}
				if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {
					//NBTTagCompound nbtgun = itemstack.getTagCompound();
					//int time = nbtgun.getInteger("guntime");
					ItemGunBase gun = (ItemGunBase) itemstack.getItem(); 
					if (mod_GVCLib.proxy.reload()) {
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(1));
						if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
							for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(1), playermp);
						}
					}
					if (mod_GVCLib.proxy.xclick() && !mod_GVCLib.proxy.keyq()) {//打开改枪GUI
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(5));
					}
					if (mod_GVCLib.proxy.keyl()) {//K帧
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(6));
					}
					if(target.world.isRemote)++gun.lighttime;
					if (mod_GVCLib.proxy.keyc()) {
						NBTTagCompound nbt = itemstack.getTagCompound();
						if(!itemstack.hasTagCompound())return;
						int am_light_kazu = nbt.getInteger("am_light_kazu");
						if(gun.render_light || gun.render_default_light) {
							if(gun.lighttime > 5) {
								if(gun.now_lightid < am_light_kazu) {
									++gun.now_lightid;
									gun.rightmode = true;
									String dd = String.format("%1$3d", (int)gun.now_lightid);
									//System.out.println(String.format(dd));
								}else {
									gun.now_lightid = 0;
									gun.rightmode = false;
								}
								gun.lighttime = 0;
							}else {
								//++gun.lighttime;
							}
						}
						/*if(gun.lighttime > 5) {
							if(gun.rightmode) {
								gun.rightmode = false;
							}else {
								gun.rightmode = true;
							}
							gun.lighttime = 0;
						}else {
							++gun.lighttime;
						}*/
					}
				}
			}
		}
		/*ItemStack itemstackl = target.getHeldItemOffhand();
		if(target instanceof EntityPlayer && target != null){
			if (target != null &&! itemstackl.isEmpty()&& itemstackl.getItem() instanceof ItemGun_Shield 
					&& itemstackl.getItemDamage() != itemstackl.getMaxDamage() && itemstackl.hasTagCompound()) {
				NBTTagCompound nbtgun = itemstackl.getTagCompound();
				boolean left = nbtgun.getBoolean("LeftClick");
				if(left)
				{
					target.motionX = target.motionX * 0.5F;
					target.motionZ = target.motionZ * 0.5F;
				}
			}
		}*/
	}
	
	@SubscribeEvent
	public void LivingHurtEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		ItemStack main = target.getHeldItemMainhand();
		ItemStack off = target.getHeldItemOffhand();
		DamageSource source = event.getSource();
		if(target != null && target instanceof EntityPlayer){
			if(!main.isEmpty() && main.getItem() instanceof IGun_Shield && main.hasTagCompound()) {
				NBTTagCompound nbt = main.getTagCompound();
				boolean can = nbt.getBoolean("CAN_SD");
				if(can && (source != null && !source.isMagicDamage()))
				{
					ItemGunBase gun = (ItemGunBase) main.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
				}
			}
			if(!off.isEmpty() && off.getItem() instanceof IGun_Shield && off.hasTagCompound()) {
				NBTTagCompound nbt = off.getTagCompound();
				boolean can = nbt.getBoolean("CAN_SD");
				if(can && (source != null && !source.isMagicDamage()))
				{
					ItemGunBase gun = (ItemGunBase) off.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
				}
			}
		}
		
		if(target instanceof EntityGVCLivingBase && target != null){
			EntityGVCLivingBase en = (EntityGVCLivingBase) target;
			if (main != null && main.getItem() instanceof IGun_Shield  
					&& (source != null && !source.isMagicDamage())) {
				if(en.getSneak() || en.sneak)
				{
					ItemGunBase gun = (ItemGunBase) main.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
					target.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + target.world.rand.nextFloat() * 0.4F);
				}
			}
			if (off != null && off.getItem() instanceof IGun_Shield  
					&& (source != null && !source.isMagicDamage())) {
				if(en.getSneak() || en.sneak)
				{
					ItemGunBase gun = (ItemGunBase) off.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
					target.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + target.world.rand.nextFloat() * 0.4F);
				}
			}
		}
		
		
	}/**/
	
	
	/*@SubscribeEvent
	public void LivingHurtEvent(LivingHurtEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		ItemStack main = target.getHeldItemMainhand();
		DamageSource source = event.getSource();
		if(target instanceof EntityPlayer && target != null){
			if (target != null && !main.isEmpty() && main.getItem() instanceof ItemGun_Shield 
					&& main.getItemDamage() != main.getMaxDamage()&& main.hasTagCompound()) {
				NBTTagCompound nbtgun = main.getTagCompound();
				boolean right = nbtgun.getBoolean("RightClick");
				if(right || target.getActiveItemStack() == main && (source != null && !source.isMagicDamage()))
				{
					//event.isCanceled();
					//event.setAmount(0);
					ItemGunBase gun = (ItemGunBase) main.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
				}
			}
		}
		
		ItemStack off = target.getHeldItemOffhand();
		if(target instanceof EntityPlayer && target != null){
			if (target != null &&! off.isEmpty()&& off.getItem() instanceof ItemGun_Shield 
					&& off.getItemDamage() != off.getMaxDamage()&& off.hasTagCompound()) {
				NBTTagCompound nbtgun = off.getTagCompound();
				boolean left = nbtgun.getBoolean("LeftClick");
				//if(left || target.isSneaking())
				if((left || target.isSneaking())  && (source != null && !source.isMagicDamage()))
				{
					//event.isCanceled();
					//event.setAmount(0);
					ItemGunBase gun = (ItemGunBase) off.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
				}
			}
		}
		if(target instanceof EntityGVCLivingBase && target != null){
			EntityGVCLivingBase en = (EntityGVCLivingBase) target;
			if (main != null && main.getItem() instanceof ItemGun_Shield  
					&& (source != null && !source.isMagicDamage())) {
				if(en.getSneak() || en.sneak)
				{
					ItemGunBase gun = (ItemGunBase) main.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
					target.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + target.world.rand.nextFloat() * 0.4F);
				}
			}
			if (off != null && off.getItem() instanceof ItemGun_Shield  
					&& (source != null && !source.isMagicDamage())) {
				if(en.getSneak() || en.sneak)
				{
					ItemGunBase gun = (ItemGunBase) off.getItem();
					event.setAmount(event.getAmount() * gun.Shield_defence);
					target.world.setEntityState(target, (byte)29);
					target.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + target.world.rand.nextFloat() * 0.4F);
				}
			}
		}
	}/**/
	
	
	
	
	

}
