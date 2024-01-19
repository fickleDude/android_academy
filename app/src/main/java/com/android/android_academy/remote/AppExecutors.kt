package com.android.android_academy.remote
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor


//helps to make calls to remote DB on the background(not in main thread)
//Retrofit doesn't accept multiple objects so we make AppExecutors a singleton
class AppExecutors private constructor() {

//    background scheduled thread
 //   private val networkIO : ScheduledExecutorService = Executors.newScheduledThreadPool(3, ThreadFactory{})
    private val networkIO : ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    companion object {

        @Volatile private var instance: AppExecutors? = null // Volatile modifier is necessary

        fun getInstance() =
            instance ?: synchronized(this) { // synchronized to avoid concurrency problem
                instance ?: AppExecutors().also { instance = it }
            }
    }

    fun networkIO():ScheduledExecutorService{
        return networkIO
    }
}