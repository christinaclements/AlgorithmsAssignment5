import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RadixSort {
    public static String[] radixSort(String[] s) {
        //null or empty array input
        if (s == null || s.length == 0){
            return s;
        }
        //find the length of longest string
        //that determines how many passes of countSort we need
       int maxLength = 0;
        for (String sl : s){
            maxLength = Math.max(maxLength, sl.length());
        }
        //perform countSort pass per character position
        //start from the last character to the first in LSD order
        for (int current = maxLength - 1; current >= 0; current--){
            s = countSort(s, current);
        }
        System.out.println(Arrays.toString(s));
        return s;
    }
    //countSort called on the array based on the
    //character at the current position in each string
    public static String[] countSort(String[] s, int current){
        //using 27 buckets where
        //bucket 0 = strings shorter than 'current'
        //(no character, sorts first)
        //buckets 1-26 = a-z
        HashMap<Integer, ArrayList<String>> buckets = new HashMap<>();
        //fill with empty arrays
        for (int i =0; i <=26; i++){
            buckets.put(i, new ArrayList<>());
        }
        //assign each string to its bucket based on 'current' character
        for (String st : s){
            int bucket;
            //if string is shorter than 'current', place in bucket 0
            if (current >= st.length()){
                bucket = 0;
            } else {
                //maps character to a bucket number 1 - 26
                //the + 1 is because we added an extra bucket
                bucket = st.charAt(current) - 'a' + 1;
            }
            //append string to back of its bucket list
            //strings with the same character at 'current'
            //stay in their original order
            buckets.get(bucket).add(st);
        }

        //rebuild sorted array, read buckets in order 0 -> 26
        String[] result = new String[s.length];
        int index = 0;
        for (int i = 0; i <= 26; i++){
            for (String st : buckets.get(i)){
                result[index++] = st;
            }
        }
        return result;
    }
}