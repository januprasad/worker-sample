package com.example.work_manager_some_cases

import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest

class LogUploadManager(private val activityContext: WorkManagerActivity) : WorkerContract {
    private lateinit var workReq: WorkRequest
    private var toastManager: ToastManager = ToastManager(activityContext)

    override fun exec() {
        workReq = createUploadLogWorkRequest()
        WorkManager.getInstance(activityContext).apply {
            enqueue(workReq)
            getWorkInfoByIdLiveData(workReq.id)
                .observe(activityContext, observerUploadLog)
        }
    }

    fun execAfter2Seconds() {
        workReq = createUploadLogWorkRequest(2000L)
        WorkManager.getInstance(activityContext).apply {
            enqueue(workReq)
            getWorkInfoByIdLiveData(workReq.id)
                .observe(activityContext, observerUploadLog)
        }
    }

    fun cancelAll() {
        WorkManager.getInstance(activityContext).cancelWorkById(workReq.id)
    }

    private val observerUploadLog: (WorkInfo) -> Unit = { workInfo ->
        when (workInfo.state) {
            WorkInfo.State.SUCCEEDED -> {
                toastManager.showToast(activityContext.getString(R.string.successfully_uploaded))
            }

            WorkInfo.State.FAILED -> {
                toastManager.showToast(activityContext.getString(R.string.failed_to_upload_due_to_connectivity_issues))
            }

            WorkInfo.State.CANCELLED -> {
                toastManager.showToast(activityContext.getString(R.string.failed_to_upload_due_to_user_cancelled))
            }

            else -> {}
        }
    }
}