package com.muneeb_dev.vpn_app.Activities.SpeedTestAct

import android.net.TrafficStats
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.muneeb_dev.vpn_app.AppUtils.updateStatusBarColor
import com.muneeb_dev.vpn_app.R
import com.muneeb_dev.vpn_app.databinding.ActivitySpeedTestBinding
import com.muneeb_dev.vpn_app.databinding.ActivityVpnlocationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

class SpeedTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpeedTestBinding

    private var isMonitoring = false
    private var totalDownloadStart = 0L
    private var totalUploadStart = 0L

    private var speedTestJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySpeedTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //status bar color
        updateStatusBarColor(getColor(R.color.app_main_bg_color),false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //set toolbar
        binding.includedToolbar.appNameTV.text = "Speed Test"
//        binding.includedToolbar.actionBtnIMGV.visibility = View.VISIBLE
//        binding.includedToolbar.actionBtnIMGV.setImageResource(R.drawable.tick_icon)
//        binding.includedToolbar.actionBtnIMGV.setOnClickListener{}
        binding.includedToolbar.mainToolBar.setNavigationOnClickListener{
            finish()
        }


        binding.btnStart.setOnClickListener {
            lifecycleScope.launch {
                startSpeedTest()
            }
        }


    }



    private fun startSpeedTest() {
        // Cancel any ongoing speed test
        speedTestJob?.cancel()

        // Reference to the LottieAnimationView
//        val speedButton = findViewById<LottieAnimationView>(R.id.speed_button)

        speedTestJob = CoroutineScope(Dispatchers.Main).launch {
            binding.btnStart.isEnabled = false // Disable button during test
            try {
                val client = OkHttpClient.Builder()
                    .callTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // Increased timeout
                    .build()

                // --------------------------
                // Start animation for download speed test
                // --------------------------
//                speedButton.setAnimation(R.raw.speed) // Ensure the correct animation is set
//                speedButton.repeatCount = LottieDrawable.INFINITE // Animation will loop until stopped
//                speedButton.playAnimation() // Start the animation

                // Download Speed Test
                binding.tvStatus.text = "Speed..."
                val downloadSpeeds = mutableListOf<Double>()
                withContext(Dispatchers.IO) {
                    measureDownloadSpeed(client, downloadSpeeds)
                }

                // Calculate and display average download speed
                val avgDownloadSpeed = downloadSpeeds.average()
                binding.tvDownloadSpeed.text = String.format("Download: %.2f MBps", avgDownloadSpeed)
//                downloadRate.text = String.format("%.2f MBps", avgDownloadSpeed)

                // --------------------------
                // Stop the animation after download speed is complete
                // --------------------------
//                speedButton.cancelAnimation()
//                speedButton.progress = 0f // Reset to the initial state

                // --------------------------
                // Start animation for upload speed test
                // --------------------------
//                speedButton.playAnimation() // Start the animation again for upload

                // Upload Speed Test
                binding.tvUploadSpeed.text = "Speed..."
                val uploadSpeeds = mutableListOf<Double>()
                withContext(Dispatchers.IO) {
                    measureUploadSpeed(client, uploadSpeeds)
                }

                // Calculate and display average upload speed
                val avgUploadSpeed = uploadSpeeds.average()
                binding.tvUploadSpeed.text = String.format("Upload: %.2f MBps", avgUploadSpeed)
//                uploadRate.text = String.format("%.2f MBps", avgUploadSpeed)

                // --------------------------
                // Stop the animation after upload speed is complete
                // --------------------------
//                speedButton.cancelAnimation()
//                speedButton.progress = 0f // Reset to the initial state

            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Error during speed test", e)
                Toast.makeText(this@SpeedTestActivity, "Error during speed test: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
//                startSpeedTestButton.isEnabled = true // Re-enable button after test
            }
        }
    }

    private suspend fun measureDownloadSpeed(client: OkHttpClient, downloadSpeeds: MutableList<Double>) {
        val downloadUrl = "https://jsonplaceholder.typicode.com/posts" // Update to your download test URL
        val totalDataSizeInKB = 1024.0 // Approximate data size in KB (1 MB)

        repeat(10) { index ->
            var success = false
            repeat(3) { attempt ->
                try {
                    val downloadTime = measureTimeMillis {
                        client.newCall(Request.Builder().url(downloadUrl).build()).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Failed to download file: ${response.message}")
                            response.body?.string() // Assuming the response is valid
                        }
                    }
                    success = true
                    val downloadSpeedMbps = (totalDataSizeInKB / (downloadTime / 1000.0)) / 1024 // Convert to Mbps
                    downloadSpeeds.add(downloadSpeedMbps)

                    // Update the UI with the current download speed
                    withContext(Dispatchers.Main) {
                        binding.tvDownloadSpeed.text = String.format(" %.2f MBps", downloadSpeedMbps)
                    }

                    Log.i("SpeedTestActivity", "Download $index completed in $downloadTime ms at speed: ${downloadSpeedMbps} MBps")
                    return@repeat
                } catch (e: IOException) {
                    Log.e("SpeedTestActivity", "Retrying download $index... (${attempt + 1}/3)", e)
                }
            }
            if (!success) {
                Log.e("SpeedTestActivity", "Failed to download $index after 3 attempts")
            }
        }
    }

    private suspend fun measureUploadSpeed(client: OkHttpClient, uploadSpeeds: MutableList<Double>) {
        val uploadUrl = "https://jsonplaceholder.typicode.com/posts" // Update to your upload test URL
        val totalDataSizeInKB = 1024.0 // Approximate data size in KB (1 MB)
        val uploadData = """{"title": "foo", "body": "bar", "userId": 1}""" // Example JSON data
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), uploadData)

        repeat(10) { index ->
            var success = false
            repeat(3) { attempt ->
                try {
                    val uploadTime = measureTimeMillis {
                        client.newCall(Request.Builder().url(uploadUrl).post(requestBody).build()).execute().use { response ->
                            if (!response.isSuccessful) throw IOException("Failed to upload file: ${response.message}")
                        }
                    }
                    success = true
                    val uploadSpeedMbps = (totalDataSizeInKB / (uploadTime / 1000.0)) / 1024 // Convert to Mbps
                    uploadSpeeds.add(uploadSpeedMbps)

                    // Update the UI with the current upload speed
                    withContext(Dispatchers.Main) {
                        binding.tvUploadSpeed.text = String.format(" %.2f MBps", uploadSpeedMbps)
                    }

                    Log.i("SpeedTestActivity", "Upload $index completed in $uploadTime ms at speed: ${uploadSpeedMbps} MBps")
                    return@repeat
                } catch (e: IOException) {
                    Log.e("SpeedTestActivity", "Retrying upload $index... (${attempt + 1}/3)", e)
                }
            }
            if (!success) {
                Log.e("SpeedTestActivity", "Failed to upload $index after 3 attempts")
            }
        }
    }




    private fun fetchIPAddress() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://ipinfo.io/json")
                .build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val ipResponse = response.body?.string()
                    // Extracting the IP address from the JSON response
                    val ip = ipResponse?.let { parseIpFromJson(it) }
                    withContext(Dispatchers.Main) {
//                        ipAddress.text = "$ip"
                    }
                }
            } catch (e: Exception) {
                Log.e("SpeedTestActivity", "Failed to fetch IP address", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SpeedTestActivity, "Failed to fetch IP address", Toast.LENGTH_SHORT).show()
//                    ipAddress.text = "IP Address: N/A"
                }
            }
        }
    }

    private fun parseIpFromJson(json: String): String? {
        // Simple JSON parsing to extract the IP address
        return try {
            val jsonObject = org.json.JSONObject(json)
            jsonObject.getString("ip")
        } catch (e: Exception) {
            Log.e("SpeedTestActivity", "JSON parsing error", e)
            null
        }
    }



}