package design.factory_method

abstract class PizzaStore {

  abstract fun createPizza(item: String): Pizza

  fun orderPizza(type: String): Pizza {
    return createPizza(type).also { println("--- Making a ${it.name} ---") }
      .apply {
        prepare()
        bake()
        cut()
        box()
      }
  }
}

class ChicagoPizzaStore : PizzaStore() {
  override fun createPizza(item: String): Pizza {
    return when (item) {
      "cheese" -> ChicagoStyleCheesePizza()
      "veggie" -> ChicagoStyleVeggiePizza()
      "clam" -> ChicagoStyleClamPizza()
      "pepperoni" -> ChicagoStylePepperoniPizza()
      else -> throw UnsupportedOperationException()
    }
  }
}