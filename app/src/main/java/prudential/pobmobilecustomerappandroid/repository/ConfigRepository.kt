package prudential.pobmobilecustomerappandroid.repository

import android.app.Application
import prudential.pobmobilecustomerappandroid.db.AppDBSingleton
import prudential.pobmobilecustomerappandroid.model.Config

/**
 * Repository to manage database and netowork access and returns data relative to [Config] object.
 * @constructor Receive context to be used on databse instance creation.
 * @property application Contex to be used on database creation.
 */
class ConfigRepository(val application: Application) {

    private val configDao by lazy {
        val db = AppDBSingleton.getInstance(application)
        db.ConfigDao()
    }

    /**
     * Get current configuration of device/user.
     * @return Current [Config] object.
     */
    suspend fun getCurrent(): Config = configDao.getCurrent()

    /**
     * Check if already has a config object inserted on database.
     * @return true if exists, false if not.
     */
    suspend fun isConfigExists(): Boolean {
        val config = getCurrent()
        return config != null
    }

    /**
     * Insert a new config based on model object.
     * @param config [Config] object based on model.
     * @return Id of object created.
     */
    suspend fun insert(config: Config): Long = configDao.insert(config)

    /**
     * Update existing config row based on [Config] model.
     * @param config [Config] object based on model.
     */
    suspend fun update(config: Config): Unit = configDao.update(config)

    /**
     * Get current config row and update it's value (isToken). This value represents if user has marked
     * to ask or not token on login.
     * @param value True if user want to receive token, false if not.
     */
    suspend fun onUpdateConfigUpdateToken(value: Boolean) {
        val config = getCurrent()
        config.isToken = value
        configDao.update(config)
    }

    /**
     * Get current config row and update it's value (isTouch). This value represents if user has marked
     * to ask or not to login with Touch Id.
     * @param value True if user want to login with Touch Id, false if not.
     */
    suspend fun onUpdateConfigUpdateTouch(value: Boolean) {
        val config = getCurrent()
        config.isTouch = value
        configDao.update(config)
    }

    /**
     * Get current config row and update it's value (isTouchIdSave). This value represents if user has marked
     * to ask or not to login with Touch Id.
     * @param value True if is saved, false if not.
     */
    suspend fun onUpdateConfigUpdateTouchBtn(value: Boolean) {
        val config = getCurrent()
        config.isTouchIdSave = value
        configDao.update(config)
    }

    /**
     * Check if Touch Id is enable to ask user on login screen.
     * @return true if Touch Id is enable, false if not.
     */
    suspend fun isTouchIdEnable(): Boolean {
        val config = getCurrent()
        return config.isTouch
    }

    /**
     * Disable Touch Id, to not ask user when login screen is showing.
     */
    suspend fun disableTouchId() {
        val config = getCurrent()
        config.isTouch = false
        update(config);
    }

}