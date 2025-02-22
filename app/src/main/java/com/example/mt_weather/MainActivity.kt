package com.example.mt_weather

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mt_weather.model.Weather
import com.example.mt_weather.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // ViewBinding pro přístup k prvkům v layoutu
    private lateinit var binding: ActivityMainBinding

    // Seznam možných počasí
    private val weatherData = listOf(
        Weather("Praha", 20, "Cloudy"),
        Weather("Brno", 25, "Sunny"),
        Weather("Ostrava", 18, "Rainy"),
        Weather("Plzeň", 22, "Partly Cloudy"),
        Weather("Nedakonice", 19, "Windy")
    )

    private var currentWeather: Weather? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializace ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obnovení
        currentWeather = savedInstanceState?.getParcelable("weather", Weather::class.java) ?: getRandomWeather()

        // Zobrazení aktuálního počasí
        displayWeather(currentWeather!!)

        // Nastavení tlačítka pro aktualizaci počasí
        binding.buttonRefresh.setOnClickListener {
            currentWeather = getRandomWeather()
            displayWeather(currentWeather!!)
        }
    }

    // Uložení stavu při změně konfigurace
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("weather", currentWeather)
    }

    // Funkce pro zobrazení aktuálního počasí na obrazovce
    private fun displayWeather(weather: Weather) {
        binding.textViewCity.text = weather.city
        binding.textViewTemperature.text = "${weather.temperature}°C"
        binding.textViewDescription.text = weather.description
    }

    // Funkce pro získání náhodného počasí z předpřipravených dat
    private fun getRandomWeather(): Weather {
        return weatherData[Random.nextInt(weatherData.size)]
    }
}
