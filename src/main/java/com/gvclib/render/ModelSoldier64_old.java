package gvclib.render;

import gvclib.entity.living.EntityGVCLivingBase;
import gvclib.item.ItemGun_Shield;
import gvclib.item.gunbase.IGun_Sword;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSoldier64_old extends ModelBiped
{
    public ModelSoldier64_old()
    {
        this(0.0F, false);
    }

    public ModelSoldier64_old(float modelSize, boolean p_i1168_2_)
    {
    	super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
       
        GlStateManager.pushMatrix();

        if (this.isChild)
        {
            float f = 2.0F;
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
            this.bipedHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        }
        else
        {
        	 if (entityIn instanceof EntityGVCLivingBase && entityIn != null) {
             	EntityGVCLivingBase en = (EntityGVCLivingBase) entityIn;
             	if (entityIn.isSneaking() || en.getSneak())
                {
                    GlStateManager.translate(0.0F, 0.2F, 0.0F);
                }
        	 }
            

            this.bipedHead.render(scale);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        }

        GlStateManager.popMatrix();
        
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    }
    
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime)
    {
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);
        if (entitylivingbaseIn instanceof EntityGVCLivingBase && entitylivingbaseIn != null) {
        	EntityGVCLivingBase en = (EntityGVCLivingBase) entitylivingbaseIn;
        	if(!en.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty()
        			&& en.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof ItemBow){
        		 this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
        	}
        }
       
        
        super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        float var8 = MathHelper.sin(this.swingProgress * (float)Math.PI);
        float var9 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        boolean guntask = false;
        boolean guntaskl = false;
        boolean isCombat = false;
        if (entityIn instanceof EntityGVCLivingBase && entityIn != null) {
        	EntityGVCLivingBase en = (EntityGVCLivingBase) entityIn;
        	if(!en.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty()
        			&& en.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof ItemBow){
        		guntask = true;
        	}
        	if(!en.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()
        			&& en.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() instanceof ItemBow){
        		//if(!(en.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() instanceof ItemGun_Shield))
        			guntaskl = true;
        	}
        	if(en.getattacktask()) {
        		isCombat = true;
        	}
        }
        if(guntask) {
        	if(isCombat) {
        		this.bipedRightArm.rotateAngleZ = 0.0F;
                this.bipedRightArm.rotateAngleY = -(0.1F - var8 * 0.6F) + this.bipedHead.rotateAngleY;
                this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
                this.bipedRightArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
                this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        	}else {
        		this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.bipedRightArm.rotateAngleY = 0.0F;
        	}
        	
        }
        
        
        
        
        if (entityIn instanceof EntityGVCLivingBase && entityIn != null) {
        	EntityGVCLivingBase en = (EntityGVCLivingBase) entityIn;
        	float offsetx = 0;
        	//ItemStack main = en.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
    		ItemStack off = en.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
    		/*if(!main.isEmpty() && main.getItem() instanceof ItemGun_Shield) {
    			if(en.getSneak()) {
    				offsetx = 0.9F;
    			}else {
    				offsetx = 0.3F;
    			}
        		
        	}*/
    		if(!off.isEmpty() && off.getItem() instanceof ItemGun_Shield) {
    			if(en.getSneak()) {
    				offsetx = 0.0F;
    			}else {
    				offsetx = 0.45F;
    			}
        	}
        	if(guntask) {
        		if(!en.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()){
            		this.bipedLeftArm.rotateAngleZ = 0.0F;
                	this.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F + this.bipedHead.rotateAngleY;
                	this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX + offsetx;
                	this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
                	this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                	this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
                }else{
                	this.bipedLeftArm.rotateAngleZ = 0.0F;
                	this.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
                	this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX + offsetx;
                	this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
                	this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
                	this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
                }
        	}else if(guntaskl){
        		this.bipedLeftArm.rotateAngleZ = 0.0F;
            	this.bipedLeftArm.rotateAngleY = 0.1F - var8 * 0.6F + this.bipedHead.rotateAngleY;
            	this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX + offsetx;
            	this.bipedLeftArm.rotateAngleX -= var8 * 1.2F - var9 * 0.4F;
            	this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            	this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        	}
        	
        	//if ((en.sneak) && guntask)
        	if (en.getSneak())
            {
        		//System.out.println(String.format("1"));
        		 this.bipedBody.rotateAngleX = 0.5F;
               //  this.bipedRightArm.rotateAngleX += 0.4F;
               //  this.bipedLeftArm.rotateAngleX += 0.4F;
                 this.bipedRightLeg.rotationPointZ = 4.0F;
                 this.bipedLeftLeg.rotationPointZ = 4.0F;
                 this.bipedRightLeg.rotationPointY = 9.0F;
                 this.bipedLeftLeg.rotationPointY = 9.0F;
                 this.bipedHead.rotationPointY = 1.0F;
                 
            }
            else
            {
            	this.bipedBody.rotateAngleX = 0.0F;
                this.bipedRightLeg.rotationPointZ = 0.1F;
                this.bipedLeftLeg.rotationPointZ = 0.1F;
                this.bipedRightLeg.rotationPointY = 12.0F;
                this.bipedLeftLeg.rotationPointY = 12.0F;
                this.bipedHead.rotationPointY = 0.0F;
            }
        }/**/
        	
    }
    
    public void postRenderArm(float scale, EnumHandSide side)
    {
        float f = side == EnumHandSide.RIGHT ? 1.0F : -1.0F;
        ModelRenderer modelrenderer = this.getArmForSide(side);
        modelrenderer.rotationPointX += f;
        modelrenderer.postRender(scale);
        modelrenderer.rotationPointX -= f;
    }
}