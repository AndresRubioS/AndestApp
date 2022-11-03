package com.example.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Listas(
    var titulo:String = "",
    var ListaNombre:String = ""
) : Parcelable