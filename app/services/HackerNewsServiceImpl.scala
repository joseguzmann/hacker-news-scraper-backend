package services

import models.HackerNewsEntry
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class HackerNewsServiceImpl @Inject()(implicit ec :ExecutionContext) extends HackerNewsService {

  override def fetchEntries(): Future[Seq[HackerNewsEntry]] = {
    for {
      entries <- fetchAndFormatEntries()
    } yield entries
  }

  private def fetchAndFormatEntries(): Future[Seq[HackerNewsEntry]] = Future {
    val url  = "https://news.ycombinator.com/"
    val doc  = JsoupBrowser().get(url)
    val rowsToBeFetched = 30
    val rows = (doc >> elementList("tr.athing.submission")).take(rowsToBeFetched)

    for {
      row        <- rows
      rowId       = row.attr("id")
      siblingRow  = doc >> element(s"tr#$rowId + tr")
      rank        = (row >> text("span.rank")).dropRight(1).toInt
      title       =  row >> text("span.titleline a")
      points     <- Try((siblingRow >> text("span.score"))
        .takeWhile(_.isDigit).toInt).toOption
      comments   <- Try((siblingRow >> text("span.subline > a:last-child"))
        .takeWhile(_.isDigit).toInt).toOption
    } yield HackerNewsEntry(rank, title, points, comments)
  }
}
