/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entities.Pharmacie;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PharmacieService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author feryel
 */
public class EditPharmacie implements Initializable {

      @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_adresse;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_matricule;
    @FXML
    private TextField tf_etat;
    @FXML
    private TextField tf_horaire;
    @FXML
    private TextArea tf_services;
    @FXML
    private TextArea tf_description;
    @FXML
    private TextField tf_num_tel;
    @FXML
    private TextField tf_gouvernorat;
    
    private int idPh;
    Pharmacie p;
    private final Connection cnx;
    private PreparedStatement ste;
   

    public EditPharmacie() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    
    ObservableList<Pharmacie> ListPharmacie=FXCollections.observableArrayList();
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inflateUI(Pharmacie ph) {
       idPh = ph.getIdph();
       tf_nom.setText(ph.getNom());
       tf_adresse.setText(ph.getAdresse());
       tf_gouvernorat.setText(ph.getGouvernorat());
       tf_num_tel.setText(ph.getNum_tel());
       tf_email.setText(ph.getEmail());
       tf_matricule.setText(ph.getMatricule());
       tf_horaire.setText(ph.getHoraire());
       tf_etat.setText(ph.getEtat());
       tf_description.setText(ph.getDescription());
       tf_services.setText(ph.getServices());


    }
    
    
    @FXML
    private void Valider(ActionEvent event) throws Exception {

        

try{
        if(tf_nom.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le nom");
            alert.showAndWait();  
        }else if(tf_adresse.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez l'adresse");
            alert.showAndWait();  
              }else if(tf_gouvernorat.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez le gouvernorat");
            alert.showAndWait(); 
             }else if(tf_num_tel.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la numéro de tel");
            alert.showAndWait(); 
             }else if(tf_email.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez l'email");
            alert.showAndWait(); 
             }else if(tf_matricule.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la matricule");
            alert.showAndWait(); 
             }else if(tf_horaire.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez l'horaire");
            alert.showAndWait(); 
             }else if(tf_etat.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez l'etat");
            alert.showAndWait(); 
             }else if(tf_description.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez la description");
            alert.showAndWait(); 
             
            }
        else{  
      
            Pharmacie ph = new Pharmacie(idPh,tf_nom.getText(),tf_adresse.getText(),tf_gouvernorat.getText(),tf_num_tel.getText(),tf_email.getText(),tf_matricule.getText(),tf_horaire.getText(),tf_etat.getText(),tf_description.getText(),tf_services.getText());
            PharmacieService pharmacieService = new PharmacieService();
            pharmacieService.EditPharmacie(ph);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MODIFICATION AVEC SUCCES");
            alert.setHeaderText(null);
            alert.setContentText("Pharmacie modifiée avec succès");
            alert.showAndWait();
   
        }
        
        
} 

      catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "pharmacie modifié avec succes";
     
    }
    @FXML
    private void AfficherPharmacieAction(ActionEvent event) throws IOException {

      // Parent parentAfficherPlanning= FXMLLoader.load(getClass().getResource("AfficherPlanning.fxml"));
    //Scene scene = new Scene(parentAfficherPlanning);
    Stage stageEdit = (Stage)((Node)event.getSource()).getScene().getWindow();

    stageEdit.hide();
    }
}