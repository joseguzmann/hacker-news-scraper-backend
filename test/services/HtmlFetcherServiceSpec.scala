package services

import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.matchers.must.Matchers._
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.ExecutionContext.Implicits.global

class HtmlFetcherServiceSpec
  extends AnyWordSpec
    with ScalaFutures
    with IntegrationPatience {

  private val fetcher = new HtmlFetcherServiceImpl

  "fetch" should {

    "return a Jsoup Document when the URL is reachable" in {
      whenReady(fetcher.fetch("https://example.com")) { doc =>
        (doc >> text("title")) mustBe "Example Domain"
      }
    }

    "fail the Future for an invalid URL" in {
      fetcher.fetch("not-a-valid-url").failed.futureValue mustBe
        a[java.lang.IllegalArgumentException]
    }
  }
}
