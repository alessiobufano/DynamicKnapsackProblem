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

    }

    @FXML
    void doTrovaOttimo(ActionEvent event) {

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
	}
}

