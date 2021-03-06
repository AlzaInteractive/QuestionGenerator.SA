package com.alza.quiz.qfactory.geom.plane.square;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import com.alza.quiz.model.GeomQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.model.geom.plane.Geom;
import com.alza.quiz.model.geom.plane.Square;
import com.alza.quiz.qfactory.IQuestionFactory;

public class SquareFindLengthKnownArea implements IQuestionFactory {
	private int numq = 2;
	Locale loc;
	ResourceBundle bundle;

	public SquareFindLengthKnownArea() {
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}

	public SquareFindLengthKnownArea(Locale loc) {
		super();
		this.loc = loc;
		initStringFromLocale();
	}

	private void initStringFromLocale() {
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
		for (int i = 0; i < numq; i++) {
			Square sq = (Square) new Square().createExample();
			//String shapeName = bundle.getString("geom.shape2d."+sq.getName().toLowerCase());
			sq.hideTextsAndMeasurements();
			GeomQuiz q = new GeomQuiz();
			q.setGeomShape(sq.getPaths());
			q.setCorrectAnswer(Geom.formatMeasurement((sq.getLength())));
			String question = bundle.getString("geom.shape2d.question.square.length.fromarea");
			q.setQuestion(question + " " + Geom.formatMeasurement(sq.getArea()));
			q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonSubcategory(bundle.getString("geom.shape2d.square"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
			q.setLessonGrade(4);
			q.setSubCategoryOrder(5);
			q.setLocale(loc);
			q.setLessonCategory(bundle.getString("geom.shape2d"));
			quizList.add(q);
		}
		return quizList;
	}

	@Override
	public List<Quiz> generateQuizList(int numOfQuestion) {
		numq = numOfQuestion;
		return generateQuizList();
	}

}
