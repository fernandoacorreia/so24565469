import sbt._
import Keys._

// imports standard command parsing functionality
import complete.DefaultParsers._

object CommandExample extends Build {
  // Declare a project, adding new commands.
  lazy override val projects = Seq(root)
  lazy val root = Project("root", file(".")) settings(
    commands ++= Seq(start, customStart)
  )

  // A fake "start" command.
  def start = Command.command("start") { state =>
    println("Fake start command executed.")
    state
  }

  // A command that executes an external command before executing the "start" command. 
  // The name of the external command is the first parameter.
  // Any additional parameters are passed along to the external command.
  def customStart = Command.args("customStart", "<name>") { (state, args) =>
    if (args.length > 0) {
      val externalCommand = args.mkString(" ")
      println(s"Executing '$externalCommand'")
      externalCommand !
    }
    "start" :: state
  }
}
