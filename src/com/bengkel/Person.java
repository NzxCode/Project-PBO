package com.bengkel;

public abstract class Person {
    private String id;
    private String nama;
    private String noHP;
    private String alamat;

    public Person(String id, String nama, String noHP, String alamat) {
        setId(id);
        setNama(nama);
        setNoHP(noHP);
        setAlamat(alamat);
    }

    public Person() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = Validasi.wajibIsi(id, "ID"); }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = Validasi.wajibIsi(nama, "Nama"); }
    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) {
        String v = Validasi.wajibIsi(noHP, "No HP");
        if (!v.matches("[0-9+\\- ]{8,16}")) throw new IllegalArgumentException("No HP tidak valid.");
        this.noHP = v;
    }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = Validasi.wajibIsi(alamat, "Alamat"); }

    public abstract String getPeran();
}
