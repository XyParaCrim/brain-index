import java.util.*;

public class Solution {
    public int smallestDivisor(int[] nums, int threshold) {
        long sum = 0;
        for (int value : nums) {
            sum += value;
        }

        long min = (long) Math.ceil(sum * 1.0 / threshold);
        long max = (long) Math.floor(sum * 1.0 / (threshold - nums.length));
        long mid = (min + max) / 2;

        while (max > mid) {
            sum = 0;
            for (int num : nums) {
                sum += (long) Math.ceil(num * 1.0 / mid);
            }

            if (sum <= threshold) {
                max = mid;
            } else {
                min = mid + 1;
            }

            mid = (max + min) / 2;
        }

        return (int) mid;
    }

    public int compress(char[] chars) {
        Map<Integer, Integer> count = new HashMap<>();
        for (char aChar : chars) {
            if (count.containsKey((int) aChar)) {
                count.put((int) aChar, count.get((int) aChar) + 1);
            } else {
                count.put((int) aChar, 0);
            }
        }

        int length = 0;
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {

            chars[length++] = (char) ((int) entry.getKey());

            String v = entry.getValue().toString();
            for (int i = 0; i < v.length(); i++) {
                chars[length++] = v.charAt(i);
            }
        }

        return length;
    }



    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode partition(ListNode head, int x) {
        // 1->4->3->2->5->2
        ListNode curren = head, tail = null,  tailHead = null, headTail = null;
        head = null;
        while (curren != null) {
            if (curren.val < x) {
                if (headTail == null) {
                    headTail = head = curren;
                } else {
                    headTail.next = curren;
                    headTail = curren;
                }
            } else {
                if (tailHead == null) {
                    tailHead = tail = curren;
                } else {
                    tail.next = curren;
                    tail = curren;
                }
            }
            curren = curren.next;
        }

        if (headTail != null) {
            headTail.next = tailHead;
        } else {
            head = tailHead;
        }

        if (tail != null) {
            tail.next = null;
        }

        return head;
    }

    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        int max = Integer.MIN_VALUE;
        for (int i = 0, value; i < A.length - L + 1; i++) {

            value = 0;
            for (int j = 0; j < L; j++) {
                value += A[i + j];
            }

            for (int j = 0; j < i - M + 1; j++) {
                int mvalue = 0;
                for (int k = 0; k < M; k++) {
                    mvalue += A[k + j];
                }

                if (mvalue + value > max) {
                    max = mvalue + value;
                }
            }

            for (int j = 0; i + L + M + j - 1 < A.length; j++) {
                int mvalue = 0;

                for (int k = 0; k < M; k++) {
                    mvalue += A[i + L + k + j];
                }

                if (mvalue + value > max) {
                    max = mvalue + value;
                }
            }

        }

        return max;
    }

    public int findNthDigit(int n) {
        int limit = 10, k = 1, count = 9;
        while (n >= limit) {
            limit += ++k * (count = count * 10);
        }

        int left = limit - n;
        int a = left / k;

        int value = 1;
        int k1 = k;
        int offset = left - k*a;
        while (k-- > 0) {
            value *= 10;
        }

        a = value - a;
        if (offset > 0) {
            a--;
        }

        offset = k1 - offset;
        while (--offset > 0) {
            a = a/10;
        }

        return a%10;
    }


    public static void main(String[] args) {
//        System.out.println(new Solution().smallestDivisor(new int[]{1, 2, 5, 9}, 6));
//        System.out.println(new Solution().smallestDivisor(new int[]{2, 3, 5, 7, 11}, 11));
//        System.out.println(new Solution().smallestDivisor(new int[]{19}, 5));
//        System.out.println(new Solution().smallestDivisor(new int[]{962551, 933661, 905225, 923035, 990560}, 10));

        // System.out.println(new Solution().maxSumTwoNoOverlap(new int[] {0,6,5,2,2,5,1,9,4} , 1, 2));
        // System.out.println(new Solution().maxSumTwoNoOverlap(new int[] {1, 0, 1} , 1, 1));
        // System.out.println(new Solution().maxSumTwoNoOverlap(new int[] {12,8,12,18,19,10,17,20,6,8,13,1,19,11,5} , 3, 6));

        //System.out.println(new Solution().findNthDigit(1001));

       //  System.out.println(new Solution().findNthDigit(11));

/*        System.out.println(new Solution().findNthDigit(11));
        System.out.println(new Solution().findNthDigit(10));
         System.out.println(new Solution().findNthDigit(0));*/
  /*      System.out.println(new Solution().findNthDigit(1));
        System.out.println(new Solution().findNthDigit(190));
        System.out.println(new Solution().findNthDigit(10));
        System.out.println(new Solution().findNthDigit(0));
        System.out.println(new Solution().findNthDigit(1));*/
  // System.out.println("123".substring(1));

        new Solution().findAnagrams("baa", "aa");
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        return wordBreak(s, new HashSet<>(wordDict));
    }


    public static List<String> wordBreak(String s, Set<String> dict) {
        List<String> list = new LinkedList<>();
        int i = 1, start = 0;
        while (i <= s.length()) {
            String word = s.substring(start, i);
            if (dict.contains(word)) {
                if (i == s.length()) {
                    return Collections.singletonList(word);
                } else {
                    List<String> sub = wordBreak(s.substring(i), dict);
                    for (String ww : sub) {
                        list.add(word + " " + ww);
                    }
                    start = i + 1;
                }
            }
            i++;
        }
        return list;
    }

    public int calPoints(String[] ops) {
        int[] stack = new int[ops.length];
        int total = 0, index = - 1;

        for (int i = 0; i < ops.length; i++) {
            if (ops[i].equals("C")) {
                if (index != -1) {
                    total -= stack[index--];
                }
            } else if (ops[i].equals("D")) {
                if (index != -1) {
                    stack[index + 1] = stack[index] * 2;
                    total += stack[++index];
                }
            } else if (ops[i].equals("+")) {

                if (index != -1) {
                    stack[index + 1] = stack[index];
                    if (index - 1 != -1) {
                        stack[index + 1] += stack[index - 1];
                    }

                    total += stack[++index];
                }
            } else  {
                stack[++index] = Integer.valueOf(ops[i]);
                total += stack[index];
            }
        }

         return total;
    }

    public List<Integer> findAnagrams(String s, String p) {
        Map<Integer, Integer> index = new HashMap<>(p.length());
        for (int i = 0; i < p.length(); i++) {
            index.put((int) p.charAt(i), index.getOrDefault((int) p.charAt(i), 0) + 1);
        }
        List<Integer> result = new LinkedList<>();
        for (int i = p.length() - 1; i < s.length(); ) {
            if (index.containsKey((int) s.charAt(i))) {
                Map<Integer, Integer> temp = new HashMap<>(index);
                int size = p.length() - 1, j = i;
                temp.put((int) s.charAt(i), temp.get((int) s.charAt(i)) - 1);
                while(size > 0) {
                    if (temp.containsKey((int)s.charAt(--j))) {
                        int a = temp.get((int)s.charAt(j));
                        if (a > 0) {
                            size--;
                            temp.put((int)s.charAt(j), --a);
                            continue;
                        }
                    }
                    break;
                }

                if (size == 0) {
                    result.add(j);
                    i++;
                } else {
                    i += size;
                }

            } else {
                i += p.length();
            }
        }

        return result;
    }

}
