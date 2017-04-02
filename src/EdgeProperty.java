
public class EdgeProperty{
    private int capacity;
    private int currentPackages;
    private double unspoiltProbability;

    public EdgeProperty(int capacity, double unspoiltProbability) {
        this.capacity = capacity;
        currentPackages = 0;
        this.unspoiltProbability = unspoiltProbability;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentPackages() {
        return currentPackages;
    }

    public double getUnspoiltProbability() {
        return unspoiltProbability;
    }

    public boolean addCurrentPackages(int p){
        if(currentPackages + p <= capacity){
            currentPackages += p;
            return true;
        }
        return false;
    }
}
