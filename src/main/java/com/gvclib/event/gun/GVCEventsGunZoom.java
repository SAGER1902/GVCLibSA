package gvclib.event.gun;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.event.RenderTextEvent;
import gvclib.item.ItemGunBase;
import gvclib.item.gunbase.IGun_Shield;
import gvclib.item.gunbase.IGun_Sword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.registry.LanguageRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

import techguns.items.guns.GenericGun;
import net.minecraftforge.fml.common.Loader;

import gvclib.item.ItemGun_SR;
public class GVCEventsGunZoom {

	//public String ads;

	public boolean zoomtype;
	private RenderGameOverlayEvent eventParent;
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderfov(FOVUpdateEvent event)
	  {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {//item
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if (gun.aim_time>(gun.time_aim-2) && (gun.time_aim-2)>=0) {
				//if (itemstack.getItem() == gun && gun.scopezoom > 1.0) {
				if (itemstack.getItem() == gun) {
					NBTTagCompound nbt = itemstack.getTagCompound();
					if(!itemstack.hasTagCompound())return;
					boolean am_sight = nbt.getBoolean("am_sight");
					if(am_sight){
						event.setNewfov(event.getFov() / nbt.getFloat("scopezoom"));
					}else {
						if(gun.scopezoom > 1.0)event.setNewfov(event.getFov() / gun.scopezoom);
					}
				}
			}
		}//item
	}
	
	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderLiving_arm(RenderLivingEvent.Pre event)//举枪手臂动作
	  {
		ModelBase mainModel = event.getRenderer().getMainModel();
	    EntityLivingBase entity = event.getEntity();
		boolean left_key = mod_GVCLib.proxy.leftclick();
		
		if(entity instanceof EntityPlayer){
			if(mainModel instanceof ModelBiped) {
				ModelBiped biped = (ModelBiped) mainModel;
				ItemStack main = entity.getHeldItem(EnumHand.MAIN_HAND);
				ItemStack off = entity.getHeldItemOffhand();
				/*if(Loader.isModLoaded("techguns")) {
					if(!(entity instanceof EntityPlayer)){
						if(!main.isEmpty() && main.getItem() instanceof GenericGun)
						{
							biped.rightArmPose = biped.rightArmPose.BOW_AND_ARROW;
						}else if(!off.isEmpty() && off.getItem() instanceof GenericGun){
							biped.leftArmPose = biped.leftArmPose.BOW_AND_ARROW;
						}
					}
				}*/
				if(!main.isEmpty() && main.getItem() instanceof ItemGunBase && !(main.getItem() instanceof IGun_Sword))
				{
					ItemGunBase gun = (ItemGunBase)main.getItem();
					if((main.getItem() instanceof IGun_Shield))
					{
						if(entity.isSneaking()||entity.getActiveItemStack() == main)biped.rightArmPose = biped.rightArmPose.BOW_AND_ARROW;
					}else {
						if(entity instanceof EntityPlayer){
							if(main.getItemDamage() != main.getMaxDamage())biped.rightArmPose = biped.rightArmPose.BOW_AND_ARROW;
						}else{
							biped.rightArmPose = biped.rightArmPose.BOW_AND_ARROW;
						}
					}
					//biped.leftArmPose = biped.leftArmPose.BOW_AND_ARROW;
					 if(!off.isEmpty() && off.getItem() instanceof ItemGunBase && !(main.getItem() instanceof IGun_Sword))
					{
						 if((main.getItem() instanceof IGun_Shield))
						{
							 if(entity.isSneaking()||entity.getActiveItemStack() == main) {
								 biped.bipedLeftArm.rotateAngleY = 0.1F + biped.bipedHead.rotateAngleY;
								 biped.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + biped.bipedHead.rotateAngleX;
							 }
						}else {
							biped.bipedLeftArm.rotateAngleY = 0.1F + biped.bipedHead.rotateAngleY;
							biped.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + biped.bipedHead.rotateAngleX;
						}
					}
				}
				else if(!off.isEmpty() && off.getItem() instanceof ItemGunBase && !(off.getItem() instanceof IGun_Sword))
				{
					ItemGunBase gun = (ItemGunBase)off.getItem();
					NBTTagCompound nbt = off.getTagCompound();
					if((off.getItem() instanceof IGun_Shield))
					{
						if(entity.isSneaking()||entity.getActiveItemStack() == main)biped.leftArmPose = biped.leftArmPose.BOW_AND_ARROW;
					}else {
						if(entity instanceof EntityPlayer){
							if(main.getItemDamage() != main.getMaxDamage())biped.leftArmPose = biped.leftArmPose.BOW_AND_ARROW;
						}else{
							biped.leftArmPose = biped.leftArmPose.BOW_AND_ARROW;
						}
					}
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onEvent(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		World world = FMLClientHandler.instance().getWorldClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		ItemStack itemstackl = ((EntityPlayer) (entityplayer)).getHeldItemOffhand();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		// OpenGlHelper.
		// GL11.glEnable(GL11.GL_BLEND);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			RenderTextEvent renderevent = new RenderTextEvent(minecraft);
			boolean main = false;
			//cross_hair
			if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				if(/*!entityplayer.isSneaking()*/entityplayer.getActiveItemStack() != itemstack && mod_GVCLib.cfg_left_shootgun||!entityplayer.isSneaking() && !mod_GVCLib.cfg_left_shootgun|| gun.render_cross_sneak) {
					if(gun.render_gvc_cross && (!(itemstack.getItem() instanceof IGun_Sword) && !(itemstack.getItem() instanceof IGun_Shield))){
						if(entityplayer.isSneaking()){
							renderevent.rendergun_cross(itemstack, gun, i, j, 0.7F);
						}else if(entityplayer.isSprinting()){
							renderevent.rendergun_cross(itemstack, gun, i, j, 1.5F);
						}else{
							renderevent.rendergun_cross(itemstack, gun, i, j, 1F);
						}
						main = true;
					}
				}
			}
			if (!itemstackl.isEmpty() && itemstackl.getItem() instanceof ItemGunBase) {
				ItemGunBase gun = (ItemGunBase) itemstackl.getItem();
				if(!main) {
					if(/*!entityplayer.isSneaking()*/entityplayer.getActiveItemStack() != itemstack && mod_GVCLib.cfg_left_shootgun||!entityplayer.isSneaking() && !mod_GVCLib.cfg_left_shootgun|| gun.render_cross_sneak) {
						if(gun.render_gvc_cross && (!(itemstackl.getItem() instanceof IGun_Sword) && !(itemstackl.getItem() instanceof IGun_Shield))){
							if(entityplayer.isSneaking()){
								renderevent.rendergun_cross(itemstack, gun, i, j, 0.7F);
							}else{
								renderevent.rendergun_cross(itemstack, gun, i, j, 1F);
							}
						}
					}
				}
			}
			
			//
			if (!itemstackl.isEmpty() && itemstackl.getItem() instanceof ItemGunBase) {
				ItemGunBase gun = (ItemGunBase) itemstackl.getItem();
				
				String ads = "gvclib:textures/misc/scope.png";
				
				renderevent.rendergunl(itemstackl, gun, 0, j);
				
			}
			if (!itemstack.isEmpty() && itemstack.getItem() instanceof ItemGunBase) {
				ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				
				String ads = "gvclib:textures/misc/scope.png";
				
				renderevent.rendergun(itemstack, gun, i, j);
				{
					GL11.glPushMatrix();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					if(gun.mitarget != null) {
						FontRenderer fontReader = Minecraft.getMinecraft().fontRenderer;
						//fontReader.drawString("Lock", i/2, j/2 + 0, 0xFFFFFF);
					}
					GL11.glPopMatrix();
				}
				
				boolean zoomrender;
				NBTTagCompound nbt = itemstack.getTagCompound();
				if(!itemstack.hasTagCompound())return;
				boolean am_sight = nbt.getBoolean("am_sight");
				if(am_sight){
					//zoomrender = gun.am_zoomrendertex;
					zoomrender = nbt.getBoolean("am_zoomrendertex");
				}else{
					if(gun.true_mat4){
						zoomrender = gun.zoomrert;
					}else if(gun.true_mat5){
						zoomrender = gun.zoomrest;
					}else{
						//zoomrender = gun.model_zoomrender;
						zoomrender = gun.zoomrent;
					}
				}
				
				
				if (gun.aim_time>(gun.time_aim-2) && (gun.time_aim-2)>=0) {
					//if (!itemstack.isEmpty() && itemstack.getItem() == gun && gun.scopezoom > 1.0) {
					if (!itemstack.isEmpty() && itemstack.getItem() == gun) {
						if(am_sight){
							//ads = gun.am_ads_tex;
							ads = nbt.getString("am_ads_tex");
						}else{
							if(gun.true_mat4){
								if(gun.zoomrert){
									ads = gun.adstexturer;
								}
							}else if(gun.true_mat5){
								if(gun.zoomrest){
									ads = gun.adstextures;
								}
							}else{
								if(gun.zoomrent){
									ads = gun.adstexture;
								}
							}
						}
						
						
						
						//ads = "gvclib:textures/misc/scope.png";

						GlStateManager.enableBlend();
						GL11.glEnable(GL11.GL_BLEND);
						// GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);//11
						GL11.glPushMatrix();// 12
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
						GL11.glPushMatrix();// 13
						GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
								GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
								GlStateManager.DestFactor.ZERO);
						if(zoomrender){
							this.renderPumpkinBlur(minecraft, scaledresolution, ads);
						}
						GL11.glPopMatrix();// 13
						GL11.glPopMatrix();// 12
						// GL11.glPopAttrib();//11
						GlStateManager.disableBlend();

					} else if (!itemstack.isEmpty() && itemstack.getItem() instanceof Item) {
					}
				}
				
				GuiIngameForge.renderCrosshairs = gun.rendercross;
				this.zoomtype = true;
				
				{
					NBTTagCompound nbt_entity = entityplayer.getEntityData();
					int setting = nbt_entity.getInteger("Gun_Setting");
					
					FontRenderer fontReader = minecraft.fontRenderer;
					GL11.glPushMatrix();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
					String dx = String.valueOf(gun.model_x_ads);
					String dy = String.valueOf(gun.model_y_ads);
					String dz = String.valueOf(gun.model_z_ads);
					
					String dx7 = String.valueOf((gun.model_x_ads - 1.388F) / (-2));
					String dy7 = String.valueOf((gun.model_y_ads + 3.6796F) / (2.096F));
					String dz7 = String.valueOf((gun.model_z_ads + 1.5F) / (-1.25F));
					
					String dxr = String.valueOf(gun.model_x_adsr);
					String dyr = String.valueOf(gun.model_y_adsr);
					String dzr = String.valueOf(gun.model_z_adsr);
					
					String dxr7 = String.valueOf((gun.model_x_adsr - 1.388F) / (-2));
					String dyr7 = String.valueOf((gun.model_y_adsr + 3.6796F) / (2.096F));
					String dzr7 = String.valueOf((gun.model_z_adsr + 1.5F) / (-1.25F));
					
					String dxs = String.valueOf(gun.model_x_adss);
					String dys = String.valueOf(gun.model_y_adss);
					String dzs = String.valueOf(gun.model_z_adss);
					
					String dxs7 = String.valueOf((gun.model_x_adss - 1.388F) / (-2));
					String dys7 = String.valueOf((gun.model_y_adss + 3.6796F) / (2.096F));
					String dzs7 = String.valueOf((gun.model_z_adss + 1.5F) / (-1.25F));
					
					String dxal = String.valueOf(gun.arm_l_posx);
					String dyal = String.valueOf(gun.arm_l_posy);
					String dzal = String.valueOf(gun.arm_l_posz);
					
					String dxal7 = String.valueOf((gun.arm_l_posx));
					String dyal7 = String.valueOf((gun.arm_l_posy - 0.75F) / (-2.5F));
					String dzal7 = String.valueOf((gun.arm_l_posz + 2.5716F) / (-2.143F));
					
					String spt_x = String.valueOf(gun.Sprintoffsetx);
					String spt_y = String.valueOf(gun.Sprintoffsety);
					String spt_z = String.valueOf(gun.Sprintoffsetz);
					
					String spt_r_x = String.valueOf(gun.Sprintrotationx);
					String spt_r_y = String.valueOf(gun.Sprintrotationy);
					String spt_r_z = String.valueOf(gun.Sprintrotationz);
					if(setting == 1){
						fontReader.drawString("ads_non", scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 - 10, 0xFFFFFF);
						fontReader.drawString("ads_x"+ "("+ dx7 + ")" + dx, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 00, 0xFFFFFF);
						fontReader.drawString("ads_y"+ "("+ dy7 + ")" + dy, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 10, 0xFFFFFF);
						fontReader.drawString("ads_z"+ "("+ dz7 + ")" + dz, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 20, 0xFFFFFF);
					}else if(setting == 2){
						fontReader.drawString("ads_reddot", scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 - 10, 0xFFFFFF);
						fontReader.drawString("adsr_x"+ "("+ dxr7 + ")" + dxr, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 00, 0xFFFFFF);
						fontReader.drawString("adsr_y"+ "("+ dyr7 + ")" + dyr, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 10, 0xFFFFFF);
						fontReader.drawString("adsr_z"+ "("+ dzr7 + ")" + dzr, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 20, 0xFFFFFF);
					}else if(setting == 3){
						fontReader.drawString("ads_scope", scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 - 10, 0xFFFFFF);
						fontReader.drawString("adss_x"+ "("+ dxs7 + ")" + dxs, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 00, 0xFFFFFF);
						fontReader.drawString("adss_y"+ "("+ dys7 + ")" + dys, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 10, 0xFFFFFF);
						fontReader.drawString("adss_z"+ "("+ dzs7 + ")" + dzs, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 20, 0xFFFFFF);
					}else if(setting == 4){
						fontReader.drawString("Arm_left", scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 - 10, 0xFFFFFF);
						fontReader.drawString("Arm_left_x"+ "("+ dxal7 + ")" + dxal, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 00, 0xFFFFFF);
						fontReader.drawString("Arm_left_y"+ "("+ dyal7 + ")" + dyal, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 10, 0xFFFFFF);
						fontReader.drawString("Arm_left_z"+ "("+ dzal7 + ")" + dzal, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 20, 0xFFFFFF);
					}else if(setting == 5){
						fontReader.drawString("SprintingPoint", scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 - 10, 0xFFFFFF);
						fontReader.drawString("SprintingPoint_x" + spt_x, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 00, 0xFFFFFF);
						fontReader.drawString("SprintingPoint_y" + spt_y, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 10, 0xFFFFFF);
						fontReader.drawString("SprintingPoint_z" + spt_z, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 20, 0xFFFFFF);
						fontReader.drawString("SprintingRotation_x" + spt_r_x, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 30, 0xFFFFFF);
						fontReader.drawString("SprintingRotation_y" + spt_r_y, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 40, 0xFFFFFF);
						fontReader.drawString("SprintingRotation_z" + spt_r_z, scaledresolution.getScaledWidth()/2 - 80,  
								scaledresolution.getScaledHeight()/2 + 50, 0xFFFFFF);
					}
					GL11.glPopMatrix();
				}
				
			} else {
				if (this.zoomtype == true) {
					this.zoomtype = false;
					GuiIngameForge.renderCrosshairs = true;
				}

			}
			
		}
	}

	@SideOnly(Side.CLIENT)
	protected void renderPumpkinBlur(Minecraft minecraft, ScaledResolution scaledRes, String adss) {
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		minecraft.getTextureManager().bindTexture(new ResourceLocation(adss));
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
