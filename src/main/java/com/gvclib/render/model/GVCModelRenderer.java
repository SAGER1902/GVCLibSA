package gvclib.render.model;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.IModelCustom;

public class GVCModelRenderer extends ModelRenderer {
    public IModelCustom obj;
    public ResourceLocation tex;
    public IModelCustom objD;
    public ResourceLocation texD;
    public float offset_x = 0;
    public float offset_y = 0;
    public float offset_z = 0;
    public float offset_x_ = 0;
    public float offset_y_ = 0;
    public float offset_z_ = 0;
    public static float tx = 0;
    public static float ty = 0;
    public static float tz = 0;
    public static float tx_ = 0;
    public static float ty_ = 0;
    public static float tz_ = 0;
    public static float tax = 0;
    public static float tay = 0;
    public static float taz = 0;
    public String part;
    public ResourceLocation skin;
    public int texID;
    public ArrayList<IModelCustom> secondObjs = new ArrayList<IModelCustom>();
    public ArrayList<ResourceLocation> secondTexs = new ArrayList<ResourceLocation>();
    public ArrayList<IModelCustom> secondObjsD = new ArrayList<IModelCustom>();
    public ArrayList<ResourceLocation> secondTexsD = new ArrayList<ResourceLocation>();
    public static final ArrayList STATIC_ARRAYLIST = new ArrayList<>();
    public boolean renderGVC = false;

    public GVCModelRenderer(ModelBase model, int texOffX, int texOffY) {
        super(model, texOffX, texOffY);
        // TODO 自?生成的?造函数存根
    }

    @SideOnly(Side.CLIENT)
    public void render(float scale) {
        if (!renderGVC) {
            super.render(scale);
            return;
        } else {
            renderGVC = false;
        }
        if (!this.showModel) {
            return;
        }
        if ((obj == null || tex == null) && (objD == null && texD == null)
                && (secondObjs == null || secondObjsD == null) || part == null) {
            return;
        }
        if (secondObjs == null) {
            secondObjs = STATIC_ARRAYLIST;
        }
        if (secondTexs == null) {
            secondTexs = STATIC_ARRAYLIST;
        }
        if (secondObjsD == null) {
            secondObjsD = STATIC_ARRAYLIST;
        }
        if (secondTexsD == null) {
            secondTexsD = STATIC_ARRAYLIST;
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
        if (skin == null) {
            texID = GL11.glGetInteger(GL11.GL_TEXTURE_2D);
        }
        if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
            if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                //torender
                GlStateManager.translate(this.offset_x, this.offset_y, this.offset_z);
                GlStateManager.translate(tx, ty, tz);
                GlStateManager.translate(this.offset_x_, this.offset_y_, this.offset_z_);

                GL11.glRotatef(180F, -1.0F, 0.0F, 0.0F);
                if (obj != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
                    obj.renderPart(part);
                }
                if (objD != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(texD);
                    if (part.equals("rightleg")) {
                        objD.renderPart("rightboots");
                    }
                    if (part.equals("leftleg")) {
                        objD.renderPart("leftboots");
                    }
                }
                for (int i = 0; i < secondObjs.size(); i++) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexs.get(i));
                    secondObjs.get(i).renderPart(part);
                }
                if (part.equals("rightleg")) {
                    for (int i = 0; i < secondObjsD.size(); i++) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexsD.get(i));
                        secondObjsD.get(i).renderPart("rightboots");
                    }
                }
                if (part.equals("leftleg")) {
                    for (int i = 0; i < secondObjsD.size(); i++) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexsD.get(i));
                        secondObjsD.get(i).renderPart("leftboots");
                    }
                }

            } else {
                GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale,
                        this.rotationPointZ * scale);
                //torender
                GlStateManager.translate(this.offset_x, this.offset_y, this.offset_z);
                GlStateManager.translate(tx, ty, tz);
                GlStateManager.translate(this.offset_x_, this.offset_y_, this.offset_z_);

                GL11.glRotatef(180F, -1.0F, 0.0F, 0.0F);
                if (obj != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
                    obj.renderPart(part);
                }
                if (objD != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(texD);
                    if (part.equals("rightleg")) {
                        objD.renderPart("rightboots");
                    }
                    if (part.equals("leftleg")) {
                        objD.renderPart("leftboots");
                    }
                }
                for (int i = 0; i < secondObjs.size(); i++) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexs.get(i));
                    secondObjs.get(i).renderPart(part);
                }
                if (part.equals("rightleg")) {
                    for (int i = 0; i < secondObjsD.size(); i++) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexsD.get(i));
                        secondObjsD.get(i).renderPart("rightboots");
                    }
                }
                if (part.equals("leftleg")) {
                    for (int i = 0; i < secondObjsD.size(); i++) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexsD.get(i));
                        secondObjsD.get(i).renderPart("leftboots");
                    }
                }

                GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale,
                        -this.rotationPointZ * scale);
            }
        } else {
            GlStateManager.pushMatrix();

            //do
            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale,
                    this.rotationPointZ * scale);

            GlStateManager.translate(tx, ty, tz);
            GlStateManager.translate(this.offset_x, this.offset_y, this.offset_z);

            if (this.rotateAngleZ != 0.0F) {
                GlStateManager.rotate(this.rotateAngleZ * (180F / (float) Math.PI) + tax, 0.0F, 0.0F, 1.0F);
            }

            if (this.rotateAngleY != 0.0F) {
                GlStateManager.rotate(this.rotateAngleY * (180F / (float) Math.PI) + tay, 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
                GlStateManager.rotate(this.rotateAngleX * (180F / (float) Math.PI) + taz, 1.0F, 0.0F, 0.0F);
            }
            //torender
            GlStateManager.translate(this.offset_x_, this.offset_y_, this.offset_z_);
            GlStateManager.translate(tx_, ty_, tz_);

            GL11.glRotatef(180F, -1.0F, 0.0F, 0.0F);

            if (obj != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
                obj.renderPart(part);
            }
            if (objD != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(texD);
                if (part.equals("rightleg")) {
                    objD.renderPart("rightboots");
                }
                if (part.equals("leftleg")) {
                    objD.renderPart("leftboots");
                }
            }
            for (int i = 0; i < secondObjs.size(); i++) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexs.get(i));
                secondObjs.get(i).renderPart(part);
            }
            if (part.equals("rightleg")) {
                for (int i = 0; i < secondObjsD.size(); i++) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexsD.get(i));
                    secondObjsD.get(i).renderPart("rightboots");
                }
            }
            if (part.equals("leftleg")) {
                for (int i = 0; i < secondObjsD.size(); i++) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexsD.get(i));
                    secondObjsD.get(i).renderPart("leftboots");
                }
            }

            GlStateManager.popMatrix();
        }

        GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
        GlStateManager.popMatrix();
        if (skin != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        } else {
            GlStateManager.bindTexture(texID);
        }
    }
}
