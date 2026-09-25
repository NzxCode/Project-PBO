package com.bengkel;

/*
 * Child Class: Part (suku cadang)
 */
public class Part extends ItemLayanan {
    private String merk;
    private int stok;

    //Constructor #1
    public Part(String kode, String nama, double harga, String merk, int stok) {
        super(kode, nama, harga);
        setMerk(merk);
        setStok(stok);
    }

    //Constructor #2: ask user to input each attribute value
    public Part() {
        super(); //input atribut milik ItemLayanan
        setMerk(Input.bacaString("Merk = "));
        setStok(Input.bacaInt("Stok = ", 0, Integer.MAX_VALUE));
    }

    //setters and getters
    public void setMerk(String merk) {
        this.merk = merk;
    }
    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif: " + stok);
        }
        this.stok = stok;
    }
    public String getMerk() {
        return merk;
    }
    public int getStok() {
        return stok;
    }

    //Kurangi stok saat part dipakai di transaksi
    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        if (jumlah > stok) {
            throw new IllegalArgumentException("Stok " + getNama() + " tidak cukup (sisa " + stok + ")");
        }
        stok = stok - jumlah;
    }

    /*
     * method overriding
     * @overriding getJenisItem dari parent class "ItemLayanan"
     */
    @Override
    public String getJenisItem() {
        return "Part";
    }
}
