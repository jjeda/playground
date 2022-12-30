package oop.programmer

interface Programmer<T : Paper> {
  fun setData(paper: T)
  fun makeProgram(): Program
  fun getProgram(paper: T): Program {
    setData(paper)
    return makeProgram()
  }
}

abstract class FrontEndProgrammer<T : Paper> : Programmer<T> {
  protected lateinit var language: Language
  protected lateinit var library: Library

  override fun makeProgram() = Program()
}

abstract class BackEndProgrammer<T : Paper> : Programmer<T> {
  protected lateinit var language: Language
  protected lateinit var server: Server

  override fun makeProgram() = Program()
}

class Program()