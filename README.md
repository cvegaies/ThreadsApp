# ThreadsApp
Working with threads

[https://developer.android.com/guide/components/processes-and-threads](https://developer.android.com/guide/components/processes-and-threads)

[https://developer.android.com/topic/performance/threads](https://developer.android.com/topic/performance/threads)

Android provides many ways of creating and managing threads:

* Thread
* Runnable
* AsyncTask
* Handler / Message
* Handler.post()
* ScheduledExecutorService
* ExecutorService
* ThreadPoolExecutor

[https://www.toptal.com/android/android-threading-all-you-need-to-know](https://www.toptal.com/android/android-threading-all-you-need-to-know)

In Android, you can categorize all threading components into two basic categories:

* Threads that are attached to an activity/fragment: These threads are tied to the lifecycle of the activity/fragment and are terminated as soon as the activity/fragment is destroyed.
* Threads that are not attached to any activity/fragment: These threads can continue to run beyond the lifetime of the activity/fragment (if any) from which they were spawned.
