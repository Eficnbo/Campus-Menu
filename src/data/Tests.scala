package data
import scala.util.{Try,Success,Failure}
//this class contains all the relevant tests:
class Tests {
  
  
  
  //GUI tests:
  
  //GUI's current day should always be monday
  assert(GUI.getDay.getDayOfWeek ==java.time.DayOfWeek.MONDAY, "Day algorithm isn't working properly, should always be set to monday")
  
  //5 days meaning there should be 5 different food buffers in the buffer
  assert(GUI.getDayFoods.size == 5, "Didn't find food for all the 5 days")
  
  
  
  //Food tests
  
  //Lets create a test food:
  private var pizza = new Food("Pizza","L","3") //3 -> Täffä
  
  //is allergenes setter working correctly:
  assert(pizza.getAllergenes=="L", "Allergenes setting isn't working for food objects")
  assert(pizza.getName=="Pizza","Name setting isn't working for food objects")
  assert(pizza.getRestaurant=="Täffä","Restaurant setting isn't working for food objects")
  //is the food's restaurant actually Täffä
  assert(GUI.getDayFoods.head.data.head.getRestaurant == "Täffä", "The restaurant should be Täffä at the moment")  
  
  //making a food with bad restaurant name shouldn't work:
  Try(new Food("potato","L","100")) match {
    case Success(a) => println("Making food with wrong restaurant name shouldnt work => FIX IT")
    case Failure(f) => println("Food object working properly")
  }
  
  
 
  //Gather tests:
  //table amount correct:
  assert(GUI.top.getAlltables.size==5)
  
  
  //all the asserts were returned as true meaning the console will print out the following text:
  //otherwise it would print the throw message
  println("Testing done succesfully, no erros found")
  
}