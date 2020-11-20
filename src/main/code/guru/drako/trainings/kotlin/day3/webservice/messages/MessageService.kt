package guru.drako.trainings.kotlin.day3.webservice.messages

interface MessageService {
  /**
   * @returns a [Message] with an id
   */
  fun save(message: Message): Message

  fun list(limit: Int = 20, offset: Int = 0): List<Message>

  fun findbyAuthor(author: String, limit: Int = 20, offset: Int = 0): List<Message>

  fun findById(id: Int): Message?

  fun deleteById(id: Int)
}
