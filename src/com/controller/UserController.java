package com.controller;

import com.po.Accounts;
import com.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.po.User;
import com.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
 * �����˻����������
 */
@Controller
public class UserController {
    @Autowired
    private AccountsService accountsService;

    @Autowired
    private UserService userService;

    //�鿴�����˻���Ϣ
    @RequestMapping("/findAllUser")
    public String findAllUser(String becomeAdmin,String deleteAccountMsg,String addAccountMsg,String urole,Model model) {
        List<User> userList = userService.findAllUserByUrole(urole);
        model.addAttribute("userList",userList);
        model.addAttribute("flag",urole);
        model.addAttribute("urole",urole);
        model.addAttribute("addAccountMsg",addAccountMsg);
        model.addAttribute("becomeAdmin",becomeAdmin);
        model.addAttribute("deleteAccountMsg",deleteAccountMsg);
        return "user/userlist";
    }

    //�鿴�����˻���Ϣ
    @RequestMapping("/userlist")
    public String userList(String uaccount,Model model) {
        User user1 = userService.findUserByUaccount(uaccount);
        model.addAttribute("user", user1);
        model.addAttribute("account", uaccount);
        return "user/userinfo";
    }

    //�޸ĸ����˻���Ϣ
    @RequestMapping("/userupdate")
    public String userUpdate(User user,Model model) {
        userService.updateUser(user);
        //���°��˺ŵ�ֵ����uaccount���Ա��޸ĺ���Ը����˺Ų�ѯ������Ϣ
        model.addAttribute("uaccount",user.getUaccount());
        model.addAttribute("account", user.getUaccount());
        return "redirect:userlist";
    }

    //��Ϊ����Ա
    @RequestMapping("/becomeAdmin")
    public String becomeAdmin(String urole,String account,Model model) {
        User user = userService.findUserByUaccount(account);
        user.setUrole("����Ա");
        userService.updateUser(user);
        Accounts accounts = accountsService.findAccountByAccount(account);
        accounts.setRole("����Ա");
        accountsService.updateAccounts(accounts);
        model.addAttribute("urole",urole);
        model.addAttribute("becomeAdmin","����Ȩ�޳ɹ���");
        return "redirect:findAllUser";
    }

    //ɾ���˻�
    @RequestMapping("/deleteAccount")
    public String deleteAccount(String account,String flag,Model model) {
        userService.deleteUserByUaccount(account);
        accountsService.deleteAccount(account);
        model.addAttribute("urole",flag);
        model.addAttribute("deleteAccountMsg","ɾ���˺ųɹ���");
        return "redirect:findAllUser";
    }

    //��ת���޸�����ҳ��
    @RequestMapping("/toUpdatePwd")
    public String toUpdatePwd(String uaccount,String pwd,Model model) {
        //�Ѵ�commonҳ�洫�������˺����봫��updatePwdҳ��
        model.addAttribute("uaccount",uaccount);
        model.addAttribute("pwd",pwd);
        return "user/updatePwd";
    }

    //�޸����������
    @RequestMapping("/updatePwd")
    public String updatePwd(String uaccount, String pwd, String oldPwd, String newPwd, String confirmPwd, Model model, HttpSession session) {
        if ("".equals(oldPwd)) {
            model.addAttribute("pwdMsg","������ԭ���룡");
            return "user/updatePwd";
        }
        else if("".equals(newPwd)) {
            model.addAttribute("pwdMsg","�����������룡");
            return "user/updatePwd";
        }
        else if ("".equals(confirmPwd)) {
            model.addAttribute("pwdMsg","������ȷ�����룡");
            return "user/updatePwd";
        }
        else if (!(pwd.equals(oldPwd))) {
            model.addAttribute("pwdMsg","ԭ�����������");
            return "user/updatePwd";
        }
        else if (newPwd.length()<8) {
            model.addAttribute("pwdMsg","�����ʽ����ȷ��");
            return "user/updatePwd";
        }
        else if (!(newPwd.equals(confirmPwd))) {
            model.addAttribute("pwdMsg","���������벻һ�£�");
            return "user/updatePwd";
        }

        Accounts accounts = new Accounts();
        accounts.setAccount(uaccount);
        accounts.setPwd(newPwd);
        accountsService.updatePassword(accounts);

        User user = new User();
        user.setUaccount(uaccount);
        user.setUpwd(newPwd);
        userService.updateUser(user);
        session.invalidate();
        model.addAttribute("msg","�����޸ĳɹ��������µ�¼��");
        return "login";
    }

    @RequestMapping("/out")
    public String out(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @RequestMapping("/addAccount")
    public String addAccount(User user,String confirmUpwd, Model model) {
        Accounts account = accountsService.findAccountByAccount(user.getUaccount());

        if ("".equals(user.getUaccount())) {
            model.addAttribute("addAccountMsg","�������˺ţ�");
        }
        else if("".equals(user.getUpwd())) {
            model.addAttribute("addAccountMsg","���������룡");
        }
        else if("".equals(confirmUpwd)) {
            model.addAttribute("addAccountMsg","������ȷ�����룡");
        }
        else if("".equals(user.getUname())) {
            model.addAttribute("addAccountMsg","���������֣�");
        }
        else if("".equals(user.getUphone())) {
            model.addAttribute("addAccountMsg","�������ֻ����룡");
        }
        else if (!confirmUpwd.equals(user.getUpwd())) {
            model.addAttribute("addAccountMsg","���벻һ�£������˻�ʧ��");
        }
        else if (account!=null) {
            model.addAttribute("addAccountMsg","�˺��Ѵ��ڣ������˻�ʧ��");
        }
        else {
            Accounts account2 = new Accounts();
            account2.setAccount(user.getUaccount());
            account2.setPwd(user.getUpwd());
            account2.setRole(user.getUrole());
            accountsService.addAccounts(account2);
            userService.addUser(user);
            model.addAttribute("addAccountMsg","�����˻��ɹ���");
        }
        model.addAttribute("urole",user.getUrole());
        return "redirect:findAllUser";
    }
}
