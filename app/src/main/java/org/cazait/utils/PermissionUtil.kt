package org.cazait.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class PermissionUtil @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun hasLocationPermissions() =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION,
                // ACCESS_BACKGROUND_LOCATION
            )
        }
}
