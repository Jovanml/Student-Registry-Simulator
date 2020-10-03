//Jovan McLeod, 500949479
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Active University Course
 
public class ActiveCourse extends Course
{
   public  ArrayList<Student> students; // map id to name
   private String             semester;
   public String			  lectureDay;
   public int				  lectureStart;
   public int 				  lectureDuration;
	
   public ActiveCourse(String name, String code, String descr, String fmt,String semester,ArrayList<Student> students)
   {
	   super(name, code, descr, fmt);
	   this.semester = semester;
	   this.students = new ArrayList<Student>(students);
   }
   
   //Gets course semester
   public String getSemester()
   {
	   return semester;
   }
   
   //Prints course class list
   public void printClassList()
   {
	   for (int i = 0; i < students.size(); i++)
	   {
		   System.out.println(students.get(i).toString());
	   }
   }
   
   //Prints course grades
   public void printGrades()
   {
	   for (int i = 0; i < students.size(); i++)
	   {
		   Student s = students.get(i);
		   System.out.println(s.getId() + " " + s.getName() + " " + s.getGrade(getCode()));
	   }
   }
   
   //Gets course description
   public String getDescription()
   {
	   return super.getDescription() + " " + semester + " Enrolment: " + students.size() +  "\n";
   }
   
   public String getCourseDescription()
   {
	   return getDescr();
   }
	
   //Gets student's grade in course
   public double getGrade(String studentId)
   {
	   for (int i = 0; i < students.size(); i++)
	   {
		   if (studentId.equals(students.get(i).getId()))
		   {
			   return students.get(i).getGrade(getCode());
		   }
	   }
	   return 0;
   }
   
   //Checks if student is enrolled in course
   public boolean enrolled(String studentId)
   {
	   for (int i = 0; i < students.size(); i++)
	   {
		   if (studentId.equals(students.get(i).getId()))
		     return true;
	   }
	   return false;
   }
   
   //Removes student from course by ID
   public void remove(String id)
   {
	   for (int j = 0; j < students.size(); j++)
	   {
   		   Student s = students.get(j);
   		   if (s.getId().equals(id))
   		   {
   		     students.remove(j);
   		     return;
   		   }
 	   }
    }
   
   //Sorts students' names in course 
   public void sortByName()
   {
 	  Collections.sort(students, new NameComparator());
   }
   
   //Implementation for Comparator Interface
   private class NameComparator implements Comparator<Student>
   {
   	public int compare(Student a, Student b)
   	{
   	  return a.getName().compareTo(b.getName());	  
   	}
   }
   
   //Student Id Sort
   public void sortById()
   {
 	  Collections.sort(students, new IdComparator());
   }
   
   private class IdComparator implements Comparator<Student>
   {
   	public int compare(Student a, Student b)
   	{
   	  return a.getId().compareTo(b.getId());	  
   	}
   }
}
