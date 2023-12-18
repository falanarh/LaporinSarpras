package com.polstat.laporinsarpras.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChairAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SmsFailed
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val id:String,
    val title:String,
    val icon: ImageVector,
){
    object Beranda:Screen("beranda","Beranda", Icons.Outlined.Home)
    object Pengaduan:Screen("pengaduan","Pengaduan",Icons.Outlined.SmsFailed)
    object Aset:Screen("aset","Aset",Icons.Outlined.ChairAlt)
    object Profil:Screen("profil","Profil",Icons.Outlined.Person)

    object Items{
        val list= listOf(
            Beranda, Pengaduan, Aset, Profil
        )
    }
}