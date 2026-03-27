package gvclib.item;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.collect.Multimap;
import java.util.Random;

import gvclib.RecoilMotionReader;
import gvclib.ReloadMotionReader;
import gvclib.mod_GVCLib;
import gvclib.entity.EntityBBase;
import gvclib.entity.EntityB_Bullet;
import gvclib.entity.EntityB_BulletAA;
import gvclib.entity.EntityB_BulletFire;
import gvclib.entity.EntityB_Cratridge;
import gvclib.entity.EntityB_GrenadeB;
import gvclib.entity.EntityB_Missile;
import gvclib.entity.EntityB_Shell;
import gvclib.entity.EntityT_Flash;
import gvclib.entity.EntityT_Light;
import gvclib.entity.EntityT_Grenade;
import gvclib.gui.GVCInventoryItem;
import gvclib.item.gunbase.GunBase_Attachment;
import gvclib.item.gunbase.GunBase_Attachment_new;
import gvclib.item.gunbase.GunBase_LockOn;
import gvclib.item.gunbase.IGun_Grenade;
import gvclib.item.gunbase.IGun_Shield;
import gvclib.item.gunbase.IGun_Sword;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import net.minecraft.entity.EntityLiving;
import gvclib.event.gun.GVCGunEvents;
import gvclib.packets.PacketShootGunGVC;
	public class ItemGunBase extends Item{

		public String modid = mod_GVCLib.MOD_ID;
		public static final String[] bowPullIconNameArray = new String[] {"", "", ""};
	    //private final Item.ToolMaterial field_150933_b;
	    private static final String __OBFID = "CL_00000072";
	    public static final String Tag_Cycle		= "Cycle";
	    public static final String Tag_CycleCount	= "CycleCount";
	    public float attackDamage = 2;
	    public double attackSpeed =  -2.4000000953674316D;
	    public float f;
	    public int j;
	    
	    public int isreload;
	    public int retime;
	    public int reloadtime;
	    
	    public static int maxdamege;
	    
	    public static boolean canreload;
	    
	    public int firetime;
	    public int firegrenadetime;
	    public int retimegrenade;

		public boolean test_damage = mod_GVCLib.gvclibsa_damage_test;
		
		public int powor;
	    public float speed;
	    public float bure;
	    public double recoil;
	    public float scopezoom;
	    public int cycle = 1;
	    public float soundsp = 1;
	    
	    public float bureads;
	    public double recoilads;
	    
	    public static int bulletcount;
	    
	    public String ads;
	    public String adsr;
	    public String adss;
	    
	    public Item magazine;
	    public SoundEvent fires = null;
	    public SoundEvent firesbase= null;
	    //0308
	    public SoundEvent fires_su= null;
	    public SoundEvent fires_reload= null;
	    public SoundEvent fires_cock= null;
	    public SoundEvent fires_un_gl = null;
	    public SoundEvent fires_un_sg = null;
	    
	    
	    public boolean ex = false;
	    public boolean exfire = false;
	    public boolean exsmoke =false;
	    public boolean exflash =false;
	    public boolean exgas =false;
	    public float exlevel = 0.0F;
	    
	    public boolean milock = true;
	    
	    public int aaa;
	    public static boolean grenadekey;
	    
	    public IModelCustom entity_model = null;
	    public ResourceLocation entity_tex = null;
	    
	    
	    public IModelCustom obj_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/ar.obj"));
	    public String obj_model_name = null;
	    public String obj_tex = "gvclib:textures/model/ar.png";
	    public boolean obj_true = false;
	    public String reload_motion_name = null;
	    public String recoil_motion_name = null;
	    
	    public IModelCustom arm_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/arm.obj"));
	    public IModelCustom arm_model2 = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/arm2.obj"));
	    public IModelCustom arm_model_alex = null;
	    //public boolean can_lmm_hand = false;
	    public IModelCustom arm_model_lmm = null;
		
	    public float model_x = 1.5F;
	    public float model_y = -2F;
	    public float model_z = -3.5F;
	    public float model_x_ads = 0F;
	    public float model_y_ads = -1.7F;
	    public float model_z_ads = -1.5F;
	    public boolean model_hand = false;
	    //public boolean model_heavy = false;
	    /**mat1,20*/
	    public boolean model_zoomrender = true;
	    /**mat4*/
	    public boolean model_zoomrenderr = true;
	    /**mat5*/
	    public boolean model_zoomrenders = true;
	    
	    //0915
	  //rotation
		public float rotationx = 0F;
		public float rotationx0;
		public float rotationx1;
		public float rotationx2;
		
		public float rotationy = 180F;
		public float rotationy0;
		public float rotationy1;
		public float rotationy2;
		
		public float rotationz = 0F;
		public float rotationz0;
		public float rotationz1;
		public float rotationz2;
	    
	    public float arm_r_posx = 0F;
	    public float arm_r_posy = 0F;
	    public float arm_r_posz = 0F;
	    
	    public float arm_l_posx = 0F;
	    public float arm_l_posy = 0F;
	    public float arm_l_posz = 0F;
	    public float arm_mag_l_posx = 0F;
	    public float arm_mag_l_posy = 0F;
	    public float arm_mag_l_posz = -1.5F;
	    
	    //8/18
	    public boolean knife = false;
	    
	    
	    public IModelCustom flash_model = null;//AdvancedModelLoader.loadModel(new ResourceLocation("gvclib:textures/model/flash.obj"));
	    public String flash_tex = "gvclib:textures/model/flash.png";
	    public float flash_posx = 0F;
	    public float flash_posy = 1F;
	    public float flash_posz = 6F;
	    
	    public boolean flash = false;
	    
		public int cooltime = 1;

	    public boolean newreload = false;
	    public int reloadmotion = 0;
	    public int mat_rk_0 = 1;
	    public String[] mat_r_0 = new String[]{"mat1", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"-20", "0", "20", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0","0"
	    		};
	    public int mat_rk_2 = 1;
	    public String[] mat_r_2 = new String[]{"mat2", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    public int mat_rk_3 = 1;
	    public String[] mat_r_3 = new String[]{"mat3", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    
	    public int mat_rk_22 = 1;
	    public String[] mat_r_22 = new String[]{"mat22", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    public int mat_rk_24 = 1;
	    public String[] mat_r_24 = new String[]{
	    		"mat25", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    public int mat_rk_25 = 1;
	    public String[] mat_r_25 = new String[]{
	    		"mat25", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    
	    public int mat_rk_31 = 1;
	    public String[] mat_r_31 = new String[]{
	    		"mat25", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    public int mat_rk_32 = 1;
	    public String[] mat_r_32 = new String[]{
	    		"mat25", "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    
	    public int mat_rk_leftarm = 1;
	    public String[] mat_r_leftarm = new String[]{"leftarm", "0", "200", 
	    		"0", "0", "0", //pos
	    		"0", "0", "0", //off
	    		"0", "0", "0", //roteoff
	    		"0", "0", "0", //move
	    		"0", "0", "0", "0F","0"
	    		};
	    
	    public int mat_rk_rightarm = 1;
	    public String[] mat_r_rightarm = new String[]{"rightarm",  "0", "200", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", 
	    		"0", "0", "0", "0F","0"
	    		};
	    
	    public boolean reloadmotion_test  =false;
	    
	    public int remat0 = 4;
	    //public int[] remat0_time = new int[] {0, 30, 35, 50};
	    public int[] remat0_time = new int[200];
	    public float[] remat0_xpoint = new float[200];
	    public float[] remat0_ypoint = new float[200];
	    public float[] remat0_zpoint = new float[200];
	    
	    public float[] remat0_xrote = new float[200];
	    public float[] remat0_yrote = new float[200];
	    public float[] remat0_zrote = new float[200];
	    
	    public float[] remat0_xmove = new float[200];
	    public float[] remat0_ymove = new float[200];
	    public float[] remat0_zmove = new float[200];
	    
	    public int remat1 = 4;
	    public int[] remat1_time = new int[200];
	    public float[] remat1_xpoint = new float[200];
	    public float[] remat1_ypoint = new float[200];
	    public float[] remat1_zpoint = new float[200];
	    
	    public float[] remat1_xrote = new float[200];
	    public float[] remat1_yrote = new float[200];
	    public float[] remat1_zrote = new float[200];
	    
	    public float[] remat1_xmove = new float[200];
	    public float[] remat1_ymove = new float[200];
	    public float[] remat1_zmove = new float[200];
	    
	    public int remat2 = 4;
	    public int[] remat2_time = new int[200];
	    public float[] remat2_xpoint = new float[200];
	    public float[] remat2_ypoint = new float[200];
	    public float[] remat2_zpoint = new float[200];
	    
	    public float[] remat2_xrote = new float[200];
	    public float[] remat2_yrote = new float[200];
	    public float[] remat2_zrote = new float[200];
	    
	    public float[] remat2_xmove = new float[200];
	    public float[] remat2_ymove = new float[200];
	    public float[] remat2_zmove = new float[200];
	    
	    public int remat3 = 4;
	    public int[] remat3_time = new int[200];
	    public float[] remat3_xpoint = new float[200];
	    public float[] remat3_ypoint = new float[200];
	    public float[] remat3_zpoint = new float[200];
	    
	    public float[] remat3_xrote = new float[200];
	    public float[] remat3_yrote = new float[200];
	    public float[] remat3_zrote = new float[200];
	    
	    public float[] remat3_xmove = new float[200];
	    public float[] remat3_ymove = new float[200];
	    public float[] remat3_zmove = new float[200];
	    
	    
	    public int remat22 = 4;
	    public int[] remat22_time = new int[200];
	    public float[] remat22_xpoint = new float[200];
	    public float[] remat22_ypoint = new float[200];
	    public float[] remat22_zpoint = new float[200];
	    
	    public float[] remat22_xrote = new float[200];
	    public float[] remat22_yrote = new float[200];
	    public float[] remat22_zrote = new float[200];
	    
	    public float[] remat22_xmove = new float[200];
	    public float[] remat22_ymove = new float[200];
	    public float[] remat22_zmove = new float[200];
	    
	    public int remat24 = 4;
	    public int[] remat24_time = new int[200];
	    public float[] remat24_xpoint = new float[200];
	    public float[] remat24_ypoint = new float[200];
	    public float[] remat24_zpoint = new float[200];
	    
	    public float[] remat24_xrote = new float[200];
	    public float[] remat24_yrote = new float[200];
	    public float[] remat24_zrote = new float[200];
	    
	    public float[] remat24_xmove = new float[200];
	    public float[] remat24_ymove = new float[200];
	    public float[] remat24_zmove = new float[200];
	    
	    public int remat25 = 4;
	    public int[] remat25_time = new int[200];
	    public float[] remat25_xpoint = new float[200];
	    public float[] remat25_ypoint = new float[200];
	    public float[] remat25_zpoint = new float[200];
	    
	    public float[] remat25_xrote = new float[200];
	    public float[] remat25_yrote = new float[200];
	    public float[] remat25_zrote = new float[200];
	    
	    public float[] remat25_xmove = new float[200];
	    public float[] remat25_ymove = new float[200];
	    public float[] remat25_zmove = new float[200];
	    
	    public int remat311 = 4;
	    public int[] remat31_time = new int[200];
	    public float[] remat31_xpoint = new float[200];
	    public float[] remat31_ypoint = new float[200];
	    public float[] remat31_zpoint = new float[200];
	    
	    public float[] remat31_xrote = new float[200];
	    public float[] remat31_yrote = new float[200];
	    public float[] remat31_zrote = new float[200];
	    
	    public float[] remat31_xmove = new float[200];
	    public float[] remat31_ymove = new float[200];
	    public float[] remat31_zmove = new float[200];
	    
	    public int remat32 = 4;
	    public int[] remat32_time = new int[200];
	    public float[] remat32_xpoint = new float[200];
	    public float[] remat32_ypoint = new float[200];
	    public float[] remat32_zpoint = new float[200];
	    
	    public float[] remat32_xrote = new float[200];
	    public float[] remat32_yrote = new float[200];
	    public float[] remat32_zrote = new float[200];
	    
	    public float[] remat32_xmove = new float[200];
	    public float[] remat32_ymove = new float[200];
	    public float[] remat32_zmove = new float[200];
	    
	    public int rematlefthand = 5;
	    public int[] rematlefthand_time = new int[200];
	    public float[] rematlefthand_xpoint = new float[200];
	    public float[] rematlefthand_ypoint = new float[200];
	    public float[] rematlefthand_zpoint = new float[200];
	    
	    public float[] rematlefthand_xrote = new float[200];
	    public float[] rematlefthand_yrote = new float[200];
	    public float[] rematlefthand_zrote = new float[200];
	    
	    public float[] rematlefthand_xmove = new float[200];
	    public float[] rematlefthand_ymove = new float[200];
	    public float[] rematlefthand_zmove = new float[200];
	    
	    public int rematrighthand = 4;
	    public int[] rematrighthand_time = new int[200];
	    public float[] rematrighthand_xpoint = new float[200];
	    public float[] rematrighthand_ypoint = new float[200];
	    public float[] rematrighthand_zpoint = new float[200];
	    
	    public float[] rematrighthand_xrote = new float[200];
	    public float[] rematrighthand_yrote = new float[200];
	    public float[] rematrighthand_zrote = new float[200];
	    
	    public float[] rematrighthand_xmove = new float[200];
	    public float[] rematrighthand_ymove = new float[200];
	    public float[] rematrighthand_zmove = new float[200];
	    
	    public boolean canex;
	    
	    public boolean canobj;
	    public int firetype;
	    public boolean rendercross = false;
	    
	    public boolean semi = true;
	    public String sound = "gvclib.fire_rifle";
	    public String soundre = "gvclib.reload_mag";
	    public String soundco= "gvclib.reload_cocking";
	    public String soundbase= "gvclib.fire_rifle";
	    public String soundsu= "gvclib.fire_supu";
	    public Item iitem;

	    public int isrecoil;
	    public int recotime;
	    
	    public boolean isisreload = false;
	    
	    public ResourceLocation texture;
	    public static int righttype;
	    public int right;
	    public int resc;
	    public boolean recoilre;
	    
	    public  String adstexture = "gvclib:textures/misc/scope.png";

	    public int zox;
	    public int zoy;
	    public int zoz;
	    
	    
	    public float scopezoombase;
	    public float scopezoomred;
	    public float scopezoomscope;
	   
	    public Item magazinesg = Items.APPLE;
	    public Item magazinegl = Items.APPLE;
	    public  String adstexturer = "gvclib:textures/misc/scope.png";
	    public  String adstextures = "gvclib:textures/misc/scope.png";
	    
	    public  boolean true_mat4 = false;
	    public  boolean true_mat5 = false;
	    public  boolean true_mat6 = false;
	    public  boolean true_mat7 = false;
	    public  boolean true_mat8 = false;
	    public  boolean true_mat9 = false;
	    public  boolean true_mat10 = false;
	    public  boolean true_mat11 = false;
	    public  boolean true_mat12 = false;
	    public  boolean true_mat100 = false;
	    
	    public int bullettype = 0;
	    public boolean zoomre = false;
	    
	    public boolean zoomren = false;
	    public boolean zoomrer = false;
	    public boolean zoomres = false;
	    
	    public boolean zoomrent = false;
	    public boolean zoomrert = false;
	    public boolean zoomrest = false;
	    
	    public double motion = 1D;
	    
	    public boolean muzzle = true;
	    
	    public boolean muzzlef;
	    
	    public boolean bullet_c = false;
	    
	    public int ff;
	    
	    public boolean mat22 = false;
	    public float mat22rotationx = 90F;
		public float mat22rotationy = 0F;
		public float mat22rotationz = 0F;
		public float mat22offsetx = 0F;
		public float mat22offsety = 1.5F;
		public float mat22offsetz = 2F;
	    
	    public float mat25rotationx = 0F;
		public float mat25rotationy = 0F;
		public float mat25rotationz = -90F;
		public float mat25offsetx = 0F;
		public float mat25offsety = 0.75F;
		public float mat25offsetz = 1.1F;
		public float soundspeed = 1.0F;
	    public float soundrespeed = 1.0F;
	    public String soundcock = "hmgww2:hmgww2.ing";
	    public int cocktime = 0;
		
	    public float Sprintrotationx = 20F;
		public float Sprintrotationy = 60F;
		public float Sprintrotationz = 0F;
		public float Sprintoffsetx = 0.5F;
		public float Sprintoffsety = 0.0F;
		public float Sprintoffsetz = 0.5F;
	    
		public int shotgun_pellet = 1;//9
		//01/27
		public float gra = 0.029F;
		public float vecy = 0;
		
		public float jump = -0.2F;
		//01/27
		public boolean all_jump = false;
		public boolean cock_left = false;
		public boolean mat25 = false;
		public boolean mat2 = false;
		
		//02/07
		public float mat31posx = 0F;
		public float mat31posy = 0.55F;
		public float mat31posz = 0F;
		public float mat31rotex = 0F;
		public float mat31rotey = 0F;
		public float mat31rotez = 60F;
	//	public float nox;
	//	public float noy;
	//	public float noz;
		public float rote;
		public float rotey;
		public float rotex;
		
		public float mat32posx;
		public float mat32posy;
		public float mat32posz;
		public float mat32rotex;
		public float mat32rotey;
		public float mat32rotez;
		
		//02/14
			public int gun_type = 0;
			public boolean noads = false;
			public int c_type;
			public boolean canmagazine = true;
			public boolean remat31 = true;
	    
	    
	    //0307
			public float model_x_adsr = 0F;
		    public float model_y_adsr = -1.7F;
		    public float model_z_adsr = -1.5F;
		    
		    public float model_x_adss = 0F;
		    public float model_y_adss = -1.7F;
		    public float model_z_adss = -1.5F;
	    
		  //0307
			public String soundunder_gl= "handmadeguns:handmadeguns.cooking";
			public String soundunder_sg= "handmadeguns:handmadeguns.cooking";
			public int under_gl_power = 20;
			public float under_gl_speed = 2;
			public float under_gl_bure = 5;
			public double under_gl_recoil = 5;
			public float under_gl_gra = 0.01F;
			public int under_sg_power = 4;
			public float under_sg_speed = 3;
			public float under_sg_bure = 20;
			public double under_sg_recoil = 5;
			public float under_sg_gra = 0.029F;
	    
		//0916
			public float model_x_adsro = 0F;
		    public float model_y_adsro = -1.7F;
		    public float model_z_adsro = -1.5F;
		    
		//0930
		    public float model_cock_x = 0F;
		    public float model_cock_y = 0F;
		    public float model_cock_z = -1.5F;
			
		    public float mat2_cock_z = -0.5F;
			
		    public float modek_third_offset_rote_x = 70F;
		    
		//12/09
		    public String bullet_model = "gvclib:textures/entity/BulletNormal.obj";
		    public String bullet_tex = "gvclib:textures/entity/BulletNormal.png";
		    public String cartridge_model = "gvclib:textures/entity/bulletcartridge.obj";
		    public String cartridge_tex = "gvclib:textures/entity/bulletcartridge.png";
		    
		    public String bulletf_model = "gvclib:textures/entity/mflash.mqo";
		    public String bulletf_tex = "gvclib:textures/entity/mflash.png";
		    
		    public int bulletDameID = 0;
		    
		//03/01
		    public float arm_scale_r = 1.0F;
		    public float arm_scale_l = 1.0F;
		    public boolean canarm = true;
		    
		   //04/17 mob ga motterutoki
		    public int mobmax = 15;
		    public int mobrange = 50;
		    public int mobbullettype = 0;
		    public int mobammo = 40;
		    public int mobcycle = 2;
		    public int mobbarst = 3;
		    
		    //04/26 gun uemuki
		    public float fire_roteoffset_y = 0;
		    
		    public boolean mugen = false;
		    public boolean mugenmaga = false;
		    
		    //05/27 gun bullet seizon time
		    public int bullet_time_max = mod_GVCLib.cfg_bullet_living;
	    
		    
		    //07/02 gun ADS bure
		    public float bure_ads = 0.2F;
		    public float recoil_ads = 0.5F;
		    
		    //07/29 missile
		    public EntityLivingBase mitarget = null;
		    public boolean aam = true;
		    public float Shield_defence = 0.25F;
		    
		    //09/22
		    public boolean render_sight = true;
		    public boolean render_light = true;
		    public boolean render_muss = true;
		    public boolean render_grip = true;
		    
		    public boolean render_default_light = false;
		    
		    //09/29
		    /**スニーク中でもクロスヘアが表示されるかどうか*/
		    public boolean render_cross_sneak = false;
		    
		    //11/1
		    public boolean render_mat4_unam = false;
		    public boolean render_mat6_unam = false;
		    public boolean render_mat8_unam = false;
		    public boolean render_mat9_unam = false;
	    
		    
		    //19/1/14
		    public boolean rendering_light = true;
		    public boolean rendering_light_am_sight = true;
		    public boolean rendering_light_am_light = true;
		    public boolean rendering_light_am_muzzle = true;
		    public boolean rendering_light_am_grip = true;
		    
		    public boolean render_gvc_cross = true;
		    
		    
		    //19/5/10
		    public float fire_posx = 0.25F;
		    public float fire_posy = 1.5F;
		    public float fire_posz = 1.0F;
		    public float fire_posx_ads = 0.0F;
		    public float fire_posy_ads = 1.5F;
		    public float fire_posz_ads = 1.0F;
		    
		    
		    //19/6/24
		    public boolean canuse_sight = true;
		    public boolean canuse_light = true;
		    public boolean canuse_muss = true;
		    public boolean canuse_grip = true;
		    
		    public boolean canuse_plugin_a = true;
		    public boolean canuse_plugin_b = true;
		    public int plugin_a_count = 4;
		    public int plugin_b_count = 4;
		    //19/7/30
		    public int designated_kazu = 0;
		    public String[] designated_attachment_name = new String[64];
		    
		    //19/8/1
		    public boolean canbarst = false;
		    public int barst_cycle = 20;
		    public int barst_limit = 3;
		    
		    //19/9/22
		    public boolean can_dual_hand = false;
		    
		    //19/10/4
		    public boolean mat25move = true;
		    /**designated_kazu>=1の時
		     * mat4系の専用「ではない」装備が装備できるかどうか*/
		    public boolean designated_mat4 = false;
		    /**designated_kazu>=1の時
		     * mat6系の専用「ではない」装備が装備できるかどうか*/
		    public boolean designated_mat6 = true;
		    /**designated_kazu>=1の時
		     * mat8系の専用「ではない」装備が装備できるかどうか*/
		    public boolean designated_mat8 = false;
		    /**designated_kazu>=1の時
		     * mat9系の専用「ではない」装備が装備できるかどうか*/
		    public boolean designated_mat9 = false;
		    
		    
		    //19/11/7
		    /**そもそもマズルフラッシュのON/OFFがなかった件*/
		    public boolean muzzle_flash = true;
		    
		    
		public ItemGunBase() {
			//iconNames = new String[] {"reload", ""};
			//maxdamege = this.getMaxDamage();
			this.am_sightbase_x = this.model_x_ads;
			this.am_sightbase_y = this.model_y_ads;
			this.am_sightbase_z = this.model_z_ads;
			this.am_sightbase_z1 = this.am_sight_z;
			this.p_idbase = this.p_id;
			this.exlevelbase = this.exlevel;
			this.poworbase = this.powor;
			this.pelletbase = this.shotgun_pellet;
			this.recoilbase = this.recoil;
			this.burebase = this.bure;
		}
		
		
		/**GVCEventsGunRender_Third,
		 * GVCEventsGunRender_First,
		 * LayerGunBase
		 * */
		@SideOnly(Side.CLIENT)
		public void ModelLoad() {
			if(obj_model == null && obj_model_name != null) {
				obj_model = AdvancedModelLoader.loadModel(new ResourceLocation(obj_model_name));
				if(reload_motion_name != null) {
					ResourceLocation reloadmotion = new ResourceLocation(reload_motion_name);
					ReloadMotionReader.read(this, reloadmotion);
				}
				if(recoil_motion_name != null) {
					ResourceLocation recoilmotion = new ResourceLocation(recoil_motion_name);
					RecoilMotionReader.read(this, recoilmotion);
				}
			}
		}
		
		public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	    {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
	        {
	            playerIn.setActiveHand(handIn);//这个不能打断疾跑
	            return new ActionResult(EnumActionResult.SUCCESS, itemstack);
	        }
	    }
		
		
		public boolean checkTags(ItemStack pitemstack) {
			if (pitemstack.hasTagCompound()) {
				return true;
			}
			NBTTagCompound ltags = new NBTTagCompound();
			pitemstack.setTagCompound(ltags);
			ltags.setInteger("Reload", 0x0000);
			ltags.setByte("Bolt", (byte)0);
			ltags.setByte("Brast", (byte)0);
			ltags.setByte("Brast_cycle", (byte)0);
			NBTTagCompound lammo = new NBTTagCompound();
			for (int li = 0; li < getMaxDamage(); li++) {
				lammo.setLong(Integer.toString(li), 0L);
			}
			//ltags.setCompoundTag("Ammo", lammo);
			return false;
		}
		
		public void resetBolt(ItemStack pItemstack) {
			checkTags(pItemstack);
			pItemstack.getTagCompound().setByte("Bolt", getCycleCount(pItemstack));
		}
		
		public byte getCycleCount(ItemStack pItemstack) {
			return (byte) (this.cycle);
		}
		
		/**Barst処理*/
		public void setBrast(ItemStack pItemstack) {
			checkTags(pItemstack);
			NBTTagCompound nbt = pItemstack.getTagCompound();
			byte lb = nbt.getByte("Brast_cycle");
			nbt.setByte("Brast_cycle", (byte) ++lb);
			//System.out.println(String.format("%1$3d", (int)lb));
			if(lb > barst_limit) {
				//System.out.println(String.format("000000"));
				resetBrast(pItemstack);
			}
		}
		
		/**Barst処理*/
		public void resetBrast(ItemStack pItemstack) {
			checkTags(pItemstack);
			pItemstack.getTagCompound().setByte("Brast", getCycleCountBrast(pItemstack));
			pItemstack.getTagCompound().setByte("Brast_cycle", (byte)0);
		}
		/**Barst処理*/
		public byte getCycleCountBrast(ItemStack pItemstack) {
			return (byte)barst_cycle;
		}
		
		
		public boolean recoilreBolts(ItemStack pItemstack) {
			if(!pItemstack.hasTagCompound())
	        {
				pItemstack.setTagCompound(new NBTTagCompound());
				pItemstack.getTagCompound().setTag("Items", new NBTTagList());
	        }
			checkTags(pItemstack);
			NBTTagCompound lnbt = pItemstack.getTagCompound();
			byte lb = lnbt.getByte("Recoilre");
			if (lb <= 0) {
				return true;
			} else {
				lnbt.setByte("Recoilre", --lb);
				return false;
			}
		}
        protected void recoilreBolt(ItemStack pItemstack) {
			checkTags(pItemstack);
			pItemstack.getTagCompound().setByte("Recoilre", getCycleCountrecoilre(pItemstack));
		}
        public byte getCycleCountrecoilre(ItemStack pItemstack) {
			return (byte)3;
		}
		
		
		public static void updateCheckinghSlot(Entity pEntity, ItemStack pItemstack) {
			if (pEntity instanceof EntityPlayerMP) {
				EntityPlayerMP lep = (EntityPlayerMP)pEntity;
				Container lctr = lep.openContainer;
				for (int li = 0; li < lctr.inventorySlots.size(); li++) {
					ItemStack lis = ((Slot)lctr.getSlot(li)).getStack(); 
					if (lis == pItemstack) {
						lctr.inventoryItemStacks.set(li, pItemstack.copy());
						break;
					}
				}
			}
		}
		
		
		public ItemStack func_185060_a(EntityPlayer player)
	    {
	        if (this.func_185058_h_(player.getHeldItem(EnumHand.OFF_HAND)))
	        {
	            return player.getHeldItem(EnumHand.OFF_HAND);
	        }
	        else if (this.func_185058_h_(player.getHeldItem(EnumHand.MAIN_HAND)))
	        {
	            return player.getHeldItem(EnumHand.MAIN_HAND);
	        }
	        else
	        {
	            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
	            {
	                ItemStack itemstack = player.inventory.getStackInSlot(i);

	                if (this.func_185058_h_(itemstack))
	                {
	                    return itemstack;
	                }
	            }

	            return ItemStack.EMPTY;
	        }
	    }

	    public boolean func_185058_h_(ItemStack stack)
	    {
	    	String tab = null;
	    	boolean re = false;
	    	if(!stack.isEmpty() && stack.getItem() == this.magazine) {
	    		re = true;
	    	}
	    	if(this.magazine != null && this.magazine instanceof ItemMagazine) {
	    		ItemMagazine maga = (ItemMagazine) this.magazine;
	    		tab = maga.magazine_tab;
	    	}
	    	if(!stack.isEmpty() && stack.getItem() instanceof ItemMagazine) {
	    		ItemMagazine maga = (ItemMagazine) stack.getItem();
	    		if(tab != null && tab.equals(maga.magazine_tab)) {
	    			re = true;
	    		}
	    	}
	    	return re;
	    }

	    public String information1 = null;
		public String information2 = null;
		public String information3 = null;
		
		public TextFormatting information1_color = TextFormatting.WHITE;
		public TextFormatting information2_color = TextFormatting.WHITE;
		public TextFormatting information3_color = TextFormatting.WHITE;
	    //public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(information1 != null) {
			TextComponentTranslation information = new TextComponentTranslation(information1, new Object[0]);
			tooltip.add(information1_color + information.getFormattedText());
		}
		if(information2 != null) {
			TextComponentTranslation information = new TextComponentTranslation(information2, new Object[0]);
			tooltip.add(information2_color + information.getFormattedText());
		}
		if(information3 != null) {
			TextComponentTranslation information = new TextComponentTranslation(information3, new Object[0]);
			tooltip.add(information3_color + information.getFormattedText());
		}
		
		if(this instanceof IGun_Sword) {
			TextComponentTranslation mov = new TextComponentTranslation("gvclib.gun.move.name", new Object[0]);
			tooltip.add(TextFormatting.YELLOW + mov.getFormattedText() + TextFormatting.YELLOW + " : x "
					+ I18n.translateToLocal(String.format("%1$.2f ms", this.motion)));
		}
		else if(this instanceof IGun_Shield) {
			TextComponentTranslation mov = new TextComponentTranslation("gvclib.gun.move.name", new Object[0]);
			TextComponentTranslation sco = new TextComponentTranslation("gvclib.gun.scope.name", new Object[0]);
			TextComponentTranslation she = new TextComponentTranslation("gvclib.gun.shield.name", new Object[0]);
			tooltip.add(TextFormatting.BLUE + she.getFormattedText()
					+ TextFormatting.BLUE + ": x"
					+ I18n.translateToLocal(String.format("%1$.2f", this.Shield_defence)));
			tooltip.add(TextFormatting.YELLOW + mov.getFormattedText() + TextFormatting.YELLOW + " : x "
					+ I18n.translateToLocal(String.format("%1$.2f ms", this.motion)));
		}
		else if(this instanceof IGun_Grenade) {
			String powor = String
					.valueOf(this.powor + EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack) * 2);
			String speed = String.valueOf(this.speed);
			String retime = String.valueOf(this.reloadtime);
			String nokori = String.valueOf(getMaxDamage() - stack.getItemDamage());

			TextComponentTranslation rema = new TextComponentTranslation("gvclib.gun.remain.name", new Object[0]);
			TextComponentTranslation mag = new TextComponentTranslation("gvclib.gun.magazine.name", new Object[0]);
			TextComponentTranslation pow = new TextComponentTranslation("gvclib.gun.power.name", new Object[0]);
			TextComponentTranslation spe = new TextComponentTranslation("gvclib.gun.speed.name", new Object[0]);
			TextComponentTranslation ret = new TextComponentTranslation("gvclib.gun.reload.name", new Object[0]);
			TextComponentTranslation mov = new TextComponentTranslation("gvclib.gun.move.name", new Object[0]);
			TextComponentTranslation sco = new TextComponentTranslation("gvclib.gun.scope.name", new Object[0]);
			TextComponentTranslation she = new TextComponentTranslation("gvclib.gun.shield.name", new Object[0]);
			{
				int bullet_powor = 0;//信息
				int bullet_id = this.bulletDameID;
				float ex_level = this.exlevel;
				int p__id = this.p_id;
				int p__leve = this.p_level;
				int p__time = this.p_time;
				NBTTagCompound nbt = stack.getTagCompound();
				float extra_power = 0;
				if(nbt != null) {
					extra_power = nbt.getFloat("extra_power")+nbt.getFloat("extra_power2");
					if(nbt.getBoolean("am_bullet")){
						bullet_powor = nbt.getInteger("powor");
						bullet_id = nbt.getInteger("bulletDameID");
						
						ex_level = nbt.getFloat("exlevel");
						
						p__id = nbt.getInteger("p_id");
						p__leve = nbt.getInteger("p_level");
						p__time = nbt.getInteger("p_time");
					}
				}
				
				tooltip.add(TextFormatting.RED + rema.getFormattedText() + TextFormatting.RED + " : "
						+ I18n.translateToLocal(nokori));
				if (this.magazine != null) {
					if(this.magazine instanceof ItemMagazine) {
			    		ItemMagazine maga = (ItemMagazine) this.magazine;
			    		tooltip.add(TextFormatting.GREEN + mag.getFormattedText() + TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.magazine.getItemStackDisplayName(new ItemStack(this.magazine))));
			    		tooltip.add(TextFormatting.GREEN + mag.getFormattedText() + TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(maga.magazine_tab));
			    	}else {
			    		tooltip.add(TextFormatting.GREEN + mag.getFormattedText() + TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.magazine.getItemStackDisplayName(new ItemStack(this.magazine))));
			    	}
				}
				if(bullet_powor > 0) {//信息
					tooltip.add(TextFormatting.WHITE + pow.getFormattedText() + TextFormatting.WHITE + " + "
							+ I18n.translateToLocal(powor) + TextFormatting.GREEN + " + "+ I18n.translateToLocal(String.valueOf(bullet_powor)));
				}else {
					tooltip.add(TextFormatting.WHITE + pow.getFormattedText() + TextFormatting.WHITE + " + "
							+ I18n.translateToLocal(powor));
				}
				tooltip.add(TextFormatting.WHITE + spe.getFormattedText() + TextFormatting.WHITE + " + "
						+ I18n.translateToLocal(speed));
				tooltip.add(TextFormatting.YELLOW + ret.getFormattedText() + TextFormatting.YELLOW + " + "
						+ I18n.translateToLocal(retime) + " tick");
				tooltip.add(TextFormatting.YELLOW + mov.getFormattedText() + TextFormatting.YELLOW + " : x "
						+ I18n.translateToLocal(String.format("%1$.2f ms", this.motion)));
			if(this.exlevel > 0F){
				TextComponentTranslation exl = new TextComponentTranslation("gvclib.gun.exlevel.name", new Object[0]);
				TextComponentTranslation exb = new TextComponentTranslation("gvclib.gun.ex.name", new Object[0]);
				String exx = String.valueOf(this.bure);
				tooltip.add(TextFormatting.YELLOW + exl.getFormattedText() + TextFormatting.YELLOW + " + "
						+ I18n.translateToLocal(exx) + " F");
				if(ex) {
					tooltip.add(TextFormatting.YELLOW + exb.getFormattedText());
				}
			}
			}
		}
		else {
			if(this.can_dual_hand) {
				TextComponentTranslation rema = new TextComponentTranslation("gvclib.gun.dual_hand.name", new Object[0]);
				tooltip.add(TextFormatting.AQUA + rema.getFormattedText());
			}
			
			String powor = String
					.valueOf(this.powor + EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack) * 2);
			String speed = String.valueOf(this.speed);
			String bure = String.valueOf(this.bure);
			String bureads = String.valueOf(this.bure * this.bure_ads);
			String recoil = String.valueOf(this.recoil);
			String recoilads = String.valueOf(this.recoil_ads);//信息
			String retime = String.valueOf(this.reloadtime);
			String nokori = String.valueOf(getMaxDamage() - stack.getItemDamage());
			/*float cy = 1200 / this.cycle;
			String cycle = String.format("%1$.2f ms",cy);
			float co = 1200 / this.cocktime;
			String cool = String.format("%1$.2f ms",co);*/

			TextComponentTranslation rema = new TextComponentTranslation("gvclib.gun.remain.name", new Object[0]);
			TextComponentTranslation mag = new TextComponentTranslation("gvclib.gun.magazine.name", new Object[0]);
			TextComponentTranslation pow = new TextComponentTranslation("gvclib.gun.power.name", new Object[0]);
			TextComponentTranslation spe = new TextComponentTranslation("gvclib.gun.speed.name", new Object[0]);
			TextComponentTranslation bur = new TextComponentTranslation("gvclib.gun.bure.name", new Object[0]);
			TextComponentTranslation rec = new TextComponentTranslation("gvclib.gun.recoid.name", new Object[0]);
			TextComponentTranslation ret = new TextComponentTranslation("gvclib.gun.reload.name", new Object[0]);
			TextComponentTranslation mov = new TextComponentTranslation("gvclib.gun.move.name", new Object[0]);
			TextComponentTranslation sco = new TextComponentTranslation("gvclib.gun.scope.name", new Object[0]);
			TextComponentTranslation she = new TextComponentTranslation("gvclib.gun.shield.name", new Object[0]);
			if (this.powor > 0) {
				tooltip.add(TextFormatting.RED + rema.getFormattedText() + TextFormatting.RED + " : "
						+ I18n.translateToLocal(nokori));
				if (this.magazine != null) {
					if(this.magazine instanceof ItemMagazine) {
			    		ItemMagazine maga = (ItemMagazine) this.magazine;
			    		tooltip.add(TextFormatting.GREEN + mag.getFormattedText() + TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.magazine.getItemStackDisplayName(new ItemStack(this.magazine))));
			    		tooltip.add(TextFormatting.GREEN + mag.getFormattedText() + TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(maga.magazine_tab));
			    	}else {
			    		tooltip.add(TextFormatting.GREEN + mag.getFormattedText() + TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.magazine.getItemStackDisplayName(new ItemStack(this.magazine))));
			    	}
				}
				float extra_power = 0;
				NBTTagCompound nbt = stack.getTagCompound();
				if(nbt != null){
					extra_power = nbt.getFloat("extra_power")+nbt.getFloat("extra_power2");
				}
				String ex_powor = String.valueOf(extra_power * 100);
				if(extra_power>0){
					tooltip.add(TextFormatting.WHITE + pow.getFormattedText() + TextFormatting.WHITE + " + "
							+ I18n.translateToLocal(powor) + " + " + I18n.translateToLocal(ex_powor) + "％");
				}else{
					tooltip.add(TextFormatting.WHITE + pow.getFormattedText() + TextFormatting.WHITE + " + "
							+ I18n.translateToLocal(powor));
				}

				tooltip.add(TextFormatting.WHITE + spe.getFormattedText() + TextFormatting.WHITE + " + "
						+ I18n.translateToLocal(speed));
				tooltip.add(TextFormatting.WHITE + bur.getFormattedText() + TextFormatting.WHITE + " x "
						+ I18n.translateToLocal(bure) + "/" +  I18n.translateToLocal(bureads));
				tooltip.add(TextFormatting.WHITE + rec.getFormattedText() + TextFormatting.WHITE + " + "
						+ I18n.translateToLocal(recoil) + "/" +  I18n.translateToLocal(recoilads));
				tooltip.add(TextFormatting.YELLOW + ret.getFormattedText() + TextFormatting.YELLOW + " + "
						+ I18n.translateToLocal(retime) + " tick");
				tooltip.add(TextFormatting.YELLOW + mov.getFormattedText() + TextFormatting.YELLOW + " : x "
						+ I18n.translateToLocal(String.format("%1$.2f ms", this.motion)));
				if(this.exlevel > 0F){
					TextComponentTranslation exl = new TextComponentTranslation("gvclib.gun.exlevel.name", new Object[0]);
					TextComponentTranslation exb = new TextComponentTranslation("gvclib.gun.ex.name", new Object[0]);
					String exx = String.valueOf(this.bure);
					tooltip.add(TextFormatting.YELLOW + exl.getFormattedText() + TextFormatting.YELLOW + " + "
							+ I18n.translateToLocal(exx) + " F");
					if(ex) {
						tooltip.add(TextFormatting.YELLOW + exb.getFormattedText());
					}
				}
				/*if(this.cocktime > this.cycle) {
					tooltip.add(TextFormatting.YELLOW + "gvclib.gun.cycle.name " + "+" + I18n.translateToLocal(cool));
				}else {
					tooltip.add(TextFormatting.YELLOW + "gvclib.gun.cycle.name " + "+" + I18n.translateToLocal(cycle));
				}*/
				//if (!(this.scopezoom == 1.0f)) 
				{
					//String scopezoom = String.valueOf(this.scopezoom);
					tooltip.add(TextFormatting.WHITE + sco.getFormattedText()
							+ TextFormatting.WHITE + " x "
							+ I18n.translateToLocal(String.format("%1$.2f", this.scopezoom)));
				}
				{
					TextComponentTranslation am_bullet = new TextComponentTranslation("gvclib.gun.attachment_bullet.name", new Object[0]);
					tooltip.add(TextFormatting.YELLOW + am_bullet.getFormattedText());
					for(int ic = 0; ic < this.canuse_bullet_kazu; ++ic) {
						tooltip.add(TextFormatting.YELLOW + " ::::: "
								+ I18n.translateToLocal(this.canuse_bullet[ic]));
					}
				}
				{
					if(designated_kazu != 0) {
						for(int kazu = 0; kazu > designated_kazu; ++kazu) {
							tooltip.add(TextFormatting.YELLOW + "AttachmentGroup" + " : " + designated_attachment_name[kazu]);
						}
					}
				}
				{
					if (!this.sight_item.isEmpty()) {
						tooltip.add(TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.sight_item.getDisplayName()));
					}
					if (!this.light_item.isEmpty()) {
						tooltip.add(TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.light_item.getDisplayName()));
					}
					if (!this.supu_item.isEmpty()) {
						tooltip.add(TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.supu_item.getDisplayName()));
					}
					if (!this.grip_item.isEmpty()) {
						tooltip.add(TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.grip_item.getDisplayName()));
					}
					if (!this.bullet_item.isEmpty()) {
						tooltip.add(TextFormatting.GREEN + " : "
								+ I18n.translateToLocal(this.bullet_item.getDisplayName()));
					}
				}
				{
					if(this.gun_can_use_underbarrel) {
						TextComponentTranslation under_on = new TextComponentTranslation("gvclib.gun.under_can.name", new Object[0]);
						tooltip.add(TextFormatting.GREEN + under_on.getFormattedText());
					}
					if(this.gun_can_get_underbarrel) {
						TextComponentTranslation under_on = new TextComponentTranslation("gvclib.gun.under_get.name", new Object[0]);
						tooltip.add(TextFormatting.GREEN + under_on.getFormattedText());
					}
					if(this.gun_can_set_underbarrel) {
						TextComponentTranslation under_on = new TextComponentTranslation("gvclib.gun.under_set.name", new Object[0]);
						tooltip.add(TextFormatting.GREEN + under_on.getFormattedText());
					}
				}
				
				
			} else {
				
			}
			/*if(this.powor > 0) {
			tooltip.add(TextFormatting.RED + "RemainingBullet " + I18n.translateToLocal(nokori));
			if (this.magazine != null) {
				tooltip.add(TextFormatting.GREEN + "Magazine "
						+ I18n.translateToLocal(this.magazine.getItemStackDisplayName(new ItemStack(this.magazine))));
			}
			tooltip.add(TextFormatting.WHITE + "FireDamege " + "+" + I18n.translateToLocal(powor));
			tooltip.add(TextFormatting.WHITE + "BlletSpeed " + "+" + I18n.translateToLocal(speed));
			tooltip.add(TextFormatting.WHITE + "BlletSpread " + "+" + I18n.translateToLocal(bure));
			tooltip.add(TextFormatting.WHITE + "Recoil " + "+" + I18n.translateToLocal(recoil));
			tooltip.add(TextFormatting.YELLOW + "ReloadTime " + "+" + I18n.translateToLocal(retime));
			tooltip.add(TextFormatting.YELLOW + "MoveSpeed " + ": x"
					+ I18n.translateToLocal(String.format("%1$.2f ms", this.motion)));
			if (!(this.scopezoom == 1.0f)) {
				String scopezoom = String.valueOf(this.scopezoom);
				tooltip.add(TextFormatting.WHITE + "ScopeZoom " + "x" + I18n.translateToLocal(scopezoom));
			}
			}
			else {
			  tooltip.add(TextFormatting.BLUE + "Shield_defence " + ": x" + I18n.translateToLocal(String.format("%1$.2f ms", this.Shield_defence)));
			  tooltip.add(TextFormatting.YELLOW + "MoveSpeed " + ": x"
					+ I18n.translateToLocal(String.format("%1$.2f ms", this.motion)));
			}*/
			
		}
		
		
	}
	    @Override
	    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	    {
	        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
	        
	        /*if(this.true_mat12){
	        	
	        	GVCInventoryItem inventory = new GVCInventoryItem(playerInv, itemstack);
				inventory.openInventory();
					ItemStack itemstacki = inventory.getStackInSlot(i1);
	        	if (!itemstacki.isEmpty() && itemstacki.getItem() instanceof ItemSword) {
	        		ItemSword sword = (ItemSword) itemstacki.getItem();
	        		sword.at
		        }
	        }*/
	        
	        
	        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	        {
	            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
	            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)this.attackSpeed, 0));
	        }

	        return multimap;
	    }
		
		

	public float func_150893_a(ItemStack p_150893_1_, Block p_150893_2_)
    {
        
            return 1.0F;
        
        
    }
    
    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    public boolean isFull3D()
    {
        return true;
    }
    
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        return p_77654_1_;
    }
    
    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	/*if(mod_GVCLib.proxy.mcbow() == 0){
    	return EnumAction.NONE;
    	}else*/
    	{
    	return EnumAction.NONE;
    	}
    }

    /**
     * How long it takes to use or consume an item
     */
    /*public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
    	if(par1ItemStack.getItemDamage() == this.getMaxDamage()){
    		return 20;
    		
    	}
    	return 7200;
    	
    }*/
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
    	return 7200;
    }

    

    public boolean func_150897_b(Block p_150897_1_)
    {
        return p_150897_1_ == Blocks.WEB;
    }

    public boolean isWeaponReload(ItemStack itemstack, EntityPlayer entityplayer)
    {
    	/*NBTTagCompound nbt = itemstack.getTagCompound();
		int reloadtime = nbt.getInteger("gvcreloadtime");
		int reload = nbt.getInteger("gvcreload");
		return reload==1;*/
     return false;
    }
    public boolean isWeaponFullAuto(ItemStack itemstack) {
		return false;
	}
	
	public boolean aim_fire = true;
	public float time_aim = 6F;
	public float time_run = 6F;
	
	public float run_time;
	public static float aim_time;
	public int active_time;
	
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
    	super.onUpdate(itemstack, world, entity, i, flag);
		boolean left_key = mod_GVCLib.proxy.leftclick();

    	if(flag) {//手持时
			if(this.cooltime < 6)++this.cooltime;//枪械奔跑动画
			fire_sr = true;
			if(entity instanceof EntityPlayer) {
        		EntityPlayer entityplayer = (EntityPlayer)entity;
				if(entityplayer.getActiveItemStack() == itemstack){
					if(this.active_time<10)++this.active_time;
					if(this.aim_time<this.time_aim)++this.aim_time;//枪械瞄准动画
				}else{
					if(this.active_time>0)--this.active_time;
					if(this.aim_time>0 && this.active_time<=0)--this.aim_time;//枪械瞄准动画
				}
				
				if(entityplayer.isSprinting()){
					if(this.run_time<this.time_run)++this.run_time;//枪械奔跑动画
				}else{
					if(this.run_time>0)--this.run_time;//枪械奔跑动画
				}

				if(left_key)fire_sr = false;//半自动判定
			}
    		if(!world.isRemote) {
    			if(entity instanceof EntityPlayer) {
        			EntityPlayer entityplayer = (EntityPlayer)entity;
        			if(entityplayer.getCooldownTracker().getCooldown(itemstack.getItem(), 0) == 0) {
        				if(!this.underbarrel.isEmpty() && this.underbarrel.getItem() instanceof ItemGunBase) {
        					ItemGunBase under = (ItemGunBase) this.underbarrel.getItem();
        					if(this.underbarrel.getItemDamage() == under.getMaxDamage()) {
        						under.getReload(this.underbarrel, world, entityplayer);
            					GVCInventoryItem inventory = new GVCInventoryItem(entityplayer.inventory, itemstack);
        						//GVCInventoryItem inventory = new GVCInventoryItem(entityplayer);
            					inventory.setInventorySlotContents(4, this.underbarrel);
            					ItemStack i1 = this.sight_item;
            					if(!i1.isEmpty())inventory.setInventorySlotContents(1, i1);
            					ItemStack i2 = this.light_item;
            					if(!i2.isEmpty())inventory.setInventorySlotContents(2, i2);
            					ItemStack i3 = this.supu_item;
            					if(!i3.isEmpty())inventory.setInventorySlotContents(3, i3);
            					ItemStack i5 = this.bullet_item;
            					if(!i5.isEmpty())inventory.setInventorySlotContents(5, i5);
            					inventory.closeInventory(entityplayer);
        					}
        				}
            		}
        		}
    		}
    	}
    }
    
	public boolean cycleBolt(ItemStack pItemstack) {
		checkTags(pItemstack);
		NBTTagCompound lnbt = pItemstack.getTagCompound();
		byte lb = lnbt.getByte("Bolt");
		if (lb <= 0) {
			//if (pReset) resetBolt(pItemstack);
			return true;
		} else {
			lnbt.setByte("Bolt", --lb);
			return false;
		}
	}
	/**Barst処理*/
	public boolean cycleBrast(ItemStack pItemstack) {
		checkTags(pItemstack);
		NBTTagCompound lnbt = pItemstack.getTagCompound();
		byte lb = lnbt.getByte("Brast");
		if (lb <= 0) {
			return true;
		} else {
			lnbt.setByte("Brast", --lb);
			return false;
		}
	}
	
    public void Fire_AR_Left(ItemStack itemstack, World world, Entity entity, int i, boolean flag, boolean flag1) {//发射
    	int li = getMaxDamage() - itemstack.getItemDamage();
    	EntityPlayer entityplayer = (EntityPlayer) entity;
		NBTTagCompound nbt = itemstack.getTagCompound();
    	boolean lflag = cycleBolt(itemstack);
		boolean lflag_barst = cycleBrast(itemstack);
		//boolean left_key = mod_GVCLib.proxy.leftclick();
		boolean left_key = true;
		byte lb = nbt.getByte("Bolt");
		byte lb2 = nbt.getByte("Brast");
    	if (entity != null /*&& !world.isRemote*/ && entityplayer != null && !itemstack.isEmpty()) 
		{
    		/*if(itemstack == entityplayer.getHeldItemOffhand() && itemstack.getItem() instanceof ItemGunBase) {//副手
    			if (lflag && lflag_barst && left_key||lb <= 0 && lb2<=0 && left_key) {
					if (li > 0) {
						this.cooltime = 0;//开火时刻
						this.Fire_Base(itemstack, world, entityplayer, li, false, nbt, flag1);
					} else {
						ItemGunBase.updateCheckinghSlot(entity, itemstack);
					}
				} else {
					ItemGunBase.updateCheckinghSlot(entity, itemstack);
				}
    		}
			else
			*/{//主手
				if (left_key) {
    				/*if (flag)*/
					{
    					if (lflag && lflag_barst||lb <= 0 && lb2<=0) {//确保动画流畅
    						if (li > 0) {
								this.cooltime = 0;//开火时刻
    							this.Fire_Base(itemstack, world, entityplayer, li, true, nbt, flag1);
    						} else {
    							if(world.isRemote)ItemGunBase.updateCheckinghSlot(entity, itemstack);
    						}
    					} else {
    						if(world.isRemote)ItemGunBase.updateCheckinghSlot(entity, itemstack);
    					}
    				}
    			}
    		}
		}
    }
    
    public void Fire_SR_Left(ItemStack itemstack, World world, Entity entity, int i, boolean flag, boolean flag1) {//半自动
    	int li = getMaxDamage() - itemstack.getItemDamage();
    	EntityPlayer entityplayer = (EntityPlayer) entity;
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean cocking = nbt.getBoolean("Cocking");
		//boolean left_key = mod_GVCLib.proxy.leftclick();
		boolean left_key = true;
		if (entity != null /*&& !world.isRemote*/ && entityplayer != null && !itemstack.isEmpty()) 
		{
			/*if(itemstack == entityplayer.getHeldItemOffhand() && itemstack.getItem() instanceof ItemGunBase) {//副手
    			if (left_key)
				{
    				if (cocking) {
    					if (li > 0) {
							this.cooltime = 0;//开火时刻
							this.Fire_Base(itemstack, world, entityplayer, li, true, nbt, flag1);
    					} else {
    						ItemGunBase.updateCheckinghSlot(entity, itemstack);
    					}
    				} else {
    					ItemGunBase.updateCheckinghSlot(entity, itemstack);
    				}
				}
    		}
			else
			*/{//主手
				if (left_key) {
    				/*if (flag)*/
    				{
    					if (cocking) {
    						if (li > 0) {
								this.cooltime = 0;//开火时刻
								this.Fire_Base(itemstack, world, entityplayer, li, true, nbt, flag1);
    						} else {
    							if(world.isRemote)ItemGunBase.updateCheckinghSlot(entity, itemstack);
    						}
    					} else {
    						if(world.isRemote)ItemGunBase.updateCheckinghSlot(entity, itemstack);
    					}
    				}
    			}
    		}
		}
    }

    public boolean fire_sr = true;
    public void Fire_Base(ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean hand, NBTTagCompound nbt, boolean flag1) {
    	if(flag1)FireBullet(itemstack, world, entityplayer);
		if(!flag1)mod_GVCLib.proxy.onShootAnimation(entityplayer, itemstack);
		/*if(world.isRemote)*/{
			resetBolt(itemstack);
			if(canbarst)setBrast(itemstack);
			if(world.isRemote)ItemGunBase.updateCheckinghSlot(entityplayer, itemstack);
			{
				if(itemstack.hasTagCompound()) {
					nbt.setBoolean("Recoiled", false);
					nbt.setBoolean("Cocking", false);
					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
							if(hand) {
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(3, entityplayer.getEntityId(), false), playermp);
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(4, entityplayer.getEntityId(), false), playermp);
							}else {
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(31, entityplayer.getEntityId(), false), playermp);
								GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(41, entityplayer.getEntityId(), false), playermp);
							}
						}
					}
				}
			}
		}
    }
	
	public String techgunfx = null;
    public boolean uselight = mod_GVCLib.gvclibsa_flash;
    public boolean shrinkitem = false;
    public void FireBullet(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
    	int ra = par2World.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
    	if(this.fires != null) {
			par2World.playSound((EntityPlayer)null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ,
					this.fires, SoundCategory.NEUTRAL, 3.0F, 0.9F + val);
		}else if(sound != null){
			par2World.playSound((EntityPlayer)null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ,
					SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, sound)), SoundCategory.NEUTRAL, 3.0F, 0.9F + val);
		}
		if(!this.mugen) {
			{
				if(!(par1ItemStack.getItem() instanceof ItemGun_Grenade)) {
					par1ItemStack.damageItem(1, par3EntityPlayer);
					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
							GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(5, par3EntityPlayer.getEntityId()), playermp);
					}
					
				}
			}
		}
		if(shrinkitem) {
			if (!par3EntityPlayer.capabilities.isCreativeMode)
	        {
				par1ItemStack.shrink(1);
				if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
					for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
						GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(6, par3EntityPlayer.getEntityId()), playermp);
				}
				
	        }
		}
		int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		int pellet = this.shotgun_pellet;
		if(nbt.getBoolean("am_bullet")){
			pellet = nbt.getInteger("shotgun_pellet");
		}
		{
			for(int p = 0; p < pellet; ++p){
				EntityBBase var8;
				if(this.gun_type == 0){
					var8 = new EntityB_Bullet(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 1){
					var8 = new EntityB_BulletAA(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 2){
					var8 = new EntityB_GrenadeB(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 3){
					var8 = new EntityB_Shell(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 4){
					var8 = new EntityB_Missile(par2World, (EntityLivingBase) par3EntityPlayer);
					EntityB_Missile mi = (EntityB_Missile) var8;
					mi.speedd = speed;
				}else if(this.gun_type == 5){
					var8 = new EntityB_BulletAA(par2World, (EntityLivingBase) par3EntityPlayer);
					var8.bulletDameID = 1;
				}else if(this.gun_type == 6){
					var8 = new EntityB_BulletFire(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 7){
					var8 = new EntityB_BulletAA(par2World, (EntityLivingBase) par3EntityPlayer);
					EntityB_BulletAA aa = (EntityB_BulletAA) var8;
					aa.exnear = true;
				}else if(this.gun_type == 8){
					var8 = new EntityB_Missile(par2World, (EntityLivingBase) par3EntityPlayer);
					EntityB_Missile mi = (EntityB_Missile) var8;
					mi.speedd = speed;
					mi.autoaim = false;
				}else if(this.gun_type == 9){
					var8 = new EntityT_Grenade(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 31){
					var8 = new EntityB_Bullet(par2World, (EntityLivingBase) par3EntityPlayer);
					var8.inwater = true;
				}else {
					var8 = new EntityB_Bullet(par2World, (EntityLivingBase) par3EntityPlayer);
				}
				int bullet_powor = this.powor;//玩家用
				int bullet_id = this.bulletDameID;
				float ex_level = this.exlevel;
				int p__id = this.p_id;
				int p__leve = this.p_level;
				int p__time = this.p_time;
				if(nbt.getBoolean("am_bullet")){
					bullet_powor = nbt.getInteger("powor");
					bullet_id = nbt.getInteger("bulletDameID");
					ex_level = nbt.getFloat("exlevel");
					
					p__id = nbt.getInteger("p_id");
					p__leve = nbt.getInteger("p_level");
					p__time = nbt.getInteger("p_time");
				}
				if(test_damage){
					var8.Bdamege = 1;
				}else{
					var8.Bdamege = (int)((bullet_powor + pluspower*2) * (1+nbt.getFloat("extra_power")+nbt.getFloat("extra_power2")));
				}
				var8.gra = this.gra;
				var8.muteki = true;
				var8.friend = par3EntityPlayer;
				var8.setModel(this.bullet_model);
				var8.setTex(this.bullet_tex);
				var8.setModelF(this.bulletf_model);
				if(var8.usetgfx){
					if(this.techgunfx!=null){
					var8.setTexF(this.techgunfx);
					}else{
						if(var8 instanceof EntityB_Bullet){
						var8.setTexF("SABulletTrail");
						}else if(var8 instanceof EntityB_Shell){
						var8.setTexF("SAAPTrail");
						}else if(var8 instanceof EntityB_Missile){
						var8.setTexF("SAMissileTrail");
						}else if(var8 instanceof EntityB_BulletFire){
						var8.setTexF("FlamethrowerTrail");
						}else if(var8 instanceof EntityB_BulletAA){
						var8.setTexF("SAAATrail");
						}
					}
				}
				var8.bulletDameID = bullet_id;
				var8.ex = this.ex;
				var8.exfire = this.exfire;
				var8.exsmoke = this.exsmoke;
				var8.exflash = this.exflash;
				var8.exgas = this.exgas;
				
				var8.exlevel = ex_level + nbt.getFloat("extra_exlevel")+nbt.getFloat("extra_exlevel2");
				var8.P_ID = p__id;
				var8.P_LEVEL = p__leve;
				var8.P_TIME = p__time;
				
				ItemGunBase gun = null;
				if(!par3EntityPlayer.getHeldItemMainhand().isEmpty() && par3EntityPlayer.getHeldItemMainhand().getItem() instanceof ItemGunBase) {
					gun = (ItemGunBase) par3EntityPlayer.getHeldItemMainhand().getItem();
				}
				if(!par3EntityPlayer.getHeldItemOffhand().isEmpty() && par3EntityPlayer.getHeldItemOffhand().getItem() instanceof ItemGunBase) {
					gun = (ItemGunBase) par3EntityPlayer.getHeldItemOffhand().getItem();
				}
				if(gun != null) {
					var8.mitarget = GunBase_LockOn.getMissileTarget(gun, par1ItemStack, par2World, par3EntityPlayer);
					var8.mitargetid = GunBase_LockOn.getMissileTarget_ID(gun, par1ItemStack, par2World, par3EntityPlayer);
				}
				float bbure = this.bure;
				var8.timemax = this.bullet_time_max;
				
				float extra_nbt_bure = nbt.getFloat("extra_bure")+nbt.getFloat("extra_bure2");
				boolean left_key = mod_GVCLib.proxy.leftclick();
				if(par1ItemStack.hasTagCompound()) {
					boolean am_grip = nbt.getBoolean("am_grip");
					if(am_grip) {
						float nbt_bure = nbt.getFloat("bure");
						float nbt_bure_ads = nbt.getFloat("bure_ads");

						if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {//瞄准/蹲下减少散布
							bbure = nbt_bure_ads * this.bure_ads * (1 + extra_nbt_bure);
						}else if(par3EntityPlayer.isSprinting()){
							bbure = nbt_bure *1.5F * (1 + extra_nbt_bure);
						}else{
							bbure = nbt_bure * (1 + extra_nbt_bure);
						}
					}else {
						if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {
							bbure = this.bure * this.bure_ads * (1 + extra_nbt_bure);
						}else if(par3EntityPlayer.isSprinting()){
							bbure = this.bure*1.5F * (1 + extra_nbt_bure);
						}else{
							bbure = this.bure * (1 + extra_nbt_bure);
						}
					}
				}else {
					if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {
						bbure = this.bure * this.bure_ads * (1 + extra_nbt_bure);
					}else if(par3EntityPlayer.isSprinting()){
						bbure = this.bure*1.5F * (1 + extra_nbt_bure);
					}else{
						bbure = this.bure * (1 + extra_nbt_bure);
					}
				}
				if(nbt.getBoolean("am_bullet")){
					bbure = bbure * nbt.getFloat("bure_bullet") * (1 + extra_nbt_bure);
				}
				
				
				//{
					double xx11 = 0;
					double zz11 = 0;
					double yy11 = 0;
					float xz = 1.57F;
					if(!par3EntityPlayer.getHeldItemMainhand().isEmpty() && !par3EntityPlayer.getHeldItemOffhand().isEmpty()) {
						if(par3EntityPlayer.getHeldItemMainhand() == par1ItemStack) {
							xz = 1.57F;
						}else if(par3EntityPlayer.getHeldItemOffhand() == par1ItemStack){
							xz = -1.57F;
						}
					}else if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0||par3EntityPlayer.getActiveItemStack() == par1ItemStack) {//瞄准子弹左右坐标
						xz = 0;
					}else if(par3EntityPlayer.getHeldItemMainhand() == par1ItemStack) {
						xz = 1.57F;
					}else if(par3EntityPlayer.getHeldItemOffhand() == par1ItemStack){
						xz = -1.57F;
					}
					double yy = fire_posy;
					if (par3EntityPlayer.isSneaking()) {//蹲下时子弹高度坐标
						yy = fire_posy_ads;
					}
					double zzz = fire_posz * Math.cos(Math.toRadians(-par3EntityPlayer.rotationPitch));
					xx11 -= MathHelper.sin(par3EntityPlayer.rotationYawHead * 0.01745329252F) * zzz;
					zz11 += MathHelper.cos(par3EntityPlayer.rotationYawHead * 0.01745329252F) * zzz;
					xx11 -= MathHelper.sin(par3EntityPlayer.rotationYawHead * 0.01745329252F + xz) * fire_posx;
					zz11 += MathHelper.cos(par3EntityPlayer.rotationYawHead * 0.01745329252F + xz) * fire_posx;
					yy11 = MathHelper.sqrt(zzz* zzz) * Math.tan(Math.toRadians(-par3EntityPlayer.rotationPitch)) * 1D;
					var8.setLocationAndAngles(par3EntityPlayer.posX + xx11, par3EntityPlayer.posY + yy + yy11, par3EntityPlayer.posZ + zz11, 
							par3EntityPlayer.rotationYaw,par3EntityPlayer.rotationPitch);
				//}
				
				var8.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch + this.fire_roteoffset_y, par3EntityPlayer.rotationYaw, 0.0F,
						this.speed, bbure);
				if (!par2World.isRemote) {
					par2World.spawnEntity(var8);
				}
				if(!this.true_mat8){
					if(muzzle_flash){
						EntityT_Flash flash = new EntityT_Flash(par2World, par3EntityPlayer);
						
						flash.gra = 0.03;
						flash.timemax = 1;
						flash.setModel(this.bulletf_model);
						flash.setTex(this.bulletf_tex);
						
						double offyyy = 0.1D;
						if (par3EntityPlayer.isSneaking()) {//蹲下子弹高度坐标
							offyyy = 0.3D;
						}
						flash.setLocationAndAngles(par3EntityPlayer.posX + xx11, par3EntityPlayer.posY + (yy - offyyy) + yy11, par3EntityPlayer.posZ + zz11, 
								par3EntityPlayer.rotationYaw,par3EntityPlayer.rotationPitch);
						flash.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch + this.fire_roteoffset_y, par3EntityPlayer.rotationYaw, 0.0F,
								0.1F, bbure);
						if (!par2World.isRemote) {
							par2World.spawnEntity(flash);
						}
					}
				}
				if(uselight){
					EntityT_Light light = new EntityT_Light(par2World, par3EntityPlayer);
					light.gra = 0.03;
					light.timemax = 1;
					if (!par2World.isRemote) {
						par2World.spawnEntity(light);
					}
				}
			}
		}
		/*if(bullet_c){//弹壳
			//System.out.println("debug");
			EntityB_Cratridge var81 = new EntityB_Cratridge(par2World, (EntityLivingBase) par3EntityPlayer);
			var81.setModel(this.cartridge_model);
			var81.setTex(this.cartridge_tex);
			//var8.setThrowableHeading(x, y, z, velocity, inaccuracy);
			var81.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw + 110, 0.0F,0.2F,5);
			if (!par2World.isRemote) {
				par2World.spawnEntity(var81);
			}
		}*/
	}
    
    public void FireBulletSA(ItemStack par1ItemStack, World par2World, EntityLiving par3EntityPlayer) {//给其它非GVC生物用
    	int ra = par2World.rand.nextInt(10) + 1;
    	float val = ra * 0.02F;
    	if(this.fires != null) {
			par3EntityPlayer.playSound(this.fires, 3.0F, 0.9F + val); 
		}else if(sound != null){
			par3EntityPlayer.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, sound)), 3.0F, 0.9F + val); 
		}
			par3EntityPlayer.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, sound)), 4.0F, this.soundsp); 
		int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);
		int pellet = this.shotgun_pellet;
		{
			for(int p = 0; p < pellet; ++p){
				EntityBBase var8;
				if(this.gun_type == 0){
					var8 = new EntityB_Bullet(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 1){
					var8 = new EntityB_BulletAA(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 2){
					var8 = new EntityB_GrenadeB(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 3){
					var8 = new EntityB_Shell(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 4){
					var8 = new EntityB_Missile(par2World, (EntityLivingBase) par3EntityPlayer);
					EntityB_Missile mi = (EntityB_Missile) var8;
					mi.speedd = speed;
				}else if(this.gun_type == 5){
					var8 = new EntityB_BulletAA(par2World, (EntityLivingBase) par3EntityPlayer);
					var8.bulletDameID = 1;
				}else if(this.gun_type == 6){
					var8 = new EntityB_BulletFire(par2World, (EntityLivingBase) par3EntityPlayer);
				}else if(this.gun_type == 7){
					var8 = new EntityB_BulletAA(par2World, (EntityLivingBase) par3EntityPlayer);
					EntityB_BulletAA aa = (EntityB_BulletAA) var8;
					aa.exnear = true;
				}else if(this.gun_type == 8){
					var8 = new EntityB_Missile(par2World, (EntityLivingBase) par3EntityPlayer);
					EntityB_Missile mi = (EntityB_Missile) var8;
					mi.speedd = speed;
					mi.autoaim = false;
				}else if(this.gun_type == 9){
					var8 = new EntityT_Grenade(par2World, (EntityLivingBase) par3EntityPlayer);
				}else {
					var8 = new EntityB_Bullet(par2World, (EntityLivingBase) par3EntityPlayer);
				}
				int bullet_powor = this.powor;//SA兼容生物用
				int bullet_id = this.bulletDameID;
				float ex_level = this.exlevel;
				int p__id = this.p_id;
				int p__leve = this.p_level;
				int p__time = this.p_time;
				var8.Bdamege = bullet_powor + pluspower*2;
				var8.gra = this.gra;
				var8.muteki = true;
				var8.friend = par3EntityPlayer;
				var8.setModel(this.bullet_model);
				var8.setTex(this.bullet_tex);
				var8.setModelF(this.bulletf_model);
				var8.setTexF(this.bulletf_tex);
				
				if(var8.usetgfx){
					if(this.techgunfx!=null){
					var8.setTexF(this.techgunfx);
					}else{
						if(var8 instanceof EntityB_Bullet){
						var8.setTexF("SABulletTrail");
						}else if(var8 instanceof EntityB_Shell){
						var8.setTexF("SAAPTrail");
						}else if(var8 instanceof EntityB_BulletFire){
						var8.setTexF("FlamethrowerTrail");
						}else if(var8 instanceof EntityB_Missile){
						var8.setTexF("SAMissileTrail");
						}else if(var8 instanceof EntityB_BulletAA){
							if(var8.exlevel<2F){
								var8.setTexF("SAAATrail");
							}else{
								var8.setTexF("LSAAATrail");
							}
						}else if(var8 instanceof EntityT_Grenade){
						var8.setTexF("SAAPTrail");
						}
					}
				}
				
				var8.bulletDameID = bullet_id;
				var8.ex = this.ex;
				var8.exfire = this.exfire;
				var8.exsmoke = this.exsmoke;
				var8.exflash = this.exflash;
				var8.exgas = this.exgas;
				var8.exlevel = ex_level;
				var8.P_ID = p__id;
				var8.P_LEVEL = p__leve;
				var8.P_TIME = p__time;
				ItemGunBase gun = null;
				if(!par3EntityPlayer.getHeldItemMainhand().isEmpty() && par3EntityPlayer.getHeldItemMainhand().getItem() instanceof ItemGunBase) {
					gun = (ItemGunBase) par3EntityPlayer.getHeldItemMainhand().getItem();
				}
				if(!par3EntityPlayer.getHeldItemOffhand().isEmpty() && par3EntityPlayer.getHeldItemOffhand().getItem() instanceof ItemGunBase) {
					gun = (ItemGunBase) par3EntityPlayer.getHeldItemOffhand().getItem();
				}
				float bbure = this.bure;
				var8.timemax = this.bullet_time_max;
					double xx11 = 0;
					double zz11 = 0;
					double yy11 = 0;
					float xz = 1.57F;
					if(!par3EntityPlayer.getHeldItemMainhand().isEmpty() && !par3EntityPlayer.getHeldItemOffhand().isEmpty()) {
						if(par3EntityPlayer.getHeldItemMainhand() == par1ItemStack) {
							xz = 1.57F;
						}else if(par3EntityPlayer.getHeldItemOffhand() == par1ItemStack){
							xz = -1.57F;
						}
					}else if (par3EntityPlayer.getActiveItemStack() == par1ItemStack) {//--生物
						xz = 0;
					}else if(par3EntityPlayer.getHeldItemMainhand() == par1ItemStack) {
						xz = 1.57F;
					}else if(par3EntityPlayer.getHeldItemOffhand() == par1ItemStack){
						xz = -1.57F;
					}
					double yy = fire_posy;
					if (par3EntityPlayer.isSneaking()) {//蹲下瞄准时发射子弹高度坐标变化
						yy = fire_posy_ads;
					}
					double zzz = fire_posz * Math.cos(Math.toRadians(-par3EntityPlayer.rotationPitch));
					xx11 -= MathHelper.sin(par3EntityPlayer.rotationYawHead * 0.01745329252F) * zzz;
					zz11 += MathHelper.cos(par3EntityPlayer.rotationYawHead * 0.01745329252F) * zzz;
					xx11 -= MathHelper.sin(par3EntityPlayer.rotationYawHead * 0.01745329252F + xz) * fire_posx;
					zz11 += MathHelper.cos(par3EntityPlayer.rotationYawHead * 0.01745329252F + xz) * fire_posx;
					yy11 = MathHelper.sqrt(zzz* zzz) * Math.tan(Math.toRadians(-par3EntityPlayer.rotationPitch)) * 1D;
					var8.setLocationAndAngles(par3EntityPlayer.posX + xx11, par3EntityPlayer.posY + yy + yy11, par3EntityPlayer.posZ + zz11, 
							par3EntityPlayer.rotationYaw,par3EntityPlayer.rotationPitch);
				//}
				var8.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch + this.fire_roteoffset_y, par3EntityPlayer.rotationYaw, 0.0F,
						this.speed, bbure);
				if (!par2World.isRemote) {
					par2World.spawnEntity(var8);
				}
				if(!this.true_mat8){
					if(muzzle_flash){
						EntityT_Flash flash = new EntityT_Flash(par2World, par3EntityPlayer);
						
						flash.gra = 0.03;
						flash.timemax = 1;
						flash.setModel(this.bulletf_model);
						flash.setTex(this.bulletf_tex);
						
						double offyyy = 0.1D;
						if (par3EntityPlayer.isSneaking()) {//蹲下瞄准
							offyyy = 0.3D;
						}
						flash.setLocationAndAngles(par3EntityPlayer.posX + xx11, par3EntityPlayer.posY + (yy - offyyy) + yy11, par3EntityPlayer.posZ + zz11, 
								par3EntityPlayer.rotationYaw,par3EntityPlayer.rotationPitch);
						flash.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch + this.fire_roteoffset_y, par3EntityPlayer.rotationYaw, 0.0F,
								0.1F, bbure);
						if (!par2World.isRemote) {
							par2World.spawnEntity(flash);
						}
					}
				}
				if(uselight){
					EntityT_Light light = new EntityT_Light(par2World, par3EntityPlayer);
					light.gra = 0.03;
					light.timemax = 1;
					if (!par2World.isRemote) {
						par2World.spawnEntity(light);
					}
				}
			}
		}
	}
	
    public int reload_type = 0;
    public int zandan = 0;
    public void getReload(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
    	//par2World.playSound((EntityPlayer)null, par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ,//弹壳
    	//		GVCSoundEvent.Reload, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		/*if(bullet_c){
			for(int p = 0; p < this.getMaxDamage(); ++p){
				EntityB_Cratridge var81 = new EntityB_Cratridge(par2World, (EntityLivingBase) par3EntityPlayer);
				var81.setModel(this.cartridge_model);
				var81.setTex(this.cartridge_tex);
				var81.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 0.0F,0.01F,5);
				if (!par2World.isRemote) {
					par2World.spawnEntity(var81);
				}
			}
		}*/
		if(this.reload_type == 1) {
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
			
			par1ItemStack.damageItem(li, par3EntityPlayer);
			InventoryPlayer playerInv = par3EntityPlayer.inventory;
			int z = this.getMaxDamage();
			for(int is = 0; is < playerInv.mainInventory.size(); ++is){
				ItemStack itemi = playerInv.mainInventory.get(is);
				if(itemi != null && itemi == this.func_185060_a(par3EntityPlayer) && !linfinity){
					for(int is2 = 0; is2 < this.getMaxDamage(); ++is2){
						if(itemi.getItemDamage() >= itemi.getMaxDamage()){
							playerInv.mainInventory.set(playerInv.currentItem, new ItemStack((Item)null));//playerInv.mainInventory.set(playerInv.currentItem, null);
							break;
						}else{
							if (!linfinity && !this.mugenmaga && !mod_GVCLib.cfg_debag_gun_mugen ) {
								itemi.damageItem(1, par3EntityPlayer);
							}
							par1ItemStack.damageItem(-1, par3EntityPlayer);
						}
						if(this.getDamage(par1ItemStack) == 0){
							break;
						}
					}
					if(this.getDamage(par1ItemStack) == 0){
						break;
					}
				}
			}
		}else if(this.reload_type == 2) {
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
			//ItemStack item = this.func_185060_a(par3EntityPlayer);
			/*if(this.magazine != null && this.magazine instanceof ItemMagazine) {
	    		ItemMagazine maga = (ItemMagazine) this.magazine;
	    		
	    	}*/
			//if(!par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItemStack(new ItemStack(this.magazine))){//2019/10/27
			if(!par3EntityPlayer.capabilities.isCreativeMode){//2019/10/27
			        //par1ItemStack.damageItem(li, par3EntityPlayer);
			       /* int zan = getMaxDamage() - zandan;
			        int lii = item.getCount() - zan;
			        if (!linfinity && !this.mugenmaga) {
			        	 if(lii <= 0) {
					        	setDamage(par1ItemStack, getMaxDamage() - item.getCount());
					        }else {
					        	setDamage(par1ItemStack, 0);
					        }
			        }else {
			        	setDamage(par1ItemStack, 0);
			        }
			        if (!linfinity && !this.mugenmaga) {
						item.shrink(zan);
						if (item.isEmpty())
		                {
							par3EntityPlayer.inventory.deleteStack(item);
		                }
					}*/
				for (int i = 0; i < par3EntityPlayer.inventory.getSizeInventory(); ++i) {
					ItemStack itemstack = par3EntityPlayer.inventory.getStackInSlot(i);
					if (this.func_185058_h_(itemstack)) {
						ItemStack item = itemstack;
						if (!item.isEmpty()) {
							int zan = getDamage(par1ItemStack) - 0;
							int lii = item.getCount() - zan;
							if (!linfinity && !this.mugenmaga && !mod_GVCLib.cfg_debag_gun_mugen ) {
								if (lii <= 0) {
									setDamage(par1ItemStack, getDamage(par1ItemStack) - item.getCount());
								} else {
									setDamage(par1ItemStack, 0);
								}
							} else {
								setDamage(par1ItemStack, 0);
							}
							if (!linfinity && !this.mugenmaga && !mod_GVCLib.cfg_debag_gun_mugen ) {
								item.shrink(zan);
								if (item.isEmpty()) {
									par3EntityPlayer.inventory.deleteStack(item);
								}
							}
							if(this.getDamage(par1ItemStack) == 0) {
								break;
							}
						}
					}
				}
			}
			
		}else {
			int li = getMaxDamage() - par1ItemStack.getItemDamage();
			boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
			ItemStack item = this.func_185060_a(par3EntityPlayer);
			//if(!par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItemStack(new ItemStack(this.magazine))){//2019/10/27
			if(!par3EntityPlayer.capabilities.isCreativeMode || !item.isEmpty()){//2019/10/27
			        par1ItemStack.damageItem(li, par3EntityPlayer);
					setDamage(par1ItemStack, -this.getMaxDamage());
					if (!linfinity && !this.mugenmaga && !mod_GVCLib.cfg_debag_gun_mugen ) {
						item.shrink(1);
						//System.out.println(String.format("input"));
						if (item.isEmpty())
		                {
							par3EntityPlayer.inventory.deleteStack(item);
		                }
					}
			}
		}
		zandan = 0;
    }
    
    public boolean onEntitySwing(EntityLivingBase entity, ItemStack par1ItemStack) {
    	World par2World = entity.world;
    	boolean linfinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, par1ItemStack) > 0;
    	int pluspower = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, par1ItemStack);
    	boolean left = false;
		boolean left_key = mod_GVCLib.proxy.leftclick();
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer par3EntityPlayer = (EntityPlayer) entity;
    		if(!this.gun_can_use_underbarrel)return false;
    		if(!par3EntityPlayer.getHeldItemOffhand().isEmpty())return false;
    		if(this.gun_can_get_underbarrel && (!this.underbarrel.isEmpty() && this.underbarrel.getItem() instanceof ItemGunBase)) {
    			ItemGunBase under = (ItemGunBase) this.underbarrel.getItem();
    			if(!under.gun_can_set_underbarrel)return false;
				if (!par2World.isRemote && this.underbarrel.getItemDamage() != under.getMaxDamage()) {
					{
						under.FireBullet(underbarrel, par2World, par3EntityPlayer);
					}
					if(this.underbarrel.getItemDamage() == under.getMaxDamage()) {
						par3EntityPlayer.getCooldownTracker().setCooldown(this, under.reloadtime);
					}else {
						par3EntityPlayer.getCooldownTracker().setCooldown(this, under.cocktime);
					}
						
					GVCInventoryItem inventory = new GVCInventoryItem(par3EntityPlayer.inventory, par1ItemStack);
					//GVCInventoryItem inventory = new GVCInventoryItem(par3EntityPlayer);
					inventory.setInventorySlotContents(4, this.underbarrel);

					ItemStack i1 = this.sight_item;
					if(!i1.isEmpty())inventory.setInventorySlotContents(1, i1);
					ItemStack i2 = this.light_item;
					if(!i2.isEmpty())inventory.setInventorySlotContents(2, i2);
					ItemStack i3 = this.supu_item;
					if(!i3.isEmpty())inventory.setInventorySlotContents(3, i3);
					ItemStack i5 = this.bullet_item;
					if(!i5.isEmpty())inventory.setInventorySlotContents(5, i5);
					//inventory.decrStackSize(4, 1);
					inventory.closeInventory(par3EntityPlayer);
				}
    		}else if(this.true_mat10){
        		ItemStack item = this.func_185060_a((EntityPlayer) par3EntityPlayer);
        		if (!par3EntityPlayer.capabilities.isCreativeMode
        				|| par3EntityPlayer.inventory.hasItemStack(new ItemStack(this.magazinegl))) {
        			if (!linfinity) {
						item.shrink(1);
						if (item.isEmpty())
		                {
							par3EntityPlayer.inventory.deleteStack(item);
		                }
					}
        			if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {
            			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -1F) * this.under_gl_recoil;
            		} else {
            			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -5F) * this.under_gl_recoil;
            		}
        			EntityB_GrenadeB var8 = new EntityB_GrenadeB(par2World, (EntityLivingBase) par3EntityPlayer);
        			var8.Bdamege = this.under_gl_power;
        			var8.gra = under_gl_gra;
        			var8.muteki = true;
        			var8.friend = par3EntityPlayer;
        			float bbure = this.bure;
        			if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {
        				bbure = this.bure / 5;
        			}else if(par3EntityPlayer.isSprinting()){
							bbure = this.bure*1.5F;
					}
        			var8.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw,
        					0.0F, this.under_gl_speed, this.under_gl_bure);
        			if (!par2World.isRemote) {
        				par2World.spawnEntity(var8);
        			}
        		}
        	}
    		if(this.true_mat11){
        		ItemStack item = this.func_185060_a((EntityPlayer) par3EntityPlayer);
        		if (!par3EntityPlayer.capabilities.isCreativeMode
        				|| par3EntityPlayer.inventory.hasItemStack(new ItemStack(this.magazinegl))) {
        			if (!linfinity) {
						item.shrink(1);
						if (item.isEmpty())
		                {
							par3EntityPlayer.inventory.deleteStack(item);
		                }
					}
        			if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {
            			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -1F) * this.under_sg_recoil;
            		} else {
            			par3EntityPlayer.rotationPitch += (itemRand.nextFloat() * -5F) * this.under_sg_recoil;
            		}
        			for(int p = 0; p < this.shotgun_pellet; ++p){
        				EntityB_Bullet var8 = new EntityB_Bullet(par2World, (EntityLivingBase) par3EntityPlayer);
        				var8.Bdamege = this.under_sg_power + pluspower;
        				var8.gra = under_sg_gra;
        				var8.muteki = true;
        				var8.friend = par3EntityPlayer;
        				float bbure = this.bure;
        				if (this.aim_time>(this.time_aim-2) && (this.time_aim-2)>=0) {
        					bbure = this.bure / 2;
        				}else if(par3EntityPlayer.isSprinting()){
							bbure = this.bure*1.5F;
						}
        				var8.setHeadingFromThrower(par3EntityPlayer, par3EntityPlayer.rotationPitch, par3EntityPlayer.rotationYaw, 
        						0.0F,this.under_sg_speed, this.under_sg_bure);
        				if (!par2World.isRemote) {
        					par2World.spawnEntity(var8);
        				}
        			}
        		}
        	}
    		
    		ItemStack itemstackl = par3EntityPlayer.getHeldItemOffhand();
    		if(itemstackl != null){
    			left = true;
    		}
    		par3EntityPlayer.addStat(StatList.getObjectUseStats(this));
    	}
		return false;
	}
    
    public void gunbase_recoil(ItemStack itemstack, World world, Entity entity, int i, boolean flag, int ii) {
    	//if(world.isRemote) 
    	{
    		NBTTagCompound nbt = itemstack.getTagCompound();
        	boolean recoiled = nbt.getBoolean("Recoiled");
    		int recoiledtime = nbt.getInteger("RecoiledTime");
    		{
    			if(recoiledtime >= ii){
    				nbt.setInteger("RecoiledTime", 0);
    				nbt.setBoolean("Recoiled", true);
    				/*if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
    					for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
    						GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(1310, entity.getEntityId(), true), playermp);
    						GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(1310, entity.getEntityId(), 0), playermp);
    					}
    				}*/
    				//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1310, entity.getEntityId(), true));
    				//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1310, entity.getEntityId(), 0));
    			}else
    			if(!recoiled){
    				++recoiledtime;
    				nbt.setInteger("RecoiledTime", recoiledtime);
    				/*if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
    					for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
    						GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(1311, entity.getEntityId(), recoiledtime), playermp);
    					}
    				}*/
    				//GVCLPacketHandler.INSTANCE2.sendToAll(new GVCLClientMessageKeyPressed(1311, entity.getEntityId(), recoiledtime));
    			}
    		}
    	}
    	
    }
    public void gunbase_cocking(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    	EntityPlayer entityplayer = (EntityPlayer)entity;
    	NBTTagCompound nbt = itemstack.getTagCompound();
    	{
			boolean cocking = nbt.getBoolean("Cocking");
			int cockingtime = nbt.getInteger("CockingTime");
			boolean gethand = false;
			if(itemstack == entityplayer.getHeldItemOffhand()) {
				gethand = true;
			}else if(flag){
				gethand = true;
			}
			if(!cocking && gethand){
				++cockingtime;
				nbt.setInteger("CockingTime", cockingtime);
				if(cockingtime == 2 && gethand && !this.semi){
					//world.playSoundAtEntity(entityplayer, this.soundcock, 1.0F, 1.0F);
					if(this.fires_cock != null) {
						world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
								this.fires_cock, SoundCategory.NEUTRAL, 1.0F, 1.0F);
					}else if(soundco != null){
						world.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
								SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, soundco)), SoundCategory.NEUTRAL, 1.0F, 1.0F);
					}
				}
				if(cockingtime > cocktime && gethand){
					nbt.setInteger("CockingTime", 0);
					nbt.setBoolean("Cocking", true);
				}
			}
		}
    }
    
    public void gunbase_reload(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    	EntityPlayer entityplayer = (EntityPlayer)entity;
    	NBTTagCompound nbt = itemstack.getTagCompound();
    	if(!itemstack.isEmpty()){
    		if (itemstack.getItemDamage() != itemstack.getMaxDamage())return;
			int D = Short.MAX_VALUE;
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
			if (itemstack.getItemDamage() == itemstack.getMaxDamage() && flag && entityplayer.inventory.hasItemStack(this.func_185060_a(entityplayer))) {
					if (entity != null && entity instanceof EntityPlayer) {
						if (itemstack == entityplayer.getHeldItemMainhand()) {
							int reloadti = nbt.getInteger("RloadTime");
							{
								gun.retime = reloadti;
								++reloadti;
								if (reloadti == gun.reloadtime) {
									gun.retime = reloadti = 0;
									nbt.setInteger("RloadTime", 0);
									{
									getReload(itemstack, world, entityplayer);
									}
									gun.resc = 0;
								}else{
									nbt.setInteger("RloadTime", reloadti);
								}
							}
						}
						if (itemstack == entityplayer.getHeldItemOffhand()) {
							int reloadti = nbt.getInteger("RloadTime");
							{
								gun.retime = reloadti;
								++reloadti;
								if (reloadti == gun.reloadtime) {
									gun.retime = reloadti = 0;
									nbt.setInteger("RloadTime", 0);
									{
									getReload(itemstack, world, entityplayer);
									}
									gun.resc = 0;
								}else{
									nbt.setInteger("RloadTime", reloadti);
								}
							}
						}
					}
			}
			if(!world.isRemote && this.retime == 2 && flag){
				if(this.fires_reload != null) {
					world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
							this.fires_reload, SoundCategory.NEUTRAL, 1.0F, 1.0F);
				}else if(soundre != null){
					world.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
							SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, soundre)), SoundCategory.NEUTRAL, 1.0F, 1.0F);
				}
				
			}
		}
    }
    
    public int lockt = 0;
    public void gunbase_lockon(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
    	if(itemstack.getItem() instanceof ItemGunBase) {
			ItemGunBase gun = (ItemGunBase) itemstack.getItem();
				GunBase_LockOn.load(gun, itemstack, world, entity, i, flag);
    	}
    }
    
    
    public IModelCustom am_model = null;
    public String am_tex = "gvclib:textures/model/ar.png";
    public boolean am_sight = false;
    public boolean am_zoomrender = false;
    public boolean am_zoomrendertex = false;
    public String am_ads_tex = "gvclib:textures/misc/scope.png";
    public float am_sight_x = 0;
    public float am_sight_y = 1.6F;
    public float am_sight_z = 1.8F;
    public float am_sightbase_x = 0;
    public float am_sightbase_y = 1.6F;
    public float am_sightbase_z = 1.8F;
    public float am_sightbase_z1 = 1.8F;
    
    
    public boolean rightmode = false;
    public float rightposx = -0.295F;
    public float rightposy = 1.3F;
    public float rightposz = 3.2F;
    public float roterightx = 0F;
    public float roterighty = 0F;
    public float roterightz = 0F;
    public int lighttime = 0;
    public IModelCustom am_light_model = null;
    public String am_light_tex = "gvclib:textures/model/ar.png";
    public boolean am_light = false;
    public float am_light_x = -0.295F;
    public float am_light_y = 1.3F;
    public float am_light_z = 3.2F;
    public float am_light_xr= 0F;
    public float am_light_yr= 0F;
    public float am_light_zr= 0F;
    public float[] am_colorlevel = new float[1024];
    public int am_light_kazu = 1;
    public int now_lightid = 0;
    
    
    
    
    public float supuposx = 0.0F;
    public float supuposy = 1.2F;
    public float supuposz = 5.8F;
    public IModelCustom am_supu_model = null;
    public String am_supu_tex = "gvclib:textures/model/ar.png";
    public boolean am_supu = false;
    public float am_supu_x = 0.0F;
    public float am_supu_y = 1.2F;
    public float am_supu_z = 5.8F;
    public float am_supu_xr = 0.0F;
    public float am_supu_yr = 0.0F;
    public float am_supu_zr = 0.0F;
    
    public float gripposx = 0.0F;
    public float gripposy = 0.85F;
    public float gripposz = 3.5F;
    public IModelCustom am_grip_model = null;
    public String am_grip_tex = "gvclib:textures/model/ar.png";
    public boolean am_grip = false;
    public float am_grip_x = 0.0F;
    public float am_grip_y = 0.85F;
    public float am_grip_z = 3.5F;
    public float am_grip_xr = 0.0F;
    public float am_grip_yr = 0.0F;
    public float am_grip_zr = 0.0F;
    
    public double am_grip_recoil = 0.4F;
    public double recoilbase = 0.0F;
    public float am_grip_bure = 0.8F;
    public float burebase = 0.0F;
    
    public boolean am_bullet = false;
    public int p_id = 0;
    public int p_level;
    public int p_time;
    public int p_idbase = 0;
    public float exlevelbase;
    public int poworbase;
 public int pelletbase;
    
    
    //public float burebase;
    
    /** アンダーバレルの銃が使えるかどうか */
    public boolean gun_can_use_underbarrel = false;
    /** 「親側が」modelを使用できるか*/
    public boolean gun_can_get_underbarrel = false;
    /** 「子側が」modelを使用できるか & アンダーバレルの銃として使えるかどうか*/
    public boolean gun_can_set_underbarrel = false;
    public float gun_underbarrel_x = 0.0F;
    public float gun_underbarrel_y = 1.2F;
    public float gun_underbarrel_z = 1.0F;
    
    public ItemStack sight_item = new ItemStack((Item)null);
    public ItemStack light_item = new ItemStack((Item)null);
    public ItemStack supu_item = new ItemStack((Item)null);
    public ItemStack bullet_item = new ItemStack((Item)null);
    
    public ItemStack underbarrel = new ItemStack((Item)null);
    
    public ItemStack grip_item = new ItemStack((Item)null);
    
    public int canuse_bullet_kazu;
    public String[] canuse_bullet = new String[64];
    
    
    public void Attachment(ItemGunBase gun, ItemStack itemstack, World world, EntityPlayer entityplayer, int i, boolean flag) {
    	//if (flag) 
    	{
    	//	GunBase_Attachment.load(gun, itemstack, world, entityplayer, i, flag);
    		GunBase_Attachment_new.load(gun, itemstack, world, entityplayer, i, flag);
    	}
    }
	
    public boolean recoilmotion = false;
    
    public int recoilmat0 = 4;
    //public int[] recoilmat0_time = new int[] {0, 30, 35, 50};
    public int[] recoilmat0_time = new int[200];
    public float[] recoilmat0_xpoint = new float[200];
    public float[] recoilmat0_ypoint = new float[200];
    public float[] recoilmat0_zpoint = new float[200];
    
    public float[] recoilmat0_xrote = new float[200];
    public float[] recoilmat0_yrote = new float[200];
    public float[] recoilmat0_zrote = new float[200];
    
    public float[] recoilmat0_xmove = new float[200];
    public float[] recoilmat0_ymove = new float[200];
    public float[] recoilmat0_zmove = new float[200];
    
    public int recoilmat1 = 4;
    public int[] recoilmat1_time = new int[200];
    public float[] recoilmat1_xpoint = new float[200];
    public float[] recoilmat1_ypoint = new float[200];
    public float[] recoilmat1_zpoint = new float[200];
    
    public float[] recoilmat1_xrote = new float[200];
    public float[] recoilmat1_yrote = new float[200];
    public float[] recoilmat1_zrote = new float[200];
    
    public float[] recoilmat1_xmove = new float[200];
    public float[] recoilmat1_ymove = new float[200];
    public float[] recoilmat1_zmove = new float[200];
    
    public int recoilmat2 = 4;
    public int[] recoilmat2_time = new int[200];
    public float[] recoilmat2_xpoint = new float[200];
    public float[] recoilmat2_ypoint = new float[200];
    public float[] recoilmat2_zpoint = new float[200];
    
    public float[] recoilmat2_xrote = new float[200];
    public float[] recoilmat2_yrote = new float[200];
    public float[] recoilmat2_zrote = new float[200];
    
    public float[] recoilmat2_xmove = new float[200];
    public float[] recoilmat2_ymove = new float[200];
    public float[] recoilmat2_zmove = new float[200];
    
    public int recoilmat3 = 4;
    public int[] recoilmat3_time = new int[200];
    public float[] recoilmat3_xpoint = new float[200];
    public float[] recoilmat3_ypoint = new float[200];
    public float[] recoilmat3_zpoint = new float[200];
    
    public float[] recoilmat3_xrote = new float[200];
    public float[] recoilmat3_yrote = new float[200];
    public float[] recoilmat3_zrote = new float[200];
    
    public float[] recoilmat3_xmove = new float[200];
    public float[] recoilmat3_ymove = new float[200];
    public float[] recoilmat3_zmove = new float[200];
    
    
    public int recoilmat22 = 4;
    public int[] recoilmat22_time = new int[200];
    public float[] recoilmat22_xpoint = new float[200];
    public float[] recoilmat22_ypoint = new float[200];
    public float[] recoilmat22_zpoint = new float[200];
    
    public float[] recoilmat22_xrote = new float[200];
    public float[] recoilmat22_yrote = new float[200];
    public float[] recoilmat22_zrote = new float[200];
    
    public float[] recoilmat22_xmove = new float[200];
    public float[] recoilmat22_ymove = new float[200];
    public float[] recoilmat22_zmove = new float[200];
    
    public int recoilmat24 = 4;
    public int[] recoilmat24_time = new int[200];
    public float[] recoilmat24_xpoint = new float[200];
    public float[] recoilmat24_ypoint = new float[200];
    public float[] recoilmat24_zpoint = new float[200];
    
    public float[] recoilmat24_xrote = new float[200];
    public float[] recoilmat24_yrote = new float[200];
    public float[] recoilmat24_zrote = new float[200];
    
    public float[] recoilmat24_xmove = new float[200];
    public float[] recoilmat24_ymove = new float[200];
    public float[] recoilmat24_zmove = new float[200];
    
    public int recoilmat25 = 4;
    public int[] recoilmat25_time = new int[200];
    public float[] recoilmat25_xpoint = new float[200];
    public float[] recoilmat25_ypoint = new float[200];
    public float[] recoilmat25_zpoint = new float[200];
    
    public float[] recoilmat25_xrote = new float[200];
    public float[] recoilmat25_yrote = new float[200];
    public float[] recoilmat25_zrote = new float[200];
    
    public float[] recoilmat25_xmove = new float[200];
    public float[] recoilmat25_ymove = new float[200];
    public float[] recoilmat25_zmove = new float[200];
    
    public int recoilmat311 = 4;
    public int[] recoilmat31_time = new int[200];
    public float[] recoilmat31_xpoint = new float[200];
    public float[] recoilmat31_ypoint = new float[200];
    public float[] recoilmat31_zpoint = new float[200];
    
    public float[] recoilmat31_xrote = new float[200];
    public float[] recoilmat31_yrote = new float[200];
    public float[] recoilmat31_zrote = new float[200];
    
    public float[] recoilmat31_xmove = new float[200];
    public float[] recoilmat31_ymove = new float[200];
    public float[] recoilmat31_zmove = new float[200];
    
    public int recoilmat32 = 4;
    public int[] recoilmat32_time = new int[200];
    public float[] recoilmat32_xpoint = new float[200];
    public float[] recoilmat32_ypoint = new float[200];
    public float[] recoilmat32_zpoint = new float[200];
    
    public float[] recoilmat32_xrote = new float[200];
    public float[] recoilmat32_yrote = new float[200];
    public float[] recoilmat32_zrote = new float[200];
    
    public float[] recoilmat32_xmove = new float[200];
    public float[] recoilmat32_ymove = new float[200];
    public float[] recoilmat32_zmove = new float[200];
    
    public int recoilmatlefthand = 5;
    public int[] recoilmatlefthand_time = new int[200];
    public float[] recoilmatlefthand_xpoint = new float[200];
    public float[] recoilmatlefthand_ypoint = new float[200];
    public float[] recoilmatlefthand_zpoint = new float[200];
    
    public float[] recoilmatlefthand_xrote = new float[200];
    public float[] recoilmatlefthand_yrote = new float[200];
    public float[] recoilmatlefthand_zrote = new float[200];
    
    public float[] recoilmatlefthand_xmove = new float[200];
    public float[] recoilmatlefthand_ymove = new float[200];
    public float[] recoilmatlefthand_zmove = new float[200];
    
    public int recoilmatrighthand = 4;
    public int[] recoilmatrighthand_time = new int[200];
    public float[] recoilmatrighthand_xpoint = new float[200];
    public float[] recoilmatrighthand_ypoint = new float[200];
    public float[] recoilmatrighthand_zpoint = new float[200];
    
    public float[] recoilmatrighthand_xrote = new float[200];
    public float[] recoilmatrighthand_yrote = new float[200];
    public float[] recoilmatrighthand_zrote = new float[200];
    
    public float[] recoilmatrighthand_xmove = new float[200];
    public float[] recoilmatrighthand_ymove = new float[200];
    public float[] recoilmatrighthand_zmove = new float[200];
    
}
