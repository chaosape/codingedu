import java.util.Random;
import java.util.Arrays;

public class Select {

    Random rand = null;

    public Select() {
        rand = new Random();
    }

    private void validKth(int[] ns, int kth) {
        if (kth < 1 || kth > ns.length) {
            throw new IllegalArgumentException("Bad kth: 0 < kth <= ns.length.");
        }
    }

    public int sortSelect(int[] ns, int kth) {
        validKth(ns, kth);
        Arrays.sort(ns);
        return ns[kth - 1];
    }

    private void swap(int[] ns, int i, int j) {
        int tmp = ns[i];
        ns[i] = ns[j];
        ns[j] = tmp;
    }

    private void shuffle(int[] ns) {
        Random rand = new Random();
        for (int i = 0; i < ns.length - 1; i++) {
            swap(ns, i, i + rand.nextInt(ns.length - i));
        }
    }

    public boolean checkPartition(int[] ns, int pidx, int bidx, int eidx) {
        int p = ns[pidx];
        for (int i = bidx; i <= eidx; i++) {
            //System.out.println("i = "+i+", pidx = "+pidx+", p = "+p+", ns[i] = "+ns[i]);
            if (i < pidx && p < ns[i]) {
                return false;
            } else if (i > pidx && ns[i] <= p) {
                return false;
            }
        }
        return true;
    }

    public int partition(int[] ns, int bidx, int eidx) {
        if (eidx < bidx) {
            throw new IllegalArgumentException("eidx < bidx");
        }
        int pidx = bidx + rand.nextInt(eidx - bidx + 1);
        swap(ns,pidx,bidx);
        pidx = bidx;
        int i = bidx + 1;
        int j = eidx;
        //System.out.println(pidx + " " + p);
        while (i <= j) {
            if(ns[pidx] > ns[i]) {
                swap(ns,pidx,i);
                pidx = i;
                i++;
            } else if (ns[pidx] == ns[i]) {
                pidx = i;
                i++;
            } else {
                swap(ns,i,j);
                j--;
            }
        }
        return pidx;
    }

    public static void main(String[] args) {

         int[] ns = {1, 2, 3, 4 ,5, 5, 5, 6 ,7, 8, 9};
        //int[] ns = { 1, 2, 3};
        System.out.println("Fuzz partition method.");
        Select select = new Select();
        for (int i = 0; i < 10000; i++) {
            select.shuffle(ns);
            int[] nsWorking = Arrays.copyOf(ns, ns.length);
            try {
                int pidx = select.partition(nsWorking, 0, ns.length - 1);
                if (!select.checkPartition(nsWorking, pidx, 0, ns.length - 1)) {
                    System.out.println("FAILURE: partition(" + Arrays.toString(ns) + ")[pidx = " + pidx + "] = "
                            + Arrays.toString(nsWorking));
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
                System.out.println("FAILURE: ns = " + Arrays.toString(ns));
            }
        }
    }

    /* XXX: Broken!!! */
    /*
     * private int quickSelect(int[] ns, int kth, int bidx, int eidx, Random rand) {
     * if(eidx < bidx) { throw new IndexOutOfBoundsException("BUG: eidx < bidex"); }
     * else if(bidx == eidx) { return ns[eidx]; } else if(bidx + 1 == eidx) { return
     * ns[kth-1]; } int pv = ns[eidx]; int i = bidx; int j = eidx;
     * System.out.println("bidx = "+bidx+", eidx = "+eidx); while(i <= j) {
     * while(ns[i] < pv) { i++; } while(pv < ns[j]) { j--; } if(i <= j) {
     * swap(ns,j,i); j--; i++; } }
     * System.out.println("j = "+j+", ns = "+Arrays.toString(ns)); if(0 < kth - j) {
     * return quickSelect(ns, kth - j, j++, eidx, rand); } else { return
     * quickSelect(ns, kth, bidx, j, rand); } }
     *
     * public int quickSelect(int[] ns, int kth) { validKth(ns, kth); return
     * quickSelect(ns, kth, 0, ns.length -1, new Random()); }
     */
}