package com.iaroslaveremeev;

import java.io.IOException;

public class WordFisherTester {
	
	public static void main(String[] args) throws IOException {
		
		WordFisher alice = new WordFisher("texts/carroll-alice.txt",
				"stopwords.txt");
		
		WordFisher moby = new WordFisher("texts/moby-dick.txt",
				"stopwords.txt");

		System.out.println("\nThe total number of words in the text");
		System.out.println("Alice: " + alice.getWordCount());
		System.out.println("Moby Dick: " + moby.getWordCount());

		System.out.println("\nThe total number of unique words in the text");
		System.out.println("Alice: " + alice.getNumUniqueWords());
		System.out.println("Moby Dick: " + moby.getNumUniqueWords());

		System.out.println("\nCheck the frequency of a certain word");
		System.out.println("Whale in Moby Dick: " + moby.getFrequency("whale"));
		System.out.println("Handkerchief in Moby Dick: " + moby.getFrequency("handkerchief"));
		System.out.println("Handkerchief in Alice: " + alice.getFrequency("handkerchief"));

		alice.pruneVocabulary();
		moby.pruneVocabulary();
		System.out.println("\nThe total number of words in the text AFTER pruning");
		System.out.println("Alice: " + alice.getWordCount());
		System.out.println("Moby Dick: " + moby.getWordCount());

		System.out.println("\nThe top n words");
		System.out.println("The top 5 words in Alice: " + alice.getTopWords(5));
		System.out.println("The top 10 words in Moby Dick: " + moby.getTopWords(10));

		System.out.println("\nCommon popular words (if choosing from 20 most popular in each text)");
		System.out.println(moby.commonPopularWords(20, alice));
	}
}
