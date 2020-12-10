package com.controller;

import java.util.List;

import com.po.*;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * �ر���Ŀ�����
 */

@Controller
public class NotifyController {

    @Autowired
    NotifyService notifyService;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    //���ݽ��շ��˺Ų鿴������Ϣ֪ͨ�����ռ���
    @RequestMapping("/notifylist")
    public String notifylist(String nto,String ntype,String role,String replyMsg,Model model) {
        List<Notify> notifyList = null;
        if("����Ա".equals(role)) {
            notifyList = notifyService.findNotifiesByRole(role);
        }
        else {
            Notify notify = new Notify();
            notify.setNto(nto);
            notify.setNtype(ntype);
            notifyList = notifyService.findNotifiesByNtoNtype(notify);
        }
        model.addAttribute("notifyList",notifyList);
        model.addAttribute("replyMsg",replyMsg);
        model.addAttribute("ntype",ntype);
        return "notify/notifylist";
    }

    //�ύ���������
    @RequestMapping("/sendNotify")
    public String sendNotify(Notify notify,String flag,Model model) {
        User user1 = userService.findUserByUaccount(notify.getNfrom());
        Notify notify1 = notify;
        notify1.setNfromName(user1.getUname());
        notify1.setNtoRole(notify.getNtoRole());

        //�Ȳ�����Ϣ��������û���ύ������Ϣ��δ��˹���
        Notify notify2 = notifyService.findNotifyToTeacher(notify1);
        if (notify2!=null) {
            model.addAttribute("applyCourse","���ύ�������룬�벻Ҫ�ظ��ύ��");
        }
        else {
            notifyService.addNotify(notify1);
            model.addAttribute("applyCourse","���ύ���룬�ȴ���ˣ�");
        }

        model.addAttribute("saccount",notify.getNfrom());
        model.addAttribute("flag",flag);
        model.addAttribute("caccount",notify.getNcourse());
        return "redirect:courselist";
    }

    //�ύ�γ����������
    @RequestMapping("/sendCourseNotify")
    public String sendCourseNotify(Notify notify,Model model) {
        User user1 = userService.findUserByUaccount(notify.getNfrom());
        Notify notify1 = notify;
        notify1.setNfromName(user1.getUname());
        notify1.setNtoRole(notify.getNtoRole());
        Course course = courseService.findCourseByCaccount(notify.getNcourse());
        notify1.setNcourseName(course.getCname());
        notify1.setNcourseTaccount(course.getTaccount());
        notify1.setNcourseTeacher(course.getCteacher());
        notify1.setNtype(notify.getNtype());

        //�Ȳ�����Ϣ��������û���ύ������Ϣ��δ��˹���
        Notify notify2 = notifyService.findNotifyToAdmin(notify1);
        if (notify2!=null) {
            model.addAttribute("applyCourse","���ύ�������룬�벻Ҫ�ظ��ύ��");
        }
        else {
            notifyService.addNotify(notify1);
            model.addAttribute("applyCourse","���ύ���룬�ȴ���ˣ�");
        }

        model.addAttribute("taccount",notify.getNcourseTaccount());
        return "redirect:teacherCourselist";
    }

    //ɾ��ѡ�е���Ϣ������
    @RequestMapping("/notifyDeleteSelected")
    public String notifyDeleteSelected(int[] nnoArray,String ntoRole,String ntype,String nto,Model model) {
        notifyService.deleteNotifiesSelected(nnoArray);
        model.addAttribute("nto",nto);
        model.addAttribute("role",ntoRole);
        model.addAttribute("ntype",ntype);
        return "redirect:notifylist";
    }

    //�����Ϣ�Ŀ�����
    @RequestMapping("/notifyExamine")
    public String notifyExamine(Notify notify,Model model) {
        //������Ϣ֪ͨ�������Ϣ״̬
        notifyService.updateNotify(notify.getNno(),notify.getNstate());
        String content = notify.getNcontent();
        //�༭�ظ���Ϣ
        Notify notify1 = new Notify();
        notify1.setNfrom(notify.getNto());
        notify1.setNfromName(notify.getNtoName());
        notify1.setNto(notify.getNfrom());
        notify1.setNtoName(notify.getNfromName());
        notify1.setNcourse(notify.getNcourse());
        notify1.setNstate("δ��");
        notify1.setNtype(notify.getNtype());
        Course course = courseService.findCourseByCaccount(notify.getNcourse());
        if(content.contains("�������γ�")) {
            if ("��ͬ��".equals(notify.getNstate())) {
                courseService.addSc_table(notify.getNfrom(),notify.getNcourse());
//                courseService.updateSc_tableByCaccount(notify.getNfrom(),notify.getNcourse());
                notify1.setNcontent("��ͬ�������γ̡�" + course.getCname() + "��");
            }
            else if("�Ѿܾ�".equals(notify.getNstate())){
                notify1.setNcontent("�Ѿܾ������γ̡�" + course.getCname() + "��");
            }
            //���ͻظ���Ϣ
            notifyService.addNotify(notify1);
        }
        else if(content.contains("�����˳��γ�")) {
            if ("��ͬ��".equals(notify.getNstate())) {
                courseService.deleteSc_table(notify.getNfrom(),notify.getNcourse());
//                courseService.updateSc_tableByCaccount("",notify.getNcourse());
                notify1.setNcontent("��ͬ�����˳��γ̡�" + course.getCname() + "��");
            }
            else if("�Ѿܾ�".equals(notify.getNstate())){
                notify1.setNcontent("�Ѿܾ����˳��γ̡�" + course.getCname() + "��");
            }            //���ͻظ���Ϣ
            notifyService.addNotify(notify1);
        }
        model.addAttribute("nto",notify.getNto());
        model.addAttribute("ntype",notify.getNtype());
        return "redirect:notifylist";

    }

    //������Ϣ״̬�Ŀ�����
    @RequestMapping("/updateNotify")
    public String updateNotify(Notify notify,Model model) {
        notifyService.updateNotify(notify.getNno(),notify.getNstate());
        model.addAttribute("nto",notify.getNto());
        model.addAttribute("ntype",notify.getNtype());
        return "redirect:notifylist";

    }

    //�����Ϣ�Ŀ�����
    @RequestMapping("/notifyAdminExamine")
    public String notifyAdminExamine(Notify notify,Model model) {
        //������Ϣ֪ͨ�������Ϣ״̬
        notifyService.updateNotify(notify.getNno(),notify.getNstate());
        String content = notify.getNcontent();
        //�༭�ظ���Ϣ
        Notify notify1 = new Notify();
        notify1.setNfromName("����Ա");
        notify1.setNto(notify.getNfrom());
        notify1.setNtoName(notify.getNfromName());
        notify1.setNstate("δ��");
        notify1.setNtype(notify.getNtype());
        if(content.contains("���봴���γ�")) {
            if ("��ͬ��".equals(notify.getNstate())) {
                Course course = new Course();
                course.setCaccount(notify.getNcourse());
                course.setCname(notify.getNcourseName());
                course.setTaccount(notify.getNcourseTaccount());
                course.setCteacher(notify.getNcourseTeacher());
                courseService.addCourse(course);
                notify1.setNcontent("��ͬ����Ŀγ̡�" + notify.getNcourseName() + "����������");
            }
            else if("�Ѿܾ�".equals(notify.getNstate())){
                notify1.setNcontent("�Ѿܾ���Ŀγ̡�" + notify.getNcourseName() + "����������");
            }
            //���ͻظ���Ϣ
            notifyService.addNotify(notify1);
        }
        else if(content.contains("��������γ�")) {
            if ("��ͬ��".equals(notify.getNstate())) {
                courseService.deleteCourse(notify.getNcourse());
                courseService.deleteSc_tableByCaccount(notify.getNcourse());
                notify1.setNcontent("��ͬ����Ŀγ̡�" + notify.getNcourseName() + "����������");
            }
            else if("�Ѿܾ�".equals(notify.getNstate())){
                notify1.setNcontent("�Ѿܾ���Ŀγ̡�" + notify.getNcourseName() + "����������");
            }
            //���ͻظ���Ϣ
            notifyService.addNotify(notify1);
        }
        model.addAttribute("nto",notify.getNto());
        model.addAttribute("role","����Ա");
        model.addAttribute("ntype",notify.getNtype());
        return "redirect:notifylist";

    }

    //���Ͷ��ſ�����
    @RequestMapping("/sendMsg")
    public String sendMsg(Notify notify,String lrole,Model model) {
        User user = userService.findUserByUaccount(notify.getNfrom());
        User user1 = userService.findUserByUaccount(notify.getNto());
        Notify notify1 = notify;
        notify1.setNfromName(user.getUname());
        notify1.setNtoName(user1.getUname());
        notify1.setNtoRole(user1.getUrole());
        notify1.setNstate("δ��");
        notify1.setNtype("����");
        notifyService.addNotify(notify1);

        model.addAttribute("lrole",lrole);
        model.addAttribute("laccount",notify.getNfrom());
        model.addAttribute("sendMsg","���ŷ��ͳɹ���");
        return "redirect:liaisonlist";
    }

    //���Ͷ��ſ�����
    @RequestMapping("/replyMsg")
    public String replyMsg(Notify notify,Model model) {
        User user = userService.findUserByUaccount(notify.getNfrom());
        User user1 = userService.findUserByUaccount(notify.getNto());
        Notify notify1 = new Notify();
        notify1.setNfrom(user1.getUaccount());
        notify1.setNfromName(user1.getUname());
        notify1.setNto(user.getUaccount());
        notify1.setNtoName(user.getUname());
        notify1.setNtoRole(user.getUrole());
        notify1.setNcontent(notify.getNcontent());
        notify1.setNstate("δ��");
        notify1.setNtype("����");
        notifyService.addNotify(notify1);

        model.addAttribute("nto",notify1.getNfrom());
        model.addAttribute("ntype",notify1.getNtype());
        model.addAttribute("replyMsg","���Żظ��ɹ���");
        return "redirect:notifylist";
    }

}
