/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entities.Pharmacie;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PharmacieService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class AjouterPharmacie implements Initializable {
        @FXML
    private Button btn_submit;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_adresse;
    @FXML
    private TextField tf_gouvernorat;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_num_tel;
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
    
 
    
    

    private final Connection cnx;
    private PreparedStatement ste;
    
    public AjouterPharmacie() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void Valider(ActionEvent event)  {
    
        

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
      
            Pharmacie ph = new Pharmacie(tf_nom.getText(),tf_adresse.getText(),tf_gouvernorat.getText(),tf_num_tel.getText(),tf_email.getText(),tf_matricule.getText(),tf_horaire.getText(),tf_etat.getText(),tf_description.getText(),tf_services.getText());
            PharmacieService pharmacieService = new PharmacieService();
            pharmacieService.ajouterPharmacie(ph);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AJOUT AVEC SUCCES");
            alert.setHeaderText(null);
            alert.setContentText("Pharmacie ajouté avec succès");
            alert.showAndWait();
   

        }}
        catch(Exception ex)
        {
            System.out.println("azertyuiop^poiu\n *********************************"+ex.getMessage());
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        
        }
    
        String title = "succes ";
        String message = "pharmacie ajoutée avec succes";
    }
       // Stage stage = (Stage) annuler.getScene().getWindow();
       // stage.close();

       //TrayNotification tray = new TrayNotification();
        //ray.setTitle(title);
        //tray.setMessage(message);
        //tray.setNotificationType(NotificationType.SUCCESS);
        //tray.showAndWait();
    }
    

