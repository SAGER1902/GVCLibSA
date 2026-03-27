package gvclib;
public class RenderParameters {
    // Recoil variables
    /**
     * The recoil applied to the player view by shooting
     */
    public static float playerRecoilPitch;
    public static float playerRecoilYaw;
    public static float prevPitch = 0;
    public static float cockRecoil;
    public static int amination_bulllet;
	
	public static float[] bulllet_x = new float[200];
	public static float[] bulllet_y = new float[200];
	public static float bulllet_z = 0;
    public static boolean bulletstart;
	
    public static float attachmentSwitch = 0f;
	
	public static boolean deploy_mode;
	public static String show_model;
	
    public static float rate;
    public static boolean phase;
    /**
     * The amount of compensation applied to recoil in order to bring it back to normal
     */
    public static float antiRecoilPitch;
    public static float antiRecoilYaw;
	
    public static float GUN_BALANCING_X = 0;
    public static float GUN_BALANCING_Y = 0;

    public static float GUN_CHANGE_Y = 0;

    public static float GUN_ROT_X = 0;
    public static float GUN_ROT_Y = 0;
    public static float GUN_ROT_Z = 0;

    public static float GUN_ROT_X_LAST = 0;
    public static float GUN_ROT_Y_LAST = 0;
    public static float GUN_ROT_Z_LAST = 0;
	
    public RenderParameters() {
    }
}
