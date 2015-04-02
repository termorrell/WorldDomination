package view;

import updates.Update;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Caroline on 02/04/2015.
 */
public class GuiApiServiceImpl implements GuiApiService, ControllerApiInterface{

    Queue<Update> updates = new LinkedList<>();

    Update response = null;

    private synchronized boolean ready() {
        if(response != null) {
            return true;
        }
        return false;
    }

    @Override
    public void addUpdate(Update update) {
        updates.add(update);
    }

    @Override
    public Update addUpdateAndWaitForResponse(Update update) {
        updates.add(update);
        while(!ready()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // TODO
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public synchronized Update getNextUpdate(Update response) {
        if(response != null) {
            this.response = response;
        }
        if(updates.isEmpty()) {
            return null;
        }
        return updates.poll();
    }
}
