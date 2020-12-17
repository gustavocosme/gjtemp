package prudential.pobmobilecustomerappandroid.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Base data class to create a [User] object and to use as a Entity with RoomDB.
 * @constructor Create a complete object.
 * @property id ID to be auto generate in db.
 * @property isLogin Define if user is logged.
 * @property cpf CPF document of user.
 */
@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var isLogin: Boolean = true,
    var cpf: String,
    var name: String = "",
    var matrialStatus: String = "",
    var nationality: String = "",
    var taxAddress: String = "",
    var tin: String = "",
    var rg: String = "",
    var rgData: String = "",
    var rgOrg: String = ""

) : Serializable

