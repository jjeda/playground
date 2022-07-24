package fp.recursive

/**
 * 연습문제 3-6
 *
 * 입력값 n이 리스트에 존재하는지 확인하는 함수를 재귀로 작성해보자.
 *
 * 힌트: 함수의 선언 타입은 아래와 같다.
 * fun elem(n: Int, list: List<Int>): Boolean
 */

fun main() {
  require(elem(5, listOf(1, 3, 5)))
  require(!elem(5, listOf(1, 3, 7)))
}

private fun elem(num: Int, list: List<Int>): Boolean {
  if (list.isEmpty()) return false

  return elem(num, list.dropLast(1)) || list.last() == num
}
