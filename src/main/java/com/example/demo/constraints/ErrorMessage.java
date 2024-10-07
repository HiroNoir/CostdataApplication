package com.example.demo.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * エラーメッセージ定義クラス（チェックOK、正常終了以外）
 */
public class ErrorMessage {

    /** エラーメッセージ情報マップ */
    private final static Map<ErrorKinds, List<String>> errorMessageMap = new HashMap<ErrorKinds, List<String>>() {
        private static final long serialVersionUID = 1L;

        {
            // 従業員コード重複チェック用エラーメッセージ
            put(ErrorKinds.CODE_DUPLICATE_ERROR, new ArrayList<String>(Arrays.asList("codeError", "既に登録されているユーザー名です")));
            // パスワード空白チェック用エラーメッセージ
            put(ErrorKinds.BLANK_ERROR, new ArrayList<String>(Arrays.asList("passwordError", "値を入力してください")));
            // パスワードの半角英数字チェック用エラーメッセージ
            put(ErrorKinds.HALFSIZE_ERROR,
                    new ArrayList<String>(Arrays.asList("passwordError", "パスワードは半角英数字のみで入力してください")));
            // パスワードの8文字～16文字チェック用エラーメッセージ
            put(ErrorKinds.RANGECHECK_ERROR,
                    new ArrayList<String>(Arrays.asList("passwordError", "8文字以上16文字以下で入力してください")));
            // ログイン中の従業員削除チェック用エラーメッセージ
            put(ErrorKinds.LOGINCHECK_ERROR,
                    new ArrayList<String>(Arrays.asList("deleteError", "ログイン中のユーザーを削除することは出来ません")));
            // 削除メソッド権限チェックー用エラーメッセージ → 削除メソッド権限はコントローラークラスの@PreAuthorizeで管理することに変更（従業員テーブルのみSecurityConfigによる権限制限）
            // put(ErrorKinds.DELETEMETHOD_ERROR,
            //         new ArrayList<String>(Arrays.asList("deleteError", "削除する権限がありませんので削除することは出来ません")));
            // 内訳頭紙登録時の内訳頭紙区分重複チェック用エラーメッセージ
            put(ErrorKinds.bcoCoId_DUPLICATE_ERROR,
                    new ArrayList<String>(Arrays.asList("bcoCoIdError", "既に登録されているデータです")));
            // 内訳種目登録時の用途詳細区分空白チェックエラー
            put(ErrorKinds.PURPOSE_BLANK_ERROR,
                    new ArrayList<String>(Arrays.asList("purposeMatchError", "用途概略区分を選択してください。表示されていない場合は、上記※を確認して再選択してください。")));
            // 内訳種目登録時の入力面積マッチングチェックエラー
            put(ErrorKinds.AREA_MATCHING_ERROR, new ArrayList<String>(Arrays.asList("areaMatchError",
                    "新営工事・とりこわし工事では建築面積・延床面積を、改修工事では改修面積を、外構工事では外構面積を入力してください。それ以外の項目には「0」を入力してください。")));
            // 内訳情報登録時の内訳情報区分重複チェック用エラーメッセージ
            put(ErrorKinds.idbIiId_DUPLICATE_ERROR,
                    new ArrayList<String>(Arrays.asList("idbIiIdError", "既に登録されているデータです")));
            // 内訳科目登録時の内訳科目区分重複チェック用エラーメッセージ
            put(ErrorKinds.bcsCsId_DUPLICATE_ERROR,
                    new ArrayList<String>(Arrays.asList("bcsCsIdError", "既に登録されているデータです")));
        }
    };

    /** エラーメッセージマップにあるエラーかどうかのチェック */
    public static boolean contains(ErrorKinds errorKinds) {
        if (errorMessageMap.containsKey(errorKinds)) {
            return true;
        } else {
            return false;
        }
    }

    /** エラーメッセージの名称を取得 */
    public static String getErrorName(ErrorKinds errorKinds) {
        return errorMessageMap.get(errorKinds).get(0);
    }

    /** エラーメッセージの値を取得 */
    public static String getErrorValue(ErrorKinds errorKinds) {
        return errorMessageMap.get(errorKinds).get(1);
    }

}