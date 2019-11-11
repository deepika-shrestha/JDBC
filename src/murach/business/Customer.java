package murach.business;

/*
Name:Deepika Shrestha
Date: 6/15/2018
Project Name: CIS3145 Project 4
Description: This is an application that displays a table of customer data.
 */
  

   public class Customer {

    private int id;
    private String email;
    private String firstName;
    private String lastName;

    public Customer() {
    }

    public Customer(int id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

     public void setId(int id) {
        this.id = id;
    }
     
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

     public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
     
    public String getLastName() {
        return lastName;
    }
   
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
   } 
