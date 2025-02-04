package services;

import entities.Facture;
import entities.Pharmacie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utils.MyConnection;

/**
 *
 * @author feryel
 */
public class FactureService {
    private final Connection cnx;
    private PreparedStatement  preparedStatement;
    public FactureService() {
           cnx = MyConnection.getInstance().getCnx();
    }
    public void ajouterFacture(Facture f){
        //String request = "INSERT INTO Planning SET date_debut=?,date_fin=?,heure_debut=?,heure_fin=?,description=?,etat=?,date_de_creation=?,date_de_modification" ;
        String request="INSERT INTO facture(date,num_facture,montant,etat,image_signature,ordonnance_id,pharmacie_id)"+"VALUES(?,?,?,?,?,?,?) ";
       

       try {
            preparedStatement = cnx.prepareStatement(request);
           
            preparedStatement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            preparedStatement.setInt(2,f.getNumfacture());
            preparedStatement.setFloat(3,f.getMontant());
            preparedStatement.setString(4,f.getEtat());
            preparedStatement.setString(5,f.getImgsig());
             preparedStatement.setInt(6,f.getOrdonnance());
             preparedStatement.setInt(7,f.getIdph());



          ;
            preparedStatement.executeUpdate();
            System.out.println ("succes"); 
       
       }catch (SQLException e) {
            System.out.println (e);
        }
        

    }
 
    
    public void EditFacture (Facture f){
        System.out.println(f.getMontant());
        String request = "UPDATE Facture SET montant=\""+f.getMontant()+"\" ,date =\""+new java.sql.Date(f.getDate().getTime())+"\",image_signature=\""+f.getImgsig()+"\",num_facture=\""+f.getNumfacture()+"\",etat=\""+f.getEtat()+"\"where id="+f.getIDf()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("facture modifiée");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service edit erreur");
    }}
    
        public ObservableList<Facture> showFacture() throws SQLException{
        String request = "SELECT * FROM facture";
        ObservableList<Facture> factureList =  FXCollections.observableArrayList();
        try {
            preparedStatement = cnx.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idf = resultSet.getInt("id");
                String etat = resultSet.getString("etat");
                Float montant = resultSet.getFloat("Montant");
                Date date = resultSet.getDate("date");
              String image_signature = resultSet.getString("image_signature");
              int num_facture = resultSet.getInt("num_facture");
              int idph = resultSet.getInt("pharmacie_id");
              int ordonnance = resultSet.getInt("ordonnance_id");


  
               

          Facture facture = new Facture(idf, date, num_facture, montant, image_signature, etat, idph,ordonnance);
               factureList.add(facture);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }

        return factureList;
        

    }
        
    public void supprimerFacture (int idFacture){
          
        String request = "DELETE FROM Facture where id="+idFacture;    
        try {
            System.out.println(idFacture);
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Supprimer Facture");
            alert1.setContentText("la facture  " + idFacture + " a été supprimer avec succés");
            Optional<ButtonType> answer1 = alert1.showAndWait();
            System.out.println("Delete Done");
               
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println ("service supression");
               
        }
    }
    
    public Facture findFacById(int Idf) throws SQLException{
        String request = "SELECT * FROM rendez_vous where id="+Idf+"";
        Facture f = null;
         try {
         preparedStatement = cnx.prepareStatement(request);
         ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            
            System.out.println("resultSet"+resultSet.getInt("id"));
            f = new Facture(Idf,resultSet.getDate("date"),resultSet.getFloat("Montant"),resultSet.getInt("Numéro Facture"),resultSet.getString("image_signature"),resultSet.getString("etat"));

        }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
         return f;
    }
      public void confirmerfacture (Facture f){
        String request = "UPDATE facture SET etat =\""+"payé"+"\"where id="+f.getIDf()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("Facture payé");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service confim erreur");
        }
    }
        public void archiverf (Facture f){
        String request = "UPDATE facture SET etat =\""+"archivé"+"\"where id="+f.getIDf()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("Facture archivé");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service archive erreur");
        }
    }
         
}
