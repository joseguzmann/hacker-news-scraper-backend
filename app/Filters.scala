import play.api.http.HttpFilters
import play.filters.cors.CORSFilter

import javax.inject._

@Singleton
class Filters @Inject()(
  corsFilter: CORSFilter
) extends HttpFilters {
  override val filters: Seq[CORSFilter] = Seq(corsFilter)
}
