package com.alza.quiz.qfactory.lcmgcd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import com.alza.common.math.MathUtils;
import com.alza.quiz.model.MultipleChoiceQuiz;
import com.alza.quiz.model.Quiz;
import com.alza.quiz.model.QuizLevel;
import com.alza.quiz.util.CommonFunctionAndValues;

/**
 * Created by ewin.sutriandi@gmail.com on 24/12/16.
 */

public class LCMBasicScenarioQuestionFactory extends LCMTwoNumQuestionFactory {
    List<String> sces;
	Locale loc;
	ResourceBundle bundle,scenarioBundle;
	private static final int PARAMLENGTH=5;
	
	public LCMBasicScenarioQuestionFactory(Locale loc){
		this.loc = loc;
		initStringFromLocale();
	}
	public LCMBasicScenarioQuestionFactory(){
		this.loc = new Locale("in", "ID");
		initStringFromLocale();
	}
	private void initStringFromLocale(){
		bundle = ResourceBundle.getBundle("lang.langbundle", loc);
		scenarioBundle = ResourceBundle.getBundle("lang.scenario-lcm", loc);
		sces = CommonFunctionAndValues.getStringCollection(scenarioBundle, "lcm.basic2");
	}
        
    public List<Quiz> generateQuizList(){
        List<Quiz> quizList = new ArrayList<>();
        Collections.shuffle(sces);
        for (int i=0;i<numq;i++){
        	int pos = i % sces.size();
			String sce = getSceScenario(pos);
			String param =  getParams(pos);
			int loBo,hiBo,offset,val1,val2,gcd,lcm;
			loBo = Integer.parseInt(param.substring(0, 2));
			hiBo = Integer.parseInt(param.substring(2, 4));
			offset = Integer.parseInt(param.substring(4, 5));
        	
			do {
				val1 = ThreadLocalRandom.current().nextInt(loBo, hiBo);
				val2 = ThreadLocalRandom.current().nextInt(loBo, hiBo);
				gcd = MathUtils.findGCD(val1,val2);
				lcm = MathUtils.findLCM(val1,val2);
			} while (val1==val2);
			int[] pairs = new int[]{val1,val2};
            String[] pairPeople = CommonFunctionAndValues.getPairofPeople();
            sce = CommonFunctionAndValues.buildScenario(sce,pairPeople,pairs);
            int correctAnswer = lcm-offset;
            
            MultipleChoiceQuiz q = new MultipleChoiceQuiz();
            q.setLessonGrade(4);
            q.setQuestion(sce);
            q.setCorrectAnswer(String.valueOf(correctAnswer));
            q.setChoices(generateChoices(pairs));
            q.setDifficultyLevel(QuizLevel.MUDAH);
			q.setLessonCategory(bundle.getString("lcmgcd"));
			q.setLessonSubcategory(bundle.getString("lcmgcd.subcategory.lcm"));
			q.setLessonClassifier(bundle.getString("mathelementary"));
            quizList.add(q);
        }

        return quizList;
    }

    @Override
    public MultipleChoiceQuiz generateQuiz(QuizLevel quizLevel) {
        return null;
    }

    private String getParams(int rnd) {
		String s = sces.get(rnd);
		String params = s.substring(s.length()-PARAMLENGTH);
		//System.out.println(params);
		return params;
	}
	public String getSceScenario(int idx){
		String s = sces.get(idx);
		String sce = s.substring(0,s.length()-(PARAMLENGTH));
		return sce;
	}
}
