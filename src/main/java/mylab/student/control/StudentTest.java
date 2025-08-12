package mylab.student.control;

import mylab.student.entity.Student;
import mylab.student.exception.InvalidGradeException;

public class StudentTest {
	public static void main(String[] args) throws InvalidGradeException {
		Student student1 = new Student("01", "김민수", "컴퓨터공학", 3);
		
		System.out.println(student1.getName() + " / " + student1.getMajor() + " / " + student1.getGrade() + "학년");
		
		try {
			System.out.println("5학년으로 변경");
			student1.setGrade(5);
		} catch (InvalidGradeException e) {
			System.out.println(e.getMessage());
		}
	}
}
