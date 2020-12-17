package prudential.pobmobilecustomerappandroid.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import prudential.pobmobilecustomerappandroid.model.User

/**
 *  * This intercace allows manage [User] by providing CRUD operations using @Dao and
 * others annotations.
 */
@Dao
interface UserDao {

    /**
     * Select all fields and all entries from [User].
     * @return list of users.
     */
    @Query("select * from user")
    suspend fun getUsers(): List<User>

    /**
     * Insert new user based on [User] model.
     * @param user [User]
     * @return the id of inserted user, if operation was performed successfully.
     */
    @Insert
    suspend fun insert(user: User): Long

    /**
     * Insert many users.
     * @param users List of users to be inserted.
     */
    @Insert
    suspend fun insertAll(users: List<User>)

    /**
     * Update with user based on [User] model
     * @param user [User] object to be updated. This object must contain ID to correctly update.
     */
    @Update
    suspend fun update(user: User)

    /**
     * Delete all entries from DB.
     */
    @Query("delete from user")
    suspend fun deleteAll()

    /**
     * Select all fields from [User] ordering by descendent and limiting to one register
     * @return Current user provided by query.
     */
    @Query("select * from user order by id desc limit 1")
    suspend fun getCurrentUser(): User

    /*
    companion object {
        lateinit var CURRENT: UserDao;
    }
    */
}