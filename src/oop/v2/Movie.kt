package oop.v2

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class Movie(
  val movieType: MovieType,
  private val title: String,
  private val runningTime: Duration,
  private val fee: Money,
  private val discountConditions: List<DisCountCondition>,
  private val discountAmount: Money,
  private val discountPercent: Double,
) {
  fun calculateAmountDiscountedFee(): Money {
    if (movieType != MovieType.AMOUNT_DISCOUNT) throw IllegalArgumentException()

    return fee - discountAmount
  }

  fun calculatePercentDiscountedFee(): Money {
    if (movieType != MovieType.PERCENT_DISCOUNT) throw IllegalArgumentException()

    return fee - (fee * discountPercent)
  }

  fun calculateNoneDiscountedFee(): Money {
    if (movieType != MovieType.NONE_DISCOUNT) throw IllegalArgumentException()

    return fee
  }

  fun isDiscountable(whenScreened: LocalDateTime, sequence: Int): Boolean {
    return discountConditions.map {
      if (it.type == DisCountConditionType.PERIOD) {
        if (it.isDiscountable(whenScreened.dayOfWeek, whenScreened.toLocalTime())) {
          return@map true
        } else {
          if (it.isDiscountable(sequence)) {
            return@map true
          }
        }
      }
      false
    }.any { true }
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
  val type: DisCountConditionType,
  private val sequence: Int,
  private val dayOfWeek: DayOfWeek,
  private val startTime: LocalTime,
  private val endTime: LocalTime,
) {
  fun isDiscountable(dayOfWeek: DayOfWeek, time: LocalTime): Boolean {
    if (type == DisCountConditionType.PERIOD) throw IllegalArgumentException("")

    return this.dayOfWeek == dayOfWeek && startTime <= time && endTime >= time
  }

  fun isDiscountable(sequence: Int): Boolean {
    if (type == DisCountConditionType.SEQUENCE) throw IllegalArgumentException("")

    return this.sequence == sequence
  }
}

class Screening(
  private val movie: Movie,
  private val sequence: Int,
  private val whenScreened: LocalDateTime,
) {
  fun calculateFee(audienceCount: Int): Money {
    return when (movie.movieType) {
      MovieType.AMOUNT_DISCOUNT -> {
        if (movie.isDiscountable(whenScreened, sequence)) {
          movie.calculateAmountDiscountedFee() * audienceCount
        }
        movie.calculateNoneDiscountedFee() * audienceCount
      }
      MovieType.PERCENT_DISCOUNT -> {
        if (movie.isDiscountable(whenScreened, sequence)) {
          movie.calculatePercentDiscountedFee() * audienceCount
        }
        movie.calculateNoneDiscountedFee() * audienceCount
      }
      MovieType.NONE_DISCOUNT -> {
        movie.calculateNoneDiscountedFee() * audienceCount
      }
    }
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

class ReservationAgency {
  fun reserve(screening: Screening, customer: Customer, audienceCount: Int): Reservation {
    val fee = screening.calculateFee(audienceCount)

    return Reservation(customer, screening, fee, audienceCount)
  }
}