package com.polstat.laporinsarpras.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class Pengaduan(
    val pengaduanId: Long,
    val emailPelapor: String,
    val emailTeknisi: String?,
    val barangId: String,
    val ruangId: String,
    val tanggal: List<Int>,
    val prioritas: String,
    val status: String,
    val keterangan: String?,
    val deskripsi: String
) {
    val date: LocalDate
        get() = LocalDate.of(tanggal[0], tanggal[1], tanggal[2])
}

//@Serializable
//data class Pengaduan(
//    val pengaduanId: Long,
//    val emailPelapor: String,
//    val emailTeknisi: String?,
//    val barangId: String,
//    val ruangId: String,
//    @Serializable(with = LocalDateSerializer::class)
//    val tanggal: LocalDate,
//    val prioritas: String,
//    val status: String,
//    val keterangan: String,
//    val deskripsi: String
//)
//
//@Serializer(forClass = LocalDate::class)
//object LocalDateSerializer : KSerializer<LocalDate> {
//    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
//
//    override fun serialize(encoder: Encoder, value: LocalDate) {
//        encoder.encodeString(value.format(formatter))
//    }
//
//    override fun deserialize(decoder: Decoder): LocalDate {
//        return LocalDate.parse(decoder.decodeString(), formatter)
//    }
//}