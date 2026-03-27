package gvclib.event.gun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import gvclib.mod_GVCLib;
import gvclib.item.ItemGunBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class GVCEventsGunRender_Setting {

	static int remat1 = 0;
	  static int reload_mat1k;
	  static String[] reload_mat1 = new String[1024];
	  
	  static int remat2 = 0;
	  static String[] reload_mat2 = new String[1024];
	  static int remat3 = 0;
	  static String[] reload_mat3 = new String[1024];
	  static int remat22 = 0;
	  static String[] reload_mat22 = new String[1024];
	  static int remat24 = 0;
	  static String[] reload_mat24 = new String[1024];
	  static int remat25 = 0;
	  static String[] reload_mat25 = new String[1024];
	  static int remat31 = 0;
	  static String[] reload_mat31 = new String[1024];
	  static int remat32 = 0;
	  static String[] reload_mat32 = new String[1024];
	  
	  static int rematleft = 0;
	  static String[] reload_matleft = new String[1024];
	  static int rematright = 0;
	  static String[] reload_matright = new String[1024];
	
	@SubscribeEvent
	public void reload_reloadEvent(LivingUpdateEvent event) {
		
		 remat1 = 0;
		 remat2 = 0;
		 remat3 = 0;
		 remat22 = 0;
		 remat24 = 0;
		 remat25 = 0;
		 remat31 = 0;
		 remat32 = 0;
		 rematleft = 0;
		 rematright = 0;
		 
		 reload_mat1 = new String[1024];
		 reload_mat2 = new String[1024];
		 reload_mat3 = new String[1024];
		 reload_mat22 = new String[1024];
		 reload_mat24 = new String[1024];
		 reload_mat25 = new String[1024];
		 reload_mat31 = new String[1024];
		 reload_mat32 = new String[1024];
		 reload_matleft = new String[1024];
		 reload_matright = new String[1024];
		
		EntityLivingBase target = event.getEntityLiving();
		ItemStack itemstack = target.getHeldItemMainhand();
		if (target instanceof EntityPlayer) {
			if (target != null && itemstack != null && itemstack.getItem() instanceof ItemGunBase) {
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					if (mod_GVCLib.proxy.setting()) {
						try {
				        	File newfile = new File(mod_GVCLib.proxy.ProxyFile(),"mods" + File.separatorChar + "test_reload.txt");
			            	if (checkBeforeReadfile(newfile))
			                {
			              	  BufferedReader br = new BufferedReader(new FileReader(newfile));  // ファイルを開く
			                
			                String str;
			                while((str = br.readLine()) != null){  // 1行ずつ読み込む
			                  //System.out.println(str);
			              	  String[] type = str.split(",");
			              	  if (type.length != 0)
			                    {
			              		if (type[0].equals("Reload_mat1")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat1[ka + (20 * remat1)] = type[ka];
									}
									++remat1;
								}
			              		if (type[0].equals("Reload_mat2")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat2[ka + (20 * remat2)] = type[ka];
									}
									++remat2;
								}
								if (type[0].equals("Reload_mat3")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat3[ka + (20 * remat3)] = type[ka];;
									}
									++remat3;
								}
								if (type[0].equals("Reload_mat22")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat22[ka + (20 * remat22)] = type[ka];
									}
									++remat22;
								}
								if (type[0].equals("Reload_mat24")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat24[ka + (20 * remat24)] = type[ka];
									}
									++remat24;
								}
								if (type[0].equals("Reload_mat25")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat25[ka + (20 * remat25)] = type[ka];
									}
									++remat25;
								}
								
								if (type[0].equals("Reload_mat31")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat31[ka + (20 * remat31)] = type[ka];
									}
									++remat31;
								}
								if (type[0].equals("Reload_mat32")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_mat32[ka + (20 * remat32)] = type[ka];
									}
									++remat32;
								}
								
								if (type[0].equals("Reload_matLeftarm")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_matleft[ka + (20 * rematleft)] = type[ka];
									}
									++rematleft;
								}
								if (type[0].equals("Reload_matRightarm")) {
									for(int ka = 0; ka <= 19; ++ka){
										reload_matright[ka + (20 * rematright)] = type[ka];
									}
									++rematright;
								}
			                    }
			                }

							if(remat1 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat1[ka] = "0";
								}
							}
							if(remat2 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat2[ka] = "0";
								}
							}
							if(remat3 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat3[ka] = "0";
								}
							}
							if(remat22 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat22[ka] = "0";
								}
							}
							if(remat24 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat24[ka] = "0";
								}
							}
							if(remat25 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat25[ka] = "0";
								}
							}
							if(remat31 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat31[ka] = "0";
								}
							}
							if(remat32 == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_mat32[ka] = "0";
								}
							}
							if(rematleft == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_matleft[ka] = "0";
								}
							}
							if(rematright == 0){
								for(int ka = 0; ka <= 19; ++ka){
									reload_matright[ka] = "0";
								}
							}
							gun.mat_rk_0 = remat1;
							gun.mat_r_0 = reload_mat1;
							gun.mat_rk_2 = remat2;
							gun.mat_r_2 = reload_mat2;
							gun.mat_rk_3 = remat3;
							gun.mat_r_3 = reload_mat3;
							gun.mat_r_31 = reload_mat31;
							gun.mat_rk_31 = remat31;
							gun.mat_r_32 = reload_mat32;
							gun.mat_rk_32 = remat32;
							
							gun.mat_rk_22 = remat22;
							gun.mat_r_22 = reload_mat22;
							gun.mat_rk_24 = remat24;
							gun.mat_r_24 = reload_mat24;
							gun.mat_rk_25 = remat25;
							gun.mat_r_25 = reload_mat25;
							
							gun.mat_rk_leftarm = rematleft;
							gun.mat_r_leftarm = reload_matleft;
							gun.mat_rk_rightarm = rematright;
							gun.mat_r_rightarm = reload_matright;
							System.out.println(String.format("reload"));
			                br.close();
			                }
				        	} catch (FileNotFoundException ex) {
				                ex.printStackTrace();
				            } catch (IOException ex) {
				                ex.printStackTrace();
				            }
					}
					
				}
			}
		}
	}
	private static boolean checkBeforeReadfile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canRead()) {
				return true;
			}
		}

		return false;
	}
	/**/
	
	boolean push = false;
	boolean flag = false;
	int flagtime = 0;
	
	@SubscribeEvent
	public void onUPEvent(LivingUpdateEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		ItemStack itemstack = target.getHeldItemMainhand();
		if (target instanceof EntityPlayer) {
			if (target != null && itemstack != null && itemstack.getItem() instanceof ItemGunBase) {
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					NBTTagCompound nbt = target.getEntityData();
					int setting = nbt.getInteger("Gun_Setting");
					/*if (mod_GVCLib.proxy.setting()) {
						push = true;
					}
					if (!mod_GVCLib.proxy.setting() && push) {
						++setting;
						push = false;
					}*/
					/*if (mod_GVCLib.proxy.setting()) {
						++setting;
					}*/
					if (mod_GVCLib.proxy.setting() && flagtime >= 5) {
						++setting;
						flagtime = 0;
					}
					if(flagtime <= 6) {
						++flagtime;
					}
					
					if(setting >= 6){
						setting = 0;
					}
					nbt.setInteger("Gun_Setting", setting);
					if(setting == 1){
						if (mod_GVCLib.proxy.left()) {
							gun.model_x_ads = gun.model_x_ads + 0.01F;
						}
						if (mod_GVCLib.proxy.right()) {
							gun.model_x_ads = gun.model_x_ads - 0.01F;
						}
						if (mod_GVCLib.proxy.up()) {
							gun.model_y_ads = gun.model_y_ads + 0.01F;
						}
						if (mod_GVCLib.proxy.down()) {
							gun.model_y_ads = gun.model_y_ads - 0.01F;
						}
						if (mod_GVCLib.proxy.nine()) {
							gun.model_z_ads = gun.model_z_ads + 0.01F;
						}
						if (mod_GVCLib.proxy.three()) {
							gun.model_z_ads = gun.model_z_ads - 0.01F;
						}
						if (mod_GVCLib.proxy.LBRACKET()) {
							gun.model_z_ads = gun.model_z_ads + 0.01F;
						}
						if (mod_GVCLib.proxy.RBRACKET()) {
							gun.model_z_ads = gun.model_z_ads - 0.01F;
						}
					}else if(setting == 2){
						if (mod_GVCLib.proxy.left()) {
							gun.model_x_adsr = gun.model_x_adsr + 0.01F;
						}
						if (mod_GVCLib.proxy.right()) {
							gun.model_x_adsr = gun.model_x_adsr - 0.01F;
						}
						if (mod_GVCLib.proxy.up()) {
							gun.model_y_adsr = gun.model_y_adsr + 0.01F;
						}
						if (mod_GVCLib.proxy.down()) {
							gun.model_y_adsr = gun.model_y_adsr - 0.01F;
						}
						if (mod_GVCLib.proxy.nine()) {
							gun.model_z_adsr = gun.model_z_adsr + 0.01F;
						}
						if (mod_GVCLib.proxy.three()) {
							gun.model_z_adsr = gun.model_z_adsr - 0.01F;
						}
						if (mod_GVCLib.proxy.LBRACKET()) {
							gun.model_z_adsr = gun.model_z_adsr + 0.01F;
						}
						if (mod_GVCLib.proxy.RBRACKET()) {
							gun.model_z_adsr = gun.model_z_adsr - 0.01F;
						}
					}else if(setting == 3){
						if (mod_GVCLib.proxy.left()) {
							gun.model_x_adss = gun.model_x_adss + 0.01F;
						}
						if (mod_GVCLib.proxy.right()) {
							gun.model_x_adss = gun.model_x_adss - 0.01F;
						}
						if (mod_GVCLib.proxy.up()) {
							gun.model_y_adss = gun.model_y_adss + 0.01F;
						}
						if (mod_GVCLib.proxy.down()) {
							gun.model_y_adss = gun.model_y_adss - 0.01F;
						}
						if (mod_GVCLib.proxy.nine()) {
							gun.model_z_adss = gun.model_z_adss + 0.01F;
						}
						if (mod_GVCLib.proxy.three()) {
							gun.model_z_adss = gun.model_z_adss - 0.01F;
						}
						if (mod_GVCLib.proxy.LBRACKET()) {
							gun.model_z_adss = gun.model_z_adss + 0.01F;
						}
						if (mod_GVCLib.proxy.RBRACKET()) {
							gun.model_z_adss = gun.model_z_adss - 0.01F;
						}
					}else if(setting == 4){
						if (mod_GVCLib.proxy.left()) {
							gun.arm_l_posx = gun.arm_l_posx + 0.01F;
						}
						if (mod_GVCLib.proxy.right()) {
							gun.arm_l_posx = gun.arm_l_posx - 0.01F;
						}
						if (mod_GVCLib.proxy.up()) {
							gun.arm_l_posy = gun.arm_l_posy + 0.01F;
						}
						if (mod_GVCLib.proxy.down()) {
							gun.arm_l_posy = gun.arm_l_posy - 0.01F;
						}
						if (mod_GVCLib.proxy.nine()) {
							gun.arm_l_posz = gun.arm_l_posz + 0.01F;
						}
						if (mod_GVCLib.proxy.three()) {
							gun.arm_l_posz = gun.arm_l_posz - 0.01F;
						}
						if (mod_GVCLib.proxy.LBRACKET()) {
							gun.arm_l_posz = gun.arm_l_posz + 0.01F;
						}
						if (mod_GVCLib.proxy.RBRACKET()) {
							gun.arm_l_posz = gun.arm_l_posz - 0.01F;
						}
					}
					
					else if(setting == 5){
						if (mod_GVCLib.proxy.left()) {
							gun.Sprintoffsetx = gun.Sprintoffsetx + 0.01F;
						}
						if (mod_GVCLib.proxy.right()) {
							gun.Sprintoffsetx = gun.Sprintoffsetx - 0.01F;
						}
						if (mod_GVCLib.proxy.up()) {
							gun.Sprintoffsety = gun.Sprintoffsety + 0.01F;
						}
						if (mod_GVCLib.proxy.down()) {
							gun.Sprintoffsety = gun.Sprintoffsety - 0.01F;
						}
						if (mod_GVCLib.proxy.LBRACKET()) {
							gun.Sprintoffsetz = gun.Sprintoffsetz + 0.01F;
						}
						if (mod_GVCLib.proxy.RBRACKET()) {
							gun.Sprintoffsetz = gun.Sprintoffsetz - 0.01F;
						}
						
						if (mod_GVCLib.proxy.numpad_4()) {
							gun.Sprintrotationx = gun.Sprintrotationx + 1F;
						}
						if (mod_GVCLib.proxy.numpad_6()) {
							gun.Sprintrotationx = gun.Sprintrotationx - 1F;
						}
						if (mod_GVCLib.proxy.numpad_8()) {
							gun.Sprintrotationy = gun.Sprintrotationy + 1F;
						}
						if (mod_GVCLib.proxy.numpad_2()) {
							gun.Sprintrotationy = gun.Sprintrotationy - 1F;
						}
						if (mod_GVCLib.proxy.numpad_1()) {
							gun.Sprintrotationz = gun.Sprintrotationz + 1F;
						}
						if (mod_GVCLib.proxy.numpad_3()) {
							gun.Sprintrotationz = gun.Sprintrotationz - 1F;
						}
					}
					
					
					
				}
			}
		}

	}
}
