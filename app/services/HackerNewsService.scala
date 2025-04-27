package services

import models.HackerNewsEntry

import scala.concurrent.Future

trait HackerNewsService {

  def fetchEntries(): Future[Seq[HackerNewsEntry]]
}
