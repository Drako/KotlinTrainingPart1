package guru.drako.trainings.kotlin.day3.webservice.messages

import guru.drako.trainings.kotlin.day3.webservice.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

interface MessageService {
  /**
   * @returns a [Message] with an id
   */
  suspend fun save(message: Message): Message

  suspend fun list(limit: Int = 20, offset: Int = 0): List<Message>

  suspend fun findbyAuthor(author: String, limit: Int = 20, offset: Int = 0): List<Message>

  suspend fun findById(id: Int): Message?

  suspend fun deleteById(id: Int)
}

class ExposedMessageService : MessageService {
  override suspend fun save(message: Message): Message = dbQuery {
    val id = Messages.insert {
      it[author] = message.author
      it[content] = message.content
    }.get(Messages.id)
    return@dbQuery message.copy(id = id)
  }

  override suspend fun list(limit: Int, offset: Int): List<Message> = dbQuery {
    Messages.selectAll()
      .limit(n = limit, offset = offset)
      .map(this::toMessage)
  }

  override suspend fun findbyAuthor(author: String, limit: Int, offset: Int): List<Message> = dbQuery {
    Messages.select { Messages.author eq author }
      .limit(n = limit, offset = offset)
      .map(this::toMessage)
  }

  override suspend fun findById(id: Int): Message? = dbQuery {
    Messages
      .select { Messages.id eq id }
      .limit(1)
      .singleOrNull()
      ?.let(this::toMessage)
  }

  override suspend fun deleteById(id: Int): Unit = dbQuery {
    Messages.deleteWhere { Messages.id eq id }
  }

  private fun toMessage(row: ResultRow) = Message(
    id = row[Messages.id],
    author = row[Messages.author],
    content = row[Messages.content]
  )
}
