package bean;

import java.util.ArrayList;

/**
 * Created by away on 03/08/2017.
 */

public class Sandbox {



    public class MenuItem {
        public int index;
        public Sandbox sandbox;
    }

    private String UDID;
    private String version;
    private String device;
    private String boxName;
    private ArrayList<String> items;
    public ArrayList<String> projectSandBoxPath;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public ArrayList<String> getProjectSandBoxPath() {
        return projectSandBoxPath;
    }

    public void setProjectSandBoxPath(ArrayList<String> projectSandBoxPath) {
        this.projectSandBoxPath = projectSandBoxPath;
    }

}

