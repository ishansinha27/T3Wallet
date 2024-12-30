package com.example.t3_wallet.retrofit

import com.google.gson.annotations.SerializedName

data class PostModel(

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("seedPhrase")
    var seedPhrase: String? = null,

    @SerializedName("ETH")
    var eth: List<Wallet>? = null,

    @SerializedName("SOLANA")
    var solana: List<Wallet>? = null,

    @SerializedName("SOID")
    var soid: List<Wallet>? = null,

    @SerializedName("BTC")
    var btc: List<Wallet>? = null
)

data class Wallet(

    @SerializedName("wallet")
    var wallet: String? = null,

    @SerializedName("privateKey")
    var privateKey: String? = null,

    @SerializedName("Sno")
    var sno: Int? = null
)



