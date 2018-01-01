// Percolation Algorithms analysed by CocoaOikawa
// based on IoriOikawa's work

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Import public class for weighted quick union algorithms: 
public class Percolation {
    // Major evaluation process definition.
    private final WeightedQuickUnionUF firstUnionFind;

    // Evade of back-wash, by setting up a new direction of percolation.
    private final WeightedQuickUnionUF secondUnionFind;

    // Formulated variation 'row'.
    private final int row;

    // Boolean status for each element in the grid.
    private boolean[][] site;

    // Calculates the amount of current open sites
    private int numberOfOpenSites = 0;
  
    private int virtualTopSite;
    private int virtualBottomSite;
  
    // Draw a square grid mounted by n, with basic regulations:
    public Percolation(int n) 
	{
		if (n <= 0) {
            // Evade exceptions.
			throw new IllegalArgumentException(); 
		}
        // index of virtual top site
        virtualTopSite = n * n;
      
        // index of virtual bottom site
        virtualBottomSite = n * n + 1;
      
        // Set relationship array describing perculate status, with BOTH virtual top site and virtual bottom site.
		firstUnionFind = new WeightedQuickUnionUF((n * n) + 2); 
      
		// Set relationship array for evading backwash status, with only virtual top site.
		secondUnionFind = new WeightedQuickUnionUF((n * n) + 1);
		
        // Definite overthrough direction by row, aria limited by n rows.
		row = n;

        // Set grid aria of evaluation as site.
		this.site = new boolean[n][n];
	}
  
    // Helper method:
    private void coordinateValidator(int i, int j)
    {
        if ((i < 1) || (i > row) || (j < 1) || (j > row)) {
            // Coordinat exception respond set.
            throw new IllegalArgumentException();
        }
    }
    
    // Open the site coordinated as (i,j) if it is not open:
    public void open(int i, int j) {
        coordinateValidator(i, j);

        // if this site is already open
        // then we just return
        if (isOpen(i, j)) return;

        // Regulating the evaluation start point in (0,0).
        site[i - 1][j - 1] = true;

        // increase open site counter
        numberOfOpenSites += 1;

        // Build the iterable position to see through the grid.
        int self = (i - 1) * row + j - 1;
        int up = self - row;
        int down = self + row;
        int left = self - 1;
        int right = self + 1;

        // self at first row, top row
        if (i == 1) {
            firstUnionFind.union(virtualTopSite, self);
            secondUnionFind.union(virtualTopSite, self);
        }

        // self at the last row, bottom row
        if (i == row) {
    		firstUnionFind.union(virtualBottomSite, self);
    	}

        // up
        if ((i != 1) && isOpen(i - 1, j)) {
            firstUnionFind.union(up, self);
            secondUnionFind.union(up, self);
        }
        // down
        if ((i != row) && isOpen(i + 1, j)) {
            firstUnionFind.union(down, self);
            secondUnionFind.union(down, self);
        }
        // left
        if ((j != 1) && isOpen(i, j - 1)) {
            firstUnionFind.union(left, self);
            secondUnionFind.union(left, self);
        }
        // right
        if ((j != row) && isOpen(i, j + 1)) {
            firstUnionFind.union(right, self);
            secondUnionFind.union(right, self);
        }
    }
  
    // Evaluate wether the site(i,j) is open:
    public boolean isOpen(int i, int j)
	{
		coordinateValidator(i, j);
		return this.site[i - 1][j - 1];
	}
  
    // Evaluate wether the site(i,j) is full:
    public boolean isFull(int i, int j)
	{
		coordinateValidator(i, j);
      
		int self = (i - 1) * row + j - 1;
        // 这里就让 secondUnionFind 来做～
		return secondUnionFind.connected(virtualTopSite, self);
	}
  
    // Evaluate wether the grid percolates:
    public boolean percolates()
	{
        // Major percolate process.
        // 而这里让 firstUnionFind 来快速检查～
		return firstUnionFind.connected(virtualTopSite, virtualBottomSite);
	}
  
    // Get the number of open sites:
    public int numberOfOpenSites() 
	{
		return numberOfOpenSites;
	}
  
    // Set excutation point for test client:
	public static void main(String[] args) {
        // empty
	}
}
