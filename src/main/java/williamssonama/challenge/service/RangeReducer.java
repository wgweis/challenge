package williamssonama.challenge.service;

import org.springframework.stereotype.Component;
import williamssonama.challenge.model.RangeTuple;

import java.util.*;

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

        NodeSearch searcher = new NodeSearch();

        for (RangeTuple rt: list) {
            switch (rt.getRangeType()) {
                case START: if (searcher.currentStart == null) {
                                searcher.currentStart = rt;
                                searcher.visitedStartNodes.put(rt.getStartIdentifier(), rt);
                            } else {
                                searcher.visitedStartNodes.put(rt.getStartIdentifier(), rt);
                            }
                            break;
                case END: if (searcher.currentStart == null) {
                             throw new IllegalArgumentException("End node found before start: " + rt.getRangeType() );
                          } else{
                             // always remove the matching start node from the visited Set
                             searcher.visitedStartNodes.remove(rt.getStartIdentifier());
                             if (rt.getStartIdentifier().equals(searcher.currentStart.getStartIdentifier())) {
                                 if (searcher.visitedStartNodes.isEmpty()) {
                                     System.out.println("Range output [" + searcher.currentStart.getRangeValue() + ", " + rt.getRangeValue() + "]");
                                     searcher.currentStart = null;
                                 } else {
                                     // continue on but with the current start value but the identifiers of the next entry in the TreeMap
                                     Optional<RangeTuple> nextVisited
                                             = searcher.visitedStartNodes.entrySet().stream().findFirst().map(r -> r.getValue());
                                     searcher.currentStart.setStartIdentifier(nextVisited.get().getStartIdentifier());
                                     searcher.currentStart.setEndIdentifier(nextVisited.get().getEndIdentifier());
                                 }
                             }
                          }
                          break;
                default:  throw new IllegalStateException("Undefined tuple type encountered: " + rt);
            }
        }

        return reducedRanges;
    }

    private class NodeSearch {
        RangeTuple currentStart;

        Map<String, RangeTuple> visitedStartNodes = new LinkedHashMap<>();
    }
}
