package BayesClassifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Map.Entry;
public class Chi2 {
		public static Float calcul(String terme, String Classe, ConcurrentMap<String, ConcurrentHashMap<String, Float>> cor) {

			float A=0, B=0; 
	        Set<Entry<String, ConcurrentHashMap<String, Float>>> set1 = cor.entrySet();
	        Set<Entry<String, Float>> set2;
	        float NCSize=0, CSize=0;
	        
	        
			for (Entry<String, ConcurrentHashMap<String, Float>> e1 : set1) {
				set2 = e1.getValue().entrySet();
				if(e1.getKey().startsWith(Classe)) {//ma classe
					CSize+=1;
		        	
		        	for(Entry<String, Float> e2: set2) {
		        		if(e2.getKey().equals(terme))
		        			A+=1;
		        			break;
		        	}
				
				}
				else {//les autres classes
					NCSize+=1;
		        	for(Entry<String, Float> e2: set2) {
		        		if(e2.getKey().equals(terme))
		        			B+=1;
		        			break;
		        	}
				}
			} 
			//float A= docTer;
			//float B= docNTer;
			float C= CSize-A;
			float D= NCSize-B;
			//float N=CSize+NCSize; 
			if((A+B)==0 || (A+C)==0||(B+D)==0||(C+D)==0) {
				//System.out.println(">>>>>"+0.0);
				return (float) 0.0;
			}else {
				float l= (float) ((CSize+NCSize)*Math.pow((A*D-B*C), 2)/((A+B)*(A+C)*(B+D)*(C+D)));
				System.out.println(">>>>>"+l);
				return l;
				
			}
		}
}