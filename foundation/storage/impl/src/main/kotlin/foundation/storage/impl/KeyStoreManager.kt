package foundation.storage.impl

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher

interface KeyStoreManager {
    fun encryptValue(value: String): String
    fun decryptValue(value: String): String
}

internal object MutedKeyStoreManagerImpl : KeyStoreManager {

    override fun encryptValue(value: String): String {
        return value
    }

    override fun decryptValue(encodedValue: String): String {
        return encodedValue
    }
}

internal object KeyStoreManagerImpl : KeyStoreManager {

    override fun encryptValue(value: String): String {
        val keyPair = initializeKeyPair()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.publicKey)
        val encryptedBytes = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    override fun decryptValue(encodedValue: String): String {
        val encryptedBytes = Base64.decode(encodedValue, Base64.DEFAULT)

        val keyPair = initializeKeyPair()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, keyPair.privateKey)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        return String(decryptedBytes, StandardCharsets.UTF_8)
    }

    private fun initializeKeyPair(): KeyPair {
        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply {
            load(null)
        }

        if (!keyStore.containsAlias(KEY_ALIAS)) {
            val keyGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                KEYSTORE_PROVIDER
            )

            val parameterSpec = KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).apply {
                setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                setUserAuthenticationRequired(false)
                setRandomizedEncryptionRequired(true)
            }.build()

            keyGenerator.initialize(parameterSpec)
            keyGenerator.generateKeyPair()
        }

        val privateKey = keyStore.getKey(KEY_ALIAS, null) as PrivateKey
        val publicKey = keyStore.getCertificate(KEY_ALIAS).publicKey

        return KeyPair(publicKey, privateKey)
    }


    private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
    private const val KEY_ALIAS = "auth_token_key"
    private const val TRANSFORMATION = "RSA/ECB/PKCS1Padding"


    /**
     * Classe que representa um par de chaves pública/privada
     */
    private data class KeyPair(val publicKey: PublicKey, val privateKey: PrivateKey)
}