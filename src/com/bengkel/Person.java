package com.bengkel;

public abstract class Person {
    private String id;
    private String nama;
    private String noHP;
    private String alamat;

    public Person(String id, String nama, String noHP, String alamat) {
        this.id = id;
        this.nama = nama;
        this.noHP = noHP;
        this.alamat = alamat;
    }

    public Person() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNoHP() { return noHP; }
    public void setNoHP(String noHP) { this.noHP = noHP; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public abstract String getPeran();
}