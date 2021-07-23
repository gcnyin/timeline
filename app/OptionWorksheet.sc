sealed trait Option[+A] {
  def filter(f: A => Boolean): Option[A] = None

  def map[B](f: A => B): Option[B] = None

  def flatMap[B](f: A => Option[B]): Option[B] = None
}

case class Some[+A](value: A) extends Option[A] {
  override def filter(f: A => Boolean): Option[A] = {
    if (f(value)) {
      this
    } else {
      None
    }
  }

  override def map[B](f: A => B): Option[B] = Some(f(value))

  override def flatMap[B](f: A => Option[B]): Option[B] = f(value)
}

case object None extends Option[Nothing]

// +++++++++++++++++++++++++++
// ++++++++ examples +++++++++
// +++++++++++++++++++++++++++

val i: Option[Int] = None

i match {
  case Some(value) => println(value)
  case None => println("empty")
}

val j: Option[Int] = Some(1)

j match {
  case Some(value) => println(value)
  case None => println("empty")
}

val a3: Option[Int] = for {
  a1 <- i
  a2 <- j
} yield a1 + a2

println(a3 eq None)
