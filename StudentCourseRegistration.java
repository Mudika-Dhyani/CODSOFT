import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }
}

class Student {
    private int id;
    private String name;
    private List<Course> registeredCourses;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }
}
class CourseRegistrationSystem {
    public static void main(String[] args) {
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Course Registration System");
            System.out.println("1. Add a Course");
            System.out.println("2. Register a Student");
            System.out.println("3. List Courses");
            System.out.println("4. List Students");
            System.out.println("5. Register Student for Course");
            System.out.println("6. Drop Course for Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Enter Course Title: ");
                    String courseTitle = scanner.nextLine();
                    System.out.print("Enter Course Description: ");
                    String courseDescription = scanner.nextLine();
                    System.out.print("Enter Course Capacity: ");
                    int courseCapacity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Course Schedule: ");
                    String courseSchedule = scanner.nextLine();

                    Course course = new Course(courseCode, courseTitle, courseDescription, courseCapacity, courseSchedule);
                    courses.add(course);
                    System.out.println("Course added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();

                    Student student = new Student(studentId, studentName);
                    students.add(student);
                    System.out.println("Student registered successfully.");
                    break;

                case 3:
                    System.out.println("\nCourse Listing:");
                    for (Course c : courses) {
                        System.out.println("Course Code: " + c.getCode());
                        System.out.println("Course Title: " + c.getTitle());
                        System.out.println("Description: " + c.getDescription());
                        System.out.println("Capacity: " + c.getCapacity());
                        System.out.println("Schedule: " + c.getSchedule());
                        System.out.println("Available Slots: " + (c.getCapacity() - getStudentsRegisteredCount(c, students)) + "\n");
                    }
                    break;

                case 4:
                    System.out.println("\nStudent Listing:");
                    for (Student s : students) {
                        System.out.println("Student ID: " + s.getId());
                        System.out.println("Name: " + s.getName());
                        System.out.println("Registered Courses:");
                        List<Course> registeredCourses = s.getRegisteredCourses();
                        if (registeredCourses.isEmpty()) {
                            System.out.println("No courses registered.");
                        } else {
                            for (Course c : registeredCourses) {
                                System.out.println("Course Code: " + c.getCode());
                                System.out.println("Course Title: " + c.getTitle());
                            }
                        }
                        System.out.println();
                    }
                    break;

                case 5:
                    System.out.print("Enter Student ID: ");
                    int studentIdToRegister = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Course Code to Register: ");
                    String courseCodeToRegister = scanner.nextLine();

                    Student studentToRegister = findStudentById(studentIdToRegister, students);
                    Course courseToRegister = findCourseByCode(courseCodeToRegister, courses);

                    if (studentToRegister != null && courseToRegister != null) {
                        if (getStudentsRegisteredCount(courseToRegister, students) < courseToRegister.getCapacity()) {
                            studentToRegister.registerCourse(courseToRegister);
                            System.out.println("Student registered for the course successfully.");
                        } else {
                            System.out.println("Course is already full. Cannot register the student.");
                        }
                    } else {
                        System.out.println("Student or course not found. Please check the ID and course code.");
                    }
                    break;

                case 6:
                    System.out.print("Enter Student ID to drop a course: ");
                    int studentIdToDrop = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter Course Code to drop: ");
                    String courseCodeToDrop = scanner.nextLine();

                    Student studentToDrop = findStudentById(studentIdToDrop, students);
                    Course courseToDrop = findCourseByCode(courseCodeToDrop, courses);

                    if (studentToDrop != null && courseToDrop != null) {
                        if (studentToDrop.getRegisteredCourses().contains(courseToDrop)) {
                            studentToDrop.dropCourse(courseToDrop);
                            System.out.println("Course dropped successfully.");
                        } else {
                            System.out.println("Student is not registered for this course.");
                        }
                    } else {
                        System.out.println("Student or course not found. Please check the ID and course code.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }

    private static Student findStudentById(int id, List<Student> students) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourseByCode(String code, List<Course> courses) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private static int getStudentsRegisteredCount(Course course, List<Student> students) {
        int count = 0;
        for (Student student : students) {
            if (student.getRegisteredCourses().contains(course)) {
                count++;
            }
        }
        return count;
    }
}
