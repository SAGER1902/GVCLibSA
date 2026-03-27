package gvclib.event.gun;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_SWORD;
import gvclib.item.ItemGun_Shield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class GVCEventsGunRenderNew {
	
	//現在は未使用
	static void rendermain(EntityPlayer entityplayer, ItemStack itemstack, boolean side){
		int xxx = 1;
		if(!side){
			xxx = -1;
		}
		
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		ItemGunBase gun = (ItemGunBase) itemstack.getItem();
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean cocking = nbt.getBoolean("Cocking");
		int cockingtime = nbt.getInteger("CockingTime");
		boolean recoiled = nbt.getBoolean("Recoiled");
		int recoiledtime = nbt.getInteger("RecoiledTime");
		
		boolean left = nbt.getBoolean("LeftClick");
		boolean right = nbt.getBoolean("RightClick");
		boolean sidel =false;
		boolean sider = false;
		if(!side && left && itemstack.getItem() instanceof ItemGun_Shield){
			sidel = true;
		}
		if(side && right && itemstack.getItem() instanceof ItemGun_Shield){
			sider = true;
		}
		
		if(minecraft.gameSettings.thirdPersonView == 0 && gun.obj_true){
			if(mod_GVCLib.cfg_optifine)
			{
				//GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180-entityplayer.rotationYawHead, 0.0F, 1.0F, 0.0F);
				//GL11.glRotatef(-entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
				if(entityplayer.isSneaking()){
					GL11.glTranslatef(0F,(float)mod_GVCLib.cfg_optifineys,0F);
					GL11.glRotatef(-entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(0F,-(float)mod_GVCLib.cfg_optifineys,0F);
					GL11.glTranslatef(0F,(float)mod_GVCLib.cfg_optifineys,-0.05F);
				}else{
	        	GL11.glTranslatef(0F,(float)mod_GVCLib.cfg_optifiney,0F);
	        	GL11.glRotatef(-entityplayer.rotationPitch, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0F,-(float)mod_GVCLib.cfg_optifiney,0F);
				GL11.glTranslatef(0F,(float)mod_GVCLib.cfg_optifiney,-0.05F);
				}
			}
			GL11.glPushMatrix();//guns
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
	//		GL11.glScalef(0.5F, 0.5F, 0.5F);
	//		GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			
			//8/18
			if(!gun.knife && side && entityplayer.getHeldItemOffhand() == null)
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(0,0, -f1*5);
				GL11.glRotatef(f1*30, -1.0F, 0.0F, 1.0F);
			}
			if(!side && itemstack.getItem() instanceof ItemGun_SWORD)
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(0,0, -f1*5);
				GL11.glRotatef(f1*30, 1.0F, 0.0F, 1.0F);
			}
			
			if (itemstack.getItemDamage() == itemstack.getMaxDamage()) {//1s
				GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				//GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
				//GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
				for(int kais = 0; kais <= gun.mat_rk_0;++kais){
					renderreload(gun, kais, gun.mat_r_0);
				}
			}
			else if(sidel || sider){//ADS
				GL11.glTranslatef((ADS_X(gun) + 0.0F) * xxx,ADS_Y(gun), ADS_Z(gun));//GL11.glTranslatef((ADS_X(gun) + 0.02F) * xxx,ADS_Y(gun), ADS_Z(gun));
				GL11.glRotatef(gun.rotationx, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.rotationy * xxx, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.rotationz * xxx, 0.0F, 0.0F, 1.0F);
			}
			else if(entityplayer.isSneaking()){//ADS
				if(side && entityplayer.getHeldItemOffhand() != null){
					GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(00F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
				}else if(!side && entityplayer.getHeldItemMainhand() != null){
					GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(00F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
				}else{
					GL11.glTranslatef((ADS_X(gun) + 0.0F) * xxx,ADS_Y(gun), ADS_Z(gun));//GL11.glTranslatef((ADS_X(gun) + 0.02F) * xxx,ADS_Y(gun), ADS_Z(gun));
					GL11.glRotatef(gun.rotationx, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(gun.rotationy * xxx, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(gun.rotationz * xxx, 0.0F, 0.0F, 1.0F);
				}
			}else if(entityplayer.isSprinting()){
				GL11.glTranslatef(gun.model_x * xxx -0.5F,gun.model_y, gun.model_z);
				GL11.glTranslatef(gun.Sprintoffsetx * xxx, gun.Sprintoffsety, gun.Sprintoffsetz);
				GL11.glRotatef(-gun.Sprintrotationx, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.Sprintrotationy * xxx+180, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.Sprintrotationz, 0.0F, 0.0F, 1.0F);
				
			}
			else{
				
			GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
			
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(00F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-3F, 1.0F, 0.0F, 0.0F);
			}//1e
			if (!recoiled)
			{
				GL11.glRotatef(gun.jump, 1.0F, 0.0F, 0.0F);
			}
			
			boolean zoomrender;
			if(gun.am_sight){
				zoomrender = gun.am_zoomrender;
			}else{
				if(gun.true_mat4){
					zoomrender = gun.model_zoomrenderr;
				}else if(gun.true_mat5){
					zoomrender = gun.model_zoomrenders;
				}else{
					zoomrender = gun.model_zoomrender;
				}
			}
			
			
			
			GL11.glPushMatrix();//guns
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			if(entityplayer.isSneaking()){
				if(gun.scopezoom > 1.1F && zoomrender){
				}else{
				rendermat(entityplayer, itemstack, gun);//rendermat
				}
			}else{
				rendermat(entityplayer, itemstack, gun);//rendermat
			}
			GL11.glPopMatrix();//gune
			
			GL11.glPushMatrix();//flashs
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.flash_tex));
			GL11.glTranslatef(gun.flash_posx,gun.flash_posy, gun.flash_posz);//0,1,6
			if (!recoiled)
			{
				if(entityplayer.isSneaking()){
					if(!zoomrender){
						GL11.glTranslatef(0.0F, 0.0F, 0F);
					}
				}else{
					GL11.glTranslatef(0.0F, 0.0F, 0F);
				}
			}else{
				GL11.glTranslatef(0.0F, -100.0F, 0F);
			}
//			gun.flash_model.renderPart("flash");
			GL11.glPopMatrix();//flashe
			
			GL11.glPushMatrix();//arms
			ResourceLocation resourcelocation = ((AbstractClientPlayer)entityplayer).getLocationSkin();
			if (resourcelocation == null)
	        {
	            resourcelocation = new ResourceLocation("textures/entity/steve.png");
	        }
			Minecraft.getMinecraft().renderEngine.bindTexture(resourcelocation);
			if(entityplayer.isSneaking()){
				if(gun.scopezoom > 1.1F && zoomrender){
				}else{
					renderarm(entityplayer, itemstack, gun);//renderarm
				}
			}else{
				renderarm(entityplayer, itemstack, gun);//renderarm
			}
			GL11.glPopMatrix();//arme
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();//gune
			
		}
	}
	
	
	private static void rendermat(EntityPlayer entityplayer, ItemStack itemstack, ItemGunBase gun){
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean cocking = nbt.getBoolean("Cocking");
		int cockingtime = nbt.getInteger("CockingTime");
		boolean recoiled = nbt.getBoolean("Recoiled");
		int recoiledtime = nbt.getInteger("RecoiledTime");
		
		
		
		gun.obj_model.renderPart("mat1");
		
		renderattachment(entityplayer, itemstack, gun);
		if (!recoiled)
		{
			mat31mat32(gun, true);
			GL11.glTranslatef(0.0F, 0.0F, gun.model_cock_z);//0, 0, -0.4
			gun.obj_model.renderPart("mat2");
			GL11.glTranslatef(0.0F, 0.0F, -gun.model_cock_z);
			mat25(gun, false, cockingtime);
			rendersight(entityplayer, itemstack, gun);
		}else{
			mat31mat32(gun, false);
			if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
				gun.obj_model.renderPart("mat2");
				mat25(gun, false, cockingtime);
				rendersight(entityplayer, itemstack, gun);
			}else{
				mat25(gun, true, cockingtime);
				gun.obj_model.renderPart("mat24");
				{
					if(gun.mat22){
						GL11.glPushMatrix();//arms
						/*GL11.glTranslatef(gun.mat22offsetx, gun.mat22offsety, gun.mat22offsetz);
						GL11.glRotatef(gun.mat22rotationx, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(gun.mat22rotationy, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(gun.mat22rotationz, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(-gun.mat22offsetx, -gun.mat22offsety, -gun.mat22offsetz);
						*/
						for(int kais = 0; kais <= gun.mat_rk_22;++kais){
							renderreload(gun, kais, gun.mat_r_22);
						}
						rendersight(entityplayer, itemstack, gun);
						GL11.glPopMatrix();//arme
					}else{
						rendersight(entityplayer, itemstack, gun);
					}
				}
				GL11.glTranslatef(0.0F, 0.0F, gun.model_cock_z);
				//gun.obj_model.renderPart("mat2");
				GL11.glTranslatef(0.0F, 0.0F, -gun.model_cock_z);
				for(int kais = 0; kais <= gun.mat_rk_2;++kais){
					renderreload(gun, kais, gun.mat_r_2);
				}
				gun.obj_model.renderPart("mat2");
			}
		}
		
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
			gun.obj_model.renderPart("mat3");
		}else{

			for(int kais = 0; kais <= gun.mat_rk_3;++kais){
				renderreload(gun, kais, gun.mat_r_3);
			}
			
			//renderreload(gun, gun.mat_r_31);
			gun.obj_model.renderPart("mat3");
		}
	}
	
	
	
	private static void renderattachment(EntityPlayer entityplayer, ItemStack itemstack, ItemGunBase gun){
		
		if(gun.true_mat6){
			gun.obj_model.renderPart("mat6");
		}else if(gun.true_mat7){
			gun.obj_model.renderPart("mat7");
		}
		if(gun.true_mat8){
			gun.obj_model.renderPart("mat8");
		}
		
		if(gun.true_mat9){
			gun.obj_model.renderPart("mat9");
		}else if(gun.true_mat10){
			gun.obj_model.renderPart("mat10");
		}else if(gun.true_mat11){
			gun.obj_model.renderPart("mat11");
		}else{
			gun.obj_model.renderPart("mat21");
		}
	}
	
	private static void rendersight(EntityPlayer entityplayer, ItemStack itemstack, ItemGunBase gun){
		if(gun.am_sight){
			GL11.glPushMatrix();//glstart11
			GL11.glTranslatef(gun.am_sight_x, gun.am_sight_y, gun.am_sight_z);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.am_tex));
			gun.am_model.renderPart("sight");
			GL11.glTranslatef( - gun.am_sight_x,  - gun.am_sight_y,  - gun.am_sight_z);
			GL11.glPopMatrix();//glend11
		}else{
			if(gun.true_mat4){
				gun.obj_model.renderPart("mat4");
			}else if(gun.true_mat5){
				gun.obj_model.renderPart("mat5");
			}else{
				gun.obj_model.renderPart("mat20");
			}
		}
		gun.obj_model.renderPart("mat22");
	}
	
	private static void mat25(ItemGunBase gun, boolean recoil, int cockingtime){
		if(recoil){
			GL11.glPushMatrix();//glstart11
			{
				for(int kais = 0; kais <= gun.mat_rk_25;++kais){
					renderreload(gun, kais, gun.mat_r_25);
				}
				gun.obj_model.renderPart("mat25");
			}
			GL11.glPopMatrix();//glend11
		}else{

			GL11.glPushMatrix();//glstart11
			if(cockingtime <= 0){
				gun.obj_model.renderPart("mat25");
			}else{
				GL11.glTranslatef(gun.mat25offsetx, gun.mat25offsety, gun.mat25offsetz);
				GL11.glRotatef(gun.mat25rotationx, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.mat25rotationy, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.mat25rotationz, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-gun.mat25offsetx, -gun.mat25offsety, -gun.mat25offsetz);
				if(cockingtime > 0 && cockingtime < (gun.cocktime/2)){
					GL11.glTranslatef(0F, 0F, -cockingtime*0.1F);
				}else{
					GL11.glTranslatef(0F, 0F, (cockingtime-gun.cocktime)*0.1F);
				}
				gun.obj_model.renderPart("mat25");
			}
			GL11.glPopMatrix();//glend11
		}
	}
	
	private static void mat31mat32(ItemGunBase gun, boolean recoil){
		if(gun.rote < 360F || gun.rotex < 360F || gun.rotey < 360F)
		{
			GL11.glPushMatrix();//glstart1
		GL11.glTranslatef(gun.mat31posx, gun.mat31posy, gun.mat31posz);//0,0.7,0
		GL11.glRotatef(gun.rotey, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(gun.rotex, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(gun.rote, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-gun.mat31posx, -gun.mat31posy, -gun.mat31posz);
		{
			gun.obj_model.renderPart("mat31");
		}
		GL11.glRotatef(-gun.rotey, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-gun.rotex, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-gun.rote, 0.0F, 1.0F, 0.0F);
		if(recoil){
			gun.rote = gun.rote + gun.mat31rotez;//60F
			gun.rotex = gun.rotex + gun.mat31rotex;
			gun.rotey = gun.rotey + gun.mat31rotey;
		}
		GL11.glPopMatrix();//glend1
		GL11.glPushMatrix();//glstrt2
		GL11.glTranslatef(gun.mat32posx, gun.mat32posy, gun.mat32posz);//0,0.5,0
		GL11.glRotatef(gun.mat32rotey, 0.0F, 1.0F, 0.0F);//90
		GL11.glRotatef(gun.mat32rotez, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(gun.mat32rotex, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(-gun.mat32posx, -gun.mat32posy, -gun.mat32posz);
		gun.obj_model.renderPart("mat32");
		GL11.glPopMatrix();//glend2
		}else{
			gun.rote = 0;
			gun.rotey = 0;
			gun.rotex = 0;
		}
	}
	
	
	public static float ADS_X(ItemGunBase gun){
		if(gun.am_sight){
			return gun.model_x_ads;
		}else{
			if(gun.true_mat4){
				return gun.model_x_adsr;
			}else if(gun.true_mat5){
				return gun.model_x_adss;
			}else{
				return gun.model_x_ads;
			}
		}
	}
	public static float ADS_Y(ItemGunBase gun){
		if(gun.am_sight){
			return gun.model_y_ads;
		}else{
			if(gun.true_mat4){
				return gun.model_y_adsr;
			}else if(gun.true_mat5){
				return gun.model_y_adss;
			}else{
				return gun.model_y_ads;
			}
		}
	}
	public static float ADS_Z(ItemGunBase gun){
		if(gun.am_sight){
			return gun.model_z_ads;
		}else{
			if(gun.true_mat4){
				return gun.model_z_adsr;
			}else if(gun.true_mat5){
				return gun.model_z_adss;
			}else{
				return gun.model_z_ads;
			}
		}
	}
	
	
	private static void renderarm(EntityPlayer entityplayer, ItemStack itemstack, ItemGunBase gun){
		ItemStack leftitem = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
		{
			GL11.glPushMatrix();//glstart1
			if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
				GL11.glTranslatef(gun.arm_r_posx,gun.arm_r_posy, gun.arm_r_posz);
			}else{
				for(int kais = 0; kais <= gun.mat_rk_rightarm;++kais){
					renderreload(gun, kais, gun.mat_r_rightarm);
				}
			}
			gun.arm_model.renderPart("rightarm");
			GL11.glPopMatrix();//glend1
		
		
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
			GL11.glTranslatef(gun.arm_l_posx,gun.arm_l_posy, gun.arm_l_posz);
			if(gun.cock_left){
				NBTTagCompound nbt = itemstack.getTagCompound();
				int cockingtime = nbt.getInteger("CockingTime");
	        	if(cockingtime <= 0){
				}else{
					if(cockingtime > 0 && cockingtime < (gun.cocktime/2)){
						GL11.glTranslatef(0F, 0F, -cockingtime*0.1F);
					}else{
						GL11.glTranslatef(0F, 0F, (cockingtime-gun.cocktime)*0.1F);
					}
				}
	        }
			
			if(gun.knife && entityplayer.swingProgress > 0.0F)
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(f1*2,0, f1*3);
				GL11.glRotatef(60, 0.0F, 1.0F, 0.0F);
			}
			if(leftitem == null){
				gun.arm_model.renderPart("leftarm");
			}
			
		}else{
			for(int kais = 0; kais <= gun.mat_rk_leftarm;++kais){
				renderreload(gun, kais, gun.mat_r_leftarm);
			}
			gun.arm_model.renderPart("leftarm");
		}
		}
	}
	
	
	private static void renderreload(ItemGunBase gun, int kaisu, String[] mat){
		if(kaisu != 0){
			kaisu = kaisu - 1;
		}
		int ka = 20 * kaisu; 
		if(mat != null){
			float bai = Float.parseFloat(mat[18 + ka]);
			float bairote = Float.parseFloat(mat[19 + ka]);
			if(gun.retime >= Integer.parseInt(mat[1 + ka]) && gun.retime <= Integer.parseInt(mat[2 + ka])){
				int time = gun.retime - Integer.parseInt(mat[1 + ka]);
				GL11.glTranslatef(Float.parseFloat(mat[3 + ka]),Float.parseFloat(mat[4 + ka]),Float.parseFloat(mat[5 + ka]));
				GL11.glTranslatef(Float.parseFloat(mat[6 + ka]),Float.parseFloat(mat[7 + ka]),Float.parseFloat(mat[8 + ka]));
				GL11.glRotatef(Float.parseFloat(mat[9 + ka]) + (time * bairote * Float.parseFloat(mat[15 + ka])), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(Float.parseFloat(mat[10 + ka]) + (time * bairote * Float.parseFloat(mat[16 + ka])), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(Float.parseFloat(mat[11 + ka]) + (time * bairote * Float.parseFloat(mat[17 + ka])), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(
						time * Float.parseFloat(mat[12 + ka]) * bai,
						time * Float.parseFloat(mat[13 + ka]) * bai,
						time * Float.parseFloat(mat[14 + ka]) * bai
						);
				GL11.glTranslatef(-Float.parseFloat(mat[3 + ka]),-Float.parseFloat(mat[4 + ka]),-Float.parseFloat(mat[5 + ka]));
			}
		}
	}
}