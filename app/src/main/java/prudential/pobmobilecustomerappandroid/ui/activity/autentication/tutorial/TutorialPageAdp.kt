package prudential.pobmobilecustomerappandroid.ui.activity.autentication.tutorial

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.model.DataTutorial
import java.util.*

/**
 * Class to create custom page for tutorial.
 * @param mContext Context to create page.
 */
class TutorialPageAdp(protected var mContext: Context) : PagerAdapter() {

    private val fragments = ArrayList<RelativeLayout>()
    private val fs = SparseArray<Any>();

    var array = ArrayList<DataTutorial>()

    /**
     * Init method to create all tutorial pages.
     */
    init {

        val a1 = DataTutorial();
        a1.title = "As informações na sua mão e sem complicação"
        a1.description =
            "Aqui você consulta os dados de todos os seus seguros de maneira fácil e direta. "
        a1.icon = R.drawable.ic_tutorial_example;

        val a2 = DataTutorial();
        a2.title = "A rapidez que você precisa para alterar seus dados"
        a2.description =
            "Nosso sistema permite que você faça mudanças em todos os seus seguros de uma só vez. "
        a2.icon = R.drawable.ic_tutorial_2;

        val a3 = DataTutorial();
        a3.title = "Planejar a segurança da sua família nunca foi tão fácil "
        a3.description =
            "Faça uma simulação e saiba o investimento ideal para garantir o futuro de quem você ama. "
        a3.icon = R.drawable.ic_tutorial_example;

        val a4 = DataTutorial();
        a4.title = "Envie documentos importantes com rapidez e segurança"
        a4.description = "Suas informações nas mãos de quem precisar, com eficiência e sem sustos."
        a4.icon = R.drawable.ic_tutorial_2;

        array.add(a1);
        array.add(a2);
        array.add(a3);
        array.add(a4);

        fragments.add(TutorialItem(mContext));
        fragments.add(TutorialItem(mContext));
        fragments.add(TutorialItem(mContext));
        fragments.add(TutorialItem(mContext));

    }

    /**
     * Get size of fragments.
     * @return Size of fragments.
     */
    override fun getCount(): Int = fragments.size

    /**
     * Check if view is equal to object.
     * @param view View to be checked.
     * @param `object` Object to be checked.
     * @return True if is equal, false if not.
     */
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    /**
     * Remove view fragment from list.
     * @param container ViewGroup that will remove object.
     * @param position Position of view in list.
     * @param `object` Object to be removed.
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        fs.remove(position)
        container.removeView(`object` as View)
    }

    /**
     * Add view fragment on position.
     * @param container Container Group to add view.
     * @param position Position to add view on Container group.
     * @return View added.
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val v = fragments.get(position) as TutorialItem;
        v.set(array.get(position))

        container.addView(v)
        fs.put(position, v)
        return v
    }

    /**
     * Change fragment position.
     * @param position Position to be changed.
     */
    fun onChange(position: Int) {
        val v = fragments.get(position) as TutorialItem;
        v.set(array.get(position))
    }

    /**
     * Get title of page.
     * @param position Position of page to get the title.
     * @return Title by position.
     */
    override fun getPageTitle(position: Int): CharSequence? = ""

}
