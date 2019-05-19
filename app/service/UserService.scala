package service

import java.util.{Date, UUID}

import dto.{SimpleUser, User, UserId}
import javax.inject.Inject
import repository.UserRepository

class UserService @Inject()(userRepository: UserRepository) {

  def createUser(user: SimpleUser): UserId = {
    val id = UUID.randomUUID()
    val creationDate = new Date()
    userRepository.save(dao.User(id, user.name, user.age, creationDate))
    UserId(id)
  }

  def getUser(id: UUID): Option[User] = {
    userRepository.get(id) match {
      case Some(user) => Some(toDto(user))
      case None => None
    }
  }

  def getUsers: Iterable[User] = {
    userRepository.getAll.map(toDto)
  }

  private def toDto(user: dao.User) = {
    User(user.id, user.name, user.age)
  }
}
