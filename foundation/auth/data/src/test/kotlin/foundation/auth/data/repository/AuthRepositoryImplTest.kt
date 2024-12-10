package foundation.auth.data.repository

import foundation.auth.data.datasource.AuthLocalDataSourceImpl
import foundation.auth.data.fakes.FakeAuthRemoteDataSource
import foundation.auth.data.model.RefreshResponse
import foundation.auth.data.model.SignInResponse
import foundation.storage.test.FakeLocalStorage
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthRepositoryImplTest {

    private lateinit var repositoryImpl: AuthRepositoryImpl
    private lateinit var localDataSOurce: AuthLocalDataSourceImpl
    private lateinit var fakeStorage: FakeLocalStorage
    private lateinit var remoteDataSource: FakeAuthRemoteDataSource

    @BeforeTest
    fun setUp() {
        fakeStorage = FakeLocalStorage()

        localDataSOurce = AuthLocalDataSourceImpl(
            localStorage = fakeStorage
        )

        remoteDataSource = FakeAuthRemoteDataSource()

        repositoryImpl = AuthRepositoryImpl(
            localDataSource = localDataSOurce,
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `sign up should call remote data source`() = runTest {
        repositoryImpl.signUp(
            email = "email",
            password = "1234",
            confirmEmail = "email"
        )

        assertEquals(expected = 1, actual = remoteDataSource.signUpCalls)
    }

    @Test
    fun `sign in should call remote data source`() = runTest {
        repositoryImpl.signIn(
            email = "email",
            password = "1234"
        )

        assertEquals(expected = 1, actual = remoteDataSource.signInCalls)
    }

    @Test
    fun `sign in should store access token`() = runTest {
        remoteDataSource.signInResponse = SignInResponse(
            token = "token",
            refreshToken = "refreshToken"
        )

        repositoryImpl.signIn(
            email = "email",
            password = "1234"
        )

        assertEquals(
            expected = "token",
            actual = fakeStorage.stringCallHistory[AuthLocalDataSourceImpl.ACCESS_TOKEN_KEY]
        )
    }

    @Test
    fun `sign in should store refresh token`() = runTest {
        remoteDataSource.signInResponse = SignInResponse(
            token = "token",
            refreshToken = "refreshToken"
        )

        repositoryImpl.signIn(
            email = "email",
            password = "1234"
        )

        assertEquals(
            expected = "refreshToken",
            actual = fakeStorage.stringCallHistory[AuthLocalDataSourceImpl.REFRESH_TOKEN_KEY]
        )
    }

    @Test
    fun `refresh token call should store refresh token`() = runTest {
        fakeStorage.getStringResponse = mutableMapOf(
            AuthLocalDataSourceImpl.REFRESH_TOKEN_KEY to "stored-token"
        )

        remoteDataSource.refreshResponse = RefreshResponse(
            token = "token",
            refreshToken = "refreshToken"
        )

        repositoryImpl.refreshToken()

        assertEquals(
            expected = "refreshToken",
            actual = fakeStorage.stringCallHistory[AuthLocalDataSourceImpl.REFRESH_TOKEN_KEY]
        )
    }

    @Test
    fun `refresh token call should store access token`() = runTest {
        fakeStorage.getStringResponse = mutableMapOf(
            AuthLocalDataSourceImpl.REFRESH_TOKEN_KEY to "stored-token"
        )

        remoteDataSource.refreshResponse = RefreshResponse(
            token = "token",
            refreshToken = "refreshToken"
        )

        repositoryImpl.refreshToken()

        assertEquals(
            expected = "refreshToken",
            actual = fakeStorage.stringCallHistory[AuthLocalDataSourceImpl.REFRESH_TOKEN_KEY]
        )
    }

    @Test
    fun `refresh token call should call data source using stored token`() = runTest {
        fakeStorage.getStringResponse = mutableMapOf(
            AuthLocalDataSourceImpl.REFRESH_TOKEN_KEY to "stored-token"
        )

        remoteDataSource.refreshResponse = RefreshResponse(
            token = "token",
            refreshToken = "refreshToken"
        )

        repositoryImpl.refreshToken()

        assertEquals(
            expected = "stored-token",
            actual = remoteDataSource.lastUsedRefreshToken
        )
    }


    @Test
    fun `refresh token call should return null if no token is stored`() = runTest {
        fakeStorage.getStringResponse = mutableMapOf()

        remoteDataSource.refreshResponse = RefreshResponse(
            token = "token",
            refreshToken = "refreshToken"
        )

        val response = repositoryImpl.refreshToken()

        assertNull(response)
    }
}