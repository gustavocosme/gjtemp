package prudential.pobmobilecustomerappandroid.db

import androidx.room.Database
import androidx.room.RoomDatabase
import prudential.pobmobilecustomerappandroid.db.dao.ConfigDao
import prudential.pobmobilecustomerappandroid.db.dao.UserDao
import prudential.pobmobilecustomerappandroid.model.Config
import prudential.pobmobilecustomerappandroid.model.User

/**
 * Base RoomDB class that handle existing Dao's and Entries.
 * All new Model and Dao must added in this class.
 * After any structural change on any model, version number must be inscresed.
 */
@Database(
    entities = [
        User::class,
        Config::class
    ],
    version = 12,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    /**
     * Reference to [UserDao].
     */
    abstract fun userDao(): UserDao

    /**
     * Reference to [ConfigDao].
     */
    abstract fun ConfigDao(): ConfigDao

    companion object{

        lateinit var CURRENT:AppDB;

    }

}