import com.daou.jiracollector.dao.manager.AdminManager;
import com.daou.jiracollector.dao.manager.ProjectManager;
import com.daou.jiracollector.dao.manager.UserManager;
import com.daou.jiracollector.dao.manager.VersionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by intern on 2016-04-07.
 */
public class AdmDataSettingTest {

    AdminManager adminManager;
    ProjectManager projectManager;
    VersionManager versionManager;
    UserManager userManager;

    @Before
    public void setInit() {

        adminManager = new AdminManager();
        projectManager = new ProjectManager();
        versionManager = new VersionManager();
        userManager = new UserManager();
    }

    @After
    public void rollbackDataBase() {

        Util util = new Util();

        util.rolBackTable();
    }

    @Test
    public void testAdmSetting() throws Exception {

        assertTrue(userManager.setUser("윤병식"));

        assertEquals("윤병식",userManager.getUser(1));

        projectManager.setProjectList("TMSE", "TMSE", 1, true, 0);

        List list = projectManager.getAllProjectList();

        for (Object aProjectInfo : list) {

            Object[] projectInfoArray = (Object[]) aProjectInfo;

            assertEquals(projectInfoArray[0], "TMSE");
            assertEquals(projectInfoArray[1], "TMSE");
            assertEquals(projectInfoArray[2], 0);
            assertEquals(projectInfoArray[3], 1);
            assertEquals(projectInfoArray[4], true);
            assertEquals(projectInfoArray[5], 1);
        }

        versionManager.setVersion(1, "1.0.0", true);

        list = versionManager.getVersion(1);

        assertEquals(list.get(0), "1.0.0");

        versionManager.updateRelease("TMSE", "1.0.0", 1);

        list = versionManager.getVersionNameRelease(1);

        for (Object aVersionList : list) {
            Object[] versionInfoArray = (Object[]) aVersionList;

            assertEquals(versionInfoArray[0], "1.0.0");
            assertEquals(versionInfoArray[1], true);
        }

        adminManager.setStartEndDate(1, 1, "2016-04-07", "2016-04-12");

        List result = adminManager.getStartEndDate("1.0.0", "TMSE");

        for (Object aResult : result) {
            Object[] obj = (Object[]) aResult;

            assertEquals(String.valueOf(obj[0]), "2016-04-07");
            assertEquals(String.valueOf(obj[1]), "2016-04-12");
        }

        assertTrue(adminManager.checkHasdate("1.0.0", "TMSE"));

        assertFalse(adminManager.checkHasdate("2.2.8", "GO"));
    }
}
