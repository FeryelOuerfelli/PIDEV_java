/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Pharmacie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class PharmacieService {
    private final Connection cnx;
    private PreparedStatement  preparedStatement;
    public PharmacieService() {
           cnx = MyConnection.getInstance().getCnx();
    }
    public void ajouterPharmacie(Pharmacie  ph){
        //String request = "INSERT INTO Planning SET date_debut=?,date_fin=?,heure_debut=?,heure_fin=?,description=?,etat=?,date_de_creation=?,date_de_modification" ;
        String request="INSERT INTO pharmacie(nom,adresse,gouvernorat,num_tel,email,matricule,etat,horaire,description,services)"+"VALUES(?,?,?,?,?,?,?,?,?,?) ";
       

       try {
             preparedStatement = cnx.prepareStatement(request);
            preparedStatement.setString(1,ph.getNom());
            preparedStatement.setString(2,ph.getAdresse());
            preparedStatement.setString(3,ph.getGouvernorat());
            preparedStatement.setString(4,ph.getNum_tel());
            preparedStatement.setString(5,ph.getEmail());
            preparedStatement.setString(6,ph.getMatricule());
            preparedStatement.setString(7,ph.getEtat());
            preparedStatement.setString(8,ph.getHoraire());
            preparedStatement.setString(9,ph.getDescription());
            preparedStatement.setString(10,ph.getServices());
            preparedStatement.executeUpdate();
            System.out.println ("succes"); 
       
       }catch (SQLException e) {
            System.out.println (e);
        }
        

    }
 
    
    public void EditPharmacie (Pharmacie ph){
        System.out.println(ph.getDescription());
        String request = "UPDATE Pharmacie SET nom=\""+ph.getNom() +"\",adresse=\""+ph.getAdresse()+"\",gouvernorat=\""+ph.getGouvernorat()+"\",num_tel=\""+ph.getNum_tel()+"\",email=\""+ph.getEmail()+"\",matricule=\""+ph.getMatricule()+"\",horaire=\""+ph.getHoraire()+"\",etat=\""+ph.getEtat()+"\",services=\""+ph.getServices()+"\",description=\""+ph.getDescription()+"\"where id="+ph.getIdph()+"";    
        try {
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
             System.out.println("pharmacie modifiée");
         } catch (SQLException ex) {
             System.out.println(ex);
             System.out.println ("service edit erreur");
    }}
    
        public ObservableList<Pharmacie> showPharmacie() throws SQLException{
        String request = "SELECT * FROM pharmacie";
        ObservableList<Pharmacie> pharmacieList =  FXCollections.observableArrayList();
        try {
            preparedStatement = cnx.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idph = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                String gouvernorat = resultSet.getString("gouvernorat");
                String num_tel = resultSet.getString("num_tel");
                String email = resultSet.getString("email");
                String matricule = resultSet.getString("matricule");
                String horaire = resultSet.getString("horaire");
                String etat = resultSet.getString("etat");
                String description = resultSet.getString("description");
                String services = resultSet.getString("services");

                Pharmacie pharmacie = new Pharmacie(idph,nom,adresse,gouvernorat,num_tel,email,matricule,horaire,etat,description,services);
                pharmacieList.add(pharmacie);

            }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }

        return pharmacieList;
        

    }
        
    public void supprimerPharmacie (int idPharmacie){
          
        String request = "DELETE FROM Pharmacie where id="+idPharmacie;    
        try {
            System.out.println(idPharmacie);
            preparedStatement = cnx.prepareStatement(request);
            preparedStatement.executeUpdate();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Supprimer Pharmacie");
            alert1.setContentText("la pharmacie  " + idPharmacie + " a été supprimer avec succés");
            Optional<ButtonType> answer1 = alert1.showAndWait();
            System.out.println("Delete Done");
               
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println ("service supression");
               
        }
    }
    
    public List<Pharmacie> search(String critere) throws SQLException {

        List<Pharmacie> pharmacie = new ArrayList<>();
        pharmacie = showPharmacie();
        List<Pharmacie> pharmacieStream = pharmacie.stream()
                .filter(a ->{
                    
                        boolean match = 
                       
                         (a.getNom().contains(critere))||
                         (a.getAdresse().contains(critere))||
                         (a.getGouvernorat().contains(critere))||
                         (a.getNum_tel().contains(critere))||
                         (a.getEmail().contains(critere))||
                         (a.getMatricule().contains(critere))||
                         (a.getHoraire().contains(critere))||
                         (a.getEtat().contains(critere))||
                         (a.getDescription().contains(critere))||
                         (a.getServices().contains(critere));

                                

                              
                              
                        return match;
                })
                       
                .collect(Collectors.toList());
        for (Pharmacie ph : pharmacieStream) {
        System.out.println(ph.toString());
        }
       return pharmacieStream;      }
         
}
