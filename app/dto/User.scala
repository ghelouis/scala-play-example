package dto

import java.util.UUID

import io.swagger.annotations.ApiModel

@ApiModel
case class SimpleUser(name: String, age: Int)

@ApiModel
case class User(id: UUID, name: String, age: Int)

@ApiModel
case class UserId(id: UUID)
