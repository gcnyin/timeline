sealed trait Option[+A] {
  def filter(f: A => Boolean): Option[A]

  def map[B](f: A => B): Option[B]

  def flatmap[B](f: A => Option[B]): Option[B]
}

case class Some[+A](value: A) extends Option[A] {
  override def filter(f: A => Boolean): Option[A] = {
    if (f(value)) {
      this
    } else {
      None()
    }
  }

  override def map[B](f: A => B): Option[B] = Some(f(value))

  override def flatmap[B](f: A => Option[B]): Option[B] = f(value)
}

class None[+A] extends Option[A] {
  override def filter(f: A => Boolean): Option[A] = this

  override def map[B](f: A => B): Option[B] = None()

  override def flatmap[B](f: A => Option[B]): Option[B] = None()
}

object None {
  def apply[A](): Option[A] = new None()
}

val v: Option[Int] = None[Int]()

v match {
  case Some(value) =>
  case none: None[_] =>
}
