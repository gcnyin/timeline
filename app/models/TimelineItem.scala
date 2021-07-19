package models

import java.sql.Timestamp

case class TimelineItem(id: Int, timelineId: Int, title: String, recordTime: Timestamp, description: String)
