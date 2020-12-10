package com.controller;

import javax.servlet.http.HttpSession;

import com.po.Liaison;
import com.service.LiaisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.po.Accounts;
import com.po.User;
import com.service.AccountsService;
import com.service.UserService;

import java.util.List;

/*
 * �˻���������
 */

@Controller
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LiaisonService liaisonService;


    //��¼���ܿ���
    @RequestMapping("/login")
    public String login(String account, String pwd, Model model, HttpSession session) {
        Accounts accounts = accountsService.findAccountByAccountByPwd(account, pwd);
        if (accounts != null) {
//            session.setAttribute("uaccount", accounts.getAccount());
            //һ��ʼ�õ�model����Ϊҳ����ת��Ϣ�ᶪʧ�����Ը�����session������account
            session.setAttribute("account", account);
            session.setAttribute("pwd",pwd);
            session.setAttribute("role",accounts.getRole());

            User user = userService.findUserByUaccount(account);
            session.setAttribute("name",user.getUname());

            if ("����Ա".equals(accounts.getRole())) {
                model.addAttribute("urole","ѧ��");
                return "redirect:findAllUser";
            }
            else {
                model.addAttribute("laccount",account);
                model.addAttribute("lrole","ѧ��");
                return "redirect:liaisonlist";
            }
        }
        model.addAttribute("msg", "�˺Ż�����������������룡");
        return "login";

    }

    //ע�Ṧ�ܿ���
    @RequestMapping("/register")
    public String register(Accounts accounts, String confirmPwd,String uname, String phone, Model model) {
        Accounts accounts1 = accountsService.findAccountByAccount(accounts.getAccount());
        if (accounts1 != null) {
            model.addAttribute("fail", "�˺��Ѵ��ڣ�");
            return "register";
        } else if ("".equals(accounts.getAccount())) {
            model.addAttribute("fail", "δ�����˺ţ�");
            return "register";
        } else if ("".equals(phone)) {
            model.addAttribute("fail", "δ�����ֻ����룡");
            return "register";
        } else if ("".equals(accounts.getPwd())) {
            model.addAttribute("fail", "δ�������룡");
            return "register";
        } else if ("".equals(confirmPwd)) {
            model.addAttribute("fail", "���ٴ��������룡");
            return "register";
        } else if (!accounts.getPwd().equals(confirmPwd)) {
            model.addAttribute("fail", "�������벻һ�£�");
            return "register";
        }
        else {
            boolean vis = true;
            for (int i = 0; i < phone.length(); i++) {
                if (phone.charAt(i) <'0' || phone.charAt(i)>'9')
                    vis = false;
            }
            if (!vis) {
                model.addAttribute("fail", "�ֻ������ʽ����ȷ��");
                return "register";
            } else {
                //ҵ�������˻�����Ϣ����accounts
                accountsService.addAccounts(accounts);

                //ҵ�������˻���Ϣ��user��
                User user = new User();
                user.setUaccount(accounts.getAccount());
                user.setUpwd(accounts.getPwd());
                user.setUphone(phone);
                user.setUname(uname);
                user.setUrole(accounts.getRole());
                userService.addUser(user);
                model.addAttribute("msg", "ע��ɹ���");
                return "login";
            }
        }

    }

    //��ת��ע��ҳ��
    @RequestMapping("/toregister")
    public String toregister() {
        return "register";
    }


}
