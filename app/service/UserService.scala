package service

import java.util.{Date, UUID}

import dto.{SimpleUserDTO, UserDTO, UserIdDTO}
import javax.inject.Inject
import repository.UserRepository

import scala.concurrent.{ExecutionContext, Future}

class UserService @Inject()(userRepository: UserRepository, implicit val ec: ExecutionContext) {

  def createUser(user: SimpleUserDTO): (Future[Int], UserIdDTO) = {
    val id = UUID.randomUUID()
    val creationDate = new Date()
    val futureResult = userRepository.save(dao.User(id, user.name, user.age, creationDate))
    (futureResult, UserIdDTO(id))
  }

  def getUser(id: UUID): Future[Option[UserDTO]] = {
    userRepository.get(id).map(maybeUser => maybeUser.map(toDto))
  }

  def getUsers: Future[Seq[UserDTO]] = {
    userRepository.getAll.map(users => users.map(toDto))
  }

  private def toDto(user: dao.User) = {
    UserDTO(user.id, user.name, user.age)
  }
}
