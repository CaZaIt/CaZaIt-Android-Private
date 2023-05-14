package org.cazait.network

import android.net.ConnectivityManager

interface NetworkConnectivity {
    fun getConnectivityManager(): ConnectivityManager
    fun isConnected(): Boolean
}
