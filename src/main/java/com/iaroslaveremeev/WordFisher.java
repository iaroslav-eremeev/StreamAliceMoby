package com.iaroslaveremeev;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class WordFisher {

    // Please note these variables. they are the state of the object.
    public HashMap<String, Long> vocabulary;
    public List<String> stopwords; // User ArrayList for initialization
    private String inputTextFile;
    private String stopwordsFile;

    WordFisher(String inputTextFile, String stopwordsFile) throws IOException {
        this.inputTextFile = inputTextFile;
        this.stopwordsFile = stopwordsFile;

        buildVocabulary();
        getStopwords();
    }

    /**
     * load in each word from inputTextFile into the vocabulary
     */
    public void buildVocabulary() throws IOException {
        Map<String, Long> vocabulary = Files.readAllLines(Paths.get(this.inputTextFile)).stream()
                .map(x -> x.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+"))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        vocabulary.remove("");
        this.vocabulary = (HashMap<String, Long>) vocabulary;
    }

    public void getStopwords() {
        stopwords = new ArrayList<String>();
        String word;

        try {
            BufferedReader input = new BufferedReader(new FileReader(stopwordsFile));
            while ((word = input.readLine()) != null) {
                stopwords.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method returns the total number of words in the text
     */
    public int getWordCount() {
        return (int) this.vocabulary.values().stream().mapToLong(Long::intValue).sum();
    }

    /**
     * This method returns the total number of unique words in the text.
     * This can be  obtained using the map vocabulary.
     */
    public int getNumUniqueWords() {
        return (int) this.vocabulary.entrySet().stream().filter(x -> x.getValue() == 1).count();
    }

    /**
     * This returns the word frequency for a given word. This can be obtained using the map vocabulary.
     * If the word does not exist in the vocabulary, -1 should be returned
     */
    public int getFrequency(String word) {
        return Math.toIntExact(this.vocabulary.getOrDefault(word, -1L));
    }

    /**
     * This method removes all stopwords from vocabulary.
     * After pruning, getWordCount() on Moby Dick returns 110,717 words;
     * Alice  returns 12,241. (that’s a lot of words removed!)
     */
    public void pruneVocabulary() {
        this.vocabulary.entrySet().removeIf(entry ->
                this.stopwords.contains(entry.getKey()));
    }

    /**
     * This method receives an integer n and returns
     * the top n most frequently occurring words in the text as an ArrayList of strings.
     */
    public List<String> getTopWords(int n) {
        return this.vocabulary.entrySet().stream()
                .sorted(Comparator.comparingLong(x -> -x.getValue()))
                .limit(n).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * This method receives an integer n and another WordFisher object (i.e. another  text)
     * as input and returns an ArrayList of the common popular words
     * (taken from the n most popular from the first text, n most popular from the other) between the two texts.
     * An empty list should be returned if there are no common words.
     */
    public List<String> commonPopularWords(int n, WordFisher other) {
        List<String> mainPopWords = this.getTopWords(n);
        List<String> otherPopWords = other.getTopWords(n);
        mainPopWords.retainAll(otherPopWords);
        return mainPopWords;
    }

}
