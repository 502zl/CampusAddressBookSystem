package com.service;

import com.po.Accounts;

public interface AccountsService {
    //ͨ���˺��������
    Accounts findAccountByAccountByPwd(String account,String pwd);
    //ͨ���˺Ų���
    Accounts findAccountByAccount(String account);
    //����˺�
    void addAccounts(Accounts accounts);
    //�޸�����
    void updatePassword(Accounts accounts);

    void deleteAccount(String account);

    void updateAccounts(Accounts accounts);
}
