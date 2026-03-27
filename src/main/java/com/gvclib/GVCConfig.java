package gvclib;

import net.minecraftforge.common.config.Configuration;

public class GVCConfig {
	public void init(Configuration config, mod_GVCLib mod)
	{
		mod.cfg_debag = config.getBoolean("debag", "all", false, "测试用");
		
		/*mod.cfg_left_shootgun = config.getBoolean("cfg_left_shootgun", "all", true, "开启单机左键开火模式（右键在服务端中无法正常使用）");
		mod.cfg_server_shootgun = config.getBoolean("cfg_server_shootgun", "all", false, "开启服务端左键开火模式（通过发包来执行，尚未完善,单机不要开这个！！！）");*/
		
		mod.cfg_debag_weather = config.getBoolean("debag_weather", "all", false, "测试天气模式");
		
		mod.cfg_debag_gun_mugen = config.getBoolean("cfg_debag_gun_mugen", "all", false, "测试枪械开启无限子弹");
		
		mod.cfg_optifine	= config.getBoolean("optifine", "all", false, "测试用");
		mod.cfg_optifiney	= config.getFloat("cfg_optifiney", "all", (float)1.60D, 0, 20, "测试用");
		mod.cfg_optifineys	= config.getFloat("cfg_optifineys", "all", (float)1.54D, 0, 20,  "测试用");
		mod.cfg_bullet_smoke	= config.getBoolean("cfg_bullet_smoke", "all", false, "是否启用原版爆炸烟雾粒子");
		
		mod.gvclibsa_tgfx	= config.getBoolean("gvclibsa_tgfx", "all", false, "开启科技枪特效 需要将assets/gvclib/particles/下的内容覆盖到assets/techguns/particles/目录下来加载GVCSA特效素材");
		mod.gvclibsa_flash	= config.getBoolean("gvclibsa_flash", "all", true, "开启开火环境动态光源");
		mod.gvclibsa_damage_test	= config.getBoolean("gvclibsa_damage_test", "all", false, "将玩家的枪械伤害设置为1");
		mod.gvclibsa_fps_limit	= config.getInt("gvclibsa_fps_limit", "all", 5, 0, 8000, "FPS为多少时清除子弹");
		
		mod.gvclibsa_amination	= config.getBoolean("gvclibsa_amination", "all", true, "启用枪械模型后坐力动画");
		mod.gvclibsa_amination_size	= config.getFloat("gvclibsa_amination_size", "all", (float)1.0F, 0, 20, "枪械模型后坐力动画的幅度");
		
		//mod.gvclibsa_recoil_size	= config.getFloat("gvclibsa_recoil_size", "all", (float)1.0F, 0, 20, "后坐力的总体幅度");
		mod.gvclibsa_shock_size	= config.getFloat("gvclibsa_shock_size", "all", (float)1.0F, 0, 20, "视角摇晃的总体幅度,值为0时无视角摇晃");
		
		
		mod.cfg_bullet_living	= config.getInt("cfg_bullet_living", "all", 80, 0, 8000, "子弹最大存在时间");
		mod.cfg_Instant_death_avoidance	= config.getBoolean("cfg_Instant_death_avoidance", "all", true, "测试用");
		mod.cfg_explotion_drop	=config.getFloat("cfg_explotion_drop", "all", (float)1D, 0, 1, "测试用");
		
		mod.cfg_turret_lockpoint	= config.getBoolean("cfg_turret_lockpoint", "all", true, "固定武器能否被推动");
		
		mod.cfg_entity_render_range	= config.getFloat("cfg_entity_render_range", "all", (float)120D, 1, 100, "实体渲染距离");
		
		mod.cfg_explotion_breakdirt	= config.getBoolean("cfg_explotion_breakdirt", "all", true, "爆炸是否破坏泥土");
		
		mod.cfg_mobdismount_insave	= config.getBoolean("cfg_mobdismount_insave", "all", true, "生物");
		
		mod.cfg_key_x	= config.getString("cfg_key_x", "all", "X", "键位设置KEY_X");
		mod.cfg_key_r	= config.getString("cfg_key_r", "all", "R", "键位设置KEY_R");
		mod.cfg_key_c	= config.getString("cfg_key_c", "all", "C", "键位设置KEY_C");
		mod.cfg_key_z	= config.getString("cfg_key_z", "all", "Z", "键位设置KEY_Z");
		mod.cfg_key_g	= config.getString("cfg_key_g", "all", "G", "键位设置KEY_G");
		mod.cfg_key_k	= config.getString("cfg_key_k", "all", "K", "键位设置KEY_K");
		mod.cfg_key_h	= config.getString("cfg_key_h", "all", "H", "键位设置KEY_H");
		mod.cfg_key_f	= config.getString("cfg_key_f", "all", "F", "键位设置KEY_F");
		mod.cfg_key_b	= config.getString("cfg_key_b", "all", "B", "键位设置KEY_B");
		mod.cfg_key_v	= config.getString("cfg_key_v", "all", "V", "键位设置KEY_V");
		
		mod.cfg_key_tab	= config.getString("cfg_key_tab", "all", "TAB", "键位设置KEY_TAB");
		
		mod.cfg_gui_x1	= config.getInt("cfg_gui_x1", "all", 0, -8000, 8000, "命中图标的x坐标-需要重启游戏才能生效");
		mod.cfg_gui_y1	= config.getInt("cfg_gui_y1", "all", 0, -8000, 8000, "命中图标的y坐标-需要重启游戏才能生效");
		mod.cfg_gui_x2	= config.getInt("cfg_gui_x2", "all", 0, -8000, 8000, "命中信息的x坐标-需要重启游戏才能生效");
		mod.cfg_gui_y2	= config.getInt("cfg_gui_y2", "all", 0, -8000, 8000, "命中信息的y坐标-需要重启游戏才能生效");
		
		mod.cfg_multiCoreLoading	= config.getBoolean("cfg_multiCoreLoading", "all", true, "测试用");		
		mod.arm_lmm	= config.getBoolean("arm_lmm", "all", false, "是否启用女仆持枪手臂模型");		
	}
}
