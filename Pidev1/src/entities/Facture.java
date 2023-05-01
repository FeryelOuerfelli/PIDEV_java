/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 *
 * @author Feryel
 */
public class Facture {
     private int idf;
    private float montant;
    private Date date ;
    private String image_signature ;
    private String num_facture;
     private String etat;
    private int idph;
   private int ordonnance;


   

    public Facture() {
    }
    public Facture(int idf, Date date, String num_facture , float montant, String image_signature, String etat , int idph ,int ordonnance) {
        this.idf = idf;
         this.date = date;
        this.num_facture = num_facture;
        this.montant = montant;
        this.image_signature = image_signature;
        this.etat = etat ;
        this.ordonnance = ordonnance;
        this.idph = idph;

    }

    public Facture(int idf, float montant, Date date) {
         this.idf = idf;
        this.montant = montant;
        this.date = date;
    }
     public Facture( Date date ,float montant,String num_facture ,String image_signature,String etat) {
                 this.date = date;
                 this.montant = montant;
                 this.num_facture = num_facture;
                this.image_signature = image_signature;
                this.etat=etat;


    }
         public Facture( int idf ,Date date ,float montant,String num_facture ,String image_signature,String etat) {
                this.idf = idf;
                 this.date = date;
                 this.montant = montant;
                 this.num_facture = num_facture;
                this.image_signature = image_signature;
                this.etat=etat;


    }



    public Facture(int idf, Date date, String num_facture, Float montant, String image_signature, String etat, int idph) {
    this.idf = idf;
         this.date = date;
        this.num_facture = num_facture;
        this.montant = montant;
        this.image_signature = image_signature;
        this.etat = etat ;
        this.idph = idph;    }

    public Facture(int idf, Date date, String num_facture, Float montant, String image_signature, String etat, int idph, int ordonnance) {
    this.idf = idf;
         this.date = date;
        this.num_facture = num_facture;
        this.montant = montant;
        this.image_signature = image_signature;
        this.etat = etat ;
        this.ordonnance = ordonnance;
        this.idph = idph;    }

   

    

    public Facture(Date date, String num_facture, float montant, String image_signature, String etat, int ordonnance, int idph) {
         this.date = date;
        this.num_facture = num_facture;
        this.montant = montant;
        this.image_signature = image_signature;
        this.etat = etat ;
        this.ordonnance = ordonnance;
        this.idph = idph;     }

    public Facture(int idph, Date date, String num_facture, Float montant, String etat, int ordonnance_id, int pharmacie_id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    

      

   public int getIDf() {
        return idf;
    }

    public void setIDf(int idf) {
        this.idf = idf;
    }
      public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
    
  public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
       public String getNumfacture() {
        return num_facture;
    }

    public void setNumfacture(String num_facture) {
        this.num_facture = num_facture;
    }
    
      public String getImgsig() {
        return image_signature;
    }

    public void setImgsig(String image_signature) {
        this.image_signature = image_signature;
    }
  
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
  public int getIdph() {
        return idph;
    }

    public void setIdph(int idph) {
        this.idph = idph;
    }
       public int getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(int ordonnance) {
        this.ordonnance = ordonnance;
    }
    
    
      @Override
    public String toString() {
        return "Facture{" + "idf=" + idf + ", montant=" + montant + ", date=" + date + ",  num_facture=" +  num_facture + ", image_signature=" + image_signature +", etat=" + etat + '}';
    }
}
