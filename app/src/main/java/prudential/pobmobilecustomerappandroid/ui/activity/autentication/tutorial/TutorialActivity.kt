package prudential.pobmobilecustomerappandroid.ui.activity.autentication.tutorial

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_tutorial.*
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.ui.activity.autentication.LoginActivity

class TutorialActivity : AppCompatActivity() {

    lateinit var pager: ViewPager;
    lateinit var adapter: TutorialPageAdp;
    lateinit var balls: CirclePageIndicator;
    var current = 0;
    var count = 4;

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        adapter = TutorialPageAdp(this);
        logo.showAnime(1300)

        pager.adapter = adapter;
        pager.currentItem = current;
        balls.setViewPager(pager);
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                current = position;
                adapter.onChange(position);
                isValidation();
            }
        })

    }


    /**
     * Go to the next screen when user cliks on rigth arrow.
     * @param v View clicked by user.
     */
    fun onClickNext(v: View) {
        current = current + 1;
        if (current > count - 1) {
            current = count - 1
        }
        pager.currentItem = current;
        isValidation()
    }
    /**
     * Go to the preview screen when user cliks on left arrow.
     * @param v View clicked by user.
     */
    fun onClickPrev(v: View) {
        current = current - 1;
        if (current <= 0) {
            current = 0;
        }
        pager.currentItem = current;
        isValidation();
    }

    /**
     * Check wich screen user is and show message according to context.
     */
    fun isValidation() {

        if (current == 0) {
            btnNext.alpha = 1.0f
            btnPrev.alpha = 0.45f
        } else {
            btnPrev.alpha = 1.0f
            btnNext.alpha = 1.0f
        }

        if (current == count - 1) {
            btnPrev.alpha = 1.0f
            btnNext.alpha = 0.45f
            btnStart.setText("Começar")
        } else {
            btnStart.setText("Pular")

        }
    }

    /**
     * Go to login activity and finish tutorial.
     * @param v View clicked by user.
     */
    fun onClickStart(v: View) {
        startActivity<LoginActivity>()
    }

}