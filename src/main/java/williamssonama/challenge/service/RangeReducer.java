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
     *
     *  This method also handles one outlier case:  the case where two intervals (sets) share a common border.  There may be more outliers
     *  I have not considered.
     *  The outlier case is dependent on Java 8 stream sorting not randomly shuffling the order of nodes with the same range value.  That is, putting
     *  an end node before a start node if their values are the same.  Not necessarily the case if sort uses parallelization.
     *
     * @param list
     * @return
     */
    public List<Integer[]> reduceRangeList(List<RangeTuple> list) {
        List<Integer[]> reducedRanges = new ArrayList<>();

        NodeSearch searcher = new NodeSearch();

        for (int i = 0; i < list.size(); i++) {

            RangeTuple rt = list.get(i);
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
                                     if (doesNotShareBoundary(i, rt, list)) {
                                         System.out.println("Range output [" + searcher.currentStart.getRangeValue() + ", " + rt.getRangeValue() + "]");
                                         reducedRanges.add(new Integer[]{searcher.currentStart.getRangeValue(), rt.getRangeValue()});
                                         searcher.currentStart = null;
                                     } else if (i + 1 < list.size()){
                                         // set the current start identifier to the next node so we can start looking for the end of that one.
                                         searcher.currentStart.setStartIdentifier(list.get(i + 1).getStartIdentifier());

                                     }
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

    private boolean doesNotShareBoundary(int ndx, RangeTuple endNode, List<RangeTuple> list) {
        int peakNdx = ndx + 1;
        if (peakNdx < list.size()) {
            return endNode.getRangeValue() < list.get(peakNdx).getRangeValue();
        }
        // case where we are at the end of the input.
        return true;
    }

    private class NodeSearch {
        RangeTuple currentStart;

        Map<String, RangeTuple> visitedStartNodes = new LinkedHashMap<>();
    }
}
