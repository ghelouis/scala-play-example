package dto

import java.util.UUID

import io.swagger.annotations.ApiModel

@ApiModel
case class SimpleUserDTO(name: String, age: Int)

@ApiModel
case class UserDTO(id: UUID, name: String, age: Int)

@ApiModel
case class UserIdDTO(id: UUID)
