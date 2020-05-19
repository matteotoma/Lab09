
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	 @FXML // ResourceBundle that was given to the FXMLLoader
	 private ResourceBundle resources;

	 @FXML // URL location of the FXML file that was given to the FXMLLoader
	 private URL location;

	 @FXML // fx:id="txtAnno"
	 private TextField txtAnno; // Value injected by FXMLLoader

	 @FXML // fx:id="boxStato"
	 private ComboBox<Country> boxStato; // Value injected by FXMLLoader

	 @FXML // fx:id="btnVicini"
	 private Button btnVicini; // Value injected by FXMLLoader

	 @FXML // fx:id="txtResult"
	 private TextArea txtResult; // Value injected by FXMLLoader

	 @FXML
	 void doCalcolaConfini(ActionEvent event) {
		 txtResult.clear();
		 int anno;
		 try {
			 anno = Integer.parseInt(txtAnno.getText());
		 }
		 catch(Exception e) {
			 txtResult.appendText("Non hai inserito un numero che rappresenta un anno!");
			 return;
		 }
		 model.creaGrafo(anno);
		 txtResult.appendText("Elenco stati con numero di stati confinanti\n");
		 StringBuilder s = new StringBuilder();
		 for(Country c: model.getGrafo().vertexSet()) {
			 s.append(String.format("%-4s ", c.getStateAbb()));
			 s.append(String.format("%-3s ", model.getGrafo().degreeOf(c)));
			 s.append("\n");
		 }
		 txtResult.appendText(s.toString());
		 txtResult.appendText("-----------------------\n");
		 txtResult.appendText("Numero componenti connesse: " + model.getComponentiConnesse());
		 
		 boxStato.getItems().addAll(model.getGrafo().vertexSet());
	 }

	 @FXML
	 void doVicini(ActionEvent event) {
		 txtResult.clear();
		 Country c = boxStato.getValue();
		 if(c==null) {
			 txtResult.appendText("Devi selezionare uno stato!");
			 return;
		 }
		 List<Country> visita = model.visita(c);
		 if(visita == null) {
			 txtResult.appendText("Non confina con nessun'altro stato");
			 return;
		 }
		 txtResult.appendText("Stati raggiungibili a partire da " + c.getStateName() + ":\n");
		 for(Country c1: visita)
			 txtResult.appendText(c1.getStateAbb()+"\n");
	 }

	 @FXML // This method is called by the FXMLLoader when initialization is complete
	 void initialize() {
	     assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
	     assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Scene.fxml'.";
	     assert btnVicini != null : "fx:id=\"btnVicini\" was not injected: check your FXML file 'Scene.fxml'.";
	     assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
	 
	 }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
