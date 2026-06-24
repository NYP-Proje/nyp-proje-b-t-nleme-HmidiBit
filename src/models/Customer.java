package models;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String tcNo;
    private String email;

    public Customer() {}

    public Customer(String firstName, String lastName, String tcNo, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tcNo = tcNo;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getTcNo() { return tcNo; }
    public void setTcNo(String tcNo) { this.tcNo = tcNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}