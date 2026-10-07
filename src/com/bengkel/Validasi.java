package com.bengkel;

import java.time.Year;

/** Aturan validasi yang dipakai bersama oleh semua kelas model. */
public final class Validasi {
    public static final int TAHUN_MIN = 1980;

    private Validasi() {}

    public static String wajibIsi(String nilai, String namaField) {
        if (nilai == null || nilai.trim().isEmpty()) {
            throw new IllegalArgumentException(namaField + " tidak boleh kosong.");
        }
        return nilai.trim();
    }

    public static int tahun(int tahun) {
        int maks = Year.now().getValue();
        if (tahun < TAHUN_MIN || tahun > maks) {
            throw new IllegalArgumentException("Tahun harus antara " + TAHUN_MIN + " dan " + maks + ".");
        }
        return tahun;
    }

    public static int tidakNegatif(int nilai, String namaField) {
        if (nilai < 0) throw new IllegalArgumentException(namaField + " tidak boleh negatif.");
        return nilai;
    }

    public static double tidakNegatif(double nilai, String namaField) {
        if (Double.isNaN(nilai) || nilai < 0) throw new IllegalArgumentException(namaField + " tidak boleh negatif.");
        return nilai;
    }
}
