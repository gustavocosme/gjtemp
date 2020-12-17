package prudential.pobmobilecustomerappandroid.ui.activity.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.alertCustomTips
import prudential.pobmobilecustomerappandroid.extensions.openExtratSheetDialog
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity.Companion.INSTANCE
import prudential.pobmobilecustomerappandroid.ui.fragment.ContractFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.ExtractFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.HomeFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.MoreFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.UserFragment
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet

/**
 * Class extending [Tab] to manager bottom nagivation.
 */
class MainActivity : Tab() {

    /**
     * @property INSTANCE Gets instance of activity.
     */
    companion object {
        lateinit var INSTANCE: MainActivity;
    }

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        INSTANCE = this;

    }

    /**
     * Indicates which fragment to call according to the index.
     * @param index Index of fragment to be created an instance.
     * @return Fragment created based on index.
     * @throws IllegalStateException if no index has passed.
     */
    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            INDEX_1 -> HomeFragment.newInstance(0)
            INDEX_2 -> UserFragment.newInstance(0)
            INDEX_3 -> ContractFragment.newInstance(0)
            INDEX_4 -> ExtractFragment.newInstance(0)
            INDEX_5 -> MoreFragment.newInstance(0)
            else -> HomeFragment.newInstance(0)
        }
        throw IllegalStateException("Need to send an index that we know")
    }
}
