package no.javatec.storage

import io.kotest.assertions.asClue
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.objectstorage.ObjectStorageOperations
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Named

@MicronautTest
class IncomingDocumentStorageTest(
    sut: SomeStorage,
    @Named("my-storage") sourceStorage: ObjectStorageOperations<*, *, *>
) : BehaviorSpec({

    Given("empty target bucket") {
        sourceStorage.deleteAllObjects()

        When("saveToSourceBucket") {
            val uploadResponse = sut.saveToSourceBucket(
                "someBody",
                blobName = BLOB_NAME_IN_TEST
            )

            Then("uploadResponse should contain expected blob name") {
                uploadResponse.asClue {
                    it.key.shouldBe(BLOB_NAME_IN_TEST)
                }
            }

            And("should be able to retrieve object from bucket") {
                val blobNames = sourceStorage.listObjects()

                "blobNames should contain one element".asClue {
                    blobNames.size.shouldBe(1)
                }

                blobNames.first().asClue {
                    it.shouldBe(BLOB_NAME_IN_TEST)
                }

                sourceStorage.retrieveObjectAsString(blobNames.first()).asClue {
                    it shouldBe "someBody"
                }
            }
        }
    }
}) {
    companion object {
        private const val BLOB_NAME_IN_TEST = "blobName.json"
    }
}

