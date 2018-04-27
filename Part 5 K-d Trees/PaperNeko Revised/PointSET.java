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
import java.lang.IllegalArgumentException;


public class PointSET {
    // Construct an empty set of points...
    private TreeSet<Point2D> set2DPoints;
    public PointSET() {
        this.set2DPoints = new TreeSet<Point2D>();
    }
    
    // Is the set empty?
    public boolean isEmpty() {
        return this.set2DPoints.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return this.set2DPoints.size();
    }
    
    // Add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        this.set2DPoints.add(p);
    }
    
    // Does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        return this.set2DPoints.contains(p);
    }
    
    // Draw all points to standard draw...
    public void draw() {
        for (Point2D p : this.set2DPoints) {
            double px = p.x();
            double py = p.y();
            StdDraw.point(px, py);
        }
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
        
        List<Point2D> list = new ArrayList<Point2D>();
        for (Point2D p : this.set2DPoints) {
            double px = p.x();
            double py = p.y();
            if (xmin <= px && px <= xmax && ymin <= py && py <= ymax) {
                list.add(p);
            }
        }
        return new PointIterable(list);
    } 
    
    // A nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        
        Point2D nearestp = null;
        double distance = Double.POSITIVE_INFINITY;
        for (Point2D e : this.set2DPoints) {
            double current_dis = e.distanceSquaredTo(p);
            if (current_dis < distance) {
                distance = current_dis;
                nearestp = e;
            }
        }
        return nearestp;
    }
    
    // Unit testing of the methods (optional)...
    public static void main(String[] args) {
        
    }                  
}