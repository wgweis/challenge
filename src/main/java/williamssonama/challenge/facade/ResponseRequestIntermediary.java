package williamssonama.challenge.facade;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import williamssonama.challenge.model.RANGE_TYPE;
import williamssonama.challenge.model.RangeTuple;
import williamssonama.challenge.model.ResponseDataTransferObject;
import williamssonama.challenge.service.RangeReducer;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ResponseRequestIntermediary {

    private static final Type rangeListType  = new TypeToken<List<Integer[]>>(){}.getType();

    private Gson gson;

    private RangeReducer rangeReducer;

    @Autowired
    public ResponseRequestIntermediary(Gson gson, RangeReducer rangeReducer) {
        this.gson = gson;
        this.rangeReducer = rangeReducer;
    }

    public String processRequest(String gsonList) {
        ResponseDataTransferObject responseDataTransferObject = new ResponseDataTransferObject();

        try {

            List<Integer[]> list = gson.fromJson(gsonList, rangeListType);

            List<RangeTuple> rangeTupleList = getSortedList(list, responseDataTransferObject);
            List<Integer[]> processedList = rangeReducer.reduceRangeList(rangeTupleList);
            responseDataTransferObject.setElements(list);

        } catch (JsonSyntaxException jse) {
            responseDataTransferObject.addError("Invalid Range Array List.  Could not process. " + (gsonList.isEmpty()? "null": gsonList.substring(0, Math.min(gsonList.length(), 1000))));
        }

        return gson.toJson(responseDataTransferObject);
    }

    private List<RangeTuple> getSortedList(List<Integer[]> input, ResponseDataTransferObject rto) {
        List<RangeTuple> formattedList = new ArrayList<>();
        for (Integer[] rangeIndicator: input) {
            if (rangeIndicator[0] > rangeIndicator[1]) {
                rto.addWarning("Invalid range Element encountered: Start of " +  rangeIndicator[0] + " cannot be greater than end of " + rangeIndicator[1] + ": Skipping this element.");
                continue;
            }

            formattedList.add(new RangeTuple(rangeIndicator[0], RANGE_TYPE.START));
            formattedList.add(new RangeTuple(rangeIndicator[1], RANGE_TYPE.END));
        }
        formattedList.sort(Comparator.comparingInt((RangeTuple r) -> r.getRangeValue()));
        return formattedList;
    }
}
