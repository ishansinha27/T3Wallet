package com.example.t3_wallet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserData(
    @PrimaryKey val username: String,
    val password: String,
    val seedPhrase: String
)


@Entity(tableName = "wallets")
data class WalletData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val walletAddress: String,
    val privateKey: String,
    val type: String,

    val userId: String
)
