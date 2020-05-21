package it.polito.tdp.dynamicknapsack.model;

import java.text.*;
import java.util.*;


public class Model {
	
	//tutti gli array necessari per costruire il modello
	private Map<Integer, Integer[]> variabili;
	private Integer[] pesi;
	private Map<Integer, Integer> capacitaMax;
	private Integer[] profitti;
	private Map<Integer, Integer[]> profittiPercentuali;   //per implementazione con profitti che diminuiscono
	private Double[] rapportiProfittiPesi;
	private Integer[] pesiIniziali;
	private Integer[] profittiIniziali;
	private Map<Integer, Integer[]> profittiPercentualiIniziali;
	private Double[] rapportiProfittiPesiIniziali;
	private Integer[] vettorePosizioniVariabiliOrdinate;   //per salvare l'ordinamento delle variabili in base a rapporto profitto/peso decrescente
	
	//variabili accessorie per costruire il modello (ovviamente sono fissate arbitrariamente e possono essere modificate)
	private final static double  percentualeSommaPesiMax = 0.5;
	private final static double percentualeSommaPesiMin = 0.1;
	private final static double percentualeProfittiMax = 1.0;
	private final static double percentualeProfittiMin = 0.1;
	
	
	//strumenti per la risoluzione del problema
	private int numVariabili;
	private int numPeriodi;
	private int ottimo;
	private Map<Integer, List<Integer>> soluzioneOttima;
	private int ottimoEuristicoEsaurimento;
	private Map<Integer, List<Integer>> soluzioneEuristicaEsaurimento;
	private int ottimoEuristicoPeriodale;
	private Map<Integer, List<Integer>> soluzioneEuristicaPeriodale;
	private int ottimoEuristicoModificato;
	private Map<Integer, List<Integer>> soluzioneEuristicaModificata;
	
	
	public Model() {
		this.ottimo = 0;
		this.soluzioneOttima = new HashMap<>();
		this.ottimoEuristicoEsaurimento = 0;
		this.soluzioneEuristicaEsaurimento = new HashMap<>();
		this.ottimoEuristicoPeriodale = 0;
		this.soluzioneEuristicaPeriodale = new HashMap<>();
		this.ottimoEuristicoModificato = 0;
		this.soluzioneEuristicaModificata = new HashMap<>();
	}
	
	public Map<Integer, Integer[]> getVariabili() {
		return variabili;
	}

	public Integer[] getPesi() {
		return pesi;
	}

	public Map<Integer, Integer> getCapacitaMax() {
		return capacitaMax;
	}
	
	public Integer[] getProfitti() {
		return profitti;
	}
	
	public Map<Integer, Integer[]> getProfittiPercentuali() {
		return profittiPercentuali;
	}

	public Double[] getRapportiProfittiPesi() {
		return rapportiProfittiPesi;
	}
	
	public Integer[] getPesiIniziali() {
		return pesiIniziali;
	}
	
	public Integer[] getProfittiIniziali() {
		return profittiIniziali;
	}
	
	public Map<Integer, Integer[]> getProfittiPercentualiIniziali() {
		return profittiPercentualiIniziali;
	}
	
	public Double[] getRapportiProfittiPesiIniziali() {
		return rapportiProfittiPesiIniziali;
	}
	
	public Integer[] getVettorePosizioniVariabiliOrdinate() {
		return vettorePosizioniVariabiliOrdinate;
	}

	
	public double getPercentualeSommaPesiMax() {
		return percentualeSommaPesiMax;
	}

	public double getPercentualeSommaPesiMin() {
		return percentualeSommaPesiMin;
	}
	
	public double getPercentualeProfittiMax() {
		return percentualeProfittiMax;
	}

	public double getPercentualeProfittiMin() {
		return percentualeProfittiMin;
	}
	
	
	public int getNumVariabili() {
		return numVariabili;
	}

	public int getNumPeriodi() {
		return numPeriodi;
	}

	public int getOttimo() {
		return ottimo;
	}

	public Map<Integer, List<Integer>> getSoluzioneOttima() {
		return soluzioneOttima;
	}
	
	public int getOttimoEuristicoEsaurimento() {
		return ottimoEuristicoEsaurimento;
	}

	public Map<Integer, List<Integer>> getSoluzioneEuristicaEsaurimento() {
		return soluzioneEuristicaEsaurimento;
	}

	public int getOttimoEuristicoPeriodale() {
		return ottimoEuristicoPeriodale;
	}
	
	public  Map<Integer, List<Integer>> getSoluzioneEuristicaPeriodale() {
		return soluzioneEuristicaPeriodale;
	}
	
	public int getOttimoEuristicoModificato() {
		return ottimoEuristicoModificato;
	}
	
	public  Map<Integer, List<Integer>> getSoluzioneEuristicaModificata() {
		return soluzioneEuristicaModificata;
	}
	
	
	//metodo per costruire il modello in base al numero di variabili e di periodi
	public void setModelloProblema(int numPeriodi, int numVariabili) {
		
		this.numVariabili = numVariabili;
		this.numPeriodi = numPeriodi;
		
		this.variabili = new HashMap<>();
		
		this.pesi = new Integer[numVariabili];
		this.capacitaMax = new HashMap<>();
		
		this.profitti = new Integer[numVariabili];
		this.profittiPercentuali = new HashMap<>();
		
		this.rapportiProfittiPesi = new Double[numVariabili];
		
		this.pesiIniziali = new Integer[numVariabili];
		this.profittiIniziali = new Integer[numVariabili];
		this.profittiPercentualiIniziali = new HashMap<>();
		this.rapportiProfittiPesiIniziali = new Double[numVariabili];
		
		int sommaPesi = 0;  //utile per poter poi calcolare la capacità massima in modo "razionale"
		
		for(int n=0; n<numVariabili; n++)
		{
			int profitto = (int) (Math.random() * 101);
			this.profitti[n] = profitto;
			this.profittiIniziali[n] = profitto;
			
			int peso = 0;
			while(peso<=0) {
				peso = (int) (Math.random() * 101);
			}
			this.pesi[n] = peso;
			this.pesiIniziali[n] = peso;
			
			double rapporto = (double) profitto/peso;
			this.rapportiProfittiPesi[n] = rapporto;
			this.rapportiProfittiPesiIniziali[n] = rapporto;
			
			sommaPesi += peso;
		}
		
		double aumentoPercentualeSommaPesi = (percentualeSommaPesiMax - percentualeSommaPesiMin)/(numPeriodi-1);
		double diminuzionePercentualeProfitti = (percentualeProfittiMax - percentualeProfittiMin)/(numPeriodi-1);
		
		for(int p=1; p<=numPeriodi; p++)
		{	
			Integer[] vettVariabili = new Integer[numVariabili];
			for(int n=0; n<numVariabili; n++)
				vettVariabili[n] = 0;
			this.variabili.put(p, vettVariabili);
		
			double percentualeSommaPesi = percentualeSommaPesiMin + aumentoPercentualeSommaPesi*(p-1);
			int capacitaMaxPeriodo = (int) (sommaPesi*percentualeSommaPesi);   //in modo da avere una capacità incrementale
			this.capacitaMax.put(p, capacitaMaxPeriodo);
			
			double percentualeProfitti = percentualeProfittiMax - diminuzionePercentualeProfitti*(p-1);
			Integer[] vettProfitti = new Integer[numVariabili];
			for(int i=0; i<numVariabili; i++)
				vettProfitti[i] = (int) ((int) profitti[i]*percentualeProfitti);  //in modo da diminuire progressivamente i vari profitti 
			this.profittiPercentuali.put(p, vettProfitti);
			this.profittiPercentualiIniziali.put(p, vettProfitti);
			
		}
	}
	
	
	//metodo per ordinare le variabili in base al rapporto profitto/peso decrescente
	public void ordinaVariabili() {
	
		this.ripristinaPosizioniIniziali();
		this.vettorePosizioniVariabiliOrdinate = new Integer[numVariabili];
		for(int pos=0; pos<numVariabili; pos++)
			vettorePosizioniVariabiliOrdinate[pos] = pos;
		int n=0;
		do
		{
			double max = this.rapportiProfittiPesi[n];
			for(int i=n+1; i<numVariabili; i++)
			{
				if(this.rapportiProfittiPesi[i]>max)
				{
					int pos = vettorePosizioniVariabiliOrdinate[i];
					vettorePosizioniVariabiliOrdinate[i] = vettorePosizioniVariabiliOrdinate[n];
					vettorePosizioniVariabiliOrdinate[n] = pos;
					
					max = this.rapportiProfittiPesi[i];
					this.rapportiProfittiPesi[i] = this.rapportiProfittiPesi[n];
					this.rapportiProfittiPesi[n] = max;
					
					int pesoMax = this.pesi[i];
					this.pesi[i] = this.pesi[n];
					this.pesi[n] = pesoMax;
					
					int profittoMax = this.profitti[i];
					this.profitti[i] = this.profitti[n];
					this.profitti[n] = profittoMax;
					
					for(int p=1; p<=numPeriodi; p++)
					{
						Integer vettore[] = this.profittiPercentuali.get(p);
						int profittoPercentualeMax = vettore[i];
						vettore[i] = vettore[n];
						vettore[n] = profittoPercentualeMax;
					}
				}
			}
			n++;
		}
		while(n<numVariabili);	
	}
	
	//metodo per ripristina variabili prima dell'ordinamento
	public void ripristinaPosizioniIniziali() {
		
		this.vettorePosizioniVariabiliOrdinate = new Integer[numVariabili];
		
		for(int i=0; i<numVariabili; i++)
		{
			this.vettorePosizioniVariabiliOrdinate[i] = i;
			this.pesi[i] = this.pesiIniziali[i];
			this.profitti[i] = this.profittiIniziali[i];
			this.rapportiProfittiPesi[i] = this.rapportiProfittiPesiIniziali[i];
		}
		
		double diminuzionePercentualeProfitti = (percentualeProfittiMax - percentualeProfittiMin)/(numPeriodi-1);
		this.profittiPercentuali.clear();
		for(int p=1; p<=numPeriodi; p++)
		{	
			double percentualeProfitti = percentualeProfittiMax - diminuzionePercentualeProfitti*(p-1);
			Integer[] vettProfitti = new Integer[numVariabili];
			for(int i=0; i<numVariabili; i++)
				vettProfitti[i] = (int) ((int) profitti[i]*percentualeProfitti);  //in modo da diminuire progressivamente i vari profitti 
			this.profittiPercentuali.put(p, vettProfitti);
		}
	}
   
	
	//metodo per calcolare se vi sono ancora posti disponibili per altre variabili (oltre a quelle già aggiunte) 
	//in corrispondenza di quel determinato periodo (variabile livello)  
	private Map<Integer, Integer> sottovettorePesi(Map<Integer, Integer[]> parziale, int livello, int pos) {
		
		int capacitaRestante = this.capacitaRestante(livello, parziale);
		Map<Integer, Integer> sottovettore = new HashMap<>();
		Integer[] vettore = parziale.get(livello);
		for(int i=pos+1; i<pesi.length; i++)
			if(pesi[i]<=capacitaRestante && vettore[i]==0)
				sottovettore.put(i, pesi[i]);
		return sottovettore;
	}
	
	private Integer[] sottovettorePesiPeriodo(Map<Integer, Integer[]> parziale, int livello) {
		
		int capacitaRestante = this.capacitaRestante(livello, parziale);
		int dim = 0;
		Integer[] vettore = parziale.get(livello);
		for(int i=0; i<pesi.length; i++)
			if(pesi[i]<=capacitaRestante && vettore[i]==0)
				dim++;
		if(dim>0)
		{
			Integer[] sottovettore = new Integer[dim];
			int j=0;
			for(int i=0; i<pesi.length; i++)
			{
				if(pesi[i]<=capacitaRestante && vettore[i]==0)
				{
					sottovettore[j] = i;
					j++;
				}
			}
			return sottovettore;
		}
		
		return null;
	}
	
	//metodo per calcolare sottovettore di un periodo per modificare soluzione
	/*
	private Integer[] sottovettoreVariabiliPeriodo(Map<Integer, Integer[]> parziale, int livello) {
		
		int capacitaRestante = this.capacitaRestantePeriodo(livello, parziale);
		int dim = 0;
		Integer[] vettore = parziale.get(livello);
		for(int i=0; i<pesi.length; i++)
			if(pesi[i]<=capacitaRestante && vettore[i]==0)
				dim++;
		if(dim>0)
		{
			Integer[] sottovettore = new Integer[dim];
			int j=0;
			for(int i=0; i<pesi.length; i++)
			{
				if(pesi[i]<=capacitaRestante && vettore[i]==0)
				{
					sottovettore[j] = i;
					j++;
				}
			}
			return sottovettore;
		}
		
		return null;
	}
	*/
	
	//metodo per calcolare la capacità restante di un periodo data tutta la soluzione 
	private int capacitaRestante(int livello, Map<Integer, Integer[]> parziale) {
		
		int capacitaRestante = this.capacitaMax.get(livello);
		Integer[] vettore = parziale.get(livello);
		for(int i=0; i<vettore.length; i++)
			if(vettore[i]==1)
				capacitaRestante -= pesi[i];
		return capacitaRestante;
	}
	
	//metodo per calcolare la capacità restante in modo da poter modificare la soluzione
	private int capacitaRestantePeriodo(int livello, Map<Integer, Integer[]> parziale) {
		
		int capacitaPeriodo = this.capacitaMax.get(livello);
		int capacitaRestante = capacitaPeriodo;
		if(livello>2)
		{
			Integer[] vettore1 = parziale.get(livello-2);
			Integer[] vettore2 = parziale.get(livello-1);
			for(int i=0; i<numVariabili; i++)
				if(vettore1[i]==1 && vettore2[i]==1)
					capacitaRestante -= pesi[i];
		}
		return capacitaRestante;
	}

	//metodo per verificare che il vincolo Xit >= Xi(t-1) venga rispettato per tutte le variabili e per ogni periodo
	private boolean vincoliVariabili(Map<Integer, Integer[]> parziale) {
		
		boolean valida = true;
		for(int i=parziale.size(); i>=2; i--)
		{
			Integer[] vettoreSucc = parziale.get(i);
			Integer[] vettorePrec = parziale.get(i-1);
			for(int pos=0; pos<vettoreSucc.length; pos++)
				if(vettoreSucc[pos]<vettorePrec[pos])
					valida = false;
		}
		return valida;
	}
	
	//metodo per verificare che sommatoria(Wit) <= WMAXt per ogni t
	//ossia in ogni periodo la somma dei pesi delle variabili scelte per quel determinato periodo sia minore o uguale della capacità massima relativa
	private boolean vincoloCapacita(Map<Integer, Integer[]> parziale) {
		
		boolean valida = true;
		for(int i=1; i<=parziale.size(); i++)
		{
			int pesoParziale = 0;
			Integer[] vettore = parziale.get(i);
			for(int pos=0; pos<vettore.length; pos++)
				if(vettore[pos]==1)
					pesoParziale += this.pesi[pos];
			
			if(pesoParziale>this.capacitaMax.get(i))
				valida = false;
		}
		return valida;
	}
	
	//metodo per verificare se la soluzione parziale trovata è valida (ossia rispetta tutti i vincoli)
	private boolean soluzioneValida(Map<Integer, Integer[]> parziale) {
		
		boolean valida = false;
		if(this.vincoliVariabili(parziale) && this.vincoloCapacita(parziale))
			valida = true;
		
		return valida;
	}
	
	//metodo per salvare una soluzione ottima valida
	private void setSoluzioneOttima(Map<Integer, Integer[]> parziale) {
		
		this.soluzioneOttima.clear();
		List<List<Integer>> listaL = new LinkedList<>();
		for(Integer[] i : parziale.values())
		{
			List<Integer> lista = new LinkedList<>();
			for(int j : i)
				lista.add(j);
			listaL.add(lista);
		}	
		for(int p=1; p<=numPeriodi; p++)
			this.soluzioneOttima.put(p, listaL.get(p-1));
	}
	
	//metodo per salvare una soluzione ottima valida con ricerca ordinata per rapporti
	private void setSoluzioneOttimaOrdinata(Map<Integer, Integer[]> parziale) {
		
		this.soluzioneOttima.clear();
		List<List<Integer>> listaL = new LinkedList<>();
		for(Integer[] i : parziale.values())
		{
			List<Integer> lista = new LinkedList<>();
			for(int pos=0; pos<i.length; pos++)
				lista.add(i[this.posizioneIndiceVettoreOrdinato(pos, this.vettorePosizioniVariabiliOrdinate)]);
			listaL.add(lista);
		}	
		for(int p=1; p<=numPeriodi; p++)
			this.soluzioneOttima.put(p, listaL.get(p-1));
	}
	
	//metodo ausiliario per metodo precedente
	private int posizioneIndiceVettoreOrdinato(int pos, Integer[] vettoreOrdinato) {
		int val = pos;
		for(int j=0; j<numVariabili; j++)
			if(vettoreOrdinato[j]==pos)
				val = j;
		return val;
	}
	
	
	//metodo per salvare una soluzione euristica valida ottenuta ottimizzando ogni periodo
	private void setSoluzioneEuristicaPeriodale(Map<Integer, Integer[]> parziale) {
		
		if(this.soluzioneValida(parziale))
		{
			this.soluzioneEuristicaPeriodale.clear();
			List<List<Integer>> listaL = new LinkedList<>();
			for(Integer[] i : parziale.values())
			{
				List<Integer> lista = new LinkedList<>();
				for(int j : i)
					lista.add(j);
				listaL.add(lista);
			}	
			for(int p=1; p<=numPeriodi; p++)
				this.soluzioneEuristicaPeriodale.put(p, listaL.get(p-1));
		}
	}	
		
	//metodo per salvare una soluzione euristica valida con ricerca ordinata per rapporti
	private void setSoluzioneEuristicaEsaurimento(Map<Integer, Integer[]> parziale) {
		
		if(this.soluzioneValida(parziale))
		{
			this.soluzioneEuristicaEsaurimento.clear();
			List<List<Integer>> listaL = new LinkedList<>();
			for(Integer[] i : parziale.values())
			{
				List<Integer> lista = new LinkedList<>();
				for(int pos=0; pos<i.length; pos++)
					lista.add(i[this.posizioneIndiceVettoreOrdinato(pos, this.vettorePosizioniVariabiliOrdinate)]);
				listaL.add(lista);
			}	
			for(int p=1; p<=numPeriodi; p++)
				this.soluzioneEuristicaEsaurimento.put(p, listaL.get(p-1));
		}
	}
	
	private void setSoluzioneEuristicaModificata(Map<Integer, Integer[]> parziale) {
		
		if(this.soluzioneValida(parziale))
		{
			this.soluzioneEuristicaModificata.clear();
			List<List<Integer>> listaL = new LinkedList<>();
			for(Integer[] i : parziale.values())
			{
				List<Integer> lista = new LinkedList<>();
				for(int pos=0; pos<i.length; pos++)
					lista.add(i[this.posizioneIndiceVettoreOrdinato(pos, this.vettorePosizioniVariabiliOrdinate)]);
				listaL.add(lista);
			}	
			for(int p=1; p<=numPeriodi; p++)
				this.soluzioneEuristicaModificata.put(p, listaL.get(p-1));
		}
	}
		
	
	//metodo per calcolare l'ottimo data una certa soluzione
	private int calcolaOttimoParziale(Map<Integer, Integer[]> parziale) {
		
		int ottimoParziale = 0;
		for(int i=1; i<=parziale.size(); i++)
		{
			Integer[] vettore = parziale.get(i);
			for(int pos=0; pos<vettore.length; pos++)
				if(vettore[pos]==1)
					ottimoParziale += this.profitti[pos];
		}
		return ottimoParziale;
	}
	
	//metodo per calcolare l'ottimo data una certa soluzione nell'implementazione con profitti decrescenti 
	private int calcolaOttimoParzialeProfittiPercentuali(Map<Integer, Integer[]> parziale) {
		
		int ottimoParziale = 0;
		for(int i=1; i<=parziale.size(); i++)
		{
			Integer[] vettore = parziale.get(i);
			for(int pos=0; pos<vettore.length; pos++)
				if(vettore[pos]==1)
					ottimoParziale += this.profittiPercentuali.get(i)[pos];
		}
		return ottimoParziale;
	}
	
	
	//metodo per trovare la soluzione ottima nel caso base (vedi funzione ricorsiva sotto)
	public Map<Integer, List<Integer>> trovaSoluzioneOttima() {
		
		this.ripristinaPosizioniIniziali();
		
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneOttima = new HashMap<>();
		this.ottimo = 0;
		
		this.funzioneRicorsiva(parziale, 1);
		
		return soluzioneOttima;
	}

	private void funzioneRicorsiva(Map<Integer, Integer[]> parziale, int livello) {
		
		//casi terminali ricorsione:
		
		if(!this.vincoliVariabili(parziale) || !this.vincoloCapacita(parziale))
			return;
		
		if(livello==numPeriodi+1 && this.soluzioneValida(parziale) && this.calcolaOttimoParziale(parziale)>ottimo)
		{
			this.setSoluzioneOttima(parziale);
			this.ottimo = this.calcolaOttimoParziale(parziale);
			return;
		}
		
		if(livello>numPeriodi)
			return;
		
		//caso intermedio:
		
		Integer[] vettore = parziale.get(livello);
		for(int pos=0; pos<vettore.length; pos++)
		{
			if(vettore[pos]==0)
			{
				vettore[pos] = 1;
				if(this.vincoloCapacita(parziale))
				{
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 1;
					this.funzioneRicorsiva(parziale, livello+1);
					if(pos<vettore.length-1 && this.sottovettorePesi(parziale, livello, pos).size()>0)
						this.funzioneRicorsiva(parziale, livello);
					
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 0;
				}	
				
				vettore[pos] = 0;
				this.funzioneRicorsiva(parziale, livello+1);
			}
			
		}
	}
	
	
	//metodo per trovare la soluzione ottima nel caso base ma ordinando le variabili in base ai rapporti profitti/pesi decrescenti (vedi anche funzione ricorsiva sotto)
	public Map<Integer, List<Integer>> trovaSoluzioneOttimaOrdinata() {
		
		this.ordinaVariabili();
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneOttima = new HashMap<>();
		this.ottimo = 0;
		
		this.funzioneRicorsivaOrdinata(parziale, 1);
		
		return soluzioneOttima;
	}

	private void funzioneRicorsivaOrdinata(Map<Integer, Integer[]> parziale, int livello) {
		
		//casi terminali ricorsione:
		
		if(!this.vincoliVariabili(parziale) || !this.vincoloCapacita(parziale))
			return;
		
		if(livello==numPeriodi+1 && this.soluzioneValida(parziale) && this.calcolaOttimoParziale(parziale)>ottimo)
		{
			this.setSoluzioneOttimaOrdinata(parziale);
			this.ottimo = this.calcolaOttimoParziale(parziale);
			return;
		}
		
		if(livello>numPeriodi)
			return;
		
		//caso intermedio:
		
		Integer[] vettore = parziale.get(livello);
		for(int pos=0; pos<vettore.length; pos++)
		{
			if(vettore[pos]==0)
			{
			    vettore[pos] = 1;
				if(this.vincoloCapacita(parziale))
				{
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 1;
					this.funzioneRicorsivaOrdinata(parziale, livello+1);
					if(pos<vettore.length-1 && this.sottovettorePesi(parziale, livello, pos).size()>0)
						this.funzioneRicorsivaOrdinata(parziale, livello);
					
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 0;
				}	
				
				vettore[pos] = 0;
				this.funzioneRicorsivaOrdinata(parziale, livello+1);
			}
			
		}
	}
	
	
	//metodo per trovare soluzione ottima nel caso di profitti decresenti nei periodi (vedi funzione ricorsiva sotto)
	public Map<Integer, List<Integer>> trovaSoluzioneOttimaProfittiPercentuali() {
		
		this.ripristinaPosizioniIniziali();
		
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneOttima = new HashMap<>();
		this.ottimo = 0;
		
		this.funzioneRicorsivaProfittiPercentuali(parziale, 1);
		
		return soluzioneOttima;
	}

	private void funzioneRicorsivaProfittiPercentuali(Map<Integer, Integer[]> parziale, int livello) {
		
		//casi terminali ricorsione:
		
		if(!this.vincoliVariabili(parziale) || !this.vincoloCapacita(parziale))
			return;
		
		if(livello==numPeriodi+1 && this.soluzioneValida(parziale) && this.calcolaOttimoParzialeProfittiPercentuali(parziale)>ottimo)
		{
			this.setSoluzioneOttima(parziale);
			this.ottimo = this.calcolaOttimoParzialeProfittiPercentuali(parziale);
			return;
		}
		
		if(livello>numPeriodi)
			return;
		
		//caso intermedio:
		
		Integer[] vettore = parziale.get(livello);
		for(int pos=0; pos<vettore.length; pos++)
		{
			if(vettore[pos]==0)
			{
				vettore[pos] = 1;
				if(this.vincoloCapacita(parziale))
				{
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 1;
					this.funzioneRicorsivaProfittiPercentuali(parziale, livello+1);
					if(pos<vettore.length-1 && this.sottovettorePesi(parziale, livello, pos).size()>0)
						this.funzioneRicorsivaProfittiPercentuali(parziale, livello);
					
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 0;
				}	
				
				vettore[pos] = 0;
				this.funzioneRicorsivaProfittiPercentuali(parziale, livello+1);
			}
			
		}
	}
	
	
	//metodo per trovare la soluzione ottima nel caso di profitti decrescenti ma ordinando le variabili in base ai rapporti profitti/pesi decrescenti (vedi anche funzione ricorsiva sotto)
	public Map<Integer, List<Integer>> trovaSoluzioneOttimaOrdinataProfittiPercentuali() {
		
		this.ordinaVariabili();
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneOttima = new HashMap<>();
		this.ottimo = 0;
		
		this.funzioneRicorsivaOrdinataProfittiPercentuali(parziale, 1);
		
		return soluzioneOttima;
	}

	private void funzioneRicorsivaOrdinataProfittiPercentuali(Map<Integer, Integer[]> parziale, int livello) {
		
		//casi terminali ricorsione:
		
		if(!this.vincoliVariabili(parziale) || !this.vincoloCapacita(parziale))
			return;
		
		if(livello==numPeriodi+1 && this.soluzioneValida(parziale) && this.calcolaOttimoParzialeProfittiPercentuali(parziale)>ottimo)
		{
			this.setSoluzioneOttimaOrdinata(parziale);
			this.ottimo = this.calcolaOttimoParzialeProfittiPercentuali(parziale);
			return;
		}
		
		if(livello>numPeriodi)
			return;
		
		//caso intermedio:
		
		Integer[] vettore = parziale.get(livello);
		for(int pos=0; pos<vettore.length; pos++)
		{
			if(vettore[pos]==0)
			{
			    vettore[pos] = 1;
				if(this.vincoloCapacita(parziale))
				{
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 1;
					this.funzioneRicorsivaOrdinataProfittiPercentuali(parziale, livello+1);
					if(pos<vettore.length-1 && this.sottovettorePesi(parziale, livello, pos).size()>0)
						this.funzioneRicorsivaOrdinataProfittiPercentuali(parziale, livello);
					
					for(int i=livello+1; i<=parziale.size(); i++)
						parziale.get(i)[pos] = 0;
				}	
				
				vettore[pos] = 0;
				this.funzioneRicorsivaOrdinataProfittiPercentuali(parziale, livello+1);
			}
		}
	}
	
	
	//metodo euristico per trovare una soluzione riempiendo zaino ordinato per rapporti profitto/peso decrescenti
	//fino ad esaurimento di variabili disponibili per quel periodo
	public  Map<Integer, List<Integer>> trovaSoluzioneEsaurimentoZainoOrdinato() {
		
		this.ordinaVariabili();
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneEuristicaEsaurimento = new HashMap<>();
		this.setSoluzioneEuristicaEsaurimento(parziale);
		this.ottimoEuristicoEsaurimento = 0;
		
		for(int p=1; p<=numPeriodi; p++)
		{
			Map<Integer, Integer> sottovettore = this.sottovettorePesi(parziale, p, -1);
			if(sottovettore.size()>0)
			{
				for(int pos : sottovettore.keySet())
				{
					if(this.capacitaRestante(p, parziale)>=pesi[pos])
					{
						for(int liv=p; liv<=numPeriodi; liv++)
							parziale.get(liv)[pos] = 1;
					}
				}
			}
		}
		
		this.ottimoEuristicoEsaurimento = this.calcolaOttimoParziale(parziale);
		this.setSoluzioneEuristicaEsaurimento(parziale);
		
		return this.soluzioneEuristicaEsaurimento;
	}
	
	//metodo uguale al precedente ma per profitti decrescenti
	public  Map<Integer, List<Integer>> trovaSoluzioneEsaurimentoZainoOrdinatoProfittiPercentuali() {
		
		this.ordinaVariabili();
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneEuristicaEsaurimento = new HashMap<>();
		this.setSoluzioneEuristicaEsaurimento(parziale);
		this.ottimoEuristicoEsaurimento = 0;
		
		for(int p=1; p<=numPeriodi; p++)
		{
			Map<Integer, Integer> sottovettore = this.sottovettorePesi(parziale, p, -1);
			if(sottovettore.size()>0)
			{
				for(int pos : sottovettore.keySet())
				{
					if(this.capacitaRestante(p, parziale)>=pesi[pos])
					{
						for(int liv=p; liv<=numPeriodi; liv++)
							parziale.get(liv)[pos] = 1;
					}
				}
			}
		}
		
		this.ottimoEuristicoEsaurimento = this.calcolaOttimoParzialeProfittiPercentuali(parziale);
		this.setSoluzioneEuristicaEsaurimento(parziale);
		
		return this.soluzioneEuristicaEsaurimento;
	}
	
	
	//metodo euristico per trovare una soluzione riempiendo zaino cercando ottimo ad ogni periodo
	public Map<Integer, List<Integer>> trovaSoluzioneTramiteOttimoPeriodale() {
		
		this.ripristinaPosizioniIniziali();
		
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneEuristicaPeriodale = new HashMap<>();
		this.setSoluzioneEuristicaPeriodale(parziale);
		this.ottimoEuristicoPeriodale = 0;
		
		for(int p=1; p<=numPeriodi; p++)
		{
			Integer[] sottovettore = this.sottovettorePesiPeriodo(parziale, p);
			int dim = 0;
			if(sottovettore!=null)
				dim = sottovettore.length;
			int capacitaRestante = this.capacitaRestante(p, parziale);
		
			while(dim>0 && capacitaRestante>=0)
			{
				int max = profitti[sottovettore[0]];
				int indMax = sottovettore[0];
				int indPos = 0;
				for(int pos=1; pos<dim; pos++) 
				{
					if(profitti[sottovettore[pos]]>max || (profitti[sottovettore[pos]]==max && pesi[sottovettore[pos]]<pesi[indMax]))
					{
						max = profitti[sottovettore[pos]];
						indMax = sottovettore[pos];
						indPos = pos;
					}
				}
				if(capacitaRestante>=pesi[indMax])
				{
					for(int liv=p; liv<=numPeriodi; liv++)
						parziale.get(liv)[indMax] = 1;
					capacitaRestante -= pesi[indMax];
				}
				int ind = sottovettore[dim-1];
				sottovettore[dim-1] = sottovettore[indPos];
				sottovettore[indPos] = ind;
				dim--;
			}
		}
		
		this.ottimoEuristicoPeriodale = this.calcolaOttimoParziale(parziale);
		this.setSoluzioneEuristicaPeriodale(parziale);
		
		return this.soluzioneEuristicaPeriodale;
	}
	
	//metodo uguale al precedente ma per profitti decrescenti
	public Map<Integer, List<Integer>> trovaSoluzioneTramiteOttimoPeriodaleProfittiPercentuali() {
		
		this.ripristinaPosizioniIniziali();
		
		Map<Integer, Integer[]> parziale = new HashMap<>(variabili);
		this.soluzioneEuristicaPeriodale = new HashMap<>();
		this.setSoluzioneEuristicaPeriodale(parziale);
		this.ottimoEuristicoPeriodale = 0;
		
		for(int p=1; p<=numPeriodi; p++)
		{
			Integer[] sottovettore = this.sottovettorePesiPeriodo(parziale, p);
			int dim = 0;
			if(sottovettore!=null)
				dim = sottovettore.length;
			int capacitaRestante = this.capacitaRestante(p, parziale);
		
			while(dim>0 && capacitaRestante>=0)
			{
				int max = profittiPercentuali.get(p)[sottovettore[0]];
				int indMax = sottovettore[0];
				int indPos = 0;
				for(int pos=1; pos<dim; pos++) 
				{
					if(profittiPercentuali.get(p)[sottovettore[pos]]>max || (profittiPercentuali.get(p)[sottovettore[pos]]==max && pesi[sottovettore[pos]]<pesi[indMax]))
					{
						max = profittiPercentuali.get(p)[sottovettore[pos]];
						indMax = sottovettore[pos];
						indPos = pos;
					}
				}
				if(capacitaRestante>=pesi[indMax])
				{
					for(int liv=p; liv<=numPeriodi; liv++)
						parziale.get(liv)[indMax] = 1;
					capacitaRestante -= pesi[indMax];
				}
				int ind = sottovettore[dim-1];
				sottovettore[dim-1] = sottovettore[indPos];
				sottovettore[indPos] = ind;
				dim--;
			}
		}
		
		this.ottimoEuristicoPeriodale = this.calcolaOttimoParzialeProfittiPercentuali(parziale);
		this.setSoluzioneEuristicaPeriodale(parziale);
		
		return this.soluzioneEuristicaPeriodale;
	}
	
	
	public Map<Integer, List<Integer>> modificaSoluzione(Map<Integer, List<Integer>> euristica) {
		
		Map<Integer, Integer[]> parziale = new HashMap<>();
		for(int p=1; p<=numPeriodi; p++)
		{
			Integer[] vettore = new Integer[numVariabili];
			for(int i=0; i<numVariabili; i++)
				vettore[i] = euristica.get(p).get(i);
			parziale.put(p, vettore);
		}
		this.soluzioneEuristicaModificata = new HashMap<>();
		this.setSoluzioneEuristicaModificata(parziale);
		this.ottimoEuristicoModificato = this.calcolaOttimoParziale(parziale);
		
		for(int p=1; p<numPeriodi; p++) 
		{
			Integer[] vettorePartenza = parziale.get(p);
			int dimUni = 0;
			int dimZeri = 0;
			for(int i=0; i<numVariabili; i++)
			{
				if(vettorePartenza[i]==1)
					dimUni++;
				else if(vettorePartenza[i]==0 && pesi[i]<=this.capacitaRestantePeriodo(p, parziale))
					dimZeri++;
			}
			if(dimUni>0 && dimZeri>0)
			{
				Integer[] vettoreUni = new Integer[dimUni];
				Integer[] vettoreZeri = new Integer[dimZeri];
				int nU = 0;
				int nZ = 0;
				for(int i=0; i<numVariabili; i++)
				{
					if(vettorePartenza[i]==1)
					{
						vettoreUni[nU] = i;
						nU++;
					}
					else if(vettorePartenza[i]==0 && pesi[i]<=this.capacitaRestantePeriodo(p, parziale))
					{
						vettoreZeri[nZ] = i;
						nZ++;
					}
				}
				for(int z=0; z<nZ; z++)
				{
					int pos = vettoreZeri[z];
					for(int u=0; u<nU; u++)
					{
						int posU = vettoreUni[u];
						int occ = 0;
						for(int liv=1; liv<=p; liv++)
							if(parziale.get(liv)[posU]==1)
								occ++;
						if((pesi[pos]<pesi[posU] && profitti[pos]>=profitti[posU]) || (pesi[pos]<=this.capacitaRestante(p, parziale)+pesi[posU] || (pesi[pos]<=this.capacitaRestante(p+1, parziale)+pesi[posU] && ((numPeriodi-p)*profitti[pos])>((numPeriodi-p+occ)*profitti[posU]))))
						{
							for(int liv=p-occ+1; liv<=numPeriodi; liv++)
								parziale.get(liv)[posU] = 0;
							for(int liv=p+1; liv<=numPeriodi; liv++)
								parziale.get(liv)[pos] = 1;
						}
					}
				}
			}
		}
			
		if(this.calcolaOttimoParziale(parziale)>this.ottimoEuristicoModificato && this.soluzioneValida(parziale))
		{
			this.ottimoEuristicoModificato = this.calcolaOttimoParziale(parziale);
			this.setSoluzioneEuristicaModificata(parziale);
		}
		
		return this.soluzioneEuristicaModificata;
	}
	

	public Map<Integer, List<Integer>> modificaSoluzioneProfittiPercentuali(Map<Integer, List<Integer>> euristica) {
		
		Map<Integer, Integer[]> parziale = new HashMap<>();
		for(int p=1; p<=numPeriodi; p++)
		{
			Integer[] vettore = new Integer[numVariabili];
			for(int i=0; i<numVariabili; i++)
				vettore[i] = euristica.get(p).get(i);
			parziale.put(p, vettore);
		}
		this.soluzioneEuristicaModificata = new HashMap<>();
		this.setSoluzioneEuristicaModificata(parziale);
		this.ottimoEuristicoModificato = this.calcolaOttimoParzialeProfittiPercentuali(parziale);
		
		for(int p=1; p<numPeriodi; p++) 
		{
			Integer[] vettorePartenza = parziale.get(p);
			int dimUni = 0;
			int dimZeri = 0;
			for(int i=0; i<numVariabili; i++)
			{
				if(vettorePartenza[i]==1)
					dimUni++;
				else if(vettorePartenza[i]==0 && pesi[i]<=this.capacitaRestantePeriodo(p, parziale))
					dimZeri++;
			}
			if(dimUni>0 && dimZeri>0)
			{
				Integer[] vettoreUni = new Integer[dimUni];
				Integer[] vettoreZeri = new Integer[dimZeri];
				int nU = 0;
				int nZ = 0;
				for(int i=0; i<numVariabili; i++)
				{
					if(vettorePartenza[i]==1)
					{
						vettoreUni[nU] = i;
						nU++;
					}
					else if(vettorePartenza[i]==0 && pesi[i]<=this.capacitaRestantePeriodo(p, parziale))
					{
						vettoreZeri[nZ] = i;
						nZ++;
					}
				}
				for(int z=0; z<nZ; z++)
				{
					int pos = vettoreZeri[z];
					for(int u=0; u<nU; u++)
					{
						int posU = vettoreUni[u];
						int occ = 0;
						for(int liv=1; liv<=p; liv++)
							if(parziale.get(liv)[posU]==1)
								occ++;
						if((pesi[pos]<pesi[posU] && profittiPercentuali.get(p)[pos]>=profittiPercentuali.get(p)[posU]) || (pesi[pos]<=this.capacitaRestante(p, parziale)+pesi[posU] || (pesi[pos]<=this.capacitaRestante(p+1, parziale)+pesi[posU] && ((numPeriodi-p)*profittiPercentuali.get(p)[pos])>((numPeriodi-p+occ)*profittiPercentuali.get(p-occ+1)[posU]))))
						{
							for(int liv=p-occ+1; liv<=numPeriodi; liv++)
								parziale.get(liv)[posU] = 0;
							for(int liv=p+1; liv<=numPeriodi; liv++)
								parziale.get(liv)[pos] = 1;
						}
					}
				}
			}
		}
			
		if(this.calcolaOttimoParzialeProfittiPercentuali(parziale)>this.ottimoEuristicoModificato && this.soluzioneValida(parziale))
		{
			this.ottimoEuristicoModificato = this.calcolaOttimoParzialeProfittiPercentuali(parziale);
			this.setSoluzioneEuristicaModificata(parziale);
		}
		
		return this.soluzioneEuristicaModificata;
	}

	
	//metodi di stampa:
	public String stampaModello() {
		
		String modello="Il modello di knapsack dinamico è costituito da "+this.numVariabili+" variabili e si caratterizza su "+this.numPeriodi+" periodi di tempo\n\n";
		
		modello += "FUNZIONE OBIETTIVO: \n";
		modello += "MAX";
		for(int t=1; t<=numPeriodi; t++)
			for(int i=1; i<=numVariabili; i++)
				modello += " "+profitti[i-1]+"*(x"+i+","+t+") +";
		modello = modello.substring(0, modello.length()-1)+"\n\n";
		
		modello += "VINCOLI: \n";
		for(int t=1; t<=numPeriodi; t++)
		{
			for(int i=1; i<=numVariabili; i++)
				modello += ""+pesi[i-1]+"*(x"+i+","+t+") + ";
			modello = modello.substring(0, modello.length()-2)+"<= "+this.capacitaMax.get(t)+"\n";
		}
		modello += "Xi,t >= Xi,(t-1)  per ogni i = 1, ... , "+numVariabili+" & per ogni t = 2, ... , "+numPeriodi+"\nXi = {0,1}  per ogni i = 1, ... , "+numVariabili+"\n";
		
		return modello;
	}
	
	public String stampaModelloProfittiPercentuali() {
		
		String modello="Il modello di knapsack dinamico con profitti decrescenti è costituito da "+this.numVariabili+" variabili e si caratterizza su "+this.numPeriodi+" periodi di tempo\n\n";
		
		modello += "FUNZIONE OBIETTIVO: \n";
		modello += "MAX";
		for(int t=1; t<=numPeriodi; t++)
			for(int i=1; i<=numVariabili; i++)
				modello += " "+profittiPercentuali.get(t)[i-1]+"*(x"+i+","+t+") +";
		modello = modello.substring(0, modello.length()-1)+"\n\n";
		
		modello += "VINCOLI: \n";
		for(int t=1; t<=numPeriodi; t++)
		{
			for(int i=1; i<=numVariabili; i++)
				modello += ""+pesi[i-1]+"*(x"+i+","+t+") + ";
			modello = modello.substring(0, modello.length()-2)+"<= "+this.capacitaMax.get(t)+"\n";
		}
		modello += "Xi,t >= Xi,(t-1)  per ogni i = 1, ... , "+numVariabili+" & per ogni t = 2, ... , "+numPeriodi+"\nXi = {0,1}  per ogni i = 1, ... , "+numVariabili+"\n";
		
		return modello;
	}
	

	public String stampaVariabiliOrdinate() {
		
		String ordine = "L'ordine delle variabili secondo rapporti profitto/peso decrescenti è:\n";
		
		for(int pos=0; pos<numVariabili; pos++)
			ordine += ""+"x"+(this.vettorePosizioniVariabiliOrdinate[pos]+1)+", ";
		ordine = ordine.substring(0, ordine.length()-2) +"\n";
		
		ordine += "(Rapporti ordinati = ";
		DecimalFormat format = new DecimalFormat("0.000");
		for(Double d : this.rapportiProfittiPesi)
			ordine += format.format(d)+" --> ";
		ordine = ordine.substring(0, ordine.length()-5) +")\n";
		
		return ordine;
	}
	

	public String stampaSoluzioneOttima() {
		
		String soluzione = "LA SOLUZIONE OTTIMA HA PROFITTO = "+this.ottimo+"\n";
		
		soluzione += "In particolare i valori delle variabili sono: \n";
		for(int i=1; i<=numVariabili; i++)
		{
			int primaOcc = -1;
			for(int p=1; p<=numPeriodi; p++)
			{
				if(this.soluzioneOttima.get(p).get(i-1)==1)
				{
					primaOcc = p;
					p=numPeriodi+1;
				}
			}
			
			if(primaOcc!=-1)
				soluzione += "La variabile x"+i+" è pari ad 1 dal periodo t = "+primaOcc+"\n";
			else
				soluzione += "La variabile x"+i+" è pari a 0 per ogni t = 1, ... , "+numPeriodi+"\n";
		}
		
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		
		String head = "xi -> [";
		for(int i=1; i<=numVariabili; i++)
			head += "x"+i+", ";
		head = head.substring(0, head.length()-2)+"] ";
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList(head.split(" "));
		rows.add(headers);
		
		for(int p=1; p<=numPeriodi; p++)
		{
			String row = "t="+p+" -> "+this.soluzioneOttima.get(p);
			List<String> rowt = Arrays.asList(row.split(" "));
			rows.add(rowt);
		}
		
		String table = Model.formatAsTable(rows);
		soluzione += table;
		
		return soluzione;
	}
	
	public String stampaSoluzioneEuristicaEsaurimento() {
		
		String soluzione = "LA SOLUZIONE RICAVATA COL METODO EURISTICO DI RIEMPIMENTO FINO AD ESAURIMENTO HA PROFITTO = "+this.ottimoEuristicoEsaurimento+"\n";
		
		soluzione += "In particolare i valori delle variabili sono: \n";
		for(int i=1; i<=numVariabili; i++)
		{
			int primaOcc = -1;
			boolean trovato = false;
			for(int p=1; p<=numPeriodi && trovato==false; p++)
			{
				if(this.soluzioneEuristicaEsaurimento.get(p).get(i-1)==1)
				{
					primaOcc = p;
					trovato = true;
				}
			}	
			
			if(primaOcc!=-1)
				soluzione += "La variabile x"+i+" è pari ad 1 dal periodo t = "+primaOcc+"\n";
			else
				soluzione += "La variabile x"+i+" è pari a 0 per ogni t = 1, ... , "+numPeriodi+"\n";
		}
		
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		/*
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		soluzione += "xi -> ";
		for(int i=1; i<=numVariabili; i++)
			soluzione += "x"+i+",";
		soluzione = soluzione.substring(0, soluzione.length()-1)+"\n";
		for(int p=1; p<=numPeriodi; p++)
			soluzione += "t="+p+" -> "+this.soluzioneEuristicaEsaurimento.get(p)+"\n";
		*/
		String head = "xi -> [";
		for(int i=1; i<=numVariabili; i++)
			head += "x"+i+", ";
		head = head.substring(0, head.length()-2)+"] ";
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList(head.split(" "));
		rows.add(headers);
		
		for(int p=1; p<=numPeriodi; p++)
		{
			String row = "t="+p+" -> "+this.soluzioneEuristicaEsaurimento.get(p);
			List<String> rowt = Arrays.asList(row.split(" "));
			rows.add(rowt);
		}
		
		String table = Model.formatAsTable(rows);
		soluzione += table;
		
		return soluzione;
	}
	
	public String stampaSoluzioneEuristicaPeriodale() {
		
		String soluzione = "LA SOLUZIONE RICAVATA COL METODO EURISTICO DI OTTIMIZZAZIONE PERIODALE HA PROFITTO = "+this.ottimoEuristicoPeriodale+"\n";
		
		soluzione += "In particolare i valori delle variabili sono: \n";
		for(int i=1; i<=numVariabili; i++)
		{
			int primaOcc = -1;
			boolean trovato = false;
			for(int p=1; p<=numPeriodi && trovato==false; p++)
			{
				if(this.soluzioneEuristicaPeriodale.get(p).get(i-1)==1)
				{
					primaOcc = p;
					trovato = true;
				}
			}
			
			if(primaOcc!=-1)
				soluzione += "La variabile x"+i+" è pari ad 1 dal periodo t = "+primaOcc+"\n";
			else
				soluzione += "La variabile x"+i+" è pari a 0 per ogni t = 1, ... , "+numPeriodi+"\n";
		}
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		/*
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		soluzione += "xi -> ";
		for(int i=1; i<=numVariabili; i++)
			soluzione += "x"+i+",";
		soluzione = soluzione.substring(0, soluzione.length()-1)+"\n";
		for(int p=1; p<=numPeriodi; p++)
			soluzione += "t="+p+" -> "+this.soluzioneEuristicaPeriodale.get(p)+"\n";
		*/
		String head = "xi -> [";
		for(int i=1; i<=numVariabili; i++)
			head += "x"+i+", ";
		head = head.substring(0, head.length()-2)+"] ";
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList(head.split(" "));
		rows.add(headers);
		
		for(int p=1; p<=numPeriodi; p++)
		{
			String row = "t="+p+" -> "+this.soluzioneEuristicaPeriodale.get(p);
			List<String> rowt = Arrays.asList(row.split(" "));
			rows.add(rowt);
		}
		
		String table = formatAsTable(rows);
		soluzione += table;
		
		return soluzione;
	}
	
	
	public String stampaSoluzioneEuristicaModificata() {
		
		String soluzione = "LA SOLUZIONE EURISTICA MODIFICATA HA PROFITTO = "+this.ottimoEuristicoModificato+"\n";
		
		soluzione += "In particolare i valori delle variabili sono: \n";
		for(int i=1; i<=numVariabili; i++)
		{
			int primaOcc = -1;
			boolean trovato = false;
			for(int p=1; p<=numPeriodi && trovato==false; p++)
			{
				if(this.soluzioneEuristicaModificata.get(p).get(i-1)==1)
				{
					primaOcc = p;
					trovato = true;
				}
			}
			
			if(primaOcc!=-1)
				soluzione += "La variabile x"+i+" è pari ad 1 dal periodo t = "+primaOcc+"\n";
			else
				soluzione += "La variabile x"+i+" è pari a 0 per ogni t = 1, ... , "+numPeriodi+"\n";
		}
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		/*
		soluzione += "\nOssia essa è rappresentabile dalla matrice delle variabili:\n";
		soluzione += "xi -> ";
		for(int i=1; i<=numVariabili; i++)
			soluzione += "x"+i+",";
		soluzione = soluzione.substring(0, soluzione.length()-1)+"\n";
		for(int p=1; p<=numPeriodi; p++)
			soluzione += "t="+p+" -> "+this.soluzioneEuristicaPeriodale.get(p)+"\n";
		*/
		String head = "xi -> [";
		for(int i=1; i<=numVariabili; i++)
			head += "x"+i+", ";
		head = head.substring(0, head.length()-2)+"] ";
		List<List<String>> rows = new ArrayList<>();
		List<String> headers = Arrays.asList(head.split(" "));
		rows.add(headers);
		
		for(int p=1; p<=numPeriodi; p++)
		{
			String row = "t="+p+" -> "+this.soluzioneEuristicaModificata.get(p);
			List<String> rowt = Arrays.asList(row.split(" "));
			rows.add(rowt);
		}
		
		String table = formatAsTable(rows);
		soluzione += table;
		
		return soluzione;
	}



	//metodo per stampare matrice variabili come tabella
	public static String formatAsTable(List<List<String>> rows)
	{
	    int[] maxLengths = new int[rows.get(0).size()];
	    for (List<String> row : rows)
	    {
	        for (int i = 0; i < row.size(); i++)
	        {
	            maxLengths[i] = Math.max(maxLengths[i], row.get(i).length());
	        }
	    }

	    StringBuilder formatBuilder = new StringBuilder();
	    for (int maxLength : maxLengths)
	    {
	        formatBuilder.append("%-").append(maxLength + 1).append("s");
	    }
	    String format = formatBuilder.toString();

	    StringBuilder result = new StringBuilder();
	    for (List<String> row : rows)
	    {
	        String format2 = String.format(format, row.toArray(new String[0]));
			result.append(format2).append("\n");
	    }
	    return result.toString();
	}
	
}