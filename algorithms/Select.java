import java.util.Random;
import java.util.Arrays;

public class Select {

    private void validKth(int[] ns, int kth) {
        if(kth < 1 || kth > ns.length) {
            throw new
                IllegalArgumentException("Bad kth: 0 < kth <= ns.length.");
        }
    }

    public int sortSelect(int[] ns, int kth) {
        validKth(ns, kth);
        Arrays.sort(ns);
        return ns[kth-1];
    }

    private void swap(int[] ns, int i, int j) {
        int tmp = ns[i];
        ns[i] = ns[j];
        ns[j] = tmp;
    }

    private int quickSelect(int[] ns, int kth, int bidx, int eidx, Random rand) {
        if(eidx < bidx) {
            throw new
                IndexOutOfBoundsException("BUG: eidx < bidex");
        }
        else if(bidx == eidx) {
            return ns[eidx];
        }
        else if(bidx + 1 == eidx) {
            return Math.max(ns[bidx], ns[eidx]);
        }
        int idx = bidx; /*+ rand.nextInt(eidx-bidx+1);*/
        swap(ns, bidx, idx);
        idx = bidx;
        int pv = ns[idx];
        int i = bidx;
        int j = eidx;
        System.out.println("bidx = "+bidx+", eidx = "+eidx);
        while(i <= j) {
            while(ns[i] < pv) { i++; }
            while(pv < ns[j]) { j--; }
            if(i <= j) {
                swap(ns,j,i);
                j--;
                i++;
            }
        }
        idx = i;
        System.out.println("idx = "+idx+", ns = "+Arrays.toString(ns));
        if(0 < kth - idx + 1) { return quickSelect(ns, kth - idx + 1, idx++, eidx, rand); }
        else { return quickSelect(ns, kth, bidx, idx, rand); }
    }

    public int quickSelect(int[] ns, int kth) {
        validKth(ns, kth);
        return quickSelect(ns, kth, 0, ns.length -1, new Random());
    }

    private void shuffle(int[] ns) {
        Random rand = new Random();
        for(int i = 0; i < ns.length - 1; i++) {
            swap(ns, i, i + rand.nextInt(ns.length - i));
        }
    }

    public static void main(String[] args) {
        //int[] ns = {1, 2, 3, 4 ,5, 5, 5, 6 ,7, 8, 9};
        int[] ns = {2, 3, 1};
        Select select = new Select();
        //select.shuffle(ns);
        System.out.println(Arrays.toString(ns));
        System.out.println(select.quickSelect(Arrays.copyOf(ns,ns.length), 3));
        System.out.println(select.sortSelect(Arrays.copyOf(ns,ns.length), 3));

    }
}