package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.DepartmentFilter
import io.mcm.kotlinaspireassignment.model.entity.Department
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Predicate

class DepartmentSpecification {
    companion object {

        fun build(departmentFilter: DepartmentFilter): Specification<Department> {
            return Specification { root, query, criteriaBuilder ->
                if (Objects.isNull(departmentFilter)) {
                    criteriaBuilder.and(*arrayOfNulls(0))
                } else {
                    val predicateList = mutableListOf<Predicate>()
                    if (Objects.nonNull(departmentFilter.id) && departmentFilter.id != 0) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), departmentFilter.id))
                    }
//                    if (Objects.nonNull(departmentFilter.courseList)) {
//                        predicateList.add(
//                            criteriaBuilder.equal(
//                                root.get<Any>("courseList"),
//                                departmentFilter.courseList
//                            )
//                        )
//                    }
                    if (Objects.nonNull(departmentFilter.name) && departmentFilter.name != "") {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), departmentFilter.name))
                    }
//                    if (Objects.nonNull(departmentFilter.teacherList)) {
//                        predicateList.add(
//                            criteriaBuilder.equal(
//                                root.get<Any>("teacherList"),
//                                departmentFilter.teacherList
//                            )
//                        )
//                    }
                    if (Objects.nonNull(departmentFilter.orderBy) && departmentFilter.orderBy != "") {
                        query.orderBy(criteriaBuilder.desc(root.get<Any>(departmentFilter.orderBy)))
                    } else {
                        query.orderBy(criteriaBuilder.asc(root.get<Any>("id")))
                    }
                    criteriaBuilder.and(*predicateList.toTypedArray())
                }
            }
        }

    }

}