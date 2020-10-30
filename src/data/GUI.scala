package data
import swing._
import Swing._
import scala.collection.mutable.Buffer
import swing.event._
import java.awt.Color
import java.awt.Dimension

    object GUI extends SimpleSwingApplication {
  
          private var dayFoods = Buffer[Gather]() //today's food
          
          //today means the monday of the current week => all the menus from each weekday will be displayed(monday - friday)
          private val today = {
            if(java.time.LocalDate.now.getDayOfWeek==java.time.DayOfWeek.MONDAY) {
              java.time.LocalDate.now
            }
            else if(java.time.LocalDate.now.getDayOfWeek==java.time.DayOfWeek.TUESDAY) {
              java.time.LocalDate.now.minusDays(1)
            }
            else if(java.time.LocalDate.now.getDayOfWeek==java.time.DayOfWeek.WEDNESDAY) {
              java.time.LocalDate.now.minusDays(2)
            }
            else if(java.time.LocalDate.now.getDayOfWeek==java.time.DayOfWeek.THURSDAY) {
              java.time.LocalDate.now.minusDays(3)
            }
            else if(java.time.LocalDate.now.getDayOfWeek==java.time.DayOfWeek.FRIDAY) {
              java.time.LocalDate.now.minusDays(4)
            }
            else if(java.time.LocalDate.now.getDayOfWeek==java.time.DayOfWeek.SATURDAY) {
              java.time.LocalDate.now.minusDays(5)
            }
            else {
              java.time.LocalDate.now.minusDays(6)
            }
           
          }
          
          //mainFrame and starting to create the checkBoxes for allergenes filtering:
          private var mainFrame = new MainFrame {
            val checkBoxA = new CheckBox {
              name = "A"
              text = "No Fresh garlic"
              
            }
            val checkBoxG = new CheckBox {
              name = "G"
              text = "No Gluten"
            }
            val checkBoxL = new CheckBox {
              name = "L"
              text = "No Lactose"
            }
            val checkBoxM = new CheckBox {
              name = "M"
              text = "No Milk"
            }
            val checkBoxE = new CheckBox {
              name = "E"
              text = "No Eggs"
              
            }
            val checkBoxS = new CheckBox {
              name = "S"
              text = "No Pig"
            }
            
            //add each days food buffers to the buffer dayFoods and at the same time create the table with the data:
            dayFoods+=new Gather(today.plusDays(0).toString)
            val table1 = new Table(dayFoods(0).data.size,3) {
              border = LineBorder(Color.BLACK)
              background = Color.BLACK
              foreground = Color.WHITE
            }
            dayFoods+=new Gather(today.plusDays(1).toString)
            val table2 = new Table(dayFoods(1).data.size,3) {
              border = LineBorder(Color.BLACK)
              
              background = Color.BLACK
              foreground = Color.WHITE
            }
            dayFoods+=new Gather(today.plusDays(2).toString)
            val table3 = new Table(dayFoods(2).data.size,3) {
              border = LineBorder(Color.BLACK)
              background = Color.BLACK
              foreground = Color.WHITE
            }
            dayFoods+=new Gather(today.plusDays(3).toString)
            val table4 = new Table(dayFoods(3).data.size,3) {
              border = LineBorder(Color.BLACK)
              background = Color.BLACK
              foreground = Color.WHITE
            }
            dayFoods+=new Gather(today.plusDays(4).toString)
            val table5 = new Table(dayFoods(4).data.size,3) {
              border = LineBorder(Color.BLACK)
              background = Color.BLACK
              foreground = Color.WHITE
            }
            
            private var alltables = List(table1,table2,table3,table4,table5)
            
            //getter for tests
            def getAlltables = {
              this.alltables
            }
            //go through each weekday's each food and add its data to the tables
            def getAllTheFoodsAgain {
              for(i <- 0 to 4) {
                for(k <- 0 to dayFoods(i).data.size-1) {
                  alltables(i).update(k,0,dayFoods(i).data(k).getName)
                  alltables(i).update(k,1,dayFoods(i).data(k).getRestaurant)
                  alltables(i).update(k,2,dayFoods(i).data(k).getAllergenes)
                }
              }
            }
            //and run it 
            getAllTheFoodsAgain
            
            //monday to friday table textboxes:
            var mondayText = new TextArea
            mondayText.append("Monday")
            mondayText.maximumSize = new Dimension(100,20)
            
            var tuesdayText = new TextArea
            tuesdayText.append("Tuesday")
            tuesdayText.maximumSize = new Dimension(100,20)
            
            var wednesdayText = new TextArea
            wednesdayText.append("Wednesday")
            wednesdayText.maximumSize = new Dimension(100,20)
            
            var thursdayText = new TextArea
            thursdayText.append("Thursday")
            thursdayText.maximumSize = new Dimension(100,20)
            
            var fridayText = new TextArea
            fridayText.append("Friday")
            fridayText.maximumSize = new Dimension(100,20)
            
            //adding all the contents together:
            contents = new BoxPanel(Orientation.Vertical) {
              border = EmptyBorder(10)
              contents += checkBoxA
              contents += checkBoxG
              contents += checkBoxL
              contents += checkBoxM
              contents += checkBoxE
              contents += checkBoxS
              
              contents += VStrut(10)
              contents += mondayText
              contents += VStrut(10)
              contents += table1
              contents += VStrut(10)
              contents += tuesdayText
              contents += VStrut(10)
              contents +=table2
              contents += VStrut(10)
              contents +=wednesdayText
              contents += VStrut(10)
              contents += table3
              contents += VStrut(10)
              contents += thursdayText
              contents += VStrut(10)
              contents += table4
              contents += VStrut(10)
              contents += fridayText
              contents += VStrut(10)
              contents += table5
              
              
              
            }
            
            //listen to checkboxes because the gui reacts to them as soon as they are clicked
            listenTo(checkBoxA)
            listenTo(checkBoxG)
            listenTo(checkBoxL)
            listenTo(checkBoxM)
            listenTo(checkBoxE)
            listenTo(checkBoxS)
            //add the reactions regarding each checkBox
            private var calcA = 1
            private var calcG = 1
            private var calcL = 1
            private var calcM = 1
            private var calcE = 1
            private var calcS = 1
            
            //helper function to change the data of the wanted cell to "filtered"
            def helperForBoxes(char:Char) = {
              alltables.foreach(x=>
                      for(i<-0 to x.rowCount-1) {
                        if(  x.apply(i,2).toString().contains(char)) {
                          x.update(i, 0, "filtered")
                          x.update(i, 1, "filtered")
                          x.update(i, 2, "filtered")
                        }
                      }
                     )
            }
            
            //adding all the reactions regarding each checkbox
            reactions += {
              case ButtonClicked(`checkBoxA`) => {
                calcA+=1
                    if(calcA%2==0) {
                      helperForBoxes('A')
                    }
                    else {
                      getAllTheFoodsAgain
                    }
              }
             
              case ButtonClicked(`checkBoxG`) => {
                calcG+=1
                    if(calcG%2==0) {
                      helperForBoxes('G')
                    }
                    else {
                      getAllTheFoodsAgain
                    }
              }
              case ButtonClicked(`checkBoxL`) => {
                calcL+=1
                    if(calcL%2==0) {
                      helperForBoxes('L')
                    }
                    else {
                      getAllTheFoodsAgain
                    }
              }
              case ButtonClicked(`checkBoxM`) => {
                calcM+=1
                    if(calcM%2==0) {
                      helperForBoxes('M')
                    }
                    else {
                      getAllTheFoodsAgain
                    }
              }
              case ButtonClicked(`checkBoxE`) => {
                calcE+=1
                    if(calcE%2==0) {
                      helperForBoxes('E')
                    }
                    else {
                      getAllTheFoodsAgain
                    }
              }
              case ButtonClicked(`checkBoxS`) => {
                calcS+=1
                    if(calcS%2==0) {
                      helperForBoxes('S')
                    }
                    else {
                      getAllTheFoodsAgain
                    }
              }
            }
            visible = true
          }
          //activate mainFrame
          def top = mainFrame
          
          //getter methods for tests
          def getDay = {
            today
          }
          def getDayFoods = {
            dayFoods
          }
          
          //and finally run the tests:
          private val runTests = new Tests
          
    }