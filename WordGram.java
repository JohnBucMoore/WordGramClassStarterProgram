import java.util.Arrays;

public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for (int i = 0; i < myWords.length; i++) {
            ret += myWords[i] + " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if (this.length() != other.length()) {
            return false;
        }
        for (int i = 0; i < myWords.length; i++) {
            if (! myWords[i].equals(other.wordAt(i))) {
                return false;
            }
        }
        return true;
    }

    public WordGram shiftAdd(String word) {
        String[] shifted = new String[myWords.length];
        for (int i = 0; i < myWords.length - 1; i++) {
            shifted[i] = myWords[i+1];
        }
        shifted[myWords.length - 1] = word;
        //System.out.println(Arrays.asList(myWords).toString());
        //System.out.println(Arrays.asList(shifted).toString());
        WordGram out = new WordGram(shifted, 0, shifted.length);
        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        return out;
    }

    public int hashCode() {
        return this.toString().hashCode();
    }
}