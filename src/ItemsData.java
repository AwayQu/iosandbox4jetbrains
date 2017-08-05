import bean.BeanFactory;
import bean.DevicePlist;
import bean.Sandbox;
import com.intellij.ui.mac.foundation.Foundation;
import com.intellij.ui.mac.foundation.ID;
import dependency.plistparser.PListDict;
import dependency.plistparser.PListException;
import dependency.plistparser.PListParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by away on 03/08/2017.
 */

public class ItemsData {

    public ItemsData() {

    }



    static ArrayList<DevicePlist> getDeviceInfoPlists() {
        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        ArrayList<DevicePlist> plists = new ArrayList<>();
        for (File f: files) {

            try {
                String devicePlistPath = f.getAbsolutePath() + "/device.plist";
                DevicePlist devicePlist = BeanFactory.createDevicePlistBean(devicePlistPath);
                plists.add(devicePlist);
            } catch (NullPointerException e) {

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (PListException e) {
                e.printStackTrace();
            }

        }

        return plists;
    }

    /**
     * getAllItems
     * @return
     */
    static ArrayList<Sandbox> getSandboxList() {
        ArrayList<DevicePlist> devicePlists = ItemsData.getDeviceInfoPlists();

        ArrayList<Sandbox> sandboxes = new ArrayList<>();
        for (DevicePlist dp : devicePlists) {
            Sandbox sandbox = new Sandbox();

            sandbox.setUDID(dp.getUDID());

            sandbox.setBoxName(dp.getBoxName());
            sandbox.setVersion(dp.getVersion());
            sandbox.setDevice(dp.getDevice());

            sandbox.items = ItemsData.getProjects(sandbox);

            sandboxes.add(sandbox);

        }

        return sandboxes;
    }

    static ArrayList<String> getProjects(Sandbox sandbox) {
        ArrayList arrayList = new ArrayList<>();

        String applicationPath = ItemsData.getDevicePath(sandbox.getUDID());
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> projectSandboxPaths = new ArrayList<>();

        File applicationPathDir = new File(applicationPath);


        File[] files = applicationPathDir.listFiles();
        if (null == files) {
            return null;
        }

        for (File f: files) {
            String fileName = f.getAbsolutePath();
            String fileUrl = ItemsData.getDataDictPath(fileName);

            File fileUrlFile = new File(fileUrl);
            if (!fileUrlFile.exists()) {
                File fileNameFile = new File(fileName);
                File[] fileNameFiles = fileNameFile.listFiles();
                if (null == fileNameFiles) {
                    continue;
                }
                for (File f1 : fileNameFiles) {
                    if (f1.getName().contains(".app")){
                        names.add(f1.getName().replace(".app", "").replace("-", "_"));
                        projectSandboxPaths.add(fileName);
                    }
                }
            } else {
                try {
                    ID myDelegate = Foundation.invoke(Foundation.invoke("NSAutoreleasePool", "alloc", new Object[0]), "init", new Object[0]);
                    Foundation.NSDictionary dictionary = new Foundation.NSDictionary(Foundation.invoke("NSDictionary", "dictionaryWithContentsOfFile:", new Object[]{fileUrl}));

                    PListDict dict = PListParser.parse(fileUrl);

                    names.add(dict.getString("MCMMetadataIdentifier"));
                    projectSandboxPaths.add(fileName);
                } catch (PListException e) {
                    e.printStackTrace();
                }
            }

        }
        sandbox.projectSandBoxPath = projectSandboxPaths;

        return names;
    }


    /**
     * applicationData path
     * @param UDID
     * @return
     */
    static String getDevicePath(String UDID) {

        File file = ItemsData.getSimulatorHomeFile();
        if (!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();
        if (null == files) {
            return null;
        }

        String applicationPath = null;

        for (File f: files) {
            String devicePlistPath = f.getAbsolutePath() + "/device.plist";
            String fPath = f.getAbsolutePath();
            try {
                DevicePlist devicePlist = BeanFactory.createDevicePlistBean(devicePlistPath);
                if (devicePlist.getUDID().contains(UDID)) {
                    applicationPath = ItemsData.getApplicationDataPath(fPath);
                    File applicationFile = new File(applicationPath);
                    if (!applicationFile.exists()) {
                        return null;
                    }

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (PListException e) {
                e.printStackTrace();
            }
        }

        return applicationPath;

    }


    static String getDataDictPath(String filePath) {
        return filePath + "/.com.apple.mobile_container_manager.metadata.plist";
    }

    /**
     * applicationsDataPath
     * @param path
     * @return
     */
    static String getApplicationDataPath(String path) {
        return path + "/data/Containers/Data/Application";

    }

    static String getAppName(String identifierName) {
        String [] arr = identifierName.split("\\.");
        String projectName = arr[arr.length - 1];
        projectName = projectName.replace("-", "_");
        return projectName;
    }

    static File getSimulatorHomeFile() {
        File file = new File(ItemsData.getUserHome() + "/Library/Developer/CoreSimulator/Devices/");
        return file;
    }


    static String getUserHome() {
        return "/Users/away";
    }


}
