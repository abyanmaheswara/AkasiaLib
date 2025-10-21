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
                catalogService.listAllBooks().forEach { println(it.info()) }
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
