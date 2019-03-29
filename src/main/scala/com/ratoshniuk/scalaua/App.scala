package com.ratoshniuk.scalaua

import java.util.concurrent.{Executors, ThreadPoolExecutor}

import com.github.pshirshov.izumi.functional.bio.{BIO, BIOAsync, BIORunner}
import com.ratoshniuk.scalaua.YourService.{AdsPlarform, UserId}
import scalaz.zio.IO

import scala.util.Random

object ScalaUA extends App {

  import logstage._

  val textSink = ConsoleSink.text(colored = true)

  val sinks = List(textSink)

  val logger: IzLogger = IzLogger.apply(Trace, sinks)

  val service = new YourService(logger)

  val bioRunner = BIORunner.createZIO(
    Executors.newFixedThreadPool(8).asInstanceOf[ThreadPoolExecutor]
    , Executors.newCachedThreadPool().asInstanceOf[ThreadPoolExecutor]
  )

  val adPlatforms = List("Adwords", "Google", "IronSource")

  val userCount = 10
  val eff = BIOAsync[IO].parTraverseN(5)((1 to userCount) map (i => s"user-$i") ) {
    user =>
      service.batchProcessing(UserId(user), AdsPlarform(Random.shuffle(adPlatforms).head))
  }.void

  bioRunner.unsafeRun(eff)

  sys.exit(0)
}
