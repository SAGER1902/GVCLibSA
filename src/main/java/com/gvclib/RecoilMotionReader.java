package gvclib;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gvclib.item.ItemGunBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class RecoilMotionReader {

	static int mat0 = 0;
	static int mat1 = 0;
	static int mat2 = 0;
	static int mat3 = 0;
	static int mat22 = 0;
	static int mat24 = 0;
	static int mat25 = 0;
	static int mat31 = 0;
	static int mat32 = 0;
	static int matlefthand = 0;
	static int matrighthand = 0;
	
	public static void read(ItemGunBase gun, ResourceLocation reloadmotion) {
		
		mat0 = 0;
		mat1 = 0;
		mat2 = 0;
		mat3 = 0;
		mat22 = 0;
		mat24 = 0;
		mat25 = 0;
		mat31 = 0;
		mat32 = 0;
		matlefthand = 0;
		matrighthand = 0;
		
		try
		{
			System.out.println(String.format("modelroad"));
		IResource res = Minecraft.getMinecraft().getResourceManager().getResource(reloadmotion);
		InputStream inputStream = res.getInputStream();
		try {
			//InputStreamReader packFile = new InputStreamReader(classs.getClassLoader().getResourceAsStream(reloadmotion), "UTF-8");
			//BufferedReader br = new BufferedReader(packFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			
			String str;
			while ((str = br.readLine()) != null) { // 1行ずつ読み込む
				String[] type = str.split(",");
				if (type.length != 0) {
					//System.out.println(String.format("ok"));
					if (type[0].equals("Remat0") && mat0 < 30) {
						gun.recoilmat0_time[mat0] = Integer.parseInt(type[1]);
						gun.recoilmat0_xpoint[mat0] = Float.parseFloat(type[2]);
						gun.recoilmat0_ypoint[mat0] = Float.parseFloat(type[3]);
						gun.recoilmat0_zpoint[mat0] = Float.parseFloat(type[4]);
						gun.recoilmat0_xrote[mat0] = Float.parseFloat(type[5]);
						gun.recoilmat0_yrote[mat0] = Float.parseFloat(type[6]);
						gun.recoilmat0_zrote[mat0] = Float.parseFloat(type[7]);
						gun.recoilmat0_xmove[mat0] = Float.parseFloat(type[8]);
						gun.recoilmat0_ymove[mat0] = Float.parseFloat(type[9]);
						gun.recoilmat0_zmove[mat0] = Float.parseFloat(type[10]);
						++mat0;
						//System.out.println(String.format("0"));
					}
					if (type[0].equals("Remat1") && mat1 < 30) {
						gun.recoilmat1_time[mat1] = Integer.parseInt(type[1]);
						gun.recoilmat1_xpoint[mat1] = Float.parseFloat(type[2]);
						gun.recoilmat1_ypoint[mat1] = Float.parseFloat(type[3]);
						gun.recoilmat1_zpoint[mat1] = Float.parseFloat(type[4]);
						gun.recoilmat1_xrote[mat1] = Float.parseFloat(type[5]);
						gun.recoilmat1_yrote[mat1] = Float.parseFloat(type[6]);
						gun.recoilmat1_zrote[mat1] = Float.parseFloat(type[7]);
						gun.recoilmat1_xmove[mat1] = Float.parseFloat(type[8]);
						gun.recoilmat1_ymove[mat1] = Float.parseFloat(type[9]);
						gun.recoilmat1_zmove[mat1] = Float.parseFloat(type[10]);
						++mat1;
					}
					if (type[0].equals("Remat2") && mat2 < 30) {
						gun.recoilmat2_time[mat2] = Integer.parseInt(type[1]);
						gun.recoilmat2_xpoint[mat2] = Float.parseFloat(type[2]);
						gun.recoilmat2_ypoint[mat2] = Float.parseFloat(type[3]);
						gun.recoilmat2_zpoint[mat2] = Float.parseFloat(type[4]);
						gun.recoilmat2_xrote[mat2] = Float.parseFloat(type[5]);
						gun.recoilmat2_yrote[mat2] = Float.parseFloat(type[6]);
						gun.recoilmat2_zrote[mat2] = Float.parseFloat(type[7]);
						gun.recoilmat2_xmove[mat2] = Float.parseFloat(type[8]);
						gun.recoilmat2_ymove[mat2] = Float.parseFloat(type[9]);
						gun.recoilmat2_zmove[mat2] = Float.parseFloat(type[10]);
						++mat2;
					}
					if (type[0].equals("Remat3") && mat3 < 30) {
						gun.recoilmat3_time[mat3] = Integer.parseInt(type[1]);
						gun.recoilmat3_xpoint[mat3] = Float.parseFloat(type[2]);
						gun.recoilmat3_ypoint[mat3] = Float.parseFloat(type[3]);
						gun.recoilmat3_zpoint[mat3] = Float.parseFloat(type[4]);
						gun.recoilmat3_xrote[mat3] = Float.parseFloat(type[5]);
						gun.recoilmat3_yrote[mat3] = Float.parseFloat(type[6]);
						gun.recoilmat3_zrote[mat3] = Float.parseFloat(type[7]);
						gun.recoilmat3_xmove[mat3] = Float.parseFloat(type[8]);
						gun.recoilmat3_ymove[mat3] = Float.parseFloat(type[9]);
						gun.recoilmat3_zmove[mat3] = Float.parseFloat(type[10]);
						++mat3;
					}
					if (type[0].equals("Remat22") && mat22 < 30) {
						gun.recoilmat22_time[mat22] = Integer.parseInt(type[1]);
						gun.recoilmat22_xpoint[mat22] = Float.parseFloat(type[2]);
						gun.recoilmat22_ypoint[mat22] = Float.parseFloat(type[3]);
						gun.recoilmat22_zpoint[mat22] = Float.parseFloat(type[4]);
						gun.recoilmat22_xrote[mat22] = Float.parseFloat(type[5]);
						gun.recoilmat22_yrote[mat22] = Float.parseFloat(type[6]);
						gun.recoilmat22_zrote[mat22] = Float.parseFloat(type[7]);
						gun.recoilmat22_xmove[mat22] = Float.parseFloat(type[8]);
						gun.recoilmat22_ymove[mat22] = Float.parseFloat(type[9]);
						gun.recoilmat22_zmove[mat22] = Float.parseFloat(type[10]);
						++mat22;
					}
					if (type[0].equals("Remat24") && mat24 < 30) {
						gun.recoilmat24_time[mat24] = Integer.parseInt(type[1]);
						gun.recoilmat24_xpoint[mat24] = Float.parseFloat(type[2]);
						gun.recoilmat24_ypoint[mat24] = Float.parseFloat(type[3]);
						gun.recoilmat24_zpoint[mat24] = Float.parseFloat(type[4]);
						gun.recoilmat24_xrote[mat24] = Float.parseFloat(type[5]);
						gun.recoilmat24_yrote[mat24] = Float.parseFloat(type[6]);
						gun.recoilmat24_zrote[mat24] = Float.parseFloat(type[7]);
						gun.recoilmat24_xmove[mat24] = Float.parseFloat(type[8]);
						gun.recoilmat24_ymove[mat24] = Float.parseFloat(type[9]);
						gun.recoilmat24_zmove[mat24] = Float.parseFloat(type[10]);
						++mat24;
					}
					if (type[0].equals("Remat25") && mat25 < 30) {
						gun.recoilmat25_time[mat25] = Integer.parseInt(type[1]);
						gun.recoilmat25_xpoint[mat25] = Float.parseFloat(type[2]);
						gun.recoilmat25_ypoint[mat25] = Float.parseFloat(type[3]);
						gun.recoilmat25_zpoint[mat25] = Float.parseFloat(type[4]);
						gun.recoilmat25_xrote[mat25] = Float.parseFloat(type[5]);
						gun.recoilmat25_yrote[mat25] = Float.parseFloat(type[6]);
						gun.recoilmat25_zrote[mat25] = Float.parseFloat(type[7]);
						gun.recoilmat25_xmove[mat25] = Float.parseFloat(type[8]);
						gun.recoilmat25_ymove[mat25] = Float.parseFloat(type[9]);
						gun.recoilmat25_zmove[mat25] = Float.parseFloat(type[10]);
						++mat25;
					}
					if (type[0].equals("Remat31") && mat31 < 30) {
						gun.recoilmat31_time[mat31] = Integer.parseInt(type[1]);
						gun.recoilmat31_xpoint[mat31] = Float.parseFloat(type[2]);
						gun.recoilmat31_ypoint[mat31] = Float.parseFloat(type[3]);
						gun.recoilmat31_zpoint[mat31] = Float.parseFloat(type[4]);
						gun.recoilmat31_xrote[mat31] = Float.parseFloat(type[5]);
						gun.recoilmat31_yrote[mat31] = Float.parseFloat(type[6]);
						gun.recoilmat31_zrote[mat31] = Float.parseFloat(type[7]);
						gun.recoilmat31_xmove[mat31] = Float.parseFloat(type[8]);
						gun.recoilmat31_ymove[mat31] = Float.parseFloat(type[9]);
						gun.recoilmat31_zmove[mat31] = Float.parseFloat(type[10]);
						++mat31;
					}
					if (type[0].equals("Remat32") && mat32 < 30) {
						gun.recoilmat32_time[mat32] = Integer.parseInt(type[1]);
						gun.recoilmat32_xpoint[mat32] = Float.parseFloat(type[2]);
						gun.recoilmat32_ypoint[mat32] = Float.parseFloat(type[3]);
						gun.recoilmat32_zpoint[mat32] = Float.parseFloat(type[4]);
						gun.recoilmat32_xrote[mat32] = Float.parseFloat(type[5]);
						gun.recoilmat32_yrote[mat32] = Float.parseFloat(type[6]);
						gun.recoilmat32_zrote[mat32] = Float.parseFloat(type[7]);
						gun.recoilmat32_xmove[mat32] = Float.parseFloat(type[8]);
						gun.recoilmat32_ymove[mat32] = Float.parseFloat(type[9]);
						gun.recoilmat32_zmove[mat32] = Float.parseFloat(type[10]);
						++mat32;
					}
					if (type[0].equals("Rematlefthand") && matlefthand < 30) {
						gun.recoilmatlefthand_time[matlefthand] = Integer.parseInt(type[1]);
						gun.recoilmatlefthand_xpoint[matlefthand] = Float.parseFloat(type[2]);
						gun.recoilmatlefthand_ypoint[matlefthand] = Float.parseFloat(type[3]);
						gun.recoilmatlefthand_zpoint[matlefthand] = Float.parseFloat(type[4]);
						gun.recoilmatlefthand_xrote[matlefthand] = Float.parseFloat(type[5]);
						gun.recoilmatlefthand_yrote[matlefthand] = Float.parseFloat(type[6]);
						gun.recoilmatlefthand_zrote[matlefthand] = Float.parseFloat(type[7]);
						gun.recoilmatlefthand_xmove[matlefthand] = Float.parseFloat(type[8]);
						gun.recoilmatlefthand_ymove[matlefthand] = Float.parseFloat(type[9]);
						gun.recoilmatlefthand_zmove[matlefthand] = Float.parseFloat(type[10]);
						++matlefthand;
					}
					if (type[0].equals("Rematrighthand") && matrighthand < 30) {
						gun.recoilmatrighthand_time[matrighthand] = Integer.parseInt(type[1]);
						gun.recoilmatrighthand_xpoint[matrighthand] = Float.parseFloat(type[2]);
						gun.recoilmatrighthand_ypoint[matrighthand] = Float.parseFloat(type[3]);
						gun.recoilmatrighthand_zpoint[matrighthand] = Float.parseFloat(type[4]);
						gun.recoilmatrighthand_xrote[matrighthand] = Float.parseFloat(type[5]);
						gun.recoilmatrighthand_yrote[matrighthand] = Float.parseFloat(type[6]);
						gun.recoilmatrighthand_zrote[matrighthand] = Float.parseFloat(type[7]);
						gun.recoilmatrighthand_xmove[matrighthand] = Float.parseFloat(type[8]);
						gun.recoilmatrighthand_ymove[matrighthand] = Float.parseFloat(type[9]);
						gun.recoilmatrighthand_zmove[matrighthand] = Float.parseFloat(type[10]);
						++matrighthand;
					}
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		}catch (IOException e)
		{
		}
		
		
		
		
	}
	
}
