package org.excellent.cancer.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 实现清晰版本的正则表达式实现
 */
class ExplicitNFA implements RegularExpressionMatching {

    /**
     * 有限状态机的所有状态
     */
    private enum RegexState {
        /**
         * 正则表达式中的*状态
         */
        ASTERISK,

        /**
         * 正则表达式中后面紧接着*的状态
         */
        NEXT_ASTERISK,

        /**
         * 正则表达式中字母或者.的状态
         */
        LETTER_OR_DOT,

        /**
         * 接受状态，即所有的正则表达式已匹配完全
         */
        ACCEPTED
    }

    @Override
    public boolean isMatch(String match, String regex) {
        // 建立有限状态机nfa
        RegexState[] nfa = makeNFA(regex);
        int endState = nfa.length - 1;

        // matchable 用于记录已经到达的状态，至多只有nfa.length个状态
        // 已知从正则第一个字符开始匹配，所以将0置于matchable
        List<Integer> matchable;
        matchable = new ArrayList<>(nfa.length);
        matchable.add(0);

        // reachable 用于记录可到达的状态，即与matchable等同的状态
        List<Integer> reachable;
        reachable = reachable(nfa, matchable);

        char current, regexp;
        for (int i = 0; i < match.length(); i++) {
            current = match.charAt(i);

            // 记录下一轮到达的状态
            matchable.clear();

            for (int j : reachable) {
                // 忽略已经到达结束的状态，因为需要匹配的字符串还未匹配完全
                if (j != endState) {
                    // 获取已达状态的正则字符，与当前字符进行匹配
                    // 若匹配成功，说明已经到达下一个状态
                    regexp = regex.charAt(j);

                    if (regexp == current || regexp == '.') {
                        matchable.add(j + 1);
                    }

                }
            }

            // 筛选可到达的状态（matchable等同的状态）
            // 若为空，说明没有一个状态可到达
            if ((reachable = reachable(nfa, matchable)).isEmpty()) {
                return false;
            }
        }

        // 执行到这里说明，所有字符已经匹配完全
        //    若已到状态中，只要有一个状态是accepted，就说明匹配成功
        //    反之，说明还有正则未匹配完全
        return reachable.stream().anyMatch(i -> i == endState);
    }

    /**
     * 通过正则表达式，建立有限状态机
     *
     * @param regex 正则表达式
     * @return 有限状态机
     */
    private static RegexState[] makeNFA(String regex) {
        int regexLength = regex.length();
        RegexState[] nfa = new RegexState[regexLength + 1];

        // 包含一个虚拟的接受状态
        nfa[regexLength] = RegexState.ACCEPTED;

        for (int i = regexLength - 1; i >= 0; i--) {
            if (regex.charAt(i) == '*') {
                assert i > 0;
                nfa[i] = RegexState.ASTERISK;
                nfa[--i] = RegexState.NEXT_ASTERISK;
            } else {
                nfa[i] = RegexState.LETTER_OR_DOT;
            }
        }

        return nfa;
    }

    private static List<Integer> reachable(RegexState[] nfa, List<Integer> matchable) {

        boolean[] marked = new boolean[nfa.length];

        for (int i : matchable) {

            if (!marked[i]) {
                marked[i] = true;

                switch (nfa[i]) {
                    case NEXT_ASTERISK:
                        marked[++i] = true;
                    case ASTERISK:
                        marked[i - 1] = true;
                        while (!marked[++i] && nfa[i] == RegexState.NEXT_ASTERISK) {
                            marked[i] = true;
                            marked[++i] = true;
                        }
                        marked[i] = true;

                        break;
                    default:
                        break;
                }

                if (nfa[i] == RegexState.NEXT_ASTERISK) {
                    marked[i + 1] = true;
                }

            }
        }

        List<Integer> reachable = new LinkedList<>();
        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) {
                reachable.add(i);
            }
        }

        return reachable;
    }
}
