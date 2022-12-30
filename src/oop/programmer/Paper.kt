package oop.programmer

interface Paper<T : Programmer> {
  fun setData(programmer: T)
}

class ClientPaper(
  var programmer: Programmer
) : Paper<FrontEndProgrammer> {
  private val library = Library("vueJS")
  private val language = Language("kotlinJS")
  override fun setData(programmer: FrontEndProgrammer) {
    programmer.setLibrary(library)
    programmer.setLanguage(language)
  }
}

class ServerClientPaper(
  var frontEndProgrammer: Programmer,
  var backEndProgrammer: Programmer,
) : Paper<FrontEndProgrammer, BackEndProgrammer> { // 이 부분을 해결할 수 없음..
  private val server = Server("test")
  private val frontEndLanguage = Language("kotlin")
  private val backEndLanguage = Language("kotlin")
  override fun setData(programmer: Programmer) {
    if (programmer is FrontEndProgrammer) {
      programmer.setLanguage(frontEndLanguage)
    } else if (programmer is BackEndProgrammer) {
      programmer.setLanguage(backEndLanguage)
      programmer.setServer(server)
    }
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