package service

import model.Anggota
import model.Reservasi
import repository.InMemoryRepo
import java.time.LocalDate
import java.util.LinkedList

class ReservationService(
    private val memberRepo: InMemoryRepo<Anggota>,
    private val reservasiRepo: InMemoryRepo<Reservasi>
) {
    private val queueMap = mutableMapOf<String, LinkedList<String>>() // key: bukuId, value: antrean anggotaId

    fun reserveBook(anggotaId: String, bukuId: String): String {
        val anggota = memberRepo.findById(anggotaId) ?: return "❌ Anggota tidak ditemukan."
        val queue = queueMap.getOrPut(bukuId) { LinkedList() }
        if (queue.contains(anggotaId)) return "⚠️ ${anggota.nama} sudah dalam daftar antrian."
        queue.add(anggotaId)

        reservasiRepo.save(
            Reservasi("RS-${reservasiRepo.findAll().size + 1}", bukuId, anggotaId, LocalDate.now())
        )

        return "📝 ${anggota.nama} berhasil mendaftar reservasi buku $bukuId (posisi ke-${queue.size})."
    }

    fun popNextReservation(bukuId: String): Anggota? {
        val queue = queueMap[bukuId] ?: return null
        val nextId = queue.poll() ?: return null
        return memberRepo.findById(nextId)
    }

    fun listReservations(): Map<String, List<String>> {
        return queueMap.mapValues { (_, queue) ->
            queue.mapNotNull { memberRepo.findById(it)?.nama }
        }
    }
}
