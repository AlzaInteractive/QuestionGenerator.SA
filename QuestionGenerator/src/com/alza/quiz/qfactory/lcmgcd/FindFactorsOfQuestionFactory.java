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
import com.alza.quiz.util.StringUtils;

public class FindFactorsOfQuestionFactory implements IQuestionFactory{
	private static int NUMQ = 2;
	Locale loc;
	ResourceBundle bundle;
	
	public FindFactorsOfQuestionFactory() {
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}
	
	public FindFactorsOfQuestionFactory(Locale loc) {
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
		int minBase=15,maxBase=41;
		for (int i=0;i<NUMQ;i++){
			int base;
			List<Integer> divs;
			do {
				base = ThreadLocalRandom.current().nextInt(minBase, maxBase);
				divs = MathUtils.findDivisors(base);
			} while (divs.size()<3);
			MultipleChoiceQuiz q = new MultipleChoiceQuiz();
			q.setQuestion(bundle.getString("lcmgcd.question.factorof")+" "+base+" ?");
			q.setCorrectAnswer(StringUtils.join(divs, ","));
			q.addChoice(base+1+","+base+2+","+base +3+","+base +4);
			q.addChoice(base/2+","+base/3+","+base /4+","+base /5);
			q.addChoice(base*2+","+base*3+","+base *4);
			q.addChoice(base+","+base*base+","+base*base*base);
			q.addChoice(StringUtils.join(divs, ","));
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonCategory(bundle.getString("lcmgcd"));
			q.setLessonSubcategory(bundle.getString("lcmgcd.subcategory.factors"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonGrade(4);
			q.setSubCategoryOrder(1);
			q.setLocale(loc);
			lq.add(q);
		}
		return lq;
	}
	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		NUMQ = numOfQuestion;
		return generateQuizList();
	}
}
