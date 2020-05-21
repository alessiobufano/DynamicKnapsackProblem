package it.polito.tdp.dynamicknapsack.model;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		m.setModelloProblema(5, 9);
		
		System.out.println(m.stampaModello());
		
		//m.trovaSoluzioneOttima();
		
		//System.out.println("\n\nSoluzione ottima:\n"+m.stampaSoluzioneOttima());
		
		
		//m.trovaSoluzioneOttimaOrdinata();
		
		//System.out.println("\n\nSoluzione ottima:\n"+m.stampaSoluzioneOttima());
		
		/*
		m.ordinaVariabili();
		
		System.out.println("\n\nVariabili Ordinate:\n"+m.stampaVariabiliOrdinate());
		
		m.ripristinaPosizioniIniziali();
		
		System.out.println("\n\nDisordinate:\n");
		for(double i : m.getRapportiProfittiPesi())
			System.out.print(i+" ");
		System.out.println("\n");
		for(int i : m.getPesi())
			System.out.print(i+" ");
		System.out.println("\n");
		for(int i : m.getProfitti())
			System.out.print(i+" ");
		System.out.println("\n");
		for(int i : m.getProfittiPercentuali().get(2))
			System.out.print(i+" ");
		*/

		m.ordinaVariabili();
		
		
		m.trovaSoluzioneTramiteOttimoPeriodaleProfittiPercentuali();
		
		System.out.print("\n\nSoluzione ottimo periodale:\n"+m.stampaSoluzioneEuristicaPeriodale());
	
		/*
		m.trovaSoluzioneTramiteOttimoPeriodaleProfittiPercentuali();
		
		System.out.print("\n\nSoluzione ottimo periodale:\n"+m.stampaSoluzioneEuristicaPeriodale());

		
		m.trovaSoluzioneEsaurimentoZainoOrdinato();
		
		System.out.print("\n\nSoluzione esaurimento:\n"+m.stampaVariabiliOrdinate()+"\n\n"+m.stampaSoluzioneEuristicaEsaurimento());

		m.trovaSoluzioneEsaurimentoZainoOrdinatoProfittiPercentuali();
		
		System.out.print("\n\nSoluzione esaurimento:\n"+m.stampaVariabiliOrdinate()+"\n\n"+m.stampaSoluzioneEuristicaEsaurimento());
*/
		
		m.modificaSoluzione(m.trovaSoluzioneTramiteOttimoPeriodale());
		
		System.out.print("\n\nSoluzione MODIFICATA ottimo periodale:\n"+m.stampaSoluzioneEuristicaModificata());
		
		
		System.out.print("\n\nperiodale:"+m.getOttimoEuristicoPeriodale()+" modificato:"+m.getOttimoEuristicoModificato());

		
		
	}

}
