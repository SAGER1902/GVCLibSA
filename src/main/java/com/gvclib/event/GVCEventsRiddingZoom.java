package gvclib.event;

import java.util.List;

import org.lwjgl.opengl.GL11;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.entity.living.EntityVehicleBase;
import gvclib.entity.living.ISoldier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GVCEventsRiddingZoom {

	@SubscribeEvent
	public void onUPEvent(LivingUpdateEvent event) {
		EntityLivingBase target = event.getEntityLiving();
		if (target instanceof EntityPlayer) {
			Minecraft minecraft = Minecraft.getMinecraft();
			EntityPlayer entityplayer = (EntityPlayer) target;
			if (entityplayer.getRidingEntity() instanceof EntityVehicleBase && entityplayer.getRidingEntity() != null) {// 1
				EntityVehicleBase balaam = (EntityVehicleBase) entityplayer.getRidingEntity();
				if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					if (mod_GVCLib.proxy.setting()) {
						balaam.debag_test_client = true;
					}
					if(balaam.debag_test_client) {
						if (mod_GVCLib.proxy.left()) {
							balaam.ridding_view1_x = balaam.ridding_view1_x + 0.1F;
						}
						if (mod_GVCLib.proxy.right()) {
							balaam.ridding_view1_x = balaam.ridding_view1_x - 0.1F;
						}
						if (mod_GVCLib.proxy.up()) {
							balaam.ridding_view1_y = balaam.ridding_view1_y + 0.1F;
						}
						if (mod_GVCLib.proxy.down()) {
							balaam.ridding_view1_y = balaam.ridding_view1_y - 0.1F;
						}
						if (mod_GVCLib.proxy.LBRACKET()) {
							balaam.ridding_view1_z = balaam.ridding_view1_z + 0.1F;
						}
						if (mod_GVCLib.proxy.RBRACKET()) {
							balaam.ridding_view1_z = balaam.ridding_view1_z - 0.1F;
						}

						if (mod_GVCLib.proxy.numpad_4()) {
							balaam.riddingx[0] = balaam.riddingx[0] + 0.1F;
						}
						if (mod_GVCLib.proxy.numpad_6()) {
							balaam.riddingx[0] = balaam.riddingx[0] - 0.1F;
						}
						if (mod_GVCLib.proxy.numpad_8()) {
							balaam.riddingy[0] = balaam.riddingy[0] + 0.1F;
						}
						if (mod_GVCLib.proxy.numpad_2()) {
							balaam.riddingy[0] = balaam.riddingy[0] - 0.1F;
						}
						if (mod_GVCLib.proxy.numpad_1()) {
							balaam.riddingz[0] = balaam.riddingz[0] + 0.1F;
						}
						if (mod_GVCLib.proxy.numpad_3()) {
							balaam.riddingz[0] = balaam.riddingz[0] - 0.1F;
						}
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void ridding_onground(EntityViewRenderEvent.CameraSetup event)
	  {
		Minecraft minecraft = Minecraft.getMinecraft();
		//FontRenderer fontReader = minecraft.fontRenderer;
		EntityPlayer entityplayer = minecraft.player;
		if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
			EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
				if(balaam instanceof EntityVehicleBase) {
					 if(balaam.onGround){
						 EntityVehicleBase vehicle = (EntityVehicleBase) balaam;
						 GL11.glTranslatef(vehicle.model_angle_offset_x, -vehicle.model_angle_offset_y, vehicle.model_angle_offset_z);
						 GL11.glRotatef(vehicle.model_angle_x, 1.0F, 0.0F, 0.0F);
						 GL11.glRotatef(vehicle.model_angle_y, 0.0F, 1.0F, 0.0F);
						 GL11.glRotatef(vehicle.model_angle_z, 0.0F, 0.0F, 1.0F);
					 }
				}
		}
	  }


	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void rendertest(EntityViewRenderEvent.CameraSetup event)
	  {
		Minecraft minecraft = Minecraft.getMinecraft();
		//FontRenderer fontReader = minecraft.fontRenderer;
		EntityPlayer entityplayer = minecraft.player;
		if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
			EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
			if(minecraft.gameSettings.thirdPersonView == 0){
				if(balaam.ridding_f){
				   if(!balaam.onGround){
				   }
				   GL11.glRotatef((float) balaam.throte, 0.0F, 0.0F, 1.0F);
				}
				if(balaam instanceof EntityVehicleBase) {
					EntityVehicleBase ve = (EntityVehicleBase) balaam;
					if(ve.spg_mode) {
					}else {
						/*if(balaam.getFTMode() == 1){
							if(ve.vehicletype == 1)GL11.glTranslatef(balaam.ridding_view_gunner_x, balaam.ridding_view_gunner_y, balaam.ridding_view_gunner_z);
							//fontReader.drawString(dx + " /" + d1x, i -70, j - 60 + 0, 0xFFFFFF);
						}else if(balaam.getFTMode() == 2){
							GL11.glTranslatef(balaam.ridding_view_gunner_x, balaam.ridding_view_gunner_y, balaam.ridding_view_gunner_z);
							entityplayer.rotationPitch = 60F;
							entityplayer.rotationYawHead = balaam.rotationYaw;
							entityplayer.rotationYaw = balaam.rotationYaw;
						}else */
						if(balaam.ridding_view1_turretmode){
							//GL11.glTranslatef(balaam.ridding_view1_x, balaam.ridding_view1_y, balaam.ridding_view1_z);
							float f1 = entityplayer.rotationYaw * (2 * (float) Math.PI / 360);
							GL11.glTranslatef(balaam.ridding_view1_x * (float)Math.sin(f1), balaam.ridding_view1_y, balaam.ridding_view1_z * (float)Math.cos(f1));
						}else {
							GL11.glTranslatef(balaam.ridding_view1_x, balaam.ridding_view1_y, balaam.ridding_view1_z);
						}
					}
				}else
				/*if(balaam.getFTMode() == 1){
					GL11.glTranslatef(balaam.ridding_view_gunner_x, balaam.ridding_view_gunner_y, balaam.ridding_view_gunner_z);
				}else if(balaam.getFTMode() == 2){
					GL11.glTranslatef(balaam.ridding_view_gunner_x, balaam.ridding_view_gunner_y, balaam.ridding_view_gunner_z);
					entityplayer.rotationPitch = 60F;
					entityplayer.rotationYawHead = balaam.rotationYaw;
					entityplayer.rotationYaw = balaam.rotationYaw;
				}
				else */
				{
					GL11.glTranslatef(balaam.ridding_view1_x, balaam.ridding_view1_y, balaam.ridding_view1_z);
				}
			}
			if(minecraft.gameSettings.thirdPersonView == 1){
				if(balaam.ridding_f){
					float ix = 0;
					float iz = 0;
					float f1 = event.getEntity().rotationYaw * (2 * (float) Math.PI / 360);
					ix -= MathHelper.sin(f1) * 1;
					iz += MathHelper.cos(f1) * 1;
				GL11.glTranslatef(0, -2, -5);
				    if(!balaam.onGround){
				    }
				}else if(balaam.ridding_t){
					float ix = 0;
					float iz = 0;
					float f1 = event.getEntity().rotationYaw * (2 * (float) Math.PI / 360);
					ix -= MathHelper.sin(f1) * 1;
					iz += MathHelper.cos(f1) * 1;
				GL11.glTranslatef(0, -1, -2);
				}else {
					EntityVehicleBase ve = null;
					if(balaam instanceof EntityVehicleBase) {
						ve = (EntityVehicleBase) balaam;
						if(ve.spg_mode) {
							//GL11.glTranslatef(ve.spg_yaw, -7, ve.spg_picth);
							double xrange = ve.spg_yaw;
							double zrange = ve.spg_picth;
							float range = (float)MathHelper.sqrt(xrange * xrange + zrange * zrange) * 0.8F;
							float yy = range * 0.5F;
							//GL11.glTranslatef(0, -12 - yy, range - 5);
							/*if(range < 20) {
								GL11.glTranslatef(0,  - yy, range - 5);
							}else {
								GL11.glTranslatef(0,  - 10, 15);
							}*/
							GL11.glTranslatef(0, -20, -5);
							boolean right = mod_GVCLib.proxy.rightclick();// 1
							if(right){}
							else {
								float kaku = 30F - yy;
								if(kaku < 5)kaku = 5;
								entityplayer.rotationPitch = kaku;
								GL11.glRotatef(20, 1.0F,  0.0F, 0.0F);
							}
						}
					}
					if(balaam.getFTMode() != 1){
						
						float f1 = entityplayer.rotationPitch * (2 * (float) Math.PI / 360);
						//float f1 = entityplayer.rotationPitch;
						
						GL11.glTranslatef(balaam.ridding_view_x, balaam.ridding_view_y  * (float)Math.cos(f1), balaam.ridding_view_z * (float)Math.cos(f1));




					}else {
						if(ve != null && ve.vehicletype == 2) {
							//GL11.glTranslatef(balaam.ridding_view_gunner_x, balaam.ridding_view_gunner_y, balaam.ridding_view_gunner_z);
							GL11.glTranslatef(balaam.ridding_view_x, balaam.ridding_view_y, balaam.ridding_view_z);
						}else {
							GL11.glTranslatef(balaam.ridding_view_x, balaam.ridding_view_y, balaam.ridding_view_z);
						}
					}

				}
			}
		} // 1
	  }

	@SideOnly(Side.CLIENT)
	  @SubscribeEvent
		  public void renderridding(RenderPlayerEvent.Pre event)
		  {
		  //Minecraft minecraft = FMLClientHandler.instance().getClient();
			EntityPlayer entityplayer = event.getEntityPlayer();
			if(entityplayer != null){
				if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {
					EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
					if(balaam.ridding_invisible) {
						/*RenderPlayer renderplayer = event.getRenderer();
						renderplayer.getMainModel().bipedHead.isHidden = true;
						renderplayer.getMainModel().bipedHeadwear.isHidden = true;
						renderplayer.getMainModel().bipedBody.isHidden = true;
						renderplayer.getMainModel().bipedRightArm.isHidden = true;
						renderplayer.getMainModel().bipedLeftArm.isHidden = true;
						renderplayer.getMainModel().bipedRightLeg.isHidden = true;
						renderplayer.getMainModel().bipedLeftLeg.isHidden = true;*/
						event.setCanceled(true);
					}
				}
			}
		  }

	@SideOnly(Side.CLIENT)
	  @SubscribeEvent
		  public void renderridding_l(RenderLivingEvent.Pre event)
		  {
		  //Minecraft minecraft = FMLClientHandler.instance().getClient();
		    EntityLivingBase entityplayer = event.getEntity();
			if(entityplayer != null){
				if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {
					EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
					if(balaam.ridding_invisible) {
						event.setCanceled(true);
					}
				}
			}
		  }

	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void Night_Vision(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		World world = FMLClientHandler.instance().getWorldClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		EntityPlayer entityplayer = minecraft.player;
		if(entityplayer != null) {
			ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
			FontRenderer fontrenderer = minecraft.fontRenderer;
			if (entityplayer.getRidingEntity() instanceof EntityVehicleBase && entityplayer.getRidingEntity() != null) {// 1
				EntityVehicleBase balaam = (EntityVehicleBase) entityplayer.getRidingEntity();
				if (balaam.night_vision && balaam.getBoosttime() == 1) 
				{
					GL11.glPushMatrix();
					GlStateManager.enableBlend();
					GL11.glEnable(GL11.GL_BLEND);
					
					GlStateManager.disableDepth();
					GlStateManager.depthMask(false);
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					GlStateManager.color(0.5F, 0.5F, 0.5F, 0.8F);
					GlStateManager.disableAlpha();
					minecraft.getTextureManager().bindTexture(new ResourceLocation(balaam.night_vision_tex));
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
			        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			        bufferbuilder.pos(0.0D, (double)scaledresolution.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
			        bufferbuilder.pos((double)scaledresolution.getScaledWidth(), (double)scaledresolution.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
			        bufferbuilder.pos((double)scaledresolution.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
			        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
					tessellator.draw();
					GlStateManager.depthMask(true);
					GlStateManager.enableDepth();
					GlStateManager.enableAlpha();
					GlStateManager.color(0.5F, 0.5F, 0.5F, 0.8F);
					
					GlStateManager.disableBlend();
					GL11.glPopMatrix();
				}
			}//item
		}
	}/**/
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEvent(RenderGameOverlayEvent.Text event) {
		Minecraft minecraft = Minecraft.getMinecraft();
		World world = FMLClientHandler.instance().getWorldClient();
		ScaledResolution scaledresolution = new ScaledResolution(minecraft);
		int i = scaledresolution.getScaledWidth();
		int j = scaledresolution.getScaledHeight();
		EntityPlayer entityplayer = minecraft.player;
		ItemStack itemstack = ((EntityPlayer) (entityplayer)).getHeldItemMainhand();
		FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.entityRenderer.setupOverlayRendering();
		// OpenGlHelper.

		// GL11.glEnable(GL11.GL_BLEND);
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			if (entityplayer.getRidingEntity() instanceof EntityVehicleBase && entityplayer.getRidingEntity() != null) {// 1
				EntityVehicleBase balaam = (EntityVehicleBase) entityplayer.getRidingEntity();

				RenderGUIEvent renderevent = new RenderGUIEvent(minecraft);
				if(minecraft.gameSettings.thirdPersonView == 0) {
					if(balaam.render_hud_icon)renderevent.render(minecraft, balaam, entityplayer, i, j, balaam.hud_icon);
					if(balaam.render_hud_icon_bomber)renderevent.render_bomber(minecraft, balaam, entityplayer, i, j, balaam.hud_icon_bomber);
					if((balaam.vehicletype == 1 || balaam.vehicletype == 4) && balaam.getFTMode() == 1){
						renderevent.render_Cruising_MODE(minecraft, balaam, entityplayer, i/2, j/2);
					}
					if(balaam.vehicletype == 2 && balaam.getFTMode() == 1){
						renderevent.render_Hovering_MODE(minecraft, balaam, entityplayer, i/2, j/2);
					}
					if((balaam.vehicletype == 0 || balaam.vehicletype == 3 || balaam.vehicletype == 5) && balaam.getFTMode() == 1){
						renderevent.render_SPG_MODE(minecraft, balaam, entityplayer, i/2, j/2);
					}
				}else {
					renderevent.render(minecraft, balaam, entityplayer, i, j, balaam.hud_icon);
					if((balaam.vehicletype == 1 || balaam.vehicletype == 4) && balaam.getFTMode() == 1){
						renderevent.render_Cruising_MODE(minecraft, balaam, entityplayer, i/2, j/2);
					}
					if(balaam.vehicletype == 2 && balaam.getFTMode() == 1){
						renderevent.render_Hovering_MODE(minecraft, balaam, entityplayer, i/2, j/2);
					}
					if((balaam.vehicletype == 0 || balaam.vehicletype == 3 || balaam.vehicletype == 5) && balaam.getFTMode() == 1){
						renderevent.render_SPG_MODE(minecraft, balaam, entityplayer, i/2, j/2);
					}
				}
				if(balaam.render_hud_icon_hori)renderevent.renderHori(minecraft, balaam, entityplayer, i, j, balaam.hud_icon_hori);
				boolean right = mod_GVCLib.proxy.rightclick();// 1

				if(balaam.render_hud_scope && minecraft.gameSettings.thirdPersonView == 0) {
					if(right && balaam.render_hud_scope_zoom) {
						this.renderPumpkinBlur(minecraft, scaledresolution, balaam.hud_icon_scope_zoom);
					}else {
						this.renderPumpkinBlur(minecraft, scaledresolution, balaam.hud_icon_scope);
					}
				}
				GuiIngame g = minecraft.ingameGUI;
				minecraft.entityRenderer.setupOverlayRendering();
				{
					if(balaam.getRemain_L() <= 0){
						if(balaam.reload1 > 0 && balaam.reload1 < balaam.reload_time1)
						{
							GL11.glPushMatrix();//111
							GL11.glEnable(GL11.GL_BLEND);
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
							GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
									GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
									GlStateManager.DestFactor.ZERO);
							float xpoint = (scaledresolution.getScaledWidth()/2-8)*16;
							float ypoint = (scaledresolution.getScaledHeight()/2-8)*16;
							minecraft.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/reload1.png"));
							GL11.glTranslatef(0.5F,0.5F, 0F);
			    			GL11.glScalef(0.0625f, 0.0625f, 1);
			    			GL11.glTranslatef(xpoint + (8 * 16), ypoint+ (8 * 16), 0F);
							GL11.glRotatef(balaam.reload1*4, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(-xpoint- (8 * 16), -ypoint- (8 * 16), 0F);
			    			g.drawTexturedModalRect(xpoint,ypoint, 0,0, 256, 256);
			    			GL11.glPopMatrix();//111
						}
					}
					if(balaam.getRemain_R() <= 0){
						if(balaam.reload2 > 0 && balaam.reload2 < balaam.reload_time2)
						{
							GL11.glPushMatrix();//111
							GL11.glEnable(GL11.GL_BLEND);
							GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
							GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
									GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
									GlStateManager.DestFactor.ZERO);
							float xpoint = (scaledresolution.getScaledWidth()/2-8)*16;
							float ypoint = (scaledresolution.getScaledHeight()/2-8)*16;
							minecraft.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/reload2.png"));
							GL11.glTranslatef(0.5F,0.5F, 0F);
			    			GL11.glScalef(0.0625f, 0.0625f, 1);
			    			GL11.glTranslatef(xpoint + (8 * 16), ypoint+ (8 * 16), 0F);
							GL11.glRotatef(balaam.reload2*4, 0.0F, 0.0F, 1.0F);
							GL11.glTranslatef(-xpoint- (8 * 16), -ypoint- (8 * 16), 0F);
			    			g.drawTexturedModalRect(xpoint,ypoint, 0,0, 256, 256);
			    			GL11.glPopMatrix();//111
						}
					}
				}
			} // 1

			//GuiIngameForge.renderGameOverlay(event.getPartialTicks());
		}
	}


	@SideOnly(Side.CLIENT)
	  @SubscribeEvent
	  public void onEventRidding(RenderGameOverlayEvent.Text event)
	  {
		  Minecraft minecraft = FMLClientHandler.instance().getClient();
		  World world = FMLClientHandler.instance().getWorldClient();
		  ScaledResolution scaledresolution = new ScaledResolution(minecraft);
	        int i = scaledresolution.getScaledWidth();
	        int j = scaledresolution.getScaledHeight();
			EntityPlayer entityplayer = minecraft.player;
			ItemStack itemstack = ((EntityPlayer)(entityplayer)).getHeldItemMainhand();
			FontRenderer fontrenderer = minecraft.fontRenderer;
	        minecraft.entityRenderer.setupOverlayRendering();
	        //OpenGlHelper.

	        boolean cx = true;

    if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
		{
    	RenderHUDEvent renderevent = new RenderHUDEvent(minecraft);
			if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
				EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
				GuiIngame g = minecraft.ingameGUI;
				renderevent.renderHP(minecraft, balaam, entityplayer, i, j);
				renderevent.rendertank(minecraft, balaam, entityplayer, i, j);
				if(balaam.renderhud){
					renderevent.renderhud(minecraft, balaam, entityplayer, i, j);
				}
			} // 1
    	{
    		GL11.glPushMatrix();//31
    		GL11.glEnable(GL11.GL_BLEND);
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		if (entityplayer.getRidingEntity() instanceof EntityGVCLivingBase && entityplayer.getRidingEntity() != null) {// 1
    			EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
    			if(balaam.renderhud && balaam.render_rader){
    				{
        				GL11.glPushMatrix();
            			GuiIngame g  = minecraft.ingameGUI;
            			minecraft.renderEngine.bindTexture(new ResourceLocation("gvclib:textures/hud/radar.png"));
            			GL11.glScalef(0.4f, 0.4f, 1);
            			g.drawTexturedModalRect((scaledresolution.getScaledWidth())*(1/4),
            					(scaledresolution.getScaledHeight()-scaledresolution.getScaledHeight())*(1/4), 0,0, 256, 256);
            			GL11.glPopMatrix();
        			}
        			{
        				Entity entity = null;
        				List<Entity> llist = balaam.world.getEntitiesWithinAABBExcludingEntity(balaam,
        						balaam.getEntityBoundingBox().expand(balaam.motionX, balaam.motionY, balaam.motionZ).grow(100.0D));
        				if (llist != null) {
        					for (int lj = 0; lj < llist.size(); lj++) {
        						Entity entity1 = (Entity) llist.get(lj);
        						if (entity1.canBeCollidedWith() && entity1 instanceof EntityLivingBase) {
        							if (((EntityLivingBase) entity1).getHealth() > 0.0F)
        							{
        								if(entity1 instanceof EntityVehicleBase) {
            								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/vehicle_dot.png");
            							}else
            							if(entity1 instanceof EntitySlime) {
                								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/enemy_slime.png");
                						}else
        								if(entity1 instanceof IMob) {
            								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/enemy.png");
            							}else
            							if(entity1 instanceof ISoldier) {
            								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/ally.png");
            							}else
                							if(entity1 instanceof EntityGolem) {
                								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/ally.png");
            							}else
            							if(entity1 instanceof EntityPlayer && entity1 != entityplayer) {
            								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/player.png");
            							}else
            							if(entity1 instanceof EntityLivingBase) {
            								this.renderRadarEntity(minecraft, scaledresolution, balaam, entity1, "gvclib:textures/hud/animal.png");
            							}
        							}
        						}
        					}
        				}
        			}
    			}
    		}
    		GL11.glPopMatrix();//31
    	}


		}

	  }

	protected void renderRadarEntity(Minecraft minecraft, ScaledResolution scaledresolution,
			Entity entity, Entity entity1, String tex)
	  {
		float xx = (scaledresolution.getScaledWidth()+32)*(1/4);
		float yy = (scaledresolution.getScaledHeight()-scaledresolution.getScaledHeight()+32)*(1/4);
		float x = xx*4+51;
		float y = yy*4+51;
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			//GL11.glEnable(GL11.GL_TEXTURE_2D);
			GuiIngame g  = minecraft.ingameGUI;
			minecraft.renderEngine.bindTexture(new ResourceLocation(tex));
			GL11.glTranslatef(x, y, 0F);
			GL11.glRotatef(-180F, 0.0F, 0.0F, 1.0F);
	//		GL11.glScalef(-1.0f, 1.0f, 1);
			GL11.glRotatef(-minecraft.player.rotationYawHead, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-x,-y, 0F);
			GL11.glScalef(0.4f, 0.4f, 1);
			g.drawTexturedModalRect(xx + -(int)(entity.posX - entity1.posX),
					yy + -(int)(entity.posZ - entity1.posZ), 0,0, 256, 256);

			//GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
	  }

	protected float zLevel;
	/**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)this.zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)this.zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }



	@SideOnly(Side.CLIENT)
    @SubscribeEvent
	  public void renderzoom(FOVUpdateEvent event)
	  {
		Minecraft minecraft = Minecraft.getMinecraft();
		EntityPlayer entityplayer = minecraft.player;
		boolean right = mod_GVCLib.proxy.rightclick();// 1
			if(entityplayer.getRidingEntity() instanceof EntityGVCLivingBase  && entityplayer.getRidingEntity() != null){
				EntityGVCLivingBase balaam = (EntityGVCLivingBase) entityplayer.getRidingEntity();
				if(right && balaam.ridding_canzoom){
					if(minecraft.gameSettings.thirdPersonView == 0){
						event.setNewfov(event.getFov() / balaam.ridding_zoom_first);
						//Minecraft.getMinecraft().gameSettings.mouseSensitivity = 0.05f;
					}else {
						event.setNewfov(event.getFov() / balaam.ridding_zoom);
						//Minecraft.getMinecraft().gameSettings.mouseSensitivity = 0.5f;
					}
				}
				if(balaam instanceof EntityVehicleBase) {
					if(minecraft.gameSettings.thirdPersonView == 1){
						EntityVehicleBase ve = (EntityVehicleBase) balaam;
						if(ve.spg_mode) {
							double xrange = ve.spg_yaw;
							double zrange = ve.spg_picth;
							float range = (float)MathHelper.sqrt(xrange * xrange + zrange * zrange) * 0.1F;
							/*if(range < 10) {
								event.setNewfov(event.getFov() - range);
							}else {
								event.setNewfov(event.getFov() - 10);
							}*/
						}
						/*if(ve.spg_mode) {
							double d5 = ve.spg_yaw - ve.posX;
							double d7 = ve.spg_picth - ve.posZ;
							float yawoffset = -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
							entityplayer.setRotationYawHead(yawoffset);
							entityplayer.setRenderYawOffset(yawoffset);
						}*/
					}
				}
			}
	  }

	@SideOnly(Side.CLIENT)
	protected void renderPumpkinBlur(Minecraft minecraft, ScaledResolution scaledRes, String adss) {
		GL11.glPushMatrix();//111
		GlStateManager.disableDepth();
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.95F);
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
		GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();//111
	}
}
