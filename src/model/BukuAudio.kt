package model

class BukuAudio(
    id: String,
    judul: String,
    penulis: String,
    tahun: Int,
    kategori: String,
    val durasiMenit: Int,
    val narrator: String
) : Buku(id, judul, penulis, tahun, kategori) {

    override fun info(): String {
        val jam = durasiMenit / 60
        val menit = durasiMenit % 60
        return """
            Tipe: Buku Audio
            ID: $id
            Judul: $judul
            Penulis: $penulis
            Tahun: $tahun
            Kategori: $kategori
            Durasi: ${jam}j ${menit}m
            Narator: $narrator
        """.trimIndent()
    }
}
