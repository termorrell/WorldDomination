package view;

import updates.Update;

/**
 * Created by Caroline on 02/04/2015.
 */
public interface ControllerApiInterface {
    public void addUpdate(Update update);
    public Update addUpdateAndWaitForResponse(Update update);
}
