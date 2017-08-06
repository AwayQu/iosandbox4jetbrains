/**
 * Created by away on 06/08/2017.
 */
package plugin;

import bean.Sandbox;
import com.apple.eio.FileManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * TODOList:
 *   1.current application quick open
 *   2.add observer of file change
 */

public class SandboxActionGroup extends ActionGroup {
    public class ProjectSandboxAction extends AnAction {
        private String projectItem;
        private String projectSandboxPath;
        public ProjectSandboxAction(String projectItem, String projectSandboxPath) {
            super(projectItem);
            this.projectItem = projectItem;
            this.projectSandboxPath = projectSandboxPath + "/Documents";

        }

        @Override
        public void actionPerformed(AnActionEvent anActionEvent) {
            try {
                FileManager.revealInFinder(new File(projectSandboxPath));
            } catch (FileNotFoundException e) {
                // todo message
                e.printStackTrace();
            }
        }
    }
    public class DeviceSandboxActionGroup extends ActionGroup {
        private Sandbox sandbox;
        @NotNull
        @Override
        public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
            ArrayList<AnAction> anActions = new ArrayList<AnAction>();
            ArrayList<String> items = sandbox.getItems();
            for (String i: items) {
                anActions.add(new ProjectSandboxAction(i, sandbox.getProjectSandBoxPath().get(items.indexOf(i))));
            }
            AnAction[] arr = new AnAction[anActions.size()];
            anActions.toArray(arr);
            return arr;
        }

        public DeviceSandboxActionGroup(Sandbox sandbox) {
            super(sandbox.getBoxName(), true);
            this.sandbox = sandbox;
        }
    }
    @NotNull
    @Override
    public AnAction[] getChildren(AnActionEvent anActionEvent) {
        ArrayList<AnAction> anActions = new ArrayList<AnAction>();
        ArrayList<Sandbox> sandboxes = ItemsData.getSandboxList();
        for (Sandbox s: sandboxes) {
            anActions.add(new DeviceSandboxActionGroup(s));
        }
        AnAction[] arr = new AnAction[anActions.size()];
        anActions.toArray(arr);
        return arr;
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
    }

    class MyAction extends AnAction {
        public MyAction() {
            super("Dynamically Added Action");
        }
        @Override
        public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        }
    }
}


