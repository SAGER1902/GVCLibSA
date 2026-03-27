package gvclib.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gvclib.item.ItemGunBase;
import gvclib.packets.PacketShootGunGVC;
import gvclib.mod_GVCLib;
import gvclib.ClientProxyGVClib;
import gvclib.item.ItemGun_AR;
import gvclib.network.GVCLPacketHandler;

@Mod.EventBusSubscriber(modid = mod_GVCLib.MOD_ID)
public class NewGunTickHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void localClientPlayerTick(PlayerTickEvent event) {
		{
			if (event.phase == Phase.START) {
				ClientProxyGVClib cp = ClientProxyGVClib.get();
				// ONLY DO FOR OWN PLAYER!
				if (event.player == cp.getPlayerClient()) {
					if (Minecraft.getMinecraft().inGameHasFocus && !event.player.isSpectator()) {
						ItemStack stack = event.player.getHeldItemMainhand();
						ItemStack stackOff = event.player.getHeldItemOffhand();
						if(!stack.isEmpty()  && stack.getItem() instanceof ItemGunBase){
							ItemGunBase.updateCheckinghSlot(event.player, stack);
							if (cp.keyFirePressedMainhand) {
								ItemGunBase gun = (ItemGunBase) stack.getItem();
								int li = gun.getMaxDamage() - stack.getItemDamage();
								//NBTTagCompound nbt = itemstack.getTagCompound();
								//gun.Fire_Base(itemstack, entityPlayer.world, entityPlayer, li, true, nbt);
								/*if(gun.aim_fire||!gun.aim_fire && gun.aim_time >= gun.time_aim||gun.aim_time <= 0)*/{
									if(gun instanceof ItemGun_AR){
										//mod_GVCLib.network.sendToServer(new PacketShootGunGVC(false,EnumHand.MAIN_HAND));
										gun.Fire_AR_Left(stack, event.player.world, event.player, li, true, false);//后座
										GVCLPacketHandler.INSTANCE3.sendToServer(new PacketShootGunGVC(false,EnumHand.MAIN_HAND));
										
									}else{
										//mod_GVCLib.network.sendToServer(new PacketShootGunGVC(false,EnumHand.MAIN_HAND));
										gun.Fire_SR_Left(stack, event.player.world, event.player, li, true, false);//后座
										GVCLPacketHandler.INSTANCE3.sendToServer(new PacketShootGunGVC(false,EnumHand.MAIN_HAND));
										
										cp.keyFirePressedMainhand = false;
									}
								}
								//gun.FireBullet(stack, event.player.world, event.player);
							}
						} else {
							cp.keyFirePressedMainhand = false;
						}
					} else {
						cp.keyFirePressedMainhand = false;
						cp.keyFirePressedOffhand = false;
					}
				}
			}
		}
	}
}
