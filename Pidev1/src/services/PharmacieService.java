/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import controller.Statistics;
import controller.StatisticsRevenu;
import entities.Pharmacie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.chart.XYChart;
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
       public Pharmacie findPhById(int idph) throws SQLException{
        String request = "SELECT * FROM pharmacie where id="+idph+"";
        Pharmacie ph = null;
         try {
         preparedStatement = cnx.prepareStatement(request);
         ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            
            System.out.println("resultSet"+resultSet.getInt("id"));
                ph = new Pharmacie(idph,resultSet.getString("nom"),resultSet.getString("adresse"),resultSet.getString("gouvernorat"),resultSet.getString("num_tel"), resultSet.getString("email"),
                       resultSet.getString("matricule"),
                        resultSet.getString("horaire"),
                       resultSet.getString("etat"),
                       resultSet.getString("description"),
                        resultSet.getString("services"));
             
              
        }
        } catch (SQLException ex) {
            System.out.println(ex);
                    
        }
         return ph;
    }
            public int CalculP() throws SQLException {
         String sql = "SELECT * FROM pharmacie";
        
      
     Statement statement = cnx.createStatement(); 
     statement.setFetchSize(0);
     
     
ResultSet resultat = statement.executeQuery(sql); 

int n=0;
while (resultat.next()) {
   n=n+1;
  
}
 System.out.println(n);
  
 return n;
         
}
    
            
            
            public ObservableList<XYChart.Series<String, Number>> dataChart() {
          ObservableList<XYChart.Series<String, Number>> answer = FXCollections.observableArrayList();
          XYChart.Series<String, Number> serie1 = new XYChart.Series<String, Number>();
          XYChart.Series<String, Number> serie2 = new XYChart.Series<String, Number>();
          XYChart.Series<String, Number> serie3 = new XYChart.Series<String, Number>();
          XYChart.Series<String, Number> serie4 = new XYChart.Series<String, Number>();
          
          serie1.setName("Android");
          serie2.setName("Windows Phone");
          serie3.setName("iOS");
          serie4.setName("J2ME");
          
          String requete = "SELECT CASE month(date_debut) when \"01\" then \"Janvier\" when \"02\" then \"Fevrier\" when \"03\" then \"Mars\" when \"04\" then \"Avril\" when \"05\" then \"Mai\" when \"06\" then \"Juin\" when \"07\" then \"Juillet\" when \"08\" then \"Aout\" when \"09\" then \"Septembre\" when \"10\" then \"Octobre\" when \"11\" then \"Novembre\" when \"12\" then \"Decembre\" END , COUNT(c.nom_cours), c.nom_cours FROM coursuivi cs, cours c where cs.id_cours=c.idcours GROUP BY month(cs.date_debut), c.nom_cours ; " ; 
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery() ; 
            while (rs.next()) {
                System.out.println(rs.getString(3)+" "+rs.getString(2)+" "+rs.getString(1));
                switch (rs.getString(3)) {
                    case "Android":
                        serie1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
                        break;
                    case "Windows Phone":
                        serie2.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
                        break;
                    case "iOS":
                        serie3.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
                        break;
                    case "J2ME":
                        serie4.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
                        break;
                }
            }
            answer.addAll(serie1,serie2,serie3,serie4) ; 
            return answer ; 
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsRevenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null ; 
      }
      
      public ObservableList<XYChart.Series<String, Number>> dataChartSecond() {
          ObservableList<XYChart.Series<String, Number>> answer = FXCollections.observableArrayList();
          XYChart.Series<String, Number> serie1 = new XYChart.Series<String, Number>();
          XYChart.Series<String, Number> serie2 = new XYChart.Series<String, Number>();
          XYChart.Series<String, Number> serie3 = new XYChart.Series<String, Number>();
          XYChart.Series<String, Number> serie4 = new XYChart.Series<String, Number>();
          int x = 0 ; 
          int y = 0 ;
          int z = 0 ;
          int w = 0 ;
          
          serie1.setName("Android");
          serie2.setName("Windows Phone");
          serie3.setName("iOS");
          serie4.setName("J2ME");
          
          String requete = "SELECT CASE month(date) when \"01\" then \"Janvier\" when \"02\" then \"Fevrier\" when \"03\" then \"Mars\" when \"04\" then \"Avril\" when \"05\" then \"Mai\" when \"06\" then \"Juin\" when \"07\" then \"Juillet\" when \"08\" then \"Aout\" when \"09\" then \"Septembre\" when \"10\" then \"Octobre\" when \"11\" then \"Novembre\" when \"12\" then \"Decembre\" END , COUNT(pharmacie), pharmacie FROM pharmacie cs, cours c where cs.id_cours=c.idcours GROUP BY month(cs.date_debut), c.nom_cours ; " ; 
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery() ; 
            while (rs.next()) {
                
                
                System.out.println(rs.getString(3)+" "+rs.getString(2)+" "+rs.getString(1));
                switch (rs.getString(3)) {
                    case "Android":
                        x= x + rs.getInt(2);
                        serie1.getData().add(new XYChart.Data(rs.getString(1), x));
                        break;
                    case "Windows Phone":
                        y= y + rs.getInt(2);
                        serie2.getData().add(new XYChart.Data(rs.getString(1), y));
                        break;
                    case "iOS":
                        z= z + rs.getInt(2);
                        serie3.getData().add(new XYChart.Data(rs.getString(1), z));
                        break;
                    case "J2ME":
                        w= w + rs.getInt(2);
                        serie4.getData().add(new XYChart.Data(rs.getString(1), w));
                        break;
                }
            }
            answer.addAll(serie1,serie2,serie3,serie4) ; 
            return answer ; 
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsRevenu.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null ; 
      }
      
         
}
