package it.polito.tdp.dynamicknapsack.model;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		m.setModelloProblema(9, 5);
		
		System.out.println(m.stampaModello());
		
		m.trovaSoluzioneOttimaOrdinata();
		
		System.out.println(m.stampaSoluzione());
		
		
	}

}
