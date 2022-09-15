package design.observer

class WeatherData : Subject {
  private var temperature: Double = 0.0
  private var humidity: Double = 0.0
  private var pressure: Double = 0.0
  private val observers =  mutableListOf<Observer>()

  override fun registerObserver(observer: Observer) {
    observers.add(observer)
  }

  override fun removeObserver(observer: Observer) {
    observers.remove(observer)
  }

  override fun notifyObservers() {
    observers.forEach {
      it.update(temperature, humidity, pressure)
    }
  }

  fun setMeasurements(temperature: Double, humidity: Double, pressure: Double) {
    this.temperature = temperature
    this.humidity = humidity
    this.pressure = pressure
    measurementsChanged()
  }

  private fun measurementsChanged() {
    notifyObservers()
  }
}