package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.dto.StudentFilter
import io.mcm.kotlinaspireassignment.model.entity.Student
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Predicate

class StudentSpecification {
    companion object {

        fun build(studentFilter: StudentFilter): Specification<Student> {
            return Specification { root, query, criteriaBuilder ->
                val predicateList = mutableListOf<Predicate>()
                if (Objects.isNull(studentFilter) || Objects.isNull(studentFilter)) {
                    return@Specification criteriaBuilder.and(*arrayOfNulls(0))
                } else {
                    if (Objects.nonNull(studentFilter.id) && studentFilter.id != 0) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), studentFilter.id))
                    }
                    if (Objects.nonNull(studentFilter.name) && studentFilter.name != "") {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), studentFilter.name))
                    }
//                    if (Objects.nonNull(studentFilter.department)) {
//                        predicateList.add(criteriaBuilder.equal(root.get<Any>("department"), studentFilter.department))
//                    }
//                    if (Objects.nonNull(studentFilter.courseList)) {
//                        predicateList.add(criteriaBuilder.equal(root.get<Any>("courseList"), studentFilter.courseList))
//                    }
                    if (Objects.nonNull(studentFilter.orderBy) && studentFilter.orderBy != "") {
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