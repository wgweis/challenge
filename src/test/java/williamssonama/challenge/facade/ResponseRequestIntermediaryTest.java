package williamssonama.challenge.facade;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Before;
import org.junit.Test;
import williamssonama.challenge.model.ResponseDataTransferObject;
import williamssonama.challenge.service.RangeReducer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ResponseRequestIntermediaryTest {

    private static final Type responseDataTransferObjectType  = new TypeToken<ResponseDataTransferObject>(){}.getType();

    ResponseRequestIntermediary rri;
    RangeReducer rangeReducer;

    Gson gson;

    @Before
    public void init() {
        gson = new Gson();
        rangeReducer = new RangeReducer();

        rri = new ResponseRequestIntermediary(gson, rangeReducer);
    }

    @Test
    public void processRequestTest() {

        List<Integer[]> list = new ArrayList<>();
        list.add(new Integer[] {1, 2});
        list.add(new Integer[] {3, 4});
        list.add(new Integer[] {2, 5});
        list.add(new Integer[] {0, 6});

        String gsonList = gson.toJson(list);

        System.out.println("Here's the array: " + gson.toJson(list));

        String serviceResponse = rri.processRequest(gsonList);

        ResponseDataTransferObject responseDataTransferObject = gson.fromJson(serviceResponse, responseDataTransferObjectType);

        assertTrue("Expected non-empty result back", responseDataTransferObject.hasElements());

    }

}