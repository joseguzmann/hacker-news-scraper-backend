package services

import net.ruippeixotog.scalascraper.model.Document

import scala.concurrent.Future

trait HtmlFetcherService {

  def fetch(url: String): Future[Document]
}
