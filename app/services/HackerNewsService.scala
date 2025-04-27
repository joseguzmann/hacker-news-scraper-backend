package services

import enums.FilterModeEnum
import models.HackerNewsEntry

import scala.concurrent.Future

trait HackerNewsService {

  def fetchEntries(filterMode: FilterModeEnum): Future[Seq[HackerNewsEntry]]
}
