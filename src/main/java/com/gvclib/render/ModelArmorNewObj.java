
package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.mod_GVCLib;
import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.item.ItemArmor_NewObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.FMLClientHandler;

import net.minecraft.client.renderer.OpenGlHelper;
//21/6/8
public class ModelArmorNewObj extends ModelBiped {
	public ModelArmorNewObj() {
		textureWidth = 512;
		textureHeight = 256;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		float head_x = this.bipedHead.rotationPointX * 0.0625F;
		float head_y = 1.501F - this.bipedHead.rotationPointY * 0.0625F;
		float head_z = this.bipedHead.rotationPointZ * 0.0625F;
		
		float body_x = this.bipedBody.rotationPointX * 0.0625F;
		float body_y = 1.501F - this.bipedBody.rotationPointY * 0.0625F;
		float body_z = this.bipedBody.rotationPointZ * 0.0625F;
		
		float arm_r_x = this.bipedRightArm.rotationPointX * 0.0625F;
		float arm_r_y = 1.501F - this.bipedRightArm.rotationPointY * 0.0625F;
		float arm_r_z = this.bipedRightArm.rotationPointZ * 0.0625F;
		
		float arm_l_x = this.bipedLeftArm.rotationPointX * 0.0625F;
		float arm_l_y = 1.501F - this.bipedLeftArm.rotationPointY * 0.0625F;
		float arm_l_z = this.bipedLeftArm.rotationPointZ * 0.0625F;
		
		float leg_r_x = this.bipedRightLeg.rotationPointX * 0.0625F;
		float leg_r_y = 1.501F - this.bipedRightLeg.rotationPointY * 0.0625F;
		float leg_r_z = this.bipedRightLeg.rotationPointZ * 0.0625F;
		
		float leg_l_x = this.bipedLeftLeg.rotationPointX * 0.0625F;
		float leg_l_y = 1.501F - this.bipedLeftLeg.rotationPointY * 0.0625F;
		float leg_l_z = this.bipedLeftLeg.rotationPointZ * 0.0625F;
		
		
		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) entityIn;
			if ((!entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
				render_objmodel(entityIn, armor, "head",0.0F, 1.501F, 0.0F, head_x, head_y, head_z, this.bipedHead.rotateAngleX, this.bipedHead.rotateAngleY,this.bipedHead.rotateAngleZ, 0);
			}
			if ((!entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty())
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
				render_objmodel(entityIn, armor, "body",0.0F, 1.501F, 0.0F,body_x, body_y, body_z, this.bipedBody.rotateAngleX, this.bipedBody.rotateAngleY,this.bipedBody.rotateAngleZ, 1);
				render_objmodel(entityIn, armor, "rightarm",-0.3125F, 1.376F, 0.0F,arm_r_x, arm_r_y, arm_r_z, this.bipedRightArm.rotateAngleX, this.bipedRightArm.rotateAngleY,this.bipedRightArm.rotateAngleZ, 2);
				render_objmodel(entityIn, armor, "leftarm",0.3125F, 1.376F, 0.0F,arm_l_x, arm_l_y, arm_l_z, this.bipedLeftArm.rotateAngleX, this.bipedLeftArm.rotateAngleY,this.bipedLeftArm.rotateAngleZ, 3);
			}
			if ((!entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty())
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
				render_objmodel(entityIn, armor, "rightleg",-0.11875F, 0.751F, 0.0F,leg_r_x, leg_r_y, leg_r_z, this.bipedRightLeg.rotateAngleX, this.bipedRightLeg.rotateAngleY,this.bipedRightLeg.rotateAngleZ, 4);
				render_objmodel(entityIn, armor, "leftleg",0.11875F, 0.751F, 0.0F,leg_l_x, leg_l_y, leg_l_z, this.bipedLeftLeg.rotateAngleX, this.bipedLeftLeg.rotateAngleY,this.bipedLeftLeg.rotateAngleZ, 5);
			}
			if ((!entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty())
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
				render_objmodel(entityIn, armor, "rightboots",-0.11875F, 0.751F, 0.0F,leg_r_x, leg_r_y, leg_r_z, this.bipedRightLeg.rotateAngleX, this.bipedRightLeg.rotateAngleY,this.bipedRightLeg.rotateAngleZ, 6);
				render_objmodel(entityIn, armor, "leftboots",0.11875F, 0.751F, 0.0F,leg_l_x, leg_l_y, leg_l_z, this.bipedLeftLeg.rotateAngleX, this.bipedLeftLeg.rotateAngleY,this.bipedLeftLeg.rotateAngleZ, 7);
			}
		}
		
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
    private void render_armor_light(Entity entity, String name, ItemArmor_NewObj armor1,int id){
		GL11.glPushMatrix();//glstart
		if(id == 1){
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
		}
        float lastx = OpenGlHelper.lastBrightnessX;
        float lasty = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		if(id == 1)GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		armor1.obj_model.renderPart(name);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		if(id == 1){
			OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		GL11.glPopMatrix();//glend
    }
	
	private void render_objmodel(Entity entity, ItemArmor_NewObj armor,String model,  float offset_x, float offset_y, float offset_z, float rote_x, float rote_y, float rote_z, 
			float ang_x, float ang_y, float ang_z, int id) {
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0, -1.501F, 0);
		
		boolean isSneak = false;
		boolean isMob = false;
		if(entity.isSneaking() || this.isSneak) {
			isSneak = true;
			//System.out.println(String.format("input"));
		}
		if(entity instanceof EntityGVCLivingBase) {
			EntityGVCLivingBase ent = (EntityGVCLivingBase) entity;
			isMob = true;
			//if((ent.sneak || ent.getSneak()) && ent.getHeldItemOffhand().isEmpty())
			if(ent.sneak || ent.getSneak() || entity.isSneaking()  || this.isSneak)
			{
				isSneak = true;
			}
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
		/*if (rote_x == 0.0F && rote_y == 0.0F && rote_z == 0.0F)
        {
			 if (offset_x == 0.0F && offset_y == 0.0F && offset_z == 0.0F)
             {
				 armor.obj_model.renderPart(model);
             }else {
            	 GL11.glTranslatef(offset_x, offset_y, offset_z);//offset
            	 armor.obj_model.renderPart(model);
            	 GL11.glTranslatef(-offset_x, -offset_y, -offset_z);//offset
             }
        }else */
        {
                /*this.bipedHead.showModel = false;
                this.bipedHeadwear.showModel = false;
                this.bipedBody.showModel = false;
                this.bipedRightArm.showModel = false;
                this.bipedLeftArm.showModel = false;
                this.bipedBody.showModel = false;
                this.bipedRightLeg.showModel = false;
                this.bipedLeftLeg.showModel = false;
                this.bipedRightLeg.showModel = false;
                this.bipedLeftLeg.showModel = false;*/
			
        	 GlStateManager.pushMatrix();
        	 if (isSneak)
             {
                 GlStateManager.translate(0.0F, -0.21F, 0.0F);
             }
        	 GL11.glTranslatef(offset_x, offset_y, offset_z);//offset
        	 GL11.glTranslatef(-offset_x + rote_x, -offset_y + rote_y, offset_z - rote_z);//offset
     		GL11.glRotatef(ang_z * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
     		GL11.glRotatef(ang_y * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
     		GL11.glRotatef(ang_x * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
     		GL11.glTranslatef(-offset_x, -offset_y, -offset_z);//offset
     		 armor.obj_model.renderPart(model);
			 render_armor_light(entity,model+"_light",armor,0);
			 render_armor_light(entity,model+"_light2",armor,1);
        	 GlStateManager.popMatrix();
        }
		if(id == 0) {
			this.bipedHeadwear.showModel = false;
			this.bipedHead.showModel = false;
		}
		else if(id == 1) {
			{
				NBTTagCompound nbt = entity.getEntityData();
				boolean para = nbt.getBoolean("Para");
				if(para){
					armor.obj_model.renderPart("parachute");
				}else{
					armor.obj_model.renderPart("parachute1");
				}
			}
			{
				BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
				int genY = bp.getY() + 3;
				if(entity != null && !(entity instanceof EntityPlayer) && !entity.isRiding()){
					if(entity.posY > genY){
						armor.obj_model.renderPart("parachute");
					}else{
						armor.obj_model.renderPart("parachute1");
					}
				}
			}
			this.bipedBody.showModel = false;
		}
		else if(id == 2) {
		}
		else if(id == 3) {
		}
		else if(id == 4) {
			armor.obj_model.renderPart("leg");
		}
		else if(id == 5) {
		}
		else if(id == 6) {
		}
		else if(id == 7) {
		}
		GL11.glPopMatrix();
	}
	
	
	
	//@Override
//	@SuppressWarnings("incomplete-switch")
	/*public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		
		{
			 
		}
		
		/*
		float head_x = this.bipedHead.rotationPointX * 0.0625F;
		float head_y = 1.501F - this.bipedHead.rotationPointY * 0.0625F;
		float head_z = this.bipedHead.rotationPointZ * 0.0625F;
		
		float body_x = this.bipedBody.rotationPointX * 0.0625F;
		float body_y = 1.501F - this.bipedBody.rotationPointY * 0.0625F;
		float body_z = this.bipedBody.rotationPointZ * 0.0625F;
		
		float arm_r_x = this.bipedRightArm.rotationPointX * 0.0625F;
		float arm_r_y = 1.501F - this.bipedRightArm.rotationPointY * 0.0625F;
		float arm_r_z = this.bipedRightArm.rotationPointZ * 0.0625F;
		
		float arm_l_x = this.bipedLeftArm.rotationPointX * 0.0625F;
		float arm_l_y = 1.501F - this.bipedLeftArm.rotationPointY * 0.0625F;
		float arm_l_z = this.bipedLeftArm.rotationPointZ * 0.0625F;
		
		float leg_r_x = this.bipedRightLeg.rotationPointX * 0.0625F;
		float leg_r_y = 1.501F - this.bipedRightLeg.rotationPointY * 0.0625F;
		float leg_r_z = this.bipedRightLeg.rotationPointZ * 0.0625F;
		
		float leg_l_x = this.bipedLeftLeg.rotationPointX * 0.0625F;
		float leg_l_y = 1.501F - this.bipedLeftLeg.rotationPointY * 0.0625F;
		float leg_l_z = this.bipedLeftLeg.rotationPointZ * 0.0625F;
		
		
		
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		if (entityIn instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) entityIn;
			boolean isSneak = false;
			boolean isMob = false;
			if(entity.isSneaking() || this.isSneak) {
				isSneak = true;
				//System.out.println(String.format("input"));
			}
			if(entity instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase ent = (EntityGVCLivingBase) entity;
				isMob = true;
				//if((ent.sneak || ent.getSneak()) && ent.getHeldItemOffhand().isEmpty())
				if(ent.sneak || ent.getSneak() || entity.isSneaking()  || this.isSneak)
				{
					isSneak = true;
				}
			}
			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0, -1.501F, 0);
			
			
			
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(head_x, head_y, head_z);//offset
				if (isSneak) {
		//			GL11.glTranslatef(0F, -0.20F, 0.0F);
				}
				{
					GL11.glRotatef(this.bipedHead.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
					GL11.glRotatef(this.bipedHead.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				}
				GL11.glTranslatef(-head_x, -head_y, -head_z);//offset
				armor.obj_model.renderPart("head");
				this.bipedHeadwear.showModel = false;
				this.bipedHead.showModel = false;
				GL11.glPopMatrix();
			}
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
				{//chest
					GL11.glPushMatrix();
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
					GL11.glTranslatef(body_x, body_y, body_z);//offset
					if(isSneak){
		//				GL11.glTranslatef(0F, -0.20F, 0.0F);
						if(isMob) {
							this.bipedBody.rotateAngleX = 0.5F;
						}
					}
					
					
					{
						GL11.glRotatef(this.bipedBody.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
						GL11.glRotatef(this.bipedBody.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(this.bipedBody.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
					}
					
					GL11.glTranslatef(-body_x,- body_y, -body_z);//offset
					armor.obj_model.renderPart("body");
					{
						NBTTagCompound nbt = entity.getEntityData();
						boolean para = nbt.getBoolean("Para");
						if(para){
							armor.obj_model.renderPart("parachute");
						}else{
							armor.obj_model.renderPart("parachute1");
						}
					}
					{
						BlockPos bp = entity.world.getHeight(new BlockPos(entity.posX, entity.posY, entity.posZ));
						int genY = bp.getY() + 3;
						if(entity != null && !(entity instanceof EntityPlayer) && !entity.isRiding()){
							if(entity.posY > genY){
								armor.obj_model.renderPart("parachute");
							}else{
								armor.obj_model.renderPart("parachute1");
							}
						}
					}
					
					this.bipedBody.showModel = false;
					GL11.glPopMatrix();
				}//chest
				
				float offset_sneak_z = -1.0F;
				if(this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW
						|| this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW) {
					offset_sneak_z = 1.0F;
				}
				
				{//leftarm
					GL11.glPushMatrix();
					
					{
						GL11.glTranslatef(body_x, body_y, body_z);//offset
						GL11.glRotatef(this.bipedBody.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
						GL11.glRotatef(this.bipedBody.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
						GL11.glRotatef(this.bipedBody.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
						GL11.glTranslatef(-body_x, -body_y, -body_z);//offset
					}
					
					
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
					GL11.glTranslatef(arm_l_x, arm_l_y, arm_l_z);//offset
					
					if (this.swingProgress > 0.0F)
			        {
						EnumHandSide enumhandside = this.getMainHand(entity);
			            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
			            float f1 = this.swingProgress;
						this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

			            if (enumhandside == EnumHandSide.LEFT)
			            {
			                this.bipedBody.rotateAngleY *= -1.0F;
			            }
				          //  GL11.glTranslatef(0F, 0F, (-MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F) * 0.0625F);
				          //  GL11.glTranslatef((MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F) * 0.0625F - 0.3125F, 0F, 0F);
			        }
					
					if(isSneak){
						    /*GL11.glRotated(-0.4F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
						    if(this.rightArmPose != ModelBiped.ArmPose.BOW_AND_ARROW) {
						    	GL11.glTranslatef(0F, -0.20F, 0.125F);
						    }else {
						    	GL11.glTranslatef(0F, -0.20F, -0.25F);
						    }
							if(isMob) {
								this.bipedRightArm.rotateAngleX += 0.4F;
							}*
					}
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {

			        }
					if(!entity.getHeldItem(EnumHand.OFF_HAND).isEmpty()) {
			        }
					GL11.glRotatef(this.bipedLeftArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, offset_sneak_z);
					GL11.glRotatef(this.bipedLeftArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
					GL11.glRotatef(this.bipedLeftArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
			        }
					GL11.glTranslatef(-arm_l_x, -arm_l_y, -arm_l_z);//offset
					armor.obj_model.renderPart("leftarm");
					this.bipedBody.showModel = false;
					GL11.glPopMatrix();
				}//leftarm
				
				{//rightarm
					GL11.glPushMatrix();
					
					{
						GL11.glTranslatef(body_x, body_y, body_z);//offset
						GL11.glRotatef(this.bipedBody.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
						GL11.glRotatef(this.bipedBody.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
						GL11.glRotatef(this.bipedBody.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
						GL11.glTranslatef(-body_x, -body_y, -body_z);//offset
					}
					
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
					GL11.glTranslatef(arm_r_x, arm_r_y, arm_r_z);//offset
					
					if (this.swingProgress > 0.0F)
			        {
				        //    GL11.glTranslatef(0F, 0F, (MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F) * 0.0625F);
				         //   GL11.glTranslatef((-MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F) * 0.0625F + 0.3125F, 0F, 0F);
			        }
					
					if(isSneak && !(this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)){
					}
					if(isSneak){
						/*GL11.glRotated(-0.4F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
						if(this.rightArmPose != ModelBiped.ArmPose.BOW_AND_ARROW) {
					    	GL11.glTranslatef(0F, -0.20F, 0.125F);
					    }else {
					    	GL11.glTranslatef(0F, -0.20F, -0.25F);
					    }
						if(isMob) {
							 this.bipedLeftArm.rotateAngleX += 0.4F;
						}*
					}
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
			        }
					if(!entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
			        }
					GL11.glRotatef(this.bipedRightArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, offset_sneak_z);
					GL11.glRotatef(this.bipedRightArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
					GL11.glRotatef(this.bipedRightArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
			        }
					GL11.glTranslatef(-arm_r_x, -arm_r_y, -arm_r_z);//offset
					armor.obj_model.renderPart("rightarm");
					this.bipedBody.showModel = false;
					GL11.glPopMatrix();
				}//rightarm
			}
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
				
				//
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(body_x, body_y, body_z);//offset
				if(isSneak){
					//GL11.glTranslatef(0F, 0.1875F, -0.25F);
				}
				GL11.glTranslatef(-body_x, -body_y, -body_z);//offset
				armor.obj_model.renderPart("leg");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				//
				
				//
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(leg_r_x, leg_r_y, leg_r_z);//offset
				if(isSneak){
					//GL11.glTranslatef(0F, -0.20F, -0.25F);
				}
				GL11.glRotatef(this.bipedRightLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				
				
				GL11.glTranslatef(-leg_r_x, -leg_r_y, -leg_r_z);//offset
				armor.obj_model.renderPart("rightleg");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				//
				
				//
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				
				GL11.glTranslatef(leg_l_x, leg_l_y, leg_l_z);//offset
				if(isSneak){
					//GL11.glTranslatef(0F, -0.20F, -0.25F);
				}
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, -1.0F, 0.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				
				GL11.glTranslatef(-leg_l_x, -leg_l_y, -leg_l_z);//offset
				armor.obj_model.renderPart("leftleg");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				//
			}
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(leg_r_x, leg_r_y, leg_r_z);//offset

				if(isSneak){
					//GL11.glTranslatef(0F, -0.20F, -0.25F);
				}
				GL11.glRotatef(this.bipedRightLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				
				
				GL11.glTranslatef(-leg_r_x, -leg_r_y, -leg_r_z);//offset
				armor.obj_model.renderPart("rightboots");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(leg_l_x, leg_l_y, leg_l_z);//offset
				if(isSneak){
					//GL11.glTranslatef(0F, -0.20F, -0.25F);
				}
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, -1.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				
				
				GL11.glTranslatef(-leg_l_x,- leg_l_y, -leg_l_z);//offset
				armor.obj_model.renderPart("leftboots");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();

		}*/
	//}

}
