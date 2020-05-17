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
    void doNuovoProblema(ActionEvent event) {
    	
    	this.txtPeriodi.clear();
    	this.txtVariabili.clear();
    	this.txtResult.clear();
    	this.txtTime.setText("");
    	this.boxRisoluzione.setValue(null);
    }

    @FXML
    void doTrovaOttimo(ActionEvent event) {

    	//controlli preliminari per verificare che tutti i parametri siano stati settati:
    	String metodo = this.boxRisoluzione.getValue();
    	if(metodo==null) {
    		this.txtResult.setText("Errore! Per favore seleziona un metodo di risoluzione\n");
    		return;
    	}
    	
    	String periodi = this.txtPeriodi.getText();
    	if(periodi==null) {
    		this.txtResult.setText("Errore! Per favore seleziona il numero di periodi\n");
    		return;
    	}
    	int numPeriodi;
    	try {
    		numPeriodi = Integer.parseInt(periodi);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Errore! Il numero di periodi deve essere un valore numerico intero\n");
    		return;
    	}
    	
    	String variabili = this.txtVariabili.getText();
    	if(variabili==null) {
    		this.txtResult.setText("Errore! Per favore seleziona il numero di variabili\n");
    		return;
    	}
    	int numVariabili;
    	try {
    		numVariabili = Integer.parseInt(variabili);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Errore! Il numero di variabili deve essere un valore numerico intero\n");
    		return;
    	}
    	
    	//a questo punto tutti i parametri sono stati settati:
    	this.model.setModelloProblema(numPeriodi, numVariabili);
    	
    	long start = System.currentTimeMillis();
    	if(metodo=="RISOLUZIONE BASE")
    		this.model.trovaSoluzioneOttima();
    	else if(metodo=="RISOLUZIONE BASE ORDINATA")
    		this.model.trovaSoluzioneOttimaOrdinata();
    	else if(metodo=="RISOLUZIONE BASE PER PROFITTI DECRESCENTI")
    		this.model.trovaSoluzioneOttimaOrdinataProfittiPercentuali();
    	else if(metodo=="RISOLUZIONE ORDINATA PER PROFITTI DECRESCENTI") 
    		this.model.trovaSoluzioneOttimaOrdinataProfittiPercentuali();
    	long end = System.currentTimeMillis();
    	double tempo = (double) ((double) ((end-start)/1000));
    	
    	if(end-start <= 1000)
    		this.txtTime.setText("Il tempo necessario per trovare la soluzione ottima è di "+(end-start)+" millisecondi\nusando il metodo di "+metodo.toLowerCase());
    	else
    		this.txtTime.setText("Il tempo necessario per trovare la soluzione ottima è di "+tempo+" secondi\nusando il metodo di "+metodo.toLowerCase());
    	
    	if(metodo=="RISOLUZIONE ORDINATA PER PROFITTI DECRESCENTI" || metodo=="RISOLUZIONE BASE PER PROFITTI DECRESCENTI")
    		this.txtResult.setText(model.stampaModelloProfittiPercentuali());
    	else
    		this.txtResult.setText(model.stampaModello());
    	
    	if(metodo.contains("ORDINATA"))
    		this.txtResult.appendText("\n"+model.stampaVariabiliOrdinate());
    	
    	this.txtResult.appendText("\n"+model.stampaSoluzione());
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
		this.metodiRisoluzione.addAll("RISOLUZIONE BASE", "RISOLUZIONE BASE ORDINATA", "RISOLUZIONE BASE PER PROFITTI DECRESCENTI", "RISOLUZIONE ORDINATA PER PROFITTI DECRESCENTI");
		this.boxRisoluzione.setItems(metodiRisoluzione);
	}
}

