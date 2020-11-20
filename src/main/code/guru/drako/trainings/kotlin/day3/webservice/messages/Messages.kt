package guru.drako.trainings.kotlin.day3.webservice.messages

import org.jetbrains.exposed.sql.Table

object Messages : Table() {
  val id = integer(name = "id").primaryKey().autoIncrement()
  val author = varchar(name = "author", length = 32).index()
  val content = varchar(name = "content", length = 280)
}
