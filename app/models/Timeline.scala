package models

import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Timeline(id: Int, title: String, createData: Instant)

object Timeline {
  implicit val format: OFormat[Timeline] = Json.format[Timeline]
}
