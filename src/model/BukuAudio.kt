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
        return "AudioBook: $judul oleh $penulis, durasi ${jam}j ${menit}m, narator: $narrator"
    }
}
