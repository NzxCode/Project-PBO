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

    public ItemLayanan() {}

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = Validasi.wajibIsi(kode, "Kode"); }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = Validasi.wajibIsi(nama, "Nama item"); }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = Validasi.tidakNegatif(harga, "Harga"); }

    public abstract String getJenisItem();
}
