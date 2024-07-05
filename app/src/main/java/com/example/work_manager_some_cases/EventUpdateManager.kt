package com.example.work_manager_some_cases

import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest

class EventUpdateManager(private val activityContext: WorkManagerActivity) : WorkerContract {
    private var toastManager: ToastManager = ToastManager(activityContext)
    private lateinit var workReq: WorkRequest

    override fun exec() {
        workReq = createEventWorkerRequest(Events.Test)
        WorkManager.getInstance(activityContext).apply {
            enqueue(workReq)
            getWorkInfoByIdLiveData(workReq.id).observe(activityContext, observerEventUpdate)
        }
    }

    fun cancelAndRestart() {
        WorkManager.getInstance(activityContext).cancelWorkById(workReq.id)
        val workReq1 = createEventWorkerRequest(Events.Test2)
        WorkManager.getInstance(activityContext).apply {
            enqueue(workReq1)
            getWorkInfoByIdLiveData(workReq1.id).observe(activityContext, observerEventUpdate)
        }
    }

    private val observerEventUpdate: (WorkInfo) -> Unit = { workInfo ->
        when (workInfo.state) {
            WorkInfo.State.SUCCEEDED -> {
                val event = workInfo.outputData.getString(Events.Event)
                event?.let {
                    val msg =
                        activityContext.getString(R.string.successfully_updated_event) + " event = $it"
                    toastManager.showToast(msg)
                }
            }

            WorkInfo.State.FAILED -> {
                toastManager.showToast(activityContext.getString(R.string.failed_to_upload_due_to_connectivity_issues))
            }

            WorkInfo.State.CANCELLED -> {
                toastManager.showToast(activityContext.getString(R.string.failed_to_update_event_due_to_user_cancelled))
            }

            else -> {}
        }
    }
}