package org.excellent.cancer.algorithms;

/**
 * 遍历数组，计算当前遍历数的两侧的大小情况，然后获得答案
 */
class Solution implements CountNumberOfTeams {

    @Override
    public int numTeams(int[] rating) {
        int teams = 0;

        for (int i = 1; i < rating.length - 1; i++) {
            int leftLess = 0;
            int leftMore = 0;
            int rightLess = 0;
            int rightMore = 0;

            for (int j = i - 1; j >= 0; j--) {
                if (rating[j] > rating[i]) {
                    leftMore++;
                } else if (rating[j] < rating[i]) {
                    leftLess++;
                }
            }

            for (int j = i + 1; j < rating.length; j++) {
                if (rating[j] > rating[i]) {
                    rightMore++;
                } else if (rating[j] < rating[i]) {
                    rightLess++;
                }
            }

            teams += leftLess * rightMore + leftMore * rightLess;

        }


        return teams;
    }

}
