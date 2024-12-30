package com.example.t3_wallet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UseraDataDAO {

    // Insert a user into the 'users' table (with conflict resolution strategy to replace existing user data)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserData)

    // Insert multiple wallets into the 'wallets' table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallets(wallets: List<WalletData>)

    // A method to insert both the user and the associated wallets together in a transaction
    @Transaction
    suspend fun insertUserAndWallets(user: UserData, wallets: List<WalletData>) {
        // Insert the user first
        insertUser(user)

        // Insert the associated wallets
        insertWallets(wallets)
    }

    // Query to get all users (if you need to retrieve them)
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserData>>

    // Query to get all wallets for a specific user
    @Query("SELECT * FROM wallets WHERE userId = :username")
    fun getWalletsForUser(username: String): LiveData<List<WalletData>>

    @Query("SELECT walletAddress FROM wallets WHERE userId = :username AND type = 'SOID'")
    fun getSOIDWalletAddressesForUser(username: String): LiveData<String>

}
