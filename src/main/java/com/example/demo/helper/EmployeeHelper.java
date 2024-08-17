package com.example.demo.helper;

import com.example.demo.entity.Employee;
import com.example.demo.form.EmployeeForm;

/**
* 従業員サービスヘルパークラス
*/
public class EmployeeHelper {

    /** 【FormからEntityへ変換】 */
    public static Employee convertEntity(EmployeeForm form) {
        Employee entity = new Employee();
        entity.setCode(form.getCode());
        entity.setFirstName(form.getFirstName());
        entity.setLastName(form.getLastName());
        entity.setPassword(form.getPassword());
        entity.setRole(form.getRole());
        return entity;
    }

    /** 【EntityからFormへ変換】 */
    public static EmployeeForm convertForm(Employee entity) {
        EmployeeForm form = new EmployeeForm();
        form.setCode(entity.getCode());
        form.setFirstName(entity.getFirstName());
        form.setLastName(entity.getLastName());
        form.setPassword(entity.getPassword());
        form.setRole(entity.getRole());
        return form;
    }

}