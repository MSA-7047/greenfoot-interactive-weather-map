import greenfoot.*;

/**
 * The GraphNavigationButton class is responsible for moving the line graph display back and forth between segments of 24 hour
 * intervals to see temperature forecast data. The two button types are "Prev" and "Next". The background colors of the buttons 
 * change when hovered and when clicked by the user due to inheritance from the Button class.
 * 
 * @author  Mohammad Sameen Ahmed
 * @version 1.0 (03.04.2025)
 */
public class GraphNavigationButton extends Button {
    private LineGraphDisplay graph;
    private boolean isNext;
    
    /**
     * Constructor for objects of class GraphNavigationButton.
     * 
     * @param   graph   the LineGraphDisplay object
     * @param   isNext  a boolean value determining the type of navigation
     */
    public GraphNavigationButton(LineGraphDisplay graph, boolean isNext) {
        super(55, 30, isNext ? "Next" : "Prev"); // The name of the button depends on the value of isNext
        this.graph = graph;
        this.isNext = isNext;
    }

    /**
     * Updates the line graph to display the next or previous segment of 24 hours depending on the value of isNext.
     */
    @Override
    protected void onClick() {
        if (isNext) {
            graph.nextDay();
        } else {
            graph.prevDay();
        }
    }
}