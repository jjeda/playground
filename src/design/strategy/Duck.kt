package design.strategy

abstract class Duck(
  val flyBehavior: FlyBehavior,
  val quackBehavior: QuackBehavior,
)

class MallardDuck : Duck(FlyWithWings(), Quack())