package williamssonama.challenge.model;

public class RangeTuple {
    private Integer rangeValue;
    private RANGE_TYPE rangeType;
    private String startIdentifier;
    private String endIdentifier;

    public RangeTuple(Integer rangeValue, RANGE_TYPE rangeType, String startIdentifier, String endIdentifier) {
        this.rangeValue = rangeValue;
        this.rangeType = rangeType;
        this.startIdentifier = startIdentifier;
        this.endIdentifier = endIdentifier;
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

    public String getEndIdentifier() {
        return endIdentifier;
    }

    public void setEndIdentifier(String endIdentifier) {
        this.endIdentifier = endIdentifier;
    }
}