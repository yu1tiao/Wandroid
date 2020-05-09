package com.pretty.core.router.entity

import android.os.Parcel
import android.os.Parcelable

data class LoginEntity(
    val username: String?,
    val password: String?,
    val publicName: String?,
    val nickname: String?,
    val email: String?,
    val icon: String?,
    val token: String?,
    val id: Long,
    val type: Int
) : Parcelable {

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readLong(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(username)
        writeString(password)
        writeString(publicName)
        writeString(nickname)
        writeString(email)
        writeString(icon)
        writeString(token)
        writeLong(id)
        writeInt(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<LoginEntity> = object : Parcelable.Creator<LoginEntity> {
            override fun createFromParcel(source: Parcel): LoginEntity = LoginEntity(source)
            override fun newArray(size: Int): Array<LoginEntity?> = arrayOfNulls(size)
        }
    }
}