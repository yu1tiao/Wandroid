package com.pretty.core.http

import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.*

/**
 * @author mathew
 * @date 2019/11/18
 */
object HttpsManager {

    /**
     * 单向验证只需要传入服务端证书输入流，其他的传入null
     * 双向认证需要后三个参数
     *
     * @param serverIns    服务端证书
     * @param keyStoreType 客户端证书库类型 如：BKS、PKCS12等，android支持未BKS
     * @param clientIns    客户端证书库
     * @param passWord     证书库密码
     * @return
     */
    fun getSSLCertification(
        serverIns: InputStream,
        keyStoreType: String?,
        clientIns: InputStream?,
        passWord: String?
    ): SSLSocketFactory? {
        var sslSocketFactory: SSLSocketFactory? = null
        try {
            val trustManagers = prepareTrustManager(serverIns)
            val keyManagers = prepareKeyManager(keyStoreType, clientIns, passWord)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(keyManagers, trustManagers, SecureRandom())
            sslSocketFactory = sslContext.socketFactory

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sslSocketFactory
    }

    /**
     * 双向验证时，加载客户端证书
     *
     * @param keyStoreType BKS等
     * @param keyStoreIs
     * @param password
     * @return
     */
    private fun prepareKeyManager(
        keyStoreType: String?,
        keyStoreIs: InputStream?,
        password: String?
    ): Array<KeyManager>? {
        try {
            if (keyStoreIs == null || password == null) return null

            val clientKeyStore = KeyStore.getInstance(keyStoreType)
            clientKeyStore.load(keyStoreIs, password.toCharArray())
            keyStoreIs.close()

            //            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            val keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(clientKeyStore, password.toCharArray())

            return keyManagerFactory.keyManagers
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 校验服务端证书，准备TrustManager
     *
     * @param certificates 服务端证书输入流
     * @return
     */
    private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager>? {
        if (certificates.isEmpty()) return null
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            for ((index, certificate) in certificates.withIndex()) {
                val certificateAlias = index.toString()
                keyStore.setCertificateEntry(
                    certificateAlias,
                    certificateFactory.generateCertificate(certificate)
                )
                try {
                    certificate.close()
                } catch (e: IOException) {
                }

            }
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)

            return trustManagerFactory.trustManagers
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun chooseTrustManager(trustManagers: Array<TrustManager>): X509TrustManager? {
        for (trustManager in trustManagers) {
            if (trustManager is X509TrustManager) {
                return trustManager
            }
        }
        return null
    }
}
