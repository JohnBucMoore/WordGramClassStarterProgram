import java.util.*;

public class WordGramTester {
	public void testWordGram(){
		String source = "this is a test this is a test this is a test of words";
		String[] words = source.split("\\s+");
		int size = 4;
		for(int index = 0; index <= words.length - size; index += 1) {
			WordGram wg = new WordGram(words,index,size);
			System.out.println(index+"\t"+wg.length()+"\t"+wg);
		}
	}
	
	public void testWordGramEquals(){
		String source = "this is a test this is a test this is a test of words";
		String[] words = source.split("\\s+");
		ArrayList<WordGram> list = new ArrayList<WordGram>();
		int size = 4;
		for(int index = 0; index <= words.length - size; index += 1) {
			WordGram wg = new WordGram(words,index,size);
			list.add(wg);
		}
		WordGram first = list.get(0);
		System.out.println("checking "+first);
		for(int k=0; k < list.size(); k++){
			//if (first == list.get(k)) {
			  if (first.equals(list.get(k))) {
				System.out.println("matched at "+k+" "+list.get(k));
			}
		}
	}

	public void testShiftAdd() {
		String source = "this is a test this is a test this is a test of words";
		String[] words = source.split("\\s+");
		WordGram wg = new WordGram(words, 0, 14);
		System.out.println(wg);
		WordGram next1 = wg.shiftAdd("again");
		WordGram next2 = next1.shiftAdd("and");
		WordGram next3 = next2.shiftAdd("again");
	}

	public void testMarkovWordWithWordGram() {
		MarkovRunner mr = new MarkovRunner();
		mr.runMarkov();
	}

	public void testEfficientMarkovWord() {
		MarkovRunner mr = new MarkovRunner();
		mr.testHashMap();
	}

	public static void main(String[] args) {
		WordGramTester w = new WordGramTester();
		w.testEfficientMarkovWord();
	}
}
