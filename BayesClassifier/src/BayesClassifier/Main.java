
package BayesClassifier;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

//import package1.Chi2;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		int n_folds = 7;
		System.out.println("Creation des folds");
			String[] folds = CrossVal.split(n_folds, "C:\\Users\\bdsas\\Desktop\\Text Mining\\20_newsgroupsall", "C:\\Users\\bdsas\\Desktop\\Text Mining\\HH\\"); //folds
			
			System.out.println("Creation du train ...");

			Occurence train = new Occurence();
			for(int i = 0;i<n_folds-1;i++) {
			    File dossier = new File(folds[i]);
			    File[] documents = dossier.listFiles();
			    for(File doc : documents) {
			    	System.out.println("to ==> "+doc.getPath());
			    	Documents d = new Documents(doc.getPath());
			    	
			    	d.load();
			    	train.TermFrequency(d);
			    }

			}
			System.out.println(train.matrice);
        	//test de Chi-2 -------------------------------------------------------------------------------------------------------------
        	
        	/*Set<Entry<String, Float>> set2;
        	ArrayList<Float> chi2= new ArrayList<Float>();
        	System.out.println("applicication du test khi-2... ");
			for (Entry<String, ConcurrentHashMap< String, Float>> a :train.matrice.entrySet()) {
				set2 = a.getValue().entrySet();
				for(Entry<String, Float> b: set2) {
					
					for (int i= 1; i<4; i++) {
					chi2.add(Chi2.calcul(b.getKey(),""+i,train.matrice));
					}
					
					if(Collections.max(chi2)<0.5) {
						//remove the terme from the corpus
						//System.out.println(Collections.max(chi2));
						set2.remove(b);
					
					}
				}

			} 
				System.out.println("chi-2 finished !");
				System.out.println("new corpus : "+train.matrice);*/
			System.out.println("Création du test ...");

			ArrayList<Documents> test = new ArrayList<Documents>();
		    File dossier = new File(folds[n_folds - 1]);
		    File[] documents = dossier.listFiles();
		    for(File doc : documents) {
		    	Documents d = new Documents(doc.getPath());
		    	d.load();
		    	test.add(d);
		    }
			System.out.println("Apprentissage...");

		    //Apprentissage
		    Naif_bayesienne nb = new Naif_bayesienne();
		    nb.training(train.matrice);
		    //Prediction 
			System.out.println("Prediction...");

		    int[][] prediction = nb.predict(test);
		    Metriques m = new Metriques();
			   m.scores(prediction, nb.prob.size());

			
	}

}