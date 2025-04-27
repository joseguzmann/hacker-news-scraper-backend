package controllers

import enums.FilterModeEnum
import models.HackerNewsEntry
import org.mockito.scalatest.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers._
import play.api.test._
import play.api.{Application, Mode}
import services.HackerNewsService

import scala.concurrent.Future

class HackerNewsControllerSpec
  extends PlaySpec
    with GuiceOneAppPerSuite
    with Injecting
    with DefaultAwaitTimeout
    with MockitoSugar {

  private val mockService: HackerNewsService = mock[HackerNewsService]

  override lazy val app: Application =
    new GuiceApplicationBuilder()
      .in(Mode.Test)
      .overrides(bind[HackerNewsService].toInstance(mockService))
      .build()

  "getEntries" should {

    "return 200 and a JSON array when service succeeds" in {
      val stubEntries = Seq(
        HackerNewsEntry(rank = 1, title = "Test title", points = 100, comments = 42)
      )

      when(mockService.fetchEntries(eqTo(FilterModeEnum.Raw)))
        .thenReturn(Future.successful(stubEntries))

      val request = FakeRequest(GET, "/hacker-news/entries")
      val result = route(app, request).value

      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
      (contentAsJson(result) \\ "title").head.as[String] mustBe "Test title"
    }

    "return 500 with error JSON when service fails" in {
      when(mockService.fetchEntries(any[FilterModeEnum]))
        .thenReturn(Future.failed(new RuntimeException("boom")))

      val result = route(app, FakeRequest(GET, "/hacker-news/entries")).value

      status(result) mustBe INTERNAL_SERVER_ERROR
      (contentAsJson(result) \ "error").as[String] must include("Unexpected")
    }
  }
}
