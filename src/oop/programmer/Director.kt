package oop.programmer

class Director {
  private val projects = mutableMapOf<String, Paper>()

  fun addProject(name: String, paper: Paper) { projects[name] = paper }

  fun runProject(name: String) {
    val paper = projects[name] ?: throw RuntimeException()
    deploy(name, *paper.run().toTypedArray())
  }
}

private fun deploy(projectName: String, vararg programs: Program) {}