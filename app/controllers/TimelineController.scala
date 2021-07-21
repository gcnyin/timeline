package controllers

import play.api.libs.json.Json
import play.api.mvc._
import repositories.TimelineRepository
import requests.CreateTimeline

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class TimelineController @Inject() (
    val controllerComponents: ControllerComponents,
    timelineRepository: TimelineRepository
)(implicit ec: ExecutionContext)
    extends BaseController {
  def timelines(id: Int): Action[AnyContent] = Action.async { implicit request =>
    timelineRepository.findTimelineInfoById(id).map { opt =>
      opt.fold(NotFound(views.html.notFound())) { value =>
        Ok(views.html.timeline(value._1, value._2.sortBy(_.recordTime)))
      }
    }
  }

  def createTimeline(): Action[CreateTimeline] = Action.async(parse.json[CreateTimeline]) { request =>
    timelineRepository.createTimeline(request.body.title).map(it => Ok(Json.toJson(it)))
  }
}
