package prudential.pobmobilecustomerappandroid

import android.content.Context
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import prudential.pobmobilecustomerappandroid.model.Config
import prudential.pobmobilecustomerappandroid.repository.ConfigRepository
import prudential.pobmobilecustomerappandroid.utils.model.Preference

class App : android.app.Application() {

    lateinit var pref: Preference

    override fun onCreate() {

        super.onCreate()
        App.APP = this;
        checkIfConfigIsNullAndSet()
        pref = Preference(
            applicationContext
        )

        initImageLoader(applicationContext)

    }

    fun initImageLoader(context: Context?) {

        val config = ImageLoaderConfiguration.Builder(context)
        config.threadPriority(Thread.MAX_PRIORITY)
        config.denyCacheImageMultipleSizesInMemory()
        config.diskCacheFileNameGenerator(Md5FileNameGenerator())
        config.diskCacheSize(500000 * 1024 * 1024) // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO)
        //config.writeDebugLogs()
        ImageLoader.getInstance().init(config.build())

        //ImageLoader.getInstance().diskCache.clear();
        //ImageLoader.getInstance().memoryCache.clear();
        //ImageLoader.getInstance().destroy();

    }

    private fun checkIfConfigIsNullAndSet() {
        val configRepository = ConfigRepository(this)
        CoroutineScope(Dispatchers.IO).launch {
            val exists = configRepository.isConfigExists()
            if (!exists) {
                val config = Config(
                    isTouch = false,
                    isTouchIdSave = false
                )
                configRepository.insert(config)
            }
        }
    }

    companion object {
        lateinit var APP: App;
        var IS_TEST = true;
    }

}
