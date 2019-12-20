package nfa;

public class Solution {
    public boolean isMatch(String s, String p) {
        // 建立状态图
        int[] graph = makeGraph(p);
        int endState = graph.length;

        // 每轮遍历匹配的状态，matchLength记录状态个数
        int[] matchable = new int[endState + 1];
        int matchLength = 1;
        matchable[0] = 0;

        // 下一轮遍历可以匹配的状态，reachLength记录状态个数
        int[] reachable = new int[endState + 1];
        int reachLength = reachable(graph, reachable, matchable, matchLength);

        char current, regexp;
        for (int i = 0; i < s.length(); i++) {
            matchLength = 0;
            current = s.charAt(i);

            // 判断current是否可以匹配状态，若匹配则记录下一个状态至matchable
            for (int j = reachLength - 1; j > -1; j--) {
                if (reachable[j] == endState) continue;
                regexp = p.charAt(reachable[j]);
                if (regexp == current || regexp == '.') {
                    matchable[matchLength++] = reachable[j] + 1;
                }
            }

            reachLength = reachable(graph, reachable, matchable, matchLength);

            // 若下一轮无状态匹配，宣告失败
            if (reachLength == 0) {
                return false;
            }
        }

        // 是否有任意一个状态到达endState
        for (int i = 0; i < reachLength; i++) {
            if (reachable[i] == endState) {
                return true;
            }
        }

        return false;
    }

    /**
     * 通过正则表达式，建立状态图。状态值为0，1，2。
     * 0表示该位置字符只会让状态向后移动
     * 1表示该位置字符在*前面，因此它的状态可能会往后一直移动
     * 2表示该位置字符为*，它的状态可能往前也可能往后
     * @param regular 正则表达式
     * @return 返回状态图
     */
    public static int[] makeGraph(String regular) {
        int[] graph = new int[regular.length()];
        for (int i = 0, endState = regular.length(); i <= endState; i++) {
            if (i < endState && regular.charAt(i + 1) == '*') {
                graph[i] = 1;
                graph[i] = 2;
            } else {
                graph[i] = 0;
            }
        }

        return graph;
    }

    /**
     * 寻找下一轮匹配状态。
     * @param graph 状态图
     * @param reachable 上一轮可匹配的状态，作为参数仅仅是为使用同一数组对象
     * @param matchable 此轮匹配的状态数组
     * @param matchLength 此轮匹配的状态数组长度，即遍历matchable的[0, matchLength - 1]元素
     * @return 可匹配状态数组的长度
     */
    public static int reachable(int[] graph, int[] reachable, int[] matchable, int matchLength) {
        int length = 0;
        // 对应正则表达式每个字符
        boolean[] marked = new boolean[graph.length];
        int vert;
        for (int i = 0; i < matchLength; i++) {
            vert = matchable[i];
            if (!marked[vert]) {
                marked[vert] = true;
                // 若是*或者可能重复的字符
                if (graph[vert] > 0) {
                    // 若是可能重复的字符 - 下一个字符可以作为可匹配的状态
                    // 若是* - 说明也可能返回上一个重复字符状态
                    marked[graph[vert] == 1 ? ++vert : vert - 1] = true;
                    // 因为*也可以不重复字符，所有若下一个也是某字符加*的组合
                    // 那么依然是可以匹配的状态
                    while (!marked[++vert] && graph[vert] == 1) {
                        marked[vert] = true;
                        marked[++vert] = true;
                    }
                    marked[vert] = true;
                }
            }
        }

        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) {
                reachable[length++] = i;
            }
        }

        return length;
    }
}
