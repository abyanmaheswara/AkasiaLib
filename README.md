# Proyek Sistem Perpustakaan - AkasiaLib

AkasiaLib adalah aplikasi Command-Line Interface (CLI) sederhana untuk manajemen perpustakaan, yang dibangun menggunakan Kotlin dengan prinsip-prinsip Object-Oriented Programming (OOP).

---

## Anggota Tim & Kontribusi

| Peran | Nama                          | NIM       | Kontribusi Spesifik |
| :--- |:------------------------------|:----------| :--- |
| **1. Analis & Desainer** | Dien Putri Alexa              | 224443028 | Menganalisis kebutuhan dan merancang struktur kelas dan relasi sesuai spesifikasi tugas (UML). |
| **2. Implementasi Model & Repository** | Abyan Maheswara               | 224443024 | - Membuat semua data class dan abstract class di direktori `model` (`Buku`, `BukuCetak`, `BukuDigital`, `Anggota`, `Peminjaman`, `Reservasi`).<br>- Mengimplementasikan `interface Identifiable` untuk type safety.<br>- Membuat repositori generik `InMemoryRepo<T>` untuk manajemen data di memori. |
| **3. Implementasi Service & Logika Bisnis** | Altaf Fazli Sakha             | 224443025 | - Membuat `CatalogService` dan `MemberService` untuk manajemen data dasar.<br>- Mengimplementasikan `ReservationService` untuk logika antrian.<br>- Membangun `CirculationService` yang berisi logika bisnis inti: peminjaman, pengembalian, perhitungan denda, dan pengecekan batas pinjam. |
| **4. Implementasi CLI & Laporan** | Muhammad Irsyad Salim Nugraha | 224443036 | - Membangun antarmuka CLI interaktif di `Main.kt`.<br>- Mengimplementasikan fungsi untuk menjalankan skenario uji wajib secara otomatis.<br>- Membuat fungsi-fungsi untuk menampilkan laporan (pinjaman aktif, buku terpopuler, total denda, antrian reservasi). |

---

## Cara Menjalankan Aplikasi

Pastikan Anda memiliki JDK (Java Development Kit) yang terinstal.

1.  Buka terminal atau command prompt di direktori root proyek.
2.  Jalankan perintah berikut:

    ```bash
    # Untuk Windows
    .\gradlew.bat run

    # Untuk macOS/Linux
    ./gradlew run
    ```
3.  Aplikasi akan pertama-tama menjalankan skenario uji otomatis, lalu menampilkan menu interaktif.

---

## Sistem Login AkasiaLib
```
=== ? Sistem Login AkasiaLib ===
Username:
Password:

Contoh Login Pustakawan:
Username:pustakawan
Password:lib123
Login berhasil! Selamat datang, pustakawan (Petugas).

Contoh Login Admin:
Username:admin
Password:admin123
```

## Menu Super Admin
```
=== ? Menu Super Admin ===
1. Tambah akun petugas baru
   Contoh:
   Masukkan username petugas baru:asepustakawan
   Masukkan password:lib234 
   Akun petugas 'asepustakawan' berhasil ditambahkan.
2. Lihat daftar petugas
   Contoh:
   1. pustakawan (PETUGAS)
   2. asepustakawan (PETUGAS)
0. Keluar
   Contoh:
   Keluar dari menu admin
Pilih menu:
```

## Menu Pustakawan
```
=== ? Sistem Perpustakaan AkasiaLib ===
1. Kelola Buku
   Contoh (jika memilih 1):
   === ? Kelola Buku ===
   1. Tambah Buku Cetak
      Contoh (jika memilih 1):
      Judul: Hujan
      Penulis: Tere Liye
      Tahun: 2016
      Kategori: roman, drama, dan fiksi ilmiah (sci-fi)
      Jumlah halaman:320
      Stok: 50 
      Buku 'Hujan' berhasil ditambahkan ke katalog.
   2. Tambah Buku Digital
      Contoh (jika memilih 2):
      Judul: Filosofi Teras: Bagaimana Stoikisme Kuno Dapat Menolong Anda Menghadapi Hidup Modern 
      Penulis: Henry Manampiring 
      Tahun: 2019 
      Kategori: Pengembangan Diri (Self-Help) 
      Ukuran File: 5.6MB 
      Format: PDF
      Buku 'Filosofi Teras: Bagaimana Stoikisme Kuno Dapat Menolong Anda Menghadapi Hidup Modern' berhasil ditambahkan ke katalog.
   3. Tambah Buku Audio
      Contoh (jika memilih 3):
      Judul: Sebuah Seni untuk Bersikap Bodo Amat 
      Penulis: Mark Manson 
      Tahun Rilis: 2021 
      Kategori: Pengembangan Diri (Self-Development) 
      Durasi Total: 405
      Narator: Dennis Adishwara 
      
      Tipe: Buku Audio
      ID: BA-0001
      Judul: Sebuah Seni untuk Bersikap Bodo Amat
      Penulis: Mark Manson
      Tahun: 2021
      Kategori: Pengembangan Diri (Self-Development)
      Durasi: 6j 45m
      Narator: Dennis Adishwara
   4. Lihat Semua Buku
      Contoh (jika memilih 4):
      Tipe: Buku Cetak
      ID: BC-0001
      Judul: Hujan
      Penulis: Tere Liye
      Tahun: 2016
      Kategori: roman, drama, dan fiksi ilmiah (sci-fi)
      Halaman: 320
      Stok: 50

      Tipe: Buku Digital
      ID: BD-0001
      Judul: Filosofi Teras: Bagaimana Stoikisme Kuno Dapat Menolong Anda Menghadapi Hidup Modern
      Penulis: Henry Manampiring
      Tahun: 2019
      Kategori: Pengembangan Diri (Self-Help)
      Ukuran File: 5.6MB
      Format: PDF

      Tipe: Buku Audio
      ID: BA-0001
      Judul: Sebuah Seni untuk Bersikap Bodo Amat
      Penulis: Mark Manson
      Tahun: 2021
      Kategori: Pengembangan Diri (Self-Development)
      Durasi: 6j 45m
      Narator: Dennis Adishwara
   0. Kembali
   Pilih menu:
2. Kelola Anggota
   Contoh (jika memilih 2):
   === ? Kelola Anggota ===
   1. Tambah Anggota
      Contoh (jika memilih 1):
      Nama: Abyan
      Tier (REGULAR / PREMIUM / STAFF): REGULAR
      Anggota 'Abyan' berhasil didaftarkan (REGULAR).

      Nama: Altaf
      Tier (REGULAR / PREMIUM / STAFF): PREMIUM 
      Anggota 'Altaf' berhasil didaftarkan (PREMIUM).

      Nama: Irsyad
      Tier (REGULAR / PREMIUM / STAFF): STAFF
      Anggota 'Irsyad' berhasil didaftarkan (STAFF).
   2. Lihat Semua Anggota
      Contoh (jika memilih 2):
      AG-0001 - Abyan (REGULAR) Aktif: true
      AG-0002 - Altaf (PREMIUM) Aktif: true
      AG-0003 - Irsyad (STAFF) Aktif: true
   0. Kembali
   Pilih menu:
3. Peminjaman Buku
4. Pengembalian Buku
5. Laporan
0. Keluar
Pilih menu:
```

---

## Hasil Skenario Uji Wajib

Saat dijalankan, program akan secara otomatis mengeksekusi skenario yang diminta dalam dokumen tugas. Hasilnya adalah sebagai berikut:

1.  **Inisialisasi Data**: 5 buku dan 3 anggota berhasil ditambahkan.
2.  **Peminjaman REGULAR**: Anggota "Andi" berhasil meminjam 2 buku.
3.  **Peminjaman PREMIUM**: Anggota "Siti" berhasil meminjam 1 buku digital.
4.  **Gagal Pinjam & Reservasi**: Anggota "Rahmat" gagal meminjam buku yang stoknya habis, lalu berhasil membuat reservasi untuk buku tersebut.
5.  **Pengembalian Terlambat**: Sebuah skenario pengembalian buku yang terlambat 3 hari disimulasikan, dan denda sebesar **Rp 3000** berhasil dihitung.
6.  **Notifikasi Reservasi**: Setelah buku dikembalikan, sistem menampilkan notifikasi bahwa buku tersebut kini ditawarkan kepada "Rahmat" yang ada di antrian.
7.  **Laporan**: Laporan total denda dan buku terpopuler ditampilkan dengan benar di akhir skenario.

Semua skenario uji wajib berhasil dijalankan sesuai ekspektasi.

💡 Tugas Tambahan (Opsional, Bonus 10–20%)

Implementasikan pattern Strategy untuk perhitungan denda.

Tambahkan class BukuAudio (durasi, narrator) dengan aturan peminjaman berbeda.

Tambahkan ekspor laporan ke CSV.

Implementasikan autonumber ID otomatis (BK-0001, AG-0001, dst).

Buat sesi login sederhana (Admin vs Pustakawan).

## Class Diagram
![Class Diagram](classdiagram_AKASIALAB.drawio.png)
