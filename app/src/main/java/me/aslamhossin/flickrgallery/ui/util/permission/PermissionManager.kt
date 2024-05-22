package me.aslamhossin.flickrgallery.ui.util.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionManager {

    fun checkPermissions(context: Context, permissionList: Array<String>): Boolean {
        return permissionList.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissionList(permissionTypes: List<PermissionType>) =
        getPermissionsForTypes(permissionTypes).toTypedArray()

    private fun getPermissionsForTypes(permissionTypes: List<PermissionType>): List<String> {
        val permissions = mutableSetOf<String>()
        permissionTypes.forEach { permissionType ->
            permissions.addAll(getPermissionsForType(permissionType))
        }
        return permissions.toList()
    }

    private fun getPermissionsForType(permissionType: PermissionType): List<String> {
        return when (permissionType) {
            PermissionType.Storage -> getStoragePermissions()
            PermissionType.Camera -> getCameraPermissions()
            PermissionType.Location -> getLocationPermissions()
            PermissionType.Notifications -> getNotificationPermissions()
        }
    }

    private fun getStoragePermissions(): List<String> {
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            permissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        }
        return permissions
    }

    private fun getCameraPermissions(): List<String> {
        return listOf(Manifest.permission.CAMERA)
    }

    private fun getLocationPermissions(): List<String> {
        val permissions = mutableListOf<String>()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissions
    }

    private fun getNotificationPermissions(): List<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyList()
        }
    }
}
