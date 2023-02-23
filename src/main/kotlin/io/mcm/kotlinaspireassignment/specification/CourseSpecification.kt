package io.mcm.kotlinaspireassignment.specification

import io.mcm.kotlinaspireassignment.model.dto.CourseFilter
import io.mcm.kotlinaspireassignment.model.entity.Course
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.criteria.Predicate

@Component
class CourseSpecification {
    companion object {

        fun build(courseFilter: CourseFilter): Specification<Course> {
            return Specification { root, query, criteriaBuilder ->
                if (Objects.isNull(courseFilter)) {
                    return@Specification criteriaBuilder.and(*arrayOfNulls(0))
                }
                val predicateList: MutableList<Predicate> = ArrayList()
                if (Objects.nonNull(courseFilter.id)) {
                    predicateList.add(criteriaBuilder.equal(root.get<Any>("id"), courseFilter.id))
                }
                if (Objects.nonNull(courseFilter.name)) {
                    predicateList.add(criteriaBuilder.equal(root.get<Any>("name"), courseFilter.name))
                }
//                if (Objects.nonNull(courseFilter.department)) {
//                    predicateList.add(criteriaBuilder.equal(root.get<Any>("department"), courseFilter.department))
//                }
                if (Objects.nonNull(courseFilter.startDate)) {
                    predicateList.add(criteriaBuilder.equal(root.get<Any>("startDate"), courseFilter.startDate))
                }
                if (Objects.nonNull(courseFilter.endDate)) {
                    predicateList.add(criteriaBuilder.equal(root.get<Any>("endDate"), courseFilter.endDate))
                }
//                if (Objects.nonNull(courseFilter.teacher)) {
//                    predicateList.add(criteriaBuilder.equal(root.get<Any>("teacher"), courseFilter.teacher))
//                }
                if (Objects.nonNull(courseFilter.orderBy)) {
                    query.orderBy(criteriaBuilder.desc(root.get<Any>(courseFilter.orderBy)))
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get<Any>("id")))
                }
                criteriaBuilder.and(*predicateList.toTypedArray())
            }
        }

    }
}