package ru.nsu.ccfit.android.fitwiki

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.nsu.ccfit.android.fitwiki.api.BackendApi
import java.util.*

class App: Application() {
    companion object {
        private const val BASE_URL = "http://" +
                "192.168.43.240" +
                ":8080"

        lateinit var api: BackendApi

        var userToken: String? = null
        var userId: UUID? = null
    }

    override fun onCreate(){
        super.onCreate()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                .build()
        api = retrofit.create(BackendApi::class.java)
    }
}