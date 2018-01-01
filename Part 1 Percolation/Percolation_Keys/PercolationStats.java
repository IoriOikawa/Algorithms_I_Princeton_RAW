import edu.princeton.cs.algs4.StdOut;    
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;    

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;

    // T independent experiments
    private final int t;
    private final double[] fraction;
    private double meanResult;
    private double stddevResult;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException("N and T must be bigger than 0");
        }
        this.t = t;
        this.meanResult = 0;
        this.stddevResult = 0;
        fraction = new double[t];
        
        for (int count = 0; count < t; count++) {
            Percolation pr =  new Percolation(n);
            int openedSites = 0;
            while (!pr.percolates()) {
                int i = StdRandom.uniform(1, n+1);
                int j = StdRandom.uniform(1, n+1);
                if (!pr.isOpen(i, j)) {
                    pr.open(i, j);
                    openedSites++;
                }
            }
            fraction[count] = (double) openedSites / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (meanResult == 0) {
            meanResult = StdStats.mean(fraction);
        }
        return meanResult;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddevResult == 0) {
            if (t == 1) {
                return Double.NaN;
            }
            stddevResult = StdStats.stddev(fraction);
        }
        return stddevResult;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(t); 
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(t); 
    } 

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = "+ps.confidenceLo()+", "+ ps.confidenceHi());
    }
}