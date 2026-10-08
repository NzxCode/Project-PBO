package com.bengkel;

// Penerapan Inheritance: Customer meng-extends class Person.
// Semua atribut dan method public/protected milik Person diwariskan ke sini.
public class Customer extends Person {
    // Variabel spesifik yang hanya relevan bagi entitas Customer.
    private String noCustomer;
    private String email;

    // Constructor lengkap class turunan.
    public Customer(String id, String nama, String noHP, String alamat, String noCustomer, String email) {
        // super() memanggil constructor parameter milik parent class (Person) untuk mengurus atribut dasarnya.
        super(id, nama, noHP, alamat); 
        // Mengurus atribut pribadinya sendiri.
        setNoCustomer(noCustomer);
        setEmail(email);
    }

    // Constructor interaktif class turunan.
    public Customer() {
        // Memanggil constructor interaktif milik Person terlebih dahulu (input ID, Nama, dll).
        super(); 
        // Melanjutkan prompt di terminal untuk field tambahannya.
        setNoCustomer(Input.bacaString("No Customer = "));
        setEmail(Input.bacaString("Email = "));
    }

    public void setNoCustomer(String noCustomer) { this.noCustomer = noCustomer; }
    public void setEmail(String email) { this.email = email; }
    public String getNoCustomer() { return noCustomer; }
    public String getEmail() { return email; }

    // Implementasi method abstract parent (Method Overriding).
    // Menandai jati diri instance ini dengan nilai konstan "Customer".
    @Override
    public String getPeran() {
        return "Customer";
    }
}