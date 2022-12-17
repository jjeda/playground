package oop.v3

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class Movie(
  private val movieType: MovieType,
  private val title: String,
  private val runningTime: Duration,
  private val fee: Money,
  private val discountConditions: List<DisCountCondition>,
  private val discountAmount: Money,
  private val discountPercent: Double,
) {
  fun calculateMovieFee(screening: Screening): Money {
    if (isDiscountable(screening)) {
      return fee - calculateDiscountAmount()
    }
    return fee
  }

  private fun isDiscountable(screening: Screening): Boolean {
    return discountConditions.any { it.isSatisfiedBy(screening) }
  }

  private fun calculateDiscountAmount(): Money {
    return when (movieType) {
      MovieType.AMOUNT_DISCOUNT -> calculateAmountDiscountAmount()
      MovieType.PERCENT_DISCOUNT -> calculatePercentDiscountAmount()
      MovieType.NONE_DISCOUNT -> calculateNoneDiscountAmount()
    }
  }

  private fun calculateAmountDiscountAmount(): Money {
    return discountAmount
  }

  private fun calculatePercentDiscountAmount(): Money {
    return fee * discountPercent
  }

  private fun calculateNoneDiscountAmount(): Money {
    return Money.ZERO
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

class DisCountCondition(
  private val type: DisCountConditionType,
  private val sequence: Int,
  private val dayOfWeek: DayOfWeek,
  private val startTime: LocalTime,
  private val endTime: LocalTime,
) {
  fun isSatisfiedBy(screening: Screening): Boolean {
    if (type == DisCountConditionType.PERIOD) {
      return isSatisfiedByPeriod(screening)
    }
    return isSatisfiedBySequence(screening)
  }

  private fun isSatisfiedByPeriod(screening: Screening): Boolean {
    val time = screening.whenScreened.toLocalTime()
    return this.dayOfWeek == dayOfWeek && startTime <= time && endTime >= time
  }

  private fun isSatisfiedBySequence(screening: Screening): Boolean {
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
