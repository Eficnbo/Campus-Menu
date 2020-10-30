package data

import io.Source
import java.net.URL
import scala.collection.mutable.Buffer

class Gather(date: String) {
  
  //reading data from the server with a GET request
  def readData(restaurant:String,date: String): String = {
    val url = "https://kitchen.kanttiinit.fi/restaurants/"+restaurant+ "/menu?day="+date
    
    //without properties sending a GET request wouldn't work:
    val requestProperties = Map(
      "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:73.0) Gecko/20100101 Firefox/73.0)"
    )
    //open TCP connection to the wanted server
    val connection = new URL(url).openConnection
    requestProperties.foreach({
      case (name, value) => connection.setRequestProperty(name, value)
    })
    
    //and finally get the lines from the html
    Source.fromInputStream(connection.getInputStream).getLines.mkString("\n")
  }
  
  
  
  //method to parse the menu's data into a list of strings consisting of the food and the allergenes
  def menuToFoodAndAllergenes(data:String):Array[String] = {
    var todaysFoods = (data.split("title").drop(1).mkString)
    var eachFood = todaysFoods.split("}")
    
    //The first food is differently formatted compared to the others so it has to be done manually and the rest automatically:
    var first = eachFood(0).drop(2)
                         .takeWhile(_!=']')
                         .dropRight(1)
    
    var otherFoods = eachFood.drop(1)
                             .map(x=>x.drop(5)
                             .takeWhile(_!=']'))
    //and add the arrays together
    return Array(first)++otherFoods
  }
  
  //making the menuToFoodAndAllergenes output into Food objects:
  def toFoodList(data:Array[String],restaurant: String): Buffer[Food] = {
    var foodList = Buffer[Food]()
    
    //getting the wanted food infos here by mapping the HTML data
    var foodNames = data.map(x=>x.drop(1)
                                 .takeWhile(_!=',')
                                 .dropRight(1))
    var allergenes = data.map(x=>x.dropWhile(_!='[')
                                  .drop(1).split(',')
                                  .mkString
                                  .filter(_.isUpper)) //filtering " characters
    //add all the gotten data to the foodList               
    for(i <- 0 to foodNames.length-3) {
      foodList+=new Food(foodNames(i),allergenes(i),restaurant)
    }
    
    foodList
  }
  //all the wanted data here:
  var data = toFoodList(menuToFoodAndAllergenes(readData("3",date)),"3") //3 => Täffä
  
  
  
}
