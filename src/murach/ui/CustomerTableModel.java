/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package murach.ui;

/**
 *
 * @author deepika
 */
import java.util.List;
import javax.swing.table.AbstractTableModel;

import murach.business.Customer;
import murach.db.CustomerDB;
import murach.db.DBException;

public class CustomerTableModel extends AbstractTableModel {
    private List<Customer> customers;
    private final String[] COLUMN_NAMES = { "Email", "First Name", "Last Name" };
 
    public CustomerTableModel() {
        try {
            customers = CustomerDB.getAll();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    @Override
    public int getRowCount() {
        return customers.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return customers.get(rowIndex).getEmail();
            case 1:
                return customers.get(rowIndex).getFirstName();
            case 2:
                return customers.get(rowIndex).getLastName();
            default:
                return null;
        }
    }   
    
    Customer getCustomer(int rowIndex) {
        return customers.get(rowIndex);
    }
    
    void databaseUpdated() {
        try {
            customers = CustomerDB.getAll();
            fireTableDataChanged();
        } catch (DBException e) {
            System.out.println(e);
        }        
    }    
}
