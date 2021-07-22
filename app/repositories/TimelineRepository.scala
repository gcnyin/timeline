package repositories

import cats.data.OptionT
import models.{Timeline, TimelineItem}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import java.time.Instant
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TimelineRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.

  import dbConfig._
  import profile.api._

  private class timeline(tag: Tag) extends Table[Timeline](tag, "timeline") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def title: Rep[String] = column[String]("title")

    def createDate: Rep[Instant] = column[Instant]("create_date")

    def * : ProvenShape[Timeline] = (id, title, createDate) <> ((Timeline.apply _).tupled, Timeline.unapply)
  }

  private val timeline = TableQuery[timeline]

  private class timelineItem(tag: Tag) extends Table[TimelineItem](tag, "timeline_item") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def timelineId: Rep[Int] = column[Int]("timeline_id")

    def title: Rep[String] = column[String]("title")

    def recordTime: Rep[Instant] = column[Instant]("record_time")

    def description: Rep[String] = column[String]("description")

    def * =
      (id, timelineId, title, recordTime, description) <> ((TimelineItem.apply _).tupled, TimelineItem.unapply)
  }

  private val timelineItem = TableQuery[timelineItem]

  def findTimelineInfoById(id: Int): Future[Option[(Timeline, Seq[TimelineItem])]] = {
    for {
      timeline <- OptionT(findTimelineById(id))
      v <- OptionT.liftF(findTimelineItemsByTimelineId(timeline.id))
    } yield (timeline, v)
  }.value

  def findTimelineById(id: Int): Future[Option[Timeline]] = db.run {
    timeline.filter(_.id === id).result.headOption
  }

  def findTimelineItemsByTimelineId(timelineId: Int): Future[Seq[TimelineItem]] =
    db.run {
      timelineItem.filter(_.timelineId === timelineId).result
    }

  def findRecentCreateTimelines(): Future[Seq[Timeline]] = db.run {
    timeline.sortBy(_.createDate.desc).take(20).result
  }

  def selectTimelineCount(): Future[Int] = db.run {
    timeline.size.result
  }

  def selectRecentCreateTimelinesAndTotalCount(): Future[(Int, Seq[Timeline])] =
    for {
      count <- selectTimelineCount()
      recentTimelines <- findRecentCreateTimelines()
    } yield (count, recentTimelines)

  def createTimeline(title: String): Future[Timeline] = db.run {
    (timeline.map(t => t.title)
      returning timeline.map(it => (it.id, it.createDate))
      into ((inputTitle, returnedInfo) => Timeline(returnedInfo._1, inputTitle, returnedInfo._2))
      ) += title
  }
}
