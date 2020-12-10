package com.controller;

import com.po.Course;
import com.po.Notify;
import com.po.StudentCourse;
import com.po.User;
import com.service.NotifyService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.CourseService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotifyService notifyService;

    //��ʾ�γ̿�����
    @RequestMapping("/courselist")
    public String courselist(String saccount,String flag,String addLiaisonCheckMsg,String applyCourse,Model model) {

        //��ѯȫ���γ�
        List<Course> allCourseList = courseService.findAllCourse();

        if("all".equals(flag)) {
            model.addAttribute("courseList",allCourseList);
            model.addAttribute("flag",flag);
            model.addAttribute("applyCourse",applyCourse);
            model.addAttribute("addLiaisonCheckMsg",addLiaisonCheckMsg);
            return "course/courselist";
        }
        //��ѯ��ѡ�γ�
        List<StudentCourse> list = courseService.findSelectedCourses(saccount);

        if ("notSelected".equals(flag)) {

            for (int i=0;i<list.size();i++) {
                StudentCourse studentCourse = list.get(i);
                for (int j=0;j<allCourseList.size();j++) {
                    if (allCourseList.get(j).getCaccount().equals(studentCourse.getCaccount())) {
                        allCourseList.remove(j);
                        break;
                    }
                }
            }
            model.addAttribute("courseList",allCourseList);
/*            model.addAttribute("flag",flag);
            model.addAttribute("applyCourse",applyCourse);
            return "course/courselist";*/
        }

        else if("selected".equals(flag)) {
            if (!list.isEmpty()) {
                List<Course> courseList = new ArrayList<Course>();
                for (int i=0;i<list.size();i++) {
                    StudentCourse studentCourse = (StudentCourse) list.get(i);
                    Course course = courseService.findCourseByCaccount(studentCourse.getCaccount());
                    courseList.add(course);
                }
                model.addAttribute("courseList",courseList);
            }
        }
        model.addAttribute("flag",flag);
        model.addAttribute("applyCourse",applyCourse);
        return "course/courselist";
    }

    //��ʾ��ʦ���̿γ�
    @RequestMapping("/teacherCourselist")
    public String teacherCourselist(String taccount,String flag,String applyCourse,Model model) {
        List<Course> courseList = courseService.findCoursesByTaccount(taccount);
        User user = userService.findUserByUaccount(taccount);
        model.addAttribute("cteacher",user.getUname());
        model.addAttribute("courseList",courseList);
        model.addAttribute("applyCourse",applyCourse);
        model.addAttribute("flag",flag);
        return "course/courselist";
    }

    //���ݿγ�����ģ����ѯ�γ�
    @RequestMapping("/toSelectCourselist")
    public String toSelectCourselist(String account,String flag,String role,String key,Model model) {
        if ("��ʦ".equals(role)) {
            List<Course> courseList = courseService.findCoursesByTaccountKey(account,key);
            model.addAttribute("courseList",courseList);
        }
        else if("ѧ��".equals(role)) {

            //��ѯ��ѡ�γ�
            List<StudentCourse> list = courseService.findSelectedCourses(account);

            if ("selected".equals(flag)) {
                if (!list.isEmpty()) {
                    List<Course> courseList = new ArrayList<Course>();
                    for (int i=0;i<list.size();i++) {
                        StudentCourse studentCourse = (StudentCourse) list.get(i);
                        Course course = courseService.findCourseByCaccountKey(studentCourse.getCaccount(),key);
                        if (course!=null) courseList.add(course);
                    }
                    model.addAttribute("courseList",courseList);
                }
            }

            else if ("notSelected".equals(flag)) {
                //��ѯȫ���γ�
                List<Course> allCourseList = courseService.findAllCourse();
                for (int i=0;i<list.size();i++) {
                    StudentCourse studentCourse = list.get(i);
                    for (int j=0;j<allCourseList.size();j++) {
                        if (allCourseList.get(j).getCaccount().equals(studentCourse.getCaccount())) {
                            allCourseList.remove(j);
                            break;
                        }
                    }
                }
                List<Course> courseList = new ArrayList<Course>();
                for (int i=0;i<allCourseList.size();i++) {
                    Course course = courseService.findCourseByCaccountKey(allCourseList.get(i).getCaccount(),key);
                    if (course!=null) courseList.add(course);
                }
                model.addAttribute("courseList",courseList);
            }
            else if("all".equals(flag)) {
                List<Course> courseList = courseService.findCoursesByKey(key);
                model.addAttribute("courseList",courseList);
            }

            model.addAttribute("flag",flag);

        }

        return "course/courselist";
    }


    @RequestMapping("/addCourse")
    public String addCourse(Course course, Model model) {
        Course course1 = courseService.findCourseByCaccount(course.getCaccount());
        if ("".equals(course.getCaccount())) {
            model.addAttribute("applyCourse","������γ̱�ţ�");
        }
        else if("".equals(course.getCname())) {
            model.addAttribute("applyCourse","������γ����ƣ�");
        }
        else if("".equals(course.getTaccount())) {
            model.addAttribute("applyCourse","�������ʦ��ţ�");
        }
        else if("".equals(course.getCteacher())) {
            model.addAttribute("applyCourse","�������ʦ������");
        }
        else if (course1!=null) {
            model.addAttribute("applyCourse","�γ��Ѵ��ڣ������γ�ʧ�ܣ�");
        }
        else {
            String taccount = course.getTaccount();
            User user = userService.findUserByUaccount(taccount);
            Notify notify = new Notify();
            notify.setNfrom(taccount);
            notify.setNfromName(user.getUname());
            notify.setNtoRole("����Ա");
            notify.setNcourse(course.getCaccount());
            notify.setNcourseName(course.getCname());
            notify.setNcourseTaccount(course.getTaccount());
            notify.setNcourseTeacher(course.getCteacher());
            notify.setNcontent("���봴���γ̡�"+course.getCname()+"��");
            notify.setNstate("δ���");
            notify.setNtype("��Ϣ");

            //�Ȳ�����Ϣ��������û���ύ������Ϣ��δ��˹���
            Notify notify2 = notifyService.findNotifyToAdmin(notify);
            if (notify2!=null) {
                model.addAttribute("applyCourse","���ύ�������룬�벻Ҫ�ظ��ύ��");
            }
            else {
                notifyService.addNotify(notify);
                model.addAttribute("applyCourse","���ύ���룬�ȴ���ˣ�");
            }
        }

        model.addAttribute("taccount",course.getTaccount());
        return "redirect:teacherCourselist";
    }

    //ɾ���γ�
    @RequestMapping("/deleteCourse")
    public String deleteCourse(String caccount,String flag,Model model) {
        courseService.deleteCourse(caccount);
        courseService.deleteSc_tableByCaccount(caccount);
        model.addAttribute("flag",flag);
        return "redirect:courselist";
    }

}
