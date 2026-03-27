package gvclib;


import gvclib.item.ItemGunBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import objmodel.AdvancedModelLoader;

public class LoadGunStatusBase {
	
	  
	  
	public static void load(ItemGunBase gun, RegistryEvent.Register<Item> event, String text, String[] type, String scopedomain, String entitydomain) {
		//reset
		{
			
		}
		
		mod_GVCLib mod = mod_GVCLib.INSTANCE;
		if(mod.proxy.getClient()) {
			gun.arm_model = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/arm/arm_64.mqo"));
			gun.arm_model_alex = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/arm/arm_alex.mqo"));
			gun.arm_model_lmm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/arm/arm_lmm.mqo"));
		}
		
		net.minecraftforge.fml.common.ProgressManager.ProgressBar bar= net.minecraftforge.fml.common.ProgressManager.push("Gun Loading", 1, true);
					if (type.length != 0) {// 1
						
						
						
						if (type[0].equals("information1")) {
							gun.information1 = type[1];
						}
						if (type[0].equals("information2")) {
							gun.information2 = type[1];
						}
						if (type[0].equals("information3")) {
							gun.information3 = type[1];
						}

						if (type[0].equals("CanDualHand")) {
							gun.can_dual_hand = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Knife")) {
							gun.knife = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Bullet_Type")) {
							gun.gun_type = gun.mobbullettype = Integer.parseInt(type[1]);
						}
						if (type[0].equals("BulletPower")) {
							gun.powor = Integer.parseInt(type[1]);
						}
						if (type[0].equals("BulletSpeed")) {
							gun.speed = Float.parseFloat(type[1]);
						}
						if (type[0].equals("BlletSpread")) {
							gun.bure = Float.parseFloat(type[1]);
						}
						if (type[0].equals("BlletSpread_ADS")) {
							gun.bure_ads = Float.parseFloat(type[1]);
						}
						if (type[0].equals("Recoil")) {
							gun.recoil = Double.parseDouble(type[1]);
						}
						if (type[0].equals("Recoil_ADS")) {
							gun.recoil_ads = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ReloadTime")) {
							gun.reloadtime = Integer.parseInt(type[1]);
						}
						if (type[0].equals("RemainingBullet")) {
							gun.setMaxDamage(Integer.parseInt(type[1]));
						}
						if (type[0].equals("Attacking")) {
							gun.attackDamage = Float.parseFloat(type[1]);
						}
						if (type[0].equals("AttackingSpeed")) {
							gun.attackSpeed = Double.parseDouble(type[1]);
						}
						if (type[0].equals("Motion")) {
							gun.motion = Double.parseDouble(type[1]);
						}
						if (type[0].equals("Zoom")) {
							gun.scopezoom = Float.parseFloat(type[1]);
							gun.scopezoombase = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ZoomRender")) {
							gun.model_zoomrender = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ZoomRenderType")) {
							gun.zoomre = Boolean.parseBoolean(type[1]);
							gun.zoomren = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ZoomRenderTypeTxture")) {
							gun.zoomrent = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Cycle")) {
							gun.cycle = gun.cocktime = gun.mobcycle = Integer.parseInt(type[1]);
						}
						if (type[0].equals("CanBarst")) {
							gun.canbarst = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Barst_Cycle")) {
							gun.barst_cycle = Integer.parseInt(type[1]);
						}
						if (type[0].equals("Barst_Limit")) {
							gun.barst_limit = gun.mobbarst = Integer.parseInt(type[1]);
						}
						
						if (type[0].equals("Explosion")) {
							gun.exlevel = Float.parseFloat(type[1]);
						}
						if (type[0].equals("BlockDestory")) {
							gun.ex = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("GunSound")) {
							gun.sound = type[1];
							gun.soundbase = type[1];
							gun.soundsu = type[2];
						}
						if (type[0].equals("GunSoundReload")) {
							gun.soundre = type[1];
						}
						if (type[0].equals("GunSoundCooking")) {
							gun.soundco = type[1];
						}
						
						
						if (type[0].equals("Explosion_Fire")) {
							gun.exfire = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Explosion_Flash")) {
							gun.exflash = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Explosion_Gas")) {
							gun.exgas = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("Muzzle_Flash")) {
							gun.muzzle_flash = Boolean.parseBoolean(type[1]);
						}
						
						//手榴弾専用
						if (type[0].equals("Explosion_Smoke")) {
							gun.exsmoke = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("Bullet_living_time")) {
							gun.bullet_time_max = Integer.parseInt(type[1]);
						}
						
						if(type[0].equals("BulletPotionID")){
							if(Integer.parseInt(type[1]) != 0)gun.p_id = Integer.parseInt(type[1]);
	            		  }
						if (type[0].equals("PotionLevel")) {
							if(Integer.parseInt(type[1]) != 0)gun.p_level = Integer.parseInt(type[1]);
						}
						if (type[0].equals("PotionTime")) {
							if(Integer.parseInt(type[1]) != 0)gun.p_time = Integer.parseInt(type[1]);
						}
						
						
						//modelset
						if (type[0].equals("ModelEquipped")) {
							gun.model_x = Float.parseFloat(type[1]);
							gun.model_y = Float.parseFloat(type[2]);
							gun.model_z = Float.parseFloat(type[3]);
						}
						if (type[0].equals("ModelHigh")) {
							gun.model_y_ads = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelWidthX")) {
							gun.model_x_ads = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelWidthZ")) {
							gun.model_z_ads = Float.parseFloat(type[1]);
						}
						
						if (type[0].equals("ModelHigh_mat4")) {
							gun.model_y_adsr = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelWidthX_mat4")) {
							gun.model_x_adsr = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelWidthZ_mat4")) {
							gun.model_z_adsr = Float.parseFloat(type[1]);
						}
						if (type[0].equals("Zoom_mat4")) {
							gun.scopezoomred = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ZoomRender_mat4")) {
							gun.model_zoomrenderr = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ZoomRenderType_mat4")) {
							gun.zoomrer = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ZoomRenderTypeTxture_mat4")) {
							gun.zoomrert = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("ModelHigh_mat5")) {
							gun.model_y_adss = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelWidthX_mat5")) {
							gun.model_x_adss = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelWidthZ_mat5")) {
							gun.model_z_adss = Float.parseFloat(type[1]);
						}
						if (type[0].equals("Zoom_mat5")) {
							gun.scopezoomscope = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ZoomRender_mat5")) {
							gun.model_zoomrenders = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ZoomRenderType_mat5")) {
							gun.zoomres = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ZoomRenderTypeTxture_mat5")) {
							gun.zoomrest = Boolean.parseBoolean(type[1]);
						}
						
						
						if (type[0].equals("ModelRotationX")) {
							gun.rotationx = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelRotationY")) {
							gun.rotationy = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelRotationZ")) {
							gun.rotationz = Float.parseFloat(type[1]);
						}
						
						//arm
						if (type[0].equals("ModelArm")) {
							gun.canarm = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ModelArmOffsetR")) {
							gun.arm_r_posx = Float.parseFloat(type[1]);
							gun.arm_r_posy = Float.parseFloat(type[2]);
							gun.arm_r_posz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("ModelArmOffsetL")) {
							gun.arm_l_posx = Float.parseFloat(type[1]);
							gun.arm_l_posy = Float.parseFloat(type[2]);
							gun.arm_l_posz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("ModelArmScaleR")) {
							gun.arm_scale_r = Float.parseFloat(type[1]);
						}
						if (type[0].equals("ModelArmScaleL")) {
							gun.arm_scale_l = Float.parseFloat(type[1]);
						}
						
						if (type[0].equals("Mat31Point")) {
							gun.mat31posx = Float.parseFloat(type[1]);
							gun.mat31posy = Float.parseFloat(type[2]);
							gun.mat31posz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Mat31Rotation")) {
							gun.mat31rotex = Float.parseFloat(type[1]);
							gun.mat31rotey = Float.parseFloat(type[2]);
							gun.mat31rotez = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Mat32Point")) {
							gun.mat32posx = Float.parseFloat(type[1]);
							gun.mat32posy = Float.parseFloat(type[2]);
							gun.mat32posz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Mat32Rotation")) {
							gun.mat32rotex = Float.parseFloat(type[1]);
							gun.mat32rotey = Float.parseFloat(type[2]);
							gun.mat32rotez = Float.parseFloat(type[3]);
						}
						
						if (type[0].equals("Mat22")) {
							gun.mat22 = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Mat22Point")) {
							gun.mat22offsetx = Float.parseFloat(type[1]);
							gun.mat22offsety = Float.parseFloat(type[2]);
							gun.mat22offsetz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Mat22Rotation")) {
							gun.mat22rotationx = Float.parseFloat(type[1]);
							gun.mat22rotationy = Float.parseFloat(type[2]);
							gun.mat22rotationz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Mat25Point")) {
							gun.mat25offsetx = Float.parseFloat(type[1]);
							gun.mat25offsety = Float.parseFloat(type[2]);
							gun.mat25offsetz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Mat25Rotation")) {
							gun.mat25rotationx = Float.parseFloat(type[1]);
							gun.mat25rotationy = Float.parseFloat(type[2]);
							gun.mat25rotationz = Float.parseFloat(type[3]);
						}
						
						if (type[0].equals("SprintingPoint")) {
							gun.Sprintoffsetx = Float.parseFloat(type[1]);
							gun.Sprintoffsety = Float.parseFloat(type[2]);
							gun.Sprintoffsetz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("SprintingRotation")) {
							gun.Sprintrotationx = Float.parseFloat(type[1]);
							gun.Sprintrotationy = Float.parseFloat(type[2]);
							gun.Sprintrotationz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("ShotGun_Pellet")) {
							gun.shotgun_pellet = Integer.parseInt(type[1]);
						}
						
						if (type[0].equals("Fire_Offset_y")) {
							gun.fire_roteoffset_y = Float.parseFloat(type[1]);
						}
						
						if (type[0].equals("Cartridge")) {
							gun.bullet_c = Boolean.parseBoolean(type[1]);
						}
						
						//01/27
						if (type[0].equals("MuzzleJump")) {
							gun.jump = Float.parseFloat(type[1]);
						}
						if (type[0].equals("Mat2")) {
							gun.mat2 = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Mat25")) {
							gun.mat25 = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Mat25Move")) {
							gun.mat25move = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("CockedLeftHand")) {
							gun.cock_left = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ALLCocked")) {
							gun.all_jump = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("BulletGravity")) {
							gun.gra = Float.parseFloat(type[1]);
						}
						
						//0214
						if (type[0].equals("CartridgeType")) {
							gun.c_type = Integer.parseInt(type[1]);
						}
						if (type[0].equals("DropMagazine")) {
							gun.canmagazine = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("ReloadMat31")) {
							gun.remat31 = Boolean.parseBoolean(type[1]);
						}
						
						
						
						
						//
						if (type[0].equals("ScopeTexture")) {
							gun.adstexture = scopedomain +  type[1];
						}
						if (type[0].equals("ScopeTexture_mat4")) {
							gun.adstexturer = scopedomain +  type[1];
						}
						if (type[0].equals("ScopeTexture_mat5")) {
							gun.adstextures = scopedomain +  type[1];
						}
						if (type[0].equals("RenderCross")) {
							gun.rendercross = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Automatic")) {
							gun.semi = Boolean.parseBoolean(type[1]);
						}

						if (type[0].equals("Magazine")) {
							int ii = Integer.parseInt(type[1]);
							if (ii == 0) {
								gun.magazine = event.getRegistry().getValue(new ResourceLocation(type[2], type[3]));
							}
						}
						if (type[0].equals("Magazine_defalt")) {
							if(gun.magazine == null) {
								int ii = Integer.parseInt(type[1]);
								if (ii == 0) {
									gun.magazine = event.getRegistry().getValue(new ResourceLocation(type[2], type[3]));
								}
							}
						}
						if (type[0].equals("MagazineSG")) {
							int ii = Integer.parseInt(type[1]);
							if (ii == 0) {
								gun.magazinesg =  event.getRegistry().getValue(new ResourceLocation(type[2], type[3]));
							}
						}
						if (type[0].equals("MagazineGL")) {
							int ii = Integer.parseInt(type[1]);
							if (ii == 0) {
								gun.magazinegl =  event.getRegistry().getValue(new ResourceLocation(type[2], type[3]));
							}
						}
						
						//gun.reload_type = 0;
						if (type[0].equals("Reload_type")) {
							gun.reload_type = Integer.parseInt(type[1]);
						}
						
						
						if (type[0].equals("Fire_point")) {
							gun.fire_posx = Float.parseFloat(type[1]);
							gun.fire_posy = Float.parseFloat(type[2]);
							gun.fire_posz = Float.parseFloat(type[3]);
						}
						if (type[0].equals("Fire_point_ADS")) {
							gun.fire_posx_ads = Float.parseFloat(type[1]);
							gun.fire_posy_ads = Float.parseFloat(type[2]);
							gun.fire_posz_ads = Float.parseFloat(type[3]);
						}
						
						if (type[0].equals("Bullet_Model")) {
							gun.bullet_model = entitydomain + type[1];
						}
						if (type[0].equals("Bullet_Texture")) {
							gun.bullet_tex = entitydomain + type[1];
						}
						if (type[0].equals("Flash_Model")) {
							gun.bulletf_model = entitydomain + type[1];
						}
						if (type[0].equals("Flash_Texture")) {
							gun.bulletf_tex = entitydomain + type[1];
						}
						if (type[0].equals("Cartridge_Model")) {
							gun.cartridge_model = entitydomain + type[1];
						}
						if (type[0].equals("Cartridge_Texture")) {
							gun.cartridge_tex =entitydomain +  type[1];
						}
						if (type[0].equals("SightSetPoint")) {
							gun.am_sight_x = gun.am_sightbase_x = Float.parseFloat(type[1]);
							gun.am_sight_y = gun.am_sightbase_y = Float.parseFloat(type[2]);
							gun.am_sight_z = gun.am_sightbase_z = Float.parseFloat(type[3]);
						}
						if (type[0].equals("LightSetPoint")) {
							gun.am_light_x = Float.parseFloat(type[1]);
							gun.am_light_y = Float.parseFloat(type[2]);
							gun.am_light_z = Float.parseFloat(type[3]);
						}
						if (type[0].equals("LightSetAngle")) {
							gun.am_light_xr = Float.parseFloat(type[1]);
							gun.am_light_yr = Float.parseFloat(type[2]);
							gun.am_light_zr = Float.parseFloat(type[3]);
						}
						if (type[0].equals("MuzzleSetPoint")) {
							gun.am_supu_x = Float.parseFloat(type[1]);
							gun.am_supu_y = Float.parseFloat(type[2]);
							gun.am_supu_z = Float.parseFloat(type[3]);
						}
						if (type[0].equals("MuzzleSetAngle")) {
							gun.am_supu_xr = Float.parseFloat(type[1]);
							gun.am_supu_yr = Float.parseFloat(type[2]);
							gun.am_supu_zr = Float.parseFloat(type[3]);
						}
						if (type[0].equals("GripSetPoint")) {
							gun.am_grip_x = Float.parseFloat(type[1]);
							gun.am_grip_y = Float.parseFloat(type[2]);
							gun.am_grip_z = Float.parseFloat(type[3]);
						}
						if (type[0].equals("GripSetAngle")) {
							gun.am_grip_xr = Float.parseFloat(type[1]);
							gun.am_grip_yr = Float.parseFloat(type[2]);
							gun.am_grip_zr = Float.parseFloat(type[3]);
						}
						
						if (type[0].equals("Mugen")) {
							gun.mugen = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("MugenMag")) {
							gun.mugenmaga = Boolean.parseBoolean(type[1]);
						}
						
						
						if (type[0].equals("Shield_defence")) {
							gun.Shield_defence = Float.parseFloat(type[1]);
						}
						if (type[0].equals("AAM")) {
							gun.aam = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("Render_Sight")) {
							gun.render_sight = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_Light")) {
							gun.render_light = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_Muzz")) {
							gun.render_muss = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_Grip")) {
							gun.render_grip = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_mat4_default")) {
							gun.render_mat4_unam = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_mat6_default")) {
							gun.render_mat6_unam = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_mat8_default")) {
							gun.render_mat8_unam = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Render_mat9_default")) {
							gun.render_mat9_unam = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("Render_crosshair_ADS")) {
							gun.render_cross_sneak = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("RenderingLight")) {
							gun.rendering_light = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("gun_can_use_underbarrel")) {
							gun.gun_can_use_underbarrel = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("gun_can_get_underbarrel")) {
							gun.gun_can_get_underbarrel = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("gun_can_set_underbarrel")) {
							gun.gun_can_set_underbarrel = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("gun_underbarrel_x")) {
							gun.gun_underbarrel_x = Float.parseFloat(type[1]);
						}
						if (type[0].equals("gun_underbarrel_y")) {
							gun.gun_underbarrel_y = Float.parseFloat(type[1]);
						}
						if (type[0].equals("gun_underbarrel_z")) {
							gun.gun_underbarrel_z = Float.parseFloat(type[1]);
						}
						
						if (type[0].equals("Render_Gun_CrossHair")) {
							gun.render_gvc_cross = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("Mob_maxrange")) {
							gun.mobmax = Integer.parseInt(type[1]);
						}
						if (type[0].equals("Mob_lookrange")) {
							gun.mobrange = Integer.parseInt(type[1]);
						}
						if (type[0].equals("Mob_cooltime")) {
							gun.mobammo = Integer.parseInt(type[1]);
						}
						if (type[0].equals("Mob_barst")) {
							gun.mobbarst = Integer.parseInt(type[1]);
						}
						
						
						if (type[0].equals("Render_Default_Light")) {
							gun.render_default_light = Boolean.parseBoolean(type[1]);
							gun.am_light = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("Light_KAZU")) {
							gun.am_light_kazu = Integer.parseInt(type[1]);
						}
						if (type[0].equals("ColorLevel")) {
							for(int ii = 0; ii < gun.am_light_kazu; ++ii) {
								gun.am_colorlevel[ii] = Float.parseFloat(type[1 + ii]);
							}
						}
						
						if (type[0].equals("Bullet_KAZU")) {
							gun.canuse_bullet_kazu = Integer.parseInt(type[1]);
						}
						if (type[0].equals("Use_Bullet")) {
							for(int ii = 0; ii < gun.canuse_bullet_kazu; ++ii) {
								gun.canuse_bullet[ii] = type[1 + ii];
							}
						}
						
						if (type[0].equals("attachmentSight")) {
							gun.canuse_sight = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("attachmentLight")) {
							gun.canuse_light = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("attachmentMuzz")) {
							gun.canuse_muss = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("attachmentGrip")) {
							gun.canuse_grip = Boolean.parseBoolean(type[1]);
						}
						
						if (type[0].equals("TechgunFx")) {//子弹所附带的科技枪特效名称
							gun.techgunfx = type[1];
						}
						if (type[0].equals("UsePluginA")) {//启用A类型插件（默认开启）
							gun.canuse_plugin_a = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("UsePluginB")) {//启用B类型插件（默认开启）
							gun.canuse_plugin_b = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("PluginA_Count")) {//可用A类型插件槽位数量（默认4）
							gun.plugin_a_count = Integer.parseInt(type[1]);
						}
						if (type[0].equals("PluginB_Count")) {//可用B类型插件槽位数量（默认4）
							gun.plugin_b_count = Integer.parseInt(type[1]);
						}
						if (type[0].equals("CanAimFire")) {//瞄准动画时能否开火（默认开启）
							gun.aim_fire = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("AimTime")) {//瞄准所需时间（默认6tick）
							gun.time_aim = Float.parseFloat(type[1]);
						}
						if (type[0].equals("Model_CockZ")) {//枪械整体前后动画幅度
							gun.model_cock_z = Float.parseFloat(type[1]);
						}
						if (type[0].equals("Mat2_CockZ")) {//mat2动画幅度
							gun.mat2_cock_z = Float.parseFloat(type[1]);
						}
						
						
						if (type[0].equals("attachment_group_kazu")) {
							gun.designated_kazu = Integer.parseInt(type[1]);
						}
						if (type[0].equals("attachment_group")) {
							for(int ii = 0; ii < gun.designated_kazu; ++ii) {
								gun.designated_attachment_name[ii] = type[1 + ii];
							}
						}
						if (type[0].equals("am_group_canuse_commonc_Sight")) {
							gun.designated_mat4 = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("am_group_canuse_commonc_Light")) {
							gun.designated_mat6 = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("am_group_canuse_commonc_Muzz")) {
							gun.designated_mat8 = Boolean.parseBoolean(type[1]);
						}
						if (type[0].equals("am_group_canuse_commonc_Grip")) {
							gun.designated_mat9 = Boolean.parseBoolean(type[1]);
						}
						
						
						
					}
					bar.step("gun load");
					net.minecraftforge.fml.common.ProgressManager.pop(bar);
				}
	

	
}
