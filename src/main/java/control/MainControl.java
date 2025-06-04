/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.UserListModel;
import model.Utilisateur;
import view.CreateDialog;
import view.MainView;
import view.ModifDialog;

/**
 *
 * @author e.talmitte
 */
public class MainControl implements PropertyChangeListener {

    //attribut
    private MainView view;
    private UserListModel userListModel;
    private ModifDialog modifDialog;
    private CreateDialog createDialog;

    //constructeur
    public MainControl(MainView view) {
        this.userListModel = new UserListModel();
        this.view = view;
        this.view.addPropertyChangeListener(this);
        this.view.setTableModel(userListModel);

        this.modifDialog = new ModifDialog(this.view, true);
        this.modifDialog.addPropertyChangeListener(this);

        this.createDialog = new CreateDialog(this.view, true);
        this.createDialog.addPropertyChangeListener(this);
    }

    //methodes
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "openInsertUser":
                this.createDialog.setDefaultText();
                //Affiche le modal CreateDialog.
                this.createDialog.setVisible(true);
                break;
            case "validInsertUser":
                //Instancie un objet Utilisateur avec les données du formulaire.
                Utilisateur newUser = new Utilisateur(
                        this.createDialog.getNom(),
                        this.createDialog.getPrenom(),
                        this.createDialog.getEmail(),
                        this.createDialog.getMotDePasse()
                );
                //Insert l'utilisateur
                this.userListModel.insert(newUser);
                // Ferme la fenêtre de création.
                this.createDialog.setVisible(false);
                break;
            case "openUpdateUser":
                //Recupere toute les informations de l'user selctionné.
                this.modifDialog.setId(this.view.getSelectedId());
                this.modifDialog.setNom(this.view.getSelectedNom());
                this.modifDialog.setPrenom(this.view.getSelectedPrenom());
                this.modifDialog.setEmail(this.view.getSelectedEmail());
                this.modifDialog.setMotDePasse(this.view.getSelectedMdp());
                this.modifDialog.setMotDePasseValider(this.view.getSelectedMdp());
                //Affiche le modal ModifDialog.
                this.modifDialog.setVisible(true);
                break;

            case "validationNouveauUser":
                //Instancie un objet Utilisateur avec les données du formulaire, y compris l'ID.
                Utilisateur utilisateur = new Utilisateur(
                        modifDialog.getId(),
                        modifDialog.getNom(),
                        modifDialog.getPrenom(),
                        modifDialog.getEmail(),
                        modifDialog.getMotDePasse()
                );
                //Met à jour les infos de l'utilisateur sélectionné.
                this.userListModel.update(utilisateur);
                //Ferme la fenêtre de modification.
                this.modifDialog.setVisible(false);

                break;
            case "deleteUser":
                //Recupere l'id selectionner dans la JTable.
                int id = view.getSelectedId();
                //Supprime l'user selon l'id selectionner.
                userListModel.delete(id);
                break;
            case "closeModifDialog":
                //Ferme le modal ModifDialog.
                this.modifDialog.setVisible(false);
                break;
            case "closeCreateDialog":
                //Ferme le modal CreateDialog.
                this.createDialog.setVisible(false);
                break;
            case "btnsupprliste":
                // Apelle la methode findUtilisateurSuppr
                this.userListModel.findUtilisateurSuppr();
                break;
            case "btnFindAlLliste":
                // Apelle la methode findALL
                this.userListModel.findAll();
                break;
        }
    }

}
