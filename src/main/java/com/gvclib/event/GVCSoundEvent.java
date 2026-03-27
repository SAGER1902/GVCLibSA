package gvclib.event;


import gvclib.mod_GVCLib;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

/**
 * Registers this mod's {@link SoundEvent}s.
 *
 * @author Choonster
 */
//@SuppressWarnings("WeakerAccess")
public class GVCSoundEvent {
	public static SoundEvent wrj_qidong;
	public static SoundEvent fengxinzi;
	public static SoundEvent jiqiang;
	public static SoundEvent juji;
	public static SoundEvent leishe;
	public static SoundEvent nengliang;
	public static SoundEvent csk;
	
	public static SoundEvent level1;
	public static SoundEvent level3;
	public static SoundEvent barrel;
	public static SoundEvent money_cost;
	public static SoundEvent money_add;
	public static SoundEvent building;
	
	public static SoundEvent fire_dsr80;
	public static SoundEvent fire_para;
	public static SoundEvent fire_flakgun;
	public static SoundEvent fire_glbq;
	public static SoundEvent fire_tesla;
	public static SoundEvent fire_haibao;
	public static SoundEvent fire_42mg;
	public static SoundEvent fire_awp;
	public static SoundEvent fire_fsp;
	public static SoundEvent fire_ggi;
	public static SoundEvent ppsh41f;
	public static SoundEvent xbf;
	public static SoundEvent bfz_say;
	public static SoundEvent bfz_move;
	public static SoundEvent bfz_fire;
	public static SoundEvent paradox_say;
	
	public static SoundEvent kirove_say;
	public static SoundEvent kirove_start;
	public static SoundEvent kirove_boom;
	public static SoundEvent hlf;
	
	
	public static SoundEvent fix;
	public static SoundEvent fire_ap;
	public static SoundEvent fire_gate;
	public static SoundEvent fire_gate2;
	public static SoundEvent fire_gate3;
	public static SoundEvent fire_ftk;
	public static SoundEvent fire_stk;
	public static SoundEvent fire_ap2;
	public static SoundEvent fire_cntk;
	public static SoundEvent fire_mast;
	public static SoundEvent fire_ml;
	public static SoundEvent fire_m1;
	public static SoundEvent fire_gltk;
	public static SoundEvent reload_mast;
	public static SoundEvent reload_tesla;
	public static SoundEvent fire_fkc1;
	public static SoundEvent fire_fkc2;
	public static SoundEvent fire_cybo;
	public static SoundEvent fire_cybo2;
	public static SoundEvent fire_cybo3;
	public static SoundEvent kff;
	public static SoundEvent soonm;
	public static SoundEvent soonf1;
	public static SoundEvent soonf2;
	public static SoundEvent buzzm;
	public static SoundEvent diskfly;
	public static SoundEvent diskf;

	public static SoundEvent fire_csk;
	public static SoundEvent fire_ivan;
	public static SoundEvent die_ivan;
	public static SoundEvent say_ivan;
	public static SoundEvent exp_ivan;
	public static SoundEvent reload_csk;
	public static SoundEvent fire_qinin;
	public static SoundEvent fire_arobow;
	public static SoundEvent fire_virus;
	public static SoundEvent fire_pyro;
	public static SoundEvent fire_nw;
	public static SoundEvent nuclear_exp;
	public static SoundEvent nuclear_worn;
	public static SoundEvent lightingstorm_exp;
	public static SoundEvent lightingstorm_worn;
	public static SoundEvent fire_sg;
	
	public static SoundEvent emp_exp;
	
	public static SoundEvent fire_56;
	public static SoundEvent fire_gat;

	public static SoundEvent agzf;
	public static SoundEvent buzzf;
	public static SoundEvent buzzf2;
	public static SoundEvent cbtf;
	public static SoundEvent cbtr;
	public static SoundEvent dbf;
	public static SoundEvent gltf;
	public static SoundEvent gltr;
	public static SoundEvent gtf;
	public static SoundEvent gyrocopter_fire;
	public static SoundEvent gyrocopter_move;
	public static SoundEvent ifvf;
	public static SoundEvent ifvfix;
	public static SoundEvent ifvmgf;
	public static SoundEvent jpf;
	public static SoundEvent kirovf;
	public static SoundEvent kirovmove;
	public static SoundEvent sjpf;
	public static SoundEvent v3f;
	public static SoundEvent v3m;
	public static SoundEvent miragefire;
	public static SoundEvent mirage2;
	public static SoundEvent qzjf;
	public static SoundEvent ifvtran;
	
	public static SoundEvent fire_rifle;
	public static SoundEvent fire_rifle2;
	public static SoundEvent fire_rifle3;
	public static SoundEvent fire_rifle4;
	public static SoundEvent fire_rifle5;
	public static SoundEvent fire_rifle_ak;
	public static SoundEvent fire_hg;
	public static SoundEvent fire_hg2;
	public static SoundEvent fire_mg;
	public static SoundEvent fire_mg2;
	public static SoundEvent fire_mg3;
	public static SoundEvent fire_mg4;
	public static SoundEvent fire_lmg;
	public static SoundEvent fire_hmg;
	public static SoundEvent fire_arrow;
	public static SoundEvent fire_supu;
	public static SoundEvent fire_cannon;
	public static SoundEvent fire_cannon2;
	public static SoundEvent fire_rail;
	public static SoundEvent fire_amr;
	public static SoundEvent fire_missile;
	public static SoundEvent fire_roket;
	public static SoundEvent fire_grenade;
	public static SoundEvent fire_gl;
	public static SoundEvent fire_20mm;
	public static SoundEvent fire_30mm;
	public static SoundEvent fire_havrycannon;
	public static SoundEvent fire_fire;
	public static SoundEvent throw_grenade;
	
	public static SoundEvent reload_cannon;
	public static SoundEvent reload_rail;
	public static SoundEvent reload_mg;
	public static SoundEvent reload_mag;
	public static SoundEvent reload_clip;
	public static SoundEvent reload_cocking;
	public static SoundEvent reload_shell;
	
	public static SoundEvent sound_tank;
	public static SoundEvent sound_car;
	public static SoundEvent sound_heli;
	public static SoundEvent sound_pera;
	public static SoundEvent sound_call;
	
	public static SoundEvent sound_jet1;
	public static SoundEvent sound_jet2;
	public static SoundEvent sound_lock;
	
	public static SoundEvent sound_fall_shell;
	public static SoundEvent sound_robo_step;
	public static SoundEvent sound_robo_boost;
	public static SoundEvent sound_hit;

	public static SoundEvent bdzf;
	public static SoundEvent bdzf_hit;
	public static SoundEvent dnqd;
	public static SoundEvent dnqf1;
	public static SoundEvent dnqf2;
	public static SoundEvent hmf;
	public static SoundEvent ifvf2;
	public static SoundEvent jxd;
	public static SoundEvent jxf1;
	public static SoundEvent jxf2;
	public static SoundEvent ldzf;
	public static SoundEvent mantif;
	public static SoundEvent qsf;
	public static SoundEvent sjpf2;
	public static SoundEvent wzf2;
	public static SoundEvent xnf1;
	
	/**
	 * Register the {@link SoundEvent}s.
	 */
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		bdzf = registerSound("gvclib.bdzf", event);
		bdzf_hit = registerSound("gvclib.bdzf_hit", event);
		dnqd = registerSound("gvclib.dnqd", event);
		dnqf1 = registerSound("gvclib.dnqf1", event);
		dnqf2 = registerSound("gvclib.dnqf2", event);
		hmf = registerSound("gvclib.hmf", event);
		ifvf2 = registerSound("gvclib.ifvf2", event);
		jxd = registerSound("gvclib.jxd", event);
		jxf1 = registerSound("gvclib.jxf1", event);
		jxf2 = registerSound("gvclib.jxf2", event);
		ldzf = registerSound("gvclib.ldzf", event);
		mantif = registerSound("gvclib.mantif", event);
		qsf = registerSound("gvclib.qsf", event);
		sjpf2 = registerSound("gvclib.sjpf2", event);
		wzf2 = registerSound("gvclib.wzf2", event);
		xnf1 = registerSound("gvclib.xnf1", event);
	
		wrj_qidong = registerSound("gvclib.wrj_qidong", event);
		fengxinzi = registerSound("gvclib.fengxinzi", event);
		jiqiang = registerSound("gvclib.jiqiang", event);
		juji = registerSound("gvclib.juji", event);
		leishe = registerSound("gvclib.leishe", event);
		nengliang = registerSound("gvclib.nengliang", event);
		csk = registerSound("gvclib.csk", event);
		
	    level1 = registerSound("gvclib.level1", event);
	    level3 = registerSound("gvclib.level3", event);
	    barrel = registerSound("gvclib.barrel", event);
	    money_cost = registerSound("gvclib.money_cost", event);
	    money_add = registerSound("gvclib.money_add", event);
	    building = registerSound("gvclib.building", event);
		
        fire_dsr80 = registerSound("gvclib.fire_dsr80", event);
	    fire_para = registerSound("gvclib.fire_para", event);
	    fire_flakgun = registerSound("gvclib.fire_flakgun", event);
	    fire_glbq = registerSound("gvclib.fire_glbq", event);
	    fire_tesla = registerSound("gvclib.fire_tesla", event);
	    fire_haibao = registerSound("gvclib.fire_haibao", event);
	    fire_awp = registerSound("gvclib.fire_awp", event);
	    fire_42mg = registerSound("gvclib.fire_42mg", event);
	    fire_fsp = registerSound("gvclib.fire_fsp", event);
	    fire_ggi = registerSound("gvclib.fire_ggi", event);		
	    ppsh41f = registerSound("gvclib.ppsh41f", event);
	    xbf = registerSound("gvclib.xbf", event);

	    bfz_say = registerSound("gvclib.bfz_say", event);
	    bfz_move = registerSound("gvclib.bfz_move", event);
	    bfz_fire = registerSound("gvclib.bfz_fire", event);

	    paradox_say = registerSound("gvclib.paradox_say", event);
	    kirove_say = registerSound("gvclib.kirove_say", event);
	    kirove_start = registerSound("gvclib.kirove_start", event);
	    kirove_boom = registerSound("gvclib.kirove_boom", event);
	    hlf = registerSound("gvclib.hlf", event);
		
	    fix = registerSound("gvclib.fix", event);
		fire_ap = registerSound("ra2sa.fire_ap", event);
		fire_gate = registerSound("ra2sa.fire_gate", event);
		fire_gate2 = registerSound("ra2sa.fire_gate2", event);
		fire_gate3 = registerSound("ra2sa.fire_gate3", event);
		fire_ap2 = registerSound("ra2sa.fire_ap2", event);
		fire_ftk = registerSound("ra2sa.fire_ftk", event);
		fire_stk = registerSound("ra2sa.fire_stk", event);
		fire_cntk = registerSound("ra2sa.fire_cntk", event);
		fire_mast = registerSound("ra2sa.fire_mast", event);
		fire_ml = registerSound("ra2sa.fire_ml", event);
		fire_m1 = registerSound("ra2sa.fire_m1", event);
		fire_gltk = registerSound("ra2sa.fire_gltk", event);
		reload_mast = registerSound("ra2sa.reload_mast", event);
		reload_tesla = registerSound("ra2sa.reload_tesla", event);
		fire_fkc1 = registerSound("ra2sa.fire_fkc1", event);
		fire_fkc2 = registerSound("ra2sa.fire_fkc2", event);
		fire_cybo = registerSound("gvclib.fire_cybo", event);
		fire_cybo2 = registerSound("gvclib.fire_cybo2", event);
		fire_cybo3 = registerSound("gvclib.fire_cybo3", event);
		kff = registerSound("gvclib.kff", event);
		soonm = registerSound("gvclib.soonm", event);
		soonf1 = registerSound("gvclib.soonf1", event);
		soonf2 = registerSound("gvclib.soonf2", event);
		diskfly = registerSound("gvclib.diskfly", event);
		diskf = registerSound("gvclib.diskf", event);
		buzzm = registerSound("gvclib.buzzm", event);

		fire_csk = registerSound("gvclib.fire_csk", event);
		fire_ivan = registerSound("gvclib.fire_ivan", event);
		die_ivan = registerSound("gvclib.die_ivan", event);
		say_ivan = registerSound("gvclib.say_ivan", event);
		exp_ivan = registerSound("gvclib.exp_ivan", event);
		reload_csk = registerSound("gvclib.reload_csk", event);
		fire_qinin = registerSound("gvclib.fire_qinin", event);
		fire_arobow = registerSound("gvclib.fire_arobow", event);
		fire_virus = registerSound("gvclib.fire_virus", event);
		fire_pyro = registerSound("gvclib.fire_pyro", event);
		fire_nw = registerSound("gvclib.fire_nw", event);
		nuclear_exp = registerSound("gvclib.nuclear_exp", event);
		nuclear_worn = registerSound("gvclib.nuclear_worn", event);
		lightingstorm_exp = registerSound("gvclib.lightingstorm_exp", event);
		lightingstorm_worn = registerSound("gvclib.lightingstorm_worn", event);
		fire_sg = registerSound("gvclib.fire_sg", event);
		
		emp_exp = registerSound("gvclib.emp_exp", event);

		fire_56 = registerSound("gvclib.fire_56", event);
		fire_gat = registerSound("gvclib.fire_gat", event);

		agzf = registerSound("ra2sa.agzf", event);
		buzzf = registerSound("ra2sa.buzzf", event);
		buzzf2 = registerSound("ra2sa.buzzf2", event);
		cbtf = registerSound("ra2sa.cbtf", event);
		cbtr = registerSound("ra2sa.cbtr", event);
		dbf = registerSound("ra2sa.dbf", event);
		gltf = registerSound("ra2sa.gltf", event);
		gltr = registerSound("ra2sa.gltr", event);
		gtf = registerSound("ra2sa.gtf", event);
		gyrocopter_fire = registerSound("ra2sa.gyrocopter_fire", event);
		gyrocopter_move = registerSound("ra2sa.gyrocopter_move", event);
		ifvf = registerSound("ra2sa.ifvf", event);
		ifvfix = registerSound("ra2sa.ifvfix", event);
		ifvmgf = registerSound("ra2sa.ifvmgf", event);
		jpf = registerSound("ra2sa.jpf", event);
		kirovf = registerSound("ra2sa.kirovf", event);
		kirovmove = registerSound("ra2sa.kirovmove", event);
		sjpf = registerSound("ra2sa.sjpf", event);
		v3f = registerSound("ra2sa.v3f", event);
		v3m = registerSound("ra2sa.v3m", event);
		miragefire = registerSound("ra2sa.miragefire", event);
		mirage2 = registerSound("gvclib.mirage2", event);
		qzjf = registerSound("ra2sa.qzjf", event);
		ifvtran = registerSound("gvclib.ifvtran", event);
		
		fire_rifle = registerSound("gvclib.fire_rifle", event);
		fire_rifle2 = registerSound("gvclib.fire_rifle2", event);
		fire_rifle3 = registerSound("gvclib.fire_rifle3", event);
		fire_rifle4 = registerSound("gvclib.fire_rifle4", event);
		fire_rifle5 = registerSound("gvclib.fire_rifle5", event);
		fire_rifle_ak = registerSound("gvclib.fire_rifle_ak", event);
		fire_hg = registerSound("gvclib.fire_hg", event);
		fire_hg2 = registerSound("gvclib.fire_hg2", event);
		fire_mg = registerSound("gvclib.fire_mg", event);
		fire_mg2 = registerSound("gvclib.fire_mg2", event);
		fire_mg3 = registerSound("gvclib.fire_mg3", event);
		fire_mg4 = registerSound("gvclib.fire_mg4", event);
		fire_lmg = registerSound("gvclib.fire_lmg", event);
		fire_hmg = registerSound("gvclib.fire_hmg", event);
		fire_arrow = registerSound("gvclib.fire_arrow", event);
		fire_supu = registerSound("gvclib.fire_supu", event);
		fire_cannon = registerSound("gvclib.fire_cannon", event);
		fire_cannon2 = registerSound("gvclib.fire_cannon2", event);
		fire_rail = registerSound("gvclib.fire_rail", event);
		fire_amr = registerSound("gvclib.fire_amr", event);
		fire_missile = registerSound("gvclib.fire_missile", event);
		fire_roket = registerSound("gvclib.fire_roket", event);
		fire_grenade = registerSound("gvclib.fire_grenade", event);
		fire_gl = registerSound("gvclib.fire_gl", event);
		fire_20mm = registerSound("gvclib.fire_20mm", event);
		fire_30mm = registerSound("gvclib.fire_30mm", event);
		fire_havrycannon = registerSound("gvclib.fire_havrycannon", event);
		fire_fire = registerSound("gvclib.fire_fire", event);
		throw_grenade = registerSound("gvclib.throw_grenade", event);
		reload_cannon = registerSound("gvclib.reload_cannon", event);
		reload_rail = registerSound("gvclib.reload_rail", event);
		reload_mg = registerSound("gvclib.reload_mg", event);
		reload_mag = registerSound("gvclib.reload_mag", event);
		reload_clip = registerSound("gvclib.reload_clip", event);
		reload_cocking = registerSound("gvclib.reload_cocking", event);
		reload_shell = registerSound("gvclib.reload_shell", event);
		sound_tank = registerSound("gvclib.sound_tank", event);
		sound_car = registerSound("gvclib.sound_car", event);
		sound_heli = registerSound("gvclib.sound_heli", event);
		sound_pera = registerSound("gvclib.sound_pera", event);
		sound_call = registerSound("gvclib.sound_call", event);
		sound_jet1 = registerSound("gvclib.sound_jet1", event);
		sound_jet2 = registerSound("gvclib.sound_jet2", event);
		
		sound_lock = registerSound("gvclib.sound_lock", event);
		
		sound_fall_shell = registerSound("gvclib.sound_fall_shell", event);
		sound_robo_step = registerSound("gvclib.sound_robo_step", event);
		sound_robo_boost = registerSound("gvclib.sound_robo_boost", event);
		sound_hit = registerSound("gvclib.sound_hit", event);
		
		event.getRegistry().register(wrj_qidong);
		event.getRegistry().register(fengxinzi);
		event.getRegistry().register(jiqiang);
		event.getRegistry().register(juji);
		event.getRegistry().register(leishe);
		event.getRegistry().register(nengliang);
		event.getRegistry().register(csk);
		
		event.getRegistry().register(level1);
		event.getRegistry().register(level3);
		event.getRegistry().register(barrel);
		event.getRegistry().register(money_cost);
		event.getRegistry().register(money_add);
		event.getRegistry().register(building);
		
		event.getRegistry().register(fire_dsr80);
		event.getRegistry().register(fire_para);
		event.getRegistry().register(fire_flakgun);
		event.getRegistry().register(fire_glbq);
		event.getRegistry().register(fire_awp);
		event.getRegistry().register(fire_42mg);
		
		event.getRegistry().register(fire_csk);
		event.getRegistry().register(fire_ivan);
		event.getRegistry().register(exp_ivan);
		event.getRegistry().register(say_ivan);
		event.getRegistry().register(die_ivan);
		
		event.getRegistry().register(reload_csk);
		event.getRegistry().register(fire_qinin);
		event.getRegistry().register(fire_arobow);
		event.getRegistry().register(fire_virus);
		event.getRegistry().register(fire_pyro);
		event.getRegistry().register(fire_nw);
		event.getRegistry().register(nuclear_exp);
		event.getRegistry().register(nuclear_worn);
		event.getRegistry().register(lightingstorm_exp);
		event.getRegistry().register(lightingstorm_worn);
		event.getRegistry().register(fire_sg);
		
		event.getRegistry().register(emp_exp);

		
		
		event.getRegistry().register(fire_56);
		event.getRegistry().register(fire_gat);
		
		event.getRegistry().register(agzf);
		event.getRegistry().register(buzzf);
		event.getRegistry().register(buzzf2);
		event.getRegistry().register(cbtf);
		event.getRegistry().register(cbtr);
		event.getRegistry().register(dbf);
		event.getRegistry().register(gltf);
		event.getRegistry().register(gltr);
		event.getRegistry().register(gtf);
		event.getRegistry().register(gyrocopter_fire);
		event.getRegistry().register(gyrocopter_move);
		event.getRegistry().register(ifvf);
		event.getRegistry().register(ifvfix);
		event.getRegistry().register(ifvmgf);
		event.getRegistry().register(jpf);
		event.getRegistry().register(kirovf);
		event.getRegistry().register(kirovmove);
		event.getRegistry().register(sjpf);
		event.getRegistry().register(v3f);
		event.getRegistry().register(v3m);
		event.getRegistry().register(miragefire);
		event.getRegistry().register(mirage2);
		event.getRegistry().register(qzjf);
		event.getRegistry().register(ifvtran);
		
		event.getRegistry().register(fire_tesla);
		event.getRegistry().register(fire_haibao);
		event.getRegistry().register(fire_fsp);
		event.getRegistry().register(fire_ggi);		
		event.getRegistry().register(ppsh41f);
		event.getRegistry().register(xbf);
		
		event.getRegistry().register(bfz_fire);
		event.getRegistry().register(bfz_move);
		event.getRegistry().register(bfz_say);
		
		event.getRegistry().register(paradox_say);
		event.getRegistry().register(kirove_say);
		event.getRegistry().register(kirove_start);
		event.getRegistry().register(kirove_boom);
		event.getRegistry().register(hlf);
		
		event.getRegistry().register(fix);
		event.getRegistry().register(fire_ap);
		event.getRegistry().register(fire_gate);
		event.getRegistry().register(fire_gate2);
		event.getRegistry().register(fire_gate3);
		event.getRegistry().register(fire_ap2);
		event.getRegistry().register(fire_ftk);
		event.getRegistry().register(fire_stk);
		event.getRegistry().register(fire_cntk);
		event.getRegistry().register(fire_mast);
		event.getRegistry().register(fire_ml);
		event.getRegistry().register(fire_m1);
		event.getRegistry().register(fire_gltk);
		event.getRegistry().register(reload_mast);
		event.getRegistry().register(reload_tesla);
		event.getRegistry().register(fire_fkc1);
		event.getRegistry().register(fire_fkc2);
		event.getRegistry().register(fire_cybo);
		event.getRegistry().register(fire_cybo2);
		event.getRegistry().register(fire_cybo3);
		event.getRegistry().register(kff);
		event.getRegistry().register(buzzm);
		event.getRegistry().register(soonm);
		event.getRegistry().register(soonf1);
		event.getRegistry().register(soonf2);
		event.getRegistry().register(diskfly);		
		event.getRegistry().register(diskf);	
		
		event.getRegistry().register(fire_rifle);
		event.getRegistry().register(fire_rifle2);
		event.getRegistry().register(fire_rifle3);
		event.getRegistry().register(fire_rifle4);
		event.getRegistry().register(fire_rifle5);
		event.getRegistry().register(fire_rifle_ak);
		event.getRegistry().register(fire_hg);
		event.getRegistry().register(fire_hg2);
		event.getRegistry().register(fire_lmg);
		event.getRegistry().register(fire_hmg);
		event.getRegistry().register(fire_mg);
		event.getRegistry().register(fire_mg2);
		event.getRegistry().register(fire_mg3);
		event.getRegistry().register(fire_mg4);
		event.getRegistry().register(fire_arrow);
		event.getRegistry().register(fire_supu);
		event.getRegistry().register(fire_cannon);
		event.getRegistry().register(fire_cannon2);
		event.getRegistry().register(fire_rail);
		event.getRegistry().register(fire_amr);
		event.getRegistry().register(fire_missile);
		event.getRegistry().register(fire_roket);
		event.getRegistry().register(fire_grenade);
		event.getRegistry().register(fire_gl);
		event.getRegistry().register(fire_20mm);
		event.getRegistry().register(fire_30mm);
		event.getRegistry().register(fire_havrycannon);
		event.getRegistry().register(fire_fire);
		event.getRegistry().register(throw_grenade);
		event.getRegistry().register(reload_cannon);
		event.getRegistry().register(reload_rail);
		event.getRegistry().register(reload_mg);
		event.getRegistry().register(reload_mag);
		event.getRegistry().register(reload_clip);
		event.getRegistry().register(reload_cocking);
		event.getRegistry().register(reload_shell);
		event.getRegistry().register(sound_tank);
		event.getRegistry().register(sound_car);
		event.getRegistry().register(sound_heli);
		event.getRegistry().register(sound_pera);
		event.getRegistry().register(sound_call);
		event.getRegistry().register(sound_jet1);
		event.getRegistry().register(sound_jet2);
		event.getRegistry().register(sound_lock);
		
		event.getRegistry().register(sound_fall_shell);
		event.getRegistry().register(sound_robo_step);
		event.getRegistry().register(sound_robo_boost);
		event.getRegistry().register(sound_hit);
		
	event.getRegistry().register(bdzf);
	event.getRegistry().register(bdzf_hit);
	event.getRegistry().register(dnqd);
	event.getRegistry().register(dnqf1);
	event.getRegistry().register(dnqf2);
	event.getRegistry().register(hmf);
	event.getRegistry().register(ifvf2);
	event.getRegistry().register(jxd);
	event.getRegistry().register(jxf1);
	event.getRegistry().register(jxf2);
	event.getRegistry().register(ldzf);
	event.getRegistry().register(mantif);
	event.getRegistry().register(qsf);
	event.getRegistry().register(sjpf2);
	event.getRegistry().register(wzf2);
	event.getRegistry().register(xnf1);
	}

	/**
	 * Register a {@link SoundEvent}.
	 *
	 * @param soundName The SoundEvent's name without the testmod3 prefix
	 * @return The SoundEvent
	 */
	private static SoundEvent registerSound(String soundName, RegistryEvent.Register<SoundEvent> event) {
		/*final ResourceLocation soundID = new ResourceLocation(mod_GVCLib.MOD_ID, soundName);
		//return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
		event.getRegistry().register(new SoundEvent(soundID).setRegistryName(soundID));		
	//	System.out.println(String.format("GVCLib-" + soundName));
		return new SoundEvent(new ResourceLocation(mod_GVCLib.MOD_ID, soundName)).setRegistryName(soundName);*/
		ResourceLocation soundID = new ResourceLocation(mod_GVCLib.MOD_ID, soundName);
	//	event.getRegistry().register(new SoundEvent(soundID).setRegistryName(soundID));		
		return new SoundEvent(soundID).setRegistryName(soundID);
	}
}