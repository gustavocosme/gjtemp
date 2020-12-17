package prudential.pobmobilecustomerappandroid.db

import android.content.Context
import androidx.room.Room
import prudential.pobmobilecustomerappandroid.db.dao.ConfigDao
import prudential.pobmobilecustomerappandroid.db.dao.UserDao
import prudential.pobmobilecustomerappandroid.model.Config
import prudential.pobmobilecustomerappandroid.model.User

/**
 * Simple singleton to get unique instance of Database, and then get any Dao and use it's functions.
 * Using object keyword to create an unique instance.
 */
object AppDBSingleton {

    lateinit var DB: AppDB;
    lateinit var tableConfig: ConfigDao;
    lateinit var tableUser: UserDao;
    var user: User? = null;
    var config: Config? = null;

    /**
     * Build Database with Room DB and set basic configuration.
     * @return instance of Room DB.
     */
    fun getInstance(context: Context) = Room.databaseBuilder(
        context,
        AppDB::class.java,
        "pudential11-db"
    )
        .fallbackToDestructiveMigration()
        .build()

//
//    fun inicialize(context: Context): AppDBSingleton {
//        DB = getInstance(context);
//        tableUser = DB.userDao();
//        tableConfig = DB.ConfigDao();
//        user = tableUser.getCurrentUser();
//        config = tableConfig.getCurrent();
//
//        if (!isConfig()) {
//            var config = Config();
//            config.isTouch = false;
//            config.isTouchIdSave = false;
//            tableConfig.insert(config)
//            config = tableConfig.getCurrent();
//        }
//
//        return this;
//
//    }

//    fun isUser(): Boolean {
//
//        user.let {
//
//            if (it == null)
//                return false;
//
//            return true
//
//        }
//
//    }

//
//    fun isConfig(): Boolean {
//
//        config.let {
//
//            if (it == null)
//                return false;
//
//            return true
//
//        }
//    }

//    fun onUpdateConfigUpdateToken(value: Boolean) {
//        config!!.isToken = value;
//        tableConfig.update(config!!)
//    }
//
//    fun onUpdateConfigUpdateTouch(value: Boolean) {
//        config!!.isTouch = value;
//        tableConfig.update(config!!)
//    }
//
//    fun onUpdateConfigUpdateTouchBtn(value: Boolean) {
//        config!!.isTouchIdSave = value;
//        tableConfig.update(config!!)
//    }

//    fun onLogout() {
//        user = null;
//        tableUser.deleteAll();
//        config!!.isTouch = false;
//        tableConfig.update(config!!);
//
//    }


}//END CLASS