package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Authentication;
import com.example.demo.entity.Role;

/**
 * UserDetails実装クラス
 */
public class LoginUserDetails implements UserDetails {

    /**
     * このUserDetails実装クラスを作成したら、STSより下記のようなfix提案があった。
     * The serializable class LoginUserDetail does not declare a static final serialVersionUID field of type long
     * 4 quick fixes available:
     *   1.Add Default serial version ID
     *   2.Add generated serial version ID
     *   3.Add @SupressWarnings 'serial' to 'LoginUserDetail'
     *   4.Configure problem severity
     * 多くの書籍やLYTA個人開発に倣って、ここでは「1.Add Default serial version ID」を選択して、
     * 「private static final long serialVersionUID = 1L;」を追記する。
     */
    private static final long serialVersionUID = 1L;

    /** 【DI】 */
    // DI
    private final Authentication authentication;
    private final List<SimpleGrantedAuthority> authorities;

    // コンストラクタインジェクション
    public LoginUserDetails(Authentication authentication) {
        this.authentication = authentication;

        // 権限リスト
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        // ログインユーザーの権限を格納
        authorities.add(new SimpleGrantedAuthority(authentication.getAuthority().toString()));
        // ADMIN ロールの場合、EDITORとGENERLの権限も付与
        if (authentication.getAuthority() == Role.ADMIN) {
            authorities.add(new SimpleGrantedAuthority(Role.EDITOR.toString()));
            authorities.add(new SimpleGrantedAuthority(Role.GENERAL.toString()));
        }
        // EDITOR ロールの場合、GENERLの権限も付与
        if (authentication.getAuthority() == Role.EDITOR) {
            authorities.add(new SimpleGrantedAuthority(Role.GENERAL.toString()));
        }
        this.authorities = authorities;
    }

    /** 【カスタム属性の名前と名字を格納】 */
    // 名前を取得
    public String getFirstName() {
        return authentication.getFirstName();
    }

    // 名字を取得
    public String getLastName() {
        return authentication.getLastName();
    }

    /** 【以下は、UserDetailsのメソッドをオーバーライド】 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /**
         * 書籍「Spring徹底入門_NTTデータ著_第2版」P442よりこのメソッドの説明を転載。
         * ユーザーに与えらている権限リストを返却するメソッド。このメソッドは許可処理で利用する。
         */
        return authorities;
    }

    @Override
    public String getPassword() {
        /**
         * 書籍「Spring徹底入門_NTTデータ著_第2版」P442よりこのメソッドの説明を転載。
         * 登録されているパスワードを返却するメソッド。このメソッドで返却したパスワードとクライアントから指定されたパスワードが
         * 一致しない場合は、DaoAuthenticationProviderはBadCredencialIsExcetionをスローする。
         */
        return authentication.getPassword();
    }

    @Override
    public String getUsername() {
        /**
         * 書籍「Spring徹底入門_NTTデータ著_第2版」P442よりこのメソッドの説明を転載。 ユーザー名を返却するメソッド。
         */
        return authentication.getUsername();
    }

}