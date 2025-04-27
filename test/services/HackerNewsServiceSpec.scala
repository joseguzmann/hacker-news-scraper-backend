package services

import enums.FilterModeEnum
import helpers.HackerNewsHelper
import models.HackerNewsEntry
import net.ruippeixotog.scalascraper.model.Document
import org.mockito.scalatest.MockitoSugar
import org.scalatest.concurrent.ScalaFutures._
import org.scalatest.matchers.must.Matchers._
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class HackerNewsServiceSpec
  extends AnyWordSpec
    with MockitoSugar {

  private val mockHelper = mock[HackerNewsHelper]
  private val mockFetcher = mock[HtmlFetcherService]
  private val service = new HackerNewsServiceImpl(mockHelper, mockFetcher)

  private val hnUrl = "https://news.ycombinator.com/"

  implicit private val mode: FilterModeEnum = FilterModeEnum.Raw

  "fetchEntries" should {

    "return parsed and filtered entries when everything succeeds" in {
      val dummyDoc = mock[Document]
      val parsedEntries = Seq(HackerNewsEntry(1, "stub title", 10, 5))

      when(mockFetcher.fetch(eqTo(hnUrl)))
        .thenReturn(Future.successful(dummyDoc))

      when(mockHelper.parseHackerNewsDocument(dummyDoc))
        .thenReturn(parsedEntries)

      when(mockHelper.applyFilterAndOrder(parsedEntries))
        .thenReturn(parsedEntries)

      whenReady(service.fetchEntries) { result =>
        result mustBe parsedEntries
      }

      verify(mockFetcher).fetch(hnUrl)
      verify(mockHelper).parseHackerNewsDocument(dummyDoc)
      verify(mockHelper).applyFilterAndOrder(parsedEntries)
    }

    "propagate failure if HtmlFetcherService fails" in {
      val boom = new RuntimeException("network down")

      when(mockFetcher.fetch(any[String]))
        .thenReturn(Future.failed(boom))

      service.fetchEntries.failed.futureValue mustBe boom
    }
  }
}
