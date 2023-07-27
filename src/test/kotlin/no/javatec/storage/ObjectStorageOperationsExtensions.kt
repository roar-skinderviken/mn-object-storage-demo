package no.javatec.storage

import io.micronaut.objectstorage.ObjectStorageEntry
import io.micronaut.objectstorage.ObjectStorageOperations

/**
 * Delete all objects in bucket.
 *
 * @receiver [ObjectStorageOperations] instance to delete all objects from
 */
fun ObjectStorageOperations<*, *, *>.deleteAllObjects() = this.listObjects().forEach { blobId ->
    this.delete(blobId)
}

fun ObjectStorageOperations<*, *, *>.retrieveObjectAsString(
    blobId: String
) = this.retrieve<ObjectStorageEntry<*>>(blobId).map { storageEntry ->
    storageEntry.inputStream.use { stream ->
        String(stream.readAllBytes())
    }
}.get()