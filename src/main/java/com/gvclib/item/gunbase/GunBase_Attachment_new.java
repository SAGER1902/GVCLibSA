package gvclib.item.gunbase;

import java.io.IOException;
import java.io.InputStreamReader;

import gvclib.mod_GVCLib;
import gvclib.gui.GVCInventoryItem;
import gvclib.item.ItemAttachment;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_SWORD;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.IResource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import objmodel.AdvancedModelLoader;

public class GunBase_Attachment_new {

	public static void load(ItemGunBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean flag) {

		NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
		
		//boolean recoiled = nbt.getBoolean("Recoiled");
		float model_x_ads = nbt.getFloat("model_x_ads");
		float model_y_ads = nbt.getFloat("model_y_ads");
		float model_z_ads = nbt.getFloat("model_z_ads");
		
		{
			nbt.setInteger("am_light_kazu", gun.am_light_kazu);
			for(int ii = 0; ii < gun.am_light_kazu; ++ii) {
				nbt.setFloat("am_colorlevel" + String.format("%1$3d", ii), gun.am_colorlevel[ii]);
			}
			//nbt.setBoolean("am_light", gun.render_default_light);
		}
		
		if(!flag)return;
		InventoryPlayer playerInv = entityplayer.inventory;
		GVCInventoryItem inventory = new GVCInventoryItem(playerInv, itemstack);
		//GVCInventoryItem inventory = new GVCInventoryItem(entityplayer);
		inventory.openInventory(entityplayer);
		for (int i1 = 0; i1 < inventory.getSizeInventory(); ++i1) {
			ItemStack itemstacki = inventory.getStackInSlot(i1);

			if (i1 == 1) {
				if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment) {
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 101){
						//gun.scopezoom = am.zoom;
						nbt.setFloat("scopezoom", am.zoom);
						if(gun.render_sight && am.objtrue) {
							if(gun.designated_kazu != 0) {
								if(gun.designated_mat4){
									mat4(itemstack, gun, am);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat4(itemstack, gun, am);
										break;
									}
								}
							}
							else if(am.designated_attachment_name == null){
								mat4(itemstack, gun, am);
							}
						}else {
							if(gun.render_mat4_unam){
								gun.scopezoom = gun.scopezoomred;
								gun.true_mat4 = true;
							}
						}
					}else{
						gun.am_sight = false;
						if(gun.designated_kazu != 0) {
							for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
								if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
									if(am.id == 4){
										gun.scopezoom = gun.scopezoomred;
										gun.true_mat4 = true;
									}else if(am.id == 5){
										gun.scopezoom = gun.scopezoomscope;
										gun.true_mat4 = false;
										gun.true_mat5 = true;
									}else{
										gun.scopezoom = gun.scopezoombase;
										gun.true_mat4 = false;
										gun.true_mat5 = false;
									}
								}
							}
						}else {
							if(am.id == 4){
								gun.scopezoom = gun.scopezoomred;
								gun.true_mat4 = true;
							}else if(am.id == 5){
								gun.scopezoom = gun.scopezoomscope;
								gun.true_mat4 = false;
								gun.true_mat5 = true;
							}else{
								gun.scopezoom = gun.scopezoombase;
								gun.true_mat4 = false;
								gun.true_mat5 = false;
							}
						}
						
					}
					gun.sight_item = itemstacki;
				}else{
					nbt.setBoolean("am_sight", false);
					nbt.setFloat("scopezoom", gun.scopezoombase);
					gun.scopezoom = gun.scopezoombase;
					gun.true_mat4 = false;
					gun.true_mat5 = false;
					gun.sight_item = new ItemStack((Item)null);
				}
			}
			if (i1 == 2) {
				if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment) {
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 102){
						if(gun.render_light && am.objtrue) {
							if(gun.designated_kazu != 0) {
								if(gun.designated_mat6){
									mat6(itemstack, gun, am);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat6(itemstack, gun, am);
										 break;
									}
								}
							}else if(am.designated_attachment_name == null){
								mat6(itemstack, gun, am);
							}
						}else if(gun.render_mat6_unam){
							gun.true_mat6 = true;
							gun.true_mat7 = false;
						}
					}else {
						if(am.id == 6){
							gun.true_mat6 = true;
							gun.true_mat7 = false;
						}else if(am.id == 7){
							gun.true_mat6 = false;
							gun.true_mat7 = true;
						}
					}
					gun.light_item = itemstacki;
				}else{
					nbt.setBoolean("am_light", false);
					gun.true_mat6 = false;
					gun.true_mat7 = false;
					gun.light_item = new ItemStack((Item)null);
				}
			}
			if (i1 == 3) {
				if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment) {
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 8){
						gun.sound = gun.soundsu;
						gun.fires = gun.fires_su;
						gun.muzzle = false;
						gun.true_mat8 = true;
						if(am.objtrue && gun.render_muss) {
							if(gun.designated_kazu != 0) {
								if(gun.designated_mat8){
									mat8(itemstack, gun, am);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat8(itemstack, gun, am);
										break;
									}
								}
							}else {if(am.designated_attachment_name == null)
								mat8(itemstack, gun, am);
							}
						}else if(gun.render_mat8_unam){
							gun.am_supu = false;
							gun.sound = gun.soundsu;
							gun.fires = gun.fires_su;
							gun.muzzle = true;
							gun.true_mat8 = true;
						}
					}else{
						gun.sound = gun.soundbase;
						gun.fires = gun.firesbase;
						gun.muzzle = true;
						gun.true_mat8 = false;
					}
					gun.supu_item = itemstacki;
				}else{
					nbt.setBoolean("am_supu", false);
					gun.sound = gun.soundbase;
					gun.fires = gun.firesbase;
					gun.muzzle = true;
					gun.true_mat8 = false;
					gun.supu_item = new ItemStack((Item)null);
				}
			}
			if(gun.canuse_plugin_a) {
				if(!itemstack.hasTagCompound())return;
				if(i1 == 6){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am1 = (ItemAttachment) itemstacki.getItem();
						if(am1.id == 56){
							nbt.setFloat("powor1", am1.extra_power);
							nbt.setFloat("ex1", am1.extra_exlevel);
							nbt.setFloat("bure1", am1.extra_bure);
							nbt.setFloat("recoil1", am1.extra_recoil);
						}
					}else{
						nbt.setFloat("powor1", 0);
						nbt.setFloat("ex1", 0);
						nbt.setFloat("bure1", 0);
						nbt.setFloat("recoil1", 0);
					}
				}
				if(i1 == 7){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am2 = (ItemAttachment) itemstacki.getItem();
						if(am2.id == 56){
							nbt.setFloat("powor2", am2.extra_power);
							nbt.setFloat("ex2", am2.extra_exlevel);
							nbt.setFloat("bure2", am2.extra_bure);
							nbt.setFloat("recoil2", am2.extra_recoil);
						}
					}else{
						nbt.setFloat("powor2", 0);
						nbt.setFloat("ex2", 0);
						nbt.setFloat("bure2", 0);
						nbt.setFloat("recoil2", 0);
					}
				}
				if(i1 == 8){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am3 = (ItemAttachment) itemstacki.getItem();
						if(am3.id == 56){
							nbt.setFloat("powor3", am3.extra_power);
							nbt.setFloat("ex3", am3.extra_exlevel);
							nbt.setFloat("bure3", am3.extra_bure);
							nbt.setFloat("recoil3", am3.extra_recoil);
						}
					}else{
						nbt.setFloat("powor3", 0);
						nbt.setFloat("ex3", 0);
						nbt.setFloat("bure3", 0);
						nbt.setFloat("recoil3", 0);
					}
				}
				if(i1 == 9){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am4 = (ItemAttachment) itemstacki.getItem();
						if(am4.id == 56){
							nbt.setFloat("powor4", am4.extra_power);
							nbt.setFloat("ex4", am4.extra_exlevel);
							nbt.setFloat("bure4", am4.extra_bure);
							nbt.setFloat("recoil4", am4.extra_recoil);
						}
					}else{
						nbt.setFloat("powor4", 0);
						nbt.setFloat("ex4", 0);
						nbt.setFloat("powor4", 0);
						nbt.setFloat("recoil4", 0);
					}
				}
				float power1 = nbt.getFloat("powor1");
				float power2 = nbt.getFloat("powor2");
				float power3 = nbt.getFloat("powor3");
				float power4 = nbt.getFloat("powor4");
				float ex1 = nbt.getFloat("ex1");
				float ex2 = nbt.getFloat("ex2");
				float ex3 = nbt.getFloat("ex3");
				float ex4 = nbt.getFloat("ex4");
				nbt.setFloat("extra_exlevel", ex1+ex2+ex3+ex4);
				nbt.setFloat("extra_power", power1+power2+power3+power4);
				float bure1 = nbt.getFloat("bure1");
				float bure2 = nbt.getFloat("bure2");
				float bure3 = nbt.getFloat("bure3");
				float bure4 = nbt.getFloat("bure4");
				float recoil1 = nbt.getFloat("recoil1");
				float recoil2 = nbt.getFloat("recoil2");
				float recoil3 = nbt.getFloat("recoil3");
				float recoil4 = nbt.getFloat("recoil4");
				nbt.setDouble("extra_recoil", recoil1+recoil2+recoil3+recoil4);
				nbt.setFloat("extra_bure", bure1+bure2+bure3+bure4);
			}
			if(gun.canuse_plugin_b) {
				if(!itemstack.hasTagCompound())return;
				if(i1 == 10){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am1 = (ItemAttachment) itemstacki.getItem();
						if(am1.id == 57){
							nbt.setFloat("powor5", am1.extra_power);
							nbt.setFloat("ex5", am1.extra_exlevel);
							nbt.setFloat("bure5", am1.extra_bure);
							nbt.setFloat("recoil5", am1.extra_recoil);
						}
					}else{
						nbt.setFloat("powor5", 0);
						nbt.setFloat("ex5", 0);
						nbt.setFloat("bure5", 0);
						nbt.setFloat("recoil5", 0);
					}
				}
				if(i1 == 11){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am2 = (ItemAttachment) itemstacki.getItem();
						if(am2.id == 57){
							nbt.setFloat("powor6", am2.extra_power);
							nbt.setFloat("ex6", am2.extra_exlevel);
							nbt.setFloat("bure6", am2.extra_bure);
							nbt.setFloat("recoil6", am2.extra_recoil);
						}
					}else{
						nbt.setFloat("powor6", 0);
						nbt.setFloat("ex6", 0);
						nbt.setFloat("bure6", 0);
						nbt.setFloat("recoil6", 0);
					}
				}
				if(i1 == 12){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am3 = (ItemAttachment) itemstacki.getItem();
						if(am3.id == 57){
							nbt.setFloat("powor7", am3.extra_power);
							nbt.setFloat("ex7", am3.extra_exlevel);
							nbt.setFloat("bure7", am3.extra_bure);
							nbt.setFloat("recoil7", am3.extra_recoil);
						}
					}else{
						nbt.setFloat("powor7", 0);
						nbt.setFloat("ex7", 0);
						nbt.setFloat("bure7", 0);
						nbt.setFloat("recoil7", 0);
					}
				}
				if(i1 == 13){
					if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment){
						ItemAttachment am4 = (ItemAttachment) itemstacki.getItem();
						if(am4.id == 57){
							nbt.setFloat("powor8", am4.extra_power);
							nbt.setFloat("ex8", am4.extra_exlevel);
							nbt.setFloat("bure8", am4.extra_bure);
							nbt.setFloat("recoil8", am4.extra_recoil);
						}
					}else{
						nbt.setFloat("powor8", 0);
						nbt.setFloat("ex8", 0);
						nbt.setFloat("powor8", 0);
						nbt.setFloat("recoil8", 0);
					}
				}
				float bure1 = nbt.getFloat("bure5");
				float bure2 = nbt.getFloat("bure6");
				float bure3 = nbt.getFloat("bure7");
				float bure4 = nbt.getFloat("bure8");
				float recoil1 = nbt.getFloat("recoil5");
				float recoil2 = nbt.getFloat("recoil6");
				float recoil3 = nbt.getFloat("recoil7");
				float recoil4 = nbt.getFloat("recoil8");
				nbt.setDouble("extra_recoil2", recoil1+recoil2+recoil3+recoil4);
				nbt.setFloat("extra_bure2", bure1+bure2+bure3+bure4);
				float power1 = nbt.getFloat("powor5");
				float power2 = nbt.getFloat("powor6");
				float power3 = nbt.getFloat("powor7");
				float power4 = nbt.getFloat("powor8");
				float ex1 = nbt.getFloat("ex5");
				float ex2 = nbt.getFloat("ex6");
				float ex3 = nbt.getFloat("ex7");
				float ex4 = nbt.getFloat("ex8");
				nbt.setFloat("extra_exlevel2", ex1+ex2+ex3+ex4);
				nbt.setFloat("extra_power2", power1+power2+power3+power4);
			}

			if (i1 == 5) {
				if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment) {
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 50){
						for(int ic = 0; ic < gun.canuse_bullet.length; ++ic) {
							if(gun.canuse_bullet[ic] != null && gun.canuse_bullet[ic].equals(am.bullet_name)) {
								if(!itemstack.hasTagCompound())return;
								
								boolean am_bullet = nbt.getBoolean("am_bullet");
								nbt.setBoolean("am_bullet", true);
								nbt.setInteger("p_id", am.p_id);
								nbt.setInteger("p_level", am.p_level);
								nbt.setInteger("p_time", am.p_time);
								
								nbt.setFloat("exlevel", am.exlevel);
								nbt.setFloat("bure_bullet", am.bure);
								nbt.setInteger("powor", (int) (gun.powor * am.power));
								nbt.setInteger("bulletDameID", am.bulletid);
								if(am.pellet_priority) {
									nbt.setInteger("shotgun_pellet", am.pellet);
								}else {
									nbt.setInteger("shotgun_pellet", 1);
								}
								
								break;
							}
						}
					}else
					if(am.id == 51){
						gun.am_bullet = true;
						gun.bullet_item = itemstacki;
						if (!world.isRemote) {
							gun.bulletDameID = 1;
						}
					}else if(am.id == 52){
						gun.am_bullet = true;
						gun.bullet_item = itemstacki;
						if (!world.isRemote) {
							gun.bulletDameID = 2;
						}
					}else if(am.id == 53){
						gun.am_bullet = true;
						gun.bullet_item = itemstacki;
						if (!world.isRemote) {
							gun.bulletDameID = 3;
						}
					}else if(am.id == 54){
						gun.am_bullet = true;
						gun.bullet_item = itemstacki;
						if (!world.isRemote) {
							gun.bulletDameID = 4;
						}
					}else if(am.id == 55){
						gun.am_bullet = true;
						gun.bullet_item = itemstacki;
						if (!world.isRemote) {
							gun.bulletDameID = 5;
						}
					}else{
						gun.am_bullet = false;
						gun.bullet_item = itemstacki;
						if (!world.isRemote) {
							gun.bulletDameID = 0;
						}
					}
				}else{
					gun.am_bullet = false;
					nbt.setBoolean("am_bullet", false);
					gun.bullet_item = new ItemStack((Item)null);
					if (!world.isRemote) {
						gun.bulletDameID = 0;
					}
				}
			}
			if (i1 == 4) {
				if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment) {
					gun.true_mat10 = false; 
					gun.true_mat11 = false; 
					gun.true_mat12 = false; 
					gun.underbarrel = new ItemStack((Item)null);
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 9){
						gun.true_mat9 = true;
						if(am.objtrue) {
							if(gun.designated_kazu != 0) {
								if(gun.designated_mat9){
									mat9(gun, am, itemstack, entityplayer);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat9(gun, am, itemstack, entityplayer);
										break;
									}
								}
							}else if(am.designated_attachment_name == null){
								mat9(gun, am, itemstack, entityplayer);
							}
						}
					}
				}else if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemGunBase) {
					ItemGunBase under = (ItemGunBase) itemstacki.getItem();
					if(gun.gun_can_use_underbarrel) {
						if(gun.gun_can_get_underbarrel && under.gun_can_set_underbarrel) {
							gun.true_mat9 = false;
							gun.true_mat10 = false;
							gun.true_mat11 = false; 
							gun.true_mat12 = false; 
							gun.true_mat100 = true;
							gun.underbarrel = itemstacki;
						}else {
							gun.true_mat9 = false;
							gun.true_mat12 = false; 
							if(under.gun_type== 0){
								gun.true_mat11 = true;
								gun.true_mat10 = false;
							}else if(under.gun_type== 3 || under.gun_type== 2){
								gun.true_mat10 = true;
								gun.true_mat11 = false;
							}
						}
					}
					
				}
				else if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemGun_SWORD) {
						gun.true_mat9 = false;
						gun.true_mat10 = false;
						gun.true_mat11 = false; 
						{
							gun.true_mat12 = true;
						}
				}else if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemSword) {
					gun.true_mat9 = false;
					gun.true_mat10 = false;
					gun.true_mat11 = false; 
					{
						gun.true_mat12 = true;
					}
				}else{
					nbt.setBoolean("am_grip", false);
					gun.true_mat9 = false; 
					gun.true_mat10 = false; 
					gun.true_mat11 = false; 
					gun.true_mat12 = false; 
					gun.true_mat100 = false; 
					gun.underbarrel = new ItemStack((Item)null);
					gun.grip_item = new ItemStack((Item)null);
				}

			}
		}
		//inventory.closeInventory(entityplayer);
	
	}
	
	public static void mat4(ItemStack itemstack, ItemGunBase gun, ItemAttachment am) {

		NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
		float model_x_ads = nbt.getFloat("model_x_ads");
		float model_y_ads = nbt.getFloat("model_y_ads");
		float model_z_ads = nbt.getFloat("model_z_ads");
		
		boolean am_sight = nbt.getBoolean("am_sight");
		nbt.setBoolean("am_sight", true);
		
		nbt.setFloat("model_x_ads", (gun.am_sight_x + am.x));
		nbt.setFloat("model_y_ads", - (gun.am_sight_y + am.y));
		nbt.setFloat("model_z_ads", am.eye_relief + gun.am_sight_z);
		nbt.setString("am_model", am.obj_model_string);
		nbt.setString("am_tex", am.obj_tex);
		
		nbt.setBoolean("am_zoomrender", am.zoomrender);
		nbt.setBoolean("am_zoomrendertex", am.zoomrendertex);
		nbt.setString("am_ads_tex", am.ads_tex);
		
	}
	
	public static void mat6(ItemStack itemstack, ItemGunBase gun, ItemAttachment am) {
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
		if(am.obj_model == null)return;
		
		boolean am_sight = nbt.getBoolean("am_light");
		nbt.setBoolean("am_light", true);
		
		nbt.setFloat("rightposx", (gun.am_light_x + am.x));
		nbt.setFloat("rightposy", (gun.am_light_y + am.y));
		nbt.setFloat("rightposz", (gun.am_light_z + am.z));
		
		nbt.setInteger("am_light_kazu", am.light_kazu);
		for(int i = 0; i < am.light_kazu; ++i) {
			nbt.setFloat("am_colorlevel" + String.format("%1$3d", i), am.colorlevel[i]);
		}
		nbt.setString("am_light_model", am.obj_model_string);
		nbt.setString("am_light_tex", am.obj_tex);
	}
	
	public static void mat8(ItemStack itemstack, ItemGunBase gun, ItemAttachment am) {

		
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
		if(am.obj_model == null)return;
		
		boolean am_supu = nbt.getBoolean("am_supu");
		nbt.setBoolean("am_supu", true);
		
		nbt.setFloat("supuposx", (gun.am_supu_x + am.x));
		nbt.setFloat("supuposy", (gun.am_supu_y + am.y));
		nbt.setFloat("supuposz", (gun.am_supu_z + am.z));
		
		nbt.setString("am_supu_model", am.obj_model_string);
		nbt.setString("am_supu_tex", am.obj_tex);
		
	}
	
	public static void mat9(ItemGunBase gun, ItemAttachment am, ItemStack itemstack, EntityPlayer entityplayer) {

		
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
		if(am.obj_model == null)return;
		
		boolean am_supu = nbt.getBoolean("am_grip");
		nbt.setBoolean("am_grip", true);
		
		nbt.setFloat("gripposx", (gun.am_grip_x + am.x));
		nbt.setFloat("gripposy", (gun.am_grip_y + am.y));
		nbt.setFloat("gripposz", (gun.am_grip_z + am.z));
		
		nbt.setBoolean("grip_gripping_point", am.grip_gripping_point);
		nbt.setFloat("grip_gripping_point_x", (am.grip_gripping_point_x));
		nbt.setFloat("grip_gripping_point_y", (am.grip_gripping_point_y));
		nbt.setFloat("grip_gripping_point_z", (am.grip_gripping_point_z));
		
		nbt.setString("am_grip_model", am.obj_model_string);
		nbt.setString("am_grip_tex", am.obj_tex);
		
		nbt.setFloat("bure", (gun.bure * am.bure));
		nbt.setFloat("bure_ads", (gun.bure * am.bure_ads));
		nbt.setDouble("recoil", (gun.recoil * am.recoil));
		nbt.setDouble("recoil_ads", (gun.recoil * am.recoil_ads));
		
	}
}
