import com.google.inject.AbstractModule
import services.{HackerNewsService, HackerNewsServiceImpl}

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[HackerNewsService]).to(classOf[HackerNewsServiceImpl])
  }
}
