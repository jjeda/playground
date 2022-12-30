package oop.programmer

interface Programmer {
  fun makeProgram(paper: Paper): Program
}

class FrontEndProgrammer : Programmer {
  private lateinit var language: Language
  private lateinit var library: Library

  override fun makeProgram(paper: Paper): Program {
    if (paper is ClientPaper) {
      val clientPaper = paper
      language = paper.language
      library = paper.library
    }

    return makeFrontEndProgram()
  }

  private fun makeFrontEndProgram(): Program {
    return Program()
  }
}

class BackEndProgrammer : Programmer {
  private lateinit var language: Language
  private lateinit var server: Server

  override fun makeProgram(paper: Paper): Program {
    if (paper is ServerClientPaper) {
      val serverClientPaper = paper
      language = paper.backEndLanguage
      server = paper.server
    }

    return makeBackEndProgram()
  }

  private fun makeBackEndProgram(): Program {
    return Program()
  }
}

class Program()