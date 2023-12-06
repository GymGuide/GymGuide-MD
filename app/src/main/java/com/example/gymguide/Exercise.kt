package com.example.gymguide

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize
data class Exercise(val name: String?, val nim: String?) : Parcelable {

    private companion object : Parceler<Exercise> {
        override fun Exercise.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(nim)
        }

        override fun create(parcel: Parcel): Exercise {
            val name = parcel.readString()
            val nim = parcel.readString()
            return Exercise(name, nim)
        }
    }
}