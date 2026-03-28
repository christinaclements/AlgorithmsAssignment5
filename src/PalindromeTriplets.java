import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PalindromeTriplets {
    public static List<int[]> palindromeTriplets(String[] words){
        List<int[]> result = new ArrayList<>();
        if (words == null || words.length < 3){
            return result;
        }
        //all words to lowercase
        String[] w = new String[words.length];
        for (int i = 0; i < words.length; i++){
            w[i] = words[i].toLowerCase();
        }
        //build hashmap: word -> index, for O(1) candidate lookup
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < w.length; i++){
            map.put(w[i], i);
        }
        //try every ordered pair (i, j) where i != j
        for (int i = 0; i < w.length; i++){
            for (int j = 0; j < w.length; j++){
                if (i == j) continue;
                //concatenate the pair to inspect all split points
                String combined = w[i] + w[j];

                //try every split point p of combined where
                //left = combined[0...p] and right = combined[p...]
                for (int p = 0; p <= combined.length(); p++){
                    String left = combined.substring(0, p);
                    String right = combined.substring(p);

                    //Case 1: left part is palindrome
                    //Then reverse(right) = words[k] placed at the front
                    //so reverse(right)+ left_palindrome + right = palindrome
                    //words[k] + combined is a palindrome
                    if (isPalindrome(left)){
                        String candidate = reverse(right);
                        if (map.containsKey(candidate)){
                            int k = map.get(candidate);
                            if (k != i && k != j){
                                //words[k] at the front
                                result.add(new int[]{k, i, j});
                            }
                        }
                    }
                    //Case 2: right part is palindrome
                    //then reverse(left) =words[k] is placed at the end
                    //     left + right_palindrome + reverse(left)
                    //   = combined + reverse(left) is a palindrome
                    if (right.length() > 0 && isPalindrome(right)){
                        String candidate = reverse(left);
                        if (map.containsKey(candidate)){
                            int k = map.get(candidate);
                            if (k != i && k != j){
                                //words[k] is at the end
                                result.add(new int[]{i, j, k});
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    //checks if a string is a palindrome
    public static boolean isPalindrome(String s){
        int left = 0;
        int right = s.length() - 1;
        while (left < right){
            if (s.charAt(left) != s.charAt(right)){
                return false;}
            left++;
            right--;
        }
        return true;
    }
    //returns the reverse of a string
    public static String reverse(String s){
        return new StringBuilder(s).reverse().toString();
    }

}
