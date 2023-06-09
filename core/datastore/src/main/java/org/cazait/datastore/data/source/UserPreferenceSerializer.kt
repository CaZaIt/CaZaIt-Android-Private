package org.cazait.datastore.data.source

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import org.cazait.model.local.UserPreference
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class UserPreferenceSerializer : Serializer<UserPreference> {
    override val defaultValue: UserPreference
        get() = UserPreference.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreference {
        return try {
            Json.decodeFromString(
                UserPreference.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPreference", serialization)
        }
    }

    override suspend fun writeTo(t: UserPreference, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(UserPreference.serializer(), t)
                    .encodeToByteArray()
            )
        }
    }

}