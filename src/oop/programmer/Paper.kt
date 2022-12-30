package oop.programmer

interface Paper {
  fun run(): List<Program>
}

abstract class ClientPaper() : Paper {
  val library = Library("vueJS")
  val language = Language("kotlinJS")
  protected lateinit var programmer: Programmer<ClientPaper>
}

abstract class ServerClientPaper() : Paper {
  val server = Server("test")
  val frontEndLanguage = Language("kotlin")
  val backEndLanguage = Language("kotlin")
  protected lateinit var frontEndProgrammer: Programmer<ServerClientPaper>
  protected lateinit var backEndProgrammer: Programmer<ServerClientPaper>
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

fun main() {
  val director = Director()
  director.addProject("프론트", object : ClientPaper() {
    override fun run(): List<Program> {
      val frontEndProgrammer = object : FrontEndProgrammer<ClientPaper>() {
        override fun setData(paper: ClientPaper) {
          library = paper.library
          language = paper.language
        }
      }
      // setProgrammer(frontEndProgrammer)
      programmer = frontEndProgrammer // 접근레벨 조정
      return listOf(frontEndProgrammer.getProgram(this))
    }
  })
  director.runProject("프론트")

  director.addProject("프론트 서버", object : ServerClientPaper() {
    override fun run(): List<Program> {
      val frontEndProgrammer = object : FrontEndProgrammer<ServerClientPaper>() {
        override fun setData(paper: ServerClientPaper) {
          language = paper.frontEndLanguage
        }
      }

      val backEndProgrammer = object : BackEndProgrammer<ServerClientPaper>() {
        override fun setData(paper: ServerClientPaper) {
          server = paper.server
          language = paper.backEndLanguage
        }
      }
      this.frontEndProgrammer = frontEndProgrammer
      this.backEndProgrammer = backEndProgrammer
      return listOf(frontEndProgrammer.getProgram(this), backEndProgrammer.getProgram(this))
    }
  })
  director.runProject("프론트 서버")
}