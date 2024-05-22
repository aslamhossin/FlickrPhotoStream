package me.aslamhossin.flickrgallery.ui.util.permission

sealed class PermissionType {
    data object Storage : PermissionType()
    data object Camera : PermissionType()
    data object Location : PermissionType()
    data object Notifications : PermissionType()
}