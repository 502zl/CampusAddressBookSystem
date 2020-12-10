package com.mapper;

import java.util.List;

import com.po.Course;
import com.po.StudentCourse;

public interface CourseMapper {
    //ͨ��ѧ����Ų�ѯ��ѡ�����пγ̱��
    List<StudentCourse> findSelectedCourses(String saccount);

/*    //ͨ��ѧ�ű�Ų�ѯδѡ�����пγ̱��
    List<StudentCourse> findNotSelectedCourses(String saccount);*/

    Course selectCourseByCaccount(String caccount);

    List<Course> selectCoursesByTaccount(String taccount);

    //���ݽ�ʦ�ź͹ؼ��ֲ�ѯ�γ�
    List<Course> selectCoursesByTaccountKey(Course course);

    //���ݿγ̺ź͹ؼ��ֲ�ѯ�γ�
    Course selectCoursesByCaccountKey(Course course);

    void updateSc_tableByCaccount(StudentCourse studentCourse);

    List<StudentCourse> selectStudentByCaccount(String caccount);

    List<Course> selectAllCourses();

    void insertSc_table(StudentCourse studentCourse);

    void deleteSc_table(StudentCourse studentCourse);

    void insertCourse(Course course);

    void deleteCourseByCaccount(String caccount);

    List<Course> selectCoursesByKey(String cname);

    void deleteSc_tableByCaccount(String caccount);

    //ͨ���˺Ų鿴����ͨ����¼
//    List<History> selectHistoryByHaccount(String haccount);
//    //ɾ��ͨ����¼
//    void deleteHistoryByHnoByHaccount(History history);
//
//    List<History> selectHistoryByHaccountByHphone(History history);
//
//    void updateHistory(History history);
}
