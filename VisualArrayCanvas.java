
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JPanel;

public class VisualArrayCanvas extends JPanel {
    private int height, width;
    private int maxBarHeight;
    private int[] arrayValues;
    private int index1, index2; // indices to swap
    private int numberOfComparisons, numberOfSwaps;
    private Color colorViDSortGrey = new Color(150, 150, 150);
    private Color highlightColor;
    
    public VisualArrayCanvas() {
        index1 = index2 = -1;
        numberOfComparisons = numberOfSwaps = 0;
               
        setBackground(Color.white);
    }
    
    public void setArray(int[] arr) {
        arrayValues = arr.clone();
        for (int i = 0; i < arrayValues.length; i++)
            arrayValues[i]++; // minimum value of 1
    }
    
    public void swapArrayValues(int index1, int index2) {
        int temp = arrayValues[index1];
        arrayValues[index1] = arrayValues[index2];
        arrayValues[index2] = temp;
    }
    
    public void updateComparisons(int comparisons) {
        numberOfComparisons = comparisons;
    }
    
    public void updateSwaps(int swaps) {
        numberOfSwaps = swaps;
    }
    
    /*
     * deprecated:
     * new drawArray() allows highlighting swapped values
     */
    public void drawArray(int arr[]) {
        getCanvasDimensions();
        maxBarHeight = height * 9 / 10; // 90% of canvas height
        
        arrayValues = arr;
        
        repaint();
    }
    
    /*
     * draw array without highlighting
     */
    public void drawArray() {
        getCanvasDimensions();
        maxBarHeight = height * 9 / 10; // 90% of canvas height
        
        this.index1 = -1;
        this.index2 = -1;
        
        repaint();
    }
    
    /*
     * draw array with highlighting at indices
     * index1 & index2
     */
    public void drawArray(int index1, int index2) {
        getCanvasDimensions();
        maxBarHeight = height * 9 / 10; // 90% of canvas height
        
        this.index1 = index1;
        this.index2 = index2;
        
        highlightColor = new Color(255, 120, 120);
        
        repaint();
    }
    
    public void drawArrayWithCompareHighlighting(int index1, int index2) {
        getCanvasDimensions();
        maxBarHeight = height * 9 / 10; // 90% of canvas height
        
        this.index1 = index1;
        this.index2 = index2;
        
        highlightColor = new Color(120, 120, 255);
        
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        if (arrayValues == null)
            return;
        
        int maxValueInArray = maxValueIn(arrayValues);
        int changeRatio = maxBarHeight / maxValueInArray;
        int barWidth = width / arrayValues.length;
        
        graphics.setColor(colorViDSortGrey);
        
        int index = 0;
        while (true) {
            int currentValue = arrayValues[index];
            int barHeight = currentValue * changeRatio;
            int barX = barWidth * index;
            int barY = height - barHeight;
            
            if (index == arrayValues.length - 1) {
                if (index == index1 || index == index2) {
                    graphics.setColor(highlightColor);
                    graphics.fillRect(barX, barY, width - barX, barHeight);
                    graphics.setColor(colorViDSortGrey);
                }
                
                graphics.drawRect(barX, barY, width - barX, barHeight);
                                
                break;
            }
            
            if (index == index1 || index == index2) {
                graphics.setColor(highlightColor);
                graphics.fillRect(barX, barY, barWidth, barHeight);
                graphics.setColor(colorViDSortGrey);
            }
                
            graphics.drawRect(barX, barY, barWidth, barHeight);
            
            index++;
        }
        
        FontMetrics metrics = graphics.getFontMetrics();
        
        String stringOfComparisonCount = "# Comparisons: " + String.valueOf(numberOfComparisons);
        int x = (width - metrics.stringWidth(stringOfComparisonCount)) / 2;
        int y = metrics.getHeight();
        graphics.setColor(new Color(120, 120, 255));
        graphics.drawString(stringOfComparisonCount, x, y);
        graphics.setColor(colorViDSortGrey);
        
        String stringOfSwapCount = "# Swaps: " + String.valueOf(numberOfSwaps);
        x = (width - metrics.stringWidth(stringOfSwapCount)) / 2;
        y = metrics.getHeight() * 2;
        graphics.setColor(new Color(255, 120, 120));
        graphics.drawString(stringOfSwapCount, x, y);
        graphics.setColor(colorViDSortGrey);
    }
    
    private int maxValueIn(int[] arr) {
        int max = Integer.MIN_VALUE;
        
        for (int index = 0; index < arr.length; index++) {
            if (arr[index] > max) {
                max = arr[index];
            }
        }
        
        return max;
    }
    
    public void getCanvasDimensions() {
        Dimension arrayCanvasDimensions = getSize();
        width = arrayCanvasDimensions.width;
        height = arrayCanvasDimensions.height;
    }
}
