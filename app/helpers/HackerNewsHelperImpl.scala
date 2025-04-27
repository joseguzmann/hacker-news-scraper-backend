package helpers

import enums.FilterModeEnum
import models.HackerNewsEntry
import net.ruippeixotog.scalascraper.model.Document
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

import javax.inject.{Inject, Singleton}
import scala.util.Try

@Singleton
class HackerNewsHelperImpl @Inject()(textHelper: TextHelper) extends HackerNewsHelper {

  private val rowsToBeTaken = 30

  override def parseHackerNewsDocument(document: Document): Seq[HackerNewsEntry] = {
    val rows = (document >> elementList("tr.athing.submission")).take(rowsToBeTaken)

    for {
      row        <- rows
      rowId       = row.attr("id")
      siblingRow  = document >> element(s"tr#$rowId + tr")
      rank        = (row >> text("span.rank")).dropRight(1).toInt
      title       =  row >> text("span.titleline a")
      points     <- Try((siblingRow >> text("span.score"))
        .takeWhile(_.isDigit).toInt).toOption
      comments   <- Try((siblingRow >> text("span.subline > a:last-child"))
        .takeWhile(_.isDigit).toInt).toOption
    } yield HackerNewsEntry(rank, title, points, comments)
  }

  override def applyFilterAndOrder(entries: Seq[HackerNewsEntry])(implicit filterMode: FilterModeEnum): Seq[HackerNewsEntry] =
    entries
      .filter(e => filterMode.filter(textHelper.countWords(e.title)))
      .sortBy(filterMode.order)
}
