package org.excellent.cancer.algorithms;

class Solution implements ScrambleString {

    @Override
    public boolean isScramble(String s1, String s2) {
        int[] bag1 = bag(s1, 0, s1.length() - 1);
        int[] bag2 = bag(s1, 0, s2.length() - 1);

        if (!isScrambleBag(bag1, bag2)) {
            return false;
        }

        if (s1.length() <= 3) {
            return true;
        }

        for (int i = 1; i < s1.length(); i++) {

            if ((isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) ||
                    (isScramble(s1.substring(0, i), s2.substring(s1.length() - i)) && isScramble(s1.substring(i), s2.substring(0, s1.length() - i)))) {
                return true;
            }
        }

        return false;
    }


    public boolean isScrambleBag(int[] bag1, int[] bag2) {
        for (int i = 0; i < bag1.length; i++) {
            if (bag1[i] == bag2[i]) {
                continue;
            }
            return false;
        }

        return true;
    }

    public int[] bag(String s, int from, int to) {
        int[] bag = new int[26];

        for (int i = from; i <= to; i++) {
            bag[s.charAt(i) - 'a']++;
        }

        return bag;
    }

    public static void main(String[] args) {
        new Solution().isScramble("great", "rgeat");
    }
}
