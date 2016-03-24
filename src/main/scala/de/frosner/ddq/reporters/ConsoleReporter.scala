package de.frosner.ddq.reporters

import java.io.PrintStream

import de.frosner.ddq.constraints.{ConstraintError, ConstraintFailure, ConstraintSuccess}
import de.frosner.ddq.core.CheckResult

/**
 * A class which produces a console report of [[CheckResult]].
 *
 * @param stream The [[java.io.PrintStream]] to put the output. The stream will not be closed internally and can
 *               be reused.
**/
case class ConsoleReporter(stream: PrintStream) extends PrintStreamReporter {

  /**
   * Output console report of a given checkResult to the stream passed to the constructor
   * @param checkResult The [[CheckResult]] to be reported
   */
  override def report(checkResult: CheckResult, header: String, prologue: String): Unit = {
    stream.println(Console.BLUE + header + Console.RESET)
    stream.println(Console.BLUE + prologue + Console.RESET)
    if (checkResult.constraintResults.nonEmpty) {
      checkResult.constraintResults.foreach {
        case (_, constraintResult) => {
          constraintResult.status match {
            case ConstraintSuccess => stream.println(Console.GREEN + "- " + constraintResult.message + Console.RESET)
            case ConstraintFailure => stream.println(Console.RED + "- " + constraintResult.message + Console.RESET)
            case ConstraintError(throwable) => stream.println(Console.YELLOW + "- " + constraintResult.message + Console.RESET)
          }
        }
      }
    } else {
      stream.println(Console.BLUE + "Nothing to check!" + Console.RESET)
    }
    stream.println("")
  }

}
