package com.workintech.Course.Rest.Api.dto;

import com.workintech.Course.Rest.Api.entity.*;

public class CourseResponseFactory {
    public static CourseResponse createCourseResponse(Course course, CourseGpa low,
                                                      CourseGpa medium, CourseGpa high){
if(course.getCredit() <= 2) {
    return new CourseResponse(course,
            course.getGrade().getCoefficient() * course.getCredit() * ((LowCourseGpa)low).getGpa());
    } else if (course.getCredit() == 3) {
    return new CourseResponse(course,
            course.getGrade().getCoefficient() * course.getCredit() * ((MediumCourseGpa)medium).getGpa());
    } else if (course.getCredit() == 4) {
    return new CourseResponse(course,
            course.getGrade().getCoefficient() * course.getCredit() * ((HighCourseGpa)high).getGpa());
}
        return null;
    }
}
