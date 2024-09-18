package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.constraints.ErrorKinds;
import com.example.demo.entity.BreakdownCd;
import com.example.demo.repository.BreakdownCdMapper;
import com.example.demo.service.BreakdownCdService;

import lombok.RequiredArgsConstructor;

/**
 * 内訳種目サービス実装クラス
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BreakdownCdServiceImpl implements BreakdownCdService {

    /** 【DI】 */
    // @RequiredArgsConstructorによりfinalで修飾されたフィールドだけを引数に受け取るコンストラクタを自動生成する
    // これにより「@Autowired」を使ったコンストラクタインジェクションの記述は不要となる
    private final BreakdownCdMapper mapper;
    // 他テーブルのデータを取得するため、他テーブルを扱うリポジトリインターフェスをDI

    /** 【合計取得】 */
    @Override
    public BreakdownCd sumFindById(Integer bcdBcoId) {
        return mapper.sumSelectById(bcdBcoId);
    }

    /** 【特定取得】 */
    @Override
    public List<BreakdownCd> findAllById(Integer bcdBcoId) {
        return mapper.selectAllById(bcdBcoId);
    }

    /** 【詳細検索取得】 */
    @Override
    public List<BreakdownCd> findAllByAdvancedSearchForm(Integer bcdCdId, Integer bcdPdId, String bcdTypeName) {
        return mapper.selectAllByAdvancedSearchForm(bcdCdId, bcdPdId, bcdTypeName);
    }

    /** 【一件取得】 */
    @Override
    public BreakdownCd findById(Integer bcdId) {
        return mapper.selectById(bcdId);
    }

    /** 【登録実行】 */
    @Override
    public ErrorKinds insert(BreakdownCd breakdownCd, LoginUserDetails loginUserDetails) {

        /** 用途詳細選択ェック */
        // 用途概略のpoIdが選択されていないことを示す、下記の条件をチェック
        // 対象データの取得
        if (breakdownCd.getBcdPdId() == 10100 || breakdownCd.getBcdPdId() == 10200 ||
            breakdownCd.getBcdPdId() == 10300 || breakdownCd.getBcdPdId() == 10400 ||
            breakdownCd.getBcdPdId() == 10500 || breakdownCd.getBcdPdId() == 10600 ||
            breakdownCd.getBcdPdId() == 10700 || breakdownCd.getBcdPdId() == 10800 ||
            breakdownCd.getBcdPdId() == 10900 || breakdownCd.getBcdPdId() == 11000 ||
            breakdownCd.getBcdPdId() == 11100 || breakdownCd.getBcdPdId() == 11200 ||
            breakdownCd.getBcdPdId() == 20100 || breakdownCd.getBcdPdId() == 20200) {
            // 選択ミスがあるためErrorKindsクラスのPURPOSE_MATCHING_ERRORを返す
            return ErrorKinds.PURPOSE_BLANK_ERROR;
        }

        /** 面積入力チェック */
        // 新営工事の場合は、改修面積と外構面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101010 || breakdownCd.getBcdCdId() == 102010
                || breakdownCd.getBcdCdId() == 103010 || breakdownCd.getBcdCdId() == 104010)
                && (breakdownCd.getBcdAreaRenovation() != 0 || breakdownCd.getBcdAreaExterior() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }
        // 改修工事の場合は、建築面積と延床面積と外構面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101020 || breakdownCd.getBcdCdId() == 102020
                || breakdownCd.getBcdCdId() == 103020 || breakdownCd.getBcdCdId() == 104020)
                && (breakdownCd.getBcdAreaBuilding() != 0 || breakdownCd.getBcdAreaTotalfloor() != 0
                        || breakdownCd.getBcdAreaExterior() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }
        // 外構工事の場合は、建築面積と延床面積と改修面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101030 || breakdownCd.getBcdCdId() == 102030
                || breakdownCd.getBcdCdId() == 103030 || breakdownCd.getBcdCdId() == 104030)
                && (breakdownCd.getBcdAreaBuilding() != 0 || breakdownCd.getBcdAreaTotalfloor() != 0
                        || breakdownCd.getBcdAreaRenovation() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }
        // とりこわし工事の場合は、改修面積と外構面積と改修面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101040 || breakdownCd.getBcdCdId() == 102040
                || breakdownCd.getBcdCdId() == 103040 || breakdownCd.getBcdCdId() == 104040)
                && (breakdownCd.getBcdAreaRenovation() != 0 || breakdownCd.getBcdAreaExterior() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }

        /** 登録に必要な情報をEntityに格納 */
        // 最終編集者の格納
        breakdownCd.setBcdLatestEditor(loginUserDetails.getUsername());
        // 登録日時と更新日時はMapper.xmlにてCURRENT_TIMESTAMPを格納しているので、ここでの格納は不要

        /** 登録処理 */
        // 登録実行
        mapper.insert(breakdownCd);
        // 登録成功したのでErrorKindsクラスのSUCCESSを返す
        return ErrorKinds.SUCCESS;

    }

    /** 【更新実行】 */
    @Override
    public ErrorKinds update(BreakdownCd breakdownCd, LoginUserDetails loginUserDetails) {

        /** 用途詳細選択ェック */
        // 用途概略のpoIdが選択されていないことを示す、下記の条件をチェック
        // 対象データの取得
        if (breakdownCd.getBcdPdId() == 10100 || breakdownCd.getBcdPdId() == 10200 ||
            breakdownCd.getBcdPdId() == 10300 || breakdownCd.getBcdPdId() == 10400 ||
            breakdownCd.getBcdPdId() == 10500 || breakdownCd.getBcdPdId() == 10600 ||
            breakdownCd.getBcdPdId() == 10700 || breakdownCd.getBcdPdId() == 10800 ||
            breakdownCd.getBcdPdId() == 10900 || breakdownCd.getBcdPdId() == 11000 ||
            breakdownCd.getBcdPdId() == 11100 || breakdownCd.getBcdPdId() == 11200 ||
            breakdownCd.getBcdPdId() == 20100 || breakdownCd.getBcdPdId() == 20200) {
            // 選択ミスがあるためErrorKindsクラスのPURPOSE_MATCHING_ERRORを返す
            return ErrorKinds.PURPOSE_BLANK_ERROR;
        }

        /** 面積入力チェック */
        // 新営工事の場合は、改修面積と外構面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101010 || breakdownCd.getBcdCdId() == 102010
                || breakdownCd.getBcdCdId() == 103010 || breakdownCd.getBcdCdId() == 104010)
                && (breakdownCd.getBcdAreaRenovation() != 0 || breakdownCd.getBcdAreaExterior() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }
        // 改修工事の場合は、建築面積と延床面積と外構面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101020 || breakdownCd.getBcdCdId() == 102020
                || breakdownCd.getBcdCdId() == 103020 || breakdownCd.getBcdCdId() == 104020)
                && (breakdownCd.getBcdAreaBuilding() != 0 || breakdownCd.getBcdAreaTotalfloor() != 0
                        || breakdownCd.getBcdAreaExterior() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }
        // 外構工事の場合は、建築面積と延床面積と改修面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101030 || breakdownCd.getBcdCdId() == 102030
                || breakdownCd.getBcdCdId() == 103030 || breakdownCd.getBcdCdId() == 104030)
                && (breakdownCd.getBcdAreaBuilding() != 0 || breakdownCd.getBcdAreaTotalfloor() != 0
                        || breakdownCd.getBcdAreaRenovation() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }
        // とりこわし工事の場合は、改修面積と外構面積と改修面積が「0」になっていることをチェック
        // 対象データの有無確認
        if ((breakdownCd.getBcdCdId() == 101040 || breakdownCd.getBcdCdId() == 102040
                || breakdownCd.getBcdCdId() == 103040 || breakdownCd.getBcdCdId() == 104040)
                && (breakdownCd.getBcdAreaRenovation() != 0 || breakdownCd.getBcdAreaExterior() != 0)) {
            // 入力ミスがあるためErrorKindsクラスのAREA_INPUT_MATCHING_ERRORを返す
            return ErrorKinds.AREA_MATCHING_ERROR;
        }

        /** 更新に必要な情報をEntityに格納 */
        // 最終編集者の格納
        breakdownCd.setBcdLatestEditor(loginUserDetails.getUsername());
        // 登録日時は更新しないため、Mapper.xmlの更新SQL文から削除してある。ここでの格納は不要
        // 更新日時はMapper.xmlにてCURRENT_TIMESTAMPを格納しているので、ここでの格納は不要
        // idはform.html内にinput（type="hidden"）仕込んであるため、ここでの格納は不要

        /** 更新処理 */
        // 更新実行
        mapper.update(breakdownCd);
        // 更新成功したのでErrorKindsクラスのSUCCESSを返す
        return ErrorKinds.SUCCESS;

    }

    /** 【削除実行】 */
    @Override
    public ErrorKinds delete(Integer bcdId) {

        /** 削除処理 */
        // 削除実行
        mapper.delete(bcdId);
        // 削除成功したのでErrorKindsクラスのSUCCESSを返す
        return ErrorKinds.SUCCESS;

    }

}