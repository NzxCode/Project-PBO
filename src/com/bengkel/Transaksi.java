package com.bengkel;

import java.util.ArrayList;
import java.util.List;

public class Transaksi implements Cetak {
    private String noPKB;
    private String tanggalWaktu;
    private int kmSaatIni;
    private Kendaraan kendaraan;
    private Customer customer;
    private Mekanik mekanik;
    private String saranPerbaikan;
    private String garansi;
    
    private List<ItemLayanan> daftarItemLayanan; 
    
    private double totalJasa;
    private double totalPart;
    private double grandTotal;

    public Transaksi(String noPKB, String tanggalWaktu, int kmSaatIni, Kendaraan kendaraan, Customer customer, Mekanik mekanik) {
        this.noPKB = noPKB;
        this.tanggalWaktu = tanggalWaktu;
        this.kmSaatIni = kmSaatIni;
        this.kendaraan = kendaraan;
        this.customer = customer;
        this.mekanik = mekanik;
        this.daftarItemLayanan = new ArrayList<>();
        this.saranPerbaikan = "-";
        this.garansi = "-";
    }

    public Transaksi() {
        this.daftarItemLayanan = new ArrayList<>();
    }

    public void tambahItem(ItemLayanan item) {
        this.daftarItemLayanan.add(item);
    }

    public double hitungTotal() {
        this.totalJasa = 0;
        this.totalPart = 0;

        for (ItemLayanan item : daftarItemLayanan) {
            if (item instanceof Jasa) {
                this.totalJasa += item.getHarga();
            } else if (item instanceof Part) {
                this.totalPart += item.getHarga();
            }
        }
        this.grandTotal = this.totalJasa + this.totalPart;
        return this.grandTotal;
    }

    @Override
    public void struk() {
        hitungTotal(); 
        System.out.println("\n==============================================");
        System.out.println("              STRUK BENGKEL MOTOR             ");
        System.out.println("==============================================");
        System.out.println("No PKB      : " + noPKB);
        System.out.println("Tanggal     : " + tanggalWaktu);
        System.out.println("Pelanggan   : " + customer.getNama() + " (ID: " + customer.getNoCustomer() + ")");
        System.out.println("Mekanik     : " + mekanik.getNama() + " (Keahlian: " + mekanik.getSpesialisasi() + ")");
        System.out.println("Kendaraan   : " + kendaraan.getNoPolisi() + " - " + kendaraan.getJenisKendaraan());
        System.out.println("KM Masuk    : " + kmSaatIni);
        System.out.println("----------------------------------------------");
        System.out.println("Detail Layanan & Suku Cadang:");
        
        for (ItemLayanan item : daftarItemLayanan) {
            System.out.printf("[%s] %-20s Rp %,10.2f\n", item.getJenisItem(), item.getNama(), item.getHarga());
        }
        
        System.out.println("----------------------------------------------");
        System.out.printf("Total Jasa  : Rp %,10.2f\n", totalJasa);
        System.out.printf("Total Part  : Rp %,10.2f\n", totalPart);
        System.out.println("==============================================");
        System.out.printf("GRAND TOTAL : Rp %,10.2f\n", grandTotal);
        System.out.println("Saran       : " + saranPerbaikan);
        System.out.println("Garansi     : " + garansi);
        System.out.println("==============================================\n");
    }

    public void setNoPKB(String noPKB) { this.noPKB = noPKB; }
    public void setTanggalWaktu(String tanggalWaktu) { this.tanggalWaktu = tanggalWaktu; }
    public void setKmSaatIni(int kmSaatIni) { this.kmSaatIni = kmSaatIni; }
    public void setKendaraan(Kendaraan kendaraan) { this.kendaraan = kendaraan; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setMekanik(Mekanik mekanik) { this.mekanik = mekanik; }
    public void setSaranPerbaikan(String saranPerbaikan) { this.saranPerbaikan = saranPerbaikan; }
    public void setGaransi(String garansi) { this.garansi = garansi; }
    
    public String getNoPKB() { return noPKB; }
    public String getTanggalWaktu() { return tanggalWaktu; }
    public int getKmSaatIni() { return kmSaatIni; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Customer getCustomer() { return customer; }
    public Mekanik getMekanik() { return mekanik; }
    public String getSaranPerbaikan() { return saranPerbaikan; }
    public String getGaransi() { return garansi; }
    public double getTotalJasa() { return totalJasa; }
    public double getTotalPart() { return totalPart; }
    public double getGrandTotal() { return grandTotal; }
}