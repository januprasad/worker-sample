package com.example.work_manager_some_cases

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.work_manager_some_cases.databinding.ActivityWorkManagerBinding

class WorkManagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkManagerBinding
    private lateinit var logUploadManager: LogUploadManager
    private lateinit var eventUpdateManager: EventUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        logUploadManager = LogUploadManager(this)
        eventUpdateManager = EventUpdateManager(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.buttonWorkerSampleToast.setOnClickListener {
            logUploadManager.exec()
        }
        binding.buttonWorkerSampleAfterTwoSeconds.setOnClickListener {
            logUploadManager.execAfter2Seconds()
        }
        binding.buttonWorkerSampleCancelAll.setOnClickListener {
            logUploadManager.execAfter2Seconds()
            logUploadManager.cancelAll()
        }
        binding.buttonWorkerSampleEventUpdate.setOnClickListener {
            eventUpdateManager.exec()
        }
        binding.buttonWorkerSampleEventCancelAndRestart.setOnClickListener {
            eventUpdateManager.exec()
            eventUpdateManager.cancelAndRestart()
        }

    }
}

