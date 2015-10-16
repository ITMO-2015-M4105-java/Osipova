/**
 * Created by AnSick on 03.10.2015.
 *
 * Class for storing both the price of the product AND the counter of how many times it appeared
 * among all of the files
 */
public class PriceCounter {
    public Integer Price;
    public Integer Counter;
    PriceCounter(Integer Price, Integer Counter){
        this.Price=Price;
        this.Counter = Counter;
    }
}
