package service

import model.Anggota
import model.Tier
import repository.InMemoryRepo

class MemberService(
    private val memberRepo: InMemoryRepo<Anggota>
) {

    // 🔹 Daftarkan anggota baru
    fun registerMember(anggota: Anggota): String {
        memberRepo.save(anggota)
        return "✅ Anggota '${anggota.nama}' berhasil didaftarkan (${anggota.tier})."
    }

    // 🔹 Nonaktifkan anggota
    fun deactivateMember(id: String): String {
        val anggota = memberRepo.findById(id) ?: return "❌ Anggota tidak ditemukan."
        anggota.statusAktif = false
        memberRepo.save(anggota)
        return "🚫 Anggota '${anggota.nama}' telah dinonaktifkan."
    }

    // 🔹 Aktifkan kembali anggota
    fun activateMember(id: String): String {
        val anggota = memberRepo.findById(id) ?: return "❌ Anggota tidak ditemukan."
        anggota.statusAktif = true
        memberRepo.save(anggota)
        return "✅ Anggota '${anggota.nama}' diaktifkan kembali."
    }

    // 🔹 Cari anggota berdasarkan nama
    fun searchMemberByName(keyword: String): List<Anggota> {
        return memberRepo.findAll().filter {
            it.nama.contains(keyword, ignoreCase = true)
        }
    }

    // 🔹 Ambil batas pinjam per tier
    fun getLoanLimit(tier: Tier): Int {
        return when (tier) {
            Tier.REGULAR -> 2
            Tier.PREMIUM -> 5
            Tier.STAFF -> 10
        }
    }

    // 🔹 Ambil lama pinjam per tier
    fun getLoanDuration(tier: Tier): Int {
        return when (tier) {
            Tier.REGULAR -> 7
            Tier.PREMIUM -> 14
            Tier.STAFF -> 30
        }
    }

    // 🔹 Lihat semua anggota
    fun listAllMembers(): List<Anggota> = memberRepo.findAll()
}
