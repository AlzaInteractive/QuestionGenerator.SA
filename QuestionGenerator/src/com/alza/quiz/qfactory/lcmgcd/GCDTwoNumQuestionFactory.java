package com.alza.quiz.qfactory.lcmgcd;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import com.alza.common.math.MathUtils;
import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.qfactory.IQuestionFactory;

public class GCDTwoNumQuestionFactory implements IQuestionFactory{
	private static int numq = 2;
	static int[][] bounds = {
		{12,29},{30,60}
	};
	
	Locale loc;
	ResourceBundle bundle;
	
	public GCDTwoNumQuestionFactory(){
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}
	
	public GCDTwoNumQuestionFactory(Locale loc) {
		this.loc = loc;
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
		List<Quiz> lq = new ArrayList<Quiz>();
		for (int i=0;i<numq;i++){
			int val1,val2,gcd,lcm;
			int loBo = bounds[0][0];
			int hiBo = bounds[0][1];
			if (i > 3) {
				loBo = bounds[1][0];
				hiBo = bounds[1][1];
			}
			do {
				val1 = ThreadLocalRandom.current().nextInt(loBo, hiBo);
				val2 = ThreadLocalRandom.current().nextInt(loBo, hiBo);
				gcd = MathUtils.findGCD(val1,val2);
				lcm = MathUtils.findLCM(val1, val2);
			} while (val1==val2 || gcd<3);
			MultipleChoiceQuiz q = new MultipleChoiceQuiz();
			//q.setQuestion("FPB dari bilangan " + val1 + " dan " + val2 + " adalah?");
			q.setQuestion(bundle.getString("lcmgcd.question.gcdof") +" "+ val1+" & "+val2+" ?");
			q.setCorrectAnswer(String.valueOf(gcd));
			q.addChoice(String.valueOf(gcd));
			q.addChoice(String.valueOf(lcm));
			q.addChoice(String.valueOf(val1/gcd));
			q.addChoice(String.valueOf(val2/gcd));
			q.addChoice(String.valueOf(val1+val2));
			q.addChoice(String.valueOf(val1*val2));
			
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonCategory(bundle.getString("lcmgcd"));
			q.setLessonSubcategory(bundle.getString("lcmgcd.subcategory.gcd"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonGrade(4);
			q.setSubCategoryOrder(2);
			q.setLocale(loc);
			lq.add(q);
		}
		return lq;
	}
	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		numq = numOfQuestion;
		return generateQuizList();
	}
}
