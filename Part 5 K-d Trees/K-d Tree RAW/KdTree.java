import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ArrayDeque;
import java.lang.IllegalArgumentException;


public class KdTree {
    // Construct an empty set of points...
    private int numberOfPoints = 0;
    private class KdTreeNode {
        private boolean isVertical;
        private Point2D p;
        private KdTreeNode left = null;
        private KdTreeNode right = null;
        
        public KdTreeNode() {
            
        }
    }
    
    private KdTreeNode root = null;
    
    public KdTree() {
    }
    
    // Is the set empty?
    public boolean isEmpty() {
        return this.root == null;
    }
    
    // number of points in the set
    public int size() {
        return this.numberOfPoints;
    }
    
    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        
        if (this.contains(p)) {
            return;
        }
        
        this.numberOfPoints++;
        
        if (this.root == null) {
            this.root = new KdTreeNode();
            this.root.isVertical = true;
            this.root.p = p;
        } else {
            KdTreeNode parentNode = this.root;
            KdTreeNode curNode = this.root;
            int direction = 0;
            while (curNode != null) {
                Point2D cur = curNode.p;
                if (curNode.isVertical) {
                    parentNode = curNode;
                    if (p.x() <= cur.x()) {
                        // left
                        curNode = curNode.left;
                        direction = 0;
                    } else {
                        // right
                        curNode = curNode.right;
                        direction = 1;
                    }
                } else {
                    parentNode = curNode;
                    if (p.y() <= cur.y()) {
                        // left
                        curNode = curNode.left;
                        direction = 0;
                    } else {
                        // right
                        curNode = curNode.right;
                        direction = 1;
                    }
                }
            }
            // found correct position in kdtree
            if (direction == 0) {
                parentNode.left = new KdTreeNode();
                parentNode.left.p = p;
                parentNode.left.isVertical = !parentNode.isVertical;
            } else {
                parentNode.right = new KdTreeNode();
                parentNode.right.p = p;
                parentNode.right.isVertical = !parentNode.isVertical;
            }
        }
    }
    
    // Does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        KdTreeNode curNode = this.root;
        while (curNode != null) {
            Point2D cur = curNode.p;
            if (cur.x() == p.x() && cur.y() == p.y()) {
                return true;
            }
            if (curNode.isVertical) {
                if (p.x() <= cur.x()) {
                    // left
                    curNode = curNode.left;
                } else {
                    // right
                    curNode = curNode.right;
                }
            } else {
                if (p.y() <= cur.y()) {
                    // left
                    curNode = curNode.left;
                } else {
                    // right
                    curNode = curNode.right;
                }
            }
        }
        
        return false;
    }
    
    // Draw all points to standard draw...
    
    private void traverseKdTree(KdTreeNode node) {
        if (node != null) {
            this.traverseKdTree(node.left);
            StdDraw.point(node.p.x(), node.p.y());
            this.traverseKdTree(node.right);
        }
    }
    
    public void draw() {
        this.traverseKdTree(this.root);
    }
    
    // All points that are inside the rectangle (or on the boundary)
    
    private class PointIterable implements Iterable<Point2D> {
        private List<Point2D> list = null;
        public PointIterable(List<Point2D> t) {
            this.list = t;
        }

        @Override
        public Iterator<Point2D> iterator() {
            return this.list.iterator();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        
        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();
        
        TreeSet<Point2D> setOfPoints = new TreeSet<Point2D>();
        if (this.size() == 0) {
            return new PointIterable(new ArrayList<Point2D>()); 
        }
        Queue<KdTreeNode> searchFrontier = new ArrayDeque<KdTreeNode>();
        searchFrontier.add(this.root);
        while (!searchFrontier.isEmpty()) {
            KdTreeNode current = searchFrontier.poll();
            if (current.isVertical) {
                if (xmin <= current.p.x()) {
                    if (xmin <= current.p.x() && current.p.x() <= xmax &&
                        ymin <= current.p.y() && current.p.y() <= ymax) {
                        setOfPoints.add(current.p);
                    }
                    // search left
                    if (current.left != null) {
                        searchFrontier.add(current.left);
                    }
                }
                if (current.p.x() < xmax) {
                    if (xmin <= current.p.x() && current.p.x() <= xmax &&
                        ymin <= current.p.y() && current.p.y() <= ymax) {
                        setOfPoints.add(current.p);
                    }
                    // search right
                    if (current.right != null) {
                        searchFrontier.add(current.right);
                    }
                }
            } else {
                if (ymin <= current.p.y()) {
                    if (xmin <= current.p.x() && current.p.x() <= xmax &&
                        ymin <= current.p.y() && current.p.y() <= ymax) {
                        setOfPoints.add(current.p);
                    }
                    // search left
                    if (current.left != null) {
                        searchFrontier.add(current.left);
                    }
                }
                if (current.p.y() < ymax) {
                    if (xmin <= current.p.x() && current.p.x() <= xmax &&
                        ymin <= current.p.y() && current.p.y() <= ymax) {
                        setOfPoints.add(current.p);
                    }
                    // search right
                    if (current.right != null) {
                        searchFrontier.add(current.right);
                    }
                }
            }
        }

        return new PointIterable(new ArrayList<Point2D>(setOfPoints));
    } 
    
    // A nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        double tx = p.x();
        double ty = p.y();
        Point2D nearestp = null;
        double distance = Double.POSITIVE_INFINITY;
        if (this.size() == 0) {
            return nearestp;
        }
        Queue<KdTreeNode> searchFrontier = new ArrayDeque<KdTreeNode>();
        searchFrontier.add(this.root);
        while (!searchFrontier.isEmpty()) {
            KdTreeNode current = searchFrontier.poll();
            if (current.isVertical) {
                if (p.x() <= current.p.x()) {
                    double current_dis = p.distanceSquaredTo(current.p);
                    if (current_dis < distance) {
                        nearestp = current.p;
                        distance = current_dis;
                    }
                    // search left
                    if (current.left != null) {
                        searchFrontier.add(current.left);
                    }
                } else {
                    double current_dis = p.distanceSquaredTo(current.p);
                    if (current_dis < distance) {
                        nearestp = current.p;
                        distance = current_dis;
                    }
                    // search right
                    if (current.right != null) {
                        searchFrontier.add(current.right);
                    }
                }
            } else {
                if (p.y() <= current.p.y()) {
                    double current_dis = p.distanceSquaredTo(current.p);
                    if (current_dis < distance) {
                        nearestp = current.p;
                        distance = current_dis;
                    }
                    // search left
                    if (current.left != null) {
                        searchFrontier.add(current.left);
                    }
                } else {
                    double current_dis = p.distanceSquaredTo(current.p);
                    if (current_dis < distance) {
                        nearestp = current.p;
                        distance = current_dis;
                    }
                    // search right
                    if (current.right != null) {
                        searchFrontier.add(current.right);
                    }
                }
            }
        }
        
        return nearestp;
    }
    
    // Unit testing of the methods (optional)...
    public static void main(String[] args) {
        KdTree t = new KdTree();
        t.insert(new Point2D(1.0, 0.75));
        t.insert(new Point2D(0.75, 1.0));
        t.insert(new Point2D(0.75, 0.25));
        t.insert(new Point2D(0.5, 0.25));
        t.insert(new Point2D(0.25, 0.5));
        
        t.insert(new Point2D(0.25, 0.75));
        t.insert(new Point2D(0.0, 0.75));
        t.insert(new Point2D(0.0, 1.0));
        t.insert(new Point2D(0.5, 0.0));
        t.insert(new Point2D(1.0, 1.0));
        Iterable<Point2D> ps = t.range(new RectHV(0.25, 0.0, 1.0, 0.25));
        for (Iterator<Point2D> iter = ps.iterator(); iter.hasNext();) {
            Point2D p2d = iter.next();
            System.out.println(p2d);
        }
    }                  
}