package prudential.pobmobilecustomerappandroid.ui.activity.app

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController
import com.nineoldandroids.view.ViewHelper
import gustavocosme.ui.BaseFragment
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.main_nav.*
import kotlinx.android.synthetic.main.toolbar_app.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnUploadFile
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.ui.fragment.ExtractFragment
import prudential.pobmobilecustomerappandroid.ui.viewCustom.DataFilterExtract
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard


/**
 * Activity to manage Tabs nagivation and manager states and screen interaction.\n
 * Open class to be extended by another class.
 */
open class Tab : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener,
    FragNavController.RootFragmentListener,
    FragNavController.TransactionListener, DrawerLayout.DrawerListener {

    val INDEX_1 = FragNavController.TAB1;
    val INDEX_2 = FragNavController.TAB2;
    val INDEX_3 = FragNavController.TAB3;
    val INDEX_4 = FragNavController.TAB4;
    val INDEX_5 = FragNavController.TAB5;

    private lateinit var onUploadFile: OnUploadFile

    var fragNavController: FragNavController? = null

    var animeSlide = FragNavTransactionOptions.newBuilder().customAnimations(
        R.anim.right_to_left_in,
        R.anim.right_to_left_out,
        R.anim.left_to_right_in,
        R.anim.left_to_right_out
    ).build()

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_nav);
        if (savedInstanceState != null) {
            return;
        }
        INSTANCE = this;

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, menuSlide)
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, slideNav)
        drawer_layout.addDrawerListener(this);
        //drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,slideNav)

        initTopo();
        initFragment(savedInstanceState)
        initBottomNavigation();

        // onOpenPagamentForm();
//        onOpenPeriod();


    }

    /**
     * Handle click event when user clicks on hamburger menu.
     * Open if it's closed, and close if its opend.
     * @param v View of layout.
     */
    fun onClickMenuSlideToggle(v: View) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }


    /**
     * Initiate bottom navigation click listener.
     */
    private fun initBottomNavigation() {
        bottomBar.setOnNavigationItemSelectedListener(this)
        bottomBar.setOnNavigationItemReselectedListener(this)
        bottomBar.setSelectedItemId(R.id.m2);
        //bottomBar.setSelectedItemId(R.id.m4);
        //bottomBar.setSelectedItemId(R.id.m5);
    }

    /**
     * Override method to handle item clicked on bottom menu.
     * @param item Item clicked by user.
     * @return True if click is enable.
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.m1 -> fragNavController?.switchTab(INDEX_1);
            R.id.m2 -> fragNavController?.switchTab(INDEX_2);
            R.id.m3 -> fragNavController?.switchTab(INDEX_3);
            R.id.m4 -> fragNavController?.switchTab(INDEX_4);
            R.id.m5 -> fragNavController?.switchTab(INDEX_5);
        }

        /*

        when (item.itemId) {
            R.id.m1 -> drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,slideNav)
            R.id.m2 -> drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,slideNav)
            R.id.m3 -> drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,slideNav)
            R.id.m4 -> drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,slideNav);
            R.id.m5 -> drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,slideNav)
        }


         */


        return true
    }

    /**
     * Handle item reselect.
     * @param item Item clicked by user.
     */
    override fun onNavigationItemReselected(item: MenuItem) {
        fragNavController?.clearStack()
    }

    /**
     * Overrided method to handle and check if fragNavController is not null.
     * @param outState State of screen.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController?.let {
            fragNavController?.onSaveInstanceState(outState)
        }
    }

    /**
     * Handle witch item is selected.
     * @param item Item selected by user.
     * @return Tur to enable click event.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {

                Keyboard.removeThis(this);
                fragNavController?.popFragment(animeSlide)
            }
        }
        return true
    }

    /**
     * Handle event when user click on "back" native button.
     * If menu is open, close it before perform back event.
     * If menu is closed, back to home screen.
     */
    override fun onBackPressed() {
        Keyboard.removeThis(this);
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END);
        } else {
            fragNavController?.let {
                if (!it.popFragment(animeSlide)) {
                    val startMain = Intent(Intent.ACTION_MAIN)
                    startMain.addCategory(Intent.CATEGORY_HOME)
                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(startMain)
                }
            }
        }
    }

    /**
     * Handle switch between itens in bottom menu.
     * @param savedInstanceState State of screen.
     */
    fun initFragment(savedInstanceState: Bundle?) {
        fragNavController = FragNavController.newBuilder(
            savedInstanceState,
            supportFragmentManager,
            R.id.container
        )
            .switchController { i, fragNavTransactionOptions ->
                when (i) {
                    0 -> bottomBar.setSelectedItemId(R.id.m1);
                    1 -> bottomBar.setSelectedItemId(R.id.m2);
                    2 -> bottomBar.setSelectedItemId(R.id.m3);
                    3 -> bottomBar.setSelectedItemId(R.id.m4);
                    4 -> bottomBar.setSelectedItemId(R.id.m5);
                }
            }
            .popStrategy(FragNavTabHistoryController.UNLIMITED_TAB_HISTORY)
            .transactionListener(this)
            //.defaultTransactionOptions(FragNavTransactionOptions.newBuilder() .customAnimations(R.anim.right_to_left_in, 0, 0, R.anim.right_to_left_out).build())
            .rootFragmentListener(this, 5)
            .build()

        fragNavController?.executePendingTransactions()
    }

    /**
     *  Get instance of current fragment index.
     *  @param index Index of fragment to get instance.
     *  @throws IllegalStateException if index is not valid.
     */
    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_1 -> return ExtractFragment.newInstance(0)
            INDEX_2 -> return ExtractFragment.newInstance(0)
            INDEX_3 -> return ExtractFragment.newInstance(0)
            INDEX_4 -> return ExtractFragment.newInstance(0)
            INDEX_5 -> return ExtractFragment.newInstance(0)
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    /**
     * Overrided method to handle fragment transactions.
     * @param fragment Fragment to be replaced.
     * @param transactionType Type of Transaction.
     */
    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        displayHomeAsUpEnabled(fragment)
    }

    /**
     * Handle tab transaction with fragment.
     * @param fragment Current fragment.
     * @param index Current index.
     */
    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        displayHomeAsUpEnabled(fragment)
    }

    /**
     * Display arrow button in current fragment.
     * @param fragment Fragment to be displayed arrow button.
     */
    private fun displayHomeAsUpEnabled(fragment: Fragment?) {
        val base = fragment as BaseFragment

        if (base.title.equals("Menu")) {
            kotlin.run {

                bar.showOrGone(false);

            }

        } else {

            bar.showOrGone(true);
        }

        if (!base.isLogo!!) {
            toolbar_btn_notification.showOrGone(false)
            logo.showOrGone(false)
            toolbar_title.showOrGone(true)
            toolbar_title.text = base.title;

        } else {

            toolbar_btn_notification.showOrGone(true)
            logo.showOrGone(true)
            toolbar_title.showOrGone(false)

        }

        if (supportActionBar != null && fragNavController != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(!fragNavController!!.isRootFragment)
        }
    }

    /**
     * Clear navigation stack.
     */
    fun onPop() {
        Keyboard.removeThis(this);
        fragNavController?.clearStack()
    }

    /**
     * Add gragment to stack.
     * @param f Fragment to be added.
     */
    fun nav(f: Fragment) {
        fragNavController?.pushFragment(f, animeSlide);
    }

    /**
     * Change toolbar to a custom one and set listener to left arrow button.
     */
    fun initTopo() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        try {
            val backArrow =
                resources.getDrawable(R.drawable.ic_back)
            supportActionBar!!.setHomeAsUpIndicator(backArrow)
        } catch (e: Exception) {
        }
        //btnCloseSlide.setOnClickListener { onBackPressed() };

        ViewHelper.setAlpha(logo, 0f);
        com.nineoldandroids.view.ViewPropertyAnimator.animate(logo)
            .setDuration(1000)
            .alpha(1.0f)

    }

    /**
     * Open slide Filter extract
     */

    fun onOpenPagamentForm(policies: List<PolicyBasicModel>) {

        slideNav.inicializeFormPayment(policies);
        drawer_layout.openDrawer(GravityCompat.END);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, slideNav)
    }


    /**
     * Open slide Filter extract
     */
    fun onOpenFilterExtract(call: (value: DataFilterExtract) -> Unit) {
        slideNav.inicializeExtractFilter();
        slideNav.extractFilter!!.call = call;
        drawer_layout.openDrawer(GravityCompat.END);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, slideNav)
    }

    /**
     * Open slide Pagament update Period
     */
    fun onOpenPeriod(policies: List<PolicyBasicModel>) {
        slideNav.inicializePeriod(policies);
        drawer_layout.openDrawer(GravityCompat.END);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, slideNav)

    }


    /**
     * Close slide Filter extract
     */
    fun onCloseSlide() {

        drawer_layout.closeDrawer(GravityCompat.END);
    }

    /**
     * Handle instance of Tab activity.
     * @property INSTANCE Instance of Tab activity.
     */
    companion object {
        lateinit var INSTANCE: Tab;
    }

    override fun onDrawerStateChanged(newState: Int) {

    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerClosed(drawerView: View) {

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, slideNav)
        slideNav.addClear();

    }

    override fun onDrawerOpened(drawerView: View) {

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, slideNav);

    }

    fun setUploadFileListener(onUploadFile: OnUploadFile) {
        this.onUploadFile = onUploadFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            onUploadFile.fileChoosedToUpload(data?.data, requestCode)
        }
    }


}
