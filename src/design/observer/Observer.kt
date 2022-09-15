package design.observer

interface Observer {
  fun update(temperature: Double, humidity: Double, pressure: Double)
}

interface Subject {
  fun registerObserver(observer: Observer)
  fun removeObserver(observer: Observer)
  fun notifyObservers()
}
