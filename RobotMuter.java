import java.awt.Robot;
import java.awt.AWTException;

public class RobotMuter {
    public void mute(User toMute) throws AWTException {
        String username = toMute.getName();
        String muteCommand = "/mute ";
        Robot robot = new Robot();
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {

        }
        //enter in the mute command and the user
        for (int i = 0; i < muteCommand.length(); i++) {
            robot.keyPress(muteCommand.charAt(i));
        }
        for (int i = 0; i < username.length(); i++) {
            robot.keyPress(username.charAt(i));
            robot.keyRelease(username.charAt(i));
            try {
                Thread.currentThread().sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        //press enter
        robot.keyPress(10);
        robot.keyRelease(10);
    }
}
