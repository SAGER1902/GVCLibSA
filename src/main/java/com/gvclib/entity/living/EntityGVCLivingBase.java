package gvclib.entity.living;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import gvclib.mod_GVCLib;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.IModelCustom;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.scoreboard.Team;

public abstract class EntityGVCLivingBase extends EntityCreature
{
    private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final DataParameter<Integer> mobmode = EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
	
	public boolean debag_test_client = false;
	
	public String tgfx = null;
    public boolean wrench_lock = false;//公共载具
	public int build_time = 0;
	public int finish_time = 1;
	
	public float rotation_turret;
	public float rotationp_turret;
	
	public boolean deploy_building = false;
	public String building_model=null;
	
	public boolean isbuilding = false;
	public boolean isbuilding_skill = false;
	public boolean building_connect = false;//建筑是否连接

	public Item[] sell_ra2 = new Item[256];//売り物
	public int[] choose_unit = new int[256];//选择单位生产
	public int[] buy_size2 = new int[256];//売り物の量
	
	public boolean zoom = true;
	/**trueの時、搭乗しているモブが透明になる*/
	public boolean ridding_invisible = false;
	/**trueの時、搭乗しているモブに対して敵対できない*/
	public boolean ridding_nottarget = false;
	
	public boolean ridding_f = false;
	public boolean ridding_t = false;
	/**ridding_view1_xなどで、砲塔の位置に依存して視点を動かす
	 * ridding_view1_x = ridding_view1_Zにすること*/
	public boolean ridding_view1_turretmode = false;
	public float ridding_view1_x = 0F;
	public float ridding_view1_y = 0F;
	public float ridding_view1_z = 0F;
	public float ridding_view_x = 0F;//thirdview
	public float ridding_view_y = -1F;//thirdview
	public float ridding_view_z = -2F;//thirdview
	@Deprecated
	public float ridding_view_gunner_x = 0F;
	@Deprecated
	public float ridding_view_gunner_y = 2F;
	@Deprecated
	public float ridding_view_gunner_z = 1F;
	/**　このモブが人型がどうか*/
	public boolean biped = false;
	public boolean ridding_canzoom = true;
	public float ridding_zoom = 2.0F;
	public float ridding_zoom_first = 2.0F;
	public boolean render_hud_icon = true;
	public String hud_icon = "gvclib:textures/hud/cross.png";
	public boolean render_hud_icon_hori = false;
	public String hud_icon_hori = "gvclib:textures/hud/cross.png";
	public boolean render_hud_scope = false;
	public String hud_icon_scope = "gvclib:textures/misc/scope.png";
	public boolean render_hud_scope_zoom = false;
	public String hud_icon_scope_zoom = "gvclib:textures/misc/scope.png";
	public boolean render_hud_icon_bomber = false;
	public String hud_icon_bomber = "gvclib:textures/hud/bomber.png";
	public boolean renderhud = false;
	public boolean render_rader = true;
	
	/**trueの時、このentityに乗っているモブにもダメージが入る*/
	public boolean riddng_opentop = true;
	/**trueの時、このentityに乗っているモブの手持ちをなくす*/
	public boolean riddng_freehand = true;
	public float ridding_damege = 0.25F;
	
	/**Zキーで降りられるようにする
	 * falseの時にZキーで降りるようになる*/
	public boolean ridding_sneakdismount = true;
	
	public int moveangle = 0;
	
	public float roo;
	public int deathTicks;
	public int type = 0;
	public Block flagbase;
	public Block flag;
	public Block flag2;
	public Block flag3;
	public Block flag4;
	public float hou_roy;
	public float hou_rop;
	public EntityLivingBase fri;
	public boolean target = false;
	
	public float throttle;
	public float throttle_up = 0;
	
	//public boolean rendertank = false;
	
	public float throttle_r;
	public float throttle_l;
	
	
	public boolean vehicle = false;
	
	/**trueのとき搭乗中のモブの銃が表示されず、MoveSのロックもない*/
	public boolean[] vehicle_ridding_nofire = new boolean[32];
	
	
	public float rotation;
	public float rotationp;
	
	/**乗っている人の横方向を固定する
	 * rotation_maxで角度制限*/
	public boolean ridding_rotation_lock = false;
	/**ridding_rotation_lockがtrueの時のみ*/
	public float rotation_max = 60;
	/**+方向、数値は-
	 * 最大仰角-90=90*/
	public float rotationp_max = -90;
	/**-方向、数値は+
	 * 最大俯角90=-90*/
	public float rotationp_min = 90;
	
	/**Pitch方向の角度にoffset(F11 + 10 + angle_offset)*/
	public float angle_offset = 0;
	
	public int angletime;
	/***横方向(rotationYaw)の遅れを作る*/
	public float rote;
	/***縦方向(rotationPitch)の遅れを作る*/
	public float rotep;
	
	/***GVC専用rotationPitch
	 * なぜかrotationPitchがupdateで0に戻るのでこれをカウンタとして使う
	 * rotationPitch = rotationPitch_GVC + XXX*/
	public float rotationPitch_gvc;
	
	public int ammo1;
	public int ammo2;
	public int ammo3;
	public int ammo4;
	public int ammo5;
	public int cooltime;
	public int cooltime2;
	public int cooltime3;
	public int cooltime4;
	public int cooltime5;
	public int cooltime6;
	public int magazine;
	public int magazine2;
	public int magazine3;
	public int magazine4;
	public boolean counter1 = false;
	public boolean counter2 = false;
	public boolean counter3 = false;
	public boolean counter4 = false;
	/**自動でカウントされる
	 * max200*/
	public int gun_count1 = 0;
	/**自動でカウントされる
	 * max200*/
	public int gun_count2 = 0;
	/**自動でカウントされる
	 * max200*/
	public int gun_count3 = 0;
	/**自動でカウントされる
	 * max200*/
	public int gun_count4 = 0;
	/**自動でカウントされる
	 * max200*/
	public int gun_count5 = 0;
	/**自動でカウントされる
	 * max200*/
	public int gun_count6 = 0;
	public int countlimit1 = 0;
	public int countlimit2 = 0;
	public int countlimit3 = 0;
	public int countlimit4 = 0;
	public int reload1 = 0;
	public int reload2 = 0;
	public int reload3 = 0;
	public int reload4 = 0;
	public int reload5 = 0;
	public int reload6 = 0;
	public int reload_time1;
	public int reload_time2;
	public int reload_time3;
	public int reload_time4;
	public int cooldown_base = 0;
	public float movespeed_base = 1.0F;
	
	/**自動でカウントされるEntityVehicleBaseで使用
	 * max200*/
	public int gun_auto_count_1 = 0;
	/**自動でカウントされるEntityVehicleBaseで使用
	 * max200*/
	public int gun_auto_count_2 = 0;
	/**自動でカウントされるEntityVehicleBaseで使用
	 * max200*/
	public int gun_auto_count_3 = 0;
	/**自動でカウントされるEntityVehicleBaseで使用
	 * max200*/
	public int gun_auto_count_4 = 0;
	
	
	public boolean server1 = false;
	public boolean server2 = false;
	public boolean serverspace = false;
	public boolean serverx = false;
	public boolean serverc = false;
	public boolean serverv = false;
	public boolean serverg = false;
	public boolean serverz = false;
	public boolean serverh = false;
	public boolean serverf = false;
	public boolean serverb = false;
	
	public boolean servertab = false;
	
	public boolean render_hud_scaleup_text1 = false;
	public boolean render_hud_scaleup_text2 = false;
	public boolean render_hud_scaleup_text3 = false;
	public boolean render_hud_scaleup_text4 = false;
	
	public String render_hud_information_1 = "";
	public String render_hud_information_2 = "";
	public String render_hud_information_3 = "";
	public String render_hud_information_4 = "";
	public String render_hud_information_5 = "";
	public String render_hud_information_6 = "";
	
	/**trueのときlock表示*/
	public boolean aarader = false;
	public boolean milock = false;
	/**trueのときlock表示*/
	public boolean asrader = false;
	
	/**ミサイル用のターゲット*/
	public EntityLivingBase mitarget = null;
	public String mitargetname = null;
	public int mitargettime;
	
	
	public double thpower;
	public double th;
	public float thmax;
	public float thmin;
	public float thmaxa;
	public float thmina;
	
	/**プロペラの回転*/
	public double thpera = 0;
	/**機体の傾き*/
	public float throte = 0;
	
	public int soundtick = 0;
	
	public boolean settnt;
	public int stoptime;
	
	/**ターゲットしているentity*/
	public EntityLivingBase targetentity = null;
	public EntityLivingBase targetentity2 = null;
	public EntityLivingBase targetentity3 = null;
	public EntityLivingBase targetentity4 = null;
	public EntityLivingBase targetentity5 = null;
	
	public double targetentity_lastposX;
	public double targetentity_lastposY;
	public double targetentity_lastposZ;

	public boolean combattask = false;
	
	public float overlayhight = 1.0F;
	public float overlayhight_3 = 1.0F;
	public float overlaywidth_3 = 1.0F;
	
	public float rotation_1;
	public float rotationp_1;
	public float rotation_2;
	public float rotationp_2;
	public float rotation_3;
	public float rotationp_3;
	public float rotation_4;
	public float rotationp_4;
	public float rotation_5;
	public float rotationp_5;
	public float rotation_6;
	public float rotationp_6;
	public int ammo_1;
	public int ammo_2;
	public int ammo_3;
	public int ammo_4;
	public int ammo_5;
	public int ammo_6;
	public int cooltime_1;
	public int cooltime_2;
	public int cooltime_3;
	public int cooltime_4;
	public int cooltime_5;
	public int cooltime_6;
	public boolean combattask_2 = false;
	public boolean combattask_3 = false;
	public boolean combattask_4 = false;
	public boolean combattask_5 = false;
	public boolean combattask_6 = false;
	
	/**stay中にカウントされる
	 * */
	public int ai_time;
	public double ai_move_x;
	public double ai_move_y;
	public double ai_move_z;
	
	
	public String w1namebase = "";
	public String w1name = "";
	public String w2name = "";
	public String w3name = "";
	public String w4name = "";
	
	//public IModelCustom model = null;
	public boolean sneak;
	public boolean firetrue = false;
	
	public int startuptime = 0;
	
	//0524
	public double pathx = 0;
	public double pathy = 0;
	public double pathz = 0;
	public int movetime = 0;
	
	//0711
	public int reloadsoundset1;
	public int reloadsoundset2;
	public int reloadsoundset3;
	public int reloadsoundset4;
	public int reloadsoundset5;
	public SoundEvent reloadsound1;
	public SoundEvent reloadsound2;
	public SoundEvent reloadsound3;
	public SoundEvent reloadsound4;
	public SoundEvent reloadsound5;
	
	public int w1cycle;
	public int w2cycle;
	public int w3cycle;
	public int w4cycle;
	public int w5cycle;
	
	public int w1barst;
	public int w2barst;
	public int w3barst;
	public int w4barst;
	public int w5barst;
	
	public int squad_followrange = 4;
	
	//19/11/26
	/**this.getcanDespawn() == 0時にカウントさせる*/
	public int despawn_count = 0;
	
	//19/12/11
	public boolean difficulty_adjustment = true;
	
	//21/1/12
	public boolean medic = false;
	public boolean wrench = false;
	//21/2/02
	/**　AI等での弾速が4Fを超えられるように*/
	public boolean ai_bullet_speed_over4f = false;
	
	/**AI_EntityWeapon.getTargetEntityで使用
	 *視界内に入ってから発見するまでのカウント*/
	public int find_enemy_count = 0;
	/**AI_EntityWeapon.getTargetEntityで使用
	 *find_enemy_countがこの値を超えたら発見
	 *初期値100*/
	public int find_enemy_maxcount = 100;
	
	
	/**trueで射撃時無敵無視
	 * 何故かAI_EntityWeaponでtrueになってたため増設*/
	public boolean fire_muteki = false;
	
	
	protected static final DataParameter<String> Model = 
    		EntityDataManager.<String>createKey(EntityGVCLivingBase.class, DataSerializers.STRING);
    protected static final DataParameter<String> Tex = 
    		EntityDataManager.<String>createKey(EntityGVCLivingBase.class, DataSerializers.STRING);
    public IModelCustom model = null;
    public ResourceLocation tex;
	
	private static final DataParameter<Integer> CanDespawn = EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MoveT = EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MoveX = EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MoveY = EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> MoveZ = EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
	
    private static final DataParameter<Integer> arm_rid = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> arm_lid = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> arm_sid = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> arm_aid = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    private static final DataParameter<Boolean> boost = 
    		EntityDataManager.<Boolean>createKey(EntityGVCLivingBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> boosttime = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    private static final DataParameter<Integer> remain_r = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> remain_l = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> remain_s = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> remain_a = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    private static final DataParameter<Integer> we_chenge = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ftmode = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    
    private static final DataParameter<Integer> aitype = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> aitype2 = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> aitype3 = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    private static final DataParameter<Integer> targetid = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    private static final DataParameter<Integer> masterid = 
    		EntityDataManager.<Integer>createKey(EntityGVCLivingBase.class, DataSerializers.VARINT);
    
    
    protected static final DataParameter<String> nameid = 
    		EntityDataManager.<String>createKey(EntityGVCLivingBase.class, DataSerializers.STRING);
	protected static final DataParameter<String> squad = 
    		EntityDataManager.<String>createKey(EntityGVCLivingBase.class, DataSerializers.STRING);
    
	private static final DataParameter<Boolean> attacktask = 
    		EntityDataManager.<Boolean>createKey(EntityGVCLivingBase.class, DataSerializers.BOOLEAN);
	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID 
	= EntityDataManager.<Optional<UUID>>createKey(EntityGVCLivingBase.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	private static final DataParameter<Boolean> vehiclelock = 
    		EntityDataManager.<Boolean>createKey(EntityGVCLivingBase.class, DataSerializers.BOOLEAN);
	
	/**GVC用スニーク*/
	private static final DataParameter<Boolean> sneak_gvc = 
    		EntityDataManager.<Boolean>createKey(EntityGVCLivingBase.class, DataSerializers.BOOLEAN);
	
	
	protected static final DataParameter<Optional<UUID>> Target_UUID 
	= EntityDataManager.<Optional<UUID>>createKey(EntityGVCLivingBase.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	
    public EntityGVCLivingBase(World worldIn)
    {
        super(worldIn);
        //Chunk chunk = this.chu
        this.posX = MathHelper.floor(this.chunkCoordX / 16.0D);
        this.posZ = MathHelper.floor(this.chunkCoordZ / 16.0D);
//        this.ignoreFrustumCheck=true;
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        boolean re = true;
        return re;
	}
	
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength();

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * mod_GVCLib.cfg_entity_render_range * 10;
       // d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < 65536.0D;
    }
    
    
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        compound.setString("Model", this.getModel());
        compound.setString("Tex", this.getTex());
        
        compound.setInteger("mobmode", getMobMode());
        
        compound.setInteger("CanDespawn", getcanDespawn());
        compound.setInteger("MoveT", getMoveT());
        compound.setInteger("MoveX", getMoveX());
        compound.setInteger("MoveY", getMoveY());
        compound.setInteger("MoveZ", getMoveZ());
        
        compound.setInteger("arm_id_r", getArmID_R());
        compound.setInteger("arm_id_l", getArmID_L());
        compound.setInteger("arm_id_s", getArmID_S());
        compound.setInteger("arm_id_a", getArmID_A());
        
        compound.setBoolean("Boost", getBoost());
        compound.setInteger("Boosttime", getBoosttime());
        
        compound.setInteger("remain_r", getRemain_R());
        compound.setInteger("remain_l", getRemain_L());
        compound.setInteger("remain_s", getRemain_S());
        compound.setInteger("remain_a", getRemain_A());
        
        compound.setInteger("we_chenge", getWeaponChange());
        compound.setInteger("ftmode", getFTMode());
        
        compound.setInteger("aitype", getAIType());
        compound.setInteger("aitype2", getAIType2());
        compound.setInteger("aitype3", getAIType3());
        
       compound.setInteger("targetid", getTargetID());
        compound.setInteger("masterid", getMasterID());
        
        compound.setString("nameid", this.getnameid());
        compound.setString("squad", this.getnameid());
        
        compound.setBoolean("attacktask", this.getattacktask());
        if (this.getOwnerId() == null)
        {
            compound.setString("OwnerUUID", "");
        }
        else
        {
            compound.setString("OwnerUUID", this.getOwnerId().toString());
        }
        
        compound.setBoolean("vehiclelock", getVehicleLock());
        compound.setBoolean("sneak_gvc", getSneak());
    }
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        {
        	this.setModel(compound.getString("Model"));
        }
        {
        	this.setTex(compound.getString("Tex"));
        }
        
        this.setMobMode(compound.getInteger("mobmode"));
        this.setcanDespawn(compound.getInteger("CanDespawn"));
        this.setMoveT(compound.getInteger("MoveT"));
        this.setMoveX(compound.getInteger("MoveX"));
        this.setMoveY(compound.getInteger("MoveY"));
        this.setMoveZ(compound.getInteger("MoveZ"));
        
        this.setArmID_R(compound.getInteger("arm_id_r"));
        this.setArmID_L(compound.getInteger("arm_id_l"));
        this.setArmID_S(compound.getInteger("arm_id_s"));
        this.setArmID_A(compound.getInteger("arm_id_a"));
        this.setBoost(compound.getBoolean("Boost"));
        this.setBoosttime(compound.getInteger("Boosttime"));
        
        this.setRemain_R(compound.getInteger("remain_r"));
        this.setRemain_L(compound.getInteger("remain_l"));
        this.setRemain_S(compound.getInteger("remain_s"));
        this.setRemain_A(compound.getInteger("remain_a"));
        
        this.setWeaponChange(compound.getInteger("we_chenge"));
        this.setFTMode(compound.getInteger("ftmode"));
        
        this.setAIType(compound.getInteger("aitype"));
        this.setAIType2(compound.getInteger("aitype2"));
        this.setAIType3(compound.getInteger("aitype3"));
        
        this.setTargetID(compound.getInteger("targetid"));
        this.setMasterID(compound.getInteger("masterid"));
        
        this.setnameid(compound.getString("nameid"));
    	this.setnameid(compound.getString("squad"));
    	
    	this.setattacktask(compound.getBoolean("attacktask"));
    	String s;

        if (compound.hasKey("OwnerUUID", 8))
        {
            s = compound.getString("OwnerUUID");
        }
        else
        {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty())
        {
            try
            {
                this.setOwnerId(UUID.fromString(s));
            }
            catch (Throwable var4)
            {
            }
        }
        this.setVehicleLock(compound.getBoolean("vehiclelock"));
        this.setSneak(compound.getBoolean("sneak_gvc"));
    }
    
   
    
    protected void entityInit()
    {
    	super.entityInit();
    	this.dataManager.register(Model, new String("gvclib:textures/entity/BulletNormal.obj"));
        this.dataManager.register(Tex, new String("gvclib:textures/entity/BulletNormal.png"));
    	
    	this.dataManager.register(mobmode, Integer.valueOf(0));
    	this.dataManager.register(CanDespawn, Integer.valueOf(0));
        this.dataManager.register(MoveT, Integer.valueOf(0));
        this.dataManager.register(MoveX, Integer.valueOf(0));
        this.dataManager.register(MoveY, Integer.valueOf(0));
        this.dataManager.register(MoveZ, Integer.valueOf(0));
        
        this.dataManager.register(arm_rid, Integer.valueOf(0));
        this.dataManager.register(arm_lid, Integer.valueOf(0));
        this.dataManager.register(arm_sid, Integer.valueOf(0));
        this.dataManager.register(arm_aid, Integer.valueOf(0));
        
        this.dataManager.register(boost, Boolean.valueOf(false));
        this.dataManager.register(boosttime, Integer.valueOf(0));
        
        this.dataManager.register(remain_r, Integer.valueOf(0));
        this.dataManager.register(remain_l, Integer.valueOf(0));
        this.dataManager.register(remain_s, Integer.valueOf(0));
        this.dataManager.register(remain_a, Integer.valueOf(0));
        
        this.dataManager.register(we_chenge, Integer.valueOf(0));
        this.dataManager.register(ftmode, Integer.valueOf(0));
        
        this.dataManager.register(aitype, Integer.valueOf(0));
        this.dataManager.register(aitype2, Integer.valueOf(0));
        this.dataManager.register(aitype3, Integer.valueOf(0));
        
        this.dataManager.register(targetid, Integer.valueOf(0));
        this.dataManager.register(masterid, Integer.valueOf(0));
        
        this.dataManager.register(nameid, new String("0"));
        this.dataManager.register(squad, new String("0"));
        
        this.dataManager.register(attacktask, Boolean.valueOf(false));
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
        
        this.dataManager.register(Target_UUID, Optional.absent());
        
        this.dataManager.register(vehiclelock, Boolean.valueOf(false));
        this.dataManager.register(sneak_gvc, Boolean.valueOf(false));
    }
    
    public String getModel() {
		return ((this.dataManager.get(Model)));
	}
	public void setModel(String s) {
		this.dataManager.set(Model, String.valueOf(new String(s)));
	}
	public String getTex() {
		return ((this.dataManager.get(Tex)));
	}
	public void setTex(String s) {
		this.dataManager.set(Tex, String.valueOf(new String(s)));
	}
    
	
	/**　現在はアクティブ状態かの判定にのみ使用*/
    public int getMobMode() {
    	//	return (((Integer) this.dataWatcher.get(SPAWN_X)).byteValue());
    		return ((this.dataManager.get(mobmode)).intValue());
    	}

    /**　現在はアクティブ状態かの判定にのみ使用*/
    public void setMobMode(int stack) {
    	//	this.dataWatcher.set(SPAWN_X, Integer.valueOf((byte) (stack)));
    		this.dataManager.set(mobmode, Integer.valueOf(stack));
    }
    
    public int getcanDespawn() {
    	//	return (((Integer) this.dataWatcher.get(SPAWN_X)).byteValue());
    		return ((this.dataManager.get(CanDespawn)).intValue());
    	}

    public void setcanDespawn(int stack) {
    	//	this.dataWatcher.set(SPAWN_X, Integer.valueOf((byte) (stack)));
    		this.dataManager.set(CanDespawn, Integer.valueOf(stack));
    }
    	
    /**通常モブの場合、1＝＝特定場所に移動
     * ペットの場合
     * 0==追従(follow)
     * 1==自由行動(free)
     * 2==特定場所に移動(point_move)
     * 3==待機(wait)
     * 5==特定場所に射撃(attack)
     * */
    public int getMoveT() {
    		return ((this.dataManager.get(MoveT)).intValue());
    	}
    public void setMoveT(int stack) {
    	this.dataManager.set(MoveT, Integer.valueOf(stack));
    }
    public int getMoveX() {
		return ((this.dataManager.get(MoveX)).intValue());
	}
    public void setMoveX(int stack) {
	this.dataManager.set(MoveX, Integer.valueOf(stack));
    }
    public int getMoveY() {
	return ((this.dataManager.get(MoveY)).intValue());
    }
    public void setMoveY(int stack) {
    this.dataManager.set(MoveY, Integer.valueOf(stack));
    }
    public int getMoveZ() {
	return ((this.dataManager.get(MoveZ)).intValue());
    }
    public void setMoveZ(int stack) {
    this.dataManager.set(MoveZ, Integer.valueOf(stack));
    }
    
	public int getArmID_R() {
    	return ((this.dataManager.get(arm_rid)).intValue());
    }
    public void setArmID_R(int stack) {
    	this.dataManager.set(arm_rid, Integer.valueOf(stack));
    }
    public int getArmID_L() {
    	return ((this.dataManager.get(arm_lid)).intValue());
    }
    public void setArmID_L(int stack) {
    	this.dataManager.set(arm_lid, Integer.valueOf(stack));
    }
    public int getArmID_S() {
    	return ((this.dataManager.get(arm_sid)).intValue());
    }
    public void setArmID_S(int stack) {
    	this.dataManager.set(arm_sid, Integer.valueOf(stack));
    }
    public int getArmID_A() {
    	return ((this.dataManager.get(arm_aid)).intValue());
    }
    public void setArmID_A(int stack) {
    	this.dataManager.set(arm_aid, Integer.valueOf(stack));
    }
    /**現在はレーザーサイトの表示に使用中*/
    public boolean getBoost() {
		return ((this.dataManager.get(boost)).booleanValue());
	}
    /**現在はレーザーサイトの表示に使用中*/
	public void setBoost(boolean stack) {
		this.dataManager.set(boost, Boolean.valueOf(stack));
	}
	
	/**03/24ナイトビジョンに使用中1=on/0=off*/
	public int getBoosttime() {
    	return ((this.dataManager.get(boosttime)).intValue());
    }
	/**03/24ナイトビジョンに使用中1=on/0=off*/
    public void setBoosttime(int stack) {
    	this.dataManager.set(boosttime, Integer.valueOf(stack));
    }
    
    
    public int getRemain_R() {
    	return ((this.dataManager.get(remain_r)).intValue());
    }
    public void setRemain_R(int stack) {
    	this.dataManager.set(remain_r, Integer.valueOf(stack));
    }
    public int getRemain_L() {
    	return ((this.dataManager.get(remain_l)).intValue());
    }
    public void setRemain_L(int stack) {
    	this.dataManager.set(remain_l, Integer.valueOf(stack));
    }
    public int getRemain_S() {
    	return ((this.dataManager.get(remain_s)).intValue());
    }
    public void setRemain_S(int stack) {
    	this.dataManager.set(remain_s, Integer.valueOf(stack));
    }
    public int getRemain_A() {
    	return ((this.dataManager.get(remain_a)).intValue());
    }
    public void setRemain_A(int stack) {
    	this.dataManager.set(remain_a, Integer.valueOf(stack));
    }
    
   
    public int getWeaponChange() {
    	return ((this.dataManager.get(we_chenge)).intValue());
    }
    public void setWeaponChange(int stack) {
    	this.dataManager.set(we_chenge, Integer.valueOf(stack));
    }
    /**　モブの場合、1でattack_range内(射撃が可能)*/
    public int getFTMode() {
    	return ((this.dataManager.get(ftmode)).intValue());
    }
    /**　モブの場合、1でattack_range内(射撃が可能)*/
    public void setFTMode(int stack) {
    	this.dataManager.set(ftmode, Integer.valueOf(stack));
    }
    
    /**aitypetime::経過時間
     * aitypemax::変更する時間(100)
     * aitypemaxset::最大変化する数(6)*/
    public int getAIType() {
    	return ((this.dataManager.get(aitype)).intValue());
    }
    /**aitypetime::経過時間
     * aitypemax::変更する時間(100)
     * aitypemaxset::最大変化する数(6)*/
    public void setAIType(int stack) {
    	this.dataManager.set(aitype, Integer.valueOf(stack));
    }
    /**aitypetime2::経過時間
     * aitypemax2::変更する時間(120)
     * aitypemaxset2::最大変化する数(8)*/
    public int getAIType2() {
    	return ((this.dataManager.get(aitype2)).intValue());
    }
    /**aitypetime2::経過時間
     * aitypemax2::変更する時間(120)
     * aitypemaxset2::最大変化する数(8)*/
    public void setAIType2(int stack) {
    	this.dataManager.set(aitype2, Integer.valueOf(stack));
    }
    /*** 自動では変更されない*/
    public int getAIType3() {
    	return ((this.dataManager.get(aitype3)).intValue());
    }
    /*** 自動では変更されない*/
    public void setAIType3(int stack) {
    	this.dataManager.set(aitype3, Integer.valueOf(stack));
    }
    
    /**現在は未使用?*/
    public int getTargetID() {
    	return ((this.dataManager.get(targetid)).intValue());
    }
    public void setTargetID(int stack) {
    	this.dataManager.set(targetid, Integer.valueOf(stack));
    }
    /**現在は乗り物の自然スポーンの判定に使用
     * 1で自然スポーンし、アイテムをドロップする*/
    public int getMasterID() {
    	return ((this.dataManager.get(masterid)).intValue());
    }
    public void setMasterID(int stack) {
    	this.dataManager.set(masterid, Integer.valueOf(stack));
    }
    
    public String getsquad() {
		return ((this.dataManager.get(squad)));
	}
	public void setsquad(String s) {
		this.dataManager.set(squad, String.valueOf(new String(s)));
	}
	
	public String getnameid() {
		return ((this.dataManager.get(nameid)));
	}
	public void setnameid(String s) {
		this.dataManager.set(nameid, String.valueOf(new String(s)));
	}
	/**　交戦状態*/
	public boolean getattacktask() {
		return ((this.dataManager.get(attacktask)).booleanValue());
	}
	/**　交戦状態*/
	public void setattacktask(boolean stack) {
		this.dataManager.set(attacktask, Boolean.valueOf(stack));
	}
	
	public void setTamedBy(EntityPlayer player)//改动EntityLivingBase
    {
        this.setOwnerId(player.getUniqueID());
    }
	@Nullable
    public UUID getOwnerId()
    {
        return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }

    public boolean getVehicleLock() {
		return ((this.dataManager.get(vehiclelock)).booleanValue());
	}
	public void setVehicleLock(boolean stack) {
		this.dataManager.set(vehiclelock, Boolean.valueOf(stack));
	}
	
	/**gvc用スニーク判定*/
	public boolean getSneak() {
		return ((this.dataManager.get(sneak_gvc)).booleanValue());
	}
	/**gvc用スニーク判定*/
	public void setSneak(boolean stack) {
		this.dataManager.set(sneak_gvc, Boolean.valueOf(stack));
	}
    
	/**　クールダウン系の絶対値
	 *カウンターに使うな*/
	public int getCoolDownBase() {
		return this.cooldown_base;
	}
	/**　クールダウン系の絶対値
	 *カウンターに使うな*/
	public void setCoolDownBase(int i) {
		this.cooldown_base = i;
	}
	
	/**　移動速度倍率系の絶対値
	 *カウンターに使うな*/
	public float getMoveSpeedBase() {
		return this.movespeed_base;
	}
	/**　移動速度倍率系の絶対値
	 *カウンターに使うな*/
	public void setMoveSpeedBase(float i) {
		this.movespeed_base = i;
	}
	
	
	@Nullable
    public UUID getTargetId()
    {
        return (UUID)((Optional)this.dataManager.get(Target_UUID)).orNull();
    }

    public void setTargetId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(Target_UUID, Optional.fromNullable(p_184754_1_));
    }
    @Nullable
    public EntityLivingBase getTargetMob()
    {
        try
        {
            UUID uuid = this.getTargetId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }
	
	
    @Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    public boolean isOwner(EntityLivingBase entityIn)
    {
        return entityIn == this.getOwner();
    }
    
    
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);

        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());

        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
        {
            Calendar calendar = this.world.getCurrentDate();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }

        return livingdata;
    }
    
    public boolean CanAttack(Entity entity){
    	return false;
    }
    
    public boolean CanAlly(Entity entity){
    	return false;
    }
    
    public void fall(float distance, float damageMultiplier)
    {
    }

    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();
        return entity instanceof EntityPlayer;
    }
    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
    }
    
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Nullable
    protected SoundEvent getHurtSound()
    {
        return null;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
        return null;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 120;
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
    	return true;
    }
    
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
		Entity entity;
    	entity = source.getImmediateSource();
    	{
        	return super.attackEntityFrom(source, par2);
        }
    }
    
    
    @SideOnly(Side.CLIENT)
    public int getClient_Gun_Count(EntityGVCLivingBase entity)
    {
        return entity.gun_count1;
    }
    
    @SideOnly(Side.CLIENT)
    public int getClient_aitypetime(EntityGVCLivingBase entity)
    {
        return entity.aitypetime;
    }
    
    @SideOnly(Side.CLIENT)
    public int getClient_aitypetime2(EntityGVCLivingBase entity)
    {
        return entity.aitypetime2;
    }
    
    @SideOnly(Side.CLIENT)
    public int getClient_aitypetime3(EntityGVCLivingBase entity)
    {
        return entity.aitypetime3;
    }
    
    public int ontick = 0;
    public int ontick10sec = 0;
    public int ontick100sec = 0;
    public int aitypetime = 0;
    /**初期100*/
    public int aitypemax = 100;
    /**初期6*/
    public int aitypemaxset = 6;
    public int aitypetime2 = 0;
    /**初期120*/
    public int aitypemax2 = 120;
    /**初期6*/
    public int aitypemaxset2 = 6;
    public int aitypetime3 = 0;
    public int aitypemax3 = 140;
    public int aitypemaxset3 = 6;
    
    /**entity.getEntitySenses().canSee(entity1) == false時に次に動けるようになるまでのカウント*/
    public int noflag = 0;
    
    /**entity.getEntitySenses().canSee(entity1) == true時にカウントし、一定値以上で発砲可能*/
    public int discover_count = 0;
    
    public boolean aitype_auto = true;
    public boolean aitype2_auto = true;
    
    
    public void AIType1_Change() {
    	
    }
    public void AIType2_Change() {
    	
    }
    
    public void onUpdate() {
    	super.onUpdate();
    	if (this.getRidingEntity() instanceof EntityGVCLivingBase && this.getRidingEntity() != null) {// 1
    		EntityGVCLivingBase ve = (EntityGVCLivingBase) this.getRidingEntity();
    		if(ve.riddng_freehand && !ve.biped && this.biped) {
    			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).EMPTY);
        		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, this.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).EMPTY);
    		}
    	}//1
    	
		if(this.getHealth()>0){
		List<Entity> llist = this.world.getEntitiesWithinAABBExcludingEntity(this,this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(50.0D));
		//附近的实体
		if (llist != null) {
			for (int lj = 0; lj < llist.size(); lj++) {
				Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 instanceof EntityPlayer) {
						boolean flag = this.getEntitySenses().canSee(entity1);
						EntityPlayer doll = (EntityPlayer) entity1;
						Team team = doll.getTeam();
						if(this.isOwner(doll)){
							if(team != this.getTeam() && team != null){
							this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), team.getName());
							}
						}
					}
				}
			}
		}
    	
    	{
    		if(cooltime < 800)++cooltime;
    		if(cooltime2 < 800)++cooltime2;
    		if(cooltime3 < 800)++cooltime3;
    		if(cooltime4 < 800)++cooltime4;
    		if(cooltime5 < 800)++cooltime5;
    		if(cooltime6 < 800)++cooltime6;
    		if(startuptime < 1300)++startuptime;
			
			if(gun_count1 < 200)++gun_count1;
			if(gun_count2 < 200)++gun_count2;
			if(gun_count3 < 200)++gun_count3;
			if(gun_count4 < 200)++gun_count4;
			if(gun_count5 < 200)++gun_count5;
			if(gun_count6 < 200)++gun_count6;
		}
    	
    	 
    	 ++ontick;
    	 
    	if(ontick >= 20){
    		ontick = 0;
    	}
    	++ontick10sec;
    	if(ontick10sec >= 200){
    		ontick10sec = 0;
    	}
    	++ontick100sec;
    	if(ontick100sec >= 2000){
    		ontick100sec = 0;
    	}
    	if(ai_time > 0) {
    		--ai_time;
    	}
    	
    	firetrue = false;
    	if(this.aitype_auto) {
    		if(!this.world.isRemote)++aitypetime;
        	//++aitypetime;
        	if(aitypetime >= aitypemax){
        		int ra = this.world.rand.nextInt(aitypemaxset);
        		if(this.getAIType() == ra) {
        			for(int xxx = 0; xxx < 10; ++xxx) {
        				ra = this.world.rand.nextInt(aitypemaxset);
        				if(this.getAIType() != ra) {
        					break;
        				}
        			}
        		}
        		this.setAIType(ra);
        		this.AIType1_Change();
        		aitypetime = 0;
        		
        	}
        	if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
				for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
					GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(2011, this.getEntityId(), aitypetime), playermp);
				}
    		}
    	}
    	if(this.aitype2_auto) {
    		if(!this.world.isRemote)++aitypetime2;
        	if(aitypetime2 >= aitypemax2){
        		int ra = this.world.rand.nextInt(aitypemaxset2);
        		if(this.getAIType2() == ra) {
        			for(int xxx = 0; xxx < 10; ++xxx) {
        				ra = this.world.rand.nextInt(aitypemaxset2);
        				if(this.getAIType2() != ra) {
        					break;
        				}
        			}
        		}
        		this.setAIType2(ra);
        		this.AIType2_Change();
        		aitypetime2 = 0;
        		
        	}
        	if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
				for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
					GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(2012, this.getEntityId(), aitypetime2), playermp);
				}
    		}
    	}
    	
    	
    	if(!this.world.isRemote)++aitypetime3;
    	targetValidtime();
    }
    
    
    /***　ターゲットをロックオンし続ける処理*/
    public void targetValidtime() {
    	
    	if(!this.world.isRemote && (this.getattacktask() || this.getMobMode() == 1))
    	{
			++this.mitargettime;
			boolean ta = true;
			if(mitargettime > 20)//200--->20
			{
				double k = this.posX;
	        	double l = this.posY;
				double i = this.posZ;
				int han = 80;
	        	AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),
						(double) (i - han), (double) (k + han), (double) (l + han), (double) (i + han)))
								.grow(1);
				List<Entity> llist = this.world.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb);
				if (llist != null) {
					for (int lj = 0; lj < llist.size(); lj++) {
						Entity entity1 = (Entity) llist.get(lj);
						if (entity1.canBeCollidedWith() && entity1 != null && this.getHealth() > 0.0F) {
							boolean flag = this.getEntitySenses().canSee(entity1);
							if(this.targetentity == entity1 && this.CanAttack(entity1)){
								if(flag){
									ta = false;
									break;
								}else{
									ta = true;
								}
							}else{
								ta = true;
							}
							
						}
					}
				}
				if(ta){
					//setTargetID(0);
					this.target = false;
					this.targetentity = null;
					this.mitargettime = 0;
					this.mitarget = null;
					this.setMobMode(0);
					this.setattacktask(false);
					this.targetValidtime_Change();
					//　多分いらない2021/2/12
					//　やっぱいるっぽい
					if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
						for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
							GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(202, this.getEntityId()), playermp);//base.setattacktask(false);
						}
		    		}
					
				}
			}
		}else {
			mitargettime = 0;
		}
    }
    
    /**this.targetentity = nullの時に呼ばれる	*/
    public void targetValidtime_Change() {
    	
    }
    
    
    
    
    
    
    /**　ターゲットしている敵モブが生きているかどうかの判定*/
    public boolean getTargetEntity_isLiving() {
    	boolean flag = false;
    	if(this.targetentity != null){
    		
    		if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
				for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
					GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(203, this.getEntityId(), this.targetentity.getEntityId()), playermp);//this.targetentity
				}
    		}
    		
			double k = this.posX;
        	double l = this.posY;
			double i = this.posZ;
			int han = 80;
        	AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double) (k - han), (double) (l - han),
					(double) (i - han), (double) (k + han), (double) (l + han), (double) (i + han)))
							.grow(1);
			List<Entity> llist = this.world.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 != null && this.getHealth() > 0.0F) {
						boolean flag2 = this.getEntitySenses().canSee(entity1);
						if(this.targetentity == entity1 && this.CanAttack(entity1)){
							flag = true;
						}
					}
				}
			}
		}
    	return flag;
    }
    
    public double getYOffset()
    {
        return -0.35D;
    }
}