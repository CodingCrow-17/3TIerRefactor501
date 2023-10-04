package mocks;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MockMouseAdapter extends MouseAdapter{
    private int callCount = 0;
    @Override
    public void mouseClicked(MouseEvent e) {
        callCount++;
    }
    public void reset(){
        callCount = 0;
    }
    public int getCallCount(){
        return callCount;
    }
}
