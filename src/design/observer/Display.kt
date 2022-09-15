package design.observer

interface DisplayElement {
  fun display()
}

class CurrentConditionsDisplay(
  private val weatherData: WeatherData
) : DisplayElement, Observer {
  private var temperature: Double = 0.0
  private var humidity: Double = 0.0

  init {
    weatherData.registerObserver(this)
  }

  override fun display() {
    println("temperature = $temperature, humidity = $humidity")
  }

  override fun update(temperature: Double, humidity: Double, pressure: Double) {
    this.temperature = temperature
    this.humidity = humidity
    display()
  }
}

class StatisticsDisplay : DisplayElement, Observer {
  override fun display() {
    TODO("Not yet implemented")
  }

  override fun update(temperature: Double, humidity: Double, pressure: Double) {
    TODO("Not yet implemented")
  }

}

class ThirdPartyDisplay : DisplayElement, Observer {
  override fun display() {
    TODO("Not yet implemented")
  }

  override fun update(temperature: Double, humidity: Double, pressure: Double) {
    TODO("Not yet implemented")
  }

}

class ForecastDisplay : DisplayElement, Observer {
  override fun display() {
    TODO("Not yet implemented")
  }

  override fun update(temperature: Double, humidity: Double, pressure: Double) {
    TODO("Not yet implemented")
  }
}