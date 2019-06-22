package BayesClassifier;


import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.tartarus.*;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.PorterStemmer;
import org.apache.lucene.*;

public class Documents {
	public HashMap<String,Double> tf;
	//public  HashMap<String,Double> tfidf;

	public String path;
	int classe;
	ArrayList<String> words;
	final static ArrayList<String> stopwords = load("C:\\Users\\bdsas\\Desktop\\Text Mining\\stopWordsEN.txt");
	
	
	public Documents(String path) {
		this.path = path;
		this.words = new ArrayList<String>();
	}
	public String stem(String word) {
		if (this.stopwords.contains(word)) return "";
		PorterStemmer s = new PorterStemmer();
		s.setCurrent(word);
		s.stem();
		return  s.getCurrent();
	}
	
	
	public void load(){
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					new FileInputStream(this.path), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Encoding error !");
		} catch (FileNotFoundException e) {
			System.out.println("File not Found !");
		}
		
		String line;
		String text = ""; 
		try {
			while ((line = in.readLine()) != null) {
				String words[] = line.replaceAll("[^\\w\\s]"," ").split("\\s");
				line = "";
				for(String word: words) {
					String w = word.toLowerCase();
					String stemmed = this.stem(w);
					if(!stemmed.isEmpty())
					line += stemmed+" ";
				}
				text+=" "+line;
			}
			String[]  result = text.split("\\s");
			ArrayList<String> un_words = new ArrayList<String>(Arrays.asList(result));
			for(String word : un_words) {
				String cleaned = word.replace(" ", "");
				if(!cleaned.isEmpty()) {
					this.words.add(cleaned);
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("I/O error");		}

	}
	
	public static ArrayList<String> load(String path) {
		ArrayList<String> arr = new ArrayList<String>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Encoding error !");
		} catch (FileNotFoundException e) {
			System.out.println("File not Found !");
		}
		
		String line;
		String text = ""; 
		try {
			while ((line = in.readLine()) != null) {
				arr.add(line);
			}
			return arr;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("I/O error");		}
		return arr;

	}

	/*public static void main(String[] args) {
		Documents d = new Documents("sss");
		//System.out.println(d.stem());
	}
	*/

}