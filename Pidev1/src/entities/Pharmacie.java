/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Feryel
 */
public class Pharmacie {
    private int idph;
    private String nom;
    private String adresse;
    private String gouvernorat;
    private String num_tel;
    private String email;
    private String matricule; 
    private String horaire; 
    private String etat;
    private String description;
    private String services;
    public Pharmacie() {
    }
     public Pharmacie(int idph , String nom, String adresse, String gouvernorat, String num_tel, String email, String matricule,  String horaire ,String etat, String description, String services) {
         this.idph = idph;
         this.nom = nom;
        this.adresse = adresse;
        this.gouvernorat = gouvernorat;
        this.num_tel= num_tel;
        this.email = email;
        this.matricule = matricule;
        this.horaire = horaire;
        this.etat = etat;
        this.description = description;
        this.services =  services ;
       
    }
          public Pharmacie(  String nom, String adresse, String gouvernorat, String num_tel, String email, String matricule,  String horaire ,String etat, String description, String services) {
         this.nom = nom;
        this.adresse = adresse;
        this.gouvernorat = gouvernorat;
        this.num_tel= num_tel;
        this.email = email;
        this.matricule = matricule;
        this.horaire = horaire;
        this.etat = etat;
        this.description = description;
        this.services =  services ;
       
    }
              public Pharmacie(  String nom, String adresse, String num_tel, String email ,String etat) {
         this.nom = nom;
        this.adresse = adresse;
        this.num_tel= num_tel;
        this.email = email;
    
        this.etat = etat;
       
       
    }
        public int getIdph() {
        return idph;
    }

    public void setIdph(int idph) {
        this.idph = idph;
    }
      public String getNom() {
        return nom;
    }
       public String getAdresse() {
        return adresse;
    }
          public String getGouvernorat() {
        return gouvernorat;
    }
             public String getNum_tel() {
        return num_tel;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricule() {
        return matricule;
    }
  public String getHoraire() {
        return horaire;
    }
    public String getEtat() {
        return etat;
    }

     public String getDescription() {
        return description ;
    }
     public String getServices() {
        return services ;
    }
     
    public void setNom(String nom) {
        this.nom = nom;
    }
 public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
  public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }
   public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }
    public void setEmail(String email) {
        this.email = email;
    }
     public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
       public void setHoraire(String horaire) {
        this.horaire = horaire;
    }
      public void setEtat(String etat) {
        this.etat = etat;
    }
        public void setDescription(String description) {
        this.description = description;
    }  
        public void setServices(String services) {
        this.services = services;
    }
        
   

  

    @Override
    public String toString() {
        return "Pharmacie{" + "nom=" + nom + ", adresse=" + adresse + ", gouvernorat=" + gouvernorat + ", num_tel=" + num_tel + "email=" + email + ", matricule=" + matricule   + "etat=" + etat + ", descroption=" + description + ", services=" + services + '}';
    }
 
    
}
