// Main Program: Sistem Bengkel Motor (Berbasis CLI / Command Line Interface)
// Aplikasi dirancang untuk me-load file teks saat booting, dan menyimpan ulang (save) tiap kali ada update data.

// Mengimport ArrayList untuk koleksi data dan Locale untuk formatting string
import java.util.ArrayList;
import java.util.Locale;

// Mengimport semua komponen class model (blueprint object)
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

// Entry point class aplikasi
public class App {
    // Menyetel Locale IDN untuk seluruh output di main application
    private static final Locale ID = Locale.forLanguageTag("id-ID");

    // Deklarasi kumpulan ArrayList statis sebagai database simulasi (in-memory list)
    private static ArrayList<Kendaraan> listKendaraan;
    private static ArrayList<Customer> listCustomer;
    private static ArrayList<Mekanik> listMekanik;
    private static ArrayList<ItemLayanan> listItem;
    private static ArrayList<Transaksi> listTransaksi;

    // Main method (pusat eksekusi aplikasi), men-throws Exception jika ada masalah I/O file text
    public static void main(String[] args) throws Exception {
        System.out.println("=== SISTEM BENGKEL MOTOR ===");

        // Memanggil helper static dari DataFile untuk membaca seluruh file txt (Deserialize)
        listKendaraan = DataFile.bacaKendaraan();
        listCustomer = DataFile.bacaCustomer();
        listMekanik = DataFile.bacaMekanik();
        listItem = DataFile.bacaItem();
        // Membaca transaksi dieksekusi terakhir karena file ini me-referensi object kendaraan/customer/mekanik/item
        listTransaksi = DataFile.bacaTransaksi(listKendaraan, listCustomer, listMekanik, listItem);
        
        // Feedback jumlah data yang berhasil di-load ke dalam memory
        System.out.println("Data dimuat: " + listKendaraan.size() + " kendaraan, "
                + listCustomer.size() + " customer, " + listMekanik.size() + " mekanik, "
                + listItem.size() + " item, " + listTransaksi.size() + " transaksi.");

        int pilih;
        // Do-while loop untuk menjalankan User Interface menu selama nilai 'pilih' bukan 0
        do {
            tampilMenu(); // Memanggil method print menu
            pilih = Input.bacaInt("Pilih menu = ", 0, 8); // Validasi input harus angka 0 s/d 8
            
            // Switch case (router) untuk memanggil method sesuai pilihan menu user
            switch (pilih) {
                case 1: tambahMotor(); break;
                case 2: tambahCustomer(); break;
                case 3: tambahMekanik(); break;
                case 4: tambahPart(); break;
                case 5: tambahJasa(); break;
                case 6: buatTransaksi(); break;
                case 7: tampilSemuaData(); break;
                case 8: cetakStruk(); break;
                default: break; // Kembali looping jika di luar kondisi
            }
        } while (pilih != 0);

        System.out.println("Semua data sudah tersimpan di folder data. Sampai jumpa!");
    }

    // Method private untuk mencetak daftar teks UI konsol menu
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

    /* ================= Bagian Tambah Data (Menggunakan Constructor #2 Interaktif) ================= */

    // Method untuk menambah registrasi motor
    private static void tambahMotor() {
        System.out.println("\n--- Input Data Motor ---");
        // Membuat instance object Motor via user input terminal
        Motor motor = new Motor();
        
        // Pengecekan Primary Key (Plat Polisi) dengan helper pencarian
        if (DataFile.cariKendaraan(listKendaraan, motor.getNoPolisi()) != null) {
            System.out.println("Gagal: No Polisi " + motor.getNoPolisi() + " sudah terdaftar.");
            return; // Batalkan penyimpanan jika bentrok
        }
        
        // Masukkan object ke dalam ArrayList
        listKendaraan.add(motor);
        // Serialize ArrayList secara keseluruhan ke dalam file TXT
        DataFile.simpanKendaraan(listKendaraan);
        System.out.println("Motor berhasil disimpan.");
    }

    // Method untuk menambah registrasi pelanggan
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

    // Method untuk menambah mekanik internal
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

    // Method membuat suku cadang (inventory)
    private static void tambahPart() {
        System.out.println("\n--- Input Data Part ---");
        // Polymorphic variable: referensi ItemLayanan (parent) disematkan object Part (child)
        ItemLayanan part = new Part(); 
        
        if (DataFile.cariItem(listItem, part.getKode()) != null) {
            System.out.println("Gagal: Kode " + part.getKode() + " sudah terdaftar.");
            return;
        }
        listItem.add(part);
        DataFile.simpanItem(listItem);
        System.out.println("Part berhasil disimpan.");
    }

    // Method untuk meregistrasi tarif jasa servis
    private static void tambahJasa() {
        System.out.println("\n--- Input Data Jasa ---");
        // Polymorphic variable untuk jasa layanan bengkel
        ItemLayanan jasa = new Jasa(); 
        
        if (DataFile.cariItem(listItem, jasa.getKode()) != null) {
            System.out.println("Gagal: Kode " + jasa.getKode() + " sudah terdaftar.");
            return;
        }
        listItem.add(jasa);
        DataFile.simpanItem(listItem);
        System.out.println("Jasa berhasil disimpan.");
    }

    /* ================= Modul Kasir Transaksi ================= */

    private static void buatTransaksi() {
        // Validasi dependencies list (semua entitas master harus diisi sebelum transaksi berjalan)
        if (listKendaraan.isEmpty() || listCustomer.isEmpty() || listMekanik.isEmpty() || listItem.isEmpty()) {
            System.out.println("Data kendaraan, customer, mekanik, dan item harus diisi dulu.");
            return;
        }

        System.out.println("\n--- Transaksi (PKB) Baru ---");
        // Menginstansiasi object transaksi baru yang memicu constructor interaktifnya
        Transaksi transaksi = new Transaksi(); 
        
        if (DataFile.cariTransaksi(listTransaksi, transaksi.getNoPKB()) != null) {
            System.out.println("Gagal: No PKB " + transaksi.getNoPKB() + " sudah ada.");
            return;
        }

        // --- Proses Selection Object Berelasi --- //
        
        // Melakukan print isi List Kendaraan
        System.out.println("\nPilih Kendaraan:");
        for (int i = 0; i < listKendaraan.size(); i++) {
            Kendaraan k = listKendaraan.get(i);
            System.out.println((i + 1) + ". " + k.getNoPolisi() + " - " + k.getMerk() + " "
                    + k.getTipe() + " [" + k.getJenisKendaraan() + "]");
        }
        // Mengambil referensi object Kendaraan dari index ArrayList sesuai input user
        Kendaraan kendaraan = listKendaraan.get(Input.bacaInt("Pilih nomor = ", 1, listKendaraan.size()) - 1);

        // Melakukan print isi List Customer
        System.out.println("\nPilih Customer:");
        for (int i = 0; i < listCustomer.size(); i++) {
            Customer c = listCustomer.get(i);
            System.out.println((i + 1) + ". " + c.getNama() + " (" + c.getNoCustomer() + ") - " + c.getPeran());
        }
        Customer customer = listCustomer.get(Input.bacaInt("Pilih nomor = ", 1, listCustomer.size()) - 1);

        // Melakukan print isi List Mekanik
        System.out.println("\nPilih Mekanik:");
        for (int i = 0; i < listMekanik.size(); i++) {
            Mekanik m = listMekanik.get(i);
            System.out.println((i + 1) + ". " + m.getNama() + " (" + m.getIdMekanik() + ") - " + m.getSpesialisasi());
        }
        Mekanik mekanik = listMekanik.get(Input.bacaInt("Pilih nomor = ", 1, listMekanik.size()) - 1);

        // Melakukan inject object-object (dependency injection) ke setter Transaksi 
        transaksi.setKendaraan(kendaraan);
        transaksi.setCustomer(customer);
        transaksi.setMekanik(mekanik);

        // --- Proses Penambahan Item Pembelian (Shopping Cart Loop) --- //
        String lagi;
        do {
            tampilItem(); // Tampilkan katalog
            String kode = Input.bacaString("Kode item = ");
            // Cari object di dalam master data berdasarkan kode yang di-input
            ItemLayanan item = DataFile.cariItem(listItem, kode);
            
            if (item == null) {
                System.out.println("Item dengan kode " + kode + " tidak ditemukan.");
            } else {
                try {
                    // Polymorphism check: jika object ini adalah child class Part
                    if (item instanceof Part) {
                        // Lakukan proses Downcasting untuk memanggil method spesifik kurangiStok()
                        ((Part) item).kurangiStok(1); 
                    }
                    // Tambah object part/jasa ke array dalam transaksi
                    transaksi.addItem(item);
                    System.out.println("Ditambahkan: " + item.getNama());
                } catch (IllegalArgumentException e) {
                    // Penanganan exception jika stok Part sudah habis/negatif
                    System.out.println("Gagal menambah item: " + e.getMessage());
                }
            }
            // Konfirmasi ulang iterasi
            lagi = Input.bacaString("Tambah item lagi? (y/n) = ");
        } while (lagi.equalsIgnoreCase("y"));

        // Pembatalan logika transaksi jika user tidak jadi membeli barang/jasa satupun
        if (transaksi.getListItem().isEmpty()) {
            System.out.println("Transaksi dibatalkan: minimal harus ada 1 item.");
            return;
        }

        // Fitur Sinkronisasi KM: update properti kendaraan master jika input kasir lebih baru
        if (transaksi.getKmSaatIni() > kendaraan.getKm()) {
            kendaraan.setKm(transaksi.getKmSaatIni());
        }

        // Proses Finalisasi
        listTransaksi.add(transaksi);
        DataFile.simpanTransaksi(listTransaksi); // Simpan log PKB
        DataFile.simpanItem(listItem);           // Simpan mutasi stok inventori Part
        DataFile.simpanKendaraan(listKendaraan); // Simpan riwayat update KM terbaru
        System.out.println("\nTransaksi berhasil disimpan.\n");

        // Polymorphic variable: menggunakan tipe data interface untuk me-refer ke object transaksi
        Cetak cetak = transaksi; 
        cetak.struk(); // Jalankan polymorphism cetak
    }

    /* ================= Helper Tampilan Logistik & Reporting ================= */

    // Method menampilkan katalog list dari master ItemLayanan
    private static void tampilItem() {
        System.out.println("\nDaftar Item:");
        for (ItemLayanan item : listItem) {
            String info = "";
            // Check spesialisasi tipe object
            if (item instanceof Part) {
                // Downcasting paksa (Part) untuk mengambil stok
                info = "stok " + ((Part) item).getStok();
            } else if (item instanceof Jasa) {
                // Downcasting paksa (Jasa) untuk mengambil estimasi waktu
                info = ((Jasa) item).getEstimasiWaktu() + " menit";
            }
            System.out.println(" " + item.getKode() + " | " + item.getJenisItem() + " | " + item.getNama()
                    + " | Rp " + String.format(ID, "%,.0f", item.getHarga()) + " | " + info);
        }
    }

    // Method utility administrator (raw dump list memory)
    private static void tampilSemuaData() {
        System.out.println("\n===== KENDARAAN =====");
        for (Kendaraan k : listKendaraan) {
            System.out.println(k.getNoPolisi() + " | " + k.getMerk() + " " + k.getTipe() + " (" + k.getTahun()
                    + ") | " + k.getWarna() + " | " + String.format(ID, "%,d", k.getKm()) + " km | "
                    + k.getJenisKendaraan());
            
            // Lakukan downcasting jika parent Kendaraan diidentifikasi berbentuk Motor
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
                Part p = (Part) item; // Downcasting
                System.out.println("    Merk = " + p.getMerk() + ", Stok = " + p.getStok());
            } else if (item instanceof Jasa) {
                Jasa j = (Jasa) item; // Downcasting
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

    // Method untuk me-reprint history struk yang pernah dibuat
    private static void cetakStruk() {
        // Validasi transaksi kosong
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
        
        // Meminta nomor array transaksi
        int no = Input.bacaInt("Pilih nomor = ", 1, listTransaksi.size());
        
        // Instansiasi variabel bertipe Interface dari ArrayList yang me-return object Transaksi
        Cetak cetak = listTransaksi.get(no - 1); 
        // Lakukan eksekusi overriding fungsi print 
        cetak.struk();
    }
}