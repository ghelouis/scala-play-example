package dao

import java.util.{Date, UUID}

case class User(id: UUID, name: String, age: Int, creationDate: Date)
