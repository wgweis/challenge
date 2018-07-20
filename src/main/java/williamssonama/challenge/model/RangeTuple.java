package williamssonama.challenge.model;

public class RangeTuple {
    private Integer rangeValue;
    private RANGE_TYPE rangeType;
    private String startIdentifier;

    public RangeTuple(Integer rangeValue, RANGE_TYPE rangeType, String startIdentifier) {
        this.rangeValue = rangeValue;
        this.rangeType = rangeType;
        this.startIdentifier = startIdentifier;
    }

    public Integer getRangeValue() {
        return rangeValue;
    }

    public RANGE_TYPE getRangeType() {
        return rangeType;
    }

    public String getStartIdentifier() {
        return startIdentifier;
    }

    public void setStartIdentifier(String startIdentifier) {
        this.startIdentifier = startIdentifier;
    }
}