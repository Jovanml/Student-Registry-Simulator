//Jovan McLeod, 500949479
import java.util.ArrayList;

public class Student implements Comparable<Student>
{
  //Initializations
  private String name;
  private String id;
  private ArrayList<CreditCourse> courses;
  
  public Student(String name, String id)
  {
	 this.name = name;
	 this.id   = id;
	 courses = new ArrayList<CreditCourse>();
  }
  
  //Gets student ID
  public String getId()
  {
	  return id;
  }
  
  //Gets student name
  public String getName()
  {
	  return name;
  }
  
  //Checks if student has taken course
  public boolean takenCourse(String courseCode)
  {
    for (int j = 0; j < courses.size(); j++)
    {
      if (courses.get(j).getCode().equalsIgnoreCase(courseCode))
        return true;
	}
    return false;
  }
  
  //Adds course to student's course list
  public void addCourse(String courseName, String courseCode, String descr, String format,String sem, double grade)
  {
	  CreditCourse cc = new CreditCourse(courseName,courseCode,descr,format,sem, grade);
	  cc.setActive();
	  courses.add(cc);
  }
  
  //Gets student's grade in course
  public double getGrade(String courseCode)
  {
	  for (int i = 0; i < courses.size(); i++)
	  {
		if (courses.get(i).getCode().equals(courseCode))
		{
			return courses.get(i).grade; 
		}
	  }
	  return 0;
  }
  
  //Sets student's grade in course
  public void setGrade(String courseCode, double grade)
  {
    for (int k = 0; k < courses.size(); k++)
    {
	   if (courses.get(k).getCode().equalsIgnoreCase(courseCode))
	   {
		  courses.get(k).grade = grade;
		  courses.get(k).setInactive();
	   }
    }
  }
  
  //Prints student's transcript
  public void printTranscript()
  {
	  for (int i = 0; i < courses.size(); i++)
	  { 
		  if (!courses.get(i).active) 
			  System.out.println(courses.get(i).displayGrade());
	  }
  }
  
  //Prints student's active courses
  public void printActiveCourses()
  {
	 for (int i = 0; i < courses.size(); i++)
	 {
		 if (courses.get(i).active)
		   System.out.println(courses.get(i).getDescription());
	 } 
  }
  
  //Prints student's completed courses
  public void printCompletedCourses()
  {
	 for (int i = 0; i < courses.size(); i++)
	 {
		 if (!courses.get(i).active)
		   System.out.println(courses.get(i).getDescription());
	 }
  }
  
  // Student has dropped course
  public void removeActiveCourse(String courseCode)
  {
	  for (int i = 0; i < courses.size(); i++)
	 {
		 if (courses.get(i).getCode().equals(courseCode) && courses.get(i).active) 
		 {
            courses.remove(i);
            return;
		 }
	 }
  }
  
  //Prints student's ID and name
  public String toString()
  {
	  return "Student ID: " + id + " Name: " + name;
  }
  
  //Compares students
  public int compareTo(Student other)
  {
	  return this.name.compareTo(other.name);
  }
  
  public boolean equals(Student other)
  {
	  Student s = (Student) other;
	  return this.name.equals(s.name) && this.id.equals(s.id);
  }
  
}
