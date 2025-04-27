package services

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HtmlFetcherServiceImpl @Inject()(implicit ec :ExecutionContext) extends HtmlFetcherService {

  private val browser = JsoupBrowser()

  override def fetch(url: String): Future[Document] =
    Future(browser.get(url))
}
