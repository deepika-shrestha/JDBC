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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import murach.business.Customer;
import murach.db.DBException;
import murach.db.CustomerDB;

public class CustomerForm extends JDialog {
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton confirmButton;
    private JButton cancelButton;

    private Customer customer = new Customer();

    public CustomerForm(java.awt.Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initComponents();
    }
    
    public CustomerForm(java.awt.Frame parent, String title,
            boolean modal, Customer customer) {
        this(parent, title, modal);        
        this.customer = customer;
        confirmButton.setText("Save");
        emailField.setText(customer.getEmail());
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
    }

    private void initComponents() {
        emailField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        cancelButton = new JButton();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);     
        
        Dimension shortField = new Dimension(100, 20);
        Dimension longField = new Dimension(300, 20);
        emailField.setPreferredSize(longField);
        emailField.setMinimumSize(longField);        
        firstNameField.setPreferredSize(shortField);
        firstNameField.setMinimumSize(shortField);        
        lastNameField.setPreferredSize(longField);
        lastNameField.setMinimumSize(longField);
        
        cancelButton.setText("Cancel");
        cancelButton.addActionListener((ActionEvent) -> {
            cancelButtonActionPerformed();
        });

        confirmButton.setText("Add");
        confirmButton.addActionListener((ActionEvent) -> {
            confirmButtonActionPerformed();
        });

        // JLabel and JTextField panel
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new GridBagLayout());
        customerPanel.add(new JLabel("Email:"), 
                getConstraints(0, 0, GridBagConstraints.LINE_END));
        customerPanel.add(emailField,
                getConstraints(1, 0, GridBagConstraints.LINE_START));
        customerPanel.add(new JLabel("First Name:"), 
                getConstraints(0, 1, GridBagConstraints.LINE_END));
        customerPanel.add(firstNameField, 
                getConstraints(1, 1, GridBagConstraints.LINE_START));
        customerPanel.add(new JLabel("Last Name:"), 
                getConstraints(0, 2, GridBagConstraints.LINE_END));
        customerPanel.add(lastNameField, 
                getConstraints(1, 2, GridBagConstraints.LINE_START));

        // JButton panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // add panels to main panel
        setLayout(new BorderLayout());
        add(customerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();        
    }
    
    private GridBagConstraints getConstraints(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        return c;
    }

    private void cancelButtonActionPerformed() {                                             
        dispose();
    }                                            

    private void confirmButtonActionPerformed() {
        if (validateData()) {
            setData();
            if (confirmButton.getText().equals("Add")) {
                doAdd();
            } else {
                doEdit();
            }
        }
    }
    
    private boolean validateData() {
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        
        if (email == null || firstName == null || lastName == null ||
                email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Missing Fields", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } 
        return true;
    }
    
    private void setData() {
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
       
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
    }
    
    private void doEdit() {
        try {
            CustomerDB.update(customer);
            dispose();
            fireDatabaseUpdatedEvent();
        } catch (DBException e) {
            System.out.println(e);
        }
    }
    
    private void doAdd() {
        try {
            CustomerDB.add(customer);
            dispose();
            fireDatabaseUpdatedEvent();
        } catch(DBException e) {
            System.out.println(e);
        }
    }
    
    private void fireDatabaseUpdatedEvent() {
        CustomerManagerFrame mainWindow = (CustomerManagerFrame) getOwner();
        mainWindow.fireDatabaseUpdatedEvent();
    }
}
