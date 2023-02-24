package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.TeacherFilter
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
                    if (null != teacherFilter.id && teacherFilter.id != 0) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), teacherFilter.id))
                    }
                    if (null != teacherFilter.courseList && teacherFilter.courseList!!.size != 0) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("courseList"), teacherFilter.courseList))
                    }
                    if (null != teacherFilter.department && teacherFilter.department!!.id != 0) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("department"), teacherFilter.department))
                    }
                    if (null != teacherFilter.name && teacherFilter.name != "") {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), teacherFilter.name))
                    }
                    if (null != teacherFilter.salary) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("salary"), teacherFilter.salary))
                    }
                    if (null != teacherFilter.joiningDate) {
                        predicateList.add(
                            criteriaBuilder.equal(
                                root.get<Any>("joiningDate"),
                                teacherFilter.joiningDate
                            )
                        )
                    }
                    if (null != teacherFilter.orderBy && teacherFilter.orderBy != "") {
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