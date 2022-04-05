/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import model.nhanVien;

/**
 *
 * @author ADMIN
 */
public class Auth {
     public static nhanVien USER = null;
     public static void clear(){
         Auth.USER=null;
     }
     public static boolean isLogin(){
         return Auth.USER !=null;
     }
     public static boolean isManager(){
         return Auth.isLogin() && USER.isVaiTro();
     }
    
}
