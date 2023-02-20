package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.StudentRequest
import io.mcm.kotlinaspireassignment.model.entity.Student
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Predicate

class StudentSpecification {
    companion object {

        fun build(request: StudentRequest): Specification<Student> {
            return Specification { root, query, criteriaBuilder ->
                val predicateList = mutableListOf<Predicate>()
                if (Objects.isNull(request) || Objects.isNull(request.studentFilter)) {
                    return@Specification criteriaBuilder.and(*arrayOfNulls(0))
                } else {
                    val studentFilter = request.studentFilter
                    if (Objects.nonNull(studentFilter.id)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), studentFilter.id))
                    }
                    if (Objects.nonNull(studentFilter.name)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), studentFilter.name))
                    }
                    if (Objects.nonNull(studentFilter.dept)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("dept"), studentFilter.dept))
                    }
                    if (Objects.nonNull(studentFilter.courseList)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("courseList"), studentFilter.courseList))
                    }
                    if (Objects.nonNull(studentFilter.orderBy)) {
                        query.orderBy(criteriaBuilder.desc(root.get<Any>(studentFilter.orderBy)))
                    } else {
                        query.orderBy(criteriaBuilder.asc(root.get<Any>("id")))
                    }
                    criteriaBuilder.and(*predicateList.toTypedArray())
                }
            }
        }

    }
}