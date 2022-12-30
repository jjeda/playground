package oop.programmer

class Director {
  private val projects = mutableMapOf<String, Paper>()

  fun addProject(name: String, paper: Paper) { projects[name] = paper }

  fun runProject(name: String) {
    val paper = projects[name]

    if (paper is ServerClientPaper) {
      val project: ServerClientPaper = paper
      val frontEndProgrammer = FrontEndProgrammer()
      val backEndProgrammer = BackEndProgrammer()
      project.frontEndProgrammer = frontEndProgrammer
      project.backEndProgrammer = backEndProgrammer
      val client = frontEndProgrammer.makeProgram(project)
      val server = backEndProgrammer.makeProgram(project)
      deploy(name, client, server)
    } else if (paper is ClientPaper) {
      val project: ClientPaper = paper
      val frontEndProgrammer = FrontEndProgrammer()
      project.programmer = frontEndProgrammer
      deploy(name, frontEndProgrammer.makeProgram(project))
    }
  }
  private fun deploy(projectName: String, vararg programs: Program) {}
}