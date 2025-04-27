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

//  val values: Seq[FilterModeEnum] = Seq(LongTitles, ShortTitles, Raw)

  def fromString(s: String): Option[FilterModeEnum] =
    s.toLowerCase match {
      case "longtitles"  => Some(LongTitles)
      case "shorttitles" => Some(ShortTitles)
      case "raw"         => Some(Raw)
      case _             => None
    }
}
