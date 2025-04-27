package enums

import models.HackerNewsEntry

sealed trait FilterModeEnum {
  def filter(wordCount: Int): Boolean
  def order(e: HackerNewsEntry): Int
}

object FilterModeEnum {

  private case object LongTitles extends FilterModeEnum {
    private val threshold = 5

    def filter(wordCount: Int): Boolean = wordCount > threshold
    def order(e: HackerNewsEntry): Int  = -e.comments
  }

  private case object ShortTitles extends FilterModeEnum {
    private val threshold = 5

    def filter(wordCount: Int): Boolean = wordCount <= threshold
    def order(e: HackerNewsEntry): Int  = -e.points
  }

  private case object Raw extends FilterModeEnum {
    def filter(wordCount: Int): Boolean = true
    def order(e: HackerNewsEntry): Int  = 0
  }

  def fromOptionString(filter: Option[String]): FilterModeEnum =
    filter.map(_.toLowerCase) match {
      case Some("longtitles")  => LongTitles
      case Some("shorttitles") => ShortTitles
      case _                   => Raw
    }
}
