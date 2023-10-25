package com.workintech.Course.Rest.Api.entity;

import org.springframework.stereotype.Component;

@Component
public class HighCourseGpa implements CourseGpa{
    public int getGpa(){
        return 10;
    }
}
