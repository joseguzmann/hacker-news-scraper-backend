package helpers

import javax.inject.Singleton

@Singleton
class TextHelperImpl extends TextHelper {

  private val WordRegex = raw"\p{L}[\p{L}\p{N}-]*".r

  override def countWords(title: String): Int =
    WordRegex.findAllIn(title).length
}
