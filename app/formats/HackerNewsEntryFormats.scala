package formats

import models.HackerNewsEntry
import play.api.libs.json.{Json, OWrites}

object HackerNewsEntryFormats {

  implicit val hackerNewsEntryWrites: OWrites[HackerNewsEntry] = Json.writes[HackerNewsEntry]
}