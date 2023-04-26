/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Pharmacie;
import javafx.geometry.Insets;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.PharmacieService;
import utils.MyConnection;


public class PatientFront implements Initializable {
    
     @FXML
    private VBox bigContainer;

    @FXML
    private HBox hbox;

    @FXML
    private ScrollPane scrollp;

    @FXML
    private GridPane UserContainer;
    
    @FXML
    private TextField rechercher;

       
    @FXML
    private TableView<Pharmacie> table_pharmacie;
      @FXML
    private TableColumn<Pharmacie, String> nom;   
    @FXML
    private TableColumn<Pharmacie, String> adresse; 
      @FXML
    private TableColumn<Pharmacie, String> gouvernorat;   
    @FXML
    private TableColumn<Pharmacie, String> num_tel; 
   
        
    @FXML
    private TableColumn<Pharmacie, String> horaire; 
    @FXML
    private TableColumn<Pharmacie, String> etat;  
     @FXML
    private TableColumn<Pharmacie, String> services; 
      @FXML
    private TableColumn<Pharmacie, Button> editCol;

    @FXML
    private TableColumn<Pharmacie, Button> deleteCol; 
     @FXML
    private Button  printbtn;
        @FXML
    private ImageView img;
    private PreparedStatement prepared;
    private  ResultSet resultat;
      private final Connection cnx;

     

    @FXML
    private TextField tf_rechercher_pharmacie;
    private ObservableList<Pharmacie> listPharmacies =  FXCollections.observableArrayList();
    public List<Pharmacie> pharmacies;
     
    public PatientFront() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    
    
  ObservableList<Pharmacie> listPharmacie =  FXCollections.observableArrayList();
    PharmacieService pharmacieService = new PharmacieService();
    Pharmacie p = new Pharmacie();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
     
        try {
            listPharmacie = pharmacieService.showPharmacie();
       
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        gouvernorat.setCellValueFactory(new PropertyValueFactory<>("gouvernorat"));
        num_tel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        horaire.setCellValueFactory(new PropertyValueFactory<>("horaire"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        services.setCellValueFactory(new PropertyValueFactory<>("services"));


        table_pharmacie.getItems().setAll(listPharmacie);
//           
    
        } 
                
       catch (SQLException ex) { 
            System.out.println(ex);
        }
    }
   
    
     
     @FXML
       private void OnClickedPrint(ActionEvent event) {
         PrinterJob job = PrinterJob.createPrinterJob();
       
        Node root= this.table_pharmacie;
       
       
     if(job != null){
     job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
     Printer printer = job.getPrinter();
     PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM );
     boolean success = job.printPage(pageLayout, root);
     if(success){
        job.endJob();
     }
     }
    }
       @FXML
    private void rechercherPharmacie() {
        System.out.println("");
        try {
            pharmacies = new PharmacieService().search(tf_rechercher_pharmacie.getText());
            listPharmacie.clear();
            table_pharmacie.getItems().setAll(pharmacies);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

       
    
    /*
    private ObservableList<Pharmacie> showPharmacies(){
        PharmacieService pharmacieService = new PharmacieService();
        try {
            listPharmacies = pharmacieService.ListPharmacies();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
             return listPharmacies;
    }

    
    public void afficherPharmacies(){
        listPharmacies = FXCollections.observableArrayList(showPharmacies());
        System.out.println(listPharmacies);
        int column = 0;
        int row=1;
     try{
        for(int i=0;i<listPharmacies.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardController cardController = fxmlLoader.getController();
                 cardController.setData(listPharmacies.get(i));
                 if(column ==8){
                     column=0;
                     ++row;
                   }
                UserContainer.add(cardBox, column++, row);
                scrollp.setContent(UserContainer);
                bigContainer.getChildren().clear();
                bigContainer.getChildren().add(hbox);
                bigContainer.getChildren().add(scrollp);
                GridPane.setMargin(cardBox,new Insets(10));
                
          }
        
        } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }
    
   @FXML
    private void rechercherPharmacies() {
    try{   
        pharmacies = new PharmacieService().search1(rechercher.getText());
        System.out.println(pharmacies);
        int column = 0;
        int row=1;
        UserContainer.getChildren().clear();
        for(int i=0;i<pharmacies.size();i++){
                 FXMLLoader fxmlLoader = new FXMLLoader();
                 fxmlLoader.setLocation(getClass().getResource("../gui/Card.fxml"));
                 VBox cardBox = fxmlLoader.load();
                 CardController cardController = fxmlLoader.getController();
                 cardController.setData(pharmacies.get(i));
                 UserContainer.add(cardBox, column++, row);
                 if(column ==8){
                     column=0;
                     ++row;
                   }
                scrollp.setContent(UserContainer);
                bigContainer.getChildren().clear();
                bigContainer.getChildren().add(hbox);
                bigContainer.getChildren().add(scrollp);
                GridPane.setMargin(cardBox,new Insets(10));
                
          }
     }  catch(SQLException | IOException e){
         System.out.println(e.getMessage());
     }
        
    }
    */
           
    
    
 
    
} 