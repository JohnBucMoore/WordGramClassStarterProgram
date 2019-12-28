import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    private int indexOf(String[] words, WordGram target, int start) {
        for (int i = start; i < words.length - myOrder; i++) {
            String[] subWordsArray = new String[target.length()];
            for (int j = 0; j < target.length(); j++) {
                subWordsArray[j] = words[i+j];
            }
            WordGram w = new WordGram(subWordsArray, 0, target.length());
            if (w.equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int currIndex = 0;
        while (currIndex != -1) {
            int keyIndex = indexOf(myText, kGram, currIndex);
            if (keyIndex != -1) {
                int followingIndex = keyIndex + myOrder;
                if (followingIndex < myText.length - myOrder) {
                    follows.add(myText[followingIndex]);
                    currIndex = keyIndex+1;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return follows;
    }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        String[] key = new String[myOrder];
        for (int i = 0; i < key.length; i++) {
            key[i] = myText[index+i];
        }
        WordGram kGram = new WordGram(key, 0, myOrder);
        sb.append(kGram.toString());
        sb.append(" ");
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(kGram);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            kGram = kGram.shiftAdd(next);
        }

        return sb.toString().trim();
    }
/*
    public void testIndexOf() {
        setTraining("this is just a test yes this is a simple test");
        System.out.println(Arrays.asList(myText).toString());
        System.out.println(indexOf(myText, "this", 0));
        System.out.println(indexOf(myText, "this", 3));
        System.out.println(indexOf(myText, "frog", 0));
        System.out.println(indexOf(myText, "frog", 5));
        System.out.println(indexOf(myText, "simple", 2));
        System.out.println(indexOf(myText, "test", 5));
    }

    public static void main(String[] args) {
        MarkovWord mw = new MarkovWord();
        mw.testIndexOf();
    }

 */
}
