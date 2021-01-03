package at.fhhagenberg.sqelevator.mock;

public class MockFloorState {
    private boolean m_up_pressed = false;
    private boolean m_down_pressed = false;

    public boolean getUpButtonState() {
        return m_up_pressed;
    }
    
    public boolean getDownButtonState() {
        return m_down_pressed;
    }

    public void setUpButtonState(boolean state) {
        m_up_pressed = state;
    }

    public void setDownButtonState(boolean state) {
        m_down_pressed = state;
    }
}