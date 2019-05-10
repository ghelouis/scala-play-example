package dto

import io.swagger.annotations.ApiModel

@ApiModel
case class User(name: String, age: Int)

@ApiModel
case class UserId(id: String)
