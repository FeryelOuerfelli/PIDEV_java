/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import entities.Pharmacie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.PharmacieService;



public class Patient implements Initializable {

    @FXML
    private Button button_login;

    @FXML
    private Label username;

    @FXML
    private GridPane rdvContainer;
    @FXML
    private VBox bigContainer;
    @FXML
    private ScrollPane scrollp;
     
    @FXML
    private TextField rechercher;
    @FXML
    private HBox hbox;
    ObservableList<Pharmacie> pharmacieList;
        public List<Pharmacie> pharmacies;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
          afficherPhs();
            

    }  
    
   private ObservableList<Pharmacie> showCards(){
        List<Pharmacie> phList = new ArrayList<>();
        ObservableList<Pharmacie> listPharmacie =  FXCollections.observableArrayList();
        PharmacieService phaService = new PharmacieService();
        try {
            listPharmacie = phaService.showPharmacie();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             return listPharmacie;
    }
 
    
    public void afficherPhs(){
        pharmacieList = FXCollections.observableArrayList(showCards());
        int column = 1;
        int row=0;

         try {

        for(int i=0;i<pharmacieList.size();i++){
        PharmacieService phService = new PharmacieService();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../gui/Card1.fxml"));
        VBox cardBox = fxmlLoader.load();
        Card cardController = fxmlLoader.getController();
        cardController.setData(pharmacieList.get(i));
         rdvContainer.add(cardBox, column++, row);

        if(column ==3){
            column=1;
            ++row;
        }
        scrollp.setContent(rdvContainer);
        bigContainer.getChildren().clear();
        bigContainer.getChildren().add(hbox);
        bigContainer.getChildren().add(scrollp);
        //rdvContainer.add(cardBox, column++, row);
        //rdvContainer.getChildren().add(cardBox,column row);
        GridPane.setMargin(cardBox,new Insets(10));
          }
        }

        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
   @FXML
   void showPharmacies(ActionEvent event) {
     
        afficherPhs();
    }
    @FXML
    private void rechercherPharmacies() {
    try{   
        pharmacies = new PharmacieService().search(rechercher.getText());
        System.out.println(pharmacies);
        int column = 1;
        int row=1;
        rdvContainer.getChildren().clear();
        for(int i=0;i<pharmacies.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/Card1.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 Card cardController = fxmlLoader.getController();
                 cardController.setData(pharmacies.get(i));
                 rdvContainer.add(cardBox, column++, row);
                 if(column ==8){
                     column=0;
                     ++row;
                   }
                scrollp.setContent(rdvContainer);
                bigContainer.getChildren().clear();
                bigContainer.getChildren().add(hbox);
                bigContainer.getChildren().add(scrollp);
                GridPane.setMargin(cardBox,new Insets(10));
                
          }
     }  catch(SQLException | IOException e){
         System.out.println(e.getMessage());
     }
        
    }

}