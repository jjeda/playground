package oop.programmer

interface Paper

class ClientPaper(
  var programmer: Programmer
) : Paper {
  val library = Library("vueJS")
  val language = Language("kotlinJS")
}

class ServerClientPaper(
  var frontEndProgrammer: Programmer,
  var backEndProgrammer: Programmer,
) : Paper {
  val server = Server("test")
  val frontEndLanguage = Language("kotlin")
  val backEndLanguage = Language("kotlin")
}

data class Library(
  private val title: String
)

data class Language(
  private val title: String
)

data class Server(
  private val title: String
)