package com.bengkel;

public class Customer extends Person {
    private String noCustomer;
    private String email;

    public Customer(String id, String nama, String noHP, String alamat, String noCustomer, String email) {
        super(id, nama, noHP, alamat);
        setNoCustomer(noCustomer);
        setEmail(email);
    }

    public Customer() { super(); }

    public String getNoCustomer() { return noCustomer; }
    public void setNoCustomer(String noCustomer) { this.noCustomer = Validasi.wajibIsi(noCustomer, "No Customer"); }
    public String getEmail() { return email; }
    public void setEmail(String email) {
        String v = Validasi.wajibIsi(email, "Email");
        if (!v.contains("@")) throw new IllegalArgumentException("Email tidak valid.");
        this.email = v;
    }

    @Override
    public String getPeran() { return "Customer"; }
}
