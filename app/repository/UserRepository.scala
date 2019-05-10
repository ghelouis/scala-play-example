package repository

import java.util.UUID

import dao.User

class UserRepository {

  private var db: Map[UUID, User] = Map()

  def save(user: User): Unit = {
    db += (user.id -> user)
  }

  def get(id: UUID): Option[User] = {
    db.get(id)
  }

  def getAll: Iterable[User] = {
    db.values
  }
}
