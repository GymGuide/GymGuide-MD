package com.example.gymguide

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize
data class Student(val name: String?, val nim: String?) : Parcelable {

    private companion object : Parceler<Student> {
        override fun Student.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(nim)
        }

        override fun create(parcel: Parcel): Student {
            val name = parcel.readString()
            val nim = parcel.readString()
            return Student(name, nim)
        }
    }
}