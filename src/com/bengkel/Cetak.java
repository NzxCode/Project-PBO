// File: Cetak.java
package com.bengkel;

// Mendeklarasikan interface Cetak yang akan di-implements oleh class lain
public interface Cetak {
    // Mendefinisikan konstanta nama toko (otomatis static final)
    String TOKO = "BENGKEL MOTOR JAYA ABADI";
    // Mendefinisikan konstanta alamat toko (otomatis static final)
    String ALAMAT = "Jl. Contoh Raya No. 123, Jakarta"; 

    // Abstract method untuk mencetak struk (otomatis public abstract)
    void struk(); 
}