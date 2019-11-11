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
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import murach.business.Customer;
import murach.db.CustomerDB;
import murach.db.DBException;

public class CustomerManagerFrame extends JFrame {
    private JTable customerTable;
    private CustomerTableModel customerTableModel;
    
    public CustomerManagerFrame() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }        
        setTitle("Customer Manager");
        setSize(768, 384);
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(buildButtonPanel(), BorderLayout.SOUTH);
        customerTable = buildCustomerTable();
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
        setVisible(true);        
    }
    
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        
        JButton addButton = new JButton("Add");
        addButton.setToolTipText("Add customer");
        addButton.addActionListener((ActionEvent) -> {
            doAddButton();
        });
        panel.add(addButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setToolTipText("Edit selected customer");
        editButton.addActionListener((ActionEvent) -> {
            doEditButton();
        });
        panel.add(editButton);
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setToolTipText("Delete selected customer");
        deleteButton.addActionListener((ActionEvent) -> {
            doDeleteButton();
        });
        panel.add(deleteButton);
        
        return panel;
    }
    
    private void doAddButton() {
        CustomerForm customerForm = new CustomerForm(this, "Add Customer", true);
        customerForm.setLocationRelativeTo(this);
        customerForm.setVisible(true);

    }
    
    private void doEditButton() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No customer is currently selected.", 
                    "No customer selected", JOptionPane.ERROR_MESSAGE);
        } else {
            Customer customer = customerTableModel.getCustomer(selectedRow);
            CustomerForm customerForm = 
                    new CustomerForm(this, "Edit Customer", true, customer);
            customerForm.setLocationRelativeTo(this);
            customerForm.setVisible(true);
        }
    }

   private void doDeleteButton() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "No customer is currently selected.", 
                    "No customer selected", JOptionPane.ERROR_MESSAGE);
        } else {
            Customer customer = customerTableModel.getCustomer(selectedRow);
            int ask = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete " + 
                        customer.getFirstName() + " from the database?",
                    "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (ask == JOptionPane.YES_OPTION) {
                try {                    
                    CustomerDB.delete(customer);
                    fireDatabaseUpdatedEvent();
                } catch (DBException e) {
                    System.out.println(e);
                }
            }
        }
    }

    public void fireDatabaseUpdatedEvent() {
        customerTableModel.databaseUpdated();
    }
    
    private JTable buildCustomerTable() {
        customerTableModel = new CustomerTableModel();
        JTable table = new JTable(customerTableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBorder(null);
        return table;
    }
}
