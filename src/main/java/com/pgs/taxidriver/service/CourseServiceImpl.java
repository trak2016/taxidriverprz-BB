package com.pgs.taxidriver.service;

import com.pgs.taxidriver.dao.CourseDAO;
import com.pgs.taxidriver.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hsadecki on 2015-09-07.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDAO courseDAO;

    /**
     * get course list
     *
     * @return course list
     */
    @Transactional
    @Override
    public List<Course> getAll() {
        return courseDAO.findAll();
    }

    /**
     * get course list sorted descending by date
     *
     * @return sorted course list
     */
    @Transactional
    @Override
    public List<Course> getAllSorted() {
        return courseDAO.getSortedCourseList();
    }

    /**
     * get course list sorted descending by date for specific company
     *
     * @return
     */
    @Transactional
    @Override
    public List<Course> getAllSortedByCompany(long companyId) {
        return courseDAO.getSortedCourseListByCompany(companyId);
    }

    /**
     * Add bew course
     *
     * @param course
     */
    @Override
    @Transactional
    public void addCourse(Course course) {
        courseDAO.create(course);
    }
}
