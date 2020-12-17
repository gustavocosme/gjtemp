package prudential.pobmobilecustomerappandroid.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import prudential.pobmobilecustomerappandroid.model.Config

/**
 * This intercace allows manage [Config] by providing CRUD operations using [Dao] and
 * others annotations.
 */

@Dao
interface ConfigDao {

    /**
     * Insert new configuration based on [Config] model.
     * @param config [Config] Object to be inserted.
     * @return the id of inserted config, if operation was performed successfully.
     */
    @Insert
    suspend fun insert(config: Config): Long

    /**
     * Update with configuration based on [Config] model
     * @param config [Config] object to be updated. This object must contain ID to correctly update.
     */
    @Update
    suspend fun update(config: Config)

    /**
     * Delete all entries from DB.
     */
    @Query("delete from config")
    suspend fun deleteAll()

    /**
     * Select all fields from [Config] ordering by descendent and limiting to one register
     * @return Current config provided by query.
     */
    @Query("select * from config order by id desc limit 1")
    suspend fun getCurrent(): Config


    /*
    companion object {
        lateinit var CURRENT: ConfigDao;
        fun updateCurrent() {
            ConfigDao.CURRENT.update(Config.CURRENT!!);
        }
    }

     */
}