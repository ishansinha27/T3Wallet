package com.example.t3_wallet

import com.example.t3_wallet.UserData
import com.example.t3_wallet.WalletData



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserData::class, WalletData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDataDao(): UseraDataDAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}