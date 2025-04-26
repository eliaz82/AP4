package model;

import dao.UserDAO;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author e.talmitte
 */
public class UserListModel extends AbstractTableModel {

    private ArrayList<Utilisateur> users = new ArrayList<Utilisateur>();

    private final String[] entetes = {"ID", "Nom", "Prenom", "Email", "Mot de passe"};
    private UserDAO usersDao;

    public UserListModel() {
        this.usersDao = new UserDAO();
        users = this.usersDao.findAll();
    }

    public String getColumnName(int column) {
        return entetes[column];
    }

    public int getRowCount() {
        return users.size();

    }

    public int getColumnCount() {
        return entetes.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Utilisateur i = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return i.getId();
            case 1:
                return i.getNom();
            case 2:
                return i.getPrenom();
            case 3:
                return i.getEmail();
            case 4:
                return i.getMdp();
        }
        return "Non defini";
    }

    public void delete(int id) {
        Utilisateur user = this.usersDao.findById(id);
        usersDao.delete(user);

        users = this.usersDao.findAll();

        fireTableDataChanged();
    }

    public void insert(Utilisateur user) {
        Utilisateur newUser = this.usersDao.insert(user);
        users.add(newUser);
        fireTableDataChanged();
    }

    public void update(Utilisateur user) {
        this.usersDao.update(user);
        this.users = this.usersDao.findAll();

        fireTableDataChanged();
    }
}
