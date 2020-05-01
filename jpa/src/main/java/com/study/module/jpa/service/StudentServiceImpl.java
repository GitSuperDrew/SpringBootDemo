package com.study.module.jpa.service;

import com.google.common.collect.Lists;
import com.study.module.jpa.dao.StudentRepository;
import com.study.module.jpa.entity.Student;
import com.study.module.jpa.utils.DataTable;
import com.study.module.jpa.utils.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author Administrator
 */
@Service
public class StudentServiceImpl implements StudentService {
//    第一种注入方式：属性注入
//    @Autowired
//    private StudentRepository studentRepository;

//    第二种注入方式：构造器注入
//    private StudentRepository studentRepository;
//    public StudentServiceImpl(StudentRepository studentRepository){
//        this.studentRepository = studentRepository;
//    }

    /**
     * 第三种注入方式：setter注入
     */
    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long count() {
        return studentRepository.count();
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findAllSqlDao() {
        return studentRepository.findAllSqlDao();
    }

    @Override
    public String findStudentByStuIdEquals(Integer stuId) {
        return studentRepository.findStudentByStuIdEquals(stuId);
    }

    @Override
    public Student findById(Integer id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.get();
    }

    @Override
    public List<Student> pageFindAll(PageBean pageBean) {
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageCount(),
                "desc".equals(pageBean.getIsDesc()) ? Sort.by(pageBean.getOrderColumn()).descending() :
                        Sort.by(pageBean.getOrderColumn()).ascending());
        return studentRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Student> pageFindAllLikeStuName(String stuName) {
        return studentRepository.findStudentByStuName(stuName);
    }

    @Override
    public List<Student> pageByIds(List<Integer> stuIds, PageBean pageBean) {
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageCount(),
                "desc".equals(pageBean.getIsDesc()) ? Sort.by(pageBean.getOrderColumn()).descending() :
                        Sort.by(pageBean.getOrderColumn()).ascending());
        return this.studentRepository.findAllByStuIdIn(stuIds, pageable).getContent();
    }

    @Override
    public List<Student> pageLike(Integer stuId, String stuName, Integer stuAge, PageBean pageBean) {
        // 1.构建模糊条件查询信息
        Specification<Student> specification = new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                // 1.1断言集合
                List<Predicate> predicates = Lists.newArrayList();
                // stuId断言
                if (stuId != null) {
                    predicates.add(cb.equal(root.get("stuId"), stuId));
                }
                // stuName断言
                if (StringUtils.isNotBlank(stuName)) {
                    predicates.add(cb.like(root.get("stuName"), "%" + stuName + "%"));
                }
                // stuAge断言
                if (stuAge != null) {
                    predicates.add(cb.equal(root.get("stuAge"), stuAge));
                }
                // 1.2生成断言规则
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        // 2.自定义分页字段
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageCount(),
                "desc".equals(pageBean.getIsDesc()) ? Sort.by(pageBean.getOrderColumn()).descending() :
                        Sort.by(pageBean.getOrderColumn()).ascending());
        return this.studentRepository.findAll(specification, pageable).getContent();
    }

    @Override
    public List<Student> pageLikeName(String stuName, PageBean pageBean) {

        // 1.自定义规则，模糊匹配查询
        Specification<Student> specification = (Specification<Student>) (root, criteriaQuery, cb) -> {
            // 所有的断言
            List<Predicate> predicates = new ArrayList<>();
            // 添加断言
            if (StringUtils.isNotBlank(stuName)) {
                Predicate likeStuName = cb.like(root.get("stuName").as(String.class), "%" + stuName + "%");
                predicates.add(likeStuName);
            }
            // 构建查询
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        // 2.构建分页信息
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageCount(),
                "desc".equals(pageBean.getIsDesc()) ? Sort.by(pageBean.getOrderColumn()).descending() :
                        Sort.by(pageBean.getOrderColumn()).ascending());
        return this.studentRepository.findAll(specification, pageable).getContent();
    }

    @Override
    public List<Student> pageStuNameRegExp(boolean stuNameIsCN) {
        // 名字中是否含有英文：① 名字纯英文；② 名字纯中文；③ 名字既有中文又有英文
        // 匹配中文
        String stuNameRegExp = "[\\u4e00-\\u9fa5]";
        // 匹配英文
        String stuNameRegExp2 = "[^\\u4e00-\\u9fa5]";
        return studentRepository.findStudentsByStuNameRegex(stuNameIsCN ? stuNameRegExp : stuNameRegExp2);
    }

}
