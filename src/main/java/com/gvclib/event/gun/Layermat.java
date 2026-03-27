package gvclib.event.gun;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.item.ItemGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import objmodel.IModelCustom;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.util.Random;
import gvclib.render.SARenderHelper;
import gvclib.render.SARenderHelper.RenderType;
import gvclib.item.ItemGun_SR;

import gvclib.RenderParameters;
import static gvclib.RenderParameters.*;
public class Layermat {
	public static void rendersight(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
		boolean rendering = true;
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound()) {
			gun.obj_model.renderPart("mat20");
			gun.obj_model.renderPart("mat22");
		}else {
		boolean am_sight = nbt.getBoolean("am_sight");
		if(!gun.rendering_light_am_sight && gun.rendering_light) {
			GL11.glDisable(GL11.GL_LIGHT1);
			GL11.glDisable(GL11.GL_LIGHTING);
			rendering = false;
		}
		if(am_sight){
			GL11.glPushMatrix();//glstart11
			GL11.glTranslatef(gun.am_sight_x, gun.am_sight_y, gun.am_sight_z);
			boolean mo = false;
			
			String model = nbt.getString("am_model");
			String tex = nbt.getString("am_tex");
			IModelCustom am_model = null;
			ResourceLocation am_tex = null;
			for(int ii = 0; ii < 1024; ++ii) {
	    		if(mod_GVCLib.am_name[ii] != null && mod_GVCLib.am_name[ii].equals(model)) {
	    			am_model  = mod_GVCLib.am_model[ii];
	    			am_tex  = mod_GVCLib.am_tex[ii];
	    			mo = true;
	    			break;
	    		}
	    	}
			if(am_model != null && am_tex != null){
				Minecraft.getMinecraft().renderEngine.bindTexture(am_tex);
				am_model.renderPart("sight");
				{
					if(rendering) {
						GL11.glDisable(GL11.GL_LIGHT1);
						GL11.glDisable(GL11.GL_LIGHTING);
					}
					am_model.renderPart("dot");
					if(rendering) {
						GL11.glEnable(GL11.GL_LIGHTING);
						GL11.glEnable(GL11.GL_LIGHT1);
					}
				}
			}
			GL11.glTranslatef( - gun.am_sight_x,  - gun.am_sight_y,  - gun.am_sight_z);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			GL11.glPopMatrix();//glend11
			gun.obj_model.renderPart("mat41");
		}else{
			if(gun.true_mat4){
				gun.obj_model.renderPart("mat4");
				if(gun.rendering_light) {
					GL11.glDisable(GL11.GL_LIGHT1);
					GL11.glDisable(GL11.GL_LIGHTING);
				}
				gun.obj_model.renderPart("mat4_dot");
				if(gun.rendering_light) {
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_LIGHT1);
				}
			}else if(gun.true_mat5){
				gun.obj_model.renderPart("mat5");
				if(gun.rendering_light) {
					GL11.glDisable(GL11.GL_LIGHT1);
					GL11.glDisable(GL11.GL_LIGHTING);
				}
				gun.obj_model.renderPart("mat5_dot");
				if(gun.rendering_light) {
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_LIGHT1);
				}
			}else{
				gun.obj_model.renderPart("mat20");
				{
	            	if(gun.rendering_light) {
	        			GL11.glDisable(GL11.GL_LIGHT1);
	        			GL11.glDisable(GL11.GL_LIGHTING);
	        		}
	            	gun.obj_model.renderPart("mat20_dot");
	            	if(gun.rendering_light) {
	            		GL11.glEnable(GL11.GL_LIGHTING);
	        			GL11.glEnable(GL11.GL_LIGHT1);
	        		}
	            }
			}
		}
		gun.obj_model.renderPart("mat22");
		if(!gun.rendering_light_am_sight && gun.rendering_light) {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT1);
		}
		}
	}
	public static int flashInt = 0;
    public static void renderattachment(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
    	NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
		boolean am_sight = nbt.getBoolean("am_sight");
//SA第三人称开火渲染		
		boolean recoiled = nbt.getBoolean("Recoiled");
		//int gunfire = entityplayer.world.rand.nextInt(4);
		boolean cocking = nbt.getBoolean("Cocking");
		Random r = new Random();
		int Low = 1;
		int High = 5;
		int result = r.nextInt(High - Low) + Low;
		flashInt = result;
		String tu2 = String.valueOf(flashInt);
		if(!recoiled){
				GL11.glPushMatrix();//glstrt
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.bulletf_tex));
				//Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("gvclib:textures/test/mwflash/newflash"+tu2+".png"));
			SARenderHelper.enableBlendMode(RenderType.ADDITIVE);
            	float size = flashInt * 0.1F + 1;
            	GlStateManager.scale(size, size, size*0.75F);
            	gun.obj_model.renderPart("fire_1");
				//gun.obj_model.renderPart("flash_random");
			SARenderHelper.disableBlendMode(RenderType.ADDITIVE);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				GL11.glPopMatrix();//glend
		}
		
		if(gun.true_mat8){
    		if(!gun.rendering_light_am_muzzle && gun.rendering_light) {
    			GL11.glDisable(GL11.GL_LIGHT1);
    			GL11.glDisable(GL11.GL_LIGHTING);
    		}
    		boolean am_supu = nbt.getBoolean("am_supu");
    		if(am_supu){
			String model = nbt.getString("am_supu_model");
			String tex = nbt.getString("am_supu_tex");
			IModelCustom am_model = null;
			ResourceLocation am_tex = null;
			for(int ii = 0; ii < 1024; ++ii) {
	    		if(mod_GVCLib.am_name[ii] != null && mod_GVCLib.am_name[ii].equals(model)) {
	    			am_model  = mod_GVCLib.am_model[ii];
	    			am_tex  = mod_GVCLib.am_tex[ii];
	    			break;
	    		}
	    	}
			if(am_model == null || am_tex == null)return;
			
			float supuposx = nbt.getFloat("supuposx");
			float supuposy = nbt.getFloat("supuposy");
			float supuposz = nbt.getFloat("supuposz");
				GL11.glPushMatrix();//glstart11
				GL11.glTranslatef(supuposx, supuposy, supuposz);
				GL11.glRotatef(gun.am_supu_xr, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.am_supu_yr, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.am_supu_zr, 0.0F, 0.0F, 1.0F);
				Minecraft.getMinecraft().renderEngine.bindTexture(am_tex);
				am_model.renderPart("sight");
				GL11.glTranslatef(-supuposx, -supuposy, -supuposz);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				GL11.glPopMatrix();//glend11
				gun.obj_model.renderPart("mat81");
			}else {
				gun.obj_model.renderPart("mat8");
			}
			if(!gun.rendering_light_am_muzzle && gun.rendering_light) {
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_LIGHT1);
			}
		}else {
			gun.obj_model.renderPart("mat82");
		}
		
    	
    	if(!gun.mat25){
    		/*if(gun.mat25move) {
				GL11.glTranslatef(gun.mat25offsetx, gun.mat25offsety, gun.mat25offsetz);
				GL11.glRotatef(gun.mat25rotationx, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.mat25rotationy, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.mat25rotationz, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-gun.mat25offsetx, -gun.mat25offsety, -gun.mat25offsetz);
				GL11.glTranslatef(0F, 0F, -(gun.cocktime/2)*0.1F);
			}*/
    		if(gun.true_mat9){
    			if(!gun.rendering_light_am_grip && gun.rendering_light) {
        			GL11.glDisable(GL11.GL_LIGHT1);
        			GL11.glDisable(GL11.GL_LIGHTING);
        		}
    			boolean am_grip = nbt.getBoolean("am_grip");
        		if(am_grip){
    			String model = nbt.getString("am_grip_model");
    			String tex = nbt.getString("am_grip_tex");
    			IModelCustom am_model = null;
    			ResourceLocation am_tex = null;
    			for(int ii = 0; ii < 1024; ++ii) {
    	    		if(mod_GVCLib.am_name[ii] != null && mod_GVCLib.am_name[ii].equals(model)) {
    	    			am_model  = mod_GVCLib.am_model[ii];
    	    			am_tex  = mod_GVCLib.am_tex[ii];
    	    			break;
    	    		}
    	    	}
    			if(am_model == null || am_tex == null)return;
    			float gripposx = nbt.getFloat("gripposx");
    			float gripposy = nbt.getFloat("gripposy");
    			float gripposz = nbt.getFloat("gripposz");
    				GL11.glPushMatrix();//glstart11
    				GL11.glTranslatef(gripposx, gripposy, gripposz);
    				GL11.glRotatef(gun.am_grip_xr, 1.0F, 0.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_yr, 0.0F, 1.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_zr, 0.0F, 0.0F, 1.0F);
    				Minecraft.getMinecraft().renderEngine.bindTexture(am_tex);
    				am_model.renderPart("sight");
    				GL11.glTranslatef(-gripposx, -gripposy, -gripposz);
    				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
    				GL11.glPopMatrix();//glend11
    				gun.obj_model.renderPart("mat91");
    			}else {
    				gun.obj_model.renderPart("mat9");
    			}
    			if(!gun.rendering_light_am_grip && gun.rendering_light) {
    				GL11.glEnable(GL11.GL_LIGHTING);
    				GL11.glEnable(GL11.GL_LIGHT1);
    			}
    		}else if(gun.true_mat10){
    			gun.obj_model.renderPart("mat10");
    		}else if(gun.true_mat11){
    			gun.obj_model.renderPart("mat11");
    		}
    		else if(gun.true_mat100){
    			if(!gun.underbarrel.isEmpty() && gun.underbarrel.getItem() instanceof ItemGunBase) {
    				ItemGunBase under = (ItemGunBase) gun.underbarrel.getItem();
    				GL11.glPushMatrix();//glstart11
    				GL11.glTranslatef(-under.gun_underbarrel_x, -under.gun_underbarrel_y, -under.gun_underbarrel_z);
    				
    				GL11.glTranslatef(gun.gripposx, gun.gripposy, gun.gripposz);
    				GL11.glRotatef(gun.am_grip_xr, 1.0F, 0.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_yr, 0.0F, 1.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_zr, 0.0F, 0.0F, 1.0F);
    				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(under.obj_tex));
    				under.obj_model.renderPart("mat1");
					render_gun_light("mat1_light",under,0);
					render_gun_light("mat1_light2",under,1);
    				under.obj_model.renderPart("mat2");
					render_gun_light("mat2_light",under,0);
					render_gun_light("mat2_light2",under,1);
    				under.obj_model.renderPart("mat3");
    				under.obj_model.renderPart("mat22");
    				under.obj_model.renderPart("mat25");
    				under.obj_model.renderPart("mat31");
					under.obj_model.renderPart("fire_1");
    				under.obj_model.renderPart("mat32");
    				under.obj_model.renderPart("mat101");
    				GL11.glTranslatef(-gun.gripposx, -gun.gripposy, -gun.gripposz);
    				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
    				GL11.glPopMatrix();//glend11
    			}
    		}
    		
    		else{
    			gun.obj_model.renderPart("mat21");
    		}
    	}
		
    	boolean am_light = nbt.getBoolean("am_light");
		if(am_light){
			String model = nbt.getString("am_light_model");
			String tex = nbt.getString("am_light_tex");
			IModelCustom am_model = null;
			ResourceLocation am_tex = null;
			for(int ii = 0; ii < 1024; ++ii) {
	    		if(mod_GVCLib.am_name[ii] != null && mod_GVCLib.am_name[ii].equals(model)) {
	    			am_model  = mod_GVCLib.am_model[ii];
	    			am_tex  = mod_GVCLib.am_tex[ii];
	    			break;
	    		}
	    	}
			if(am_model == null || am_tex == null)return;
			
			float rightposx = nbt.getFloat("rightposx");
			float rightposy = nbt.getFloat("rightposy");
			float rightposz = nbt.getFloat("rightposz");
			
			if(!gun.rendering_light_am_light && gun.rendering_light) {
    			GL11.glDisable(GL11.GL_LIGHT1);
    			GL11.glDisable(GL11.GL_LIGHTING);
    		}
			GL11.glPushMatrix();//glstart11
			if(gun.render_light) {
				GL11.glTranslatef(rightposx, rightposy, rightposz);
				GL11.glRotatef(gun.am_light_xr, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.am_light_yr, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.am_light_zr, 0.0F, 0.0F, 1.0F);
				Minecraft.getMinecraft().renderEngine.bindTexture(am_tex);
				am_model.renderPart("sight");
			}
			if(gun.rightmode) {
				if( gun.rendering_light) {
	    			GL11.glDisable(GL11.GL_LIGHT1);
	    			GL11.glDisable(GL11.GL_LIGHTING);
	    		}
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDepthMask(false);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				//GL11.glColor4f(1F, 1F, 1F, gun.am_colorlevel);//old
				//GL11.glColor4f(1F, 1F, 1F, gun.am_colorlevel[gun.now_lightid - 1]);
				float[] am_colorlevel = new float[1024];//nbt.getFloat("am_colorlevel");
				for(int i = 0; i < nbt.getInteger("am_light_kazu"); ++i) {
					am_colorlevel[i] = nbt.getFloat("am_colorlevel" + String.format("%1$3d", i));
				}
				GL11.glColor4f(1F, 1F, 1F, am_colorlevel[gun.now_lightid - 1]);
				
				if(gun.render_light) {
					am_model.renderPart("light");
				}/*else if(gun.render_default_light) {
					gun.obj_model.renderPart("light");
				}*/
				//gun.am_light_model.renderPart("light");
				String tu1 = String.valueOf(gun.now_lightid);
				if(gun.render_light) {
					am_model.renderPart("light_" + tu1);
				}/*else if(gun.render_default_light) {
					gun.obj_model.renderPart("light_" + tu1);
				}*/
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDepthMask(true);
		        GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				if(gun.rendering_light) {
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_LIGHT1);
				}
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			}
			GL11.glTranslatef(-rightposx, -rightposy, -rightposz);
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			GL11.glPopMatrix();//glend11
			gun.obj_model.renderPart("mat61");
			if(!gun.rendering_light_am_light && gun.rendering_light) {
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_LIGHT1);
			}
		}else{
			if(gun.true_mat6){
				gun.obj_model.renderPart("mat6");
			}else if(gun.true_mat7){
				gun.obj_model.renderPart("mat7");
			}
			if(gun.rightmode) {
				GL11.glPushMatrix();//glstart11
				if( gun.rendering_light) {
	    			GL11.glDisable(GL11.GL_LIGHT1);
	    			GL11.glDisable(GL11.GL_LIGHTING);
	    		}
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glDisable(GL11.GL_CULL_FACE);
				GL11.glDepthMask(false);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				//GL11.glColor4f(1F, 1F, 1F, gun.am_colorlevel);//old
				//GL11.glColor4f(1F, 1F, 1F, gun.am_colorlevel[gun.now_lightid - 1]);
				float[] am_colorlevel = new float[1024];//nbt.getFloat("am_colorlevel");
				for(int i = 0; i < nbt.getInteger("am_light_kazu"); ++i) {
					am_colorlevel[i] = nbt.getFloat("am_colorlevel" + String.format("%1$3d", i));
				}
				GL11.glColor4f(1F, 1F, 1F, am_colorlevel[gun.now_lightid - 1]);
				
				 if(gun.render_default_light) {
					gun.obj_model.renderPart("light");
				}
				//gun.am_light_model.renderPart("light");
				String tu1 = String.valueOf(gun.now_lightid);
				if(gun.render_default_light) {
					gun.obj_model.renderPart("light_" + tu1);
				}
				GL11.glColor4f(1F, 1F, 1F, 1F);
				GL11.glDepthMask(true);
		        GL11.glEnable(GL11.GL_CULL_FACE);
				GL11.glDisable(GL11.GL_BLEND);
				if(gun.rendering_light) {
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_LIGHT1);
				}
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				GL11.glPopMatrix();//glend11
			}
		}
		
		render_gun_light("mat1_light",gun,0);
		render_gun_light("mat1_light2",gun,1);
	}
    static void mat25on(ItemStack itemstack, ItemGunBase gun){
    	/*if(gun.true_mat9){
			if(gun.am_grip){
				GL11.glPushMatrix();//glstart11
				GL11.glTranslatef(gun.gripposx, gun.gripposy, gun.gripposz);
				GL11.glRotatef(gun.am_grip_xr, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.am_grip_yr, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.am_grip_zr, 0.0F, 0.0F, 1.0F);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.am_grip_tex));
				gun.am_grip_model.renderPart("sight");
				GL11.glTranslatef(-gun.gripposx, -gun.gripposy, -gun.gripposz);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
				GL11.glPopMatrix();//glend11
				gun.obj_model.renderPart("mat91");
			}else {
				gun.obj_model.renderPart("mat9");
			}
		}else if(gun.true_mat10){
			gun.obj_model.renderPart("mat10");
		}else if(gun.true_mat11){
			gun.obj_model.renderPart("mat11");
		}else{
			gun.obj_model.renderPart("mat21");
		}*/
    	NBTTagCompound nbt = itemstack.getTagCompound();
		if(!itemstack.hasTagCompound())return;
    	{
    		if(gun.true_mat9){
    			if(!gun.rendering_light_am_grip && gun.rendering_light) {
        			GL11.glDisable(GL11.GL_LIGHT1);
        			GL11.glDisable(GL11.GL_LIGHTING);
        		}
    			boolean am_grip = nbt.getBoolean("am_grip");
        		if(am_grip){
    			String model = nbt.getString("am_grip_model");
    			String tex = nbt.getString("am_grip_tex");
    			IModelCustom am_model = null;
    			ResourceLocation am_tex = null;
    			for(int ii = 0; ii < 1024; ++ii) {
    	    		if(mod_GVCLib.am_name[ii] != null && mod_GVCLib.am_name[ii].equals(model)) {
    	    			am_model  = mod_GVCLib.am_model[ii];
    	    			am_tex  = mod_GVCLib.am_tex[ii];
    	    			break;
    	    		}
    	    	}
    			if(am_model == null || am_tex == null)return;
    			float gripposx = nbt.getFloat("gripposx");
    			float gripposy = nbt.getFloat("gripposy");
    			float gripposz = nbt.getFloat("gripposz");
    				GL11.glPushMatrix();//glstart11
    				GL11.glTranslatef(gripposx, gripposy, gripposz);
    				GL11.glRotatef(gun.am_grip_xr, 1.0F, 0.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_yr, 0.0F, 1.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_zr, 0.0F, 0.0F, 1.0F);
    				Minecraft.getMinecraft().renderEngine.bindTexture(am_tex);
    				am_model.renderPart("sight");
    				GL11.glTranslatef(-gripposx, -gripposy, -gripposz);
    				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
    				GL11.glPopMatrix();//glend11
    				gun.obj_model.renderPart("mat91");
    			}else {
    				gun.obj_model.renderPart("mat9");
    			}
    			if(!gun.rendering_light_am_grip && gun.rendering_light) {
    				GL11.glEnable(GL11.GL_LIGHTING);
    				GL11.glEnable(GL11.GL_LIGHT1);
    			}
    		}else if(gun.true_mat10){
    			gun.obj_model.renderPart("mat10");
    		}else if(gun.true_mat11){
    			gun.obj_model.renderPart("mat11");
    		}
    		else if(gun.true_mat100){
    			if(!gun.underbarrel.isEmpty() && gun.underbarrel.getItem() instanceof ItemGunBase) {
    				ItemGunBase under = (ItemGunBase) gun.underbarrel.getItem();
    				GL11.glPushMatrix();//glstart11
    				GL11.glTranslatef(-under.gun_underbarrel_x, -under.gun_underbarrel_y, -under.gun_underbarrel_z);
    				
    				GL11.glTranslatef(gun.gripposx, gun.gripposy, gun.gripposz);
    				GL11.glRotatef(gun.am_grip_xr, 1.0F, 0.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_yr, 0.0F, 1.0F, 0.0F);
    				GL11.glRotatef(gun.am_grip_zr, 0.0F, 0.0F, 1.0F);
    				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(under.obj_tex));
    				under.obj_model.renderPart("mat1");
					render_gun_light("mat1_light",under,0);
					render_gun_light("mat1_light2",under,1);
    				under.obj_model.renderPart("mat2");
    				under.obj_model.renderPart("mat3");
    				under.obj_model.renderPart("mat22");
    				under.obj_model.renderPart("mat25");
    				under.obj_model.renderPart("mat31");
					under.obj_model.renderPart("fire_1");
    				under.obj_model.renderPart("mat32");
    				under.obj_model.renderPart("mat101");
    				GL11.glTranslatef(-gun.gripposx, -gun.gripposy, -gun.gripposz);
    				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
    				GL11.glPopMatrix();//glend11
    			}
    		}
    		
    		else{
    			gun.obj_model.renderPart("mat21");
    		}
    	}
    }
    
    static void mat25(ItemStack itemstack, ItemGunBase gun, boolean recoil, int cockingtime){
		if(recoil){
			GL11.glPushMatrix();//glstart11
			{
				if(gun.mat25) {
					mat25on(itemstack, gun);
				}
				if(gun.newreload){
					if (gun.reloadmotion == 0) {
						for (int kais = 0; kais <= gun.mat_rk_24; ++kais) {
							renderreload(gun, kais, gun.mat_r_24);
						}
					} else {
						newrenderreload(gun, gun.remat25, gun.remat25_time, gun.remat25_xpoint, gun.remat25_ypoint,
								gun.remat25_zpoint, gun.remat25_xrote, gun.remat25_yrote, gun.remat25_zrote,
								gun.remat25_xmove, gun.remat25_ymove, gun.remat25_zmove);
					}
				}else{
					if(gun.mat25move) {
						GL11.glTranslatef(gun.mat25offsetx, gun.mat25offsety, gun.mat25offsetz);
						GL11.glRotatef(gun.mat25rotationx, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(gun.mat25rotationy, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(gun.mat25rotationz, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(-gun.mat25offsetx, -gun.mat25offsety, -gun.mat25offsetz);
						GL11.glTranslatef(0F, 0F, -(gun.cocktime/2)*0.1F);
					}
				}
				gun.obj_model.renderPart("mat25");
			}
			GL11.glPopMatrix();//glend11
		}else{

			GL11.glPushMatrix();//glstart11
			if(cockingtime <= 0){
				gun.obj_model.renderPart("mat25");
				if(gun.mat25) {
					mat25on(itemstack, gun);
				}
			}else{
				if(gun.mat25) {
					mat25on(itemstack, gun);
				}
				if(gun.mat25move) {
					GL11.glTranslatef(gun.mat25offsetx, gun.mat25offsety, gun.mat25offsetz);
					GL11.glRotatef(gun.mat25rotationx, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(gun.mat25rotationy, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(gun.mat25rotationz, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(-gun.mat25offsetx, -gun.mat25offsety, -gun.mat25offsetz);
					if (cockingtime > 0 && cockingtime < (gun.cocktime / 2)) {
						GL11.glTranslatef(0F, 0F, -cockingtime * 0.1F);
					} else {
						GL11.glTranslatef(0F, 0F, (cockingtime - gun.cocktime) * 0.1F);
					}
				}
				gun.obj_model.renderPart("mat25");
			}
			GL11.glPopMatrix();//glend11
		}
		
	}
	
	static void mat31mat32(ItemGunBase gun, ItemStack itemstack, boolean recoil){
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
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
					render_gun_light("mat31_light",gun,0);
				}
				GL11.glRotatef(-gun.rotey, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-gun.rotex, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-gun.rote, 0.0F, 1.0F, 0.0F);
				if (recoil) {
					gun.rote = gun.rote + gun.mat31rotez*0.1F;//60F
					gun.rotex = gun.rotex + gun.mat31rotex*0.1F;
					gun.rotey = gun.rotey + gun.mat31rotey*0.1F;
				}
				GL11.glPopMatrix();//glend1
				if (recoil) {
					GL11.glPushMatrix();//glstrt2
					GL11.glTranslatef(gun.mat32posx, gun.mat32posy, gun.mat32posz);//0,0.5,0
					GL11.glRotatef(gun.mat32rotey*0.1F, 0.0F, 1.0F, 0.0F);//90
					GL11.glRotatef(gun.mat32rotez*0.1F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(gun.mat32rotex*0.1F, 1.0F, 0.0F, 0.0F);
					GL11.glTranslatef(-gun.mat32posx, -gun.mat32posy, -gun.mat32posz);
					gun.obj_model.renderPart("mat32");
					GL11.glRotatef(-gun.mat32rotey*0.1F, 0.0F, 1.0F, 0.0F);//90
					GL11.glRotatef(-gun.mat32rotez*0.1F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-gun.mat32rotex*0.1F, 1.0F, 0.0F, 0.0F);
					GL11.glPopMatrix();//glend2
				} else {
					gun.obj_model.renderPart("mat32");
					
				}

			}else{
				gun.rote = 0;
				gun.rotey = 0;
				gun.rotex = 0;
			}
		}else {
			if(gun.newreload){
				GL11.glPushMatrix();//glstrt2
				if (gun.reloadmotion == 0) {
					for (int kais = 0; kais <= gun.mat_rk_31; ++kais) {
						renderreload(gun, kais, gun.mat_r_31);
					}
				} else {
					newrenderreload(gun, gun.remat311, gun.remat31_time, gun.remat31_xpoint, gun.remat31_ypoint,
							gun.remat31_zpoint, gun.remat31_xrote, gun.remat31_yrote, gun.remat31_zrote,
							gun.remat31_xmove, gun.remat31_ymove, gun.remat31_zmove);
				}
				gun.obj_model.renderPart("mat31");
				render_gun_light("mat31_light",gun,0);
				GL11.glPopMatrix();//glend2
			}else{
				gun.obj_model.renderPart("mat31");
				render_gun_light("mat31_light",gun,0);
			}
			if(gun.newreload){
				GL11.glPushMatrix();//glstrt2
				if (gun.reloadmotion == 0) {
					for (int kais = 0; kais <= gun.mat_rk_32; ++kais) {
						renderreload(gun, kais, gun.mat_r_32);
					}
				} else {
					newrenderreload(gun, gun.remat32, gun.remat32_time, gun.remat32_xpoint, gun.remat32_ypoint,
							gun.remat32_zpoint, gun.remat32_xrote, gun.remat32_yrote, gun.remat32_zrote,
							gun.remat32_xmove, gun.remat32_ymove, gun.remat32_zmove);
				}
				gun.obj_model.renderPart("mat32");
				GL11.glPopMatrix();//glend2
			}else{
				gun.obj_model.renderPart("mat32");
			}
		}
		
	}
	
    private static void render_gun_light(String name, ItemGunBase gun, int id){
		GL11.glPushMatrix();//glstart
		if(id == 1){
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
		}
        float lastx = OpenGlHelper.lastBrightnessX;
        float lasty = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		if(id == 1)GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		gun.obj_model.renderPart(name);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		if(id == 1){
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		GL11.glPopMatrix();//glend
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
	private static void newrenderreload(ItemGunBase gun, int remat, int[] remattime
			, float[] remat_xpoint, float[] remat_ypoint, float[] remat_zpoint
			, float[] remat_xrote, float[] remat_yrote, float[] remat_zrote
			, float[] remat_xmove, float[] remat_ymove, float[] remat_zmove){
		//if(mat != null)
		{
			for(int i = 1; i < remat; ++i) {
				if(gun.retime >= remattime[i - 1] && gun.retime < remattime[i]){
					int time = remattime[i] - remattime[i - 1];
					int nowtime = gun.retime - remattime[i - 1];
					
					float xpoint = remat_xpoint[i] - remat_xpoint[i - 1];
					xpoint = xpoint / time;
					float ypoint = remat_ypoint[i] - remat_ypoint[i - 1];
					ypoint = ypoint / time;
					float zpoint = remat_zpoint[i] - remat_zpoint[i - 1];
					zpoint = zpoint / time;
					
					float xrote = remat_xrote[i] - remat_xrote[i - 1];
					xrote = xrote / time;
					float yrote = remat_yrote[i] - remat_yrote[i - 1];
					yrote = yrote / time;
					float zrote = remat_zrote[i] - remat_zrote[i - 1];
					zrote = zrote / time;
					
					float xmove = remat_xmove[i] - remat_xmove[i - 1];
					xmove = xmove / time;
					float ymove = remat_ymove[i] - remat_ymove[i - 1];
					ymove = ymove / time;
					float zmove = remat_zmove[i] - remat_zmove[i - 1];
					zmove = zmove / time;
					
					GL11.glTranslatef((remat_xpoint[i - 1] + xpoint * nowtime)
							,(remat_ypoint[i - 1] + ypoint * nowtime)
							,(remat_zpoint[i - 1] + zpoint * nowtime));
					GL11.glRotatef(remat_xrote[i - 1] + xrote * nowtime, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(remat_yrote[i - 1] + yrote * nowtime, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(remat_zrote[i - 1] + zrote * nowtime, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef((remat_xmove[i - 1] + xmove * nowtime)
							,(remat_ymove[i - 1] + ymove * nowtime)
							,(remat_zmove[i - 1] + zmove * nowtime));
					GL11.glTranslatef(-(remat_xpoint[i - 1] + xpoint * nowtime)
							,-(remat_ypoint[i - 1] + ypoint * nowtime)
							,-(remat_zpoint[i - 1] + zpoint * nowtime));
				}
			}
			
		}
	}
}
