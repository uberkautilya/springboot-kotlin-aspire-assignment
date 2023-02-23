package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.dto.TeacherFilter
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Predicate

class TeacherSpecification {
    companion object {

        fun build(teacherFilter: TeacherFilter): Specification<Teacher> {
            return Specification { root, query, criteriaBuilder ->
                if (Objects.isNull(teacherFilter)) {
                    return@Specification criteriaBuilder.and(*arrayOfNulls(0))
                } else {
                    val predicateList = mutableListOf<Predicate>()
                    if (Objects.nonNull(teacherFilter.id)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), teacherFilter.id))
                    }
                    if (Objects.nonNull(teacherFilter.courseList)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("courseList"), teacherFilter.courseList))
                    }
                    if (Objects.nonNull(teacherFilter.department)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("department"), teacherFilter.department))
                    }
                    if (Objects.nonNull(teacherFilter.name)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), teacherFilter.name))
                    }
                    if (Objects.nonNull(teacherFilter.orderBy)) {
                        query.orderBy(criteriaBuilder.desc(root.get<Any>(teacherFilter.orderBy)))
                    } else {
                        query.orderBy(criteriaBuilder.asc(root.get<Any>("id")))
                    }
                    criteriaBuilder.and(*predicateList.toTypedArray())
                }
            }
        }

    }
}