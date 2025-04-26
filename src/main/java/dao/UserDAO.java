/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        this.connexion = MySQLConnexion.getConnexion();
    }

    public ArrayList<Utilisateur> findAll() {
        try {
            String query = "SELECT * FROM utilisateur";
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

    public Utilisateur delete(Utilisateur user) {
        try {
            String query = "delete from utilisateur where id = ?";
            PreparedStatement ps = this.connexion.prepareStatement(query);
            ps.setString(1, Integer.toString(user.getId()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Utilisateur.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

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
