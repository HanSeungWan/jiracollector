import com.daou.jiracollector.dao.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by intern on 2016-04-07.
 */
class Util {

    public void rolBackTable(){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "DELETE FROM AdminTbEntity as admin";
        Query query = session.createQuery(hql);
        query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        /*delete indicator_tb*/
        HibernateUtil.beginTransition();

        session = HibernateUtil.getCurrentSeesion();

        hql = "DELETE FROM IndicatorTbEntity as indicator";
        query = session.createQuery(hql);
        query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

         /**/
        HibernateUtil.beginTransition();

        session = HibernateUtil.getCurrentSeesion();

        hql = "DELETE FROM VersionTbEntity ";
        query = session.createQuery(hql);
        query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        /**/
        HibernateUtil.beginTransition();

        session = HibernateUtil.getCurrentSeesion();

        hql = "DELETE FROM ProjectTbEntity ";
        query = session.createQuery(hql);
        query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        /*delete user_tb*/
        HibernateUtil.beginTransition();

        session = HibernateUtil.getCurrentSeesion();

        hql = "DELETE FROM UserTbEntity as user";
        query = session.createQuery(hql);
        query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

    }
}
