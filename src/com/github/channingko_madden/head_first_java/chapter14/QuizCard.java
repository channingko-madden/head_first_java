package com.github.channingko_madden.head_first_java.chapter14;

/** @brief QuizCard class 
 *  Holds QuizCard data */

public class QuizCard
{
	private String mQuestion;
	private String mAnswer;

	QuizCard(String question, String answer)
	{
		mQuestion = question;
		mAnswer = answer;
	}

	public String getQuestion()
	{
		return mQuestion;
	}

	public String getAnswer()
	{
		return mAnswer;
	}
}
