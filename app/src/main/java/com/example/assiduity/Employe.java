package com.example.assiduity;

public class Employe {
    String nom,prenom,email,adresse,mdp;
    String tel;

    public Employe(String nom, String prenom, String email, String adresse, String mdp, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.mdp = mdp;
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getMdp() {
        return mdp;
    }

    public String getTel() {
        return tel;
    }
}
