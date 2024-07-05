package com.example.work_manager_some_cases

import android.content.Context
import androidx.work.OneTimeWorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class LogUploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            uploadLogData()
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }


    private fun uploadLogData() {
        runBlocking {
            val state = (1..10).random() % 2 == 0
            delay(100L) //just for some delay
            if (state)
                throw Exception("")
        }
    }
}

//data class Response(val msg: String)

fun createUploadLogWorkRequest() = OneTimeWorkRequest.Builder(LogUploadWorker::class.java)
    .build()

fun createUploadLogWorkRequest(delay: Long) =
    OneTimeWorkRequest.Builder(LogUploadWorker::class.java)
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()
