import com.daou.jiracollector.dao.manager.AdminManager;
import com.daou.jiracollector.dao.manager.ProjectManager;
import com.daou.jiracollector.dao.manager.UserManager;
import com.daou.jiracollector.dao.manager.VersionManager;
import com.daou.jiracollector.dao.manager.tbmanager.IndicatorTbManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by intern on 2016-04-07.
 */
public class IndicatorSettingTest {

    AdminManager adminManager;
    ProjectManager projectManager;
    VersionManager versionManager;
    UserManager userManager;
    IndicatorTbManager indicatorTbManager;

    @Before
    public void setInit() {

        adminManager = new AdminManager();
        projectManager = new ProjectManager();
        versionManager = new VersionManager();
        userManager = new UserManager();
        indicatorTbManager = new IndicatorTbManager();
    }

    @After
    public void rollbackDataBase() {

        Util util = new Util();

        util.rolBackTable();
    }

    /*test manager_tb*/
    @Test
    public void testIndicatorSetting() throws Exception {

        assertTrue(userManager.setUser("윤병식"));

        assertEquals("윤병식", userManager.getUser(1));

        projectManager.setProjectList("DaouOffice", "GO", 1, true, 0);

        List list = projectManager.getAllProjectList();

        for (Object aProjectInfo : list) {

            Object[] projectInfoArray = (Object[]) aProjectInfo;

            assertEquals(projectInfoArray[0], "DaouOffice");
            assertEquals(projectInfoArray[1], "GO");
            assertEquals(projectInfoArray[2], 0);
            assertEquals(projectInfoArray[3], 1);
            assertEquals(projectInfoArray[4], true);
            assertEquals(projectInfoArray[5], 1);
        }

        versionManager.setVersion(1, "1.0.0", true);

        list = versionManager.getVersion(1);

        assertEquals(list.get(0), "1.0.0");

        versionManager.updateRelease("GO", "1.0.0", 0);

        list = versionManager.getVersionNameRelease("GO");

        for (Object aVersionList : list) {
            Object[] versionInfoArray = (Object[]) aVersionList;

            assertEquals(versionInfoArray[0], "1.0.0");
            assertEquals(versionInfoArray[1], false);
        }

        versionManager.updateRelease("GO", "1.0.0", 1);

        list = versionManager.getVersionNameRelease(1);

        for (Object aVersionList : list) {
            Object[] versionInfoArray = (Object[]) aVersionList;

            assertEquals(versionInfoArray[0], "1.0.0");
            assertEquals(versionInfoArray[1], true);
        }

        assertTrue(indicatorTbManager.setIndicator("이슈추이율"));

        assertTrue(adminManager.setIndicator(1, 1, 90, "%", "테스트"));

        list = adminManager.getIndicator();

        for (Object aIndicator : list) {
            Object[] indicator = (Object[]) aIndicator;

            assertEquals("GO",indicator[0]);
            assertEquals("이슈추이율",indicator[1]);
            assertEquals(90,indicator[2]);
            assertEquals("%",indicator[3]);
            assertEquals("테스트",indicator[4]);
        }

        assertTrue(adminManager.updateIndicator(1, "이슈추이율", 100, "%", "테스트2"));

        list = adminManager.getIndicator();

        for (Object aIndicator : list) {
            Object[] indicator = (Object[]) aIndicator;

            assertEquals("GO",indicator[0]);
            assertEquals("이슈추이율",indicator[1]);
            assertEquals(100,indicator[2]);
            assertEquals("%",indicator[3]);
            assertEquals("테스트2",indicator[4]);
        }

        adminManager.deleteIndicator(1, "이슈추이율");
    }
}
