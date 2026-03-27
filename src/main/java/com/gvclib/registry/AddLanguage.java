package gvclib.registry;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;



public class AddLanguage
{
  static List<ArrayList<String>> mainList = new ArrayList();
  public static List Guns = new ArrayList();
  
  public static String[] soundsfile = new String[4095];
  public static String[] blocksfile = new String[4095];
  public static String[] itemfile = new String[4095];
  
  public static void load(File configfile, boolean isClient, String modname, File dire)
  {
	  int files = 0;
		File directory1 = new File(dire, "mods" + File.separatorChar + modname
				+ File.separatorChar + "assets" + File.separatorChar + modname + File.separatorChar +
				"lang" + File.separatorChar + "gunname");
		directory1.mkdirs();
		File[] filelist1 = directory1.listFiles();
		for (int i = 0; i < filelist1.length; i++) {
			if (filelist1[i].isFile()) {
				soundsfile[i] = filelist1[i].getName();
			}
		}
		File directory2 = new File(dire, "mods" + File.separatorChar + modname
				+ File.separatorChar + "assets" + File.separatorChar + modname + File.separatorChar +
				"lang" + File.separatorChar + "block");
		directory2.mkdirs();
		File[] filelist2 = directory2.listFiles();
		for (int i = 0; i < filelist2.length; i++) {
			if (filelist2[i].isFile()) {
				blocksfile[i] = filelist2[i].getName();
			}
		}
		File directory3 = new File(dire, "mods" + File.separatorChar + modname
				+ File.separatorChar + "assets" + File.separatorChar + modname + File.separatorChar +
				"lang" + File.separatorChar + "item");
		directory3.mkdirs();
		File[] filelist3 = directory3.listFiles();
		for (int i = 0; i < filelist3.length; i++) {
			if (filelist3[i].isFile()) {
				itemfile[i] = filelist3[i].getName();
			}
		}
	    
	    
	    {
			try {
				File newfile = new File(dire,
						"mods" + File.separatorChar + modname + File.separatorChar + "assets"
								+ File.separatorChar + modname + File.separatorChar + "lang" + File.separatorChar
								+ "en_us.lang");
				newfile.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(newfile));
				bw.write("#");
				bw.newLine();
				for (int i = 0; i < filelist1.length; i++) {
					String s = "item." + soundsfile[i] + ".name=" + soundsfile[i];
					bw.write(s);
					bw.newLine();
				}
				for (int i = 0; i < filelist2.length; i++) {
					String s = "tile." + blocksfile[i] + ".name=" + blocksfile[i];
					bw.write(s);
					bw.newLine();
				}
				for (int i = 0; i < filelist3.length; i++) {
					String s = "item." + itemfile[i] + ".name=" + itemfile[i];
					bw.write(s);
					bw.newLine();
				}
				bw.close();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	    }
	    {
			try {
				File newfile = new File(dire,
						"mods" + File.separatorChar + modname + File.separatorChar + "assets"
								+ File.separatorChar + modname + File.separatorChar + "lang" + File.separatorChar
								+ "ja_jp.lang");
				newfile.createNewFile();
				//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile),"UTF-8"));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile)));
				//BufferedReaderクラスを使用する
				//BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(newfile),"UTF-8"));
				//PrintWriterクラスを使用する
				//PrintWriter bwr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile),"UTF-8")));
				
				bw.write("#");
				bw.newLine();
				for (int i = 0; i < filelist1.length; i++) {
					String name = soundsfile[i];
					if (checkBeforeReadfile(filelist1[i]))
			          {
						BufferedReader br = new BufferedReader(new FileReader(filelist1[i])); // ファイルを開く
						String str;
						while ((str = br.readLine()) != null) { // 1行ずつ読み込む
							String[] type = str.split(",");
							if(type[0] != null){
								name = type[0];
							}
						}
						br.close();
			          }
					String s = "item." + soundsfile[i] + ".name=" + name;
					bw.write(s);
					bw.newLine();
				}
				for (int i = 0; i < filelist2.length; i++) {
					String name = blocksfile[i];
					if (checkBeforeReadfile(filelist2[i]))
			          {
						BufferedReader br = new BufferedReader(new FileReader(filelist2[i])); // ファイルを開く
						String str;
						while ((str = br.readLine()) != null) { // 1行ずつ読み込む
							String[] type = str.split(",");
							if(type[0] != null){
								name = type[0];
							}
						}
						br.close();
			          }
					String s = "tile." + blocksfile[i] + ".name=" + name;
					bw.write(s);
					bw.newLine();
				}
				for (int i = 0; i < filelist3.length; i++) {
					String name = itemfile[i];
					if (checkBeforeReadfile(filelist3[i]))
			          {
						BufferedReader br = new BufferedReader(new FileReader(filelist3[i])); // ファイルを開く
						String str;
						while ((str = br.readLine()) != null) { // 1行ずつ読み込む
							String[] type = str.split(",");
							if(type[0] != null){
								name = type[0];
							}
						}
						br.close();
			          }
					String s = "item." + itemfile[i] + ".name=" + name;
					bw.write(s);
					bw.newLine();
				}
				bw.close();
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	    }
	    
	    
	    
  }
  
  public static String getNameWithoutExtension(File[] filelist1, int i)
  {
    String fileName = filelist1[i].getName();
    int index = fileName.lastIndexOf('.');
    if (index!=-1)
    {
      return fileName.substring(0, index);
    }
    return "";
  }
  
  private static boolean checkBeforeReadfile(File file){
	    if (file.exists()){
	      if (file.isFile() && file.canRead()){
	        return true;
	      }
	    }

	    return false;
	  }
}
