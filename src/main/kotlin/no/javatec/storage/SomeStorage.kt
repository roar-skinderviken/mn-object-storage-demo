package no.javatec.storage

import io.micronaut.objectstorage.ObjectStorageOperations
import io.micronaut.objectstorage.request.UploadRequest
import io.micronaut.objectstorage.response.UploadResponse
import jakarta.inject.Named
import jakarta.inject.Singleton


/**
 * [ObjectStorageOperations] wrapper.
 *
 * @property sourceStorage
 */
@Singleton
class SomeStorage(
    @Named("my-storage") private val sourceStorage: ObjectStorageOperations<*, *, *>,
) {
    fun saveToSourceBucket(
        payload: String,
        blobName: String
    ): UploadResponse<*> = sourceStorage.upload(
        UploadRequest.fromBytes(
            payload.toByteArray(),
            blobName
        )
    )
}
