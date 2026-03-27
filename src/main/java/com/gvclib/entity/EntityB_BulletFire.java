package gvclib.entity;

import gvclib.world.GVCExplosionBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.scoreboard.Team;
import gvclib.entity.living.ISoldier;
import gvclib.mod_GVCLib;
import net.minecraftforge.fml.common.Loader;
import techguns.client.ClientProxy;
import techguns.packets.PacketSpawnParticle;
import techguns.damagesystem.TGExplosion;
import techguns.Techguns;
import techguns.TGPackets;

public class EntityB_BulletFire extends EntityBBase {
	public EntityB_BulletFire(World worldIn) {
		super(worldIn);
	}

	public EntityB_BulletFire(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityB_BulletFire(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public static void func_189662_a(DataFixer p_189662_0_) {
		EntityBBase.func_189661_a(p_189662_0_, "Snowball");
	}

	public void setDead(){
		if(Loader.isModLoaded("techguns")){
			if (!this.world.isRemote && this.usetgfx){
				/*if(mod_GVCLib.proxy.getFPS() < mod_GVCLib.gvclibsa_fps_limit)*/{
				TGPackets.network.sendToAllAround(new PacketSpawnParticle("FlamethrowerImpact", this.posX,this.posY,this.posZ), TGPackets.targetPointAroundEnt(this, 50.0f));;
				}
			}
		}
		//this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		super.setDead();
	}
	
	public void onUpdate()
    {
        super.onUpdate();
        float sizee = time / 50;
        this.setSize(0.5F + sizee, 0.5F + sizee);
        //if(this.smoke)
        if(!this.usetgfx){
			this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY + 0D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        //if (this.isFireballFiery())
        /*if(this.time > 3){
            this.setFire(1);
        }*/
        //if(this.onGround)
        /*{
        	if (this.world.isAirBlock(new BlockPos(this.posX + 0, this.posY + 0, this.posZ)) 
        			&& !this.world.isAirBlock(new BlockPos(this.posX + 0, this.posY -0.1, this.posZ))
        			&& this.world.rand.nextInt(5) == 0)
        	{
        		//this.worldObj.setBlockState(new BlockPos(this.posX + 0, this.posY + 0, this.posZ), Blocks.FIRE.getDefaultState());
        		//this.worldObj.createExplosion(this, this.posX + 0, this.posY + 0, this.posZ + 0, 0, false);
        	}
        }*/
    }
	
	protected void onImpact(RayTraceResult result)
    {
		boolean damage_flag = true;
		boolean ap = true;
		boolean dead_flag = true;
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
				if(result.entityHit instanceof EntityLivingBase && (this.getThrower() != null && this.getThrower() instanceof EntityLivingBase)) {
					EntityLivingBase hitentity = (EntityLivingBase)result.entityHit;
					EntityLivingBase shootentity = (EntityLivingBase)this.getThrower();
					Team hitteam = hitentity.getTeam();
					Team shootteam = shootentity.getTeam();
					if(shootteam == null && hitteam == null){
						if(hitentity instanceof ISoldier && shootentity instanceof ISoldier){
							dead_flag = false;
							damage_flag = false;
						}
					}else if(shootteam == hitteam){
						dead_flag = false;
						damage_flag = false;
					}
				}
				if(damage_flag){
					if (!result.entityHit.isImmuneToFire())
					{
						boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0F);

						if (flag)
						{
							this.applyEnchantments(this.getThrower(), result.entityHit);
							result.entityHit.setFire(5);
						}
					}
					//result.entityHit.hurtResistantTime = 5;
					int i = Bdamege;
					//result.entityHit.attackEntityFrom(GVCDamageSource.causeBulletFire(this, this.getThrower()), (float) i);
					result.entityHit.attackEntityFrom(DamageSource.ON_FIRE, (float) i);
					result.entityHit.attackEntityFrom(DamageSource.IN_FIRE, (float) i);
					
					if(exlevel > 0)GVCExplosionBase.ExplosionKai(this.getThrower(), this, this.posX + 0, this.posY + 0, this.posZ + 0, this.exlevel, this.exfire,  this.ex);
				}

            }
            if(dead_flag && !ap)this.setDead();
        }
    }
	/**
	 * Called when this EntityThrowable hits a block or entity.
	 */
	
	/**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }
	
	protected void onBreak(RayTraceResult result) {
		if (this.world.getBlockState(result.getBlockPos()).getBlock() == Blocks.GLASS_PANE){
			Block block = this.world.getBlockState(result.getBlockPos()).getBlock();
			int blockmeta = this.world.getBlockState(result.getBlockPos())
					.getBlock().getMetaFromState(this.world.getBlockState(result.getBlockPos()));
			BlockPos blockpos = result.getBlockPos();
			IBlockState iblock = this.world.getBlockState(result.getBlockPos()).getBlock().getStateFromMeta(blockmeta);

			block.onBlockDestroyedByPlayer(this.world, blockpos, iblock);
			block.dropBlockAsItem(this.world, blockpos, iblock, blockmeta);
			this.world.setBlockToAir(blockpos);
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}