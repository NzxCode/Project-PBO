package com.bengkel;

// Penerapan Abstraction: Person dijadikan abstract class karena ini hanya kerangka dasar.
// Secara logis, tidak ada entitas murni bernama "Person" di sistem, yang ada hanyalah Customer atau Mekanik.
public abstract class Person {
    // Encapsulation: Menggunakan modifier private agar data tidak bisa dimutasi sembarangan dari luar class.
    private String id;
    private String nama;
    private String noHP;
    private String alamat;

    // Constructor #1: Overloading constructor untuk inisialisasi object secara langsung (terprogram).
    // Berguna saat kita membaca data dari file teks dan langsung memasukkannya ke memori.
    public Person(String id, String nama, String noHP, String alamat) {
        // Meneruskan assignment ke setter agar jika di masa depan ada validasi (misal regex No HP),
        // kita cukup mengubah logika di setternya saja.
        setId(id);
        setNama(nama);
        setNoHP(noHP);
        setAlamat(alamat);
    }

    // Constructor #2: Overloading constructor khusus untuk menangani input dinamis via Command Line.
    public Person() {
        // Memanggil class utilitas Input untuk melakukan sanitasi/penjagaan format input.
        setId(Input.bacaString("ID = "));
        setNama(Input.bacaString("Nama = "));
        setNoHP(Input.bacaString("No HP = "));
        setAlamat(Input.bacaString("Alamat = "));
    }

    // Kumpulan Mutator (Setter) untuk mengamankan modifikasi atribut.
    public void setId(String id) { this.id = id; }
    public void setNama(String nama) { this.nama = nama; }
    public void setNoHP(String noHP) { this.noHP = noHP; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    // Kumpulan Accessor (Getter) untuk membaca nilai properti private.
    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getNoHP() { return noHP; }
    public String getAlamat() { return alamat; }

    // Abstract method: Method ini belum memiliki implementasi (body).
    // Ini memaksa setiap child class untuk mendeklarasikan jati dirinya (perannya) masing-masing.
    public abstract String getPeran();
}