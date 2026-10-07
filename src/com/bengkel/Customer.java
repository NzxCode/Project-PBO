package com.bengkel;

public class Customer extends Person {
    private String noCustomer;
    private String email;

    //Constructor #1
    public Customer(String id, String nama, String noHP, String alamat,
                    String noCustomer, String email) {
        super(id, nama, noHP, alamat);
        setNoCustomer(noCustomer);
        setEmail(email);
    }

    //Constructor #2: ask user to input each attribute value
    public Customer() {
        super(); //input atribut milik Person
        setNoCustomer(Input.bacaString("No Customer = "));
        setEmail(Input.bacaString("Email = "));
    }

    //setters and getters
    public void setNoCustomer(String noCustomer) {
        this.noCustomer = noCustomer;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNoCustomer() {
        return noCustomer;
    }
    public String getEmail() {
        return email;
    }

    /*
     * method overriding
     * @overriding getPeran dari parent class "Person"
     */
    @Override
    public String getPeran() {
        return "Customer";
    }
}
