/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Facture;
import entities.Pharmacie;
import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JTable;
import services.FactureService;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author feryel
 */
public class EditerFacture implements Initializable {

    @FXML
    private Button valider;
    @FXML
    private Button  printbtn;
    
   
  
    @FXML
    private TextField tf_montant;
      @FXML
    private DatePicker tf_date;
   

    @FXML
    private TextField  tf_num_facture;
     @FXML
    private TextField  tf_image_signature;
     
 @FXML
   private TextField  tf_etat;
  @FXML
   private TextField  tf_ordonnance;
     
    
    private int Idf;
   Facture f;

    
    private int pharmacieId;
    
    
    private final Connection cnx;
    
    private PreparedStatement ste;
    
    SimpleDateFormat heureFormatPicker = new SimpleDateFormat("HH:mm");
    
    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Change the output format to match the format accepted by JFoenix DatePicker

    public EditerFacture() {
        MyConnection bd=MyConnection.getInstance();
        cnx=bd.getCnx();
    }
    
    ObservableList<Facture> ListFacture=FXCollections.observableArrayList();
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void inflateUI(Facture f) {
       Idf= f.getIDf();
      tf_etat.setText(f.getEtat());
      tf_num_facture.setText(f.getNumfacture());
      tf_image_signature.setText(f.getImgsig());
       tf_montant.setText(f.getMontant() + "");
      tf_ordonnance.setText(f.getOrdonnance()+ "");
       pharmacieId= f.getIdph();
       tf_date.setValue(LocalDate.parse(outputFormat.format(f.getDate()), DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Set the DatePicker value using the formatted date

        
    }   


    @FXML
    private void Valider(ActionEvent event) {
        
try{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(tf_date.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous devez entrez une date");
            alert.showAndWait();  
        }
        
        else{  
            try {
      Date date = dateFormat.parse(tf_date.getValue().toString());
                     float montant = Float.parseFloat(tf_montant.getText());
                    String num_facture = tf_num_facture.getText();
                      String image_signature = tf_image_signature.getText();
                       String etat =  tf_etat.getText();
                       int ordonnance = Integer.parseInt(tf_ordonnance.getText());


                 System.out.println("date controller"+date);
                 
                Facture f = new Facture(Idf,date,num_facture,montant,image_signature,etat,ordonnance,pharmacieId);                FactureService factureService = new FactureService();
                factureService.EditFacture(f);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification Facture");
                alert.setHeaderText(null);
                alert.setContentText("Facture modifié avec succès");
                alert.showAndWait();
                }catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        
        }}

        catch(RuntimeException ex)
        {
            System.out.println(ex);
             Alert alert = new Alert(Alert.AlertType.ERROR," Les informations sont Invalides ou incompletes Veuillez les verifiers ",ButtonType.CLOSE);
            alert.showAndWait();
        }
        String title = "succes ";
        String message = "Facture modifié avec succes";
     
     }
    
       private void AfficherFactureAction(ActionEvent event) throws IOException {

      // Parent parentAfficherPlanning= FXMLLoader.load(getClass().getResource("AfficherPlanning.fxml"));
    //Scene scene = new Scene(parentAfficherPlanning);
    Stage stageEdit = (Stage)((Node)event.getSource()).getScene().getWindow();

    stageEdit.hide();
    }
}

          