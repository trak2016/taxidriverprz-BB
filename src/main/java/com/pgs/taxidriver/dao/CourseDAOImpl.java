package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Course;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hsadecki on 2015-09-07.
 */
@Service
public class CourseDAOImpl extends GenericDAOImpl<Course> implements CourseDAO {

    @Override
    public List<Course> getSortedCourseList() {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        return session.createQuery("from Course c order by c.date desc").list();
    }

    @Override
    public List<Course> getSortedCourseListByCompany(long companyId) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Query query =
                session.createQuery("select u.courses from UserCompany uc inner join uc.user u where uc.company.id=:companyid order by date desc");
        query.setParameter("companyid", companyId);
        List<Course> test = query.list();
        return query.list();
    }
}
