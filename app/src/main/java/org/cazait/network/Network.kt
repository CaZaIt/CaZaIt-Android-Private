package org.cazait.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class Network @Inject constructor(val context: Context) : NetworkConnectivity {
    override fun getConnectivityManager() = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun isConnected(): Boolean {
        val info = getConnectivityManager()
        return info.isDefaultNetworkActive
    }
}
