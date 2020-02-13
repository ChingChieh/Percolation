/* *****************************************************************************
 *  Name:              Ching-Chieh Huang
 *  Coursera User ID:  123456
 *  Last modified:     2/8/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf_full;
    private boolean[] status;
    private final int size; // store the n * n value
    private final int num;  // store the n value
    private int num_OpenSite = 0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n * n + 2;
        num = n;
        check(n);
        uf = new WeightedQuickUnionUF(size);
        uf_full = new WeightedQuickUnionUF(size);
        status = new boolean[size];
        for (int i = 0; i < size; ++i) {
            status[i] = false;
        }
        // for (int i = 1; i <= n; ++i) {
        //     uf.union(0, i);
        //     uf.union(size - 1, size - 1 - i);
        // }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row);
        check(col);
        int index = xyTo1D(row, col);
        if (index <= num) {
            status[0] = true;
            uf.union(0, index);
            uf_full.union(0, index);
        }
        if (index >= size - num - 1) {
            status[size - 1] = true;
            uf.union(size - 1, index);
        }
        if (!status[index]) {
            status[index] = true;
            num_OpenSite++;
            if (row + 1 <= num && status[index + num]) {
                uf.union(index, index + num);
                uf_full.union(index, index + num);
            }
            if (row - 1 > 0 && status[index - num]) {
                uf.union(index, index - num);
                uf_full.union(index, index - num);
            }
            if (col + 1 <= num && status[index + 1]) {
                uf.union(index, index + 1);
                uf_full.union(index, index + 1);
            }
            if (col - 1 > 0 && status[index - 1]) {
                uf.union(index, index - 1);
                uf_full.union(index, index - 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row);
        check(col);
        return status[xyTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row);
        check(col);
        if (status[0]) {
            int index = xyTo1D(row, col);
            return uf_full.connected(index, 0);
        }
        else {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return num_OpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        // boolean per = false;
        // for (int j = 1; j <= num; ++j) {
        //     if ((numberOfOpenSites() >= num) && isOpen(num, j) && isFull(num, j)) {
        //         per = true;
        //         break;
        //     }
        // }
        if (status[0] && status[size - 1]) {
            return uf.connected(0, size - 1);
        }
        else {
            return false;
        }
    }

    private void check(int i) {
        if (i <= 0 || i > num) throw new IllegalArgumentException("Invalid Argument!");
    }

    private int xyTo1D(int row, int col) {
        return ((row - 1) * num + col);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 8;
        Percolation p = new Percolation(n);
        System.out.println(p.xyTo1D(8, 8));
        p.open(1, 1);
        p.open(1, 2);
        p.open(2, 2);
        System.out.println(p.isFull(1, 1));
        System.out.println(p.isFull(1, 2));
        System.out.println(p.isFull(1, 3));
        System.out.println(p.isFull(2, 2));
    }
}
