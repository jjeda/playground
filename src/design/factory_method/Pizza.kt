package design.factory_method

abstract class Pizza(
  val name: String,
  private val dough: String,
  private val sauce: String,
) {
  val toppings = mutableListOf<String>()

  open fun prepare() { println("prepare") }
  open fun bake() { println("bake") }
  open fun cut() { println("cut") }
  open fun box() { println("bux") }
}

class ChicagoStyleCheesePizza : Pizza(
  name = "Chicago Style Deep Dish Cheese Pizza",
  dough = "Extra Thick Crust Dough",
  sauce = "Plum Tomato Sauce",
) {
  init {
    toppings.add("Shredded Mozzarella Cheese");
  }

  override fun cut() {
    println("Cutting the pizza into square slices")
  }
}

class ChicagoStyleClamPizza : Pizza(
  name = "Chicago Style Clam Pizza",
  dough = "Extra Thick Crust Dough",
  sauce = "Plum Tomato Sauce",
) {
  init {
    toppings.add("Shredded Mozzarella Cheese")
    toppings.add("Frozen Clams from Chesapeake Bay")
  }

  override fun cut() {
    println("Cutting the pizza into square slices")
  }
}

class ChicagoStylePepperoniPizza : Pizza(
  name = "Chicago Style Pepperoni Pizza",
  dough = "Extra Thick Crust Dough",
  sauce = "Plum Tomato Sauce",
) {
  init {
    toppings.add("Shredded Mozzarella Cheese")
    toppings.add("Black Olives")
    toppings.add("Spinach")
    toppings.add("Eggplant")
    toppings.add("Sliced Pepperoni")
  }

  override fun cut() {
    println("Cutting the pizza into square slices")
  }
}

class ChicagoStyleVeggiePizza : Pizza(
  name = "Chicago Deep Dish Veggie Pizza",
  dough = "Extra Thick Crust Dough",
  sauce = "Plum Tomato Sauce",
) {
  init {
    toppings.add("Shredded Mozzarella Cheese")
    toppings.add("Black Olives")
    toppings.add("Spinach")
    toppings.add("Eggplant")
  }

  override fun cut() {
    println("Cutting the pizza into square slices")
  }
}

fun main() {
  val chicagoStore = ChicagoPizzaStore()

  var pizza = chicagoStore.orderPizza("cheese")
  println("Ethan ordered a ${pizza.name}\n")

  pizza = chicagoStore.orderPizza("clam")
  println("joel ordered a ${pizza.name}\n")

  pizza = chicagoStore.orderPizza("pepperoni")
  println("ethan ordered a ${pizza.name}\n")

  pizza = chicagoStore.orderPizza("veggie")
  println("joel ordered a ${pizza.name}\n")
}