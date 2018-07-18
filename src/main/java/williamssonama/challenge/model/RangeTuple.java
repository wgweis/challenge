package williamssonama.challenge.model;

public class RangeTuple {
    private Integer rangeValue;
    private RANGE_TYPE rangeType;

    public RangeTuple(Integer rangeValue, RANGE_TYPE rangeType) {
        this.rangeValue = rangeValue;
        this.rangeType = rangeType;
    }

    public Integer getRangeValue() {
        return rangeValue;
    }

    public RANGE_TYPE getRangeType() {
        return rangeType;
    }
}