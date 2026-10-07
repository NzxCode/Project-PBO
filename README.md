# Aplikasi Kasir Bengkel Motor (Project PBO)

Aplikasi konsol Java (OOP) untuk mencatat transaksi servis motor: customer, mekanik, motor, part, jasa, dan struk.

## Cara menjalankan di VS Code
1. Extract ZIP ini.
2. Di VS Code pilih **File > Open Folder** lalu buka folder **Project-PBO-main** (folder yang berisi `src`, `data`, dan `.vscode`).
3. Buka `src/com/bengkel/App.java`.
4. Klik **Run** di atas method `main`, atau buka **Run and Debug** dan pilih **Jalankan Aplikasi Bengkel**.
5. Tidak perlu mengetik `javac App.java` dari folder `src/com/bengkel`.

Jika memakai ekstensi Code Runner, tombol **Run Code** juga sudah diarahkan untuk compile seluruh class dan menjalankan `com.bengkel.App`.

Alternatif Windows: klik dua kali `Jalankan-Bengkel.bat`.

## Struktur
- `src/com/bengkel/` : kelas model, util (`Input`, `DataFile`, `Validasi`), dan `App`
- `data/` : data teks (customer, mekanik, kendaraan, item, transaksi, detail)
- `.vscode/` : konfigurasi Run/Debug VS Code

## Penting
Semua class menggunakan package `com.bengkel`, jadi compile harus dilakukan terhadap seluruh file Java, bukan hanya `App.java`.
