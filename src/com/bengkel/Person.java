package com.bengkel;

/*
 * Abstract (Parent) Class: Person
 */
public abstract class Person {
    private String id;
    private String nama;
    private String noHP;
    private String alamat;

    //Constructor #1
    public Person(String id, String nama, String noHP, String alamat) {
        setId(id);
        setNama(nama);
        setNoHP(noHP);
        setAlamat(alamat);
    }

    //Constructor #2: ask user to input each attribute value
    public Person() {
        setId(Input.bacaString("ID = "));
        setNama(Input.bacaString("Nama = "));
        setNoHP(Input.bacaString("No HP = "));
        setAlamat(Input.bacaString("Alamat = "));
    }

    //setters - public
    public void setId(String id) {
        this.id = id;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    //getters - public
    public String getId() {
        return id;
    }
    public String getNama() {
        return nama;
    }
    public String getNoHP() {
        return noHP;
    }
    public String getAlamat() {
        return alamat;
    }

    //Abstract Method
    public abstract String getPeran();
}
