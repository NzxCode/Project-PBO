package com.bengkel;

public class Customer extends Person {
    private String noCustomer;
    private String email;

    public Customer(String id, String nama, String noHP, String alamat, String noCustomer, String email) {
        super(id, nama, noHP, alamat);
        this.noCustomer = noCustomer;
        this.email = email;
    }

    public Customer() { super(); }

    public String getNoCustomer() { return noCustomer; }
    public void setNoCustomer(String noCustomer) { this.noCustomer = noCustomer; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getPeran() { return "Customer"; }
}