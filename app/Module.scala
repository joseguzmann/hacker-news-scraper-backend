import com.google.inject.AbstractModule
import helpers.{HackerNewsHelper, HackerNewsHelperImpl, TextHelper, TextHelperImpl}
import services.{HackerNewsService, HackerNewsServiceImpl, HtmlFetcherService, HtmlFetcherServiceImpl}

class Module extends AbstractModule {

  override def configure(): Unit = {
    // Services
    bind(classOf[HackerNewsService]).to(classOf[HackerNewsServiceImpl])
    bind(classOf[HtmlFetcherService]).to(classOf[HtmlFetcherServiceImpl])
    // Helpers
    bind(classOf[HackerNewsHelper]).to(classOf[HackerNewsHelperImpl])
    bind(classOf[TextHelper]).to(classOf[TextHelperImpl])
  }
}
