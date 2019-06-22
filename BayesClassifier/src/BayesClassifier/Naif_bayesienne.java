package BayesClassifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Naif_bayesienne {
	public HashMap<Integer, HashMap<String,Float>> naif;
	HashMap<Integer,Float> prob;//probabilité de chaque classe
	
	
	public Naif_bayesienne() {
		this.naif = new HashMap<Integer, HashMap<String,Float>>();
		prob = new HashMap<Integer,Float>();
	}
	public static int get_classe(String path) {
		String[] pa = path.split("[_\\\\]");
		return Integer.parseInt(pa[7]);
	}
	public void training(ConcurrentHashMap<String,ConcurrentHashMap<String , Float>> matrice) {
		HashMap<Integer,Integer> n = new HashMap<Integer,Integer>();//le nbr des mots pour chaque classe n
		Set<String> unique = new HashSet<String>();// mot unique dans tous les documents m
		for(String Doc: matrice.keySet()) {// pour chaque document 
			int cl = get_classe(Doc);
			if(!naif.containsKey(cl)) {
				naif.put(cl, new HashMap<String,Float>());
				prob.put(cl, (float) 0.0);
				n.put(cl, 0);

			}
			prob.replace(cl, prob.get(cl) + 1);

			for (String mot : matrice.get(Doc).keySet()) {//pour chaque mot dans le document
				if(!naif.get(cl).containsKey(mot)) {
					naif.get(cl).put(mot, (float) 0.0);
				}
				naif.get(cl).replace(mot, naif.get(cl).get(mot) + matrice.get(Doc).get(mot));
				n.replace(cl, n.get(cl) + 1);
				unique.add(mot);

			}
		}
		int m = unique.size(); //Nombre des mots uniques
		for(int classe : naif.keySet()) {
			prob.replace(classe, prob.get(classe)/matrice.size());
			for(String mot : unique) {
				if(naif.get(classe).containsKey(mot)) {
					naif.get(classe).replace(mot, (float) ((naif.get(classe).get(mot) + 1.0)/(m+n.get(classe))));

				}
				else {
					naif.get(classe).put(mot,  (float) (1.0/(m+n.get(classe))));
				}
			}
		}		
	}
	public int[][] predict(ArrayList<Documents> list ) {
		int[][] mat = new int[list.size()][2];
		int progress = 0;
		System.out.print("Prediction: Loading ");
		for(int i=0;i<list.size();i++) {
			if(progress%(list.size()/10) == 0)
				System.out.print(". ");
			progress++;
			Documents doc = list.get(i);
			doc.load();
			mat[i][0] = get_classe(doc.path);
			double max = Double.MIN_VALUE;
			int classe= -1;
			for(int c : naif.keySet()) {
				float proba =  prob.get(c);				

				for(String word : naif.get(c).keySet()) {
					if(doc.words.contains(word)) {
						if(proba * naif.get(c).get(word) > Double.MIN_VALUE)
							proba*=naif.get(c).get(word);
					}
				}

				if(proba > max) {
					max = proba;
					classe = c;
				}

			}
			mat[i][1] = classe;
		}
		System.out.println("Done !");
		return mat;
	}

}