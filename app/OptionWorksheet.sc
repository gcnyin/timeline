sealed trait Option[+A] {
  def filter(f: A => Boolean): Option[A]

  def map[B](f: A => B): Option[B]

  def flatMap[B](f: A => Option[B]): Option[B]

  def foreach(f: A => Unit): Unit
}

/**
 * @param value 真正的值
 * @tparam A neo蜀黍强调，Some从语义上讲，不应该支持协变
 */
case class Some[A](value: A) extends Option[A] {
  override def filter(f: A => Boolean): Option[A] =
    if (f(value)) this else None

  override def map[B](f: A => B): Option[B] = Some(f(value))

  override def flatMap[B](f: A => Option[B]): Option[B] = f(value)

  override def foreach(f: A => Unit): Unit = f(value)
}

case object None extends Option[Nothing] {
  override def filter(f: Nothing => Boolean): Option[Nothing] = None

  override def map[B](f: Nothing => B): Option[Nothing] = None

  override def flatMap[B](f: Nothing => Option[B]): Option[Nothing] = None

  override def foreach(f: Nothing => Unit): Unit = {}
}

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

for (a <- i) {
  println(a)
}

for (a <- j) {
  println(a)
}
