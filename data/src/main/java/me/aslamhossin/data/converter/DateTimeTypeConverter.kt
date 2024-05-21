package me.aslamhossin.data.converter

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
/**
 * A custom serializer for Joda-Time's DateTime class.
 */
object DateTimeSerializer : KSerializer<DateTime> {
    private val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DateTime", PrimitiveKind.STRING)

    /**
     * Serializes a DateTime object to a string.
     *
     * @param encoder The encoder to use for serialization.
     * @param value The DateTime object to serialize.
     */
    override fun serialize(encoder: Encoder, value: DateTime) {
        encoder.encodeString(value.toString(formatter))
    }

    /**
     * Deserializes a string to a DateTime object.
     *
     * @param decoder The decoder to use for deserialization.
     * @return The deserialized DateTime object.
     */
    override fun deserialize(decoder: Decoder): DateTime {
        return formatter.parseDateTime(decoder.decodeString())
    }
}
