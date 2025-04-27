package helpers

import enums.FilterModeEnum
import models.HackerNewsEntry
import net.ruippeixotog.scalascraper.model.Document

trait HackerNewsHelper {

  def parseHackerNewsDocument(document: Document): Seq[HackerNewsEntry]

  def applyFilterAndOrder(entries: Seq[HackerNewsEntry])(implicit filterMode: FilterModeEnum): Seq[HackerNewsEntry]
}
