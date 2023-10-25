package com.workintech.Course.Rest.Api.controller;

import com.workintech.Course.Rest.Api.dto.CourseResponse;
import com.workintech.Course.Rest.Api.dto.CourseResponseFactory;
import com.workintech.Course.Rest.Api.entity.Course;
import com.workintech.Course.Rest.Api.entity.CourseGpa;
import com.workintech.Course.Rest.Api.exceptions.CourseException;
import com.workintech.Course.Rest.Api.exceptions.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {
    private List<Course> courses;
    private CourseGpa low;
    private CourseGpa medium;
    private CourseGpa high;
@PostConstruct
public void init(){
    courses = new ArrayList<>();
}
@Autowired
    public CourseController (@Qualifier("lowCourseGpa") CourseGpa low,
                             @Qualifier("mediumCourseGpa") CourseGpa medium,
                             @Qualifier("highCourseGpa") CourseGpa high){
    this.low = low;
    this.medium=medium;
    this.high=high;

    }

    @GetMapping("/")
    public List<Course> findAll(){
    return courses;
    }
@GetMapping("/{name}")
    public Course find(@PathVariable String name){
 Optional<Course> optionalCourse = courses.stream()
         .filter(course -> course.getName().equals(name)).findFirst();
 if(optionalCourse.isPresent()){
     return optionalCourse.get();
 } else {
    throw new CourseException("Course with given name is not exist" + name, HttpStatus.NOT_FOUND);
 }
}

@PostMapping("/")
    public CourseResponse save(@RequestBody Course course){
    CourseValidation.isIdValid(course.getId());
    CourseValidation.checkCourseIsValid(course);
    CourseValidation.isDuplicateNameFound(courses, course.getName());
courses.add(course);
  return   CourseResponseFactory.createCourseResponse(course, low, medium, high);
}

@PutMapping("/{id}")
    public Course update(@RequestBody Course course, @PathVariable int id){
    CourseValidation.checkCourseIsValid(course);

    Optional<Course> optionalCourse = courses.stream()
            .filter(course1 -> course1.getId() == id).findFirst();
    if (optionalCourse.isPresent()){
        int index = courses.indexOf(optionalCourse.get());
        course.setId(id);
        courses.set(index, course);
        return course;
    } else {
        throw new CourseException("Course with given id is not exist" + id, HttpStatus.NOT_FOUND);

    }

}
@DeleteMapping("/{id}")
public  Course delete(@PathVariable int id){
    Optional<Course> optionalCourse = courses.stream()
            .filter(course1 -> course1.getId() == id).findFirst();
    if (optionalCourse.isPresent()){
        int index = courses.indexOf(optionalCourse.get());
        courses.remove(index);
        return optionalCourse.get();
    } else {
        throw new CourseException("Course with given id is not exist" + id, HttpStatus.NOT_FOUND);

    }
}
}
