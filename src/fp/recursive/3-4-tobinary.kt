package fp.recursive

/**
 * 연습문제 3-4
 *
 * 10진수 숫자를 입력받아서 2진수로 문자열로 변환하여 반환하는 함수를 작성하라.
 *
 * 힌트: 함수의 선언 타입은 아래와 같다.
 * fun toBinary(n: Int): String
 */

fun main() {
  require("1010" == toBinary(10))
  require("11011" == toBinary(27))
  require("11111111" == toBinary(255))
}

private fun toBinary(n: Int): String {
  if (n == 1) return "1"
  else if (n == 0) return "0"

  return toBinary(n = n / 2) + n % 2
}
