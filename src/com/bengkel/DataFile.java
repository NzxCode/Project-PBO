package com.bengkel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/** Utilitas file teks (CSV sederhana). Tidak mencetak apa pun; pemanggil yang menangani pesan. */
public final class DataFile {
    private DataFile() {}

    /** Membaca semua baris tidak kosong, dipecah per koma. File belum ada = daftar kosong. */
    public static List<String[]> bacaBaris(String pathFile) throws IOException {
        List<String[]> hasil = new ArrayList<>();
        Path path = Paths.get(pathFile);
        if (!Files.exists(path)) return hasil;
        for (String baris : Files.readAllLines(path, StandardCharsets.UTF_8)) {
            if (baris.trim().isEmpty()) continue;
            String[] kolom = baris.split(",", -1);
            for (int i = 0; i < kolom.length; i++) kolom[i] = kolom[i].trim();
            hasil.add(kolom);
        }
        return hasil;
    }

    /** Menambah satu baris di akhir file (folder dibuat otomatis). */
    public static void tambahBaris(String pathFile, String baris) throws IOException {
        Path path = Paths.get(pathFile);
        buatFolder(path);
        Files.write(path, (baris + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /** Menimpa seluruh isi file. */
    public static void tulisSemua(String pathFile, List<String> baris) throws IOException {
        Path path = Paths.get(pathFile);
        buatFolder(path);
        Files.write(path, baris, StandardCharsets.UTF_8);
    }

    private static void buatFolder(Path path) throws IOException {
        Path folder = path.toAbsolutePath().getParent();
        if (folder != null) Files.createDirectories(folder);
    }

    /** Menghilangkan koma/baris baru agar tidak merusak format CSV. */
    public static String bersihkan(String s) {
        return s == null ? "" : s.replace(',', ' ').replace('\n', ' ').replace('\r', ' ').trim();
    }
}
