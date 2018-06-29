import java.lang.reflect.Array;
import java.util.ArrayList;

public class KMPAlgorithm extends PatternFinder{
    ArrayList<Integer> prefix;


    public ArrayList<Integer> Search(){
        prefix();
        ArrayList<Integer> res = new ArrayList<Integer>();
        int k = 0;
        for (int i = 0; i < text.length(); ++i) {
            while (pattern.charAt(k) != text.charAt(i) && k > 0) {
                k = prefix.get(k - 1);
            }
            if (pattern.charAt(k) == text.charAt(i)) {
                k = k + 1;
                if (k == pattern.length()) {
                    res.add(i + 1 - k);
                    k = prefix.get(k - 1);
                }
            }
            else {
                k = 0;
            }
        }
        return res;
    }

    void prefix(){
        prefix.add(new Integer(0));
        for(int i = 1 ; i < text.length() ; ++i){
            Integer k = prefix.get(i-1);
            while(k > 0 && text.charAt(i) != text.charAt(k){
                k = prefix.get(k - 1);
            }
            if(text.charAt(i) == text.charAt(k)) ++k;
            prefix.add(k);
        }
    }
}
