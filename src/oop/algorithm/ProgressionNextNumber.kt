package oop.algorithm

class ProgressionNextNumber {
  fun solution(common: IntArray): Int {
    return ProgressionHelper(progression = common.toList()).nextInt()
  }
}

class ProgressionHelper(
  private val progression: List<Int>
) {
  fun nextInt(): Int {
    val metadata = getMetadata()
    val nextNumberGenerator = when (metadata.type) {
      ProgressionType.ARITHMETIC -> ArithmeticalProgressionNextNumberGenerator
      ProgressionType.GEOMETRIC -> GeometricProgressionNextNumberGenerator
    }
    return nextNumberGenerator.generateWith(metadata)
  }

  private fun getMetadata(): ProgressionInformation {
    val (first, second) = progression

    return if (isArithmetic()) {
      ProgressionInformation(progression, ProgressionType.ARITHMETIC, second - first)
    } else {
      ProgressionInformation(progression, ProgressionType.GEOMETRIC, second / first)
    }
  }

  private fun isArithmetic(): Boolean {
    val (first, second, third) = progression
    return second * 2 == first + third
  }
}

data class ProgressionInformation(
  val progression: List<Int>,
  val type: ProgressionType,
  val distance: Int,
)

enum class ProgressionType {
  ARITHMETIC, GEOMETRIC
}


sealed interface ProgressionNextNumberGenerator {
  fun generateWith(metadata: ProgressionInformation): Int
}

object ArithmeticalProgressionNextNumberGenerator : ProgressionNextNumberGenerator {
  override fun generateWith(metadata: ProgressionInformation) = metadata.progression.last() + metadata.distance
}

object GeometricProgressionNextNumberGenerator : ProgressionNextNumberGenerator {
  override fun generateWith(metadata: ProgressionInformation) = metadata.progression.last() * metadata.distance
}