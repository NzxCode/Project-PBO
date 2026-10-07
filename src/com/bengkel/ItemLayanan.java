package com.bengkel;

public abstract class ItemLayanan {
    private String kode;
    private String nama;
    private double harga;

    public ItemLayanan(String kode, String nama, double harga) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    public ItemLayanan() {}

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public abstract String getJenisItem();
}