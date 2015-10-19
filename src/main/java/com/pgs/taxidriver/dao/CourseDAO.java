package com.pgs.taxidriver.dao;

import com.pgs.taxidriver.model.Course;

import java.util.List;

/**
 * Created by hsadecki on 2015-09-07.
 */
public interface CourseDAO extends GenericDAO<Course> {

    /**
     * get courses list sorted by date descending
     *
     * @return
     */
    public List<Course> getSortedCourseList();

    /**
     * get courses list sorted by date descending for specific company
     *
     * @param companyId
     * @return list of courses for specific company
     */
    public List<Course> getSortedCourseListByCompany(long companyId);
}
