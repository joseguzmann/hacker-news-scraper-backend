package services

import enums.FilterModeEnum
import helpers.HackerNewsHelper
import models.HackerNewsEntry

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HackerNewsServiceImpl @Inject()(
  hackerNewsHelper: HackerNewsHelper,
  htmlFetcherService: HtmlFetcherService
)(implicit ec :ExecutionContext) extends HackerNewsService {

  private val HackerNewsUrl = "https://news.ycombinator.com/"

  override def fetchEntries(implicit filterMode: FilterModeEnum): Future[Seq[HackerNewsEntry]] =
    htmlFetcherService.fetch(HackerNewsUrl).map { html =>
      hackerNewsHelper.applyFilterAndOrder(hackerNewsHelper.formatHackerNewsDocument(html))
    }
}