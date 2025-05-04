package uk.ac.tees.mad.e4294395

import android.app.Application
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import uk.ac.tees.mad.e4294395.newsapp.utils.AppConstant.WORKMANAGER_NAME
import uk.ac.tees.mad.e4294395.newsapp.utils.TimeUtils
import uk.ac.tees.mad.e4294395.newsapp.worker.NewsWorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltAndroidApp
class NewsApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        WorkManagerScheduler()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    private fun WorkManagerScheduler() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            PeriodicWorkRequest.Builder(
                NewsWorkManager::class.java,
                24,
                TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .setInitialDelay(TimeUtils.calculateInitialDelay(), TimeUnit.MILLISECONDS)
                .build()
        } else {
            TODO("VERSION.SDK_INT < GINGERBREAD")
        }

        workManager.enqueueUniquePeriodicWork(
            WORKMANAGER_NAME,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }
}