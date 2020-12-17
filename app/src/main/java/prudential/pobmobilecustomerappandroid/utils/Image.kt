package prudential.pobmobilecustomerappandroid.utils

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import java.util.*

object Image {

    fun load(image: ImageView?, url: String?) {
        val options: DisplayImageOptions
        options = DisplayImageOptions.Builder()
            //.showImageOnLoading(R.drawable.holder)
            //.showImageForEmptyUri(R.drawable.holder)
            //.showImageOnFail(R.drawable.holder)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build()
        val animateFirstListener: ImageLoadingListener = AnimateFirstDisplayListener()
        ImageLoader.getInstance().displayImage(url, image, options, animateFirstListener)
    }

    private class AnimateFirstDisplayListener : SimpleImageLoadingListener() {
        override fun onLoadingComplete(
            imageUri: String,
            view: View,
            loadedImage: Bitmap
        ) {
            if (loadedImage != null) {
                val imageView = view as ImageView
                val firstDisplay =
                    !displayedImages.contains(imageUri)
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 1000)
                    displayedImages.add(imageUri)
                }
            }
        }

        companion object {
            val displayedImages =
                Collections.synchronizedList(LinkedList<String>())
        }
    }


}