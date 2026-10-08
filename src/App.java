// Arsitektur: Main Entry Point & Controller 
// Class App bertindak sebagai konduktor/pusat kendali MVC (walaupun dalam format CLI). 
// Mengelola integrasi UI (teks), interaksi User Input, state ArrayList memory, dan layer database DAO (DataFile).

import java.util.ArrayList;
import java.util.Locale;

// Meng-import semua rancangan Class Model kita.
import com.bengkel.Cetak;
import com.bengkel.Customer;
import com.bengkel.DataFile;
import com.bengkel.Input;
import com.bengkel.ItemLayanan;
import com.bengkel.Jasa;
import com.bengkel.Kendaraan;
import com.bengkel.Mekanik;
import com.bengkel.Motor;
import com.bengkel.Part;
import com.bengkel.Transaksi;

public class App {
    // Variabel Environment untuk men-setting standar penulisan mata uang ke ID (Rupiah).
    private static final Locale ID = Locale.forLanguageTag("id-ID");

    // Deklarasi In-Memory Data Storage menggunakan arsitektur Collection Framework (ArrayList).
    // Variabel list ini bersifat static karena hanya butuh 1 database simulasi untuk seluruh sistem.
    private static ArrayList<Kendaraan> listKendaraan;
    private static ArrayList<Customer> listCustomer;
    private static ArrayList<Mekanik> listMekanik;
    private static ArrayList<ItemLayanan> listItem;
    private static ArrayList<Transaksi> listTransaksi;

    // Signature method utama: 'throws Exception' digunakan untuk membypass handle error 
    // file sistem (I/O) ke layer Java Virtual Machine (JVM) seandainya ada crash tak terduga.
    public static void main(String[] args) throws Exception {
        System.out.println("=== SISTEM BENGKEL MOTOR ===");

        // Fase 1: BOOTING & DESERIALIZATION (Mapping dari Txt jadi Object)
        // Load data Master yang berdiri sendiri (Independent class).
        listKendaraan = DataFile.bacaKendaraan();
        listCustomer = DataFile.bacaCustomer();
        listMekanik = DataFile.bacaMekanik();
        listItem = DataFile.bacaItem();
        
        // Load data Relasi Agregat diurutan Paling Akhir.
        // Kenapa terakhir? Karena bacaTransaksi butuh alamat-alamat memory (pointer referensi) 
        // milik entitas Customer/Kendaraan/Mekanik untuk direlasikan ke dalam transaksinya.
        listTransaksi = DataFile.bacaTransaksi(listKendaraan, listCustomer, listMekanik, listItem);
        
        // Cetak feedback status sinkronisasi ke user interface
        System.out.println("Data dimuat: " + listKendaraan.size() + " kendaraan, "
                + listCustomer.size() + " customer, " + listMekanik.size() + " mekanik, "
                + listItem.size() + " item, " + listTransaksi.size() + " transaksi.");

        // Fase 2: STATE LOOPING & ROUTING
        int pilih;
        do {
            tampilMenu(); 
            // Validasi di handle class helper sehingga bebas dari aplikasi meledak.
            pilih = Input.bacaInt("Pilih menu = ", 0, 8); 
            
            // Konsep Routing: Switch Case mendistribusikan action pengguna ke method/modul yang sesuai.
            switch (pilih) {
                case 1: tambahMotor(); break;
                case 2: tambahCustomer(); break;
                case 3: tambahMekanik(); break;
                case 4: tambahPart(); break;
                case 5: tambahJasa(); break;
                case 6: buatTransaksi(); break;
                case 7: tampilSemuaData(); break;
                case 8: cetakStruk(); break;
                default: break; // Menjaga rotasi idle
            }
        } while (pilih != 0); // Trigger angka 0 untuk keluar

        // Fase 3: SHUT DOWN
        System.out.println("Semua data sudah tersimpan di folder data. Sampai jumpa!");
    }

    // Method private untuk rendering UI layout.
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

    /* ================= Handler Pengisian Data Master Berbasis Interaktif Console ================= */

    private static void tambahMotor() {
        System.out.println("\n--- Input Data Motor ---");
        // Pemanggilan 'new Motor()' tanpa parameter ini yang secara otomatis men-trigger constructor 
        // superclass Kendaraan untuk bertanya input noPolisi, warna, tahun, dsb di terminal.
        Motor motor = new Motor();
        
        // Simulasi Unique Constraint Database
        // Menggunakan helper DataFile untuk mengecek duplikasi Primary Key (No Polisi).
        if (DataFile.cariKendaraan(listKendaraan, motor.getNoPolisi()) != null) {
            System.out.println("Gagal: No Polisi " + motor.getNoPolisi() + " sudah terdaftar.");
            return; // Gagalkan scope simpan (early return).
        }
        
        listKendaraan.add(motor); // Inject object ke local RAM
        DataFile.simpanKendaraan(listKendaraan); // Sinkronisasi otomatis RAM dengan TXT Disk (Reactive save).
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
        // Konsep Polymorphism Upcasting (Dari Child Part naik ke referensi Superclass ItemLayanan).
        ItemLayanan part = new Part(); 
        
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
        // Polymorphism Upcasting.
        ItemLayanan jasa = new Jasa(); 
        
        if (DataFile.cariItem(listItem, jasa.getKode()) != null) {
            System.out.println("Gagal: Kode " + jasa.getKode() + " sudah terdaftar.");
            return;
        }
        listItem.add(jasa);
        DataFile.simpanItem(listItem);
        System.out.println("Jasa berhasil disimpan.");
    }

    /* ================= Logika Utama Algoritma Modul Kasir & Transaksi ================= */

    private static void buatTransaksi() {
        // Validation Constraint: 
        // Mengamankan logika mesin kasir agar tidak crash karena beroperasi pada array kosong.
        if (listKendaraan.isEmpty() || listCustomer.isEmpty() || listMekanik.isEmpty() || listItem.isEmpty()) {
            System.out.println("Data kendaraan, customer, mekanik, dan item harus diisi dulu.");
            return; 
        }

        System.out.println("\n--- Transaksi (PKB) Baru ---");
        // Instansiasi awal rangka body struk
        Transaksi transaksi = new Transaksi(); 
        
        if (DataFile.cariTransaksi(listTransaksi, transaksi.getNoPKB()) != null) {
            System.out.println("Gagal: No PKB " + transaksi.getNoPKB() + " sudah ada.");
            return;
        }

        // --- Step 1: Mapping Association / Relational Selection ---
        // Memberikan list ke user dan mengambil instance class spesifik berdasarkan indeks yang dipilih.
        
        // Select Kendaraan...
        System.out.println("\nPilih Kendaraan:");
        for (int i = 0; i < listKendaraan.size(); i++) {
            Kendaraan k = listKendaraan.get(i);
            System.out.println((i + 1) + ". " + k.getNoPolisi() + " - " + k.getMerk() + " "
                    + k.getTipe() + " [" + k.getJenisKendaraan() + "]");
        }
        Kendaraan kendaraan = listKendaraan.get(Input.bacaInt("Pilih nomor = ", 1, listKendaraan.size()) - 1);

        // Select Customer...
        System.out.println("\nPilih Customer:");
        for (int i = 0; i < listCustomer.size(); i++) {
            Customer c = listCustomer.get(i);
            System.out.println((i + 1) + ". " + c.getNama() + " (" + c.getNoCustomer() + ") - " + c.getPeran());
        }
        Customer customer = listCustomer.get(Input.bacaInt("Pilih nomor = ", 1, listCustomer.size()) - 1);

        // Select Mekanik...
        System.out.println("\nPilih Mekanik:");
        for (int i = 0; i < listMekanik.size(); i++) {
            Mekanik m = listMekanik.get(i);
            System.out.println((i + 1) + ". " + m.getNama() + " (" + m.getIdMekanik() + ") - " + m.getSpesialisasi());
        }
        Mekanik mekanik = listMekanik.get(Input.bacaInt("Pilih nomor = ", 1, listMekanik.size()) - 1);

        // Konsep: Dependency Injection Manual (Lewat Setter)
        // Kita tidak membuat objek baru, melainkan menyematkan pointer memory objek-objek di atas ke setter transaksi.
        transaksi.setKendaraan(kendaraan);
        transaksi.setCustomer(customer);
        transaksi.setMekanik(mekanik);

        // --- Step 2: Aggregation Loop (Shopping Cart System) ---
        String lagi;
        do {
            tampilItem();
            String kode = Input.bacaString("Kode item = ");
            // Grab master item original.
            ItemLayanan item = DataFile.cariItem(listItem, kode);
            
            if (item == null) {
                System.out.println("Item dengan kode " + kode + " tidak ditemukan.");
            } else {
                // Trap Error Khusus pengurangan Stok
                try {
                    // Konsep PBO Lanjut: Runtime Downcasting dan 'instanceof' Type Checking.
                    // Pointer 'item' saat ini bertipe parent class ItemLayanan. Parent Class tidak kenal apa itu 'stok'.
                    // Oleh karena itu, kita paksa JVM mencek wujud aslinya via 'instanceof Part'.
                    if (item instanceof Part) {
                        // Jika benar, paksa referensi ItemLayanan berubah bentuk cast jadi child (Part) agar
                        // metode spesifik kurangiStok() bisa diakses.
                        ((Part) item).kurangiStok(1); 
                    }
                    // Lempar object Part atau Jasa masuk ke list keranjang Transaksi (Polimorfisme lagi).
                    transaksi.addItem(item);
                    System.out.println("Ditambahkan: " + item.getNama());
                    
                } catch (IllegalArgumentException e) {
                    // Menangkap error jika logic kurangiStok mendeteksi gudang sudah habis
                    System.out.println("Gagal menambah item: " + e.getMessage());
                }
            }
            lagi = Input.bacaString("Tambah item lagi? (y/n) = ");
        } while (lagi.equalsIgnoreCase("y"));

        // Step 3: Guard Validation (Tidak boleh ada struk nota tanpa barang)
        if (transaksi.getListItem().isEmpty()) {
            System.out.println("Transaksi dibatalkan: minimal harus ada 1 item.");
            return;
        }

        // Fitur Sinkronisasi Object Memory: Update Odometer Master.
        // Jika pelanggan mengetik nilai KM yang lebih besar dari data lawas sistem, mutasi properti master kendaraannya.
        if (transaksi.getKmSaatIni() > kendaraan.getKm()) {
            kendaraan.setKm(transaksi.getKmSaatIni());
        }

        // Step 4: System State Synchronization (Penyimpanan ke disk fisik)
        listTransaksi.add(transaksi);
        DataFile.simpanTransaksi(listTransaksi); 
        // Mengapa memanggil fungsi simpan Item/Kendaraan? 
        // Karena atribut "Stok" barang Part dan "KM" master kendaraan baru saja dirubah nilainya di memory.
        DataFile.simpanItem(listItem);           
        DataFile.simpanKendaraan(listKendaraan); 
        
        System.out.println("\nTransaksi berhasil disimpan.\n");

        // Konsep: Polymorphism lewat Interface
        // Kita membuat variabel referensi dengan tipe *Interface Cetak*. Namun, 
        // object di dalamnya adalah bentuk riil class Transaksi yang sudah meng-implements struk().
        Cetak cetak = transaksi; 
        cetak.struk(); 
    }

    /* ================= Utility Rendering Data Terminal ================= */

    // Print tabel katalog item dengan Downcasting
    private static void tampilItem() {
        System.out.println("\nDaftar Item:");
        for (ItemLayanan item : listItem) {
            String info = "";
            // JVM Type identification untuk mencomot properti child eksklusif.
            if (item instanceof Part) {
                info = "stok " + ((Part) item).getStok(); // Force cast ke Part
            } else if (item instanceof Jasa) {
                info = ((Jasa) item).getEstimasiWaktu() + " menit"; // Force cast ke Jasa
            }
            System.out.println(" " + item.getKode() + " | " + item.getJenisItem() + " | " + item.getNama()
                    + " | Rp " + String.format(ID, "%,.0f", item.getHarga()) + " | " + info);
        }
    }

    // Report Raw memory dump (Fitur adminisator sistem)
    private static void tampilSemuaData() {
        System.out.println("\n===== KENDARAAN =====");
        for (Kendaraan k : listKendaraan) {
            System.out.println(k.getNoPolisi() + " | " + k.getMerk() + " " + k.getTipe() + " (" + k.getTahun()
                    + ") | " + k.getWarna() + " | " + String.format(ID, "%,d", k.getKm()) + " km | "
                    + k.getJenisKendaraan()); // Memanggil polymorphism jenis (Motor, Mobil, dll)
            
            // Downcasting agar bisa nembus ke properti Motor
            if (k instanceof Motor) {
                System.out.println("    Jenis motor = " + ((Motor) k).getJenisMotor()
                        + ", No Rangka = " + k.getNoRangka() + ", No Mesin = " + k.getNoMesin());
            }
        }

        System.out.println("\n===== CUSTOMER =====");
        for (Customer c : listCustomer) {
            System.out.println(c.getNoCustomer() + " | " + c.getNama() + " | " + c.getNoHP()
                    + " | " + c.getEmail() + " | " + c.getAlamat() + " | " + c.getPeran()); // getPeran() dari Person overrides
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
                Part p = (Part) item; // Eksekusi downcast ke variabel sementara (p)
                System.out.println("    Merk = " + p.getMerk() + ", Stok = " + p.getStok());
            } else if (item instanceof Jasa) {
                Jasa j = (Jasa) item; // Eksekusi downcast ke variabel sementara (j)
                System.out.println("    Kategori = " + j.getKategori() + ", Estimasi = " + j.getEstimasiWaktu() + " menit");
            }
        }

        System.out.println("\n===== TRANSAKSI =====");
        for (Transaksi t : listTransaksi) {
            // Polymorphism Call method hitungTotal(). Biar yang jalan logic pembukuan gabungan dari child Part & Jasa.
            System.out.println(t.getNoPKB() + " | " + t.getTanggalWaktu() + " | " + t.getKendaraan().getNoPolisi()
                    + " | " + t.getCustomer().getNama() + " | Grand Total Rp "
                    + String.format(ID, "%,.0f", t.hitungTotal()));
        }
    }

    // Fitur Reprint struk nota lawas.
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
        
        // Polymorphic Assignment: "Programming towards interface, not implementation".
        // Memakai wadah tipe <Cetak> membuktikan bahwa object ini 100% patuh pada aturan cetak struk nota.
        Cetak cetak = listTransaksi.get(no - 1); 
        cetak.struk();
    }
}