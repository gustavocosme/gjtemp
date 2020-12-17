package prudential.pobmobilecustomerappandroid.interfaces

/**
 * Interface for a callback to be invoked when item on recycler view is clicked.
 */
interface OnRecyclerClickListener<T> {

    /**
     * Called when item on recycler view is clicked.
     * @param model Model object generics.
     * @param position Position of item clicked on list.
     */
    fun onRecyclerItemClick(model: T, position: Int)

    interface ChckeckListener <T> {

        fun onChckedItem(item: T, position: Int)

    }

}