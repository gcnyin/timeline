package requests

import play.api.libs.json.{Json, Reads}

case class CreateTimeline (title: String)

object CreateTimeline {
  implicit val reads: Reads[CreateTimeline] = Json.reads[CreateTimeline]
}
