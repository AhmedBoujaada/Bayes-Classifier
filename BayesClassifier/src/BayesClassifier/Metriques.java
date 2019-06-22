package BayesClassifier;

import java.util.*;

public class Metriques {
	public void scores(int[][] matrice,int nclasse) {
		double[] score = new double[3];
		int[][] cm = new int[nclasse][nclasse];
		for(int i=0;i<nclasse;i++) {
			for(int j=0;j<nclasse;j++) {
				cm[i][j] = 0;
			}

		}
		for(int i=0;i<matrice.length;i++) {
			cm[matrice[i][0] - 1][matrice[i][1] - 1]++;
			
		}
		double precision = 0.0;
		double recall = 0.0;
		for(int cl =0;cl < nclasse;cl++) {
			int tp = cm[cl][cl];
			double fp =0.0;
			for(int i=0;i<nclasse;i++) {
				if( i == cl)continue;
				fp += cm[i][cl];
			}

			double fn =0.0;
			for(int i=0;i<nclasse;i++) {
				if( i == cl)continue;
				fn += cm[cl][i];
			}

			precision+=(tp/(tp+fp));
			recall+=(tp/(tp+fn));
			
		}
		precision/=nclasse;
		recall/=nclasse;
		System.out.println("Precision = "+precision);
		System.out.println("recall = "+recall);
		System.out.println("f-mesure = "+2*precision*recall/(precision+recall));

	}

}