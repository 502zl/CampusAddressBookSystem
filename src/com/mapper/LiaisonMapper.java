package com.mapper;

import java.util.List;

import com.po.Liaison;

public interface LiaisonMapper {
    //ͨ���˺Ų鿴������ϵ��
    List<Liaison> selectLiaisonsByAccountRole(Liaison liaison);
    //ͨ���˺š����������ģ����ѯ��ϵ��
    List<Liaison> selectLiaisonByLaccountLnameLrole(Liaison liaison);
    //ͨ���˺š��ֻ������ģ����ѯ��ϵ��
    List<Liaison> selectLiaisonByLaccountLphoneLrole(Liaison liaison);

    //ɾ��ѡ�е���ϵ��
    void deleteLiaisonByLnoByLaccount(Liaison liaison);
    //�����ϵ��
    void insertLiaison(Liaison liaison);
    //�޸���ϵ����Ϣ
    void updateLiaison(Liaison liaison);

    Liaison selectLiaisonByLaccountLnumber(Liaison liaison);

/*    Liaison selectLiaisonByLnumber(String lnumber);*/
}
