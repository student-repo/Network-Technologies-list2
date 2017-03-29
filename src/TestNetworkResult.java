/**
 * Created by ubuntu-master on 29.03.17.
 */
public class TestNetworkResult {
    private int successNumber = 0;
    private int testNumber = 0;

    public void incrementSuccessNumber(){
        successNumber++;
    }

    public void incrementTestNumber(){
        testNumber++;
    }

    public double getPercentResult(){
        return (double)successNumber / (double)testNumber;
    }

    @Override
    public String toString() {
        return "Test number: " + testNumber + " Success number: " + successNumber;
    }
}
