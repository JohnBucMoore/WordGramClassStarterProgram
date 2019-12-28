import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> myMap;

    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        myMap = new HashMap<>();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
        printHashMapInfo();
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

    private ArrayList<String> getKeyValues(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int currIndex = 0;
        while (currIndex != -1) {
            int keyIndex = indexOf(myText, kGram, currIndex);
            System.out.println(keyIndex+"\t"+kGram+"\t"+currIndex+"\t"+indexOf(myText,kGram,(keyIndex+1));
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

    public void buildMap() {
        String[] key = new String[myOrder];
        for (int i = 0; i < key.length; i++) {
            key[i] = myText[i];
        }
        WordGram kGram = new WordGram(key, 0, myOrder);
        for (int i = 0; i < myText.length - myOrder; i++) {
            ArrayList<String> follows;
            if (! myMap.containsKey(kGram)) {
                follows = getKeyValues(kGram);
                myMap.put(kGram, follows);
            }
            String next = myText[i+myOrder];
            kGram = kGram.shiftAdd(next);
        }
    }

    public ArrayList<String> getFollows(WordGram kGram) {
        return myMap.get(kGram);
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

    public void printHashMapInfo() {
        int largestValue = 0;
        ArrayList<WordGram> largest = new ArrayList<>();
        for (WordGram w : myMap.keySet()) {
            if (myMap.get(w).size() > largestValue) {
                largestValue = myMap.get(w).size();
                largest.clear();
                largest.add(w);
            } else if (myMap.get(w).size() == largestValue) {
                largest.add(w);
            }
            System.out.println(w+"\t"+myMap.get(w));
        }
        System.out.println("The text created a WordGram map of size "+myMap.keySet().size());
        System.out.println("WordGram's with the largest value "+largestValue+" in the text:");
        for (WordGram l : largest) {
            System.out.println(l.toString());
        }
    }
}
