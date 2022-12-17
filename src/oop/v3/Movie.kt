package oop.v3

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class Movie(
  private val title: String,
  private val runningTime: Duration,
  private val fee: Money,
  private val discountPolicy: DiscountPolicy,
  private val discountConditions: List<DisCountCondition>,
) {
  fun calculateMovieFee(screening: Screening): Money {
    if (isDiscountable(screening)) {
      return fee - discountPolicy.calculateDiscountAmount()
    }
    return fee
  }

  private fun isDiscountable(screening: Screening): Boolean {
    return discountConditions.any { it.isSatisfiedBy(screening) }
  }
}

enum class MovieType(description: String) {
  AMOUNT_DISCOUNT("금액 할인 정책"),
  PERCENT_DISCOUNT("비율 할인 정책"),
  NONE_DISCOUNT("미적용")
}

enum class DisCountConditionType {
  SEQUENCE, PERIOD
}

interface DiscountPolicy {
  fun calculateDiscountAmount(): Money
}

class AmountDiscountPolicy(
  private val discountAmount: Money,
) : DiscountPolicy {
  override fun calculateDiscountAmount(): Money {
    return discountAmount
  }
}

class PercentDiscountPolicy(
  private val fee: Money,
  private val discountPercent: Double,
) : DiscountPolicy {
  override fun calculateDiscountAmount(): Money {
    return fee * discountPercent
  }
}

class NoneDiscountPolicy : DiscountPolicy {
  override fun calculateDiscountAmount(): Money {
    return Money.ZERO
  }
}

interface DisCountCondition {
  fun isSatisfiedBy(screening: Screening): Boolean
}

class PeriodCondition(
  private val dayOfWeek: DayOfWeek,
  private val startTime: LocalTime,
  private val endTime: LocalTime,
) : DisCountCondition {
  override fun isSatisfiedBy(screening: Screening): Boolean {
    val time = screening.whenScreened.toLocalTime()
    return this.dayOfWeek == dayOfWeek && startTime <= time && endTime >= time
  }
}

class SequenceCondition(
  private val sequence: Int
) : DisCountCondition {
  override fun isSatisfiedBy(screening: Screening): Boolean {
    return sequence == screening.sequence
  }
}


class Screening(
  private val movie: Movie,
  val sequence: Int,
  val whenScreened: LocalDateTime,
) {
  fun reserve(customer: Customer, audienceCount: Int): Reservation {
    return Reservation(customer, this, calculateFee(audienceCount), audienceCount)
  }

  private fun calculateFee(audienceCount: Int): Money {
    return movie.calculateMovieFee(this) * audienceCount
  }
}

class Reservation(
  val customer: Customer,
  val screening: Screening,
  val fee: Money,
  val audienceCount: Int
)

class Customer(
  val name: String,
  val id: String
)

class Money(
  private val amount: Int
) {
  companion object {
    val ZERO = Money(0)
  }

  operator fun minus(money: Money): Money {
    return Money(amount - money.amount)
  }

  operator fun times(percent: Double): Money {
    return Money(amount.times(percent).toInt())
  }

  operator fun times(count: Int): Money {
    return Money(amount.times(count))
  }
}
