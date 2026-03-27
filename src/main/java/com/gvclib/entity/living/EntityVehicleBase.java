package gvclib.entity.living;


import java.util.List;

import javax.annotation.Nullable;

import gvclib.mod_GVCLib;
import gvclib.item.ItemWrench;
import gvclib.network.GVCLClientMessageKeyPressed;
import gvclib.network.GVCLMessageKeyPressed;
import gvclib.network.GVCLPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
//import handmadevehicle.mod_HandmadeVehicle;
//import handmadevehicle.item.ItemSpawnHMV;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.IModelCustom;
import net.minecraft.scoreboard.Team;
public abstract class EntityVehicleBase extends EntityGVCLivingBase implements IAnimals
{
	public String modid = mod_GVCLib.MOD_ID;
	/**搭乗位置をプレイヤーのrotationに追従させる
	 * false時乗り物の位置そのまま*/
	public boolean ridding_roteplayer = true;
	/**搭乗位置をプレイヤーのrotationPitchに追従させる
	 * false時乗り物の位置そのまま*/
	public boolean ridding_roteplayerPitch = true;
	public int riddng_maximum = 1;
	/**ridding_roteplayer = false時モデルの位置依存*/
	public double[] riddingx = new double[32];
	/**ridding_roteplayer = false時モデルの位置依存*/
	public double[] riddingy = new double[32];
	/**ridding_roteplayer = false時モデルの位置依存*/
	public double[] riddingz = new double[32];
	
	/**ridding_roteplayer = false時モデルの位置からさらにプレイヤーのyaw分回転させる*/
	public double[] riddingx_roteplayer = new double[32];
	/**ridding_roteplayer = false時モデルの位置からさらにプレイヤーのyaw分回転させる*/
	public double[] riddingy_roteplayer = new double[32];
	/**ridding_roteplayer = false時モデルの位置からさらにプレイヤーのyaw分回転させる*/
	public double[] riddingz_roteplayer = new double[32];
	
	 /**水陸両用*/
    public boolean amphibious = false;
    
	public boolean[] seat_rote = new boolean[32];
    /**true時乗り物が傾くようになる
     * GVCEventsRiddingVehicleでtrue時モブを透明化*/
	public boolean ridding_roteangle = false;
    
	
	/**night_vision*/
	public boolean night_vision = false;
	public String night_vision_tex = "gvclib:textures/misc/night_vision.png";
	
	public boolean ridding_rote;
	/**GVCEventsRiddingZoomで使用
	 * 0 = 戦車
	 *  1 = 航空機
	 *  2 = ヘリ
	 *  3 = 自走砲
	 *  4 = 爆撃機
	 *  5 = ボート*/
	public int vehicletype = 0;
	public SoundEvent movesound;
	public double maxhp = 20D;
	public float antibullet_0 = 1;
	public float antibullet_1 = 1;
	public float antibullet_2 = 1;
	public float antibullet_3 = 1;
	public SoundEvent  DamegeSound = SoundEvents.BLOCK_ANVIL_LAND;
	public float sp;
	public float turnspeed;
	
	/**　この値以上のEntityB_Bulletでダメージが入ると、乗り物を貫通する*/
	public float normal_anti_bullet = 1000;
	
	/**true時に移動時ブロックを破壊する*/
	public boolean roodbreak = false;
	
	/**ridding_rotation_lock = trueの時この値以下ではAIが発砲しない*/
	public int mob_min_range = 0;
	/**最大射撃距離*/
	public int mob_max_range = 60;
	
	public int mob_min_height = 15;
	
	public boolean spg_mode = false;
	public int spg_min_range = 40;
	public float spg_yaw;
	public float spg_picth;
	public float spg_y;
	
	public float angle_max = 30;
	public float angle_min = -20;
	
	
	/**空機類で地上においての角度*/
	public float model_angle_base_x = 0;
	public float model_angle_base_y = 0;
	public float model_angle_base_z = 0;
	public float model_angle_offset_x = 0;
	public float model_angle_offset_y = 0;
	public float model_angle_offset_z = 0;
	public float model_angle_x = 0;
	public float model_angle_y = 0;
	public float model_angle_z = 0;
	
	
	
	public boolean weapon1true;
	public boolean weapon1_auto_reload = true;
	public int weapon1key = -1;
	public int weapon1;
	/**mob用
	 * 1で軸が正面のみ
	 * 3で爆撃*/
	public int weapon1type = 0;
	public boolean w1crossfire = false;
	public boolean w1can_cooldown = false;
	public float w1kakumax = 360;
	public float w1kakumin = -360;
	public boolean w1missile_aam = true;
	public int[] bullet_type1 = new int[32];
	public String[] bullet_model1 = new String[32];
	public String[] bullet_tex1 = new String[32];
	public String[] fire_model1 = new String[32];
	public String[] fire_tex1 = new String[32];
	public int[] fire_time1 = new int[32];
	public String[] sound_fire1 = new String[32];
	public float[] fire_pointx1 = new float[32];
	public float[] fire_pointy1 = new float[32];
	public float[] fire_pointz1 = new float[32];
	public float[] basis_pointx1 = new float[32];
	public float[] basis_pointy1 = new float[32];
	public float[] basis_pointz1 = new float[32];
	/**初期から+0.03入っている*/
	public double[] fire_yoffset1 = new double[32];
	/**true時搭乗者の向きに合わせて発砲する*/
	public boolean[] rotationfollowing1 = new boolean[32];
	@Deprecated
	public boolean[] rotationfirepoint1 = new boolean[32];
	/**回転軸中心をarm_x,arm_zに合わせるかどうか
	 * 回転軸が中心にない物に使用*/
	public boolean[] rotation_firepointbxbz1 = new boolean[32];
	/**回転軸中心を搭乗中のモブに合わせる*/
	public boolean[] rotation_player1 = new boolean[32];
	/**回転軸(rotationYawHead)を搭乗中のモブの頭に合わせる*/
	public boolean[] rotation_mob_head1 = new boolean[32];
	/**ピッチ方向(rotationpitch)をthisに合わせる(航空機のpitchがサーバーに適応されないため、爆撃の処置)*/
	public boolean[] rotation_lock_pitch_vehicle1 = new boolean[32];
	public int[] bullet_damage1 = new int[32];
	public float[] bullet_speed1 = new float[32];
	public float[] bullet_bure1 = new float[32];
	public float[] bullet_expower1 = new float[32];
	public boolean[] bullet_ex1 = new boolean[32];
	public int[] bullet_kazu1 = new int[32];
	public double[] bullet_gravity1 = new double[32];
	public int[] bullet_livingtime1 = new int[32];
	
	public String[] weapon_tgfx1 = new String[32];
	public String[] weapon_tgfx11 = new String[32];
	public String[] weapon_tgfx12 = new String[32];
	public String[] weapon_tgfx2 = new String[32];
	public String[] weapon_tgfx3 = new String[32];
	public String[] weapon_tgfx4 = new String[32];
	
	public boolean weapon11true = false;
	public String w11name;
	public int weapon11;
	public int[] bullet_type11 = new int[32];
	public String[] bullet_model11 = new String[32];
	public String[] bullet_tex11 = new String[32];
	public String[] fire_model11 = new String[32];
	public String[] fire_tex11 = new String[32];
	public int[] fire_time11 = new int[32];
	public String[] sound_fire11 = new String[32];
	public float[] fire_pointx11 = new float[32];
	public float[] fire_pointy11 = new float[32];
	public float[] fire_pointz11 = new float[32];
	public float[] basis_pointx11 = new float[32];
	public float[] basis_pointy11 = new float[32];
	public float[] basis_pointz11 = new float[32];
	public double[] fire_yoffset11 = new double[32];
	public boolean[] rotationfollowing11 = new boolean[32];
	@Deprecated
	public boolean[] rotationfirepoint11 = new boolean[32];
	/**回転軸中心をarm_x,arm_zに合わせるかどうか
	 * 回転軸が中心にない物に使用*/
	public boolean[] rotation_firepointbxbz11 = new boolean[32];
	public boolean[] rotation_player11 = new boolean[32];
	/**回転軸(rotation)を搭乗中のモブの頭に合わせる*/
	public boolean[] rotation_mob_head11 = new boolean[32];
	/**ピッチ方向(rotationpitch)をthisに合わせる(航空機のpitchがサーバーに適応されないため、爆撃の処置)*/
	public boolean[] rotation_lock_pitch_vehicle11 = new boolean[32];
	public int[] bullet_damage11 = new int[32];
	public float[] bullet_speed11 = new float[32];
	public float[] bullet_bure11 = new float[32];
	public float[] bullet_expower11 = new float[32];
	public boolean[] bullet_ex11 = new boolean[32];
	public int[] bullet_kazu11 = new int[32];
	public double[] bullet_gravity11 = new double[32];
	public int[] bullet_livingtime11 = new int[32];
	
	public boolean weapon12true = false;
	public String w12name;
	public int weapon12;
	public int[] bullet_type12 = new int[32];
	public String[] bullet_model12 = new String[32];
	public String[] bullet_tex12 = new String[32];
	public String[] fire_model12 = new String[32];
	public String[] fire_tex12 = new String[32];
	public int[] fire_time12 = new int[32];
	public String[] sound_fire12 = new String[32];
	public float[] fire_pointx12 = new float[32];
	public float[] fire_pointy12 = new float[32];
	public float[] fire_pointz12 = new float[32];
	public float[] basis_pointx12 = new float[32];
	public float[] basis_pointy12 = new float[32];
	public float[] basis_pointz12 = new float[32];
	public double[] fire_yoffset12 = new double[32];
	public boolean[] rotationfollowing12 = new boolean[32];
	@Deprecated
	public boolean[] rotationfirepoint12 = new boolean[32];
	/**回転軸中心をarm_x,arm_zに合わせるかどうか
	 * 回転軸が中心にない物に使用*/
	public boolean[] rotation_firepointbxbz12 = new boolean[32];
	public boolean[] rotation_player12 = new boolean[32];
	/**回転軸(rotation)を搭乗中のモブの頭に合わせる*/
	public boolean[] rotation_mob_head12 = new boolean[32];
	/**ピッチ方向(rotationpitch)をthisに合わせる(航空機のpitchがサーバーに適応されないため、爆撃の処置)*/
	public boolean[] rotation_lock_pitch_vehicle12 = new boolean[32];
	public int[] bullet_damage12 = new int[32];
	public float[] bullet_speed12 = new float[32];
	public float[] bullet_bure12 = new float[32];
	public float[] bullet_expower12 = new float[32];
	public boolean[] bullet_ex12 = new boolean[32];
	public int[] bullet_kazu12 = new int[32];
	public double[] bullet_gravity12 = new double[32];
	public int[] bullet_livingtime12 = new int[32];
	
	public int mob_w1range = 50;
	public int mob_w1range_max_y = 20;
	public int mob_w1range_min_y = 3;
	
	
	
	public boolean weapon2true;
	public boolean weapon2_auto_reload = true;
	public int weapon2key = -1;
	public int weapon2;
	public int weapon2type = 0;
	public boolean w2crossfire = false;
	public boolean w2can_cooldown = false;
	public float w2kakumax = 360;
	public float w2kakumin = -360;
	public boolean w2missile_aam = true;
	public int[] bullet_type2 = new int[32];
	public String[] bullet_model2 = new String[32];
	public String[] bullet_tex2 = new String[32];
	public String[] fire_model2 = new String[32];
	public String[] fire_tex2 = new String[32];
	public int[] fire_time2 = new int[32];
	public String[] sound_fire2 = new String[32];
	public float[] fire_pointx2 = new float[32];
	public float[] fire_pointy2 = new float[32];
	public float[] fire_pointz2 = new float[32];
	public float[] basis_pointx2 = new float[32];
	public float[] basis_pointy2 = new float[32];
	public float[] basis_pointz2 = new float[32];
	public double[] fire_yoffset2 = new double[32];
	public boolean[] rotationfollowing2 = new boolean[32];
	@Deprecated
	public boolean[] rotationfirepoint2 = new boolean[32];
	/**回転軸中心をarm_x,arm_zに合わせるかどうか
	 * 回転軸が中心にない物に使用*/
	public boolean[] rotation_firepointbxbz2 = new boolean[32];
	public boolean[] rotation_player2 = new boolean[32];
	/**回転軸(rotation)を搭乗中のモブの頭に合わせる*/
	public boolean[] rotation_mob_head2 = new boolean[32];
	/**ピッチ方向(rotationpitch)をthisに合わせる(航空機のpitchがサーバーに適応されないため、爆撃の処置)*/
	public boolean[] rotation_lock_pitch_vehicle2 = new boolean[32];
	public int[] bullet_damage2 = new int[32];
	public float[] bullet_speed2 = new float[32];
	public float[] bullet_bure2 = new float[32];
	public float[] bullet_expower2 = new float[32];
	public boolean[] bullet_ex2 = new boolean[32];
	public int[] bullet_kazu2 = new int[32];
	public double[] bullet_gravity2 = new double[32];
	public int[] bullet_livingtime2 = new int[32];
	
	public int mob_w2range = 50;
	public int mob_w2range_max_y = 20;
	public int mob_w2range_min_y = 3;
	
	
	public boolean weapon3true;
	public boolean weapon3_auto_reload = true;
	public int weapon3key = -1;
	public int weapon3;
	public int weapon3type = 0;
	public boolean w3crossfire = false;
	public boolean w3can_cooldown = false;
	public float w3kakumax = 180;
	public float w3kakumin = -180;
	public boolean w3missile_aam = true;
	public int[] bullet_type3 = new int[33];
	public String[] bullet_model3 = new String[33];
	public String[] bullet_tex3 = new String[33];
	public String[] fire_model3 = new String[33];
	public String[] fire_tex3 = new String[33];
	public int[] fire_time3 = new int[33];
	public String[] sound_fire3 = new String[33];
	public float[] fire_pointx3 = new float[33];
	public float[] fire_pointy3 = new float[33];
	public float[] fire_pointz3 = new float[33];
	public float[] basis_pointx3 = new float[33];
	public float[] basis_pointy3 = new float[33];
	public float[] basis_pointz3 = new float[33];
	public double[] fire_xoffset3 = new double[33];
	public double[] fire_yoffset3 = new double[33];
	public boolean[] rotationfollowing3 = new boolean[33];
	@Deprecated
	public boolean[] rotationfirepoint3 = new boolean[33];
	/**回転軸中心をarm_x,arm_zに合わせるかどうか
	 * 回転軸が中心にない物に使用*/
	public boolean[] rotation_firepointbxbz3 = new boolean[32];
	public boolean[] rotation_player3 = new boolean[33];
	/**回転軸(rotation)を搭乗中のモブの頭に合わせる*/
	public boolean[] rotation_mob_head3 = new boolean[32];
	/**ピッチ方向(rotationpitch)をthisに合わせる(航空機のpitchがサーバーに適応されないため、爆撃の処置)*/
	public boolean[] rotation_lock_pitch_vehicle3 = new boolean[32];
	public int[] bullet_damage3 = new int[33];
	public float[] bullet_speed3 = new float[33];
	public float[] bullet_bure3 = new float[33];
	public float[] bullet_expower3 = new float[33];
	public boolean[] bullet_ex3 = new boolean[33];
	public boolean[] bullet_exfire3 = new boolean[33];
	public boolean[] bullet_exsmoke3 = new boolean[33];
	public boolean[] bullet_exflash3 = new boolean[33];
	public int[] bullet_kazu3 = new int[33];
	public double[] bullet_gravity3 = new double[33];
	public int[] bullet_livingtime3 = new int[33];
	
	public int mob_w3range = 50;
	public int mob_w3range_max_y = 40;
	public int mob_w3range_min_y = 3;
	
	
	public boolean weapon4true;
	public boolean weapon4_auto_reload = true;
	public int weapon4key = -1;
	public int weapon4;
	public int weapon4type = 0;
	public boolean w4crossfire = false;
	public boolean w4can_cooldown = false;
	public float w4kakumax = 180;
	public float w4kakumin = -180;
	public boolean w4missile_aam = true;
	public int[] bullet_type4 = new int[34];
	public String[] bullet_model4 = new String[34];
	public String[] bullet_tex4 = new String[34];
	public String[] fire_model4 = new String[34];
	public String[] fire_tex4 = new String[34];
	public int[] fire_time4 = new int[34];
	public String[] sound_fire4 = new String[34];
	public float[] fire_pointx4 = new float[34];
	public float[] fire_pointy4 = new float[34];
	public float[] fire_pointz4 = new float[34];
	public float[] basis_pointx4 = new float[34];
	public float[] basis_pointy4 = new float[34];
	public float[] basis_pointz4 = new float[34];
	public double[] fire_xoffset4 = new double[34];
	public double[] fire_yoffset4 = new double[34];
	public boolean[] rotationfollowing4 = new boolean[34];
	@Deprecated
	public boolean[] rotationfirepoint4 = new boolean[34];
	/**回転軸中心をarm_x,arm_zに合わせるかどうか
	 * 回転軸が中心にない物に使用*/
	public boolean[] rotation_firepointbxbz4 = new boolean[32];
	public boolean[] rotation_player4 = new boolean[34];
	/**回転軸(rotation)を搭乗中のモブの頭に合わせる*/
	public boolean[] rotation_mob_head4 = new boolean[32];
	/**ピッチ方向(rotationpitch)をthisに合わせる(航空機のpitchがサーバーに適応されないため、爆撃の処置)*/
	public boolean[] rotation_lock_pitch_vehicle4 = new boolean[32];
	public int[] bullet_damage4 = new int[34];
	public float[] bullet_speed4 = new float[34];
	public float[] bullet_bure4 = new float[34];
	public float[] bullet_expower4 = new float[34];
	public boolean[] bullet_ex4 = new boolean[34];
	public boolean[] bullet_exfire4 = new boolean[34];
	public boolean[] bullet_exsmoke4 = new boolean[34];
	public boolean[] bullet_exflash4 = new boolean[34];
	public int[] bullet_kazu4 = new int[34];
	public double[] bullet_gravity4 = new double[34];
	public int[] bullet_livingtime4 = new int[34];
	
	public int mob_w4range = 50;
	public int mob_w4range_max_y = 40;
	public int mob_w4range_min_y = 3;
	
	
	public int turret;
	public boolean[] turretrote = new boolean[32];
	/**ma4*/
	public float[] arm_x = new float[32];
	/**ma4*/
	public float[] arm_y = new float[32];
	/**ma4*/
	public float[] arm_z = new float[32];
    
	/**ma5*/
	public float[] hand_x = new float[32];
	/**ma5*/
	public float[] hand_y = new float[32];
	/**ma5*/
	public float[] hand_z = new float[32];
	
	/**mat6*/
	public int[] finger_type = new int[32];
	
	public int pera;
	public float[] pera_x = new float[32];
	public float[] pera_y = new float[32];
	public float[] pera_z = new float[32];
	public float[] perarote_x = new float[32];
	public float[] perarote_y = new float[32];
	public float[] perarote_z = new float[32];
	
	public int cloud;
	public double[] cloud_x = new double[32];
	public double[] cloud_y = new double[32];
	public double[] cloud_z = new double[32];
	
	public int rader;
	public int[] raderturret = new int[32];
	public float[] rader_x = new float[32];
	public float[] rader_y = new float[32];
	public float[] rader_z = new float[32];
	public float[] raderrote_x = new float[32];
	public float[] raderrote_y = new float[32];
	public float[] raderrote_z = new float[32];
	
	/**　傾き試験
	 * */
	public int inclined_x = 0;
	/**　傾き試験
	 * */
	public int inclined_z = 0;
	
	
	protected static final DataParameter<String> Model_exhaust = 
    		EntityDataManager.<String>createKey(EntityGVCLivingBase.class, DataSerializers.STRING);
    protected static final DataParameter<String> Tex_exhaust = 
    		EntityDataManager.<String>createKey(EntityGVCLivingBase.class, DataSerializers.STRING);
    public IModelCustom model_exhaust = null;
    public ResourceLocation tex_exhaust;
	
	
    public EntityVehicleBase(World worldIn)
    {
        super(worldIn);
       
    }
    
    
    
    public BlockPos blockpos = null;
    public void setGun() {
    	if (blockpos == null && !this.world.isRemote)
        {
            blockpos = new BlockPos(this);
        }
		
		if(blockpos != null){
			this.posX = (double)blockpos.getX() + 0.5D;
            this.posZ = (double)blockpos.getZ() + 0.5D;
            this.prevPosX = this.posX;
            this.prevPosZ = this.posZ;
            this.lastTickPosX = this.posX;
            this.lastTickPosZ = this.posZ;
            this.posY = (double)blockpos.getY();
		}
    }
    
    public int ticks;
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(this.getModel_exhaust().equals("") || this.getModel_exhaust() == null) {
        	this.setModel_exhaust("gvclib:textures/marker/exhaust.mqo");
        	this.setTex_exhaust("gvclib:textures/marker/exhaust.png");
        }
        
        if(FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
			for (EntityPlayerMP playermp : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
				GVCLPacketHandler.INSTANCE2.sendTo(new GVCLClientMessageKeyPressed(62, this.getEntityId(), this.thpera), playermp);
			}
		}
        
		if(this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityLivingBase){
			EntityLivingBase passenger_team = (EntityLivingBase)this.getControllingPassenger();
			if(passenger_team.getTeam()!=null && (this.getTeam()==null||this.getTeam() != passenger_team.getTeam())){
				this.world.getScoreboard().addPlayerToTeam(this.getCachedUniqueIdString(), passenger_team.getTeam().getName());
			}
		}else{
			if(this.getTeam()!=null)this.world.getScoreboard().removePlayerFromTeams(this.getCachedUniqueIdString());
		}
		
        if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
			EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
			// if(this.bullet_type1[0] == 4 || this.bullet_type2[0] == 4|| this.bullet_type3[0] == 4|| this.bullet_type4[0] == 4) 
			 {
		        	PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
		        	if(this.bullet_type1[0] == 4) {
		        		we.EntityLock(this.w1missile_aam);
		        	}
		        	if(this.bullet_type2[0] == 4) {
		        		we.EntityLock(this.w2missile_aam);
		        	}
		        	if(this.bullet_type3[0] == 4) {
		        		we.EntityLock(this.w3missile_aam);
		        	}
		        	if(this.bullet_type4[0] == 4) {
		        		we.EntityLock(this.w4missile_aam);
		        	}
		        }
			 
			 
			 //StateGUI
			 {
				 
				 boolean tab = mod_GVCLib.proxy.tab();
				 if (tab) {
						GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(22, this.getEntityId()));
						this.servertab = true;
						//System.out.println("0");
					}
				 
				 if (this.servertab) {
						NBTTagCompound nbt = entitylivingbase.getEntityData();
						nbt.setInteger("vehi", this.getEntityId());
						entitylivingbase.openGui(mod_GVCLib.INSTANCE, 5,
								entitylivingbase.world, (int) entitylivingbase.posX, (int) entitylivingbase.posY, (int) entitylivingbase.posZ);
						/*{
							this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, 
					    			SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.NEUTRAL, 0.5F, 1.0F);
						}*/
						this.servertab = false;
					}
			 }
			 
			 
			 
			}
		}
       
        
        
        
        if(this.getArmID_R() == 1 && this.weapon11true){
			this.w1namebase = this.w11name;
		}else if(this.getArmID_R() == 2 && this.weapon12true){
			this.w1namebase = this.w12name;
		}else {
			this.w1namebase = this.w1name;
		}
        
        if(this.getRemain_L() <= 0 && weapon1_auto_reload){
			++reload1;
			
			if(reload1 == reload_time1 - reloadsoundset1){
				//this.playSound(reloadsound1, 1.0F, 1.0F);
				this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound1, SoundCategory.WEATHER, 1.0F, 1.0F);
			}
			if(reload1 >= reload_time1){
				this.setRemain_L(this.magazine);
				reload1 = 0;
			}
		}
		if(this.getRemain_R() <= 0 && weapon2_auto_reload){
			++reload2;
			if(reload2 == reload_time2 - reloadsoundset2){
				this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound2, SoundCategory.WEATHER, 1.0F, 1.0F);
			}
			if(reload2 >= reload_time2){
				this.setRemain_R(this.magazine2);
				reload2 = 0;
			}
		}
		if(this.getRemain_A() <= 0 && weapon3_auto_reload){
			++reload3;
			if(reload3 == reload_time3 - reloadsoundset3){
				this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound3, SoundCategory.WEATHER, 1.0F, 1.0F);
			}
			if(reload3 >= reload_time3){
				this.setRemain_A(this.magazine3);
				reload3 = 0;
			}
		}
		
		if (this.getRemain_S() <= 0 && weapon4_auto_reload) {
			++reload4;
			if (reload4 == reload_time4 - reloadsoundset4) {
				this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, reloadsound4, SoundCategory.WEATHER, 1.0F, 1.0F);
			}
			if (reload4 >= reload_time4) {
				this.setRemain_S(this.magazine4);
				reload4 = 0;
			}
		}
        
		/*if (this.getRemain_A() <= 0) {
			++reload5;
			if (reload5 >= 20) {
				this.setRemain_A(1);
				reload5 = 0;
			}
		}*/
		
		if (this.getWeaponChange() <= 0) {
			++reload6;
			if (reload6 >= 20) {
				this.setWeaponChange(1);
				reload6 = 0;
			}
		}
		
		if(this.getRemain_L() > 0 && this.getRemain_L() < this.magazine && this.w1can_cooldown) {
			if(gun_auto_count_1 < 200)++gun_auto_count_1;
    		if(this.gun_auto_count_1 > (int)(this.ammo1 * 2)) {
    			this.setRemain_L(this.getRemain_L() +1);
    			this.gun_auto_count_1 = 0;
    		}
    	}
		if(this.getRemain_R() > 0 && this.getRemain_R() < this.magazine2 && this.w2can_cooldown) {
			if(gun_auto_count_2 < 200)++gun_auto_count_2;
    		if(this.gun_auto_count_2 > (int)(this.ammo2 * 2)) {
    			this.setRemain_R(this.getRemain_R() +1);
    			this.gun_auto_count_2 = 0;
    		}
    	}
		if(this.getRemain_A() > 0 && this.getRemain_A() < this.magazine3 && this.w3can_cooldown) {
			if(gun_auto_count_3 < 200)++gun_auto_count_3;
    		if(this.gun_auto_count_3 > (int)(this.ammo3 * 2)) {
    			this.setRemain_A(this.getRemain_A() +1);
    			this.gun_auto_count_3 = 0;
    		}
    	}
		if(this.getRemain_S() > 0 && this.getRemain_S() < this.magazine4 && this.w4can_cooldown) {
			if(gun_auto_count_4 < 200)++gun_auto_count_4;
    		if(this.gun_auto_count_4 > (int)(this.ammo4 * 2)) {
    			this.setRemain_S(this.getRemain_S() +1);
    			this.gun_auto_count_4 = 0;
    		}
    	}
		
		for(int t1 = 0; t1 < this.cloud; ++t1){
			if(this.throttle > 1  && this.getHealth() > 0.0F)
			{
				double x1 = 0;
				double z1 = 0;
				float ff = this.rotationYawHead * 0.01745329252F;
				
				double b = 0;
				double b2 = 0;
				double a = 0;
				double ax = 0;
				double az = 0;
				//if(i != 0) 
				float rpitch = this.rotationPitch;
				if(this.onGround || !ridding_roteplayerPitch)rpitch = 0;
				
				{
					float rote = this.rotationYawHead;
					b =  cloud_z[t1] * MathHelper.sin(rpitch  * (1 * (float) Math.PI / 180)) *  1.0D;
					a =  cloud_z[t1] * Math.abs(Math.cos(rpitch  * (1 * (float) Math.PI / 180))) *  1.0D;
					ax -= MathHelper.sin(rote * (2 * (float) Math.PI / 360)) * a;
					az += MathHelper.cos(rote * (2 * (float) Math.PI / 360)) * a;
					ax -= MathHelper.sin(rote * (2 * (float) Math.PI / 360) - 1.57F) * cloud_x[t1];
					az += MathHelper.cos(rote * (2 * (float) Math.PI / 360) - 1.57F) * cloud_x[t1];
				}
				
				x1 -= MathHelper.sin(ff -1.57F) * cloud_x[t1];
				z1 += MathHelper.cos(ff -1.57F) * cloud_x[t1];
				x1 -= MathHelper.sin(ff) * cloud_z[t1];
				z1 += MathHelper.cos(ff) * cloud_z[t1];
				this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + 0 + ax, this.posY + cloud_y[t1] - b, this.posZ + 0 + az, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
		
		
        ++ticks;
        if(ticks > 360){
        	ticks = 0;
        }
        {
                 {
                     List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());

                     if (!list.isEmpty())
                     {
                         for (Entity entity : list)
                         {
                             if (!(entity instanceof EntityVehicleBase) && !entity.noClip)
                             {
                                 entity.move(MoverType.SELF,0, 0, 0);
                             }
                         }
                     }
                 }
        }
    }
    
    /**
     * If the rider should be dismounted from the entity when the entity goes under water
     *
     * @param rider The entity that is riding
     * @return if the entity should be dismounted when under water
     */
    @Override
    public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }
    
    /**
     * Dismounts this entity from the entity it is riding.
     */
    public void dismountRidingEntity()
    {
        Entity entity = this.getRidingEntity();
        super.dismountRidingEntity();

        if (entity != null && entity != this.getRidingEntity() && !this.world.isRemote)
        {
        	//if(!this.getRidingEntity().shouldDismountInWater(this))
            this.dismountEntity(entity);
        }
    }
    
    /***　キー操作でモブの搭乗操作
     * 　Vキーで乗せ、Bキーで降ろし
     */
    public void ContMobRidding() {
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
				boolean kv = mod_GVCLib.proxy.keyv();
				boolean kb = mod_GVCLib.proxy.keyb();
				if (kv) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(20, this.getEntityId()));
					this.serverv = true;
				}
				if (kb) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(21, this.getEntityId()));
					this.serverb = true;
				}
				if(!this.world.isRemote) {
					if (this.serverb) {
						for(int m = 0; m < this.getPassengers().size(); ++m) {
							//if (!this.world.isRemote && this.getPassengers().get(m) != null) {
							if (this.getPassengers().get(m) != null) {
								if (this.getPassengers().get(m) instanceof EntityLivingBase
										&& !(this.getPassengers().get(m) instanceof EntityPlayer)) {
									EntityLivingBase entity = (EntityLivingBase)this.getPassengers().get(m);
									entity.dismountRidingEntity();
								}
							}
						}
						this.serverb = false;
					}
					
					if (this.serverv) {
						{
							List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this,this.getEntityBoundingBox().grow(3D));
							if (!list.isEmpty()) {
								for (int j = 0; j < list.size(); ++j) {
									Entity entity = list.get(j);
									if (!entity.isPassenger(this)) {
										if (this.getPassengers().size() < riddng_maximum && !entity.isRiding()
												&& (entity instanceof ISoldier || entity instanceof IAnimals)) {
											entity.startRiding(this);
										} else {
											this.applyEntityCollision(entity);
										}
									}
								}
							}
						}
						this.serverv = false;
					}
				}
				
			}
		}
    }
    
    
    public Item spawn_item = null;
    
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
    	ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        boolean re = true;
        boolean lock = false;
        if(this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityGVCLivingBase) {
        	EntityGVCLivingBase ridding = (EntityGVCLivingBase) this.getControllingPassenger();
        	if(!(ridding instanceof IMob)) {
        		if(player.isSneaking() && !this.world.isRemote) {
        			ridding.dismountRidingEntity();
        			lock = true;
        			this.setVehicleLock(true);
					if (!this.world.isRemote)player.sendMessage(new TextComponentTranslation("载具已上锁", new Object[0]));
        		}
        		if(ridding.getOwner() != null) {
        			if(ridding.getMoveT() == 1) {
        				ridding.setMoveT(0);
        				if(ridding.world.isRemote)
        				player.sendMessage(new TextComponentTranslation("gvclib.order.follow.name", new Object[0]));
        			}
        			else if(ridding.getMoveT() == 0) {
        				ridding.setMoveT(3);
        				if(ridding.world.isRemote)
        				player.sendMessage(new TextComponentTranslation("gvclib.order.stay.name", new Object[0]));
        			}
        			else if(ridding.getMoveT() == 3) {
        				ridding.setMoveT(1);
        				if(ridding.world.isRemote)
        				player.sendMessage(new TextComponentTranslation("gvclib.order.free.name", new Object[0]));
        			}
        		}
        	}
        }
        
        if(!this.world.isRemote){
        	if(flag){
        		if(itemstack.getItem() instanceof ItemWrench) {
        			if(player.isSneaking() && spawn_item != null) {
        				//if(itemstack.getItemDamage() < this.getHealth())
        				if(this.getHealth() >= this.getMaxHealth() && this.getHealth() > 0.0F) 
        				{
        					//itemstack.damageItem((int)this.getHealth(), player);
        					itemstack.damageItem(1, player);
        					if (!this.world.isRemote)
                            {
            					this.entityDropItem(new ItemStack(spawn_item), 0.0F);
                                this.setDead();
                            }
        				}
        			}else {
        				if(this.getHealth() < this.getMaxHealth() && this.getHealth() > 0.0F) {
            				this.setHealth(this.getHealth() + 10);
                			itemstack.damageItem(1, player);
                			this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 5F, 2F);
                			//System.out.println(String.format("not"));
            			}
        			}
        		}else if(itemstack.getItem() == Items.WOODEN_HOE) {
        			if (!this.world.isRemote)
                    {
                        this.setDead();
                    }
        		}else if(player.isSneaking()) {
        			this.setVehicleLock(true);
        		}
        		else {
        			if (!this.world.isRemote && !player.isRiding())
                    {
        				this.setVehicleLock(true);
        				if (!this.world.isRemote)player.sendMessage(new TextComponentTranslation("Vehicle_Lock", new Object[0]));
                        player.startRiding(this);
                        this.setcanDespawn(1);
                    }
        			re = false;
        		}
        	}else{
        		if(player.isSneaking()){
        			if(!player.isRiding()) {
        				if(this.getVehicleLock() && !lock) {
        					this.setVehicleLock(false);
        					if (!this.world.isRemote)player.sendMessage(new TextComponentTranslation("Vehicle_UnLock", new Object[0]));
        				}else {
        					this.setVehicleLock(true);
        					if (!this.world.isRemote)player.sendMessage(new TextComponentTranslation("Vehicle_Lock", new Object[0]));
        				}
        			}else {
        				re = false;
        			}
        		}else
        		{
        			
        			if (!this.world.isRemote && !player.isRiding())
                    {
        				this.setVehicleLock(true);
            			if (!this.world.isRemote)player.sendMessage(new TextComponentTranslation("Vehicle_Lock", new Object[0]));
                        player.startRiding(this);
                        this.setcanDespawn(1);
                    }
        		}
        	}
        }
        	return re;
    }

    //座る人数
    protected boolean canFitPassenger(Entity passenger)
    {
        return this.getPassengers().size() < riddng_maximum;
    }
    
    private float deltaRotation;
    
    public void updatePassenger(Entity passenger)
    {
        if (this.isPassenger(passenger))
        {
        	 int i = this.getPassengers().indexOf(passenger);
        	// if (i == 0)
        	 
             {
				double ix = 0;
				double iy = 0;
				double iz = 0;
				float rote = this.rotationYawHead;
				if (ridding_roteplayer) {
					rote = passenger.rotationYaw;
				}
				float f1 = rote * (2 * (float) Math.PI / 360);
				ix -= MathHelper.sin(f1) * riddingz[i];
				iz += MathHelper.cos(f1) * riddingz[i];
				ix -= MathHelper.sin(f1 - 1.57F) * riddingx[i];
				iz += MathHelper.cos(f1 - 1.57F) * riddingx[i];
				double ix2 = 0;
				double iz2 = 0;
				float f12 = passenger.rotationYaw * (2 * (float) Math.PI / 360);
	//			float f22 = this.rotationPitch * (2 * (float) Math.PI / 360);
				ix2 -= MathHelper.sin(f12) * riddingz_roteplayer[i];
				iz2 += MathHelper.cos(f12) * riddingz_roteplayer[i];
				ix2 -= MathHelper.sin(f12 - 1.57F) * riddingx_roteplayer[i];
				iz2 += MathHelper.cos(f12 - 1.57F) * riddingx_roteplayer[i];
				
				double b = 0;
				double b2 = 0;
				double a = 0;
				double ax = 0;
				double az = 0;
				//if(i != 0) 
				float rpitch = this.rotationPitch;
				if(this.onGround || !ridding_roteplayerPitch)rpitch = 0;
				
				{
					b =  riddingz[i] * MathHelper.sin(rpitch  * (1 * (float) Math.PI / 180)) *  1.0D;
					//b2 =  Math.abs(riddingx[i]) * Math.tan(this.throte  * (2 * (float) Math.PI / 360)) *  1.0D;
					a =  riddingz[i] * Math.abs(Math.cos(rpitch  * (1 * (float) Math.PI / 180))) *  1.0D;
					//a =  riddingz[i] *  1.0D;
					ax -= MathHelper.sin(rote * (2 * (float) Math.PI / 360)) * a;
					az += MathHelper.cos(rote * (2 * (float) Math.PI / 360)) * a;
					ax -= MathHelper.sin(rote * (2 * (float) Math.PI / 360) - 1.57F) * riddingx[i];
					az += MathHelper.cos(rote * (2 * (float) Math.PI / 360) - 1.57F) * riddingx[i];
				}
				/*
				ix2 -= MathHelper.sin(f1) * arm_z[i];
				iz2 += MathHelper.cos(f1) * arm_z[i];
				ix2 -= MathHelper.sin(f1 - 1.57F) * arm_x[i];
				iz2 += MathHelper.cos(f1 - 1.57F) * arm_x[i];
				*/
				Vec3d vec3d = new Vec3d(ax + ix2, riddingy[i] + riddingy_roteplayer[i] + passenger.getYOffset() - b, az + iz2);
				passenger.setPosition(this.posX + vec3d.x,this.posY + vec3d.y, this.posZ + vec3d.z);
				
				/*if (passenger.rotationPitch > passenger.prevRotationPitch)
	            {
	                this.deltaRotation += -1.0F;
	            }

	            if (passenger.rotationPitch < passenger.prevRotationPitch)
	            {
	                ++this.deltaRotation;
	            }
	            this.rotationPitch += this.deltaRotation;
	            
				passenger.rotationPitch += this.deltaRotation;
				
				if(passenger.rotationPitch <= this.rotationp_max) {
					passenger.rotationPitch = this.rotationp_max;
					passenger.prevRotationPitch = this.rotationp_max;
				}
				if(passenger.rotationPitch >= this.rotationp_min) {
					passenger.rotationPitch = this.rotationp_min;
					passenger.prevRotationPitch = this.rotationp_min;
				}*/
				//float f = MathHelper.wrapDegrees(passenger.rotationPitch - this.rotationp);
				if(this.getFTMode() != 1)this.applyYawToEntityPitch(passenger);
				if(ridding_rotation_lock)this.applyYawToEntity(passenger);
				/*float f = passenger.rotationPitch - this.rotationPitch;
		        float f11 = MathHelper.clamp(f, this.rotationp_max, this.rotationp_min);
		        passenger.prevRotationPitch -= f11 - f;
		        passenger.rotationPitch -= f11 - f;*/
				
             }
        }
    }
    
    protected void applyYawToEntityPitch(Entity entityToUpdate)
    {
    	float f = entityToUpdate.rotationPitch;
        float f11 = MathHelper.clamp(f, this.rotationp_max, this.rotationp_min);
        entityToUpdate.prevRotationPitch += f11 - f;
        entityToUpdate.rotationPitch += f11 - f;
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyYawToEntity(Entity entityToUpdate)
    {
    	entityToUpdate.setRotationYawHead(entityToUpdate.getRotationYawHead());
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -rotation_max, rotation_max);//MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }
    
    /**
     * Applies this entity's orientation (pitch/yaw) to another entity. Used to update passenger orientation.
     */
    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate)
    {
    	if(this.getFTMode() != 1)this.applyYawToEntityPitch(entityToUpdate);
        if(ridding_rotation_lock)this.applyYawToEntity(entityToUpdate);
    }
    
    
    public boolean can_turret = false;
    public float turret_height = 0;
    public float damage_front = 0;
    public float damage_side = 0;
    public float damage_rear = 0;
    public float damage_top = 0;
    public float damage_bottom = 0;
    
    public float damage_turret_front = 0;
    public float damage_turret_side = 0;
    public float damage_turret_rear = 0;
    
    
    public boolean attackEntityFrom(DamageSource source, float par2)
    {
    	Entity entity;
    	entity = source.getImmediateSource();
    	
    	Entity entity2 = source.getTrueSource();
    	
		if(entity != null){
			if(this.getControllingPassenger() != null && this.getControllingPassenger() == source.getImmediateSource()){
	    		return false;
	        }else if(this.getPassengers() != null && this.getPassengers() == source.getImmediateSource()){
	    		return false;
	        }
			else
	        {
				if(can_turret) {
					double ey = entity.posY;
					if(ey >= this.getEntityBoundingBox().maxY) {
						if(par2 < damage_top) {
							par2 = 0;
						}
					}
					else if(ey >= this.getEntityBoundingBox().minY + turret_height) {
						par2 = AI_Damage.newTankArmor(this, entity, par2, true, this.rotation);
					}else if(ey >= this.getEntityBoundingBox().minY){
						par2 = AI_Damage.newTankArmor(this, entity, par2, false, this.rotationYaw);
					}else {
						if(par2 < damage_bottom) {
							par2 = 0;
						}
					}
				}else {
					par2 = AI_Damage.newTankArmor(this, entity, par2, false, this.rotationYaw);
				}
				par2 = par2 * AI_Damage.newAntiBullet(this, entity, par2, this.antibullet_0, this.antibullet_1, this.antibullet_2, this.antibullet_3);
				if(par2 <= 0.0) {
					this.playSound(DamegeSound, 0.1F, 1F);
				}
				return super.attackEntityFrom(source, par2);
			}
		}
		else if(entity2!= null)
		{
			if(this.getControllingPassenger() != null && this.getControllingPassenger() == source.getTrueSource()){
	    		return false;
	        }else if(this.getPassengers() != null && this.getPassengers() == source.getTrueSource()){
	    		return false;
	        }
			else
	        {
				if(can_turret) {
					double ey = entity2.posY;
					if(ey >= this.getEntityBoundingBox().maxY) {
						if(par2 < damage_top) {
							par2 = 0;
						}
					}
					else if(ey >= this.getEntityBoundingBox().minY + turret_height) {
						par2 = AI_Damage.newTankArmor(this, entity, par2, true, this.rotation);
					}else if(ey >= this.getEntityBoundingBox().minY){
						par2 = AI_Damage.newTankArmor(this, entity, par2, false, this.rotationYaw);
					}else {
						if(par2 < damage_bottom) {
							par2 = 0;
						}
					}
				}else {
					par2 = AI_Damage.newTankArmor(this, entity, par2, false, this.rotationYaw);
				}
				if(par2 <= 0.0) {
					this.playSound(DamegeSound, 0.1F, 1F);
				}
				return super.attackEntityFrom(source, par2);
			}
		}
		else{
			return super.attackEntityFrom(source, par2);
		}
    }
    

    public void weapon1active(Vec3d looked, EntityPlayer entitylivingbase) {
    	if(this.counter1 && gun_count1 >= w1cycle && this.getRemain_L() > 0){
    		boolean shouhi = false;
			for(int w1 = 0; w1 < weapon1; ++w1) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing1[w1]) {
					looked = entitylivingbase.getLookVec();
				}
				/*else {
					looked = this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
				}*/
				if(this.rotation_player1[w1]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint1[w1]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_firepointbxbz1[w1]) {
					double xx1 = 0;
					double zz1 = 0;
					xx1 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w1];
					zz1 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w1];
					xx1 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w1];
					zz1 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w1];
					x = this.posX + xx1;
					z = this.posZ + zz1;
				}
				float side = - 1.57F;
				if(this.w1crossfire) {
					if(this.getRemain_L() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				/*PL_Weapon.WeaponAttack(this, this.bullet_type1[w1], entitylivingbase, 
						this.bullet_model1[w1],this.bullet_tex1[w1],this.fire_model1[w1],this.fire_tex1[w1], this.fire_time1[w1],
						SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1])), 
						side, this.fire_pointx1[w1], this.fire_pointy1[w1], this.fire_pointz1[w1], this.fire_yoffset1[w1],
						x, this.posY, z, looked, rotefire,0,0,
						this.bullet_damage1[w1], this.bullet_speed1[w1], this.bullet_bure1[w1], 
						this.bullet_expower1[w1], this.bullet_ex1[w1], 
						this.bullet_kazu1[w1], this.bullet_gravity1[w1], this.bullet_livingtime1[w1], 0);*/
				PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
				we.id = this.bullet_type1[w1];
				we.model = this.bullet_model1[w1];
				we.tex = this.bullet_tex1[w1];
				we.modelf = this.fire_model1[w1];
				we.texf = this.fire_tex1[w1];
				we.ftime = this.fire_time1[w1];
				if(this.sound_fire1[w1] != null)we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1]));
				
				we.f = side;
				we.w = this.fire_pointx1[w1];
				we.h = this.fire_pointy1[w1];
				we.z = this.fire_pointz1[w1];
				we.bx = this.basis_pointx1[w1];
				we.by = this.basis_pointy1[w1];
				we.bz = this.basis_pointz1[w1];
				we.yoffset = this.fire_yoffset1[w1];
				we.px = x;
				we.py = this.posY;
				we.pz = z;
				we.w = this.fire_pointx1[w1];
				we.lock = looked;
				we.lock_player = this.rotationfollowing1[w1];
				we.lock_pitch_vehicle = this.rotation_lock_pitch_vehicle1[w1];
				we.rote = rotefire;
				we.maxy = 0;
				we.miny = 0;
				
				we.power = this.bullet_damage1[w1];
				we.we_tgfx = this.weapon_tgfx1[w1];
				
				we.speed = this.bullet_speed1[w1];
				we.bure = this.bullet_bure1[w1];
				we.ex = this.bullet_expower1[w1];
				we.extrue = this.bullet_ex1[w1];
				we.kazu = this.bullet_kazu1[w1];
				we.gra = this.bullet_gravity1[w1];
				we.maxtime = this.bullet_livingtime1[w1];
				we.dameid = 0;
				float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
				if(f <= this.w1kakumax && f >= this.w1kakumin) {
					boolean canfire = true;
					if(((we.id == 11 || we.id == 12)) && this.spg_mode) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else if((we.id == 13 || we.id == 14)) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else {
						we.WeaponAttack();
					}
					shouhi = canfire;
				}
				
			}
			if(shouhi) {
				this.gun_count1 = 0;
				this.setRemain_L(this.getRemain_L() -1);
				++countlimit1;
			}
		}
		if(countlimit1 >= w1barst){
			this.gun_count1 = 0;
			this.counter1 = false;
			this.countlimit1 = 0;
		}
    }
    
    public void weapon11active(Vec3d looked, EntityPlayer entitylivingbase) {
    	if(this.counter1 && gun_count1 >= w1cycle && this.getRemain_L() > 0){
    		boolean shouhi = false;
			for(int w11 = 0; w11 < weapon11; ++w11) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing11[w11]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotation_player11[w11]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint11[w11]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_firepointbxbz11[w11]) {
					double xx11 = 0;
					double zz11 = 0;
					xx11 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w11];
					zz11 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w11];
					xx11 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w11];
					zz11 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w11];
					x = this.posX + xx11;
					z = this.posZ + zz11;
				}
				float side = - 1.57F;
				if(this.w1crossfire) {
					if(this.getRemain_L() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
				we.id = this.bullet_type11[w11];
				we.model = this.bullet_model11[w11];
				we.tex = this.bullet_tex11[w11];
				we.modelf = this.fire_model11[w11];
				we.texf = this.fire_tex11[w11];
				we.ftime = this.fire_time11[w11];
				if(this.sound_fire11[w11] != null)we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire11[w11]));
				
				we.f = side;
				we.w = this.fire_pointx11[w11];
				we.h = this.fire_pointy11[w11];
				we.z = this.fire_pointz11[w11];
				we.bx = this.basis_pointx11[w11];
				we.by = this.basis_pointy11[w11];
				we.bz = this.basis_pointz11[w11];
				we.yoffset = this.fire_yoffset11[w11];
				we.px = x;
				we.py = this.posY;
				we.pz = z;
				we.w = this.fire_pointx11[w11];
				we.lock = looked;
				we.lock_player = this.rotationfollowing11[w11];
				we.lock_pitch_vehicle = this.rotation_lock_pitch_vehicle11[w11];
				we.rote = rotefire;
				we.maxy = 0;
				we.miny = 0;
				
				we.power = this.bullet_damage11[w11];
				we.we_tgfx = this.weapon_tgfx11[w11];
				
				we.speed = this.bullet_speed11[w11];
				we.bure = this.bullet_bure11[w11];
				we.ex = this.bullet_expower11[w11];
				we.extrue = this.bullet_ex11[w11];
				we.kazu = this.bullet_kazu11[w11];
				we.gra = this.bullet_gravity11[w11];
				we.maxtime = this.bullet_livingtime11[w11];
				we.dameid = 0;
				float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
				if(f <= this.w1kakumax && f >= this.w1kakumin) {
					boolean canfire = true;
					if((we.id == 11 || we.id == 12) && this.spg_mode) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else if((we.id == 13 || we.id == 14)) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else {
						we.WeaponAttack();
					}
					shouhi = canfire;
				}
			}
			if(shouhi) {
				this.gun_count1 = 0;
				this.setRemain_L(this.getRemain_L() -1);
				++countlimit1;
			}
		}
		if(countlimit1 >= w1barst){
			this.gun_count1 = 0;
			this.counter1 = false;
			this.countlimit1 = 0;
		}
    }
    
    public void weapon12active(Vec3d looked, EntityPlayer entitylivingbase) {
    	if(this.counter1 && gun_count1 >= w1cycle && this.getRemain_L() > 0){
    		boolean shouhi = false;
			for(int w12 = 0; w12 < weapon12; ++w12) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				
				if(this.rotationfollowing12[w12]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotation_player12[w12]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint12[w12]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_firepointbxbz12[w12]) {
					double xx12 = 0;
					double zz12 = 0;
					xx12 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w12];
					zz12 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w12];
					xx12 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w12];
					zz12 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w12];
					x = this.posX + xx12;
					z = this.posZ + zz12;
				}
				float side = - 1.57F;
				if(this.w1crossfire) {
					if(this.getRemain_L() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				/*PL_Weapon.WeaponAttack(this, this.bullet_type12[w12], entitylivingbase, 
						this.bullet_model12[w12],this.bullet_tex12[w12],this.fire_model12[w12],this.fire_tex12[w12], this.fire_time12[w12],
						SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire12[w12])), 
						side, this.fire_pointx12[w12], this.fire_pointy12[w12], this.fire_pointz12[w12], this.fire_yoffset12[w12],
						x, this.posY, z, looked, rotefire,0,0,
						this.bullet_damage12[w12], this.bullet_speed12[w12], this.bullet_bure12[w12], 
						this.bullet_expower12[w12], this.bullet_ex12[w12], 
						this.bullet_kazu12[w12], this.bullet_gravity12[w12], this.bullet_livingtime12[w12], 0);*/
				PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
				we.id = this.bullet_type12[w12];
				we.model = this.bullet_model12[w12];
				we.tex = this.bullet_tex12[w12];
				we.modelf = this.fire_model12[w12];
				we.texf = this.fire_tex12[w12];
				we.ftime = this.fire_time12[w12];
				if(this.sound_fire12[w12] != null)we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire12[w12]));
				
				we.f = side;
				we.w = this.fire_pointx12[w12];
				we.h = this.fire_pointy12[w12];
				we.z = this.fire_pointz12[w12];
				we.bx = this.basis_pointx12[w12];
				we.by = this.basis_pointy12[w12];
				we.bz = this.basis_pointz12[w12];
				we.yoffset = this.fire_yoffset12[w12];
				we.px = x;
				we.py = this.posY;
				we.pz = z;
				we.w = this.fire_pointx12[w12];
				we.lock = looked;
				we.lock_player = this.rotationfollowing12[w12];
				we.lock_pitch_vehicle = this.rotation_lock_pitch_vehicle12[w12];
				we.rote = rotefire;
				we.maxy = 0;
				we.miny = 0;
				
				we.power = this.bullet_damage12[w12];
				we.we_tgfx = this.weapon_tgfx12[w12];
				
				we.speed = this.bullet_speed12[w12];
				we.bure = this.bullet_bure12[w12];
				we.ex = this.bullet_expower12[w12];
				we.extrue = this.bullet_ex12[w12];
				we.kazu = this.bullet_kazu12[w12];
				we.gra = this.bullet_gravity12[w12];
				we.maxtime = this.bullet_livingtime12[w12];
				we.dameid = 0;
				float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
				if(f <= this.w1kakumax && f >= this.w1kakumin) {
					boolean canfire = true;
					if((we.id == 11 || we.id == 12) && this.spg_mode) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else if((we.id == 13 || we.id == 14)) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else {
						we.WeaponAttack();
					}
					shouhi = canfire;
				}
			}
			if(shouhi) {
				this.gun_count1 = 0;
				this.setRemain_L(this.getRemain_L() -1);
				++countlimit1;
			}
		}
		if(countlimit1 >= w1barst){
			this.gun_count1 = 0;
			this.counter1 = false;
			this.countlimit1 = 0;
		}
    }
    
    public void weapon2active(Vec3d looked, EntityPlayer entitylivingbase) {
    	if(this.counter2 && gun_count2 >= w2cycle && this.getRemain_R() > 0){
    		boolean shouhi = false;
			for(int w2 = 0; w2 < weapon2; ++w2) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing2[w2]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotation_player2[w2]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint2[w2]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_firepointbxbz2[w2]) {
					double xx2 = 0;
					double zz2 = 0;
					xx2 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w2];
					zz2 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w2];
					xx2 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w2];
					zz2 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w2];
					x = this.posX + xx2;
					z = this.posZ + zz2;
				}
				float side = - 1.57F;
				if(this.w2crossfire) {
					if(this.getRemain_R() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				/*PL_Weapon.WeaponAttack(this, this.bullet_type2[w2], entitylivingbase, 
						this.bullet_model2[w2],this.bullet_tex2[w2],this.fire_model2[w2],this.fire_tex2[w2], this.fire_time2[w2],
						SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire2[w2])), 
						side, this.fire_pointx2[w2], this.fire_pointy2[w2], this.fire_pointz2[w2], this.fire_yoffset2[w2],
						x, this.posY, z, looked, rotefire,0,0,
						this.bullet_damage2[w2], this.bullet_speed2[w2], this.bullet_bure2[w2], 
						this.bullet_expower2[w2], this.bullet_ex2[w2], 
						this.bullet_kazu2[w2], this.bullet_gravity2[w2], this.bullet_livingtime2[w2], 0);*/
				PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
				we.id = this.bullet_type2[w2];
				we.model = this.bullet_model2[w2];
				we.tex = this.bullet_tex2[w2];
				we.modelf = this.fire_model2[w2];
				we.texf = this.fire_tex2[w2];
				we.ftime = this.fire_time2[w2];
				if(this.sound_fire2[w2] != null)we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire2[w2]));
				
				we.f = side;
				we.w = this.fire_pointx2[w2];
				we.h = this.fire_pointy2[w2];
				we.z = this.fire_pointz2[w2];
				we.bx = this.basis_pointx2[w2];
				we.by = this.basis_pointy2[w2];
				we.bz = this.basis_pointz2[w2];
				we.yoffset = this.fire_yoffset2[w2];
				we.px = x;
				we.py = this.posY;
				we.pz = z;
				we.w = this.fire_pointx2[w2];
				we.lock = looked;
				we.lock_player = this.rotationfollowing2[w2];
				we.lock_pitch_vehicle = this.rotation_lock_pitch_vehicle2[w2];
				we.rote = rotefire;
				we.maxy = 0;
				we.miny = 0;
				
				we.power = this.bullet_damage2[w2];
				we.we_tgfx = this.weapon_tgfx2[w2];
				
				we.speed = this.bullet_speed2[w2];
				we.bure = this.bullet_bure2[w2];
				we.ex = this.bullet_expower2[w2];
				we.extrue = this.bullet_ex2[w2];
				we.kazu = this.bullet_kazu2[w2];
				we.gra = this.bullet_gravity2[w2];
				we.maxtime = this.bullet_livingtime2[w2];
				we.dameid = 0;
				float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
				if(f <= this.w2kakumax && f >= this.w2kakumin) {
					boolean canfire = true;
					if((we.id == 11 || we.id == 12) && this.spg_mode) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else if((we.id == 13 || we.id == 14)) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else {
						we.WeaponAttack();
					}
					shouhi = canfire;
				}
			}
			if(shouhi) {
				this.gun_count2 = 0;
				this.setRemain_R(this.getRemain_R() -1);
				++countlimit2;
			}
		}
		if(countlimit2 >= w2barst){
			this.gun_count2 = 0;
			this.counter2 = false;
			this.countlimit2 = 0;
		}
    }
    
    public void weapon3active(Vec3d looked, EntityPlayer entitylivingbase) {
    	if(this.counter3 && gun_count3 >= w3cycle && this.getRemain_A() > 0){
    		boolean shouhi = false;
			for(int w3 = 0; w3 < weapon3; ++w3) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing3[w3]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotation_player3[w3]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint3[w3]) {
					double xx3 = 0;
					double zz3 = 0;
					xx3 -= MathHelper.sin(this.rotationYaw * 0.01735329252F) * this.arm_z[w3];
					zz3 += MathHelper.cos(this.rotationYaw * 0.01735329252F) * this.arm_z[w3];
					xx3 -= MathHelper.sin(this.rotationYaw * 0.01735329252F  -1.57F) * this.arm_x[w3];
					zz3 += MathHelper.cos(this.rotationYaw * 0.01735329252F -1.57F) * this.arm_x[w3];
					x = this.posX + xx3;
					z = this.posZ + zz3;
				}
				if(this.rotation_firepointbxbz3[w3]) {
					x = this.basis_pointx3[w3];
					z = this.basis_pointz3[w3];
				}
				float side = - 1.57F;
				if(this.w3crossfire) {
					if(this.getRemain_A() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
				we.id = this.bullet_type3[w3];
				we.model = this.bullet_model3[w3];
				we.tex = this.bullet_tex3[w3];
				we.modelf = this.fire_model3[w3];
				we.texf = this.fire_tex3[w3];
				we.ftime = this.fire_time3[w3];
				if(this.sound_fire3[w3] != null)we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire3[w3]));
				
				we.f = side;
				we.w = this.fire_pointx3[w3];
				we.h = this.fire_pointy3[w3];
				we.z = this.fire_pointz3[w3];
				we.bx = this.basis_pointx3[w3];
				we.by = this.basis_pointy3[w3];
				we.bz = this.basis_pointz3[w3];
				we.xoffset = this.fire_xoffset3[w3];
				we.yoffset = this.fire_yoffset3[w3];
				we.px = x;
				we.py = this.posY;
				we.pz = z;
				we.w = this.fire_pointx3[w3];
				we.lock = looked;
				we.lock_player = this.rotationfollowing3[w3];
				we.lock_pitch_vehicle = this.rotation_lock_pitch_vehicle3[w3];
				we.rote = rotefire;
				we.maxy = 0;
				we.miny = 0;
				
				we.power = this.bullet_damage3[w3];
				we.we_tgfx = this.weapon_tgfx3[w3];
				
				we.speed = this.bullet_speed3[w3];
				we.bure = this.bullet_bure3[w3];
				we.ex = this.bullet_expower3[w3];
				we.exfire = this.bullet_exfire3[w3];
				we.exsmoke = this.bullet_exsmoke3[w3];
				we.exflash = this.bullet_exflash3[w3];
				we.extrue = this.bullet_ex3[w3];
				we.kazu = this.bullet_kazu3[w3];
				we.gra = this.bullet_gravity3[w3];
				we.maxtime = this.bullet_livingtime3[w3];
				we.dameid = 0;
				float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
				if(f <= this.w3kakumax && f >= this.w3kakumin) {
					boolean canfire = true;
					if((we.id == 11 || we.id == 12) && this.spg_mode) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else if((we.id == 13 || we.id == 14)) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else {
						we.WeaponAttack();
					}
					shouhi = canfire;
				}
			}
			if(shouhi) {
				this.gun_count3 = 0;
				this.setRemain_A(this.getRemain_A() -1);
				++countlimit3;
			}
		}
		if(countlimit3 >= w3barst){
			this.gun_count3 = 0;
			this.counter3 = false;
			this.countlimit3 = 0;
		}
    }
    
    
    
    public void weapon4active(Vec3d looked, EntityPlayer entitylivingbase) {
    	if(this.counter4 && gun_count4 >= w4cycle && this.getRemain_S() > 0){
    		boolean shouhi = false;
			for(int w4 = 0; w4 < weapon4; ++w4) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing4[w4]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotation_player4[w4]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint4[w4]) {
					double xx4 = 0;
					double zz4 = 0;
					xx4 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w4];
					zz4 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w4];
					xx4 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w4];
					zz4 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w4];
					x = this.posX + xx4;
					z = this.posZ + zz4;
				}
				if(this.rotation_firepointbxbz4[w4]) {
					x = this.basis_pointx4[w4];
					z = this.basis_pointz4[w4];
				}
				float side = - 1.57F;
				if(this.w4crossfire) {
					if(this.getRemain_S() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				PL_Weapon_new we = new PL_Weapon_new(this, entitylivingbase);
				we.id = this.bullet_type4[w4];
				we.model = this.bullet_model4[w4];
				we.tex = this.bullet_tex4[w4];
				we.modelf = this.fire_model4[w4];
				we.texf = this.fire_tex4[w4];
				we.ftime = this.fire_time4[w4];
				if(this.sound_fire4[w4] != null)we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire4[w4]));
				
				we.f = side;
				we.w = this.fire_pointx4[w4];
				we.h = this.fire_pointy4[w4];
				we.z = this.fire_pointz4[w4];
				we.bx = this.basis_pointx4[w4];
				we.by = this.basis_pointy4[w4];
				we.bz = this.basis_pointz4[w4];
				we.xoffset = this.fire_xoffset4[w4];
				we.yoffset = this.fire_yoffset4[w4];
				we.px = x;
				we.py = this.posY;
				we.pz = z;
				we.w = this.fire_pointx4[w4];
				we.lock = looked;
				we.lock_player = this.rotationfollowing4[w4];
				we.lock_pitch_vehicle = this.rotation_lock_pitch_vehicle4[w4];
				we.rote = rotefire;
				we.maxy = 0;
				we.miny = 0;
				
				we.power = this.bullet_damage4[w4];
				we.we_tgfx = this.weapon_tgfx4[w4];
				
				we.speed = this.bullet_speed4[w4];
				we.bure = this.bullet_bure4[w4];
				we.ex = this.bullet_expower4[w4];
				we.exfire = this.bullet_exfire4[w4];
				we.exsmoke = this.bullet_exsmoke4[w4];
				we.exflash = this.bullet_exflash4[w4];
				we.extrue = this.bullet_ex4[w4];
				we.kazu = this.bullet_kazu4[w4];
				we.gra = this.bullet_gravity4[w4];
				we.maxtime = this.bullet_livingtime4[w4];
				we.dameid = 0;
				float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
				if(f <= this.w4kakumax && f >= this.w4kakumin) {
					boolean canfire = true;
					if((we.id == 11 || we.id == 12) && this.spg_mode) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else if((we.id == 13 || we.id == 14)) {
						we.spg_target_x = (float) (this.posX + this.spg_yaw);
						we.spg_target_z = (float) (this.posZ + this.spg_picth);
						we.spg_target_y = (float) (this.posY);
						{
							double d5 = we.spg_target_x - x;
							double d7 = we.spg_target_z - z;
					        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
					        if(d3 >= this.spg_min_range) {
					        	we.WeaponAttackSPG();
					        }else {
					        	canfire = false;
					        	if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
					        }
						}
					}
					else {
						we.WeaponAttack();
					}
					shouhi = canfire;
				}
			}
			if(shouhi) {
				this.gun_count4 = 0;
				this.setRemain_S(this.getRemain_S() -1);
				++countlimit4;
			}
		}
		if(countlimit4 >= w4barst){
			this.gun_count4 = 0;
			this.counter4 = false;
			this.countlimit4 = 0;
		}
    }
    
    public void weapon1activeMob(Vec3d looked, EntityGVCLivingBase entitylivingbase, int id) {
    	if(this.counter1 && gun_count1 >= w1cycle && this.getRemain_L() > 0){
    		boolean shouhi = false;
			for(int w1 = 0; w1 < weapon1; ++w1) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing1[w1]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotation_player1[w1]) {
					rotefire = this.rotation;
				}else {
					rotefire = this.rotationYaw;
				}
				if(this.rotationfirepoint1[w1]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_mob_head1[w1]) {
					rotefire = entitylivingbase.rotationYawHead;
				}
				if(this.rotation_firepointbxbz1[w1]) {
					double xx1 = 0;
					double zz1 = 0;
					xx1 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w1];
					zz1 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w1];
					xx1 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w1];
					zz1 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w1];
					x = this.posX + xx1;
					z = this.posZ + zz1;
				}
				float side = - 1.57F;
				if(this.w1crossfire) {
					if(this.getRemain_L() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				if(id == 1) {
					/*AI_EntityWeapon.AttacktaskAir(entitylivingbase, this, this.bullet_type1[w1], this.mob_w1range,
							this.bullet_model1[w1],this.bullet_tex1[w1],this.fire_model1[w1],this.fire_tex1[w1], this.fire_time1[w1],
							SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1])), 
							side, this.fire_pointx1[w1], this.fire_pointy1[w1], this.fire_pointz1[w1],
							x, this.posY, z, looked, rotefire,this.mob_w1range_max_y,this.mob_w1range_min_y,
							this.bullet_damage1[w1], this.bullet_speed1[w1], this.bullet_bure1[w1], 
							this.bullet_expower1[w1], this.bullet_ex1[w1], 
							this.bullet_kazu1[w1], this.bullet_gravity1[w1], this.bullet_livingtime1[w1], 0);*/
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type1[w1];
					we.model = this.bullet_model1[w1];
					we.tex = this.bullet_tex1[w1];
					we.modelf = this.fire_model1[w1];
					we.texf = this.fire_tex1[w1];
					we.ftime = this.fire_time1[w1];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1]));
					
					we.f = side;
					we.w = this.fire_pointx1[w1];
					we.h = this.fire_pointy1[w1];
					we.z = this.fire_pointz1[w1];
					we.bx = this.basis_pointx1[w1];
					we.by = this.basis_pointy1[w1];
					we.bz = this.basis_pointz1[w1];
					we.yoffset = this.fire_yoffset1[w1];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					
					we.follow_rote = true;//向いている方向に
					
					we.maxy = mob_w1range_max_y;
					we.miny = mob_w1range_min_y;
					
					we.power = this.bullet_damage1[w1];
					we.we_tgfx = this.weapon_tgfx1[w1];
					
					we.speed = this.bullet_speed1[w1];
					we.bure = this.bullet_bure1[w1];
					we.ex = this.bullet_expower1[w1];
					we.extrue = this.bullet_ex1[w1];
					we.kazu = this.bullet_kazu1[w1];
					we.gra = this.bullet_gravity1[w1];
					we.maxtime = this.bullet_livingtime1[w1];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w1kakumax && f >= this.w1kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskAir(gvcentity, this, mob_w1range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w1range);
						}
						shouhi = true;
					}
				}
				else if(id == 3) {
					float bsp = (float)Math.tan((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) * 10;
					/*AI_EntityWeapon.AttacktaskB(entitylivingbase, this, this.bullet_type1[w1], this.mob_w1range,
							this.bullet_model1[w1],this.bullet_tex1[w1],this.fire_model1[w1],this.fire_tex1[w1], this.fire_time1[w1],
							SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1])), 
							side, this.fire_pointx1[w1], this.fire_pointy1[w1], this.fire_pointz1[w1],
							x, this.posY, z, looked, rotefire,this.mob_w1range_max_y,this.mob_w1range_min_y,
							this.bullet_damage1[w1], bsp, this.bullet_bure1[w1], 
							this.bullet_expower1[w1], this.bullet_ex1[w1], 
							this.bullet_kazu1[w1], this.bullet_gravity1[w1], this.bullet_livingtime1[w1], 0);*/
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type1[w1];
					we.model = this.bullet_model1[w1];
					we.tex = this.bullet_tex1[w1];
					we.modelf = this.fire_model1[w1];
					we.texf = this.fire_tex1[w1];
					we.ftime = this.fire_time1[w1];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1]));
					
					we.f = side;
					we.w = this.fire_pointx1[w1];
					we.h = this.fire_pointy1[w1];
					we.z = this.fire_pointz1[w1];
					we.bx = this.basis_pointx1[w1];
					we.by = this.basis_pointy1[w1];
					we.bz = this.basis_pointz1[w1];
					we.yoffset = this.fire_yoffset1[w1];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					
					we.follow_rote = this.rotationfollowing1[w1];//向いている方向に
					
					we.maxy = mob_w1range_max_y;
					we.miny = mob_w1range_min_y;
					
					we.power = this.bullet_damage1[w1];
					we.we_tgfx = this.weapon_tgfx1[w1];
					
					we.speed = bsp;
					we.bure = this.bullet_bure1[w1];
					we.ex = this.bullet_expower1[w1];
					we.extrue = this.bullet_ex1[w1];
					we.kazu = this.bullet_kazu1[w1];
					we.gra = this.bullet_gravity1[w1];
					we.maxtime = this.bullet_livingtime1[w1];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w1kakumax && f >= this.w1kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskB(gvcentity, this, mob_w1range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w1range);
						}
						shouhi = true;
					}
				}
				else {
					/*AI_EntityWeapon.Attacktask(entitylivingbase, this, this.bullet_type1[w1], this.mob_w1range,
							this.bullet_model1[w1],this.bullet_tex1[w1],this.fire_model1[w1],this.fire_tex1[w1], this.fire_time1[w1],
							SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1])), 
							side, this.fire_pointx1[w1], this.fire_pointy1[w1], this.fire_pointz1[w1],
							x, this.posY, z, looked, rotefire,this.mob_w1range_max_y,this.mob_w1range_min_y,
							this.bullet_damage1[w1], this.bullet_speed1[w1], this.bullet_bure1[w1], 
							this.bullet_expower1[w1], this.bullet_ex1[w1], 
							this.bullet_kazu1[w1], this.bullet_gravity1[w1], this.bullet_livingtime1[w1], 0);*/
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type1[w1];
					we.model = this.bullet_model1[w1];
					we.tex = this.bullet_tex1[w1];
					we.modelf = this.fire_model1[w1];
					we.texf = this.fire_tex1[w1];
					we.ftime = this.fire_time1[w1];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire1[w1]));
					
					we.f = side;
					we.w = this.fire_pointx1[w1];
					we.h = this.fire_pointy1[w1];
					we.z = this.fire_pointz1[w1];
					we.bx = this.basis_pointx1[w1];
					we.by = this.basis_pointy1[w1];
					we.bz = this.basis_pointz1[w1];
					we.yoffset = this.fire_yoffset1[w1];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					
					we.follow_rote = this.rotationfollowing1[w1];//向いている方向に
					
					we.maxy = mob_w1range_max_y;
					we.miny = mob_w1range_min_y;
					
					we.power = this.bullet_damage1[w1];
					we.we_tgfx = this.weapon_tgfx1[w1];
					
					we.speed = this.bullet_speed1[w1];
					we.bure = this.bullet_bure1[w1];
					we.ex = this.bullet_expower1[w1];
					we.extrue = this.bullet_ex1[w1];
					we.kazu = this.bullet_kazu1[w1];
					we.gra = this.bullet_gravity1[w1];
					we.maxtime = this.bullet_livingtime1[w1];
					we.dameid = 0;
					
					we.mob_min_range = this.mob_min_range;
					
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w1kakumax && f >= this.w1kakumin) {
				//		we.Attacktask(entitylivingbase, this, mob_w1range);
						boolean canfire = true;
						if((we.id == 11 || we.id == 12) || (we.id == 13 || we.id == 14)) {
							if(entitylivingbase instanceof EntityGVCLivingBase) {
								EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
								double d5 = gvcentity.targetentity.posX - x;
								double d7 = gvcentity.targetentity.posZ - z;
						        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
						        if(d3 >= this.spg_min_range) {
						        	we.Attacktask(gvcentity, this, mob_w1range);
						        }else {
						        	canfire = false;
						        	//if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
						        }
							}else {
								double d5 = this.targetentity.posX - x;
								double d7 = this.targetentity.posZ - z;
						        double d3 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7) * 1;
						        if(d3 >= this.spg_min_range) {
						        	we.Attacktask_LivingBase(entitylivingbase, this, mob_w1range);
						        }else {
						        	canfire = false;
						        	//if(entitylivingbase.world.isRemote)entitylivingbase.sendMessage(new TextComponentTranslation("Too close to Fire!", new Object[0]));
						        }
							}
						}
						else {
							if(entitylivingbase instanceof EntityGVCLivingBase) {
								EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
								we.Attacktask(gvcentity, this, mob_w1range);
							}else {
								we.Attacktask_LivingBase(entitylivingbase, this, mob_w1range);
							}
						}
						shouhi = canfire;
					}
					
				}
				
				/*AI_EntityWeapon.Attacktask(entitylivingbase, this, 2, 50, 
						"gvclib:textures/entity/bulletcannon.mqo","gvclib:textures/entity/bulletcannon.png",
						"gvclib:textures/entity/msmoke.mqo","gvclib:textures/entity/msmoke.png",16,GVCSoundEvent.fire_cannon, 
						0F, 0.0, 2.15, 1.0, entitylivingbase.posX, this.posY, entitylivingbase.posZ,looked,this.rotation,15,3,
						30, 2.0F, 1F, 2.2F, false, 1, 0.020D, 80, 0);//1.57/1.5/3*/
			}
			if(shouhi) {
				this.gun_count1 = 0;
				if(entitylivingbase instanceof EntityGVCLivingBase) {
					EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
					if(gvcentity.firetrue){
						this.setRemain_L(this.getRemain_L() -1);
					}
				}else {
					if(this.firetrue){
						this.setRemain_L(this.getRemain_L() -1);
					}
				}
				++countlimit1;
			}
		}
		if(countlimit1 >= w1barst){
			this.gun_count1 = 0;
			this.counter1 = false;
			this.countlimit1 = 0;
		}
    }
    
    public void weapon2activeMob(Vec3d looked, EntityGVCLivingBase entitylivingbase, int id) {
    	if(this.counter2 && gun_count2 >= w2cycle && this.getRemain_R() > 0){
    		boolean shouhi = false;
			for(int w2 = 0; w2 < weapon2; ++w2) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing2[w2]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotationfirepoint2[w2]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_mob_head2[w2]) {
					rotefire = entitylivingbase.rotationYawHead;
				}
				if(this.rotation_firepointbxbz2[w2]) {
					double xx2 = 0;
					double zz2 = 0;
					xx2 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w2];
					zz2 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w2];
					xx2 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w2];
					zz2 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w2];
					x = this.posX + xx2;
					z = this.posZ + zz2;
				}
				float side = - 1.57F;
				if(this.w2crossfire) {
					if(this.getRemain_R() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				if(id == 1) {
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type2[w2];
					we.model = this.bullet_model2[w2];
					we.tex = this.bullet_tex2[w2];
					we.modelf = this.fire_model2[w2];
					we.texf = this.fire_tex2[w2];
					we.ftime = this.fire_time2[w2];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire2[w2]));
					
					we.f = side;
					we.w = this.fire_pointx2[w2];
					we.h = this.fire_pointy2[w2];
					we.z = this.fire_pointz2[w2];
					we.bx = this.basis_pointx2[w2];
					we.by = this.basis_pointy2[w2];
					we.bz = this.basis_pointz2[w2];
					we.yoffset = this.fire_yoffset2[w2];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w2range_max_y;
					we.miny = mob_w2range_min_y;
					
					we.power = this.bullet_damage2[w2];
					we.we_tgfx = this.weapon_tgfx2[w2];
					
					we.speed = this.bullet_speed2[w2];
					we.bure = this.bullet_bure2[w2];
					we.ex = this.bullet_expower2[w2];
					we.extrue = this.bullet_ex2[w2];
					we.kazu = this.bullet_kazu2[w2];
					we.gra = this.bullet_gravity2[w2];
					we.maxtime = this.bullet_livingtime2[w2];
					we.dameid = 0;
					
					we.follow_rote = this.rotationfollowing2[w2];//向いている方向に
					
					we.mob_min_range = this.mob_min_range;
					
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w2kakumax && f >= this.w2kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskAir(gvcentity, this, mob_w2range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w2range);
						}
						shouhi = true;
					}
					
				}
				else if(id == 3) {
					float bsp = (float)Math.tan((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) * 4;
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type2[w2];
					we.model = this.bullet_model2[w2];
					we.tex = this.bullet_tex2[w2];
					we.modelf = this.fire_model2[w2];
					we.texf = this.fire_tex2[w2];
					we.ftime = this.fire_time2[w2];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire2[w2]));
					
					we.f = side;
					we.w = this.fire_pointx2[w2];
					we.h = this.fire_pointy2[w2];
					we.z = this.fire_pointz2[w2];
					we.bx = this.basis_pointx2[w2];
					we.by = this.basis_pointy2[w2];
					we.bz = this.basis_pointz2[w2];
					we.yoffset = this.fire_yoffset2[w2];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w2range_max_y;
					we.miny = mob_w2range_min_y;
					
					we.follow_rote = this.rotationfollowing2[w2];//向いている方向に
					
					we.power = this.bullet_damage2[w2];
					we.we_tgfx = this.weapon_tgfx2[w2];
					we.speed = bsp;
					we.bure = this.bullet_bure2[w2];
					we.ex = this.bullet_expower2[w2];
					we.extrue = this.bullet_ex2[w2];
					we.kazu = this.bullet_kazu2[w2];
					we.gra = this.bullet_gravity2[w2];
					we.maxtime = this.bullet_livingtime2[w2];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w2kakumax && f >= this.w2kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskB(gvcentity, this, mob_w2range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w2range);
						}
						shouhi = true;
					}
					
				}
				else {
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type2[w2];
					we.model = this.bullet_model2[w2];
					we.tex = this.bullet_tex2[w2];
					we.modelf = this.fire_model2[w2];
					we.texf = this.fire_tex2[w2];
					we.ftime = this.fire_time2[w2];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire2[w2]));
					
					we.f = side;
					we.w = this.fire_pointx2[w2];
					we.h = this.fire_pointy2[w2];
					we.z = this.fire_pointz2[w2];
					we.bx = this.basis_pointx2[w2];
					we.by = this.basis_pointy2[w2];
					we.bz = this.basis_pointz2[w2];
					we.yoffset = this.fire_yoffset2[w2];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w2range_max_y;
					we.miny = mob_w2range_min_y;
					
					we.follow_rote = this.rotationfollowing2[w2];//向いている方向に
					
					we.power = this.bullet_damage2[w2];
					we.we_tgfx = this.weapon_tgfx2[w2];
					we.speed = this.bullet_speed2[w2];
					we.bure = this.bullet_bure2[w2];
					we.ex = this.bullet_expower2[w2];
					we.extrue = this.bullet_ex2[w2];
					we.kazu = this.bullet_kazu2[w2];
					we.gra = this.bullet_gravity2[w2];
					we.maxtime = this.bullet_livingtime2[w2];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w2kakumax && f >= this.w2kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.Attacktask(gvcentity, this, mob_w2range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w2range);
						}
						shouhi = true;
					}
					
				}
			}
			if(shouhi) {
				this.gun_count2 = 0;
				if(entitylivingbase instanceof EntityGVCLivingBase) {
					EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
					if(gvcentity.firetrue){
						this.setRemain_R(this.getRemain_R() -1);
					}
				}else {
					if(this.firetrue){
						this.setRemain_R(this.getRemain_R() -1);
					}
				}
				++countlimit2;
			}
		}
		if(countlimit2 >= w2barst){
			this.gun_count2 = 0;
			this.counter2 = false;
			this.countlimit2 = 0;
		}
    }
    
    public void weapon3activeMob(Vec3d looked, EntityGVCLivingBase entitylivingbase, int id) {
    	if(this.counter3 && gun_count3 >= w3cycle && this.getRemain_A() > 0){
    		boolean shouhi = false;
			for(int w3 = 0; w3 < weapon3; ++w3) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing3[w3]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotationfirepoint3[w3]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_mob_head3[w3]) {
					rotefire = entitylivingbase.rotationYawHead;
				}
				if(this.rotation_firepointbxbz3[w3]) {
					double xx3 = 0;
					double zz3 = 0;
					xx3 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w3];
					zz3 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w3];
					xx3 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w3];
					zz3 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w3];
					x = this.posX + xx3;
					z = this.posZ + zz3;
				}
				float side = - 1.57F;
				if(this.w3crossfire) {
					if(this.getRemain_A() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				if(id == 1) {
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type3[w3];
					we.model = this.bullet_model3[w3];
					we.tex = this.bullet_tex3[w3];
					we.modelf = this.fire_model3[w3];
					we.texf = this.fire_tex3[w3];
					we.ftime = this.fire_time3[w3];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire3[w3]));
					
					we.f = side;
					we.w = this.fire_pointx3[w3];
					we.h = this.fire_pointy3[w3];
					we.z = this.fire_pointz3[w3];
					we.bx = this.basis_pointx3[w3];
					we.by = this.basis_pointy3[w3];
					we.bz = this.basis_pointz3[w3];
					we.yoffset = this.fire_yoffset3[w3];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w3range_max_y;
					we.miny = mob_w3range_min_y;
					
					we.power = this.bullet_damage3[w3];
					we.we_tgfx = this.weapon_tgfx3[w3];
					
					we.speed = this.bullet_speed3[w3];
					we.bure = this.bullet_bure3[w3];
					we.ex = this.bullet_expower3[w3];
					we.extrue = this.bullet_ex3[w3];
					we.kazu = this.bullet_kazu3[w3];
					we.gra = this.bullet_gravity3[w3];
					we.maxtime = this.bullet_livingtime3[w3];
					we.dameid = 0;
					
					we.mob_min_range = this.mob_min_range;
					
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w3kakumax && f >= this.w3kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskAir(gvcentity, this, mob_w3range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w3range);
						}
						shouhi = true;
					}
					
				}
				else if(id == 3) {
					float bsp = (float)Math.tan((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) * 3;
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type3[w3];
					we.model = this.bullet_model3[w3];
					we.tex = this.bullet_tex3[w3];
					we.modelf = this.fire_model3[w3];
					we.texf = this.fire_tex3[w3];
					we.ftime = this.fire_time3[w3];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire3[w3]));
					
					we.f = side;
					we.w = this.fire_pointx3[w3];
					we.h = this.fire_pointy3[w3];
					we.z = this.fire_pointz3[w3];
					we.bx = this.basis_pointx3[w3];
					we.by = this.basis_pointy3[w3];
					we.bz = this.basis_pointz3[w3];
					we.yoffset = this.fire_yoffset3[w3];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w3range_max_y;
					we.miny = mob_w3range_min_y;
					
					we.power = this.bullet_damage3[w3];
					we.we_tgfx = this.weapon_tgfx3[w3];
					
					we.speed = bsp;
					we.bure = this.bullet_bure3[w3];
					we.ex = this.bullet_expower3[w3];
					we.extrue = this.bullet_ex3[w3];
					we.kazu = this.bullet_kazu3[w3];
					we.gra = this.bullet_gravity3[w3];
					we.maxtime = this.bullet_livingtime3[w3];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w3kakumax && f >= this.w3kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskB(gvcentity, this, mob_w3range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w3range);
						}
						shouhi = true;
					}
					
				}
				else {
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type3[w3];
					we.model = this.bullet_model3[w3];
					we.tex = this.bullet_tex3[w3];
					we.modelf = this.fire_model3[w3];
					we.texf = this.fire_tex3[w3];
					we.ftime = this.fire_time3[w3];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire3[w3]));
					
					we.f = side;
					we.w = this.fire_pointx3[w3];
					we.h = this.fire_pointy3[w3];
					we.z = this.fire_pointz3[w3];
					we.bx = this.basis_pointx3[w3];
					we.by = this.basis_pointy3[w3];
					we.bz = this.basis_pointz3[w3];
					we.yoffset = this.fire_yoffset3[w3];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w3range_max_y;
					we.miny = mob_w3range_min_y;
					
					we.power = this.bullet_damage3[w3];
					we.we_tgfx = this.weapon_tgfx3[w3];
					
					we.speed = this.bullet_speed3[w3];
					we.bure = this.bullet_bure3[w3];
					we.ex = this.bullet_expower3[w3];
					we.extrue = this.bullet_ex3[w3];
					we.kazu = this.bullet_kazu3[w3];
					we.gra = this.bullet_gravity3[w3];
					we.maxtime = this.bullet_livingtime3[w3];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w3kakumax && f >= this.w3kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.Attacktask(gvcentity, this, mob_w3range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w3range);
						}
						shouhi = true;
					}
					
				}
			}
			if(shouhi) {
				this.gun_count3 = 0;
				if(entitylivingbase instanceof EntityGVCLivingBase) {
					EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
					if(gvcentity.firetrue){
						this.setRemain_A(this.getRemain_A() -1);
					}
				}else {
					if(this.firetrue){
						this.setRemain_A(this.getRemain_A() -1);
					}
				}
				++countlimit3;
			}
		}
		if(countlimit3 >= w3barst){
			this.gun_count3 = 0;
			this.counter3 = false;
			this.countlimit3 = 0;
		}
    }
    
    public void weapon4activeMob(Vec3d looked, EntityGVCLivingBase entitylivingbase, int id) {
    	if(this.counter4 && gun_count4 >= w4cycle && this.getRemain_S() > 0){
    		boolean shouhi = false;
			for(int w4 = 0; w4 < weapon4; ++w4) {
				float rotefire = this.rotationYawHead;
				double x = this.posX;
				double z = this.posZ;
				looked = this.getLookVec();
				if(this.rotationfollowing4[w4]) {
					looked = entitylivingbase.getLookVec();
				}
				if(this.rotationfirepoint4[w4]) {
					rotefire = this.rotation;
					x = entitylivingbase.posX;
					z = entitylivingbase.posZ;
				}
				if(this.rotation_mob_head4[w4]) {
					rotefire = entitylivingbase.rotationYawHead;
				}
				if(this.rotation_firepointbxbz4[w4]) {
					double xx4 = 0;
					double zz4 = 0;
					xx4 -= MathHelper.sin(this.rotationYaw * 0.01745329252F) * this.arm_z[w4];
					zz4 += MathHelper.cos(this.rotationYaw * 0.01745329252F) * this.arm_z[w4];
					xx4 -= MathHelper.sin(this.rotationYaw * 0.01745329252F  -1.57F) * this.arm_x[w4];
					zz4 += MathHelper.cos(this.rotationYaw * 0.01745329252F -1.57F) * this.arm_x[w4];
					x = this.posX + xx4;
					z = this.posZ + zz4;
				}
				float side = - 1.57F;
				if(this.w4crossfire) {
					if(this.getRemain_S() % 2 == 0)
					{
						side =  1.57F;
					}else {
						side = - 1.57F;
					}
				}
				if(id == 1) {
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type4[w4];
					we.model = this.bullet_model4[w4];
					we.tex = this.bullet_tex4[w4];
					we.modelf = this.fire_model4[w4];
					we.texf = this.fire_tex4[w4];
					we.ftime = this.fire_time4[w4];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire4[w4]));
					
					we.f = side;
					we.w = this.fire_pointx4[w4];
					we.h = this.fire_pointy4[w4];
					we.z = this.fire_pointz4[w4];
					we.bx = this.basis_pointx4[w4];
					we.by = this.basis_pointy4[w4];
					we.bz = this.basis_pointz4[w4];
					we.yoffset = this.fire_yoffset4[w4];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w4range_max_y;
					we.miny = mob_w4range_min_y;
					
					we.power = this.bullet_damage4[w4];
					we.we_tgfx = this.weapon_tgfx4[w4];
					
					we.speed = this.bullet_speed4[w4];
					we.bure = this.bullet_bure4[w4];
					we.ex = this.bullet_expower4[w4];
					we.extrue = this.bullet_ex4[w4];
					we.kazu = this.bullet_kazu4[w4];
					we.gra = this.bullet_gravity4[w4];
					we.maxtime = this.bullet_livingtime4[w4];
					we.dameid = 0;
					
					we.mob_min_range = this.mob_min_range;
					
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w4kakumax && f >= this.w4kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskAir(gvcentity, this, mob_w4range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w4range);
						}
						shouhi = true;
					}
					
				}
				else if(id == 3) {
					float bsp = (float)Math.tan((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) * 4;
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type4[w4];
					we.model = this.bullet_model4[w4];
					we.tex = this.bullet_tex4[w4];
					we.modelf = this.fire_model4[w4];
					we.texf = this.fire_tex4[w4];
					we.ftime = this.fire_time4[w4];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire4[w4]));
					
					we.f = side;
					we.w = this.fire_pointx4[w4];
					we.h = this.fire_pointy4[w4];
					we.z = this.fire_pointz4[w4];
					we.bx = this.basis_pointx4[w4];
					we.by = this.basis_pointy4[w4];
					we.bz = this.basis_pointz4[w4];
					we.yoffset = this.fire_yoffset4[w4];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w4range_max_y;
					we.miny = mob_w4range_min_y;
					
					we.power = this.bullet_damage4[w4];
					we.we_tgfx = this.weapon_tgfx4[w4];
					
					we.speed = bsp;
					we.bure = this.bullet_bure4[w4];
					we.ex = this.bullet_expower4[w4];
					we.extrue = this.bullet_ex4[w4];
					we.kazu = this.bullet_kazu4[w4];
					we.gra = this.bullet_gravity4[w4];
					we.maxtime = this.bullet_livingtime4[w4];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w4kakumax && f >= this.w4kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.AttacktaskB(gvcentity, this, mob_w4range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w4range);
						}
						shouhi = true;
					}
					
				}
				else {
					AI_EntityWeapon_new we = new AI_EntityWeapon_new(this, entitylivingbase);
					we.id = this.bullet_type4[w4];
					we.model = this.bullet_model4[w4];
					we.tex = this.bullet_tex4[w4];
					we.modelf = this.fire_model4[w4];
					we.texf = this.fire_tex4[w4];
					we.ftime = this.fire_time4[w4];
					we.sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(modid, this.sound_fire4[w4]));
					
					we.f = side;
					we.w = this.fire_pointx4[w4];
					we.h = this.fire_pointy4[w4];
					we.z = this.fire_pointz4[w4];
					we.bx = this.basis_pointx4[w4];
					we.by = this.basis_pointy4[w4];
					we.bz = this.basis_pointz4[w4];
					we.yoffset = this.fire_yoffset4[w4];
					we.px = x;
					we.py = this.posY;
					we.pz = z;
					we.lock = looked;
					we.rote = rotefire;
					we.maxy = mob_w4range_max_y;
					we.miny = mob_w4range_min_y;
					
					we.power = this.bullet_damage4[w4];
					we.we_tgfx = this.weapon_tgfx4[w4];
					
					we.speed = this.bullet_speed4[w4];
					we.bure = this.bullet_bure4[w4];
					we.ex = this.bullet_expower4[w4];
					we.extrue = this.bullet_ex4[w4];
					we.kazu = this.bullet_kazu4[w4];
					we.gra = this.bullet_gravity4[w4];
					we.maxtime = this.bullet_livingtime4[w4];
					we.dameid = 0;
					float f = Math.abs(rotefire) - Math.abs(this.rotationYaw);
					if(f <= this.w4kakumax && f >= this.w4kakumin) {
						if(entitylivingbase instanceof EntityGVCLivingBase) {
							EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
							we.Attacktask(gvcentity, this, mob_w4range);
						}else {
							we.Attacktask_LivingBase(entitylivingbase, this, mob_w4range);
						}
						shouhi = true;
					}
					
				}
			}
			if(shouhi) {
				this.gun_count4 = 0;
				/*if(entitylivingbase.firetrue){
					this.setRemain_S(this.getRemain_S() -1);
				}*/
				if(entitylivingbase instanceof EntityGVCLivingBase) {
					EntityGVCLivingBase gvcentity = (EntityGVCLivingBase) entitylivingbase;
					if(gvcentity.firetrue){
						this.setRemain_S(this.getRemain_S() -1);
					}
				}else {
					if(this.firetrue){
						this.setRemain_S(this.getRemain_S() -1);
					}
				}
				++countlimit4;
			}
		}
		if(countlimit4 >= w4barst){
			this.gun_count4 = 0;
			this.counter4 = false;
			this.countlimit4 = 0;
		}
    }
    
    public void catchEntityBiped() {
    	if(this.getHealth() > 0.0F && !this.getVehicleLock()) {
    		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, 
					this.getEntityBoundingBox().grow(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), 
					EntitySelectors.getTeamCollisionPredicate(this));
	        if (!list.isEmpty())
	        {
	            boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);

	            for (int j = 0; j < list.size(); ++j)
	            {
	                Entity entity = list.get(j);

	                if (!entity.isPassenger(this))
	                {
	                    if (flag && this.getPassengers().size() < riddng_maximum && !entity.isRiding() 
	                    		&& entity.width < this.width && entity instanceof EntityGVCLivingBase 
	                    		&& !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer))
	                    {
	                    	{
		                    	EntityGVCLivingBase en = (EntityGVCLivingBase) entity;
		                    	if(en.biped) {
		                    		entity.startRiding(this);
		                    	}
		                    }
	                    }
	                    else
	                    {
	                        this.applyEntityCollision(entity);
	                    }
	                }
	            }
	        }
    	}
    }
    
    public void roadattack() {
    	double dx = Math.abs(this.posX - this.prevPosX);
		double dz = Math.abs(this.posZ - this.prevPosZ);
		float dd = (float)Math.sqrt((dx * dx) + (dz * dz)) * 20;
    	if(dd != 0){
			this.swingArm(EnumHand.MAIN_HAND);
			AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(
					this.posX - this.width, this.posY, this.posZ - this.width, 
					this.posX + this.width, this.posY + this.height, this.posZ + this.width)
	        		.expand(this.width, this.height, this.width);
			List<Entity> llist = this.world.getEntitiesWithinAABBExcludingEntity(this,axisalignedbb2);
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith() && entity1 instanceof EntityLivingBase && entity1 != null) {
						if(entity1 != this && entity1 != this.getControllingPassenger() && entity1 instanceof IMob && !entity1.isRiding()){
							if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
							{
								if(!(this.getControllingPassenger() instanceof IMob))
								{
									entity1.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) this.getControllingPassenger()), 10);
								}
							}
						}
					}
				}
			}
		}
    	if(!this.world.isRemote && roodbreak){
    		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
    		double xx = width;
        		for(double x = 0; x < width*2; ++x) {
        			for(double y = -1; y < height*1.2; ++y) {
        				for(double z = 0; z < width*2; ++z) {
        					BlockPos pos = new BlockPos(this.posX -xx + x, this.posY + y, this.posZ - xx + z);
        					if(this.world.getBlockState(pos).getBlock() instanceof BlockOldLog
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockOldLeaf	
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockPane	
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockLog
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockNewLeaf	
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockPlanks	
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockLilyPad	
        						|| this.world.getBlockState(pos).getBlock() instanceof BlockCactus	
        						//|| this.world.getBlockState(pos).getBlock() instanceof BlockGrass	
        							) 
        					{
        						IBlockState iblockstate = this.world.getBlockState(pos);
        		                Block block = iblockstate.getBlock();
        		                if (iblockstate.getMaterial() != Material.AIR)
        		                {
        		                    block.dropBlockAsItemWithChance(this.world, pos, this.world.getBlockState(pos), 1.0F, 0);
        		                    world.setBlockToAir(pos);
        		                }
        					}
        				}
        			}
        		}
    	}
    	/*if(!this.world.isRemote && roodbreak){
    		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
    		double xx = width;
        		for(double x = 0; x < width*2; ++x) {
        			for(double y = 0; y < height*1; ++y) {
        				for(double z = 0; z < width*2; ++z) {
        					BlockPos pos = new BlockPos(this.posX -xx + x, this.posY + y, this.posZ - xx + z);
        					double dxx = Math.abs(this.posX - this.prevPosX);
        					double dzz = Math.abs(this.posZ - this.prevPosZ);
        					float ddxz = (float)Math.sqrt((dxx * dxx) + (dzz * dzz)) * 20;
        					if(ddxz > 4) {
        						if(this.world.getBlockState(pos).getBlock() instanceof BlockStone) {
        							IBlockState iblockstate = this.world.getBlockState(pos);
            		                Block block = iblockstate.getBlock();
            		                if (iblockstate.getMaterial() != Material.AIR)
            		                {
            		                    block.dropBlockAsItemWithChance(this.world, pos, this.world.getBlockState(pos), 1.0F, 0);
            		                    world.setBlockToAir(pos);
            		                    //this.motionX = this.motionX * 0.1;
            		                    //this.motionZ = this.motionZ * 0.1;
            		                    //this.move(MoverType.PLAYER, this.motionX*0.01, this.motionY, this.motionZ*0.01);
            		                    this.throttle = -throttle * 0.5F;
            		                }
        						}
        					}
        				}
        			}
        		}
    	}*/
    }
    
    protected void CNT_MobRidding() {
    	if (this.canBeSteered() && this.getControllingPassenger() != null && this.getHealth() > 0.0F)
		{
			if(this.getControllingPassenger() instanceof EntityPlayer)
			{
				EntityPlayer entitylivingbase = (EntityPlayer) this.getControllingPassenger();
				boolean kg = mod_GVCLib.proxy.keyg();
				boolean kh = mod_GVCLib.proxy.keyh();
				if (kg) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(15, this.getEntityId()));
				}
				if (kh) {
					GVCLPacketHandler.INSTANCE.sendToServer(new GVCLMessageKeyPressed(18, this.getEntityId()));
				}

				if (this.serverg) {
					for(int m = 0; m < this.getPassengers().size(); ++m) {
						//if (!this.world.isRemote && this.getPassengers().get(m) != null) {
						if (this.getPassengers().get(m) != null) {
							if (this.getPassengers().get(m) instanceof EntityLivingBase
									&& !(this.getPassengers().get(m) instanceof EntityPlayer)) {
								EntityLivingBase entity = (EntityLivingBase)this.getPassengers().get(m);
								entity.dismountRidingEntity();
							}
						}
					}
					this.serverg = false;
				}
				if (this.serverh) {
					{
						List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this,this.getEntityBoundingBox().grow(3D));
						if (!list.isEmpty()) {
							for (int j = 0; j < list.size(); ++j) {
								Entity entity = list.get(j);
								if (!entity.isPassenger(this)) {
									if (this.getPassengers().size() < riddng_maximum && !entity.isRiding()
											&& entity instanceof ISoldier) {
										entity.startRiding(this);
									} else {
										this.applyEntityCollision(entity);
									}
								}
							}
						}
					}
					this.serverh = false;
				}
			}//player
			else if(this.getControllingPassenger() instanceof EntityGVCLivingBase) {
			}
		}
    }
    
    public void CNT_MobRidding_getHead(EntityGVCLivingBase entitylivingbase, double range) {
    	List<Entity> llist = entitylivingbase.world.getEntitiesWithinAABBExcludingEntity(entitylivingbase, entitylivingbase
					.getEntityBoundingBox().expand(entitylivingbase.motionX, entitylivingbase.motionY, entitylivingbase.motionZ).grow(range));
			if (llist != null) {
				for (int lj = 0; lj < llist.size(); lj++) {
					Entity entity1 = (Entity) llist.get(lj);
					if (entity1.canBeCollidedWith()) {
						boolean flag = entitylivingbase.getEntitySenses().canSee(entity1);
						if (entity1 != null && entitylivingbase.targetentity == entity1 && ((EntityLivingBase) entity1).getHealth() > 0.0F) {//target
								double d5 = entity1.posX - entitylivingbase.posX;
								double d7 = entity1.posZ - entitylivingbase.posZ;
								double d6 = entity1.posY - entitylivingbase.posY;
								double d1 = entitylivingbase.posY - (entity1.posY);
								double d3 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7);
								float f11 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
								
								if (flag) {
									entitylivingbase.rotationYawHead = entitylivingbase.rotation = entitylivingbase.rote = entitylivingbase.renderYawOffset 
											= -((float) Math.atan2(d5, d7)) * 180.0F / (float) Math.PI;
									entitylivingbase.rotationp = entitylivingbase.rotationPitch = -f11 + 0;
								}
						}
					}
				}
			}
    }
    
     
    
    
    public void fall(float distance, float damageMultiplier)
    {
    }

    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();
        return entity instanceof EntityLivingBase;
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
        return false;
    }
    
    public void applyEntityCollision(Entity entityIn)
    {
        if (entityIn instanceof EntityVehicleBase)
        {
            if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
            {
                super.applyEntityCollision(entityIn);
            }
        }
        else if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
        {
            super.applyEntityCollision(entityIn);
        }
    }
    
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        {
        	this.setModel_exhaust(compound.getString("Model_exhaust"));
        }
        {
        	this.setTex_exhaust(compound.getString("Tex_exhaust"));
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        compound.setString("Model_exhaust", this.getModel_exhaust());
        compound.setString("Tex_exhaust", this.getTex_exhaust());
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(Model_exhaust, new String("gvclib:textures/marker/exhaust.mqo"));
        this.dataManager.register(Tex_exhaust, new String("gvclib:textures/marker/exhaust.png"));
    }
    
    public String getModel_exhaust() {
		return ((this.dataManager.get(Model_exhaust)));
	}
	public void setModel_exhaust(String s) {
		this.dataManager.set(Model_exhaust, String.valueOf(new String(s)));
	}
	public String getTex_exhaust() {
		return ((this.dataManager.get(Tex_exhaust)));
	}
	public void setTex_exhaust(String s) {
		this.dataManager.set(Tex_exhaust, String.valueOf(new String(s)));
	}
    
    
    //試作
    
    public int motion_time = 0;
    public int motion_time_max = 20;
    
    public int head = 4;
    public int[] head_time = new int[200];
    public float head_xpoint = 0;
    public float head_ypoint = 0;
    public float head_zpoint = 0;
    
    public float[] head_xrote = new float[200];
    public float[] head_yrote = new float[200];
    public float[] head_zrote = new float[200];
    
    public int body = 4;
    public int[] body_time = new int[200];
    public float body_xpoint = 0;
    public float body_ypoint = 0;
    public float body_zpoint = 0;
    
    public float[] body_xrote = new float[200];
    public float[] body_yrote = new float[200];
    public float[] body_zrote = new float[200];
    
    public int waist = 4;
    public int[] waist_time = new int[200];
    public float waist_xpoint = 0;
    public float waist_ypoint = 0;
    public float waist_zpoint = 0;
    
    public float[] waist_xrote = new float[200];
    public float[] waist_yrote = new float[200];
    public float[] waist_zrote = new float[200];
    
    
    public int knee_l = 4;
    public int[] knee_l_time = new int[200];
    public float knee_l_xpoint = 0;
    public float knee_l_ypoint = 0;
    public float knee_l_zpoint = 0;
    
    public float[] knee_l_xrote = new float[200];
    public float[] knee_l_yrote = new float[200];
    public float[] knee_l_zrote = new float[200];
    
    public int leg_l = 4;
    public int[] leg_l_time = new int[200];
    public float leg_l_xpoint = 0;
    public float leg_l_ypoint = 0;
    public float leg_l_zpoint = 0;
    
    public float[] leg_l_xrote = new float[200];
    public float[] leg_l_yrote = new float[200];
    public float[] leg_l_zrote = new float[200];
    
    public int knee_r = 4;
    public int[] knee_r_time = new int[200];
    public float knee_r_xpoint = 0;
    public float knee_r_ypoint = 0;
    public float knee_r_zpoint = 0;
    
    public float[] knee_r_xrote = new float[200];
    public float[] knee_r_yrote = new float[200];
    public float[] knee_r_zrote = new float[200];
    
    public int leg_r = 4;
    public int[] leg_r_time = new int[200];
    public float leg_r_xpoint = 0;
    public float leg_r_ypoint = 0;
    public float leg_r_zpoint = 0;
    
    public float[] leg_r_xrote = new float[200];
    public float[] leg_r_yrote = new float[200];
    public float[] leg_r_zrote = new float[200];
}