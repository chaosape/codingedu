import java.util.Arrays;

class WiggleSortII {

    public void swap(int[] ns, int i, int j) {
        int tmp = ns[i];
        ns[i] = ns[j];
        ns[j] = tmp;
    }

    public boolean evenP(int i) { return (i % 2 == 0); }

    public boolean check(int[] ns) {
        for (int i = 0; i < ns.length - 1; i++) {
            if (evenP(i) && ns[i + 1] <= ns[i]) {
                return false;
            }
            if (!evenP(i) && ns[i] <= ns[i + 1]) {
                return false;
            }
        }
        return true;
    }

    //XXX: This algorithm is broken.
    public void wiggleSort(int[] nums) {
        if (nums.length <= 1) {
            return;
        }
        Arrays.sort(nums);

        int min = Integer.MAX_VALUE;
        int minidx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
                minidx = i;
            }
        }
        swap(nums, 0, minidx);
        for (int i = 0; i < nums.length - 1; i++) {
            if ((i % 2 == 0 && nums[i] < nums[i + 1]) || (i % 2 == 1 && nums[i + 1] < nums[i])) {
                continue;
            }
            for (int j = i + 2; j < nums.length; j++) {
                if ((i % 2 == 0 && nums[i] < nums[j]) || (i % 2 == 1 && nums[j] < nums[i])) {
                    swap(nums, i + 1, j);
                    break;
                }
            }
        }
        if ((nums.length % 2 == 0 && nums[nums.length - 1] < nums[nums.length - 2])
                || (nums.length % 2 == 1 && nums[nums.length - 2] < nums[nums.length - 1])) {
            swap(nums, nums.length - 2, nums.length - 1);
        }
    }

    public static void main(String[] args) {
        int[][] qs =
        {{1, 1, 1, 2, 2}
        ,{1, 5, 1, 1, 6, 4}
        ,{1, 3, 2, 2, 3, 1}
        ,{6, 5, 4, 5}};
        boolean[] as = {true, true, true, true};

        WiggleSortII wsii = new WiggleSortII();
        for(int i = 0; i < qs.length; i++) {
            wsii.wiggleSort(qs[i]);
            if( wsii.check(qs[i])!= as[i]) {
                System.out.println("Test "+i+" failed: "+Arrays.toString(qs[i]));
            }
        }
    }

}