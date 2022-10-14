package oop.v1

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
    val movie = screening.movie

    val discountable = movie.discountConditions.map {
      if (it.type == DisCountConditionType.PERIOD) {
        screening.whenScreened.dayOfWeek == it.dayOfWeek &&
          it.startTime <= screening.whenScreened.toLocalTime() &&
          it.endTime >= screening.whenScreened.toLocalTime()
      } else {
        it.sequence == screening.sequence
      }
    }.any { true }

    val fee: Money = if (discountable) {
      val discountAmount: Int = when (movie.movieType) {
        MovieType.AMOUNT_DISCOUNT -> movie.discountAmount.amount
        MovieType.PERCENT_DISCOUNT -> movie.fee.amount.times(movie.discountPercent).toInt()
        MovieType.NONE_DISCOUNT -> 0
      }
      Money((movie.fee.amount - discountAmount) * audienceCount)
    } else {
      Money(movie.fee.amount)
    }
    return Reservation(customer, screening, fee, audienceCount)
  }
}