import java.util.*;

public class Solution {
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        List<List<Integer>> result = new ArrayList<>(groupSizes.length);
        Map<Integer, Integer> map = new HashMap<>(groupSizes.length);

        for (int i = 0; i < groupSizes.length; i++) {
            if (map.containsKey(groupSizes[i])) {
                int index = map.get(groupSizes[i]);
                List<Integer> group = result.get(index);
                group.add(i);
                if (group.size() == groupSizes[i]) {
                    map.remove(groupSizes[i]);
                }
            } else {
                List<Integer> group = new ArrayList<>(groupSizes[i]);
                group.add(i);
                result.add(group);
                if (groupSizes[i] > 1) {
                    map.put(groupSizes[i], result.size() - 1);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        new Solution().groupThePeople(new int[] {
            3, 3, 3, 3, 3, 1, 3
        });
        new Solution().groupThePeople(new int[] {
                2,1,3,3,3,2
        });

        System.out.println(Math.floor(7.0 / 2));
    }
}
