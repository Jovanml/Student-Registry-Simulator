//Jovan McLeod, 500949479
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Set;
import java.util.Arrays;
public class Scheduler {
    //Initializations
    private TreeMap<String,ActiveCourse>    courses = new TreeMap<String,ActiveCourse>();
    public TreeMap<String,Integer>          dictDays = new TreeMap<String,Integer>();
    public TreeMap<String,Integer>          dictTime = new TreeMap<String,Integer>();
    private String                          courseCode;
    private String                          lectureDay;
    private int                             lectureStart;
    private int                             duration;

    //2D array of Schedule
    public  String arr[][] = {
                                {"       Mon","      Tue","     Wed","    Thur","    Fri"},
                                {"0800","        ","        ","        ","        ","        "},                 
                                {"0900","        ","        ","        ","        ","        "},  
                                {"1000","        ","        ","        ","        ","        "},                   
                                {"1100","        ","        ","        ","        ","        "},                  
                                {"1300","        ","        ","        ","        ","        "},  
                                {"1400","        ","        ","        ","        ","        "},                 
                                {"1500","        ","        ","        ","        ","        "},                        
                                {"1600","        ","        ","        ","        ","        "},                   
                                {"1700","        ","        ","        ","        ","        "},   
                             };

    public Scheduler(TreeMap<String,ActiveCourse> courses)
    {
        this.courses = courses;
    }

    //Assigns 2D array index values to Dates and Times
    public void dictionary()
    {
        dictDays.put("Mon", 1); dictDays.put("Tue", 2); dictDays.put("Wed", 3); dictDays.put("Thur", 4); dictDays.put("Fri", 5);
        dictTime.put("800",1); dictTime.put("900",2); dictTime.put("1000",3); dictTime.put("1100",4); dictTime.put("1200",5);
        dictTime.put("1300",6); dictTime.put("1400",7); dictTime.put("1500",8); dictTime.put("1600",9); dictTime.put("1700",10);
    }

    //Checks course code and lecture duration exceptions, then sets lecture's date and time
    public void setDayAndTime(String courseCode,int duration)
    {
        dictionary();
        int lectureHours = 0;

        //Exception handling
        if(!courses.containsKey(courseCode))
        {
            throw new UnknownCourseException();
        }
        else if(duration >3)
        {
            throw new InvalidDurationException();
        }

        //Increments counter on number of times courseCode is detected in schedule
        for(String[] rows:arr)
        {
            for(String columns:rows)
            {
                if(columns.contains(courseCode))
                {
                    lectureHours++;
                }
            }
        }

        //Ensures lecture hours for each course does not exceed 3 
        if(duration + lectureHours <= 3)
        {
            automaticScheduling(courseCode,duration);
        }
        else
        {
            System.out.println("Exceeded Maximum Lecture Hours");
        }
        
    }

    //Automatically schedules courses based on duration and available slots in schedule
    public void automaticScheduling(String courseCode, int duration)
    {
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        int counter = 0;

        //Goes through array to check for available timeslots
        outerloop:
        for(int i = 1; i < arr.length; i++)
        {
            for(int j = 1; j < arr[i].length; j++)
            {  
                for(int k = 0; k<duration;k++)
                {
                    if(arr[i + k][j].equals("        ") && (i+k)<arr.length)
                    {
                        x.add(i+k);
                        y.add(j);
                        counter++;
                    }
                }
                if(counter == duration)
                {
                    break outerloop;
                }
                else
                {
                    counter = 0;
                    x.clear(); y.clear();                               
                }         
            }
        }

        //Throws collision exception if lecture cannot be added
        if(x.size() == 0 && y.size() == 0)
        {
            throw new LectureTimeCollision();
        }
        //Assigns course to timeslot
        for(int i = 0; i < x.size();i++)
        {
            arr[x.get(i)][y.get(i)] = "  " + courseCode;
        }

        Set<String> keySet = dictDays.keySet();
        Set<String> keySet2 = dictTime.keySet();

        //Sets lecture date
        for(String keys:keySet)
        {
            if(dictDays.get(keys).equals(x.get(0)))
            {
                courses.get(courseCode).lectureDay = keys;
            }
        }

        //Sets lecture time
        for(String keys2:keySet2)
        {
            if(dictTime.get(keys2).equals(x.get(0)))
            {
                courses.get(courseCode).lectureStart = Integer.parseInt(keys2);
            }
        }

        //Sets lecture duration
        courses.get(courseCode).lectureDuration = duration;
    }

    //Clears schedule
    public void clearSchedule(String courseCode)
    { 
        if(!courses.containsKey(courseCode))
        {
            throw new UnknownCourseException();
        }    
                                                                 
        courses.get(courseCode).lectureDay = "";
        courses.get(courseCode).lectureStart = 0;
        courses.get(courseCode).lectureDuration = 0;
        
        //Clears printed version of schedule
        for(int i = 1; i < arr.length; i++)
        {
            for(int j = 1; j < arr[i].length; j++)
            {                
                if(arr[i][j].contains(courseCode))
                {
                    arr[i][j] = "        ";
                }
            }
        }
    }

    //Prints schedule
    public void printSchedule()
    {
        for(int i = 0; i<arr.length;i++)
        {
            for(int j = 0; j <arr[i].length;j++)
            {
                System.out.print(arr[i][j]);
            }
            System.out.print("\n");
        }     
    }   

}
