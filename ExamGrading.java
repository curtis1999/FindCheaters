//Curtis Docherty
//260769767

import java.util.Arrays;
public class ExamGrading {
	public static void main (String []args) {
		char[] ans=       {'A','B','C','D','A','B','C','D'};
		char[][]studentAns= {{'1','B','C','D','B','B','C','C'},{'1','B','C','A','B','B','C','C'}, {'A','B','C','D','B','B','C','C'},{'A','B','C','D','A','B','C','D'}};
		
		System.out.println(Arrays.toString(gradeAllStudents(studentAns, ans)));
		System.out.println(Arrays.deepToString(findSimilarAnswers(studentAns, ans, 3)));
		
}
	//A helper method that determines the grade of a specific students answers.  
	public static double gradeStudent (char []studentAns, char []ans) {
		//initialize the number of correnct answers before the loop
		int correct=0;
		//Loop through the indexes of the answers and of the students responses.
			for (int j=0; j<ans.length; j++) {
				//If the students response is equal to the answer then the correct variable is increased
				if (studentAns[j]==ans[j]) {
					correct++;
				} 
			}
			//the variable grades is equals to the number of correct answers multiplied by 100 divided by the length of the test.
			double grades=(correct*100)/ans.length;
			return grades;
	}
	//A method that displays an array of each students grade
	public static double [] gradeAllStudents (char[][]studentAns,char[]ans) {
		//initialized new array grades
		double [] grades= new double [studentAns.length];
		//looped through the sub arrays(the students)
		for (int i=0; i<studentAns.length; i++) {
			//updated the grade at i to be what the previous method returns with the student i and the answer as input
			grades [i]=gradeStudent(studentAns[i],ans);
		}
		return grades;
	} 
	//A method that check how many wrong answers two tests have in common
	public static int numWrongSimilar(char []A, char[]B, char[]ans) {
		//initialize the number of similar wrong to 0
		int wrongSimilar=0;
		//Loop through the answers of the test
		for (int i=0; i<ans.length; i++) {
			//If student A's answer is equal to studnet B's answer at index i...
			//And student A's answer is not equal to the answer key, then wrong similar is increased
			if(A[i]==B[i] && A[i]!=ans[i]) {
				wrongSimilar++;
			}
		}
		return wrongSimilar;
	}
	//A method that determines the number of other students that a specific student has as many/more similar wrong answers than the threshold.
	public static int numMatches(char[][]studentAns, char[]ans, int index, int threshold) {
		int counter=0;
		//Loop through all of the students 
		for (int i=0; i<studentAns.length; i++) {
			//If i = index this means that you are comparing a student to themselves, so it should skip this itteration
			if (i==index) {
				continue;
			}
			//If the previous method inputed with the index student, and the current itterations student and the answer key...
			//is greater than the threshold, the counter is increased
			if (numWrongSimilar(studentAns[index], studentAns[i], ans)>=threshold){
				counter++;
			}
		}
		return counter;
	}
	//This method returns an int[][] where each index represents a student, and each sub-array lists the indices of all students...
	//who have similar wrong answers to the student at that index 
	public static int [][] findSimilarAnswers(char[][]studentAns, char []ans, int threshold){
		//initialize an array cheater which I will return
		int [][]cheaters= new int[studentAns.length][];
		//initialize an integer which will represent the index of the sub-array later
		int x=0;
		//Loop through the students answers
		for (int i=0; i<studentAns.length; i++) {
			//The length of each sub array is equal to the number of matches that the student has
			cheaters[i]= new int [numMatches(studentAns, ans, i, threshold)];
			//Loop through the students answers again to compare the student with index i to the rest of the students
			for (int j=0; j<studentAns.length; j++) {
				//If i is equal to j, then we are comparing a student to themselves and should move to the next iteration by continuing
				if(i==j) {
					continue;
				}
				//If the method numWrongSimilar with input student at i, student at j and the answers is greater than the threshold then...
				if (numWrongSimilar(studentAns[i], studentAns[j], ans)>=threshold) {
					//the student i at x is equal to the index of student j
					cheaters[i][x]=j;
					//increase x before the next iteration
					x++;
				}
			}
			//reset the value of x to 0 before the next iteration
			x=0;
		}
		return cheaters;
	}
}
