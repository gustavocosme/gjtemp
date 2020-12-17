package prudential.pobmobilecustomerappandroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Base data class to create a [Config] object and to use as a Entity with RoomDB.
 * @constructor Create a complete object.
 * @property id ID to be auto generate in db.
 * @property isTouch Define if Touch ID is enable.
 * @property touchIdCount Count tries of Touch ID.
 * @property isToken Define if token will be asked or not.
 */
@Entity
data class Config(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var isTouch: Boolean = true,
    var touchIdCount: Int = 0,
    var isTouchIdSave: Boolean = false,
    var isToken: Boolean = false

) : Serializable

