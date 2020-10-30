package data
import scala.collection.mutable.Buffer

class Food(name: String, allergenes: String, restaurant: String) {
  
  private var foodName = name
  private var foodAllergenes = allergenes
  private var foodRestaurant = restaurant match {
    case "1" => "Kvarkki"
    case "2" => "Tietotekniikantalo"
    case "3" => "Täffä"
    case "5" => "Alvari"
    case "8" => "Valimo"
    case _ => throw new Exception("False restaurant")
  }
  
  
  private var favourite = false  
 
  
  
  //favourite setter to set the given food as your favourite or undo it with the parameter t.
  def setFavourite(t:Boolean) = {
    favourite = t
  }
  
  //getters so a random user cannot edit these variables, can only view them
  def getName = {
    this.name
  }
  
  def getRestaurant = {
    this.foodRestaurant
  }
  
  def getAllergenes = {
    this.allergenes
  }
  
  
  def testPrint = {
    println(foodName +" "+ foodAllergenes + " "  + " " + foodRestaurant)
  }
  //check whether the given food is your favourite
  def isFavourite: Boolean = {
    if(favourite) {
      return true
    }
    else {
      return false
    }
  }
  
  override def toString(): String = {
    return foodName
  }
  

}