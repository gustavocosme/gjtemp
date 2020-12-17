package prudential.pobmobilecustomerappandroid.model

import prudential.pobmobilecustomerappandroid.ui.adapter.UserConfirmationUpdateAdapter

data class DataConfirmationUpdate(
    val type: UserConfirmationUpdateAdapter.DataConfirmationUpdateType = UserConfirmationUpdateAdapter.DataConfirmationUpdateType.ITEM,
    var label1: String = "",
    var current: String = "",
    var hold: String = "",
    var isUpdate: Boolean = false
)
{

    fun initHold(){

        hold = current;

    }

}