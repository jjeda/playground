package oop.programmer

interface Programmer {
  fun makeProgram(paper: Paper): Program
}

class FrontEndProgrammer : Programmer {
  private lateinit var language: Language
  private lateinit var library: Library

  override fun makeProgram(paper: Paper): Program {
    paper.setData(this)
    return makeFrontEndProgram()
  }
  fun setLanguage(language: Language) { this.language = language }
  fun setLibrary(library: Library) { this.library = library }
  private fun makeFrontEndProgram() = Program()
}

class BackEndProgrammer : Programmer {
  private lateinit var language: Language
  private lateinit var server: Server

  override fun makeProgram(paper: Paper): Program {
    paper.setData(this)
    return makeBackEndProgram()
  }
  fun setServer(server: Server) { this.server = server }
  fun setLanguage(language: Language) { this.language = language }
  private fun makeBackEndProgram() = Program()
}

class Program()