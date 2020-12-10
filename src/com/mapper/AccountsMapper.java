package com.mapper;

import org.apache.ibatis.annotations.Param;

import com.po.Accounts;

public interface AccountsMapper {
    //��¼ʱͨ���˺ź�������֤
    Accounts selectAccountsByAccountByPwd(@Param("account")String account,@Param("pwd")String pwd);
    //ע��ʱͨ���˺���֤
    Accounts selectAccountsByAccount(@Param("account")String account);
    //ע��ɹ�ʱ����˺ŵ����ݿ�
    void addAccounts(Accounts accounts);
    //�޸�����
    void updatePassword(Accounts accounts);

    void deleteAccount(String account);

    void updateAccounts(Accounts accounts);
}
