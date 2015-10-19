package com.pgs.taxidriver.service;

import com.pgs.taxidriver.model.Course;

import java.util.List;

/**
 * Created by hsadecki on 2015-09-07.
 */
public interface CourseService {

    /**
     * Get Course List
     *
     * @return Set - Course list
     */
    List<Course> getAll();

    /**
     * Get sorted courses list
     *
     * @return list of courses sorted descending by id
     */
    List<Course> getAllSorted();

    /**
     * Get sorted courses list for selected company
     *
     * @return
     */
    List<Course> getAllSortedByCompany(long companyId);

    /**
     * Add bew course
     *
     * @param course
     */
    void addCourse(Course course);
}
