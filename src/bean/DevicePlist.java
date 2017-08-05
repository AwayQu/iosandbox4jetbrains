/**
 * Created by away on 05/08/2017.
 */
package bean;

public class DevicePlist {

    private String UDID;
    private String deviceType;
    private String name;
    private String runtime;
    private int state;

    public DevicePlist() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVersion() {
        String runtime = this.getRuntime();
        String[] strings = runtime.split("\\.");
        return strings[strings.length - 1];

    }

    public String getDevice() {
        return this.getName();
    }

    public String getBoxName() {
        return this.getDevice() + "> (" + this.getVersion() + ")";
    }
}
