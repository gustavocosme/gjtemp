package prudential.pobmobilecustomerappandroid.utils.navigation

import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import prudential.pobmobilecustomerappandroid.R

open class Ac : AppCompatActivity() {
    var mTitle: TextView? = null
    fun initToolbar(titulo: String?) {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
         title = ""
         setDisplayHomeAsUpEnabled(true)
         setDisplayShowHomeEnabled(true)
        }

        mTitle = toolbar.findViewById<View>(R.id.toolbar_title) as TextView
        mTitle?.text = titulo
        try {
            val backArrow = resources.getDrawable(R.drawable.ic_back, theme)
            supportActionBar?.setHomeAsUpIndicator(backArrow)
        } catch (e: Exception) {
        }
    }

    fun setTitle(title: String?) {
        mTitle?.text = title
    }

    override fun onSupportNavigateUp(): Boolean =  true

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}