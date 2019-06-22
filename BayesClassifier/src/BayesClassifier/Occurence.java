package BayesClassifier;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Occurence {
	
	
	
	public ConcurrentHashMap<String,ConcurrentHashMap<String , Float>> matrice;
	public Set<String> unique;

	int occ;
	
	
	public Occurence() {
		this.matrice = new ConcurrentHashMap<String,ConcurrentHashMap<String , Float>>();
		
	}
	
	
	public void TermFrequency(Documents Doc) {
		Set<String> unique = new HashSet<String>(Doc.words);
		ConcurrentHashMap<String,Float> tf=new ConcurrentHashMap<String,Float>();
		for (String key : unique) {
			float occ =  Collections.frequency(Doc.words, key);
		    tf.put(key,occ); 
		}
		this.matrice.put(Doc.path, tf);
		
	}
	public static void main(String[] args) {
		Documents s = new Documents("C:\\Users\\bdsas\\Desktop\\Text Mining\\HHH\\fold0\\1_0.txt");
		s.load();
		Occurence c = new Occurence();
		c.TermFrequency(s);
		for(String st : c.matrice.keySet()) {
			for(String g : c.matrice.get(st).keySet()) {
				System.out.println("Word : "+g+"  : "+c.matrice.get(st).get(g));
			}
		}
		
	}

}