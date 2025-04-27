package services

import enums.FilterModeEnum
import models.HackerNewsEntry

import scala.concurrent.Future

trait HackerNewsService {

  def fetchEntries(implicit filterMode: FilterModeEnum): Future[Seq[HackerNewsEntry]]
}
