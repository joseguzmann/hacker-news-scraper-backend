package helpers

import org.scalatest.matchers.must.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class TextHelperSpec extends AnyWordSpec {

  private val textHelper = new TextHelperImpl

  "countWords" should {

    "count plain words separated by single spaces" in {
      textHelper.countWords("scala rocks hard") mustBe 3
    }

    "treat multiple spaces as a single separator" in {
      textHelper.countWords("play   framework   ") mustBe 2
    }

    "ignore punctuation and symbols between words" in {
      textHelper.countWords("Hello, world!!!") mustBe 2
    }

    "consider hyphenated tokens as a single word" in {
      textHelper.countWords("state-of-the-art solution") mustBe 2
    }

    "work with Unicode letters (accents, ñ, ü, etc.)" in {
      textHelper.countWords("Programación en español") mustBe 3
    }

    "return zero for an empty string" in {
      textHelper.countWords("") mustBe 0
    }
  }
}
