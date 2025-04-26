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
            //Partie CreateDialog
            case "openInsertUser":
                // demander à creatDialog de s'afficher
                this.createDialog.setVisible(true);
                break;
            case "validInsertUser":
                Utilisateur newUser = new Utilisateur(
                        this.createDialog.getNom(),
                        this.createDialog.getPrenom(),
                        this.createDialog.getEmail(),
                        this.createDialog.getMotDePasse()
                );
                this.userListModel.insert(newUser);

                this.createDialog.setVisible(false);
                break;

            //Partie ModifDialog
            case "openUpdateUser":
                this.modifDialog.setId(this.view.getSelectedId());
                this.modifDialog.setNom(this.view.getSelectedNom());
                this.modifDialog.setPrenom(this.view.getSelectedPrenom());
                this.modifDialog.setEmail(this.view.getSelectedEmail());
                this.modifDialog.setMotDePasse(this.view.getSelectedMdp());
                this.modifDialog.setMotDePasseValider(this.view.getSelectedMdp());
                this.modifDialog.setVisible(true);
                break;

            case "validationNouveauUser":
                Utilisateur utilisateur = new Utilisateur(
                        modifDialog.getId(),
                        modifDialog.getNom(),
                        modifDialog.getPrenom(),
                        modifDialog.getEmail(),
                        modifDialog.getMotDePasse()
                );

                this.userListModel.update(utilisateur);
                this.modifDialog.setVisible(false);

                break;
            case "deleteUser":
                int id = view.getSelectedId();
                userListModel.delete(id);

                view.setTableModel(userListModel);
                break;
            case "closeModifDialog":
                this.modifDialog.setVisible(false);
                break;
            case "closeCreateDialog":
                this.createDialog.setVisible(false);
                break;
        }
    }

}
