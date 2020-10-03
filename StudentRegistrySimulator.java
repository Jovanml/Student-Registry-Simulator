//Jovan McLeod, 500949479
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
public class StudentRegistrySimulator 
{
  public static void main(String[] args)
  {
	  
	  Registry registry = null;
	  //Badfile,FileNotFound, ArrayIndexOutOfBounds Exception handling for registry files(students & courses)
	  try
	  {
		registry = new Registry();
	  }
	  catch(IOException e)
	  {
		  System.out.println("Invalid File");
		  System.exit(1);
	  }
	  catch(BadFile e)
	  {
		  System.exit(1);
	  }
	  catch(ArrayIndexOutOfBoundsException e)
	  {
		System.out.println("Bad Course File");
		System.exit(1);
	  }
	  
	  Scheduler sc = new Scheduler((TreeMap<String,ActiveCourse>)registry.getCourses());
	  Scanner scanner = new Scanner(System.in);
	  System.out.print(">");
	  
	  //Scanning User Input
	  while (scanner.hasNextLine())
	  {
		  String inputLine = scanner.nextLine();
		  if (inputLine == null || inputLine.equals("")) continue;
		  
		  Scanner commandLine = new Scanner(inputLine);
		  String command = commandLine.next();
		  
		  if (command == null || command.equals("")) continue;
		  
		  else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST"))
		  {
			  registry.printAllStudents();
		  }
		  else if (command.equals("Q") || command.equals("QUIT"))
			  return;
		  //Registers student
		  else if (command.equalsIgnoreCase("REG"))
		  {
			  String name = null;
			  String id   = null;
			  if (commandLine.hasNext())
			  {
				 name = commandLine.next();
				 // check for all alphabetical
				 String lcase = name.toLowerCase();
				 if (!isStringOnlyAlphabet(lcase))
				 {
				   System.out.println("Invalid Characters in Name " + name);
				   continue;
				 }
			  }
			  if (commandLine.hasNext())
			  {
				 id = commandLine.next();
				 // check for all numeric
				 if (!isNumeric(id))
				 {
				   System.out.println("Invalid Characters in ID " + id);
				   continue;
				 }
				 if (!registry.addNewStudent(name,id))
					 System.out.println("Student " + name + " already registered");
			  }
			 
		  }
		  //Deletes student from registry
		  else if (command.equalsIgnoreCase("DEL"))
		  {
			  if (commandLine.hasNext())
			  {
				 String id = commandLine.next();
				 // check for all numeric
				 
				 if (!isNumeric(id))
				   System.out.println("Invalid Characters in student id " + id);
				 registry.removeStudent(id);
			  }
		  }
		  //Prints active courses
		  else if (command.equalsIgnoreCase("PAC"))
		  {
			  registry.printActiveCourses();
		  }		 
		  //Prints class list 
		  else if (command.equalsIgnoreCase("PCL"))
		  {
			  String courseCode = null;
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
			     registry.printClassList(courseCode);
			  }
		  }
		  //Prints course grades
		  else if (command.equalsIgnoreCase("PGR"))
		  {
			  String courseCode = null;
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
			     registry.printGrades(courseCode);
			  }
		  }
		  //Adds course to students courses
		  else if (command.equalsIgnoreCase("ADDC"))
		  {
			  String courseCode = null;
			  String id         = null;
			  
			  if (commandLine.hasNext())
			  {
				 id = commandLine.next();
			  }
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
				 registry.addCourse(id, courseCode);
			  }
			  
		  }
		  //Drops course from student's courses
		  else if (command.equalsIgnoreCase("DROPC"))
		  {
			  String courseCode = null;
			  String id         = null;
			  
			  if (commandLine.hasNext())
			  {
				 id = commandLine.next();
			  }
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
				 registry.dropCourse(id, courseCode);
			  }
			  
		  }
		  //Prints student's courses
		  else if (command.equalsIgnoreCase("PSC"))
		  {
			  String studentId = null;
			  if (commandLine.hasNext())
			  {
				 studentId = commandLine.next();
			     registry.printStudentCourses(studentId);
			  }
		  }
		  //Prints student's transcript
		  else if (command.equalsIgnoreCase("PST"))
		  {
			  String studentId = null;
			  if (commandLine.hasNext())
			  {
				 studentId = commandLine.next();
			     registry.printStudentTranscript(studentId);
			  }
		  }
		  //Sets student's final grade
		  else if (command.equalsIgnoreCase("SFG"))
		  {
			  String courseCode = null;
			  String id         = null;
			  String grade      = null;
			  double numGrade = 0;
			  
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
			  }
			  if (commandLine.hasNext())
			  {
				 id = commandLine.next();
			  }
			  if (commandLine.hasNext())
			  {
				  grade = commandLine.next();
				  if (!isNumeric(grade)) continue;
				  numGrade = Integer.parseInt(grade);
				  registry.setFinalGrade(courseCode, id, numGrade);
			  }
			  
		  }
		  //Sorts courses alphabetically by name
		  else if (command.equalsIgnoreCase("SCN"))
		  {
			  String courseCode = null;
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
			     registry.sortCourseByName(courseCode);
			  }
		  }
		  //Sorts courses by ID
		  else if (command.equalsIgnoreCase("SCI"))
		  {
			  String courseCode = null;
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
				 registry.sortCourseById(courseCode);
			  }
		  }
		  //Schedules courses 
		  else if (command.equalsIgnoreCase("SCH"))
		  {
			  String courseCode = null;
			  String day = null;
			  String start = null;
			  int start1 = 0;
			  String duration = null;
			  int duration1 = 0;
				if (commandLine.hasNext())
				{
					courseCode = commandLine.next();
				}
				if (commandLine.hasNext())
				{
					duration = commandLine.next();
					if(!isNumeric(duration)) continue;
							duration1 = Integer.parseInt(duration);
							//Exception Handling for course scheduling
							try
							{
							sc.setDayAndTime(courseCode,duration1);
							}
							catch(InvalidDurationException |UnknownCourseException | LectureTimeCollision e)
							{}
							catch(ArrayIndexOutOfBoundsException e)
							{
								System.out.println("Duration Exceeds Lecture Hours");
							}
								
								
				}			
		  }
		  //Clears course from schedule
		  else if (command.equalsIgnoreCase("CSCH"))
		  {
			  String courseCode = null;
			  if (commandLine.hasNext())
			  {
				 courseCode = commandLine.next();
				 try
				 {
					sc.clearSchedule(courseCode);
				 }
				 catch(UnknownCourseException e)
				 {}			
			  }
		  }
		  //Prints schedule
		  else if (command.equalsIgnoreCase("PSCH"))
		  {
				sc.printSchedule();		  
		  }
		  System.out.print("\n>");
	  }
	}

  
  //Checks if string is alphabetical
  private static boolean isStringOnlyAlphabet(String str) 
  { 
      return ((!str.equals("")) 
              && (str != null) 
              && (str.matches("^[a-zA-Z]*$"))); 
  } 
  
  //Checks if string is numerics
  public static boolean isNumeric(String str)
  {
      for (char c : str.toCharArray())
      {
          if (!Character.isDigit(c)) return false;
      }
      return true;
  }
  
}