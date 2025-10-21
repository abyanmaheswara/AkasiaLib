package util

import model.Peminjaman
import java.io.File
import java.time.format.DateTimeFormatter

object CsvExporter {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // 🔹 Ekspor daftar peminjaman aktif
    fun exportActiveLoans(loans: List<Peminjaman>, fileName: String = "laporan_pinjaman.csv"): String {
        if (loans.isEmpty()) return "⚠️ Tidak ada data pinjaman aktif untuk diekspor."

        val file = File(fileName)
        file.printWriter().use { out ->
            out.println("ID,Peminjam,Buku,Tanggal Pinjam,Jatuh Tempo,Denda")
            for (l in loans) {
                val tglPinjam = l.tglPinjam.format(dateFormatter)
                val jatuhTempo = l.jatuhTempo.format(dateFormatter)
                out.println("${l.id},${l.anggotaId},${l.bukuId},$tglPinjam,$jatuhTempo,${l.denda}")
            }
        }
        return "✅ Laporan pinjaman berhasil diekspor ke '$fileName'"
    }

    // 🔹 Ekspor total denda
    fun exportTotalFine(totalFine: Int, fileName: String = "laporan_denda.csv"): String {
        val file = File(fileName)
        file.printWriter().use { out ->
            out.println("Total Denda (Rp)")
            out.println(totalFine)
        }
        return "✅ Laporan total denda berhasil diekspor ke '$fileName'"
    }

    // 🔹 Ekspor Top 3 Buku
    fun exportTopBooks(topBooks: List<String>, fileName: String = "laporan_top_buku.csv"): String {
        if (topBooks.isEmpty()) return "⚠️ Tidak ada data buku untuk diekspor."

        val file = File(fileName)
        file.printWriter().use { out ->
            out.println("Peringkat,Judul Buku")
            topBooks.forEachIndexed { index, buku ->
                out.println("${index + 1},$buku")
            }
        }
        return "✅ Laporan Top 3 Buku berhasil diekspor ke '$fileName'"
    }
}
