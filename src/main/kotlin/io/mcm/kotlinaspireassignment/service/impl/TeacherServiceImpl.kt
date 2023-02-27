package io.mcm.kotlinaspireassignment.service.impl

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.TeacherException
import io.mcm.kotlinaspireassignment.model.TeacherRequest
import io.mcm.kotlinaspireassignment.model.TeacherResponse
import io.mcm.kotlinaspireassignment.model.dto.TeacherDto
import io.mcm.kotlinaspireassignment.model.entity.Teacher
import io.mcm.kotlinaspireassignment.repository.TeacherRepository
import io.mcm.kotlinaspireassignment.service.TeacherService
import io.mcm.kotlinaspireassignment.specification.TeacherSpecification
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class TeacherServiceImpl(val teacherRepository: TeacherRepository) : TeacherService {
    private val logger = LoggerFactory.getLogger(TeacherServiceImpl::class.java)

    @Value("\${default.pageSize.teachers:3}")
    private var defaultPageSize: Int = 1

    override fun findAll(): TeacherResponse {
        val teacherList = teacherRepository.findAll()
        return TeacherResponse(teacherList)
    }

    override fun findById(id: Int): TeacherResponse {
        val teacherByIdOptional = teacherRepository.findById(id)
        val teacherById = teacherByIdOptional.orElseThrow { throw TeacherException.TeacherNotFoundException() }
        return TeacherResponse(mutableListOf(teacherById))
    }

    override fun save(teacherRequest: TeacherRequest): TeacherResponse {
        val teacherRequestList = TeacherDto.getTeacherEntityListFromDtoList(teacherRequest.teacherList)
        val teacherList = teacherRepository.saveAll(teacherRequestList)
        return TeacherResponse(teacherList)
    }

    override fun update(teacherRequest: TeacherRequest): TeacherResponse {
        val teacherRequestList = TeacherDto.getTeacherEntityListFromDtoList(teacherRequest.teacherList)
        val teacherInDBList = mutableListOf<Teacher>()
        for (teacher in teacherRequestList) {
            if (null == teacher.id) {
                continue
            }
            val teacherInDB = teacherRepository.findById(teacher.id!!)
                .orElseThrow { throw TeacherException.TeacherNotFoundException() }
            teacherInDB.name = teacher.name
            teacherInDB.courseList = teacher.courseList
            teacherInDB.department = teacher.department
            teacherInDBList.add(teacherInDB)
        }
        val savedTeacherList = teacherRepository.saveAll(teacherInDBList)
        return TeacherResponse(savedTeacherList)
    }

    override fun delete(teacherRequest: TeacherRequest): TeacherResponse {
        val teacherInDBList = mutableListOf<Teacher>()
        for (teacher in teacherRequest.teacherList) {
            if (null == teacher.id) {
                continue
            }
            val teacherInDB = teacherRepository.findById(teacher.id)
                .orElseThrow { throw TeacherException.TeacherNotFoundException() }
            teacherInDBList.add(teacherInDB)
        }
        teacherRepository.deleteAll(teacherInDBList)
        return TeacherResponse(teacherInDBList)
    }

    override fun filter(teacherRequest: TeacherRequest): TeacherResponse {
        var page: Page<Teacher>
        val teacherFilter = teacherRequest.teacherFilter
        if (Objects.isNull(teacherFilter.pageNo)) {
            page = PageImpl(teacherRepository.findAll(TeacherSpecification.build(teacherFilter)))
        } else {
            if (Objects.isNull(teacherFilter.pageSize) || teacherFilter.pageSize == 0) {
                teacherFilter.pageSize = defaultPageSize
            }
            logger.debug("TeacherService.filter: pageNumber: ${teacherFilter.pageNo}, pageSize: ${teacherFilter.pageSize}")
            val pageable = PageRequest.of(teacherFilter.pageNo!! - 1, teacherFilter.pageSize!!)
            page = teacherRepository.findAll(TeacherSpecification.build(teacherFilter), pageable)
        }
        val list: List<Teacher> = page.content
        val teacherResponse = TeacherResponse(mutableListOf<Teacher>())
        if (list.isNotEmpty()) {
            teacherResponse.totalPages = page.totalPages
            teacherResponse.totalCount = page.totalElements
            teacherResponse.pageNo = page.number + 1
            for (teacher in list) {
                teacherResponse.teacherList.add(teacher)
            }
        }
        return teacherResponse
    }

    override fun findAllByJoiningDateBetween(
        joiningDateMin: String,
        joiningDateMax: String
    ): TeacherResponse {
        val sdFormat = SimpleDateFormat("yyyy-MM-dd")
        val teacherList = teacherRepository.findAllByJoiningDateBetween(
            sdFormat.parse(joiningDateMin),
            sdFormat.parse(joiningDateMax)
        )
        return TeacherResponse(teacherList)
    }

    override fun findAllBySalaryBetweenAndAgeBetween(salaryMin: Long, salaryMax: Long, ageMin: Int, ageMax: Int): TeacherResponse {
        val teacherList = teacherRepository.findAllBySalaryBetweenAndAgeBetween(salaryMin, salaryMax, ageMin, ageMax)
        return TeacherResponse(teacherList)
    }
}
