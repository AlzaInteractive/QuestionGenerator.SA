package com.alza.quiz.qfactory.fraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import com.alza.common.math.Fraction;
import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.qfactory.IQuestionFactory;
import com.alza.quiz.util.CommonFunctionAndValues;

public class FractionConvertToDecimal implements IQuestionFactory {
	private static int numq = 4;
	Locale loc;
	ResourceBundle bundle;
	public FractionConvertToDecimal(Locale loc){
		this.loc = loc;
		initStringFromLocale();
	}
	public FractionConvertToDecimal(){
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
		List<Quiz> quizList = new ArrayList<Quiz>();
		generateFractionToDecimal(quizList);
		return quizList;
	}

	private void generateFractionToDecimal(List<Quiz> quizList) {
		int z = numq/ 2;
		for (int i=0;i<z;i++){
			MultipleChoiceQuiz q = new MultipleChoiceQuiz();
			int a,denom;
			if (i % 2 == 1){
				do {
					a = CommonFunctionAndValues.getRandomInt(2, 10);
					denom = CommonFunctionAndValues.getRandomInt(2, 11);					
				} while (denom>=a || (a%denom==0));
			} else {
				do {
					a = CommonFunctionAndValues.getRandomInt(2, 23);
					denom = CommonFunctionAndValues.getRandomInt(2, 10);
				} while (denom<=a);
			}
			Fraction fQuest = new Fraction(a,denom);
			String correctAnswer = fQuest.getThreeDigitDecimalForm();
			Fraction f1 = fQuest.getResultWhenMultipliedBy(new Fraction(10,1));
			Fraction f2 = fQuest.getResultWhenMultipliedBy(new Fraction(1,10));
			Fraction f3 = fQuest.getResultWhenMultipliedBy(new Fraction(1,5));
			Fraction f4 = fQuest.getResultWhenMultipliedBy(new Fraction(2,1));
			List<Fraction> choices = new ArrayList<Fraction>();
			choices.add(f1);choices.add(f2);choices.add(f3);choices.add(f4);choices.add(fQuest);
			Collections.shuffle(choices);
			
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setChoices(convertChoices(choices,true));
			q.setCorrectAnswer(correctAnswer);
			q.setQuestion(CommonFunctionAndValues.MJXTAG+
					bundle.getString("fraction.decimalvalueof")
					+"\frac{"+a+"}{"+denom+"}"
					+CommonFunctionAndValues.MJXTAG);
			q.setLessonSubcategory(bundle.getString("fraction.convtodecimal"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonCategory(bundle.getString("fraction"));
			q.setLessonGrade(5);
			q.setSubCategoryOrder(3);
			q.setLocale(loc);
			quizList.add(q);
		}
	}
	private List<String> convertChoices(List<Fraction> fracs, boolean toDecimal){
		List<String> choicesInString = new ArrayList<String>();
		for (Fraction f : fracs) {
			if (toDecimal) choicesInString.add(f.getThreeDigitDecimalForm());
			else choicesInString.add(f.getSimplestForm().toString());
		}
		return choicesInString;
	}
	
	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		numq = numOfQuestion;
		return generateQuizList();
	}

}
