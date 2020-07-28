package com.violin.service

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class MyIntentService : IntentService("MyIntentService") {

    val TAG = MyIntentService::class.java.simpleName
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val param = intent.getStringExtra("param")
            Log.d(TAG, "onHandleIntent $param")
            Log.d(TAG, "Thread ${Thread.currentThread().name}")
            Thread.sleep(2000)

        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        Thread.currentThread().isInterrupted
        Thread.currentThread().interrupt()
        Thread.interrupted()
    }


    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        @JvmStatic
        fun startIntentService(context: Context, param1: String) {
            val intent = Intent(context, MyIntentService::class.java).apply {
                putExtra("param", param1)
            }

            context.startService(intent)
        }

    }
}
