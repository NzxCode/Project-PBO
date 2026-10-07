package com.bengkel;

// Import library Date/Time Java
import java.time.Year;

// Mendeklarasikan class Validasi sebagai final class (tidak dapat di-inherit / diturunkan)
public final class Validasi {
    // Konstanta global public static final
    public static final int TAHUN_MIN = 1980;

    // Private constructor agar class ini murni utility dan tidak bisa diinstansiasi menjadi object
    private Validasi() {}

    // Static method untuk validasi input teks yang tidak boleh null atau kosong
    public static String wajibIsi(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            // Melempar exception jika kondisi fail
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
        return nilai.trim();
    }

    // Static method untuk validasi nilai tahun yang wajar (contoh: 1980 s/d Tahun Sekarang)
    public static int tahun(int tahun) {
        // Mengambil integer tahun saat ini dari OS
        int maks = Year.now().getValue();
        if (tahun < TAHUN_MIN || tahun > maks) {
            // Lempar exception jika tahun ada di masa depan atau terlalu jadul
            throw new IllegalArgumentException("Tahun harus antara " + TAHUN_MIN + " dan " + maks + ".");
        }
        return tahun;
    }

    // Static method penahan angka negatif untuk tipe data integer
    public static int tidakNegatif(int nilai, String namaField) {
        if (nilai < 0) throw new IllegalArgumentException(namaField + " tidak boleh negatif.");
        return nilai;
    }

    // Method overloading dari 'tidakNegatif' di atas untuk tipe data double (desimal)
    public static double tidakNegatif(double nilai, String namaField) {
        // Double.isNaN digunakan untuk mengecek tipe data rusak/bukan angka
        if (Double.isNaN(nilai) || nilai < 0) throw new IllegalArgumentException(namaField + " tidak boleh negatif.");
        return nilai;
    }
}