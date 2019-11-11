package murach.db;

/*
Name:Deepika Shrestha
Date: 6/15/2018
Project Name: CIS3145 Project 4
Description: This is an application that displays a table of customer data.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import murach.business.Customer;

public class CustomerDB {

    private static Customer getCustomerFromRow(ResultSet rs) throws SQLException {

        int customerID = rs.getInt(1);
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);
        String email = rs.getString(4);

        Customer c = new Customer();
        c.setId(customerID);
        c.setEmail(email);
        c.setFirstName(firstName);
        c.setLastName(lastName);

        return c;
    }

    public static List<Customer> getAll() throws DBException {
        String sql = "SELECT * FROM Customer ORDER BY CustomerID";
        List<Customer> customers = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = getCustomerFromRow(rs);
                customers.add(c);
            }
            rs.close();
            return customers;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Customer get(String email) throws DBException {
        String sql = "SELECT * FROM Customer WHERE EmailAddress = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = getCustomerFromRow(rs);
                rs.close();
                return c;
            } else {
                rs.close();
                return null;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void add(Customer customer) throws DBException {
        String sql
                = "INSERT INTO Customer (CustomerID, EmailAddress, FirstName, LastName) "
                + "VALUES (?, ?, ?, ?)";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getFirstName());
            ps.setString(4, customer.getLastName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void update(Customer customer) throws DBException {
        String sql = "UPDATE Customer SET "
                + "EmailAddress = ?, "
                + "FirstName = ?, "
                + "LastName = ? "
                + "WHERE CustomerID = ? ";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {            
            ps.setString(1, customer.getEmail());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setLong(4, customer.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static void delete(Customer customer)
            throws DBException {
        String sql = "DELETE FROM Customer "
                + "WHERE CustomerID = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}

