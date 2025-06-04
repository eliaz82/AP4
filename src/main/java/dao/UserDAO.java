/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Utilisateur;

/**
 *
 * @author e.talmitte
 */
public class UserDAO {

    private Connection connexion;

    public UserDAO() {
        // Instanciation Singleton
        this.connexion = MySQLConnexion.getConnexion();
    }

    //Récupere tout les utilisateurs
    public ArrayList<Utilisateur> findAll() {
        try {
            // ajout dun where pour filter les utilisateurs pas supprime
            //donc qui ont dans la clone datedepart null
            String query = "SELECT * FROM utilisateur WHERE dateDepart IS NULL";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ResultSet res = ps.executeQuery();

            ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();

            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresseMail = res.getString("adresseMail");
                String mdp = res.getString("mdp");

                users.add(new Utilisateur(id, nom, prenom, adresseMail, mdp));
            }

            return users;
        } catch (SQLException ex) {
            return null;
        }
    }

    public ArrayList<Utilisateur> findUtilisateurSuppr() {
        try {

            // soustrait 2 ans a la date du jours
            java.sql.Date dateLimite = java.sql.Date.valueOf(LocalDate.now().minusYears(2));

            //ajout du where pour filtreer les utilisateur suprimer depuis au moins 2 ans
            String query = "SELECT * FROM utilisateur WHERE dateDepart <= ?";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ps.setDate(1, dateLimite);

            ResultSet res = ps.executeQuery();
            ArrayList<Utilisateur> users = new ArrayList<>();

            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String adresseMail = res.getString("adresseMail");
                String mdp = res.getString("mdp");

                users.add(new Utilisateur(id, nom, prenom, adresseMail, mdp));
            }
            return users;
        } catch (SQLException ex) {
            return null;
        }
    }

    //Crée un user
    public Utilisateur insert(Utilisateur user) {
        try {
            String query = "insert into utilisateur(nom, prenom,adresseMail,mdp) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp());
            int n = ps.executeUpdate();
            user.setId(n);
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    //Modifie un user
    public Utilisateur update(Utilisateur user) {
        try {
            String query = "update utilisateur Set nom = ?, prenom = ?,adresseMail = ?,mdp = ? where id = ?";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getMdp());
            ps.setString(5, Integer.toString(user.getId()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    //Suprime un user
    public void delete(int id) {
        try {
            //fais un update met la date actuelle dans la colone dateDepart
            String query = "UPDATE utilisateur SET dateDepart = ? WHERE id = ?";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Trouve un user grace a son id
    public Utilisateur findById(int id) {
        Utilisateur user = null;
        try {
            String query = "SELECT * FROM utilisateur WHERE id = ?";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                user = new Utilisateur(
                        res.getInt("id"),
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("adresseMail"),
                        res.getString("mdp")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
