package williamssonama.challenge.service;

import org.springframework.stereotype.Service;
import williamssonama.challenge.model.RangeTuple;

import java.util.*;

@Service
public class RangeReducer {

    /**
     * Reduction of zip code ranges.
     * What we are actually doing here is combining sets, each set being all the integers between a start and an end value.
     * If we use Venn Diagrams, there are only three possiblities for two sets:
     *  a.)  All elements in one set are contained in the other set.
     *  b.)  Some elements in one set are contained in the other set.
     *  c.)  No elements in one set are contained in the other set.
     *
     *  Things get tricky when we have more than two sets involved.  For that reason we have to remember what set we started from
     *  and ignore the starting boundary of any other sets encountered until we either encounter the ending boundary of the set
     *  we started from or our own ending boundary is less than ending boundary of a partially contained set.
     * @param list
     * @return
     */
    public List<Integer[]> reduceRangeList(List<RangeTuple> list) {
        List<Integer[]> reducedRanges = new ArrayList<>();

        NodeSearch searcher = new NodeSearch();

        for (RangeTuple rt: list) {
            switch (rt.getRangeType()) {
                case START: searcher.visitedStartNodes.put(rt.getStartIdentifier(), rt);
                            if (searcher.currentStart == null) {
                                searcher.currentStart = rt;
                            }
                            break;
                case END: if (searcher.currentStart == null) {
                             throw new IllegalArgumentException("End node found before start: " + rt.getRangeType() );
                          } else{
                             // always remove the matching start node from the visited Set
                             searcher.visitedStartNodes.remove(rt.getStartIdentifier());
                             if (rt.getStartIdentifier().equals(searcher.currentStart.getStartIdentifier())) {
                                 // we may have found the end of the union of our sets here.
                                 if (searcher.visitedStartNodes.isEmpty()) {
                                     System.out.println("Range output [" + searcher.currentStart.getRangeValue() + ", " + rt.getRangeValue() + "]");
                                     reducedRanges.add(new Integer[] {searcher.currentStart.getRangeValue(), rt.getRangeValue()});
                                     searcher.currentStart = null;
                                 } else {
                                     // continue on but with the current start value but the identifier of the next entry in the TreeMap
                                     Optional<RangeTuple> nextVisited
                                             = searcher.visitedStartNodes.entrySet().stream().findFirst().map(r -> r.getValue());
                                     searcher.currentStart.setStartIdentifier(nextVisited.get().getStartIdentifier());
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
