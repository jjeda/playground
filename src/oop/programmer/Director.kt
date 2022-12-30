package oop.programmer

class Director {
  private val projects = mutableMapOf<String, Paper>()

  fun addProject(name: String, paper: Paper) { projects[name] = paper }

  fun runProject(name: String) {
    val paper = projects[name]

    if (paper is ServerClientPaper) { // 이제 이 부분이 OCP 위반
      val project: ServerClientPaper = paper
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
      project.setFrontEndProgrammer(frontEndProgrammer)
      project.setBackEndProgrammer(backEndProgrammer)
      val client = frontEndProgrammer.getProgram(project)
      val server = backEndProgrammer.getProgram(project)
      deploy(name, client, server)
    } else if (paper is ClientPaper) {
      val project: ClientPaper = paper
      val frontEndProgrammer = object : FrontEndProgrammer<ClientPaper>() {
        override fun setData(paper: ClientPaper) {
          library = paper.library
          language = paper.language
        }
      }
      project.setProgrammer(frontEndProgrammer)
      deploy(name, frontEndProgrammer.getProgram(project))
    }
  }
  private fun deploy(projectName: String, vararg programs: Program) {}
}