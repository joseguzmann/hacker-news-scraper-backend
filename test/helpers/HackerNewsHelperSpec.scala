package helpers

import enums.FilterModeEnum
import models.HackerNewsEntry
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.mockito.scalatest.MockitoSugar
import org.scalatest.matchers.must.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class HackerNewsHelperSpec
  extends AnyWordSpec
    with MockitoSugar {

  private val sampleHtml: String =
    """
      |<html>
      | <body>
      |  <table class="itemlist">
      |   <!-- first entry -->
      |   <tr class="athing submission" id="1">
      |     <td align="right" class="title"><span class="rank">1.</span></td>
      |     <td class="title">
      |       <span class="titleline"><a href="#">First example title</a></span>
      |     </td>
      |   </tr>
      |   <tr>
      |     <td colspan="2">
      |       <span class="score" id="score_1">42 points</span>
      |       <span class="subline">
      |         <a href="item?id=1">42&nbsp;comments</a>
      |       </span>
      |     </td>
      |   </tr>
      |
      |   <!-- second entry -->
      |   <tr class="athing submission" id="2">
      |     <td align="right" class="title"><span class="rank">2.</span></td>
      |     <td class="title">
      |       <span class="titleline"><a href="#">Second longer example title line</a></span>
      |     </td>
      |   </tr>
      |   <tr>
      |     <td colspan="2">
      |       <span class="score" id="score_2">100 points</span>
      |       <span class="subline">
      |         <a href="item?id=2">5&nbsp;comments</a>
      |       </span>
      |     </td>
      |   </tr>
      |  </table>
      | </body>
      |</html>
      |""".stripMargin

  private val doc = JsoupBrowser().parseString(sampleHtml)

  private val mockTextHelper = mock[TextHelper]
  private val helper = new HackerNewsHelperImpl(mockTextHelper)

  "parseHackerNewsDocument" should {

    "extract rank, title, points and comments from the DOM" in {
      val entries = helper.parseHackerNewsDocument(doc)

      entries must have size 2
      entries.head mustBe HackerNewsEntry(1, "First example title", 42, 42)
      entries.tail.head mustBe HackerNewsEntry(2, "Second longer example title line", 100, 5)
    }
  }

  "applyFilterAndOrder" should {

    "keep only long titles (>5 words) and order by comments DESC when LongTitles mode" in {
      val e1 = HackerNewsEntry(1, "Short title", 50, 10)
      val e2 = HackerNewsEntry(2, "This is a six word sample title", 30, 99)

      when(mockTextHelper.countWords(eqTo(e1.title))).thenReturn(2)
      when(mockTextHelper.countWords(eqTo(e2.title))).thenReturn(7)

      implicit val mode: FilterModeEnum = FilterModeEnum.LongTitles

      val result = helper.applyFilterAndOrder(Seq(e1, e2))

      result must have size 1
      result.head.title mustBe e2.title
    }

    "keep only short titles (<=5 words) and order by points DESC when ShortTitles mode" in {
      val e1 = HackerNewsEntry(1, "Five word title only here", 80, 2)
      val e2 = HackerNewsEntry(2, "Tiny", 90, 7) // 1 word

      when(mockTextHelper.countWords(eqTo(e1.title))).thenReturn(5)
      when(mockTextHelper.countWords(eqTo(e2.title))).thenReturn(1)

      implicit val mode: FilterModeEnum = FilterModeEnum.ShortTitles

      val result = helper.applyFilterAndOrder(Seq(e1, e2))

      result.map(_.title) mustBe Seq(e2.title, e1.title)
    }
  }
}
