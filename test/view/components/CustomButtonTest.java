package view.components;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import mocks.MockMouseAdapter;

public class CustomButtonTest {

    @Test
    public void testVerboseConstructor(){
        CustomButton testButton = new CustomButton("test", 123, 321, 576, 213, null);
        int actualFontSize = testButton.getFont().getSize();
        String actualFontName = testButton.getFont().getFontName();
        int actualXCor = testButton.getBounds().x;
        int actualYCor = testButton.getBounds().y;
        int actualWidth = testButton.getBounds().width;
        int actualHeight = testButton.getBounds().height;
        assertEquals("Times New Roman", actualFontName);
        assertEquals(11, actualFontSize);
        assertEquals(123, actualXCor);
        assertEquals(321, actualYCor);
        assertEquals(576, actualWidth);
        assertEquals(213, actualHeight);
    }
    
    @Test
    public void testQuickConstructor(){
        CustomButton testButton = new CustomButton("test", 123, 321,  null);
        int actualFontSize = testButton.getFont().getSize();
        String actualFontName = testButton.getFont().getFontName();
        int actualXCor = testButton.getBounds().x;
        int actualYCor = testButton.getBounds().y;
        int actualWidth = testButton.getBounds().width;
        int actualHeight = testButton.getBounds().height;
        assertEquals("Times New Roman", actualFontName);
        assertEquals(11, actualFontSize);
        assertEquals(123, actualXCor);
        assertEquals(321, actualYCor);
        assertEquals(89, actualWidth);
        assertEquals(23, actualHeight);
    }
}
