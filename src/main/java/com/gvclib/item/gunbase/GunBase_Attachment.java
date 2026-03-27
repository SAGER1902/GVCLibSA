package gvclib.item.gunbase;

import gvclib.gui.GVCInventoryItem;
import gvclib.item.ItemAttachment;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_SWORD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class GunBase_Attachment {

	public static void load(ItemGunBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean flag) {

		
		//reset
		if(!gun.am_sight){
			gun.am_sightbase_x = gun.model_x_ads;
			gun.am_sightbase_y = gun.model_y_ads;
			gun.am_sightbase_z = gun.model_z_ads;
			gun.am_sightbase_z1 = gun.am_sight_z;
		}
		if(!gun.am_bullet){
			gun.p_idbase = gun.p_id;
			gun.exlevelbase = gun.exlevel;
			gun.poworbase = gun.powor;
			gun.pelletbase = gun.shotgun_pellet;
		}
		if(gun.recoilbase == 0.0F){
			gun.recoilbase = gun.recoil;
			gun.burebase = gun.bure;
		}
		{
			gun.am_sight = false;
			gun.model_x_ads = gun.am_sightbase_x;
			gun.model_y_ads = gun.am_sightbase_y;
			gun.model_z_ads = gun.am_sightbase_z;
			gun.am_sight_z = gun.am_sightbase_z1;
			gun.p_id = gun.p_idbase;
			gun.exlevel = gun.exlevelbase;
			gun.powor = gun.poworbase;
			gun.shotgun_pellet = gun.pelletbase;
			
			if(!gun.render_default_light)gun.am_light = false;
			gun.am_supu = false;
			gun.am_grip = false;
			gun.recoil = gun.recoilbase;
			gun.bure = gun.burebase;
			
		}
		if(gun.soundbase == null) {
			gun.soundbase = gun.sound;
		}
		if(gun.firesbase == null && gun.fires != null) {
			gun.firesbase = gun.fires;
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
						gun.scopezoom = am.zoom;
						if(gun.render_sight && am.objtrue) {
							if(gun.designated_kazu != 0) {
								if(gun.designated_mat4){
									mat4(gun, am);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat4(gun, am);
										break;
									}
								}
							}
							else if(am.designated_attachment_name == null){
								mat4(gun, am);
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
									mat6(gun, am);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat6(gun, am);
										 break;
									}
								}
							}else if(am.designated_attachment_name == null){
								mat6(gun, am);
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
									mat8(gun, am);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat8(gun, am);
										break;
									}
								}
							}else {if(am.designated_attachment_name == null)
								mat8(gun, am);
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
					gun.sound = gun.soundbase;
					gun.fires = gun.firesbase;
					gun.muzzle = true;
					gun.true_mat8 = false;
					gun.supu_item = new ItemStack((Item)null);
				}
			}
			
			if (i1 == 5) {
				if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemAttachment) {
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 50){
						for(int ic = 0; ic < gun.canuse_bullet.length; ++ic) {
							if(gun.canuse_bullet[ic] != null && gun.canuse_bullet[ic].equals(am.bullet_name)) {
								gun.am_bullet = true;
								gun.p_id = am.p_id;
								gun.p_level = am.p_level;
								gun.p_time = am.p_time;
								gun.exlevel = am.exlevel;
								gun.bure = gun.bure * am.bure;
								gun.powor = (int) (gun.powor * am.power);
								gun.bulletDameID = am.bulletid;
								if(am.pellet_priority)gun.shotgun_pellet = am.pellet;
								gun.bullet_item = itemstacki;
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
					ItemAttachment am = (ItemAttachment) itemstacki.getItem();
					if(am.id == 9){
						gun.true_mat9 = true;
						if(am.objtrue) {
							if(gun.designated_kazu != 0) {
								if(gun.designated_mat9){
									mat9(gun, am, itemstacki, entityplayer);
								}
								for(int ic = 0; ic < gun.designated_attachment_name.length; ++ic) {
									if(gun.designated_attachment_name[ic] != null && gun.designated_attachment_name[ic].equals(am.designated_attachment_name)) {
										mat9(gun, am, itemstacki, entityplayer);
										break;
									}
								}
							}else if(am.designated_attachment_name == null){
								mat9(gun, am, itemstacki, entityplayer);
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
				/*else if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemGun_SR) {
					
					gun.true_mat9 = false;
					gun.true_mat11 = false; 
					gun.true_mat12 = false; 
					ItemGun_SR am = (ItemGun_SR) itemstacki.getItem();
					if(am.gun_type== 3){
						gun.true_mat10 = true;
					}
				}else if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemGun_SR) {
					gun.true_mat9 = false;
					gun.true_mat10 = false;
					gun.true_mat12 = false; 
					ItemGun_SR am = (ItemGun_SR) itemstacki.getItem();
					if(am.gun_type== 0){
						gun.true_mat11 = true;
					}
				}*/
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
	
	public static void mat4(ItemGunBase gun, ItemAttachment am) {
		gun.rendering_light_am_sight = am.rendering_light;
		gun.am_sight = true;
		gun.am_model = am.obj_model;
		gun.am_tex = am.obj_tex;
		gun.am_zoomrender = am.zoomrender;
		gun.am_zoomrendertex = am.zoomrendertex;
		gun.am_ads_tex = am.ads_tex;
		gun.model_x_ads =  (gun.am_sight_x + am.x);
		gun.model_y_ads = - (gun.am_sight_y + am.y);
		//gun.model_z_ads = - (-gun.model_z_ads + gun.am_sight_z + am.z);
		gun.model_z_ads =  am.eye_relief + gun.am_sight_z;
	}
	
	public static void mat6(ItemGunBase gun, ItemAttachment am) {
		gun.rendering_light_am_light = am.rendering_light;
		gun.am_light = true;
		gun.am_light_kazu = am.light_kazu;
		gun.am_light_model = am.obj_model;
		gun.am_light_tex = am.obj_tex;
		gun.rightposx =  (gun.am_light_x + am.x);
		gun.rightposy =  (gun.am_light_y +am.y);
		gun.rightposz =  (gun.am_light_z +am.z);
		//gun.roterightx = am_light_xr;
		//gun.roterighty = am_light_yr;
		//gun.roterightz = am_light_zr;
		
		 gun.am_colorlevel = am.colorlevel;
	}
	
	public static void mat8(ItemGunBase gun, ItemAttachment am) {
		gun.rendering_light_am_muzzle = am.rendering_light;
		gun.am_supu = true;
		gun.am_supu_model = am.obj_model;
		gun.am_supu_tex = am.obj_tex;
		gun.supuposx =  (gun.am_supu_x + am.x);
		gun.supuposy =  (gun.am_supu_y +am.y);
		gun.supuposz =  (gun.am_supu_z +am.z);
	}
	
	public static void mat9(ItemGunBase gun, ItemAttachment am, ItemStack itemstack, EntityPlayer entityplayer) {
		if(gun.render_grip) {
			gun.grip_item = itemstack;
		gun.rendering_light_am_grip = am.rendering_light;
		gun.am_grip = true;
		gun.am_grip_model = am.obj_model;
		gun.am_grip_tex = am.obj_tex;
		gun.gripposx =  (gun.am_grip_x + am.x);
		gun.gripposy =  (gun.am_grip_y +am.y);
		gun.gripposz =  (gun.am_grip_z +am.z);
		}
		if(entityplayer.isSneaking()) {
			gun.bure = gun.bure * am.bure_ads;
			gun.recoil = gun.recoil * am.recoil_ads;
		}else {
			gun.bure = gun.bure * am.bure;
			gun.recoil = gun.recoil * am.recoil;
		}
	}
}
