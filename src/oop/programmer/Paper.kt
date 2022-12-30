package oop.programmer

interface Paper

class ClientPaper() : Paper {
  val library = Library("vueJS")
  val language = Language("kotlinJS")
  private lateinit var programmer: Programmer<ClientPaper>
  fun setProgrammer(programmer: Programmer<ClientPaper>) {
    this.programmer = programmer
  }
}

class ServerClientPaper() : Paper {
  val server = Server("test")
  val frontEndLanguage = Language("kotlin")
  val backEndLanguage = Language("kotlin")
  private lateinit var frontEndProgrammer: Programmer<ServerClientPaper>
  private lateinit var backEndProgrammer: Programmer<ServerClientPaper>

  fun setBackEndProgrammer(programmer: Programmer<ServerClientPaper>) {
    this.backEndProgrammer = programmer
  }

  fun setFrontEndProgrammer(programmer: Programmer<ServerClientPaper>) {
    this.frontEndProgrammer = programmer
  }
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