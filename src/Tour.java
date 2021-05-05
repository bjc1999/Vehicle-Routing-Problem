import java.util.LinkedList;

public class Tour {
    private LinkedList<LinkedList<Integer>> solution;
    private LinkedList<Integer> capacity;
    private double cost;
    private LinkedList<Double> costs;

    public Tour(double cost) {
        this.solution = new LinkedList<LinkedList<Integer>>();
        this.solution.add(new LinkedList<Integer>());
        this.solution.getLast().add(0);
        this.capacity = new LinkedList<Integer>();
        this.capacity.add(0);
        this.cost = cost;
        this.costs = new LinkedList<Double>();
    }

    public void addStop(int stop) {
        solution.getLast().add(stop);
    }

    public void addNewVehicle() {
        solution.add(new LinkedList<Integer>());
        solution.getLast().add(0);
        capacity.add(0);
    }

    public LinkedList<LinkedList<Integer>> getSolution() {
        return this.solution;
    }

    public void setSolution(LinkedList<LinkedList<Integer>> solution) {
        this.solution = solution;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void appendCost(double cost) {
        double prevSum = 0;
        for (double aCost: costs)
            prevSum += aCost;
        this.costs.add(cost-prevSum);
    }

    public LinkedList<Integer> getCapacity() {
        return this.capacity;
    }

    public void addCapacity(int capacity) {
        this.capacity.set(this.capacity.size()-1, this.capacity.getLast() + capacity);
    }

    public int getCurrentVehicleCapacity() {
        return this.capacity.getLast();
    }

    public String toString() {
        String result = "\nTour\nTour Cost: " + this.cost + "\n";
        for (int i=0; i<solution.size(); i++) {
            result += "Vehicle " + (i+1) + "\n0 -> ";
            for (int j=1; j<solution.get(i).size() - 1; j++) {
                result += solution.get(i).get(j) + " -> ";
            }
            result += "0\nCapacity: " + this.capacity.get(i)+"\nCost: " + this.costs.get(i) + "\n";
        }
        return result;
    }
    
}
