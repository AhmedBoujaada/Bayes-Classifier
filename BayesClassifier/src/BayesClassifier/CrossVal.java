package BayesClassifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
//import org.tartarus.snowball.ext.PorterStemmer;

public class CrossVal  {
	
	//ajouter les fichier vers les distinations avec _classe 
	public static void add_to_fold(String origin,String folds, String destination,File file,int classe) throws IOException {
        Path path = Paths.get(folds+destination);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        File from = new File(file.getPath());
        File to = new File(folds+destination+"\\"+file.getName());//+"_"+classe
        
        FileUtils.copyFile(from, to);

    }
	// n nombre des folds, source les fichiers orig, destination vers les dossier 
	public static String[] split(int n,String path_source, String path_destination) throws IOException {
		File directory = new File(path_source);
	    // get all the files from a directory
	    File[] folders = directory.listFiles();
	    int i = 1;
	    for (File folder : folders) {
	    	File[] fList = folder.listFiles();
	    	String chemin = folder.getPath();
	    	int j = n;
	    	for(File file : fList) {
	    		String s = "fold"+j%n;
			    add_to_fold(chemin, path_destination,s, file, i);
			    j++;
	    	}
	    i++;
	    }
	    File dossier = new File(path_destination);
	    File[] folds = dossier.listFiles();
	    String[] train_test = new String[n];
	    int index = 0;
	    for(File fold : folds) {
	    	
	    	train_test[index] = fold.getPath();
	    	index++;
	    }
	    return train_test;
		}
}