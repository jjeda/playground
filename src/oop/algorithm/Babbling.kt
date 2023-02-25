package oop.algorithm

// https://school.programmers.co.kr/learn/courses/30/lessons/120956?language=kotlin
class Babbling {
  fun solution(babbling: Array<String>): Int {
    return babbling.count { Nephew.isSpeakable(word = it) }
  }
}

object Nephew {
  private val basicBabbling = setOf("aya", "ye", "woo", "ma")

  fun isSpeakable(word: String): Boolean {
    // return word.split(*basicBabbling.toTypedArray()).all { it.isEmpty() }
    return word.replace(basicBabbling.joinToString("|").toRegex(), "").isEmpty()
  }
}
