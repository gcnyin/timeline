import cats._
import cats.implicits._

import scala.annotation.tailrec

sealed trait Option[+A]

case class Some[A](value: A) extends Option[A]

case object None extends Option[Nothing]

object Option {
  /**
   * 因为scala worksheet的限制，implicit没法用，只能摆在这里
   */
  implicit val m: Monad[Option] = new Monad[Option] {
    override def pure[A](x: A): Option[A] = Some(x)

    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa match {
        case Some(value) => f(value)
        case None => None
      }

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] =
      f(a) match {
        case None => None
        case Some(Left(a1)) => tailRecM(a1)(f)
        case Some(Right(b)) => Some(b)
      }
  }
}

val i: Option[Int] = 3.pure[Option]

println(i)
