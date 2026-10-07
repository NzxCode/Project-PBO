package com.bengkel;

import java.util.Scanner;

/** Helper input konsol. Satu Scanner bersama; tidak pernah menutup System.in. */
public final class Input {
    private static final Scanner SCANNER = new Scanner(System.in);

    private Input() {}

    /** Teks boleh kosong. */
    public static String bacaOpsional(String pesan) {
        System.out.print(pesan);
        return SCANNER.nextLine().trim();
    }

    /** Teks wajib diisi. */
    public static String bacaString(String pesan) {
        while (true) {
            String s = bacaOpsional(pesan);
            if (!s.isEmpty()) return s;
            System.out.println("[!] Tidak boleh kosong.");
        }
    }

    public static int bacaInt(String pesan, int min, int maks) {
        while (true) {
            try {
                int v = Integer.parseInt(bacaOpsional(pesan));
                if (v < min || v > maks) {
                    System.out.println("[!] Nilai harus antara " + min + " dan " + maks + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("[!] Input harus berupa angka bulat.");
            }
        }
    }

    public static double bacaDouble(String pesan, double min) {
        while (true) {
            try {
                double v = Double.parseDouble(bacaOpsional(pesan));
                if (v < min) {
                    System.out.println("[!] Nilai minimal " + min + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("[!] Input harus berupa angka (pakai titik untuk desimal).");
            }
        }
    }

    public static boolean bacaYaTidak(String pesan) {
        while (true) {
            String s = bacaOpsional(pesan + " (y/n): ").toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            System.out.println("[!] Ketik y atau n.");
        }
    }
}
