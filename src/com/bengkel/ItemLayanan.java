package com.bengkel;

public abstract class ItemLayanan {
    private String kode;
    private String nama;
    private double harga;

    public ItemLayanan(String kode, String nama, double harga) {
        setKode(kode);
        setNama(nama);
        setHarga(harga);
    }

    public ItemLayanan() {
        setKode(Input.bacaString("Kode = "));
        setNama(Input.bacaString("Nama = "));
        setHarga(Input.bacaDouble("Harga = ", 0, Double.MAX_VALUE));
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif: " + harga);
        }
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }
    public String getNama() {
        return nama;
    }
    public double getHarga() {
        return harga;
    }

    public abstract String getJenisItem();
}
