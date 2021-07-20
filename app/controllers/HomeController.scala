package controllers

import play.api.mvc._
import repositories.TimelineRepository

import javax.inject._
import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents, timelineRepository: TimelineRepository)(
    implicit ec: ExecutionContext
) extends BaseController {
  def index(): Action[AnyContent] = Action.async {
    timelineRepository.selectRecentCreateTimelinesAndTotalCount().map {
      case (count, timelines) => Ok(views.html.index(count, timelines))
    }
  }
}
