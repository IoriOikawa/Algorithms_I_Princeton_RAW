//Percolation Algorithms as Assignment of Week 1.1 by IoriOikawa 2017

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//Import public class for weighted quick union algorithms: 
public class Percolation {
 	private WeightedQuickUnionUF firstUnionFind; //Major evaluation process definition.
 	private WeightedQuickUnionUF secondUnionFind; //Evade of back-wash, by setting up a new direction of percolation.
 	private int row = 0; //Formulated variation 'row'.
 	private Boolean[][] site; //Boolean status for each element in the grid.

//Draw a square grid mounted by n, with basic regulations:
	public Percolation(int n) 
	{
		if (n <= 0){
			throw new IllegalArgumentException(); //Evade exceptions.
		}
		firstUnionFind = new WeightedQuickUnionUF((n * n) + 2); 
		//Set relationship array describing perculate status, with BOTH upper virtual leapgate and lower virtual leapgate.
		secondUnionFind = new WeightedQuickUnionUF((n * n) + 1);
		//Set relationship array for evading backwash status, with only ONE lower virtual leapgate.
		row = n; //Definite overthrough direction by row, aria limited by n rows.
		site = new Boolean[n][n]; //Set grid aria of evaluation as site.
	}

//Open the site coordinated as (i,j) if it is not open:
	public void open(int i, int j)
	{
		if ((i < 1) || (i > row) || (j < 1) || (j > row)){
			throw new IndexOutOfBoundsException(); //Coordinat exception respond set.
		}
		
		site[i - 1][j - 1] = true; //Regulating the evaluation start point in (0,0).
		int self = (i - 1) * row + j - 1; //Build the iterable position to see through the grid.
		int up = self - row; //Identify the element in the over the position 'self'.
		int down = self + row; //Identify the element in the beneth position 'self'.
		int left = self - 1; //Identify the element on the left side of 'self'.
		int right = self + 1; //Identify the element on the right side of 'self'.

//Move the starting point to (1,2):
		if (i == 1){
			firstUnionFind.union(row * row, self);
		}
		if (i == row){
			firstUnionFind.union(row * row + 1, self);
		}

//Begin evaluation of percolation process...
		if ((i != 1) && isOpen(i - 1, j)){
			firstUnionFind.union(up, self);
			secondUnionFind.union(up, self);
		}
		if ((i != row) && isOpen(i + 1, j)){
			firstUnionFind.union(down, self);
			secondUnionFind.union(down, self);
		}
		if ((j != 1) && isOpen(i, j - 1)){
			firstUnionFind.union(left, self);
			secondUnionFind.union(left, self);
		}
		if ((j != row) && isOpen(i, j + 1)){
			firstUnionFind.union(right, self);
			secondUnionFind.union(right, self);
		}
	}

//Evaluate wether the site(i,j) is open:
	public boolean isOpen(int i, int j)
	{
		if ((i < 1) || (i > row) || (j < 1) || (j > row) ){
			throw new IndexOutOfBoundsException(); //Coordinat exception respond set.
		}
		return site[i - 1][j - 1];
	}

//Evaluate wether the site(i,j) is full:
	public boolean isFull(int i, int j)
	{
		if ((i < 1) || (i > row) || (j < 1) || (j > row)){
			throw new IndexOutOfBoundsException(); //Coordinat exception respond set.
		}
		int self = (i - 1) * row + j - 1;
		return secondUnionFind.connected(row * row, self);
		//int self = (i - 1) * row + j - 1;
		//return secondUnionFind.connected(row*row, self);
		 //Backwash status respond set.
	}

//Evaluate wether the grid percolates:
	public boolean percoates()
	{
		return firstUnionFind.connected(row * row + 1, row * row); //Major percolate process.
	}

//Set excutation point for test client:
	public static void main(String[] args) {		
	}
}