/*
 * Main Program: Sistem Bengkel Motor (CLI)
 * Semua data dibaca dari file saat program mulai, dan disimpan ke file
 * setiap ada perubahan (folder "data").
 */
import java.util.ArrayList;
import java.util.Locale;

import com.bengkel.Cetak;
import com.bengkel.Customer;
import com.bengkel.DataFile;
import com.bengkel.DetailTransaksi;
import com.bengkel.Input;
import com.bengkel.ItemLayanan;
import com.bengkel.Jasa;
import com.bengkel.Kendaraan;
import com.bengkel.Mekanik;
import com.bengkel.Motor;
import com.bengkel.Part;
import com.bengkel.Transaksi;

public class App {
    private static final Locale ID = Locale.forLanguageTag("id-ID");

    private static ArrayList<Kendaraan> listKendaraan;
    private static ArrayList<Customer> listCustomer;
    private static ArrayList<Mekanik> listMekanik;
    private static ArrayList<ItemLayanan> listItem;
    private static ArrayList<Transaksi> listTransaksi;

    public static void main(String[] args) throws Exception {
        System.out.println("=== SISTEM BENGKEL MOTOR ===");

        //Baca semua object dari file (urutan penting: transaksi butuh data lainnya)
        listKendaraan = DataFile.bacaKendaraan();
        listCustomer = DataFile.bacaCustomer();
        listMekanik = DataFile.bacaMekanik();
        listItem = DataFile.bacaItem();
        listTransaksi = DataFile.bacaTransaksi(listKendaraan, listCustomer, listMekanik, listItem);
        System.out.println("Data dimuat: " + listKendaraan.size() + " kendaraan, "
                + listCustomer.size() + " customer, " + listMekanik.size() + " mekanik, "
                + listItem.size() + " item, " + listTransaksi.size() + " transaksi.");

        int pilih;
        do {
            tampilMenu();
            pilih = Input.bacaInt("Pilih menu = ", 0, 8);
            switch (pilih) {
                case 1: tambahMotor(); break;
                case 2: tambahCustomer(); break;
                case 3: tambahMekanik(); break;
                case 4: tambahPart(); break;
                case 5: tambahJasa(); break;
                case 6: buatTransaksi(); break;
                case 7: tampilSemuaData(); break;
                case 8: cetakStruk(); break;
                default: break;
            }
        } while (pilih != 0);

        System.out.println("Semua data sudah tersimpan di folder data. Sampai jumpa!");
    }

    private static void tampilMenu() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1. Tambah Motor");
        System.out.println("2. Tambah Customer");
        System.out.println("3. Tambah Mekanik");
        System.out.println("4. Tambah Part");
        System.out.println("5. Tambah Jasa");
        System.out.println("6. Buat Transaksi (PKB) baru");
        System.out.println("7. Tampilkan semua data");
        System.out.println("8. Cetak struk transaksi");
        System.out.println("0. Keluar");
    }

    /* ================= Tambah data (Constructor #2: input user) ================= */

    private static void tambahMotor() {
        System.out.println("\n--- Input Data Motor ---");
        Motor motor = new Motor();
        if (DataFile.cariKendaraan(listKendaraan, motor.getNoPolisi()) != null) {
            System.out.println("Gagal: No Polisi " + motor.getNoPolisi() + " sudah terdaftar.");
            return;
        }
        listKendaraan.add(motor);
        DataFile.simpanKendaraan(listKendaraan);
        System.out.println("Motor berhasil disimpan.");
    }

    private static void tambahCustomer() {
        System.out.println("\n--- Input Data Customer ---");
        Customer customer = new Customer();
        if (DataFile.cariCustomer(listCustomer, customer.getNoCustomer()) != null) {
            System.out.println("Gagal: No Customer " + customer.getNoCustomer() + " sudah terdaftar.");
            return;
        }
        listCustomer.add(customer);
        DataFile.simpanCustomer(listCustomer);
        System.out.println("Customer berhasil disimpan.");
    }

    private static void tambahMekanik() {
        System.out.println("\n--- Input Data Mekanik ---");
        Mekanik mekanik = new Mekanik();
        if (DataFile.cariMekanik(listMekanik, mekanik.getIdMekanik()) != null) {
            System.out.println("Gagal: ID Mekanik " + mekanik.getIdMekanik() + " sudah terdaftar.");
            return;
        }
        listMekanik.add(mekanik);
        DataFile.simpanMekanik(listMekanik);
        System.out.println("Mekanik berhasil disimpan.");
    }

    private static void tambahPart() {
        System.out.println("\n--- Input Data Part ---");
        ItemLayanan part = new Part(); //polymorphic variable
        if (DataFile.cariItem(listItem, part.getKode()) != null) {
            System.out.println("Gagal: Kode " + part.getKode() + " sudah terdaftar.");
            return;
        }
        listItem.add(part);
        DataFile.simpanItem(listItem);
        System.out.println("Part berhasil disimpan.");
    }

    private static void tambahJasa() {
        System.out.println("\n--- Input Data Jasa ---");
        ItemLayanan jasa = new Jasa(); //polymorphic variable
        if (DataFile.cariItem(listItem, jasa.getKode()) != null) {
            System.out.println("Gagal: Kode " + jasa.getKode() + " sudah terdaftar.");
            return;
        }
        listItem.add(jasa);
        DataFile.simpanItem(listItem);
        System.out.println("Jasa berhasil disimpan.");
    }

    /* ================= Transaksi ================= */

    private static void buatTransaksi() {
        if (listKendaraan.isEmpty() || listCustomer.isEmpty() || listMekanik.isEmpty() || listItem.isEmpty()) {
            System.out.println("Data kendaraan, customer, mekanik, dan item harus diisi dulu.");
            return;
        }

        System.out.println("\n--- Transaksi (PKB) Baru ---");
        Transaksi transaksi = new Transaksi(); //input No PKB, waktu, KM, saran, garansi
        if (DataFile.cariTransaksi(listTransaksi, transaksi.getNoPKB()) != null) {
            System.out.println("Gagal: No PKB " + transaksi.getNoPKB() + " sudah ada.");
            return;
        }

        //Pilih kendaraan, customer, dan mekanik dari data yang sudah ada
        System.out.println("\nPilih Kendaraan:");
        for (int i = 0; i < listKendaraan.size(); i++) {
            Kendaraan k = listKendaraan.get(i);
            System.out.println((i + 1) + ". " + k.getNoPolisi() + " - " + k.getMerk() + " "
                    + k.getTipe() + " [" + k.getJenisKendaraan() + "]");
        }
        Kendaraan kendaraan = listKendaraan.get(Input.bacaInt("Pilih nomor = ", 1, listKendaraan.size()) - 1);

        System.out.println("\nPilih Customer:");
        for (int i = 0; i < listCustomer.size(); i++) {
            Customer c = listCustomer.get(i);
            System.out.println((i + 1) + ". " + c.getNama() + " (" + c.getNoCustomer() + ") - " + c.getPeran());
        }
        Customer customer = listCustomer.get(Input.bacaInt("Pilih nomor = ", 1, listCustomer.size()) - 1);

        System.out.println("\nPilih Mekanik:");
        for (int i = 0; i < listMekanik.size(); i++) {
            Mekanik m = listMekanik.get(i);
            System.out.println((i + 1) + ". " + m.getNama() + " (" + m.getIdMekanik() + ") - " + m.getSpesialisasi());
        }
        Mekanik mekanik = listMekanik.get(Input.bacaInt("Pilih nomor = ", 1, listMekanik.size()) - 1);

        transaksi.setKendaraan(kendaraan);
        transaksi.setCustomer(customer);
        transaksi.setMekanik(mekanik);

        //Tambah item (Part / Jasa) ke transaksi
        String lagi;
        do {
            tampilItem();
            String kode = Input.bacaString("Kode item = ");
            ItemLayanan item = DataFile.cariItem(listItem, kode);
            if (item == null) {
                System.out.println("Item dengan kode " + kode + " tidak ditemukan.");
            } else {
                int qty = Input.bacaInt("Qty = ", 1, 9999);
                double diskon = Input.bacaDouble("Diskon (%) = ", 0, 100);
                try {
                    DetailTransaksi detail = new DetailTransaksi(item, qty, item.getHarga(), diskon);
                    if (item instanceof Part) {
                        ((Part) item).kurangiStok(qty); //downcasting
                    }
                    transaksi.addDetail(detail);
                    System.out.println("Ditambahkan: " + item.getNama() + " x" + qty);
                } catch (IllegalArgumentException e) {
                    System.out.println("Gagal menambah item: " + e.getMessage());
                }
            }
            lagi = Input.bacaString("Tambah item lagi? (y/n) = ");
        } while (lagi.equalsIgnoreCase("y"));

        if (transaksi.getListDetail().isEmpty()) {
            System.out.println("Transaksi dibatalkan: minimal harus ada 1 item.");
            return;
        }

        //KM kendaraan ikut diperbarui jika lebih besar
        if (transaksi.getKmSaatIni() > kendaraan.getKm()) {
            kendaraan.setKm(transaksi.getKmSaatIni());
        }

        listTransaksi.add(transaksi);
        DataFile.simpanTransaksi(listTransaksi);
        DataFile.simpanItem(listItem);           //stok part berubah
        DataFile.simpanKendaraan(listKendaraan); //KM kendaraan berubah
        System.out.println("\nTransaksi berhasil disimpan.\n");

        Cetak cetak = transaksi; //polymorphic variable (interface)
        cetak.struk();
    }

    /* ================= Tampil data ================= */

    private static void tampilItem() {
        System.out.println("\nDaftar Item:");
        for (ItemLayanan item : listItem) {
            String info = "";
            if (item instanceof Part) {
                info = "stok " + ((Part) item).getStok();
            } else if (item instanceof Jasa) {
                info = ((Jasa) item).getEstimasiWaktu() + " menit";
            }
            System.out.println(" " + item.getKode() + " | " + item.getJenisItem() + " | " + item.getNama()
                    + " | Rp " + String.format(ID, "%,.0f", item.getHarga()) + " | " + info);
        }
    }

    private static void tampilSemuaData() {
        System.out.println("\n===== KENDARAAN =====");
        for (Kendaraan k : listKendaraan) {
            System.out.println(k.getNoPolisi() + " | " + k.getMerk() + " " + k.getTipe() + " (" + k.getTahun()
                    + ") | " + k.getWarna() + " | " + String.format(ID, "%,d", k.getKm()) + " km | "
                    + k.getJenisKendaraan());
            if (k instanceof Motor) {
                System.out.println("    Jenis motor = " + ((Motor) k).getJenisMotor()
                        + ", No Rangka = " + k.getNoRangka() + ", No Mesin = " + k.getNoMesin());
            }
        }

        System.out.println("\n===== CUSTOMER =====");
        for (Customer c : listCustomer) {
            System.out.println(c.getNoCustomer() + " | " + c.getNama() + " | " + c.getNoHP()
                    + " | " + c.getEmail() + " | " + c.getAlamat() + " | " + c.getPeran());
        }

        System.out.println("\n===== MEKANIK =====");
        for (Mekanik m : listMekanik) {
            System.out.println(m.getIdMekanik() + " | " + m.getNama() + " | " + m.getNoHP()
                    + " | " + m.getSpesialisasi() + " | " + m.getPeran());
        }

        System.out.println("\n===== ITEM (PART & JASA) =====");
        for (ItemLayanan item : listItem) {
            System.out.println(item.getKode() + " | " + item.getJenisItem() + " | " + item.getNama()
                    + " | Rp " + String.format(ID, "%,.0f", item.getHarga()));
            if (item instanceof Part) {
                Part p = (Part) item;
                System.out.println("    Merk = " + p.getMerk() + ", Stok = " + p.getStok());
            } else if (item instanceof Jasa) {
                Jasa j = (Jasa) item;
                System.out.println("    Kategori = " + j.getKategori() + ", Estimasi = " + j.getEstimasiWaktu() + " menit");
            }
        }

        System.out.println("\n===== TRANSAKSI =====");
        for (Transaksi t : listTransaksi) {
            System.out.println(t.getNoPKB() + " | " + t.getTanggalWaktu() + " | " + t.getKendaraan().getNoPolisi()
                    + " | " + t.getCustomer().getNama() + " | Grand Total Rp "
                    + String.format(ID, "%,.0f", t.hitungTotal()));
        }
    }

    private static void cetakStruk() {
        if (listTransaksi.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        System.out.println("\nDaftar Transaksi:");
        for (int i = 0; i < listTransaksi.size(); i++) {
            Transaksi t = listTransaksi.get(i);
            System.out.println((i + 1) + ". " + t.getNoPKB() + " | " + t.getTanggalWaktu()
                    + " | " + t.getCustomer().getNama());
        }
        int no = Input.bacaInt("Pilih nomor = ", 1, listTransaksi.size());
        Cetak cetak = listTransaksi.get(no - 1); //polymorphic variable (interface)
        cetak.struk();
    }
}