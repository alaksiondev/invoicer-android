package io.github.alaksion.invoicer.features.beneficiary.services.data.datasource

import io.github.alaksion.invoicer.features.beneficiary.services.data.model.BeneficiariesData
import io.github.alaksion.invoicer.features.beneficiary.services.data.model.BeneficiaryData
import io.github.alaksion.invoicer.features.beneficiary.services.data.model.CreateBeneficiaryData
import io.github.alaksion.invoicer.features.beneficiary.services.data.model.UpdateBeneficiaryData
import io.github.alaksion.invoicer.foundation.network.client.HttpWrapper
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal interface BeneficiaryRemoteDataSource {
    suspend fun createBeneficiary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String,
    )

    suspend fun getBeneficiaries(
        limit: Long,
        page: Long
    ): BeneficiariesData

    suspend fun getBeneficiaryDetails(
        id: String
    ): BeneficiaryData

    suspend fun deleteBeneficiary(
        id: String
    )

    suspend fun updateBeneficiary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String,
    )
}

internal class BeneficiaryRemoteDataSourceImpl(
    private val httpWrapper: HttpWrapper,
    private val dispatcher: CoroutineDispatcher
) : BeneficiaryRemoteDataSource {

    override suspend fun createBeneficiary(
        name: String,
        swift: String,
        iban: String,
        bankName: String,
        bankAddress: String
    ) = withContext<Unit>(dispatcher) {
        httpWrapper.client.post(
            urlString = "/v1/beneficiary",
            block = {
                contentType(ContentType.Application.Json)
                setBody(
                    CreateBeneficiaryData(
                        name = name,
                        swift = swift,
                        iban = iban,
                        bankName = bankName,
                        bankAddress = bankAddress
                    )
                )
            }
        )
    }

    override suspend fun getBeneficiaries(
        limit: Long,
        page: Long
    ): BeneficiariesData =
        withContext(dispatcher) {
            httpWrapper.client.get(
                urlString = "/v1/beneficiary",
                block = {
                    parameter("page", page.toString())
                    parameter("limit", limit.toString())
                }
            )
        }.body()

    override suspend fun getBeneficiaryDetails(id: String): BeneficiaryData =
        withContext(dispatcher) {
            httpWrapper.client.get(
                urlString = "/v1/beneficiary/${id}",
            )
        }.body()

    override suspend fun deleteBeneficiary(id: String) {
        withContext(dispatcher) {
            httpWrapper.client.delete(
                urlString = "/v1/beneficiary/${id}",
            )
        }
    }

    override suspend fun updateBeneficiary(
        id: String,
        name: String,
        bankName: String,
        bankAddress: String,
        swift: String,
        iban: String
    ) {
        withContext(dispatcher) {
            httpWrapper.client.put(
                urlString = "/v1/beneficiary/${id}",
                block = {
                    setBody(
                        UpdateBeneficiaryData(
                            name = name,
                            swift = swift,
                            iban = iban,
                            bankName = bankName,
                            bankAddress = bankAddress
                        )
                    )
                }
            )
        }
    }

}
