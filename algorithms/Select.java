import java.util.Random;
import java.util.Arrays;

public class Select {

    Random rand = null;

    public Select() {
        rand = new Random();
    }

    private void validKth(int[] ns, int kth) {
        if (kth < 1 || kth > ns.length) {
            String excStr = "Bad kth: 0 < kth <= ns.length.";
            throw new IllegalArgumentException(excStr);
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

    private int quickSelect(int[] ns, int kth, int bidx, int eidx) {
        if(eidx < bidx) {
            throw new IndexOutOfBoundsException("BUG: eidx < bidx");
        }
        else if(bidx == eidx) {
            if(kth != 1) {
                String excStr = "bidx == eidx && kth != 1 (kth = "+kth+")";
                throw new IllegalArgumentException(excStr);
            }
            return ns[eidx];
        }
        else if(bidx + 1 == eidx) {
            if(kth != 2) {
                String excStr = "bidx + 1 == eidx && kth != 2 (kth = "+kth+")";
                throw new IllegalArgumentException(excStr);
            }
            return ns[kth-1];
        }
        int pidx = partition(ns, bidx, eidx);
        int leftOver = kth - (pidx - bidx + 1);
        if(0 < leftOver ) {
            return quickSelect(ns, leftOver, pidx+1, eidx);
        }
        else if (leftOver == 0) {
            return ns[pidx];
        }
        else {
            return quickSelect(ns, kth, bidx, pidx);
        }
    }

    public int quickSelect(int[] ns, int kth) {
        validKth(ns, kth);
        return quickSelect(ns, kth, 0, ns.length - 1);
    }

    public static void main(String[] args) {
        int[] ns;
        Select select = new Select();
        Random rand = new Random();
        for (int j = 0; j < 100; j++) {
            ns = new int[rand.nextInt(1000)];
            for(int i = 0; i < ns.length; i++) { ns[i] = rand.nextInt(); }
            for (int i = 0; i < 100; i++) {
                select.shuffle(ns);
                int[] nsWorking = Arrays.copyOf(ns, ns.length);
                try {
                    int pidx = select.partition(nsWorking, 0, ns.length - 1);
                    if (!select.checkPartition(nsWorking, pidx, 0, ns.length - 1)) {
                        System.out.println("FAILURE: partition("
                                           + Arrays.toString(ns) + ")"
                                           +"[pidx = " + pidx + "] = "
                                           + Arrays.toString(nsWorking)+".");
                    }
                    int ssAns = select.sortSelect(Arrays.copyOf(ns, ns.length)
                                                  ,ns.length);
                    int qsAns = select.quickSelect(Arrays.copyOf(ns, ns.length)
                                                   ,ns.length);
                    if(ssAns != qsAns) {
                        String failStr;
                        failStr = "FAILURE:"
                            +" quickSelect("+Arrays.toString(ns)+") = "+qsAns
                            +" but expected "+ssAns+".";
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    System.out.println("FAILURE: ns = " + Arrays.toString(ns));
                }
            }
        }
    }
}
