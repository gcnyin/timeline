package models

import java.time.Instant

case class TimelineItem(id: Int, timelineId: Int, title: String, recordTime: Instant, description: String)
