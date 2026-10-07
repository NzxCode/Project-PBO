package com.bengkel;

public class Part extends ItemLayanan {
    private String merk;
    private int stok;

    public Part(String kode, String nama, double harga, String merk, int stok) {
        super(kode, nama, harga);
        this.merk = merk;
        this.stok = stok;
    }

    public Part() { super(); }

    public void setMerk(String merk) { this.merk = merk; }
    public void setStok(int stok) { this.stok = stok; }

    public String getMerk() { return merk; }
    public int getStok() { return stok; }
    
    public void kurangStok(int jumlah) { 
        if (this.stok >= jumlah) this.stok -= jumlah; 
    }

    @Override
    public String getJenisItem() { return "PART"; }
}