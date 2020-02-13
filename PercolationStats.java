/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] OpenSite;
    private final double T;
    private double mean;
    private double stddev;
    private boolean count_m = false;
    private boolean count_s = false;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        check(n, trials);
        int ran_i;
        int ran_j;
        OpenSite = new double[trials];
        T = trials;
        for (int i = 0; i < trials; ++i) {
            Percolation perc = new Percolation(n);
            while (true) {
                if (perc.percolates()) {
                    //System.out.println("open site:" + perc.numberOfOpenSites());
                    OpenSite[i] = perc.numberOfOpenSites() / (double) (n * n);
                    break;
                }
                ran_i = StdRandom.uniform(n) + 1;
                ran_j = StdRandom.uniform(n) + 1;
                perc.open(ran_i, ran_j);
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(OpenSite);
        count_m = true;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(OpenSite);
        count_s = true;
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        if (!count_m || !count_s) {
            return mean() - (1.96 * stddev() / Math.sqrt(T));
        }
        else {
            return mean - (1.96 * stddev / Math.sqrt(T));
        }
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (!count_m || !count_s) {
            return mean() + (1.96 * stddev() / Math.sqrt(T));
        }
        else {
            return mean + (1.96 * stddev / Math.sqrt(T));
        }
    }

    private void check(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException("Invalid Argument!");
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println(
                "95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
