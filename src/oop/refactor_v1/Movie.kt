package oop.refactor_v1

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class Movie(
  val title: String,
  val runningTime: Duration,
  val fee: Money,
  val discountConditions: List<DisCountCondition>,
  val movieType: MovieType,
  val discountAmount: Money,
  val discountPercent: Double,
)

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
  val sequence: Int,
  val dayOfWeek: DayOfWeek,
  val startTime: LocalTime,
  val endTime: LocalTime,
)

class Screening(
  val movie: Movie,
  val sequence: Int,
  val whenScreened: LocalDateTime,
)

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
  val amount: Int
)

class ReservationAgency {
  fun reserve(screening: Screening, customer: Customer, audienceCount: Int): Reservation {
    val discountable = checkDiscountable(screening)
    val fee: Money = calculateFee(screening, discountable, audienceCount)
    return Reservation(customer, screening, fee, audienceCount)
  }

  private fun checkDiscountable(screening: Screening): Boolean {
    val movie = screening.movie

    return movie.discountConditions.any { isDiscountable(it, screening) }
  }

  private fun isDiscountable(disCountCondition: DisCountCondition, screening: Screening): Boolean {
    if (disCountCondition.type == DisCountConditionType.PERIOD) {
      return isSatisfiedByPeriod(disCountCondition, screening)
    }
    return isSatisfiedBySequence(disCountCondition, screening)
  }

  private fun isSatisfiedByPeriod(condition: DisCountCondition, screening: Screening): Boolean {
    return screening.whenScreened.dayOfWeek == condition.dayOfWeek &&
      condition.startTime <= screening.whenScreened.toLocalTime() &&
      condition.endTime >= screening.whenScreened.toLocalTime()
  }

  private fun isSatisfiedBySequence(condition: DisCountCondition, screening: Screening): Boolean {
    return condition.sequence == screening.sequence
  }

  private fun calculateFee(
    screening: Screening,
    discountable: Boolean,
    audienceCount: Int
  ): Money {
    val movie = screening.movie
    if (discountable) {
      val discountAmount = calculateDiscountedFee(movie)
      return Money((movie.fee.amount - discountAmount) * audienceCount)
    }
    return Money(movie.fee.amount)
  }

  private fun calculateDiscountedFee(movie: Movie): Int {
    return when (movie.movieType) {
      MovieType.AMOUNT_DISCOUNT -> calculateAmountDiscountedFee(movie)
      MovieType.PERCENT_DISCOUNT -> calculatePercentDiscountedFee(movie)
      MovieType.NONE_DISCOUNT -> calculateAmountDiscountedFee()
    }
  }

  private fun calculateAmountDiscountedFee(movie: Movie): Int {
    return movie.discountAmount.amount
  }

  private fun calculatePercentDiscountedFee(movie: Movie): Int {
    return movie.fee.amount.times(movie.discountPercent).toInt()
  }

  private fun calculateAmountDiscountedFee(): Int {
    return 0
  }
}