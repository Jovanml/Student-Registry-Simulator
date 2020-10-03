//Jovan McLeod, 500949479
import java.util.ArrayList;

public class CreditCourse extends Course 
{
	//Initializations
	private String  semester;
	public  double  grade;
	public  boolean active;
	
	//References Instance variables
	public CreditCourse(String name, String code, String descr, String fmt,String semester, double grade)
	{
		// redundant 
		super(name, code, descr, fmt);
		this.semester = semester;
		this.grade    = grade;
		active = false;
	}
	
	//Sets active to true
	public void setActive()
	{
		active = true;
	}
	
	//Sets active to false
	public void setInactive()
	{
		active = false;
	}
	
	//Displays student's active course grade 
	public String displayGrade()
	{
		return getCode() + " " + getName() + " " + semester + " Grade " + convertNumericGrade(grade); 
	}
	
}
