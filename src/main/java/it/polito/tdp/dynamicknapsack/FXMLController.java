package it.polito.tdp.dynamicknapsack;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.dynamicknapsack.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class FXMLController {
	
	private Model model;
	private ObservableList<String> metodiRisoluzione = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> boxRisoluzione;

    @FXML
    private TextField txtVariabili;

    @FXML
    private TextField txtPeriodi;

    @FXML
    private Button doTrovaOttimo;

    @FXML
    private TextArea txtResult;

    @FXML
    private Text txtTime;
    
    @FXML
    private TextArea txtResultPP;

    @FXML
    private Text txtTimePP;

    @FXML
    void doNuovoProblema(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.txtTime.setText("");
    	this.txtResultPP.clear();
    	this.txtTimePP.setText("");
    	this.boxRisoluzione.setValue(null);
    	
    	//controlli preliminari per verificare che i parametri siano settati correttamente:
    	String periodi = this.txtPeriodi.getText();
    	if(periodi==null) {
    		this.txtResult.setText("Errore! Per favore seleziona il numero di periodi\n");
    		this.txtResultPP.setText("Errore! Per favore seleziona il numero di periodi\n");
    		return;
    	}
    	int numPeriodi;
    	try {
    		numPeriodi = Integer.parseInt(periodi);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Errore! Il numero di periodi deve essere un valore numerico intero\n");
    		this.txtResultPP.setText("Errore! Il numero di periodi deve essere un valore numerico intero\n");
    		return;
    	}
    	
    	String variabili = this.txtVariabili.getText();
    	if(variabili==null) {
    		this.txtResult.setText("Errore! Per favore seleziona il numero di variabili\n");
    		this.txtResultPP.setText("Errore! Per favore seleziona il numero di variabili\n");
    		return;
    	}
    	int numVariabili;
    	try {
    		numVariabili = Integer.parseInt(variabili);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Errore! Il numero di variabili deve essere un valore numerico intero\n");
    		this.txtResultPP.setText("Errore! Il numero di variabili deve essere un valore numerico intero\n");
    		return;
    	}
    	
    	//a questo punto tutti i parametri sono stati settati:
    	this.model.setModelloProblema(numPeriodi, numVariabili);
    	
    	this.txtResult.setText(model.stampaModello());
    	this.txtResultPP.setText(model.stampaModelloProfittiPercentuali());
    	
    }

    @FXML
    void doTrovaOttimo(ActionEvent event) {
    	
    	//controllo per verificare che il metodo sia stato selezionato:
    	String metodo = this.boxRisoluzione.getValue();
    	if(metodo==null) {
    		this.txtResult.setText("Errore! Per favore seleziona un metodo di risoluzione\n");
    		this.txtResultPP.setText("Errore! Per favore seleziona un metodo di risoluzione\n");
    		return;
    	}
    	
    	this.txtResult.setText(model.stampaModello());
    	this.txtResultPP.setText(model.stampaModelloProfittiPercentuali());
    	
    	long start = System.currentTimeMillis();
    	if(metodo=="RICERCA BASE")
    		this.model.trovaSoluzioneOttima();
    	else if(metodo=="RICERCA ORDINATA RAPPORTI")
    		this.model.trovaSoluzioneOttimaOrdinata();
    	else if(metodo=="EURISTICA ESAURIMENTO ZAINO")
    		this.model.trovaSoluzioneEsaurimentoZainoOrdinato();
    	else if(metodo=="EURISTICA OTTIMO PERIODALE") 
    		this.model.trovaSoluzioneTramiteOttimoPeriodale();
    	else if(metodo=="MODIFICA EURISTICA ESAURIMENTO")
    		this.model.modificaSoluzione(model.trovaSoluzioneEsaurimentoZainoOrdinato());
    	else if(metodo=="MODIFICA EURISTICA PERIODALE")
    		this.model.modificaSoluzione(model.trovaSoluzioneTramiteOttimoPeriodale());
    	long end = System.currentTimeMillis();
    	double tempo = (double) ((double) ((end-start)/1000));
    	
    	if(end-start <= 1000)
    		this.txtTime.setText("Il tempo necessario per trovare la soluzione ottima è di "+(end-start)+" millisecondi\nusando la risoluzione con "+metodo.toLowerCase());
    	else
    		this.txtTime.setText("Il tempo necessario per trovare la soluzione ottima è di "+tempo+" secondi\nusando la risoluzione con "+metodo.toLowerCase());
    	
    	if(metodo=="RICERCA ORDINATA RAPPORTI" || metodo.contains("ESAURIMENTO")) {
    		this.txtResult.appendText("\n"+model.stampaVariabiliOrdinate());
    		this.txtResultPP.appendText("\n"+model.stampaVariabiliOrdinate());
    	}
    	
    	if(metodo=="EURISTICA ESAURIMENTO ZAINO")
    		this.txtResult.appendText("\n"+model.stampaSoluzioneEuristicaEsaurimento());
    	else if(metodo=="EURISTICA OTTIMO PERIODALE")
    		this.txtResult.appendText("\n"+model.stampaSoluzioneEuristicaPeriodale());
    	else if(metodo.contains("MODIFICA"))
    		this.txtResult.appendText("\n"+model.stampaSoluzioneEuristicaModificata());
    	else
    		this.txtResult.appendText("\n"+model.stampaSoluzioneOttima());
    	
    	long startPP = System.currentTimeMillis();
    	if(metodo=="RICERCA BASE")
    		this.model.trovaSoluzioneOttimaProfittiPercentuali();
    	else if(metodo=="RICERCA ORDINATA RAPPORTI")
    		this.model.trovaSoluzioneOttimaOrdinataProfittiPercentuali();
    	else if(metodo=="EURISTICA ESAURIMENTO ZAINO")
    		this.model.trovaSoluzioneEsaurimentoZainoOrdinatoProfittiPercentuali();
    	else if(metodo=="EURISTICA OTTIMO PERIODALE") 
    		this.model.trovaSoluzioneTramiteOttimoPeriodaleProfittiPercentuali();
    	else if(metodo=="MODIFICA EURISTICA ESAURIMENTO")
    		this.model.modificaSoluzioneProfittiPercentuali(model.trovaSoluzioneEsaurimentoZainoOrdinatoProfittiPercentuali());
    	else if(metodo=="MODIFICA EURISTICA PERIODALE")
    		this.model.modificaSoluzioneProfittiPercentuali(model.trovaSoluzioneTramiteOttimoPeriodaleProfittiPercentuali());
    	long endPP = System.currentTimeMillis();
    	double tempoPP = (double) ((double) ((endPP-startPP)/1000));
    	
    	if(endPP-startPP <= 1000)
    		this.txtTimePP.setText("Il tempo necessario per trovare la soluzione ottima è di "+(endPP-startPP)+" millisecondi\nusando la risoluzione con "+metodo.toLowerCase());
    	else
    		this.txtTimePP.setText("Il tempo necessario per trovare la soluzione ottima è di "+tempoPP+" secondi\nusando la risoluzione con "+metodo.toLowerCase());
    	
    	if(metodo=="EURISTICA ESAURIMENTO ZAINO")
    		this.txtResultPP.appendText("\n"+model.stampaSoluzioneEuristicaEsaurimento());
    	else if(metodo=="EURISTICA OTTIMO PERIODALE")
    		this.txtResultPP.appendText("\n"+model.stampaSoluzioneEuristicaPeriodale());
    	else if(metodo.contains("MODIFICA"))
    		this.txtResultPP.appendText("\n"+model.stampaSoluzioneEuristicaModificata());
    	else
    		this.txtResultPP.appendText("\n"+model.stampaSoluzioneOttima());
    }

    @FXML
    void initialize() {
        assert boxRisoluzione != null : "fx:id=\"boxRisoluzione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVariabili != null : "fx:id=\"txtVariabili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtPeriodi != null : "fx:id=\"txtPeriodi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert doTrovaOttimo != null : "fx:id=\"doTrovaOttimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.metodiRisoluzione.addAll("RICERCA BASE", "RICERCA ORDINATA RAPPORTI", "EURISTICA ESAURIMENTO ZAINO", "EURISTICA OTTIMO PERIODALE", "MODIFICA EURISTICA ESAURIMENTO", "MODIFICA EURISTICA PERIODALE");
		this.boxRisoluzione.setItems(metodiRisoluzione);
	}
}

