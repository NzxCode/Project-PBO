package com.bengkel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

public class App {
    private static final String DIR = "data/";
    private static final String F_CUSTOMER = DIR + "customer.txt";
    private static final String F_MEKANIK = DIR + "mekanik.txt";
    private static final String F_KENDARAAN = DIR + "kendaraan.txt";
    private static final String F_ITEM = DIR + "item.txt";
    private static final String F_TRANSAKSI = DIR + "transaksi.txt";
    private static final String F_DETAIL = DIR + "detail.txt";

    private static final Locale ID = Locale.forLanguageTag("id-ID");

    // Sesuai diagram: Transaksi berelasi dengan Kendaraan (abstract), Customer, Mekanik, ItemLayanan (abstract)
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Mekanik> mekaniks = new ArrayList<>();
    private static final List<Kendaraan> kendaraans = new ArrayList<>();
    private static final List<ItemLayanan> items = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== APLIKASI KASIR " + Cetak.TOKO + " ===");
        System.out.println(Cetak.ALAMAT);
        muatSemua();

        try {
            boolean jalan = true;
            while (jalan) {
                System.out.println();
                System.out.println("1. Transaksi baru");
                System.out.println("2. Tambah customer");
                System.out.println("3. Tambah mekanik");
                System.out.println("4. Tambah motor");
                System.out.println("5. Tambah part");
                System.out.println("6. Tambah jasa");
                System.out.println("7. Lihat data");
                System.out.println("8. Riwayat transaksi / cetak ulang struk");
                System.out.println("0. Keluar");
                int pilih = Input.bacaInt("Pilih menu: ", 0, 8);
                try {
                    switch (pilih) {
                        case 1: transaksiBaru(); break;
                        case 2: tambahCustomer(); break;
                        case 3: tambahMekanik(); break;
                        case 4: tambahMotor(); break;
                        case 5: tambahPart(); break;
                        case 6: tambahJasa(); break;
                        case 7: lihatData(); break;
                        case 8: riwayat(); break;
                        default: jalan = false;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("[!] " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("[!] Gagal mengakses file: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("[!] Input terputus, program ditutup.");
        }
        System.out.println("Terima kasih.");
    }

    // ---------- Util ----------

    /** Membersihkan teks agar aman disimpan di file CSV. */
    private static String b(String s) {
        return DataFile.bersihkan(s);
    }

    /** Memastikan baris file punya cukup kolom sebelum dibaca. */
    private static void cekKolom(String[] k, int minimal) {
        if (k.length < minimal) {
            throw new IllegalArgumentException("Kolom kurang (butuh " + minimal + ", ada " + k.length + ").");
        }
    }

    // ---------- Muat data ----------

    private static void muatSemua() {
        try {
            for (String[] k : DataFile.bacaBaris(F_CUSTOMER)) {
                try {
                    cekKolom(k, 6);
                    if (idPersonDipakai(k[0]) || cariCustomer(k[4]) != null) {
                        throw new IllegalArgumentException("ID/No Customer sudah dipakai.");
                    }
                    customers.add(new Customer(k[0], k[1], k[2], k[3], k[4], k[5]));
                } catch (RuntimeException e) { lewati(F_CUSTOMER, k, e); }
            }
            for (String[] k : DataFile.bacaBaris(F_MEKANIK)) {
                try {
                    cekKolom(k, 6);
                    if (idPersonDipakai(k[0]) || cariMekanik(k[4]) != null) {
                        throw new IllegalArgumentException("ID/ID Mekanik sudah dipakai.");
                    }
                    mekaniks.add(new Mekanik(k[0], k[1], k[2], k[3], k[4], k[5]));
                } catch (RuntimeException e) { lewati(F_MEKANIK, k, e); }
            }
            for (String[] k : DataFile.bacaBaris(F_KENDARAAN)) {
                try {
                    cekKolom(k, 10);
                    if (!k[0].equalsIgnoreCase("Motor")) {
                        throw new IllegalArgumentException("Tipe kendaraan tidak dikenal: " + k[0]);
                    }
                    if (cariKendaraan(k[1]) != null) {
                        throw new IllegalArgumentException("No Polisi sudah terdaftar.");
                    }
                    // Upcasting: Motor disimpan sebagai Kendaraan (polymorphic variable)
                    Kendaraan kd = new Motor(k[1], k[2], k[3], Integer.parseInt(k[4]), k[5],
                            Integer.parseInt(k[6]), k[7], k[8], k[9]);
                    kendaraans.add(kd);
                } catch (RuntimeException e) { lewati(F_KENDARAAN, k, e); }
            }
            for (String[] k : DataFile.bacaBaris(F_ITEM)) {
                try {
                    cekKolom(k, 6);
                    if (cariItem(k[1]) != null) {
                        throw new IllegalArgumentException("Kode item sudah dipakai.");
                    }
                    ItemLayanan item; // Part dan Jasa disimpan sebagai ItemLayanan
                    if (k[0].equalsIgnoreCase("Part")) {
                        item = new Part(k[1], k[2], Double.parseDouble(k[3]), k[4], Integer.parseInt(k[5]));
                    } else if (k[0].equalsIgnoreCase("Jasa")) {
                        item = new Jasa(k[1], k[2], Double.parseDouble(k[3]), k[4], Integer.parseInt(k[5]));
                    } else {
                        throw new IllegalArgumentException("Tipe item tidak dikenal: " + k[0]);
                    }
                    items.add(item);
                } catch (RuntimeException e) { lewati(F_ITEM, k, e); }
            }
        } catch (IOException e) {
            System.out.println("[!] Gagal membaca data: " + e.getMessage());
        }
        System.out.println("Data dimuat: " + customers.size() + " customer, " + mekaniks.size() + " mekanik, "
                + kendaraans.size() + " kendaraan, " + items.size() + " item.");
    }

    private static void lewati(String file, String[] baris, RuntimeException e) {
        System.out.println("[!] Baris dilewati di " + file + " (" + String.join(",", baris) + "): " + e.getMessage());
    }

    // ---------- Simpan data ----------

    private static void simpanCustomer() throws IOException {
        List<String> out = new ArrayList<>();
        for (Customer c : customers) {
            out.add(String.join(",", b(c.getId()), b(c.getNama()), b(c.getNoHP()),
                    b(c.getAlamat()), b(c.getNoCustomer()), b(c.getEmail())));
        }
        DataFile.tulisSemua(F_CUSTOMER, out);
    }

    private static void simpanMekanik() throws IOException {
        List<String> out = new ArrayList<>();
        for (Mekanik m : mekaniks) {
            out.add(String.join(",", b(m.getId()), b(m.getNama()), b(m.getNoHP()),
                    b(m.getAlamat()), b(m.getIdMekanik()), b(m.getSpesialisasi())));
        }
        DataFile.tulisSemua(F_MEKANIK, out);
    }

    private static void simpanKendaraan() throws IOException {
        List<String> out = new ArrayList<>();
        for (Kendaraan kd : kendaraans) {
            if (!(kd instanceof Motor)) continue;
            Motor m = (Motor) kd; // downcasting setelah dicek instanceof
            out.add(String.join(",", "Motor", b(m.getNoPolisi()), b(m.getMerk()),
                    b(m.getTipe()), String.valueOf(m.getTahun()), b(m.getWarna()),
                    String.valueOf(m.getKm()), b(m.getNoRangka()),
                    b(m.getNoMesin()), b(m.getJenisMotor())));
        }
        DataFile.tulisSemua(F_KENDARAAN, out);
    }

    private static void simpanItem() throws IOException {
        List<String> out = new ArrayList<>();
        for (ItemLayanan i : items) {
            if (i instanceof Part) {
                Part p = (Part) i;
                out.add(String.join(",", "Part", b(p.getKode()), b(p.getNama()),
                        String.format(Locale.US, "%.2f", p.getHarga()), b(p.getMerk()),
                        String.valueOf(p.getStok())));
            } else if (i instanceof Jasa) {
                Jasa j = (Jasa) i;
                out.add(String.join(",", "Jasa", b(j.getKode()), b(j.getNama()),
                        String.format(Locale.US, "%.2f", j.getHarga()), b(j.getKategori()),
                        String.valueOf(j.getEstimasiWaktu())));
            }
        }
        DataFile.tulisSemua(F_ITEM, out);
    }

    // ---------- Pencarian ----------

    private static Customer cariCustomer(String noCustomer) {
        for (Customer c : customers) if (c.getNoCustomer().equalsIgnoreCase(noCustomer)) return c;
        return null;
    }

    private static Mekanik cariMekanik(String idMekanik) {
        for (Mekanik m : mekaniks) if (m.getIdMekanik().equalsIgnoreCase(idMekanik)) return m;
        return null;
    }

    private static Kendaraan cariKendaraan(String noPolisi) {
        for (Kendaraan k : kendaraans) if (k.getNoPolisi().equalsIgnoreCase(noPolisi)) return k;
        return null;
    }

    private static ItemLayanan cariItem(String kode) {
        for (ItemLayanan i : items) if (i.getKode().equalsIgnoreCase(kode)) return i;
        return null;
    }

    private static boolean idPersonDipakai(String id) {
        for (Person p : customers) if (p.getId().equalsIgnoreCase(id)) return true;
        for (Person p : mekaniks) if (p.getId().equalsIgnoreCase(id)) return true;
        return false;
    }

    // ---------- Tambah data ----------

    private static void tambahCustomer() throws IOException {
        int n = 1;
        while (idPersonDipakai(String.format("P%03d", n)) || cariCustomer(String.format("C%03d", n)) != null) n++;
        String id = String.format("P%03d", n);
        String noCust = String.format("C%03d", n);
        System.out.println("ID otomatis: " + id + " / No Customer: " + noCust);
        Customer c = new Customer();
        c.setId(id);
        c.setNoCustomer(noCust);
        isi(() -> c.setNama(Input.bacaString("Nama   : ")));
        isi(() -> c.setNoHP(Input.bacaString("No HP  : ")));
        isi(() -> c.setAlamat(Input.bacaString("Alamat : ")));
        isi(() -> c.setEmail(Input.bacaString("Email  : ")));
        customers.add(c);
        try {
            simpanCustomer();
        } catch (IOException e) {
            customers.remove(c);
            throw e;
        }
        System.out.println("Customer tersimpan.");
    }

    private static void tambahMekanik() throws IOException {
        int n = 101;
        while (idPersonDipakai(String.format("P%03d", n))) n++;
        int m = 1;
        while (cariMekanik(String.format("M%03d", m)) != null) m++;
        Mekanik k = new Mekanik();
        k.setId(String.format("P%03d", n));
        k.setIdMekanik(String.format("M%03d", m));
        System.out.println("ID otomatis: " + k.getId() + " / ID Mekanik: " + k.getIdMekanik());
        isi(() -> k.setNama(Input.bacaString("Nama         : ")));
        isi(() -> k.setNoHP(Input.bacaString("No HP        : ")));
        isi(() -> k.setAlamat(Input.bacaString("Alamat       : ")));
        isi(() -> k.setSpesialisasi(Input.bacaString("Spesialisasi : ")));
        mekaniks.add(k);
        try {
            simpanMekanik();
        } catch (IOException e) {
            mekaniks.remove(k);
            throw e;
        }
        System.out.println("Mekanik tersimpan.");
    }

    private static void tambahMotor() throws IOException {
        Motor m = new Motor();
        isi(() -> {
            m.setNoPolisi(Input.bacaString("No Polisi : "));
            if (cariKendaraan(m.getNoPolisi()) != null) {
                throw new IllegalArgumentException("No Polisi sudah terdaftar.");
            }
        });
        isi(() -> m.setMerk(Input.bacaString("Merk      : ")));
        isi(() -> m.setTipe(Input.bacaString("Tipe      : ")));
        isi(() -> m.setTahun(Input.bacaInt("Tahun (" + Validasi.TAHUN_MIN + "-" + Year.now().getValue() + "): ",
                Validasi.TAHUN_MIN, Year.now().getValue())));
        isi(() -> m.setWarna(Input.bacaString("Warna     : ")));
        isi(() -> m.setKm(Input.bacaInt("KM        : ", 0, Integer.MAX_VALUE)));
        isi(() -> m.setNoRangka(Input.bacaString("No Rangka : ")));
        isi(() -> m.setNoMesin(Input.bacaString("No Mesin  : ")));
        isi(() -> m.setJenisMotor(Input.bacaString("Jenis (Matic/Sport/Bebek): ")));
        kendaraans.add(m);
        try {
            simpanKendaraan();
        } catch (IOException e) {
            kendaraans.remove(m);
            throw e;
        }
        System.out.println("Motor tersimpan.");
    }

    private static String kodeBaru(String prefix) {
        int n = 1;
        while (cariItem(String.format("%s-%03d", prefix, n)) != null) n++;
        return String.format("%s-%03d", prefix, n);
    }

    private static void tambahPart() throws IOException {
        Part p = new Part();
        p.setKode(kodeBaru("P"));
        System.out.println("Kode otomatis: " + p.getKode());
        isi(() -> p.setNama(Input.bacaString("Nama  : ")));
        isi(() -> p.setHarga(Input.bacaDouble("Harga : ", 0)));
        isi(() -> p.setMerk(Input.bacaString("Merk  : ")));
        isi(() -> p.setStok(Input.bacaInt("Stok  : ", 0, Integer.MAX_VALUE)));
        items.add(p);
        try {
            simpanItem();
        } catch (IOException e) {
            items.remove(p);
            throw e;
        }
        System.out.println("Part tersimpan.");
    }

    private static void tambahJasa() throws IOException {
        Jasa j = new Jasa();
        j.setKode(kodeBaru("J"));
        System.out.println("Kode otomatis: " + j.getKode());
        isi(() -> j.setNama(Input.bacaString("Nama     : ")));
        isi(() -> j.setHarga(Input.bacaDouble("Harga    : ", 0)));
        isi(() -> j.setKategori(Input.bacaString("Kategori : ")));
        isi(() -> j.setEstimasiWaktu(Input.bacaInt("Estimasi (menit): ", 0, Integer.MAX_VALUE)));
        items.add(j);
        try {
            simpanItem();
        } catch (IOException e) {
            items.remove(j);
            throw e;
        }
        System.out.println("Jasa tersimpan.");
    }

    /** Mengulang pengisian satu field sampai lolos validasi model. */
    private static void isi(Runnable aksi) {
        while (true) {
            try {
                aksi.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("[!] " + e.getMessage());
            }
        }
    }

    // ---------- Transaksi ----------

    /** Nomor urut = urutan terbesar hari ini + 1 (aman walaupun ada baris yang terhapus). */
    private static int nomorUrutHariIni(LocalDateTime sekarang) throws IOException {
        String prefix = "PKB-" + sekarang.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";
        int maks = 0;
        for (String[] k : DataFile.bacaBaris(F_TRANSAKSI)) {
            if (k[0].startsWith(prefix)) {
                try {
                    maks = Math.max(maks, Integer.parseInt(k[0].substring(prefix.length())));
                } catch (NumberFormatException e) {
                    // nomor PKB tidak standar: abaikan
                }
            }
        }
        return maks + 1;
    }

    /** Mengembalikan stok part yang sudah terpotong kalau transaksi dibatalkan/gagal disimpan. */
    private static void kembalikanStok(Transaksi trx) {
        for (Transaksi.Baris br : trx.getDaftarItemLayanan()) {
            if (br.getItem() instanceof Part) {
                Part p = (Part) br.getItem();
                p.setStok(p.getStok() + br.getQty());
            }
        }
    }

    private static void transaksiBaru() throws IOException {
        if (customers.isEmpty() || mekaniks.isEmpty() || kendaraans.isEmpty() || items.isEmpty()) {
            System.out.println("[!] Data customer, mekanik, kendaraan, dan item harus ada dulu.");
            return;
        }
        Customer cust = null;
        while (cust == null) {
            tampilCustomer();
            cust = cariCustomer(Input.bacaString("No Customer: "));
            if (cust == null) System.out.println("[!] Customer tidak ditemukan.");
        }
        Kendaraan kendaraan = null;
        while (kendaraan == null) {
            tampilKendaraan();
            kendaraan = cariKendaraan(Input.bacaString("No Polisi: "));
            if (kendaraan == null) System.out.println("[!] Kendaraan tidak ditemukan.");
        }
        Mekanik mek = null;
        while (mek == null) {
            tampilMekanik();
            mek = cariMekanik(Input.bacaString("ID Mekanik: "));
            if (mek == null) System.out.println("[!] Mekanik tidak ditemukan.");
        }
        int kmLama = kendaraan.getKm();
        int km = Input.bacaInt("KM saat ini (min " + kmLama + "): ", kmLama, Integer.MAX_VALUE);

        LocalDateTime sekarang = LocalDateTime.now();
        Transaksi trx = new Transaksi(
                Transaksi.buatNoPKB(sekarang, nomorUrutHariIni(sekarang)),
                Transaksi.formatTanggal(sekarang),
                km, kendaraan, cust, mek, "-", "-");

        tampilItem();
        System.out.println("Masukkan kode item (kosongkan untuk selesai).");
        while (true) {
            String kode = Input.bacaOpsional("Kode item: ");
            if (kode.isEmpty()) break;
            ItemLayanan item = cariItem(kode);
            if (item == null) {
                System.out.println("[!] Kode tidak ditemukan.");
                continue;
            }
            int qty = Input.bacaInt("Jumlah: ", 1, 1000);
            try {
                trx.tambahItem(item, qty);
                System.out.println("Ditambahkan: " + item.getNama() + " x" + qty);
            } catch (IllegalArgumentException e) {
                System.out.println("[!] " + e.getMessage());
            }
        }
        if (trx.getDaftarItemLayanan().isEmpty()) {
            System.out.println("Transaksi dibatalkan (tidak ada item).");
            return;
        }
        trx.setSaranPerbaikan(b(Input.bacaOpsional("Saran perbaikan (boleh kosong): ")));
        trx.setGaransi(b(Input.bacaOpsional("Garansi (boleh kosong): ")));

        Cetak cetak = trx; // Transaksi mengimplementasikan interface Cetak
        cetak.struk();

        if (!Input.bacaYaTidak("Simpan transaksi ini")) {
            kembalikanStok(trx);
            System.out.println("Transaksi dibatalkan, stok dikembalikan.");
            return;
        }

        kendaraan.setKm(km); // KM kendaraan ikut diperbarui
        try {
            DataFile.tambahBaris(F_TRANSAKSI, String.join(",", b(trx.getNoPKB()), b(trx.getTanggalWaktu()),
                    String.valueOf(km), b(kendaraan.getNoPolisi()), b(cust.getNoCustomer()),
                    b(mek.getIdMekanik()), b(trx.getSaranPerbaikan()), b(trx.getGaransi())));
            for (Transaksi.Baris br : trx.getDaftarItemLayanan()) {
                DataFile.tambahBaris(F_DETAIL,
                        trx.getNoPKB() + "," + b(br.getItem().getKode()) + "," + br.getQty());
            }
            simpanItem();
            simpanKendaraan();
        } catch (IOException e) {
            // gagal simpan: kembalikan keadaan memori agar konsisten dengan file
            kembalikanStok(trx);
            kendaraan.setKm(kmLama);
            throw e;
        }
        System.out.println("Transaksi tersimpan.");
    }

    // ---------- Tampilan ----------

    private static void tampilCustomer() {
        System.out.println("-- Customer --");
        for (Customer c : customers) {
            System.out.printf("%-6s %-20s %s%n", c.getNoCustomer(), c.getNama(), c.getNoHP());
        }
    }

    private static void tampilMekanik() {
        System.out.println("-- Mekanik --");
        for (Mekanik m : mekaniks) {
            System.out.printf("%-6s %-20s %s%n", m.getIdMekanik(), m.getNama(), m.getSpesialisasi());
        }
    }

    private static void tampilKendaraan() {
        System.out.println("-- Kendaraan --");
        for (Kendaraan k : kendaraans) {
            // getJenisKendaraan() adalah abstract method yang di-override Motor (polymorphism)
            System.out.printf(ID, "%-12s %-8s %-14s %d  KM %,d  (%s)%n",
                    k.getNoPolisi(), k.getMerk(), k.getTipe(), k.getTahun(), k.getKm(), k.getJenisKendaraan());
        }
    }

    private static void tampilItem() {
        System.out.println("-- Item --");
        for (ItemLayanan i : items) {
            String info = (i instanceof Part) ? "stok " + ((Part) i).getStok() : "jasa";
            System.out.println(String.format(ID, "%-6s %-5s %-24s Rp %,.0f  (%s)",
                    i.getKode(), i.getJenisItem(), i.getNama(), i.getHarga(), info));
        }
    }

    /** Customer dan Mekanik sama-sama Person; getPeran() berbeda sesuai class aslinya. */
    private static void tampilSemuaPerson() {
        List<Person> semua = new ArrayList<>();
        semua.addAll(customers);
        semua.addAll(mekaniks);
        System.out.println("-- Semua Person --");
        for (Person p : semua) {
            System.out.printf("%-6s %-20s %-10s %s%n", p.getId(), p.getNama(), p.getPeran(), p.getNoHP());
        }
    }

    private static void lihatData() {
        tampilCustomer();
        tampilMekanik();
        tampilSemuaPerson();
        tampilKendaraan();
        tampilItem();
    }

    // ---------- Riwayat ----------

    /**
     * Menyusun ulang Transaksi dari file. Memakai konstruktor kosong + setter (KM diisi sebelum
     * kendaraan) supaya cek KM tidak gagal untuk transaksi lama, dan stok dikompensasi dulu
     * supaya tambahItem() tidak mengubah stok sebenarnya.
     */
    private static Transaksi susunTransaksi(String[] k, List<String[]> detail) {
        if (k.length < 8) throw new IllegalArgumentException("Baris transaksi rusak.");
        Customer cust = cariCustomer(k[4]);
        Kendaraan kendaraan = cariKendaraan(k[3]);
        Mekanik mek = cariMekanik(k[5]);
        if (cust == null || kendaraan == null || mek == null) {
            throw new IllegalArgumentException("Data customer/kendaraan/mekanik tidak ditemukan.");
        }
        Transaksi trx = new Transaksi();
        trx.setNoPKB(k[0]);
        trx.setTanggalWaktu(k[1]);
        trx.setKmSaatIni(Integer.parseInt(k[2]));
        trx.setKendaraan(kendaraan);
        trx.setCustomer(cust);
        trx.setMekanik(mek);
        trx.setSaranPerbaikan(k[6]);
        trx.setGaransi(k[7]);
        for (String[] d : detail) {
            if (d.length < 3 || !d[0].equals(k[0])) continue;
            ItemLayanan item = cariItem(d[1]);
            if (item == null) throw new IllegalArgumentException("Item " + d[1] + " tidak ditemukan.");
            int qty = Integer.parseInt(d[2]);
            // validasi SEBELUM kompensasi stok, supaya stok tidak bocor kalau data rusak
            if (qty <= 0) throw new IllegalArgumentException("Jumlah item " + d[1] + " tidak valid.");
            if (item instanceof Part) {
                Part p = (Part) item;
                p.setStok(p.getStok() + qty); // kompensasi, akan dikurangi lagi oleh tambahItem
            }
            trx.tambahItem(item, qty);
        }
        return trx;
    }

    private static void riwayat() throws IOException {
        List<String[]> data = DataFile.bacaBaris(F_TRANSAKSI);
        if (data.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        List<String[]> detail = DataFile.bacaBaris(F_DETAIL);
        System.out.println("-- Riwayat Transaksi --");
        for (String[] k : data) {
            try {
                Transaksi t = susunTransaksi(k, detail);
                System.out.println(String.format(ID, "%-18s %-17s %-12s %-18s Rp %,.0f",
                        t.getNoPKB(), t.getTanggalWaktu(), t.getKendaraan().getNoPolisi(),
                        t.getCustomer().getNama(), t.getGrandTotal()));
            } catch (RuntimeException e) {
                System.out.println("[!] " + k[0] + " dilewati: " + e.getMessage());
            }
        }
        String pkb = Input.bacaOpsional("No PKB untuk cetak ulang struk (kosong = kembali): ");
        if (pkb.isEmpty()) return;
        for (String[] k : data) {
            if (k[0].equalsIgnoreCase(pkb)) {
                Cetak cetak = susunTransaksi(k, detail);
                cetak.struk();
                return;
            }
        }
        System.out.println("[!] No PKB tidak ditemukan.");
    }
}