package com.polstat.laporinsarpras.model

import kotlinx.serialization.Serializable
import java.time.LocalDate

//val module = SerializersModule {
//    contextual(LocalDate::class, LocalDateSerializer)
//}
//val json = Json { serializersModule = module }

@Serializable
data class Aset(
    var barangId: String,
    var nama: String,
//    @Serializable(with = LocalDateSerializer::class)
    var tglPerolehan: List<Int>,
    var kondisi: String,
    var ruangId: String
){
    val datePerolehan: LocalDate
        get() = LocalDate.of(tglPerolehan[0], tglPerolehan[1], tglPerolehan[2])
}
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
