package design.observer

fun main() {
  val weatherData = WeatherData()
  val currentConditionsDisplay = CurrentConditionsDisplay(weatherData)

  weatherData.setMeasurements(80.0, 65.0, 30.4)
  weatherData.setMeasurements(82.0, 70.0, 29.2)
  weatherData.setMeasurements(78.0, 90.0, 29.2)
}