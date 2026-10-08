package com.bengkel;

// Interface bertindak sebagai kontrak (contract) baku untuk class yang mengimplementasikannya.
// Semua class yang meng-implements Cetak diwajibkan memiliki fungsionalitas cetak struk.
public interface Cetak {
    // Di dalam interface, setiap variabel otomatis bersifat public static final (konstanta).
    // Cocok untuk menyimpan data statis seperti nama toko agar menghindari hardcode.
    String TOKO = "BENGKEL MOTOR JAYA ABADI";
    String ALAMAT = "Jl. Contoh Raya No. 123, Jakarta"; 

    // Abstract method tanpa body.
    // Memaksa class turunannya untuk mendefinisikan sendiri bagaimana cara mencetak struk.
    void struk(); 
}