package repository

import java.sql.Timestamp
import java.util.{Date, UUID}

import dao.User
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

class UserRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val users = TableQuery[UsersTable]

  def save(user: User): Future[Int] = {
    db.run(users += user)
  }

  def get(id: UUID): Future[Option[User]] = {
    db.run(users.filter(_.id === id).result.headOption)
  }

  def getAll: Future[Seq[User]] = {
    db.run(users.result)
  }

  implicit def dateMapper: BaseColumnType[Date] = MappedColumnType.base[Date, Timestamp](d => new Timestamp(d.getTime), identity)

  private class UsersTable(tag: Tag) extends Table[User](tag, "users") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")
    def age = column[Int]("age")
    def creationDate = column[Date]("creation_date")
    def * = (id, name, age, creationDate) <> ((User.apply _).tupled, User.unapply)
  }
}
