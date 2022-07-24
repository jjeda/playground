package fp.recursive

/**
 * 연습문제 3-7
 *
 * 코드 3-9에서 다루었던 take 함수를 참고하여 repeat 함수를 테스트하기 위해서 사용한 takeSequence 함수를 작성해보자.
 * 그리고 repeat 함수가 잘 동작하는지 직접 테스트 해보라.
 *
 * 힌트: 함수의 선언 타입은 아래와 같다. 빈 시퀀스는 sequence.none() 으로 표현한다.
 * fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int>
 */

fun main() {
  require(listOf(3, 3, 3, 3, 3) == takeSequence(5, repeat(3)))
}

private fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> = when {
  n <= 0 -> emptyList()
  sequence.none() -> emptyList()
  else -> listOf(sequence.first()) + takeSequence(n - 1, sequence.drop(1))
}

private fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
  private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
  private val otherIterator: Iterator<T> by lazy { other().iterator() }
  override fun iterator() = object : Iterator<T> {
    override fun next(): T =
      if (thisIterator.hasNext()) thisIterator.next()
      else otherIterator.next()

    override fun hasNext(): Boolean = thisIterator.hasNext() || otherIterator.hasNext()
  }
}
