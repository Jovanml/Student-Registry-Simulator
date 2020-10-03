//Jovan McLeod, 500949479
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Set;

public class Registry 
{
   //Initializes TreeMaps of students and courses
   private Map<String,Student>     students = new TreeMap<String,Student>();
   private Map<String,ActiveCourse> courses  = new TreeMap<String,ActiveCourse>();
   public String studentFile,courseFile;

   //Reads students and courses from file and assigns students to specific courses
   public Registry() throws IOException, ArrayIndexOutOfBoundsException
   {
		this.studentFile = "students.txt";
		this.courseFile = "courses.txt";

	
			File file = new File(studentFile); 
			Scanner scan = new Scanner(file);
			while(scan.hasNext()){ 
				//Distinguishing Student Name from ID + new student referenced
				String line = scan.nextLine();
				String[] lineArray = line.split(" ");
				//Creating and adding new student to students
				String studentName = (Arrays.asList(lineArray).get(0));
				String studentId = (Arrays.asList(lineArray).get(1));
				Student newStudent = new Student(studentName,studentId); 
				students.put(studentId, newStudent);
				if(studentId.length() != 5)
				{
					throw new BadFile("Bad File Format: " + studentFile);
				}
			}
			scan.close();       
			

	   //Setting s1-s5 to their respective students
	   Student s1 = students.get(students.keySet().toArray()[0]);
	   Student s2 = students.get(students.keySet().toArray()[1]);
	   Student s3 = students.get(students.keySet().toArray()[2]);
	   Student s4 = students.get(students.keySet().toArray()[3]);
	   Student s5 = students.get(students.keySet().toArray()[4]);
	   Student s6 = students.get(students.keySet().toArray()[5]);

	   // sort the students alphabetically - see class Student
	   

	   ArrayList<Student> list = new ArrayList<Student>();
	   
	   //BONUS:Reading courses from text file

			File file1 = new File(courseFile);
			Scanner scan1 = new Scanner(file1);
			while(scan1.hasNext())
			{
				//Distinguishing course elements + course referenced
				String line = scan1.nextLine();
				String[] lineArray = line.split(",");
				//Creating course parameters
				String courseName = (Arrays.asList(lineArray).get(0));
				String courseCode = (Arrays.asList(lineArray).get(1));
				String descr = (Arrays.asList(lineArray).get(2));
				String format = (Arrays.asList(lineArray).get(3));
				list.clear();

				//Adding courses to students' courses
				//CPS209
				if(courseCode.equals("CPS209"))
				{
					list.add(s2); list.add(s3); list.add(s4);
					s2.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s3.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s4.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
				}
				//CPS511
				else if(courseCode.equals("CPS511"))
				{
					list.add(s1); list.add(s5); list.add(s6);
					s1.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s5.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s6.addCourse(courseName,courseCode,descr,format,"W2020", 0);
				}
				//CPS643
				else if(courseCode.equals("CPS643"))
				{
					list.add(s1); list.add(s2); list.add(s4); list.add(s6);
					s1.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s2.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s4.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
					s6.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
				}
			//Adding courses to course list
			ActiveCourse newCourse = new ActiveCourse(courseName,courseCode,descr,format,"W2020",list);
			courses.put(courseCode,newCourse);	
			}
		scan1.close();      

   }
   
   //Adds new student to registry
   public boolean addNewStudent(String name, String id)
   {
	   if (findStudent(id) != null) return false;
	   	   
	   students.put(id,new Student(name, id));
	   return true;
   }

   //Removing student from registry
   public boolean removeStudent(String studentId)
   {
     for (int i = 0; i < students.size(); i++)
	   if (students.containsKey(studentId))
	   {
	      students.remove(studentId);
		  return true;
	   }
	 return false;
   }
   
   //Prints all students in registry
   public void printAllStudents()
   {
	   Set<String> keySet = students.keySet();
	   for (String keys:keySet)
	   {
		   System.out.println("ID: " + students.get(keys).getId() + " Name: " + students.get(keys).getName() );   
	   }
   }
   
   //Finds student in registry
   private Student findStudent(String id)
   {
		if(students.containsKey(id))
		{
			return students.get(id);
     	}
     return null;
   }
   
   //Finds active course in registry
   private ActiveCourse findCourse(String code)
   {
		if(courses.containsKey(code))
		{
			return courses.get(code);
		}
     return null;
   }
   
   //Course to student's list of courses + student to course's list of students
   public void addCourse(String studentId, String courseCode)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   if (s.takenCourse(courseCode)) return;
	   
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   if (ac.enrolled(studentId)) return;
			   
	   ac.students.add(s);
	   s.addCourse(ac.getName(),ac.getCode(),ac.getCourseDescription(),ac.getFormat(),ac.getSemester(),0);
   }
   
   //Drops course from student's list of courses and viceversa
   public void dropCourse(String studentId, String courseCode)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.remove(studentId);
	   s.removeActiveCourse(courseCode);
   }
   
   //Prints all active courses
   public void printActiveCourses()
   {
		Set<String> keySet = courses.keySet();
      	for (String keys: keySet)
	     System.out.println(courses.get(keys).getDescription());
   }
   
   //Prints course's class list
   public void printClassList(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.printClassList();
   }
   
   //Sorts courses by Name
   public void sortCourseByName(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.sortByName();
   }
   
   //Sorts courses by Id
   public void sortCourseById(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.sortById();	   
   }
   
   //Prints grades in course
   public void printGrades(String courseCode)
   {
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   
	   ac.printGrades();
   }

   //Prints all of student's active courses
   public void printStudentCourses(String studentId)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   s.printActiveCourses();
   }
   
   //Prints student's transcript
   public void printStudentTranscript(String studentId)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   
	   s.printTranscript();
   }
   
   //Sets student's final course grade
   public void setFinalGrade(String courseCode, String studentId, double grade)
   {
	   Student s = findStudent(studentId);
	   if (s == null) return;
	   s.setGrade(courseCode, grade);
	   
	   ActiveCourse ac = findCourse(courseCode);
	   if (ac == null) return;
	   ac.remove(studentId);
   }

   //Getter from courses
   public Map<String,ActiveCourse> getCourses()
   {
	   return courses;
   }

}
