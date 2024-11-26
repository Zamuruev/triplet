package com.triplet.models

import java.io.Serializable

data class User(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String
) : Serializable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: "",
//        parcel.readString() ?: ""
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(firstName)
//        parcel.writeString(lastName)
//        parcel.writeString(email)
//        parcel.writeString(password)
//    }
//
//    override fun describeContents(): Int = 0
//
//    companion object CREATOR : Parcelable.Creator<User> {
//        override fun createFromParcel(parcel: Parcel): User = User(parcel)
//        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
//    }
}
