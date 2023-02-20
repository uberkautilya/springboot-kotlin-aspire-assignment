package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.DepartmentRequest
import io.mcm.kotlinaspireassignment.model.entity.Department
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Predicate

class DepartmentSpecification {
    companion object{

        fun build(request: DepartmentRequest): Specification<Department> {
            return Specification { root, query, criteriaBuilder ->
                if (Objects.isNull(request) || Objects.isNull(request.filterDepartment)) {
                    criteriaBuilder.and(*arrayOfNulls(0))
                } else {
                    val predicateList = mutableListOf<Predicate>()
                    val filterDepartment = request.filterDepartment
                    if (Objects.nonNull(filterDepartment.id)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), filterDepartment.id))
                    }
                    if (Objects.nonNull(filterDepartment.courseList)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("courseList"), filterDepartment.courseList))
                    }
                    if (Objects.nonNull(filterDepartment.name)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), filterDepartment.name))
                    }
                    if (Objects.nonNull(filterDepartment.teacherList)) {
                        predicateList.add(criteriaBuilder.equal(root.get<Any>("teacherList"), filterDepartment.teacherList))
                    }
                    if (Objects.nonNull(filterDepartment.orderBy)) {
                        query.orderBy(criteriaBuilder.desc(root.get<Any>(filterDepartment.orderBy)))
                    } else {
                        query.orderBy(criteriaBuilder.asc(root.get<Any>("id")))
                    }
                    criteriaBuilder.and(*predicateList.toTypedArray())
                }
            }
        }

    }

}