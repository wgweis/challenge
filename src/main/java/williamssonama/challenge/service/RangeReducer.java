package williamssonama.challenge.service;

import org.springframework.stereotype.Component;
import williamssonama.challenge.model.RangeTuple;

import java.util.ArrayList;
import java.util.List;

@Component
public class RangeReducer {
    private final static List<Integer[]> sampleData = new ArrayList<>();

    static {
        //sampleData.add(new Integer[] {1, 3});
        //sampleData.add(new Integer[] {0, 8});
//        sampleData.add(new Integer[] {94133,94133});
//        sampleData.add(new Integer[] {94200,94299});
//        sampleData.add(new Integer[] {94600,94699});


        //[94133,94133] [94200,94299] [94600,94699]

        sampleData.add(new Integer[] {94133,94133});
        sampleData.add(new Integer[] {94200,94299});
        sampleData.add(new Integer[] {94226,94399});

        // [94133,94133] [94200,94299] [94226,94399]
    }
    

    public List<Integer[]> reduceRangeList(List<RangeTuple> list) {
        List<Integer[]> reducedRanges = new ArrayList<>();

        Integer[] tuple =  new Integer[2];
        for (RangeTuple rt: list) {
            switch (rt.getRangeType()) {
                case START: if (tuple[0] == null) {
                                tuple[0] = new Integer(rt.getRangeValue());
                            } else if (tuple[1] != null && tuple[0] > tuple[1]) {
                                System.out.println("Range output [" + tuple[0] + ", " + tuple[1] + "]");
                                tuple[0] = new Integer(rt.getRangeValue());
                                tuple[1] = null;
                            }
                            break;
                case END: if (tuple[0] == null) {
                             throw new IllegalArgumentException("End node found before start: " + rt.getRangeType() );
                          } else {
                             tuple[1] = rt.getRangeValue();
                          }
                          break;
                default:  throw new IllegalStateException("Undefined tuple type encountered: " + rt);
            }
        }
        System.out.println("Range output [" + tuple[0] + ", " + tuple[1] + "]");

        return reducedRanges;
    }
}
