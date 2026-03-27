package gvclib.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.item.ItemArmor_NewObj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
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

//2019/11/9以前のもの
public class ModelArmorNewObj_backup extends ModelBiped {
	public ModelArmorNewObj_backup() {
		textureWidth = 512;
		textureHeight = 256;
	}

	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float p_78088_7_) {
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	@Override
//	@SuppressWarnings("incomplete-switch")
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
			float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		{
			this.rightArmPose = null;
		//	this.aimedBow = false;
			
			EntityLivingBase el = (EntityLivingBase) p_78087_7_;
			ItemStack itemstack = el.getHeldItemMainhand();
			if(itemstack != null){
				this.rightArmPose = ModelBiped.ArmPose.ITEM;
				if(itemstack.getItem() instanceof ItemBow && el.isHandActive()){
					this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
			//		this.aimedBow = true;
				}
			}
			
			
			
			this.bipedHead.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
	        this.bipedHead.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
	        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
	        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
	        this.bipedRightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 2.0F * p_78087_2_ * 0.5F;
	        this.bipedLeftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
	        this.bipedRightArm.rotateAngleZ = 0.0F;
	        this.bipedLeftArm.rotateAngleZ = 0.0F;
	        this.bipedRightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
	        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
	        this.bipedRightLeg.rotateAngleY = 0.0F;
	        this.bipedLeftLeg.rotateAngleY = 0.0F;

	        if (this.isRiding)
	        {
	            this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
	            this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
	            this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
	            this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
	            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
	            this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
	        }

	        this.bipedRightArm.rotateAngleY = 0.0F;
	        this.bipedLeftArm.rotateAngleY = 0.0F;
	        float f6;
	        float f7;

	        if (this.swingProgress > 0.0F)
	        {
	            EnumHandSide enumhandside = this.getMainHand(el);
	            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
	            float f1 = this.swingProgress;
	            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

	            if (enumhandside == EnumHandSide.LEFT)
	            {
	                this.bipedBody.rotateAngleY *= -1.0F;
	            }

	            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
	            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
	            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
	            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
	            f1 = 1.0F - this.swingProgress;
	            f1 = f1 * f1;
	            f1 = f1 * f1;
	            f1 = 1.0F - f1;
	            float f2 = MathHelper.sin(f1 * (float)Math.PI);
	            float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
	            modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
	            modelrenderer.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
	            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
	        }

	        if (this.isSneak)
	        {
	            this.bipedBody.rotateAngleX = 0.5F;
	            this.bipedRightArm.rotateAngleX += 0.4F;
	            this.bipedLeftArm.rotateAngleX += 0.4F;
	            this.bipedRightLeg.rotationPointZ = 4.0F;
	            this.bipedLeftLeg.rotationPointZ = 4.0F;
	            this.bipedRightLeg.rotationPointY = 9.0F;
	            this.bipedLeftLeg.rotationPointY = 9.0F;
	            this.bipedHead.rotationPointY = 1.0F;
	            this.bipedHeadwear.rotationPointY = 1.0F;
	        }
	        else
	        {
	            this.bipedBody.rotateAngleX = 0.0F;
	            this.bipedRightLeg.rotationPointZ = 0.1F;
	            this.bipedLeftLeg.rotationPointZ = 0.1F;
	            this.bipedRightLeg.rotationPointY = 12.0F;
	            this.bipedLeftLeg.rotationPointY = 12.0F;
	            this.bipedHead.rotationPointY = 0.0F;
	            this.bipedHeadwear.rotationPointY = 0.0F;
	        }

	        this.bipedRightArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
	        this.bipedLeftArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
	        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
	        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;

	        if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
	        {
	            this.bipedRightArm.rotateAngleY = -0.1F + this.bipedHead.rotateAngleY;
	            this.bipedLeftArm.rotateAngleY = 0.1F + this.bipedHead.rotateAngleY + 0.4F;
	            this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
	            this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
	        }
		}
		
		
		Minecraft minecraft = FMLClientHandler.instance().getClient();
		if (p_78087_7_ instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) p_78087_7_;
			boolean isSneak = false;
			if(entity.isSneaking() || this.isSneak) {
				isSneak = true;
				//System.out.println(String.format("input"));
			}
			if(entity instanceof EntityGVCLivingBase) {
				EntityGVCLivingBase ent = (EntityGVCLivingBase) entity;
				if(ent.sneak && ent.getHeldItemOffhand().isEmpty()) {
					isSneak = true;
				}
			}
			GL11.glPushMatrix();
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0, -1.5F, 0);
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(0F, 1.5F, 0.0F);
				if (isSneak) {
					GL11.glTranslatef(0F, -0.20F, 0.0F);
				}
				{
					GL11.glRotated(180.0F - entity.rotationYawHead - (180.0F - entity.renderYawOffset), 0.0F, 1.0F,
							0.0F);
					GL11.glRotated(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
				}
				GL11.glTranslatef(0F, -1.5F, 0.0F);
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
					GL11.glTranslatef(0F, 1.5F, 0.0F);
					if(isSneak){
					//	GL11.glTranslatef(0F, -0.05F, 0.0F);
						GL11.glRotated(30, 1.0F, 0.0F, 0.0F);
						GL11.glTranslatef(0F, -0.20F, 0.0F);
						//GL11.glRotatef(this.body_rotex * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					}
					GL11.glTranslatef(0F, -1.5F, 0.0F);
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
						if(entity != null && !(entity instanceof EntityPlayer)){
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
				
				{//leftarm
					GL11.glPushMatrix();
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
					//GL11.glTranslatef(0F, 1.5F, 0.0F);
					GL11.glTranslatef(0.375F, 1.375F, 0.0F);//offset_start
					if(isSneak && !(this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)){
					//	GL11.glTranslatef(0F, -0.05F, 0.0F);
						GL11.glRotated(22.5F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(this.leftarm_rotex * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					}
					if(isSneak){
						//this.bipedLeftArm.rotateAngleX += 0.4F;
						    GL11.glRotated(-22.5F, 1.0F, 0.0F, 0.0F);
							GL11.glTranslatef(0F, -0.20F, 0.0F);
					}
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
						GL11.glRotated(180.0F - entity.rotationYawHead - (180.0F - entity.renderYawOffset), 0.0F, 1.0F,0.0F);
						
			        }
					if(!entity.getHeldItem(EnumHand.OFF_HAND).isEmpty()) {
			        	this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
			        }
					GL11.glRotatef(this.bipedLeftArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(this.bipedLeftArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(this.bipedLeftArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
						GL11.glRotatef(-30, 0.0F, 0.0F, 1.0F);
			        }
					GL11.glTranslatef(-0.375F, -1.375F, 0.0F);//offset_end
					//GL11.glTranslatef(0F, -1.5F, 0.0F);
					armor.obj_model.renderPart("leftarm");
					this.bipedBody.showModel = false;
					GL11.glPopMatrix();
				}//leftarm
				
				{//rightarm
					GL11.glPushMatrix();
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
					//GL11.glTranslatef(0F, 1.5F, 0.0F);
					GL11.glTranslatef(-0.375F, 1.375F, 0.0F);//offset_start
					
			//		GL11.glTranslatef(-this.rightarm_posx, -rightarm_posy, -rightarm_posz);
					
					if(isSneak && !(this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)){
					//	GL11.glTranslatef(0F, -0.05F, 0.0F);
						GL11.glRotated(22.5F, 1.0F, 0.0F, 0.0F);
						//GL11.glRotatef(this.rightarm_rotex * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					}
					if(isSneak){
						//this.bipedRightArm.rotateAngleX += 0.4F;
						 GL11.glRotated(-22.5F, 1.0F, 0.0F, 0.0F);
							GL11.glTranslatef(0F, -0.20F, 0.0F);
					}
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
						GL11.glRotated(180.0F - entity.rotationYawHead - (180.0F - entity.renderYawOffset), 0.0F, 1.0F,0.0F);
			        }
					if(!entity.getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
			        	this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
			        }
					GL11.glRotatef(this.bipedRightArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(this.bipedRightArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(this.bipedRightArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
					if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
			        {
						GL11.glRotatef(5, 0.0F, 0.0F, 1.0F);
			        }
					GL11.glTranslatef(0.375F, -1.375F, 0.0F);//offset_end
					//GL11.glTranslatef(0F, -1.5F, 0.0F);
					armor.obj_model.renderPart("rightarm");
					this.bipedBody.showModel = false;
					GL11.glPopMatrix();
				}//rightarm
			}
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(0F, 1.5F, 0.0F);
				if(isSneak){
					GL11.glTranslatef(0F, 0.1875F, -0.25F);
				}
				GL11.glTranslatef(0F, -1.5F, 0.0F);
				armor.obj_model.renderPart("leg");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(-0.125F, 0.75F, 0.0F);
				if(isSneak){
					GL11.glTranslatef(0F, 0.1875F, -0.25F);
				}
				GL11.glRotatef(this.bipedRightLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.125F, -0.75F, 0.0F);
				armor.obj_model.renderPart("rightleg");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(0.125F, 0.75F, 0.0F);
				if(isSneak){
					GL11.glTranslatef(0F, 0.1875F, -0.25F);
				}
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.125F, -0.75F, 0.0F);
				armor.obj_model.renderPart("leftleg");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
			}
			if ((entity.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null)
					&& (entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemArmor_NewObj)) {
				ItemArmor_NewObj armor = (ItemArmor_NewObj) entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(-0.125F, 0.75F, 0.0F);
				if(isSneak){
					GL11.glTranslatef(0F, 0.1875F, -0.25F);
				}
				GL11.glRotatef(this.bipedRightLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedRightLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.125F, -0.75F, 0.0F);
				armor.obj_model.renderPart("rightboots");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(armor.obj_tex));
				GL11.glTranslatef(0.125F, 0.75F, 0.0F);
				if(isSneak){
					GL11.glTranslatef(0F, 0.1875F, -0.25F);
				}
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(this.bipedLeftLeg.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.125F, -0.75F, 0.0F);
				armor.obj_model.renderPart("leftboots");
				this.bipedBody.showModel = false;
				GL11.glPopMatrix();
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();

		}
	}

}
