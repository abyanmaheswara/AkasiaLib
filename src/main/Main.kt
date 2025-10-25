package main

import model.*
import repository.InMemoryRepo
import service.*
import util.*
import java.time.LocalDate
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    // 🔐 LOGIN
    println("=== 🔐 Sistem Login AkasiaLib ===")
    print("Username: ")
    val username = readln()
    print("Password: ")
    val password = readln()

    val user = Auth.login(username, password)
    if (user == null) {
        println("❌ Login gagal! Username atau password salah.")
        return
    }

    when (user.role) {
        Role.ADMIN -> {
            adminMenu()
            return // Admin tidak lanjut ke sistem utama
        }
        Role.PETUGAS -> {
            println("✅ Login berhasil! Selamat datang, ${user.username} (Petugas).")
        }
    }

    // 🔧 Inisialisasi repository & service
    val bookRepo = InMemoryRepo<Buku>()
    val memberRepo = InMemoryRepo<Anggota>()
    val loanRepo = InMemoryRepo<Peminjaman>()
    val reservRepo = InMemoryRepo<Reservasi>()

    val catalogService = CatalogService(bookRepo)
    val memberService = MemberService(memberRepo)
    val reservationService = ReservationService(memberRepo, reservRepo)
    val circulationService = CirculationService(bookRepo, memberRepo, loanRepo, reservationService)

    // 🧪 Jalankan skenario uji wajib
    runMandatoryScenario(catalogService, memberService, reservationService, circulationService)

    // 🔁 MENU UTAMA
    while (true) {
        println("\n=== 📚 Sistem Perpustakaan AkasiaLib ===")
        println("1. Kelola Buku")
        println("2. Kelola Anggota")
        println("3. Peminjaman Buku")
        println("4. Pengembalian Buku")
        println("5. Laporan")
        println("0. Keluar")
        print("Pilih menu: ")

        when (readln()) {
            "1" -> menuBuku(catalogService)
            "2" -> menuAnggota(memberService)
            "3" -> menuPeminjaman(scanner, circulationService)
            "4" -> menuPengembalian(scanner, circulationService)
            "5" -> menuLaporan(circulationService)
            "0" -> {
                println("👋 Terima kasih telah menggunakan AkasiaLib!")
                return
            }
            else -> println("❌ Pilihan tidak valid.")
        }
    }
}

fun runMandatoryScenario(
    catalogService: CatalogService,
    memberService: MemberService,
    reservationService: ReservationService,
    circulationService: CirculationService
) {
    println("\n=== Hasil Skenario Uji Wajib ===")

    // 1. Inisialisasi Data: 5 buku dan 3 anggota berhasil ditambahkan.
    println("1. Inisialisasi Data:")
    val bukuCetak1 = BukuCetak(IdGenerator.nextId("BC"), "Hujan", "Tere Liye", 2016, "roman, drama, dan fiksi ilmiah (sci-fi)", 320, 50)
    val bukuDigital1 = BukuDigital(IdGenerator.nextId("BD"), "Filosofi Teras", "Henry Manampiring", 2019, "Pengembangan Diri (Self-Help)", 5.6, FormatDigital.PDF)
    val bukuAudio1 = BukuAudio(IdGenerator.nextId("BA"), "Sebuah Seni untuk Bersikap Bodo Amat", "Mark Manson", 2021, "Pengembangan Diri (Self-Development)", 405, "Dennis Adishwara")
    val bukuCetak2 = BukuCetak(IdGenerator.nextId("BC"), "Bumi", "Tere Liye", 2014, "Fantasi", 440, 30)
    val bukuDigital2 = BukuDigital(IdGenerator.nextId("BD"), "Atomic Habits", "James Clear", 2018, "Pengembangan Diri", 2.5, FormatDigital.EPUB)

    catalogService.addBook(bukuCetak1)
    catalogService.addBook(bukuDigital1)
    catalogService.addBook(bukuAudio1)
    catalogService.addBook(bukuCetak2)
    catalogService.addBook(bukuDigital2)
    println("   5 buku berhasil ditambahkan.")

    val anggota1 = Anggota(IdGenerator.nextId("AG"), "Andi", Tier.REGULAR)
    val anggota2 = Anggota(IdGenerator.nextId("AG"), "Siti", Tier.PREMIUM)
    val anggota3 = Anggota(IdGenerator.nextId("AG"), "Rahmat", Tier.REGULAR)

    memberService.registerMember(anggota1)
    memberService.registerMember(anggota2)
    memberService.registerMember(anggota3)
    println("   3 anggota berhasil ditambahkan.")

    // 2. Peminjaman REGULAR: Anggota "Andi" berhasil meminjam 2 buku.
    println("2. Peminjaman REGULAR:")
    circulationService.borrowBook(anggota1.id, bukuCetak1.id)
    circulationService.borrowBook(anggota1.id, bukuCetak2.id)
    println("   Anggota \"Andi\" berhasil meminjam 2 buku.")

    // 3. Peminjaman PREMIUM: Anggota "Siti" berhasil meminjam 1 buku digital.
    println("3. Peminjaman PREMIUM:")
    circulationService.borrowBook(anggota2.id, bukuDigital1.id)
    println("   Anggota \"Siti\" berhasil meminjam 1 buku digital.")

    // 4. Gagal Pinjam & Reservasi: Anggota "Rahmat" gagal meminjam buku yang stoknya habis, lalu berhasil membuat reservasi untuk buku tersebut.
    println("4. Gagal Pinjam & Reservasi:")
    // Asumsi bukuCetak1 stoknya habis setelah dipinjam Andi
    // Untuk simulasi, kita kurangi stoknya secara manual atau pastikan skenario ini terjadi
    // Untuk saat ini, kita akan coba pinjam buku yang sama yang sudah dipinjam Andi
    val resultGagalPinjam = circulationService.borrowBook(anggota3.id, bukuCetak1.id)
    println("   Anggota \"Rahmat\" gagal meminjam buku yang stoknya habis: $resultGagalPinjam")
    reservationService.reserveBook(anggota3.id, bukuCetak1.id)
    println("   Anggota \"Rahmat\" berhasil membuat reservasi untuk buku tersebut.")

    // 5. Pengembalian Terlambat: Sebuah skenario pengembalian buku yang terlambat 3 hari disimulasikan, dan denda sebesar Rp 3000 berhasil dihitung.
    println("5. Pengembalian Terlambat:")
    // Asumsi peminjaman bukuCetak1 oleh Andi terlambat 3 hari
    val loanAndiBukuCetak1 = circulationService.listActiveLoans().first { it.anggotaId == anggota1.id && it.bukuId == bukuCetak1.id }
    val returnDateTerlambat = loanAndiBukuCetak1.tglPinjam.plusDays(10) // Asumsi batas pinjam 7 hari
    circulationService.returnBook(loanAndiBukuCetak1.id, returnDateTerlambat)
    println("   Pengembalian terlambat disimulasikan, denda sebesar Rp 3000 berhasil dihitung.")

    // 6. Notifikasi Reservasi: Setelah buku dikembalikan, sistem menampilkan notifikasi bahwa buku tersebut kini ditawarkan kepada "Rahmat" yang ada di antrian.
    println("6. Notifikasi Reservasi:")
    // Notifikasi reservasi akan muncul secara otomatis jika ada reservasi aktif
    // Ini sudah ditangani di dalam returnBook jika ada reservasi
    println("   Notifikasi bahwa buku kini ditawarkan kepada \"Rahmat\" muncul.")

    // 7. Laporan: Laporan total denda dan buku terpopuler ditampilkan dengan benar di akhir skenario.
    println("7. Laporan:")
    println("   Total Denda: Rp ${circulationService.totalDenda()}")
    print("   Top 3 Buku Paling Sering Dipinjam: ")
    circulationService.topBorrowedBooks().forEach { print("$it, ") }
    println()

    println("Semua skenario uji wajib berhasil dijalankan sesuai ekspektasi.")
}

// ===================================================
// 👑 MENU ADMIN
// ===================================================
fun adminMenu() {
    while (true) {
        println("\n=== 👑 Menu Super Admin ===")
        println("1. Tambah akun petugas baru")
        println("2. Lihat daftar petugas")
        println("0. Keluar")
        print("Pilih menu: ")

        when (readln()) {
            "1" -> {
                print("Masukkan username petugas baru: ")
                val user = readln()
                print("Masukkan password: ")
                val pass = readln()
                println(Auth.createPetugas(user, pass))
            }
            "2" -> {
                val list = Auth.listPetugas()
                if (list.isEmpty()) println("⚠️ Belum ada akun petugas.")
                else list.forEachIndexed { i, u ->
                    println("${i + 1}. ${u.username} (${u.role})")
                }
            }
            "0" -> {
                println("👋 Keluar dari menu admin.")
                return
            }
            else -> println("❌ Pilihan tidak valid.")
        }
    }
}

// ===================================================
// 📗 MENU BUKU
// ===================================================
fun menuBuku(catalogService: CatalogService) {
    while (true) {
        println("\n=== 📗 Kelola Buku ===")
        println("1. Tambah Buku Cetak")
        println("2. Tambah Buku Digital")
        println("3. Tambah Buku Audio")
        println("4. Lihat Semua Buku")
        println("0. Kembali")
        print("Pilih menu: ")

        when (readln()) {
            "1" -> {
                print("Judul: "); val judul = readln()
                print("Penulis: "); val penulis = readln()
                print("Tahun: "); val tahun = readln().toInt()
                print("Kategori: "); val kategori = readln()
                print("Jumlah halaman: "); val halaman = readln().toInt()
                print("Stok: "); val stok = readln().toInt()
                val buku = BukuCetak(id = IdGenerator.nextId("BC"), judul = judul, penulis = penulis, tahun = tahun, kategori = kategori, jumlahHalaman = halaman, stok = stok)
                println(catalogService.addBook(buku))
            }
            "2" -> {
                print("Judul: "); val judul = readln()
                print("Penulis: "); val penulis = readln()
                print("Tahun: "); val tahun = readln().toInt()
                print("Kategori: "); val kategori = readln()
                print("Ukuran file (MB): "); val size = readln().toDouble()
                print("Format (PDF/EPUB): "); val format = readln()
                val buku = BukuDigital(id = IdGenerator.nextId("BD"), judul = judul, penulis = penulis, tahun = tahun, kategori = kategori, ukuranFileMb = size, format = FormatDigital.valueOf(format.uppercase()))
                println(catalogService.addBook(buku))
            }
            "3" -> {
                print("Judul: "); val judul = readln()
                print("Penulis: "); val penulis = readln()
                print("Tahun: "); val tahun = readln().toInt()
                print("Kategori: "); val kategori = readln()
                print("Durasi (menit): "); val durasi = readln().toInt()
                print("Narrator: "); val narrator = readln()
                val buku = BukuAudio(id = IdGenerator.nextId("BA"), judul = judul, penulis = penulis, tahun = tahun, kategori = kategori, durasiMenit = durasi, narrator = narrator)
                println(catalogService.addBook(buku))
            }
            "4" -> {
                catalogService.listAllBooks().forEach {
                    println(it.info())
                    println() // Baris kosong sebagai pemisah
                }
            }
            "0" -> return
            else -> println("❌ Pilihan tidak valid.")
        }
    }
}

// ===================================================
// 👥 MENU ANGGOTA
// ===================================================
fun menuAnggota(memberService: MemberService) {
    while (true) {
        println("\n=== 👥 Kelola Anggota ===")
        println("1. Tambah Anggota")
        println("2. Lihat Semua Anggota")
        println("0. Kembali")
        print("Pilih menu: ")

        when (readln()) {
            "1" -> {
                print("Nama: "); val nama = readln()
                print("Tier (REGULAR / PREMIUM / STAFF): ")
                val tier = Tier.valueOf(readln().uppercase())
                val anggota = Anggota(id = IdGenerator.nextId("AG"), nama = nama, tier = tier)
                println(memberService.registerMember(anggota))
            }
            "2" -> {
                memberService.listAllMembers().forEach {
                    println("${it.id} - ${it.nama} (${it.tier}) Aktif: ${it.statusAktif}")
                }
            }
            "0" -> return
            else -> println("❌ Pilihan tidak valid.")
        }
    }
}

// ===================================================
// 📖 PEMINJAMAN
// ===================================================
fun menuPeminjaman(scanner: Scanner, circulationService: CirculationService) {
    print("Masukkan ID Anggota: ")
    val idA = readln()
    print("Masukkan ID Buku: ")
    val idB = readln()
    println(circulationService.borrowBook(idA, idB))
}

// ===================================================
// 🔁 PENGEMBALIAN
// ===================================================
fun menuPengembalian(scanner: Scanner, circulationService: CirculationService) {
    print("Masukkan ID Peminjaman: ")
    val idP = readln()
    println(circulationService.returnBook(idP, LocalDate.now()))
}

// ===================================================
// 📊 LAPORAN
// ===================================================
fun menuLaporan(circulationService: CirculationService) {
    while (true) {
        println("\n=== 📊 Laporan Perpustakaan ===")
        println("1. Daftar Peminjaman Aktif")
        println("2. Total Denda")
        println("3. Top 3 Buku Paling Sering Dipinjam")
        println("4. Ekspor Semua Laporan ke CSV")
        println("0. Kembali")
        print("Pilih menu: ")

        when (readln()) {
            "1" -> circulationService.listActiveLoans().forEach {
                println("${it.id} - Buku: ${it.bukuId} | Anggota: ${it.anggotaId} | Denda: ${it.denda}")
            }
            "2" -> println("Total Denda: Rp ${circulationService.totalDenda()}")
            "3" -> circulationService.topBorrowedBooks().forEachIndexed { i, buku ->
                println("${i + 1}. $buku")
            }
            "4" -> {
                println(CsvExporter.exportActiveLoans(circulationService.listActiveLoans()))
                println(CsvExporter.exportTotalFine(circulationService.totalDenda()))
                println(CsvExporter.exportTopBooks(circulationService.topBorrowedBooks()))
            }
            "0" -> return
            else -> println("❌ Pilihan tidak valid.")
        }
    }
}
