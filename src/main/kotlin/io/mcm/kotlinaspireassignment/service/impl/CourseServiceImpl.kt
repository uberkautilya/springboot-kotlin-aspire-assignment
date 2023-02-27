package io.mcm.kotlinaspireassignment.service.impl

import io.mcm.kotlinaspireassignment.exceptionhandling.exception.CourseException
import io.mcm.kotlinaspireassignment.model.CourseRequest
import io.mcm.kotlinaspireassignment.model.CourseResponse
import io.mcm.kotlinaspireassignment.model.dto.CourseDto
import io.mcm.kotlinaspireassignment.model.entity.Course
import io.mcm.kotlinaspireassignment.model.entity.Student
import io.mcm.kotlinaspireassignment.repository.CourseRepository
import io.mcm.kotlinaspireassignment.repository.DepartmentRepository
import io.mcm.kotlinaspireassignment.repository.StudentRepository
import io.mcm.kotlinaspireassignment.repository.TeacherRepository
import io.mcm.kotlinaspireassignment.service.CourseService
import io.mcm.kotlinaspireassignment.specification.CourseSpecification
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.jvm.optionals.getOrDefault

@Service
class CourseServiceImpl(val courseRepository: CourseRepository) : CourseService {

    @Autowired
    lateinit var messageSource: MessageSource;
    @Autowired
    lateinit var departmentRepository: DepartmentRepository
    @Autowired
    lateinit var teacherRepository: TeacherRepository
    @Autowired
    lateinit var studentRepository: StudentRepository

    /**
     * Substitute the argument text to the language of Locale provided
     * Default: US
     */
    private fun internationalizeText(fieldText: String?, locale: Locale?): String? {
        if (null == fieldText) {
            return null
        }
        return try {
            var internationalizedCourseName: String = if (locale == null) {
                //Post and Put methods have validations against blank course name
                messageSource.getMessage(fieldText, null, Locale.US)
            }else{
                messageSource.getMessage(fieldText, null, locale)
            }
            internationalizedCourseName
        } catch (e: NoSuchMessageException) {
            logger.warn("No message for $fieldText")
            fieldText
        }
    }

    private val logger = LoggerFactory.getLogger(CourseServiceImpl::class.java)

    @Value("\${default.pageSize.courses:3}")
    private var defaultPageSize: Int = 1

    /**
     * Fetch all Course entities from the DB
     */
    override fun findAll(): CourseResponse {
        val courseList = courseRepository.findAll()
        logger.info("$courseList")
        return CourseResponse(courseList)
    }

    /**
     * Fetch the Course entity with the provided id
     * Course content of the entity fetched is written to the disk as well
     */
    override fun findById(id: Int, locale: Locale?): CourseResponse {
        val courseById =
            courseRepository.findById(id).orElseThrow { CourseException.CourseNotFoundException() }
        val courseContentFileName = courseById.fileName
        if (StringUtils.isNotBlank(courseContentFileName)) {
            val fileName = courseContentFileName!!.split(".")
            val parentPath = Paths.get("D:/Assignment/${fileName[0]}")
            Files.createDirectories(parentPath)
            val filePath =
                Paths.get("D:/Assignment/${fileName[0]}/$courseContentFileName")
            Files.write(filePath, courseById.courseContent)
        } else {
            logger.warn("Course content file name is null. Not saving data on disk")
        }
        courseById.name = internationalizeText(courseById.name, locale)
        return CourseResponse(mutableListOf(courseById))
    }

    /**
     * Saves list of Course entities in the CourseRequest to DB
     */
    override fun save(courseRequest: CourseRequest): CourseResponse {
        val courseRequestList = CourseDto.getCourseEntityListFromDtoList(courseRequest.courseList)
        for (course in courseRequestList) {
            if (course.department != null && course.department!!.id != null) {
                course.department = departmentRepository.findById(course.department!!.id!!).getOrDefault(course.department)
            }
            if (course.teacher != null && course.teacher!!.id != null) {
                course.teacher = teacherRepository.findById(course.teacher!!.id!!).getOrDefault(course.teacher)
            }
            if (course.studentList != null) {
                val studentInDBList = mutableListOf<Student>()
                for (student in course.studentList!!) {
                    if (student.id != null) {
                        var studentInDB: Student = studentRepository.findById(student.id!!).getOrDefault(student)
                        studentInDBList.add(studentInDB)
                    } else {
                        //Create this record, since the id is not available
                        studentInDBList.add(student)
                    }
                }
                course.studentList = studentInDBList
            }
        }
        val courseList = courseRepository.saveAll(courseRequestList)
        return CourseResponse(courseList)
    }

    /**
     * Updates a list of Course entities in the CourseRequest
     */
    override fun update(courseRequest: CourseRequest): CourseResponse {
        val courseRequestList = CourseDto.getCourseEntityListFromDtoList(courseRequest.courseList)
        val courseInDBList = mutableListOf<Course>()
        for (course in courseRequestList) {
            if (null == course.id) {
                continue
            }
            val courseInDB = courseRepository.findById(course.id!!)
                .orElseThrow { throw CourseException.CourseNotFoundException() }
            courseInDB.name = course.name
            courseInDB.department = course.department
            courseInDB.endDate = course.endDate
            courseInDB.startDate = course.startDate
            courseInDB.studentList = course.studentList
            courseInDB.teacher = course.teacher
            courseInDBList.add(courseInDB)
        }
        val savedCourseList = courseRepository.saveAll(courseInDBList)
        return CourseResponse(savedCourseList)
    }

    /**
     * Deletes a list of Course entities from the DB
     */
    override fun delete(courseRequest: CourseRequest): CourseResponse {
        val courseInDBList = mutableListOf<Course>()
        for (course in courseRequest.courseList) {
            if (null == course.id) {
                continue
            }
            val courseInDB = courseRepository.findById(course.id)
                .orElseThrow { throw CourseException.CourseNotFoundException() }
            courseInDBList.add(courseInDB)
        }
        courseRepository.deleteAll(courseInDBList)
        return CourseResponse(courseInDBList)
    }

    /**
     * Filter Course entities as per the courseFilter, of the CourseRequest
     */
    override fun filter(courseRequest: CourseRequest): CourseResponse {
        var page: Page<Course>
        val courseFilter = courseRequest.courseFilter
        if (Objects.isNull(courseFilter.pageNo)) {
            page = PageImpl(courseRepository.findAll(CourseSpecification.build(courseFilter)))
        } else {
            if (Objects.isNull(courseFilter.pageSize) || courseFilter.pageSize == 0) {
                courseFilter.pageSize = defaultPageSize
            }
            logger.debug("CourseService.filter: pageNumber: ${courseFilter.pageNo}, pageSize: ${courseFilter.pageSize}")
            val pageable = PageRequest.of(courseFilter.pageNo!! - 1, courseFilter.pageSize!!)
            page = courseRepository.findAll(CourseSpecification.build(courseFilter), pageable)
        }
        val list: List<Course> = page.content
        val courseResponse = CourseResponse(mutableListOf())
        if (list.isNotEmpty()) {
            courseResponse.totalPages = page.totalPages
            courseResponse.totalCount = page.totalElements
            courseResponse.pageNo = page.number + 1
            for (course in list) {
                courseResponse.courseList.add(course)
            }
        }
        return courseResponse
    }

    /**
     * Update a Course entity by Id: Add its CourseContent to DB
     */
    override fun updateCourseContent(courseId: Int, courseContent: MultipartFile): CourseResponse {
        if (0 == courseId) {
            throw CourseException.CourseNotFoundException()
        }
        if (null == courseContent.originalFilename || courseContent.originalFilename == "") {
            throw CourseException.InvalidFileNameForCourseContentException()
        }
        val fileNameWithExtension: String = courseContent.originalFilename!!
        val course = courseRepository.findById(courseId).orElseThrow {
            CourseException.CourseNotFoundException("Course Id: $courseId not found")
        }
        course.courseContent = courseContent.bytes
        course.fileName = fileNameWithExtension
        return CourseResponse(mutableListOf(courseRepository.save(course)))
    }

}
