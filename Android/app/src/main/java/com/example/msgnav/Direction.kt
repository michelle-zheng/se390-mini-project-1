package com.example.msgnav

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Direction(
    val icon: Int,
    val directionText: String,
    val distance: String
) : Parcelable