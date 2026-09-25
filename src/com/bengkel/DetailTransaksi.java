package com.bengkel;

public class DetailTransaksi {
    private ItemLayanan item;
    private int qty;
    private double hargaSatuan;
    private double diskonPersen;
    private double subtotal;

    public DetailTransaksi(ItemLayanan item, int qty, double hargaSatuan, double diskonPersen) {
        setItem(item);
        setQty(qty);
        setHargaSatuan(hargaSatuan);
        setDiskonPersen(diskonPersen);
    }

    public DetailTransaksi() {
        System.out.println("Jenis item: 1 = Part, 2 = Jasa");
        int pilih = Input.bacaInt("Pilih = ", 1, 2);
        if (pilih == 1) {
            setItem(new Part());
        } else {
            setItem(new Jasa());
        }
        setQty(Input.bacaInt("Qty = ", 1, 9999));
        setHargaSatuan(item.getHarga()); 
        setDiskonPersen(Input.bacaDouble("Diskon (%) = ", 0, 100));
    }
    public void setItem(ItemLayanan item) {
        this.item = item;
    }
    public void setQty(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Qty harus lebih dari 0");
        }
        this.qty = qty;
        hitungSubtotal();
    }
    public void setHargaSatuan(double hargaSatuan) {
        if (hargaSatuan < 0) {
            throw new IllegalArgumentException("Harga satuan tidak boleh negatif");
        }
        this.hargaSatuan = hargaSatuan;
        hitungSubtotal();
    }
    public void setDiskonPersen(double diskonPersen) {
        if (diskonPersen < 0 || diskonPersen > 100) {
            throw new IllegalArgumentException("Diskon harus antara 0 - 100 persen");
        }
        this.diskonPersen = diskonPersen;
        hitungSubtotal();
    }
    public ItemLayanan getItem() {
        return item;
    }
    public int getQty() {
        return qty;
    }
    public double getHargaSatuan() {
        return hargaSatuan;
    }
    public double getDiskonPersen() {
        return diskonPersen;
    }
    public double getSubtotal() {
        return subtotal;
    }

    public double hitungSubtotal() {
        subtotal = qty * hargaSatuan * (1 - diskonPersen / 100);
        return subtotal;
    }
}
