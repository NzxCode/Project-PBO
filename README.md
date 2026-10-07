# Aplikasi Kasir Bengkel Motor (Project PBO)

Aplikasi konsol Java (OOP) untuk mencatat transaksi servis motor: customer, mekanik, motor, part, jasa, dan struk.

## Struktur
- `src/com/bengkel/` : kelas model, util (`Input`, `DataFile`, `Validasi`), dan `App`
- `data/` : data teks (customer, mekanik, kendaraan, item, transaksi, detail)

## Format data
- Customer: `id,nama,noHP,alamat,noCustomer,email` (id `Pxxx`, noCustomer `Cxxx`)
- Mekanik: `id,nama,noHP,alamat,idMekanik,spesialisasi`
- Kendaraan: `Motor,noPolisi,merk,tipe,tahun,warna,km,noRangka,noMesin,jenis`
- Item: `Part|Jasa,kode,nama,harga,merk/kategori,stok/estimasi`
- Transaksi: `noPKB,tanggal,km,noPolisi,noCustomer,idMekanik,saran,garansi`
- Detail: `noPKB,kodeItem,qty`

## Build dan jalankan (dari folder project)
```
javac -encoding UTF-8 -d bin src/com/bengkel/*.java
java -cp bin com.bengkel.App
```
