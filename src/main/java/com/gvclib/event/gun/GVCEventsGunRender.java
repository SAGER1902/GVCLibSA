package gvclib.event.gun;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.item.ItemAttachment;
import gvclib.item.ItemGunBase;
import gvclib.item.ItemGun_Grenade;
import gvclib.item.ItemGun_SWORD;
import gvclib.item.ItemGun_Shield;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

public class GVCEventsGunRender {

	//現在は未使用
	
	public String ads;

	public boolean zoomtype;
	private RenderGameOverlayEvent eventParent;
	
	private static final ResourceLocation rightt = new ResourceLocation("gvclib:textures/model/Right.png");
	private static final IModelCustom rightm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/Right.mqo"));
	private static final ResourceLocation lasert = new ResourceLocation("gvclib:textures/model/RightLaser.png");
	private static final IModelCustom laserm = AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/RightLaser.mqo"));
	
	/*private final ResourceLocation lasert1 = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
		      .getResourceAsStream("assets/magicbeans/structures/schematic.txt"), "UTF-8"));*/
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void rendergun(RenderHandEvent event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		ItemStack itemstackl = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase && itemstack.hasTagCompound()) {//item
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			/*if(gun.newreload){
				GVCEventsGunRenderNew.rendermain(entityplayer, itemstack, true);
			}else*/
			{
				this.rendermain(entityplayer, itemstack, true);
			}
		}//item
		if (!itemstackl.isEmpty()  && itemstackl.getItem() instanceof ItemGunBase && itemstackl.hasTagCompound()) {//item
			ItemGunBase gun = (ItemGunBase) itemstackl.getItem();
			/*if(gun.newreload){
				GVCEventsGunRenderNew.rendermain(entityplayer, itemstackl, false);
			}else*/
			{
				this.rendermain(entityplayer, itemstackl, false);
			}
		}//item
	  }
	
	float usemax;
	
	private void rendermain(EntityPlayer entityplayer, ItemStack itemstack, boolean side){
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
		/*if(!side && (left || entityplayer.isSneaking()) && itemstack.getItem() instanceof ItemGun_Shield){
			sidel = true;
		}*/
		if(!side && (entityplayer.isSneaking()) && itemstack.getItem() instanceof ItemGun_Shield){
			sidel = true;
		}
		if(side &&  itemstack.getItem() instanceof ItemGun_Shield && entityplayer.getActiveItemStack() == itemstack){
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
			//GlStateManager.enableLighting();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
	//		GL11.glScalef(0.5F, 0.5F, 0.5F);
	//		GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			
			//8/18
			if(side && entityplayer.getHeldItemOffhand() == null && !(itemstack.getItem() instanceof ItemGun_SWORD))
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(0,0, -f1*5);
				GL11.glRotatef(f1*30, -1.0F, 0.0F, 1.0F);
			}
			if(side && itemstack.getItem() instanceof ItemGun_SWORD)
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(0,0, -f1*5);
				GL11.glRotatef(f1*30, -1.0F, 0.0F, 1.0F);
			}
			if(!side && itemstack.getItem() instanceof ItemGun_SWORD)
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(0,0, -f1*5);
				GL11.glRotatef(f1*30, -1.0F, 0.0F, -1.0F);
			}
			if(side && gun.knife)
			{
				float f1 = entityplayer.swingProgress;
				GL11.glTranslatef(0,0, -f1*6);
				GL11.glRotatef(f1*15, -1.0F, 0.0F, -1.0F);
			}
			/*if(!side && gun.knife)
			{
				if(entityplayer.getHeldItemMainhand().isEmpty()) {
					float f1 = entityplayer.swingProgress;
					GL11.glTranslatef(0,0, -f1*6);
					GL11.glRotatef(f1*15, -1.0F, 0.0F, -1.0F);
				}
				else if( (!(entityplayer.getHeldItemMainhand().getItem() instanceof ItemSword) 
						&& !(entityplayer.getHeldItemMainhand().getItem() instanceof ItemGun_SWORD))){
					float f1 = entityplayer.swingProgress;
					GL11.glTranslatef(0,0, -f1*6);
					GL11.glRotatef(f1*15, -1.0F, 0.0F, -1.0F);
				}else if(entityplayer.getHeldItemMainhand().getItem() instanceof ItemGunBase) {
					ItemGunBase gun2 = (ItemGunBase) entityplayer.getHeldItemMainhand().getItem();
					if(!gun2.knife) {
						float f1 = entityplayer.swingProgress;
						GL11.glTranslatef(0,0, -f1*6);
						GL11.glRotatef(f1*15, -1.0F, 0.0F, -1.0F);
					}
				}
				
			}*/
			if(itemstack.getItem() instanceof ItemGun_Grenade)
			{
				float use = 0;
				use = (float)(itemstack.getMaxItemUseDuration() - entityplayer.getItemInUseCount()) / 1.0F;
				if(use > 30 && entityplayer.isHandActive()) {
					use = 30;
				}
				GL11.glRotatef(use, 0.0F, -1.0F * xxx, 0.0F);
				GL11.glRotatef(use, 1.0F, 0.0F, 0.0F);
			}
			
			if(!recoiled) {
				GL11.glTranslatef(0.0F, 0.0F, -gun.model_cock_z*0.1F);
			}
			
			if (itemstack.getItemDamage() == itemstack.getMaxDamage()) {//1s
				GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
				if(gun.newreload){
					if (gun.reloadmotion == 0) {
						for (int kais = 0; kais <= gun.mat_rk_0; ++kais) {
							renderreload(gun, kais, gun.mat_r_0);
						}
					} else {
						newrenderreload(gun, gun.remat0, gun.remat0_time, gun.remat0_xpoint, gun.remat0_ypoint,
								gun.remat0_zpoint, gun.remat0_xrote, gun.remat0_yrote, gun.remat0_zrote,
								gun.remat0_xmove, gun.remat0_ymove, gun.remat0_zmove);
					}
				}else{
					GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
				}
				
			}
			else if(sidel || sider){//ADS
				GL11.glTranslatef(ADS_X(gun) * xxx,ADS_Y(gun), ADS_Z(gun));//0,-1.7,-1.5
				GL11.glRotatef(gun.rotationx, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(gun.rotationy * xxx, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(gun.rotationz * xxx, 0.0F, 0.0F, 1.0F);
			}
			else if(entityplayer.isSneaking()){//ADS
				if(side && !entityplayer.getHeldItemOffhand().isEmpty()){
					GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(00F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
				}else if(!side && !entityplayer.getHeldItemMainhand().isEmpty()){
					GL11.glTranslatef(gun.model_x * xxx ,gun.model_y, gun.model_z + 1);//1.5,-2,-2.5
					GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(00F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(0F, 1.0F, 0.0F, 0.0F);
				}else{
					GL11.glTranslatef(ADS_X(gun) * xxx,ADS_Y(gun), ADS_Z(gun));//0,-1.7,-1.5
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
				zoomrender = !gun.am_zoomrender;
			}else{
				if(gun.true_mat4){
					zoomrender = !gun.model_zoomrenderr;
				}else if(gun.true_mat5){
					zoomrender = !gun.model_zoomrenders;
				}else{
					zoomrender = !gun.model_zoomrender;
				}
			}
			
			GL11.glPushMatrix();//guns
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(gun.obj_tex));
			if(entityplayer.isSneaking()){
				if(gun.scopezoom > 1.1F && zoomrender){
				}else{
					if(itemstack.getItemDamage() == itemstack.getMaxDamage()) {
						this.rendermat_reload(entityplayer, itemstack, gun);//rendermat
					}else {
						this.rendermat(entityplayer, itemstack, gun);//rendermat
					}
				}
			}else{
				if(gun.reloadmotion_test) {
					this.rendermat_reloadtest(entityplayer, itemstack, gun);//rendermat
				}else {
					if(itemstack.getItemDamage() == itemstack.getMaxDamage()) {
						this.rendermat_reload(entityplayer, itemstack, gun);//rendermat
					}else {
						this.rendermat(entityplayer, itemstack, gun);//rendermat
					}
				}
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
					if(gun.canarm) {
						this.renderarm(entityplayer, itemstack, gun);//renderarm
					}
				}
			}else{
				if(gun.canarm) {
					if(gun.reloadmotion_test) {
						this.renderarm_reloattest(entityplayer, itemstack, gun);//renderarm
					}else {
						this.renderarm(entityplayer, itemstack, gun);//renderarm
					}
				}
			}
			/*GL11.glPushMatrix();//right
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if(gun.rightmode && gun.true_mat7) {
				GL11.glColor4f(1F, 1F, 1F, 0.2F);
				GL11.glTranslatef(gun.rightposx, gun.rightposy, gun.rightposz);
				Minecraft.getMinecraft().renderEngine.bindTexture(rightt);
				rightm.renderAll();
				GL11.glColor4f(1F, 1F, 1F, 1F);
			}
			if(gun.rightmode && gun.true_mat6) {
				GL11.glColor4f(1F, 1F, 1F, 0.5F);
				GL11.glTranslatef(gun.rightposx, gun.rightposy, gun.rightposz);
				Minecraft.getMinecraft().renderEngine.bindTexture(lasert);
				laserm.renderAll();
				GL11.glColor4f(1F, 1F, 1F, 1F);
			}
			GL11.glPopMatrix();//right
			*/
			
			GL11.glPopMatrix();//arme
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			//GlStateManager.disableLighting();
			GL11.glPopMatrix();//gune
			
		}
	}
	
	
	public static void rendermat(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean cocking = nbt.getBoolean("Cocking");
		int cockingtime = nbt.getInteger("CockingTime");
		boolean recoiled = nbt.getBoolean("Recoiled");
		int recoiledtime = nbt.getInteger("RecoiledTime");
		GL11.glPushMatrix();//glstrt0
		if(gun.rendering_light) {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT1);
		}
		
		
		GL11.glPushMatrix();//glstrt2
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
		}else {
			newrenderreload(gun, gun.remat1, gun.remat1_time, gun.remat1_xpoint, gun.remat1_ypoint,
					gun.remat1_zpoint, gun.remat1_xrote, gun.remat1_yrote, gun.remat1_zrote,
					gun.remat1_xmove, gun.remat1_ymove, gun.remat1_zmove);
		}
		/*{
			GL11.glPushMatrix();//glstrt2
			GL11.glEnable(GL11.GL_CULL_FACE); 
			GL11.glCullFace(GL11.GL_FRONT);
			GL11.glScaled(1.1, 1.1, 1.1);
			GL11.glColor4d(0, 0, 0, 1);
			gun.obj_model.renderPart("mat1");
			GL11.glColor4d(1, 1, 1, 1);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();//glend2
		}*/
		{
			GL11.glPushMatrix();//glstrt2
			ShaderGroup shader;
			Framebuffer frame;
            try {
            	shader = new ShaderGroup(Minecraft.getMinecraft().getTextureManager(), 
                		Minecraft.getMinecraft().getResourceManager(), 
                		Minecraft.getMinecraft().getFramebuffer(), new ResourceLocation(gun.obj_tex));
            	frame = shader.getFramebufferRaw("final");
            }catch (IOException ioexception){
            	shader = null;
            	frame = null;
            }
            //if(shader != null && frame != null)
            /*{
            	GlStateManager.depthFunc(519);
                GlStateManager.disableFog();
                //frame.bindFramebuffer(false);
                RenderHelper.disableStandardItemLighting();
                Minecraft.getMinecraft().getRenderManager().setRenderOutlines(true);
                gun.obj_model.renderPart("mat1");
                Minecraft.getMinecraft().getRenderManager().setRenderOutlines(false);
                RenderHelper.enableStandardItemLighting();
                GlStateManager.depthMask(false);
                //shader.render(Minecraft.getMinecraft().getRenderPartialTicks());
                GlStateManager.enableLighting();
                GlStateManager.depthMask(true);
                GlStateManager.enableFog();
                GlStateManager.enableBlend();
                GlStateManager.enableColorMaterial();
                GlStateManager.depthFunc(515);
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
            }*/
           // if (this.renderOutlines)
           
            /*{
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(0);
            }
            gun.obj_model.renderPart("mat1");
            //if (this.renderOutlines)
            {
                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();
            }*/
            gun.obj_model.renderPart("mat1");
            gun.obj_model.renderPart("mat100");
			GL11.glPopMatrix();//glend2
		}
		//gun.obj_model.renderPart("mat1");
		GL11.glPopMatrix();//glend2
		
		Layermat.renderattachment(entityplayer, itemstack, gun);
		if (!recoiled)
		{
			if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
				Layermat.mat31mat32(gun, itemstack, true);
			}else {
				Layermat.mat31mat32(gun, itemstack, true);
			}
			
			GL11.glTranslatef(0.0F, 0.0F, gun.model_cock_z);//0, 0, -0.4
			gun.obj_model.renderPart("mat2");
			if(gun.mat2) {
				Layermat.rendersight(entityplayer, itemstack, gun);
			}
			GL11.glTranslatef(0.0F, 0.0F, -gun.model_cock_z);
			if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
				Layermat.mat25(itemstack, gun, false, cockingtime);
			}else {
				Layermat.mat25(itemstack, gun, true, cockingtime);
			}
			if(!gun.mat2) {
				Layermat.rendersight(entityplayer, itemstack, gun);
			}
		}else{
			Layermat.mat31mat32(gun, itemstack, false);
			if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
				gun.obj_model.renderPart("mat2");
				Layermat.mat25(itemstack, gun, false, cockingtime);
				Layermat.rendersight(entityplayer, itemstack, gun);
			}else{
				Layermat.mat25(itemstack, gun, true, cockingtime);
				if(gun.newreload){
					GL11.glPushMatrix();//glstrt2
					if (gun.reloadmotion == 0) {
						for (int kais = 0; kais <= gun.mat_rk_24; ++kais) {
							renderreload(gun, kais, gun.mat_r_24);
						}
					} else {
						newrenderreload(gun, gun.remat24, gun.remat24_time, gun.remat24_xpoint, gun.remat24_ypoint,
								gun.remat24_zpoint, gun.remat24_xrote, gun.remat24_yrote, gun.remat24_zrote,
								gun.remat24_xmove, gun.remat24_ymove, gun.remat24_zmove);
					}
					gun.obj_model.renderPart("mat24");
					GL11.glPopMatrix();//glend2
				}else{
					gun.obj_model.renderPart("mat24");
				}
				//gun.obj_model.renderPart("mat24");
				{
					if(gun.mat22){
						GL11.glPushMatrix();//arms
						if(gun.newreload){
							if (gun.reloadmotion == 0) {
								for (int kais = 0; kais <= gun.mat_rk_22; ++kais) {
									renderreload(gun, kais, gun.mat_r_22);
								}
							} else {
								newrenderreload(gun, gun.remat22, gun.remat22_time, gun.remat22_xpoint, gun.remat22_ypoint,
										gun.remat22_zpoint, gun.remat22_xrote, gun.remat22_yrote, gun.remat22_zrote,
										gun.remat22_xmove, gun.remat22_ymove, gun.remat22_zmove);
							}
						}else{
							GL11.glTranslatef(gun.mat22offsetx, gun.mat22offsety, gun.mat22offsetz);
							GL11.glRotatef(gun.mat22rotationx, 1.0F, 0.0F, 0.0F);
							GL11.glRotatef(gun.mat22rotationy, 0.0F, 1.0F, 0.0F);
							GL11.glRotatef(gun.mat22rotationz, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(-gun.mat22offsetx, -gun.mat22offsety, -gun.mat22offsetz);
						}
						Layermat.rendersight(entityplayer, itemstack, gun);
						GL11.glPopMatrix();//arme
					}else{
						Layermat.rendersight(entityplayer, itemstack, gun);
					}
				}
				if(gun.newreload){
					GL11.glPushMatrix();//glstrt2
					GL11.glTranslatef(0.0F, 0.0F, gun.model_cock_z);
					GL11.glTranslatef(0.0F, 0.0F, -gun.model_cock_z);
					if (gun.reloadmotion == 0) {
						for (int kais = 0; kais <= gun.mat_rk_2; ++kais) {
							renderreload(gun, kais, gun.mat_r_2);
						}
					} else {
						newrenderreload(gun, gun.remat2, gun.remat2_time, gun.remat2_xpoint, gun.remat2_ypoint,
								gun.remat2_zpoint, gun.remat2_xrote, gun.remat2_yrote, gun.remat2_zrote,
								gun.remat2_xmove, gun.remat2_ymove, gun.remat2_zmove);
					}
					gun.obj_model.renderPart("mat2");
					GL11.glPopMatrix();//glend2
				}else{
					GL11.glTranslatef(0.0F, 0.0F, gun.model_cock_z);
					gun.obj_model.renderPart("mat2");
					if(gun.mat2) {
						Layermat.rendersight(entityplayer, itemstack, gun);
					}
					GL11.glTranslatef(0.0F, 0.0F, -gun.model_cock_z);
				}
			}
		}
		
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
			gun.obj_model.renderPart("mat3");
		}else{
			GL11.glPushMatrix();//glstrt2
			if(gun.newreload){
				if (gun.reloadmotion == 0) {
					for (int kais = 0; kais <= gun.mat_rk_3; ++kais) {
						renderreload(gun, kais, gun.mat_r_3);
					}
				} else {
					newrenderreload(gun, gun.remat3, gun.remat3_time, gun.remat3_xpoint, gun.remat3_ypoint,
							gun.remat3_zpoint, gun.remat3_xrote, gun.remat3_yrote, gun.remat3_zrote,
							gun.remat3_xmove, gun.remat3_ymove, gun.remat3_zmove);
				}
				gun.obj_model.renderPart("mat3");
			}else{
				if(gun.model_hand){
					if(gun.retime < gun.reloadtime / 2){
						GL11.glTranslatef(0.0F, -gun.retime * 0.5F, 0F);
					}else if(gun.retime < gun.reloadtime){
						GL11.glTranslatef(0.0F, gun.retime * 0.5F - (gun.reloadtime) * 0.5F, 0F);
					}
					gun.obj_model.renderPart("mat3");
				}else{
					if(gun.retime < gun.reloadtime / 4){
						GL11.glTranslatef(0.0F, -gun.retime * 0.5F, 0F);
					}else if(gun.retime < gun.reloadtime / 2){
						GL11.glTranslatef(0.0F, gun.retime * 0.5F - (gun.reloadtime/2) * 0.5F, 0F);
					}
					gun.obj_model.renderPart("mat3");
				}
			}
			GL11.glPopMatrix();//glend2
		}
		if(gun.rendering_light) {
			GL11.glDisable(GL11.GL_LIGHT1);
			GL11.glDisable(GL11.GL_LIGHTING);
		}
		GL11.glPopMatrix();//glend0
	}
	public static void rendermat_reload(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean cocking = nbt.getBoolean("Cocking");
		int cockingtime = nbt.getInteger("CockingTime");
		boolean recoiled = nbt.getBoolean("Recoiled");
		int recoiledtime = nbt.getInteger("RecoiledTime");
		GL11.glPushMatrix();//glstrt0
		if(gun.rendering_light) {
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT1);
		}
		
		/*
		newrenderreload(gun, gun.remat0, gun.remat0_time, gun.remat0_xpoint, gun.remat0_ypoint,
				gun.remat0_zpoint, gun.remat0_xrote, gun.remat0_yrote, gun.remat0_zrote,
				gun.remat0_xmove, gun.remat0_ymove, gun.remat0_zmove);
		*/
		
		{
			GL11.glPushMatrix();//glstrt2
			Layermat.renderattachment(entityplayer, itemstack, gun);
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat1, gun.remat1_time, gun.remat1_xpoint, gun.remat1_ypoint,
					gun.remat1_zpoint, gun.remat1_xrote, gun.remat1_yrote, gun.remat1_zrote,
					gun.remat1_xmove, gun.remat1_ymove, gun.remat1_zmove);
			gun.obj_model.renderPart("mat1");
			gun.obj_model.renderPart("mat100");
			GL11.glPopMatrix();//glend2
		}
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat2, gun.remat2_time, gun.remat2_xpoint, gun.remat2_ypoint,
					gun.remat2_zpoint, gun.remat2_xrote, gun.remat2_yrote, gun.remat2_zrote,
					gun.remat2_xmove, gun.remat2_ymove, gun.remat2_zmove);
			gun.obj_model.renderPart("mat2");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat3, gun.remat3_time, gun.remat3_xpoint, gun.remat3_ypoint,
					gun.remat3_zpoint, gun.remat3_xrote, gun.remat3_yrote, gun.remat3_zrote,
					gun.remat3_xmove, gun.remat3_ymove, gun.remat3_zmove);
			gun.obj_model.renderPart("mat3");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat22, gun.remat22_time, gun.remat22_xpoint, gun.remat22_ypoint,
					gun.remat22_zpoint, gun.remat22_xrote, gun.remat22_yrote, gun.remat22_zrote,
					gun.remat22_xmove, gun.remat22_ymove, gun.remat22_zmove);
			gun.obj_model.renderPart("mat22");
			Layermat.rendersight(entityplayer, itemstack, gun);
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat24, gun.remat24_time, gun.remat24_xpoint, gun.remat24_ypoint,
					gun.remat24_zpoint, gun.remat24_xrote, gun.remat24_yrote, gun.remat24_zrote,
					gun.remat24_xmove, gun.remat24_ymove, gun.remat24_zmove);
			gun.obj_model.renderPart("mat24");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat25, gun.remat25_time, gun.remat25_xpoint, gun.remat25_ypoint,
					gun.remat25_zpoint, gun.remat25_xrote, gun.remat25_yrote, gun.remat25_zrote,
					gun.remat25_xmove, gun.remat25_ymove, gun.remat25_zmove);
			gun.obj_model.renderPart("mat25");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat311, gun.remat31_time, gun.remat31_xpoint, gun.remat31_ypoint,
					gun.remat31_zpoint, gun.remat31_xrote, gun.remat31_yrote, gun.remat31_zrote,
					gun.remat31_xmove, gun.remat31_ymove, gun.remat31_zmove);
			gun.obj_model.renderPart("mat31");
			GL11.glPopMatrix();//glend2
		}
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat32, gun.remat32_time, gun.remat32_xpoint, gun.remat32_ypoint,
					gun.remat32_zpoint, gun.remat32_xrote, gun.remat32_yrote, gun.remat32_zrote,
					gun.remat32_xmove, gun.remat32_ymove, gun.remat32_zmove);
			gun.obj_model.renderPart("mat32");
			GL11.glPopMatrix();//glend2
		}
		
		if(gun.rendering_light) {
			GL11.glDisable(GL11.GL_LIGHT1);
			GL11.glDisable(GL11.GL_LIGHTING);
		}
		GL11.glPopMatrix();//glend0
	}
	
	
	
	public static void rendermat_reloadtest(EntityLivingBase entityplayer, ItemStack itemstack, ItemGunBase gun){
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean cocking = nbt.getBoolean("Cocking");
		int cockingtime = nbt.getInteger("CockingTime");
		boolean recoiled = nbt.getBoolean("Recoiled");
		int recoiledtime = nbt.getInteger("RecoiledTime");
		
		
		newrenderreload(gun, gun.remat0, gun.remat0_time, gun.remat0_xpoint, gun.remat0_ypoint,
				gun.remat0_zpoint, gun.remat0_xrote, gun.remat0_yrote, gun.remat0_zrote,
				gun.remat0_xmove, gun.remat0_ymove, gun.remat0_zmove);
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat1, gun.remat1_time, gun.remat1_xpoint, gun.remat1_ypoint,
					gun.remat1_zpoint, gun.remat1_xrote, gun.remat1_yrote, gun.remat1_zrote,
					gun.remat1_xmove, gun.remat1_ymove, gun.remat1_zmove);
			gun.obj_model.renderPart("mat1");
			gun.obj_model.renderPart("mat100");
			GL11.glPopMatrix();//glend2
		}
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat2, gun.remat2_time, gun.remat2_xpoint, gun.remat2_ypoint,
					gun.remat2_zpoint, gun.remat2_xrote, gun.remat2_yrote, gun.remat2_zrote,
					gun.remat2_xmove, gun.remat2_ymove, gun.remat2_zmove);
			gun.obj_model.renderPart("mat2");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat3, gun.remat3_time, gun.remat3_xpoint, gun.remat3_ypoint,
					gun.remat3_zpoint, gun.remat3_xrote, gun.remat3_yrote, gun.remat3_zrote,
					gun.remat3_xmove, gun.remat3_ymove, gun.remat3_zmove);
			gun.obj_model.renderPart("mat3");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat22, gun.remat22_time, gun.remat22_xpoint, gun.remat22_ypoint,
					gun.remat22_zpoint, gun.remat22_xrote, gun.remat22_yrote, gun.remat22_zrote,
					gun.remat22_xmove, gun.remat22_ymove, gun.remat22_zmove);
			gun.obj_model.renderPart("mat22");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat24, gun.remat24_time, gun.remat24_xpoint, gun.remat24_ypoint,
					gun.remat24_zpoint, gun.remat24_xrote, gun.remat24_yrote, gun.remat24_zrote,
					gun.remat24_xmove, gun.remat24_ymove, gun.remat24_zmove);
			gun.obj_model.renderPart("mat24");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat25, gun.remat25_time, gun.remat25_xpoint, gun.remat25_ypoint,
					gun.remat25_zpoint, gun.remat25_xrote, gun.remat25_yrote, gun.remat25_zrote,
					gun.remat25_xmove, gun.remat25_ymove, gun.remat25_zmove);
			gun.obj_model.renderPart("mat25");
			GL11.glPopMatrix();//glend2
		}
		
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat311, gun.remat31_time, gun.remat31_xpoint, gun.remat31_ypoint,
					gun.remat31_zpoint, gun.remat31_xrote, gun.remat31_yrote, gun.remat31_zrote,
					gun.remat31_xmove, gun.remat31_ymove, gun.remat31_zmove);
			gun.obj_model.renderPart("mat31");
			GL11.glPopMatrix();//glend2
		}
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.remat32, gun.remat32_time, gun.remat32_xpoint, gun.remat32_ypoint,
					gun.remat32_zpoint, gun.remat32_xrote, gun.remat32_yrote, gun.remat32_zrote,
					gun.remat32_xmove, gun.remat32_ymove, gun.remat32_zmove);
			gun.obj_model.renderPart("mat32");
			GL11.glPopMatrix();//glend2
		}
		
	}
	private void renderarm_reloattest(EntityPlayer entityplayer, ItemStack itemstack, ItemGunBase gun){
		ItemStack leftitem = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
		newrenderreload(gun, gun.remat0, gun.remat0_time, gun.remat0_xpoint, gun.remat0_ypoint,
				gun.remat0_zpoint, gun.remat0_xrote, gun.remat0_yrote, gun.remat0_zrote,
				gun.remat0_xmove, gun.remat0_ymove, gun.remat0_zmove);
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.rematrighthand, gun.rematrighthand_time, gun.rematrighthand_xpoint, gun.rematrighthand_ypoint,
					gun.rematrighthand_zpoint, gun.rematrighthand_xrote, gun.rematrighthand_yrote, gun.rematrighthand_zrote,
					gun.rematrighthand_xmove, gun.rematrighthand_ymove, gun.rematrighthand_zmove);
			gun.arm_model.renderPart("rightarm");
			GL11.glPopMatrix();//glend2
		}
		{
			GL11.glPushMatrix();//glstrt2
			newrenderreload(gun, gun.rematlefthand, gun.rematlefthand_time, gun.rematlefthand_xpoint, gun.rematlefthand_ypoint,
					gun.rematlefthand_zpoint, gun.rematlefthand_xrote, gun.rematlefthand_yrote, gun.rematlefthand_zrote,
					gun.rematlefthand_xmove, gun.rematlefthand_ymove, gun.rematlefthand_zmove);
			gun.arm_model.renderPart("leftarm");
			GL11.glPopMatrix();//glend2
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
	
	
	private void renderarm(EntityPlayer entityplayer, ItemStack itemstack, ItemGunBase gun){
		ItemStack leftitem = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
		
		{
			if(gun.newreload){
				GL11.glPushMatrix();//glstart1
				if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
					GL11.glTranslatef(gun.arm_r_posx,gun.arm_r_posy, gun.arm_r_posz);
				}else{
					if (gun.reloadmotion == 0) {
						for (int kais = 0; kais <= gun.mat_rk_rightarm; ++kais) {
							renderreload(gun, kais, gun.mat_r_rightarm);
						}
					} else {
						newrenderreload(gun, gun.rematrighthand, gun.rematrighthand_time, gun.rematrighthand_xpoint, gun.rematrighthand_ypoint,
								gun.rematrighthand_zpoint, gun.rematrighthand_xrote, gun.rematrighthand_yrote, gun.rematrighthand_zrote,
								gun.rematrighthand_xmove, gun.rematrighthand_ymove, gun.rematrighthand_zmove);
					}
				}
				GL11.glScalef(gun.arm_scale_r, gun.arm_scale_r, gun.arm_scale_r);
				gun.arm_model.renderPart("rightarm");
				GL11.glPopMatrix();//glend1
			}else{
				GL11.glPushMatrix();//glstart1
				GL11.glTranslatef(gun.arm_r_posx,gun.arm_r_posy, gun.arm_r_posz);
				GL11.glScalef(gun.arm_scale_r, gun.arm_scale_r, gun.arm_scale_r);
				gun.arm_model.renderPart("rightarm");
				GL11.glPopMatrix();//glend1
			}
			
		
		
		if (itemstack.getItemDamage() != itemstack.getMaxDamage()) {
			boolean underbarrelweapon = false;
			if(gun.true_mat100){
    			if(!gun.underbarrel.isEmpty() && gun.underbarrel.getItem() instanceof ItemGunBase) {
    				underbarrelweapon = true;
    				ItemGunBase under = (ItemGunBase) gun.underbarrel.getItem();
    				GL11.glTranslatef(-0.2F, -0.225F, -3.2F);
    				GL11.glTranslatef(gun.gripposx, gun.gripposy, gun.gripposz);
    				GL11.glTranslatef(-under.gun_underbarrel_x, -under.gun_underbarrel_y, -under.gun_underbarrel_z);
    				GL11.glTranslatef(under.arm_r_posx, under.arm_r_posy, under.arm_r_posz);
    			}
			}
			if(gun.true_mat9){
				if(gun.am_grip) {
					if(!gun.grip_item.isEmpty() && gun.grip_item.getItem() instanceof ItemAttachment) {
						ItemAttachment am = (ItemAttachment) gun.grip_item.getItem();
						if(am.grip_gripping_point) {
							underbarrelweapon = true;
							GL11.glTranslatef(-0.2F, -0.225F, -3.2F);
							GL11.glTranslatef(gun.gripposx, gun.gripposy, gun.gripposz);
							GL11.glTranslatef(am.grip_gripping_point_x, am.grip_gripping_point_y, am.grip_gripping_point_z);
						}
					}
				}
			}
			if(!underbarrelweapon)GL11.glTranslatef(gun.arm_l_posx,gun.arm_l_posy, gun.arm_l_posz);
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
			if(leftitem.isEmpty()){
				GL11.glScalef(gun.arm_scale_l, gun.arm_scale_l, gun.arm_scale_l);
				gun.arm_model.renderPart("leftarm");
			}
			
		}else{
			if(gun.newreload){
				if (gun.reloadmotion == 0) {
					for (int kais = 0; kais <= gun.mat_rk_leftarm; ++kais) {
						renderreload(gun, kais, gun.mat_r_leftarm);
					}
				} else {
					newrenderreload(gun, gun.rematlefthand, gun.rematlefthand_time, gun.rematlefthand_xpoint, gun.rematlefthand_ypoint,
							gun.rematlefthand_zpoint, gun.rematlefthand_xrote, gun.rematlefthand_yrote, gun.rematlefthand_zrote,
							gun.rematlefthand_xmove, gun.rematlefthand_ymove, gun.rematlefthand_zmove);
				}
				GL11.glScalef(gun.arm_scale_l, gun.arm_scale_l, gun.arm_scale_l);
				gun.arm_model.renderPart("leftarm");
			}else{
				GL11.glTranslatef(gun.arm_mag_l_posx,gun.arm_mag_l_posy, gun.arm_mag_l_posz);
				if(gun.retime < gun.reloadtime / 4){
					GL11.glTranslatef(0.0F, -gun.retime * 0.5F, 0F);
				}else if(gun.retime < gun.reloadtime / 2){
					GL11.glTranslatef(0.0F, gun.retime * 0.5F - (gun.reloadtime/2) * 0.5F, 0F);
				}else if(gun.retime < (gun.reloadtime * 3) / 4){
					GL11.glTranslatef(0.0F,0F, -gun.retime * 0.05F);
				}
				if(leftitem.isEmpty()){
					GL11.glScalef(gun.arm_scale_l, gun.arm_scale_l, gun.arm_scale_l);
					gun.arm_model.renderPart("leftarm");
				}
			}
			
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
	private static void newrenderreload(ItemGunBase gun, int remat, int[] remattime
			, float[] remat_xpoint, float[] remat_ypoint, float[] remat_zpoint
			, float[] remat_xrote, float[] remat_yrote, float[] remat_zrote
			, float[] remat_xmove, float[] remat_ymove, float[] remat_zmove){
		//if(mat != null)
		{
			for(int i = 1; i < 200; ++i) {
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
