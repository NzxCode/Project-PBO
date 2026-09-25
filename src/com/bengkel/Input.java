package com.bengkel;

import java.util.Scanner;

public class Input {
    private static final Scanner inputUser = new Scanner(System.in);

    public static String bacaString(String pesan) {
        String hasil;
        do {
            System.out.print(pesan);
            hasil = inputUser.nextLine().trim();
            if (hasil.isEmpty()) {
                System.out.println("Input tidak boleh kosong, ulangi lagi!");
            }
        } while (hasil.isEmpty());
        return hasil;
    }

    public static String bacaStringKosong(String pesan) {
        System.out.print(pesan);
        return inputUser.nextLine().trim();
    }
    public static int bacaInt(String pesan, int min, int max) {
        int hasil = 0;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(pesan);
            String cek = inputUser.nextLine().trim();
            try {
                hasil = Integer.parseInt(cek);
                if (hasil < min || hasil > max) {
                    System.out.println("Nilai harus antara " + min + " dan " + max + ", ulangi lagi!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Salah input, ulangi lagi!");
                isValid = false;
            }
        } while (!isValid);
        return hasil;
    }

    public static double bacaDouble(String pesan, double min, double max) {
        double hasil = 0;
        boolean isValid;
        do {
            isValid = true;
            System.out.print(pesan);
            String cek = inputUser.nextLine().trim();
            try {
                hasil = Double.parseDouble(cek);
                if (Double.isNaN(hasil) || Double.isInfinite(hasil) || hasil < min || hasil > max) {
                    System.out.println("Nilai harus antara " + min + " dan " + max + ", ulangi lagi!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Salah input, ulangi lagi!");
                isValid = false;
            }
        } while (!isValid);
        return hasil;
    }
}
