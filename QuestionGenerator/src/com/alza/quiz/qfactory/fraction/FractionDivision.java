package com.alza.quiz.qfactory.fraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import com.alza.common.math.Fraction;
import com.alza.common.math.MathUtils;
import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.qfactory.IQuestionFactory;
import com.alza.quiz.util.CommonFunctionAndValues;

public class FractionDivision implements IQuestionFactory{
	private static int numq = 4;
	Locale loc;
	ResourceBundle bundle;
	public FractionDivision(Locale loc){
		this.loc = loc;
		initStringFromLocale();
	}
	public FractionDivision(){
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}
	private void initStringFromLocale(){
		bundle = ResourceBundle.getBundle("lang.langbundle", loc);
	}
	@Override
	public Quiz generateQuiz() {
		List<Quiz> quizList = generateQuizList();
		int rnd = new Random().nextInt(quizList.size()); 
		return quizList.get(rnd);
	}

	@Override
	public Quiz generateQuiz(QuizLevel quizLevel) {
		return generateQuiz();
	}

	@Override
	public List<Quiz> generateQuizList() {
		List<Quiz> quizList= new ArrayList<Quiz>();
		for (int i=0; i<numq; i++){
			MultipleChoiceQuiz q = null;
			q = generateQuestion(i);
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonSubcategory(bundle.getString("fraction.division"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonCategory(bundle.getString("fraction"));
			q.setSubCategoryOrder(1);
			q.setLessonGrade(5);
			q.setLocale(loc);
			quizList.add(q);
		}
		return quizList;
	}

	private MultipleChoiceQuiz generateQuestion(int i) {
		MultipleChoiceQuiz q = new MultipleChoiceQuiz();		
		int a1,a2;
		int denomLeft,denomRight;
		int gcdL,gcdR;
		Fraction f1,f2;
		if (i < 3) {
			do {
				denomLeft = CommonFunctionAndValues.getRandomInt(2, 9);
				denomRight = CommonFunctionAndValues.getRandomInt(2, 9);
				a1 = CommonFunctionAndValues.getRandomInt(2, 10);
				a2 = CommonFunctionAndValues.getRandomInt(2, 10);
			} while (a1==a2 || a1 >= denomLeft || a2 >= denomRight || (a1 * denomRight == a2 * denomLeft)); // ensure no common numbers						
			f1 = new Fraction(a1, denomLeft);
			f2 = new Fraction(a2, denomRight);
			q.setDifficultyLevel(QuizLevel.MUDAH);
			
		} else  {
			do {
				denomLeft = CommonFunctionAndValues.getRandomInt(5, 21);;
				denomRight = CommonFunctionAndValues.getRandomInt(5, 21);
				a1 = CommonFunctionAndValues.getRandomInt(5, 21);
				a2 = CommonFunctionAndValues.getRandomInt(5, 21);
				gcdL = MathUtils.findGCD(a1,denomRight);
				gcdR = MathUtils.findGCD(a2,denomLeft);
			} while (a1 == a2 || a1==denomLeft || a2 == denomRight
					|| gcdL < 2 || gcdR < 2);
			f1 = new Fraction(a1, denomLeft);
			f2 = new Fraction(a2, denomRight);
			q.setDifficultyLevel(QuizLevel.SEDANG);
		} 
		
		Fraction result;
		result = f1.getResultWhenDividedBy(f2);
		q.setQuestion(f1.toMathJaxString()+" \u00f7 "+f2.toMathJaxString());
		q.setCorrectAnswer(result.toString());
		q.setChoices(buildChoices(f1,f2,result));
		return q;
	}
	
	private Set<String> buildChoices(Fraction f1,Fraction f2,Fraction result){
		Fraction[] choices = new Fraction[3];
		choices[0] = result;		
		choices[1] = result.inverse();		
		choices[2] = f2.getResultWhenDividedBy(f1.inverse());				
		Set<String> choicesInString = new HashSet<String>();
		for (Fraction f : choices) {
			choicesInString.add(f.toString());
		}
		return choicesInString;
	}
	
	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		numq = numOfQuestion;
		return generateQuizList();
	}

}
