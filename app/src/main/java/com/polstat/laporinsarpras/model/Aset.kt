package com.polstat.laporinsarpras.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val module = SerializersModule {
    contextual(LocalDate::class, LocalDateSerializer)
}
val json = Json { serializersModule = module }

@Serializable
data class Aset(
    var barangId: String,
    var nama: String,
    @Contextual var tglPerolehan: LocalDate,
    var kondisi: String,
    var ruangId: String
)

@Serializer(forClass = LocalDate::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}
