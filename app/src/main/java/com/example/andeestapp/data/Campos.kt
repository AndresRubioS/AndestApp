package com.example.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Campos(
    var Nombre:String = "",
    var lista:Boolean= false
) : Parcelable {
}