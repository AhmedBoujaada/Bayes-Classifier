package BayesClassifier;

import java.io.File;
import java.util.ArrayList;


public class Read {
	public static ArrayList<String> classes=new ArrayList<String>();
	public static ArrayList<String> f_paths=new ArrayList<String>();
	public static ArrayList<String> f_names=new ArrayList<String>();
	public static String file_path="",dir_name="",file_name="";
	public static ArrayList<String> readAllFiles(String path){
		File f=new File(path);
		File [] listOfFiles=f.listFiles();
		
		for (File file : listOfFiles)
		{
			if (file.isFile())
			{
				f_paths.add(file.getPath());
				f_names.add(file.getName()+"_"+dir_name);
			}
			if (file.isDirectory())
			{
				dir_name=file.getName();
				classes.add(file.getPath());
				readAllFiles(file.getPath());
			}
		}
		return f_paths;
	}

}
