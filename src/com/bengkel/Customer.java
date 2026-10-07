// File: Customer.java
package com.bengkel;

// Mendeklarasikan class Customer yang merupakan child class dari Person
public class Customer extends Person {
    // Atribut private untuk menyimpan data spesifik customer
    private String noCustomer;
    private String email;

    // Constructor #1 dengan parameter lengkap untuk inisialisasi objek
    public Customer(String id, String nama, String noHP, String alamat,
                    String noCustomer, String email) {
        // Memanggil constructor dari parent class (Person)
        super(id, nama, noHP, alamat);
        // Memanggil setter untuk mengisi nilai atribut class ini
        setNoCustomer(noCustomer);
        setEmail(email);
    }

    // Constructor #2 tanpa parameter yang meminta input user
    public Customer() {
        // Memanggil constructor default dari parent class
        super(); 
        // Meminta input dari console dan memasukkannya lewat setter
        setNoCustomer(Input.bacaString("No Customer = "));
        setEmail(Input.bacaString("Email = "));
    }

    // Setter untuk atribut noCustomer
    public void setNoCustomer(String noCustomer) {
        this.noCustomer = noCustomer;
    }
    // Setter untuk atribut email
    public void setEmail(String email) {
        this.email = email;
    }
    // Getter untuk atribut noCustomer
    public String getNoCustomer() {
        return noCustomer;
    }
    // Getter untuk atribut email
    public String getEmail() {
        return email;
    }

    // Melakukan overriding terhadap method getPeran dari parent class
    @Override
    public String getPeran() {
        // Me-return string yang merepresentasikan peran objek ini
        return "Customer";
    }
}